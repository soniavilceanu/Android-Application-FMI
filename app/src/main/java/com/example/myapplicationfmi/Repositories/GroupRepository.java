package com.example.myapplicationfmi.Repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.myapplicationfmi.DAO.GroupDAO;
import com.example.myapplicationfmi.MyRoomDatabase;
import com.example.myapplicationfmi.beans.Group;

import java.util.List;

public class GroupRepository {

    // below line is the create a variable
    // for dao and list for all groups.
    private GroupDAO dao;
    private LiveData<List<Group>> allgroups;

    // creating a constructor for our variables
    // and passing the variables to it.
    public GroupRepository(Application application) {
        MyRoomDatabase database = MyRoomDatabase.getInstance(application);
        dao = database.groupDao();
        allgroups = dao.getAllGroups();
    }

    // creating a method to insert the data to our database.
    public void insert(Group model) {
        new InsertgroupAsyncTask(dao).execute(model);
    }

    // creating a method to update data in database.
    public void update(Group model) {
        new UpdategroupAsyncTask(dao).execute(model);
    }

    // creating a method to delete the data in our database.
    public void delete(Group model) {
        new DeletegroupAsyncTask(dao).execute(model);
    }

    // below is the method to delete all the groups.
    public void deleteAllgroups() {
        new DeleteAllgroupsAsyncTask(dao).execute();
    }

    // below method is to read all the groups.
    public LiveData<List<Group>> getAllGroups() {
        return allgroups;
    }

    public LiveData<Group> getGroupById(long groupId) {
        return dao.getGroupById(groupId);
    }

    public LiveData<Long> getGroupIdByNumarAndForma(int numar, String formaDeInvatamant) {
        return dao.getGroupIdByNumarAndForma(numar, formaDeInvatamant);
    }

    // we are creating a async task method to insert new group.
    private static class InsertgroupAsyncTask extends AsyncTask<Group, Void, Void> {
        private GroupDAO dao;

        private InsertgroupAsyncTask(GroupDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Group... model) {
            // below line is use to insert our modal in dao.
            dao.insertGroup(model[0]);
            return null;
        }
    }

    // we are creating a async task method to update our group.
    private static class UpdategroupAsyncTask extends AsyncTask<Group, Void, Void> {
        private GroupDAO dao;

        private UpdategroupAsyncTask(GroupDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Group... models) {
            // below line is use to update
            // our modal in dao.
            dao.updateGroup(models[0]);
            return null;
        }
    }

    // we are creating a async task method to delete group.
    private static class DeletegroupAsyncTask extends AsyncTask<Group, Void, Void> {
        private GroupDAO dao;

        private DeletegroupAsyncTask(GroupDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Group... models) {
            // below line is use to delete
            // our group modal in dao.
            dao.deleteGroup(models[0]);
            return null;
        }
    }

    // we are creating a async task method to delete all groups.
    private static class DeleteAllgroupsAsyncTask extends AsyncTask<Void, Void, Void> {
        private GroupDAO dao;
        private DeleteAllgroupsAsyncTask(GroupDAO dao) {
            this.dao = dao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            // on below line calling method
            // to delete all groups.
            dao.deleteAllGroups();
            return null;
        }
    }
}
