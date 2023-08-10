package com.example.myapplicationfmi;


import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
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
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
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
import com.example.myapplicationfmi.beans.Group;
import com.example.myapplicationfmi.beans.GroupWithStudents;
import com.example.myapplicationfmi.beans.Note;
import com.example.myapplicationfmi.beans.ProfessorWithSubjects;
import com.example.myapplicationfmi.beans.Student;
import com.example.myapplicationfmi.beans.Subject;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
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
    private DateTimeFormatter formatter, dateFormatter;
    private String emailHolder;
    private TableLayout tableLayout;
    private Spinner spinnerSelecteazaGrupa, spinnerSelecteazaMateria;
    private List<Long> grupeIds;
    private String[] materii, grupe, minuteValues;
    private List<Long> subjectIds;
    private Button buttonAdaugaNote, addContestatie, contestatieTabClose;
    private  List<Student> students;
    private EditText editDataContestatii;
    private TextView textMissingGrupa;
    private NumberPicker hourPicker, minutePicker;
    private LinearLayout editContestatii;
    private ScrollView tableLayoutScrollable;
    private List<Boolean> contestatiiActive;

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
            dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
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

        addContestatie = findViewById(R.id.addContestatie);
        editDataContestatii = findViewById(R.id.editDataContestatii);
        hourPicker = findViewById(R.id.hourPicker);
        minutePicker = findViewById(R.id.minutePicker);
        editContestatii = findViewById(R.id.editContestatii);
        contestatieTabClose = findViewById(R.id.contestatieTabClose);
        tableLayoutScrollable = findViewById(R.id.tableLayoutScrollable);
        textMissingGrupa = findViewById(R.id.textMissingGrupa);

        NotesSQLiteHelper dbHelper = new NotesSQLiteHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        SharedPreferences sharedPreferences = getSharedPreferences(DashboardActivity.SHARED_PREFS, MODE_PRIVATE);
        emailHolder = sharedPreferences.getString("email", "");

        if (MainActivity.USER_TYPE != 1) {
            creareContNouItem.setVisible(false);
        } else {
            creareContNouItem.setVisible(true);
        }

        hourPicker.setMinValue(0);
        hourPicker.setMaxValue(23);
        Map<Integer, Integer> minuteValueMap = new HashMap<>();
        minuteValues = new String[12]; // We want to allow increments of 5 minutes (12 intervals)
        for (int i = 0; i < 12; i++) {
            int minuteValue = i * 5;
            minuteValues[i] = String.format("%02d", minuteValue); // Format the minutes to have leading zeros
            minuteValueMap.put(minuteValue, i);
        }
        minutePicker.setMinValue(0);
        minutePicker.setMaxValue(minuteValues.length - 1);
        minutePicker.setDisplayedValues(minuteValues);

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
                            contestatiiActive = new ArrayList<>();

                            if((materii[spinnerSelecteazaMateria.getSelectedItemPosition()].equals("Practică") || materii[spinnerSelecteazaMateria.getSelectedItemPosition()].equals("Limba engleză") ||
                                    materii[spinnerSelecteazaMateria.getSelectedItemPosition()].equals("Deontologie academică") ||
                                    materii[spinnerSelecteazaMateria.getSelectedItemPosition()].equals("Pregătirea proiectului și a tezei de disertație")))
                                ((TextView)((TableRow) tableLayout.getChildAt(0)).getChildAt(1)).setText("CALIFICATIV");
                            else ((TextView)((TableRow) tableLayout.getChildAt(0)).getChildAt(1)).setText("NOTĂ");

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

                                        for(int i = 0; i < grupeIds.size(); i ++)
                                            contestatiiActive.add(false);

                                        if(groups.size() == 0){
                                            int childCount = tableLayout.getChildCount();
                                            for (int i = childCount - 1; i > 0; i--) {
                                                View child = tableLayout.getChildAt(i);
                                                if (child instanceof TableRow) {
                                                    tableLayout.removeViewAt(i);
                                                }
                                            }
                                            tableLayoutScrollable.setVisibility(View.GONE);
                                            buttonAdaugaNote.setVisibility(View.GONE);
                                            textMissingGrupa.setVisibility(View.VISIBLE);
                                        }
                                        else{
                                            tableLayoutScrollable.setVisibility(View.VISIBLE);
                                            buttonAdaugaNote.setVisibility(View.VISIBLE);
                                            textMissingGrupa.setVisibility(View.GONE);
                                        }
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
                                @RequiresApi(api = Build.VERSION_CODES.O)
                                @Override
                                public void onChanged(GroupWithStudents groupWithStudents) {

                                    int childCount = tableLayout.getChildCount();
                                    for (int i = childCount - 1; i > 0; i--) {
                                        View child = tableLayout.getChildAt(i);
                                        if (child instanceof TableRow) {
                                            tableLayout.removeViewAt(i);
                                        }
                                    }
                                    if (groupWithStudents != null) {
                                        students = groupWithStudents.students;
                                        Collections.sort(students);
                                        for(int i = 0; i < students.size(); i ++){
                                            int finalI = i;
                                            addTableRow(students.get(finalI).getNume() + " " + students.get(finalI).getPrenume(), "");


                                            String[] projection = {
                                                    NotesSQLiteHelper.COLUMN_NAME_ID,
                                                    NotesSQLiteHelper.COLUMN_NAME_STUDENT_ID,
                                                    NotesSQLiteHelper.COLUMN_NAME_VALUE,
                                                    NotesSQLiteHelper.COLUMN_NAME_LOCKED,
                                                    NotesSQLiteHelper.COLUMN_NAME_EDITED,
                                                    NotesSQLiteHelper.COLUMN_NAME_DATA_CONTESTATIE,
                                                    NotesSQLiteHelper.COLUMN_NAME_ORA_CONTESTATIE
                                            };
                                            String selection = NotesSQLiteHelper.COLUMN_NAME_STUDENT_ID + " = ? AND " + NotesSQLiteHelper.COLUMN_NAME_SUBJECT_ID + " = ?";
                                            String[] selectionArgs = {String.valueOf(students.get(finalI).getStudentId()), String.valueOf(subjectIds.get(spinnerSelecteazaMateria.getSelectedItemPosition()))};

                                            Cursor cursor = db.query(NotesSQLiteHelper.TABLE_NAME, projection, selection, selectionArgs, null, null, null);

                                            if (cursor != null && cursor.moveToFirst()) {
                                                long noteId = cursor.getLong(cursor.getColumnIndexOrThrow(NotesSQLiteHelper.COLUMN_NAME_ID));
                                                String value = cursor.getString(cursor.getColumnIndexOrThrow(NotesSQLiteHelper.COLUMN_NAME_VALUE));
                                                boolean locked = cursor.getInt(cursor.getColumnIndexOrThrow(NotesSQLiteHelper.COLUMN_NAME_LOCKED)) == 1;
                                                boolean edited = cursor.getInt(cursor.getColumnIndexOrThrow(NotesSQLiteHelper.COLUMN_NAME_EDITED)) == 1;
                                                LocalDate dataContestatie = LocalDate.parse(cursor.getString(cursor.getColumnIndexOrThrow(NotesSQLiteHelper.COLUMN_NAME_DATA_CONTESTATIE)));
                                                LocalTime oraContestatie = LocalTime.parse(cursor.getString(cursor.getColumnIndexOrThrow(NotesSQLiteHelper.COLUMN_NAME_ORA_CONTESTATIE)));

                                                if ((TableRow) tableLayout.getChildAt(finalI + 1) != null) {
                                                    ((EditText) ((TableRow) tableLayout.getChildAt(finalI + 1)).getChildAt(1)).setText(value);
                                                    if (!locked && LocalDate.now().compareTo(dataContestatie) >= 0 && LocalTime.now().compareTo(oraContestatie) >= 0 && ChronoUnit.DAYS.between(dataContestatie, LocalDate.now()) < 2) {
                                                        contestatiiActive.set(spinnerSelecteazaGrupa.getSelectedItemPosition(), true);
                                                    }
                                                    if (locked && !edited && LocalDate.now().compareTo(dataContestatie) >= 0 && LocalTime.now().compareTo(oraContestatie) >= 0 && ChronoUnit.DAYS.between(dataContestatie, LocalDate.now()) < 2) {
                                                        contestatiiActive.set(spinnerSelecteazaGrupa.getSelectedItemPosition(), true);
                                                        ContentValues contentValues = new ContentValues();
                                                        contentValues.put(NotesSQLiteHelper.COLUMN_NAME_LOCKED, 0);

                                                        String updateSelection = NotesSQLiteHelper.COLUMN_NAME_ID + " = ?";
                                                        String[] updateSelectionArgs = {String.valueOf(noteId)};

                                                        db.update(NotesSQLiteHelper.TABLE_NAME, contentValues, updateSelection, updateSelectionArgs);

                                                    } else if (contestatiiActive.get(spinnerSelecteazaGrupa.getSelectedItemPosition()) && ChronoUnit.DAYS.between(dataContestatie, LocalDate.now()) >= 2 && LocalTime.now().compareTo(oraContestatie) >= 0) {
                                                        contestatiiActive.set(spinnerSelecteazaGrupa.getSelectedItemPosition(), false);
                                                        ContentValues contentValues = new ContentValues();
                                                        contentValues.put(NotesSQLiteHelper.COLUMN_NAME_LOCKED, 1);

                                                        String updateSelection = NotesSQLiteHelper.COLUMN_NAME_ID + " = ?";
                                                        String[] updateSelectionArgs = {String.valueOf(noteId)};

                                                        db.update(NotesSQLiteHelper.TABLE_NAME, contentValues, updateSelection, updateSelectionArgs);
                                                    } else if (locked) {
                                                        if (edited || !contestatiiActive.get(spinnerSelecteazaGrupa.getSelectedItemPosition())) {
                                                            ((EditText) ((TableRow) tableLayout.getChildAt(finalI + 1)).getChildAt(1)).setBackgroundResource(R.drawable.lavender_border_v6);
                                                            ((EditText) ((TableRow) tableLayout.getChildAt(finalI + 1)).getChildAt(1)).setFocusable(false);
                                                            ((EditText) ((TableRow) tableLayout.getChildAt(finalI + 1)).getChildAt(1)).setFocusableInTouchMode(false);
                                                            ((EditText) ((TableRow) tableLayout.getChildAt(finalI + 1)).getChildAt(1)).setTextColor(Color.parseColor("#a3a3a3"));
                                                        }
                                                    }
                                                }
                                            }
                                            if (cursor != null) {
                                                cursor.close();
                                            }

//                                            noteModal.getNoteByStudentAndSubjectIds(students.get(i).getStudentId(), subjectIds.get(spinnerSelecteazaMateria.getSelectedItemPosition())).observe(CarnetActivity.this, note -> {
//                                                if (note != null) {
//                                                    //((EditText)((TableRow) tableLayout.getChildAt(finalI + 1)).getChildAt(1)).setText(note.getValoare());
//                                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                                                        updateNoteAndUI(note, finalI);
//                                                    }
//                                                }
//                                            });
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
                            if(contestatiiActive.get(spinnerSelecteazaGrupa.getSelectedItemPosition())){
                                List<String> notes = new ArrayList<>();
                                boolean validDeAdaugat = true;
                                for (int i = 1; i < tableLayout.getChildCount(); i++) {
                                    View view = tableLayout.getChildAt(i);
                                    if (view instanceof TableRow) {
                                        TableRow row = (TableRow) view;
                                        if ("".equals(((EditText) row.getChildAt(1)).getText().toString())) {
                                            validDeAdaugat = false;
                                            Toast.makeText(CarnetActivity.this, "Nu ați completat toate notele!", Toast.LENGTH_LONG).show();
                                            break;
                                        }
                                        if ((materii[spinnerSelecteazaMateria.getSelectedItemPosition()].equals("Practică") || materii[spinnerSelecteazaMateria.getSelectedItemPosition()].equals("Limba engleză") ||
                                                materii[spinnerSelecteazaMateria.getSelectedItemPosition()].equals("Deontologie academică") ||
                                                materii[spinnerSelecteazaMateria.getSelectedItemPosition()].equals("Pregătirea proiectului și a tezei de disertație"))
                                                && !((EditText) row.getChildAt(1)).getText().toString().equals("Admis") && !((EditText) row.getChildAt(1)).getText().toString().equals("Respins")) {
                                            validDeAdaugat = false;
                                            Toast.makeText(CarnetActivity.this, "Calificativele sunt de forma ADMIS/RESPINS!", Toast.LENGTH_LONG).show();
                                            break;
                                        } else if (!(materii[spinnerSelecteazaMateria.getSelectedItemPosition()].equals("Practică") || materii[spinnerSelecteazaMateria.getSelectedItemPosition()].equals("Limba engleză") ||
                                                materii[spinnerSelecteazaMateria.getSelectedItemPosition()].equals("Deontologie academică") || materii[spinnerSelecteazaMateria.getSelectedItemPosition()].equals("Pregătirea proiectului și a tezei de disertație"))
                                                && !((EditText) row.getChildAt(1)).getText().toString().matches("\\d+")) {
                                            validDeAdaugat = false;
                                            Toast.makeText(CarnetActivity.this, "Notele sunt de forma 1 - 10!", Toast.LENGTH_LONG).show();
                                            break;
                                        } else if (!(materii[spinnerSelecteazaMateria.getSelectedItemPosition()].equals("Practică") || materii[spinnerSelecteazaMateria.getSelectedItemPosition()].equals("Limba engleză") ||
                                                materii[spinnerSelecteazaMateria.getSelectedItemPosition()].equals("Deontologie academică") || materii[spinnerSelecteazaMateria.getSelectedItemPosition()].equals("Pregătirea proiectului și a tezei de disertație"))
                                                && ((EditText) row.getChildAt(1)).getText().toString().matches("\\d+") && Integer.valueOf(((EditText) row.getChildAt(1)).getText().toString()) > 10 || Integer.valueOf(((EditText) row.getChildAt(1)).getText().toString()) <= 0) {
                                            validDeAdaugat = false;
                                            Toast.makeText(CarnetActivity.this, "Notele trebuie cuprinse in intervalul [1, 10]!", Toast.LENGTH_LONG).show();
                                            break;
                                        } else
                                            notes.add(((EditText) row.getChildAt(1)).getText().toString());
                                    }
                                }
                                if (validDeAdaugat)
                                    for (int i = 0; i < notes.size(); i++) {
                                        int finalI = i;


                                        String[] projection = {
                                                NotesSQLiteHelper.COLUMN_NAME_ID,
                                                NotesSQLiteHelper.COLUMN_NAME_STUDENT_ID,
                                                NotesSQLiteHelper.COLUMN_NAME_VALUE,
                                                NotesSQLiteHelper.COLUMN_NAME_LOCKED,
                                                NotesSQLiteHelper.COLUMN_NAME_EDITED,
                                                NotesSQLiteHelper.COLUMN_NAME_DATA_CONTESTATIE,
                                                NotesSQLiteHelper.COLUMN_NAME_ORA_CONTESTATIE
                                        };
                                        String selection = NotesSQLiteHelper.COLUMN_NAME_STUDENT_ID + " = ? AND " +
                                                NotesSQLiteHelper.COLUMN_NAME_SUBJECT_ID + " = ?";
                                String[] selectionArgs = {String.valueOf(students.get(i).getStudentId()), String.valueOf(subjectIds.get(spinnerSelecteazaMateria.getSelectedItemPosition()))};

                                Cursor cursor = db.query(
                                                NotesSQLiteHelper.TABLE_NAME,
                                                projection,
                                                selection,
                                                selectionArgs,
                                                null,
                                                null,
                                                null
                                        );

                                        if (cursor != null && cursor.moveToFirst()) {
                                            int idIndex = cursor.getColumnIndex(NotesSQLiteHelper.COLUMN_NAME_ID);
                                            int valueIndex = cursor.getColumnIndex(NotesSQLiteHelper.COLUMN_NAME_VALUE);

                                            if (idIndex >= 0 && valueIndex >= 0) {
                                                long noteId = cursor.getLong(idIndex);
                                                String valoare = cursor.getString(valueIndex);
                                                ContentValues values = new ContentValues();
                                                values.put(NotesSQLiteHelper.COLUMN_NAME_VALUE, notes.get(finalI));
                                                values.put(NotesSQLiteHelper.COLUMN_NAME_LOCKED, true);
                                                values.put(NotesSQLiteHelper.COLUMN_NAME_EDITED, true);

                                                String updateSelection = NotesSQLiteHelper.COLUMN_NAME_ID + " = ?";
                                                String[] updateSelectionArgs = {String.valueOf(noteId)};

                                                db.update(
                                                        NotesSQLiteHelper.TABLE_NAME,
                                                        values,
                                                        updateSelection,
                                                        updateSelectionArgs
                                                );

                                                if ((TableRow) tableLayout.getChildAt(finalI + 1) != null) {
                                                    ((EditText) ((TableRow) tableLayout.getChildAt(finalI + 1)).getChildAt(1))
                                                            .setBackgroundResource(R.drawable.lavender_border_v6);
                                                    ((EditText) ((TableRow) tableLayout.getChildAt(finalI + 1)).getChildAt(1))
                                                            .setFocusable(false);
                                                    ((EditText) ((TableRow) tableLayout.getChildAt(finalI + 1)).getChildAt(1))
                                                            .setFocusableInTouchMode(false);
                                                    ((EditText) ((TableRow) tableLayout.getChildAt(finalI + 1)).getChildAt(1))
                                                            .setTextColor(Color.parseColor("#a3a3a3"));
                                                }
                                            }
                                        }


//                                        noteModal.getNoteByStudentAndSubjectIds(students.get(i).getStudentId(), subjectIds.get(spinnerSelecteazaMateria.getSelectedItemPosition())).observe(CarnetActivity.this, note -> {
//                                            if (note != null) {
//                                                updateNote(note, finalI, notes);
//                                            }
//                                        });
                                    }
                            }
                            else
                            if(((EditText) ((TableRow) tableLayout.getChildAt(1)).getChildAt(1)).getBackground().getConstantState() != getResources().getDrawable(R.drawable.lavender_border_v6).getConstantState()){
                            buttonAdaugaNote.setVisibility(View.GONE);
                            editContestatii.setVisibility(View.VISIBLE);
                            addContestatie.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Integer semestru = -1;
                                    LocalDate currentDate = null;
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                        currentDate = LocalDate.now();
                                        Month[] monthsToCheck = {Month.OCTOBER, Month.NOVEMBER, Month.DECEMBER, Month.JANUARY, Month.FEBRUARY};
                                        if (containsMonth(currentDate.getMonth(), monthsToCheck)) {
                                            semestru = 1;
                                        } else {
                                            semestru = 2;
                                        }
                                    }
                                    List<String> notes = new ArrayList<>();
                                    boolean validDeAdaugat = true;
                                    for (int i = 1; i < tableLayout.getChildCount(); i++) {
                                        View view = tableLayout.getChildAt(i);
                                        if (view instanceof TableRow) {
                                            TableRow row = (TableRow) view;
                                            if ("".equals(((EditText) row.getChildAt(1)).getText().toString())) {
                                                validDeAdaugat = false;
                                                Toast.makeText(CarnetActivity.this, "Nu ați completat toate notele!", Toast.LENGTH_LONG).show();
                                                break;
                                            }
                                            if ((materii[spinnerSelecteazaMateria.getSelectedItemPosition()].equals("Practică") || materii[spinnerSelecteazaMateria.getSelectedItemPosition()].equals("Limba engleză") ||
                                                    materii[spinnerSelecteazaMateria.getSelectedItemPosition()].equals("Deontologie academică") ||
                                                    materii[spinnerSelecteazaMateria.getSelectedItemPosition()].equals("Pregătirea proiectului și a tezei de disertație"))
                                                    && !((EditText) row.getChildAt(1)).getText().toString().equals("Admis") && !((EditText) row.getChildAt(1)).getText().toString().equals("Respins")) {
                                                validDeAdaugat = false;
                                                Toast.makeText(CarnetActivity.this, "Calificativele sunt de forma ADMIS/RESPINS!", Toast.LENGTH_LONG).show();
                                                break;
                                            } else if (!(materii[spinnerSelecteazaMateria.getSelectedItemPosition()].equals("Practică") || materii[spinnerSelecteazaMateria.getSelectedItemPosition()].equals("Limba engleză") ||
                                                    materii[spinnerSelecteazaMateria.getSelectedItemPosition()].equals("Deontologie academică") || materii[spinnerSelecteazaMateria.getSelectedItemPosition()].equals("Pregătirea proiectului și a tezei de disertație"))
                                                    && !((EditText) row.getChildAt(1)).getText().toString().matches("\\d+")) {
                                                validDeAdaugat = false;
                                                Toast.makeText(CarnetActivity.this, "Notele sunt de forma 1 - 10!", Toast.LENGTH_LONG).show();
                                                break;
                                            } else if (!(materii[spinnerSelecteazaMateria.getSelectedItemPosition()].equals("Practică") || materii[spinnerSelecteazaMateria.getSelectedItemPosition()].equals("Limba engleză") ||
                                                    materii[spinnerSelecteazaMateria.getSelectedItemPosition()].equals("Deontologie academică") || materii[spinnerSelecteazaMateria.getSelectedItemPosition()].equals("Pregătirea proiectului și a tezei de disertație"))
                                                    && ((EditText) row.getChildAt(1)).getText().toString().matches("\\d+") && Integer.valueOf(((EditText) row.getChildAt(1)).getText().toString()) > 10 || Integer.valueOf(((EditText) row.getChildAt(1)).getText().toString()) <= 0) {
                                                validDeAdaugat = false;
                                                Toast.makeText(CarnetActivity.this, "Notele trebuie cuprinse in intervalul [1, 10]!", Toast.LENGTH_LONG).show();
                                                break;
                                            } else
                                                notes.add(((EditText) row.getChildAt(1)).getText().toString());
                                        }
                                    }
                                    if (validDeAdaugat)
                                        for (int i = 0; i < notes.size(); i++) {
                                            Note note = new Note();
                                            note.setAn(students.get(i).getAn());
                                            note.setSemestru(semestru);
                                            note.setValoare(notes.get(i));
                                            note.setEditat(false);
                                            note.setSubjectId(subjectIds.get(spinnerSelecteazaMateria.getSelectedItemPosition()));
                                            note.setStudentId(students.get(i).getStudentId());
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                                note.setDataContestatie(LocalDate.parse(editDataContestatii.getText().toString(), dateFormatter));
                                            }
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                                note.setOraContestatie(LocalTime.parse(String.format(Locale.US, "%02d", hourPicker.getValue()) + ":" + String.format(Locale.US, "%02d", Integer.parseInt(minuteValues[minutePicker.getValue()])), formatter));
                                            }
                                            note.setLocked(true);
                                            noteModal.insert(note);
                                            editContestatii.setVisibility(View.GONE);
                                            buttonAdaugaNote.setVisibility(View.VISIBLE);

                                            ((EditText) ((TableRow) tableLayout.getChildAt(i + 1)).getChildAt(1)).setBackgroundResource(R.drawable.lavender_border_v6);
                                            ((EditText) ((TableRow) tableLayout.getChildAt(i + 1)).getChildAt(1)).setFocusable(false);
                                            ((EditText) ((TableRow) tableLayout.getChildAt(i + 1)).getChildAt(1)).setFocusableInTouchMode(false);
                                            ((EditText) ((TableRow) tableLayout.getChildAt(i + 1)).getChildAt(1)).setTextColor(Color.parseColor("#a3a3a3"));
                                        }
                                }
                            });
                        }
                        }
                    });
                }
            }
        });

        contestatieTabClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editContestatii.setVisibility(View.GONE);
                editDataContestatii.setText("");
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

    public void updateNote(Note note, int finalI, List<String> notes){
        note.setValoare(notes.get(finalI));
        note.setLocked(true);
        note.setEditat(true);
        noteModal.update(note);

        ((EditText) ((TableRow) tableLayout.getChildAt(finalI + 1)).getChildAt(1)).setBackgroundResource(R.drawable.lavender_border_v6);
        ((EditText) ((TableRow) tableLayout.getChildAt(finalI + 1)).getChildAt(1)).setFocusable(false);
        ((EditText) ((TableRow) tableLayout.getChildAt(finalI + 1)).getChildAt(1)).setFocusableInTouchMode(false);
        ((EditText) ((TableRow) tableLayout.getChildAt(finalI + 1)).getChildAt(1)).setTextColor(Color.parseColor("#a3a3a3"));

    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void updateNoteAndUI(Note note, int finalI){
        if((TableRow) tableLayout.getChildAt(finalI + 1) != null)
            ((EditText)((TableRow) tableLayout.getChildAt(finalI + 1)).getChildAt(1)).setText(note.getValoare());
        if(!note.isLocked() && LocalDate.now().compareTo(note.getDataContestatie()) >= 0 && LocalTime.now().compareTo(note.getOraContestatie()) >= 0 && ChronoUnit.DAYS.between(note.getDataContestatie(), LocalDate.now()) < 2){
            //a venit data contestatiilor dar in caz ca se inchide interfata si contestatiiActive devine false
            contestatiiActive.set(spinnerSelecteazaGrupa.getSelectedItemPosition(), true);
        }
        if(note.isLocked() && !note.isEditat() && LocalDate.now().compareTo(note.getDataContestatie()) >= 0 && LocalTime.now().compareTo(note.getOraContestatie()) >= 0 && ChronoUnit.DAYS.between(note.getDataContestatie(), LocalDate.now()) < 2){
            //a venit data contestatiilor
            contestatiiActive.set(spinnerSelecteazaGrupa.getSelectedItemPosition(), true);
            note.setLocked(false);
            noteModal.update(note);
        }
        else if(contestatiiActive.get(spinnerSelecteazaGrupa.getSelectedItemPosition()) && ChronoUnit.DAYS.between(note.getDataContestatie(), LocalDate.now()) >= 2 && LocalTime.now().compareTo(note.getOraContestatie()) >= 0){
            //mai mult de doua zile au trecut de la contestatii
            contestatiiActive.set(spinnerSelecteazaGrupa.getSelectedItemPosition(), false);
            note.setLocked(true);
            noteModal.update(note);
        }
        else if(note.isLocked()){
            if(note.isEditat() || !contestatiiActive.get(spinnerSelecteazaGrupa.getSelectedItemPosition())){
                ((EditText)((TableRow) tableLayout.getChildAt(finalI + 1)).getChildAt(1)).setBackgroundResource(R.drawable.lavender_border_v6);
                ((EditText)((TableRow) tableLayout.getChildAt(finalI + 1)).getChildAt(1)).setFocusable(false);
                ((EditText)((TableRow) tableLayout.getChildAt(finalI + 1)).getChildAt(1)).setFocusableInTouchMode(false);
                ((EditText)((TableRow) tableLayout.getChildAt(finalI + 1)).getChildAt(1)).setTextColor(Color.parseColor("#a3a3a3"));
            }
        }
    }

    public void addTableRow(String nume, String nota){


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
        editText.setText(nota);
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