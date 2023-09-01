package com.example.myapplicationfmi.Repositories;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;

import com.example.myapplicationfmi.DAO.ProfessorSubjectDAO;
import com.example.myapplicationfmi.MyRoomDatabase;
import com.example.myapplicationfmi.beans.ProfessorSubject;

import java.util.List;

public class ProfessorSubjectRepository {

    private ProfessorSubjectDAO dao;
    private LiveData<List<ProfessorSubject>> allprofessorSubjects;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ProfessorSubjectRepository(Application application) {
        MyRoomDatabase database = MyRoomDatabase.getInstance(application);
        dao = database.professorSubjectDao();
        allprofessorSubjects = dao.getAllProfessorSubjects();
    }

    public void insert(ProfessorSubject model) {
        new InsertprofessorSubjectAsyncTask(dao).execute(model);
    }

    public void update(ProfessorSubject model) {
        new UpdateprofessorSubjectAsyncTask(dao).execute(model);
    }

    public void delete(ProfessorSubject model) {
        new DeleteprofessorSubjectAsyncTask(dao).execute(model);
    }

    public void deleteAllprofessorSubjects() {
        new DeleteAllprofessorSubjectsAsyncTask(dao).execute();
    }

    public LiveData<List<ProfessorSubject>> getAllProfessorSubjects() {
        return allprofessorSubjects;
    }

    private static class InsertprofessorSubjectAsyncTask extends AsyncTask<ProfessorSubject, Void, Void> {
        private ProfessorSubjectDAO dao;

        private InsertprofessorSubjectAsyncTask(ProfessorSubjectDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(ProfessorSubject... model) {
            dao.insertProfessorSubject(model[0]);
            return null;
        }
    }

    private static class UpdateprofessorSubjectAsyncTask extends AsyncTask<ProfessorSubject, Void, Void> {
        private ProfessorSubjectDAO dao;

        private UpdateprofessorSubjectAsyncTask(ProfessorSubjectDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(ProfessorSubject... models) {
            dao.updateProfessorSubject(models[0]);
            return null;
        }
    }

    private static class DeleteprofessorSubjectAsyncTask extends AsyncTask<ProfessorSubject, Void, Void> {
        private ProfessorSubjectDAO dao;

        private DeleteprofessorSubjectAsyncTask(ProfessorSubjectDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(ProfessorSubject... models) {
            dao.deleteProfessorSubject(models[0]);
            return null;
        }
    }

    private static class DeleteAllprofessorSubjectsAsyncTask extends AsyncTask<Void, Void, Void> {
        private ProfessorSubjectDAO dao;
        private DeleteAllprofessorSubjectsAsyncTask(ProfessorSubjectDAO dao) {
            this.dao = dao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            dao.deleteAllProfessorSubjects();
            return null;
        }
    }
}
