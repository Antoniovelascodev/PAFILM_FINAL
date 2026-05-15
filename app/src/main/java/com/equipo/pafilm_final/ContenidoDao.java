package com.equipo.pafilm_final;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ContenidoDao {


    private static final String FICHERO = "contenidos.json";

    public static List<Contenido> obtenerContenido() { // lee el fichero y devuelve todos los contenidos en una lista
        List<Contenido> lista = new ArrayList<>();
        try {
            String contenido = GestorFicheros.leerFichero(FICHERO);
            JSONArray array = new JSONArray(contenido);
            int i = 0;
            while (i < array.length()) {
                JSONObject obj = array.getJSONObject(i);
                Contenido c = new Contenido();
                c.setIdContenido(obj.getInt("idContenido"));
                c.setTitulo(obj.getString("titulo"));
                c.setAnio(obj.getInt("anio"));
                c.setNota((float) obj.getDouble("nota"));
                c.setTipo(obj.getString("tipo"));
                c.setSpoiler(obj.getBoolean("spoiler"));
                lista.add(c);
                i++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return lista;
    }

    private static void guardarContenidos(List<Contenido> lista) { // solo lo usa este DAO  paa sobreescribir el fichero
        try {
            JSONArray array = new JSONArray();
            int i = 0;
            while (i < lista.size()) {
                Contenido c = lista.get(i);
                JSONObject obj = new JSONObject();
                obj.put("idContenido", c.getIdContenido());
                obj.put("titulo", c.getTitulo());
                obj.put("anio", c.getAnio());
                obj.put("nota", c.getNota());
                obj.put("tipo", c.getTipo());
                obj.put("spoiler", c.isSpoiler());
                array.put(obj);
                i++;
            }
            GestorFicheros.escribirFichero(FICHERO, array.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static Contenido buscarPorId(int id) { // devuelve el contenido con ese id
        List<Contenido> lista = obtenerContenido();
        int i = 0;
        while (i < lista.size()) {
            if (lista.get(i).getIdContenido() == id) {
                return lista.get(i);
            }
            i++;
        }
        return null;
    }

    public static void insertar(Contenido contenido) { // añade un contenido nuevo al fichero
        List<Contenido> lista = obtenerContenido();
        lista.add(contenido);
        guardarContenidos(lista);
    }

    public static int nuevoId() { // hace el siguiente id disponible pa el contenido
        List<Contenido> lista = obtenerContenido();
        if (lista.isEmpty()) return 1;
        int maxId = 0;
        int i = 0;
        while (i < lista.size()) {
            if (lista.get(i).getIdContenido() > maxId) {
                maxId = lista.get(i).getIdContenido();
            }
            i++;
        }
        return maxId + 1;
    }

}
