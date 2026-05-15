package com.equipo.pafilm_final;

import android.content.Context;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ResenaDao {

    // sigue la misma estructura que he explicado en contenidoDao

    private static final String FICHERO = "resenas.json";

    public static List<Resena> obtenerResena(Context context) { // lee el fichero y devuelve todas las reseñas en una lista
        List<Resena> lista = new ArrayList<>();
        try {
            String contenido = GestorFicheros.leerFichero(context, FICHERO);
            JSONArray array = new JSONArray(contenido);
            int i = 0;
            while (i < array.length()) {
                JSONObject obj = array.getJSONObject(i);
                Resena r = new Resena();
                r.setIdResena(obj.getInt("idResena"));
                r.setTitulo(obj.getString("titulo"));
                r.setComentario(obj.getString("comentario"));
                r.setNota((float) obj.getDouble("nota"));
                r.setIdUsuario(obj.getInt("idUsuario"));
                r.setIdContenido(obj.getInt("idContenido"));
                lista.add(r);
                i++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return lista;
    }

    private static void guardarResenas(Context context, List<Resena> lista) { // sobreescribe el fichero con la lista actualizada
        try {
            JSONArray array = new JSONArray();
            int i = 0;
            while (i < lista.size()) {
                Resena r = lista.get(i);
                JSONObject obj = new JSONObject();
                obj.put("idResena", r.getIdResena());
                obj.put("titulo", r.getTitulo());
                obj.put("comentario", r.getComentario());
                obj.put("nota", r.getNota());
                obj.put("idUsuario", r.getIdUsuario());
                obj.put("idContenido", r.getIdContenido());
                array.put(obj);
                i++;
            }
            GestorFicheros.escribirFichero(context, FICHERO, array.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static List<Resena> obtenerPorUsuario(Context context, int idUsuario) { // devuelve todas las reseñas de un usuario concreto
        List<Resena> lista = obtenerResena(context);
        List<Resena> resultado = new ArrayList<>();
        int i = 0;
        while (i < lista.size()) {
            if (lista.get(i).getIdUsuario() == idUsuario) {
                resultado.add(lista.get(i));
            }
            i++;
        }
        return resultado;
    }

    public static List<Resena> obtenerPorContenido(Context context, int idContenido) { // devuelve todas las reseñas de una pelicula o serie concreta
        List<Resena> lista = obtenerResena(context);
        List<Resena> resultado = new ArrayList<>();
        int i = 0;
        while (i < lista.size()) {
            if (lista.get(i).getIdContenido() == idContenido) {
                resultado.add(lista.get(i));
            }
            i++;
        }
        return resultado;
    }

    public static void insertar(Context context, Resena resena) { // añade una reseña nueva al fichero
        List<Resena> lista = obtenerResena(context);
        lista.add(resena);
        guardarResenas(context, lista);

        // Llamamos a ContenidoDao para que actualice la nota media de la pelio serie
        ContenidoDao.actualizarNotaMedia(context, resena.getIdContenido());
    }

    public static int nuevoId(Context context) { // genera el siguiente id disponible
        List<Resena> lista = obtenerResena(context);
        if (lista.isEmpty()) return 1;
        int maxId = 0;
        int i = 0;
        while (i < lista.size()) {
            if (lista.get(i).getIdResena() > maxId) {
                maxId = lista.get(i).getIdResena();
            }
            i++;
        }
        return maxId + 1;
    }

}
