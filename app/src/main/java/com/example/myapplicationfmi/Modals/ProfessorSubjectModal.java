package com.example.myapplicationfmi.Modals;

import android.app.Application;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.myapplicationfmi.Repositories.ProfessorSubjectRepository;
import com.example.myapplicationfmi.beans.ProfessorSubject;

import java.util.List;

public class ProfessorSubjectModal extends AndroidViewModel {
    private ProfessorSubjectRepository repository;

    private LiveData<List<ProfessorSubject>> allprofessorSubjects;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ProfessorSubjectModal(@NonNull Application application) {
        super(application);
        repository = new ProfessorSubjectRepository(application);
        allprofessorSubjects = repository.getAllProfessorSubjects();
    }

    public void insert(ProfessorSubject model) {
        repository.insert(model);
    }

    public void update(ProfessorSubject model) {
        repository.update(model);
    }

    public void delete(ProfessorSubject model) {
        repository.delete(model);
    }

    public void deleteAllprofessorSubjects() {
        repository.deleteAllprofessorSubjects();
    }

    public LiveData<List<ProfessorSubject>> getAllProfessorSubjects() {
        return allprofessorSubjects;
    }
}
