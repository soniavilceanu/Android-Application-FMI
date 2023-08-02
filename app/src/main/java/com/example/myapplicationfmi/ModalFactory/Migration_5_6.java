package com.example.myapplicationfmi.ModalFactory;


import androidx.annotation.NonNull;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

public class Migration_5_6 extends Migration {
    public Migration_5_6() {
        super(5, 6);
    }

    @Override
    public void migrate(@NonNull SupportSQLiteDatabase database) {
        String createTableSql = "CREATE TABLE IF NOT EXISTS `Calendars` (" +
                "`lunaId` INTEGER NOT NULL, " +
                "`saptamana` TEXT NOT NULL, " +
                "`ziuaSaptamanii` INTEGER NOT NULL, " +
                "`eveniment` TEXT, " +
                "`valabilPentru` TEXT, " +
                "PRIMARY KEY(`lunaId`, `saptamana`, `ziuaSaptamanii`)" +
                ")";
        database.execSQL(createTableSql);
    }
}
