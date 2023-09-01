package com.example.myapplicationfmi.Repositories;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;

import com.example.myapplicationfmi.DAO.NoteDAO;
import com.example.myapplicationfmi.MyRoomDatabase;
import com.example.myapplicationfmi.beans.Course;
import com.example.myapplicationfmi.beans.Note;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class NoteRepository {

    private NoteDAO dao;
    private LiveData<List<Note>> allnotes;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public NoteRepository(Application application) {
        MyRoomDatabase database = MyRoomDatabase.getInstance(application);
        dao = database.noteDao();
        allnotes = dao.getAllNotes();
    }

//    public void insert(Note model) {
//        new InsertnoteAsyncTask(dao).execute(model);
//    }
    public long insert(Note model) {
        try {
            return new NoteRepository.InsertnoteAsyncTask(dao).execute(model).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return -1;
        }
    }
    public void update(Note model) {
        new UpdatenoteAsyncTask(dao).execute(model);
    }

    public void delete(Note model) {
        new DeletenoteAsyncTask(dao).execute(model);
    }

    public void deleteAllnotes() {
        new DeleteAllnotesAsyncTask(dao).execute();
    }

    public LiveData<List<Note>> getAllNotes() {
        return allnotes;
    }

    public LiveData<Note> getNoteByStudentAndSubjectIds(long studentId, long subjectId){
        return dao.getNoteByStudentAndSubjectIds(studentId,subjectId);
    }

    public LiveData<List<Note>> getNotesForSubjectAndGroup(long subjectId) {
        return dao.getNotesForSubjectAndGroup(subjectId);
    }

    public LiveData<Note> getNoteByStudentAndSubjectIdAndAnAndSemestru(long studentId, long subjectId, Integer an, Integer semestru){
        return dao.getNoteByStudentAndSubjectIdAndAnAndSemestru(studentId,subjectId,an,semestru);
    }

    public LiveData<Note> getNoteByNoteId(long noteId){
        return dao.getNoteByNoteId(noteId);
    }

    private static class InsertnoteAsyncTask extends AsyncTask<Note, Void, Long> {
        private NoteDAO dao;

        private InsertnoteAsyncTask(NoteDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Long doInBackground(Note... model) {
            // below line is use to insert our modal in dao.
            return dao.insertNote(model[0]);
        }
    }

    private static class UpdatenoteAsyncTask extends AsyncTask<Note, Void, Void> {
        private NoteDAO dao;

        private UpdatenoteAsyncTask(NoteDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Note... models) {
            dao.updateNote(models[0]);
            return null;
        }
    }

    private static class DeletenoteAsyncTask extends AsyncTask<Note, Void, Void> {
        private NoteDAO dao;

        private DeletenoteAsyncTask(NoteDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Note... models) {
            dao.deleteNote(models[0]);
            return null;
        }
    }

    private static class DeleteAllnotesAsyncTask extends AsyncTask<Void, Void, Void> {
        private NoteDAO dao;
        private DeleteAllnotesAsyncTask(NoteDAO dao) {
            this.dao = dao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            dao.deleteAllNotes();
            return null;
        }
    }
}
