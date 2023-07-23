package com.example.myapplicationfmi.Modals;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.myapplicationfmi.Repositories.NotificationRepository;
import com.example.myapplicationfmi.beans.Notification;

import java.util.List;

public class NotificationModal extends AndroidViewModel {
    // creating a new variable for notification repository.
    private NotificationRepository repository;

    // below line is to create a variable for live
    // data where all the notifications are present.
    private LiveData<List<Notification>> allnotifications;

    // constructor for our view modal.
    public NotificationModal(@NonNull Application application) {
        super(application);
        repository = new NotificationRepository(application);
        allnotifications = repository.getAllNotifications();
    }

    // below method is use to insert the data to our repository.
    public void insert(Notification model) {
        repository.insert(model);
    }

    // below line is to update data in our repository.
    public void update(Notification model) {
        repository.update(model);
    }

    // below line is to delete the data in our repository.
    public void delete(Notification model) {
        repository.delete(model);
    }

    // below method is to delete all the notifications in our list.
    public void deleteAllnotifications() {
        repository.deleteAllnotifications();
    }

    // below method is to get all the notifications in our list.
    public LiveData<List<Notification>> getAllNotifications() {
        return allnotifications;
    }
}
