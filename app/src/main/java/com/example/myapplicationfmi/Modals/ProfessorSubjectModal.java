package com.example.myapplicationfmi.Modals;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.myapplicationfmi.Repositories.ProfessorSubjectRepository;
import com.example.myapplicationfmi.beans.ProfessorSubject;

import java.util.List;

public class ProfessorSubjectModal extends AndroidViewModel {
    // creating a new variable for professorSubject repository.
    private ProfessorSubjectRepository repository;

    // below line is to create a variable for live
    // data where all the professorSubjects are present.
    private LiveData<List<ProfessorSubject>> allprofessorSubjects;

    // constructor for our view modal.
    public ProfessorSubjectModal(@NonNull Application application) {
        super(application);
        repository = new ProfessorSubjectRepository(application);
        allprofessorSubjects = repository.getAllProfessorSubjects();
    }

    // below method is use to insert the data to our repository.
    public void insert(ProfessorSubject model) {
        repository.insert(model);
    }

    // below line is to update data in our repository.
    public void update(ProfessorSubject model) {
        repository.update(model);
    }

    // below line is to delete the data in our repository.
    public void delete(ProfessorSubject model) {
        repository.delete(model);
    }

    // below method is to delete all the professorSubjects in our list.
    public void deleteAllprofessorSubjects() {
        repository.deleteAllprofessorSubjects();
    }

    // below method is to get all the professorSubjects in our list.
    public LiveData<List<ProfessorSubject>> getAllProfessorSubjects() {
        return allprofessorSubjects;
    }
}
