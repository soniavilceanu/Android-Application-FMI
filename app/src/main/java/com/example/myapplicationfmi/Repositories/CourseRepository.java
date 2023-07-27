package com.example.myapplicationfmi.Repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.myapplicationfmi.DAO.CourseDAO;
import com.example.myapplicationfmi.MyRoomDatabase;
import com.example.myapplicationfmi.beans.Course;

import java.util.List;

public class CourseRepository {

    private CourseDAO dao;
    private LiveData<List<Course>> allcourses;
    public CourseRepository(Application application) {
        MyRoomDatabase database = MyRoomDatabase.getInstance(application);
        dao = database.courseDao();
        allcourses = dao.getAllCourses();
    }

    public void insert(Course model) {
        new InsertcourseAsyncTask(dao).execute(model);
    }
    public void update(Course model) {
        new UpdatecourseAsyncTask(dao).execute(model);
    }
    public void delete(Course model) {
        new DeletecourseAsyncTask(dao).execute(model);
    }
    public void deleteAllcourses() {
        new DeleteAllcoursesAsyncTask(dao).execute();
    }
    public LiveData<List<Course>> getAllCourses() {
        return allcourses;
    }
    public LiveData<Course> getCourseByGroupIdZiAndIntervalOrar(long groupId, String zi, int intervalOrar){
        return dao.getCourseByGroupIdZiAndIntervalOrar(groupId, zi, intervalOrar);
    }
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
    private static class UpdatecourseAsyncTask extends AsyncTask<Course, Void, Void> {
        private CourseDAO dao;

        private UpdatecourseAsyncTask(CourseDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Course... models) {
            dao.updateCourse(models[0]);
            return null;
        }
    }
    private static class DeletecourseAsyncTask extends AsyncTask<Course, Void, Void> {
        private CourseDAO dao;

        private DeletecourseAsyncTask(CourseDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Course... models) {
            dao.deleteCourse(models[0]);
            return null;
        }
    }
    private static class DeleteAllcoursesAsyncTask extends AsyncTask<Void, Void, Void> {
        private CourseDAO dao;
        private DeleteAllcoursesAsyncTask(CourseDAO dao) {
            this.dao = dao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            dao.deleteAllCourses();
            return null;
        }
    }
}
