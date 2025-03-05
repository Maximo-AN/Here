package com.cda.here.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import androidx.annotation.Nullable;

import com.cda.here.Login;
import com.cda.here.entidades.Listas;
import com.cda.here.entidades.Users;

import java.util.ArrayList;

public class DbUsers extends DbHelper{
    Context context;
    public DbUsers(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long insertarUsuario(String nombre, String psw){
        long Id = 0;
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursorVer = null;
        cursorVer = db.rawQuery("SELECT * FROM "+TABLE_USERS+" WHERE us_nombre = '"+nombre+"'", null);
        if(!cursorVer.moveToNext()) {
            try {
                ContentValues values = new ContentValues();
                values.put("us_nombre", nombre);
                values.put("us_psw", psw);
                values.put("us_session", 1);

                Id = db.insert(TABLE_USERS, null, values);
            } catch (Exception ex) {
                ex.toString();
            }
        }
        return Id;
    }

    public ArrayList<Users> buscarUsuario(String nombreUs, String pswUs){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Users> listaUsers = new ArrayList<>();
        Users users = null;
        Cursor cursorUsers = null;

        cursorUsers = db.rawQuery("SELECT * FROM "+ TABLE_USERS+ " WHERE us_nombre = '"+nombreUs+"' AND us_psw = '"+pswUs+"'", null);
        if(cursorUsers.moveToFirst()) {
            do {
                users = new Users();
                users.setIdUs(cursorUsers.getInt(0));
                users.setNombreUs(cursorUsers.getString(1));
                users.setPswUs(cursorUsers.getString(2));
                listaUsers.add(users);
            }while (cursorUsers.moveToNext());
        }
        else{
            users = new Users();
            users.setIdUs(0);
            users.setNombreUs("ERROR");
            users.setPswUs("ERROR");
            listaUsers.add(users);
        }
        cursorUsers.close();
        return listaUsers;
    }

    public boolean modificarSesion(int idSes, int idUser){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db =  dbHelper.getWritableDatabase();
        boolean confirmacion;
        try {
            db.execSQL("UPDATE " + TABLE_USERS + " SET us_session = '"+ idSes +"' WHERE us_id = '" + idUser + "'");
            confirmacion = true;
        }catch (Exception ex){
            ex.toString();
            confirmacion = false;
        }finally {
            db.close();
        }
        return confirmacion;
    }

    public ArrayList<Users> verificarSesion(){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db =  dbHelper.getWritableDatabase();

        ArrayList<Users> listaUser = new ArrayList<>();
        Cursor cursorUser = null;
        Users user = null;

            cursorUser = db.rawQuery("SELECT * FROM "+ TABLE_USERS +" WHERE us_session = 2 LIMIT 1", null);
            if(cursorUser.moveToFirst()) {
                    user = new Users();
                    user.setIdUs(cursorUser.getInt(0));
                    user.setNombreUs(cursorUser.getString(1));
                    user.setPswUs(cursorUser.getString(2));
                    user.setIdSes(cursorUser.getInt(3));
                    listaUser.add(user);
            }
            else {
                user = new Users();
                user.setIdSes(0);
                listaUser.add(user);
            }
            cursorUser.close();

        return listaUser;
    }

}
