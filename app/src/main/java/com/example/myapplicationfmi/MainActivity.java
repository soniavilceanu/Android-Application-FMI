package com.example.myapplicationfmi;


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
        //getSupportActionBar().hide();
        LogInButton = (Button)findViewById(R.id.buttonLogin);
        //RegisterButton = (Button)findViewById(R.id.buttonRegister);
        Email = (EditText)findViewById(R.id.editEmail);
        Password = (EditText)findViewById(R.id.editPassword);
        sqLiteHelper = new SQLiteHelper(this);

        checkBox = findViewById(R.id.rememberLogin);
        checkbox();
        //Adding click listener to log in button.
        LogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Calling EditText is empty or no method.
                CheckEditTextStatus();
                // Calling login method.
                LoginFunction();
            }
        });
    }

    private void checkbox() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String check = sharedPreferences.getString("name", "");
        if(check.equals("true")){
            Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
            // Sending Email to Dashboard Activity using intent.
            intent.putExtra(UserEmail, EmailHolder);
            startActivity(intent);
            finish();
        }
    }

    // Login function starts from here.
    public void LoginFunction(){
        if(EditTextEmptyHolder) {
            // Opening SQLite database write permission.
            sqLiteDatabaseObj = sqLiteHelper.getWritableDatabase();
            // Adding search email query to cursor.
            cursor = sqLiteDatabaseObj.query(SQLiteHelper.TABLE_NAME, null, " " + SQLiteHelper.Table_Column_3_Email + "=?", new String[]{EmailHolder}, null, null, null);
            while (cursor.moveToNext()) {
                if (cursor.isFirst()) {
                    cursor.moveToFirst();
                    // Storing Password associated with entered email.
                    TempPassword = cursor.getString(cursor.getColumnIndexOrThrow(SQLiteHelper.Table_Column_4_Password));
                    // Closing cursor.
                    cursor.close();
                }
            }
            // Calling method to check final result ..
            CheckFinalResult();
        }
        else {
            //If any of login EditText empty then this block will be executed.
            Toast.makeText(MainActivity.this,"Please Enter UserName or Password.",Toast.LENGTH_LONG).show();
        }
    }
    // Checking EditText is empty or not.
    public void CheckEditTextStatus(){
        // Getting value from All EditText and storing into String Variables.
        EmailHolder = Email.getText().toString();
        PasswordHolder = Password.getText().toString();
        // Checking EditText is empty or no using TextUtils.
        if( TextUtils.isEmpty(EmailHolder) || TextUtils.isEmpty(PasswordHolder))
            EditTextEmptyHolder = false;
        else EditTextEmptyHolder = true;
        //verificam tipul de user
        if(EmailHolder.contains("@s.unibuc.ro")) MainActivity.USER_TYPE = 2;
        else if(!EmailHolder.contains("admin")) MainActivity.USER_TYPE = 3;
            else  MainActivity.USER_TYPE = 1;
    }
    // Checking entered password from SQLite database email associated password.
    public void CheckFinalResult(){
        if(TempPassword.equalsIgnoreCase(PasswordHolder))
        {
            SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
            if(checkBox.isChecked()) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("name", "true");
                editor.apply();
            }
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("email", String.valueOf(Email.getText()));
            editor.apply();
            Toast.makeText(MainActivity.this,"Login Successful",Toast.LENGTH_LONG).show();
            // Going to Dashboard activity after login success message.
            Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
            // Sending Email to Dashboard Activity using intent.
            intent.putExtra(UserEmail, EmailHolder);
            startActivity(intent);
        }
        else {
            Toast.makeText(MainActivity.this,"UserName or Password is Wrong, Please Try Again.",Toast.LENGTH_LONG).show();
        }
        TempPassword = "NOT_FOUND" ;
    }
}