package com.example.myapplicationfmi.Modals;

import android.app.Application;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.myapplicationfmi.Repositories.NotificationRepository;
import com.example.myapplicationfmi.beans.Calendar;
import com.example.myapplicationfmi.beans.Notification;

import java.util.List;

public class NotificationModal extends AndroidViewModel {
    private NotificationRepository repository;

    private LiveData<List<Notification>> allnotifications;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public NotificationModal(@NonNull Application application) {
        super(application);
        repository = new NotificationRepository(application);
        allnotifications = repository.getAllNotifications();
    }

//    public void insert(Notification model) {
//        repository.insert(model);
//    }
    public long insert(Notification model) {
        return repository.insert(model);
    }
    public void update(Notification model) {
        repository.update(model);
    }

    public void delete(Notification model) {
        repository.delete(model);
    }

    public void deleteAllnotifications() {
        repository.deleteAllnotifications();
    }

    public LiveData<List<Notification>> getAllNotifications() {
        return allnotifications;
    }

    public void deleteNotificationsWithType(String type) {
        repository.deleteNotificationsWithType(type);
    }

    public LiveData<List<Notification>> getAllNotificationsByType(String type){
        return repository.getAllNotificationsByType(type);
    }
}
