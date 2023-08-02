package com.example.myapplicationfmi.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplicationfmi.beans.Calendar;


import java.time.LocalTime;
import java.util.List;
@Dao
public interface CalendarDAO {
    @Insert
    long insertCalendar(Calendar calendar);

    @Update
    void updateCalendar(Calendar calendar);

    @Delete
    void deleteCalendar(Calendar calendar);

    @Query("DELETE FROM Calendars")
    void deleteAllCalendars();

    @Query("SELECT * FROM Calendars")
    LiveData<List<Calendar>> getAllCalendars();

    @Query("SELECT * FROM Calendars WHERE lunaId = :lunaId AND saptamana = :saptamana AND ziuaSaptamanii = :ziuaSaptamanii AND oraInceput IS NULL AND oraFinal IS NULL")
    LiveData<Calendar> getCalendarByLunaIdSaptamanaAndZiuaSaptamaniiForAdmin(long lunaId, String saptamana, int ziuaSaptamanii);

    @Query("SELECT * FROM Calendars WHERE lunaId = :lunaId AND saptamana = :saptamana AND ziuaSaptamanii = :ziuaSaptamanii AND oraInceput = :oraInceput AND oraFinal = :oraFinal")
    LiveData<Calendar> getCalendarByLunaIdSaptamanaAndZiuaSaptamaniiForProfessor(long lunaId, String saptamana, int ziuaSaptamanii, LocalTime oraInceput, LocalTime oraFinal);

    @Query("SELECT * FROM Calendars " + "WHERE lunaId = :lunaId AND saptamana = :saptamana AND ziuaSaptamanii = :ziuaSaptamanii")
    LiveData<List<Calendar>> getCalendarsByLunaIdSaptamanaAndZiuaSaptamanii(long lunaId, String saptamana, int ziuaSaptamanii);

}

