package com.example.myapplicationfmi.ModalFactory;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplicationfmi.Modals.StudentModal;

public class StudentModalFactory implements ViewModelProvider.Factory {
    private Application application;

    public StudentModalFactory(Application application) {
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(StudentModal.class)) {
            return (T) new StudentModal(application);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
