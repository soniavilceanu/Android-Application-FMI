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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_dashboard_tabs);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String date = intent.getStringExtra("date");
        String body = intent.getStringExtra("body");

        buttonBack = findViewById(R.id.buttonBack);
        titluInformatii = findViewById(R.id.titluInformatii);
        dateInformatii = findViewById(R.id.dateInformatii);
        infoAdmin = findViewById(R.id.infoAdmin);

        titluInformatii.setText(title);
        dateInformatii.setText(date);
        infoAdmin.setText(body);

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(DetailsDashboardTabsActivity.this, DashboardActivity.class);

                    startActivity(intent);
                    finish();
                }
        });
    }
}