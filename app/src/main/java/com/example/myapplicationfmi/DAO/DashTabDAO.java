package com.example.myapplicationfmi.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;
import com.example.myapplicationfmi.beans.DashTab;
import com.example.myapplicationfmi.beans.Student;


@Dao
public interface DashTabDAO {
    @Insert
    long insertDashTab(DashTab dashTab);

    @Update
    void updateDashTab(DashTab dashTab);

    @Delete
    void deleteDashTab(DashTab dashTab);

    @Query("DELETE FROM DashTabs")
    void deleteAllDashTabs();

    @Query("SELECT * FROM DashTabs")
    LiveData<List<DashTab>> getAllDashTabs();
}
