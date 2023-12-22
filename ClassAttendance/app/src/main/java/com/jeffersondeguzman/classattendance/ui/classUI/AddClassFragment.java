package com.jeffersondeguzman.classattendance.ui.classUI;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.material.snackbar.Snackbar;
import com.jeffersondeguzman.classattendance.R;
import com.jeffersondeguzman.classattendance.data.DataBaseHelper;

public class AddClassFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public AddClassFragment() {
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
        View root = inflater.inflate(R.layout.fragment_add_class, container, false);

        EditText tvClassName = root.findViewById(R.id.tvClassName);
        EditText tvClassSubject = root.findViewById(R.id.tvClassSubject);
        Button btnSave = root.findViewById(R.id.btnSave);
        Button btnCancel = root.findViewById(R.id.btnCancel);

        DataBaseHelper db = new DataBaseHelper(root.getContext());

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tvClassName.getText().toString().isEmpty() || tvClassSubject.getText().toString().isEmpty()){
                    Snackbar.make(root, "Please enter class name and/or subject", Snackbar.LENGTH_SHORT).show();
                }else{
                    long result = db.addClass(tvClassName.getText().toString(), tvClassSubject.getText().toString());
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
