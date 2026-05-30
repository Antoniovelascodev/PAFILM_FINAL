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
import com.Modelo.pafilm_final.UsuarioDao;
import com.equipo.pafilm_final.R;

public class Registrarse extends AppCompatActivity {
    EditText etCorreoRegis;
    EditText etContrasenaRegis;
    EditText etContrasenaRegis2;
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
        etContrasenaRegis2 = findViewById(R.id.et_ContrasenaRegis2);
        btnRegistrar = findViewById(R.id.btn_Registrar);
        ivLogo = findViewById(R.id.imageView);

        // Pulsar el botón registrarse lleva a la pantalla contenido
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String correo = etCorreoRegis.getText().toString();
                String pass = etContrasenaRegis.getText().toString();
                String pass2 = etContrasenaRegis2.getText().toString();

                // Comprueba q no estén vacios los campos
                if (correo.isEmpty() || pass.isEmpty() || pass2.isEmpty()) {
                    Toast.makeText(Registrarse.this, "Por favor, rellene todos los campos", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Comprueba que las contraseñas coincidan
                if (!pass.equals(pass2)) {
                    Toast.makeText(Registrarse.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Comprueba si el usuario ya existe
                if (UsuarioDao.existeUsuario(Registrarse.this, correo)) {
                    Toast.makeText(Registrarse.this, "Este correo ya está registrado", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Realiza el registro
                int id = controladorUsuario.nuevoId(Registrarse.this);
                Usuario nuevoUsuario = new Usuario(correo, id, pass);
                controladorUsuario.registrarUsuario(Registrarse.this, nuevoUsuario);

                Toast.makeText(Registrarse.this, "Registro completado", Toast.LENGTH_SHORT).show();
                
                // lleva a la pantalla de selección
                Intent intent = new Intent(Registrarse.this, Selector.class);
                intent.putExtra("idUsuario", nuevoUsuario.getIdUsuario());
                startActivity(intent);
                finish();
            }
        });

        // Pulsar la imagen hace que vuelvas al inicio de sesión
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
