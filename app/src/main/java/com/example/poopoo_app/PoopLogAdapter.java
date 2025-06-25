package com.example.poopoo_app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PoopLogAdapter extends RecyclerView.Adapter<PoopLogAdapter.ViewHolder> {

    private List<PoopLog> poopLogs;

    public PoopLogAdapter(List<PoopLog> poopLogs) {
        this.poopLogs = poopLogs;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTime, txtShape, txtColor, txtSize, txtNotes;

        public ViewHolder(View view) {
            super(view);
            txtTime = view.findViewById(R.id.txtTime);
            txtShape = view.findViewById(R.id.txtShape);
            txtColor = view.findViewById(R.id.txtColor);
            txtSize = view.findViewById(R.id.txtSize);
            txtNotes = view.findViewById(R.id.txtNotes);
        }
    }

    @NonNull
    @Override
    public PoopLogAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_poop_log, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PoopLog log = poopLogs.get(position);

        holder.txtTime.setText("Time: " + log.getTime());
        holder.txtShape.setText("Shape: " + log.getShape());
        holder.txtColor.setText("Color: " + log.getColor());
        holder.txtSize.setText("Size: " + log.getSize());
        holder.txtNotes.setText("Notes: " + log.getNotes());
    }

    @Override
    public int getItemCount() {
        return poopLogs.size();
    }
}