package com.example.easymeal.cl.model.bd;

public class Receta {
    Integer idReceta;
    String nombre,pasos;

    public Receta() {
    }

    public Receta(Integer idReceta, String nombre, String pasos) {
        this.idReceta = idReceta;
        this.nombre = nombre;
        this.pasos = pasos;
    }

    public boolean isNull(){
        if(idReceta.equals("")&&nombre.equals("")&&pasos.equals("")){
            return false;
        }else{
            return true;
        }
    }

    @Override
    public String toString() {
        return "Receta{" +
                "idReceta=" + idReceta +
                ", nombre='" + nombre + '\'' +
                ", pasos='" + pasos + '\'' +
                '}';
    }

    public Integer getIdReceta() {
        return idReceta;
    }

    public void setIdReceta(Integer idReceta) {
        this.idReceta = idReceta;
    }

    public String getPasos() {
        return pasos;
    }

    public void setPasos(String pasos) {
        this.pasos = pasos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
