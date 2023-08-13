package com.example.myapplicationfmi.beans;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;

@Entity(tableName = "EvidentaVoluntariats", primaryKeys = {"studentId", "voluntariatId"})
public class EvidentaVoluntariat {

    @NonNull
    public Long studentId; // Foreign key referencing Students table
    @NonNull
    public Long voluntariatId; // Foreign key referencing Voulentatiate table

    @NonNull
    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(@NonNull Long studentId) {
        this.studentId = studentId;
    }

    @NonNull
    public Long getVoluntariatId() {
        return voluntariatId;
    }

    public void setVoluntariatId(@NonNull Long voluntariatId) {
        this.voluntariatId = voluntariatId;
    }

    @Ignore
    public EvidentaVoluntariat(@NonNull Long studentId, @NonNull Long voluntariatId) {
        this.studentId = studentId;
        this.voluntariatId = voluntariatId;
    }
    public EvidentaVoluntariat(){

    }
}
