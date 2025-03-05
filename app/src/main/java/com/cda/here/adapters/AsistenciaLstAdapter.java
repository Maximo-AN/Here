package com.cda.here.adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cda.here.R;
import com.cda.here.VerAsistencia;
import com.cda.here.db.DbAsistencia;
import com.cda.here.entidades.Asistencia;

import java.util.ArrayList;

public class AsistenciaLstAdapter extends RecyclerView.Adapter<AsistenciaLstAdapter.AsistenciaViewHolder>{
    ArrayList<Asistencia> asistenciaArrayList;
    public AsistenciaLstAdapter(ArrayList<Asistencia> asistenciaArrayList){
        this.asistenciaArrayList = asistenciaArrayList;
    }

    @NonNull
    @Override
    public AsistenciaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.asistencia_item_lst, parent, false);
        return new AsistenciaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AsistenciaViewHolder holder, int position) {
        holder.txtvFecha.setText(asistenciaArrayList.get(position).getFecha());
        holder.txtvEstado.setText(asistenciaArrayList.get(position).getEstado());
        holder.txtvMatricula.setText(String.valueOf(asistenciaArrayList.get(position).getEstudiante()));
    }

    @Override
    public int getItemCount() {
        return asistenciaArrayList.size();
    }

    public class AsistenciaViewHolder extends RecyclerView.ViewHolder {
        TextView txtvMatricula, txtvEstado, txtvFecha;
        Context context;
        public AsistenciaViewHolder(@NonNull View itemView) {
            super(itemView);
            txtvEstado = itemView.findViewById(R.id.txtvEstado);
            txtvFecha = itemView.findViewById(R.id.txtvFecha);
            txtvMatricula = itemView.findViewById(R.id.txtvMatricula);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cambiarAsistencia();
                }
            });
        }

        void cambiarAsistencia(){
            context = itemView.getContext();
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(true);
            dialog.setContentView(R.layout.opcionesasis);
            final RadioButton rdbAsistencia = dialog.findViewById(R.id.rdbAsistenciaN);
            final RadioButton rdbFalta = dialog.findViewById(R.id.rdbFaltaN);
            final RadioButton rdbPendiente = dialog.findViewById(R.id.rdbPendienteN);
            Button btnGuardarCambios = dialog.findViewById(R.id.btnGuardarCambios);
            btnGuardarCambios.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int id = asistenciaArrayList.get(getAdapterPosition()).getIdAs();
                    String estadon = "";
                    DbAsistencia dbAsistencia =new DbAsistencia(context);
                    if(!rdbAsistencia.isChecked() && !rdbFalta.isChecked() && !rdbPendiente.isChecked()){
                        Toast.makeText(context, "Escoge alguna de las opciones...", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        if (rdbAsistencia.isChecked())
                            estadon = "ASISTENCIA";
                        if (rdbFalta.isChecked())
                            estadon = "FALTA";
                        if (rdbPendiente.isChecked())
                            estadon = "PENDIENTE";

                        boolean res = dbAsistencia.cambiarAsistencia(id, estadon);
                        if (res) {
                            Toast.makeText(context, "Éxito en el cambio", Toast.LENGTH_SHORT).show();
                            actualizar();
                            dialog.dismiss();
                        }
                            else
                            Toast.makeText(context, "Ha ocurrido un error", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            dialog.show();
        }

        public void actualizar(){
            Intent i = new Intent(context, VerAsistencia.class);
            i.putExtra("listId", asistenciaArrayList.get(getAdapterPosition()).getNumlista());
            i.putExtra("idAdmin", asistenciaArrayList.get(getAdapterPosition()).getIdDocente());
            i.putExtra("estMat", asistenciaArrayList.get(getAdapterPosition()).getEstudiante());
            context.startActivity(i);
        }

    }
}
