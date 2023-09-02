package com.example.myapplicationfmi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplicationfmi.Modals.CalendarModal;
import com.example.myapplicationfmi.Modals.CourseModal;
import com.example.myapplicationfmi.Modals.EvidentaNotificariModal;
import com.example.myapplicationfmi.Modals.EvidentaVoluntariatModal;
import com.example.myapplicationfmi.Modals.GroupModal;
import com.example.myapplicationfmi.Modals.NoteModal;
import com.example.myapplicationfmi.Modals.NotificationModal;
import com.example.myapplicationfmi.Modals.ProfessorModal;
import com.example.myapplicationfmi.Modals.ProfessorSubjectModal;
import com.example.myapplicationfmi.Modals.StudentModal;
import com.example.myapplicationfmi.Modals.SubjectModal;
import com.example.myapplicationfmi.beans.Calendar;
import com.example.myapplicationfmi.beans.EvidentaNotificari;
import com.example.myapplicationfmi.beans.EvidentaVoluntariat;
import com.example.myapplicationfmi.beans.Notification;
import com.example.myapplicationfmi.beans.Student;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class DetailsDashboardTabsActivity extends AppCompatActivity {

    private Button buttonBack;
    private TextView titluInformatii;
    private TextView dateInformatii;
    private TextView infoAdmin;
    private TextView emailInfo, textInscriereVoluntariat, textEleviInscrisi;
    private CheckBox checkboxVoluntariat;
    private LinearLayout layoutVoluntariat, studentiVoluntariat;

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
    private DateTimeFormatter formatter;
    private CheckBox checkboxMarcheazaPrincipal;
    private TextView textMarcheaza;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_dashboard_tabs);

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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            formatter = DateTimeFormatter.ofPattern("HH:mm");
        }

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String date = intent.getStringExtra("date");
        String body = intent.getStringExtra("body");
        String whereFrom = intent.getStringExtra("previousActivity");
        String email = intent.getStringExtra("email");
        String id = intent.getStringExtra("id");

        SharedPreferences sharedPreferences = getSharedPreferences(DashboardActivity.SHARED_PREFS, MODE_PRIVATE);
        String emailHolder = sharedPreferences.getString("email", "");

        buttonBack = findViewById(R.id.buttonBack);
        titluInformatii = findViewById(R.id.titluInformatii);
        dateInformatii = findViewById(R.id.dateInformatii);
        infoAdmin = findViewById(R.id.infoAdmin);
        emailInfo = findViewById(R.id.emailInfo);
        textInscriereVoluntariat = findViewById(R.id.textInscriereVoluntariat);
        checkboxVoluntariat = findViewById(R.id.checkboxVoluntariat);
        layoutVoluntariat = findViewById(R.id.layoutVoluntariat);
        studentiVoluntariat = findViewById(R.id.studentiVoluntariat);
        textEleviInscrisi = findViewById(R.id.textEleviInscrisi);
        checkboxMarcheazaPrincipal = findViewById(R.id.checkboxMarcheazaPrincipal);
        textMarcheaza = findViewById(R.id.textMarcheaza);

                titluInformatii.setText(title);
        dateInformatii.setText(date);
        infoAdmin.setText(body);
        emailInfo.setText(emailInfo.getText() + email);

        if(MainActivity.USER_TYPE == 1 || MainActivity.USER_TYPE == 3){
            layoutVoluntariat.setVisibility(View.GONE);
            studentiVoluntariat.setVisibility(View.GONE);
            textEleviInscrisi.setVisibility(View.GONE);
        }

        if(MainActivity.USER_TYPE == 1){
            textMarcheaza.setVisibility(View.VISIBLE);
            checkboxMarcheazaPrincipal.setVisibility(View.VISIBLE);
        }
        else {
            textMarcheaza.setVisibility(View.GONE);
            checkboxMarcheazaPrincipal.setVisibility(View.GONE);
        }

        if(whereFrom.equals("announcements") || whereFrom.equals("activities")){
            emailInfo.setVisibility(View.GONE);
            layoutVoluntariat.setVisibility(View.GONE);
            studentiVoluntariat.setVisibility(View.GONE);
            textEleviInscrisi.setVisibility(View.GONE);

            checkboxMarcheazaPrincipal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if(whereFrom.equals("announcements")){
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("dashboardTabTitle", String.valueOf(title));
                        editor.putString("dashboardTabBody", String.valueOf(body));
                        editor.putString("dashboardTabDate", String.valueOf(date));
                        editor.apply();
                    }
                   else{
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("dashboardTabTitleActivities", String.valueOf(title));
                        editor.putString("dashboardTabBodyActivities", String.valueOf(body));
                        editor.putString("dashboardTabDateActivities", String.valueOf(date));
                        editor.apply();
                    }
                }
            });

        }
        else {
            String previousTabItem = intent.getStringExtra("previousTabItem");
            if(previousTabItem != null && previousTabItem.equals("voluntariat")) {
                //vine din voluntariat
                emailInfo.setVisibility(View.GONE);
                if (!email.equals(emailHolder) && MainActivity.USER_TYPE == 2) {
                    // e student oarecare
                    layoutVoluntariat.setVisibility(View.VISIBLE);
                    studentiVoluntariat.setVisibility(View.GONE);
                    textEleviInscrisi.setVisibility(View.GONE);

                    studentModal.getStudentByEmail(emailHolder).observe(DetailsDashboardTabsActivity.this, new Observer<Student>() {
                        @Override
                        public void onChanged(Student student) {
                            if (student != null) {
                                evidentaVoluntariatModal.getAllEvidentaVoluntariatByStudentId(student.getStudentId()).observe(DetailsDashboardTabsActivity.this, new Observer<List<EvidentaVoluntariat>>() {
                                    @Override
                                    public void onChanged(List<EvidentaVoluntariat> evidentaVoluntariats) {
                                        if (evidentaVoluntariats != null && evidentaVoluntariats.size() > 0) {
                                            for (int i = 0; i < evidentaVoluntariats.size(); i++) {
                                                if (evidentaVoluntariats.get(i).getVoluntariatId() == Long.valueOf(Integer.parseInt(id))) {
                                                    textInscriereVoluntariat.setText("Doriti sa anulati inscrierea?");
                                                }
                                            }
                                        }
                                    }
                                });
                            }
                        }
                    });
                } else {
                    if(MainActivity.USER_TYPE == 2){
                    // e studentul ASMI care se ocupa cu voluntariatul asta
                    layoutVoluntariat.setVisibility(View.GONE);
                    studentiVoluntariat.setVisibility(View.VISIBLE);
                    textEleviInscrisi.setVisibility(View.VISIBLE);
                    studentiVoluntariat.removeAllViews();

//                    SQLiteHelperVolunteerings dbHelper = new SQLiteHelperVolunteerings(DetailsDashboardTabsActivity.this);
//
//                    List<String> voluntariateleMele = dbHelper.getDashboardTabIdsByDashboardTabEmail(emailHolder);
//
//                    for (int i = 0; i < voluntariateleMele.size(); i++) {
                        evidentaVoluntariatModal.getAllEvidentaVoluntariatByVoluntariatId(Long.valueOf(id)).observe(DetailsDashboardTabsActivity.this, new Observer<List<EvidentaVoluntariat>>() {
                            @Override
                            public void onChanged(List<EvidentaVoluntariat> evidentaVoluntariats) {
                                if (evidentaVoluntariats != null && evidentaVoluntariats.size() > 0) {
                                    for (int j = 0; j < evidentaVoluntariats.size(); j++) {
                                        //pt studentii cu studentIds de aici afisam numele
                                        studentModal.getStudentById(evidentaVoluntariats.get(j).getStudentId()).observe(DetailsDashboardTabsActivity.this, new Observer<Student>() {
                                            @Override
                                            public void onChanged(Student student) {
                                                if(student != null)
                                                    addVoluntariatStudentTab(student.getNume() + " " + student.getPrenume());
                                            }
                                        });
                                    }
                                }
                            }
                        });
                  //  }


                }
            }
            }
            else{
                // vine din internship-uri
                emailInfo.setVisibility(View.VISIBLE);
                layoutVoluntariat.setVisibility(View.GONE);
                textEleviInscrisi.setVisibility(View.GONE);
            }

        }

        checkboxVoluntariat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    studentModal.getStudentByEmail(emailHolder).observe(DetailsDashboardTabsActivity.this, new Observer<Student>() {
                        @Override
                        public void onChanged(Student student) {
                            if(student != null){
                                if(textInscriereVoluntariat.getText().equals("Doriti sa anulati inscrierea?")){
                                    evidentaVoluntariatModal.getAllEvidentaVoluntariatByStudentIdAndVoluntariatId(student.getStudentId(), Long.valueOf(Integer.parseInt(id))).observe(DetailsDashboardTabsActivity.this, new Observer<EvidentaVoluntariat>() {
                                        @Override
                                        public void onChanged(EvidentaVoluntariat evidentaVoluntariat) {
                                            if(evidentaVoluntariat != null){
                                                evidentaVoluntariatModal.delete(evidentaVoluntariat);
                                                textInscriereVoluntariat.setText("Vreau sa ma inscriu!");
                                                checkboxVoluntariat.setChecked(false);
                                            }
                                        }
                                    });
                                }
                                else{
                                    EvidentaVoluntariat evidentaVoluntariat = new EvidentaVoluntariat();
                                    evidentaVoluntariat.setVoluntariatId(Long.valueOf(id));
                                    evidentaVoluntariat.setStudentId(student.getStudentId());
                                    evidentaVoluntariatModal.insert(evidentaVoluntariat);
                                    checkboxVoluntariat.setChecked(false);
                                    textInscriereVoluntariat.setText("Doriti sa anulati inscrierea?");

                                    Notification notification = new Notification();
                                    notification.setType("voluntariatInscris");

                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                        notification.setTime(LocalTime.now().format(formatter));
                                    }
                                    notification.setCauseId(Math.toIntExact(student.getStudentId()));
                                    notificationModal.insert(notification);
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
                if(whereFrom.equals("announcements") || whereFrom.equals("activities")){
                    Intent intent = new Intent(DetailsDashboardTabsActivity.this, DashboardActivity.class);
                    startActivity(intent);
                    finish();
                }
                else if(whereFrom.equals("ExtracurricularActivity")){
                    Intent intent = new Intent(DetailsDashboardTabsActivity.this, ExtracurricularActivity.class);
                    startActivity(intent);
                    finish();
                }
                }
        });
    }
    public void addVoluntariatStudentTab(String numeStudent){

        TextView valabilPentruTextView = new TextView(this);
        LinearLayout.LayoutParams valabilPentruParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        valabilPentruParams.rightMargin = 8;

        valabilPentruTextView.setLayoutParams(valabilPentruParams);
        valabilPentruTextView.setPadding(dpToPx(this,0), dpToPx(DetailsDashboardTabsActivity.this,20),dpToPx(DetailsDashboardTabsActivity.this,0),dpToPx(DetailsDashboardTabsActivity.this,10));

        valabilPentruTextView.setText(numeStudent);
        valabilPentruTextView.setTextSize(18);
        valabilPentruTextView.setTextColor(getResources().getColor(R.color.black));
        valabilPentruTextView.setTypeface(Typeface.create("casual", Typeface.NORMAL));

        View view = new View(this);
        LinearLayout.LayoutParams viewLayoutParams = new LinearLayout.LayoutParams(dpToPx(this,380),dpToPx(this,2));
        viewLayoutParams.setMargins(dpToPx(this,5), 0, 0, 0);
        view.setLayoutParams(viewLayoutParams);
        view.setBackgroundResource(R.color.white);

        studentiVoluntariat.addView(valabilPentruTextView);
        studentiVoluntariat.addView(view);
    }
    public static int dpToPx(Context context, float dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }
}