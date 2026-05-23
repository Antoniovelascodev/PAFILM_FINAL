package com.Vista;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.Controlador.ControladorResena;
import com.Modelo.pafilm_final.Resena;
import com.equipo.pafilm_final.R;

import java.util.List;

public class Resenas extends AppCompatActivity {

    LinearLayout layoutResenas;
    ImageView ivLogo;
    ControladorResena controladorResena;
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

        layoutResenas = findViewById(R.id.layout_Resenas);
        ivLogo = findViewById(R.id.imageView3);

        // Pulsar el logo vuelve a la pantalla de selección
        ivLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Resenas.this, Selector.class);
                intent.putExtra("idUsuario", idUsuario);
                startActivity(intent);
            }
        });

        cargarResenas();
    }

    private void cargarResenas() {
        List<Resena> lista = controladorResena.obtenerTodas(this);

        if (lista == null || lista.isEmpty()) {
            // Si no hay reseñas, mostrar un mensaje
            TextView tvVacio = new TextView(this);
            tvVacio.setText("No hay reseñas todavía");
            tvVacio.setTextSize(16);
            tvVacio.setPadding(16, 32, 16, 16);
            layoutResenas.addView(tvVacio);
            return;
        }

        // Recorrer la lista y crear una "tarjeta" de texto por cada reseña
        for (Resena r : lista) {

            // Contenedor de cada reseña (fondo gris claro con margen)
            LinearLayout tarjeta = new LinearLayout(this);
            tarjeta.setOrientation(LinearLayout.VERTICAL);
            tarjeta.setBackgroundColor(Color.parseColor("#F0F0F0"));
            LinearLayout.LayoutParams paramsContenedor = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            paramsContenedor.setMargins(8, 8, 8, 8);
            tarjeta.setLayoutParams(paramsContenedor);
            tarjeta.setPadding(16, 16, 16, 16);

            // Título de la reseña en negrita
            TextView tvTitulo = new TextView(this);
            tvTitulo.setText(r.getTitulo());
            tvTitulo.setTextSize(18);
            tvTitulo.setTypeface(null, Typeface.BOLD);
            tarjeta.addView(tvTitulo);

            // Nota numérica
            TextView tvNota = new TextView(this);
            tvNota.setText("Nota: " + r.getNota() + " / 10");
            tvNota.setTextSize(14);
            tarjeta.addView(tvNota);

            // Aviso de spoiler (solo si tiene)
            if (r.isSpoiler()) {
                TextView tvSpoiler = new TextView(this);
                tvSpoiler.setText("⚠️ Contiene spoilers");
                tvSpoiler.setTextSize(13);
                tvSpoiler.setTextColor(Color.parseColor("#B71C1C")); // rojo oscuro
                tarjeta.addView(tvSpoiler);
            }

            // Comentario de la reseña
            TextView tvComentario = new TextView(this);
            tvComentario.setText(r.getComentario());
            tvComentario.setTextSize(14);
            tvComentario.setPadding(0, 8, 0, 0);
            tarjeta.addView(tvComentario);

            // Añadir la tarjeta al ScrollView
            layoutResenas.addView(tarjeta);
        }
    }
}