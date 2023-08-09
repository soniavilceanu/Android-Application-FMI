package com.example.myapplicationfmi.ModalFactory;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.room.TypeConverter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RequiresApi(api = Build.VERSION_CODES.O)
public class LocalDateConverter {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

    @TypeConverter
    public static LocalDate toLocalDate(String value) {
        return value == null ? null : LocalDate.parse(value, formatter);
    }

    @TypeConverter
    public static String fromLocalDate(LocalDate date) {
        return date == null ? null : date.format(formatter);
    }
}
