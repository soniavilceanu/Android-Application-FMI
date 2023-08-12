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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplicationfmi.Modals.CalendarModal;
import com.example.myapplicationfmi.Modals.CourseModal;
import com.example.myapplicationfmi.Modals.GroupModal;
import com.example.myapplicationfmi.Modals.NoteModal;
import com.example.myapplicationfmi.Modals.NotificationModal;
import com.example.myapplicationfmi.Modals.ProfessorModal;
import com.example.myapplicationfmi.Modals.ProfessorSubjectModal;
import com.example.myapplicationfmi.Modals.StudentModal;
import com.example.myapplicationfmi.Modals.SubjectModal;
import com.example.myapplicationfmi.beans.Calendar;
import com.example.myapplicationfmi.beans.Group;
import com.example.myapplicationfmi.beans.Note;
import com.example.myapplicationfmi.beans.Notification;
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
    private LinearLayout parentLinearLayout;
    private DateTimeFormatter formatter;

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

        parentLinearLayout = findViewById(R.id.evenimenteLinearLayoutMultiple);
        buttonBack = findViewById(R.id.buttonBack);
        Intent intent = getIntent();
        String whereFrom = intent.getStringExtra("previousActivity");

        SharedPreferences sharedPreferences = getSharedPreferences(DashboardActivity.SHARED_PREFS, MODE_PRIVATE);
        String emailHolder = sharedPreferences.getString("email", "");

        studentModal.getStudentByEmail(emailHolder).observe(NotificationActivity.this, new Observer<Student>() {
            @Override
            public void onChanged(Student student) {
                if(student != null){
                    notificationModal.getAllNotifications().observe(NotificationActivity.this, new Observer<List<Notification>>() {
                        @Override
                        public void onChanged(List<Notification> notifications) {
                            if(notifications != null){
                                for(int i = 0; i < notifications.size(); i ++){
                                    if(notifications.get(i).getType().equals("orar")){
                                        //vrem sa fim notificati de orar actualizat doar pt grupa noastra
                                        if(student.getGrupaId() == notifications.get(i).getCauseId().longValue())
                                            addEvenimentTab("Orarul a fost actualizat!");
                                    }

                                    int finalI = i;
                                    noteModal.getNoteByNoteId(notifications.get(i).getCauseId()).observe(NotificationActivity.this, new Observer<Note>() {
                                        @Override
                                        public void onChanged(Note note) {
                                            if(note != null)
                                                if(note.getStudentId() == student.getStudentId()){
                                                    if(notifications.get(finalI).getType().equals("nota actualizata"))
                                                        addEvenimentTab("Notele au fost actualizate!");
                                                    else if(notifications.get(finalI).getType().equals("nota noua"))
                                                        addEvenimentTab("Note noi adaugate!");
                                                }
                                        }
                                    });

                                    // pt toata lumea
                                    if(notifications.get(i).getType().equals("activitate"))
                                        addEvenimentTab("Activitate nou adăugată");
                                    if(notifications.get(i).getType().equals("anunt"))
                                        addEvenimentTab("Anunț nou adăugat!");
                                    if(notifications.get(i).getType().equals("voluntariat"))
                                        addEvenimentTab("Oportunitate nouă voluntariat ASMI!");
                                    if(notifications.get(i).getType().equals("internship"))
                                        addEvenimentTab("Oportunitate nouă internship!");

                                    if(notifications.get(i).getType().equals("calendar"))
                                        calendarModal.getCalendarById(notifications.get(i).getCauseId()).observe(NotificationActivity.this, new Observer<Calendar>() {
                                            @Override
                                            public void onChanged(Calendar calendar) {
                                                if(calendar != null)
                                                    if(calendar.getValabilPentru().equals(student.getTipStudii()))
                                                        addEvenimentTab("Eveniment nou în calendar!");
                                            }
                                        });

                                    //eventual doar seria/grupa
                                    if(notifications.get(i).getType().equals("examen"))
                                        addEvenimentTab("Dată nouă examen a fost stabilită!");
                                }
                            }
                        }
                    });
                }
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

    private void addEvenimentTab(String tipNotificare) {
        LinearLayout evenimentTab = new LinearLayout(this);
        evenimentTab.setId(View.generateViewId());

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, dpToPx(this, 10), 0, 0);
        evenimentTab.setOrientation(LinearLayout.VERTICAL);
        evenimentTab.setLayoutParams(layoutParams);
        evenimentTab.setBackgroundResource(R.drawable.dashboard_article_background_v2);

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