package com.cda.here.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 6;
    private static final String DATABASE_NAME = "here.db";
    public static final String TABLE_USERS = "t_users";
    public static final String TABLE_ESTUDIANTES = "t_estudiantes";
    public static final String TABLE_LISTAS = "t_listas";
    public static final String TABLE_ASISTENCIA = "t_asistencia";
    public static String DATABASE_PATH = "";


    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        DATABASE_PATH = "/data/data/"+context.getPackageName()+"/databases/"+DATABASE_NAME;
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_USERS + "(" +
                    "us_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "us_nombre TEXT NOT NULL," +
                    "us_psw TEXT NOT NULL," +
                    "us_session INTEGER NOT NULL)");

            sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_LISTAS + "(" +
                    "lst_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "lst_nombre TEXT NOT NULL," +
                    "lst_admin INTEGER NOT NULL," +
                    "FOREIGN KEY (lst_admin) REFERENCES t_users(us_id))");

            sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_ESTUDIANTES + "(" +
                    "est_matricula INTEGER PRIMARY KEY NOT NULL," +
                    "est_numerol INTEGER NOT NULL," +
                    "est_nombre TEXT NOT NULL," +
                    "est_lista INTEGER NOT NULL," +
                    "est_docente INTEGER NOT NULL," +
                    "FOREIGN KEY (est_lista) REFERENCES t_listas(lst_id)," +
                    "FOREIGN KEY (est_docente) REFERENCES t_users(us_id))");

            sqLiteDatabase.execSQL("CREATE TABLE "+ TABLE_ASISTENCIA + "(" +
                    "ast_id INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    "ast_fecha DATE DEFAULT CURRENT_DATE," +
                    "ast_estado TEXT NOT NULL," +
                    "ast_lista INTEGER NOT NULL," +
                    "ast_estudiante INTEGER NOT NULL," +
                    "ast_docente INTEGER NOT NULL,"+
                    "FOREIGN KEY (ast_lista) REFERENCES t_listas(lst_id)," +
                    "FOREIGN KEY (ast_estudiante) REFERENCES t_estudiantes(est_matricula)," +
                    "FOREIGN KEY(ast_docente) REFERENCES t_users(us_id))");


        }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE "+ TABLE_USERS);
        sqLiteDatabase.execSQL("DROP TABLE "+ TABLE_ESTUDIANTES);
        sqLiteDatabase.execSQL("DROP TABLE "+ TABLE_LISTAS);
        sqLiteDatabase.execSQL("DROP TABLE "+ TABLE_ASISTENCIA);
        onCreate(sqLiteDatabase);
    }

    public boolean verificarBD(){
        SQLiteDatabase verBD = null;
        try{
            verBD = SQLiteDatabase.openDatabase(DATABASE_PATH, null, SQLiteDatabase.OPEN_READONLY);
            verBD.close();
        }catch (SQLiteException e){
            Log.e("Error", "Base de datos inexistente"+e.getMessage());
        }
        if(verBD != null)
            verBD.close();

        return verBD != null;
    }

}
