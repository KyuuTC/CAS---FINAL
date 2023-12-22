package com.jeffersondeguzman.classattendance.ui.attendance;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.jeffersondeguzman.classattendance.R;
import com.jeffersondeguzman.classattendance.data.DataBaseHelper;
import com.jeffersondeguzman.classattendance.databinding.FragmentAttendanceBinding;
import com.jeffersondeguzman.classattendance.ui.classUI.ClassAdapter;
import com.jeffersondeguzman.classattendance.ui.classUI.ClassFragment;
import com.jeffersondeguzman.classattendance.ui.classUI.ClassModel;
import com.jeffersondeguzman.classattendance.ui.students.StudentsModel;

import java.util.List;

public class AttendanceFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    AttendanceAdapter attendanceAdapter = null;
    public static class WEWE {
        public static int WEWEWE;
    }

    public AttendanceFragment() {

    }

    public static ClassFragment newInstance(String param1, String param2) {
        ClassFragment fragment = new ClassFragment();
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

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_attendance, container, false);
        final RecyclerView recyclerView = root.findViewById(R.id.rvAttendance);
        DataBaseHelper db = new DataBaseHelper(root.getContext());
        List<AttendanceModel> data = db.getAllAttendance(ClassFragment.WAWA.AWAWAWA);
        attendanceAdapter = new AttendanceAdapter(
                data,
                new AttendanceAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(AttendanceModel item) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("AttendanceID", item.attendanceID);
                        WEWE.WEWEWE = item.attendanceID;
                        Navigation.findNavController(root).navigate(R.id.navigation_students);
                    }
                },
                new AttendanceAdapter.OnDeleteClickListener() {
                    @Override
                    public void onItemClick(AttendanceModel item, int position) {
                        db.deleteAttendance(item.attendanceID);
                        data.remove(position);
                        attendanceAdapter.notifyDataSetChanged();
                        Snackbar.make(root, item.attendanceName + " has been deleted.", Snackbar.LENGTH_LONG).show();
                    }
                },
                root.getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        recyclerView.setAdapter(attendanceAdapter);

        FloatingActionButton fabAddAtt = root.findViewById(R.id.fabAddAttendance);
        fabAddAtt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(root).navigate(R.id.navigation_add_attendance);
            }
        });

        return root;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}