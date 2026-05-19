package com.Vista;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Redirigir directamente a la pantalla de Inicio de Sesión
        Intent intent = new Intent(MainActivity.this, IniSesion.class);
        startActivity(intent);
        finish(); // Cerramos MainActivity para que no se quede en la pila
    }
}
