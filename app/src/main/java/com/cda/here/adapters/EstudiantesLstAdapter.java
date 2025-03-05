package com.cda.here.adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cda.here.R;
import com.cda.here.VerAsistencia;
import com.cda.here.VerLista;
import com.cda.here.db.DbAsistencia;
import com.cda.here.db.DbEstudiantes;
import com.cda.here.entidades.Estudiantes;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class EstudiantesLstAdapter extends RecyclerView.Adapter<EstudiantesLstAdapter.EstudianteViewHolder> {
    ArrayList<Estudiantes> estudiantesArrayList;

    public EstudiantesLstAdapter(ArrayList<Estudiantes> estudiantesArrayList){
        this.estudiantesArrayList = estudiantesArrayList;
    }

    @NonNull
    @Override
    public EstudianteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.estudiante_item_lst, parent, false);
        return new EstudianteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EstudianteViewHolder holder, int position) {
        ///Vincular objetos
        holder.txtvMatricula.setText(String.valueOf(estudiantesArrayList.get(position).getMatricula()));
        holder.txtvNumLista.setText(String.valueOf(estudiantesArrayList.get(position).getNumeroL()));
        holder.txtvNombreAl.setText(estudiantesArrayList.get(position).getNombreAl());
    }

    @Override
    public int getItemCount() {
        return estudiantesArrayList.size();
    }

    public class EstudianteViewHolder extends RecyclerView.ViewHolder {
        //Tambien vincular objetos
        TextView txtvNombreAl, txtvNumLista, txtvMatricula;
        Context context;
        public EstudianteViewHolder(@NonNull View itemView) {
            super(itemView);
            txtvMatricula = itemView.findViewById(R.id.txtvMatricula);
            txtvNombreAl = itemView.findViewById(R.id.txtvNombreAl);
            txtvNumLista = itemView.findViewById(R.id.txtvNumeroAl);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    opcionesEstudiante();
                }
            });
        }
        void opcionesEstudiante(){
            context = itemView.getContext();
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(true);
            dialog.setContentView(R.layout.opcionesest);

            String nombre = txtvNombreAl.getText().toString(),
            matricula = txtvMatricula.getText().toString(),
            numlista = txtvNumLista.getText().toString();
            /*Lista super larga de declaracion de objetos, son "final" porque se usaran una vez en el dialogo*/
            final EditText txtAlumnoAct = dialog.findViewById(R.id.txtAlumnoAct);
            final EditText txtMatriculaAct =  dialog.findViewById(R.id.txtMatriculaAct);
            final EditText txtNumListaAct = dialog.findViewById(R.id.txtNumListaAct);
            final Spinner spnAlumno = dialog.findViewById(R.id.spnAlumno);
            final LinearLayout objActualizar = dialog.findViewById(R.id.objetosAlumno);
            final LinearLayout objEliminar = dialog.findViewById(R.id.objetosEliminar);
            final LinearLayout objAsistencia = dialog.findViewById(R.id.objetosAsistencia);
            final RadioButton rdbAsistencia = dialog.findViewById(R.id.rdbAsistencia);
            final RadioButton rdbFalta = dialog.findViewById(R.id.rdbFalta);
            final RadioButton rdbPendiente = dialog.findViewById(R.id.rdbPendiente);
            final RadioGroup rdgBotones = dialog.findViewById(R.id.rbgBotones);
            Button btnAceptar = dialog.findViewById(R.id.btnAceptar);
            spnAlumno.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    String opcion =  spnAlumno.getSelectedItem().toString();
                    switch (opcion){
                        case "Opciones":
                        objActualizar.setVisibility(View.GONE);
                        objAsistencia.setVisibility(View.GONE);
                        objEliminar.setVisibility(View.GONE);
                            break;
                        case "Ver historial de asistencia":
                            objActualizar.setVisibility(View.GONE);
                            objAsistencia.setVisibility(View.GONE);
                            objEliminar.setVisibility(View.GONE);
                            break;
                        case "Manejar asistencia":
                            objActualizar.setVisibility(View.GONE);
                            objAsistencia.setVisibility(View.VISIBLE);
                            objEliminar.setVisibility(View.GONE);
                            rdgBotones.clearCheck();
                            break;
                        case "Actualizar datos":
                            objActualizar.setVisibility(View.VISIBLE);
                            objAsistencia.setVisibility(View.GONE);
                            objEliminar.setVisibility(View.GONE);
                            txtAlumnoAct.setText(nombre);
                            txtMatriculaAct.setText(matricula);
                            txtNumListaAct.setText(numlista);
                            break;
                        case "Borrar alumno":
                            objActualizar.setVisibility(View.GONE);
                            objAsistencia.setVisibility(View.GONE);
                            objEliminar.setVisibility(View.VISIBLE);
                            break;
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            btnAceptar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String opcion =  spnAlumno.getSelectedItem().toString();
                    DbAsistencia dbAsistencia = new DbAsistencia(context);
                    DbEstudiantes dbEstudiantes = new DbEstudiantes(context);
                    if(opcion.equals("Opciones")){
                        Toast.makeText(context, "Elija una opcion valida para continuar", Toast.LENGTH_SHORT).show();
                    }

                    if(opcion.equals("Ver historial de asistencia")){
                        //Programar activity rapida
                        Intent i =  new Intent(context, VerAsistencia.class);
                        i.putExtra("listId", estudiantesArrayList.get(getAdapterPosition()).getListaAlumno());
                        i.putExtra("estMat", estudiantesArrayList.get(getAdapterPosition()).getMatricula());
                        i.putExtra("idAdmin", estudiantesArrayList.get(getAdapterPosition()).getDocenteAlumno());
                        context.startActivity(i);
                        dialog.dismiss();
                    }

                    if(opcion.equals("Manejar asistencia")) {
                        Calendar calendar;
                        SimpleDateFormat dateFormat;
                        String estado = "", fecha;
                        calendar = Calendar.getInstance();
                        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        fecha = dateFormat.format(calendar.getTime());
                        if (!rdbAsistencia.isChecked() && !rdbPendiente.isChecked() && !rdbFalta.isChecked()) {
                            Toast.makeText(context, "Selecciona alguna opcion...", Toast.LENGTH_SHORT).show();
                        } else {
                            if (rdbAsistencia.isChecked()) {
                                estado = "ASISTENCIA";
                            }
                            if (rdbFalta.isChecked()) {
                                estado = "FALTA";
                            }
                            if (rdbPendiente.isChecked()) {
                                estado = "PENDIENTE";
                            }
                            long res = dbAsistencia.insertarAsistencia(estado,
                                    estudiantesArrayList.get(getAdapterPosition()).getListaAlumno(),
                                    estudiantesArrayList.get(getAdapterPosition()).getMatricula(),
                                    estudiantesArrayList.get(getAdapterPosition()).getDocenteAlumno());
                            if (res > 0) {
                                Toast.makeText(context, "Asistencia actualizada", Toast.LENGTH_SHORT).show();
                                actualizar();
                            } else {
                                Toast.makeText(context, "Ha ocurrido un error", Toast.LENGTH_SHORT).show();
                            }
                            dialog.dismiss();
                        }
                    }
                        if(opcion.equals("Actualizar datos")) {
                            boolean correcto = dbEstudiantes.actualizarAlumno(
                                    Long.parseLong(txtMatriculaAct.getText().toString()),
                                    Integer.parseInt(txtNumListaAct.getText().toString()),
                                    txtAlumnoAct.getText().toString(),
                                    estudiantesArrayList.get(getAdapterPosition()).getMatricula()
                            );
                            if(correcto){
                                Toast.makeText(context, "Datos actualizados", Toast.LENGTH_SHORT).show();
                                actualizar();
                            }
                            else {
                                Toast.makeText(context, "Error al actualizar", Toast.LENGTH_SHORT).show();
                            }
                            dialog.dismiss();
                        }
                        if (opcion.equals("Borrar alumno")) {
                            long maticulaEliminar = estudiantesArrayList.get(getAdapterPosition()).getMatricula();
                            boolean res = dbEstudiantes.eliminarAlumno(maticulaEliminar);
                            if(res){
                                Toast.makeText(context, "Alumno eliminado", Toast.LENGTH_SHORT).show();
                                actualizar();
                            }
                            else{
                                Toast.makeText(context, "Error al eliminar", Toast.LENGTH_SHORT).show();
                            }
                            dialog.dismiss();
                        }
                    }
            });
            dialog.show();
        }

        public void actualizar(){
            Intent i = new Intent(context, VerLista.class);
            i.putExtra("idLista", estudiantesArrayList.get(getAdapterPosition()).getListaAlumno());
            i.putExtra("idDocente", estudiantesArrayList.get(getAdapterPosition()).getDocenteAlumno());
            context.startActivity(i);
        }

    }

}
