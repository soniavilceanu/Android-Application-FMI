package com.example.myapplicationfmi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.myapplicationfmi.beans.Student;

public class StudentsSQLiteHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "RoomDatabase";
    private static final int DATABASE_VERSION = 16;

    public static final String TABLE_NAME = "Students";
    public static final String COLUMN_NAME_STUDENT_ID = "studentId";
    public static final String COLUMN_NAME_NUME = "nume";
    public static final String COLUMN_NAME_PRENUME = "prenume";
    public static final String COLUMN_NAME_EMAIL = "email";
    public static final String COLUMN_NAME_PAROLA = "parola";
    public static final String COLUMN_NAME_AN = "an";
    public static final String COLUMN_NAME_TAXA = "taxa";
    public static final String COLUMN_NAME_BURSA = "bursa";
    public static final String COLUMN_NAME_AN_INSCRIERE = "anInscriere";
    public static final String COLUMN_NAME_TIP_STUDII = "tipStudii";
    public static final String COLUMN_NAME_GRUPA_ID = "grupa_id";
    public static final String COLUMN_NAME_ASMI = "asmi";



    public StudentsSQLiteHelper(Context context) {
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

    public Student getStudentByEmail(String email) {
        SQLiteDatabase db = getReadableDatabase();

        String selection = COLUMN_NAME_EMAIL + " = ?";
        String[] selectionArgs = { email };

        Cursor cursor = db.query(TABLE_NAME, null, selection, selectionArgs, null, null, null);

        Student student = null;
        if (cursor != null && cursor.moveToFirst()) {
            student = new Student();
            student.setStudentId(cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_NAME_STUDENT_ID)));
            student.setNume(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_NUME)));
            student.setPrenume(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_PRENUME)));
            student.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_EMAIL)));
            student.setParola(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_PAROLA)));
            student.setAn(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_NAME_AN)));
            student.setTaxa(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_NAME_TAXA)) == 1);
            student.setBursa(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_NAME_BURSA)) == 1);
            student.setAnInscriere(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_NAME_AN_INSCRIERE)));
            student.setTipStudii(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_TIP_STUDII)));
            student.setGrupaId(cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_NAME_GRUPA_ID)));
            student.setAsmi(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_NAME_ASMI)) == 1);
        }

        if (cursor != null) {
            cursor.close();
        }

        return student;
    }

    public void updateStudentAsmi(Long studentId, boolean asmi) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_ASMI, asmi ? 1 : 0); // Convert boolean to integer

        String selection = COLUMN_NAME_STUDENT_ID + " = ?";
        String[] selectionArgs = { String.valueOf(studentId) };

        db.update(TABLE_NAME, values, selection, selectionArgs);
    }
}
