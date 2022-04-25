package com.example.easymeal.cl.model.bd;

public class IngredienteReceta {
    Integer idIngredientesReceta,idIngrediente,idReceta;
    Float cantidad;

    public IngredienteReceta() {
    }

    public IngredienteReceta(Integer idIngredientesReceta, Integer idIngrediente, Integer idReceta, Float cantidad) {
        this.idIngredientesReceta = idIngredientesReceta;
        this.idIngrediente = idIngrediente;
        this.idReceta = idReceta;
        this.cantidad = cantidad;
    }

    public boolean isNull(){
        if(idIngrediente.equals(0)&&idReceta.equals(0)&&cantidad.equals(0f)){
            return false;
        }else{
            return true;
        }
    }

    @Override
    public String toString() {
        return "IngredienteReceta{" +
                "idIngredientesReceta=" + idIngredientesReceta +
                ", idIngrediente=" + idIngrediente +
                ", idReceta=" + idReceta +
                ", cantidad=" + cantidad +
                '}';
    }

    public Integer getIdIngredientesReceta() {
        return idIngredientesReceta;
    }

    public void setIdIngredientesReceta(Integer idIngredientesReceta) {
        this.idIngredientesReceta = idIngredientesReceta;
    }

    public Integer getIdIngrediente() {
        return idIngrediente;
    }

    public void setIdIngrediente(Integer idIngrediente) {
        this.idIngrediente = idIngrediente;
    }

    public Integer getIdReceta() {
        return idReceta;
    }

    public void setIdReceta(Integer idReceta) {
        this.idReceta = idReceta;
    }

    public Float getCantidad() {
        return cantidad;
    }

    public void setCantidad(Float cantidad) {
        this.cantidad = cantidad;
    }
}
