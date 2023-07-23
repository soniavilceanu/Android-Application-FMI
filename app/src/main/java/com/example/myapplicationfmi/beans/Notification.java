package com.example.myapplicationfmi.beans;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "Notifications")
public class Notification {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    public Long notificationId;

    public String text;
    public String time;

    @ColumnInfo(name = "student_id")
    public Long studentId; // Foreign key referencing Students table

    public Long getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Long notificationId) {
        this.notificationId = notificationId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    @Ignore
    public Notification(String text, String time, Long studentId) {
        this.text = text;
        this.time = time;
        this.studentId = studentId;
    }

    public Notification() {
    }
}
