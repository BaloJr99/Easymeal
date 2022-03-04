package com.example.easymeal.cl.model.bd;

public class Ingrediente {
    Integer idIngrediente,idAlimento;
    String unidadDeMedida,tipo;

    public Ingrediente() {
    }

    public Ingrediente(Integer idIngrediente, Integer idAlimento, String unidadDeMedida, String tipo) {
        this.idIngrediente = idIngrediente;
        this.idAlimento = idAlimento;
        this.unidadDeMedida = unidadDeMedida;
        this.tipo = tipo;
    }
    public boolean isNull(){
        if(idIngrediente.equals("")&&idAlimento.equals("")&&unidadDeMedida.equals("")&&tipo.equals("")){
            return false;
        }else{
            return true;
        }
    }
    @Override
    public String toString(){
        return "Ingrediente{ " +
                "idIngrediente='" + idIngrediente + '\'' +
                ", unidadDeMedida='" + unidadDeMedida + '\'' +
                ", tipo='" + tipo + '\'' +
                ", idAlimento='" + idAlimento + '\'' +
                '}';
    }

    public Integer getIdIngrediente() {
        return idIngrediente;
    }

    public void setIdIngrediente(Integer idIngrediente) {
        this.idIngrediente = idIngrediente;
    }

    public Integer getIdAlimento() {
        return idAlimento;
    }

    public void setIdAlimento(Integer idAlimento) {
        this.idAlimento = idAlimento;
    }

    public String getUnidadDeMedida() {
        return unidadDeMedida;
    }

    public void setUnidadDeMedida(String unidadDeMedida) {
        this.unidadDeMedida = unidadDeMedida;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
