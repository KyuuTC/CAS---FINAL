package com.jeffersondeguzman.classattendance.ui.students;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.jeffersondeguzman.classattendance.R;
import com.jeffersondeguzman.classattendance.data.DataBaseHelper;
import com.jeffersondeguzman.classattendance.ui.attendance.AttendanceFragment;

import java.util.List;

public class EditStudentsFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    DateAttendanceAdapter dateAttendanceAdapter = null;

    public EditStudentsFragment() {
        // Required empty public constructor
    }

    public static EditStudentsFragment newInstance(String param1, String param2) {
        EditStudentsFragment fragment = new EditStudentsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_edit_student, container, false);
        final RecyclerView recyclerView = root.findViewById(R.id.rvAttendance);
        DataBaseHelper db = new DataBaseHelper(root.getContext());
        List<AttendanceDateModel> data = db.getAllAttendanceDate(StudentsFragment.WOWO.OWOWOWO);
        if (data.isEmpty()) {
            // Handle the case where the data is empty, e.g., show a message or take appropriate action
            Log.i("DATATATA", "Data is empty");
            // You might want to return or display an empty state here
        } else {
            // Data is not empty, proceed to set up the adapter
            Log.i("DATATATA", "Data is not empty. Size: " + data.size());
            dateAttendanceAdapter = new DateAttendanceAdapter(data, root.getContext());
            recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
            recyclerView.setAdapter(dateAttendanceAdapter);
        }

        EditText tvFirst = root.findViewById(R.id.tvFirstName);
        EditText tvLast = root.findViewById(R.id.tvLastName);
        Button btnUpdate = root.findViewById(R.id.btnUpdate);
        Button btnCancel = root.findViewById(R.id.btnCancel);

        StudentsModel std = db.getStudent(getArguments().getInt("studentID"));

        tvFirst.setText(std.studentFirstName);
        tvLast.setText(std.studentLastName);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tvFirst.getText().toString().isEmpty() || tvLast.getText().toString().isEmpty()){
                    Snackbar.make(root, "Please enter note title and/or description", Snackbar.LENGTH_SHORT).show();
                }else{
                    long result = db.updateStudent(getArguments().getInt("studentID"), tvFirst.getText().toString(), tvLast.getText().toString());
                    if(result < 0){
                        Snackbar.make(root, "Something went wrong.", Snackbar.LENGTH_SHORT).show();
                    }else{
                        Snackbar.make(root, "Your note has been updated.", Snackbar.LENGTH_SHORT).show();
                        Navigation.findNavController(root).navigateUp();
                    }

                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(root).navigateUp();
            }
        });

        return root;
    }
}
