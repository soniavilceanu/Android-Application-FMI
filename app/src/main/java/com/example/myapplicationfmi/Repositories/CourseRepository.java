package com.example.myapplicationfmi.Repositories;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;

import com.example.myapplicationfmi.DAO.CourseDAO;
import com.example.myapplicationfmi.MyRoomDatabase;
import com.example.myapplicationfmi.beans.Calendar;
import com.example.myapplicationfmi.beans.Course;
import com.example.myapplicationfmi.beans.Group;
import com.example.myapplicationfmi.beans.Subject;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class CourseRepository {

    private CourseDAO dao;
    private LiveData<List<Course>> allcourses;
    @RequiresApi(api = Build.VERSION_CODES.O)
    public CourseRepository(Application application) {
        MyRoomDatabase database = MyRoomDatabase.getInstance(application);
        dao = database.courseDao();
        allcourses = dao.getAllCourses();
    }

//    public void insert(Course model) {
//        new InsertcourseAsyncTask(dao).execute(model);
//    }
    public long insert(Course model) {
        try {
            return new CourseRepository.InsertcourseAsyncTask(dao).execute(model).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return -1;
        }
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
    public void deleteCoursesByGroupId(Long groupId) {
        new DeleteCoursesByGroupIdAsyncTask(dao).execute(groupId);
    }
    public LiveData<List<Course>> getAllCourses() {
        return allcourses;
    }
    public LiveData<Course> getCourseByGroupIdZiAndIntervalOrar(long groupId, String zi, int intervalOrar){
        return dao.getCourseByGroupIdZiAndIntervalOrar(groupId, zi, intervalOrar);
    }
    public LiveData<List<Long>> getSubjectIdsByGroupIdAndProfessorId(long groupId, long professorId){
        return dao.getSubjectIdsByGroupIdAndProfessorId(groupId,professorId);
    }
    public LiveData<List<Subject>> getSubjectsByGroupIdAndProfessorId(long groupId, long professorId){
        return dao.getSubjectsByGroupIdAndProfessorId(groupId, professorId);
    }
    public LiveData<List<Group>> getGroupsByProfessorId(long professorId){
        return dao.getGroupsByProfessorId(professorId);
    }
    public LiveData<List<Group>> getGroupsBySubjectIdAndProfessorId(long subjectId, long professorId){
        return dao.getGroupsBySubjectIdAndProfessorId(subjectId,professorId);
    }

    public LiveData<List<Subject>> getSubjectsByGroupId(long groupId){
        return dao.getSubjectsByGroupId(groupId);
    }

    private static class InsertcourseAsyncTask extends AsyncTask<Course, Void, Long> {
        private CourseDAO dao;

        private InsertcourseAsyncTask(CourseDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Long doInBackground(Course... model) {
            // below line is use to insert our modal in dao.
            return dao.insertCourse(model[0]);
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

    private static class DeleteCoursesByGroupIdAsyncTask extends AsyncTask<Long, Void, Void> {
        private CourseDAO dao;

        private DeleteCoursesByGroupIdAsyncTask(CourseDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Long... groupIds) {
            dao.deleteCoursesByGroupId(groupIds[0]);
            return null;
        }
    }
}
