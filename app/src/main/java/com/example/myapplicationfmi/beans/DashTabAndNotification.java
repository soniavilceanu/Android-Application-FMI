package com.example.myapplicationfmi.beans;

import androidx.room.Embedded;
import androidx.room.Relation;

public class DashTabAndNotification {
    @Embedded
    public Notification notification;

    //@Relation(parentColumn = "dashTabId", entityColumn = "dashTabId")
    @Relation(parentColumn = "notificationId", entityColumn = "notif_id")
    public DashTab dashTab;
}