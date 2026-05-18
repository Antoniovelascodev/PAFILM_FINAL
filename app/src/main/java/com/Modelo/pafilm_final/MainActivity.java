package com.Modelo.pafilm_final;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.util.Log;

import com.Modelo.pafilm_final.R;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // inserto pelis mientras esté vacio para no liarla
        if (ContenidoDao.obtenerContenidos(this).isEmpty()) {
            ContenidoDao.crearContenido(this, new Contenido(1, "Interestellar", 2014, 8.7, "Pelicula", false));
            ContenidoDao.crearContenido(this, new Contenido(2, "The Bear", 2022, 8.6, "Serie", false));
        }

        // aqui pa comprobar si se ponen los datos viendolo desde el cat de abajo
        List<Contenido> lista = ContenidoDao.obtenerContenidos(this);
        Log.d("PAFILM_PRUEBA", "Cantidad de contenidos en DB: " + lista.size());

        // esto pa que cuando inicie se vea en el movil que esta furulando la base de datos
        android.widget.Toast.makeText(this, "Datos cargados: " + lista.size() + " contenidos", android.widget.Toast.LENGTH_LONG).show();
        for (Contenido c : lista) {
            Log.d("PAFILM_PRUEBA", "Cargado: " + c.getTitulo() + " (" + c.getTipo() + ") - Spoiler: " + c.isSpoiler());
        }

        // --- PRUEBA DE RESEÑAS CON SPOILER ---
        if (ResenaDao.obtenerResenas(this).isEmpty()) {
            // Creamos una reseña con spoiler (true)
            Resena r = new Resena(1, "Gran final", "No me esperaba que muriera el protagonista", 9.0f, 1, 1, true);
            ResenaDao.crearResena(this, r);
            
            // Creamos una reseña sin spoiler (false)
            Resena r2 = new Resena(2, "Me gustó", "Muy buena fotografía", 8.0f, 1, 1, false);
            ResenaDao.crearResena(this, r2);
        }

        List<Resena> resenas = ResenaDao.obtenerResenas(this);
        for (Resena res : resenas) {
            String aviso = res.isSpoiler() ? "[¡SPOILER!]" : "[Limpio]";
            Log.d("PAFILM_PRUEBA", "Reseña: " + res.getTitulo() + " " + aviso + ": " + res.getComentario());
        }
        // -------------------------------------

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
