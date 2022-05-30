package com.example.easymeal.mensajeria;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.easymeal.R;

public class HolderMensaje extends RecyclerView.ViewHolder {

    private TextView nombre, mensaje, hora;
    public HolderMensaje(View itemView) {
        super(itemView);
        nombre = itemView.findViewById(R.id.tvNombreM);
        mensaje = itemView.findViewById(R.id.tvMensaje);
        hora = itemView.findViewById(R.id.tvHoraM);
    }

    public TextView getNombre() {
        return nombre;
    }

    public void setNombre(TextView nombre) {
        this.nombre = nombre;
    }

    public TextView getMensaje() {
        return mensaje;
    }

    public void setMensaje(TextView mensaje) {
        this.mensaje = mensaje;
    }

    public TextView getHora() {
        return hora;
    }

    public void setHora(TextView hora) {
        this.hora = hora;
    }
}
