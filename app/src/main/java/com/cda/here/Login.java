package com.cda.here;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cda.here.db.DbUsers;
import com.cda.here.entidades.Users;

import java.util.ArrayList;

public class Login extends AppCompatActivity {
Button btnLogin, btnRegistro;
EditText txtPsw, txtUser;
int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegistro = findViewById(R.id.btnRegistro);
        txtPsw = findViewById(R.id.txtaPassword2);
        txtUser = findViewById(R.id.txtaUser2);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Entrar();
            }
        });

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Registrarse();
            }
        });

    }

    public void Entrar(){
        if(valCampos()) {
            String user = txtUser.getText().toString(),
                    psw = txtPsw.getText().toString();
            //Busqueda de usuario en la base de datos
            DbUsers dbUsers = new DbUsers(Login.this);
            ArrayList<Users> users = dbUsers.buscarUsuario(user, psw);
            for(Users us:users){
                if(us.getNombreUs().equals(user) && us.getPswUs().equals(psw)){
                    id = users.get(0).getIdUs();
                    dbUsers.modificarSesion(2, id);
                    Intent intent = new Intent(this, MainActivity.class);
                    intent.putExtra("idAdmin", id);
                    startActivity(intent);
                    finish();
                }
                else if(us.getIdUs() == 0){
                    Toast.makeText(Login.this, "Usuario inexistente o contraseña incorrecta", Toast.LENGTH_SHORT).show();
                }
            }
        }
        else{
            Toast.makeText(Login.this, "Rellene todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    public void Registrarse(){
        Intent intent = new Intent(Login.this, Registro.class);
        startActivity(intent);
    }

    public boolean valCampos(){
        boolean retorno = true;
        if(txtUser.getText().toString().trim().length() == 0 ||
                txtPsw.getText().toString().trim().length() == 0)
            retorno = false;
        return retorno;
    }
}