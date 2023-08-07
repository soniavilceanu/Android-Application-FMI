package com.example.myapplicationfmi;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;

import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.example.myapplicationfmi.Modals.CalendarModal;
import com.example.myapplicationfmi.Modals.CourseModal;
import com.example.myapplicationfmi.Modals.DashTabModal;
import com.example.myapplicationfmi.Modals.GroupModal;
import com.example.myapplicationfmi.Modals.NoteModal;
import com.example.myapplicationfmi.Modals.NotificationModal;
import com.example.myapplicationfmi.Modals.ProfessorModal;
import com.example.myapplicationfmi.Modals.ProfessorSubjectModal;
import com.example.myapplicationfmi.Modals.StudentModal;
import com.example.myapplicationfmi.Modals.SubjectModal;
import com.example.myapplicationfmi.beans.Calendar;
import com.example.myapplicationfmi.beans.Group;
import com.example.myapplicationfmi.beans.GroupWithStudents;
import com.example.myapplicationfmi.beans.Note;
import com.example.myapplicationfmi.beans.Professor;
import com.example.myapplicationfmi.beans.ProfessorWithGroups;
import com.example.myapplicationfmi.beans.ProfessorWithSubjects;
import com.example.myapplicationfmi.beans.Student;
import com.example.myapplicationfmi.beans.Subject;
import com.example.myapplicationfmi.beans.SubjectWithProfessors;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public class CarnetActivity extends AppCompatActivity {
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
    private DashTabModal dashTabModal;
    private NotificationModal notificationModal;
    private ProfessorModal professorModal;
    private SubjectModal subjectModal;
    private ProfessorSubjectModal professorSubjectModal;
    private CalendarModal calendarModal;
    private NoteModal noteModal;
    private DateTimeFormatter formatter;
    private String emailHolder;
    private TableLayout tableLayout;
    private Spinner spinnerSelecteazaGrupa;
    private Spinner spinnerSelecteazaMateria;
    private List<Long> grupeIds;
    private String[] materii;
    private String[] grupe;
    private List<Long> subjectIds;
    private List<Integer> tableRowEditTextIds;
    private Button buttonAdaugaNote;
    private  List<Student> students;

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
        setContentView(R.layout.activity_carnet);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            formatter = DateTimeFormatter.ofPattern("HH:mm");
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            myRoomDatabase = MyRoomDatabase.getInstance(this);
        }
        studentModal = new ViewModelProvider(this).get(StudentModal.class);
        groupModal = new ViewModelProvider(this).get(GroupModal.class);
        courseModal = new ViewModelProvider(this).get(CourseModal.class);
        dashTabModal = new ViewModelProvider(this).get(DashTabModal.class);
        notificationModal = new ViewModelProvider(this).get(NotificationModal.class);
        professorModal = new ViewModelProvider(this).get(ProfessorModal.class);
        subjectModal = new ViewModelProvider(this).get(SubjectModal.class);
        professorSubjectModal = new ViewModelProvider(this).get(ProfessorSubjectModal.class);
        calendarModal = new ViewModelProvider(this).get(CalendarModal.class);
        noteModal = new ViewModelProvider(this).get(NoteModal.class);

        navigationView = findViewById(R.id.nav_view);
        topAppBar = findViewById(R.id.topAppBar);

        Menu menu = navigationView.getMenu();
        MenuItem creareContNouItem = menu.findItem(R.id.creareContNou);

        tableLayout = findViewById(R.id.tableLayout);
        spinnerSelecteazaMateria = findViewById(R.id.spinnerSelecteazaMateria);
        spinnerSelecteazaGrupa = findViewById(R.id.spinnerSelecteazaGrupa);
        buttonAdaugaNote = findViewById(R.id.buttonAdaugaNote);

        SharedPreferences sharedPreferences = getSharedPreferences(DashboardActivity.SHARED_PREFS, MODE_PRIVATE);
        emailHolder = sharedPreferences.getString("email", "");

        if (MainActivity.USER_TYPE != 1) {
            creareContNouItem.setVisible(false);
        } else {
            creareContNouItem.setVisible(true);
        }


        professorModal.getProfessorIdByEmail(emailHolder).observe(CarnetActivity.this, new Observer<Long>() {
            @Override
            public void onChanged(Long professorId) {
                if (professorId != null) {

                    professorModal.getProfessorWithSubjectsById(professorId).observe(CarnetActivity.this, new Observer<ProfessorWithSubjects>(){
                        public void onChanged(ProfessorWithSubjects professorWithSubjects){
                            if(professorWithSubjects != null){
                                List<Subject> subjects = professorWithSubjects.subjects;
                                materii = subjects.stream()
                                        .map(subject -> String.valueOf(subject.getDenumire()))
                                        .toArray(String[]::new);
                                subjectIds = subjects.stream()
                                        .map(Subject::getSubjectId)
                                        .collect(Collectors.toList());
                                ArrayAdapter<String> adapterMateriiItems = new ArrayAdapter<>(CarnetActivity.this, android.R.layout.simple_spinner_dropdown_item, materii);
                                spinnerSelecteazaMateria.setAdapter(adapterMateriiItems);
                            }
                        }
                    });

                    spinnerSelecteazaMateria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                            courseModal.getGroupsBySubjectIdAndProfessorId(subjectIds.get(spinnerSelecteazaMateria.getSelectedItemPosition()), professorId).observe(CarnetActivity.this, new Observer<List<Group>>() {
                                @Override
                                public void onChanged(List<Group> groups) {
                                    if(groups != null){
                                        grupe = groups.stream()
                                        .map(group -> String.valueOf(group.getNumar()))
                                        .toArray(String[]::new);
                                        grupeIds = groups.stream()
                                                .map(Group::getGroupId)
                                                .collect(Collectors.toList());
                                        ArrayAdapter<String> adapterMateriiItems = new ArrayAdapter<>(CarnetActivity.this, android.R.layout.simple_spinner_dropdown_item, grupe);
                                        spinnerSelecteazaGrupa.setAdapter(adapterMateriiItems);
                                    }
                                }
                            });
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> parentView) {
                        }
                    });

                    spinnerSelecteazaGrupa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                            groupModal.getGroupWithStudentsById(grupeIds.get(spinnerSelecteazaGrupa.getSelectedItemPosition())).observe(CarnetActivity.this, new Observer<GroupWithStudents>() {
                                @Override
                                public void onChanged(GroupWithStudents groupWithStudents) {

                                    int childCount = tableLayout.getChildCount();
                                    for (int i = 1; i < childCount; i++) {
                                        View child = tableLayout.getChildAt(i);
                                        if (child instanceof TableRow) {
                                            tableLayout.removeViewAt(i);
                                        }
                                    }
                                    if (groupWithStudents != null) {
                                        students = groupWithStudents.students;
                                        for(int i = 0; i < students.size(); i ++){
                                            addTableRow(students.get(i).getNume() + " " + students.get(i).getPrenume());
                                        }
                                    }
                                }
                            });
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> parentView) {
                        }
                    });


                    buttonAdaugaNote.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int i;
                            Integer semestru = -1;
                            LocalDate currentDate = null;
                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                                currentDate = LocalDate.now();
                                Month[] monthsToCheck = {Month.OCTOBER, Month.NOVEMBER, Month.DECEMBER, Month.JANUARY, Month.FEBRUARY};
                                if (containsMonth(currentDate.getMonth(), monthsToCheck)) {
                                    semestru = 1;
                                } else {
                                    semestru = 2;
                                }
                            }
                            for (i = 1; i < tableLayout.getChildCount(); i++) {
                                View view = tableLayout.getChildAt(i);
                                if (view instanceof TableRow) {
                                    TableRow row = (TableRow) view;

                                    Note note = new Note();
                                    note.setAn(students.get(i).getAn());
                                    note.setSemestru(semestru);
                                    note.setValoare(((EditText) row.getChildAt(1)).getText().toString());
                                    note.setSubjectId(subjectIds.get(spinnerSelecteazaMateria.getSelectedItemPosition()));
                                    note.setStudentId(students.get(i - 1).getStudentId());

                                    noteModal.insert(note);
                                }
                            }
                        }
                    });
                }
            }
        });










//        professorModal.getProfessorIdByEmail(emailHolder).observe(CarnetActivity.this, new Observer<Long>() {
//            @Override
//            public void onChanged(Long professorId) {
//                if (professorId != null) {
//                    courseModal.getGroupsByProfessorId(professorId).observe(CarnetActivity.this, new Observer<List<Group>>() {
//                        @Override
//                        public void onChanged(List<Group> groups) {
//                            if (groups != null) {
//                                grupe = groups.stream()
//                                        .map(group -> String.valueOf(group.getNumar()))
//                                        .toArray(String[]::new);
//                                grupeIds = groups.stream()
//                                        .map(Group::getGroupId)
//                                        .collect(Collectors.toList());
//                                ArrayAdapter<String> adapterMateriiItems = new ArrayAdapter<>(CarnetActivity.this, android.R.layout.simple_spinner_dropdown_item, grupe);
//                                spinnerSelecteazaGrupa.setAdapter(adapterMateriiItems);
//                            }
//                        }
//                    });
//
//                    spinnerSelecteazaGrupa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                        @Override
//                        public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
//                            courseModal.getSubjectsByGroupIdAndProfessorId(grupeIds.get(spinnerSelecteazaGrupa.getSelectedItemPosition()), professorId).observe(CarnetActivity.this, new Observer<List<Subject>>() {
//                                @Override
//                                public void onChanged(List<Subject> subjects) {
//                                    String[] denumiresArray = new String[subjects.size()];
//                                    subjectIds = new ArrayList<>();
//                                    for (int k = 0; k < subjects.size(); k++) {
//                                        denumiresArray[k] = subjects.get(k).getDenumire();
//                                        subjectIds.add(subjects.get(k).getSubjectId());
//                                    }
//                                    ArrayAdapter<String> adapterMateriiItems = new ArrayAdapter<>(CarnetActivity.this, android.R.layout.simple_spinner_dropdown_item, denumiresArray);
//                                    spinnerSelecteazaMateria.setAdapter(adapterMateriiItems);
//                                }
//                            });
//
//                            groupModal.getGroupWithStudentsById(grupeIds.get(spinnerSelecteazaGrupa.getSelectedItemPosition())).observe(CarnetActivity.this, new Observer<GroupWithStudents>() {
//                                @Override
//                                public void onChanged(GroupWithStudents groupWithStudents) {
//                                    if (groupWithStudents != null) {
//                                      students = groupWithStudents.students;
//                                      for(int i = 0; i < students.size(); i ++){
//                                          addTableRow(students.get(i).getNume() + " " + students.get(i).getPrenume());
//                                      }
//                                    }
//                                }
//                            });
//                        }
//
//                        @Override
//                        public void onNothingSelected(AdapterView<?> parentView) {
//                        }
//                    });
//
//
//                    buttonAdaugaNote.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            int i;
//                            Integer semestru = -1;
//                            LocalDate currentDate = null;
//                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//                                currentDate = LocalDate.now();
//                                Month[] monthsToCheck = {Month.OCTOBER, Month.NOVEMBER, Month.DECEMBER, Month.JANUARY, Month.FEBRUARY};
//                                if (containsMonth(currentDate.getMonth(), monthsToCheck)) {
//                                    semestru = 1;
//                                } else {
//                                    semestru = 2;
//                                }
//                            }
//                            for (i = 1; i < tableLayout.getChildCount(); i++) {
//                                View view = tableLayout.getChildAt(i);
//                                if (view instanceof TableRow) {
//                                    TableRow row = (TableRow) view;
//
//                                    Note note = new Note();
//                                    note.setAn(students.get(i).getAn());
//                                    note.setSemestru(semestru);
//                                    note.setValoare(((EditText) row.getChildAt(1)).getText().toString());
//                                    note.setSubjectId(subjectIds.get(spinnerSelecteazaMateria.getSelectedItemPosition()));
//                                    note.setStudentId(students.get(i - 1).getStudentId());
//
//                                    noteModal.insert(note);
//                                }
//                            }
////                            if(i == tableLayout.getChildCount()){
////                                Note note = new Note();
////                                note.setAn(students.get(i).getAn());
////                                note.setSemestru(semestru);
////                                note.setValoare(((EditText) ((TableRow)tableLayout.getChildAt(i)).getChildAt(1)).getText().toString());
////                                note.setSubjectId(subjectIds.get(spinnerSelecteazaMateria.getSelectedItemPosition()));
////                                note.setStudentId(students.get(i - 1).getStudentId());
////
////                                noteModal.insert(note);
////                            }
//                        }
//                    });
//                }
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
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else if (itemId == R.id.orar) {
                    Intent intent = new Intent(CarnetActivity.this, OrarActivity.class);
                    startActivity(intent);
                    finish();
                } else if (itemId == R.id.calendar) {
                    Intent intent = new Intent(CarnetActivity.this, CalendarAcademicActivity.class);
                    startActivity(intent);
                    finish();
                } else if (itemId == R.id.activitiesAnnouncements) {
                    Intent intent = new Intent(CarnetActivity.this, DashboardActivity.class);
                    startActivity(intent);
                    finish();
                } else if (itemId == R.id.internshipVoluntariat) {
                    Intent intent = new Intent(CarnetActivity.this, ExtracurricularActivity.class);
                    startActivity(intent);
                    finish();
                } else if (itemId == R.id.cantina) {
                } else if (itemId == R.id.informatii) {
                }
                else if(itemId == R.id.creareContNou) {
                    Intent intent = new Intent(CarnetActivity.this, RegisterActivity.class);
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
                    Intent intent = new Intent(CarnetActivity.this, ProfileActivity.class);
                    intent.putExtra("previousActivity", "CarnetActivity");
                    startActivity(intent);
                    finish();
                }
                if(item.getItemId() == R.id.setari){
                }
                if(item.getItemId() == R.id.deconectare){
                    SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("name", "");
                    editor.putString("emailConectat","");
                    editor.apply();

                    Intent intent = new Intent(CarnetActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                return false;
            }
        });
    }

    public void addTableRow(String nume){


        TableRow tableRow = new TableRow(this);
        TableLayout.LayoutParams tableRowParams = new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT
        );
        tableRow.setLayoutParams(tableRowParams);

        TextView textView = new TextView(this);
        TableRow.LayoutParams textViewParams = new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 1f);
        textView.setLayoutParams(textViewParams);
        textView.setBackgroundResource(R.drawable.lavender_border_v3);
        textView.setPadding(16, 16, 16, 16);
        textView.setText(nume);
        textView.setGravity(Gravity.CENTER);

        EditText editText = new EditText(this);
        TableRow.LayoutParams editTextParams = new TableRow.LayoutParams(100, TableRow.LayoutParams.MATCH_PARENT);
        editText.setLayoutParams(editTextParams);
        editText.setBackgroundResource(R.drawable.lavender_border_v3);
        editText.setPadding(16, 16, 16, 16);
        editText.setGravity(Gravity.CENTER);

        tableRow.addView(textView);
        tableRow.addView(editText);

        tableLayout.addView(tableRow);

    }
    private static boolean containsMonth(Month month, Month[] months) {
        for (Month m : months) {
            if (m == month) {
                return true;
            }
        }
        return false;
    }
    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen((GravityCompat.START)))
            drawerLayout.closeDrawer(GravityCompat.START);
        else super.onBackPressed();
    }

    public static int dpToPx(Context context, float dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }
}