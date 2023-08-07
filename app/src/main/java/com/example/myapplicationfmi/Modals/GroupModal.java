package com.example.myapplicationfmi.Modals;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.myapplicationfmi.Repositories.GroupRepository;
import com.example.myapplicationfmi.beans.Group;
import com.example.myapplicationfmi.beans.GroupWithStudents;

import java.util.List;

public class GroupModal extends AndroidViewModel {
    // creating a new variable for group repository.
    private GroupRepository repository;

    // below line is to create a variable for live
    // data where all the groups are present.
    private LiveData<List<Group>> allgroups;

    // constructor for our view modal.
    public GroupModal(@NonNull Application application) {
        super(application);
        repository = new GroupRepository(application);
        allgroups = repository.getAllGroups();
    }

    // below method is use to insert the data to our repository.
    public void insert(Group model) {
        repository.insert(model);
    }

    // below line is to update data in our repository.
    public void update(Group model) {
        repository.update(model);
    }

    // below line is to delete the data in our repository.
    public void delete(Group model) {
        repository.delete(model);
    }

    // below method is to delete all the groups in our list.
    public void deleteAllgroups() {
        repository.deleteAllgroups();
    }
    public LiveData<Group> getGroupById(long groupId) {
        return repository.getGroupById(groupId);
    }

    public LiveData<Long> getGroupIdByNumarAndForma(int numar, String formaDeInvatamant) {
        return repository.getGroupIdByNumarAndForma(numar, formaDeInvatamant);
    }

    public LiveData<GroupWithStudents> getGroupWithStudentsById(long groupId){
        return repository.getGroupWithStudentsById(groupId);
    }

    // below method is to get all the groups in our list.
    public LiveData<List<Group>> getAllGroups() {
        return allgroups;
    }
}
