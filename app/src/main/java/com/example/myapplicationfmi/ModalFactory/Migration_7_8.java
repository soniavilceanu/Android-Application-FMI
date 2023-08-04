package com.example.myapplicationfmi.ModalFactory;

import androidx.annotation.NonNull;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

public class Migration_7_8 extends Migration {
    public Migration_7_8() {
        super(7, 8);
    }

    @Override
    public void migrate(@NonNull SupportSQLiteDatabase database) {

        database.execSQL("ALTER TABLE Calendars ADD COLUMN professorId INTEGER");

    }
}
