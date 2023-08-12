package com.example.myapplicationfmi.Modals;

import android.app.Application;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.myapplicationfmi.Repositories.ProfessorRepository;
import com.example.myapplicationfmi.beans.Calendar;
import com.example.myapplicationfmi.beans.Professor;
import com.example.myapplicationfmi.beans.ProfessorWithGroups;
import com.example.myapplicationfmi.beans.ProfessorWithSubjects;

import java.util.List;

public class ProfessorModal extends AndroidViewModel {
    private ProfessorRepository repository;
    private LiveData<List<Professor>> allprofessors;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ProfessorModal(@NonNull Application application) {
        super(application);
        repository = new ProfessorRepository(application);
        allprofessors = repository.getAllProfessors();
    }

//    public void insert(Professor model) {
//        repository.insert(model);
//    }
public long insert(Professor model) {
    return repository.insert(model);
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
    public LiveData<Professor> getProfessorById(long professorId){
        return repository.getProfessorById(professorId);
    }
    public LiveData<ProfessorWithGroups> getProfessorWithGroupsById(long professorId){
        return repository.getProfessorWithGroupsById(professorId);
    }
    public LiveData<ProfessorWithSubjects> getProfessorWithSubjectsById(Long professorId){
        return repository.getProfessorWithSubjectsById(professorId);
    }
    public LiveData<Professor> getProfessorByEmail(String email){
        return repository.getProfessorByEmail(email);
    }
}
