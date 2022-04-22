package com.example.easymeal.cl.model.bd;

public class RecetaPreparacion {
    Integer idRecetaPreparacion,idReceta,idPreparaciones,cantidadAPreparar;

    public RecetaPreparacion() {
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

    public Integer getCantidadAPreparar() {
        return cantidadAPreparar;
    }

    public void setCantidadAPreparar(Integer cantidadAPreparar) {
        this.cantidadAPreparar = cantidadAPreparar;
    }
}
