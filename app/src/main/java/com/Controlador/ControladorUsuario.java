package com.Controlador;

import com.Modelo.pafilm_final.Usuario;
import com.Modelo.pafilm_final.UsuarioDao;



import android.content.Context;


public class ControladorUsuario {

    public void registrarUsuario(Context context, Usuario u) {
        if (!UsuarioDao.existeUsuario(context, u.getNombreUsuario())) {
            UsuarioDao.crearUsuario(context, u);
        }
    }

    public Usuario login(Context context, String nombre, String pass) {
        return UsuarioDao.login(context, nombre, pass);
    }


    public int nuevoId(Context context) {
        return UsuarioDao.nuevoIdUsuario(context);
    }

    public boolean existeUsuario(Context context, String nombre) {
        return UsuarioDao.existeUsuario(context, nombre);
    }
}