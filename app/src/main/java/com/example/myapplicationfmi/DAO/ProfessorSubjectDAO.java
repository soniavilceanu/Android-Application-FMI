
package com.example.myapplicationfmi.DAO;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplicationfmi.beans.Course;
import com.example.myapplicationfmi.beans.ProfessorSubject;

import java.util.List;
@Dao
public interface ProfessorSubjectDAO {
    @Insert
    void insertProfessorSubject(ProfessorSubject professorSubject);

    @Delete
    void deleteProfessorSubject(ProfessorSubject professorSubject);

    @Query("DELETE FROM ProfessorSubjects")
    void deleteAllProfessorSubjects();

    @Update
    void updateProfessorSubject(ProfessorSubject professorSubject);

    @Query("SELECT * FROM ProfessorSubjects")
    LiveData<List<ProfessorSubject>> getAllProfessorSubjects();
}