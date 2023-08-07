package com.example.myapplicationfmi.DAO;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.myapplicationfmi.beans.Group;
import com.example.myapplicationfmi.beans.GroupWithProfessors;
import com.example.myapplicationfmi.beans.GroupWithStudents;
import com.example.myapplicationfmi.beans.ProfessorWithSubjects;
import com.example.myapplicationfmi.beans.Student;

import java.util.List;
@Dao
public interface GroupDAO {
    @Insert
    long insertGroup(Group group);

    @Update
    void updateGroup(Group group);

    @Delete
    void deleteGroup(Group group);

    @Query("SELECT * FROM Groups")
    LiveData<List<Group>> getAllGroups();


    @Query("DELETE FROM Groups")
    void deleteAllGroups();

    @Transaction
    @Query("SELECT * FROM Groups WHERE groupId = :groupId")
    GroupWithStudents getGroupWithStudents(long groupId);

    @Query("SELECT * FROM Groups WHERE groupId = :groupId")
    LiveData<Group> getGroupById(long groupId);

    @Transaction
    @Query("SELECT * FROM Groups")
    List<GroupWithProfessors> getGroupWithProfessors();

    @Query("SELECT groupId FROM Groups WHERE numar = :numar AND formaDeInvatamant = :formaDeInvatamant")
    LiveData<Long> getGroupIdByNumarAndForma(int numar, String formaDeInvatamant);

    @Transaction
    @Query("SELECT * FROM Groups WHERE groupId = :groupId")
    LiveData<GroupWithStudents> getGroupWithStudentsById(long groupId);
}


