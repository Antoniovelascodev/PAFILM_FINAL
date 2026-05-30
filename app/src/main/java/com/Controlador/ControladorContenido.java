package com.Controlador;
import android.content.Context;

import com.Modelo.pafilm_final.Contenido;
import com.Modelo.pafilm_final.ContenidoDao;



public class ControladorContenido {

    public void crearContenido(Context context, Contenido c) {
        ContenidoDao.crearContenido(context, c);
    }


    public Contenido obtenerUno(Context context, int id) {
        return ContenidoDao.leerContenido(context, id);
    }


    public Contenido buscarPorTituloExacto(Context context, String titulo) {
        return ContenidoDao.buscarPorTituloExacto(context, titulo);
    }


    public int nuevoId(Context context) {
        return ContenidoDao.nuevoIdContenido(context);
    }
}
