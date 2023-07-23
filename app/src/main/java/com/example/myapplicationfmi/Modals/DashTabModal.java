package com.example.myapplicationfmi.Modals;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.myapplicationfmi.Repositories.DashTabRepository;
import com.example.myapplicationfmi.beans.DashTab;

import java.util.List;

public class DashTabModal extends AndroidViewModel {
    // creating a new variable for dashTab repository.
    private DashTabRepository repository;

    // below line is to create a variable for live
    // data where all the dashTabs are present.
    private LiveData<List<DashTab>> alldashTabs;

    // constructor for our view modal.
    public DashTabModal(@NonNull Application application) {
        super(application);
        repository = new DashTabRepository(application);
        alldashTabs = repository.getAllDashTabs();
    }

    // below method is use to insert the data to our repository.
    public void insert(DashTab model) {
        repository.insert(model);
    }

    // below line is to update data in our repository.
    public void update(DashTab model) {
        repository.update(model);
    }

    // below line is to delete the data in our repository.
    public void delete(DashTab model) {
        repository.delete(model);
    }

    // below method is to delete all the dashTabs in our list.
    public void deleteAlldashTabs() {
        repository.deleteAlldashTabs();
    }

    // below method is to get all the dashTabs in our list.
    public LiveData<List<DashTab>> getAllDashTabs() {
        return alldashTabs;
    }
}
