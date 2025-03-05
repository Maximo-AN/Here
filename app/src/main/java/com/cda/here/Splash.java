package com.cda.here;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cda.here.db.DbHelper;
import com.cda.here.db.DbUsers;
import com.cda.here.entidades.Users;

import java.util.ArrayList;

public class Splash extends AppCompatActivity {
    TextView txtTexto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        txtTexto = findViewById(R.id.textView4);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                DbHelper dbHelper = new DbHelper(Splash.this);
                if(dbHelper.verificarBD()) {
                    DbUsers dbUsers = new DbUsers(Splash.this);
                    ArrayList<Users> user = dbUsers.verificarSesion();
                    for(Users us:user) {
                        if (us.getIdSes() == 2) {
                            int id = user.get(0).getIdUs();
                            dbUsers.modificarSesion(2, id);
                            Intent intent = new Intent(Splash.this, MainActivity.class);
                            intent.putExtra("idAdmin", id);
                            startActivity(intent);
                        }
                        else {
                            Intent intento = new Intent(Splash.this, Login.class);
                            startActivity(intento);
                        }
                    }
                }
                else {
                    Intent intento = new Intent(Splash.this, Registro.class);
                    startActivity(intento);
                }
                finish();
            }
        },3500);

        DbHelper dbHelper2 = new DbHelper(Splash.this);
        if(dbHelper2.verificarBD()) {
            txtTexto.setText("Hey...\n¿Qué tal?");
            txtTexto.setGravity(Gravity.CENTER);
        }
        else {
            txtTexto.setText("¡Bienvenid@!");
        }
    }
}