package com.equipo.pafilm_final;

public class Resena {

    //atrb
    private int idResena;
    private String titulo;
    private String comentario;
    private float nota;
    private int idUsuario;    // quién hizo la reseña
    private int idContenido;  // a cual peli o seire pertenece

    //constr
    public Resena(int idResena, String titulo, String comentario, float nota, int idUsuario, int idContenido) {
        this.idResena = idResena;
        this.titulo = titulo;
        this.comentario = comentario;
        this.nota = nota;
        this.idUsuario = idUsuario;
        this.idContenido = idContenido;
    }

    public Resena() {
    }

    //getterysetter
    public int getIdResena() {
        return idResena;
    }

    public void setIdResena(int idResena) {
        this.idResena = idResena;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public float getNota() {
        return nota;
    }

    public void setNota(float nota) {
        this.nota = nota;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdContenido() {
        return idContenido;
    }

    public void setIdContenido(int idContenido) {
        this.idContenido = idContenido;
    }
}
