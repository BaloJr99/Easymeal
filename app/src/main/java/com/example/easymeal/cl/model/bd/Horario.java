package com.example.easymeal.cl.model.bd;

public class Horario {
    String idHorario,comidas;
    Integer fecha,username;

    public Horario() {
    }

    public Horario(String idHorario, String comidas, Integer fecha, Integer username) {
        this.idHorario = idHorario;
        this.comidas = comidas;
        this.fecha = fecha;
        this.username = username;
    }
    public boolean isNull(){
        if(idHorario.equals("")&&comidas.equals("")&&fecha.equals("")&&username.equals("")){
            return false;
        }else{
            return true;
        }
    }
    @Override
    public String toString(){
        return "Horario{ " +
                "idHorario='" + idHorario + '\'' +
                ", comidas='" + comidas + '\'' +
                ", fecha='" + fecha + '\'' +
                ", username='" + username + '\'' +
                '}';
    }

    public String getIdHorario() {
        return idHorario;
    }

    public void setIdHorario(String idHorario) {
        this.idHorario = idHorario;
    }

    public String getComidas() {
        return comidas;
    }

    public void setComidas(String comidas) {
        this.comidas = comidas;
    }

    public Integer getFecha() {
        return fecha;
    }

    public void setFecha(Integer fecha) {
        this.fecha = fecha;
    }

    public Integer getUsername() {
        return username;
    }

    public void setUsername(Integer username) {
        this.username = username;
    }
}
