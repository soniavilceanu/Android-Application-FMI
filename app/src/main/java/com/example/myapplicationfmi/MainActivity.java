package com.example.myapplicationfmi;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class MainActivity extends AppCompatActivity {
    Button LogInButton, RegisterButton;
    EditText Email, Password;
    String EmailHolder, PasswordHolder;
    Boolean EditTextEmptyHolder;
    SQLiteDatabase sqLiteDatabaseObj;
    SQLiteHelper sqLiteHelper;
    Cursor cursor;
    String TempPassword = "NOT_FOUND" ;
    public static final String UserEmail = "";
    public static final String SHARED_PREFS = "sharedPrefs";
    public static int USER_TYPE = 1; //1 - admin, 2 - student, 3 - profesor
    CheckBox checkBox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //copyDatabaseToDevice(this);
        //getSupportActionBar().hide();
        LogInButton = (Button)findViewById(R.id.buttonLogin);
        //RegisterButton = (Button)findViewById(R.id.buttonRegister);
        Email = (EditText)findViewById(R.id.editEmail);
        Password = (EditText)findViewById(R.id.editPassword);
        sqLiteHelper = new SQLiteHelper(this);

        checkBox = findViewById(R.id.rememberLogin);
        checkbox();
        LogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckEditTextStatus();
                LoginFunction();
            }
        });
    }

    private void copyDatabaseToDevice(Context context) {
        String destinationPath = context.getDatabasePath("UserDataBase_copie_noua").getPath();

        try {
            InputStream inputStream = context.getAssets().open("baze_de_date/UserDataBase_copie_noua");
            OutputStream outputStream = new FileOutputStream(destinationPath);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }

            outputStream.flush();
            outputStream.close();
            inputStream.close();

            Toast.makeText(context, "Database copied to device", Toast.LENGTH_SHORT).show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void checkbox() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String check = sharedPreferences.getString("name", "");
        String email = sharedPreferences.getString("emailConectat","");
        if(check.equals("true")){
            if(email.contains("@s.unibuc.ro")) MainActivity.USER_TYPE = 2;
            else if(!email.contains("admin")) MainActivity.USER_TYPE = 3;
            else  MainActivity.USER_TYPE = 1;
            Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
            intent.putExtra(UserEmail, email);
            startActivity(intent);
            finish();
        }
    }

    public void LoginFunction(){
        if(EditTextEmptyHolder) {
            sqLiteDatabaseObj = sqLiteHelper.getWritableDatabase();
            cursor = sqLiteDatabaseObj.query(SQLiteHelper.TABLE_NAME, null, " " + SQLiteHelper.Table_Column_3_Email + "=?", new String[]{EmailHolder}, null, null, null);
            while (cursor.moveToNext()) {
                if (cursor.isFirst()) {
                    cursor.moveToFirst();
                    TempPassword = cursor.getString(cursor.getColumnIndexOrThrow(SQLiteHelper.Table_Column_4_Password));
                    cursor.close();
                }
            }
            CheckFinalResult();
        }
        else {
            Toast.makeText(MainActivity.this,"Introduceți username și parolă",Toast.LENGTH_LONG).show();
        }
    }
    public void CheckEditTextStatus(){
        EmailHolder = Email.getText().toString();
        PasswordHolder = Password.getText().toString();
        if( TextUtils.isEmpty(EmailHolder) || TextUtils.isEmpty(PasswordHolder))
            EditTextEmptyHolder = false;
        else EditTextEmptyHolder = true;
        //verificam tipul de user
        if(EmailHolder.contains("@s.unibuc.ro")) MainActivity.USER_TYPE = 2;
        else if(!EmailHolder.contains("admin")) MainActivity.USER_TYPE = 3;
            else  MainActivity.USER_TYPE = 1;
    }

    public void CheckFinalResult(){
        if(TempPassword.equalsIgnoreCase(PasswordHolder))
        {
            SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
            if(checkBox.isChecked()) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("name", "true");
                editor.putString("emailConectat", EmailHolder);
                editor.apply();
            }
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("email", String.valueOf(Email.getText()));
            editor.apply();
            //Toast.makeText(MainActivity.this,"Login Successful",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
            intent.putExtra(UserEmail, EmailHolder);
            startActivity(intent);
        }
        else {
            Toast.makeText(MainActivity.this,"Username sau parola greșită! Încercați din nou",Toast.LENGTH_LONG).show();
        }
        TempPassword = "NOT_FOUND" ;
    }
}