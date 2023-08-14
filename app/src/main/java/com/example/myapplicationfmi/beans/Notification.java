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

    public String time;
    public String type; // activitate, anunt, internship, voluntariat, nota, calendar, examen, orar, voluntariatInscris
    public Integer causeId; //dashboardTabId, noteId, calendarId, grupaId, studentId

    public Long getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Long notificationId) {
        this.notificationId = notificationId;
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getCauseId() {
        return causeId;
    }

    public void setCauseId(Integer causeId) {
        this.causeId = causeId;
    }

    @Ignore
    public Notification(@NonNull Long notificationId, String time, String type, Integer causeId) {
        this.notificationId = notificationId;
        this.time = time;
        this.type = type;
        this.causeId = causeId;
    }

    public Notification() {
    }
}
