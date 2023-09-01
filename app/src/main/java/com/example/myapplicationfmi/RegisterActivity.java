package com.example.myapplicationfmi;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplicationfmi.Modals.CourseModal;
import com.example.myapplicationfmi.Modals.GroupModal;
import com.example.myapplicationfmi.Modals.NotificationModal;
import com.example.myapplicationfmi.Modals.ProfessorModal;
import com.example.myapplicationfmi.Modals.ProfessorSubjectModal;
import com.example.myapplicationfmi.Modals.SetariNotificariModal;
import com.example.myapplicationfmi.Modals.StudentModal;
import com.example.myapplicationfmi.Modals.SubjectModal;
import com.example.myapplicationfmi.beans.Group;
import com.example.myapplicationfmi.beans.Professor;
import com.example.myapplicationfmi.beans.ProfessorSubject;
import com.example.myapplicationfmi.beans.SetariNotificari;
import com.example.myapplicationfmi.beans.Student;
import com.example.myapplicationfmi.beans.Subject;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {
    EditText Email, Password, Nume, Prenume, editStudentAnIncepere;
    Button Register;
    String NumeHolder, PrenumeHolder, EmailHolder, PasswordHolder, editStudentAnIncepereHolder;
    Boolean EditTextEmptyHolder;
    SQLiteDatabase sqLiteDatabaseObj;
    String SQLiteDataBaseQueryHolder;
    SQLiteHelper sqLiteHelper;
    Cursor cursor;
    String F_Result = "Not_Found";
    Spinner spinnerTipCont, spinnerStudentAn, spinnerStudentSerie, spinnerStudentGrupa, spinnerProfesorPozitie;
//    Spinner spinnerStudentProfil;
    CheckBox checkboxLicenta, checkboxMaster, checkboxIF, checkboxIFR, checkboxID, checkboxTaxa, checkboxBursa;
    LinearLayout layoutStudent, layoutProfesor;
    MaterialCardView materialCardProfesorDisciplina, materialCardProfesorGrupe;
    TextView profDisciplineTextView;
    boolean [] disciplineSelectate;
    ArrayList<Integer> listDiscipline = new ArrayList<>();
    String[] discipline;
    TextView profGrupeTextView;
    boolean [] grupeSelectate;
    ArrayList<Integer> listGrupe = new ArrayList<>();
    String[] grupe;

    private MyRoomDatabase myRoomDatabase;
    private StudentModal studentModal;
    private GroupModal groupModal;
    private CourseModal courseModal;
    private NotificationModal notificationModal;
    private ProfessorModal professorModal;
    private SubjectModal subjectModal;
    private ProfessorSubjectModal professorSubjectModal;
    private SetariNotificariModal setariNotificariModal;
    private String[] licentaGrupaSerie13Items;
    private String[] licentaGrupaSerie14Items;
    private String[] licentaGrupaSerie23Items;
    private String[] licentaGrupaSerie24Items;
    private String[] licentaGrupaSerie33Items;
    private String[] licentaGrupaSerie34Items;
    private String[] mastergrupa1Items;
    private String[] mastergrupa2Items;
    private String[] licentaIDItems;
    private String[] masterIFRItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            myRoomDatabase = MyRoomDatabase.getInstance(this);
        }
        studentModal = new ViewModelProvider(this).get(StudentModal.class);
        groupModal = new ViewModelProvider(this).get(GroupModal.class);
        courseModal = new ViewModelProvider(this).get(CourseModal.class);
        notificationModal = new ViewModelProvider(this).get(NotificationModal.class);
        professorModal = new ViewModelProvider(this).get(ProfessorModal.class);
        subjectModal = new ViewModelProvider(this).get(SubjectModal.class);
        professorSubjectModal = new ViewModelProvider(this).get(ProfessorSubjectModal.class);
        setariNotificariModal = new ViewModelProvider(this).get(SetariNotificariModal.class);

        groupModal.getAllGroups().observe(this, new Observer<List<Group>>() {
            @Override
            public void onChanged(List<Group> groups) {
                if(groups != null) {
                    grupe = groups.stream()
                            .map(group -> String.valueOf(group.getNumar()))
                            .toArray(String[]::new);

                    int nr = 0;
                    for(int i = 0; i < grupe.length; i ++){
                        if((grupe[i].equals("1") || grupe[i].equals("2")) && nr < 2) {
                            grupe[i] = grupe[i] + " ID";
                            nr ++;
                        }
                        else if(grupe[i].equals("1") || grupe[i].equals("2"))  grupe[i] = grupe[i] + " IFR";
                    }


                    grupeSelectate = new boolean[grupe.length];

                    licentaGrupaSerie13Items = groups.stream()
                            .filter(group -> !group.getSerie().equals("-") && Integer.valueOf(group.getSerie()) == 13)
                            .map(group -> "Grupa " + String.valueOf(group.getNumar()))
                            .toArray(String[]::new);
                    licentaGrupaSerie14Items = groups.stream()
                            .filter(group -> !group.getSerie().equals("-") && Integer.valueOf(group.getSerie()) == 14)
                            .map(group -> "Grupa " + String.valueOf(group.getNumar()))
                            .toArray(String[]::new);
                    licentaGrupaSerie23Items = groups.stream()
                            .filter(group -> !group.getSerie().equals("-") && Integer.valueOf(group.getSerie()) == 23)
                            .map(group -> "Grupa " + String.valueOf(group.getNumar()))
                            .toArray(String[]::new);
                    licentaGrupaSerie24Items = groups.stream()
                            .filter(group -> !group.getSerie().equals("-") && Integer.valueOf(group.getSerie()) == 24)
                            .map(group -> "Grupa " + String.valueOf(group.getNumar()))
                            .toArray(String[]::new);
                    licentaGrupaSerie33Items = groups.stream()
                            .filter(group -> !group.getSerie().equals("-") && Integer.valueOf(group.getSerie()) == 33)
                            .map(group -> "Grupa " + String.valueOf(group.getNumar()))
                            .toArray(String[]::new);
                    licentaGrupaSerie34Items = groups.stream()
                            .filter(group -> !group.getSerie().equals("-") && Integer.valueOf(group.getSerie()) == 34)
                            .map(group -> "Grupa " + String.valueOf(group.getNumar()))
                            .toArray(String[]::new);
                    licentaGrupaSerie34Items = groups.stream()
                            .filter(group -> !group.getSerie().equals("-") && Integer.valueOf(group.getSerie()) == 34)
                            .map(group -> "Grupa " + String.valueOf(group.getNumar()))
                            .toArray(String[]::new);
                    mastergrupa1Items = groups.stream()
                            .filter(group -> group.getSerie().equals("-") && group.getNumar() > 400 && group.getNumar() < 500)
                            .map(group -> "Grupa " + String.valueOf(group.getNumar()))
                            .toArray(String[]::new);
                    mastergrupa2Items = groups.stream()
                            .filter(group -> group.getSerie().equals("-") && group.getNumar() > 500)
                            .map(group -> "Grupa " + String.valueOf(group.getNumar()))
                            .toArray(String[]::new);

                    licentaIDItems = groups.stream()
                            .filter(group -> group.getFormaDeInvatamant().equals("ID"))
                            .map(group -> "Grupa " + String.valueOf(group.getNumar()))
                            .toArray(String[]::new);

                    masterIFRItems = groups.stream()
                            .filter(group -> group.getFormaDeInvatamant().equals("IFR"))
                            .map(group -> "Grupa " + String.valueOf(group.getNumar()))
                            .toArray(String[]::new);

                    spinnerStudentGrupa = findViewById(R.id.spinnerStudentGrupa);
                    final ArrayAdapter<String> adapterGrupa = new ArrayAdapter<>(RegisterActivity.this, android.R.layout.simple_spinner_dropdown_item, licentaGrupaSerie13Items);
                    spinnerStudentGrupa.setAdapter(adapterGrupa);


                }
               }
        });

        /**
         *  functie de obtinere obiect din baza dupa ID si delete dupa un ID
         */

//        long groupIdToDelete = 99;
//        groupModal.getGroupById(groupIdToDelete).observe(this, new Observer<Group>() {
//            @Override
//            public void onChanged(Group group) {
//                if (group != null) {
//                    groupModal.delete(group);
//                } else {
//                }
//            }
//        });

        /**
         *  functie de obtinere obiect din baza dupa ID si update obiectului
         */
//        studentModal.getStudentById(21).observe(this, new Observer<Student>() {
//            @Override
//            public void onChanged(Student student) {
//                if (student != null) {
//                    student.setAn(2);
//                    studentModal.update(student);
//                } else {
//                }
//            }
//        });

        subjectModal.getAllSubjects().observe(this, new Observer<List<Subject>>() {
            @Override
            public void onChanged(List<Subject> subjects) {
                if(subjects != null) {
                    discipline = subjects.stream()
                            .map(subject -> subject.getDenumire())
                            .toArray(String[]::new);
                    disciplineSelectate = new boolean[discipline.length];
                }
            }
        });


        Register = (Button)findViewById(R.id.buttonRegister);
        Email = (EditText)findViewById(R.id.editEmail);
        Password = (EditText)findViewById(R.id.editPassword);
        Nume = (EditText)findViewById(R.id.editFirstName);
        Prenume = (EditText)findViewById(R.id.editLastName);
        editStudentAnIncepere = (EditText)findViewById(R.id.editStudentAnIncepere);

        checkboxIF = findViewById(R.id.checkboxIF);
        checkboxIFR = findViewById(R.id.checkboxIFR);
        checkboxID = findViewById(R.id.checkboxID);

        checkboxTaxa = findViewById(R.id.checkboxTaxa);
        checkboxBursa = findViewById(R.id.checkboxBursa);

        layoutStudent = findViewById(R.id.layoutStudent);
        layoutProfesor = findViewById(R.id.layoutProfesor);

        materialCardProfesorDisciplina = findViewById(R.id.materialCardProfesorDisciplina);
        profDisciplineTextView = findViewById(R.id.profDisciplineTextView);

        materialCardProfesorGrupe = findViewById(R.id.materialCardProfesorGrupe);
        profGrupeTextView = findViewById(R.id.profGrupeTextView);

        checkboxLicenta = findViewById(R.id.checkboxLicenta);
        checkboxMaster = findViewById(R.id.checkboxMaster);

        EmptyEditTextAfterDataInsert();

        checkboxTaxa.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if(isChecked) {
                   checkboxBursa.setChecked(false);
                }
            }
        });
        checkboxBursa.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if(isChecked) {
                   checkboxTaxa.setChecked(false);
                }
            }
        });





        //spinnerStudentProfil = findViewById(R.id.spinnerStudentProfil);

        String[] tipContItems = {"STUDENT", "PROFESOR", "ADMIN"};
        ArrayAdapter<String> adaptertipCont = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, tipContItems);
        spinnerTipCont = findViewById(R.id.spinnerTipCont);
        spinnerTipCont.setAdapter(adaptertipCont);

        spinnerTipCont.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if(position == 0) {
                    //student
                    layoutStudent.setVisibility(View.VISIBLE);
                    layoutProfesor.setVisibility(View.GONE);
                    Nume.setVisibility(View.VISIBLE);
                    Prenume.setVisibility(View.VISIBLE);
                }
                if(position == 1) {
                    //profesor
                    layoutStudent.setVisibility(View.GONE);
                    layoutProfesor.setVisibility(View.VISIBLE);
                    Nume.setVisibility(View.VISIBLE);
                    Prenume.setVisibility(View.VISIBLE);
                }
                if(position == 2){
                    //admin
                    Nume.setVisibility(View.GONE);
                    Prenume.setVisibility(View.GONE);
                    layoutStudent.setVisibility(View.GONE);
                    layoutProfesor.setVisibility(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }

        });
//        String[] programDeStudiuItems = {"Matematică și informatică"};
//        ArrayAdapter<String> adapterProgramDeStudiu = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, programDeStudiuItems);
//        spinnerStudentProgramDeStudiu.setAdapter(adapterProgramDeStudiu);

//        String[] licentaProfilItems = {"Informatică", "Matematică", "CTI"};
//        String[] masterProfilItems = {"BDTS", "Inginerie Software", "Artificial Intelligence", "Sisteme distribuite", "SAL", "Data Science", "NLP"};
//        ArrayAdapter<String> adapterProfil = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, licentaProfilItems);
//        spinnerStudentProfil.setAdapter(adapterProfil);

//
//        String[] licentaAnItems = new String[4];
//        String[] masterAnItems = new String[2];
//        licentaAnItems[0] = "Anul 1";
//        licentaAnItems[1] = "Anul 2";
//        licentaAnItems[2] = "Anul 3";
//        masterAnItems[0] = "Anul 1";
//        masterAnItems[1] = "Anul 2";
//        spinnerStudentProfil.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
//                if(position == 1 || position == 1) {
//                    licentaAnItems[0] = "Anul 1";
//                    licentaAnItems[1] = "Anul 2";
//                    licentaAnItems[2] = "Anul 3";
//                    masterAnItems[0] = "Anul 1";
//                    masterAnItems[1] = "Anul 2";
//                }
//                if(position == 2){
//                    licentaAnItems[0] = "Anul 1";
//                    licentaAnItems[1] = "Anul 2";
//                    licentaAnItems[2] = "Anul 3";
//                    licentaAnItems[3] = "Anul 4";
//                    masterAnItems[0] = "Anul 1";
//                    masterAnItems[1] = "Anul 2";
//                }
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> parentView) {
//            }
//        });

        String[] licentaAnItems = {"Anul 1", "Anul 2", "Anul 3"};
        String[] masterAnItems = {"Anul 1", "Anul 2"};
        ArrayAdapter<String> adapterAn = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, licentaAnItems);
        spinnerStudentAn = findViewById(R.id.spinnerStudentAn);
        spinnerStudentAn.setAdapter(adapterAn);

        String[] licentaSerie1Items = {"Seria 13", "Seria 14"};
        String[] licentaSerie2Items = {"Seria 23", "Seria 24"};
        String[] licentaSerie3Items = {"Seria 33", "Seria 34"};
        String[] masterSerieItems = {"-"};
        spinnerStudentSerie = findViewById(R.id.spinnerStudentSerie);
        final ArrayAdapter<String> adapterSerie = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, licentaSerie1Items);
        spinnerStudentSerie.setAdapter(adapterSerie);

        checkboxLicenta.setChecked(true);
        checkboxIFR.setVisibility(View.GONE);
        checkboxMaster.setChecked(false);
        checkboxMaster.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if(isChecked) {
                    checkboxID.setVisibility(View.GONE);
                    checkboxIFR.setVisibility(View.VISIBLE);
                    checkboxIF.setChecked(true);
                    checkboxLicenta.setChecked(false);
                    ArrayAdapter<String> tempAdapter = new ArrayAdapter<>(RegisterActivity.this, android.R.layout.simple_spinner_dropdown_item, masterAnItems);
                    spinnerStudentAn.setAdapter(tempAdapter);
                    ArrayAdapter<String> tempAdapter2 = new ArrayAdapter<>(RegisterActivity.this, android.R.layout.simple_spinner_dropdown_item, masterSerieItems);
                    spinnerStudentSerie.setAdapter(tempAdapter2);
                    ArrayAdapter<String> tempAdapter3 = new ArrayAdapter<>(RegisterActivity.this, android.R.layout.simple_spinner_dropdown_item, mastergrupa1Items);
                    spinnerStudentGrupa.setAdapter(tempAdapter3);
//                    ArrayAdapter<String> tempAdapter4 = new ArrayAdapter<>(RegisterActivity.this, android.R.layout.simple_spinner_dropdown_item, masterProfilItems);
//                    spinnerStudentProfil.setAdapter(tempAdapter4);
                }
            }
        });
        checkboxLicenta.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if(isChecked) {
                    checkboxIFR.setVisibility(View.GONE);
                    checkboxID.setVisibility(View.VISIBLE);
                    checkboxIF.setChecked(true);
                    checkboxMaster.setChecked(false);
                    ArrayAdapter<String> tempAdapter = new ArrayAdapter<>(RegisterActivity.this, android.R.layout.simple_spinner_dropdown_item, licentaAnItems);
                    spinnerStudentAn.setAdapter(tempAdapter);
                    ArrayAdapter<String> tempAdapter2 = new ArrayAdapter<>(RegisterActivity.this, android.R.layout.simple_spinner_dropdown_item, licentaSerie1Items);
                    spinnerStudentSerie.setAdapter(tempAdapter2);
                    ArrayAdapter<String> tempAdapter3 = new ArrayAdapter<>(RegisterActivity.this, android.R.layout.simple_spinner_dropdown_item, licentaGrupaSerie13Items);
                    spinnerStudentGrupa.setAdapter(tempAdapter3);
//                    ArrayAdapter<String> tempAdapter4 = new ArrayAdapter<>(RegisterActivity.this, android.R.layout.simple_spinner_dropdown_item, licentaProfilItems);
//                    spinnerStudentProfil.setAdapter(tempAdapter4);
                }
            }
        });

        checkboxID.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if(isChecked) {
                    checkboxIF.setChecked(false);
                    checkboxIFR.setChecked(false);
                    ArrayAdapter<String> tempAdapterID = new ArrayAdapter<>(RegisterActivity.this, android.R.layout.simple_spinner_dropdown_item, licentaIDItems);
                    spinnerStudentGrupa.setAdapter(tempAdapterID);
                    ArrayAdapter<String> tempAdapter2 = new ArrayAdapter<>(RegisterActivity.this, android.R.layout.simple_spinner_dropdown_item, masterSerieItems);
                    spinnerStudentSerie.setAdapter(tempAdapter2);
                }
            }
        });
        checkboxIF.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if(isChecked) {
                    checkboxID.setChecked(false);
                    checkboxIFR.setChecked(false);
                    if(checkboxLicenta.isChecked()){
                        ArrayAdapter<String> tempAdapter = new ArrayAdapter<>(RegisterActivity.this, android.R.layout.simple_spinner_dropdown_item, licentaAnItems);
                        spinnerStudentAn.setAdapter(tempAdapter);
                        ArrayAdapter<String> tempAdapter2 = new ArrayAdapter<>(RegisterActivity.this, android.R.layout.simple_spinner_dropdown_item, licentaSerie1Items);
                        spinnerStudentSerie.setAdapter(tempAdapter2);
                        ArrayAdapter<String> tempAdapter3 = new ArrayAdapter<>(RegisterActivity.this, android.R.layout.simple_spinner_dropdown_item, licentaGrupaSerie13Items);
                        spinnerStudentGrupa.setAdapter(tempAdapter3);
                    }
                  else if(checkboxMaster.isChecked()){
                        ArrayAdapter<String> tempAdapter = new ArrayAdapter<>(RegisterActivity.this, android.R.layout.simple_spinner_dropdown_item, masterAnItems);
                        spinnerStudentAn.setAdapter(tempAdapter);
                        ArrayAdapter<String> tempAdapter2 = new ArrayAdapter<>(RegisterActivity.this, android.R.layout.simple_spinner_dropdown_item, masterSerieItems);
                        spinnerStudentSerie.setAdapter(tempAdapter2);
                        ArrayAdapter<String> tempAdapter3 = new ArrayAdapter<>(RegisterActivity.this, android.R.layout.simple_spinner_dropdown_item, mastergrupa1Items);
                        spinnerStudentGrupa.setAdapter(tempAdapter3);
                    }
                }
            }
        });
        checkboxIFR.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if(isChecked) {
                    checkboxID.setChecked(false);
                    checkboxIF.setChecked(false);
                    ArrayAdapter<String> tempAdapterIFR = new ArrayAdapter<>(RegisterActivity.this, android.R.layout.simple_spinner_dropdown_item, masterIFRItems);
                    spinnerStudentGrupa.setAdapter(tempAdapterIFR);
                    ArrayAdapter<String> tempAdapter2 = new ArrayAdapter<>(RegisterActivity.this, android.R.layout.simple_spinner_dropdown_item, masterSerieItems);
                    spinnerStudentSerie.setAdapter(tempAdapter2);
                }
            }
        });
        spinnerStudentAn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if(position == 1) {
                    ArrayAdapter<String> tempAdapter = new ArrayAdapter<>(RegisterActivity.this, android.R.layout.simple_spinner_dropdown_item, (!checkboxLicenta.isChecked() && checkboxMaster.isChecked()) ? masterSerieItems : (checkboxID.isChecked() ? masterSerieItems : licentaSerie2Items));
                    spinnerStudentSerie.setAdapter(tempAdapter);
                    ArrayAdapter<String> tempAdapter3 = new ArrayAdapter<>(RegisterActivity.this, android.R.layout.simple_spinner_dropdown_item, (!checkboxLicenta.isChecked() && checkboxMaster.isChecked()) ?(checkboxIFR.isChecked() ? masterIFRItems : mastergrupa2Items) : (checkboxID.isChecked() ? licentaIDItems : licentaGrupaSerie23Items));
                    spinnerStudentGrupa.setAdapter(tempAdapter3);
                }
                if(position == 0) {

                    ArrayAdapter<String> tempAdapter = new ArrayAdapter<>(RegisterActivity.this, android.R.layout.simple_spinner_dropdown_item, (!checkboxLicenta.isChecked() && checkboxMaster.isChecked()) ? (masterSerieItems) : (checkboxID.isChecked() ? masterSerieItems : licentaSerie1Items));
                    spinnerStudentSerie.setAdapter(tempAdapter);
                    ArrayAdapter<String> tempAdapter3 = new ArrayAdapter<>(RegisterActivity.this, android.R.layout.simple_spinner_dropdown_item, (!checkboxLicenta.isChecked() && checkboxMaster.isChecked()) ? (checkboxIFR.isChecked() ? masterIFRItems : mastergrupa1Items) : (checkboxID.isChecked() ? licentaIDItems : licentaGrupaSerie13Items));
                    spinnerStudentGrupa.setAdapter(tempAdapter3);
                }
                if(position == 2){
                    ArrayAdapter<String> tempAdapter = new ArrayAdapter<>(RegisterActivity.this, android.R.layout.simple_spinner_dropdown_item, (checkboxID.isChecked() ? masterSerieItems : licentaSerie3Items));
                    spinnerStudentSerie.setAdapter(tempAdapter);
                    ArrayAdapter<String> tempAdapter3 = new ArrayAdapter<>(RegisterActivity.this, android.R.layout.simple_spinner_dropdown_item, (checkboxID.isChecked() ? licentaIDItems : licentaGrupaSerie33Items));
                    spinnerStudentGrupa.setAdapter(tempAdapter3);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

        spinnerStudentSerie.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if(checkboxLicenta.isChecked() && checkboxIF.isChecked()) {
                    if (position == 0 && spinnerStudentAn.getSelectedItemPosition() == 0) {
                        ArrayAdapter<String> tempAdapter = new ArrayAdapter<>(RegisterActivity.this, android.R.layout.simple_spinner_dropdown_item, licentaGrupaSerie13Items);
                        spinnerStudentGrupa.setAdapter(tempAdapter);
                    }
                    if (position == 1 && spinnerStudentAn.getSelectedItemPosition() == 0) {
                        ArrayAdapter<String> tempAdapter = new ArrayAdapter<>(RegisterActivity.this, android.R.layout.simple_spinner_dropdown_item, licentaGrupaSerie14Items);
                        spinnerStudentGrupa.setAdapter(tempAdapter);
                    }
                    if (position == 0 && spinnerStudentAn.getSelectedItemPosition() == 1) {
                        ArrayAdapter<String> tempAdapter = new ArrayAdapter<>(RegisterActivity.this, android.R.layout.simple_spinner_dropdown_item, licentaGrupaSerie23Items);
                        spinnerStudentGrupa.setAdapter(tempAdapter);
                    }
                    if (position == 1 && spinnerStudentAn.getSelectedItemPosition() == 1) {
                        ArrayAdapter<String> tempAdapter = new ArrayAdapter<>(RegisterActivity.this, android.R.layout.simple_spinner_dropdown_item, licentaGrupaSerie24Items);
                        spinnerStudentGrupa.setAdapter(tempAdapter);
                    }
                    if (position == 0 && spinnerStudentAn.getSelectedItemPosition() == 2) {
                        ArrayAdapter<String> tempAdapter = new ArrayAdapter<>(RegisterActivity.this, android.R.layout.simple_spinner_dropdown_item, licentaGrupaSerie33Items);
                        spinnerStudentGrupa.setAdapter(tempAdapter);
                    }
                    if (position == 1 && spinnerStudentAn.getSelectedItemPosition() == 2) {
                        ArrayAdapter<String> tempAdapter = new ArrayAdapter<>(RegisterActivity.this, android.R.layout.simple_spinner_dropdown_item, licentaGrupaSerie34Items);
                        spinnerStudentGrupa.setAdapter(tempAdapter);
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });


        String[] profesorPozitieItems = {"Asistent", "Lector", "Conferențiar", "Profesor"};
        ArrayAdapter<String> adapterProfesorPozitie = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, profesorPozitieItems);
        spinnerProfesorPozitie = findViewById(R.id.spinnerProfesorPozitie);
        spinnerProfesorPozitie.setAdapter(adapterProfesorPozitie);


        materialCardProfesorDisciplina.setOnClickListener(v -> {
            displayDiscipline();
        });

        materialCardProfesorGrupe.setOnClickListener(v -> {
            displayGrupe();
        });

        sqLiteHelper = new SQLiteHelper(this);
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDataBaseBuild();
                SQLiteTableBuild();
                CheckEditTextStatus();
                CheckingEmailAlreadyExistsOrNot();
            }
        });
    }

    private void displayDiscipline(){
        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
        builder.setTitle("Selectează discipline");
        builder.setCancelable(false);

        builder.setMultiChoiceItems(discipline, disciplineSelectate, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if (isChecked) {
                    listDiscipline.add(which);
                } else {
                    if (listDiscipline.contains(which)) {
                        listDiscipline.remove(Integer.valueOf(which));
                    }
                }
            }
        }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < listDiscipline.size(); i++) {
                    stringBuilder.append(discipline[listDiscipline.get(i)]);
                    if (i != listDiscipline.size() - 1)
                        stringBuilder.append(", ");
                }
                profDisciplineTextView.setText(stringBuilder.toString());
            }
        }).setNegativeButton("Anulează", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).setNeutralButton("Ștergere", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                for (int i = 0; i < disciplineSelectate.length; i++) {
                    disciplineSelectate[i] = false;
                }
                listDiscipline.clear();
                profDisciplineTextView.setText("");
            }
        });
        builder.show();
    }

    private void displayGrupe(){
        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
        builder.setTitle("Selectează grupe");
        builder.setCancelable(false);

        builder.setMultiChoiceItems(grupe, grupeSelectate, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if (isChecked) {
                    listGrupe.add(which);
                } else {
                    if (listGrupe.contains(which)) {
                        listGrupe.remove(Integer.valueOf(which));
                    }
                }
            }
        }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < listGrupe.size(); i++) {
                    stringBuilder.append(grupe[listGrupe.get(i)]);
                    if (i != listGrupe.size() - 1)
                        stringBuilder.append(", ");
                }
                profGrupeTextView.setText(stringBuilder.toString());
            }
        }).setNegativeButton("Anulează", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).setNeutralButton("Ștergere", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                for (int i = 0; i < grupeSelectate.length; i++) {
                    grupeSelectate[i] = false;
                }
                listGrupe.clear();
                profGrupeTextView.setText("");
            }
        });
        builder.show();
    }

    public void SQLiteDataBaseBuild(){
        sqLiteDatabaseObj = openOrCreateDatabase(SQLiteHelper.DATABASE_NAME, Context.MODE_PRIVATE, null);
    }
    public void SQLiteTableBuild() {
        sqLiteDatabaseObj.execSQL("CREATE TABLE IF NOT EXISTS " + SQLiteHelper.TABLE_NAME + "("
                + SQLiteHelper.Table_Column_ID + " INTEGER PRIMARY KEY, "
                + SQLiteHelper.Table_Column_1_Nume + " VARCHAR, "
                + SQLiteHelper.Table_Column_2_Prenume + " VARCHAR, "
                + SQLiteHelper.Table_Column_3_Email + " VARCHAR, "
                + SQLiteHelper.Table_Column_4_Password + " VARCHAR, "
                + SQLiteHelper.Table_Column_5_An + " VARCHAR, "
                + SQLiteHelper.Table_Column_6_Serie + " VARCHAR, "
                + SQLiteHelper.Table_Column_7_Grupa + " VARCHAR, "
                + SQLiteHelper.Table_Column_8_Taxa + " VARCHAR, "
                + SQLiteHelper.Table_Column_9_Bursa + " VARCHAR, "
                + SQLiteHelper.Table_Column_10_An_Inscriere + " VARCHAR, "
                + SQLiteHelper.Table_Column_11_Forma_De_Invatamant + " VARCHAR, "
                + SQLiteHelper.Table_Column_12_Tip_Studii + " VARCHAR);");
    }
    public void InsertDataIntoSQLiteDatabase(){
        if(EditTextEmptyHolder == true && (int)spinnerTipCont.getSelectedItemPosition() == 0)
        {
            String formaDeInvatamant = null;
            String tipStudii = null;
            if(checkboxIFR.isChecked()) formaDeInvatamant = "IFR";
            else if(checkboxIF.isChecked()) formaDeInvatamant = "IF";
            else if(checkboxID.isChecked()) formaDeInvatamant = "ID";

            if(checkboxLicenta.isChecked()) tipStudii = "Licenta";
            else if(checkboxMaster.isChecked()) tipStudii = "Master";

            int numar  = (checkboxID.isChecked() || checkboxIFR.isChecked()) ? Integer.valueOf(String.valueOf(spinnerStudentGrupa.getSelectedItem().toString().charAt(spinnerStudentGrupa.getSelectedItem().toString().length() - 1))) : Integer.valueOf(String.valueOf(spinnerStudentGrupa.getSelectedItem().toString().charAt(spinnerStudentGrupa.getSelectedItem().toString().length() - 3)) + String.valueOf(spinnerStudentGrupa.getSelectedItem().toString().charAt(spinnerStudentGrupa.getSelectedItem().toString().length() - 2)) + String.valueOf(spinnerStudentGrupa.getSelectedItem().toString().charAt(spinnerStudentGrupa.getSelectedItem().toString().length() - 1)));


            String finalTipStudii = tipStudii;
            groupModal.getGroupIdByNumarAndForma(numar, formaDeInvatamant).observe(this, new Observer<Long>() {
                @Override
                public void onChanged(Long groupId) {
                    long insertedId = studentModal.insert(new Student(NumeHolder, PrenumeHolder, EmailHolder, PasswordHolder, Integer.valueOf(String.valueOf(spinnerStudentAn.getSelectedItem().toString().charAt(spinnerStudentAn.getSelectedItem().toString().length() - 1))), checkboxTaxa.isChecked(), checkboxBursa.isChecked(), Integer.valueOf(editStudentAnIncepereHolder), finalTipStudii, groupId, false));

                    SetariNotificari setariNotificari = new SetariNotificari();
                    setariNotificari.setStudentId(insertedId);
                    setariNotificari.setType("orar");
                    setariNotificari.setVreaNotificare(true);
                    setariNotificariModal.insert(setariNotificari);

                    SetariNotificari setariNotificari1 = new SetariNotificari();
                    setariNotificari1.setStudentId(insertedId);
                    setariNotificari1.setType("calendar");
                    setariNotificari1.setVreaNotificare(true);
                    setariNotificariModal.insert(setariNotificari1);

                    SetariNotificari setariNotificari2 = new SetariNotificari();
                    setariNotificari2.setStudentId(insertedId);
                    setariNotificari2.setType("examen");
                    setariNotificari2.setVreaNotificare(true);
                    setariNotificariModal.insert(setariNotificari2);

                    SetariNotificari setariNotificari3 = new SetariNotificari();
                    setariNotificari3.setStudentId(insertedId);
                    setariNotificari3.setType("nota");
                    setariNotificari3.setVreaNotificare(true);
                    setariNotificariModal.insert(setariNotificari3);

                    SetariNotificari setariNotificari4 = new SetariNotificari();
                    setariNotificari4.setStudentId(insertedId);
                    setariNotificari4.setType("activitate");
                    setariNotificari4.setVreaNotificare(true);
                    setariNotificariModal.insert(setariNotificari4);

                    SetariNotificari setariNotificari5 = new SetariNotificari();
                    setariNotificari5.setStudentId(insertedId);
                    setariNotificari5.setType("anunt");
                    setariNotificari5.setVreaNotificare(true);
                    setariNotificariModal.insert(setariNotificari5);

                    SetariNotificari setariNotificari6 = new SetariNotificari();
                    setariNotificari6.setStudentId(insertedId);
                    setariNotificari6.setType("internship");
                    setariNotificari6.setVreaNotificare(true);
                    setariNotificariModal.insert(setariNotificari6);

                    SetariNotificari setariNotificari7 = new SetariNotificari();
                    setariNotificari7.setStudentId(insertedId);
                    setariNotificari7.setType("voluntariat");
                    setariNotificari7.setVreaNotificare(true);
                    setariNotificariModal.insert(setariNotificari7);

                    SetariNotificari setariNotificari8 = new SetariNotificari();
                    setariNotificari8.setStudentId(insertedId);
                    setariNotificari8.setType("voluntariatInscris");
                    setariNotificari8.setVreaNotificare(false);
                    setariNotificariModal.insert(setariNotificari8);
                }
            });

            SQLiteDataBaseQueryHolder = "INSERT INTO "+SQLiteHelper.TABLE_NAME+" (nume,prenume,email,password,an,serie,grupa,taxa,bursa,an_inscriere,forma_de_invatamant,tip_studii)" +
                    " VALUES('"+NumeHolder+"', '"+PrenumeHolder+"', '"+EmailHolder+"', '"+PasswordHolder+"', '"+spinnerStudentAn.getSelectedItem()+"', '"+spinnerStudentSerie.getSelectedItem()+"', '"+spinnerStudentGrupa.getSelectedItem()+"', '"+checkboxTaxa.isChecked()+"'," +
                    "'"+checkboxBursa.isChecked()+"', '"+editStudentAnIncepereHolder+"', '"+formaDeInvatamant+"', '"+tipStudii+"');";

            sqLiteDatabaseObj.execSQL(SQLiteDataBaseQueryHolder);
            sqLiteDatabaseObj.close();

            Toast.makeText(RegisterActivity.this,"User înregistrat cu succes!", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(RegisterActivity.this, DashboardActivity.class);
            startActivity(intent);
        }
        else if(EditTextEmptyHolder == true && (int)spinnerTipCont.getSelectedItemPosition() == 2)
        {
            studentModal.insert(new Student(NumeHolder, PrenumeHolder, EmailHolder, PasswordHolder, 0, false, false, 0, null, null, true));

//            SQLiteDataBaseQueryHolder = "INSERT INTO "+SQLiteHelper.TABLE_NAME+" (nume,prenume,email,password,an,serie,grupa,taxa,bursa,an_inscriere,forma_de_invatamant,tip_studii)" +
//                    " VALUES('"+NumeHolder+"', '"+null+"', '"+EmailHolder+"', '"+PasswordHolder+"', '"+null+"', '"+null+"', '"+null+"', '"+null+"'," +
//                    "'"+null+"', '"+null+"', '"+null+"', '"+null+"');";
//
//            sqLiteDatabaseObj.execSQL(SQLiteDataBaseQueryHolder);
//            sqLiteDatabaseObj.close();

            Toast.makeText(RegisterActivity.this,"User înregistrat cu succes!", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(RegisterActivity.this, DashboardActivity.class);
            startActivity(intent);
        }
        else if(EditTextEmptyHolder == true && (int)spinnerTipCont.getSelectedItemPosition() == 1)
        {
            List<String> subjectsNames = new ArrayList();
            List<Integer> groupsNrs = new ArrayList();
            List<String> formaDeInvatamant = new ArrayList();

            StringBuilder stringBuilderGrupe = new StringBuilder();
            for(int i = 0; i < listGrupe.size() - 1; i ++){
                stringBuilderGrupe.append(grupe[listGrupe.get(i)]). append("; ");
                if(String.valueOf(grupe[listGrupe.get(i)].charAt(grupe[listGrupe.get(i)].length() - 1)).equals("D")) {
                    grupe[listGrupe.get(i)] = grupe[listGrupe.get(i)].substring(0, grupe[listGrupe.get(i)].length() - 3);
                    formaDeInvatamant.add("ID");
                }
                else  if(String.valueOf(grupe[listGrupe.get(i)].charAt(grupe[listGrupe.get(i)].length() - 1)).equals("R")){
                    grupe[listGrupe.get(i)] = grupe[listGrupe.get(i)].substring(0, grupe[listGrupe.get(i)].length() - 4);
                    formaDeInvatamant.add("IFR");
                }
                else formaDeInvatamant.add("IF");
                groupsNrs.add(Integer.valueOf(grupe[listGrupe.get(i)]));
            }
            groupsNrs.add(Integer.valueOf(grupe[listGrupe.get(listGrupe.size() - 1)]));
            if(String.valueOf(grupe[listGrupe.size() - 1].charAt(grupe[listGrupe.size() - 1].length() - 1)).equals("D")) {
                grupe[listGrupe.size() - 1] = grupe[listGrupe.size() - 1].substring(0, grupe[listGrupe.size() - 1].length() - 3);
                formaDeInvatamant.add("ID");
            }
            else  if(String.valueOf(grupe[listGrupe.size() - 1].charAt(grupe[listGrupe.size() - 1].length() - 1)).equals("R")){
                grupe[listGrupe.size() - 1] = grupe[listGrupe.size() - 1].substring(0, grupe[listGrupe.size() - 1].length() - 4);
                formaDeInvatamant.add("IFR");
            }
            else formaDeInvatamant.add("IF");
            stringBuilderGrupe.append(grupe[listGrupe.get(listGrupe.size() - 1)]);

            StringBuilder stringBuilderDiscipline = new StringBuilder();
            for(int i = 0; i < listDiscipline.size() - 1; i ++){
                stringBuilderDiscipline.append(discipline[listDiscipline.get(i)]). append("; ");
                subjectsNames.add(discipline[listDiscipline.get(i)]);
            }
            subjectsNames.add(discipline[listDiscipline.get(listDiscipline.size() - 1)]);
            stringBuilderDiscipline.append(discipline[listDiscipline.get(listDiscipline.size() - 1)]);

//            List<Long> groupIds = new ArrayList<>();
//            for (int i = 0; i <groupsNrs.size(); i ++) {
//                groupModal.getGroupIdByNumarAndForma(groupsNrs.get(i), formaDeInvatamant.get(i)).observe(this, new Observer<Long>() {
//                    @Override
//                    public void onChanged(Long groupId) {
//                        groupIds.add(groupId);
//                    }
//                });
//            }

            List<Long> subjectIds = new ArrayList<>();
            for (String subjectsName : subjectsNames) {
                subjectModal.getSubjectIdByDenumire(subjectsName).observe(this, new Observer<Long>() {
                    @Override
                    public void onChanged(Long subjectId) {
                        if (subjectId != null) {
                            subjectIds.add(subjectId);
                        }
                    }
                });
            }
            professorModal.insert(new Professor(NumeHolder, PrenumeHolder, EmailHolder, PasswordHolder, spinnerProfesorPozitie.getSelectedItem().toString()));

            professorModal.getProfessorIdByEmail(EmailHolder).observe(this, new Observer<Long>() {
                @Override
                public void onChanged(Long professorId) {
                    if (professorId != null) {
//                        for (Long groupId : groupIds) {
//                            Course course = new Course();
//
//                            course.setProfessorId(professorId);
//                            course.setGroupId(groupId);
//                            courseModal.insert(course);
//                        }
                        for (Long subjectId : subjectIds) {
                            ProfessorSubject professorSubject = new ProfessorSubject();

                            professorSubject.setProfessorId(professorId);
                            professorSubject.setSubjectId(subjectId);
                            professorSubjectModal.insert(professorSubject);
                        }
                    } else {
                    }
                }
            });


            SQLiteDataBaseQueryHolder = "INSERT INTO "+SQLiteHelper.TABLE_NAME+" (nume,prenume,email,password,an,serie,grupa,taxa,bursa,an_inscriere,forma_de_invatamant,tip_studii)" +
                    " VALUES('"+NumeHolder+"', '"+PrenumeHolder+"', '"+EmailHolder+"', '"+PasswordHolder+"', '"+null+"', '"+null+"', '"+stringBuilderGrupe+"', '"+null+"'," +
                    "'"+null+"', '"+spinnerProfesorPozitie.getSelectedItem()+"', '"+null+"', '"+stringBuilderDiscipline+"');";

            sqLiteDatabaseObj.execSQL(SQLiteDataBaseQueryHolder);
            sqLiteDatabaseObj.close();

            Toast.makeText(RegisterActivity.this,"User înregistrat cu succes!", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(RegisterActivity.this, DashboardActivity.class);
            startActivity(intent);
        }
        else {
            Toast.makeText(RegisterActivity.this,"Vă rog completați toate datele necesare!", Toast.LENGTH_LONG).show();
        }

    }

    public void EmptyEditTextAfterDataInsert(){
        Nume.getText().clear();
        Prenume.getText().clear();
        Email.getText().clear();
        Password.getText().clear();
        editStudentAnIncepere.getText().clear();
        checkboxLicenta.setChecked(false);
        checkboxMaster.setChecked(false);
        checkboxIF.setChecked(false);
        checkboxIFR.setChecked(false);
        checkboxID.setChecked(false);
        checkboxTaxa.setChecked(false);
        checkboxBursa.setChecked(false);
    }
    // metoda sa vedem daca e totul completat cum vrem
    public void CheckEditTextStatus(){
        NumeHolder = Nume.getText().toString();
        PrenumeHolder = Prenume.getText().toString();
        EmailHolder = Email.getText().toString();
        PasswordHolder = Password.getText().toString();
        editStudentAnIncepereHolder = editStudentAnIncepere.getText().toString();
        if(((int)spinnerTipCont.getSelectedItemPosition()) == 0 && (TextUtils.isEmpty(NumeHolder) || TextUtils.isEmpty(PrenumeHolder) || TextUtils.isEmpty(EmailHolder) || TextUtils.isEmpty(PasswordHolder) ||
                TextUtils.isEmpty(editStudentAnIncepereHolder) || (!checkboxIF.isChecked() && !checkboxID.isChecked() && !checkboxIFR.isChecked()) ||
                (!checkboxMaster.isChecked() && !checkboxLicenta.isChecked())))
            EditTextEmptyHolder = false;
        else if(((int)spinnerTipCont.getSelectedItemPosition()) == 2 && (TextUtils.isEmpty(EmailHolder) || TextUtils.isEmpty(PasswordHolder)))
                EditTextEmptyHolder = false;
            else if(((int)spinnerTipCont.getSelectedItemPosition()) == 1 && (TextUtils.isEmpty(NumeHolder) || TextUtils.isEmpty(PrenumeHolder) || TextUtils.isEmpty(EmailHolder) || TextUtils.isEmpty(PasswordHolder)
                || profDisciplineTextView.getText().toString().isEmpty() || profDisciplineTextView.getText().toString().equals("Selectează discipline") || profGrupeTextView.getText().toString().isEmpty() || profGrupeTextView.getText().toString().equals("Selectează grupe")))
                    EditTextEmptyHolder = false;
                else EditTextEmptyHolder = true;
    }
    public void CheckingEmailAlreadyExistsOrNot(){
        sqLiteDatabaseObj = sqLiteHelper.getWritableDatabase();
        cursor = sqLiteDatabaseObj.query(SQLiteHelper.TABLE_NAME, null, " " + SQLiteHelper.Table_Column_3_Email + "=?", new String[]{EmailHolder}, null, null, null);
        while (cursor.moveToNext()) {
            if (cursor.isFirst()) {
                cursor.moveToFirst();
                F_Result = "Email Found";
                cursor.close();
            }
        }
        CheckFinalResult();
    }
    public void CheckFinalResult(){
        if(F_Result.equalsIgnoreCase("Email Found"))
            Toast.makeText(RegisterActivity.this,"Email deja utilizat!",Toast.LENGTH_LONG).show();
        else InsertDataIntoSQLiteDatabase();
        F_Result = "Not_Found" ;
    }
}