package com.example.myapplicationfmi.Repositories;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;

import com.example.myapplicationfmi.DAO.CourseDAO;
import com.example.myapplicationfmi.DAO.NotificationDAO;
import com.example.myapplicationfmi.MyRoomDatabase;
import com.example.myapplicationfmi.beans.Course;
import com.example.myapplicationfmi.beans.Notification;

import java.util.List;
import java.util.concurrent.ExecutionException;

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
//    public void insert(Notification model) {
//        new InsertnotificationAsyncTask(dao).execute(model);
//    }
    public long insert(Notification model) {
        try {
            return new NotificationRepository.InsertnotificationAsyncTask(dao).execute(model).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return -1;
        }
    }
    // creating a method to update data in database.
    public void update(Notification model) {
        new UpdatenotificationAsyncTask(dao).execute(model);
    }

    // creating a method to delete the data in our database.
    public void delete(Notification model) {
        new DeletenotificationAsyncTask(dao).execute(model);
    }
    public void deleteNotificationsWithType(String type) {
        new NotificationRepository.DeleteNotificationsWithTypeAsyncTask(dao).execute(type);
    }

    // below is the method to delete all the notifications.
    public void deleteAllnotifications() {
        new DeleteAllnotificationsAsyncTask(dao).execute();
    }

    // below method is to read all the notifications.
    public LiveData<List<Notification>> getAllNotifications() {
        return allnotifications;
    }

    public LiveData<List<Notification>> getAllNotificationsByType(String type){
        return dao.getAllNotificationsByType(type);
    }

    // we are creating a async task method to insert new notification.
    private static class InsertnotificationAsyncTask extends AsyncTask<Notification, Void, Long> {
        private NotificationDAO dao;

        private InsertnotificationAsyncTask(NotificationDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Long doInBackground(Notification... model) {
            // below line is use to insert our modal in dao.
            return dao.insertNotification(model[0]);
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
    private static class DeleteNotificationsWithTypeAsyncTask extends AsyncTask<String, Void, Void> {
        private NotificationDAO dao;

        private DeleteNotificationsWithTypeAsyncTask(NotificationDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(String... types) {
            dao.deleteNotificationsWithType(types[0]);
            return null;
        }
    }
}
