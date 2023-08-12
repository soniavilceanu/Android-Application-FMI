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

    // below line is the create a variable
    // for dao and list for all students.
    private StudentDAO dao;
    private LiveData<List<Student>> allstudents;

    // creating a constructor for our variables
    // and passing the variables to it.
    @RequiresApi(api = Build.VERSION_CODES.O)
    public StudentRepository(Application application) {
        MyRoomDatabase database = MyRoomDatabase.getInstance(application);
        dao = database.studentDao();
        allstudents = dao.getAllStudents();
    }

    // creating a method to insert the data to our database.
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
    // creating a method to update data in database.
    public void update(Student model) {
        new UpdatestudentAsyncTask(dao).execute(model);
    }

    // creating a method to delete the data in our database.
    public void delete(Student model) {
        new DeletestudentAsyncTask(dao).execute(model);
    }

    // below is the method to delete all the students.
    public void deleteAllstudents() {
        new DeleteAllstudentsAsyncTask(dao).execute();
    }

    // below method is to read all the students.
    public LiveData<List<Student>> getAllStudents() {
        return allstudents;
    }

    // we are creating a async task method to insert new student.
    private static class InsertstudentAsyncTask extends AsyncTask<Student, Void, Long> {
        private StudentDAO dao;

        private InsertstudentAsyncTask(StudentDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Long doInBackground(Student... model) {
            // below line is use to insert our modal in dao.
            return dao.insertStudent(model[0]);
        }
    }

    // we are creating a async task method to update our student.
    private static class UpdatestudentAsyncTask extends AsyncTask<Student, Void, Void> {
        private StudentDAO dao;

        private UpdatestudentAsyncTask(StudentDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Student... models) {
            // below line is use to update
            // our modal in dao.
            dao.updateStudent(models[0]);
            return null;
        }
    }

    // we are creating a async task method to delete student.
    private static class DeletestudentAsyncTask extends AsyncTask<Student, Void, Void> {
        private StudentDAO dao;

        private DeletestudentAsyncTask(StudentDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Student... models) {
            // below line is use to delete
            // our student modal in dao.
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

    // we are creating a async task method to delete all students.
    private static class DeleteAllstudentsAsyncTask extends AsyncTask<Void, Void, Void> {
        private StudentDAO dao;
        private DeleteAllstudentsAsyncTask(StudentDAO dao) {
            this.dao = dao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            // on below line calling method
            // to delete all students.
            dao.deleteAllStudents();
            return null;
        }
    }
}
