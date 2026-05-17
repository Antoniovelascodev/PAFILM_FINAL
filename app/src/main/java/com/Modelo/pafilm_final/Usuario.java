package com.Modelo.pafilm_final;

public class Usuario {

    //atrib
    private int idUsuario;
    private String nombreUsuario;
    private String contrasena;

    //constr
    public Usuario(String nombreUsuario, int idUsuario, String contrasena) {
        this.nombreUsuario = nombreUsuario;
        this.idUsuario = idUsuario;
        this.contrasena = contrasena;
    }

    public Usuario() {
    }

    //getteryseteer
    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }




}
