package com.example.myapplicationfmi.Repositories;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;

import com.example.myapplicationfmi.DAO.NotificationDAO;
import com.example.myapplicationfmi.MyRoomDatabase;
import com.example.myapplicationfmi.beans.Notification;

import java.util.List;

public class NotificationRepository {

    // below line is the create a variable
    // for dao and list for all notifications.
    private NotificationDAO dao;
    private LiveData<List<Notification>> allnotifications;

    // creating a constructor for our variables
    // and passing the variables to it.
    @RequiresApi(api = Build.VERSION_CODES.O)
    public NotificationRepository(Application application) {
        MyRoomDatabase database = MyRoomDatabase.getInstance(application);
        dao = database.notificationDao();
        allnotifications = dao.getAllNotifications();
    }

    // creating a method to insert the data to our database.
    public void insert(Notification model) {
        new InsertnotificationAsyncTask(dao).execute(model);
    }

    // creating a method to update data in database.
    public void update(Notification model) {
        new UpdatenotificationAsyncTask(dao).execute(model);
    }

    // creating a method to delete the data in our database.
    public void delete(Notification model) {
        new DeletenotificationAsyncTask(dao).execute(model);
    }

    // below is the method to delete all the notifications.
    public void deleteAllnotifications() {
        new DeleteAllnotificationsAsyncTask(dao).execute();
    }

    // below method is to read all the notifications.
    public LiveData<List<Notification>> getAllNotifications() {
        return allnotifications;
    }

    // we are creating a async task method to insert new notification.
    private static class InsertnotificationAsyncTask extends AsyncTask<Notification, Void, Void> {
        private NotificationDAO dao;

        private InsertnotificationAsyncTask(NotificationDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Notification... model) {
            // below line is use to insert our modal in dao.
            dao.insertNotification(model[0]);
            return null;
        }
    }

    // we are creating a async task method to update our notification.
    private static class UpdatenotificationAsyncTask extends AsyncTask<Notification, Void, Void> {
        private NotificationDAO dao;

        private UpdatenotificationAsyncTask(NotificationDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Notification... models) {
            // below line is use to update
            // our modal in dao.
            dao.updateNotification(models[0]);
            return null;
        }
    }

    // we are creating a async task method to delete notification.
    private static class DeletenotificationAsyncTask extends AsyncTask<Notification, Void, Void> {
        private NotificationDAO dao;

        private DeletenotificationAsyncTask(NotificationDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Notification... models) {
            // below line is use to delete
            // our notification modal in dao.
            dao.deleteNotification(models[0]);
            return null;
        }
    }

    // we are creating a async task method to delete all notifications.
    private static class DeleteAllnotificationsAsyncTask extends AsyncTask<Void, Void, Void> {
        private NotificationDAO dao;
        private DeleteAllnotificationsAsyncTask(NotificationDAO dao) {
            this.dao = dao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            // on below line calling method
            // to delete all notifications.
            dao.deleteAllNotifications();
            return null;
        }
    }
}
