package com.example.myapplicationfmi.ModalFactory;

import androidx.annotation.NonNull;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

public class Migration_12_13 extends Migration {
    public Migration_12_13() {
        super(12, 13);
    }

    @Override
    public void migrate(@NonNull SupportSQLiteDatabase database) {
        database.execSQL("CREATE TABLE IF NOT EXISTS `EvidentaNotificaris` (" +
                "`studentId` INTEGER NOT NULL," +
                "`notificationId` INTEGER NOT NULL," +
                "PRIMARY KEY(`studentId`, `notificationId`)" +
                ")");
    }
}
