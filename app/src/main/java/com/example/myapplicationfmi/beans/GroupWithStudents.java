package com.example.myapplicationfmi.beans;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class GroupWithStudents {
    @Embedded
    public Group group;

    @Relation(parentColumn = "groupId", entityColumn = "grupa_id")
    //@Relation(parentColumn = "grupaId", entityColumn = "grp_id")
    public List<Student> students;
}
