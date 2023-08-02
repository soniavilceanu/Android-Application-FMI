package com.example.myapplicationfmi.DAO;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.myapplicationfmi.beans.Professor;
import com.example.myapplicationfmi.beans.ProfessorWithGroups;
import com.example.myapplicationfmi.beans.ProfessorWithSubjects;

import java.util.List;
@Dao
public interface ProfessorDAO {
    @Insert
    long insertProfessor(Professor professor);

    @Update
    void updateProfessor(Professor professor);

    @Delete
    void deleteProfessor(Professor professor);

    @Query("SELECT * FROM Professors")
    LiveData<List<Professor>> getAllProfessors();

    @Query("DELETE FROM Professors")
    void deleteAllProfessors();

    @Transaction
    @Query("SELECT * FROM Professors")
    List<ProfessorWithSubjects> getProfessorsWithSubjects();

    @Transaction
    @Query("SELECT * FROM Professors")
    List<ProfessorWithGroups> getProfessorsWithGroups();

    @Query("SELECT professorId FROM Professors WHERE email = :email")
    LiveData<Long> getProfessorIdByEmail(String email);

    @Query("SELECT * FROM Professors WHERE professorId = :professorId")
    LiveData<Professor> getProfessorById(long professorId);

    @Transaction
    @Query("SELECT * FROM Professors WHERE professorId = :professorId")
    LiveData<ProfessorWithGroups> getProfessorWithGroupsById(long professorId);

}
