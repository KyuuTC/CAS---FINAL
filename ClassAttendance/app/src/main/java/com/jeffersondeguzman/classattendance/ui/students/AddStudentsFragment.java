package com.jeffersondeguzman.classattendance.ui.students;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.navigation.Navigation;

import com.google.android.material.snackbar.Snackbar;
import com.jeffersondeguzman.classattendance.R;
import com.jeffersondeguzman.classattendance.data.DataBaseHelper;
import com.jeffersondeguzman.classattendance.ui.attendance.AttendanceFragment;
import com.jeffersondeguzman.classattendance.ui.classUI.ClassFragment;

public class AddStudentsFragment extends AppCompatDialogFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_student, null);
        DataBaseHelper db = new DataBaseHelper(view.getContext());

        EditText tvFirstName = view.findViewById(R.id.tvFirstName);
        EditText tvLastName = view.findViewById(R.id.tvLastName);
        Button btnSave = view.findViewById(R.id.btnSave);
        Button btnCancel = view.findViewById(R.id.btnCancel);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if(tvFirstName.getText().toString().isEmpty() || tvLastName.getText().toString().isEmpty()){
                        Snackbar.make(view, "Please enter Student name", Snackbar.LENGTH_SHORT).show();
                    }else{
                        Log.i("StudentAttendanceIDChecker", ""+ AttendanceFragment.WEWE.WEWEWE);
                        long result = db.addStudent(tvFirstName.getText().toString(), tvLastName.getText().toString(), AttendanceFragment.WEWE.WEWEWE);
                        if(result < 0){
                            Snackbar.make(view, "Something went wrong.", Snackbar.LENGTH_SHORT).show();
                        }else{
                            Snackbar.make(view, "Your new Attendance has been saved.", Snackbar.LENGTH_SHORT).show();
                            Navigation.findNavController(view).navigateUp();
                        }

                    }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigateUp();
            }
        });

        return view;
    }
}
