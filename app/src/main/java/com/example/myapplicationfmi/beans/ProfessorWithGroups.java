package com.example.myapplicationfmi.beans;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class ProfessorWithGroups {
    @Embedded
    public Professor professor;

    @Relation(
            parentColumn = "professorId",
            entityColumn = "groupId",
            associateBy = @Junction(Course.class)
    )
    public List<Group> groups;
}
