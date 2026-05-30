package com.Controlador;
import android.content.Context;

import com.Modelo.pafilm_final.Contenido;
import com.Modelo.pafilm_final.ContenidoDao;

import java.util.List;

public class ControladorContenido {

    public void crearContenido(Context context, Contenido c) {
        ContenidoDao.crearContenido(context, c);
    }

    public List<Contenido> obtenerTodos(Context context) {
        return ContenidoDao.obtenerContenidos(context);
    }

    public Contenido obtenerUno(Context context, int id) {
        return ContenidoDao.leerContenido(context, id);
    }

    public List<Contenido> buscarPorTitulo(Context context, String titulo) {
        return ContenidoDao.buscarPorTitulo(context, titulo);
    }

    public Contenido buscarPorTituloExacto(Context context, String titulo) {
        return ContenidoDao.buscarPorTituloExacto(context, titulo);
    }

    public List<Contenido> porTipo(Context context, String tipo) {
        return ContenidoDao.obtenerPorTipo(context, tipo);
    }

    public void actualizarNota(Context context, int idContenido) {
        ContenidoDao.actualizarNotaMedia(context, idContenido);
    }

    public int nuevoId(Context context) {
        return ContenidoDao.nuevoIdContenido(context);
    }
}
