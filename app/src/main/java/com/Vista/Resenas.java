package com.Vista;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
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
import com.Modelo.pafilm_final.ContenidoDao;
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

        cargarCategoriasAgrupadas();
    }

    private void cargarCategoriasAgrupadas() {
        layoutResenas.removeAllViews();
        List<Resena> todasResenas = controladorResena.obtenerTodas(this);

        if (todasResenas == null || todasResenas.isEmpty()) {
            mostrarMensajeVacio();
            return;
        }

        // 1. Agrupar reseñas por título normalizado
        Map<String, List<Resena>> grupos = new HashMap<>();
        for (Resena r : todasResenas) {
            String tituloNorm = ContenidoDao.normalizarTitulo(r.getTitulo());
            if (!grupos.containsKey(tituloNorm)) {
                grupos.put(tituloNorm, new ArrayList<>());
            }
            grupos.get(tituloNorm).add(r);
        }

        // 2. Separar grupos en Películas y Series
        List<Map.Entry<String, List<Resena>>> peliculas = new ArrayList<>();
        List<Map.Entry<String, List<Resena>>> series = new ArrayList<>();

        for (Map.Entry<String, List<Resena>> entry : grupos.entrySet()) {
            int idCont = entry.getValue().get(0).getIdContenido();
            Contenido c = controladorContenido.obtenerUno(this, idCont);
            if (c != null && "Serie".equalsIgnoreCase(c.getTipo())) {
                series.add(entry);
            } else {
                peliculas.add(entry);
            }
        }

        // 3. Mostrar Sección Películas
        if (!peliculas.isEmpty()) {
            mostrarCabeceraSeccion("--- PELÍCULAS ---", Color.parseColor("#C2185B"));
            for (Map.Entry<String, List<Resena>> entry : peliculas) {
                crearCategoriaObra(entry.getValue());
            }
        }

        // 4. Mostrar Sección Series
        if (!series.isEmpty()) {
            mostrarCabeceraSeccion("--- SERIES ---", Color.parseColor("#1976D2"));
            for (Map.Entry<String, List<Resena>> entry : series) {
                crearCategoriaObra(entry.getValue());
            }
        }
    }

    private void mostrarCabeceraSeccion(String texto, int color) {
        TextView tv = new TextView(this);
        tv.setText(texto);
        tv.setTextSize(24);
        tv.setTypeface(null, Typeface.BOLD);
        tv.setTextColor(color);
        tv.setPadding(20, 40, 20, 20);
        tv.setGravity(View.TEXT_ALIGNMENT_CENTER);
        layoutResenas.addView(tv);
    }

    private void crearCategoriaObra(List<Resena> resenas) {
        String tituloOriginal = resenas.get(0).getTitulo();
        
        // Calcular media
        float suma = 0;
        for (Resena r : resenas) {
            suma += r.getNota();
        }
        float media = suma / resenas.size();

        // Contenedor de la Categoría (La película en sí)
        LinearLayout catContainer = new LinearLayout(this);
        catContainer.setOrientation(LinearLayout.VERTICAL);
        catContainer.setPadding(20, 20, 20, 10);
        catContainer.setBackgroundColor(Color.parseColor("#F0F4F8"));
        LinearLayout.LayoutParams paramsCat = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        paramsCat.setMargins(10, 20, 10, 10);
        catContainer.setLayoutParams(paramsCat);

        TextView tvTitulo = new TextView(this);
        tvTitulo.setText(tituloOriginal.toUpperCase());
        tvTitulo.setTextSize(18);
        tvTitulo.setTypeface(null, Typeface.BOLD);
        tvTitulo.setTextColor(Color.BLACK);
        catContainer.addView(tvTitulo);

        TextView tvMedia = new TextView(this);
        tvMedia.setText("MEDIA: " + String.format(Locale.getDefault(), "%.1f", media) + " / 10");
        tvMedia.setTextSize(15);
        tvMedia.setTextColor(Color.parseColor("#388E3C")); // Verde
        tvMedia.setTypeface(null, Typeface.BOLD_ITALIC);
        catContainer.addView(tvMedia);

        layoutResenas.addView(catContainer);

        // Listado de reseñas de esta categoría
        for (Resena r : resenas) {
            LinearLayout item = new LinearLayout(this);
            item.setOrientation(LinearLayout.VERTICAL);
            item.setPadding(40, 10, 20, 10);
            
            TextView tvResenaText = new TextView(this);
            String spoilerTag = r.isSpoiler() ? "[SPOILER] " : "";
            tvResenaText.setText(spoilerTag + "Nota: " + r.getNota() + " - " + r.getComentario());
            tvResenaText.setTextSize(14);
            item.addView(tvResenaText);
            
            layoutResenas.addView(item);
        }
        
        // Separador fino
        View v = new View(this);
        v.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 2));
        v.setBackgroundColor(Color.GRAY);
        layoutResenas.addView(v);
    }

    private void mostrarMensajeVacio() {
        TextView tv = new TextView(this);
        tv.setText("No hay nada que mostrar aún.");
        tv.setTextSize(18);
        tv.setPadding(20, 100, 20, 20);
        layoutResenas.addView(tv);
    }
}
