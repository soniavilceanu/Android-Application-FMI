package com.example.myapplicationfmi.DAO;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.myapplicationfmi.beans.Notification;
import com.example.myapplicationfmi.beans.Student;

import java.util.List;

@Dao
public interface NotificationDAO {
    @Insert
    long insertNotification(Notification notification);

    @Update
    void updateNotification(Notification notification);

    @Delete
    void deleteNotification(Notification notification);

    @Query("SELECT * FROM Notifications")
    LiveData<List<Notification>> getAllNotifications();

    @Query("DELETE FROM Notifications")
    void deleteAllNotifications();

    @Query("DELETE FROM Notifications WHERE type = :type")
    void deleteNotificationsWithType(String type);

    @Query("SELECT * FROM Notifications WHERE type = :type")
    LiveData<List<Notification>> getAllNotificationsByType(String type);

}
