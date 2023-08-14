package com.example.myapplicationfmi;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class NotesSQLiteHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "RoomDatabase";
    private static final int DATABASE_VERSION = 16;

    public static final String TABLE_NAME = "Notes";

    // Column names
    public static final String COLUMN_NAME_ID = "noteId";
    public static final String COLUMN_NAME_STUDENT_ID = "student_id";
    public static final String COLUMN_NAME_SUBJECT_ID = "subject_id";
    public static final String COLUMN_NAME_VALUE = "valoare";
    public static final String COLUMN_NAME_LOCKED = "locked";
    public static final String COLUMN_NAME_EDITED = "editat";
    public static final String COLUMN_NAME_DATA_CONTESTATIE = "dataContestatie";
    public static final String COLUMN_NAME_ORA_CONTESTATIE = "oraContestatie";


    public NotesSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // You don't need to create tables here, as Room has already created them
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database upgrades if needed
    }

    // Add methods to query or update the Notes table using raw SQL queries
    // ...
}
