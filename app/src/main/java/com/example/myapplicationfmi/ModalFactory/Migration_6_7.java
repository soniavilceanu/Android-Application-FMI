package com.example.myapplicationfmi.ModalFactory;

import androidx.annotation.NonNull;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

public class Migration_6_7 extends Migration {
    public Migration_6_7() {
        super(6, 7);
    }

    @Override
    public void migrate(@NonNull SupportSQLiteDatabase database) {
        database.execSQL("CREATE TABLE IF NOT EXISTS `Calendars_new` (" +
                "`calendarId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "`lunaId` INTEGER NOT NULL, " +
                "`saptamana` TEXT NOT NULL, " +
                "`ziuaSaptamanii` INTEGER NOT NULL, " +
                "`eveniment` TEXT, " +
                "`valabilPentru` TEXT, " +
                "`oraInceput` TEXT, " +
                "`oraFinal` TEXT, " +
                "`materieId` INTEGER" +
                ")");

        database.execSQL("DROP TABLE `Calendars`");

        database.execSQL("ALTER TABLE `Calendars_new` RENAME TO `Calendars`");
    }
}
