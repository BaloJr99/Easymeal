package com.example.easymeal.cl.model.bd;

public class Compras {
    int idCompra;
    String fechaCompra;
    float importeGasto;

    public Compras() {
    }

    public Compras(String fechaCompra, float importeGasto) {
        this.fechaCompra = fechaCompra;
        this.importeGasto = importeGasto;
    }

    public int getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(int idCompra) {
        this.idCompra = idCompra;
    }

    public String getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(String fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public float getImporteGasto() {
        return importeGasto;
    }

    public void setImporteGasto(float importeGasto) {
        this.importeGasto = importeGasto;
    }
}
