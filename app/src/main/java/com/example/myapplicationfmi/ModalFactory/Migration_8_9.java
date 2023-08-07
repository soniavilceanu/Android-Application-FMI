package com.example.myapplicationfmi.ModalFactory;


import androidx.annotation.NonNull;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

public class Migration_8_9 extends Migration {
    public Migration_8_9() {
        super(8, 9);
    }

    @Override
    public void migrate(@NonNull SupportSQLiteDatabase database) {
        database.execSQL("CREATE TABLE IF NOT EXISTS `Notes` (`noteId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "`an` INTEGER, " +
                "`semestru` INTEGER, " +
                "`valoare` TEXT, " +
                "`subject_id` INTEGER, " +
                "`student_id` INTEGER)");

    }
}
