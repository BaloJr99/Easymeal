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
            case 5:
                mensaje = "Campo Fecha Vacio";
                break;
            case 6:
                mensaje = "Favor de tomar la foto";
                break;
            case 7:
                mensaje = "No hay importe capturado";
                break;
            case 8:
                mensaje = "Favor de ingresar la fecha";
                break;

        }
        return mensaje;
    }
}
