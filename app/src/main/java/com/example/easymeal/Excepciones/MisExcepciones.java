package com.example.easymeal.Excepciones;

import androidx.annotation.Nullable;

public class MisExcepciones extends Exception{

    int codigo;

    public MisExcepciones(int codigo){
        this.codigo = codigo;
    }

    @Nullable
    @Override
    public String getMessage() {
        String mensaje = "";
        switch(codigo){
            case 1:
                mensaje = "Campo Descripcion Vacio";
                break;
            case 2:
                mensaje = "Campo Cantidad Vacio";
                break;
            case 3:
                mensaje = "Campo Marca Vacio";
                break;
            case 4:
                mensaje = "Campo Medida Vacio";
                break;

        }
        return mensaje;
    }
}
