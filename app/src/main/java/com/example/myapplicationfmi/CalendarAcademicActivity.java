package com.example.myapplicationfmi;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;

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
import com.example.myapplicationfmi.Modals.NotificationModal;
import com.example.myapplicationfmi.Modals.ProfessorModal;
import com.example.myapplicationfmi.Modals.ProfessorSubjectModal;
import com.example.myapplicationfmi.Modals.StudentModal;
import com.example.myapplicationfmi.Modals.SubjectModal;
import com.example.myapplicationfmi.beans.Calendar;
import com.example.myapplicationfmi.beans.Group;
import com.example.myapplicationfmi.beans.Professor;
import com.example.myapplicationfmi.beans.ProfessorWithGroups;
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

public class CalendarAcademicActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;
    MaterialToolbar materialToolbar;
    private MaterialToolbar topAppBar;
    public static final String SHARED_PREFS = "sharedPrefs";
    private Menu menu;
    private Spinner spinnerSelecteazaLuna, spinnerValabilPentru;
    private LinearLayout editOrarInfoTab;
    private Button orarTabClose, addOrarInfo, dashboardTabDelete;
    private FloatingActionButton floatingActionButton, floatingActionButtonAdd;
    private List<Long> grupeIds;
    private String[] grupe;
    private List<Long> subjectIds;
    private TextView evenimentTextView, valabilPentruTextView, titluOrarInfoUpdate;
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
    private int clickedCellIndex = -1;
    private List<Calendar> allCalendars;
    private List<Long> luniIds;
    private List<Long> valabilPentruIds;
    private EditText eveniment;
    private TableRow lastRow;
    private Year thisYear;
    private LinearLayout layoutMateriiPtProf, layoutOraInceput, layoutOraFinal;
    private Spinner spinnerMateriiPtProf;
    private NumberPicker hourPicker, hourPicker2;
    private NumberPicker minutePicker, minutePicker2;
    private ScrollView evenimenteScrollViewMultiple;
    private LinearLayout parentLinearLayout, eventLinearAfisare;
    private DateTimeFormatter formatter;
    private String[] valabilPentruArray = {"Licență", "Master", "Restanțieri licență", "Restanțieri master"};
    private  String[] minuteValues;
    private  String emailHolder;
    private List<TextView> tableCells;
    private Long profId;

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
        setContentView(R.layout.activity_calendar_academic);

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


        navigationView = findViewById(R.id.nav_view);
        topAppBar = findViewById(R.id.topAppBar);
        editOrarInfoTab = findViewById(R.id.editOrarInfoTab);
        eveniment = findViewById(R.id.editEveniment);

        spinnerValabilPentru = findViewById(R.id.spinnerValabilPentru);
        addOrarInfo = findViewById(R.id.addOrarInfo);
        dashboardTabDelete = findViewById(R.id.dashboardTabDelete);

        evenimentTextView = findViewById(R.id.evenimentTextView);
        valabilPentruTextView = findViewById(R.id.valabilPentruTextView);
        titluOrarInfoUpdate = findViewById(R.id.titluOrarInfoUpdate);

        floatingActionButton =  findViewById(R.id.floatingActionButton);
        floatingActionButtonAdd = findViewById(R.id.floatingActionButtonAdd);
        lastRow = findViewById(R.id.lastRow);

        layoutMateriiPtProf = findViewById(R.id.layoutMateriiPtProf);
        layoutOraInceput = findViewById(R.id.layoutOraInceput);
        layoutOraFinal = findViewById(R.id.layoutOraFinal);

        hourPicker = findViewById(R.id.hourPickerInceput);
        minutePicker = findViewById(R.id.minutePickerInceput);

        eventLinearAfisare = findViewById(R.id.eventLinearAfisare);
        evenimenteScrollViewMultiple = findViewById(R.id.evenimenteScrollViewMultiple);
        parentLinearLayout = findViewById(R.id.evenimenteLinearLayoutMultiple);

        spinnerMateriiPtProf = findViewById(R.id.spinnerMateriiPtProf);

        Menu menu = navigationView.getMenu();
        MenuItem creareContNouItem = menu.findItem(R.id.creareContNou);

        tableCells = Arrays.asList(findViewById(R.id.cell_1_0), findViewById(R.id.cell_1_1), findViewById(R.id.cell_1_2), findViewById(R.id.cell_1_3), findViewById(R.id.cell_1_4), findViewById(R.id.cell_1_5), findViewById(R.id.cell_1_6), findViewById(R.id.cell_2_0), findViewById(R.id.cell_2_1),
                findViewById(R.id.cell_2_2), findViewById(R.id.cell_2_3), findViewById(R.id.cell_2_4), findViewById(R.id.cell_2_5), findViewById(R.id.cell_2_6), findViewById(R.id.cell_3_0), findViewById(R.id.cell_3_1), findViewById(R.id.cell_3_2), findViewById(R.id.cell_3_3),
                findViewById(R.id.cell_3_4), findViewById(R.id.cell_3_5),  findViewById(R.id.cell_3_6), findViewById(R.id.cell_4_0), findViewById(R.id.cell_4_1), findViewById(R.id.cell_4_2), findViewById(R.id.cell_4_3), findViewById(R.id.cell_4_4), findViewById(R.id.cell_4_5), findViewById(R.id.cell_4_6), findViewById(R.id.cell_5_0),
                findViewById(R.id.cell_5_1), findViewById(R.id.cell_5_2), findViewById(R.id.cell_5_3), findViewById(R.id.cell_5_4), findViewById(R.id.cell_5_5), findViewById(R.id.cell_5_6),
                findViewById(R.id.cell_6_0), findViewById(R.id.cell_6_1), findViewById(R.id.cell_6_2), findViewById(R.id.cell_6_3), findViewById(R.id.cell_6_4), findViewById(R.id.cell_6_5), findViewById(R.id.cell_6_6));

        spinnerSelecteazaLuna = findViewById(R.id.spinnerSelecteazaLuna);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            thisYear = Year.now(Clock.systemUTC());
        }
        calcutateCalendarDaysForYear(tableCells, thisYear, spinnerSelecteazaLuna.getSelectedItemPosition());

        calendarModal.getAllCalendars().observe(CalendarAcademicActivity.this, new Observer<List<Calendar>>() {
            @Override
            public void onChanged(List<Calendar> calendars) {
                if(calendars != null) {
                    allCalendars = calendars;
                }
            }
        });

        hourPicker.setMinValue(8);
        hourPicker.setMaxValue(19);
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

        hourPicker2 = findViewById(R.id.hourPickerFinal);
        minutePicker2 = findViewById(R.id.minutePickerFinal);
        hourPicker2.setMinValue(8);
        hourPicker2.setMaxValue(19);
        String[] minuteValues2 = new String[12];
        for (int i = 0; i < 12; i++) {
            int minuteValue2 = i * 5;
            minuteValues2[i] = String.format("%02d", minuteValue2);
        }
        minutePicker2.setMinValue(0);
        minutePicker2.setMaxValue(minuteValues2.length - 1);
        minutePicker2.setDisplayedValues(minuteValues2);

        String[] luni = {"Ianuarie","Februarie","Martie","Aprilie","Mai","Iunie","Iulie","August","Septembrie","Octombrie","Noiembrie","Decembrie"};
        luniIds = new ArrayList<>();
        luniIds.add(1L);
        luniIds.add(2L);
        luniIds.add(3L);
        luniIds.add(4L);
        luniIds.add(5L);
        luniIds.add(6L);
        luniIds.add(7L);
        luniIds.add(8L);
        luniIds.add(9L);
        luniIds.add(10L);
        luniIds.add(11L);
        luniIds.add(12L);
        ArrayAdapter<String> adapterGrupaItems = new ArrayAdapter<>(CalendarAcademicActivity.this, android.R.layout.simple_spinner_dropdown_item, luni);
        spinnerSelecteazaLuna.setAdapter(adapterGrupaItems);

        LocalDate currentDate = null;
        int currentM = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            currentDate = LocalDate.now();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            currentM = currentDate.getMonthValue();
        }
        spinnerSelecteazaLuna.setSelection(currentM - 1);
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) floatingActionButtonAdd.getLayoutParams();
        if (lastRow.getVisibility() == View.VISIBLE) {
            layoutParams.bottomMargin = dpToPx(CalendarAcademicActivity.this, 140);
            floatingActionButtonAdd.setLayoutParams(layoutParams);
        } else {
            layoutParams.bottomMargin = dpToPx(CalendarAcademicActivity.this, 205);
            floatingActionButtonAdd.setLayoutParams(layoutParams);
        }

        spinnerSelecteazaLuna.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if(allCalendars != null) {
                    List<String> saptamani = new ArrayList<>();
                    List<Integer> zileleSaptamanii = new ArrayList<>();
                    for (int i = 0; i < allCalendars.size(); i++)
                        if (allCalendars.get(i).getLunaId() == luniIds.get(spinnerSelecteazaLuna.getSelectedItemPosition())) {
                            tableCells.get(Integer.valueOf(allCalendars.get(i).getSaptamana()) * 6 + allCalendars.get(i).getZiuaSaptamanii()).setBackgroundResource(R.drawable.lavender_border_v4);
                            saptamani.add(allCalendars.get(i).getSaptamana());
                            zileleSaptamanii.add(allCalendars.get(i).getZiuaSaptamanii());
                        } else {
                            boolean gasit = false;
                            for (int j = 0; j < saptamani.size(); j++)
                                if (saptamani.get(j).equals(allCalendars.get(i).getSaptamana()) && zileleSaptamanii.get(j) == allCalendars.get(i).getZiuaSaptamanii())
                                    gasit = true;
                            if(gasit == false)
                                tableCells.get(Integer.valueOf(allCalendars.get(i).getSaptamana()) * 6 + allCalendars.get(i).getZiuaSaptamanii()).setBackgroundResource(R.drawable.lavender_border_v3);
                        }

                    calcutateCalendarDaysForYear(tableCells, thisYear, spinnerSelecteazaLuna.getSelectedItemPosition());
                    ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) floatingActionButtonAdd.getLayoutParams();
                    if (lastRow.getVisibility() == View.VISIBLE) {
                        layoutParams.bottomMargin = dpToPx(CalendarAcademicActivity.this, 140);
                        floatingActionButtonAdd.setLayoutParams(layoutParams);
                    } else {
                        layoutParams.bottomMargin = dpToPx(CalendarAcademicActivity.this, 205);
                        floatingActionButtonAdd.setLayoutParams(layoutParams);
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });
        SharedPreferences sharedPreferences = getSharedPreferences(DashboardActivity.SHARED_PREFS, MODE_PRIVATE);
        emailHolder = sharedPreferences.getString("email", "");

        if (MainActivity.USER_TYPE != 1) {
            creareContNouItem.setVisible(false);
        } else {
            creareContNouItem.setVisible(true);
        }

        valabilPentruIds = new ArrayList<>();
        valabilPentruIds.add(1L);
        valabilPentruIds.add(2L);
        valabilPentruIds.add(3L);
        valabilPentruIds.add(4L);

        for(int i = 0; i < tableCells.size(); i ++){
            int finalI = i;
            tableCells.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickedCellIndex = finalI;
                    if (MainActivity.USER_TYPE == 1) {
                        editOrarInfoTab.getLayoutParams().height = 800;
                        editOrarInfoTab.requestLayout();
                        eventLinearAfisare.setVisibility(View.VISIBLE);
                        evenimenteScrollViewMultiple.setVisibility(View.GONE);
                        layoutMateriiPtProf.setVisibility(View.GONE);
                        layoutOraInceput.setVisibility(View.GONE);
                        layoutOraFinal.setVisibility(View.GONE);
                        floatingActionButtonAdd.setVisibility(View.GONE);
                        eveniment.setVisibility(View.VISIBLE);
                        ArrayAdapter<String> adapterMateriiItems = new ArrayAdapter<>(CalendarAcademicActivity.this, android.R.layout.simple_spinner_dropdown_item, valabilPentruArray);
                        spinnerValabilPentru.setAdapter(adapterMateriiItems);

                        if (tableCells.get(finalI).getBackground().getConstantState() == getResources().getDrawable(R.drawable.lavender_border_v3).getConstantState()) {
                            editOrarInfoTab.setVisibility(View.VISIBLE);
                            floatingActionButton.setVisibility(View.GONE);
                            dashboardTabDelete.setVisibility(View.GONE);
                            titluOrarInfoUpdate.setText("ADAUGĂ EVENIMENT");
                            eveniment.setText("");
                            addOrarInfo.setText("Adaugă");


                            dashboardTabDelete.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    calendarModal.getCalendarByLunaIdSaptamanaAndZiuaSaptamaniiForAdmin(luniIds.get(spinnerSelecteazaLuna.getSelectedItemPosition()), String.valueOf(finalI / 6), finalI % 6).observe(CalendarAcademicActivity.this, new Observer<Calendar>() {
                                        @Override
                                        public void onChanged(Calendar calendar) {
                                            if (calendar != null) {
                                                calendarModal.delete(calendar);
                                                parentLinearLayout.removeView((View) v.getParent().getParent());
                                                tableCells.get(finalI).setBackgroundResource(R.drawable.lavender_border_v3);
                                            }
                                        }
                                    });
                                }
                            });


                            addOrarInfo.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    //adaugam eveniment
                                    Calendar calendar = new Calendar();
                                    calendar.setLunaId(luniIds.get(spinnerSelecteazaLuna.getSelectedItemPosition()));
                                    calendar.setValabilPentru(spinnerValabilPentru.getSelectedItem().toString());

                                    calendar.setSaptamana(String.valueOf(finalI / 6));
                                    calendar.setZiuaSaptamanii(finalI % 6);
                                    calendar.setEveniment(eveniment.getText().toString());
                                    calendar.setOraFinal(null);
                                    calendar.setOraInceput(null);
                                    calendar.setMaterieId(null);
                                    calendar.setProfessorId(-1L);

                                    calendarModal.insert(calendar);

                                    tableCells.get(finalI).setBackgroundResource(R.drawable.lavender_border_v4);
                                    editOrarInfoTab.setVisibility(View.GONE);
                                    processCourseData(calendar, spinnerValabilPentru.getSelectedItem().toString(), eveniment.getText().toString());


                                }
                            });
                        } else {
                            titluOrarInfoUpdate.setText("EDITEAZĂ EVENIMENT");
                            addOrarInfo.setText("Editează");
                            floatingActionButton.setVisibility(View.VISIBLE);
                            dashboardTabDelete.setVisibility(View.VISIBLE);
                            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    editOrarInfoTab.setVisibility(View.VISIBLE);
                                    floatingActionButtonAdd.setVisibility(View.GONE);
                                    floatingActionButton.setVisibility(View.GONE);
                                    dashboardTabDelete.setVisibility(View.GONE);

                                    calendarModal.getCalendarByLunaIdSaptamanaAndZiuaSaptamaniiForAdmin(luniIds.get(spinnerSelecteazaLuna.getSelectedItemPosition()), String.valueOf(finalI / 6), finalI % 6).observe(CalendarAcademicActivity.this, new Observer<Calendar>() {
                                        @Override
                                        public void onChanged(Calendar calendar) {
                                            if (calendar != null) {
                                                spinnerValabilPentru.setSelection(Arrays.asList(valabilPentruArray).indexOf(calendar.getValabilPentru()));
                                                eveniment.setText(calendar.getEveniment());

                                                addOrarInfo.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {

                                                        //actualizam curs
                                                        calendar.setValabilPentru(spinnerValabilPentru.getSelectedItem().toString());
                                                        calendar.setEveniment(eveniment.getText().toString());
                                                        calendarModal.update(calendar);
                                                        processCourseData(calendar, spinnerValabilPentru.getSelectedItem().toString(), eveniment.getText().toString());

                                                        editOrarInfoTab.setVisibility(View.GONE);
                                                    }
                                                });
                                            }
                                        }
                                    });
                                }
                            });

                        }
                        final String[] professorNume = new String[1];
                        final String[] subjectDenumire = new String[1];
//                        calendarModal.getCalendarByLunaIdSaptamanaAndZiuaSaptamaniiForAdmin(luniIds.get(spinnerSelecteazaLuna.getSelectedItemPosition()), String.valueOf(finalI / 6), finalI % 6).observe(CalendarAcademicActivity.this, new Observer<Calendar>() {
//                            @Override
//                            public void onChanged(Calendar calendar) {
//                                if (calendar != null && finalI == clickedCellIndex) {
//                                    processCourseData(calendar, calendar.getValabilPentru(), calendar.getEveniment());
//                                }
//                            }
//                        });

                        parentLinearLayout.removeAllViews();

                        List<Calendar> allCalendarsDinData = new ArrayList<>();
                        for (int l = 0; l < allCalendars.size(); l++) {
                            if (allCalendars.get(l).getLunaId() == luniIds.get(spinnerSelecteazaLuna.getSelectedItemPosition()) && allCalendars.get(l).getSaptamana().equals(String.valueOf(finalI / 6)) && allCalendars.get(l).getZiuaSaptamanii() == finalI % 6)
                                allCalendarsDinData.add(allCalendars.get(l));
                        }
                        if (allCalendarsDinData != null && allCalendarsDinData.size() > 0 && finalI == clickedCellIndex) {
                            if (allCalendarsDinData.get(0).getOraFinal() == null && allCalendarsDinData.get(0).getOraInceput() == null) {
                                editOrarInfoTab.setVisibility(View.GONE);
                                eventLinearAfisare.setVisibility(View.VISIBLE);
                                evenimenteScrollViewMultiple.setVisibility(View.GONE);
                                floatingActionButton.setVisibility(View.VISIBLE);
                                dashboardTabDelete.setVisibility(View.VISIBLE);
                                processCourseData(allCalendarsDinData.get(0), allCalendarsDinData.get(0).getValabilPentru(), allCalendarsDinData.get(0).getEveniment());
                            } else for (int j = 0; j < allCalendarsDinData.size(); j++) {
                                evenimenteScrollViewMultiple.setVisibility(View.VISIBLE);
                                eventLinearAfisare.setVisibility(View.GONE);
                                floatingActionButton.setVisibility(View.GONE);
                                dashboardTabDelete.setVisibility(View.GONE);
                                if (allCalendarsDinData.get(j).getOraFinal() != null && allCalendarsDinData.get(j).getOraInceput() != null) {
                                    addEvenimentTab(allCalendarsDinData.get(j).getEveniment(), allCalendarsDinData.get(j).getValabilPentru(), finalI, allCalendarsDinData.get(j).getOraInceput(), allCalendarsDinData.get(j).getOraFinal());
                                }
                            }

                        }
                    } else if (MainActivity.USER_TYPE == 3) {

                        eventLinearAfisare.setVisibility(View.GONE);
                        evenimenteScrollViewMultiple.setVisibility(View.VISIBLE);

                        if (tableCells.get(finalI).getBackground().getConstantState() == getResources().getDrawable(R.drawable.lavender_border_v3).getConstantState()) {

                            floatingActionButtonAdd.setVisibility(View.GONE);
                            layoutMateriiPtProf.setVisibility(View.VISIBLE);
                            layoutOraInceput.setVisibility(View.VISIBLE);
                            layoutOraFinal.setVisibility(View.VISIBLE);
                            eveniment.setVisibility(View.GONE);
                            titluOrarInfoUpdate.setText("ADAUGĂ EXAMEN");

                            editOrarInfoTab.setVisibility(View.VISIBLE);
                            editOrarInfoTab.getLayoutParams().height = 1200;
                            editOrarInfoTab.requestLayout();
                            professorModal.getProfessorIdByEmail(emailHolder).observe(CalendarAcademicActivity.this, new Observer<Long>() {
                                @Override
                                public void onChanged(Long professorId) {
                                    if (professorId != null) {
                                        courseModal.getGroupsByProfessorId(professorId).observe(CalendarAcademicActivity.this, new Observer<List<Group>>() {
                                            @Override
                                            public void onChanged(List<Group> groups) {
                                                if (groups != null) {;
                                                    grupe = groups.stream()
                                                            .map(group -> String.valueOf(group.getNumar()))
                                                            .toArray(String[]::new);
                                                    grupeIds = groups.stream()
                                                            .map(Group::getGroupId)
                                                            .collect(Collectors.toList());
                                                    ArrayAdapter<String> adapterMateriiItems = new ArrayAdapter<>(CalendarAcademicActivity.this, android.R.layout.simple_spinner_dropdown_item, grupe);
                                                    spinnerValabilPentru.setAdapter(adapterMateriiItems);
                                                }
                                            }
                                        });

                                        spinnerValabilPentru.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                            @Override
                                            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                                                courseModal.getSubjectsByGroupIdAndProfessorId(grupeIds.get(spinnerValabilPentru.getSelectedItemPosition()), professorId).observe(CalendarAcademicActivity.this, new Observer<List<Subject>>() {
                                                    @Override
                                                    public void onChanged(List<Subject> subjects) {
                                                        String[] denumiresArray = new String[subjects.size()];
                                                        subjectIds = new ArrayList<>();
                                                        for (int k = 0; k < subjects.size(); k++) {
                                                            denumiresArray[k] = subjects.get(k).getDenumire();
                                                            subjectIds.add(subjects.get(k).getSubjectId());
                                                        }
                                                        ArrayAdapter<String> adapterMateriiItems = new ArrayAdapter<>(CalendarAcademicActivity.this, android.R.layout.simple_spinner_dropdown_item, denumiresArray);
                                                        spinnerMateriiPtProf.setAdapter(adapterMateriiItems);
                                                    }
                                                });
                                            }

                                            @Override
                                            public void onNothingSelected(AdapterView<?> parentView) {
                                            }
                                        });
                                    }
                                }
                            });

                            addOrarInfo.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    LocalTime oraFinala = null;
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                        oraFinala = LocalTime.parse(String.format(Locale.US, "%02d", hourPicker2.getValue()) + ":" + String.format(Locale.US, "%02d", Integer.parseInt(minuteValues[minutePicker2.getValue()])), DateTimeFormatter.ofPattern("HH:mm"));
                                    }
                                    LocalTime oraInceputa = null;
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                        oraInceputa = LocalTime.parse(String.format(Locale.US, "%02d", hourPicker.getValue()) + ":" + String.format(Locale.US, "%02d", Integer.parseInt(minuteValues[minutePicker.getValue()])), DateTimeFormatter.ofPattern("HH:mm"));
                                    }
                                    boolean validDeAdaugat = true;
                                    for (int j = 0; j < allCalendars.size(); j++) {
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                            if (allCalendars.get(j).getLunaId() == luniIds.get(spinnerSelecteazaLuna.getSelectedItemPosition()) &&
                                                    allCalendars.get(j).getSaptamana().equals(String.valueOf(finalI / 6)) && allCalendars.get(j).getZiuaSaptamanii() == (finalI % 6)
                                                    && !(oraInceputa.compareTo(allCalendars.get(j).getOraFinal()) > 0 && oraFinala.compareTo(allCalendars.get(j).getOraInceput()) < 0)
                                                    && allCalendars.get(j).getEveniment().equals("Examen")
                                                    && allCalendars.get(j).getValabilPentru().equals(spinnerValabilPentru.getSelectedItem().toString())) {
                                                Toast.makeText(CalendarAcademicActivity.this, "Această grupă mai are un examen în același interval orar!", Toast.LENGTH_LONG).show();
                                                validDeAdaugat = false;
                                            }
                                            /**
                                             * sa facem 2 mesaje pt. cele 2 cazuri in care trebuie prevenita adaugarea unui examen. Grupa mai are un examen (deja facut mai sus)sau proful mai are un examen in acelasi interval orar
                                             */
                                            //                                            if (allCalendars.get(j).getLunaId() == luniIds.get(spinnerSelecteazaLuna.getSelectedItemPosition()) &&
                                            //                                                    allCalendars.get(j).getSaptamana() == String.valueOf(finalI / 6) && allCalendars.get(j).getZiuaSaptamanii() == (finalI % 6)
                                            //                                                    && !(oraInceputa.compareTo(allCalendars.get(j).getOraFinal()) > 0 && oraFinala.compareTo(allCalendars.get(j).getOraInceput()) < 0)
                                            //                                                    && ){
                                            //                                                Toast.makeText(CalendarAcademicActivity.this,"", Toast.LENGTH_LONG).show();
                                            //                                            }
                                        }
                                    }
                                    if (validDeAdaugat) {
                                        LocalTime finalOraFinala = oraFinala;
                                        LocalTime finalOraInceputa = oraInceputa;
                                        professorModal.getProfessorIdByEmail(emailHolder).observe(CalendarAcademicActivity.this, new Observer<Long>() {
                                            @Override
                                            public void onChanged(Long professorId) {
                                                if (professorId != null) {
                                                    Calendar calendar = new Calendar();
                                                    calendar.setLunaId(luniIds.get(spinnerSelecteazaLuna.getSelectedItemPosition()));
                                                    calendar.setValabilPentru(spinnerValabilPentru.getSelectedItem().toString());

                                                    calendar.setSaptamana(String.valueOf(finalI / 6));
                                                    calendar.setZiuaSaptamanii(finalI % 6);
                                                    calendar.setEveniment("Examen");
                                                    calendar.setOraFinal(finalOraFinala);
                                                    calendar.setOraInceput(finalOraInceputa);
                                                    calendar.setMaterieId(subjectIds.get(spinnerMateriiPtProf.getSelectedItemPosition()));
                                                    calendar.setProfessorId(professorId);

                                                    calendarModal.insert(calendar);

                                                    tableCells.get(finalI).setBackgroundResource(R.drawable.lavender_border_v4);
                                                    editOrarInfoTab.setVisibility(View.GONE);

                                                    LocalTime localTimeInceput = null;
                                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                                        localTimeInceput = LocalTime.parse(String.format(Locale.US, "%02d", hourPicker.getValue()) + ":" + String.format(Locale.US, "%02d", Integer.parseInt(minuteValues[minutePicker.getValue()])), formatter);
                                                    }
                                                    LocalTime localTimeFinal = null;
                                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                                        localTimeFinal = LocalTime.parse(String.format(Locale.US, "%02d", hourPicker2.getValue()) + ":" + String.format(Locale.US, "%02d", Integer.parseInt(minuteValues[minutePicker2.getValue()])), formatter);
                                                    }
                                                    parentLinearLayout.removeAllViews();
                                                    addEvenimentTab("Examen", spinnerValabilPentru.getSelectedItem().toString(), finalI, localTimeInceput, localTimeFinal);
                                                    floatingActionButtonAdd.setVisibility(View.GONE);
                                                }
                                            }
                                        });
                                    }
                                }
                            });
                        }else {

                            floatingActionButtonAdd.setVisibility(View.VISIBLE);

                            floatingActionButtonAdd.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    floatingActionButtonAdd.setVisibility(View.GONE);
                                    layoutMateriiPtProf.setVisibility(View.VISIBLE);
                                    layoutOraInceput.setVisibility(View.VISIBLE);
                                    layoutOraFinal.setVisibility(View.VISIBLE);
                                    eveniment.setVisibility(View.GONE);
                                    titluOrarInfoUpdate.setText("ADAUGĂ EXAMEN");

                                    editOrarInfoTab.setVisibility(View.VISIBLE);
                                    editOrarInfoTab.getLayoutParams().height = 1200;
                                    editOrarInfoTab.requestLayout();
                                    professorModal.getProfessorIdByEmail(emailHolder).observe(CalendarAcademicActivity.this, new Observer<Long>() {
                                        @Override
                                        public void onChanged(Long professorId) {
                                            if (professorId != null) {
                                                profId = professorId;
                                                courseModal.getGroupsByProfessorId(professorId).observe(CalendarAcademicActivity.this, new Observer<List<Group>>() {
                                                    @Override
                                                    public void onChanged(List<Group> groups) {
                                                        if (groups != null) {
                                                            grupe = groups.stream()
                                                                    .map(group -> String.valueOf(group.getNumar()))
                                                                    .toArray(String[]::new);
                                                            grupeIds = groups.stream()
                                                                    .map(Group::getGroupId)
                                                                    .collect(Collectors.toList());
                                                            ArrayAdapter<String> adapterMateriiItems = new ArrayAdapter<>(CalendarAcademicActivity.this, android.R.layout.simple_spinner_dropdown_item, grupe);
                                                            spinnerValabilPentru.setAdapter(adapterMateriiItems);
                                                        }
                                                    }
                                                });

                                                spinnerValabilPentru.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                    @Override
                                                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                                                        courseModal.getSubjectsByGroupIdAndProfessorId(grupeIds.get(spinnerValabilPentru.getSelectedItemPosition()), professorId).observe(CalendarAcademicActivity.this, new Observer<List<Subject>>() {
                                                            @Override
                                                            public void onChanged(List<Subject> subjects) {
                                                                String[] denumiresArray = new String[subjects.size()];
                                                                subjectIds = new ArrayList<>();
                                                                for (int k = 0; k < subjects.size(); k++) {
                                                                    denumiresArray[k] = subjects.get(k).getDenumire();
                                                                    subjectIds.add(subjects.get(k).getSubjectId());
                                                                }
                                                                ArrayAdapter<String> adapterMateriiItems = new ArrayAdapter<>(CalendarAcademicActivity.this, android.R.layout.simple_spinner_dropdown_item, denumiresArray);
                                                                spinnerMateriiPtProf.setAdapter(adapterMateriiItems);
                                                            }
                                                        });
                                                    }

                                                    @Override
                                                    public void onNothingSelected(AdapterView<?> parentView) {
                                                    }
                                                });
                                            }
                                        }
                                    });

                                    addOrarInfo.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            LocalTime oraFinala = null;
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                                oraFinala = LocalTime.parse(String.format(Locale.US, "%02d", hourPicker2.getValue()) + ":" + String.format(Locale.US, "%02d", Integer.parseInt(minuteValues[minutePicker2.getValue()])), DateTimeFormatter.ofPattern("HH:mm"));
                                            }
                                            LocalTime oraInceputa = null;
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                                oraInceputa = LocalTime.parse(String.format(Locale.US, "%02d", hourPicker.getValue()) + ":" + String.format(Locale.US, "%02d", Integer.parseInt(minuteValues[minutePicker.getValue()])), DateTimeFormatter.ofPattern("HH:mm"));
                                            }
                                            final boolean[] validDeAdaugat = {true};
                                            for (int j = 0; j < allCalendars.size(); j++) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                                    if (allCalendars.get(j).getLunaId() == luniIds.get(spinnerSelecteazaLuna.getSelectedItemPosition()) &&
                                                            allCalendars.get(j).getSaptamana().equals(String.valueOf(finalI / 6)) && allCalendars.get(j).getZiuaSaptamanii() == (finalI % 6)
                                                            && !(oraInceputa.compareTo(allCalendars.get(j).getOraFinal()) > 0 || oraFinala.compareTo(allCalendars.get(j).getOraInceput()) < 0)
                                                            && allCalendars.get(j).getEveniment().equals("Examen")
                                                            && allCalendars.get(j).getValabilPentru().equals(spinnerValabilPentru.getSelectedItem().toString())) {
                                                        Toast.makeText(CalendarAcademicActivity.this, "Această grupă mai are un examen în același interval orar!", Toast.LENGTH_LONG).show();
                                                        validDeAdaugat[0] = false;
                                                        break;
                                                    }
                                                    else {
                                                        int finalJ = j;
                                                        LocalTime finalOraInceputa1 = oraInceputa;
                                                        LocalTime finalOraFinala1 = oraFinala;

                                                        if (allCalendars.get(finalJ).getLunaId() == luniIds.get(spinnerSelecteazaLuna.getSelectedItemPosition()) &&
                                                                allCalendars.get(finalJ).getSaptamana().equals(String.valueOf(finalI / 6)) && allCalendars.get(finalJ).getZiuaSaptamanii() == (finalI % 6)
                                                                && !(finalOraInceputa1.compareTo(allCalendars.get(finalJ).getOraFinal()) > 0 && finalOraFinala1.compareTo(allCalendars.get(finalJ).getOraInceput()) < 0)
                                                                && allCalendars.get(finalJ).getProfessorId() == profId) {
                                                            validDeAdaugat[0] = false;
                                                            Toast.makeText(CalendarAcademicActivity.this, "Mai aveți un examen în același interval orar!", Toast.LENGTH_LONG).show();
                                                            break;
                                                        }
                                                    }
                                                }
                                            }
                                            if (validDeAdaugat[0]) {
                                                LocalTime finalOraInceputa = oraInceputa;
                                                LocalTime finalOraFinala = oraFinala;
                                                professorModal.getProfessorIdByEmail(emailHolder).observe(CalendarAcademicActivity.this, new Observer<Long>() {
                                                    @Override
                                                    public void onChanged(Long professorId) {
                                                        if (professorId != null) {
                                                            Calendar calendar = new Calendar();
                                                            calendar.setLunaId(luniIds.get(spinnerSelecteazaLuna.getSelectedItemPosition()));
                                                            calendar.setValabilPentru(spinnerValabilPentru.getSelectedItem().toString());

                                                            calendar.setSaptamana(String.valueOf(finalI / 6));
                                                            calendar.setZiuaSaptamanii(finalI % 6);
                                                            calendar.setEveniment("Examen");
                                                            calendar.setOraFinal(finalOraFinala);
                                                            calendar.setOraInceput(finalOraInceputa);
                                                            calendar.setMaterieId(subjectIds.get(spinnerMateriiPtProf.getSelectedItemPosition()));
                                                            calendar.setProfessorId(professorId);

                                                            calendarModal.insert(calendar);

                                                            tableCells.get(finalI).setBackgroundResource(R.drawable.lavender_border_v4);
                                                            editOrarInfoTab.setVisibility(View.GONE);

                                                            LocalTime localTimeInceput = null;
                                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                                                localTimeInceput = LocalTime.parse(String.format(Locale.US, "%02d", hourPicker.getValue()) + ":" + String.format(Locale.US, "%02d", Integer.parseInt(minuteValues[minutePicker.getValue()])), formatter);
                                                            }
                                                            LocalTime localTimeFinal = null;
                                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                                                localTimeFinal = LocalTime.parse(String.format(Locale.US, "%02d", hourPicker2.getValue()) + ":" + String.format(Locale.US, "%02d", Integer.parseInt(minuteValues[minutePicker2.getValue()])), formatter);
                                                            }
                                                            parentLinearLayout.removeAllViews();
                                                            addEvenimentTab("Examen", spinnerValabilPentru.getSelectedItem().toString(), finalI, localTimeInceput, localTimeFinal);
                                                            floatingActionButtonAdd.setVisibility(View.GONE);
                                                        }
                                                    }
                                                });
                                            }
                                        }
                                    });
                                }
                            });
                        }
                        if (tableCells.get(finalI).getBackground().getConstantState() == getResources().getDrawable(R.drawable.lavender_border_v4).getConstantState()) {

                            parentLinearLayout.removeAllViews();

                            List<Calendar> allCalendarsDinData = new ArrayList<>();
                            for (int l = 0; l < allCalendars.size(); l++) {
                                if (allCalendars.get(l).getLunaId() == luniIds.get(spinnerSelecteazaLuna.getSelectedItemPosition()) && allCalendars.get(l).getSaptamana().equals(String.valueOf(finalI / 6)) && allCalendars.get(l).getZiuaSaptamanii() == finalI % 6)
                                    allCalendarsDinData.add(allCalendars.get(l));
                            }
                            if (allCalendarsDinData != null && allCalendarsDinData.size() > 0 && finalI == clickedCellIndex) {
                                if (allCalendarsDinData.get(0).getOraFinal() == null && allCalendarsDinData.get(0).getOraInceput() == null) {
                                    editOrarInfoTab.setVisibility(View.GONE);
                                    eventLinearAfisare.setVisibility(View.VISIBLE);
                                    processCourseData(allCalendarsDinData.get(0), allCalendarsDinData.get(0).getValabilPentru(), allCalendarsDinData.get(0).getEveniment());
                                } else for (int j = 0; j < allCalendarsDinData.size(); j++) {
                                    if (allCalendarsDinData.get(j).getOraFinal() != null && allCalendarsDinData.get(j).getOraInceput() != null) {
                                        addEvenimentTab(allCalendarsDinData.get(j).getEveniment(), allCalendarsDinData.get(j).getValabilPentru(), finalI, allCalendarsDinData.get(j).getOraInceput(), allCalendarsDinData.get(j).getOraFinal());
                                    }
                                }

                            }
                        }
                }
                    else if(MainActivity.USER_TYPE == 2){
                        editOrarInfoTab.setVisibility(View.GONE);
                        floatingActionButton.setVisibility(View.GONE);
                        dashboardTabDelete.setVisibility(View.GONE);
                        if (tableCells.get(finalI).getBackground().getConstantState() == getResources().getDrawable(R.drawable.lavender_border_v4).getConstantState()) {

                            parentLinearLayout.removeAllViews();

                            List<Calendar> allCalendarsDinData = new ArrayList<>();
                            for (int l = 0; l < allCalendars.size(); l++) {
                                if (allCalendars.get(l).getLunaId() == luniIds.get(spinnerSelecteazaLuna.getSelectedItemPosition()) && allCalendars.get(l).getSaptamana().equals(String.valueOf(finalI / 6)) && allCalendars.get(l).getZiuaSaptamanii() == finalI % 6)
                                    allCalendarsDinData.add(allCalendars.get(l));
                            }
                            if (allCalendarsDinData != null && allCalendarsDinData.size() > 0 && finalI == clickedCellIndex) {
                                if (allCalendarsDinData.get(0).getOraFinal() == null && allCalendarsDinData.get(0).getOraInceput() == null) {
                                    eventLinearAfisare.setVisibility(View.VISIBLE);
                                    evenimenteScrollViewMultiple.setVisibility(View.GONE);
                                    processCourseData(allCalendarsDinData.get(0), allCalendarsDinData.get(0).getValabilPentru(), allCalendarsDinData.get(0).getEveniment());
                                } else for (int j = 0; j < allCalendarsDinData.size(); j++) {
                                    if (allCalendarsDinData.get(j).getOraFinal() != null && allCalendarsDinData.get(j).getOraInceput() != null) {
                                        evenimenteScrollViewMultiple.setVisibility(View.VISIBLE);
                                        eventLinearAfisare.setVisibility(View.GONE);
                                        addEvenimentTab(allCalendarsDinData.get(j).getEveniment(), allCalendarsDinData.get(j).getValabilPentru(), finalI, allCalendarsDinData.get(j).getOraInceput(), allCalendarsDinData.get(j).getOraFinal());
                                    }
                                }

                            }
                        }
                    }
                }
            });
        }

        orarTabClose = findViewById(R.id.orarTabClose);
        orarTabClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editOrarInfoTab.setVisibility(View.GONE);
                floatingActionButton.setVisibility(View.GONE);
                dashboardTabDelete.setVisibility(View.GONE);
                floatingActionButtonAdd.setVisibility(View.GONE);
                eveniment.setText("");
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
                } else if (itemId == R.id.orar) {
                    Intent intent = new Intent(CalendarAcademicActivity.this, OrarActivity.class);
                    startActivity(intent);
                    finish();
                } else if (itemId == R.id.calendar) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else if (itemId == R.id.activitiesAnnouncements) {
                    Intent intent = new Intent(CalendarAcademicActivity.this, DashboardActivity.class);
                    startActivity(intent);
                    finish();
                } else if (itemId == R.id.internshipVoluntariat) {
                    Intent intent = new Intent(CalendarAcademicActivity.this, ExtracurricularActivity.class);
                    startActivity(intent);
                    finish();
                } else if (itemId == R.id.cantina) {
                } else if (itemId == R.id.informatii) {
                }
                else if(itemId == R.id.creareContNou) {
                    Intent intent = new Intent(CalendarAcademicActivity.this, RegisterActivity.class);
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
                    Intent intent = new Intent(CalendarAcademicActivity.this, ProfileActivity.class);
                    intent.putExtra("previousActivity", "CalendarAcademicActivity");
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

                    Intent intent = new Intent(CalendarAcademicActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                return false;
            }
        });
    }
    public void processCourseData(Calendar calendar,  String valabilPentru, String event){
        evenimentTextView.setText("Eveniment: " + event + " ");
        valabilPentruTextView.setText("Valabil pentru: " + valabilPentru);
        }
    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen((GravityCompat.START)))
            drawerLayout.closeDrawer(GravityCompat.START);
        else super.onBackPressed();
    }
    private List<Integer> prevLuna = new ArrayList<>();

    public void calcutateCalendarDaysForYear(List<TextView> tableCells, Year thisYear, int lunaIndex){
        Year year2023 = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            year2023 = Year.of(2023);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if(thisYear.compareTo(year2023) == 0) {
                prevLuna.add(26);
                prevLuna.add(27);
                prevLuna.add(28);
                prevLuna.add(29);
                prevLuna.add(30);
                prevLuna.add(31);
                prevLuna.add(1);
            }
        }

        for(int i = 0; i < 12; i ++) {
            List<Integer> luna = new ArrayList<>();
            int zilePeLuna = 0;
            if(i == 3 || i== 5 || i == 8 || i == 10) zilePeLuna = 30;
            else if(i == 1) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    if(thisYear.isLeap())
                        zilePeLuna = 29;
                    else zilePeLuna = 28;
                }
            }
            else zilePeLuna = 31;

            luna = prevLuna;

            for (int j = (luna.size() == 0) ? 0 : (luna.get(luna.size() - 1)); j < zilePeLuna; j++) {
                luna.add(((luna.size() == 0) ? 0 : (luna.get(luna.size() - 1))) + 1);
            }
            int nrZileDeAdaugat = luna.size() % 7 == 0 ? 0 : (7 - (luna.size() % 7));
            for(int j = 0; j < nrZileDeAdaugat; j++){
                luna.add(j + 1);
            }
            if(nrZileDeAdaugat == 0)
                prevLuna = new ArrayList<>();
            else prevLuna = luna.subList(prevLuna.size() - 7, prevLuna.size());
            if(i == lunaIndex){
                if(luna.size() <= 35) lastRow.setVisibility(View.GONE);
                else lastRow.setVisibility(View.VISIBLE);
                for(int k = 0; k < luna.size(); k ++){
                    tableCells.get(k).setText(String.valueOf(luna.get(k)));
                }
            }
        }
    }



    private void addEvenimentTab(String event, String valabilPentru, int finalI, LocalTime oraInceput, LocalTime oraFinal) {
        LinearLayout evenimentTab = new LinearLayout(this);
        evenimentTab.setId(View.generateViewId());

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        evenimentTab.setOrientation(LinearLayout.VERTICAL);
        evenimentTab.setLayoutParams(layoutParams);

        TextView evenimentTextView = new TextView(this);
        LinearLayout.LayoutParams evenimentParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        evenimentParams.rightMargin = 8;

        evenimentTextView.setLayoutParams(evenimentParams);
        evenimentTextView.setPadding(dpToPx(this,0), dpToPx(CalendarAcademicActivity.this,20),dpToPx(CalendarAcademicActivity.this,0),dpToPx(CalendarAcademicActivity.this,10));
        //evenimentParams.setMargins(0, 20, 8, 10);
        evenimentTextView.setText("Eveniment: " + event);
        evenimentTextView.setTextSize(18);
        evenimentTextView.setTextColor(getResources().getColor(R.color.black));
        evenimentTextView.setTypeface(Typeface.create("casual", Typeface.NORMAL));


        View view = new View(this);
        LinearLayout.LayoutParams viewLayoutParams = new LinearLayout.LayoutParams(dpToPx(this,380),dpToPx(this,2));
        viewLayoutParams.setMargins(dpToPx(this,5), 0, 0, 0);
        view.setLayoutParams(viewLayoutParams);
        view.setBackgroundResource(R.color.original_lavender);

        TextView intervalOrarTextView = new TextView(this);
        LinearLayout.LayoutParams intervalParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        intervalParams.rightMargin = 8;

        intervalOrarTextView.setLayoutParams(intervalParams);
        intervalOrarTextView.setPadding(dpToPx(this,0), dpToPx(CalendarAcademicActivity.this,20),dpToPx(CalendarAcademicActivity.this,0),dpToPx(CalendarAcademicActivity.this,10));
        intervalOrarTextView.setTextSize(18);
        intervalOrarTextView.setTextColor(getResources().getColor(R.color.black));
        intervalOrarTextView.setTypeface(Typeface.create("casual", Typeface.NORMAL));

        String oraInceputa = null;
        String oraFinala = null;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            oraInceputa = oraInceput.format(formatter);
        }
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            oraFinala = oraFinal.format(formatter);
        }

        intervalOrarTextView.setText("Interval Orar: " + oraInceputa + "-" + oraFinala);


        View view2 = new View(this);
        view2.setLayoutParams(viewLayoutParams);
        view2.setBackgroundResource(R.color.original_lavender);


        // Enclosing LinearLayout for "Valabil pentru:" TextView and ConstraintLayout
        LinearLayout valabilPentruLayout = new LinearLayout(this);
        valabilPentruLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        valabilPentruLayout.setOrientation(LinearLayout.HORIZONTAL);

        TextView valabilPentruTextView = new TextView(this);
        LinearLayout.LayoutParams valabilPentruParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        valabilPentruParams.rightMargin = 8;

        valabilPentruTextView.setLayoutParams(valabilPentruParams);
        valabilPentruTextView.setPadding(dpToPx(this,0), dpToPx(CalendarAcademicActivity.this,20),dpToPx(CalendarAcademicActivity.this,0),dpToPx(CalendarAcademicActivity.this,10));

        valabilPentruTextView.setText("Valabil pentru: " + valabilPentru);
        valabilPentruTextView.setTextSize(18);
        valabilPentruTextView.setTextColor(getResources().getColor(R.color.black));
        valabilPentruTextView.setTypeface(Typeface.create("casual", Typeface.NORMAL));


        Button dashboardTabDeleteMultiple = new Button(this);
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(dpToPx(this, 40), dpToPx(this, 40));
        layoutParams2.setMarginStart(dpToPx(this, 140));
        dashboardTabDeleteMultiple.setLayoutParams(layoutParams2);

        dashboardTabDeleteMultiple.setBackgroundResource(R.drawable.baseline_close_24);
        dashboardTabDeleteMultiple.setVisibility(View.GONE);

        dashboardTabDeleteMultiple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarModal.getCalendarByLunaIdSaptamanaAndZiuaSaptamaniiForProfessor(luniIds.get(spinnerSelecteazaLuna.getSelectedItemPosition()), String.valueOf(finalI / 6), finalI % 6, oraInceput, oraFinal).observe(CalendarAcademicActivity.this, new Observer<Calendar>() {
                    @Override
                    public void onChanged(Calendar calendar) {
                        if (calendar != null) {
                           calendarModal.delete(calendar);
                           parentLinearLayout.removeView((View) v.getParent().getParent());

                            List<Calendar> allCalendarsDinData = new ArrayList<>();
                            for (int l = 0; l < allCalendars.size(); l++) {
                                if (allCalendars.get(l).getLunaId() == luniIds.get(spinnerSelecteazaLuna.getSelectedItemPosition()) && allCalendars.get(l).getSaptamana().equals(String.valueOf(finalI / 6)) && allCalendars.get(l).getZiuaSaptamanii() == finalI % 6)
                                    allCalendarsDinData.add(allCalendars.get(l));
                            }
                            if(allCalendarsDinData.size() == 0)
                           tableCells.get(finalI).setBackgroundResource(R.drawable.lavender_border_v3);
                        }
                    }
                });
            }
        });



        ConstraintLayout constraintLayout = new ConstraintLayout(this);
        ConstraintLayout.LayoutParams constraintLayoutParams = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        constraintLayoutParams.setMargins(0,0,10,0);
        constraintLayout.setLayoutParams(constraintLayoutParams);

        FloatingActionButton fab = new FloatingActionButton(this);
        fab.setId(View.generateViewId());
        ConstraintLayout.LayoutParams fabLayoutParams = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        fab.setLayoutParams(fabLayoutParams);
        fab.setClickable(true);
        fab.setContentDescription("Add Event");
        fab.setImageResource(R.drawable.baseline_edit_24);
        fab.setVisibility(View.GONE);
        fab.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));

        ConstraintLayout.LayoutParams fabConstraints = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        fabConstraints.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
        fabConstraints.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        fab.setLayoutParams(fabConstraints);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                titluOrarInfoUpdate.setText("EDITEAZĂ EVENIMENT");
                addOrarInfo.setText("Editează");
                editOrarInfoTab.setVisibility(View.VISIBLE);
                floatingActionButtonAdd.setVisibility(View.GONE);
                eveniment.setVisibility(View.GONE);
                fab.setVisibility(View.GONE);
                dashboardTabDeleteMultiple.setVisibility(View.GONE);

                calendarModal.getCalendarByLunaIdSaptamanaAndZiuaSaptamaniiForProfessor(luniIds.get(spinnerSelecteazaLuna.getSelectedItemPosition()), String.valueOf(finalI / 6), finalI % 6, oraInceput, oraFinal).observe(CalendarAcademicActivity.this, new Observer<Calendar>() {
                    @Override
                    public void onChanged(Calendar calendar) {
                                if (calendar != null) {

                                    professorModal.getProfessorIdByEmail(emailHolder).observe(CalendarAcademicActivity.this, new Observer<Long>() {
                                        @Override
                                        public void onChanged(Long professorId) {
                                            if (professorId != null) {
                                                courseModal.getGroupsByProfessorId(professorId).observe(CalendarAcademicActivity.this, new Observer<List<Group>>() {
                                                    @Override
                                                    public void onChanged(List<Group> groups) {
                                                        if (groups != null) {;
                                                            grupe = groups.stream()
                                                                    .map(group -> String.valueOf(group.getNumar()))
                                                                    .toArray(String[]::new);
                                                            grupeIds = groups.stream()
                                                                    .map(Group::getGroupId)
                                                                    .collect(Collectors.toList());
                                                            ArrayAdapter<String> adapterMateriiItems = new ArrayAdapter<>(CalendarAcademicActivity.this, android.R.layout.simple_spinner_dropdown_item, grupe);
                                                            spinnerValabilPentru.setAdapter(adapterMateriiItems);


                                    spinnerValabilPentru.setSelection(Arrays.asList(grupe).indexOf(calendar.getValabilPentru()));
                                    eveniment.setText(calendar.getEveniment());


                                                            spinnerValabilPentru.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                                @Override
                                                                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                                                                    courseModal.getSubjectsByGroupIdAndProfessorId(grupeIds.get(spinnerValabilPentru.getSelectedItemPosition()), professorId).observe(CalendarAcademicActivity.this, new Observer<List<Subject>>() {
                                                                        @Override
                                                                        public void onChanged(List<Subject> subjects) {
                                                                            String[] denumiresArray = new String[subjects.size()];
                                                                            subjectIds = new ArrayList<>();
                                                                            for (int k = 0; k < subjects.size(); k++) {
                                                                                denumiresArray[k] = subjects.get(k).getDenumire();
                                                                                subjectIds.add(subjects.get(k).getSubjectId());
                                                                            }
                                                                            ArrayAdapter<String> adapterMateriiItems = new ArrayAdapter<>(CalendarAcademicActivity.this, android.R.layout.simple_spinner_dropdown_item, denumiresArray);
                                                                            spinnerMateriiPtProf.setAdapter(adapterMateriiItems);
                                                                        }
                                                                    });
                                                                }

                                                                @Override
                                                                public void onNothingSelected(AdapterView<?> parentView) {
                                                                }
                                                            });


                                    addOrarInfo.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            //actualizam curs
                                            calendar.setValabilPentru(spinnerValabilPentru.getSelectedItem().toString());
                                            calendar.setEveniment(eveniment.getText().toString());

                                            LocalTime oraF = null;
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                                oraF = LocalTime.parse(String.format(Locale.US, "%02d",hourPicker2.getValue()) + ":" + String.format(Locale.US, "%02d",Integer.parseInt(minuteValues[minutePicker2.getValue()])), DateTimeFormatter.ofPattern("HH:mm"));
                                            }
                                            LocalTime oraI = null;
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                                oraI = LocalTime.parse(String.format(Locale.US, "%02d",hourPicker.getValue()) + ":" + String.format(Locale.US, "%02d",Integer.parseInt(minuteValues[minutePicker.getValue()])), DateTimeFormatter.ofPattern("HH:mm"));
                                            }

                                            boolean validDeAdaugat = true;
                                            for (int j = 0; j < allCalendars.size(); j++) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                                    if (allCalendars.get(j).getLunaId() == luniIds.get(spinnerSelecteazaLuna.getSelectedItemPosition()) &&
                                                            allCalendars.get(j).getSaptamana().equals(String.valueOf(finalI / 6)) && allCalendars.get(j).getZiuaSaptamanii() == (finalI % 6)
                                                            && !(oraI.compareTo(allCalendars.get(j).getOraFinal()) > 0 || oraF.compareTo(allCalendars.get(j).getOraInceput()) < 0)
                                                            && allCalendars.get(j).getEveniment().equals("Examen")
                                                            && allCalendars.get(j).getValabilPentru().equals(spinnerValabilPentru.getSelectedItem().toString())) {
                                                        Toast.makeText(CalendarAcademicActivity.this, "Această grupă mai are un examen în același interval orar!", Toast.LENGTH_LONG).show();
                                                        validDeAdaugat = false;
                                                        break;
                                                    }
                                                    else {
                                                        int finalJ = j;
                                                        LocalTime finalOraInceputa1 = oraI;
                                                        LocalTime finalOraFinala1 = oraF;

                                                        if (allCalendars.get(finalJ).getLunaId() == luniIds.get(spinnerSelecteazaLuna.getSelectedItemPosition()) &&
                                                                allCalendars.get(finalJ).getSaptamana().equals(String.valueOf(finalI / 6)) && allCalendars.get(finalJ).getZiuaSaptamanii() == (finalI % 6)
                                                                && !(finalOraInceputa1.compareTo(allCalendars.get(finalJ).getOraFinal()) > 0 && finalOraFinala1.compareTo(allCalendars.get(finalJ).getOraInceput()) < 0)
                                                                && allCalendars.get(finalJ).getProfessorId() == profId) {
                                                            validDeAdaugat = false;
                                                            Toast.makeText(CalendarAcademicActivity.this, "Mai aveți un examen în același interval orar!", Toast.LENGTH_LONG).show();
                                                            break;
                                                        }
                                                    }
                                                }
                                            }

                                            if(validDeAdaugat) {
                                                calendar.setOraFinal(oraF);
                                                calendar.setOraInceput(oraI);
                                                calendar.setMaterieId(subjectIds.get(spinnerMateriiPtProf.getSelectedItemPosition()));

                                                calendarModal.update(calendar);
                                                editOrarInfoTab.setVisibility(View.GONE);

                                                intervalOrarTextView.setText("Interval Orar: " + String.format(Locale.US, "%02d", hourPicker.getValue()) + ":" + String.format(Locale.US, "%02d", Integer.parseInt(minuteValues[minutePicker.getValue()])) + "-" + String.format(Locale.US, "%02d", hourPicker2.getValue()) + ":" + String.format(Locale.US, "%02d", Integer.parseInt(minuteValues[minutePicker2.getValue()])));
                                                valabilPentruTextView.setText("Valabil pentru: " + calendar.getValabilPentru());

                                                //                                    parentLinearLayout.removeAllViews();
                                                //                                    addEvenimentTab("Examen", spinnerValabilPentru.getSelectedItem().toString(), finalI, oraI, oraF);
                                                //floatingActionButtonAdd.setVisibility(View.GONE);
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
                    }
                });
            }
        });

        professorModal.getProfessorIdByEmail(emailHolder).observe(CalendarAcademicActivity.this, new Observer<Long>() {
                    @Override
                    public void onChanged(Long professorId) {
                        if (professorId != null) {
                            calendarModal.getCalendarsByLunaIdSaptamanaAndZiuaSaptamanii(luniIds.get(spinnerSelecteazaLuna.getSelectedItemPosition()), String.valueOf(finalI / 6), finalI % 6).observe(CalendarAcademicActivity.this, new Observer<List<Calendar>>() {
                                @Override
                                public void onChanged(List<Calendar> calendars) {
                                    if (calendars != null) {
                                        for(int k = 0; k < calendars.size(); k ++)
                                            if(calendars.get(k).getProfessorId() == professorId){
                                                dashboardTabDeleteMultiple.setVisibility(View.VISIBLE);
                                                fab.setVisibility(View.VISIBLE);
                                            }
                                    }
                                }
                            });
                        }
                    }
                });

        evenimentTab.addView(evenimentTextView);
        evenimentTab.addView(view);
        evenimentTab.addView(intervalOrarTextView);
        evenimentTab.addView(view2);
        valabilPentruLayout.addView(valabilPentruTextView);
        constraintLayout.addView(fab);
        valabilPentruLayout.addView(dashboardTabDeleteMultiple);
        valabilPentruLayout.addView(constraintLayout);
        evenimentTab.addView(valabilPentruLayout);

        View view3 = new View(this);
        view3.setLayoutParams(viewLayoutParams);
        view3.setBackgroundResource(R.color.original_lavender);
        view3.setPadding(0, dpToPx(this,5), 0, 0);
        evenimentTab.addView(view3);

        View viewPadding = new View(this);
        viewPadding.setLayoutParams(viewLayoutParams);
        viewPadding.setPadding(0, dpToPx(this,5), 0, 0);
        evenimentTab.addView(viewPadding);

        View view4 = new View(this);
        view4.setLayoutParams(viewLayoutParams);
        view4.setBackgroundResource(R.color.original_lavender);
        view4.setPadding(0, dpToPx(this,5), 0, 0);
        evenimentTab.addView(view4);

        View viewPadding2 = new View(this);
        viewPadding2.setLayoutParams(viewLayoutParams);
        viewPadding2.setPadding(0, dpToPx(this,5), 0, 0);
        evenimentTab.addView(viewPadding2);

        View view5 = new View(this);
        view5.setLayoutParams(viewLayoutParams);
        view5.setBackgroundResource(R.color.original_lavender);
        view5.setPadding(0, dpToPx(this,5), 0, 0);
        evenimentTab.addView(view5);


        parentLinearLayout.addView(evenimentTab);
    }

    public static int dpToPx(Context context, float dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }
}