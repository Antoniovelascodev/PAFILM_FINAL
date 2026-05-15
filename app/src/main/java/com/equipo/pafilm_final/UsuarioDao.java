package com.equipo.pafilm_final;

import android.content.Context;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UsuarioDao {

    //misma estructura qie en ContenidoDao

    private static final String FICHERO = "usuarios.json";

    public static List<Usuario> obtenerUsuarios(Context context) {
        List<Usuario> lista = new ArrayList<>();
        try {
            String contenido = GestorFicheros.leerFichero(context, FICHERO);
            JSONArray array = new JSONArray(contenido);
            int i = 0;
            while (i < array.length()) {
                JSONObject obj = array.getJSONObject(i);
                Usuario u = new Usuario();
                u.setIdUsuario(obj.getInt("idUsuario"));
                u.setNombreUsuario(obj.getString("nombreUsuario"));
                u.setContrasena(obj.getString("contrasena"));
                lista.add(u);
                i++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return lista;
    }

    private static void guardarUsuarios(Context context, List<Usuario> lista) {
        try {
            JSONArray array = new JSONArray();
            int i = 0;
            while (i < lista.size()) {
                Usuario u = lista.get(i);
                JSONObject obj = new JSONObject();
                obj.put("idUsuario", u.getIdUsuario());
                obj.put("nombreUsuario", u.getNombreUsuario());
                obj.put("contrasena", u.getContrasena());
                array.put(obj);
                i++;
            }
            GestorFicheros.escribirFichero(context, FICHERO, array.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static Usuario buscarPorId(Context context, int id) {
        List<Usuario> lista = obtenerUsuarios(context);
        int i = 0;
        while (i < lista.size()) {
            if (lista.get(i).getIdUsuario() == id) {
                return lista.get(i);
            }
            i++;
        }
        return null;
    }

    public static Usuario login(Context context, String nombre, String contrasena) { // para hacer login aunque nose muy bien que puedo añadir aqui solo hace login y ya
        List<Usuario> lista = obtenerUsuarios(context);
        int i = 0;
        while (i < lista.size()) {
            Usuario u = lista.get(i);
            if (u.getNombreUsuario().equals(nombre) && u.getContrasena().equals(contrasena)) {
                return u;
            }
            i++;
        }
        return null;
    }

    public static boolean existeUsuario(Context context, String nombre) { // para ver si existe un usuario accediendo al contexto y devuelve si hay o no
        List<Usuario> lista = obtenerUsuarios(context);
        int i = 0;
        while (i < lista.size()) {
            if (lista.get(i).getNombreUsuario().equalsIgnoreCase(nombre)) {
                return true;
            }
            i++;
        }
        return false;
    }

    public static void insertar(Context context, Usuario usuario) { // pa insertar un usuario
        List<Usuario> lista = obtenerUsuarios(context);
        lista.add(usuario);
        guardarUsuarios(context, lista);
    }

    public static int nuevoId(Context context) { // pa ponerle un id nuevo que se autoaumenta
        List<Usuario> lista = obtenerUsuarios(context);
        if (lista.isEmpty()) return 1;
        int maxId = 0;
        int i = 0;
        while (i < lista.size()) {
            if (lista.get(i).getIdUsuario() > maxId) {
                maxId = lista.get(i).getIdUsuario();
            }
            i++;
        }
        return maxId + 1;
    }
}
