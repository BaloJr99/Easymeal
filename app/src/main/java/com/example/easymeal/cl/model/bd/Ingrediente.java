package com.example.easymeal.cl.model.bd;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;

public class Ingrediente {
    Integer idIngrediente;
    String unidadDeMedida,descripcion,fechaCaducidad;
    Float cantidad;
    byte[] imagen;

    public Ingrediente() {
    }

    public Ingrediente(Integer idIngrediente, String unidadDeMedida, String descripcion, String fechaCaducidad, float cantidad, byte[] imagen) {
        this.idIngrediente = idIngrediente;
        this.unidadDeMedida = unidadDeMedida;
        this.descripcion = descripcion;
        this.fechaCaducidad = fechaCaducidad;
        this.cantidad = cantidad;
        this.imagen = imagen;
    }

    public boolean isNull(){
        if(idIngrediente.equals("")&&unidadDeMedida.equals("")&&descripcion.equals("")&&fechaCaducidad.equals("")&&cantidad.equals("")){
            return false;
        }else{
            return true;
        }
    }

    @Override
    public String toString() {
        return "Ingrediente{" +
                "idIngrediente=" + idIngrediente +
                ", unidadDeMedida='" + unidadDeMedida + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", fechaCaducidad='" + fechaCaducidad + '\'' +
                ", cantidad=" + cantidad +
                ", imagen=" + Arrays.toString(imagen) +
                '}';
    }

    public Integer getIdIngrediente() {
        return idIngrediente;
    }

    public void setIdIngrediente(Integer idIngrediente) {
        this.idIngrediente = idIngrediente;
    }

    public String getUnidadDeMedida() {
        return unidadDeMedida;
    }

    public void setUnidadDeMedida(String unidadDeMedida) {
        this.unidadDeMedida = unidadDeMedida;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(String fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    public float getCantidad() {
        return cantidad;
    }

    public void setCantidad(float cantidad) {
        this.cantidad = cantidad;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }
}
