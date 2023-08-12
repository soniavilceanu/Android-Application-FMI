package com.example.myapplicationfmi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplicationfmi.Modals.CalendarModal;
import com.example.myapplicationfmi.Modals.CourseModal;
import com.example.myapplicationfmi.Modals.GroupModal;
import com.example.myapplicationfmi.Modals.NotificationModal;
import com.example.myapplicationfmi.Modals.ProfessorModal;
import com.example.myapplicationfmi.Modals.ProfessorSubjectModal;
import com.example.myapplicationfmi.Modals.StudentModal;
import com.example.myapplicationfmi.Modals.SubjectModal;
import com.example.myapplicationfmi.beans.Group;
import com.example.myapplicationfmi.beans.Professor;
import com.example.myapplicationfmi.beans.ProfessorWithGroups;
import com.example.myapplicationfmi.beans.ProfessorWithSubjects;
import com.example.myapplicationfmi.beans.Student;

import java.time.LocalDate;
import java.time.Month;

public class ProfileActivity extends AppCompatActivity {

    private Button buttonBack;
    private TextView nume, email, anUniversitar, formaInvatamant, an, semestru, serie, grupa, titluInformatii, infoAdmin;
    private ImageView bugetCheckbox, bursaCheckbox;
    private SQLiteDatabase sqLiteDatabaseObj;
    private SQLiteHelper sqLiteHelper;
    private LinearLayout studentInfoForms, profesorInfoForms;
    private MyRoomDatabase myRoomDatabase;
    private StudentModal studentModal;
    private GroupModal groupModal;
    private CourseModal courseModal;
    private NotificationModal notificationModal;
    private ProfessorModal professorModal;
    private SubjectModal subjectModal;
    private ProfessorSubjectModal professorSubjectModal;
    private CalendarModal calendarModal;

    private TextView grupe, pozitie;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        buttonBack = findViewById(R.id.buttonBack);
        nume = findViewById(R.id.nume);
        email = findViewById(R.id.email);
        anUniversitar = findViewById(R.id.anUniversitar);
        formaInvatamant = findViewById(R.id.formaInvatamant);
        an = findViewById(R.id.an);
        semestru = findViewById(R.id.semestru);
        serie = findViewById(R.id.serie);
        grupa = findViewById(R.id.grupa);
        bugetCheckbox = findViewById(R.id.bugetCheckbox);
        bursaCheckbox = findViewById(R.id.bursaCheckbox);
        studentInfoForms = findViewById(R.id.studentInfoForms);
        profesorInfoForms = findViewById(R.id.profesorInfoForms);
        titluInformatii = findViewById(R.id.titluInformatii);
        infoAdmin = findViewById(R.id.infoAdmin);
        grupe = findViewById(R.id.grupe);
        pozitie = findViewById(R.id.pozitie);


        studentModal = new ViewModelProvider(this).get(StudentModal.class);
        groupModal = new ViewModelProvider(this).get(GroupModal.class);
        courseModal = new ViewModelProvider(this).get(CourseModal.class);
        notificationModal = new ViewModelProvider(this).get(NotificationModal.class);
        professorModal = new ViewModelProvider(this).get(ProfessorModal.class);
        subjectModal = new ViewModelProvider(this).get(SubjectModal.class);
        professorSubjectModal = new ViewModelProvider(this).get(ProfessorSubjectModal.class);
        calendarModal = new ViewModelProvider(this).get(CalendarModal.class);

        Intent intent = getIntent();
        String whereFrom = intent.getStringExtra("previousActivity");

        SharedPreferences sharedPreferences = getSharedPreferences(DashboardActivity.SHARED_PREFS, MODE_PRIVATE);
        String emailHolder = sharedPreferences.getString("email", "");


        if(emailHolder.contains("admin")){
            studentInfoForms.setVisibility(View.GONE);
            infoAdmin.setVisibility(View.VISIBLE);
            profesorInfoForms.setVisibility(View.GONE);

            titluInformatii.setText("Informații cont");
            nume.setText(nume.getText() + "ADMIN");
            email.setText(email.getText() + emailHolder);
        }
        else if(emailHolder.contains("@unibuc.ro")){
            studentInfoForms.setVisibility(View.GONE);
            infoAdmin.setVisibility(View.GONE);
            profesorInfoForms.setVisibility(View.VISIBLE);

            titluInformatii.setText("Info. cadru didactic");
            email.setText(email.getText() + emailHolder);
            professorModal.getProfessorByEmail(emailHolder).observe(ProfileActivity.this, new Observer<Professor>() {
                @Override
                public void onChanged(Professor professor) {
                    if (professor != null) {

                        nume.setText(nume.getText() + professor.getNume() + " " + professor.getPrenume());
                        pozitie.setText(pozitie.getText() + professor.getPozitie());
                        professorModal.getProfessorWithGroupsById(professor.getProfessorId()).observe(ProfileActivity.this, new Observer<ProfessorWithGroups>() {
                            @Override
                            public void onChanged(ProfessorWithGroups professorWithGroups) {
                                if (professorWithGroups != null) {
                                    for(int i = 0; i < professorWithGroups.groups.size() - 1; i ++)
                                        grupe.setText(grupe.getText().toString() + professorWithGroups.groups.get(i).getNumar() + ", ");
                                    grupe.setText(grupe.getText().toString() + professorWithGroups.groups.get(professorWithGroups.groups.size() - 1).getNumar());
                                }
                            }
                        });

                        professorModal.getProfessorWithSubjectsById(professor.getProfessorId()).observe(ProfileActivity.this, new Observer<ProfessorWithSubjects>() {
                            @Override
                            public void onChanged(ProfessorWithSubjects professorWithSubjects) {
                                if (professorWithSubjects != null) {
                                    for(int i = 0; i < professorWithSubjects.subjects.size() - 1; i ++){
                                        TextView textView = new TextView(ProfileActivity.this);
                                        textView.setLayoutParams(new LinearLayout.LayoutParams(
                                                ViewGroup.LayoutParams.MATCH_PARENT,
                                                ViewGroup.LayoutParams.WRAP_CONTENT));
                                        textView.setText(professorWithSubjects.subjects.get(i).getDenumire());
                                        textView.setTextSize(22);
                                        textView.setTextColor(Color.BLACK);
                                        textView.setGravity(Gravity.CENTER);
                                        textView.setPadding(0, 20, 8, 0);
                                        textView.setBackgroundColor(Color.TRANSPARENT);
                                        textView.setIncludeFontPadding(false);
                                        textView.setTypeface(Typeface.create("casual", Typeface.NORMAL));

//                                        View view = new View(ProfileActivity.this);
//                                        LinearLayout.LayoutParams viewParams = new LinearLayout.LayoutParams(200, 2);
//                                        viewParams.gravity = Gravity.CENTER_VERTICAL | Gravity.START;
//                                        view.setLayoutParams(viewParams);
//                                        view.setBackgroundColor(Color.WHITE);
//                                        view.setPadding(5, 10, 0, 0);

                                        View view = new View(ProfileActivity.this);
                                        LinearLayout.LayoutParams viewLayoutParams = new LinearLayout.LayoutParams(dpToPx(ProfileActivity.this,200),dpToPx(ProfileActivity.this,2));
                                        viewLayoutParams.setMargins(dpToPx(ProfileActivity.this,5), 0, 0, 0);
                                        viewLayoutParams.gravity = Gravity.CENTER;
                                        view.setLayoutParams(viewLayoutParams);
                                        view.setBackgroundResource(R.color.white);

                                        profesorInfoForms.addView(textView);
                                        profesorInfoForms.addView(view);
                                    }
                                }
                            }
                        });
                    }
                }
            });
        }
        else{
            studentInfoForms.setVisibility(View.VISIBLE);
            infoAdmin.setVisibility(View.GONE);
            profesorInfoForms.setVisibility(View.GONE);

            titluInformatii.setText("Informații student");

            studentModal.getStudentByEmail(emailHolder).observe(ProfileActivity.this, new Observer<Student>() {
                @Override
                public void onChanged(Student student) {
                    if (student != null) {
                       groupModal.getGroupById(student.getGrupaId()).observe(ProfileActivity.this, new Observer<Group>() {
                           @Override
                           public void onChanged(Group group) {
                               nume.setText(nume.getText() + student.getNume() + " " + student.getPrenume());
                               email.setText(email.getText() + student.getEmail());
                               int anUniversitarCurentStart = Integer.valueOf(student.getAnInscriere()) + Integer.parseInt(String.valueOf(String.valueOf(student.getAn()).charAt(String.valueOf(student.getAn()).length() - 1))) - 1;
                               anUniversitar.setText(anUniversitar.getText() + String.valueOf(anUniversitarCurentStart) + " - " + String.valueOf(anUniversitarCurentStart + 1));
                               formaInvatamant.setText(formaInvatamant.getText() + group.getFormaDeInvatamant());
                               an.setText(an.getText() + String.valueOf(String.valueOf(student.getAn()).charAt(String.valueOf(student.getAn()).length() - 1)));
                               if (student.getTipStudii().equals("Licenta")) {
                                   serie.setText(serie.getText() + String.valueOf(group.getSerie().charAt(group.getSerie().length() - 2)) + String.valueOf(group.getSerie().charAt(group.getSerie().length() - 1)));
                               } else serie.setText(serie.getText() + "-");
                               grupa.setText(grupa.getText() + String.valueOf(String.valueOf(group.getNumar()).charAt(String.valueOf(group.getNumar()).length() - 3)) + String.valueOf(String.valueOf(group.getNumar()).charAt(String.valueOf(group.getNumar()).length() - 2)) + String.valueOf(String.valueOf(group.getNumar()).charAt(String.valueOf(group.getNumar()).length() - 1)));

                               LocalDate currentDate = null;
                               if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                                   currentDate = LocalDate.now();
                                   Month[] monthsToCheck = {Month.OCTOBER, Month.NOVEMBER, Month.DECEMBER, Month.JANUARY, Month.FEBRUARY};
                                   if (containsMonth(currentDate.getMonth(), monthsToCheck)) {
                                       semestru.setText(semestru.getText() + String.valueOf(1));
                                   } else {
                                       semestru.setText(semestru.getText() + String.valueOf(2));
                                   }
                               }
                               if (student.getTaxa() == true)
                                   bugetCheckbox.setImageResource(R.drawable.baseline_remove_circle_outline_24);
                               else
                                   bugetCheckbox.setImageResource(R.drawable.baseline_check_circle_outline_24);

                               if (student.getBursa() == false)
                                   bursaCheckbox.setImageResource(R.drawable.baseline_remove_circle_outline_24);
                               else
                                   bursaCheckbox.setImageResource(R.drawable.baseline_check_circle_outline_24);
                           }
                       });
                    }
                }
            });

        }




//        sqLiteHelper = new SQLiteHelper(this);
//        sqLiteDatabaseObj = sqLiteHelper.getWritableDatabase();
//
//            studentInfoForms.setVisibility(View.VISIBLE);
//            infoAdmin.setVisibility(View.GONE);
//        profesorInfoForms.setVisibility(View.GONE);
//
//            Cursor cursor = sqLiteDatabaseObj.query(SQLiteHelper.TABLE_NAME, new String[]{SQLiteHelper.Table_Column_1_Nume, SQLiteHelper.Table_Column_2_Prenume, SQLiteHelper.Table_Column_3_Email, SQLiteHelper.Table_Column_4_Password, SQLiteHelper.Table_Column_5_An, SQLiteHelper.Table_Column_6_Serie, SQLiteHelper.Table_Column_7_Grupa, SQLiteHelper.Table_Column_8_Taxa, SQLiteHelper.Table_Column_9_Bursa, SQLiteHelper.Table_Column_10_An_Inscriere, SQLiteHelper.Table_Column_11_Forma_De_Invatamant, SQLiteHelper.Table_Column_12_Tip_Studii}, SQLiteHelper.Table_Column_3_Email + " = ?", new String[]{emailHolder}, null, null, null);
//
//            if (cursor.moveToFirst()) {
//                String numeExtras = cursor.getString(cursor.getColumnIndexOrThrow(SQLiteHelper.Table_Column_1_Nume));
//                String prenumeExtras = cursor.getString(cursor.getColumnIndexOrThrow(SQLiteHelper.Table_Column_2_Prenume));
//                String emailExtras = cursor.getString(cursor.getColumnIndexOrThrow(SQLiteHelper.Table_Column_3_Email));
//                String passwordExtras = cursor.getString(cursor.getColumnIndexOrThrow(SQLiteHelper.Table_Column_4_Password));
//                String anExtras = cursor.getString(cursor.getColumnIndexOrThrow(SQLiteHelper.Table_Column_5_An));
//                String serieExtras = cursor.getString(cursor.getColumnIndexOrThrow(SQLiteHelper.Table_Column_6_Serie));
//                String grupaExtras = cursor.getString(cursor.getColumnIndexOrThrow(SQLiteHelper.Table_Column_7_Grupa));
//                String taxaExtras = cursor.getString(cursor.getColumnIndexOrThrow(SQLiteHelper.Table_Column_8_Taxa));
//                String bursaExtras = cursor.getString(cursor.getColumnIndexOrThrow(SQLiteHelper.Table_Column_9_Bursa));
//                String anInscriereExtras = cursor.getString(cursor.getColumnIndexOrThrow(SQLiteHelper.Table_Column_10_An_Inscriere));
//                String formaInvatamantExtras = cursor.getString(cursor.getColumnIndexOrThrow(SQLiteHelper.Table_Column_11_Forma_De_Invatamant));
//                String tipStudiiExtras = cursor.getString(cursor.getColumnIndexOrThrow(SQLiteHelper.Table_Column_12_Tip_Studii));
//
//                if(emailHolder.contains("admin")){
//                    studentInfoForms.setVisibility(View.GONE);
//                    infoAdmin.setVisibility(View.VISIBLE);
//                    titluInformatii.setText("Informații cont");
//                    nume.setText(nume.getText() + "ADMIN");
//                    email.setText(email.getText() + emailExtras);
//                }
//                else {
//                    nume.setText(nume.getText() + numeExtras + " " + prenumeExtras);
//                    email.setText(email.getText() + emailExtras);
//                    int anUniversitarCurentStart = Integer.valueOf(anInscriereExtras) + Integer.parseInt(String.valueOf(anExtras.charAt(anExtras.length() - 1))) - 1;
//                    anUniversitar.setText(anUniversitar.getText() + String.valueOf(anUniversitarCurentStart) + " - " + String.valueOf(anUniversitarCurentStart + 1));
//                    formaInvatamant.setText(formaInvatamant.getText() + formaInvatamantExtras);
//                    an.setText(an.getText() + String.valueOf(anExtras.charAt(anExtras.length() - 1)));
//                    if (tipStudiiExtras.equals("Licenta")) {
//                        serie.setText(serie.getText() + String.valueOf(serieExtras.charAt(serieExtras.length() - 2)) + String.valueOf(serieExtras.charAt(serieExtras.length() - 1)));
//                    } else serie.setText(serie.getText() + "-");
//                    grupa.setText(grupa.getText() + String.valueOf(grupaExtras.charAt(grupaExtras.length() - 3)) + String.valueOf(grupaExtras.charAt(grupaExtras.length() - 2)) + String.valueOf(grupaExtras.charAt(grupaExtras.length() - 1)));
//
//                    LocalDate currentDate = null;
//                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//                        currentDate = LocalDate.now();
//                        Month[] monthsToCheck = {Month.OCTOBER, Month.NOVEMBER, Month.DECEMBER, Month.JANUARY, Month.FEBRUARY};
//                        if (containsMonth(currentDate.getMonth(), monthsToCheck)) {
//                            semestru.setText(semestru.getText() + String.valueOf(1));
//                        } else {
//                            semestru.setText(semestru.getText() + String.valueOf(2));
//                        }
//                    }
//                    if (taxaExtras.equals("true"))
//                        bugetCheckbox.setImageResource(R.drawable.baseline_remove_circle_outline_24);
//                    else
//                        bugetCheckbox.setImageResource(R.drawable.baseline_check_circle_outline_24);
//
//                    if (bursaExtras.equals("false"))
//                        bursaCheckbox.setImageResource(R.drawable.baseline_remove_circle_outline_24);
//                    else
//                        bursaCheckbox.setImageResource(R.drawable.baseline_check_circle_outline_24);
//                }
//                cursor.close();
//            }

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(whereFrom.equals("DashboardActivity")) {
                    Intent intent = new Intent(ProfileActivity.this, DashboardActivity.class);
                    startActivity(intent);
                    finish();
                }
                else if(whereFrom.equals("ExtracurricularActivity")) {
                    Intent intent = new Intent(ProfileActivity.this, ExtracurricularActivity.class);
                    startActivity(intent);
                    finish();
                }
                else if(whereFrom.equals("OrarActivity")) {
                    Intent intent = new Intent(ProfileActivity.this, OrarActivity.class);
                    startActivity(intent);
                    finish();
                }
                else if(whereFrom.equals("CalendarAcademicActivity")) {
                    Intent intent = new Intent(ProfileActivity.this, CalendarAcademicActivity.class);
                    startActivity(intent);
                    finish();
                }
                else if(whereFrom.equals("CarnetActivity")) {
                    Intent intent = new Intent(ProfileActivity.this, CarnetActivity.class);
                    startActivity(intent);
                    finish();
                }
                else if(whereFrom.equals("InformatiiGeneraleActivity")) {
                    Intent intent = new Intent(ProfileActivity.this, InformatiiGeneraleActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }
    private static boolean containsMonth(Month month, Month[] months) {
        for (Month m : months) {
            if (m == month) {
                return true;
            }
        }
        return false;
    }

    public static int dpToPx(Context context, float dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }
}

