package com.example.myapplicationfmi.ModalFactory;


import androidx.annotation.NonNull;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

public class Migration_10_11 extends Migration {
    public Migration_10_11() {
        super(10, 11);
    }

    @Override
    public void migrate(@NonNull SupportSQLiteDatabase database) {
        database.execSQL("ALTER TABLE `Notes` ADD COLUMN `editat` INTEGER DEFAULT 0");
    }
}
