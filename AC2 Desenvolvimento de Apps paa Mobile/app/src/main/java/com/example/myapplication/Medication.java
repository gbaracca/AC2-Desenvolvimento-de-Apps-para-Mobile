package com.example.myapplication;

public class Medication {
    private int id;
    private String name;
    private String description;
    private String time;
    private boolean taken; // Atributo para verificar se o medicamento foi tomado

    // Construtor
    public Medication(String name, String description, String time) {
        this.name = name;
        this.description = description;
        this.time = time;
        this.taken = false; // Inicialmente, o medicamento não foi tomado
    }

    // Construtor para carregar dados do banco
    public Medication(int id, String name, String description, String time, boolean taken) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.time = time;
        this.taken = taken;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isTaken() {
        return taken; // Retorna se o medicamento foi tomado
    }

    public void setTaken(boolean taken) {
        this.taken = taken; // Define se o medicamento foi tomado
    }
}
