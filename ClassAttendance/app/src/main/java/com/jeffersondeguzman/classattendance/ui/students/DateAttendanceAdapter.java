package com.jeffersondeguzman.classattendance.ui.students;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.jeffersondeguzman.classattendance.R;

import java.util.List;

public class DateAttendanceAdapter extends RecyclerView.Adapter<DateAttendanceAdapter.ViewHolder> {
    private final List<AttendanceDateModel> localDataSet;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvDateAttendance;

        public ViewHolder(View view) {
            super(view);

            tvDateAttendance = view.findViewById(R.id.tvDateAttendance);

        }

        public TextView getDateAttendance() {
            return tvDateAttendance;
        }
    }

    public DateAttendanceAdapter(List<AttendanceDateModel> dataSet, Context context) {
        localDataSet = dataSet;
        this.context = context;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.date_attendance_row_item, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.getDateAttendance().setText(localDataSet.get(position).attendanceDate);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}
