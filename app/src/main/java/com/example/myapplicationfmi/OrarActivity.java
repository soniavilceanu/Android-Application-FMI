package com.example.myapplicationfmi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplicationfmi.Modals.CourseModal;
import com.example.myapplicationfmi.Modals.GroupModal;
import com.example.myapplicationfmi.Modals.NotificationModal;
import com.example.myapplicationfmi.Modals.ProfessorModal;
import com.example.myapplicationfmi.Modals.ProfessorSubjectModal;
import com.example.myapplicationfmi.Modals.StudentModal;
import com.example.myapplicationfmi.Modals.SubjectModal;
import com.example.myapplicationfmi.beans.Course;
import com.example.myapplicationfmi.beans.Group;
import com.example.myapplicationfmi.beans.Notification;
import com.example.myapplicationfmi.beans.Professor;
import com.example.myapplicationfmi.beans.Student;
import com.example.myapplicationfmi.beans.Subject;
import com.example.myapplicationfmi.beans.SubjectWithProfessors;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
    private FloatingActionButton floatingActionButton;
    private CheckBox checkbox1, checkbox2;
    private String[] grupe;
    private String[] profesori;
    private List<Long> professorIds;
    private List<Long> grupeIds;
    private String[] materii;
    private String[] semigrupe = new String[]{"Toată grupa", "Gr. 1", "Gr. 2"};
    private List<Long> materiiIds;
    private TextView materieSiGrupaTextView, profesorTextView, frecventaTextView, saptamanaTextView, titluOrarInfoUpdate, textOrarProf;
    private MyRoomDatabase myRoomDatabase;
    private StudentModal studentModal;
    private GroupModal groupModal;
    private CourseModal courseModal;
    private NotificationModal notificationModal;
    private ProfessorModal professorModal;
    private SubjectModal subjectModal;
    private ProfessorSubjectModal professorSubjectModal;
    private int clickedCellIndex = -1;
    private Long profId;
    private List<Course> allCourses;
    private Button  buttonCuratareOrar;
    private View dummyView;
    private DateTimeFormatter formatter;
    private boolean primaDeschidereOrarProf;

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
        setContentView(R.layout.activity_orar);

        myRoomDatabase = MyRoomDatabase.getInstance(this);
        studentModal = new ViewModelProvider(this).get(StudentModal.class);
        groupModal = new ViewModelProvider(this).get(GroupModal.class);
        courseModal = new ViewModelProvider(this).get(CourseModal.class);
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
        //checkbox1.setChecked(true);
        addOrarInfo = findViewById(R.id.addOrarInfo);

        materieSiGrupaTextView = findViewById(R.id.materieSiGrupaTextView);
        profesorTextView = findViewById(R.id.profesorTextView);
        saptamanaTextView = findViewById(R.id.saptamanaTextView);
        titluOrarInfoUpdate = findViewById(R.id.titluOrarInfoUpdate);
        textOrarProf = findViewById(R.id.textOrarProf);

        floatingActionButton = findViewById(R.id.floatingActionButton);
        buttonCuratareOrar = findViewById(R.id.buttonCuratareOrar);
        dummyView = findViewById(R.id.dummyView);

        primaDeschidereOrarProf = false;

        Menu menu = navigationView.getMenu();
        MenuItem creareContNouItem = menu.findItem(R.id.creareContNou);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            formatter = DateTimeFormatter.ofPattern("HH:mm");
        }

        List<TextView> tableCells = Arrays.asList(findViewById(R.id.cell_1_1), findViewById(R.id.cell_1_2), findViewById(R.id.cell_1_3), findViewById(R.id.cell_1_4), findViewById(R.id.cell_1_5), findViewById(R.id.cell_2_1),
                findViewById(R.id.cell_2_2), findViewById(R.id.cell_2_3), findViewById(R.id.cell_2_4), findViewById(R.id.cell_2_5), findViewById(R.id.cell_3_1), findViewById(R.id.cell_3_2), findViewById(R.id.cell_3_3),
                findViewById(R.id.cell_3_4), findViewById(R.id.cell_3_5), findViewById(R.id.cell_4_1), findViewById(R.id.cell_4_2), findViewById(R.id.cell_4_3), findViewById(R.id.cell_4_4), findViewById(R.id.cell_4_5),
                findViewById(R.id.cell_5_1), findViewById(R.id.cell_5_2), findViewById(R.id.cell_5_3), findViewById(R.id.cell_5_4), findViewById(R.id.cell_5_5), findViewById(R.id.cell_5_5), findViewById(R.id.cell_5_6),
                findViewById(R.id.cell_1_6), findViewById(R.id.cell_2_6), findViewById(R.id.cell_3_6), findViewById(R.id.cell_4_6));

        courseModal.getAllCourses().observe(OrarActivity.this, new Observer<List<Course>>() {
            @Override
            public void onChanged(List<Course> courses) {
                if(courses != null) {
                    allCourses = courses;
                }
            }
        });


        spinnerSelecteazaGrupa = findViewById(R.id.spinnerSelecteazaGrupa);
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
                    spinnerSelecteazaGrupa.setAdapter(adapterGrupaItems);


        spinnerSelecteazaGrupa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if(allCourses != null) {
                    List<String> zile = new ArrayList<>();
                    List<Integer> intervaleOrare = new ArrayList<>();
                    for (int i = 0; i < allCourses.size(); i++)
                        if (allCourses.get(i).getGroupId() == grupeIds.get(spinnerSelecteazaGrupa.getSelectedItemPosition())) {
                            tableCells.get(Integer.valueOf(allCourses.get(i).getZi()) * 6 + allCourses.get(i).getIntervalOrar()).setBackgroundResource(R.drawable.lavender_border_v4);
                            zile.add(allCourses.get(i).getZi());
                            intervaleOrare.add(allCourses.get(i).getIntervalOrar());
                        } else {
                            boolean gasit = false;
                            for (int j = 0; j < zile.size(); j++)
                                if (zile.get(j).equals(allCourses.get(i).getZi()) && intervaleOrare.get(j) == allCourses.get(i).getIntervalOrar())
                                    gasit = true;
                            if(gasit == false)
                                tableCells.get(Integer.valueOf(allCourses.get(i).getZi()) * 6 + allCourses.get(i).getIntervalOrar()).setBackgroundResource(R.drawable.lavender_border_v3);
                        }
                }
                if(MainActivity.USER_TYPE == 3 && !primaDeschidereOrarProf)
                    textOrarProf.performClick();
                primaDeschidereOrarProf = true;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

        textOrarProf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(allCourses != null)
                    for(int i = 0; i <allCourses.size(); i ++)
                        if(allCourses.get(i).getProfessorId() == profId)
                            tableCells.get(Integer.valueOf(allCourses.get(i).zi) * 6 + allCourses.get(i).intervalOrar).setBackgroundResource(R.drawable.lavender_border_v4);
                else tableCells.get(Integer.valueOf(allCourses.get(i).zi) * 6 + allCourses.get(i).intervalOrar).setBackgroundResource(R.drawable.lavender_border_v3);
            }
        });

        SharedPreferences sharedPreferences = getSharedPreferences(DashboardActivity.SHARED_PREFS, MODE_PRIVATE);
        String emailHolder = sharedPreferences.getString("email", "");

        if (MainActivity.USER_TYPE != 1) {
            creareContNouItem.setVisible(false);
            buttonCuratareOrar.setVisibility(View.GONE);
            dummyView.setVisibility(View.GONE);
            if(MainActivity.USER_TYPE == 3) {
                textOrarProf.setVisibility(View.VISIBLE);
                professorModal.getProfessorIdByEmail(emailHolder).observe(OrarActivity.this, new Observer<Long>() {
                    @Override
                    public void onChanged(Long professorId) {
                        if (professorId != null) {
                            profId = professorId;
                            if(allCourses != null)
                                for(int i = 0; i <allCourses.size(); i ++)
                                    if(allCourses.get(i).getProfessorId() == profId)
                                        tableCells.get(Integer.valueOf(allCourses.get(i).zi) * 6 + allCourses.get(i).intervalOrar).setBackgroundResource(R.drawable.lavender_border_v4);
                                    else tableCells.get(Integer.valueOf(allCourses.get(i).zi) * 6 + allCourses.get(i).intervalOrar).setBackgroundResource(R.drawable.lavender_border_v3);
                        }
                    }
                });
            }
            else {
                textOrarProf.setVisibility(View.GONE);
                studentModal.getStudentByEmail(emailHolder).observe(OrarActivity.this, new Observer<Student>() {
                    @Override
                    public void onChanged(Student student) {
                        if (student != null) {
                            spinnerSelecteazaGrupa.setSelection(grupeIds.indexOf(student.getGrupaId()));
                           }
                    }
                });

            }
        } else {
            buttonCuratareOrar.setVisibility(View.VISIBLE);
            dummyView.setVisibility(View.VISIBLE);
            creareContNouItem.setVisible(true);
            textOrarProf.setVisibility(View.GONE);
        }

                }
            }
        });

        buttonCuratareOrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                courseModal.deleteCoursesByGroupId(grupeIds.get(spinnerSelecteazaGrupa.getSelectedItemPosition()));
                for(int i = 0; i < tableCells.size(); i ++)
                    tableCells.get(i).setBackgroundResource(R.drawable.lavender_border_v3);

//                if (allCourses != null) {
//                    for (int i = 0; i < allCourses.size(); i++)
//                        if (allCourses.get(i).getGroupId() == grupeIds.get(spinnerSelecteazaGrupa.getSelectedItemPosition()))
//                            tableCells.get(Integer.valueOf(allCourses.get(i).getZi()) * 6 + allCourses.get(i).getIntervalOrar()).setBackgroundResource(R.drawable.lavender_border_v3);
//                }
            }
        });


        for(int i = 0; i < tableCells.size(); i ++){
            int finalI = i;
            tableCells.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickedCellIndex = finalI;
                    if (MainActivity.USER_TYPE == 1) {
                        if (tableCells.get(finalI).getBackground().getConstantState() == getResources().getDrawable(R.drawable.lavender_border_v3).getConstantState()) {
                            editOrarInfoTab.setVisibility(View.VISIBLE);
                            titluOrarInfoUpdate.setText("ADAUGĂ CURS");
                            addOrarInfo.setText("Adaugă");

                            subjectModal.getAllSubjects().observe(OrarActivity.this, new Observer<List<Subject>>() {
                                @Override
                                public void onChanged(List<Subject> subjects) {
                                    if (subjects != null) {
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

                            spinnerMaterie.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
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
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parentView) {
                                }
                            });
                            checkbox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                    if (isChecked) {
                                        checkbox2.setChecked(false);
                                    }
                                }
                            });
                            checkbox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                    if (isChecked) {
                                        checkbox1.setChecked(false);
                                    }
                                }
                            });

                            ArrayAdapter<String> adapteSemigrupaItems = new ArrayAdapter<>(OrarActivity.this, android.R.layout.simple_spinner_dropdown_item, semigrupe);
                            spinnerSemigrupa.setAdapter(adapteSemigrupaItems);

                            addOrarInfo.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    //adaugam curs
                                    Course course = new Course();
                                    course.setGroupId(grupeIds.get(spinnerSelecteazaGrupa.getSelectedItemPosition()));
                                    course.setSubjectId(materiiIds.get(spinnerMaterie.getSelectedItemPosition()));
                                    course.setProfessorId(professorIds.get(spinnerProfesor.getSelectedItemPosition()));
                                    course.setFrecventa(checkbox1.isChecked() && !checkbox2.isChecked() ? 1 : 2);
                                    course.setSemigrupa(spinnerSemigrupa.getSelectedItemPosition());

                                    course.setZi(String.valueOf(finalI / 6));
                                    course.setIntervalOrar(finalI % 6);

                                    courseModal.insert(course);

                                    Notification notification = new Notification();
                                    notification.setType("orar");

                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                        notification.setTime(LocalTime.now().format(formatter));
                                    }
                                    notification.setCauseId(Math.toIntExact(grupeIds.get(spinnerSelecteazaGrupa.getSelectedItemPosition())));
                                    notificationModal.insert(notification);

                                    tableCells.get(finalI).setBackgroundResource(R.drawable.lavender_border_v4);
                                    editOrarInfoTab.setVisibility(View.GONE);


                                }
                            });
                        }
                        else {
                            titluOrarInfoUpdate.setText("EDITEAZĂ CURS");
                            addOrarInfo.setText("Editează");
                            floatingActionButton.setVisibility(View.VISIBLE);
                            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    editOrarInfoTab.setVisibility(View.VISIBLE);
                                    floatingActionButton.setVisibility(View.GONE);

                                    professorIds = new ArrayList<>();
                                    professorIds.add(1L);

                                    subjectModal.getAllSubjects().observe(OrarActivity.this, new Observer<List<Subject>>() {
                                        @Override
                                        public void onChanged(List<Subject> subjects) {
                                            if (subjects != null) {
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

                                    checkbox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                        @Override
                                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                            if (isChecked) {
                                                checkbox2.setChecked(false);
                                            }
                                        }
                                    });
                                    checkbox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                        @Override
                                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                            if (isChecked) {
                                                checkbox1.setChecked(false);
                                            }
                                        }
                                    });

                                    ArrayAdapter<String> adapteSemigrupaItems = new ArrayAdapter<>(OrarActivity.this, android.R.layout.simple_spinner_dropdown_item, semigrupe);
                                    spinnerSemigrupa.setAdapter(adapteSemigrupaItems);




                                    courseModal.getCourseByGroupIdZiAndIntervalOrar(grupeIds.get(spinnerSelecteazaGrupa.getSelectedItemPosition()), String.valueOf(finalI / 6), finalI % 6).observe(OrarActivity.this, new Observer<Course>() {
                                        @Override
                                        public void onChanged(Course course) {
                                            if (course != null) {
                                                spinnerMaterie.setSelection(materiiIds.indexOf(course.getSubjectId()));
                                                spinnerMaterie.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                    @Override
                                                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
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
                                                    }
                                                    @Override
                                                    public void onNothingSelected(AdapterView<?> parentView) {
                                                    }
                                                });
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
                                                            spinnerProfesor.setSelection(professorIds.indexOf(course.getProfessorId()));
                                                        }
                                                    }
                                                });

                                                if(course.getFrecventa() == 1){
                                                    checkbox1.setChecked(true);
                                                    checkbox2.setChecked(false);
                                                }else{
                                                    checkbox2.setChecked(true);
                                                    checkbox1.setChecked(false);
                                                }
                                                spinnerSemigrupa.setSelection(course.getSemigrupa());




                                                addOrarInfo.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {

                                                        //actualizam curs
                                                        course.setSubjectId(materiiIds.get(spinnerMaterie.getSelectedItemPosition()));
                                                        course.setProfessorId(professorIds.get(spinnerProfesor.getSelectedItemPosition()));
                                                        course.setFrecventa(checkbox1.isChecked() && !checkbox2.isChecked() ? 1 : 2);
                                                        course.setSemigrupa(spinnerSemigrupa.getSelectedItemPosition());

                                                        Notification notification = new Notification();
                                                        notification.setType("orar");

                                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                                            notification.setTime(LocalTime.now().format(formatter));
                                                        }
                                                        notification.setCauseId(Math.toIntExact(course.getGroupId()));
                                                        notificationModal.insert(notification);

                                                        courseModal.update(course);

                                                        editOrarInfoTab.setVisibility(View.GONE);
                                                    }
                                                });
                                            }
                                        }
                                    });

                                    /**
                                     * fara getCourseByGroupIdZiAndIntervalOrar de mai sus si ce e mai jos decomentat,
                                     * se puteau schimba materiile si profi din spinnere
                                     * dar ca acum sunt asezate in spinnere cand se deschid si se poate edita de mai multe ori acelasi curs
                                     */


//                                    spinnerMaterie.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                                        @Override
//                                        public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
//                                            subjectModal.getSubjectWithProfessorsById(materiiIds.get((int) spinnerMaterie.getSelectedItemPosition())).observe(OrarActivity.this, new Observer<SubjectWithProfessors>() {
//                                                @Override
//                                                public void onChanged(SubjectWithProfessors subjectWithProfessors) {
//                                                    if (subjectWithProfessors != null) {
//                                                        List<Professor> professors = subjectWithProfessors.professors;
//                                                        profesori = professors.stream()
//                                                                .map(professor -> String.valueOf(professor.getNume() + " " + professor.getPrenume()))
//                                                                .toArray(String[]::new);
//                                                        professorIds = professors.stream()
//                                                                .map(Professor::getProfessorId)
//                                                                .collect(Collectors.toList());
//                                                        ArrayAdapter<String> adapterProfessorItems = new ArrayAdapter<>(OrarActivity.this, android.R.layout.simple_spinner_dropdown_item, profesori);
//                                                        spinnerProfesor.setAdapter(adapterProfessorItems);
//                                                    }
//                                                }
//                                            });
//                                        }
//                                        @Override
//                                        public void onNothingSelected(AdapterView<?> parentView) {
//                                        }
//                                    });

//                                    addOrarInfo.setOnClickListener(new View.OnClickListener() {
//                                        @Override
//                                        public void onClick(View v) {
//
//                                            //actualizam curs
//                                            courseModal.getCourseByGroupIdZiAndIntervalOrar(grupeIds.get(spinnerSelecteazaGrupa.getSelectedItemPosition()), String.valueOf(finalI / 6), finalI % 6).observe(OrarActivity.this, new Observer<Course>() {
//                                                @Override
//                                                public void onChanged(Course course) {
//                                                    if (course != null) {
//                                                        course.setSubjectId(materiiIds.get(spinnerMaterie.getSelectedItemPosition()));
//                                                        course.setProfessorId(professorIds.get(spinnerProfesor.getSelectedItemPosition()));
//                                                        course.setFrecventa(checkbox1.isChecked() && !checkbox2.isChecked() ? 1 : 2);
//                                                        course.setSemigrupa(spinnerSemigrupa.getSelectedItemPosition());
//                                                        courseModal.update(course);
//                                                    }
//                                                }
//                                            });
//                                            editOrarInfoTab.setVisibility(View.GONE);
//                                            //floatingActionButton.setVisibility(View.GONE);
//                                        }
//                                    });
                                }
                            });

                        }
                    }
                    final String[] professorNume = new String[1];
                    final String[] subjectDenumire = new String[1];
                    courseModal.getCourseByGroupIdZiAndIntervalOrar(grupeIds.get(spinnerSelecteazaGrupa.getSelectedItemPosition()), String.valueOf(finalI / 6), finalI % 6).observe(OrarActivity.this, new Observer<Course>() {
                        @Override
                        public void onChanged(Course course) {
                            if (course != null && finalI == clickedCellIndex) {
                                professorModal.getProfessorById(course.getProfessorId()).observe(OrarActivity.this, new Observer<Professor>() {
                                    @Override
                                    public void onChanged(Professor professor) {
                                        if (professor != null && finalI == clickedCellIndex) {
                                            final String professorNume = professor.getNume() + " " + professor.getPrenume();
                                            subjectModal.getSubjectById(course.getSubjectId()).observe(OrarActivity.this, new Observer<Subject>() {
                                                @Override
                                                public void onChanged(Subject subject) {
                                                    if (subject != null && finalI == clickedCellIndex) {
                                                        final String subjectDenumire = subject.getDenumire();
                                                        processCourseData(course, professorNume, subjectDenumire);
                                                    }
                                                }
                                            });
                                        }
                                    }
                                });
                            }
                        }
                    });
                }
            });
        }

        orarTabClose = findViewById(R.id.orarTabClose);
        orarTabClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editOrarInfoTab.setVisibility(View.GONE);
                floatingActionButton.setVisibility(View.GONE);
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
                    Intent intent = new Intent(OrarActivity.this, CarnetActivity.class);
                    startActivity(intent);
                    finish();
                } else if (itemId == R.id.orar) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else if (itemId == R.id.calendar) {
                    Intent intent = new Intent(OrarActivity.this, CalendarAcademicActivity.class);
                    startActivity(intent);
                    finish();
                } else if (itemId == R.id.activitiesAnnouncements) {
                    Intent intent = new Intent(OrarActivity.this, DashboardActivity.class);
                    startActivity(intent);
                    finish();
                } else if (itemId == R.id.internshipVoluntariat) {
                    Intent intent = new Intent(OrarActivity.this, ExtracurricularActivity.class);
                    startActivity(intent);
                    finish();
                } else if (itemId == R.id.informatii) {
                    Intent intent = new Intent(OrarActivity.this, InformatiiGeneraleActivity.class);
                    startActivity(intent);
                    finish();
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
                    Intent intent = new Intent(OrarActivity.this, NotificationActivity.class);
                    intent.putExtra("previousActivity", "OrarActivity");
                    startActivity(intent);
                    finish();
                }
                if(item.getItemId() == R.id.deconectare){
                    SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("name", "");
                    editor.putString("emailConectat","");
                    editor.apply();

                    Intent intent = new Intent(OrarActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                return false;
            }
        });
    }
    public void processCourseData(Course course,  String professorNume, String subjectDenumire){
        materieSiGrupaTextView.setText("Materie: " + subjectDenumire + " ");
        if(course.getSemigrupa() != 0)
            materieSiGrupaTextView.setText(materieSiGrupaTextView.getText() + semigrupe[course.getSemigrupa()]);
        profesorTextView.setText("Profesor: " + professorNume);
        saptamanaTextView.setText("Frecventa: " + (course.getFrecventa() == 1 ? course.getFrecventa() + "/sapt." : "1/" + course.getFrecventa() + "/săpt."));
    }
    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen((GravityCompat.START)))
            drawerLayout.closeDrawer(GravityCompat.START);
        else super.onBackPressed();
    }

}