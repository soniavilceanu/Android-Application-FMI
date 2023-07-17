package com.example.myapplicationfmi;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelperAnnouncements extends SQLiteOpenHelper {

    static String DATABASE_NAME="UserDataBase";

    public static final String TABLE_NAME="Announcements"; // tabela cu dashboard-urile stocate si date din acestia

    public static final String Table_Column_ID="id";
    public static final String Table_Column_1_Titlu="titlu";
    public static final String Table_Column_2_Data="data";

    public static final String Table_Column_3_Image_Link="image_link";

    public static final String Table_Column_4_Body="body";

    public static final String Table_Column_5_Dashboard_Tab_Id="dashboard_tab_id";
    public static final String Table_Column_6_Dashboard_Tab_Date_Id="dashboard_tab_date_id";
    public static final String Table_Column_7_Dashboard_Tab_Image_Id="dashboard_tab_image_id";
    public static final String Table_Column_8_Dashboard_Tab_Delete_Id="dashboard_tab_delete_id";
    public static final String Table_Column_9_Dashboard_Tab_Title_Id="dashboard_tab_title_id";
    public static final String Table_Column_10_Dashboard_Tab_Body_Id="dashboard_tab_body_id";
    //public static final String Table_Column_11_Imagine="imagine";

    public SQLiteHelperAnnouncements(Context context) {

        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        String CREATE_TABLE="CREATE TABLE IF NOT EXISTS "+TABLE_NAME+" ("+Table_Column_ID+" INTEGER PRIMARY KEY, "+Table_Column_1_Titlu+" VARCHAR, "+Table_Column_2_Data+" VARCHAR, "+Table_Column_3_Image_Link+" VARCHAR, "+Table_Column_4_Body+" VARCHAR" +
                ", "+Table_Column_5_Dashboard_Tab_Id+" VARCHAR, "+Table_Column_6_Dashboard_Tab_Date_Id+" VARCHAR, "+Table_Column_7_Dashboard_Tab_Image_Id+" VARCHAR, "+Table_Column_8_Dashboard_Tab_Delete_Id+" VARCHAR, "+Table_Column_9_Dashboard_Tab_Title_Id+" VARCHAR, "+Table_Column_10_Dashboard_Tab_Body_Id+" VARCHAR)";
        database.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);

    }
}
