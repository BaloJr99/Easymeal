package com.example.easymeal.cl.model.bd;

public class Producto {
    Integer idProducto;
    String proveedor;

    public Producto() {
    }

    public Producto(String proveedor) {
        this.proveedor = proveedor;
    }

    public Producto(Integer idProducto, String proveedor) {
        this.idProducto = idProducto;
        this.proveedor = proveedor;
    }

    public boolean isNull(){
        if(proveedor.equals("")){
            return false;
        }else{
            return true;
        }
    }
    @Override
    public String toString(){
        return "Producto{ " +
                "idProducto='" + idProducto + '\'' +
                ", proveedor='" + proveedor + '\'' +
                '}';
    }
    public Integer getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }
}
