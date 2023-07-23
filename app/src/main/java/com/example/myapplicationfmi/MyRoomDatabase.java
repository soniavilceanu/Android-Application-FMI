package com.example.myapplicationfmi;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.myapplicationfmi.DAO.CourseDAO;
import com.example.myapplicationfmi.DAO.DashTabDAO;
import com.example.myapplicationfmi.DAO.GroupDAO;
import com.example.myapplicationfmi.DAO.NotificationDAO;
import com.example.myapplicationfmi.DAO.ProfessorDAO;
import com.example.myapplicationfmi.DAO.ProfessorSubjectDAO;
import com.example.myapplicationfmi.DAO.StudentDAO;
import com.example.myapplicationfmi.DAO.SubjectDAO;
import com.example.myapplicationfmi.beans.Course;
import com.example.myapplicationfmi.beans.DashTab;
import com.example.myapplicationfmi.beans.Group;
import com.example.myapplicationfmi.beans.Notification;
import com.example.myapplicationfmi.beans.Professor;
import com.example.myapplicationfmi.beans.ProfessorSubject;
import com.example.myapplicationfmi.beans.Student;
import com.example.myapplicationfmi.beans.Subject;

@Database(entities = {DashTab.class, Notification.class, Student.class, Group.class, Course.class, Professor.class, Subject.class, ProfessorSubject.class}, version = 1)
public abstract class MyRoomDatabase extends RoomDatabase {
    public abstract DashTabDAO dashTabDao();
    public abstract NotificationDAO notificationDao();
    public abstract StudentDAO studentDao();
    public abstract GroupDAO groupDao();
    public abstract CourseDAO courseDao();
    public abstract ProfessorDAO professorDao();
    public abstract SubjectDAO subjectDao();
    public abstract ProfessorSubjectDAO professorSubjectDao();

    private static MyRoomDatabase instance; // Change RoomDatabase to MyRoomDatabase

    public static synchronized MyRoomDatabase getInstance(Context context) { // Change RoomDatabase to MyRoomDatabase
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), MyRoomDatabase.class, "RoomDatabase")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    // below line is to create a callback for our room database.
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            // this method is called when database is created
            // and below line is to populate our data.
            new SPopulateDbAsyncTask(instance).execute();
        }
    };

    // we are creating an async task class to perform task in background.
    private static class SPopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        SPopulateDbAsyncTask(MyRoomDatabase instance) {
            StudentDAO studentDAO = instance.studentDao();
            GroupDAO groupDAO = instance.groupDao();
            ProfessorDAO professorDAO = instance.professorDao();
            NotificationDAO notificationDAO = instance.notificationDao();
            CourseDAO courseDAO = instance.courseDao();
            SubjectDAO subjectDAO = instance.subjectDao();
            DashTabDAO dashTabDAO = instance.dashTabDao();
            ProfessorSubjectDAO professorSubjectDAO = instance.professorSubjectDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }
}
