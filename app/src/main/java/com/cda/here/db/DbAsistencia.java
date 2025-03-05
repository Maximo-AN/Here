package com.cda.here.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.cda.here.entidades.Asistencia;
import com.cda.here.entidades.Estudiantes;

import java.util.ArrayList;

public class DbAsistencia extends DbHelper{
    Context context;

    public DbAsistencia(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long insertarAsistencia(String estado, int lista, long estudiante, int docente){
        long res = 0;
        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("ast_estado", estado);
            values.put("ast_lista", lista);
            values.put("ast_estudiante", estudiante);
            values.put("ast_docente", docente);

            res = db.insert(TABLE_ASISTENCIA, null, values);
        }catch (Exception ex){
            ex.toString();
        }
        return res;
    }

    public ArrayList<Asistencia> mostrarAsistencia(long matricula){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Asistencia> asistenciaL = new ArrayList<>();
        Asistencia asistencia = null;
        Cursor cursorAsistencia = null;
        cursorAsistencia = db.rawQuery("SELECT * FROM "+TABLE_ASISTENCIA+" WHERE ast_estudiante = " +
                ""+ matricula + "", null);
        if(cursorAsistencia.moveToFirst()){
            do{
                asistencia = new Asistencia();
                asistencia.setIdAs(cursorAsistencia.getInt(0));
                asistencia.setFecha(cursorAsistencia.getString(1));
                asistencia.setEstado(cursorAsistencia.getString(2));
                asistencia.setNumlista(cursorAsistencia.getInt(3));
                asistencia.setEstudiante(cursorAsistencia.getLong(4));
                asistencia.setIdDocente(cursorAsistencia.getInt(5));

                asistenciaL.add(asistencia);
            }while(cursorAsistencia.moveToNext());
        }
        cursorAsistencia.close();
        return asistenciaL;
    }

    public boolean cambiarAsistencia(int id, String estado){
        boolean correcto = false;
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db =  dbHelper.getWritableDatabase();
        try {
            db.execSQL("UPDATE "+ TABLE_ASISTENCIA + " SET ast_estado = '"+estado+"' WHERE ast_id = '"+id+"' ");
            correcto = true;
        }catch (Exception ex){
            ex.toString();
            correcto = false;
        }
        finally {
            db.close();
        }
        return correcto;
    }

}
