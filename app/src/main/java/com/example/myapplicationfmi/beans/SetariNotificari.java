package com.example.myapplicationfmi.beans;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity(tableName = "SetariNotificaris")
public class SetariNotificari {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    public Long setariNotificariId;

    public Long studentId;
    public String type;
    public Boolean vreaNotificare;

    @NonNull
    public Long getSetariNotificariId() {
        return setariNotificariId;
    }

    public void setSetariNotificariId(@NonNull Long setariNotificariId) {
        this.setariNotificariId = setariNotificariId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getVreaNotificare() {
        return vreaNotificare;
    }

    public void setVreaNotificare(Boolean vreaNotificare) {
        this.vreaNotificare = vreaNotificare;
    }


    @Ignore
    public SetariNotificari(@NonNull Long setariNotificariId, Long studentId, String type, Boolean vreaNotificare) {
        this.setariNotificariId = setariNotificariId;
        this.studentId = studentId;
        this.type = type;
        this.vreaNotificare = vreaNotificare;
    }

    public SetariNotificari() {
    }
}
