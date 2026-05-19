package com.Vista;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.equipo.pafilm_final.R;

public class IniSesion extends AppCompatActivity {
    EditText etCorreoIniSesion;
    EditText etContrasenaIniSesion;
    Button btnAccederIniSesion;
    Button btnRegistrarIniSesion;
    ImageView ivLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.pantalla_ini_sesion);

        etCorreoIniSesion = findViewById(R.id.et_CorreoIniSesion);
        etContrasenaIniSesion = findViewById(R.id.et_ContrasenaIniSesion);
        btnAccederIniSesion = findViewById(R.id.btn_Acceder);
        btnRegistrarIniSesion = findViewById(R.id.btn_Registrar);
        ivLogo = findViewById(R.id.imageView4);

        // Al pulsar la imagen, volvemos a cargar el inicio de sesión
        ivLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IniSesion.this, IniSesion.class);
                startActivity(intent);
                finish();
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

        // botón Acceder
        btnAccederIniSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String correo = etCorreoIniSesion.getText().toString();
                String pass = etContrasenaIniSesion.getText().toString();

                if (correo.isEmpty() || pass.isEmpty()) {
                    Toast.makeText(IniSesion.this, "Por favor, rellene todos los campos", Toast.LENGTH_SHORT).show();
                } else {
                    // Simulación de inicio de sesión exitoso
                    Toast.makeText(IniSesion.this, "Bienvenido " + correo, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(IniSesion.this, Contenido.class);
                    startActivity(intent);
                }
            }
        });

        // botón Registrarse, lleva a la pantalla de registro
        btnRegistrarIniSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IniSesion.this, Registrarse.class);
                startActivity(intent);
            }
        });
    }
}
