package com.example.myapplicationfmi.DAO;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplicationfmi.beans.EvidentaNotificari;
import com.example.myapplicationfmi.beans.Group;
import com.example.myapplicationfmi.beans.Student;
import com.example.myapplicationfmi.beans.Subject;

import java.util.List;
@Dao
public interface EvidentaNotificariDAO {
    @Insert
    long insertEvidentaNotificari(EvidentaNotificari evidentaNotificari);

    @Update
    void updateEvidentaNotificari(EvidentaNotificari evidentaNotificari);

    @Delete
    void deleteEvidentaNotificari(EvidentaNotificari evidentaNotificari);

    @Query("DELETE FROM EvidentaNotificaris")
    void deleteAllEvidentaNotificaris();

    @Query("SELECT * FROM EvidentaNotificaris")
    LiveData<List<EvidentaNotificari>> getAllEvidentaNotificaris();

    @Query("SELECT * FROM EvidentaNotificaris WHERE studentId = :studentId")
    LiveData<List<EvidentaNotificari>> getAllEvidentaNotificariByStudentId(Long studentId);

}

