package com.example.myapplicationfmi.Repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.myapplicationfmi.DAO.DashTabDAO;
import com.example.myapplicationfmi.MyRoomDatabase;
import com.example.myapplicationfmi.beans.DashTab;

import java.util.List;

public class DashTabRepository {

    // below line is the create a variable
    // for dao and list for all dashTabs.
    private DashTabDAO dao;
    private LiveData<List<DashTab>> alldashTabs;

    // creating a constructor for our variables
    // and passing the variables to it.
    public DashTabRepository(Application application) {
        MyRoomDatabase database = MyRoomDatabase.getInstance(application);
        dao = database.dashTabDao();
        alldashTabs = dao.getAllDashTabs();
    }

    // creating a method to insert the data to our database.
    public void insert(DashTab model) {
        new InsertdashTabAsyncTask(dao).execute(model);
    }

    // creating a method to update data in database.
    public void update(DashTab model) {
        new UpdatedashTabAsyncTask(dao).execute(model);
    }

    // creating a method to delete the data in our database.
    public void delete(DashTab model) {
        new DeletedashTabAsyncTask(dao).execute(model);
    }

    // below is the method to delete all the dashTabs.
    public void deleteAlldashTabs() {
        new DeleteAlldashTabsAsyncTask(dao).execute();
    }

    // below method is to read all the dashTabs.
    public LiveData<List<DashTab>> getAllDashTabs() {
        return alldashTabs;
    }

    // we are creating a async task method to insert new dashTab.
    private static class InsertdashTabAsyncTask extends AsyncTask<DashTab, Void, Void> {
        private DashTabDAO dao;

        private InsertdashTabAsyncTask(DashTabDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(DashTab... model) {
            // below line is use to insert our modal in dao.
            dao.insertDashTab(model[0]);
            return null;
        }
    }

    // we are creating a async task method to update our dashTab.
    private static class UpdatedashTabAsyncTask extends AsyncTask<DashTab, Void, Void> {
        private DashTabDAO dao;

        private UpdatedashTabAsyncTask(DashTabDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(DashTab... models) {
            // below line is use to update
            // our modal in dao.
            dao.updateDashTab(models[0]);
            return null;
        }
    }

    // we are creating a async task method to delete dashTab.
    private static class DeletedashTabAsyncTask extends AsyncTask<DashTab, Void, Void> {
        private DashTabDAO dao;

        private DeletedashTabAsyncTask(DashTabDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(DashTab... models) {
            // below line is use to delete
            // our dashTab modal in dao.
            dao.deleteDashTab(models[0]);
            return null;
        }
    }

    // we are creating a async task method to delete all dashTabs.
    private static class DeleteAlldashTabsAsyncTask extends AsyncTask<Void, Void, Void> {
        private DashTabDAO dao;
        private DeleteAlldashTabsAsyncTask(DashTabDAO dao) {
            this.dao = dao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            // on below line calling method
            // to delete all dashTabs.
            dao.deleteAllDashTabs();
            return null;
        }
    }
}
