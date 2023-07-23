package com.example.myapplicationfmi.beans;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class SubjectWithProfessors {
    @Embedded
    public Subject subject;

    @Relation(
            parentColumn = "subjectId",
            entityColumn = "professorId",
            associateBy = @Junction(ProfessorSubject.class)
    )
    public List<Professor> professors;
}
