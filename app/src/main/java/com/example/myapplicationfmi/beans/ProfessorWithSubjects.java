package com.example.myapplicationfmi.beans;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class ProfessorWithSubjects {
    @Embedded
    public Professor professor;

    @Relation(
            parentColumn = "professorId",
            entityColumn = "subjectId",
            associateBy = @Junction(ProfessorSubject.class)
    )
    public List<Subject> subjects;
}
