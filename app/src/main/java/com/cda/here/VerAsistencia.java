package com.cda.here;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.cda.here.adapters.AsistenciaLstAdapter;
import com.cda.here.adapters.EstudiantesLstAdapter;
import com.cda.here.db.DbAsistencia;
import com.cda.here.db.DbEstudiantes;
import com.cda.here.entidades.Asistencia;

import java.util.ArrayList;

public class VerAsistencia extends AppCompatActivity {
    TextView txtvMensajeAs;
    RecyclerView rcvAsistencia;
    ArrayList<Asistencia> asistenciaArrayList;
    int lista, idU;
    long estudiante;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asistencia);
        txtvMensajeAs = findViewById(R.id.txtvMensajeAs);
        rcvAsistencia = findViewById(R.id.rcvAsistencia);
        Bundle bundle = getIntent().getExtras();
        lista = bundle.getInt("listId");
        idU = bundle.getInt("idAdmin");
        estudiante = bundle.getLong("estMat");

        rcvAsistencia.setLayoutManager(new LinearLayoutManager(this));
        DbAsistencia dbAsistencia = new DbAsistencia(this);
        asistenciaArrayList = new ArrayList<>();

        AsistenciaLstAdapter adapter = new AsistenciaLstAdapter(dbAsistencia.mostrarAsistencia(estudiante));
        rcvAsistencia.setAdapter(adapter);
        int itemsEst = rcvAsistencia.getAdapter().getItemCount();
        if(itemsEst > 0){
            txtvMensajeAs.setText("");
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        Intent i = new Intent(VerAsistencia.this, VerLista.class);
        i.putExtra("idLista", lista);
        i.putExtra("idDocente", idU);
        startActivity(i);
    }
}