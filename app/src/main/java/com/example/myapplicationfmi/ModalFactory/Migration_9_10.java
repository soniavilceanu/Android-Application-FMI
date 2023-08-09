package com.example.myapplicationfmi.ModalFactory;


import androidx.annotation.NonNull;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

public class Migration_9_10 extends Migration {
    public Migration_9_10() {
        super(9, 10);
    }

    @Override
    public void migrate(@NonNull SupportSQLiteDatabase database) {
        database.execSQL("ALTER TABLE `Notes` ADD COLUMN `dataContestatie` TEXT");
        database.execSQL("ALTER TABLE `Notes` ADD COLUMN `oraContestatie` TEXT");
        database.execSQL("ALTER TABLE `Notes` ADD COLUMN `locked` INTEGER DEFAULT 0");
    }
}
