package com.Vista;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.Controlador.ControladorContenido;
import com.Controlador.ControladorResena;
import com.Modelo.pafilm_final.Resena;
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

    ControladorContenido controladorContenido;
    ControladorResena controladorResena;
    int idUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.pantalla_contenido);

        controladorContenido = new ControladorContenido();
        controladorResena = new ControladorResena();

        // Obtener el ID del usuario que ha iniciado sesión
        idUsuario = getIntent().getIntExtra("idUsuario", -1);

        etTitulo = findViewById(R.id.et_Titulo);
        etPuntuacion = findViewById(R.id.et_Puntuacion);
        cbPelicula = findViewById(R.id.cb_Pelicula);
        cbSerie = findViewById(R.id.cb_Serie);
        cbSpoiler = findViewById(R.id.cb_Spoiler);
        etResena = findViewById(R.id.et_Resena);
        ivLogo = findViewById(R.id.imageView7);
        btnPublicar = findViewById(R.id.btn_Publicar);

        // El botón publicar guarda los datos en la base de datos a través de los controladores
        btnPublicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tituloInput = etTitulo.getText().toString();
                String puntuacionStr = etPuntuacion.getText().toString();
                String resenaTexto = etResena.getText().toString();
                boolean esPelicula = cbPelicula.isChecked();
                boolean esSerie = cbSerie.isChecked();
                boolean esSpoiler = cbSpoiler.isChecked();

                if (tituloInput.isEmpty() || puntuacionStr.isEmpty() || resenaTexto.isEmpty()) {
                    Toast.makeText(Contenido.this, "Por favor, rellene los campos obligatorios", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!esPelicula && !esSerie) {
                    Toast.makeText(Contenido.this, "Seleccione si es Película o Serie", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    float puntuacion = Float.parseFloat(puntuacionStr);
                    String tipo = esPelicula ? "Película" : "Serie";

                    // BUSCAR SI EL CONTENIDO YA EXISTE (para hacer la media)
                    com.Modelo.pafilm_final.Contenido existente = controladorContenido.buscarPorTituloExacto(Contenido.this, tituloInput);
                    int idContenido;

                    if (existente != null) {
                        // Si ya existe, usamos su ID para agrupar la reseña
                        idContenido = existente.getIdContenido();
                    } else {
                        // Si no existe, creamos el contenido nuevo
                        idContenido = controladorContenido.nuevoId(Contenido.this);
                        com.Modelo.pafilm_final.Contenido nuevoContenido = new com.Modelo.pafilm_final.Contenido(
                                idContenido, tituloInput, 2024, (double) puntuacion, tipo, esSpoiler);
                        controladorContenido.crearContenido(Contenido.this, nuevoContenido);
                    }

                    // Crea y guarda la reseña asociada al contenido
                    int idResena = controladorResena.nuevoId(Contenido.this);
                    Resena nuevaResena = new Resena(
                            idResena, tituloInput, resenaTexto, puntuacion, idUsuario, idContenido, esSpoiler);
                    controladorResena.crearResena(Contenido.this, nuevaResena);

                    // La media se actualiza automáticamente en el DAO de Resena al llamar a crearResena

                    Toast.makeText(Contenido.this, "Publicado correctamente. La media se ha actualizado.", Toast.LENGTH_SHORT).show();
                    limpiarCampos();

                } catch (NumberFormatException e) {
                    Toast.makeText(Contenido.this, "La puntuación debe ser un número", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Pulsar la imagen hace que vuelvas a la pantalla de selección
        ivLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Contenido.this, Selector.class);
                intent.putExtra("idUsuario", idUsuario);
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

    private void limpiarCampos() {
        etTitulo.setText("");
        etPuntuacion.setText("");
        etResena.setText("");
        cbPelicula.setChecked(false);
        cbSerie.setChecked(false);
        cbSpoiler.setChecked(false);
    }
}
