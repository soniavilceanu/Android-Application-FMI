package com.example.myapplicationfmi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplicationfmi.Modals.CalendarModal;
import com.example.myapplicationfmi.Modals.CourseModal;
import com.example.myapplicationfmi.Modals.EvidentaNotificariModal;
import com.example.myapplicationfmi.Modals.EvidentaVoluntariatModal;
import com.example.myapplicationfmi.Modals.GroupModal;
import com.example.myapplicationfmi.Modals.NoteModal;
import com.example.myapplicationfmi.Modals.NotificationModal;
import com.example.myapplicationfmi.Modals.ProfessorModal;
import com.example.myapplicationfmi.Modals.ProfessorSubjectModal;
import com.example.myapplicationfmi.Modals.SetariNotificariModal;
import com.example.myapplicationfmi.Modals.StudentModal;
import com.example.myapplicationfmi.Modals.SubjectModal;
import com.example.myapplicationfmi.beans.Calendar;
import com.example.myapplicationfmi.beans.EvidentaNotificari;
import com.example.myapplicationfmi.beans.EvidentaVoluntariat;
import com.example.myapplicationfmi.beans.Group;
import com.example.myapplicationfmi.beans.Note;
import com.example.myapplicationfmi.beans.Notification;
import com.example.myapplicationfmi.beans.SetariNotificari;
import com.example.myapplicationfmi.beans.Student;
import com.example.myapplicationfmi.beans.Subject;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class NotificationActivity extends AppCompatActivity {

    private Button buttonBack;
    private MyRoomDatabase myRoomDatabase;
    private StudentModal studentModal;
    private GroupModal groupModal;
    private CourseModal courseModal;
    private NotificationModal notificationModal;
    private ProfessorModal professorModal;
    private SubjectModal subjectModal;
    private ProfessorSubjectModal professorSubjectModal;
    private CalendarModal calendarModal;
    private NoteModal noteModal;
    private EvidentaNotificariModal evidentaNotificariModal;
    private EvidentaVoluntariatModal evidentaVoluntariatModal;
    private SetariNotificariModal setariNotificariModal;
    private LinearLayout parentLinearLayout, notificariDisplay, setariDisplay, voluntariSetari;
    private DateTimeFormatter formatter;
    private boolean amStersSauPlecat;
    private boolean avemNotificari;
    private TextView textAvemNotificari;
    private List<Long> studentiVoluntari;
    private Button buttonSalveazaSetari;
    private CheckBox checkboxOrar, checkboxNote, checkboxCalendar, checkboxExamene, checkboxAnunturi, checkboxActivitati, checkboxInternshipuri, checkboxVoluntariat, checkboxVoluntariInscrisi;
    private ImageView logoProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        studentModal = new ViewModelProvider(this).get(StudentModal.class);
        groupModal = new ViewModelProvider(this).get(GroupModal.class);
        courseModal = new ViewModelProvider(this).get(CourseModal.class);
        notificationModal = new ViewModelProvider(this).get(NotificationModal.class);
        professorModal = new ViewModelProvider(this).get(ProfessorModal.class);
        subjectModal = new ViewModelProvider(this).get(SubjectModal.class);
        professorSubjectModal = new ViewModelProvider(this).get(ProfessorSubjectModal.class);
        calendarModal = new ViewModelProvider(this).get(CalendarModal.class);
        noteModal = new ViewModelProvider(this).get(NoteModal.class);
        evidentaNotificariModal = new ViewModelProvider(this).get(EvidentaNotificariModal.class);
        evidentaVoluntariatModal = new ViewModelProvider(this).get(EvidentaVoluntariatModal.class);
        setariNotificariModal = new ViewModelProvider(this).get(SetariNotificariModal.class);

        parentLinearLayout = findViewById(R.id.evenimenteLinearLayoutMultiple);
        buttonBack = findViewById(R.id.buttonBack);
        textAvemNotificari = findViewById(R.id.textAvemNotificari);
        notificariDisplay = findViewById(R.id.notificariDisplay);
        setariDisplay = findViewById(R.id.setariDisplay);
        buttonSalveazaSetari = findViewById(R.id.buttonSalveazaSetari);
        checkboxOrar = findViewById(R.id.checkboxOrar);
        checkboxNote = findViewById(R.id.checkboxNote);
        checkboxCalendar = findViewById(R.id.checkboxCalendar);
        checkboxExamene = findViewById(R.id.checkboxExamene);
        checkboxAnunturi = findViewById(R.id.checkboxAnunturi);
        checkboxActivitati = findViewById(R.id.checkboxActivitati);
        checkboxInternshipuri = findViewById(R.id.checkboxInternshipuri);
        checkboxVoluntariat = findViewById(R.id.checkboxVoluntariate);
        checkboxVoluntariInscrisi = findViewById(R.id.checkboxVoluntariInscrisi);
        logoProfile = findViewById(R.id.logoProfile);
        voluntariSetari = findViewById(R.id.voluntariSetari);

        amStersSauPlecat = false;
        avemNotificari = false;
        studentiVoluntari = new ArrayList<>();

        Intent intent = getIntent();
        String whereFrom = intent.getStringExtra("previousActivity");

        SharedPreferences sharedPreferences = getSharedPreferences(DashboardActivity.SHARED_PREFS, MODE_PRIVATE);
        String emailHolder = sharedPreferences.getString("email", "");

        studentModal.getStudentByEmail(emailHolder).observe(NotificationActivity.this, new Observer<Student>() {
            @Override
            public void onChanged(Student student) {
                if(student != null){


                    setariNotificariModal.getSetariNotificariByStudentIdAndType(student.getStudentId(), "orar").observe(NotificationActivity.this, new Observer<SetariNotificari>() {
                        @Override
                        public void onChanged(SetariNotificari setariNotificari) {
                            if(setariNotificari != null){
                                checkboxOrar.setChecked(setariNotificari.getVreaNotificare());
                            }
                        }
                    });
                    setariNotificariModal.getSetariNotificariByStudentIdAndType(student.getStudentId(), "calendar").observe(NotificationActivity.this, new Observer<SetariNotificari>() {
                        @Override
                        public void onChanged(SetariNotificari setariNotificari) {
                            if(setariNotificari != null){
                                checkboxCalendar.setChecked(setariNotificari.getVreaNotificare());
                            }
                        }
                    });
                    setariNotificariModal.getSetariNotificariByStudentIdAndType(student.getStudentId(), "examen").observe(NotificationActivity.this, new Observer<SetariNotificari>() {
                        @Override
                        public void onChanged(SetariNotificari setariNotificari) {
                            if(setariNotificari != null){
                                checkboxExamene.setChecked(setariNotificari.getVreaNotificare());
                            }
                        }
                    });
                    setariNotificariModal.getSetariNotificariByStudentIdAndType(student.getStudentId(), "nota").observe(NotificationActivity.this, new Observer<SetariNotificari>() {
                        @Override
                        public void onChanged(SetariNotificari setariNotificari) {
                            if(setariNotificari != null){
                                checkboxNote.setChecked(setariNotificari.getVreaNotificare());
                            }
                        }
                    });
                    setariNotificariModal.getSetariNotificariByStudentIdAndType(student.getStudentId(), "activitate").observe(NotificationActivity.this, new Observer<SetariNotificari>() {
                        @Override
                        public void onChanged(SetariNotificari setariNotificari) {
                            if(setariNotificari != null){
                                checkboxActivitati.setChecked(setariNotificari.getVreaNotificare());
                            }
                        }
                    });
                    setariNotificariModal.getSetariNotificariByStudentIdAndType(student.getStudentId(), "anunt").observe(NotificationActivity.this, new Observer<SetariNotificari>() {
                        @Override
                        public void onChanged(SetariNotificari setariNotificari) {
                            if(setariNotificari != null){
                                checkboxAnunturi.setChecked(setariNotificari.getVreaNotificare());
                            }
                        }
                    });
                    setariNotificariModal.getSetariNotificariByStudentIdAndType(student.getStudentId(), "internship").observe(NotificationActivity.this, new Observer<SetariNotificari>() {
                        @Override
                        public void onChanged(SetariNotificari setariNotificari) {
                            if(setariNotificari != null){
                                checkboxInternshipuri.setChecked(setariNotificari.getVreaNotificare());
                            }
                        }
                    });
                    setariNotificariModal.getSetariNotificariByStudentIdAndType(student.getStudentId(), "voluntariat").observe(NotificationActivity.this, new Observer<SetariNotificari>() {
                        @Override
                        public void onChanged(SetariNotificari setariNotificari) {
                            if(setariNotificari != null){
                                checkboxVoluntariat.setChecked(setariNotificari.getVreaNotificare());
                            }
                        }
                    });
                    if(student.isAsmi())
                        setariNotificariModal.getSetariNotificariByStudentIdAndType(student.getStudentId(), "voluntariatInscris").observe(NotificationActivity.this, new Observer<SetariNotificari>() {
                            @Override
                            public void onChanged(SetariNotificari setariNotificari) {
                                if(setariNotificari != null){
                                    checkboxVoluntariInscrisi.setChecked(setariNotificari.getVreaNotificare());
                                }
                            }
                        });

                    if(student.isAsmi()) voluntariSetari.setVisibility(View.VISIBLE);
                    else voluntariSetari.setVisibility(View.GONE);
                    evidentaNotificariModal.getAllEvidentaNotificariByStudentId(student.getStudentId()).observe(NotificationActivity.this, new Observer<List<EvidentaNotificari>>() {
                        @Override
                        public void onChanged(List<EvidentaNotificari> evidentaNotificaris) {
                            if(evidentaNotificaris != null && evidentaNotificaris.size() > 0){

                                notificationModal.getAllNotifications().observe(NotificationActivity.this, new Observer<List<Notification>>() {
                                    @Override
                                    public void onChanged(List<Notification> notifications) {
                                        if(notifications != null && notifications.size() > 0) {
                                            if(amStersSauPlecat == false){
                                            for (int i = 0; i < notifications.size(); i++) {

                                                boolean gasit = false;
                                                for (int j = 0; j < evidentaNotificaris.size(); j++) {
                                                    if (evidentaNotificaris.get(j).getNotificationId() == notifications.get(i).getNotificationId()) {
                                                        gasit = true;
                                                        break;
                                                    }
                                                }

                                                if (!gasit) {

                                                    if (notifications.get(i).getType().equals("voluntariatInscris") && checkboxVoluntariInscrisi.isChecked()) {

                                                        boolean gasit2 = false;
                                                        for(int ll = 0; ll < studentiVoluntari.size(); ll ++)
                                                            if(studentiVoluntari.get(ll) == Long.valueOf(notifications.get(i).getCauseId())){
                                                                gasit2 = true;
                                                                break;
                                                            }
                                                        if(!gasit2){
                                                            studentiVoluntari.add(Long.valueOf(notifications.get(i).getCauseId()));

                                                            SQLiteHelperVolunteerings dbHelper = new SQLiteHelperVolunteerings(NotificationActivity.this);

                                                            List<String> voluntariateleMele = dbHelper.getDashboardTabIdsByDashboardTabEmail(emailHolder);

                                                            int finalI3 = i;
                                                            evidentaVoluntariatModal.getAllEvidentaVoluntariatByStudentId(notifications.get(i).getCauseId().longValue()).observe(NotificationActivity.this, new Observer<List<EvidentaVoluntariat>>() {
                                                                @Override
                                                                public void onChanged(List<EvidentaVoluntariat> evidentaVoluntariats) {
                                                                    if (evidentaVoluntariats != null && evidentaVoluntariats.size() > 0) {
                                                                        for (int j = 0; j < evidentaVoluntariats.size(); j++) {

                                                                            for (int k = 0; k < voluntariateleMele.size(); k++) {
                                                                                if (evidentaVoluntariats.get(j).getVoluntariatId() == Integer.valueOf(voluntariateleMele.get(k)).longValue()) {
                                                                                    avemNotificari = true;
                                                                                    addEvenimentTab("Un student s-a inscris la un voluntariat administrat de tine!", notifications.get(finalI3), student.getStudentId());
                                                                                }
                                                                            }
                                                                        }
                                                                        if(avemNotificari == false)
                                                                            textAvemNotificari.setVisibility(View.VISIBLE);
                                                                        else textAvemNotificari.setVisibility(View.GONE);
                                                                    }
                                                                }
                                                            });
                                                        }
                                                    }
                                                    if (notifications.get(i).getType().equals("orar") && checkboxOrar.isChecked()) {
                                                        //vrem sa fim notificati de orar actualizat doar pt grupa noastra
                                                        if (student.getGrupaId() == notifications.get(i).getCauseId().longValue()){
                                                            avemNotificari = true;
                                                            addEvenimentTab("Orarul a fost actualizat!", notifications.get(i), student.getStudentId());
                                                        }
                                                    }

                                                    int finalI = i;
                                                    int finalI1 = i;
                                                    noteModal.getNoteByNoteId(notifications.get(i).getCauseId()).observe(NotificationActivity.this, new Observer<Note>() {
                                                        @Override
                                                        public void onChanged(Note note) {
                                                            if (note != null)
                                                                if (note.getStudentId() == student.getStudentId()) {
                                                                    if (notifications.get(finalI).getType().equals("nota actualizata") && checkboxNote.isChecked()){
                                                                        avemNotificari = true;
                                                                        addEvenimentTab("Notele au fost actualizate!", notifications.get(finalI1), student.getStudentId());
                                                                    }
                                                                    else if (notifications.get(finalI).getType().equals("nota noua") && checkboxNote.isChecked()){
                                                                        avemNotificari = true;
                                                                        addEvenimentTab("Note noi adaugate!", notifications.get(finalI1), student.getStudentId());
                                                                    }
                                                                }
                                                        }
                                                    });

                                                    // pt toata lumea
                                                    if (notifications.get(i).getType().equals("activitate")  && checkboxActivitati.isChecked()){
                                                        avemNotificari = true;
                                                        addEvenimentTab("Activitate nou adăugată", notifications.get(i), student.getStudentId());
                                                    }
                                                    if (notifications.get(i).getType().equals("anunt")  && checkboxAnunturi.isChecked()){
                                                        avemNotificari = true;
                                                        addEvenimentTab("Anunț nou adăugat!", notifications.get(i), student.getStudentId());
                                                    }
                                                    if (notifications.get(i).getType().equals("voluntariat")  && checkboxVoluntariat.isChecked()){
                                                        avemNotificari = true;
                                                        addEvenimentTab("Oportunitate nouă voluntariat ASMI!", notifications.get(i), student.getStudentId());
                                                    }
                                                    if (notifications.get(i).getType().equals("internship")  && checkboxInternshipuri.isChecked()){
                                                        avemNotificari = true;
                                                        addEvenimentTab("Oportunitate nouă internship!", notifications.get(i), student.getStudentId());
                                                    }

                                                    if (notifications.get(i).getType().equals("calendar")  && checkboxCalendar.isChecked()) {
                                                        int finalI2 = i;
                                                        calendarModal.getCalendarById(notifications.get(i).getCauseId()).observe(NotificationActivity.this, new Observer<Calendar>() {
                                                            @Override
                                                            public void onChanged(Calendar calendar) {
                                                                if (calendar != null)
                                                                    if (calendar.getValabilPentru().equals(student.getTipStudii())){
                                                                        avemNotificari = true;
                                                                        addEvenimentTab("Eveniment nou în calendar!", notifications.get(finalI2), student.getStudentId());
                                                                    }
                                                            }
                                                        });
                                                    }

                                                    //eventual doar seria/grupa
                                                    if (notifications.get(i).getType().equals("examen")  && checkboxExamene.isChecked()){
                                                        avemNotificari = true;
                                                        addEvenimentTab("Dată nouă examen a fost stabilită!", notifications.get(i), student.getStudentId());
                                                    }
                                                }

                                                if(avemNotificari == false)
                                                    textAvemNotificari.setVisibility(View.VISIBLE);
                                                else textAvemNotificari.setVisibility(View.GONE);

                                            }
                                        }
                                        }
                                    }
                                });
                            }
                        }
                    });


                }
            }
        });



        logoProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setariDisplay.setVisibility(View.VISIBLE);
                notificariDisplay.setVisibility(View.GONE);
            }
        });

        buttonSalveazaSetari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                studentModal.getStudentByEmail(emailHolder).observe(NotificationActivity.this, new Observer<Student>() {
                    @Override
                    public void onChanged(Student student) {
                        if(student != null){
                            setariDisplay.setVisibility(View.GONE);
                            notificariDisplay.setVisibility(View.VISIBLE);

                            SetariNotificariSQLiteHelper dbHelper = new SetariNotificariSQLiteHelper(NotificationActivity.this);

                            SetariNotificari setariNotificari = dbHelper.getSetariNotificariByStudentIdAndType(student.getStudentId(), "orar");
                            if (setariNotificari != null) {
                                setariNotificari.setVreaNotificare(checkboxOrar.isChecked());
                                dbHelper.updateSetariNotificari(setariNotificari);
                            }
                            SetariNotificari setariNotificariCalendar = dbHelper.getSetariNotificariByStudentIdAndType(student.getStudentId(), "calendar");
                            if (setariNotificariCalendar != null) {
                                setariNotificariCalendar.setVreaNotificare(checkboxCalendar.isChecked());
                                dbHelper.updateSetariNotificari(setariNotificariCalendar);
                            }
                            SetariNotificari setariNotificariExamen = dbHelper.getSetariNotificariByStudentIdAndType(student.getStudentId(), "examen");
                            if (setariNotificariExamen != null) {
                                setariNotificariExamen.setVreaNotificare(checkboxExamene.isChecked());
                                dbHelper.updateSetariNotificari(setariNotificariExamen);
                            }
                            SetariNotificari setariNotificariNota = dbHelper.getSetariNotificariByStudentIdAndType(student.getStudentId(), "nota");
                            if (setariNotificariNota != null) {
                                setariNotificariNota.setVreaNotificare(checkboxNote.isChecked());
                                dbHelper.updateSetariNotificari(setariNotificariNota);
                            }
                            SetariNotificari setariNotificariActivitate = dbHelper.getSetariNotificariByStudentIdAndType(student.getStudentId(), "activitate");
                            if (setariNotificariActivitate != null) {
                                setariNotificariActivitate.setVreaNotificare(checkboxActivitati.isChecked());
                                dbHelper.updateSetariNotificari(setariNotificariActivitate);
                            }
                            SetariNotificari setariNotificariAnunt = dbHelper.getSetariNotificariByStudentIdAndType(student.getStudentId(), "anunt");
                            if (setariNotificariAnunt != null) {
                                setariNotificariAnunt.setVreaNotificare(checkboxAnunturi.isChecked());
                                dbHelper.updateSetariNotificari(setariNotificariAnunt);
                            }
                            SetariNotificari setariNotificariInternship = dbHelper.getSetariNotificariByStudentIdAndType(student.getStudentId(), "internship");
                            if (setariNotificariInternship != null) {
                                setariNotificariInternship.setVreaNotificare(checkboxInternshipuri.isChecked());
                                dbHelper.updateSetariNotificari(setariNotificariInternship);
                            }
                            SetariNotificari setariNotificariVoluntariat = dbHelper.getSetariNotificariByStudentIdAndType(student.getStudentId(), "voluntariat");
                            if (setariNotificariVoluntariat != null) {
                                setariNotificariVoluntariat.setVreaNotificare(checkboxVoluntariat.isChecked());
                                dbHelper.updateSetariNotificari(setariNotificariVoluntariat);
                            }
                            if (student.isAsmi()) {
                                SetariNotificari setariNotificariVoluntariatInscris = dbHelper.getSetariNotificariByStudentIdAndType(student.getStudentId(), "voluntariatInscris");
                                if (setariNotificariVoluntariatInscris != null) {
                                    setariNotificariVoluntariatInscris.setVreaNotificare(checkboxVoluntariInscrisi.isChecked());
                                    dbHelper.updateSetariNotificari(setariNotificariVoluntariatInscris);
                                }
                            }

                            dbHelper.close();
                        }
                    }
                });
            }
        });


        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(whereFrom.equals("DashboardActivity")) {
                    Intent intent = new Intent(NotificationActivity.this, DashboardActivity.class);
                    startActivity(intent);
                    finish();
                }
                else if(whereFrom.equals("ExtracurricularActivity")) {
                    Intent intent = new Intent(NotificationActivity.this, ExtracurricularActivity.class);
                    startActivity(intent);
                    finish();
                }
                else if(whereFrom.equals("OrarActivity")) {
                    Intent intent = new Intent(NotificationActivity.this, OrarActivity.class);
                    startActivity(intent);
                    finish();
                }
                else if(whereFrom.equals("CalendarAcademicActivity")) {
                    Intent intent = new Intent(NotificationActivity.this, CalendarAcademicActivity.class);
                    startActivity(intent);
                    finish();
                }
                else if(whereFrom.equals("CarnetActivity")) {
                    Intent intent = new Intent(NotificationActivity.this, CarnetActivity.class);
                    startActivity(intent);
                    finish();
                }
                else if(whereFrom.equals("InformatiiGeneraleActivity")) {
                    Intent intent = new Intent(NotificationActivity.this, InformatiiGeneraleActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }

    private void addEvenimentTab(String tipNotificare, Notification notification, Long studentId) {
        LinearLayout evenimentTab = new LinearLayout(this);
        evenimentTab.setId(View.generateViewId());

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, dpToPx(this, 10), 0, 0);
        evenimentTab.setOrientation(LinearLayout.VERTICAL);
        evenimentTab.setLayoutParams(layoutParams);
        evenimentTab.setBackgroundResource(R.drawable.dashboard_article_background_v2);

        evenimentTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(notification.getType().equals("nota actualizata") || notification.getType().equals("nota noua")){
                   amStersSauPlecat = true;
                   Intent intent = new Intent(NotificationActivity.this, CarnetActivity.class);
                   startActivity(intent);
                   finish();
               }
               else if(notification.getType().equals("examen") || notification.getType().equals("calendar")){
                   calendarModal.getCalendarById(notification.getCauseId()).observe(NotificationActivity.this, new Observer<Calendar>() {
                       @Override
                       public void onChanged(Calendar calendar) {
                           if(calendar != null){
                               amStersSauPlecat = true;
                               Intent intent = new Intent(NotificationActivity.this, CalendarAcademicActivity.class);
                               intent.putExtra("prevActivity", "NotificationActivity");
                               intent.putExtra("luna", calendar.getLunaId());
                               startActivity(intent);
                               finish();
                           }
                       }
                   });
               }
               else if(notification.getType().equals("voluntariat")){
                   amStersSauPlecat = true;
                   Intent intent = new Intent(NotificationActivity.this, ExtracurricularActivity.class);
                   intent.putExtra("prevActivity", "NotificationActivity");
                   intent.putExtra("tabItemToSelect", "0");
                   startActivity(intent);
                   finish();
               }
                else if(notification.getType().equals("internship")){
                   amStersSauPlecat = true;
                    Intent intent = new Intent(NotificationActivity.this, ExtracurricularActivity.class);
                   intent.putExtra("prevActivity", "NotificationActivity");
                   intent.putExtra("tabItemToSelect", "1");
                    startActivity(intent);
                    finish();
                }
               else if(notification.getType().equals("anunt")){
                   amStersSauPlecat = true;
                   Intent intent = new Intent(NotificationActivity.this, DashboardActivity.class);
                   intent.putExtra("prevActivity", "NotificationActivity");
                   intent.putExtra("tabItemToSelect", "0");
                   startActivity(intent);
                   finish();
               }
               else if(notification.getType().equals("activitate")){
                   amStersSauPlecat = true;
                   Intent intent = new Intent(NotificationActivity.this, DashboardActivity.class);
                   intent.putExtra("prevActivity", "NotificationActivity");
                   intent.putExtra("tabItemToSelect", "1");
                   startActivity(intent);
                   finish();
               }
               else if(notification.getType().equals("orar")){
                   amStersSauPlecat = true;
                   Intent intent = new Intent(NotificationActivity.this, OrarActivity.class);
                   startActivity(intent);
                   finish();
               }
               else if(notification.getType().equals("voluntariatInscris")){
                   amStersSauPlecat = true;
                   Intent intent = new Intent(NotificationActivity.this, ExtracurricularActivity.class);
                   intent.putExtra("prevActivity", "NotificationActivity");
                   intent.putExtra("tabItemToSelect", "0");
                   startActivity(intent);
                   finish();
               }


               EvidentaNotificari evidentaNotificari = new EvidentaNotificari();
               evidentaNotificari.setStudentId(studentId);
               evidentaNotificari.setNotificationId(notification.getNotificationId());
               evidentaNotificariModal.insert(evidentaNotificari);
            }
        });

        LinearLayout valabilPentruLayout = new LinearLayout(this);
        valabilPentruLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        valabilPentruLayout.setOrientation(LinearLayout.HORIZONTAL);

        TextView valabilPentruTextView = new TextView(this);
        LinearLayout.LayoutParams valabilPentruParams = new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        valabilPentruParams.weight = 1;
        valabilPentruParams.rightMargin = 8;

        valabilPentruTextView.setLayoutParams(valabilPentruParams);
        valabilPentruTextView.setPadding(dpToPx(this,0), dpToPx(NotificationActivity.this,20),dpToPx(NotificationActivity.this,0),dpToPx(NotificationActivity.this,10));

        valabilPentruTextView.setText(tipNotificare);
        valabilPentruTextView.setTextSize(18);
        valabilPentruTextView.setTextColor(getResources().getColor(R.color.black));
        valabilPentruTextView.setTypeface(Typeface.create("casual", Typeface.NORMAL));


        Button dashboardTabDeleteMultiple = new Button(this);
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(dpToPx(this, 40), dpToPx(this, 40));
        layoutParams2.gravity = Gravity.END;
        //layoutParams2.setMarginStart(dpToPx(this, 150));
        dashboardTabDeleteMultiple.setLayoutParams(layoutParams2);

        dashboardTabDeleteMultiple.setBackgroundResource(R.drawable.baseline_close_24);

        dashboardTabDeleteMultiple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentLinearLayout.removeView((View) v.getParent().getParent());

                amStersSauPlecat = true;
                EvidentaNotificari evidentaNotificari = new EvidentaNotificari();
                evidentaNotificari.setStudentId(studentId);
                evidentaNotificari.setNotificationId(notification.getNotificationId());
                evidentaNotificariModal.insert(evidentaNotificari);
            }
        });

        valabilPentruLayout.addView(valabilPentruTextView);
        valabilPentruLayout.addView(dashboardTabDeleteMultiple);
        evenimentTab.addView(valabilPentruLayout);

        parentLinearLayout.addView(evenimentTab);
    }

    public static int dpToPx(Context context, float dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }
}