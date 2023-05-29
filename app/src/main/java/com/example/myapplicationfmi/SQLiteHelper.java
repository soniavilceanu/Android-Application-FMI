package com.example.myapplicationfmi;

/**
 * Created by Shashank on 14-Feb-18.
 */

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

public class SQLiteHelper extends SQLiteOpenHelper {

    static String DATABASE_NAME="UserDataBase";

    public static final String TABLE_NAME="UserTable";

    public static final String Table_Column_ID="id";

    public static final String Table_Column_1_Last_Name="last_name";
    public static final String Table_Column_2_First_Name="first_name";

    public static final String Table_Column_3_Email="email";

    public static final String Table_Column_4_Password="password";

    public static final String Table_Column_5_Year="year"; // anul in care e studentul
    public static final String Table_Column_6_Section="section"; //seria
    public static final String Table_Column_7_Cohort="cohort"; //grupa
    public static final String Table_Column_8_Program_Study="program_of_study"; //programul de studiu (matematica si informatica_
    public static final String Table_Column_9_Major="major"; //profilul (informatica)
    public static final String Table_Column_10_Tax="tax"; //e la buget sau la taxa
    public static final String Table_Column_11_Scholarship="scholarship"; //are sau nu bursa
    public static final String Table_Column_12_Year_Of_Study="year_of_study"; //anul de studiu 2021-2023
    public static final String Table_Column_13_Type_Of_Enrollment="type_of_enrollment"; //forma de invatamant: IF, ID


    public SQLiteHelper(Context context) {

        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase database) {

        String CREATE_TABLE="CREATE TABLE IF NOT EXISTS "+TABLE_NAME+" ("+Table_Column_ID+" INTEGER PRIMARY KEY, "+Table_Column_1_Last_Name+" VARCHAR, "+Table_Column_2_First_Name+" VARCHAR, "+Table_Column_3_Email+" VARCHAR, "+Table_Column_4_Password+" VARCHAR" +
                ", "+Table_Column_5_Year+" VARCHAR, "+Table_Column_6_Section+" VARCHAR, "+Table_Column_7_Cohort+" VARCHAR, "+Table_Column_8_Program_Study+" VARCHAR, "+Table_Column_9_Major+" VARCHAR, "+Table_Column_10_Tax+" VARCHAR" +
                ", "+Table_Column_11_Scholarship+" VARCHAR, "+Table_Column_12_Year_Of_Study+" VARCHAR, "+Table_Column_13_Type_Of_Enrollment+" VARCHAR)";
        database.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);

    }

}