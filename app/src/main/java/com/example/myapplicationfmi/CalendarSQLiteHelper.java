package com.example.myapplicationfmi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.myapplicationfmi.beans.Calendar;

import java.time.LocalTime;

public class CalendarSQLiteHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "RoomDatabase";
    private static final int DATABASE_VERSION = 16;

    public static final String TABLE_NAME = "Calendars";
    public static final String COLUMN_NAME_ID = "calendarId";
    public static final String COLUMN_NAME_LUNA_ID = "lunaId";
    public static final String COLUMN_NAME_SAPTAMANA = "saptamana";
    public static final String COLUMN_NAME_ZIUA_SAPTAMANII = "ziuaSaptamanii";
    public static final String COLUMN_NAME_EVENIMENT = "eveniment";
    public static final String COLUMN_NAME_VALABIL_PENTRU = "valabilPentru";
    public static final String COLUMN_NAME_ORA_INCEPUT = "oraInceput";
    public static final String COLUMN_NAME_ORA_FINAL = "oraFinal";
    public static final String COLUMN_NAME_MATERIE_ID = "materie_id";
    public static final String COLUMN_NAME_PROFESSOR_ID = "professorId";

    public CalendarSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                + COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NAME_LUNA_ID + " INTEGER, "
                + COLUMN_NAME_SAPTAMANA + " TEXT, "
                + COLUMN_NAME_ZIUA_SAPTAMANII + " INTEGER, "
                + COLUMN_NAME_EVENIMENT + " TEXT, "
                + COLUMN_NAME_VALABIL_PENTRU + " TEXT, "
                + COLUMN_NAME_ORA_INCEPUT + " TEXT, "
                + COLUMN_NAME_ORA_FINAL + " TEXT, "
                + COLUMN_NAME_MATERIE_ID + " INTEGER, "
                + COLUMN_NAME_PROFESSOR_ID + " INTEGER"
                + ")";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private long getLongFromCursor(Cursor cursor, String columnName) {
        int columnIndex = cursor.getColumnIndex(columnName);
        if (columnIndex >= 0) {
            return cursor.getLong(columnIndex);
        } else {
            return 0L;
        }
    }

    private String getStringFromCursor(Cursor cursor, String columnName) {
        int columnIndex = cursor.getColumnIndex(columnName);
        if (columnIndex >= 0) {
            return cursor.getString(columnIndex);
        } else {
            return null;
        }
    }

    private int getIntFromCursor(Cursor cursor, String columnName) {
        int columnIndex = cursor.getColumnIndex(columnName);
        if (columnIndex >= 0) {
            return cursor.getInt(columnIndex);
        } else {
            return 0;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Calendar getCalendarById(long calendarId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Calendar calendar = null;

        Cursor cursor = db.query(
                TABLE_NAME,
                null,
                COLUMN_NAME_ID + " = ?",
                new String[]{String.valueOf(calendarId)},
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            calendar = new Calendar();
            calendar.calendarId = getLongFromCursor(cursor, COLUMN_NAME_ID);
            calendar.lunaId = getLongFromCursor(cursor, COLUMN_NAME_LUNA_ID);
            calendar.saptamana = getStringFromCursor(cursor, COLUMN_NAME_SAPTAMANA);
            calendar.ziuaSaptamanii = getIntFromCursor(cursor, COLUMN_NAME_ZIUA_SAPTAMANII);
            calendar.eveniment = getStringFromCursor(cursor, COLUMN_NAME_EVENIMENT);
            calendar.valabilPentru = getStringFromCursor(cursor, COLUMN_NAME_VALABIL_PENTRU);
            calendar.oraInceput = LocalTime.parse(getStringFromCursor(cursor, COLUMN_NAME_ORA_INCEPUT));
            calendar.oraFinal = LocalTime.parse(getStringFromCursor(cursor, COLUMN_NAME_ORA_FINAL));
            calendar.materieId = getLongFromCursor(cursor, COLUMN_NAME_MATERIE_ID);
            calendar.professorId = getLongFromCursor(cursor, COLUMN_NAME_PROFESSOR_ID);
            cursor.close();
        }

        db.close();
        return calendar;
    }


    public void updateCalendar(Calendar calendar) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_LUNA_ID, calendar.lunaId);
        values.put(COLUMN_NAME_SAPTAMANA, calendar.saptamana);
        values.put(COLUMN_NAME_ZIUA_SAPTAMANII, calendar.ziuaSaptamanii);
        values.put(COLUMN_NAME_EVENIMENT, calendar.eveniment);
        values.put(COLUMN_NAME_VALABIL_PENTRU, calendar.valabilPentru);
        values.put(COLUMN_NAME_ORA_INCEPUT, calendar.oraInceput.toString());
        values.put(COLUMN_NAME_ORA_FINAL, calendar.oraFinal.toString());
        values.put(COLUMN_NAME_MATERIE_ID, calendar.materieId);
        values.put(COLUMN_NAME_PROFESSOR_ID, calendar.professorId);

        db.update(
                TABLE_NAME,
                values,
                COLUMN_NAME_ID + " = ?",
                new String[]{String.valueOf(calendar.calendarId)}
        );

        db.close();
    }

    public long insertCalendar(Calendar calendar) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_NAME_LUNA_ID, calendar.lunaId);
        values.put(COLUMN_NAME_SAPTAMANA, calendar.saptamana);
        values.put(COLUMN_NAME_ZIUA_SAPTAMANII, calendar.ziuaSaptamanii);
        values.put(COLUMN_NAME_EVENIMENT, calendar.eveniment);
        values.put(COLUMN_NAME_VALABIL_PENTRU, calendar.valabilPentru);
        values.put(COLUMN_NAME_ORA_INCEPUT, calendar.oraInceput.toString());
        values.put(COLUMN_NAME_ORA_FINAL, calendar.oraFinal.toString());
        values.put(COLUMN_NAME_MATERIE_ID, calendar.materieId);
        values.put(COLUMN_NAME_PROFESSOR_ID, calendar.professorId);

        long newRowId = db.insert(TABLE_NAME, null, values);
        db.close();

        return newRowId;
    }


}

