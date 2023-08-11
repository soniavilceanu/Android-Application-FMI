package com.example.myapplicationfmi.Repositories;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;

import com.example.myapplicationfmi.DAO.NoteDAO;
import com.example.myapplicationfmi.MyRoomDatabase;
import com.example.myapplicationfmi.beans.Note;

import java.util.List;

public class NoteRepository {

    // below line is the create a variable
    // for dao and list for all notes.
    private NoteDAO dao;
    private LiveData<List<Note>> allnotes;

    // creating a constructor for our variables
    // and passing the variables to it.
    @RequiresApi(api = Build.VERSION_CODES.O)
    public NoteRepository(Application application) {
        MyRoomDatabase database = MyRoomDatabase.getInstance(application);
        dao = database.noteDao();
        allnotes = dao.getAllNotes();
    }

    // creating a method to insert the data to our database.
    public void insert(Note model) {
        new InsertnoteAsyncTask(dao).execute(model);
    }

    // creating a method to update data in database.
    public void update(Note model) {
        new UpdatenoteAsyncTask(dao).execute(model);
    }

    // creating a method to delete the data in our database.
    public void delete(Note model) {
        new DeletenoteAsyncTask(dao).execute(model);
    }

    // below is the method to delete all the notes.
    public void deleteAllnotes() {
        new DeleteAllnotesAsyncTask(dao).execute();
    }

    // below method is to read all the notes.
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


    // we are creating a async task method to insert new note.
    private static class InsertnoteAsyncTask extends AsyncTask<Note, Void, Void> {
        private NoteDAO dao;

        private InsertnoteAsyncTask(NoteDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Note... model) {
            // below line is use to insert our modal in dao.
            dao.insertNote(model[0]);
            return null;
        }
    }

    // we are creating a async task method to update our note.
    private static class UpdatenoteAsyncTask extends AsyncTask<Note, Void, Void> {
        private NoteDAO dao;

        private UpdatenoteAsyncTask(NoteDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Note... models) {
            // below line is use to update
            // our modal in dao.
            dao.updateNote(models[0]);
            return null;
        }
    }

    // we are creating a async task method to delete note.
    private static class DeletenoteAsyncTask extends AsyncTask<Note, Void, Void> {
        private NoteDAO dao;

        private DeletenoteAsyncTask(NoteDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Note... models) {
            // below line is use to delete
            // our note modal in dao.
            dao.deleteNote(models[0]);
            return null;
        }
    }

    // we are creating a async task method to delete all notes.
    private static class DeleteAllnotesAsyncTask extends AsyncTask<Void, Void, Void> {
        private NoteDAO dao;
        private DeleteAllnotesAsyncTask(NoteDAO dao) {
            this.dao = dao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            // on below line calling method
            // to delete all notes.
            dao.deleteAllNotes();
            return null;
        }
    }
}
