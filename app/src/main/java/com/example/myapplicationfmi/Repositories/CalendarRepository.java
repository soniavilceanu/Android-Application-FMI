package com.example.myapplicationfmi.Repositories;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;

import com.example.myapplicationfmi.DAO.CalendarDAO;
import com.example.myapplicationfmi.MyRoomDatabase;
import com.example.myapplicationfmi.beans.Calendar;

import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class CalendarRepository {

    private CalendarDAO dao;
    private LiveData<List<Calendar>> allcalendars;
    @RequiresApi(api = Build.VERSION_CODES.O)
    public CalendarRepository(Application application) {
        MyRoomDatabase database = MyRoomDatabase.getInstance(application);
        dao = database.calendarDao();
        allcalendars = dao.getAllCalendars();
    }

//    public void insert(Calendar model) {
//        new InsertcalendarAsyncTask(dao).execute(model);
//    }
    public long insert(Calendar model) {
        try {
            return new InsertcalendarAsyncTask(dao).execute(model).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return -1;
        }
    }
    public void update(Calendar model) {
        new UpdatecalendarAsyncTask(dao).execute(model);
    }
    public void delete(Calendar model) {
        new DeletecalendarAsyncTask(dao).execute(model);
    }
    public void deleteAllcalendars() {
        new DeleteAllcalendarsAsyncTask(dao).execute();
    }
    public LiveData<List<Calendar>> getAllCalendars() {
        return allcalendars;
    }
    public LiveData<Calendar> getCalendarByLunaIdSaptamanaAndZiuaSaptamaniiForAdmin(long lunaId, String saptamana, int ziuaSaptamanii){
        return dao.getCalendarByLunaIdSaptamanaAndZiuaSaptamaniiForAdmin(lunaId, saptamana, ziuaSaptamanii);
    }
    public LiveData<Calendar> getCalendarByLunaIdSaptamanaAndZiuaSaptamaniiForProfessor(long lunaId, String saptamana, int ziuaSaptamanii, LocalTime oraInceput, LocalTime oraFinal){
        return dao.getCalendarByLunaIdSaptamanaAndZiuaSaptamaniiForProfessor(lunaId,saptamana,ziuaSaptamanii,oraInceput,oraFinal);
    }
    public LiveData<List<Calendar>> getCalendarsByLunaIdSaptamanaAndZiuaSaptamanii(long lunaId, String saptamana, int ziuaSaptamanii){
        return dao.getCalendarsByLunaIdSaptamanaAndZiuaSaptamanii(lunaId,saptamana,ziuaSaptamanii);
    }
    public LiveData<Calendar> getCalendarById(long calendarId){
        return dao.getCalendarById(calendarId);
    }
    private static class InsertcalendarAsyncTask extends AsyncTask<Calendar, Void, Long> {
        private CalendarDAO dao;

        private InsertcalendarAsyncTask(CalendarDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Long doInBackground(Calendar... model) {
            // below line is use to insert our modal in dao.
            return dao.insertCalendar(model[0]);
        }
    }
    private static class UpdatecalendarAsyncTask extends AsyncTask<Calendar, Void, Void> {
        private CalendarDAO dao;

        private UpdatecalendarAsyncTask(CalendarDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Calendar... models) {
            dao.updateCalendar(models[0]);
            return null;
        }
    }
    private static class DeletecalendarAsyncTask extends AsyncTask<Calendar, Void, Void> {
        private CalendarDAO dao;

        private DeletecalendarAsyncTask(CalendarDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Calendar... models) {
            dao.deleteCalendar(models[0]);
            return null;
        }
    }
    private static class DeleteAllcalendarsAsyncTask extends AsyncTask<Void, Void, Void> {
        private CalendarDAO dao;
        private DeleteAllcalendarsAsyncTask(CalendarDAO dao) {
            this.dao = dao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            dao.deleteAllCalendars();
            return null;
        }
    }
}
