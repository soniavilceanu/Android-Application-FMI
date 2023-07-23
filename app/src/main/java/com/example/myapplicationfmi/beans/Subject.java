package com.example.myapplicationfmi.beans;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "Subjects")
public class Subject {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    public Long subjectId;

    public String denumire;

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    @Ignore
    public Subject(String denumire) {
        this.denumire = denumire;
    }

    public Subject() {
    }
}
