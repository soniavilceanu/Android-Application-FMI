package com.example.myapplicationfmi.beans;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class SubjectWithCourses {
    @Embedded
    public Subject subject;

    @Relation(parentColumn = "subjectId", entityColumn = "subject_id")
    public List<Course> courses;
}
