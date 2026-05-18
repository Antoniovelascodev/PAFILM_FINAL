package com.Controlador;

import android.content.Context;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class GestorFicheros {

    // Añadimos 'Context context' para que Android nos deje leer de su carpeta privada y no mande errores por todos lados

    public static String leerFichero(Context context, String nombreFichero) {
        String contenidoFichero = "";
        BufferedReader leer = null;
        try {
            // Buscamos el archivo en la carpeta interna de la App por eso el context.getFilerDir() eso es el directorio de la carpeta con acceso a los datos
            File archivo = new File(context.getFilesDir(), nombreFichero);
            leer = new BufferedReader(new FileReader(archivo));
            String linea = leer.readLine();
            while (linea != null) {
                contenidoFichero = contenidoFichero + linea;
                linea = leer.readLine();
            }
        } catch (FileNotFoundException e) {
            return "[]"; // el fichero no existe por eos decimos que el array esta bvacio lo he puesto así para que sea visual
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (leer != null) {
                    leer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (contenidoFichero.equals("")){
            return "[]"; // si esta vacio le devolvemos que esta vacio asi
        }
        return contenidoFichero;
    }

    // usamos el context como antes he explicado
    public static void escribirFichero(Context context, String nombreFichero, String contenido) {
        BufferedWriter escribir = null;
        try {
            // Buscamos o creamos el archivo en la carpeta interna de la App
            File archivo = new File(context.getFilesDir(), nombreFichero);
            escribir = new BufferedWriter(new FileWriter(archivo));
            escribir.write(contenido);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // si tiene algo de texto lo quitamos y cerramos en final.
                if (escribir != null) {
                    escribir.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
