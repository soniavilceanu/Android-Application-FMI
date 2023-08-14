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
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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

        if (MainActivity.USER_TYPE != 1) {
            creareContNouItem.setVisible(false);
            adminAdaugareStudentInASMI.setVisibility(View.GONE);
        } else {
            // e admin
            creareContNouItem.setVisible(true);
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

        SharedPreferences sharedPreferences = getSharedPreferences(DashboardActivity.SHARED_PREFS, MODE_PRIVATE);
        String emailHolder = sharedPreferences.getString("email", "");



        drawerLayout = findViewById(R.id.activity_dashboard);
        materialToolbar = findViewById(R.id.topAppBar);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, materialToolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        Menu menu2 = materialToolbar.getMenu();
        MenuItem search = menu2.findItem(R.id.cautare);
        search.setVisible(false);

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
}