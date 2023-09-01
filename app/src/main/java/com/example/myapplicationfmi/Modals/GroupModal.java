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
    private GroupRepository repository;

    private LiveData<List<Group>> allgroups;

    public GroupModal(@NonNull Application application) {
        super(application);
        repository = new GroupRepository(application);
        allgroups = repository.getAllGroups();
    }

//    public void insert(Group model) {
//        repository.insert(model);
//    }

    public long insert(Group model){
        return repository.insert(model);
    }

    public void update(Group model) {
        repository.update(model);
    }

    public void delete(Group model) {
        repository.delete(model);
    }

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

    public LiveData<List<Group>> getAllGroups() {
        return allgroups;
    }
}
