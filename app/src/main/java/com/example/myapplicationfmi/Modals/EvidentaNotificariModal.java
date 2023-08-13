package com.example.myapplicationfmi.Modals;

import android.app.Application;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.myapplicationfmi.Repositories.EvidentaNotificariRepository;
import com.example.myapplicationfmi.beans.Calendar;
import com.example.myapplicationfmi.beans.EvidentaNotificari;
import com.example.myapplicationfmi.beans.Group;
import com.example.myapplicationfmi.beans.Subject;

import java.util.List;

public class EvidentaNotificariModal extends AndroidViewModel {


    private EvidentaNotificariRepository repository;

    private LiveData<List<EvidentaNotificari>> allevidentaNotificaris;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public EvidentaNotificariModal(@NonNull Application application) {
        super(application);
        repository = new EvidentaNotificariRepository(application);
        allevidentaNotificaris = repository.getAllEvidentaNotificaris();
    }

    //    public void insert(EvidentaNotificari model) {
//        repository.insert(model);
//    }
    public long insert(EvidentaNotificari model) {
        return repository.insert(model);
    }

    public void update(EvidentaNotificari model) {
        repository.update(model);
    }

    public void delete(EvidentaNotificari model) {
        repository.delete(model);
    }

    public void deleteAllevidentaNotificaris() {
        repository.deleteAllevidentaNotificaris();
    }

    public LiveData<List<EvidentaNotificari>> getAllEvidentaNotificaris() {
        return allevidentaNotificaris;
    }
    public  LiveData<List<EvidentaNotificari>> getAllEvidentaNotificariByStudentId(Long studentId){
        return repository.getAllEvidentaNotificariByStudentId(studentId);
    }
}
