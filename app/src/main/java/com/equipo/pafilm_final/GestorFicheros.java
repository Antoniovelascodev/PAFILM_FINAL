package com.equipo.pafilm_final;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class GestorFicheros {

    public static String leerFichero(String nombreFichero) { // para leer el fichero
        String contenidoFichero = "";
        BufferedReader leer = null;
        try {
            leer = new BufferedReader(new FileReader(nombreFichero));
            String linea = leer.readLine();
            while (linea != null) {
                contenidoFichero = contenidoFichero + linea;
                linea = leer.readLine();
            }
        } catch (FileNotFoundException e) {
            return "[]"; // el fichero no existe por eos decimos que el array esta bvacio
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (leer != null) leer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (contenidoFichero.equals("")) return "[]"; // si esta vacio le devolvemos que esta vacio asi
        return contenidoFichero;
    }

    public static void escribirFichero(String nombreFichero, String contenido) { // para escribir el fichero
        BufferedWriter escribir = null;
        try {
            escribir = new BufferedWriter(new FileWriter(nombreFichero));
            escribir.write(contenido);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (escribir != null) escribir.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
