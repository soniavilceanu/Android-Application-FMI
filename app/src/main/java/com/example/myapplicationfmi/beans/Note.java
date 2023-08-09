package com.example.myapplicationfmi.beans;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity(tableName = "Notes")
public class Note {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    public Long noteId;

    public Integer an;
    public Integer semestru;
    public String valoare; //nota sau admis/respins

    @ColumnInfo(name = "subject_id")
    public Long subjectId;

    @ColumnInfo(name = "student_id")
    public Long studentId; // Foreign key referencing Students table
    public LocalDate dataContestatie;
    public LocalTime oraContestatie;
    public Boolean locked;
    public Boolean editat;

    @NonNull
    public Long getNoteId() {
        return noteId;
    }

    public void setNoteId(@NonNull Long noteId) {
        this.noteId = noteId;
    }

    public Integer getAn() {
        return an;
    }

    public void setAn(Integer an) {
        this.an = an;
    }

    public Integer getSemestru() {
        return semestru;
    }

    public void setSemestru(Integer semestru) {
        this.semestru = semestru;
    }

    public String getValoare() {
        return valoare;
    }

    public void setValoare(String valoare) {
        this.valoare = valoare;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public LocalDate getDataContestatie() {
        return dataContestatie;
    }

    public void setDataContestatie(LocalDate dataContestatie) {
        this.dataContestatie = dataContestatie;
    }

    public LocalTime getOraContestatie() {
        return oraContestatie;
    }

    public void setOraContestatie(LocalTime oraContestatie) {
        this.oraContestatie = oraContestatie;
    }

    public Boolean isLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public Boolean isEditat() {
        return editat;
    }

    public void setEditat(Boolean editat) {
        this.editat = editat;
    }

    @Ignore
    public Note(@NonNull Long noteId, Integer an, Integer semestru, String valoare, Long subjectId, Long studentId, LocalDate dataContestatie, LocalTime oraContestatie, boolean locked) {
        this.noteId = noteId;
        this.an = an;
        this.semestru = semestru;
        this.valoare = valoare;
        this.subjectId = subjectId;
        this.studentId = studentId;
        this.dataContestatie = dataContestatie;
        this.oraContestatie = oraContestatie;
        this.locked = locked;
    }
    public Note() {
    }
}
