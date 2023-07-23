package com.example.myapplicationfmi.Repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.myapplicationfmi.DAO.ProfessorSubjectDAO;
import com.example.myapplicationfmi.MyRoomDatabase;
import com.example.myapplicationfmi.beans.ProfessorSubject;

import java.util.List;

public class ProfessorSubjectRepository {

    // below line is the create a variable
    // for dao and list for all professorSubjects.
    private ProfessorSubjectDAO dao;
    private LiveData<List<ProfessorSubject>> allprofessorSubjects;

    // creating a constructor for our variables
    // and passing the variables to it.
    public ProfessorSubjectRepository(Application application) {
        MyRoomDatabase database = MyRoomDatabase.getInstance(application);
        dao = database.professorSubjectDao();
        allprofessorSubjects = dao.getAllProfessorSubjects();
    }

    // creating a method to insert the data to our database.
    public void insert(ProfessorSubject model) {
        new InsertprofessorSubjectAsyncTask(dao).execute(model);
    }

    // creating a method to update data in database.
    public void update(ProfessorSubject model) {
        new UpdateprofessorSubjectAsyncTask(dao).execute(model);
    }

    // creating a method to delete the data in our database.
    public void delete(ProfessorSubject model) {
        new DeleteprofessorSubjectAsyncTask(dao).execute(model);
    }

    // below is the method to delete all the professorSubjects.
    public void deleteAllprofessorSubjects() {
        new DeleteAllprofessorSubjectsAsyncTask(dao).execute();
    }

    // below method is to read all the professorSubjects.
    public LiveData<List<ProfessorSubject>> getAllProfessorSubjects() {
        return allprofessorSubjects;
    }

    // we are creating a async task method to insert new professorSubject.
    private static class InsertprofessorSubjectAsyncTask extends AsyncTask<ProfessorSubject, Void, Void> {
        private ProfessorSubjectDAO dao;

        private InsertprofessorSubjectAsyncTask(ProfessorSubjectDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(ProfessorSubject... model) {
            // below line is use to insert our modal in dao.
            dao.insertProfessorSubject(model[0]);
            return null;
        }
    }

    // we are creating a async task method to update our professorSubject.
    private static class UpdateprofessorSubjectAsyncTask extends AsyncTask<ProfessorSubject, Void, Void> {
        private ProfessorSubjectDAO dao;

        private UpdateprofessorSubjectAsyncTask(ProfessorSubjectDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(ProfessorSubject... models) {
            // below line is use to update
            // our modal in dao.
            dao.updateProfessorSubject(models[0]);
            return null;
        }
    }

    // we are creating a async task method to delete professorSubject.
    private static class DeleteprofessorSubjectAsyncTask extends AsyncTask<ProfessorSubject, Void, Void> {
        private ProfessorSubjectDAO dao;

        private DeleteprofessorSubjectAsyncTask(ProfessorSubjectDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(ProfessorSubject... models) {
            // below line is use to delete
            // our professorSubject modal in dao.
            dao.deleteProfessorSubject(models[0]);
            return null;
        }
    }

    // we are creating a async task method to delete all professorSubjects.
    private static class DeleteAllprofessorSubjectsAsyncTask extends AsyncTask<Void, Void, Void> {
        private ProfessorSubjectDAO dao;
        private DeleteAllprofessorSubjectsAsyncTask(ProfessorSubjectDAO dao) {
            this.dao = dao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            // on below line calling method
            // to delete all professorSubjects.
            dao.deleteAllProfessorSubjects();
            return null;
        }
    }
}
