package com.example.myapplicationfmi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.example.myapplicationfmi.Modals.CourseModal;
import com.example.myapplicationfmi.Modals.DashTabModal;
import com.example.myapplicationfmi.Modals.GroupModal;
import com.example.myapplicationfmi.Modals.NotificationModal;
import com.example.myapplicationfmi.Modals.ProfessorModal;
import com.example.myapplicationfmi.Modals.ProfessorSubjectModal;
import com.example.myapplicationfmi.Modals.StudentModal;
import com.example.myapplicationfmi.Modals.SubjectModal;
import com.example.myapplicationfmi.beans.Course;
import com.example.myapplicationfmi.beans.Group;
import com.example.myapplicationfmi.beans.Professor;
import com.example.myapplicationfmi.beans.Subject;
import com.example.myapplicationfmi.beans.SubjectWithProfessors;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class OrarActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;
    MaterialToolbar materialToolbar;
    private MaterialToolbar topAppBar;
    public static final String SHARED_PREFS = "sharedPrefs";
    private Menu menu;
    private Spinner spinnerSelecteazaGrupa, spinnerProfesor, spinnerMaterie, spinnerSemigrupa;
    private LinearLayout editOrarInfoTab;
    private Button orarTabClose, addOrarInfo;
    private CheckBox checkbox1, checkbox2;
    private String[] grupe;
    private String[] profesori;
    private List<Long> professorIds;
    private List<Long> grupeIds;
    private String[] materii;
    private String[] semigrupe = new String[]{"Toată grupa", "Semigr. 1", "Semigr. 2"};
    private List<Long> materiiIds;
    private TextView materieSiGrupaTextView, profesorTextView, frecventaTextView, saptamanaTextView;
    private MyRoomDatabase myRoomDatabase;
    private StudentModal studentModal;
    private GroupModal groupModal;
    private CourseModal courseModal;
    private DashTabModal dashTabModal;
    private NotificationModal notificationModal;
    private ProfessorModal professorModal;
    private SubjectModal subjectModal;
    private ProfessorSubjectModal professorSubjectModal;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orar);

        myRoomDatabase = MyRoomDatabase.getInstance(this);
        studentModal = new ViewModelProvider(this).get(StudentModal.class);
        groupModal = new ViewModelProvider(this).get(GroupModal.class);
        courseModal = new ViewModelProvider(this).get(CourseModal.class);
        dashTabModal = new ViewModelProvider(this).get(DashTabModal.class);
        notificationModal = new ViewModelProvider(this).get(NotificationModal.class);
        professorModal = new ViewModelProvider(this).get(ProfessorModal.class);
        subjectModal = new ViewModelProvider(this).get(SubjectModal.class);
        professorSubjectModal = new ViewModelProvider(this).get(ProfessorSubjectModal.class);

        navigationView = findViewById(R.id.nav_view);
        topAppBar = findViewById(R.id.topAppBar);
        editOrarInfoTab = findViewById(R.id.editOrarInfoTab);

        spinnerMaterie = findViewById(R.id.spinnerMaterie);
        spinnerProfesor = findViewById(R.id.spinnerProfesor);
        spinnerSemigrupa = findViewById(R.id.spinnerSemigrupa);

        checkbox1 = findViewById(R.id.checkbox1);
        checkbox2 = findViewById(R.id.checkbox2);
        checkbox1.setChecked(true);
        addOrarInfo = findViewById(R.id.addOrarInfo);

        materieSiGrupaTextView = findViewById(R.id.materieSiGrupaTextView);
        profesorTextView = findViewById(R.id.profesorTextView);
        saptamanaTextView = findViewById(R.id.saptamanaTextView);

        Menu menu = navigationView.getMenu();
        MenuItem creareContNouItem = menu.findItem(R.id.creareContNou);
        if (MainActivity.USER_TYPE != 1) {
            creareContNouItem.setVisible(false);
        } else {
            creareContNouItem.setVisible(true);
        }

        List<TextView> tableCells = Arrays.asList(findViewById(R.id.cell_1_1), findViewById(R.id.cell_1_2), findViewById(R.id.cell_1_3), findViewById(R.id.cell_1_4), findViewById(R.id.cell_1_5), findViewById(R.id.cell_2_1),
                findViewById(R.id.cell_2_2), findViewById(R.id.cell_2_3), findViewById(R.id.cell_2_4), findViewById(R.id.cell_2_5), findViewById(R.id.cell_3_1), findViewById(R.id.cell_3_2), findViewById(R.id.cell_3_3),
                findViewById(R.id.cell_3_4), findViewById(R.id.cell_3_5), findViewById(R.id.cell_4_1), findViewById(R.id.cell_4_2), findViewById(R.id.cell_4_3), findViewById(R.id.cell_4_4), findViewById(R.id.cell_4_5),
                findViewById(R.id.cell_5_1), findViewById(R.id.cell_5_2), findViewById(R.id.cell_5_3), findViewById(R.id.cell_5_4), findViewById(R.id.cell_5_5), findViewById(R.id.cell_5_5), findViewById(R.id.cell_5_6),
                findViewById(R.id.cell_1_6), findViewById(R.id.cell_2_6), findViewById(R.id.cell_3_6), findViewById(R.id.cell_4_6));

        for(int i = 0; i < tableCells.size(); i ++){
            int finalI = i;
            tableCells.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (MainActivity.USER_TYPE == 1) {
                        editOrarInfoTab.setVisibility(View.VISIBLE);

                        subjectModal.getAllSubjects().observe(OrarActivity.this, new Observer<List<Subject>>() {
                            @Override
                            public void onChanged(List<Subject> subjects) {
                                if(subjects != null) {
                                    materii = subjects.stream()
                                            .map(subject -> String.valueOf(subject.getDenumire()))
                                            .toArray(String[]::new);
                                    materiiIds = subjects.stream()
                                            .map(Subject::getSubjectId)
                                            .collect(Collectors.toList());

                                    ArrayAdapter<String> adapterMateriiItems = new ArrayAdapter<>(OrarActivity.this, android.R.layout.simple_spinner_dropdown_item, materii);
                                    spinnerMaterie.setAdapter(adapterMateriiItems);
                                }
                            }
                        });

//                        professorModal.getAllProfessors().observe(OrarActivity.this, new Observer<List<Professor>>() {
//                            @Override
//                            public void onChanged(List<Professor> professors) {
//                                if(professors != null) {
//                                    profesori = professors.stream()
//                                            .map(professor -> String.valueOf(professor.getNume() + " " + professor.getPrenume()))
//                                            .toArray(String[]::new);
//                                    professorIds = professors.stream()
//                                            .map(Professor::getProfessorId)
//                                            .collect(Collectors.toList());
//
//                                    ArrayAdapter<String> adapterProfessorItems = new ArrayAdapter<>(OrarActivity.this, android.R.layout.simple_spinner_dropdown_item, profesori);
//                                    spinnerProfesor = findViewById(R.id.spinnerProfesor);
//                                    spinnerProfesor.setAdapter(adapterProfessorItems);
//                                }
//                            }
//                        });


                        /**
                         * randul de jos trebuie on click listener pe spinner
                          */



                        subjectModal.getSubjectWithProfessorsById(materiiIds.get((int) spinnerMaterie.getSelectedItemPosition())).observe(OrarActivity.this, new Observer<SubjectWithProfessors>() {
                            @Override
                            public void onChanged(SubjectWithProfessors subjectWithProfessors) {
                                if (subjectWithProfessors != null) {
                                    List<Professor> professors = subjectWithProfessors.professors;
                                    profesori = professors.stream()
                                            .map(professor -> String.valueOf(professor.getNume() + " " + professor.getPrenume()))
                                            .toArray(String[]::new);
                                    professorIds = professors.stream()
                                            .map(Professor::getProfessorId)
                                            .collect(Collectors.toList());
                                    ArrayAdapter<String> adapterProfessorItems = new ArrayAdapter<>(OrarActivity.this, android.R.layout.simple_spinner_dropdown_item, profesori);
                                    spinnerProfesor.setAdapter(adapterProfessorItems);
                                }
                            }
                        });

                        checkbox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                checkbox2.setChecked(false);
                            }
                        });
                        checkbox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                checkbox1.setChecked(false);
                            }
                        });

                        ArrayAdapter<String> adapteSemigrupaItems = new ArrayAdapter<>(OrarActivity.this, android.R.layout.simple_spinner_dropdown_item, semigrupe);
                        spinnerSemigrupa.setAdapter(adapteSemigrupaItems);
       /////////////////////////////

                    addOrarInfo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Course course = new Course();
                            course.setGroupId(grupeIds.get(spinnerSelecteazaGrupa.getSelectedItemPosition()));
                            course.setSubjectId(materiiIds.get(spinnerMaterie.getSelectedItemPosition()));
                            course.setProfessorId(professorIds.get(spinnerProfesor.getSelectedItemPosition()));
                            course.setFrecventa(checkbox1.isChecked() && !checkbox2.isChecked() ? 1 : 2);
                            course.setSemigrupa(spinnerSemigrupa.getSelectedItemPosition());


                            course.setZi(String.valueOf(finalI / 6));
                            course.setIntervalOrar(finalI % 6);

                            courseModal.insert(course);
                        }
                    });
                    }
                    courseModal.getCourseByGroupIdZiAndIntervalOrar(grupeIds.get(spinnerSelecteazaGrupa.getSelectedItemPosition()), String.valueOf(finalI / 6), finalI % 6).observe(OrarActivity.this, new Observer<Course>() {
                        @Override
                        public void onChanged(Course course) {
                            if (course != null) {
                                materieSiGrupaTextView.setText(materieSiGrupaTextView.getText() + " " + materii[Math.toIntExact(course.getSubjectId())] + " ");
                                if(course.getSemigrupa() != 0)
                                    materieSiGrupaTextView.setText(materieSiGrupaTextView.getText() + semigrupe[course.getSemigrupa()]);
                                profesorTextView.setText(profesorTextView.getText() + " " + profesori[Math.toIntExact(course.getSubjectId())]);
                                saptamanaTextView.setText(saptamanaTextView.getText() + " " + (course.getFrecventa() == 1 ? course.getFrecventa() + "/săpt." : "1/" + course.getFrecventa() + "/săpt."));
                            }
                        }
                    });

                    tableCells.get(finalI).setBackgroundColor(Color.parseColor("#8692f7"));

                }//////////////
            });
        }

        orarTabClose = findViewById(R.id.orarTabClose);
        orarTabClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editOrarInfoTab.setVisibility(View.GONE);
            }
        });

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
                    grupeIds = groups.stream()
                            .map(Group::getGroupId)
                            .collect(Collectors.toList());

                    ArrayAdapter<String> adapterGrupaItems = new ArrayAdapter<>(OrarActivity.this, android.R.layout.simple_spinner_dropdown_item, grupe);
                    spinnerSelecteazaGrupa = findViewById(R.id.spinnerSelecteazaGrupa);
                    spinnerSelecteazaGrupa.setAdapter(adapterGrupaItems);
                }
            }
        });



//        spinnerSelecteazaGrupa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> parentView) {
//            }
//        });

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
                    Toast.makeText(OrarActivity.this, "Carnet selected", Toast.LENGTH_SHORT).show();
                } else if (itemId == R.id.orar) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else if (itemId == R.id.calendar) {
                    Toast.makeText(OrarActivity.this, "Calendar selected", Toast.LENGTH_SHORT).show();
                } else if (itemId == R.id.activitiesAnnouncements) {
                    Intent intent = new Intent(OrarActivity.this, DashboardActivity.class);
                    startActivity(intent);
                    finish();
                } else if (itemId == R.id.internshipVoluntariat) {
                    Intent intent = new Intent(OrarActivity.this, ExtracurricularActivity.class);
                    startActivity(intent);
                    finish();
                } else if (itemId == R.id.cantina) {
                    Toast.makeText(OrarActivity.this, "Cantina selected", Toast.LENGTH_SHORT).show();
                } else if (itemId == R.id.informatii) {
                    Toast.makeText(OrarActivity.this, "Informatii selected", Toast.LENGTH_SHORT).show();
                }
                else if(itemId == R.id.creareContNou) {
                    Intent intent = new Intent(OrarActivity.this, RegisterActivity.class);
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
                    Intent intent = new Intent(OrarActivity.this, ProfileActivity.class);
                    intent.putExtra("previousActivity", "OrarActivity");
                    startActivity(intent);
                    finish();
                }
                if(item.getItemId() == R.id.setari){
                    Toast.makeText(OrarActivity.this, "Settings selected", Toast.LENGTH_SHORT).show();
                }
                if(item.getItemId() == R.id.deconectare){
                    SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("name", "");
                    editor.apply();

                    Intent intent = new Intent(OrarActivity.this, MainActivity.class);
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