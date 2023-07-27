package com.example.myapplicationfmi.DAO;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.myapplicationfmi.beans.Group;
import com.example.myapplicationfmi.beans.Student;
import com.example.myapplicationfmi.beans.StudentWithNotifications;

import java.util.List;
@Dao
public interface StudentDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertStudent(Student student);

    @Update
    void updateStudent(Student student);

    @Delete
    void deleteStudent(Student student);

    @Query("SELECT * FROM Students")
    LiveData<List<Student>> getAllStudents();

    @Query("DELETE FROM Students")
    void deleteAllStudents();

    @Query("SELECT * FROM Students WHERE studentId = :studentId")
    LiveData<Student> getStudentById(long studentId);

    @Transaction
    @Query("SELECT * FROM Students WHERE studentId = :studentId")
    StudentWithNotifications getStudentWithNotifications(long studentId);

    @Query("SELECT * FROM Students WHERE email = :email")
    LiveData<Student> getStudentByEmail(String email);
}
