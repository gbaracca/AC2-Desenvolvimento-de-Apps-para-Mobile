package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Nome do banco de dados e versão
    private static final String DATABASE_NAME = "medications.db";
    private static final int DATABASE_VERSION = 1;

    // Nome da tabela e colunas
    public static final String TABLE_MEDICATIONS = "medications";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_TAKEN = "taken";

    // SQL para criar a tabela
    private static final String CREATE_TABLE_MEDICATIONS =
            "CREATE TABLE IF NOT EXISTS " + TABLE_MEDICATIONS + " ("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUMN_NAME + " TEXT NOT NULL, "
                    + COLUMN_DESCRIPTION + " TEXT, "
                    + COLUMN_TIME + " TEXT NOT NULL, "
                    + COLUMN_TAKEN + " INTEGER DEFAULT 0);";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Método que cria a tabela
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_MEDICATIONS);
    }

    // Método para atualizar o banco de dados
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEDICATIONS);
        onCreate(db);
    }

    // Método para adicionar um medicamento
    public long addMedication(Medication medication) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, medication.getName());
        values.put(COLUMN_DESCRIPTION, medication.getDescription());
        values.put(COLUMN_TIME, medication.getTime());
        values.put(COLUMN_TAKEN, medication.isTaken() ? 1 : 0);

        return db.insert(TABLE_MEDICATIONS, null, values);
    }

    // Método para obter todos os medicamentos
    public List<Medication> getAllMedications() {
        List<Medication> medicationList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_MEDICATIONS, null, null, null, null, null, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
                    String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
                    String description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION));
                    String time = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIME));
                    boolean taken = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_TAKEN)) == 1;

                    medicationList.add(new Medication(id, name, description, time, taken));
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return medicationList;
    }

    // Método para atualizar um medicamento
    public int updateMedication(Medication medication) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, medication.getName());
        values.put(COLUMN_DESCRIPTION, medication.getDescription());
        values.put(COLUMN_TIME, medication.getTime());
        values.put(COLUMN_TAKEN, medication.isTaken() ? 1 : 0);

        return db.update(TABLE_MEDICATIONS, values, COLUMN_ID + " = ?", new String[]{String.valueOf(medication.getId())});
    }

    // Método para excluir um medicamento
    public void deleteMedication(int medicationId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MEDICATIONS, COLUMN_ID + " = ?", new String[]{String.valueOf(medicationId)});
    }

    // Método para marcar o medicamento como tomado ou não
    public void markAsTaken(int medicationId, boolean taken) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_TAKEN, taken ? 1 : 0);

        db.update(TABLE_MEDICATIONS, values, COLUMN_ID + " = ?", new String[]{String.valueOf(medicationId)});
    }
}