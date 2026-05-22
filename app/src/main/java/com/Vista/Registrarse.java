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

import com.Controlador.ControladorUsuario;
import com.Modelo.pafilm_final.Usuario;
import com.equipo.pafilm_final.R;

public class Registrarse extends AppCompatActivity {
    EditText etCorreoRegis;
    EditText etContrasenaRegis;
    Button btnRegistrar;
    ImageView ivLogo;
    ControladorUsuario controladorUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.pantalla_registrarse);

        controladorUsuario = new ControladorUsuario();

        etCorreoRegis = findViewById(R.id.et_CorreoRegis);
        etContrasenaRegis = findViewById(R.id.et_ContrasenaRegis);
        btnRegistrar = findViewById(R.id.btn_Registrar);
        ivLogo = findViewById(R.id.imageView);

        // Pulsar el botón registrarse lleva a la pantalla de contenido
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = etCorreoRegis.getText().toString();
                String pass = etContrasenaRegis.getText().toString();

                if (nombre.isEmpty() || pass.isEmpty()) {
                    Toast.makeText(Registrarse.this, "Por favor, rellene todos los campos", Toast.LENGTH_SHORT).show();
                    return;
                }

                int id = controladorUsuario.nuevoId(Registrarse.this);
                Usuario nuevoUsuario = new Usuario(nombre, id, pass);
                controladorUsuario.registrarUsuario(Registrarse.this, nuevoUsuario);

                Toast.makeText(Registrarse.this, "Registro completado", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Registrarse.this, Selector.class);
                intent.putExtra("idUsuario", nuevoUsuario.getIdUsuario());
                startActivity(intent);
                finish();
            }
        });

        // pulsar la imagen hace que vuelvas al inicio de sesión
        ivLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Registrarse.this, IniSesion.class);
                startActivity(intent);
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
