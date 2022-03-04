package com.example.easymeal.cl.model.bd;

public class Preparaciones {
    Integer idPreparaciones;
    String nombreComida,tipoComida;

    public Preparaciones(){

    }
    public Preparaciones(Integer idPreparaciones, String nombreComida, String tipoComida){
    this.idPreparaciones = idPreparaciones;
    this.nombreComida = nombreComida;
    this.tipoComida = tipoComida;
    }
    public boolean isNull(){
        if(idPreparaciones.equals("")&&nombreComida.equals("")&&tipoComida.equals("")){
            return false;
        }else{
            return true;
        }
    }
    @Override
    public String toString(){
        return "Preparaciones{ " +
                "idPreparaciones='" + idPreparaciones + '\'' +
                ", nombreComida='" + nombreComida + '\'' +
                ", tipoComida='" + tipoComida + '\'' +
                '}';
    }

    public Integer getIdPreparaciones() {
        return idPreparaciones;
    }

    public void setIdPreparaciones(Integer idPreparaciones) {
        this.idPreparaciones = idPreparaciones;
    }

    public String getNombreComida() {
        return nombreComida;
    }

    public void setNombreComida(String nombreComida) {
        this.nombreComida = nombreComida;
    }

    public String getTipoComida() {
        return tipoComida;
    }

    public void setTipoComida(String tipoComida) {
        this.tipoComida = tipoComida;
    }
}
