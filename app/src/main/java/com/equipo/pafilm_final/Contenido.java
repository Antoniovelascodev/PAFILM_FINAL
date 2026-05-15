package com.equipo.pafilm_final;

public class Contenido {

    // atributos
    private int idContenido;
    private String titulo;
    private int anio;
    private double nota;
    private String tipo;       // peli o serie
    private boolean spoiler;

    //constr
    public Contenido(int idContenido, String titulo, int anio, double nota, String tipo, boolean spoiler) {
        this.idContenido = idContenido;
        this.titulo = titulo;
        this.anio = anio;
        this.nota = nota;
        this.tipo = tipo;
        this.spoiler = spoiler;
    }
    public Contenido() {
    }

    //getterysetter
    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public int getIdContenido() {
        return idContenido;
    }

    public void setIdContenido(int idContenido) {
        this.idContenido = idContenido;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

    public boolean isSpoiler() {
        return spoiler;
    }

    public void setSpoiler(boolean spoiler) {
        this.spoiler = spoiler;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }


}
