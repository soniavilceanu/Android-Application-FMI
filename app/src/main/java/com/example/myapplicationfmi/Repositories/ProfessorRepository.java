package com.example.myapplicationfmi.Repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.myapplicationfmi.DAO.ProfessorDAO;
import com.example.myapplicationfmi.MyRoomDatabase;
import com.example.myapplicationfmi.beans.Professor;
import com.example.myapplicationfmi.beans.ProfessorWithGroups;
import com.example.myapplicationfmi.beans.ProfessorWithSubjects;

import java.util.List;

public class ProfessorRepository {

    // below line is the create a variable
    // for dao and list for all professors.
    private ProfessorDAO dao;
    private LiveData<List<Professor>> allprofessors;

    // creating a constructor for our variables
    // and passing the variables to it.
    public ProfessorRepository(Application application) {
        MyRoomDatabase database = MyRoomDatabase.getInstance(application);
        dao = database.professorDao();
        allprofessors = dao.getAllProfessors();
    }

    // creating a method to insert the data to our database.
    public void insert(Professor model) {
        new InsertprofessorAsyncTask(dao).execute(model);
    }

    // creating a method to update data in database.
    public void update(Professor model) {
        new UpdateprofessorAsyncTask(dao).execute(model);
    }

    // creating a method to delete the data in our database.
    public void delete(Professor model) {
        new DeleteprofessorAsyncTask(dao).execute(model);
    }

    // below is the method to delete all the professors.
    public void deleteAllprofessors() {
        new DeleteAllprofessorsAsyncTask(dao).execute();
    }

    // below method is to read all the professors.
    public LiveData<List<Professor>> getAllProfessors() {
        return allprofessors;
    }

    public LiveData<Long> getProfessorIdByEmail(String email) {
        return dao.getProfessorIdByEmail(email);
    }
    public LiveData<Professor> getProfessorById(long professorId){
        return dao.getProfessorById(professorId);
    }
    public LiveData<ProfessorWithGroups> getProfessorWithGroupsById(long professorId){
        return dao.getProfessorWithGroupsById(professorId);
    }
    public LiveData<ProfessorWithSubjects> getProfessorWithSubjectsById(Long professorId){
        return dao.getProfessorWithSubjectsById(professorId);
    }
    public LiveData<Professor> getProfessorByEmail(String email){
        return dao.getProfessorByEmail(email);
    }

    // we are creating a async task method to insert new professor.
    private static class InsertprofessorAsyncTask extends AsyncTask<Professor, Void, Void> {
        private ProfessorDAO dao;

        private InsertprofessorAsyncTask(ProfessorDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Professor... model) {
            // below line is use to insert our modal in dao.
            dao.insertProfessor(model[0]);
            return null;
        }
    }

    // we are creating a async task method to update our professor.
    private static class UpdateprofessorAsyncTask extends AsyncTask<Professor, Void, Void> {
        private ProfessorDAO dao;

        private UpdateprofessorAsyncTask(ProfessorDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Professor... models) {
            // below line is use to update
            // our modal in dao.
            dao.updateProfessor(models[0]);
            return null;
        }
    }

    // we are creating a async task method to delete professor.
    private static class DeleteprofessorAsyncTask extends AsyncTask<Professor, Void, Void> {
        private ProfessorDAO dao;

        private DeleteprofessorAsyncTask(ProfessorDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Professor... models) {
            // below line is use to delete
            // our professor modal in dao.
            dao.deleteProfessor(models[0]);
            return null;
        }
    }

    // we are creating a async task method to delete all professors.
    private static class DeleteAllprofessorsAsyncTask extends AsyncTask<Void, Void, Void> {
        private ProfessorDAO dao;
        private DeleteAllprofessorsAsyncTask(ProfessorDAO dao) {
            this.dao = dao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            // on below line calling method
            // to delete all professors.
            dao.deleteAllProfessors();
            return null;
        }
    }
}
