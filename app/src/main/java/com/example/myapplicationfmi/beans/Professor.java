package com.example.myapplicationfmi.beans;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "Professors")
public class Professor {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    public Long professorId;

    public String nume;
    public String prenume;
    public String email;
    public String parola;
    public String pozitie;

    public Long getProfessorId() {
        return professorId;
    }

    public void setProfessorId(Long professorId) {
        this.professorId = professorId;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }

    public String getPozitie() {
        return pozitie;
    }

    public void setPozitie(String pozitie) {
        this.pozitie = pozitie;
    }

    @Ignore
    public Professor(String nume, String prenume, String email, String parola, String pozitie) {
        this.nume = nume;
        this.prenume = prenume;
        this.email = email;
        this.parola = parola;
        this.pozitie = pozitie;
    }

    public Professor() {
    }
}