package com.equipo.pafilm_final;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UsuarioDao {

    private static final String FICHERO = "usuarios.json"; // del fichero que vamos a coger

    public static List<Usuario> obtenerUsuarios() { // para que añada a la lista todos los usuarios introducidos en el fichero y asi poder mostrarlos
        List<Usuario> lista = new ArrayList<>();
        try {
            String contenido = GestorFicheros.leerFichero(FICHERO);
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

    private static void guardarUsuarios(List<Usuario> lista) { // lo pongo privado a que solo se use en esta clase, sobreescribe la lista con lo que se le pase
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
            GestorFicheros.escribirFichero(FICHERO, array.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static Usuario buscarPorId(int id) { // busca ese Usuario por su id
        List<Usuario> lista = obtenerUsuarios();
        int i = 0;
        while (i < lista.size()) {
            if (lista.get(i).getIdUsuario() == id) {
                return lista.get(i);
            }
            i++;
        }
        return null;
    }

    public static Usuario login(String nombre, String contrasena) {
        List<Usuario> lista = obtenerUsuarios();
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

    public static void insertar(Usuario usuario) { // le mete el usuario nuevo al fichero
        List<Usuario> lista = obtenerUsuarios(); // creo una lista con lo mismo que treniamos y le añado el usuario nuevo
        lista.add(usuario);
        guardarUsuarios(lista); // le pongo la ufncion que he creado antes para asi meterla en la lista directamente
    }

    public static int nuevoId() { // para saber el nuevo id que hay que ponerle al siguiente usuario
        List<Usuario> lista = obtenerUsuarios();
        if (lista.isEmpty()) return 1;
        int maxId = 0;
        int i = 0;
        while (i < lista.size()) {
            if (lista.get(i).getIdUsuario() > maxId) { // si el id es mayor pasa a ser el valor y se le suma 1 al final
                maxId = lista.get(i).getIdUsuario();
            }
            i++;
        }
        return maxId + 1;
    }

}
