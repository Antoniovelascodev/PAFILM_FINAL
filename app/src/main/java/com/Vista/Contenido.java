package com.Vista;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.equipo.pafilm_final.R;

public class Contenido extends AppCompatActivity {
    EditText etTitulo;
    EditText etPuntuacion;
    CheckBox cbPelicula;
    CheckBox cbSerie;
    CheckBox cbSpoiler;
    EditText etResena;
    ImageView ivLogo;
    Button btnPublicar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.pantalla_contenido);

        etTitulo = findViewById(R.id.et_Titulo);
        etPuntuacion = findViewById(R.id.et_Puntuacion);
        cbPelicula = findViewById(R.id.cb_Pelicula);
        cbSerie = findViewById(R.id.cb_Serie);
        cbSpoiler = findViewById(R.id.cb_Spoiler);
        etResena = findViewById(R.id.et_Resena);
        ivLogo = findViewById(R.id.imageView7);
        btnPublicar = findViewById(R.id.btn_Publicar);

        //pulsar una imagen hace que vuelvas al inicio de sesión
        ivLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Contenido.this, IniSesion.class);
                startActivity(intent);
            }
        });

        // con esto no puedes marcar pelicula o serie a la vez
        cbPelicula.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cbSerie.setChecked(false);
                }
            }
        });

        cbSerie.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cbPelicula.setChecked(false);
                }
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), new androidx.core.view.OnApplyWindowInsetsListener() {
            @Override
            public WindowInsetsCompat onApplyWindowInsets(View v, WindowInsetsCompat insets) {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            }
        });
    }
}
