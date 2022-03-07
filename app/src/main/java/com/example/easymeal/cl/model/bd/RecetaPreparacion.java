package com.example.easymeal.cl.model.bd;

public class RecetaPreparacion {
    Integer idRecetaPreparacion,idReceta,idPreparaciones;

    public RecetaPreparacion() {
    }

    public RecetaPreparacion(Integer idRecetaPreparacion, Integer idReceta, Integer idPreparaciones) {
        this.idRecetaPreparacion = idRecetaPreparacion;
        this.idReceta = idReceta;
        this.idPreparaciones = idPreparaciones;
    }

    public boolean isNull(){
        if(idRecetaPreparacion.equals("")&&idReceta.equals("")&&idPreparaciones.equals("")){
            return false;
        }else{
            return true;
        }
    }

    @Override
    public String toString() {
        return "RecetaPreparacion{" +
                "idRecetaPreparacion=" + idRecetaPreparacion +
                ", idReceta=" + idReceta +
                ", idPreparaciones=" + idPreparaciones +
                '}';
    }

    public Integer getIdRecetaPreparacion() {
        return idRecetaPreparacion;
    }

    public void setIdRecetaPreparacion(Integer idRecetaPreparacion) {
        this.idRecetaPreparacion = idRecetaPreparacion;
    }

    public Integer getIdReceta() {
        return idReceta;
    }

    public void setIdReceta(Integer idReceta) {
        this.idReceta = idReceta;
    }

    public Integer getIdPreparaciones() {
        return idPreparaciones;
    }

    public void setIdPreparaciones(Integer idPreparaciones) {
        this.idPreparaciones = idPreparaciones;
    }
}
