package com.example.easymeal.cl.model.bd;

public class Usuario {
    String username,clave,nombre,apellidoPaterno,apellidoMaterno,fechaNacimiento;

    public Usuario() {

    }

    public Usuario(String username, String clave, String nombre, String apellidoPaterno, String apellidoMaterno, String fechaNacimiento) {
        this.username = username;
        this.clave = clave;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.fechaNacimiento = fechaNacimiento;
    }

    public boolean isNull(){
        if(nombre.equals("")&&apellidoPaterno.equals("")&&apellidoMaterno.equals("")&&username.equals("")&&clave.equals("")&&fechaNacimiento.equals("")){
            return false;
        }else{
            return true;
        }
    }
    @Override
    public String toString(){
        return "Usuario{ " +
                "Username='" + username + '\'' +
                ", Clave='" + clave + '\'' +
                ", Nombre='" + nombre + '\'' +
                ", ApellidoPaterno='" + apellidoPaterno + '\'' +
                ", ApellidoMaterno='" + apellidoMaterno + '\'' +
                ", fechaNacimiento='" + fechaNacimiento + '\'' +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
}
