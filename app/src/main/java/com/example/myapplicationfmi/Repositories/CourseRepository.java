package com.example.myapplicationfmi.Repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.myapplicationfmi.DAO.CourseDAO;
import com.example.myapplicationfmi.MyRoomDatabase;
import com.example.myapplicationfmi.beans.Course;

import java.util.List;

public class CourseRepository {

    // below line is the create a variable
    // for dao and list for all courses.
    private CourseDAO dao;
    private LiveData<List<Course>> allcourses;

    // creating a constructor for our variables
    // and passing the variables to it.
    public CourseRepository(Application application) {
        MyRoomDatabase database = MyRoomDatabase.getInstance(application);
        dao = database.courseDao();
        allcourses = dao.getAllCourses();
    }

    // creating a method to insert the data to our database.
    public void insert(Course model) {
        new InsertcourseAsyncTask(dao).execute(model);
    }

    // creating a method to update data in database.
    public void update(Course model) {
        new UpdatecourseAsyncTask(dao).execute(model);
    }

    // creating a method to delete the data in our database.
    public void delete(Course model) {
        new DeletecourseAsyncTask(dao).execute(model);
    }

    // below is the method to delete all the courses.
    public void deleteAllcourses() {
        new DeleteAllcoursesAsyncTask(dao).execute();
    }

    // below method is to read all the courses.
    public LiveData<List<Course>> getAllCourses() {
        return allcourses;
    }

    // we are creating a async task method to insert new course.
    private static class InsertcourseAsyncTask extends AsyncTask<Course, Void, Void> {
        private CourseDAO dao;

        private InsertcourseAsyncTask(CourseDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Course... model) {
            // below line is use to insert our modal in dao.
            dao.insertCourse(model[0]);
            return null;
        }
    }

    // we are creating a async task method to update our course.
    private static class UpdatecourseAsyncTask extends AsyncTask<Course, Void, Void> {
        private CourseDAO dao;

        private UpdatecourseAsyncTask(CourseDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Course... models) {
            // below line is use to update
            // our modal in dao.
            dao.updateCourse(models[0]);
            return null;
        }
    }

    // we are creating a async task method to delete course.
    private static class DeletecourseAsyncTask extends AsyncTask<Course, Void, Void> {
        private CourseDAO dao;

        private DeletecourseAsyncTask(CourseDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Course... models) {
            // below line is use to delete
            // our course modal in dao.
            dao.deleteCourse(models[0]);
            return null;
        }
    }

    // we are creating a async task method to delete all courses.
    private static class DeleteAllcoursesAsyncTask extends AsyncTask<Void, Void, Void> {
        private CourseDAO dao;
        private DeleteAllcoursesAsyncTask(CourseDAO dao) {
            this.dao = dao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            // on below line calling method
            // to delete all courses.
            dao.deleteAllCourses();
            return null;
        }
    }
}
