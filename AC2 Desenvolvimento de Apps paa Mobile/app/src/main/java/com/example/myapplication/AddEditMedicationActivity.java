package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddEditMedicationActivity extends AppCompatActivity {

    private EditText edtName, edtDescription, edtTime;
    private Button btnSave;
    private DatabaseHelper dbHelper;
    private Medication medication;
    private boolean isEditMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

        edtName = findViewById(R.id.edtName);
        edtDescription = findViewById(R.id.edtDescription);
        edtTime = findViewById(R.id.edtTime);
        btnSave = findViewById(R.id.btnSave);
        dbHelper = new DatabaseHelper(this);

        // Verifica se está editando ou criando um novo medicamento
        Intent intent = getIntent();
        if (intent.hasExtra("medication")) {
            medication = (Medication) intent.getSerializableExtra("medication");
            isEditMode = true;
            populateFields();
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveMedication();
            }
        });
    }

    private void populateFields() {
        if (medication != null) {
            edtName.setText(medication.getName());
            edtDescription.setText(medication.getDescription());
            edtTime.setText(medication.getTime());
        }
    }

    private void saveMedication() {
        String name = edtName.getText().toString().trim();
        String description = edtDescription.getText().toString().trim();
        String time = edtTime.getText().toString().trim();

        // Verificar se os campos estão preenchidos
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(description) || TextUtils.isEmpty(time)) {
            Toast.makeText(this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        Medication newMedication = new Medication(name, description, time);

        if (isEditMode) {
            // Atualizar medicamento
            newMedication.setId(medication.getId());
            long result = dbHelper.updateMedication(newMedication);
            if (result > 0) {
                Toast.makeText(this, "Medicamento atualizado com sucesso!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Erro ao atualizar medicamento.", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Inserir novo medicamento
            long result = dbHelper.addMedication(newMedication);
            if (result > 0) {
                Toast.makeText(this, "Medicamento adicionado com sucesso!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Erro ao adicionar medicamento.", Toast.LENGTH_SHORT).show();
            }
        }

        // Voltar para a MainActivity
        finish();
    }
}