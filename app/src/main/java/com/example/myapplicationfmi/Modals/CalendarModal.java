package com.example.myapplicationfmi.Modals;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.myapplicationfmi.Repositories.CalendarRepository;
import com.example.myapplicationfmi.beans.Calendar;

import java.time.LocalTime;
import java.util.List;

public class CalendarModal extends AndroidViewModel {


    private CalendarRepository repository;

    private LiveData<List<Calendar>> allcalendars;

    public CalendarModal(@NonNull Application application) {
        super(application);
        repository = new CalendarRepository(application);
        allcalendars = repository.getAllCalendars();
    }

    public void insert(Calendar model) {
        repository.insert(model);
    }

    public void update(Calendar model) {
        repository.update(model);
    }

    public void delete(Calendar model) {
        repository.delete(model);
    }

    public void deleteAllcalendars() {
        repository.deleteAllcalendars();
    }

    public LiveData<List<Calendar>> getAllCalendars() {
        return allcalendars;
    }
    public LiveData<Calendar> getCalendarByLunaIdSaptamanaAndZiuaSaptamaniiForAdmin(long lunaId, String saptamana, int ziuaSaptamanii){
        return repository.getCalendarByLunaIdSaptamanaAndZiuaSaptamaniiForAdmin(lunaId, saptamana, ziuaSaptamanii);
    }
    public LiveData<Calendar> getCalendarByLunaIdSaptamanaAndZiuaSaptamaniiForProfessor(long lunaId, String saptamana, int ziuaSaptamanii, LocalTime oraInceput, LocalTime oraFinal){
        return repository.getCalendarByLunaIdSaptamanaAndZiuaSaptamaniiForProfessor(lunaId,saptamana,ziuaSaptamanii,oraInceput,oraFinal);
    }
    public LiveData<List<Calendar>> getCalendarsByLunaIdSaptamanaAndZiuaSaptamanii(long lunaId, String saptamana, int ziuaSaptamanii){
        return repository.getCalendarsByLunaIdSaptamanaAndZiuaSaptamanii(lunaId,saptamana,ziuaSaptamanii);
    }
}
