package com.cda.here.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.cda.here.entidades.Estudiantes;
import com.cda.here.entidades.Listas;

import java.util.ArrayList;

public class DbEstudiantes extends DbHelper{

    Context context;

    public DbEstudiantes(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long insertarEstudiante(long matricula, int numerol,String nombre, int lista, int docente){
        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("est_matricula", matricula);
            values.put("est_numerol", numerol);
            values.put("est_nombre", nombre);
            values.put("est_lista", lista);
            values.put("est_docente", docente);

            matricula = db.insert(TABLE_ESTUDIANTES, null, values);
        }catch (Exception ex){
            ex.toString();
        }
        return matricula;
    }

    public ArrayList<Estudiantes> mostrarAlumnos(int idLista){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Estudiantes> estudiantesL = new ArrayList<>();
        Estudiantes estudiantes = null;
        Cursor cursorEstudiantes = null;
        int i = 0;
        cursorEstudiantes = db.rawQuery("SELECT * FROM "+TABLE_ESTUDIANTES+" WHERE est_lista = " +
                ""+ idLista + " ORDER BY est_numerol", null);
        if(cursorEstudiantes.moveToFirst()){
            do{
                estudiantes = new Estudiantes();
                estudiantes.setMatricula(cursorEstudiantes.getLong(0));
                estudiantes.setNumeroL(cursorEstudiantes.getInt(1));
                estudiantes.setNombreAl(cursorEstudiantes.getString(2));
                estudiantes.setListaAlumno(cursorEstudiantes.getInt(3));
                estudiantes.setDocenteAlumno(cursorEstudiantes.getInt(4));
                i++;
                estudiantesL.add(estudiantes);
            }while(cursorEstudiantes.moveToNext());
        }
        cursorEstudiantes.close();
        return estudiantesL;
    }

    public boolean actualizarAlumno(long matricula, int numlista, String nombre, long matriculaSeg){
        boolean correcto = false;
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db =  dbHelper.getWritableDatabase();
        try {
            db.execSQL("UPDATE "+ TABLE_ESTUDIANTES + " SET est_matricula = '"+matricula+"', est_numerol = '" +
                    numlista+ "', est_nombre = '"+nombre+"'" + " WHERE est_matricula = '"+matriculaSeg+"' ");
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

    public boolean eliminarAlumno(long matricula){
        boolean correcto = false;
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db =  dbHelper.getWritableDatabase();
        try {
            db.execSQL("DELETE FROM "+ TABLE_ESTUDIANTES + " WHERE est_matricula = '"+matricula+"' ");
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
