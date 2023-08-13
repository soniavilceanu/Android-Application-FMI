package com.example.myapplicationfmi.beans;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "EvidentaNotificaris", primaryKeys = {"studentId", "notificationId"})
public class EvidentaNotificari {
    @NonNull
    public Long studentId; // Foreign key referencing Students table
    @NonNull
    public Long notificationId; // Foreign key referencing Notifications table

    @NonNull
    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(@NonNull Long studentId) {
        this.studentId = studentId;
    }

    @NonNull
    public Long getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(@NonNull Long notificationId) {
        this.notificationId = notificationId;
    }

    @Ignore
    public EvidentaNotificari(@NonNull Long studentId, @NonNull Long notificationId) {
        this.studentId = studentId;
        this.notificationId = notificationId;
    }

    public EvidentaNotificari(){

    }
}
