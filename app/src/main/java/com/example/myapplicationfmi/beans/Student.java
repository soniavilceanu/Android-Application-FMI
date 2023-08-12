package com.example.myapplicationfmi.beans;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "Students")
public class Student implements Comparable<Student>{
    @PrimaryKey(autoGenerate = true)
    @NonNull
    public Long studentId;

    public String nume;
    public String prenume;
    public String email;
    public String parola;
    public int an;
    public boolean taxa;
    public boolean bursa;
    public int anInscriere;
    public String tipStudii;
    public boolean asmi;

    @ColumnInfo(name = "grupa_id")
    public Long grupaId; // Foreign key referencing Groups table

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
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

    public int getAn() {
        return an;
    }

    public void setAn(int an) {
        this.an = an;
    }

    public boolean getTaxa() {
        return taxa;
    }

    public void setTaxa(boolean taxa) {
        this.taxa = taxa;
    }

    public boolean getBursa() {
        return bursa;
    }

    public void setBursa(boolean bursa) {
        this.bursa = bursa;
    }

    public int getAnInscriere() {
        return anInscriere;
    }

    public void setAnInscriere(int anInscriere) {
        this.anInscriere = anInscriere;
    }

    public String getTipStudii() {
        return tipStudii;
    }

    public void setTipStudii(String tipStudii) {
        this.tipStudii = tipStudii;
    }

    public Long getGrupaId() {
        return grupaId;
    }

    public void setGrupaId(Long grupaId) {
        this.grupaId = grupaId;
    }

    public boolean isAsmi() {
        return asmi;
    }

    public void setAsmi(boolean asmi) {
        this.asmi = asmi;
    }

    @Ignore
    public Student(String nume, String prenume, String email, String parola, int an, boolean taxa, boolean bursa, int anInscriere, String tipStudii, Long grupaId, boolean asmi) {
        this.nume = nume;
        this.prenume = prenume;
        this.email = email;
        this.parola = parola;
        this.an = an;
        this.taxa = taxa;
        this.bursa = bursa;
        this.anInscriere = anInscriere;
        this.tipStudii = tipStudii;
        this.grupaId = grupaId;
        this.asmi = asmi;
    }

    public Student() {
    }
    @Override
    public int compareTo(Student otherStudent) {
        //pentru ordonare alfabetica
        return (this.nume + " " + this.prenume).compareTo(otherStudent.nume + " " + otherStudent.prenume);
    }
}