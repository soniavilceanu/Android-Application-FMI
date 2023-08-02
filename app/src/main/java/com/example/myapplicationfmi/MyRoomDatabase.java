package com.example.myapplicationfmi;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.myapplicationfmi.DAO.CalendarDAO;
import com.example.myapplicationfmi.DAO.CourseDAO;
import com.example.myapplicationfmi.DAO.DashTabDAO;
import com.example.myapplicationfmi.DAO.GroupDAO;
import com.example.myapplicationfmi.DAO.NotificationDAO;
import com.example.myapplicationfmi.DAO.ProfessorDAO;
import com.example.myapplicationfmi.DAO.ProfessorSubjectDAO;
import com.example.myapplicationfmi.DAO.StudentDAO;
import com.example.myapplicationfmi.DAO.SubjectDAO;
import com.example.myapplicationfmi.ModalFactory.Migration_1_2;
import com.example.myapplicationfmi.ModalFactory.Migration_2_3;
import com.example.myapplicationfmi.ModalFactory.Migration_3_4;
import com.example.myapplicationfmi.ModalFactory.Migration_4_5;
import com.example.myapplicationfmi.ModalFactory.Migration_5_6;
import com.example.myapplicationfmi.ModalFactory.Migration_6_7;
import com.example.myapplicationfmi.beans.Calendar;
import com.example.myapplicationfmi.beans.Course;
import com.example.myapplicationfmi.beans.DashTab;
import com.example.myapplicationfmi.beans.Group;
import com.example.myapplicationfmi.beans.Notification;
import com.example.myapplicationfmi.beans.Professor;
import com.example.myapplicationfmi.beans.ProfessorSubject;
import com.example.myapplicationfmi.beans.Student;
import com.example.myapplicationfmi.beans.Subject;

@Database(entities = {DashTab.class, Notification.class, Student.class, Group.class, Course.class, Professor.class, Subject.class, ProfessorSubject.class, Calendar.class}, version = 7)
public abstract class MyRoomDatabase extends RoomDatabase {
    public abstract DashTabDAO dashTabDao();
    public abstract NotificationDAO notificationDao();
    public abstract StudentDAO studentDao();
    public abstract GroupDAO groupDao();
    public abstract CourseDAO courseDao();
    public abstract ProfessorDAO professorDao();
    public abstract SubjectDAO subjectDao();
    public abstract ProfessorSubjectDAO professorSubjectDao();
    public abstract CalendarDAO calendarDao();
    static final Migration[] MIGRATIONS = {new Migration_1_2(), new Migration_2_3(), new Migration_3_4(), new Migration_4_5(), new Migration_5_6(), new Migration_6_7()};

    private static MyRoomDatabase instance;

    public static synchronized MyRoomDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), MyRoomDatabase.class, "RoomDatabase")
                    .fallbackToDestructiveMigration()
                    .addMigrations(MIGRATIONS)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new SPopulateDbAsyncTask(instance).execute();
        }
    };

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
            CalendarDAO calendarDAO = instance.calendarDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }
}
