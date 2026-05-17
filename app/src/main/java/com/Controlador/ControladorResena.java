package com.Controlador;
import android.content.Context;

import com.Modelo.pafilm_final.ContenidoDao;
import com.Modelo.pafilm_final.Resena;
import com.Modelo.pafilm_final.ResenaDao;

import java.util.List;

public class ControladorResena {

    public void crearResena(Context context, Resena r) {
        ResenaDao.crearResena(context, r);

        // recalcular nota automáticamente
        ContenidoDao.actualizarNotaMedia(context, r.getIdContenido());
    }

    public List<Resena> obtenerTodas(Context context) {
        return ResenaDao.obtenerResenas(context);
    }

    public List<Resena> porUsuario(Context context, int idUsuario) {
        return ResenaDao.obtenerPorUsuario(context, idUsuario);
    }

    public List<Resena> porContenido(Context context, int idContenido) {
        return ResenaDao.obtenerPorContenido(context, idContenido);
    }

    public int nuevoId(Context context) {
        return ResenaDao.nuevoIdResena(context);
    }
}