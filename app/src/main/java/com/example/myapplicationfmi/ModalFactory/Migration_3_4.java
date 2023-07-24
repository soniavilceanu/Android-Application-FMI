package com.example.myapplicationfmi.ModalFactory;


import androidx.annotation.NonNull;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

public class Migration_3_4 extends Migration {
    public Migration_3_4() {
        super(3, 4);
    }

    @Override
    public void migrate(@NonNull SupportSQLiteDatabase database) {
        database.execSQL("ALTER TABLE Courses ADD COLUMN frecventa INTEGER");
        database.execSQL("ALTER TABLE Courses ADD COLUMN semigrupa INTEGER");
    }
}
