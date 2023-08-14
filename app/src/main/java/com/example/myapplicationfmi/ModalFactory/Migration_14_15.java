package com.example.myapplicationfmi.ModalFactory;
import androidx.annotation.NonNull;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

public class Migration_14_15 extends Migration {
    public Migration_14_15() {
        super(14, 15);
    }

    @Override
    public void migrate(@NonNull SupportSQLiteDatabase database) {
        // Create the new table
        database.execSQL("CREATE TABLE IF NOT EXISTS `SetariNotificaris` (" +
                "`setariNotificariId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "`studentId` INTEGER, " +
                "`type` TEXT, " +
                "`locked` INTEGER, " +
                "`editat` INTEGER)");
    }
}
