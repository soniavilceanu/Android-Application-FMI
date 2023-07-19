package com.example.myapplicationfmi;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

import com.google.android.material.card.MaterialCardView;

import java.io.PipedOutputStream;
import java.util.ArrayList;

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
    String[] discipline = {"Structuri algebrice în informatică", "Arhitectura sistemelor de calcul", "Gândire critică și etică academică", "Programare algoritmică", "Calcul diferențial și integral",
            "Programare funcțională", "Probabilități și statistică", "Sisteme de gestiune a bazelor de date", "Algoritmi fundamentali", "Sisteme de operare",
            "Inginerie Software", "Calcul și complexități", "Securitatea sistemelor informatice",
            "Data Mining & Knowledge discovery", "Deontologie academică", "Sisteme de baze de date", "Programare web și tehnologii java", "Management și organizarea proiectelor software", "Programare algoritmi eficienți", "Analiza și modificarea sistemelor software",
            "Practical Machine Learning", "Programare paralelă și concurentă", "Advanced Crypto","OS: Design și securitate","Linguistics for computer science","Foundations NLP",
            "Securitatea bazelor de date","Data Warehousing & Bussiness intelligence", "Cloud Computing","Arhitectura sistemelor software","Modelarea sistemelor software","Arhitectura sistemelor software","Natural Language Processing","Deep learning", "Distributed data engineering",
            "Machine Translation",
            "Baze de date", "Programare orientată pe obiecte", "Structuri de date", "Tehnici Web", "Geometrie și algoritmi liniari", "Limbaje formale și automate"
            ,"Programare avansată pe obiecte","Metode dezvoltare software","Inteligență artificială","Rețele de calcul","Algoritmi avansați","Tehnologii compilare","Tehnologii optimizare",
            "Criptare și securitate","Tehnologii simulare","Ingineria programării",
            "Big data","Aplicații web pentru baze de date","Dezvoltare software pentru dispozitive mobile","Tehnologii avansate de programare","Advanced machine learning","Computer Vision","Network security","Cyber security","Statistical data science","Bio mechanical NLP",
            "Metode de optimizare și distribuire în baze de date","Topici speciale în baze de date și tehnologii software","Testare și verificare","Dezvoltare aplicații interactive","Special Topics AI","Problem Solving & Searching","Pagini web semantice", "Procese concurente", "Special topics security & applied logic","Info Visualization"};
    TextView profGrupeTextView;
    boolean [] grupeSelectate;
    ArrayList<Integer> listGrupe = new ArrayList<>();
    String[] grupe = {"131", "132", "133", "134", "135", "141", "142", "143", "144", "231", "232", "233", "234", "235", "241", "242", "243", "244", "331", "332", "333", "334", "335",
    "341","342","343","344","405","406","407","408","410","411","412","505","506","507","508","510","511","512"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
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
        disciplineSelectate = new boolean[discipline.length];

        materialCardProfesorGrupe = findViewById(R.id.materialCardProfesorGrupe);
        profGrupeTextView = findViewById(R.id.profGrupeTextView);
        grupeSelectate = new boolean[grupe.length];

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

        String[] licentaGrupaSerie13Items = {" Grupa 131", "Grupa 132", "Grupa 133", "Grupa 134", "Grupa 135"};
        String[] licentaGrupaSerie14Items = {"Grupa 141", "Grupa 142", "Grupa 143", "Grupa 144"};
        String[] licentaGrupaSerie23Items = {"Grupa 231", "Grupa 232", "Grupa 233", "Grupa 234", "Grupa 235"};
        String[] licentaGrupaSerie24Items = {"Grupa 241", "Grupa 242", "Grupa 243", "Grupa 244"};
        String[] licentaGrupaSerie33Items = {"Grupa 331", "Grupa 332", "Grupa 333", "Grupa 334", "Grupa 335"};
        String[] licentaGrupaSerie34Items = {"Grupa 341", "Grupa 342", "Grupa 343", "Grupa 344"};
        String[] mastergrupa1Items = {"Grupa 405", "Grupa 406", "Grupa 407", "Grupa 408", "Grupa 410", "Grupa 411", "Grupa 412"};
        String[] mastergrupa2Items = {"Grupa 505", "Grupa 506", "Grupa 507", "Grupa 508", "Grupa 510", "Grupa 511", "Grupa 512"};
        spinnerStudentGrupa = findViewById(R.id.spinnerStudentGrupa);
        final ArrayAdapter<String> adapterGrupa = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, licentaGrupaSerie13Items);
        spinnerStudentGrupa.setAdapter(adapterGrupa);

        checkboxLicenta = findViewById(R.id.checkboxLicenta);
        checkboxLicenta.setChecked(true);
        checkboxMaster = findViewById(R.id.checkboxMaster);
        checkboxMaster.setChecked(false);
        checkboxMaster.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if(isChecked) {
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
                }
            }
        });
        checkboxIF.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if(isChecked) {
                    checkboxID.setChecked(false);
                    checkboxIFR.setChecked(false);
                }
            }
        });
        checkboxIFR.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if(isChecked) {
                    checkboxID.setChecked(false);
                    checkboxIF.setChecked(false);
                }
            }
        });
        spinnerStudentAn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if(position == 1) {
                    ArrayAdapter<String> tempAdapter = new ArrayAdapter<>(RegisterActivity.this, android.R.layout.simple_spinner_dropdown_item, (!checkboxLicenta.isChecked() && checkboxMaster.isChecked()) ? masterSerieItems : licentaSerie2Items);
                    spinnerStudentSerie.setAdapter(tempAdapter);
                    ArrayAdapter<String> tempAdapter3 = new ArrayAdapter<>(RegisterActivity.this, android.R.layout.simple_spinner_dropdown_item, (!checkboxLicenta.isChecked() && checkboxMaster.isChecked()) ? mastergrupa2Items : licentaGrupaSerie23Items);
                    spinnerStudentGrupa.setAdapter(tempAdapter3);
                }
                if(position == 0) {

                    ArrayAdapter<String> tempAdapter = new ArrayAdapter<>(RegisterActivity.this, android.R.layout.simple_spinner_dropdown_item, (!checkboxLicenta.isChecked() && checkboxMaster.isChecked()) ? masterSerieItems : licentaSerie1Items);
                    spinnerStudentSerie.setAdapter(tempAdapter);
                    ArrayAdapter<String> tempAdapter3 = new ArrayAdapter<>(RegisterActivity.this, android.R.layout.simple_spinner_dropdown_item, (!checkboxLicenta.isChecked() && checkboxMaster.isChecked()) ? mastergrupa1Items : licentaGrupaSerie13Items);
                    spinnerStudentGrupa.setAdapter(tempAdapter3);
                }
                if(position == 2){
                    ArrayAdapter<String> tempAdapter = new ArrayAdapter<>(RegisterActivity.this, android.R.layout.simple_spinner_dropdown_item, licentaSerie3Items);
                    spinnerStudentSerie.setAdapter(tempAdapter);
                    ArrayAdapter<String> tempAdapter3 = new ArrayAdapter<>(RegisterActivity.this, android.R.layout.simple_spinner_dropdown_item, licentaGrupaSerie33Items);
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
                if(checkboxLicenta.isChecked()) {
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
                EmptyEditTextAfterDataInsert();
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

            SQLiteDataBaseQueryHolder = "INSERT INTO "+SQLiteHelper.TABLE_NAME+" (nume,prenume,email,password,an,serie,grupa,taxa,bursa,an_inscriere,forma_de_invatamant,tip_studii)" +
                    " VALUES('"+NumeHolder+"', '"+PrenumeHolder+"', '"+EmailHolder+"', '"+PasswordHolder+"', '"+spinnerStudentAn.getSelectedItem()+"', '"+spinnerStudentSerie.getSelectedItem()+"', '"+spinnerStudentGrupa.getSelectedItem()+"', '"+checkboxTaxa.isChecked()+"'," +
                    "'"+checkboxBursa.isChecked()+"', '"+editStudentAnIncepereHolder+"', '"+formaDeInvatamant+"', '"+tipStudii+"');";

            sqLiteDatabaseObj.execSQL(SQLiteDataBaseQueryHolder);
            sqLiteDatabaseObj.close();

            Toast.makeText(RegisterActivity.this,"User înregistrat cu succes!", Toast.LENGTH_LONG).show();
        }
        else if(EditTextEmptyHolder == true && (int)spinnerTipCont.getSelectedItemPosition() == 2)
        {
            SQLiteDataBaseQueryHolder = "INSERT INTO "+SQLiteHelper.TABLE_NAME+" (nume,prenume,email,password,an,serie,grupa,taxa,bursa,an_inscriere,forma_de_invatamant,tip_studii)" +
                    " VALUES('"+NumeHolder+"', '"+null+"', '"+EmailHolder+"', '"+PasswordHolder+"', '"+null+"', '"+null+"', '"+null+"', '"+null+"'," +
                    "'"+null+"', '"+null+"', '"+null+"', '"+null+"');";

            sqLiteDatabaseObj.execSQL(SQLiteDataBaseQueryHolder);
            sqLiteDatabaseObj.close();

            Toast.makeText(RegisterActivity.this,"User înregistrat cu succes!", Toast.LENGTH_LONG).show();
        }
        else if(EditTextEmptyHolder == true && (int)spinnerTipCont.getSelectedItemPosition() == 1)
        {
            StringBuilder stringBuilderGrupe = new StringBuilder();
            for(int i = 0; i < listGrupe.size() - 1; i ++)
                stringBuilderGrupe.append(grupe[listGrupe.get(i)]). append("; ");
            stringBuilderGrupe.append(grupe[listGrupe.get(listGrupe.size() - 1)]);

            StringBuilder stringBuilderDiscipline = new StringBuilder();
            for(int i = 0; i < listDiscipline.size() - 1; i ++)
                stringBuilderDiscipline.append(discipline[listDiscipline.get(i)]). append("; ");
            stringBuilderDiscipline.append(discipline[listDiscipline.get(listDiscipline.size() - 1)]);

            SQLiteDataBaseQueryHolder = "INSERT INTO "+SQLiteHelper.TABLE_NAME+" (nume,prenume,email,password,an,serie,grupa,taxa,bursa,an_inscriere,forma_de_invatamant,tip_studii)" +
                    " VALUES('"+NumeHolder+"', '"+PrenumeHolder+"', '"+EmailHolder+"', '"+PasswordHolder+"', '"+null+"', '"+null+"', '"+stringBuilderGrupe+"', '"+null+"'," +
                    "'"+null+"', '"+spinnerProfesorPozitie.getSelectedItem()+"', '"+null+"', '"+stringBuilderDiscipline+"');";

            sqLiteDatabaseObj.execSQL(SQLiteDataBaseQueryHolder);
            sqLiteDatabaseObj.close();

            Toast.makeText(RegisterActivity.this,"User înregistrat cu succes!", Toast.LENGTH_LONG).show();
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
        Intent intent = new Intent(RegisterActivity.this, DashboardActivity.class);
        startActivity(intent);
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
        else if(((int)spinnerTipCont.getSelectedItemPosition()) == 2 && (TextUtils.isEmpty(NumeHolder) || TextUtils.isEmpty(EmailHolder) || TextUtils.isEmpty(PasswordHolder)))
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
        // Calling method to check final result and insert data into SQLite database.
        CheckFinalResult();
    }
    public void CheckFinalResult(){
        if(F_Result.equalsIgnoreCase("Email Found"))
            Toast.makeText(RegisterActivity.this,"Email deja utilizat!",Toast.LENGTH_LONG).show();
        else InsertDataIntoSQLiteDatabase();
        F_Result = "Not_Found" ;
    }
}