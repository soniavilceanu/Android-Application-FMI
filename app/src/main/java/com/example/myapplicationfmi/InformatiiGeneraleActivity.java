package com.example.myapplicationfmi;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.Toast;

import com.example.myapplicationfmi.Modals.CourseModal;
import com.example.myapplicationfmi.Modals.GroupModal;
import com.example.myapplicationfmi.Modals.NotificationModal;
import com.example.myapplicationfmi.Modals.ProfessorModal;
import com.example.myapplicationfmi.Modals.ProfessorSubjectModal;
import com.example.myapplicationfmi.Modals.SetariNotificariModal;
import com.example.myapplicationfmi.Modals.StudentModal;
import com.example.myapplicationfmi.Modals.SubjectModal;
import com.example.myapplicationfmi.beans.SetariNotificari;
import com.example.myapplicationfmi.beans.Student;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

public class InformatiiGeneraleActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;
    MaterialToolbar materialToolbar;
    private MaterialToolbar topAppBar;
    public static final String SHARED_PREFS = "sharedPrefs";
    private Menu menu;
    private MyRoomDatabase myRoomDatabase;
    private StudentModal studentModal;
    private GroupModal groupModal;
    private CourseModal courseModal;
    private NotificationModal notificationModal;
    private ProfessorModal professorModal;
    private SubjectModal subjectModal;
    private ProfessorSubjectModal professorSubjectModal;
    private SetariNotificariModal setariNotificariModal;
    private EditText addASMIEmail;
    private LinearLayout adminAdaugareStudentInASMI;
    private Button buttonAdaugaASMI;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(drawerToggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        this.menu = menu;

        MenuItem creareContNouItem = menu.findItem(R.id.creareContNou);

        if (MainActivity.USER_TYPE != 1) {
            creareContNouItem.setVisible(false);
        } else {
            creareContNouItem.setVisible(true);
        }
        return true;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informatii_generale);

        myRoomDatabase = MyRoomDatabase.getInstance(this);
        studentModal = new ViewModelProvider(this).get(StudentModal.class);
        groupModal = new ViewModelProvider(this).get(GroupModal.class);
        courseModal = new ViewModelProvider(this).get(CourseModal.class);
        notificationModal = new ViewModelProvider(this).get(NotificationModal.class);
        professorModal = new ViewModelProvider(this).get(ProfessorModal.class);
        subjectModal = new ViewModelProvider(this).get(SubjectModal.class);
        professorSubjectModal = new ViewModelProvider(this).get(ProfessorSubjectModal.class);
        setariNotificariModal =  new ViewModelProvider(this).get(SetariNotificariModal.class);

        navigationView = findViewById(R.id.nav_view);
        topAppBar = findViewById(R.id.topAppBar);
        addASMIEmail = findViewById(R.id.addASMIEmail);
        adminAdaugareStudentInASMI = findViewById(R.id.adminAdaugareStudentInASMI);
        buttonAdaugaASMI = findViewById(R.id.buttonAdaugaASMI);

        Menu menu = navigationView.getMenu();
        MenuItem creareContNouItem = menu.findItem(R.id.creareContNou);
        Button buttonEditProgSecretariat = findViewById(R.id.buttonEditProgSecretariat);
        Button buttonEditProgCantina = findViewById(R.id.buttonEditProgCantina);
        Button buttonEditProgBiblioteca = findViewById(R.id.buttonEditProgBiblioteca);
        Button buttonStiati = findViewById(R.id.buttonStiati);
        Button buttonAlteInfo = findViewById(R.id.buttonAlteInfo);
        DrawerLayout activity_dashboard = findViewById(R.id.activity_dashboard);
        LinearLayout programCantina = findViewById(R.id.programCantina);
        LinearLayout programSecretariat = findViewById(R.id.programSecretariat);
        LinearLayout programBiblioteca = findViewById(R.id.programBiblioteca);


        LinearLayout tabelSecretariat = findViewById(R.id.tabelSecretariat);
        LinearLayout tabelCantina =  findViewById(R.id.tabelCantina);
        LinearLayout tabelBiblioteca =  findViewById(R.id.tabelBiblioteca);

        enableOrDisableEditTexts(tabelSecretariat, false);
        enableOrDisableEditTexts(tabelCantina, false);
        enableOrDisableEditTexts(tabelBiblioteca, false);

        SharedPreferences sharedPreferences = getSharedPreferences(DashboardActivity.SHARED_PREFS, MODE_PRIVATE);
        String emailHolder = sharedPreferences.getString("email", "");

        EditText progInceputLuni = findViewById(R.id.progInceputLuni);
        EditText progInceputMarti = findViewById(R.id.progInceputMarti);
        EditText progInceputMiercuri = findViewById(R.id.progInceputMiercuri);
        EditText progInceputJoi = findViewById(R.id.progInceputJoi);
        EditText progInceputVineri = findViewById(R.id.progInceputVineri);

        EditText progInceputLuniC = findViewById(R.id.progInceputLuniC);
        EditText progInceputMartiC = findViewById(R.id.progInceputMartiC);
        EditText progInceputMiercuriC = findViewById(R.id.progInceputMiercuriC);
        EditText progInceputJoiC = findViewById(R.id.progInceputJoiC);
        EditText progInceputVineriC = findViewById(R.id.progInceputVineriC);

        EditText progInceputLuniB = findViewById(R.id.progInceputLuniB);
        EditText progInceputMartiB = findViewById(R.id.progInceputMartiB);
        EditText progInceputMiercuriB = findViewById(R.id.progInceputMiercuriB);
        EditText progInceputJoiB = findViewById(R.id.progInceputJoiB);
        EditText progInceputVineriB = findViewById(R.id.progInceputVineriB);


        EditText progSfarsitLuni = findViewById(R.id.progSfarsitLuni);
        EditText progSfarsitMarti = findViewById(R.id.progSfarsitMarti);
        EditText progSfarsitMiercuri = findViewById(R.id.progSfarsitMiercuri);
        EditText progSfarsitJoi = findViewById(R.id.progSfarsitJoi);
        EditText progSfarsitVineri = findViewById(R.id.progSfarsitVineri);

        EditText progSfarsitLuniC = findViewById(R.id.progSfarsitLuniC);
        EditText progSfarsitMartiC = findViewById(R.id.progSfarsitMartiC);
        EditText progSfarsitMiercuriC = findViewById(R.id.progSfarsitMiercuriC);
        EditText progSfarsitJoiC = findViewById(R.id.progSfarsitJoiC);
        EditText progSfarsitVineriC = findViewById(R.id.progSfarsitVineriC);

        EditText progSfarsitLuniB = findViewById(R.id.progSfarsitLuniB);
        EditText progSfarsitMartiB = findViewById(R.id.progSfarsitMartiB);
        EditText progSfarsitMiercuriB = findViewById(R.id.progSfarsitMiercuriB);
        EditText progSfarsitJoiB = findViewById(R.id.progSfarsitJoiB);
        EditText progSfarsitVineriB = findViewById(R.id.progSfarsitVineriB);

        progInceputLuni.setText(sharedPreferences.getString("progInceputLuni", ""));
        progInceputMarti.setText(sharedPreferences.getString("progInceputMarti", ""));
        progInceputMiercuri.setText(sharedPreferences.getString("progInceputMiercuri", ""));
        progInceputJoi.setText(sharedPreferences.getString("progInceputJoi", ""));
        progInceputVineri.setText(sharedPreferences.getString("progInceputVineri", ""));

        progInceputLuniC.setText(sharedPreferences.getString("progInceputLuniC", ""));
        progInceputMartiC.setText(sharedPreferences.getString("progInceputMartiC", ""));
        progInceputMiercuriC.setText(sharedPreferences.getString("progInceputMiercuriC", ""));
        progInceputJoiC.setText(sharedPreferences.getString("progInceputJoiC", ""));
        progInceputVineriC.setText(sharedPreferences.getString("progInceputVineriC", ""));

        progInceputLuniB.setText(sharedPreferences.getString("progInceputLuniB", ""));
        progInceputMartiB.setText(sharedPreferences.getString("progInceputMartiB", ""));
        progInceputMiercuriB.setText(sharedPreferences.getString("progInceputMiercuriB", ""));
        progInceputJoiB.setText(sharedPreferences.getString("progInceputJoiB", ""));
        progInceputVineriB.setText(sharedPreferences.getString("progInceputVineriB", ""));

        progSfarsitLuni.setText(sharedPreferences.getString("progSfarsitLuni", ""));
        progSfarsitMarti.setText(sharedPreferences.getString("progSfarsitMarti", ""));
        progSfarsitMiercuri.setText(sharedPreferences.getString("progSfarsitMiercuri", ""));
        progSfarsitJoi.setText(sharedPreferences.getString("progSfarsitJoi", ""));
        progSfarsitVineri.setText(sharedPreferences.getString("progSfarsitVineri", ""));

        progSfarsitLuniC.setText(sharedPreferences.getString("progSfarsitLuniC", ""));
        progSfarsitMartiC.setText(sharedPreferences.getString("progSfarsitMartiC", ""));
        progSfarsitMiercuriC.setText(sharedPreferences.getString("progSfarsitMiercuriC", ""));
        progSfarsitJoiC.setText(sharedPreferences.getString("progSfarsitJoiC", ""));
        progSfarsitVineriC.setText(sharedPreferences.getString("progSfarsitVineriC", ""));

        progSfarsitLuniB.setText(sharedPreferences.getString("progSfarsitLuniB", ""));
        progSfarsitMartiB.setText(sharedPreferences.getString("progSfarsitMartiB", ""));
        progSfarsitMiercuriB.setText(sharedPreferences.getString("progSfarsitMiercuriB", ""));
        progSfarsitJoiB.setText(sharedPreferences.getString("progSfarsitJoiB", ""));
        progSfarsitVineriB.setText(sharedPreferences.getString("progSfarsitVineriB", ""));

        EditText stiatiCaEditText = findViewById(R.id.stiatiCaEditText);
        stiatiCaEditText.setEnabled(false);
        stiatiCaEditText.setText(sharedPreferences.getString("stiatiCa", ""));

        EditText alteInfoEditText = findViewById(R.id.alteInfoEditText);
        alteInfoEditText.setEnabled(false);
        alteInfoEditText.setText(sharedPreferences.getString("alteInfo", ""));

        if (MainActivity.USER_TYPE != 1) {
            creareContNouItem.setVisible(false);
            buttonEditProgSecretariat.setVisibility(View.GONE);
            buttonEditProgCantina.setVisibility(View.GONE);
            buttonEditProgBiblioteca.setVisibility(View.GONE);
            buttonStiati.setVisibility(View.GONE);
            buttonAlteInfo.setVisibility(View.GONE);
            adminAdaugareStudentInASMI.setVisibility(View.GONE);
        } else {
            // e admin
            creareContNouItem.setVisible(true);
            buttonEditProgSecretariat.setVisibility(View.VISIBLE);
            buttonEditProgCantina.setVisibility(View.VISIBLE);
            buttonEditProgBiblioteca.setVisibility(View.VISIBLE);
            buttonStiati.setVisibility(View.VISIBLE);
            buttonAlteInfo.setVisibility(View.VISIBLE);
            adminAdaugareStudentInASMI.setVisibility(View.VISIBLE);

            buttonAdaugaASMI.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    StudentsSQLiteHelper dbHelper = new StudentsSQLiteHelper(InformatiiGeneraleActivity.this);

                    Student student = dbHelper.getStudentByEmail(String.valueOf(addASMIEmail.getText()));

                    if (student != null) {
                        dbHelper.updateStudentAsmi(student.getStudentId(), true);
                        addASMIEmail.setText("");
                        setariNotificariModal.getSetariNotificariByStudentIdAndType(student.getStudentId(), "voluntariatInscris").observe(InformatiiGeneraleActivity.this, new Observer<SetariNotificari>() {
                            @Override
                            public void onChanged(SetariNotificari setariNotificari) {
                                if(setariNotificari != null){
                                    setariNotificari.setVreaNotificare(true);
                                    setariNotificariModal.update(setariNotificari);
                                }
                            }
                        });
                    }
                    else{
                        Toast.makeText(InformatiiGeneraleActivity.this, "Nu s-a găsit studentul cu acest email", Toast.LENGTH_LONG).show();
                    }

                    dbHelper.close();




//                    studentModal.getStudentByEmail(String.valueOf(addASMIEmail.getText())).observe(InformatiiGeneraleActivity.this, student -> {
//                            if(student != null){
//                                student.setAsmi(true);
//                                studentModal.update(student);
//                                addASMIEmail.setText("");
//                            }
//                            else{
//                                Toast.makeText(InformatiiGeneraleActivity.this, "Nu s-a găsit studentul cu acest email", Toast.LENGTH_LONG).show();
//                            }
//                    });
                }
            });

        }

        final boolean[] isEditMode = {true};

        buttonEditProgSecretariat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEditMode[0]){
                    buttonEditProgSecretariat.setBackgroundResource(R.drawable.baseline_check_24);
                    enableOrDisableEditTexts(tabelSecretariat, true);
                }
                else{
                    buttonEditProgSecretariat.setBackgroundResource(R.drawable.baseline_edit_24);

                    // salvare
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("progInceputLuni", String.valueOf(progInceputLuni.getText()));
                    editor.putString("progInceputMarti", String.valueOf(progInceputMarti.getText()));
                    editor.putString("progInceputMiercuri", String.valueOf(progInceputMiercuri.getText()));
                    editor.putString("progInceputJoi", String.valueOf(progInceputJoi.getText()));
                    editor.putString("progInceputVineri", String.valueOf(progInceputVineri.getText()));
                    editor.putString("progSfarsitLuni", String.valueOf(progSfarsitLuni.getText()));
                    editor.putString("progSfarsitMarti", String.valueOf(progSfarsitMarti.getText()));
                    editor.putString("progSfarsitMiercuri", String.valueOf(progSfarsitMiercuri.getText()));
                    editor.putString("progSfarsitJoi", String.valueOf(progSfarsitJoi.getText()));
                    editor.putString("progSfarsitVineri", String.valueOf(progSfarsitVineri.getText()));


                    editor.apply();

                    enableOrDisableEditTexts(tabelSecretariat, false);
                }
                isEditMode[0] = !isEditMode[0];
            }
        });

        final boolean[] isEditMode2 = {true};

        buttonEditProgCantina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEditMode2[0]){
                    buttonEditProgCantina.setBackgroundResource(R.drawable.baseline_check_24);
                    enableOrDisableEditTexts(tabelCantina, true);
                }
                else{
                    buttonEditProgCantina.setBackgroundResource(R.drawable.baseline_edit_24);

                    // salvare
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("progInceputLuniC", String.valueOf(progInceputLuniC.getText()));
                    editor.putString("progInceputMartiC", String.valueOf(progInceputMartiC.getText()));
                    editor.putString("progInceputMiercuriC", String.valueOf(progInceputMiercuriC.getText()));
                    editor.putString("progInceputJoiC", String.valueOf(progInceputJoiC.getText()));
                    editor.putString("progInceputVineriC", String.valueOf(progInceputVineriC.getText()));
                    editor.putString("progSfarsitLuniC", String.valueOf(progSfarsitLuniC.getText()));
                    editor.putString("progSfarsitMartiC", String.valueOf(progSfarsitMartiC.getText()));
                    editor.putString("progSfarsitMiercuriC", String.valueOf(progSfarsitMiercuriC.getText()));
                    editor.putString("progSfarsitJoiC", String.valueOf(progSfarsitJoiC.getText()));
                    editor.putString("progSfarsitVineriC", String.valueOf(progSfarsitVineriC.getText()));


                    editor.apply();

                    enableOrDisableEditTexts(tabelCantina, false);
                }
                isEditMode2[0] = !isEditMode2[0];
            }
        });

        final boolean[] isEditMode3 = {true};

        buttonEditProgBiblioteca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEditMode3[0]){
                    buttonEditProgBiblioteca.setBackgroundResource(R.drawable.baseline_check_24);
                    enableOrDisableEditTexts(tabelBiblioteca, true);
                }
                else{
                    buttonEditProgBiblioteca.setBackgroundResource(R.drawable.baseline_edit_24);

                    // salvare
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("progInceputLuniB", String.valueOf(progInceputLuniB.getText()));
                    editor.putString("progInceputMartiB", String.valueOf(progInceputMartiB.getText()));
                    editor.putString("progInceputMiercuriB", String.valueOf(progInceputMiercuriB.getText()));
                    editor.putString("progInceputJoiB", String.valueOf(progInceputJoiB.getText()));
                    editor.putString("progInceputVineriB", String.valueOf(progInceputVineriB.getText()));
                    editor.putString("progSfarsitLuniB", String.valueOf(progSfarsitLuniB.getText()));
                    editor.putString("progSfarsitMartiB", String.valueOf(progSfarsitMartiB.getText()));
                    editor.putString("progSfarsitMiercuriB", String.valueOf(progSfarsitMiercuriB.getText()));
                    editor.putString("progSfarsitJoiB", String.valueOf(progSfarsitJoiB.getText()));
                    editor.putString("progSfarsitVineriB", String.valueOf(progSfarsitVineriB.getText()));
                    editor.apply();

                    enableOrDisableEditTexts(tabelBiblioteca, false);
                }
                isEditMode3[0] = !isEditMode3[0];
            }
        });

        final boolean[] isEditMode4 = {true};
        buttonStiati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEditMode4[0]){
                    buttonStiati.setBackgroundResource(R.drawable.baseline_check_24);
                    stiatiCaEditText.setEnabled(true);
                }
                else{
                    buttonStiati.setBackgroundResource(R.drawable.baseline_edit_24);

                    // salvare
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("stiatiCa", String.valueOf(stiatiCaEditText.getText()));
                    editor.apply();

                    stiatiCaEditText.setEnabled(false);
                }
                isEditMode4[0] = !isEditMode4[0];
            }
        });

        final boolean[] isEditMode5 = {true};
        buttonAlteInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEditMode5[0]){
                    buttonAlteInfo.setBackgroundResource(R.drawable.baseline_check_24);
                    alteInfoEditText.setEnabled(true);
                }
                else{
                    buttonAlteInfo.setBackgroundResource(R.drawable.baseline_edit_24);

                    // salvare
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("alteInfo", String.valueOf(alteInfoEditText.getText()));
                    editor.apply();

                    alteInfoEditText.setEnabled(false);
                }
                isEditMode5[0] = !isEditMode5[0];
            }
        });


        drawerLayout = findViewById(R.id.activity_dashboard);
        materialToolbar = findViewById(R.id.topAppBar);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, materialToolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        Menu menu2 = materialToolbar.getMenu();
        MenuItem search = menu2.findItem(R.id.cautare);
        search.setVisible(false);

        MenuItem notificari = menu2.findItem(R.id.setari);
        if(MainActivity.USER_TYPE == 1 || MainActivity.USER_TYPE == 3)
            notificari.setVisible(false);
        else notificari.setVisible(true);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.carnet) {
                    Intent intent = new Intent(InformatiiGeneraleActivity.this, CarnetActivity.class);
                    startActivity(intent);
                    finish();
                } else if (itemId == R.id.orar) {
                    Intent intent = new Intent(InformatiiGeneraleActivity.this, OrarActivity.class);
                    startActivity(intent);
                    finish();
                } else if (itemId == R.id.calendar) {
                    Intent intent = new Intent(InformatiiGeneraleActivity.this, CalendarAcademicActivity.class);
                    startActivity(intent);
                    finish();
                } else if (itemId == R.id.activitiesAnnouncements) {
                    Intent intent = new Intent(InformatiiGeneraleActivity.this, DashboardActivity.class);
                    startActivity(intent);
                    finish();
                } else if (itemId == R.id.internshipVoluntariat) {
                    Intent intent = new Intent(InformatiiGeneraleActivity.this, ExtracurricularActivity.class);
                    startActivity(intent);
                    finish();
                } else if (itemId == R.id.informatii) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                else if(itemId == R.id.creareContNou) {
                    Intent intent = new Intent(InformatiiGeneraleActivity.this, RegisterActivity.class);
                    startActivity(intent);
                    finish();
                }
                return false;
            }
        });


        materialToolbar.setOnMenuItemClickListener(new MaterialToolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if(item.getItemId() == R.id.profil){
                    Intent intent = new Intent(InformatiiGeneraleActivity.this, ProfileActivity.class);
                    intent.putExtra("previousActivity", "InformatiiGeneraleActivity");
                    startActivity(intent);
                    finish();
                }
                if(item.getItemId() == R.id.setari){
                    Intent intent = new Intent(InformatiiGeneraleActivity.this, NotificationActivity.class);
                    intent.putExtra("previousActivity", "InformatiiGeneraleActivity");
                    startActivity(intent);
                    finish();
                }
                if(item.getItemId() == R.id.deconectare){
                    SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("name", "");
                    editor.putString("emailConectat","");
                    editor.apply();

                    Intent intent = new Intent(InformatiiGeneraleActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen((GravityCompat.START)))
            drawerLayout.closeDrawer(GravityCompat.START);
        else super.onBackPressed();
    }

//    private void disableEditTexts(View view) {
//        if (view instanceof EditText) {
//            EditText editText = (EditText) view;
//            editText.setEnabled(false);
//            editText.setTextColor(Color.parseColor("#a3a3a3"));
//        } else if (view instanceof ViewGroup) {
//            ViewGroup viewGroup = (ViewGroup) view;
//            for (int i = 0; i < viewGroup.getChildCount(); i++) {
//                disableEditTexts(viewGroup.getChildAt(i));
//            }
//        }
//    }

//    private void enableEditTexts(View view) {
//        if (view instanceof EditText) {
//            EditText editText = (EditText) view;
//            editText.setEnabled(true);
//            editText.setTextColor(Color.parseColor("#000000"));
//        } else if (view instanceof ViewGroup) {
//            ViewGroup viewGroup = (ViewGroup) view;
//            for (int i = 0; i < viewGroup.getChildCount(); i++) {
//                enableEditTexts(viewGroup.getChildAt(i));
//            }
//        }
//    }
private void enableOrDisableEditTexts(View view, boolean enable) {
    if (view instanceof EditText) {
        EditText editText = (EditText) view;
        editText.setEnabled(enable);
        if(enable)
            editText.setBackgroundResource(R.drawable.lavender_border_v3);
        else  editText.setBackgroundResource(R.drawable.lavender_border_v6);
    } else if (view instanceof ViewGroup) {
        ViewGroup viewGroup = (ViewGroup) view;
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            enableOrDisableEditTexts(viewGroup.getChildAt(i), enable);
        }
    }
}

}