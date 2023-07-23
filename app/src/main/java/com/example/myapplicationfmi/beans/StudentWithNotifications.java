package com.example.myapplicationfmi.beans;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class StudentWithNotifications {
    @Embedded
    public Student student;

    @Relation(parentColumn = "studentId", entityColumn = "student_id")
    //@Relation(parentColumn = "studentId", entityColumn = "studId")
    public List<Notification> notifications;
}
