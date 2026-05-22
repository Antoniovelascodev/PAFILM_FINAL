package com.Vista;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.equipo.pafilm_final.R;

public class Selector extends AppCompatActivity {

    Button btnPonerResena;
    Button btnVerResenas;
    int idUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        idUsuario = getIntent().getIntExtra("idUsuario", -1);
        
        if (idUsuario == -1) {
            // Redirigir directamente a la pantalla de Inicio de Sesión si no hay usuario logueado
            Intent intent = new Intent(Selector.this, IniSesion.class);
            startActivity(intent);
            finish();
            return;
        }

        setContentView(R.layout.pantalla_selector);


        btnPonerResena = findViewById(R.id.btn_PonerResena);
        btnVerResenas = findViewById(R.id.btn_VerResena);

        //Pulsar el botón Poner una reseña hace que vamos a la pantalla de Contenido
        btnPonerResena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Selector.this, Contenido.class);
                intent.putExtra("idUsuario", idUsuario);
                startActivity(intent);
            }
        });

    }
}
