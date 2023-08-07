package com.example.myapplicationfmi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
public class ExtracurricularActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;
    MaterialToolbar materialToolbar;
    TabLayout tabLayout;
    ViewPager2 viewPager;
    VPAdapter2 vpAdapter2;
    private MaterialToolbar topAppBar;
    public static SearchView searchView;
    public static final String SHARED_PREFS = "sharedPrefs";
    private Menu menu;
    SQLiteDatabase sqLiteDatabaseObj;
    private SQLiteHelperInternships sqLiteHelperInternships;
    private SQLiteHelperVolunteerings sqLiteHelperVolunteerings;

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
        setContentView(R.layout.activity_extracurricular);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        vpAdapter2 = new VPAdapter2(this);
        viewPager.setAdapter(vpAdapter2);

        navigationView = findViewById(R.id.nav_view);
        topAppBar = findViewById(R.id.topAppBar);
        sqLiteHelperInternships = new SQLiteHelperInternships(this);
        sqLiteHelperVolunteerings = new SQLiteHelperVolunteerings(this);

        Menu menu = navigationView.getMenu();
        MenuItem creareContNouItem = menu.findItem(R.id.creareContNou);
        if (MainActivity.USER_TYPE != 1) {
            creareContNouItem.setVisible(false);
        } else {
            creareContNouItem.setVisible(true);
        }

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabLayout.getTabAt(position).select();
            }
        });
        drawerLayout = findViewById(R.id.activity_dashboard);
        materialToolbar = findViewById(R.id.topAppBar);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, materialToolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.carnet) {
                    Intent intent = new Intent(ExtracurricularActivity.this, CarnetActivity.class);
                    startActivity(intent);
                    finish();
                } else if (itemId == R.id.orar) {
                    Intent intent = new Intent(ExtracurricularActivity.this, OrarActivity.class);
                    startActivity(intent);
                    finish();
                } else if (itemId == R.id.calendar) {
                    Intent intent = new Intent(ExtracurricularActivity.this, CalendarAcademicActivity.class);
                    startActivity(intent);
                    finish();
                } else if (itemId == R.id.activitiesAnnouncements) {
                    Intent intent = new Intent(ExtracurricularActivity.this, DashboardActivity.class);
                    startActivity(intent);
                    finish();
                } else if (itemId == R.id.internshipVoluntariat) {
                     drawerLayout.closeDrawer(GravityCompat.START);
                } else if (itemId == R.id.cantina) {
                } else if (itemId == R.id.informatii) {
                }
                else if(itemId == R.id.creareContNou) {
                    Intent intent = new Intent(ExtracurricularActivity.this, RegisterActivity.class);
                    startActivity(intent);
                    finish();
                }
                return false;
            }
        });


        materialToolbar.setOnMenuItemClickListener(new MaterialToolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId() == R.id.cautare){
                    searchView = (SearchView) MenuItemCompat.getActionView(item);
                    searchView.setQueryHint("Caută...");
                    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                        @Override
                        public boolean onQueryTextSubmit(String query) {
                            int currentItem = viewPager.getCurrentItem();
                            Fragment fragment = vpAdapter2.getFragment(currentItem);
                            if(fragment instanceof internships_fragment) {
                                ArrayList<String> titles = getDashboardTabTitlesInternships();
                                ArrayList<String> bodies = getDashboardTabBodiesInternships();
                                sqLiteDatabaseObj = sqLiteHelperInternships.getWritableDatabase();
                                for (int i = 0; i < titles.size(); i++) {
                                    if (titles.get(i).toLowerCase().contains(query.toLowerCase())) {
                                        Cursor cursor = sqLiteDatabaseObj.query(SQLiteHelperInternships.TABLE_NAME, new String[]{SQLiteHelperInternships.Table_Column_5_Dashboard_Tab_Id}, SQLiteHelperInternships.Table_Column_1_Titlu + " = ?", new String[]{titles.get(i)}, null, null, null);
                                        if (cursor.moveToFirst()) {
                                            String dashboardIdExtras = cursor.getString(cursor.getColumnIndexOrThrow(SQLiteHelperInternships.Table_Column_5_Dashboard_Tab_Id));

                                            internships_fragment yourFragment = (internships_fragment) fragment;
                                            yourFragment.focusOnView(Integer.valueOf(dashboardIdExtras), "top");
                                            cursor.close();
                                        }
                                    }
                                }
                                for (int i = 0; i < bodies.size(); i++) {
                                    if (bodies.get(i).toLowerCase().contains(query.toLowerCase())) {
                                        Cursor cursor = sqLiteDatabaseObj.query(SQLiteHelperInternships.TABLE_NAME, new String[]{SQLiteHelperInternships.Table_Column_5_Dashboard_Tab_Id}, SQLiteHelperInternships.Table_Column_4_Body + " = ?", new String[]{bodies.get(i)}, null, null, null);
                                        if (cursor.moveToFirst()) {
                                            String dashboardIdExtras = cursor.getString(cursor.getColumnIndexOrThrow(SQLiteHelperInternships.Table_Column_5_Dashboard_Tab_Id));

                                            internships_fragment yourFragment = (internships_fragment) fragment;
                                            yourFragment.focusOnView(Integer.valueOf(dashboardIdExtras), "top");
                                            cursor.close();
                                        }
                                    }
                                }
                            }
                            else if(fragment instanceof volunteerings_fragment){
                                ArrayList<String> titles = getDashboardTabTitlesVolunteerings();
                                ArrayList<String> bodies = getDashboardTabBodiesVolunteerings();
                                sqLiteDatabaseObj = sqLiteHelperVolunteerings.getWritableDatabase();
                                for (int i = 0; i < titles.size(); i++) {
                                    if (titles.get(i).toLowerCase().contains(query.toLowerCase())) {
                                        Cursor cursor = sqLiteDatabaseObj.query(SQLiteHelperVolunteerings.TABLE_NAME, new String[]{SQLiteHelperVolunteerings.Table_Column_5_Dashboard_Tab_Id}, SQLiteHelperVolunteerings.Table_Column_1_Titlu + " = ?", new String[]{titles.get(i)}, null, null, null);
                                        if (cursor.moveToFirst()) {
                                            String dashboardIdExtras = cursor.getString(cursor.getColumnIndexOrThrow(SQLiteHelperVolunteerings.Table_Column_5_Dashboard_Tab_Id));

                                            volunteerings_fragment yourFragment = (volunteerings_fragment) fragment;
                                            yourFragment.focusOnView(Integer.valueOf(dashboardIdExtras), "top");
                                            cursor.close();
                                        }
                                    }
                                }
                                for (int i = 0; i < bodies.size(); i++) {
                                    if (bodies.get(i).toLowerCase().contains(query.toLowerCase())) {
                                        Cursor cursor = sqLiteDatabaseObj.query(SQLiteHelperVolunteerings.TABLE_NAME, new String[]{SQLiteHelperVolunteerings.Table_Column_5_Dashboard_Tab_Id}, SQLiteHelperVolunteerings.Table_Column_4_Body + " = ?", new String[]{bodies.get(i)}, null, null, null);
                                        if (cursor.moveToFirst()) {
                                            String dashboardIdExtras = cursor.getString(cursor.getColumnIndexOrThrow(SQLiteHelperVolunteerings.Table_Column_5_Dashboard_Tab_Id));

                                            volunteerings_fragment yourFragment = (volunteerings_fragment) fragment;
                                            yourFragment.focusOnView(Integer.valueOf(dashboardIdExtras), "top");
                                            cursor.close();
                                        }
                                    }
                                }
                            }
                            return true;
                        }
                        @Override
                        public boolean onQueryTextChange(String newText) {
                            return false;
                        }
                    });
                }
                if(item.getItemId() == R.id.profil){
                    Intent intent = new Intent(ExtracurricularActivity.this, ProfileActivity.class);
                    intent.putExtra("previousActivity", "ExtracurricularActivity");
                    startActivity(intent);
                    finish();
                }
                if(item.getItemId() == R.id.setari){
                    Toast.makeText(ExtracurricularActivity.this, "Settings selected", Toast.LENGTH_SHORT).show();
                }
                if(item.getItemId() == R.id.deconectare){
                    SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("name", "");
                    editor.putString("emailConectat","");
                    editor.apply();

                    Intent intent = new Intent(ExtracurricularActivity.this, MainActivity.class);
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
    public SearchView getSearchView() {
        return searchView;
    }
    public ArrayList<String> getDashboardTabTitlesInternships() {
        ArrayList<String> dashboardTabTitles = new ArrayList<>();
        sqLiteDatabaseObj = sqLiteHelperInternships.getWritableDatabase();
        Cursor cursor = sqLiteDatabaseObj.query(SQLiteHelperInternships.TABLE_NAME, new String[]{SQLiteHelperInternships.Table_Column_1_Titlu}, null, null, null, null, null);

        if (cursor != null) {
            int columnIndex = cursor.getColumnIndex(SQLiteHelperInternships.Table_Column_1_Titlu);
            if (columnIndex != -1) {
                while (cursor.moveToNext()) {
                    String dashboardTabTitle = cursor.getString(columnIndex);
                    dashboardTabTitles.add(dashboardTabTitle);
                }
            }
            cursor.close();
        }
        return dashboardTabTitles;
    }
    public ArrayList<String> getDashboardTabBodiesInternships() {
        ArrayList<String> dashboardTabBodies = new ArrayList<>();
        sqLiteDatabaseObj = sqLiteHelperInternships.getWritableDatabase();
        Cursor cursor = sqLiteDatabaseObj.query(SQLiteHelperInternships.TABLE_NAME, new String[]{SQLiteHelperInternships.Table_Column_4_Body}, null, null, null, null, null);

        if (cursor != null) {
            int columnIndex = cursor.getColumnIndex(SQLiteHelperInternships.Table_Column_4_Body);
            if (columnIndex != -1) {
                while (cursor.moveToNext()) {
                    String dashboardTabBody = cursor.getString(columnIndex);
                    dashboardTabBodies.add(dashboardTabBody);
                }
            }
            cursor.close();
        }
        return dashboardTabBodies;
    }

    public ArrayList<String> getDashboardTabTitlesVolunteerings() {
        ArrayList<String> dashboardTabTitles = new ArrayList<>();
        sqLiteDatabaseObj = sqLiteHelperVolunteerings.getWritableDatabase();
        Cursor cursor = sqLiteDatabaseObj.query(SQLiteHelperVolunteerings.TABLE_NAME, new String[]{SQLiteHelperVolunteerings.Table_Column_1_Titlu}, null, null, null, null, null);

        if (cursor != null) {
            int columnIndex = cursor.getColumnIndex(SQLiteHelperVolunteerings.Table_Column_1_Titlu);
            if (columnIndex != -1) {
                while (cursor.moveToNext()) {
                    String dashboardTabTitle = cursor.getString(columnIndex);
                    dashboardTabTitles.add(dashboardTabTitle);
                }
            }
            cursor.close();
        }
        return dashboardTabTitles;
    }
    public ArrayList<String> getDashboardTabBodiesVolunteerings() {
        ArrayList<String> dashboardTabBodies = new ArrayList<>();
        sqLiteDatabaseObj = sqLiteHelperVolunteerings.getWritableDatabase();
        Cursor cursor = sqLiteDatabaseObj.query(SQLiteHelperVolunteerings.TABLE_NAME, new String[]{SQLiteHelperVolunteerings.Table_Column_4_Body}, null, null, null, null, null);

        if (cursor != null) {
            int columnIndex = cursor.getColumnIndex(SQLiteHelperVolunteerings.Table_Column_4_Body);
            if (columnIndex != -1) {
                while (cursor.moveToNext()) {
                    String dashboardTabBody = cursor.getString(columnIndex);
                    dashboardTabBodies.add(dashboardTabBody);
                }
            }
            cursor.close();
        }
        return dashboardTabBodies;
    }
}