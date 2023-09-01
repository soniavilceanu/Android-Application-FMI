package com.example.myapplicationfmi.Repositories;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;

import com.example.myapplicationfmi.DAO.SetariNotificariDAO;
import com.example.myapplicationfmi.MyRoomDatabase;
import com.example.myapplicationfmi.beans.Course;
import com.example.myapplicationfmi.beans.Note;
import com.example.myapplicationfmi.beans.SetariNotificari;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class SetariNotificariRepository {

    private SetariNotificariDAO dao;
    private LiveData<List<SetariNotificari>> allsetariNotificaris;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public SetariNotificariRepository(Application application) {
        MyRoomDatabase database = MyRoomDatabase.getInstance(application);
        dao = database.setariNotificariDao();
        allsetariNotificaris = dao.getAllSetariNotificaris();
    }

//    public void insert(SetariNotificari model) {
//        new InsertsetariNotificariAsyncTask(dao).execute(model);
//    }
    public long insert(SetariNotificari model) {
        try {
            return new SetariNotificariRepository.InsertsetariNotificariAsyncTask(dao).execute(model).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return -1;
        }
    }
    public void update(SetariNotificari model) {
        new UpdatesetariNotificariAsyncTask(dao).execute(model);
    }

    public void delete(SetariNotificari model) {
        new DeletesetariNotificariAsyncTask(dao).execute(model);
    }

    public void deleteAllsetariNotificaris() {
        new DeleteAllsetariNotificarisAsyncTask(dao).execute();
    }

    public LiveData<List<SetariNotificari>> getAllSetariNotificaris() {
        return allsetariNotificaris;
    }
    public LiveData<SetariNotificari> getSetariNotificariBySetariNotificariId(long setariNotificariId){
        return dao.getSetariNotificariBySetariNotificariId(setariNotificariId);
    }
    public LiveData<List<SetariNotificari>> getSetariNotificariByStudentId(long studentId){
        return dao.getSetariNotificariByStudentId(studentId);
    }
    public LiveData<SetariNotificari> getSetariNotificariByStudentIdAndType(long studentId, String type){
        return dao.getSetariNotificariByStudentIdAndType(studentId, type);
    }

    private static class InsertsetariNotificariAsyncTask extends AsyncTask<SetariNotificari, Void, Long> {
        private SetariNotificariDAO dao;

        private InsertsetariNotificariAsyncTask(SetariNotificariDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Long doInBackground(SetariNotificari... model) {
            return dao.insertSetariNotificari(model[0]);
        }
    }

    private static class UpdatesetariNotificariAsyncTask extends AsyncTask<SetariNotificari, Void, Void> {
        private SetariNotificariDAO dao;

        private UpdatesetariNotificariAsyncTask(SetariNotificariDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(SetariNotificari... models) {
            dao.updateSetariNotificari(models[0]);
            return null;
        }
    }

    private static class DeletesetariNotificariAsyncTask extends AsyncTask<SetariNotificari, Void, Void> {
        private SetariNotificariDAO dao;

        private DeletesetariNotificariAsyncTask(SetariNotificariDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(SetariNotificari... models) {
            dao.deleteSetariNotificari(models[0]);
            return null;
        }
    }

    private static class DeleteAllsetariNotificarisAsyncTask extends AsyncTask<Void, Void, Void> {
        private SetariNotificariDAO dao;
        private DeleteAllsetariNotificarisAsyncTask(SetariNotificariDAO dao) {
            this.dao = dao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            dao.deleteAllSetariNotificaris();
            return null;
        }
    }
}
