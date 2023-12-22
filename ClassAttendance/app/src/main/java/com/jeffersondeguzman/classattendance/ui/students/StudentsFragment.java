package com.jeffersondeguzman.classattendance.ui.students;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.jeffersondeguzman.classattendance.R;
import com.jeffersondeguzman.classattendance.data.DataBaseHelper;
import com.jeffersondeguzman.classattendance.databinding.FragmentStudentsBinding;
import com.jeffersondeguzman.classattendance.ui.attendance.AttendanceFragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class StudentsFragment extends Fragment {
    FragmentStudentsBinding binding;
    StudentsAdapter studentsAdapter = null;
    Calendar c = Calendar.getInstance();
    Calendar e = Calendar.getInstance();
    DateFormat df = DateFormat.getInstance();
    String pattern = "MMMM dd, YYYY";
    SimpleDateFormat sdf = new SimpleDateFormat(pattern);
    String dateMeow = sdf.format(e.getTime());
    TextView attendanceDate;
    String awaw = "Set a Date";
    public static class Date{
        public static String date;
    }
    public static class WOWO {
        public static int OWOWOWO;
    }
    SwipeRefreshLayout swipeLayout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_students, container, false);
        final RecyclerView recyclerView = root.findViewById(R.id.rvStudents);
        DataBaseHelper db = new DataBaseHelper(root.getContext());
        List<StudentsModel> data = db.getAllStudents(AttendanceFragment.WEWE.WEWEWE);
        swipeLayout = (SwipeRefreshLayout) root.findViewById(R.id.swipeToRefresh);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.i("REFRESH","REFRESH");
                if (!Objects.equals(Date.date, dateMeow)){
                    saveStudentStatsToSharedPreferences(data);
                    db.updateAllStudentStat();
                    studentsAdapter.notifyDataSetChanged();
                    Navigation.findNavController(root).navigate(R.id.navigation_students);
                } else {
                    restoreDate();
                    restoreStudentStatsFromSharedPreferences(data);
                    Log.i("","AYO");
                }
                if (swipeLayout.isRefreshing()) {
                    swipeLayout.setRefreshing(false);
                }
            }
        });

        attendanceDate = root.findViewById(R.id.attendanceDate);

        if (Date.date != null) {
            attendanceDate.setText("Class Attendance Date: "+restoreDate());
        }

        studentsAdapter = new StudentsAdapter(
                data,
                new StudentsAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(StudentsModel item) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("studentID", item.studentID);
                        WOWO.OWOWOWO = item.studentID;
                        Navigation.findNavController(root).navigate(R.id.navigation_edit_student, bundle);
                    }
                },
                new StudentsAdapter.OnPresentClickListener() {
                    @Override
                    public void onPresentClick(StudentsModel item, int position) {
                        if (Date.date != null){
                            if (Date.date.equals(dateMeow)){
                                if (item.studentStat != 2){
                                    String dateAttendance = StudentsFragment.Date.date + " " + "PRESENT";
                                    db.addAttendanceDate(dateAttendance, item.studentID);
                                    db.updateStudentStat(item.studentID, 2);
                                    data.set(position, new StudentsModel(item.studentID, item.studentAbsents, item.studentFirstName, item.studentLastName, item.studentDate, 2, item.attendanceID));
                                    studentsAdapter.notifyDataSetChanged();
                                    Snackbar.make(root, ""+item.studentFirstName+" "+item.studentLastName+" marked PRESENT", Snackbar.LENGTH_SHORT).show();
                                } else {
                                    Snackbar.make(root, ""+item.studentFirstName+" "+item.studentLastName+" is already marked PRESENT", Snackbar.LENGTH_SHORT).show();
                                }

                            } else {
                                Snackbar.make(root,"INVALID Date", Snackbar.LENGTH_SHORT).show();
                            }
                        } else {
                            Snackbar.make(root, "Please Select a Date", Snackbar.LENGTH_SHORT).show();
                        }
                    }
                },
                new StudentsAdapter.OnAbsentClickListener() {
                    @Override
                    public void onAbsentClick(StudentsModel item, int position) {
                        if (Date.date != null) {
                            if (Date.date.equals(dateMeow)) {
                                if (item.studentStat != 3){
                                    String dateAttendance = StudentsFragment.Date.date + " " + "ABSENT";
                                    db.addAttendanceDate(dateAttendance, item.studentID);
                                    db.updateStudentStat(item.studentID, 3);
                                    data.set(position, new StudentsModel(item.studentID, item.studentAbsents, item.studentFirstName, item.studentLastName, item.studentDate, 3, item.attendanceID));
                                    studentsAdapter.notifyDataSetChanged();
                                    Snackbar.make(root, "" + item.studentFirstName + " " + item.studentLastName + " marked ABSENT", Snackbar.LENGTH_SHORT).show();
                                } else {
                                    Snackbar.make(root, "" + item.studentFirstName + " " + item.studentLastName + " is already marked ABSENT", Snackbar.LENGTH_SHORT).show();
                                }
                            } else {
                                Snackbar.make(root, "INVALID Date", Snackbar.LENGTH_SHORT).show();
                            }
                        } else {
                            Snackbar.make(root, "Please Select a Date", Snackbar.LENGTH_SHORT).show();
                        }

                    }
                },
                root.getContext());

        DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                c.set(Calendar.YEAR, year);
                c.set(Calendar.MONTH, month);
                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                Date.date = sdf.format(c.getTime());
                awaw = "Class Attendance Date: "+ Date.date;
                attendanceDate.setText(awaw);
                saveDate(Date.date);
            }
        };

        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        recyclerView.setAdapter(studentsAdapter);

        FloatingActionButton fabAddStd = root.findViewById(R.id.fabAddStudents);
        fabAddStd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(root).navigate(R.id.navigation_add_student);
            }
        });

        FloatingActionButton fabDateSet = root.findViewById(R.id.fabSetDate);
        fabDateSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(root.getContext(), d, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        EditText searchBar = root.findViewById(R.id.searchbar);
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //WAG DITO
            }

            @Override
            public void afterTextChanged(Editable s) {
                //WAG RIN DTO
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() == 0){
                    data.clear();
                    data.addAll(db.getAllStudents(AttendanceFragment.WEWE.WEWEWE));
                    studentsAdapter.notifyDataSetChanged();
                }else{
                    String input = s.toString();

                    data.clear();
                    data.addAll(db.getStudentsWithMatches(input));
                    studentsAdapter.notifyDataSetChanged();

                }
            }


        });
        return root;


        // Assuming borrowR.borrowItemName is the item you want to select
//        for (int i = 0; i < data.size(); i++) {
//            if (data != null){
//                switch (db.getStudentStat(i)) {
//                    case 1:
//                        studentsAdapter.onCreateViewHolder(recyclerView, )
//                        viewHolder.getBtnAbsent().setBackgroundTintList(ColorStateList.valueOf(Color.GRAY));
//                        break;
//                    case 2:
//                        viewHolder.getBtnPresent().setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
//                        viewHolder.getBtnAbsent().setBackgroundTintList(ColorStateList.valueOf(Color.GRAY));
//                        break;
//                    case 3:
//                        viewHolder.getBtnAbsent().setBackgroundTintList(ColorStateList.valueOf(Color.RED));
//                        viewHolder.getBtnPresent().setBackgroundTintList(ColorStateList.valueOf(Color.GRAY));
//                        break;
//                }
//                }
//            }

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void saveDate(String dateSave){
        SharedPreferences preferences = requireActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("dateToday", dateSave);
        editor.apply();
    }
    private String restoreDate(){
        SharedPreferences preferences = requireActivity().getPreferences(Context.MODE_PRIVATE);
        return preferences.getString("dateToday", dateMeow);
    }
    private void saveStudentStatsToSharedPreferences(List<StudentsModel> students) {
        SharedPreferences preferences = requireActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        for (StudentsModel student : students) {
            editor.putInt("studentStat_" + student.studentID, student.studentStat);
        }

        editor.apply();
    }

    private void restoreStudentStatsFromSharedPreferences(List<StudentsModel> students) {
        SharedPreferences preferences = requireActivity().getPreferences(Context.MODE_PRIVATE);

        for (StudentsModel student : students) {
            int studentStat = preferences.getInt("studentStat_" + student.studentID, 1);
            student.studentStat = studentStat;
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}