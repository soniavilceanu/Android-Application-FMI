package com.example.myapplicationfmi.Repositories;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;

import com.example.myapplicationfmi.DAO.EvidentaNotificariDAO;
import com.example.myapplicationfmi.MyRoomDatabase;
import com.example.myapplicationfmi.beans.Calendar;
import com.example.myapplicationfmi.beans.EvidentaNotificari;
import com.example.myapplicationfmi.beans.Group;
import com.example.myapplicationfmi.beans.Subject;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class EvidentaNotificariRepository {

    private EvidentaNotificariDAO dao;
    private LiveData<List<EvidentaNotificari>> allevidentaNotificaris;
    @RequiresApi(api = Build.VERSION_CODES.O)
    public EvidentaNotificariRepository(Application application) {
        MyRoomDatabase database = MyRoomDatabase.getInstance(application);
        dao = database.evidentaNotificariDao();
        allevidentaNotificaris = dao.getAllEvidentaNotificaris();
    }

    //    public void insert(EvidentaNotificari model) {
//        new InsertevidentaNotificariAsyncTask(dao).execute(model);
//    }
    public long insert(EvidentaNotificari model) {
        try {
            return new EvidentaNotificariRepository.InsertevidentaNotificariAsyncTask(dao).execute(model).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return -1;
        }
    }
    public void update(EvidentaNotificari model) {
        new UpdateevidentaNotificariAsyncTask(dao).execute(model);
    }
    public void delete(EvidentaNotificari model) {
        new DeleteevidentaNotificariAsyncTask(dao).execute(model);
    }
    public void deleteAllevidentaNotificaris() {
        new DeleteAllevidentaNotificarisAsyncTask(dao).execute();
    }
    public LiveData<List<EvidentaNotificari>> getAllEvidentaNotificaris() {
        return allevidentaNotificaris;
    }
    public  LiveData<List<EvidentaNotificari>> getAllEvidentaNotificariByStudentId(Long studentId){
        return dao.getAllEvidentaNotificariByStudentId(studentId);
    }

    private static class InsertevidentaNotificariAsyncTask extends AsyncTask<EvidentaNotificari, Void, Long> {
        private EvidentaNotificariDAO dao;

        private InsertevidentaNotificariAsyncTask(EvidentaNotificariDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Long doInBackground(EvidentaNotificari... model) {
            return dao.insertEvidentaNotificari(model[0]);
        }
    }
    private static class UpdateevidentaNotificariAsyncTask extends AsyncTask<EvidentaNotificari, Void, Void> {
        private EvidentaNotificariDAO dao;

        private UpdateevidentaNotificariAsyncTask(EvidentaNotificariDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(EvidentaNotificari... models) {
            dao.updateEvidentaNotificari(models[0]);
            return null;
        }
    }
    private static class DeleteevidentaNotificariAsyncTask extends AsyncTask<EvidentaNotificari, Void, Void> {
        private EvidentaNotificariDAO dao;

        private DeleteevidentaNotificariAsyncTask(EvidentaNotificariDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(EvidentaNotificari... models) {
            dao.deleteEvidentaNotificari(models[0]);
            return null;
        }
    }
    private static class DeleteAllevidentaNotificarisAsyncTask extends AsyncTask<Void, Void, Void> {
        private EvidentaNotificariDAO dao;
        private DeleteAllevidentaNotificarisAsyncTask(EvidentaNotificariDAO dao) {
            this.dao = dao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            dao.deleteAllEvidentaNotificaris();
            return null;
        }
    }
}
