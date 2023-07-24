package com.example.myapplicationfmi.Repositories;



import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.myapplicationfmi.DAO.SubjectDAO;
import com.example.myapplicationfmi.MyRoomDatabase;
import com.example.myapplicationfmi.beans.Subject;
import com.example.myapplicationfmi.beans.SubjectWithProfessors;

import java.util.List;

public class SubjectRepository {

    // below line is the create a variable
    // for dao and list for all subjects.
    private SubjectDAO dao;
    private LiveData<List<Subject>> allsubjects;

    // creating a constructor for our variables
    // and passing the variables to it.
    public SubjectRepository(Application application) {
        MyRoomDatabase database = MyRoomDatabase.getInstance(application);
        dao = database.subjectDao();
        allsubjects = dao.getAllSubjects();
    }

    // creating a method to insert the data to our database.
    public void insert(Subject model) {
        new InsertsubjectAsyncTask(dao).execute(model);
    }

    // creating a method to update data in database.
    public void update(Subject model) {
        new UpdatesubjectAsyncTask(dao).execute(model);
    }

    // creating a method to delete the data in our database.
    public void delete(Subject model) {
        new DeletesubjectAsyncTask(dao).execute(model);
    }

    // below is the method to delete all the subjects.
    public void deleteAllsubjects() {
        new DeleteAllsubjectsAsyncTask(dao).execute();
    }

    // below method is to read all the subjects.
    public LiveData<List<Subject>> getAllSubjects() {
        return allsubjects;
    }
    public LiveData<Long> getSubjectIdByDenumire(String denumire) {
        return dao.getSubjectIdByDenumire(denumire);
    }
    public LiveData<SubjectWithProfessors> getSubjectWithProfessorsById(long subjectId){
        return dao.getSubjectWithProfessorsById(subjectId);
    }


    // we are creating a async task method to insert new subject.
    private static class InsertsubjectAsyncTask extends AsyncTask<Subject, Void, Void> {
        private SubjectDAO dao;

        private InsertsubjectAsyncTask(SubjectDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Subject... model) {
            // below line is use to insert our modal in dao.
            dao.insertSubject(model[0]);
            return null;
        }
    }

    // we are creating a async task method to update our subject.
    private static class UpdatesubjectAsyncTask extends AsyncTask<Subject, Void, Void> {
        private SubjectDAO dao;

        private UpdatesubjectAsyncTask(SubjectDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Subject... models) {
            // below line is use to update
            // our modal in dao.
            dao.updateSubject(models[0]);
            return null;
        }
    }

    // we are creating a async task method to delete subject.
    private static class DeletesubjectAsyncTask extends AsyncTask<Subject, Void, Void> {
        private SubjectDAO dao;

        private DeletesubjectAsyncTask(SubjectDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Subject... models) {
            // below line is use to delete
            // our subject modal in dao.
            dao.deleteSubject(models[0]);
            return null;
        }
    }

    // we are creating a async task method to delete all subjects.
    private static class DeleteAllsubjectsAsyncTask extends AsyncTask<Void, Void, Void> {
        private SubjectDAO dao;
        private DeleteAllsubjectsAsyncTask(SubjectDAO dao) {
            this.dao = dao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            // on below line calling method
            // to delete all subjects.
            dao.deleteAllSubjects();
            return null;
        }
    }
}
