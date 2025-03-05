package com.cda.here.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.cda.here.entidades.Listas;

import java.util.ArrayList;

public class DbListas extends DbHelper{
    Context context;
    public DbListas(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long insertarLista(String nombre, int IdAdmin){
        long id = 0;
        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("lst_nombre", nombre);
            values.put("lst_admin", IdAdmin);

            id = db.insert(TABLE_LISTAS, null, values);
        }catch (Exception ex){
            ex.toString();
        }
        return id;
    }

    public ArrayList<Listas> mostrarListas(int idAdmin){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Listas> listaL = new ArrayList<>();
        Listas listas = null;
        Cursor cursorListas = null;
        int i = 0;
        cursorListas = db.rawQuery("SELECT * FROM "+TABLE_LISTAS+" WHERE lst_admin = "+idAdmin, null);
        if(cursorListas.moveToFirst()){
            do{
                listas = new Listas();
                listas.setId(cursorListas.getInt(0));
                listas.setNombre(cursorListas.getString(1));
                listas.setIdAdmin(cursorListas.getInt(2));
                listas.setNumLista(i+1);
                i++;
                listaL.add(listas);
            }while(cursorListas.moveToNext());
        }
        cursorListas.close();
        return listaL;
    }

    public Listas verLista(int idLista) {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Listas listas = null;
        Cursor cursorListas = null;

        cursorListas = db.rawQuery("SELECT * FROM " + TABLE_LISTAS + " WHERE lst_id = '" + idLista + "' LIMIT 1",
                null);
        if (cursorListas.moveToFirst()) {
            listas = new Listas();
            listas.setId(cursorListas.getInt(0));
            listas.setNombre(cursorListas.getString(1));
            listas.setIdAdmin(cursorListas.getInt(2));
        }

        cursorListas.close();
        return listas;
    }
}
