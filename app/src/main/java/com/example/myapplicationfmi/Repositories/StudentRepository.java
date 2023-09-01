package com.example.myapplicationfmi.Repositories;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;

import com.example.myapplicationfmi.DAO.StudentDAO;
import com.example.myapplicationfmi.MyRoomDatabase;
import com.example.myapplicationfmi.beans.Course;
import com.example.myapplicationfmi.beans.Group;
import com.example.myapplicationfmi.beans.Student;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class StudentRepository {

    private StudentDAO dao;
    private LiveData<List<Student>> allstudents;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public StudentRepository(Application application) {
        MyRoomDatabase database = MyRoomDatabase.getInstance(application);
        dao = database.studentDao();
        allstudents = dao.getAllStudents();
    }

//    public void insert(Student model) {
//        new InsertstudentAsyncTask(dao).execute(model);
//    }
    public long insert(Student model) {
        try {
            return new StudentRepository.InsertstudentAsyncTask(dao).execute(model).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return -1;
        }
    }
    public void update(Student model) {
        new UpdatestudentAsyncTask(dao).execute(model);
    }

    public void delete(Student model) {
        new DeletestudentAsyncTask(dao).execute(model);
    }

    public void deleteAllstudents() {
        new DeleteAllstudentsAsyncTask(dao).execute();
    }

    public LiveData<List<Student>> getAllStudents() {
        return allstudents;
    }

    private static class InsertstudentAsyncTask extends AsyncTask<Student, Void, Long> {
        private StudentDAO dao;

        private InsertstudentAsyncTask(StudentDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Long doInBackground(Student... model) {
            return dao.insertStudent(model[0]);
        }
    }

    private static class UpdatestudentAsyncTask extends AsyncTask<Student, Void, Void> {
        private StudentDAO dao;

        private UpdatestudentAsyncTask(StudentDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Student... models) {
            dao.updateStudent(models[0]);
            return null;
        }
    }

    private static class DeletestudentAsyncTask extends AsyncTask<Student, Void, Void> {
        private StudentDAO dao;

        private DeletestudentAsyncTask(StudentDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Student... models) {
            dao.deleteStudent(models[0]);
            return null;
        }
    }
    public LiveData<Student> getStudentById(long studentId) {
        return dao.getStudentById(studentId);
    }
    public LiveData<Student> getStudentByEmail(String email){
        return dao.getStudentByEmail(email);
    }

    private static class DeleteAllstudentsAsyncTask extends AsyncTask<Void, Void, Void> {
        private StudentDAO dao;
        private DeleteAllstudentsAsyncTask(StudentDAO dao) {
            this.dao = dao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            dao.deleteAllStudents();
            return null;
        }
    }
}
