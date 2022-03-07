package com.example.easymeal.cl.model.bd;

public class Receta {
    Integer idReceta;
    String pasos;

    public Receta() {
    }

    public Receta(Integer idReceta, String pasos) {
        this.idReceta = idReceta;
        this.pasos = pasos;
    }

    public boolean isNull(){
        if(idReceta.equals("")&&pasos.equals("")){
            return false;
        }else{
            return true;
        }
    }
    @Override
    public String toString(){
        return "Receta{ " +
                "idReceta='" + idReceta + '\'' +
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
}
