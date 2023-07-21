package com.example.myapplicationfmi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DetailsDashboardTabsActivity extends AppCompatActivity {

    private Button buttonBack;
    private TextView titluInformatii;
    private TextView dateInformatii;
    private TextView infoAdmin;
    private TextView emailInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_dashboard_tabs);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String date = intent.getStringExtra("date");
        String body = intent.getStringExtra("body");
        String whereFrom = intent.getStringExtra("previousActivity");
        String email = intent.getStringExtra("email");

        buttonBack = findViewById(R.id.buttonBack);
        titluInformatii = findViewById(R.id.titluInformatii);
        dateInformatii = findViewById(R.id.dateInformatii);
        infoAdmin = findViewById(R.id.infoAdmin);
        emailInfo = findViewById(R.id.emailInfo);

        titluInformatii.setText(title);
        dateInformatii.setText(date);
        infoAdmin.setText(body);
        emailInfo.setText(emailInfo.getText() + email);

        if(whereFrom.equals("DashboardActivity")){
            emailInfo.setVisibility(View.GONE);
        }
        else emailInfo.setVisibility(View.VISIBLE);

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(whereFrom.equals("DashboardActivity")){
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
}