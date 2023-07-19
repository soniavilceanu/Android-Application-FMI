package com.example.myapplicationfmi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {

    private Button buttonBack;
    private TextView nume, email, anUniversitar, formaInvatamant, an, semestru, serie, grupa, titluInformatii, infoAdmin;
    private ImageView bugetCheckbox, bursaCheckbox;
    private SQLiteDatabase sqLiteDatabaseObj;
    private SQLiteHelper sqLiteHelper;
    private LinearLayout studentInfoForms;

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
        titluInformatii = findViewById(R.id.titluInformatii);
        infoAdmin = findViewById(R.id.infoAdmin);

        Intent intent = getIntent();
        String whereFrom = intent.getStringExtra("previousActivity");

        sqLiteHelper = new SQLiteHelper(this);
        sqLiteDatabaseObj = sqLiteHelper.getWritableDatabase();

        SharedPreferences sharedPreferences = getSharedPreferences(DashboardActivity.SHARED_PREFS, MODE_PRIVATE);
        String emailHolder = sharedPreferences.getString("email", "");



            studentInfoForms.setVisibility(View.VISIBLE);
            infoAdmin.setVisibility(View.GONE);
            Cursor cursor = sqLiteDatabaseObj.query(SQLiteHelper.TABLE_NAME, new String[]{SQLiteHelper.Table_Column_1_Nume, SQLiteHelper.Table_Column_2_Prenume, SQLiteHelper.Table_Column_3_Email, SQLiteHelper.Table_Column_4_Password, SQLiteHelper.Table_Column_5_An, SQLiteHelper.Table_Column_6_Serie, SQLiteHelper.Table_Column_7_Grupa, SQLiteHelper.Table_Column_8_Taxa, SQLiteHelper.Table_Column_9_Bursa, SQLiteHelper.Table_Column_10_An_Inscriere, SQLiteHelper.Table_Column_11_Forma_De_Invatamant, SQLiteHelper.Table_Column_12_Tip_Studii}, SQLiteHelper.Table_Column_3_Email + " = ?", new String[]{emailHolder}, null, null, null);

            if (cursor.moveToFirst()) {
                String numeExtras = cursor.getString(cursor.getColumnIndexOrThrow(SQLiteHelper.Table_Column_1_Nume));
                String prenumeExtras = cursor.getString(cursor.getColumnIndexOrThrow(SQLiteHelper.Table_Column_2_Prenume));
                String emailExtras = cursor.getString(cursor.getColumnIndexOrThrow(SQLiteHelper.Table_Column_3_Email));
                String passwordExtras = cursor.getString(cursor.getColumnIndexOrThrow(SQLiteHelper.Table_Column_4_Password));
                String anExtras = cursor.getString(cursor.getColumnIndexOrThrow(SQLiteHelper.Table_Column_5_An));
                String serieExtras = cursor.getString(cursor.getColumnIndexOrThrow(SQLiteHelper.Table_Column_6_Serie));
                String grupaExtras = cursor.getString(cursor.getColumnIndexOrThrow(SQLiteHelper.Table_Column_7_Grupa));
                String taxaExtras = cursor.getString(cursor.getColumnIndexOrThrow(SQLiteHelper.Table_Column_8_Taxa));
                String bursaExtras = cursor.getString(cursor.getColumnIndexOrThrow(SQLiteHelper.Table_Column_9_Bursa));
                String anInscriereExtras = cursor.getString(cursor.getColumnIndexOrThrow(SQLiteHelper.Table_Column_10_An_Inscriere));
                String formaInvatamantExtras = cursor.getString(cursor.getColumnIndexOrThrow(SQLiteHelper.Table_Column_11_Forma_De_Invatamant));
                String tipStudiiExtras = cursor.getString(cursor.getColumnIndexOrThrow(SQLiteHelper.Table_Column_12_Tip_Studii));

                if(emailHolder.contains("admin")){
                    studentInfoForms.setVisibility(View.GONE);
                    infoAdmin.setVisibility(View.VISIBLE);
                    titluInformatii.setText("Informații cont");
                    nume.setText(nume.getText() + "ADMIN");
                    email.setText(email.getText() + emailExtras);
                }
                else {
                    nume.setText(nume.getText() + numeExtras + " " + prenumeExtras);
                    email.setText(email.getText() + emailExtras);
                    int anUniversitarCurentStart = Integer.valueOf(anInscriereExtras) + Integer.parseInt(String.valueOf(anExtras.charAt(anExtras.length() - 1))) - 1;
                    anUniversitar.setText(anUniversitar.getText() + String.valueOf(anUniversitarCurentStart) + " - " + String.valueOf(anUniversitarCurentStart + 1));
                    formaInvatamant.setText(formaInvatamant.getText() + formaInvatamantExtras);
                    an.setText(an.getText() + String.valueOf(anExtras.charAt(anExtras.length() - 1)));
                    if (tipStudiiExtras.equals("Licenta")) {
                        serie.setText(serie.getText() + String.valueOf(serieExtras.charAt(serieExtras.length() - 2)) + String.valueOf(serieExtras.charAt(serieExtras.length() - 1)));
                    } else serie.setText(serie.getText() + "-");
                    grupa.setText(grupa.getText() + String.valueOf(grupaExtras.charAt(grupaExtras.length() - 3)) + String.valueOf(grupaExtras.charAt(grupaExtras.length() - 2)) + String.valueOf(grupaExtras.charAt(grupaExtras.length() - 1)));

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
                    if (taxaExtras.equals("true"))
                        bugetCheckbox.setImageResource(R.drawable.baseline_remove_circle_outline_24);
                    else
                        bugetCheckbox.setImageResource(R.drawable.baseline_check_circle_outline_24);

                    if (bursaExtras.equals("false"))
                        bursaCheckbox.setImageResource(R.drawable.baseline_remove_circle_outline_24);
                    else
                        bursaCheckbox.setImageResource(R.drawable.baseline_check_circle_outline_24);
                }
                cursor.close();
            }

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
}

