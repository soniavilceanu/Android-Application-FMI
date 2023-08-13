package com.example.myapplicationfmi.Repositories;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;

import com.example.myapplicationfmi.DAO.EvidentaVoluntariatDAO;
import com.example.myapplicationfmi.MyRoomDatabase;
import com.example.myapplicationfmi.beans.Calendar;
import com.example.myapplicationfmi.beans.EvidentaVoluntariat;
import com.example.myapplicationfmi.beans.Group;
import com.example.myapplicationfmi.beans.Subject;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class EvidentaVoluntariatRepository {

    private EvidentaVoluntariatDAO dao;
    private LiveData<List<EvidentaVoluntariat>> allevidentaVoluntariats;
    @RequiresApi(api = Build.VERSION_CODES.O)
    public EvidentaVoluntariatRepository(Application application) {
        MyRoomDatabase database = MyRoomDatabase.getInstance(application);
        dao = database.evidentaVoluntariatDao();
        allevidentaVoluntariats = dao.getAllEvidentaVoluntariats();
    }

    //    public void insert(EvidentaVoluntariat model) {
//        new InsertevidentaVoluntariatAsyncTask(dao).execute(model);
//    }
    public long insert(EvidentaVoluntariat model) {
        try {
            return new EvidentaVoluntariatRepository.InsertevidentaVoluntariatAsyncTask(dao).execute(model).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return -1;
        }
    }
    public void update(EvidentaVoluntariat model) {
        new UpdateevidentaVoluntariatAsyncTask(dao).execute(model);
    }
    public void delete(EvidentaVoluntariat model) {
        new DeleteevidentaVoluntariatAsyncTask(dao).execute(model);
    }
    public void deleteAllevidentaVoluntariats() {
        new DeleteAllevidentaVoluntariatsAsyncTask(dao).execute();
    }
    public LiveData<List<EvidentaVoluntariat>> getAllEvidentaVoluntariats() {
        return allevidentaVoluntariats;
    }
    public  LiveData<List<EvidentaVoluntariat>> getAllEvidentaVoluntariatByStudentId(Long studentId){
        return dao.getAllEvidentaVoluntariatByStudentId(studentId);
    }

    public LiveData<EvidentaVoluntariat> getAllEvidentaVoluntariatByStudentIdAndVoluntariatId(Long studentId, Long voluntariatId){
        return dao.getAllEvidentaVoluntariatByStudentIdAndVoluntariatId(studentId, voluntariatId);
    }
    public LiveData<List<EvidentaVoluntariat>> getAllEvidentaVoluntariatByVoluntariatId(Long voluntariatId){
        return dao.getAllEvidentaVoluntariatByVoluntariatId(voluntariatId);
    }

    private static class InsertevidentaVoluntariatAsyncTask extends AsyncTask<EvidentaVoluntariat, Void, Long> {
        private EvidentaVoluntariatDAO dao;

        private InsertevidentaVoluntariatAsyncTask(EvidentaVoluntariatDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Long doInBackground(EvidentaVoluntariat... model) {
            // below line is use to insert our modal in dao.
            return dao.insertEvidentaVoluntariat(model[0]);
        }
    }
    private static class UpdateevidentaVoluntariatAsyncTask extends AsyncTask<EvidentaVoluntariat, Void, Void> {
        private EvidentaVoluntariatDAO dao;

        private UpdateevidentaVoluntariatAsyncTask(EvidentaVoluntariatDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(EvidentaVoluntariat... models) {
            dao.updateEvidentaVoluntariat(models[0]);
            return null;
        }
    }
    private static class DeleteevidentaVoluntariatAsyncTask extends AsyncTask<EvidentaVoluntariat, Void, Void> {
        private EvidentaVoluntariatDAO dao;

        private DeleteevidentaVoluntariatAsyncTask(EvidentaVoluntariatDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(EvidentaVoluntariat... models) {
            dao.deleteEvidentaVoluntariat(models[0]);
            return null;
        }
    }
    private static class DeleteAllevidentaVoluntariatsAsyncTask extends AsyncTask<Void, Void, Void> {
        private EvidentaVoluntariatDAO dao;
        private DeleteAllevidentaVoluntariatsAsyncTask(EvidentaVoluntariatDAO dao) {
            this.dao = dao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            dao.deleteAllEvidentaVoluntariats();
            return null;
        }
    }
}
