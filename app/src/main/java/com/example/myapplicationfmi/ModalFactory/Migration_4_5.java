package com.example.myapplicationfmi.ModalFactory;


import androidx.annotation.NonNull;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

public class Migration_4_5 extends Migration {
    public Migration_4_5() {
        super(4, 5);
    }

    @Override
    public void migrate(@NonNull SupportSQLiteDatabase database) {
        database.execSQL("CREATE TABLE IF NOT EXISTS Courses_new (" +
                "groupId INTEGER NOT NULL, " +
                "professorId INTEGER NOT NULL, " +
                "zi TEXT NOT NULL, " +
                "intervalOrar INTEGER NOT NULL, " +
                "frecventa INTEGER, " +
                "semigrupa INTEGER, " +
                "subject_id INTEGER, " +
                "PRIMARY KEY(groupId, professorId, zi, intervalOrar))");

        database.execSQL("INSERT INTO Courses_new (groupId, professorId, zi, intervalOrar, frecventa, semigrupa, subject_id) " +
                "SELECT groupId, professorId, zi, intervalOrar, frecventa, semigrupa, subject_id FROM Courses");

        database.execSQL("DROP TABLE Courses");

        database.execSQL("ALTER TABLE Courses_new RENAME TO Courses");
    }
}
