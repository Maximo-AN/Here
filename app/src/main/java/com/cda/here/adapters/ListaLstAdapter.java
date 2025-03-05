package com.cda.here.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cda.here.R;
import com.cda.here.VerLista;
import com.cda.here.entidades.Listas;

import java.util.ArrayList;

public class ListaLstAdapter extends RecyclerView.Adapter<ListaLstAdapter.ListaViewHolder> {

    ArrayList<Listas> listasArrayList;
    public ListaLstAdapter(ArrayList<Listas> listasArrayList){
        this.listasArrayList = listasArrayList;
    }

    @NonNull
    @Override
    public ListaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_item_lst, parent, false);
        return new ListaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaViewHolder holder, int position) {
        holder.txtNombre.setText(listasArrayList.get(position).getNombre());
        holder.txtIdLista.setText(String.valueOf(listasArrayList.get(position).getNumLista()));
    }

    @Override
    public int getItemCount() {
        return listasArrayList.size();
    }

    public class ListaViewHolder extends RecyclerView.ViewHolder {

        TextView txtNombre, txtIdLista;
        public ListaViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNombre = itemView.findViewById(R.id.txtvNombre);
            txtIdLista = itemView.findViewById(R.id.txtvNumero);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context =  view.getContext();
                    Intent verLista = new Intent(context, VerLista.class);
                    verLista.putExtra("idLista", listasArrayList.get(getAdapterPosition()).getId());
                    verLista.putExtra("idDocente", listasArrayList.get(getAdapterPosition()).getIdAdmin());
                    context.startActivity(verLista);
                }
            });
        }
    }
}
