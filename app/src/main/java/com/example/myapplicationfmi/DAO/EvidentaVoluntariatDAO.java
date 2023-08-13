package com.example.myapplicationfmi.DAO;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplicationfmi.beans.EvidentaVoluntariat;
import com.example.myapplicationfmi.beans.Group;
import com.example.myapplicationfmi.beans.Student;
import com.example.myapplicationfmi.beans.Subject;

import java.util.List;
@Dao
public interface EvidentaVoluntariatDAO {
    @Insert
    long insertEvidentaVoluntariat(EvidentaVoluntariat evidentaVoluntariat);

    @Update
    void updateEvidentaVoluntariat(EvidentaVoluntariat evidentaVoluntariat);

    @Delete
    void deleteEvidentaVoluntariat(EvidentaVoluntariat evidentaVoluntariat);

    @Query("DELETE FROM EvidentaVoluntariats")
    void deleteAllEvidentaVoluntariats();

    @Query("SELECT * FROM EvidentaVoluntariats")
    LiveData<List<EvidentaVoluntariat>> getAllEvidentaVoluntariats();

    @Query("SELECT * FROM EvidentaVoluntariats WHERE studentId = :studentId")
    LiveData<List<EvidentaVoluntariat>> getAllEvidentaVoluntariatByStudentId(Long studentId);

    @Query("SELECT * FROM EvidentaVoluntariats WHERE studentId = :studentId AND voluntariatId = :voluntariatId")
    LiveData<EvidentaVoluntariat> getAllEvidentaVoluntariatByStudentIdAndVoluntariatId(Long studentId, Long voluntariatId);

    @Query("SELECT * FROM EvidentaVoluntariats WHERE voluntariatId = :voluntariatId")
    LiveData<List<EvidentaVoluntariat>> getAllEvidentaVoluntariatByVoluntariatId(Long voluntariatId);
}

