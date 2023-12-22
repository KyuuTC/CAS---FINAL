package com.jeffersondeguzman.classattendance.ui.attendance;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.jeffersondeguzman.classattendance.R;

import java.util.List;

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.ViewHolder> {
    private final List<AttendanceModel> localDataSet;
    private final AttendanceAdapter.OnItemClickListener localListener;
    private final AttendanceAdapter.OnDeleteClickListener localListenerDel;
    private Context context;

    public interface OnItemClickListener {
        void onItemClick(AttendanceModel item);
    }

    public interface OnDeleteClickListener {
        void onItemClick(AttendanceModel item, int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView attName;
        //private final TextView attSubject;
        private final CardView cardView;
        private final Button btnDelete;

        public ViewHolder(View view) {
            super(view);

            attName = view.findViewById(R.id.attName);
            //attSubject = view.findViewById(R.id.attSubject);
            cardView = view.findViewById(R.id.classCardView);
            btnDelete = view.findViewById(R.id.btnDelete);
        }

        public TextView getClassName() {
            return attName;
        }
//        public TextView getClassSubject() {
//            return attSubject;
//        }
        public CardView getCardView() {
            return cardView;
        }
        public Button getBtnDelete() {
            return btnDelete;
        }
    }

    public AttendanceAdapter(List<AttendanceModel> dataSet, OnItemClickListener listener, OnDeleteClickListener listenerdel, Context context) {
        localDataSet = dataSet;
        localListener = listener;
        localListenerDel = listenerdel;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.attendance_row_item, viewGroup, false);

        return new AttendanceAdapter.ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(AttendanceAdapter.ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.getClassName().setText(localDataSet.get(position).attendanceName);
        viewHolder.getCardView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                localListener.onItemClick(localDataSet.get(position));
            }
        });
        viewHolder.getBtnDelete().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                localListenerDel.onItemClick(localDataSet.get(position), position);
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}
