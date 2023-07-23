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

    @Ignore
    public Course(Long groupId, Long professorId) {
        this.groupId = groupId;
        this.professorId = professorId;
    }
    public Course(){

    }
}
