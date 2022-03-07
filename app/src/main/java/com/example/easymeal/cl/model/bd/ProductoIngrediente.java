package com.example.easymeal.cl.model.bd;

public class ProductoIngrediente {
    Integer idProductoIngrediente,idProducto,idIngrediente;

    public ProductoIngrediente() {
    }

    public ProductoIngrediente(Integer idProductoIngrediente, Integer idProducto, Integer idIngrediente) {
        this.idProductoIngrediente = idProductoIngrediente;
        this.idProducto = idProducto;
        this.idIngrediente = idIngrediente;
    }

    public boolean isNull(){
        if(idProductoIngrediente.equals("")&&idProducto.equals("")&&idIngrediente.equals("")){
            return false;
        }else{
            return true;
        }
    }
    @Override
    public String toString(){
        return "ProductoIngrediente{ " +
                "idProductoIngrediente='" + idProductoIngrediente + '\'' +
                ", idProducto='" + idProducto + '\'' +
                ", idIngrediente='" + idIngrediente + '\'' +
                '}';
    }

    public Integer getIdProductoIngrediente() {
        return idProductoIngrediente;
    }

    public void setIdProductoIngrediente(Integer idProductoIngrediente) {
        this.idProductoIngrediente = idProductoIngrediente;
    }

    public Integer getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public Integer getIdIngrediente() {
        return idIngrediente;
    }

    public void setIdIngrediente(Integer idIngrediente) {
        this.idIngrediente = idIngrediente;
    }
}
