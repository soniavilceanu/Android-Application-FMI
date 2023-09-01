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

    private NotificationDAO dao;
    private LiveData<List<Notification>> allnotifications;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public NotificationRepository(Application application) {
        MyRoomDatabase database = MyRoomDatabase.getInstance(application);
        dao = database.notificationDao();
        allnotifications = dao.getAllNotifications();
    }

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
    public void update(Notification model) {
        new UpdatenotificationAsyncTask(dao).execute(model);
    }

    public void delete(Notification model) {
        new DeletenotificationAsyncTask(dao).execute(model);
    }
    public void deleteNotificationsWithType(String type) {
        new NotificationRepository.DeleteNotificationsWithTypeAsyncTask(dao).execute(type);
    }

    public void deleteAllnotifications() {
        new DeleteAllnotificationsAsyncTask(dao).execute();
    }

    public LiveData<List<Notification>> getAllNotifications() {
        return allnotifications;
    }

    public LiveData<List<Notification>> getAllNotificationsByType(String type){
        return dao.getAllNotificationsByType(type);
    }

    private static class InsertnotificationAsyncTask extends AsyncTask<Notification, Void, Long> {
        private NotificationDAO dao;

        private InsertnotificationAsyncTask(NotificationDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Long doInBackground(Notification... model) {
            return dao.insertNotification(model[0]);
        }
    }

    private static class UpdatenotificationAsyncTask extends AsyncTask<Notification, Void, Void> {
        private NotificationDAO dao;

        private UpdatenotificationAsyncTask(NotificationDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Notification... models) {
            dao.updateNotification(models[0]);
            return null;
        }
    }

    private static class DeletenotificationAsyncTask extends AsyncTask<Notification, Void, Void> {
        private NotificationDAO dao;

        private DeletenotificationAsyncTask(NotificationDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Notification... models) {
            dao.deleteNotification(models[0]);
            return null;
        }
    }

    private static class DeleteAllnotificationsAsyncTask extends AsyncTask<Void, Void, Void> {
        private NotificationDAO dao;
        private DeleteAllnotificationsAsyncTask(NotificationDAO dao) {
            this.dao = dao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
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
