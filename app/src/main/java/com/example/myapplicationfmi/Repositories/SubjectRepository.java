package com.example.myapplicationfmi.Repositories;



import android.app.Application;
import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;

import com.example.myapplicationfmi.DAO.SubjectDAO;
import com.example.myapplicationfmi.MyRoomDatabase;
import com.example.myapplicationfmi.beans.Course;
import com.example.myapplicationfmi.beans.Subject;
import com.example.myapplicationfmi.beans.SubjectWithProfessors;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class SubjectRepository {

    private SubjectDAO dao;
    private LiveData<List<Subject>> allsubjects;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public SubjectRepository(Application application) {
        MyRoomDatabase database = MyRoomDatabase.getInstance(application);
        dao = database.subjectDao();
        allsubjects = dao.getAllSubjects();
    }

//    public void insert(Subject model) {
//        new InsertsubjectAsyncTask(dao).execute(model);
//    }
    public long insert(Subject model) {
        try {
            return new SubjectRepository.InsertsubjectAsyncTask(dao).execute(model).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return -1;
        }
    }
    public void update(Subject model) {
        new UpdatesubjectAsyncTask(dao).execute(model);
    }

    public void delete(Subject model) {
        new DeletesubjectAsyncTask(dao).execute(model);
    }

    public void deleteAllsubjects() {
        new DeleteAllsubjectsAsyncTask(dao).execute();
    }
    public void deleteSubjectById(Long subjectId) {
        new DeleteSubjectByIdAsyncTask(dao).execute(subjectId);
    }


    public LiveData<List<Subject>> getAllSubjects() {
        return allsubjects;
    }
    public LiveData<Long> getSubjectIdByDenumire(String denumire) {
        return dao.getSubjectIdByDenumire(denumire);
    }
    public LiveData<SubjectWithProfessors> getSubjectWithProfessorsById(long subjectId){
        return dao.getSubjectWithProfessorsById(subjectId);
    }
    public LiveData<Subject> getSubjectById(long subjectId){
        return dao.getSubjectById(subjectId);
    }


    private static class InsertsubjectAsyncTask extends AsyncTask<Subject, Void, Long> {
        private SubjectDAO dao;

        private InsertsubjectAsyncTask(SubjectDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Long doInBackground(Subject... model) {
            return dao.insertSubject(model[0]);
        }
    }

    private static class UpdatesubjectAsyncTask extends AsyncTask<Subject, Void, Void> {
        private SubjectDAO dao;

        private UpdatesubjectAsyncTask(SubjectDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Subject... models) {
            dao.updateSubject(models[0]);
            return null;
        }
    }

    private static class DeletesubjectAsyncTask extends AsyncTask<Subject, Void, Void> {
        private SubjectDAO dao;

        private DeletesubjectAsyncTask(SubjectDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Subject... models) {
            dao.deleteSubject(models[0]);
            return null;
        }
    }

    private static class DeleteAllsubjectsAsyncTask extends AsyncTask<Void, Void, Void> {
        private SubjectDAO dao;
        private DeleteAllsubjectsAsyncTask(SubjectDAO dao) {
            this.dao = dao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            dao.deleteAllSubjects();
            return null;
        }
    }

    private static class DeleteSubjectByIdAsyncTask extends AsyncTask<Long, Void, Void> {
        private SubjectDAO dao;

        private DeleteSubjectByIdAsyncTask(SubjectDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Long... subjectIds) {
            dao.deleteSubjectById(subjectIds[0]);
            return null;
        }
    }


}
