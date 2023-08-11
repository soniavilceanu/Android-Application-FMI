package com.example.myapplicationfmi.DAO;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.myapplicationfmi.beans.Student;
import com.example.myapplicationfmi.beans.StudentWithNotifications;
import com.example.myapplicationfmi.beans.Subject;
import com.example.myapplicationfmi.beans.SubjectWithCourses;
import com.example.myapplicationfmi.beans.SubjectWithProfessors;

import java.util.List;
@Dao
public interface SubjectDAO {
    @Insert
    long insertSubject(Subject subject);

    @Update
    void updateSubject(Subject subject);

    @Delete
    void deleteSubject(Subject subject);

    @Query("SELECT * FROM Subjects")
    LiveData<List<Subject>> getAllSubjects();
    @Query("DELETE FROM Subjects")
    void deleteAllSubjects();

    @Transaction
    @Query("SELECT * FROM Subjects")
    List<SubjectWithProfessors> getSubjectsWithProfessors();

    @Transaction
    @Query("SELECT * FROM Subjects WHERE subjectId = :subjectId")
    LiveData<SubjectWithProfessors> getSubjectWithProfessorsById(long subjectId);


    @Query("SELECT subjectId FROM Subjects WHERE denumire = :denumire")
    LiveData<Long> getSubjectIdByDenumire(String denumire);

    @Transaction
    @Query("SELECT * FROM Subjects WHERE subjectId = :subjectId")
    SubjectWithCourses getSubjectWithCourses(long subjectId);

    @Query("SELECT * FROM Subjects WHERE subjectId = :subjectId")
    LiveData<Subject> getSubjectById(long subjectId);

    @Query("DELETE FROM Subjects WHERE subjectId = :subjectId")
    void deleteSubjectById(long subjectId);

}

