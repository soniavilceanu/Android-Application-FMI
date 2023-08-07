package com.example.myapplicationfmi.beans;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class StudentWithNotes {

    @Embedded
    public Student student;

    @Relation(parentColumn = "studentId", entityColumn = "student_id")
    public List<Note> notes;
}
