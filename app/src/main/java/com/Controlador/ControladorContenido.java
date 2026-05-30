package com.Controlador;
import android.content.Context;

import com.Modelo.pafilm_final.Contenido;
import com.Modelo.pafilm_final.ContenidoDao;

public class ControladorContenido {

    public void crearContenido(Context context, Contenido c) {
        ContenidoDao.crearContenido(context, c);
    }

    public int nuevoId(Context context) {
        return ContenidoDao.nuevoIdContenido(context);
    }
}