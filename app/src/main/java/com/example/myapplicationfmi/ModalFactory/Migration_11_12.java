package com.example.myapplicationfmi.ModalFactory;

import androidx.annotation.NonNull;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

public class Migration_11_12 extends Migration {
    public Migration_11_12() {
        super(11, 12);
    }

    @Override
    public void migrate(@NonNull SupportSQLiteDatabase database) {
        database.execSQL("ALTER TABLE `Students` ADD COLUMN `asmi`  INTEGER NOT NULL DEFAULT 0");

        database.execSQL("CREATE TABLE Notifications_temp (" +
                "notificationId INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "time TEXT, " +
                "type TEXT, " +
                "causeId INTEGER)");

        database.execSQL("DROP TABLE IF EXISTS `DashTabs`");

        database.execSQL("DROP TABLE Notifications");

        database.execSQL("ALTER TABLE Notifications_temp RENAME TO Notifications");

    }
}

