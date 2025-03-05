package com.cda.here;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cda.here.adapters.EstudiantesLstAdapter;
import com.cda.here.db.DbEstudiantes;
import com.cda.here.db.DbListas;
import com.cda.here.entidades.Estudiantes;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class VerLista extends AppCompatActivity {

    int idL, idDocente;
    FloatingActionButton fabAlumno;
    ArrayList<Estudiantes> estudiantesArrayList;
    TextView txtMensaje;
    RecyclerView lstEstudiantes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_lista);
        fabAlumno = findViewById(R.id.fabAlumno);
        txtMensaje = findViewById(R.id.txtvMensaje2);
        lstEstudiantes = findViewById(R.id.rcvEstudiantes);
        idL = getIntent().getExtras().getInt("idLista");
        idDocente = getIntent().getExtras().getInt("idDocente");
        fabAlumno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                crearAlumno();
            }
        });

        lstEstudiantes.setLayoutManager(new LinearLayoutManager(this));
        DbEstudiantes dbEstudiantes = new DbEstudiantes(this);
        estudiantesArrayList = new ArrayList<>();

        EstudiantesLstAdapter adapter = new EstudiantesLstAdapter(dbEstudiantes.mostrarAlumnos(idL));
        lstEstudiantes.setAdapter(adapter);
        int itemsEst = lstEstudiantes.getAdapter().getItemCount();
        if(itemsEst > 0){
            txtMensaje.setText("");
        }
    }

    void crearAlumno(){
        final Dialog dialog = new Dialog(VerLista.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.agregar_alumno);
        final EditText txtNombreAl = dialog.findViewById(R.id.txtNombreAl);
        final EditText txtMatricula = dialog.findViewById(R.id.txtMatricula);
        final EditText txtNumeroL = dialog.findViewById(R.id.txtNumLista);
        Button btnAñadir = dialog.findViewById(R.id.btnAñadir);

        btnAñadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = VerLista.this;
                String nombreAl = txtNombreAl.getText().toString();
                long matricula = Long.parseLong(txtMatricula.getText().toString());
                int numeroL = Integer.parseInt(txtNumeroL.getText().toString());
                DbEstudiantes dbEstudiantes = new DbEstudiantes(context);
                dbEstudiantes.insertarEstudiante(matricula, numeroL, nombreAl, idL, idDocente);
                if(matricula > 0) {
                    Toast.makeText(VerLista.this, "Alumno añadido", Toast.LENGTH_SHORT).show();
                    refrescar();
                }
                else
                    Toast.makeText(VerLista.this, "Error al añadir alumno", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void refrescar(){
        finishAffinity();
        Intent i = new Intent(this, VerLista.class);
        i.putExtra("idLista", idL);
        i.putExtra("idDocente", idDocente);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
        Intent i = new Intent(VerLista.this, MainActivity.class);
        i.putExtra("idAdmin", idDocente);
        startActivity(i);
    }
}