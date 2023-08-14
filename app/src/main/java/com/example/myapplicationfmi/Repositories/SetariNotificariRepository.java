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

    // below line is the create a variable
    // for dao and list for all setariNotificaris.
    private SetariNotificariDAO dao;
    private LiveData<List<SetariNotificari>> allsetariNotificaris;

    // creating a constructor for our variables
    // and passing the variables to it.
    @RequiresApi(api = Build.VERSION_CODES.O)
    public SetariNotificariRepository(Application application) {
        MyRoomDatabase database = MyRoomDatabase.getInstance(application);
        dao = database.setariNotificariDao();
        allsetariNotificaris = dao.getAllSetariNotificaris();
    }

    // creating a method to insert the data to our database.
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
    // creating a method to update data in database.
    public void update(SetariNotificari model) {
        new UpdatesetariNotificariAsyncTask(dao).execute(model);
    }

    // creating a method to delete the data in our database.
    public void delete(SetariNotificari model) {
        new DeletesetariNotificariAsyncTask(dao).execute(model);
    }

    // below is the method to delete all the setariNotificaris.
    public void deleteAllsetariNotificaris() {
        new DeleteAllsetariNotificarisAsyncTask(dao).execute();
    }

    // below method is to read all the setariNotificaris.
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

    // we are creating a async task method to insert new setariNotificari.
    private static class InsertsetariNotificariAsyncTask extends AsyncTask<SetariNotificari, Void, Long> {
        private SetariNotificariDAO dao;

        private InsertsetariNotificariAsyncTask(SetariNotificariDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Long doInBackground(SetariNotificari... model) {
            // below line is use to insert our modal in dao.
            return dao.insertSetariNotificari(model[0]);
        }
    }

    // we are creating a async task method to update our setariNotificari.
    private static class UpdatesetariNotificariAsyncTask extends AsyncTask<SetariNotificari, Void, Void> {
        private SetariNotificariDAO dao;

        private UpdatesetariNotificariAsyncTask(SetariNotificariDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(SetariNotificari... models) {
            // below line is use to update
            // our modal in dao.
            dao.updateSetariNotificari(models[0]);
            return null;
        }
    }

    // we are creating a async task method to delete setariNotificari.
    private static class DeletesetariNotificariAsyncTask extends AsyncTask<SetariNotificari, Void, Void> {
        private SetariNotificariDAO dao;

        private DeletesetariNotificariAsyncTask(SetariNotificariDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(SetariNotificari... models) {
            // below line is use to delete
            // our setariNotificari modal in dao.
            dao.deleteSetariNotificari(models[0]);
            return null;
        }
    }

    // we are creating a async task method to delete all setariNotificaris.
    private static class DeleteAllsetariNotificarisAsyncTask extends AsyncTask<Void, Void, Void> {
        private SetariNotificariDAO dao;
        private DeleteAllsetariNotificarisAsyncTask(SetariNotificariDAO dao) {
            this.dao = dao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            // on below line calling method
            // to delete all setariNotificaris.
            dao.deleteAllSetariNotificaris();
            return null;
        }
    }
}
