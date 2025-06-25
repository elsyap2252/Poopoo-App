package com.example.poopoo_app;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
        Button btnEdit, btnDelete;

        public ViewHolder(View view) {
            super(view);
            txtTime = view.findViewById(R.id.txtTime);
            txtShape = view.findViewById(R.id.txtShape);
            txtColor = view.findViewById(R.id.txtColor);
            txtSize = view.findViewById(R.id.txtSize);
            txtNotes = view.findViewById(R.id.txtNotes);
            btnEdit = view.findViewById(R.id.btnEdit);
            btnDelete = view.findViewById(R.id.btnDelete);
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
        Context context = holder.itemView.getContext();

        holder.txtTime.setText("Time: " + log.getTime());
        holder.txtShape.setText("Shape: " + log.getShape());
        holder.txtColor.setText("Color: " + log.getColor());
        holder.txtSize.setText("Size: " + log.getSize());
        holder.txtNotes.setText("Notes: " + log.getNotes());

        holder.btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(context, EditPoopActivity.class);
            intent.putExtra("id", log.getId());
            intent.putExtra("date", log.getDate());
            intent.putExtra("time", log.getTime());
            intent.putExtra("shape", log.getShape());
            intent.putExtra("color", log.getColor());
            intent.putExtra("size", log.getSize());
            intent.putExtra("notes", log.getNotes());
            context.startActivity(intent);
        });

        holder.btnDelete.setOnClickListener(v -> {
            PoopDatabaseHelper dbHelper = new PoopDatabaseHelper(context);
            dbHelper.deletePoopLog(log.getId());
            poopLogs.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, poopLogs.size());
            Toast.makeText(context, "Log deleted", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return poopLogs.size();
    }
}
