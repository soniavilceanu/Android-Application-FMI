package com.example.myapplicationfmi.Modals;

import android.app.Application;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.myapplicationfmi.Repositories.NoteRepository;
import com.example.myapplicationfmi.beans.Calendar;
import com.example.myapplicationfmi.beans.Note;

import java.util.List;

public class NoteModal extends AndroidViewModel {
    private NoteRepository repository;

    private LiveData<List<Note>> allnotes;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public NoteModal(@NonNull Application application) {
        super(application);
        repository = new NoteRepository(application);
        allnotes = repository.getAllNotes();
    }

//    public void insert(Note model) {
//        repository.insert(model);
//    }
    public long insert(Note model) {
        return repository.insert(model);
    }
    public void update(Note model) {
        repository.update(model);
    }

    public void delete(Note model) {
        repository.delete(model);
    }

    public void deleteAllnotes() {
        repository.deleteAllnotes();
    }

    public LiveData<List<Note>> getAllNotes() {
        return allnotes;
    }

    public LiveData<Note> getNoteByStudentAndSubjectIds(long studentId, long subjectId){
        return repository.getNoteByStudentAndSubjectIds(studentId,subjectId);
    }
    public LiveData<List<Note>> getNotesForSubjectAndGroup(long subjectId) {
        return repository.getNotesForSubjectAndGroup(subjectId);
    }
    public LiveData<Note> getNoteByStudentAndSubjectIdAndAnAndSemestru(long studentId, long subjectId, Integer an, Integer semestru){
        return repository.getNoteByStudentAndSubjectIdAndAnAndSemestru(studentId,subjectId,an,semestru);
    }
    public LiveData<Note> getNoteByNoteId(long noteId){
        return repository.getNoteByNoteId(noteId);
    }
}
