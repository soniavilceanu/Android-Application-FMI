package com.example.myapplicationfmi.DAO;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.myapplicationfmi.beans.Note;
import com.example.myapplicationfmi.beans.SetariNotificari;

import java.util.List;

@Dao
public interface SetariNotificariDAO {
    @Insert
    long insertSetariNotificari(SetariNotificari setariNotificari);

    @Update
    void updateSetariNotificari(SetariNotificari setariNotificari);

    @Delete
    void deleteSetariNotificari(SetariNotificari setariNotificari);

    @Query("SELECT * FROM SetariNotificaris")
    LiveData<List<SetariNotificari>> getAllSetariNotificaris();

    @Query("DELETE FROM SetariNotificaris")
    void deleteAllSetariNotificaris();

    @Query("SELECT * FROM SetariNotificaris WHERE setariNotificariId = :setariNotificariId")
    LiveData<SetariNotificari> getSetariNotificariBySetariNotificariId(long setariNotificariId);

    @Query("SELECT * FROM SetariNotificaris WHERE studentId = :studentId")
    LiveData<List<SetariNotificari>> getSetariNotificariByStudentId(long studentId);

    @Query("SELECT * FROM SetariNotificaris WHERE studentId = :studentId AND type = :type")
    LiveData<SetariNotificari> getSetariNotificariByStudentIdAndType(long studentId, String type);
}
