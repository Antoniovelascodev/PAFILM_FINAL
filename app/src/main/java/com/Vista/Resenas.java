package com.Vista;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.Controlador.ControladorContenido;
import com.Controlador.ControladorResena;
import com.Modelo.pafilm_final.Contenido;
import com.Modelo.pafilm_final.Resena;
import com.equipo.pafilm_final.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Resenas extends AppCompatActivity {

    LinearLayout layoutResenas;
    ImageView ivLogo;
    ControladorResena controladorResena;
    ControladorContenido controladorContenido;
    int idUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.pantalla_resenas);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        idUsuario = getIntent().getIntExtra("idUsuario", -1);
        controladorResena = new ControladorResena();
        controladorContenido = new ControladorContenido();

        layoutResenas = findViewById(R.id.layout_Resenas);
        ivLogo = findViewById(R.id.imageView3);

        ivLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Resenas.this, Selector.class);
                intent.putExtra("idUsuario", idUsuario);
                startActivity(intent);
            }
        });

        cargarAgrupadoPorObra();
    }

    private void cargarAgrupadoPorObra() {
        layoutResenas.removeAllViews();
        List<Resena> todasResenas = controladorResena.obtenerTodas(this);

        if (todasResenas == null || todasResenas.isEmpty()) {
            mostrarMensajeVacio();
            return;
        }

        // 1. Agrupamos TODAS las reseñas por el título normalizado (esto une Historias Corrientes con historias corrientes)
        Map<String, List<Resena>> mapaGrupos = new HashMap<>();
        for (Resena r : todasResenas) {
            String clave = controladorContenido.normalizarTitulo(r.getTitulo());
            if (!mapaGrupos.containsKey(clave)) {
                mapaGrupos.put(clave, new ArrayList<>());
            }
            List<Resena> lista = mapaGrupos.get(clave);
            if (lista != null) {
                lista.add(r);
            }
        }

        // 2. Clasificamos los grupos en Películas y Series para mostrarlos por separado
        List<List<Resena>> listaPeliculas = new ArrayList<>();
        List<List<Resena>> listaSeries = new ArrayList<>();

        for (List<Resena> grupo : mapaGrupos.values()) {
            if (grupo.isEmpty()) continue;
            
            // Miramos el tipo del contenido (cogemos el de la primera reseña del grupo)
            int idCont = grupo.get(0).getIdContenido();
            Contenido c = controladorContenido.obtenerUno(this, idCont);
            
            if (c != null && "Serie".equalsIgnoreCase(c.getTipo())) {
                listaSeries.add(grupo);
            } else {
                listaPeliculas.add(grupo);
            }
        }

        // 3. Dibujamos la sección de PELÍCULAS
        if (!listaPeliculas.isEmpty()) {
            crearSeccionVisual("--- PELÍCULAS ---", Color.parseColor("#880E4F"), listaPeliculas);
        }

        // 4. Dibujamos la sección de SERIES
        if (!listaSeries.isEmpty()) {
            crearSeccionVisual("--- SERIES ---", Color.parseColor("#0D47A1"), listaSeries);
        }
    }

    private void crearSeccionVisual(String tituloSeccion, int color, List<List<Resena>> grupos) {
        // Cabecera de bloque (PELICULAS / SERIES)
        TextView tvCabecera = new TextView(this);
        tvCabecera.setText(tituloSeccion);
        tvCabecera.setTextSize(24);
        tvCabecera.setTypeface(null, Typeface.BOLD);
        tvCabecera.setTextColor(color);
        tvCabecera.setPadding(20, 50, 20, 20);
        tvCabecera.setGravity(Gravity.CENTER);
        layoutResenas.addView(tvCabecera);

        // Por cada obra en este bloque
        for (List<Resena> obraResenas : grupos) {
            float sumaNotas = 0;
            for (Resena r : obraResenas) {
                sumaNotas += r.getNota();
            }
            float mediaFinal = sumaNotas / obraResenas.size();
            String tituloPrincipal = obraResenas.get(0).getTitulo();

            // Contenedor de la Categoría de la Película
            LinearLayout obraContainer = new LinearLayout(this);
            obraContainer.setOrientation(LinearLayout.VERTICAL);
            obraContainer.setPadding(20, 20, 20, 15);
            obraContainer.setBackgroundColor(Color.parseColor("#ECEFF1"));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(15, 25, 15, 10);
            obraContainer.setLayoutParams(params);

            // Título de la obra
            TextView tvT = new TextView(this);
            tvT.setText(tituloPrincipal.toUpperCase());
            tvT.setTextSize(20);
            tvT.setTypeface(null, Typeface.BOLD);
            tvT.setTextColor(Color.BLACK);
            obraContainer.addView(tvT);

            // Nota Media (Calculada entre todas las reseñas de este grupo)
            TextView tvM = new TextView(this);
            tvM.setText("VALORACIÓN MEDIA: " + String.format(Locale.getDefault(), "%.2f", mediaFinal) + " / 10");
            tvM.setTextSize(16);
            tvM.setTextColor(Color.parseColor("#2E7D32"));
            tvM.setTypeface(null, Typeface.BOLD_ITALIC);
            obraContainer.addView(tvM);

            layoutResenas.addView(obraContainer);

            // Reseñas de los usuarios para esta obra
            for (Resena res : obraResenas) {
                TextView tvR = new TextView(this);
                String sp = res.isSpoiler() ? "[ALERTA SPOILER] " : "";
                tvR.setText(sp + "Nota: " + res.getNota() + " - " + res.getComentario());
                tvR.setTextSize(14);
                tvR.setPadding(50, 10, 30, 10);
                layoutResenas.addView(tvR);
            }
            
            // Separador entre obras
            View sep = new View(this);
            sep.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 3));
            sep.setBackgroundColor(Color.LTGRAY);
            layoutResenas.addView(sep);
        }
    }

    private void mostrarMensajeVacio() {
        TextView tv = new TextView(this);
        tv.setText("Todavía no se ha publicado ninguna reseña.");
        tv.setTextSize(18);
        tv.setPadding(30, 150, 30, 30);
        layoutResenas.addView(tv);
    }
}
