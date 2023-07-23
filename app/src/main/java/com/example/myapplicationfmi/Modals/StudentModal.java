package com.example.myapplicationfmi.Modals;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.myapplicationfmi.Repositories.StudentRepository;
import com.example.myapplicationfmi.beans.Student;

import java.util.List;

public class StudentModal extends AndroidViewModel {
    // creating a new variable for student repository.
    private StudentRepository repository;

    // below line is to create a variable for live
    // data where all the students are present.
    private LiveData<List<Student>> allstudents;

    // constructor for our view modal.
    public StudentModal(@NonNull Application application) {
        super(application);
        repository = new StudentRepository(application);
        allstudents = repository.getAllStudents();
    }

    // below method is use to insert the data to our repository.
    public void insert(Student model) {
        repository.insert(model);
    }

    // below line is to update data in our repository.
    public void update(Student model) {
        repository.update(model);
    }

    // below line is to delete the data in our repository.
    public void delete(Student model) {
        repository.delete(model);
    }

    // below method is to delete all the students in our list.
    public void deleteAllstudents() {
        repository.deleteAllstudents();
    }

    public LiveData<Student> getStudentById(long studentId) {
        return repository.getStudentById(studentId);
    }
    // below method is to get all the students in our list.
    public LiveData<List<Student>> getAllStudents() {
        return allstudents;
    }
}
