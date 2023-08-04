package com.example.myapplicationfmi.DAO;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplicationfmi.beans.Course;
import com.example.myapplicationfmi.beans.Group;
import com.example.myapplicationfmi.beans.Student;
import com.example.myapplicationfmi.beans.Subject;

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

    @Query("SELECT subject_id FROM Courses WHERE groupId = :groupId AND professorId = :professorId")
    LiveData<List<Long>> getSubjectIdsByGroupIdAndProfessorId(long groupId, long professorId);

    @Query("SELECT Subjects.* FROM Subjects " +
            "INNER JOIN Courses ON Subjects.subjectId = Courses.subject_id " +
            "WHERE Courses.groupId = :groupId AND Courses.professorId = :professorId")
    LiveData<List<Subject>> getSubjectsByGroupIdAndProfessorId(long groupId, long professorId);

    @Query("SELECT Groups.* FROM Groups " +
            "INNER JOIN Courses ON Groups.groupId = Courses.groupId " +
            "WHERE Courses.professorId = :professorId " +
            "GROUP BY Groups.groupId")
    LiveData<List<Group>> getGroupsByProfessorId(long professorId);

}

