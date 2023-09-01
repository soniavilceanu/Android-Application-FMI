package com.example.myapplicationfmi.Repositories;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;

import com.example.myapplicationfmi.DAO.ProfessorDAO;
import com.example.myapplicationfmi.MyRoomDatabase;
import com.example.myapplicationfmi.beans.Course;
import com.example.myapplicationfmi.beans.Professor;
import com.example.myapplicationfmi.beans.ProfessorWithGroups;
import com.example.myapplicationfmi.beans.ProfessorWithSubjects;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class ProfessorRepository {

    private ProfessorDAO dao;
    private LiveData<List<Professor>> allprofessors;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ProfessorRepository(Application application) {
        MyRoomDatabase database = MyRoomDatabase.getInstance(application);
        dao = database.professorDao();
        allprofessors = dao.getAllProfessors();
    }

//    public void insert(Professor model) {
//        new InsertprofessorAsyncTask(dao).execute(model);
//    }
    public long insert(Professor model) {
        try {
            return new ProfessorRepository.InsertprofessorAsyncTask(dao).execute(model).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return -1;
        }
    }
    public void update(Professor model) {
        new UpdateprofessorAsyncTask(dao).execute(model);
    }

    public void delete(Professor model) {
        new DeleteprofessorAsyncTask(dao).execute(model);
    }

    public void deleteAllprofessors() {
        new DeleteAllprofessorsAsyncTask(dao).execute();
    }

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

    private static class InsertprofessorAsyncTask extends AsyncTask<Professor, Void, Long> {
        private ProfessorDAO dao;

        private InsertprofessorAsyncTask(ProfessorDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Long doInBackground(Professor... model) {
            return dao.insertProfessor(model[0]);
        }
    }

    private static class UpdateprofessorAsyncTask extends AsyncTask<Professor, Void, Void> {
        private ProfessorDAO dao;

        private UpdateprofessorAsyncTask(ProfessorDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Professor... models) {
            dao.updateProfessor(models[0]);
            return null;
        }
    }

    private static class DeleteprofessorAsyncTask extends AsyncTask<Professor, Void, Void> {
        private ProfessorDAO dao;

        private DeleteprofessorAsyncTask(ProfessorDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Professor... models) {
            dao.deleteProfessor(models[0]);
            return null;
        }
    }

    private static class DeleteAllprofessorsAsyncTask extends AsyncTask<Void, Void, Void> {
        private ProfessorDAO dao;
        private DeleteAllprofessorsAsyncTask(ProfessorDAO dao) {
            this.dao = dao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            dao.deleteAllProfessors();
            return null;
        }
    }
}
