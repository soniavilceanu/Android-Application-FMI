package com.example.myapplicationfmi.beans;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "Groups")
public class Group {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    public Long groupId;

    public int numar;
    public String serie;
    public String formaDeInvatamant;

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long grupaId) {
        this.groupId = grupaId;
    }

    public int getNumar() {
        return numar;
    }

    public void setNumar(int numar) {
        this.numar = numar;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getFormaDeInvatamant() {
        return formaDeInvatamant;
    }

    public void setFormaDeInvatamant(String formaDeInvatamant) {
        this.formaDeInvatamant = formaDeInvatamant;
    }

    @Ignore
    public Group(int numar, String serie, String formaDeInvatamant) {
        this.numar = numar;
        this.serie = serie;
        this.formaDeInvatamant = formaDeInvatamant;
    }
    public Group(){}
}