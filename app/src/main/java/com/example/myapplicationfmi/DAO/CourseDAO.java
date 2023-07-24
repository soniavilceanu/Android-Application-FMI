package com.example.myapplicationfmi.DAO;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplicationfmi.beans.Course;
import com.example.myapplicationfmi.beans.Student;

import java.util.List;
@Dao
public interface CourseDAO {
    @Insert
    long insertCourse(Course course);

    @Update
    void updateCourse(Course course);

    @Delete
    void deleteCourse(Course course);

    @Query("DELETE FROM Courses")
    void deleteAllCourses();

    @Query("SELECT * FROM Courses")
    LiveData<List<Course>> getAllCourses();

    @Query("SELECT * FROM Courses WHERE groupId = :groupId AND zi = :zi AND intervalOrar = :intervalOrar")
    LiveData<Course> getCourseByGroupIdZiAndIntervalOrar(long groupId, String zi, int intervalOrar);


}

