package com.example.myapplicationfmi.beans;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.time.LocalTime;

@Entity(tableName = "Calendars")
public class Calendar {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    public Long calendarId;
    public Long lunaId;
    public String saptamana; //zi
    public Integer ziuaSaptamanii; //intervalOrar
    public String eveniment;
    public String valabilPentru; //o sa fie id-ul grupei daca evenimentul este examen
    public LocalTime oraInceput; // daca evenimentul este examen
    public LocalTime oraFinal; // daca evenimentul este examen

    @ColumnInfo(name = "materie_id")
    public Long materieId; // Foreign key referencing Subjects table  // daca evenimentul este examen

    @NonNull
    public Long getLunaId() {
        return lunaId;
    }

    public void setLunaId(@NonNull Long lunaId) {
        this.lunaId = lunaId;
    }

    @NonNull
    public String getSaptamana() {
        return saptamana;
    }

    public void setSaptamana(@NonNull String saptamana) {
        this.saptamana = saptamana;
    }

    @NonNull
    public Integer getZiuaSaptamanii() {
        return ziuaSaptamanii;
    }

    public void setZiuaSaptamanii(@NonNull Integer ziuaSaptamanii) {
        this.ziuaSaptamanii = ziuaSaptamanii;
    }

    public String getEveniment() {
        return eveniment;
    }

    public void setEveniment(String eveniment) {
        this.eveniment = eveniment;
    }

    public String getValabilPentru() {
        return valabilPentru;
    }

    public void setValabilPentru(String valabilPentru) {
        this.valabilPentru = valabilPentru;
    }

    @NonNull
    public Long getCalendarId() {
        return calendarId;
    }

    public void setCalendarId(@NonNull Long calendarId) {
        this.calendarId = calendarId;
    }

    public LocalTime getOraInceput() {
        return oraInceput;
    }

    public void setOraInceput(LocalTime oraInceput) {
        this.oraInceput = oraInceput;
    }

    public LocalTime getOraFinal() {
        return oraFinal;
    }

    public void setOraFinal(LocalTime oraFinal) {
        this.oraFinal = oraFinal;
    }

    public Long getMaterieId() {
        return materieId;
    }

    public void setMaterieId(Long materieId) {
        this.materieId = materieId;
    }

    @Ignore
    public Calendar(@NonNull Long calendarId, Long lunaId, String saptamana, Integer ziuaSaptamanii, String eveniment, String valabilPentru, LocalTime oraInceput, LocalTime oraFinal, Long materieId) {
        this.calendarId = calendarId;
        this.lunaId = lunaId;
        this.saptamana = saptamana;
        this.ziuaSaptamanii = ziuaSaptamanii;
        this.eveniment = eveniment;
        this.valabilPentru = valabilPentru;
        this.oraInceput = oraInceput;
        this.oraFinal = oraFinal;
        this.materieId = materieId;
    }

    public Calendar(){
    }
}
