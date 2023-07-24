package com.example.myapplicationfmi.beans;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "Courses", primaryKeys = {"groupId", "professorId"})
public class Course {
    @NonNull
    public Long groupId; // Foreign key referencing Groups table
    @NonNull
    public Long professorId; // Foreign key referencing Professors table
    public String zi;
    public Integer intervalOrar;
    public Integer frecventa; // 1 sau 2; 1 = o data pe sapt.; 2 = o data la 2 sapt
    public Integer semigrupa;

    @ColumnInfo(name = "subject_id")
    public Long subjectId; // Foreign key referencing Subjects table
    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getProfessorId() {
        return professorId;
    }

    public void setProfessorId(Long professorId) {
        this.professorId = professorId;
    }

    public String getZi() {
        return zi;
    }

    public void setZi(String zi) {
        this.zi = zi;
    }

    public Integer getIntervalOrar() {
        return intervalOrar;
    }

    public void setIntervalOrar(Integer intervalOrar) {
        this.intervalOrar = intervalOrar;
    }

    public Integer getFrecventa() {
        return frecventa;
    }

    public void setFrecventa(Integer frecventa) {
        this.frecventa = frecventa;
    }

    public Integer getSemigrupa() {
        return semigrupa;
    }

    public void setSemigrupa(Integer semigrupa) {
        this.semigrupa = semigrupa;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    @Ignore
    public Course(Long groupId, Long professorId) {
        this.groupId = groupId;
        this.professorId = professorId;
    }
    public Course(){

    }
}
