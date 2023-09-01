package com.example.myapplicationfmi.Modals;

import android.app.Application;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.myapplicationfmi.Repositories.SetariNotificariRepository;
import com.example.myapplicationfmi.beans.Calendar;
import com.example.myapplicationfmi.beans.Note;
import com.example.myapplicationfmi.beans.SetariNotificari;

import java.util.List;

public class SetariNotificariModal extends AndroidViewModel {
    private SetariNotificariRepository repository;

    private LiveData<List<SetariNotificari>> allsetariNotificaris;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public SetariNotificariModal(@NonNull Application application) {
        super(application);
        repository = new SetariNotificariRepository(application);
        allsetariNotificaris = repository.getAllSetariNotificaris();
    }

//    public void insert(SetariNotificari model) {
//        repository.insert(model);
//    }
    public long insert(SetariNotificari model) {
        return repository.insert(model);
    }
    public void update(SetariNotificari model) {
        repository.update(model);
    }

    public void delete(SetariNotificari model) {
        repository.delete(model);
    }

    public void deleteAllsetariNotificaris() {
        repository.deleteAllsetariNotificaris();
    }

    public LiveData<List<SetariNotificari>> getAllSetariNotificaris() {
        return allsetariNotificaris;
    }
    public LiveData<SetariNotificari> getSetariNotificariBySetariNotificariId(long setariNotificariId){
        return repository.getSetariNotificariBySetariNotificariId(setariNotificariId);
    }
    public LiveData<List<SetariNotificari>> getSetariNotificariByStudentId(long studentId){
        return repository.getSetariNotificariByStudentId(studentId);
    }
    public LiveData<SetariNotificari> getSetariNotificariByStudentIdAndType(long studentId, String type){
        return repository.getSetariNotificariByStudentIdAndType(studentId, type);
    }
}
