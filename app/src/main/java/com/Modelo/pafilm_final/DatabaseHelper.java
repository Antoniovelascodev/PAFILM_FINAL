package com.Modelo.pafilm_final;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    // atributos finales del nombre de la base de datos y la version, la version es solo por si hago cambios le tengo que aumentar
    private static final String NOMBRE_BASEDATOS = "pafilm.db";
    private static final int VERSION_BASEDATOS = 1;

    // sentencias de la sql para pasar a los sql
    private static final String CREATE_TABLE_USUARIOS = "CREATE TABLE usuarios (idUsuario INTEGER PRIMARY KEY, nombreUsuario VARCHAR(30), contrasena VARCHAR(10))";
    
    // crea la tabla de contenidos
    private static final String CREATE_TABLE_CONTENIDOS = "CREATE TABLE contenidos (idContenido INTEGER PRIMARY KEY, titulo VARCHAR(80), anio INTEGER, nota FLOAT, tipo VARCHAR(12), spoiler BOOLEAN)";

    // crea la tabla de resenas con la foreing key para referenciar al usuario que la crea con la pelicula en especifico.
    private static final String CREATE_TABLE_RESENAS = 
            "CREATE TABLE resenas (idResena INTEGER PRIMARY KEY, titulo VARCHAR(50), comentario VARCHAR(300), nota FLOAT, idUsuario INTEGER, idContenido INTEGER, spoiler BOOLEAN, " +
            "FOREIGN KEY(idUsuario) REFERENCES usuarios(idUsuario), " +
            "FOREIGN KEY(idContenido) REFERENCES contenidos(idContenido))";

    // es el constructor con los atributos del nombre de la base de datos y la version, pongo null pa que no sobrescriba
    public DatabaseHelper(Context context) {
        super(context, NOMBRE_BASEDATOS, null, VERSION_BASEDATOS);
    }

    // Como se ejecuta cuando no hay nada al principio esto crea las tablas
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USUARIOS);
        db.execSQL(CREATE_TABLE_CONTENIDOS);
        db.execSQL(CREATE_TABLE_RESENAS);
    }

    //Hace que copia y pega en diferentes versiones, si creas una version nueva y llamas al upgrade directamente elimina la antigua y pega la nueva.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS resenas");
        db.execSQL("DROP TABLE IF EXISTS contenidos");
        db.execSQL("DROP TABLE IF EXISTS usuarios");
        onCreate(db);
    }
}
