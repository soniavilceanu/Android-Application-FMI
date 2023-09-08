package com.example.myapplicationfmi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.myapplicationfmi.beans.Course;

public class CoursesSQLiteHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "RoomDatabase";
    private static final int DATABASE_VERSION = 16;

    public static final String TABLE_NAME = "Courses";

    // Column names
    public static final String COLUMN_NAME_GROUP_ID = "groupId";
    public static final String COLUMN_NAME_PROFESSOR_ID = "professorId";
    public static final String COLUMN_NAME_ZI = "zi";
    public static final String COLUMN_NAME_INTERVAL_ORAR = "intervalOrar";
    public static final String COLUMN_NAME_FRECVENTA = "frecventa";
    public static final String COLUMN_NAME_SEMIGRUPA = "semigrupa";
    public static final String COLUMN_NAME_SUBJECT_ID = "subject_id";

    public CoursesSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    // Update the course in the Courses table
    public void updateCourse(Course course) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_SUBJECT_ID, course.getSubjectId());
        values.put(COLUMN_NAME_PROFESSOR_ID, course.getProfessorId());
        values.put(COLUMN_NAME_FRECVENTA, course.getFrecventa());
        values.put(COLUMN_NAME_SEMIGRUPA, course.getSemigrupa());

        db.update(
                TABLE_NAME,
                values,
                COLUMN_NAME_GROUP_ID + " = ? AND " +
                        COLUMN_NAME_ZI + " = ? AND " +
                        COLUMN_NAME_INTERVAL_ORAR + " = ?",
                new String[]{
                        String.valueOf(course.getGroupId()),
                        course.getZi(),
                        String.valueOf(course.getIntervalOrar())
                }
        );

        db.close();
    }
}
