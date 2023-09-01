package com.example.myapplicationfmi.Repositories;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;

import com.example.myapplicationfmi.DAO.GroupDAO;
import com.example.myapplicationfmi.MyRoomDatabase;
import com.example.myapplicationfmi.beans.Course;
import com.example.myapplicationfmi.beans.Group;
import com.example.myapplicationfmi.beans.GroupWithStudents;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class GroupRepository {

    private GroupDAO dao;
    private LiveData<List<Group>> allgroups;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public GroupRepository(Application application) {
        MyRoomDatabase database = MyRoomDatabase.getInstance(application);
        dao = database.groupDao();
        allgroups = dao.getAllGroups();
    }

//    public void insert(Group model) {
//        new InsertgroupAsyncTask(dao).execute(model);
//    }
    public long insert(Group model) {
        try {
            return new GroupRepository.InsertgroupAsyncTask(dao).execute(model).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public void update(Group model) {
        new UpdategroupAsyncTask(dao).execute(model);
    }

    public void delete(Group model) {
        new DeletegroupAsyncTask(dao).execute(model);
    }

    public void deleteAllgroups() {
        new DeleteAllgroupsAsyncTask(dao).execute();
    }

    public LiveData<List<Group>> getAllGroups() {
        return allgroups;
    }

    public LiveData<Group> getGroupById(long groupId) {
        return dao.getGroupById(groupId);
    }

    public LiveData<Long> getGroupIdByNumarAndForma(int numar, String formaDeInvatamant) {
        return dao.getGroupIdByNumarAndForma(numar, formaDeInvatamant);
    }
    public LiveData<GroupWithStudents> getGroupWithStudentsById(long groupId){
        return dao.getGroupWithStudentsById(groupId);
    }

    private static class InsertgroupAsyncTask extends AsyncTask<Group, Void, Long> {
        private GroupDAO dao;

        private InsertgroupAsyncTask(GroupDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Long doInBackground(Group... model) {
            return dao.insertGroup(model[0]);
        }
    }

    private static class UpdategroupAsyncTask extends AsyncTask<Group, Void, Void> {
        private GroupDAO dao;

        private UpdategroupAsyncTask(GroupDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Group... models) {
            dao.updateGroup(models[0]);
            return null;
        }
    }

    private static class DeletegroupAsyncTask extends AsyncTask<Group, Void, Void> {
        private GroupDAO dao;

        private DeletegroupAsyncTask(GroupDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Group... models) {
            dao.deleteGroup(models[0]);
            return null;
        }
    }

    private static class DeleteAllgroupsAsyncTask extends AsyncTask<Void, Void, Void> {
        private GroupDAO dao;
        private DeleteAllgroupsAsyncTask(GroupDAO dao) {
            this.dao = dao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            dao.deleteAllGroups();
            return null;
        }
    }
}
