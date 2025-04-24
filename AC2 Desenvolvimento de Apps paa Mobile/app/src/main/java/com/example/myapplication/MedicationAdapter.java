package com.example.myapplication;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MedicationAdapter extends RecyclerView.Adapter<MedicationAdapter.MedicationViewHolder> {

    public interface MedicationClickListener {
        void onEditClicked(Medication med);
        void onDeleteClicked(Medication med);
        void onTakenClicked(Medication med);
    }

    private List<Medication> medications;
    private MedicationClickListener listener;

    public MedicationAdapter(List<Medication> medications, MedicationClickListener listener) {
        this.medications = medications;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MedicationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_medication, parent, false);
        return new MedicationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicationViewHolder holder, int position) {
        Medication med = medications.get(position);
        holder.bind(med);
    }

    @Override
    public int getItemCount() {
        return medications.size();
    }

    class MedicationViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtTime, txtDescription;
        ImageButton btnEdit, btnDelete, btnTaken;

        public MedicationViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtTime = itemView.findViewById(R.id.txtTime);
            txtDescription = itemView.findViewById(R.id.txtDescription);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            btnTaken = itemView.findViewById(R.id.btnTaken);
        }

        void bind(Medication med) {
            txtName.setText(med.getName());
            txtTime.setText(med.getTime());
            txtDescription.setText(med.getDescription());

            // Cor de fundo ou ícone se tomado
            if (med.isTaken()) {
                itemView.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.purple_100));
                btnTaken.setImageResource(R.drawable.ic_check_circle);
            } else {
                itemView.setBackgroundColor(Color.TRANSPARENT);
                btnTaken.setImageResource(R.drawable.ic_radio_button_unchecked);
            }

            btnEdit.setOnClickListener(v -> listener.onEditClicked(med));
            btnDelete.setOnClickListener(v -> listener.onDeleteClicked(med));
            btnTaken.setOnClickListener(v -> listener.onTakenClicked(med));
        }
    }
}
