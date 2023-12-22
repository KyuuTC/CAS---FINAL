package com.jeffersondeguzman.classattendance.ui.students;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.jeffersondeguzman.classattendance.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class StudentsAdapter extends RecyclerView.Adapter<StudentsAdapter.ViewHolder> {

    private List<StudentsModel> localDataSet;
    private final OnItemClickListener localListener;
    private final OnPresentClickListener localListenerPresent;
    private final OnAbsentClickListener localListenerAbsent;
    private Context context;
    public interface OnItemClickListener {
        void onItemClick(StudentsModel item);
    }
    public interface OnPresentClickListener {
        void onPresentClick(StudentsModel item, int position);
    }
    public interface OnAbsentClickListener {
        void onAbsentClick(StudentsModel item, int position);
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView stdName;
        private final CardView cardView;
        private final Button btnPresent;
        private final Button btnAbsent;
        private final TextView tvDateAttendance;

        public ViewHolder(View view) {
            super(view);

            stdName = view.findViewById(R.id.studName);
            cardView = view.findViewById(R.id.classCardView);
            btnPresent = view.findViewById(R.id.btnPresent);
            btnAbsent = view.findViewById(R.id.btnAbsent);
            tvDateAttendance = view.findViewById(R.id.tvDateAttendance);
        }

        public TextView getClassName() {
            return stdName;
        }

        public CardView getCardView() {
            return cardView;
        }

        public Button getBtnPresent() {
            return btnPresent;
        }

        public Button getBtnAbsent() {
            return btnAbsent;
        }

        public TextView getTvDateAttendance() {
            return tvDateAttendance;
        }
    }

    public StudentsAdapter(List<StudentsModel> dataSet, OnItemClickListener listener, OnPresentClickListener listenerPresent, OnAbsentClickListener listenerAbsent, Context context) {
        localDataSet = dataSet;
        localListener = listener;
        localListenerPresent = listenerPresent;
        localListenerAbsent = listenerAbsent;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.student_row_item, viewGroup, false);

        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        String fullName = localDataSet.get(position).studentFirstName + " " + localDataSet.get(position).studentLastName;
        viewHolder.getClassName().setText(fullName);

        // Set click listener for the entire item
        viewHolder.getCardView().setOnClickListener(v -> localListener.onItemClick(localDataSet.get(position)));
        viewHolder.getBtnPresent().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                localListenerPresent.onPresentClick(localDataSet.get(position), position);
            }
        });

        viewHolder.getBtnAbsent().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                localListenerAbsent.onAbsentClick(localDataSet.get(position), position);
            }
        });

        Log.i("WAAGa2", "onBindViewHolder called for position: " + position);
        Log.i("WAAGa3", "Favorite at position " + position + ": " + localDataSet.get(position).studentStat);
        Log.i("LOCALLISTENER ON PRESENT", ""+localDataSet.get(position));

        switch (localDataSet.get(position).studentStat){
            case 1:
                viewHolder.getBtnPresent().setBackgroundTintList(ColorStateList.valueOf(Color.GRAY));
                viewHolder.getBtnAbsent().setBackgroundTintList(ColorStateList.valueOf(Color.GRAY));
                break;
            case 2:
                viewHolder.getBtnPresent().setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                viewHolder.getBtnAbsent().setBackgroundTintList(ColorStateList.valueOf(Color.GRAY));
                break;
            case 3:
                viewHolder.getBtnAbsent().setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                viewHolder.getBtnPresent().setBackgroundTintList(ColorStateList.valueOf(Color.GRAY));
                break;
        }

    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}