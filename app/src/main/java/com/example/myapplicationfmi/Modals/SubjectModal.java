package com.example.myapplicationfmi.Modals;

import android.app.Application;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.myapplicationfmi.Repositories.SubjectRepository;
import com.example.myapplicationfmi.beans.Calendar;
import com.example.myapplicationfmi.beans.Subject;
import com.example.myapplicationfmi.beans.SubjectWithProfessors;

import java.util.List;

public class SubjectModal extends AndroidViewModel {
    private SubjectRepository repository;

    private LiveData<List<Subject>> allsubjects;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public SubjectModal(@NonNull Application application) {
        super(application);
        repository = new SubjectRepository(application);
        allsubjects = repository.getAllSubjects();
    }

//    public void insert(Subject model) {
//        repository.insert(model);
//    }
    public long insert(Subject model) {
        return repository.insert(model);
    }
    public void update(Subject model) {
        repository.update(model);
    }

    public void delete(Subject model) {
        repository.delete(model);
    }

    public void deleteAllsubjects() {
        repository.deleteAllsubjects();
    }

    public LiveData<List<Subject>> getAllSubjects() {
        return allsubjects;
    }
    public LiveData<Long> getSubjectIdByDenumire(String denumire) {
        return repository.getSubjectIdByDenumire(denumire);
    }
    public LiveData<SubjectWithProfessors> getSubjectWithProfessorsById(long subjectId){
        return repository.getSubjectWithProfessorsById(subjectId);
    }
    public LiveData<Subject> getSubjectById(long subjectId){
        return repository.getSubjectById(subjectId);
    }
    public void deleteSubjectById(Long subjectId) {
        repository.deleteSubjectById(subjectId);
    }

}
