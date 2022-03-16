package com.example.easymeal.cl.model.bd;

public class Preparaciones {
    Integer idPreparaciones;
    String tipoComida,fechaPreparacion;

    public Preparaciones(){

    }
    public Preparaciones(Integer idPreparaciones, String tipoComida, String fechaPreparacion){
    this.idPreparaciones = idPreparaciones;
    this.tipoComida = tipoComida;
    this.fechaPreparacion = fechaPreparacion;
    }
    public boolean isNull(){
        if(tipoComida.equals("")&&fechaPreparacion.equals("")){
            return false;
        }else{
            return true;
        }
    }
    @Override
    public String toString(){
        return "Preparaciones{ " +
                "idPreparaciones='" + idPreparaciones + '\'' +
                ", tipoComida='" + tipoComida + '\'' +
                ", fechaPreparacion='" + fechaPreparacion + '\'' +
                '}';
    }

    public Integer getIdPreparaciones() {
        return idPreparaciones;
    }

    public void setIdPreparaciones(Integer idPreparaciones) {
        this.idPreparaciones = idPreparaciones;
    }

    public String getfechaPreparacion() {
        return fechaPreparacion;
    }

    public void setfechaPreparacion(String fechaPreparacion) {
        this.fechaPreparacion = fechaPreparacion;
    }

    public String getTipoComida() {
        return tipoComida;
    }

    public void setTipoComida(String tipoComida) {
        this.tipoComida = tipoComida;
    }
}
