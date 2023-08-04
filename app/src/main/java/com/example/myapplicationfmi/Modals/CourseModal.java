package com.example.myapplicationfmi.Modals;

import android.app.Application;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.myapplicationfmi.Repositories.CourseRepository;
import com.example.myapplicationfmi.beans.Course;
import com.example.myapplicationfmi.beans.Group;
import com.example.myapplicationfmi.beans.Subject;

import java.util.List;

public class CourseModal extends AndroidViewModel {


    private CourseRepository repository;

    private LiveData<List<Course>> allcourses;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public CourseModal(@NonNull Application application) {
        super(application);
        repository = new CourseRepository(application);
        allcourses = repository.getAllCourses();
    }

    public void insert(Course model) {
        repository.insert(model);
    }

    public void update(Course model) {
        repository.update(model);
    }

    public void delete(Course model) {
        repository.delete(model);
    }

    public void deleteAllcourses() {
        repository.deleteAllcourses();
    }

    public LiveData<List<Course>> getAllCourses() {
        return allcourses;
    }
    public LiveData<Course> getCourseByGroupIdZiAndIntervalOrar(long groupId, String zi, int intervalOrar){
        return repository.getCourseByGroupIdZiAndIntervalOrar(groupId, zi, intervalOrar);
    }
    public LiveData<List<Long>> getSubjectIdsByGroupIdAndProfessorId(long groupId, long professorId){
        return repository.getSubjectIdsByGroupIdAndProfessorId(groupId,professorId);
    }
    public LiveData<List<Subject>> getSubjectsByGroupIdAndProfessorId(long groupId, long professorId){
        return repository.getSubjectsByGroupIdAndProfessorId(groupId,professorId);
    }
    public LiveData<List<Group>> getGroupsByProfessorId(long professorId){
        return repository.getGroupsByProfessorId(professorId);
    }
}
