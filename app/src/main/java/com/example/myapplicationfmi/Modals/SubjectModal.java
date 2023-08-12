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
    // creating a new variable for subject repository.
    private SubjectRepository repository;

    // below line is to create a variable for live
    // data where all the subjects are present.
    private LiveData<List<Subject>> allsubjects;

    // constructor for our view modal.
    @RequiresApi(api = Build.VERSION_CODES.O)
    public SubjectModal(@NonNull Application application) {
        super(application);
        repository = new SubjectRepository(application);
        allsubjects = repository.getAllSubjects();
    }

    // below method is use to insert the data to our repository.
//    public void insert(Subject model) {
//        repository.insert(model);
//    }
    public long insert(Subject model) {
        return repository.insert(model);
    }
    // below line is to update data in our repository.
    public void update(Subject model) {
        repository.update(model);
    }

    // below line is to delete the data in our repository.
    public void delete(Subject model) {
        repository.delete(model);
    }

    // below method is to delete all the subjects in our list.
    public void deleteAllsubjects() {
        repository.deleteAllsubjects();
    }

    // below method is to get all the subjects in our list.
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
