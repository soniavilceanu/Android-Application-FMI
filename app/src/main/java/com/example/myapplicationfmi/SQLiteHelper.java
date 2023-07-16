package com.example.myapplicationfmi;

/**
 * Created by Shashank on 14-Feb-18.
 */

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

public class SQLiteHelper extends SQLiteOpenHelper {

    static String DATABASE_NAME="UserDataBase";

    public static final String TABLE_NAME="UserTable"; // tabela cu useri din aplicatie si date despe acestia

    public static final String Table_Column_ID="id";
    public static final String Table_Column_1_Nume="nume";
    public static final String Table_Column_2_Prenume="prenume";

    public static final String Table_Column_3_Email="email";

    public static final String Table_Column_4_Password="password";

    public static final String Table_Column_5_An="an"; // anul in care e studentul
    public static final String Table_Column_6_Serie="serie"; //seria
    public static final String Table_Column_7_Grupa="grupa"; //grupa ||| grupele la care predau profesorii
    //public static final String Table_Column_8_Program_de_studiu="program_of_study"; //programul de studiu (matematica si informatica_
    //public static final String Table_Column_9_Profil="profil"; //profilul (informatica)
    public static final String Table_Column_8_Taxa="taxa"; //e sau nu la taxa
    public static final String Table_Column_9_Bursa="bursa"; //are sau nu bursa
    public static final String Table_Column_10_An_Inscriere="an_inscriere"; //anul in care a inceput studiile ||| pozitia/gradul profesorului
    public static final String Table_Column_11_Forma_De_Invatamant="forma_de_invatamant"; //forma de invatamant: IF, ID, IFR
    public static final String Table_Column_12_Tip_Studii="tip_studii"; //daca e licenta sau master |||| disciplinele in cazul profesorilor


    public SQLiteHelper(Context context) {

        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        String CREATE_TABLE="CREATE TABLE IF NOT EXISTS "+TABLE_NAME+" ("+Table_Column_ID+" INTEGER PRIMARY KEY, "+Table_Column_1_Nume+" VARCHAR, "+Table_Column_2_Prenume+" VARCHAR, "+Table_Column_3_Email+" VARCHAR, "+Table_Column_4_Password+" VARCHAR" +
                ", "+Table_Column_5_An+" VARCHAR, "+Table_Column_6_Serie+" VARCHAR, "+Table_Column_7_Grupa+" VARCHAR, "+Table_Column_8_Taxa+" VARCHAR, "+Table_Column_9_Bursa+" VARCHAR, "+Table_Column_10_An_Inscriere+" VARCHAR" +
                ", "+ Table_Column_11_Forma_De_Invatamant+" VARCHAR, " + Table_Column_12_Tip_Studii+" VARCHAR)";
        database.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);

    }

}