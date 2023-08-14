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
    // creating a new variable for setariNotificari repository.
    private SetariNotificariRepository repository;

    // below line is to create a variable for live
    // data where all the setariNotificaris are present.
    private LiveData<List<SetariNotificari>> allsetariNotificaris;

    // constructor for our view modal.
    @RequiresApi(api = Build.VERSION_CODES.O)
    public SetariNotificariModal(@NonNull Application application) {
        super(application);
        repository = new SetariNotificariRepository(application);
        allsetariNotificaris = repository.getAllSetariNotificaris();
    }

    // below method is use to insert the data to our repository.
//    public void insert(SetariNotificari model) {
//        repository.insert(model);
//    }
    public long insert(SetariNotificari model) {
        return repository.insert(model);
    }
    // below line is to update data in our repository.
    public void update(SetariNotificari model) {
        repository.update(model);
    }

    // below line is to delete the data in our repository.
    public void delete(SetariNotificari model) {
        repository.delete(model);
    }

    // below method is to delete all the setariNotificaris in our list.
    public void deleteAllsetariNotificaris() {
        repository.deleteAllsetariNotificaris();
    }

    // below method is to get all the setariNotificaris in our list.
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
