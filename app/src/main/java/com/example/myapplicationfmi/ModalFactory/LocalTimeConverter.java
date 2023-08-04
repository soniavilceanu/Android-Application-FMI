package com.example.myapplicationfmi.ModalFactory;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.room.TypeConverter;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@RequiresApi(api = Build.VERSION_CODES.O)
public class LocalTimeConverter {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_TIME;

    @TypeConverter
    public static LocalTime toLocalTime(String value) {
        return value == null ? null : LocalTime.parse(value, formatter);
    }

    @TypeConverter
    public static String fromLocalTime(LocalTime time) {
        return time == null ? null : time.format(formatter);
    }
}
