package com.example.myapplicationfmi.DAO;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.myapplicationfmi.beans.Note;

import java.util.List;

@Dao
public interface NoteDAO {
    @Insert
    long insertNote(Note note);

    @Update
    void updateNote(Note note);

    @Delete
    void deleteNote(Note note);

    @Query("SELECT * FROM Notes")
    LiveData<List<Note>> getAllNotes();

    @Query("DELETE FROM Notes")
    void deleteAllNotes();

    @Query("SELECT * FROM Notes WHERE student_id = :studentId AND subject_id = :subjectId")
    LiveData<Note> getNoteByStudentAndSubjectIds(long studentId, long subjectId);

}
