package com.example.myapplicationfmi.Modals;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.myapplicationfmi.Repositories.CourseRepository;
import com.example.myapplicationfmi.beans.Course;

import java.util.List;

public class CourseModal extends AndroidViewModel {
    // creating a new variable for course repository.
    private CourseRepository repository;

    // below line is to create a variable for live
    // data where all the courses are present.
    private LiveData<List<Course>> allcourses;

    // constructor for our view modal.
    public CourseModal(@NonNull Application application) {
        super(application);
        repository = new CourseRepository(application);
        allcourses = repository.getAllCourses();
    }

    // below method is use to insert the data to our repository.
    public void insert(Course model) {
        repository.insert(model);
    }

    // below line is to update data in our repository.
    public void update(Course model) {
        repository.update(model);
    }

    // below line is to delete the data in our repository.
    public void delete(Course model) {
        repository.delete(model);
    }

    // below method is to delete all the courses in our list.
    public void deleteAllcourses() {
        repository.deleteAllcourses();
    }

    // below method is to get all the courses in our list.
    public LiveData<List<Course>> getAllCourses() {
        return allcourses;
    }
    public LiveData<Course> getCourseByGroupIdZiAndIntervalOrar(long groupId, String zi, int intervalOrar){
        return repository.getCourseByGroupIdZiAndIntervalOrar(groupId, zi, intervalOrar);
    }

}
