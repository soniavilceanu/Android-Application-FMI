package com.example.myapplicationfmi;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.myapplicationfmi.DAO.CalendarDAO;
import com.example.myapplicationfmi.DAO.CourseDAO;
import com.example.myapplicationfmi.DAO.EvidentaNotificariDAO;
import com.example.myapplicationfmi.DAO.EvidentaVoluntariatDAO;
import com.example.myapplicationfmi.DAO.GroupDAO;
import com.example.myapplicationfmi.DAO.NoteDAO;
import com.example.myapplicationfmi.DAO.NotificationDAO;
import com.example.myapplicationfmi.DAO.ProfessorDAO;
import com.example.myapplicationfmi.DAO.ProfessorSubjectDAO;
import com.example.myapplicationfmi.DAO.SetariNotificariDAO;
import com.example.myapplicationfmi.DAO.StudentDAO;
import com.example.myapplicationfmi.DAO.SubjectDAO;
import com.example.myapplicationfmi.ModalFactory.LocalDateConverter;
import com.example.myapplicationfmi.ModalFactory.LocalTimeConverter;
import com.example.myapplicationfmi.ModalFactory.Migration_10_11;
import com.example.myapplicationfmi.ModalFactory.Migration_11_12;
import com.example.myapplicationfmi.ModalFactory.Migration_12_13;
import com.example.myapplicationfmi.ModalFactory.Migration_13_14;
import com.example.myapplicationfmi.ModalFactory.Migration_14_15;
import com.example.myapplicationfmi.ModalFactory.Migration_15_16;
import com.example.myapplicationfmi.ModalFactory.Migration_1_2;
import com.example.myapplicationfmi.ModalFactory.Migration_2_3;
import com.example.myapplicationfmi.ModalFactory.Migration_3_4;
import com.example.myapplicationfmi.ModalFactory.Migration_4_5;
import com.example.myapplicationfmi.ModalFactory.Migration_5_6;
import com.example.myapplicationfmi.ModalFactory.Migration_6_7;
import com.example.myapplicationfmi.ModalFactory.Migration_7_8;
import com.example.myapplicationfmi.ModalFactory.Migration_8_9;
import com.example.myapplicationfmi.ModalFactory.Migration_9_10;
import com.example.myapplicationfmi.beans.Calendar;
import com.example.myapplicationfmi.beans.Course;
import com.example.myapplicationfmi.beans.EvidentaNotificari;
import com.example.myapplicationfmi.beans.EvidentaVoluntariat;
import com.example.myapplicationfmi.beans.Group;
import com.example.myapplicationfmi.beans.Note;
import com.example.myapplicationfmi.beans.Notification;
import com.example.myapplicationfmi.beans.Professor;
import com.example.myapplicationfmi.beans.ProfessorSubject;
import com.example.myapplicationfmi.beans.SetariNotificari;
import com.example.myapplicationfmi.beans.Student;
import com.example.myapplicationfmi.beans.Subject;

@RequiresApi(api = Build.VERSION_CODES.O)
@Database(entities = {Notification.class, Student.class, Group.class, Course.class, Professor.class, Subject.class, ProfessorSubject.class, Calendar.class, Note.class, EvidentaNotificari.class, EvidentaVoluntariat.class, SetariNotificari.class}, version = 16)
@TypeConverters({LocalTimeConverter.class, LocalDateConverter.class})
public abstract class MyRoomDatabase extends RoomDatabase {
    public abstract NotificationDAO notificationDao();
    public abstract StudentDAO studentDao();
    public abstract GroupDAO groupDao();
    public abstract CourseDAO courseDao();
    public abstract ProfessorDAO professorDao();
    public abstract SubjectDAO subjectDao();
    public abstract ProfessorSubjectDAO professorSubjectDao();
    public abstract CalendarDAO calendarDao();
    public abstract NoteDAO noteDao();
    public abstract EvidentaNotificariDAO evidentaNotificariDao();
    public abstract EvidentaVoluntariatDAO evidentaVoluntariatDao();
    public abstract SetariNotificariDAO setariNotificariDao();
    static final Migration[] MIGRATIONS = {new Migration_1_2(), new Migration_2_3(), new Migration_3_4(), new Migration_4_5(), new Migration_5_6(), new Migration_6_7(), new Migration_7_8(), new Migration_8_9(), new Migration_9_10(), new Migration_10_11(), new Migration_11_12(), new Migration_12_13(), new Migration_13_14(), new Migration_14_15(), new Migration_15_16()};

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
            ProfessorSubjectDAO professorSubjectDAO = instance.professorSubjectDao();
            CalendarDAO calendarDAO = instance.calendarDao();
            NoteDAO noteDAO = instance.noteDao();
            EvidentaNotificariDAO evidentaNotificariDAO = instance.evidentaNotificariDao();
            EvidentaVoluntariatDAO evidentaVoluntariatDAO = instance.evidentaVoluntariatDao();
            SetariNotificariDAO setariNotificariDAO = instance.setariNotificariDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }
}
