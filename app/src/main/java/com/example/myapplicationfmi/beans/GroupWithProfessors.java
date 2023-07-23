package com.example.myapplicationfmi.beans;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class GroupWithProfessors {
    @Embedded
    public Group group;

    @Relation(
            parentColumn = "groupId",
            entityColumn = "professorId",
            associateBy = @Junction(Course.class)
    )
    public List<Professor> professors;
}
