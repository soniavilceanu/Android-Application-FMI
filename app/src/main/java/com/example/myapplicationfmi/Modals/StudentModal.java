package com.example.myapplicationfmi.Modals;

import android.app.Application;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.myapplicationfmi.Repositories.StudentRepository;
import com.example.myapplicationfmi.beans.Calendar;
import com.example.myapplicationfmi.beans.Student;

import java.util.List;

public class StudentModal extends AndroidViewModel {
    private StudentRepository repository;

    private LiveData<List<Student>> allstudents;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public StudentModal(@NonNull Application application) {
        super(application);
        repository = new StudentRepository(application);
        allstudents = repository.getAllStudents();
    }

//    public void insert(Student model) {
//        repository.insert(model);
//    }
    public long insert(Student model) {
        return repository.insert(model);
    }
    public void update(Student model) {
        repository.update(model);
    }

    public void delete(Student model) {
        repository.delete(model);
    }

    public void deleteAllstudents() {
        repository.deleteAllstudents();
    }

    public LiveData<Student> getStudentById(long studentId) {
        return repository.getStudentById(studentId);
    }
    public LiveData<List<Student>> getAllStudents() {
        return allstudents;
    }

    public LiveData<Student> getStudentByEmail(String email){
        return repository.getStudentByEmail(email);
    }
}
