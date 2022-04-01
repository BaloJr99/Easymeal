package com.example.easymeal.cl.model.bd;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;

public class Ingrediente {
    Integer idIngrediente, mandado;
    String unidadDeMedida,descripcion,fechaCaducidad, proveedor;
    Float cantidad, cantidadAComprar;
    byte[] imagen;

    public Ingrediente() {
    }

    public Ingrediente(Integer idIngrediente, Integer mandado, String unidadDeMedida, String descripcion, String fechaCaducidad, Float cantidad, Float cantidadAComprar, byte[] imagen, String proveedor) {
        this.idIngrediente = idIngrediente;
        this.mandado = mandado;
        this.unidadDeMedida = unidadDeMedida;
        this.descripcion = descripcion;
        this.fechaCaducidad = fechaCaducidad;
        this.cantidad = cantidad;
        this.cantidadAComprar = cantidadAComprar;
        this.imagen = imagen;
        this.proveedor = proveedor;
    }

    public Ingrediente(Integer idIngrediente, String descripcion, float cantidadAComprar, String unidadDeMedida, String proveedor) {
        this.idIngrediente = idIngrediente;
        this.unidadDeMedida = unidadDeMedida;
        this.descripcion = descripcion;
        this.cantidadAComprar = cantidadAComprar;
        this.proveedor = proveedor;
    }

    public Ingrediente(Integer idIngrediente, String descripcion, String unidadDeMedida, float cantidad, String proveedor) {
        this.idIngrediente = idIngrediente;
        this.unidadDeMedida = unidadDeMedida;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.proveedor = proveedor;
    }

    @Override
    public String toString() {
        return "Ingrediente{" +
                "idIngrediente=" + idIngrediente +
                ", mandado=" + mandado +
                ", unidadDeMedida='" + unidadDeMedida + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", fechaCaducidad='" + fechaCaducidad + '\'' +
                ", cantidadAComprar='" + cantidadAComprar + '\'' +
                ", cantidad=" + cantidad +
                ", proveedor=" + proveedor +
                ", imagen=" + Arrays.toString(imagen) +
                '}';
    }

    public Integer getIdIngrediente() {
        return idIngrediente;
    }

    public void setIdIngrediente(Integer idIngrediente) {
        this.idIngrediente = idIngrediente;
    }

    public Integer getMandado() {
        return mandado;
    }

    public void setMandado(Integer mandado) {
        this.mandado = mandado;
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

    public Float getCantidad() {
        return cantidad;
    }

    public void setCantidad(Float cantidad) {
        this.cantidad = cantidad;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public Float getCantidadAComprar() {
        return cantidadAComprar;
    }

    public void setCantidadAComprar(Float cantidadAComprar) {
        this.cantidadAComprar = cantidadAComprar;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }
}
