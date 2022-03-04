package com.example.easymeal.cl.model.bd;

public class Alimento {
    Integer idAlimento,idPreparaciones;
    String nombre;
    Float cantidad;

    public Alimento() {
    }

    public Alimento(Integer idAlimento, Integer idPreparaciones, String nombre, Float cantidad) {
        this.idAlimento = idAlimento;
        this.idPreparaciones = idPreparaciones;
        this.nombre = nombre;
        this.cantidad = cantidad;
    }
    public boolean isNull(){
        if(idAlimento.equals("")&&nombre.equals("")&&cantidad.equals("")&&idPreparaciones.equals("")){
            return false;
        }else{
            return true;
        }
    }
    @Override
    public String toString(){
        return "Alimento{ " +
                "idAlimento='" + idAlimento + '\'' +
                ", nombre='" + nombre + '\'' +
                ", cantidad='" + cantidad + '\'' +
                ", idPreparaciones='" + idPreparaciones + '\'' +
                '}';
    }

    public Integer getIdAlimento() {
        return idAlimento;
    }

    public void setIdAlimento(Integer idAlimento) {
        this.idAlimento = idAlimento;
    }

    public Integer getIdPreparaciones() {
        return idPreparaciones;
    }

    public void setIdPreparaciones(Integer idPreparaciones) {
        this.idPreparaciones = idPreparaciones;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Float getCantidad() {
        return cantidad;
    }

    public void setCantidad(Float cantidad) {
        this.cantidad = cantidad;
    }
}
