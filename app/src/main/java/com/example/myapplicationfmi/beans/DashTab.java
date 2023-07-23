package com.example.myapplicationfmi.beans;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "DashTabs")
public class DashTab {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    public Long dashTabId;

    public String tip;
    public Long dashboardId;

    @ColumnInfo(name = "notif_id")
    public Long notifId; // Foreign key referencing Notification table

    public Long getDashTabId() {
        return dashTabId;
    }

    public void setDashTabId(Long dashTabId) {
        this.dashTabId = dashTabId;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public Long getDashboardId() {
        return dashboardId;
    }

    public void setDashboardId(Long dashboardId) {
        this.dashboardId = dashboardId;
    }

    public Long getNotificationId() {
        return notifId;
    }

    public void setNotificationId(Long notificationId) {
        this.notifId = notificationId;
    }

    @Ignore
    public DashTab(String tip, Long dashboardId, Long notificationId) {
        this.tip = tip;
        this.dashboardId = dashboardId;
        this.notifId = notificationId;
    }
    public DashTab(){}
}

