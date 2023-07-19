package com.example.myapplicationfmi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OrarActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;
    MaterialToolbar materialToolbar;
    private MaterialToolbar topAppBar;
    public static final String SHARED_PREFS = "sharedPrefs";
    private Menu menu;
    private Spinner spinnerSelecteazaGrupa;
    private LinearLayout editOrarInfoTab;
    private Button orarTabClose;

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
        setContentView(R.layout.activity_orar);

        navigationView = findViewById(R.id.nav_view);
        topAppBar = findViewById(R.id.topAppBar);
        editOrarInfoTab = findViewById(R.id.editOrarInfoTab);

        Menu menu = navigationView.getMenu();
        MenuItem creareContNouItem = menu.findItem(R.id.creareContNou);
        if (MainActivity.USER_TYPE != 1) {
            creareContNouItem.setVisible(false);
        } else {
            creareContNouItem.setVisible(true);
        }

        List<TextView> tableCells = Arrays.asList(findViewById(R.id.cell_1_1), findViewById(R.id.cell_1_2), findViewById(R.id.cell_1_3), findViewById(R.id.cell_1_4), findViewById(R.id.cell_1_5), findViewById(R.id.cell_2_1),
                findViewById(R.id.cell_2_2), findViewById(R.id.cell_2_3), findViewById(R.id.cell_2_4), findViewById(R.id.cell_2_5), findViewById(R.id.cell_3_1), findViewById(R.id.cell_3_2), findViewById(R.id.cell_3_3),
                findViewById(R.id.cell_3_4), findViewById(R.id.cell_3_5), findViewById(R.id.cell_4_1), findViewById(R.id.cell_4_2), findViewById(R.id.cell_4_3), findViewById(R.id.cell_4_4), findViewById(R.id.cell_4_5),
                findViewById(R.id.cell_5_1), findViewById(R.id.cell_5_2), findViewById(R.id.cell_5_3), findViewById(R.id.cell_5_4), findViewById(R.id.cell_5_5), findViewById(R.id.cell_5_5), findViewById(R.id.cell_5_6),
                findViewById(R.id.cell_1_6), findViewById(R.id.cell_2_6), findViewById(R.id.cell_3_6), findViewById(R.id.cell_4_6));

        for(int i = 0; i < tableCells.size(); i ++){
            tableCells.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (MainActivity.USER_TYPE == 1) {
                        editOrarInfoTab.setVisibility(View.VISIBLE);
                    }
                    /**
                     *  populam TextView-uri
                     *  coloram celule
                     *  bagam in baza
                     *  tragem din baza
                     */
                }
            });
        }

        orarTabClose = findViewById(R.id.orarTabClose);
        orarTabClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editOrarInfoTab.setVisibility(View.GONE);
            }
        });

        String[] tipContItems = {" Grupa 131", "Grupa 132", "Grupa 133", "Grupa 134", "Grupa 135", "Grupa 141", "Grupa 142", "Grupa 143", "Grupa 144", "Grupa 231", "Grupa 232", "Grupa 233", "Grupa 234", "Grupa 235", "Grupa 241", "Grupa 242", "Grupa 243", "Grupa 244", "Grupa 331", "Grupa 332", "Grupa 333", "Grupa 334", "Grupa 335", "Grupa 341", "Grupa 342", "Grupa 343", "Grupa 344", "Grupa 405", "Grupa 406", "Grupa 407", "Grupa 408", "Grupa 410", "Grupa 411", "Grupa 412", "Grupa 505", "Grupa 506", "Grupa 507", "Grupa 508", "Grupa 510", "Grupa 511", "Grupa 512"};
        ArrayAdapter<String> adaptertipCont = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, tipContItems);
        spinnerSelecteazaGrupa = findViewById(R.id.spinnerSelecteazaGrupa);
        spinnerSelecteazaGrupa.setAdapter(adaptertipCont);

        spinnerSelecteazaGrupa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

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
                    Toast.makeText(OrarActivity.this, "Carnet selected", Toast.LENGTH_SHORT).show();
                } else if (itemId == R.id.orar) {
                    Toast.makeText(OrarActivity.this, "Orar selected", Toast.LENGTH_SHORT).show();
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else if (itemId == R.id.calendar) {
                    Toast.makeText(OrarActivity.this, "Calendar selected", Toast.LENGTH_SHORT).show();
                } else if (itemId == R.id.activitiesAnnouncements) {
                    Intent intent = new Intent(OrarActivity.this, DashboardActivity.class);
                    startActivity(intent);
                    finish();
                } else if (itemId == R.id.internshipVoluntariat) {
                    Intent intent = new Intent(OrarActivity.this, ExtracurricularActivity.class);
                    startActivity(intent);
                    finish();
                } else if (itemId == R.id.cantina) {
                    Toast.makeText(OrarActivity.this, "Cantina selected", Toast.LENGTH_SHORT).show();
                } else if (itemId == R.id.informatii) {
                    Toast.makeText(OrarActivity.this, "Informatii selected", Toast.LENGTH_SHORT).show();
                }
                else if(itemId == R.id.creareContNou) {
                    Intent intent = new Intent(OrarActivity.this, RegisterActivity.class);
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
                    Intent intent = new Intent(OrarActivity.this, ProfileActivity.class);
                    intent.putExtra("previousActivity", "OrarActivity");
                    startActivity(intent);
                    finish();
                }
                if(item.getItemId() == R.id.setari){
                    Toast.makeText(OrarActivity.this, "Settings selected", Toast.LENGTH_SHORT).show();
                }
                if(item.getItemId() == R.id.deconectare){
                    SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("name", "");
                    editor.apply();

                    Intent intent = new Intent(OrarActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                return false;
            }
        });
    }
    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen((GravityCompat.START)))
            drawerLayout.closeDrawer(GravityCompat.START);
        else super.onBackPressed();
    }
}