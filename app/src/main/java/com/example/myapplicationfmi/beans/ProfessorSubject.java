package com.example.myapplicationfmi.beans;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "ProfessorSubjects", primaryKeys = {"professorId", "subjectId"})
public class ProfessorSubject {
    @NonNull
    public Long professorId;
    @NonNull
    public Long subjectId;

    public Long getProfessorId() {
        return professorId;
    }

    public void setProfessorId(Long professorId) {
        this.professorId = professorId;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    @Ignore
    public ProfessorSubject(Long professorId, Long subjectId) {
        this.professorId = professorId;
        this.subjectId = subjectId;
    }

    public ProfessorSubject() {
    }
}
