package com.example.myapplicationfmi.Modals;

import android.app.Application;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.myapplicationfmi.Repositories.EvidentaVoluntariatRepository;
import com.example.myapplicationfmi.beans.Calendar;
import com.example.myapplicationfmi.beans.EvidentaVoluntariat;
import com.example.myapplicationfmi.beans.Group;
import com.example.myapplicationfmi.beans.Subject;

import java.util.List;

public class EvidentaVoluntariatModal extends AndroidViewModel {


    private EvidentaVoluntariatRepository repository;

    private LiveData<List<EvidentaVoluntariat>> allevidentaVoluntariats;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public EvidentaVoluntariatModal(@NonNull Application application) {
        super(application);
        repository = new EvidentaVoluntariatRepository(application);
        allevidentaVoluntariats = repository.getAllEvidentaVoluntariats();
    }

    //    public void insert(EvidentaVoluntariat model) {
//        repository.insert(model);
//    }
    public long insert(EvidentaVoluntariat model) {
        return repository.insert(model);
    }

    public void update(EvidentaVoluntariat model) {
        repository.update(model);
    }

    public void delete(EvidentaVoluntariat model) {
        repository.delete(model);
    }

    public void deleteAllevidentaVoluntariats() {
        repository.deleteAllevidentaVoluntariats();
    }

    public LiveData<List<EvidentaVoluntariat>> getAllEvidentaVoluntariats() {
        return allevidentaVoluntariats;
    }
    public  LiveData<List<EvidentaVoluntariat>> getAllEvidentaVoluntariatByStudentId(Long studentId){
        return repository.getAllEvidentaVoluntariatByStudentId(studentId);
    }
    public LiveData<EvidentaVoluntariat> getAllEvidentaVoluntariatByStudentIdAndVoluntariatId(Long studentId, Long voluntariatId){
        return repository.getAllEvidentaVoluntariatByStudentIdAndVoluntariatId(studentId, voluntariatId);
    }
    public LiveData<List<EvidentaVoluntariat>> getAllEvidentaVoluntariatByVoluntariatId(Long voluntariatId){
        return repository.getAllEvidentaVoluntariatByVoluntariatId(voluntariatId);
    }
}
