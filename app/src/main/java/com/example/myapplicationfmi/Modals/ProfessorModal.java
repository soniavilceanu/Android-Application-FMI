package com.example.myapplicationfmi.Modals;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.myapplicationfmi.Repositories.ProfessorRepository;
import com.example.myapplicationfmi.beans.Professor;

import java.util.List;

public class ProfessorModal extends AndroidViewModel {
    private ProfessorRepository repository;
    private LiveData<List<Professor>> allprofessors;

    public ProfessorModal(@NonNull Application application) {
        super(application);
        repository = new ProfessorRepository(application);
        allprofessors = repository.getAllProfessors();
    }

    public void insert(Professor model) {
        repository.insert(model);
    }

    public void update(Professor model) {
        repository.update(model);
    }

    public void delete(Professor model) {
        repository.delete(model);
    }

    public void deleteAllprofessors() {
        repository.deleteAllprofessors();
    }

    public LiveData<List<Professor>> getAllProfessors() {
        return allprofessors;
    }
    public LiveData<Long> getProfessorIdByEmail(String email) {
        return repository.getProfessorIdByEmail(email);
    }

}
