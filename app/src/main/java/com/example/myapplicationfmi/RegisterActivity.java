package com.example.myapplicationfmi;


import android.content.Context;
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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {
    EditText Email, Password, Nume, Prenume, editStudentAnIncepere;
    Button Register;
    String NumeHolder, PrenumeHolder, EmailHolder, PasswordHolder, editStudentAnIncepereHolder;
    Boolean EditTextEmptyHolder;
    SQLiteDatabase sqLiteDatabaseObj;
    String SQLiteDataBaseQueryHolder ;
    SQLiteHelper sqLiteHelper;
    Cursor cursor;
    String F_Result = "Not_Found";
    Spinner spinnerTipCont, spinnerStudentAn, spinnerStudentSerie, spinnerStudentGrupa;
//    Spinner spinnerStudentProfil;
    CheckBox checkboxLicenta, checkboxMaster, checkboxIF, checkboxIFR, checkboxID, checkboxTaxa, checkboxBursa;
    LinearLayout layoutStudent, layoutProfesor;
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

        //spinnerStudentProfil = findViewById(R.id.spinnerStudentProfil);

        String[] tipContItems = {"STUDENT", "PROFESOR"};
        ArrayAdapter<String> adaptertipCont = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, tipContItems);
        spinnerTipCont = findViewById(R.id.spinnerTipCont);
        spinnerTipCont.setAdapter(adaptertipCont);

        spinnerTipCont.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if(position == 1) {
                    layoutStudent.setVisibility(View.GONE);
                    layoutProfesor.setVisibility(View.VISIBLE);
                }
                if(position == 0) {
                    layoutStudent.setVisibility(View.VISIBLE);
                    layoutProfesor.setVisibility(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
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
                // your code here
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
                // your code here
            }
        });


        sqLiteHelper = new SQLiteHelper(this);
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Creating SQLite database if doesn't exists
                SQLiteDataBaseBuild();
                // Creating SQLite table if doesn't exists.
                SQLiteTableBuild();
                // Checking EditText is empty or Not.
                CheckEditTextStatus();
                // Method to check Email is already exists or not.
                CheckingEmailAlreadyExistsOrNot();
                // Empty EditText After done inserting process.
                EmptyEditTextAfterDataInsert();
            }
        });
    }
    // SQLite database build method.
    public void SQLiteDataBaseBuild(){
        sqLiteDatabaseObj = openOrCreateDatabase(SQLiteHelper.DATABASE_NAME, Context.MODE_PRIVATE, null);
    }
    // SQLite table build method.
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
    // Insert data into SQLite database method.
    public void InsertDataIntoSQLiteDatabase(){
        // If editText is not empty then this block will executed.
        if(EditTextEmptyHolder == true)
        {
            String formaDeInvatamant = null;
            String tipStudii = null;
            if(checkboxIFR.isChecked()) formaDeInvatamant = "IFR";
            else if(checkboxIF.isChecked()) formaDeInvatamant = "IF";
            else if(checkboxID.isChecked()) formaDeInvatamant = "ID";

            if(checkboxLicenta.isChecked()) tipStudii = "Licenta";
            else if(checkboxMaster.isChecked()) tipStudii = "Master";

            // SQLite query to insert data into table.
            SQLiteDataBaseQueryHolder = "INSERT INTO "+SQLiteHelper.TABLE_NAME+" (nume,prenume,email,password,an,serie,grupa,taxa,bursa,an_inscriere,forma_de_invatamant,tip_studii)" +
                    " VALUES('"+NumeHolder+"', '"+PrenumeHolder+"', '"+EmailHolder+"', '"+PasswordHolder+"', '"+spinnerStudentAn.getSelectedItem()+"', '"+spinnerStudentSerie.getSelectedItem()+"', '"+spinnerStudentGrupa.getSelectedItem()+"', '"+checkboxTaxa.isChecked()+"'," +
                    "'"+checkboxBursa.isChecked()+"', '"+editStudentAnIncepereHolder+"', '"+formaDeInvatamant+"', '"+tipStudii+"');";

            sqLiteDatabaseObj.execSQL(SQLiteDataBaseQueryHolder);
            sqLiteDatabaseObj.close();

            Toast.makeText(RegisterActivity.this,"User înregistrat cu succes", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(RegisterActivity.this,"Va rog completati toate datele necesare!", Toast.LENGTH_LONG).show();
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
        // Getting value from All EditText and storing into String Variables.
        NumeHolder = Nume.getText().toString();
        PrenumeHolder = Prenume.getText().toString();
        EmailHolder = Email.getText().toString();
        PasswordHolder = Password.getText().toString();
        editStudentAnIncepereHolder = editStudentAnIncepere.getText().toString();
        if(TextUtils.isEmpty(NumeHolder) || TextUtils.isEmpty(EmailHolder) || TextUtils.isEmpty(PasswordHolder) ||
                TextUtils.isEmpty(editStudentAnIncepereHolder) || (!checkboxIF.isChecked() && !checkboxID.isChecked() && !checkboxIFR.isChecked()) ||
                (!checkboxMaster.isChecked() && !checkboxLicenta.isChecked()))
            EditTextEmptyHolder = false;
        else EditTextEmptyHolder = true;
    }
    // Checking Email is already exists or not.
    public void CheckingEmailAlreadyExistsOrNot(){
        // Opening SQLite database write permission.
        sqLiteDatabaseObj = sqLiteHelper.getWritableDatabase();
        // Adding search email query to cursor.
        cursor = sqLiteDatabaseObj.query(SQLiteHelper.TABLE_NAME, null, " " + SQLiteHelper.Table_Column_3_Email + "=?", new String[]{EmailHolder}, null, null, null);
        while (cursor.moveToNext()) {
            if (cursor.isFirst()) {
                cursor.moveToFirst();
                // If Email already exists then Result variable value set as Email Found.
                F_Result = "Email Found";
                // Closing cursor.
                cursor.close();
            }
        }
        // Calling method to check final result and insert data into SQLite database.
        CheckFinalResult();
    }
    // Checking result
    public void CheckFinalResult(){
        // Checking whether email is already exists or not.
        if(F_Result.equalsIgnoreCase("Email Found"))
        {
            // If email exists then toast msg will display.
            Toast.makeText(RegisterActivity.this,"Email deja utilizat!",Toast.LENGTH_LONG).show();
        }
        else {
            // If email doesn't already exist then user registration details will be entered in SQLite database.
            InsertDataIntoSQLiteDatabase();
        }
        F_Result = "Not_Found" ;
    }
}