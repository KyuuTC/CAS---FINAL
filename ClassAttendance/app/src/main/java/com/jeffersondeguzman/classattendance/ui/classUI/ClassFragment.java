package com.jeffersondeguzman.classattendance.ui.classUI;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.jeffersondeguzman.classattendance.R;
import com.jeffersondeguzman.classattendance.data.DataBaseHelper;

import java.util.List;

public class ClassFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    ClassAdapter classAdapter = null;
    public static class WAWA {
        public static int AWAWAWA;
    }

    public ClassFragment() {

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_class, container, false);
        final RecyclerView recyclerView = root.findViewById(R.id.rvClass);
        DataBaseHelper db = new DataBaseHelper(root.getContext());
        List<ClassModel> data = db.getAllClass();
        classAdapter = new ClassAdapter(
                data,
                new ClassAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(ClassModel item) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("classID", item.classID);
                        WAWA.AWAWAWA = item.classID;
                        Navigation.findNavController(root).navigate(R.id.navigation_attendance, bundle);
                    }
                },
                new ClassAdapter.OnDeleteClickListener() {
                    @Override
                    public void onItemClick(ClassModel item, int position) {
                        db.deleteClass(item.classID);
                        data.remove(position);
                        classAdapter.notifyDataSetChanged();
                        Snackbar.make(root, item.className + " has been deleted.", Snackbar.LENGTH_LONG).show();
                    }
                },
                root.getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        recyclerView.setAdapter(classAdapter);

        FloatingActionButton fabAddClass = root.findViewById(R.id.fabAddClass);
        fabAddClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(root).navigate(R.id.navigation_add_class);
            }
        });
        SharedPreferences prefs = root.getContext().getSharedPreferences("com.jeffersondeguzman.classattendance", Context.MODE_PRIVATE);
        boolean isOn = prefs.getBoolean("enableDarkMode", false);
        Switch enableDarkMode = root.findViewById(R.id.enableDarkMode);
        enableDarkMode.setChecked(isOn);
        enableDarkMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //Ito specifically
                if(isChecked){
                    //Ito ung nag-eenable ng night mode
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    //Sympre, i-save natin sa SharedPrefs ung bagong value ng switch natin
                    prefs.edit().putBoolean("enableDarkMode", isChecked).commit();
                }else{
                    //Ito ung nag-didisable ng night mode
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    //Sympre, i-save natin sa SharedPrefs ung bagong value ng switch natin
                    prefs.edit().putBoolean("enableDarkMode", isChecked).commit();
                }
            }
        });

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}