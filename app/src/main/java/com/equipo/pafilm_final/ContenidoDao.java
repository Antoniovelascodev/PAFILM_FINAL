package com.equipo.pafilm_final;

import android.content.Context;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ContenidoDao {

    // aqui hay mucho que entender, la diferencia entre la clase Contenido es por el tipo como hemos visto antes,
    // lo que hago es que llamo con una constante final al contenido.json para jugar con esa variable y no
    // tener que estar escribiendola to do el rato
    // JsonObject es para referenciar un nuevo objeto sobre el contenido que hay en el indice i es decir
    // creo un obj con ese contenido y creo un contenido c y para meterle las cosas lo hago a traves del obj
    // esta estructura es la que he seguido en casi todos los lados.
    // en los bucles hago los set de ese mismo contenido con su categoria despues y lo añado a la lista así con casi to

    private static final String FICHERO = "contenidos.json";

    public static List<Contenido> obtenerContenido(Context context) { // lee el fichero y devuelve todos los contenidos en una lista
        List<Contenido> lista = new ArrayList<>();
        try {
            String contenido = GestorFicheros.leerFichero(context, FICHERO);
            JSONArray array = new JSONArray(contenido);
            int i = 0;
            while (i < array.length()) {
                JSONObject obj = array.getJSONObject(i);
                Contenido c = new Contenido();
                c.setIdContenido(obj.getInt("idContenido"));
                c.setTitulo(obj.getString("titulo"));
                c.setAnio(obj.getInt("anio"));
                c.setNota(obj.getDouble("nota"));
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

    private static void guardarContenidos(Context context, List<Contenido> lista) { // solo lo usa este DAO para sobreescribir el fichero y meter lo nuevo
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
            GestorFicheros.escribirFichero(context, FICHERO, array.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static Contenido buscarPorId(Context context, int id) { // devuelve el contenido con ese id
        List<Contenido> lista = obtenerContenido(context);
        int i = 0;
        while (i < lista.size()) {
            if (lista.get(i).getIdContenido() == id) {
                return lista.get(i);
            }
            i++;
        }
        return null;
    }

    public static List<Contenido> obtenerPorTipo(Context context, String tipo) { // Filtra una lista de pelis o series
        List<Contenido> lista = obtenerContenido(context);
        List<Contenido> filtrada = new ArrayList<>();
        int i = 0;
        while (i < lista.size()) {
            if (lista.get(i).getTipo().equalsIgnoreCase(tipo)) {
                filtrada.add(lista.get(i));
            }
            i++;
        }
        return filtrada;
    }

    public static List<Contenido> buscarPorTitulo(Context context, String texto) { // Busca pelis o series que contengan ese texto
        List<Contenido> lista = obtenerContenido(context);
        List<Contenido> resultado = new ArrayList<>();
        int i = 0;
        while (i < lista.size()) {
            if (lista.get(i).getTitulo().toLowerCase().contains(texto.toLowerCase())) { // mientras tenga ese texto busca donde tenga
                resultado.add(lista.get(i));
            }
            i++;
        }
        return resultado;
    }

    public static void actualizarNotaMedia(Context context, int idContenido) { // pa cambiar la nota de las peliculas o series con su valoracion de cada reseña
        List<Resena> resenas = ResenaDao.obtenerPorContenido(context, idContenido); // esto lo que hace es que siempre que se meta una reseña se actualiza solo con este mismo id, no pilla otro contenido
        if (resenas.isEmpty()) return;

        double suma = 0;
        int i = 0;
        while (i < resenas.size()) {
            suma += resenas.get(i).getNota();
            i++;
        }
        double media = suma / resenas.size();

        //  buscamos el contenido y le cambiamos la nota
        List<Contenido> listaContenido = obtenerContenido(context);
        int j = 0;
        while (j < listaContenido.size()) {
            if (listaContenido.get(j).getIdContenido() == idContenido) {
                listaContenido.get(j).setNota(media);
                break;
            }
            j++;
        }
        guardarContenidos(context, listaContenido);
    }

    public static void insertar(Context context, Contenido contenido) { // añade un contenido nuevo al fichero
        List<Contenido> lista = obtenerContenido(context);
        lista.add(contenido);
        guardarContenidos(context, lista);
    }

    public static int nuevoId(Context context) { // hace el siguiente id disponible pa el contenido
        List<Contenido> lista = obtenerContenido(context);
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
