package com.example.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MedicationAdapter.MedicationClickListener {

    private RecyclerView recyclerView;
    private MedicationAdapter adapter;
    private DatabaseHelper dbHelper;
    private FloatingActionButton fabAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        fabAdd = findViewById(R.id.fabAdd);

        dbHelper = new DatabaseHelper(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadMedications();

        fabAdd.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddEditMedicationActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadMedications(); // Atualiza a lista ao voltar
    }

    private void loadMedications() {
        List<Medication> meds = dbHelper.getAllMedications();
        adapter = new MedicationAdapter(meds, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onEditClicked(Medication med) {
        Intent intent = new Intent(MainActivity.this, AddEditMedicationActivity.class);
        intent.putExtra("med_id", med.getId());
        startActivity(intent);
    }

    @Override
    public void onDeleteClicked(Medication med) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.confirm_delete)
                .setMessage(getString(R.string.confirm_delete_msg, med.getName()))
                .setPositiveButton(R.string.yes, (dialog, which) -> {
                    dbHelper.deleteMedication(med.getId());
                    loadMedications();
                    Toast.makeText(this, R.string.deleted, Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton(R.string.no, null)
                .show();
    }

    @Override
    public void onTakenClicked(Medication med) {
        dbHelper.markAsTaken(med.getId(), !med.isTaken());
        loadMedications();
    }
}
