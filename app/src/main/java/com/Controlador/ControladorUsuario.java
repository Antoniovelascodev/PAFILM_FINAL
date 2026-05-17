package com.Controlador;

import com.Modelo.pafilm_final.Usuario;
import com.Modelo.pafilm_final.UsuarioDao;



import android.content.Context;
import java.util.List;

public class ControladorUsuario {

    public void registrarUsuario(Context context, Usuario u) {
        if (!UsuarioDao.existeUsuario(context, u.getNombreUsuario())) {
            UsuarioDao.crearUsuario(context, u);
        }
    }

    public Usuario login(Context context, String nombre, String pass) {
        return UsuarioDao.login(context, nombre, pass);
    }

    public List<Usuario> obtenerUsuarios(Context context) {
        return UsuarioDao.obtenerUsuarios(context);
    }

    public void eliminarUsuario(Context context, int id) {
        UsuarioDao.eliminarUsuario(context, id);
    }

    public void actualizarUsuario(Context context, Usuario u) {
        UsuarioDao.actualizarUsuario(context, u);
    }

    public int nuevoId(Context context) {
        return UsuarioDao.nuevoIdUsuario(context);
    }
}