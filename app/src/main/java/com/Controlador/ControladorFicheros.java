package com.Controlador;
import android.content.Context;

import com.Modelo.pafilm_final.GestorFicheros;

public class ControladorFicheros {

    public void guardarLogin(Context context, String usuario, String pass) {
        String data = usuario + ";" + pass;
        GestorFicheros.escribirFichero(context, "config.txt", data);
    }

    public String leerConfig(Context context) {
        return GestorFicheros.leerFichero(context, "config.txt");
    }
}