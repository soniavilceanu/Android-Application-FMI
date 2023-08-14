package com.example.myapplicationfmi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.myapplicationfmi.beans.SetariNotificari;

public class SetariNotificariSQLiteHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "RoomDatabase";
    private static final int DATABASE_VERSION = 16;

    public static final String TABLE_NAME = "SetariNotificaris";

    // Column names
    public static final String COLUMN_NAME_ID = "setariNotificariId";
    public static final String COLUMN_NAME_STUDENT_ID = "studentId";
    public static final String COLUMN_NAME_TYPE = "type";
    public static final String COLUMN_NAME_VREANOTIFICARE = "vreaNotificare";

    public SetariNotificariSQLiteHelper(Context context) {
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
    public SetariNotificari getSetariNotificariByStudentIdAndType(long studentId, String type) {
        SQLiteDatabase db = this.getReadableDatabase();
        SetariNotificari setariNotificari = null;

        Cursor cursor = db.query(
                "SetariNotificaris",
                null,
                "studentId = ? AND type = ?",
                new String[]{String.valueOf(studentId), type},
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            // Retrieve data from cursor and create SetariNotificari object
            // SetariNotificari is the bean class you provided

            int columnIndexSetariNotificariId = cursor.getColumnIndex("setariNotificariId");
            int columnIndexStudentId = cursor.getColumnIndex("studentId");
            int columnIndexType = cursor.getColumnIndex("type");
            int columnIndexVreaNotificare = cursor.getColumnIndex("vreaNotificare");

            if (columnIndexSetariNotificariId >= 0 && columnIndexStudentId >= 0 &&
                    columnIndexType >= 0 && columnIndexVreaNotificare >= 0) {

                setariNotificari = new SetariNotificari();
                setariNotificari.setSetariNotificariId(cursor.getLong(columnIndexSetariNotificariId));
                setariNotificari.setStudentId(cursor.getLong(columnIndexStudentId));
                setariNotificari.setType(cursor.getString(columnIndexType));
                setariNotificari.setVreaNotificare(cursor.getInt(columnIndexVreaNotificare) == 1); // Convert integer to boolean
            } else {
                // Handle the case where one or more columns are missing
            }

            cursor.close();
        }


        db.close();
        return setariNotificari;
    }

    // Update SetariNotificari
    public void updateSetariNotificari(SetariNotificari setariNotificari) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("vreaNotificare", setariNotificari.getVreaNotificare());

        db.update(
                "SetariNotificaris",
                values,
                "setariNotificariId = ?",
                new String[]{String.valueOf(setariNotificari.getSetariNotificariId())}
        );

        db.close();
    }

}
