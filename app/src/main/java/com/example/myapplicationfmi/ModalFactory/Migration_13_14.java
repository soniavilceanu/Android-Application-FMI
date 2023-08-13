package com.example.myapplicationfmi.ModalFactory;

import androidx.annotation.NonNull;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

public class Migration_13_14 extends Migration {
    public Migration_13_14() {
        super(13, 14);
    }

    @Override
    public void migrate(@NonNull SupportSQLiteDatabase database) {
        database.execSQL("CREATE TABLE IF NOT EXISTS `EvidentaVoluntariats` (" +
                "`studentId` INTEGER NOT NULL," +
                "`voluntariatId` INTEGER NOT NULL," +
                "PRIMARY KEY(`studentId`, `voluntariatId`)" +
                ")");
    }
}
