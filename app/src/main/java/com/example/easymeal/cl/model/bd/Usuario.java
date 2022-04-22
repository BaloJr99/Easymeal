package com.example.easymeal.cl.model.bd;

public class Usuario {
    String username,clave,nombre,apellidoPaterno,apellidoMaterno,fechaNacimiento;
    int idUsuario;

    public Usuario() {

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
                "IdUsuario="+idUsuario+'\'' +
                "Username='" + username + '\'' +
                ", Clave='" + clave + '\'' +
                ", Nombre='" + nombre + '\'' +
                ", ApellidoPaterno='" + apellidoPaterno + '\'' +
                ", ApellidoMaterno='" + apellidoMaterno + '\'' +
                ", fechaNacimiento='" + fechaNacimiento + '\'' +
                '}';
    }

    public int getIdUsuario() { return idUsuario; }

    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

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
