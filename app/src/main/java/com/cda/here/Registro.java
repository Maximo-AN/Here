package com.cda.here;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteBindOrColumnIndexOutOfRangeException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cda.here.db.DbHelper;
import com.cda.here.db.DbUsers;

public class Registro extends AppCompatActivity {
Button btnReg;
EditText txtUser, txtPsw, txtVerPsw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        btnReg = findViewById(R.id.btnReg);
        txtUser = findViewById(R.id.txtaUser);
        txtPsw = findViewById(R.id.txtaPassword);
        txtVerPsw = findViewById(R.id.txtaVPass);
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Enviar();
            }
        });
    }

    public void Enviar(){
        String psw = txtPsw.getText().toString(),
                pswVer = txtVerPsw.getText().toString(), user = txtUser.getText().toString();
        if(valCampos()) {
            if(psw.equals(pswVer)) {
                    Context context = this;
                    DbUsers dbUsers = new DbUsers(context);
                    long id = dbUsers.insertarUsuario(user, psw);
                    if(id > 0) {
                        Toast.makeText(this, "\tRegistro Exitoso\nIngrese ahora sus credenciales.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(this, Login.class);
                        startActivity(intent);
                        finish();
                    }
                    else
                        Toast.makeText(this, "Error en el registro\nEl usuario ya existe", Toast.LENGTH_SHORT).show();
            }
            else
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(this, "No deje ningun campo vacio", Toast.LENGTH_SHORT).show();
    }


    public boolean valCampos(){
        boolean retorno = true;
        if(txtUser.getText().toString().trim().length() == 0 ||
        txtPsw.getText().toString().trim().length() == 0)
            retorno = false;
        return retorno;
    }
}