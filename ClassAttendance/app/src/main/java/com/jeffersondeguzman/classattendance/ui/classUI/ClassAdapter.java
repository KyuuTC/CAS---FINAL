package com.jeffersondeguzman.classattendance.ui.classUI;

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

public class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.ViewHolder> {
    private final List<ClassModel> localDataSet;
    private final OnItemClickListener localListener;
    private final OnDeleteClickListener localListenerDel;
    private Context context;

    public interface OnItemClickListener {
        void onItemClick(ClassModel item);
    }

    public interface OnDeleteClickListener {
        void onItemClick(ClassModel item, int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView className;
        private final TextView classSubject;
        private final CardView cardView;
        private final Button btnDelete;

        public ViewHolder(View view) {
            super(view);

            className = view.findViewById(R.id.className);
            classSubject = view.findViewById(R.id.classSubject);
            cardView = view.findViewById(R.id.classCardView);
            btnDelete = view.findViewById(R.id.btnDelete);
        }

        public TextView getClassName() {
            return className;
        }
        public TextView getClassSubject() {
            return classSubject;
        }
        public CardView getCardView() {
            return cardView;
        }
        public Button getBtnDelete() {
            return btnDelete;
        }
    }

    public ClassAdapter(List<ClassModel> dataSet, OnItemClickListener listener, OnDeleteClickListener listenerDel, Context context) {
        localDataSet = dataSet;
        localListener = listener;
        localListenerDel = listenerDel;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.class_row_item, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.getClassName().setText(localDataSet.get(position).className);
        viewHolder.getClassSubject().setText(localDataSet.get(position).classSubject);
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
