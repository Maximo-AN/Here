package com.cda.here;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cda.here.adapters.ListaLstAdapter;
import com.cda.here.db.DbListas;
import com.cda.here.db.DbUsers;
import com.cda.here.entidades.Listas;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{
    FloatingActionButton fabCrear;
    RecyclerView lstListas;
    TextView txtvMensaje;
    ArrayList<Listas> listasArrayList;
    int idU;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int idAdmin = getIntent().getExtras().getInt("idAdmin");
        idU = idAdmin;
        fabCrear = findViewById(R.id.fabAgregar);
        txtvMensaje = findViewById(R.id.txtvMensaje);
        lstListas = findViewById(R.id.rcvListas);
        fabCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                crearLista();
            }
        });

        lstListas.setLayoutManager(new LinearLayoutManager(this));
        DbListas dbListas = new DbListas(MainActivity.this);
        listasArrayList = new ArrayList<>();

        ListaLstAdapter adapter = new ListaLstAdapter(dbListas.mostrarListas(idAdmin));
        lstListas.setAdapter(adapter);
        int itemsRcv = lstListas.getAdapter().getItemCount();
        if(itemsRcv > 0){
            txtvMensaje.setText("");
        }
    }

    void crearLista(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.crear_lista);
        final EditText txtNombre = dialog.findViewById(R.id.txtNombre);
        Button btnCrear = dialog.findViewById(R.id.btnCrear);

        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = MainActivity.this;
                String nombreL = txtNombre.getText().toString();
                DbListas dbListas = new DbListas(context);
                long id = dbListas.insertarLista(nombreL, idU);
                if(id > 0) {
                        Toast.makeText(MainActivity.this, "Lista creada", Toast.LENGTH_SHORT).show();
                        refrescar();
                    }
                    else
                    Toast.makeText(MainActivity.this, "Error al crear la lista", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void refrescar(){
        finish();
        Intent intent = new Intent(MainActivity.this, MainActivity.class);
        intent.putExtra("idAdmin", idU);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Salir")
                        .setMessage("¿Seguro que quieres salir de la aplicación?")
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                                dialogInterface.dismiss();
                            }

                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).show();
                break;

            case R.id.item1:
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Confirmación")
                        .setMessage("¿Cerrar Sesión?")
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Context context = MainActivity.this;
                                DbUsers dbUsers = new DbUsers(context);
                                boolean resultado = dbUsers.modificarSesion(1, idU);
                                if(resultado){
                                    Toast.makeText(MainActivity.this,
                                            "Sesión Cerrada...", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(MainActivity.this, Login.class);
                                    finish();
                                    startActivity(intent);
                                }
                                else{
                                    Toast.makeText(MainActivity.this,
                                            "Ha ocurrido un error", Toast.LENGTH_SHORT).show();
                                }
                                dialogInterface.dismiss();
                            }

                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).show();
                break;
            case R.id.item2:
                acercaDe();
                break;
        }
        return true;
    }

    public void acercaDe(){
        LayoutInflater caratula = getLayoutInflater();
        Toast mostrar = new Toast(getApplicationContext());
        View vista = caratula.inflate(R.layout.acercade, null);
        mostrar.setDuration(Toast.LENGTH_SHORT);
        mostrar.setView(vista);
        mostrar.setGravity(Gravity.CENTER, 0, 0);
        mostrar.show();
    }

    @Override
    public void onBackPressed(){
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Salir")
                .setMessage("¿Seguro que quieres salir de la aplicación?")
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finishAffinity();
                        System.exit(0);
                        dialogInterface.dismiss();
                    }

                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
    }
}