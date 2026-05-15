package com.equipo.pafilm_final;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ResenaDao {

    private static final String FICHERO = "resenas.json";

    public static List<Resena> obtenerResena() { // lee el fichero y devuelve todas las reseñas en una lista para asi poder verlsa
        List<Resena> lista = new ArrayList<>();
        try {
            String contenido = GestorFicheros.leerFichero(FICHERO);
            JSONArray array = new JSONArray(contenido);
            int i = 0;
            while (i < array.length()) {
                JSONObject obj = array.getJSONObject(i);
                Resena r = new Resena();
                r.setIdResena(obj.getInt("idResena"));
                r.setTitulo(obj.getString("titulo"));
                r.setTipo(obj.getString("tipo"));
                r.setComentario(obj.getString("comentario"));
                r.setNota((float) obj.getDouble("nota"));
                r.setIdUsuario(obj.getInt("idUsuario"));
                r.setIdContenido(obj.getInt("idContenido"));
                lista.add(r); // despyes de añadirle al r el contenido lo añadimos a la lista para asi luego tenerla e imprimirla con sus valores
                i++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return lista;
    }

    private static void guardarResenas(List<Resena> lista) { // solo lo usa este DAO internamente para sobreescribir el fichero
        try {
            JSONArray array = new JSONArray();
            int i = 0;
            while (i < lista.size()) {
                Resena r = lista.get(i);
                JSONObject obj = new JSONObject();
                obj.put("idResena", r.getIdResena());
                obj.put("titulo", r.getTitulo());
                obj.put("tipo", r.getTipo());
                obj.put("comentario", r.getComentario());
                obj.put("nota", r.getNota());
                obj.put("idUsuario", r.getIdUsuario());
                obj.put("idContenido", r.getIdContenido());
                array.put(obj);
                i++;
            }
            GestorFicheros.escribirFichero(FICHERO, array.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static List<Resena> obtenerPorUsuario(int idUsuario) { // devuelve todas las reseñas de un usuario concreto
        List<Resena> lista = obtenerResena();
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

    public static List<Resena> obtenerPorContenido(int idContenido) { // devuelve todas las reseñas de una pelicula o serie concreta
        List<Resena> lista = obtenerResena();
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

    public static void insertar(Resena resena) { // añade una reseña nueva al fichero
        List<Resena> lista = obtenerResena();
        lista.add(resena);
        guardarResenas(lista);
    }

    public static int nuevoId() { // genera el siguiente id disponible
        List<Resena> lista = obtenerResena();
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
