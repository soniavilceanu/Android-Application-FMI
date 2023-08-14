package com.example.myapplicationfmi.ModalFactory;
import androidx.annotation.NonNull;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

public class Migration_15_16 extends Migration {
    public Migration_15_16() {
        super(15, 16);
    }

    @Override
    public void migrate(@NonNull SupportSQLiteDatabase database) {
        // Create a temporary table with the desired columns
        database.execSQL("CREATE TABLE IF NOT EXISTS `SetariNotificaris_temp` (" +
                "`setariNotificariId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "`studentId` INTEGER, " +
                "`type` TEXT, " +
                "`vreaNotificare` INTEGER)");

        // Copy data from the old table to the temporary table
        database.execSQL("INSERT INTO `SetariNotificaris_temp` (`setariNotificariId`, `studentId`, `type`, `vreaNotificare`) " +
                "SELECT `setariNotificariId`, `studentId`, `type`, CASE WHEN `locked` = 0 THEN 0 ELSE 1 END AS `vreaNotificare` FROM `SetariNotificaris`");

        // Drop the old table
        database.execSQL("DROP TABLE IF EXISTS `SetariNotificaris`");

        // Rename the temporary table to the original table name
        database.execSQL("ALTER TABLE `SetariNotificaris_temp` RENAME TO `SetariNotificaris`");
    }
}
