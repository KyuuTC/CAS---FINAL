package com.jeffersondeguzman.classattendance.ui.attendance;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
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
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.material.snackbar.Snackbar;
import com.jeffersondeguzman.classattendance.R;
import com.jeffersondeguzman.classattendance.data.DataBaseHelper;
import com.jeffersondeguzman.classattendance.ui.classUI.AddClassFragment;
import com.jeffersondeguzman.classattendance.ui.classUI.ClassFragment;
import com.jeffersondeguzman.classattendance.ui.classUI.ClassModel;

public class AddAttendanceFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public AddAttendanceFragment() {
    }
    public static AddClassFragment newInstance(String param1, String param2) {
        AddClassFragment fragment = new AddClassFragment();
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
        View root = inflater.inflate(R.layout.fragment_add_attendance, container, false);

        EditText tvAttendance = root.findViewById(R.id.tvAttendance);
        Button btnSave = root.findViewById(R.id.btnSave);
        Button btnCancel = root.findViewById(R.id.btnCancel);

        DataBaseHelper db = new DataBaseHelper(root.getContext());

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tvAttendance.getText().toString().isEmpty()){
                    Snackbar.make(root, "Please enter class name and/or subject", Snackbar.LENGTH_SHORT).show();
                }else{
                    long result = db.addAttendance(tvAttendance.getText().toString(), ClassFragment.WAWA.AWAWAWA);
                    if(result < 0){
                        Snackbar.make(root, "Something went wrong.", Snackbar.LENGTH_SHORT).show();
                    }else{
                        Snackbar.make(root, "Your new class has been saved.", Snackbar.LENGTH_SHORT).show();
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
