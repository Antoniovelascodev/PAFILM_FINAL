package com.Modelo.pafilm_final;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDao {

    private static SQLiteDatabase conectar(Context context) {
        DatabaseHelper helper = new DatabaseHelper(context);
        return helper.getWritableDatabase();
    }

    public static void crearUsuario(Context context, Usuario u) { 
        SQLiteDatabase db = conectar(context);
        try {
            String sql = "INSERT INTO usuarios (idUsuario, nombreUsuario, contrasena) VALUES ("
                    + u.getIdUsuario() + ", '" 
                    + u.getNombreUsuario() + "', '" 
                    + u.getContrasena() + "')";
            db.execSQL(sql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
    }

    public static Usuario leerUsuario(Context context, int idUsuario) {
        SQLiteDatabase db = conectar(context);
        Usuario u = null;
        try {
            String sql = "SELECT idUsuario, nombreUsuario, contrasena FROM usuarios WHERE idUsuario = " + idUsuario;
            Cursor rs = db.rawQuery(sql, null);
            if (rs.moveToFirst()) {
                u = new Usuario();
                u.setIdUsuario(rs.getInt(0));
                u.setNombreUsuario(rs.getString(1));
                u.setContrasena(rs.getString(2));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
        return u;
    }

    public static void actualizarUsuario(Context context, Usuario u) {
        SQLiteDatabase db = conectar(context);
        try {
            String sql = "UPDATE usuarios SET nombreUsuario = '" + u.getNombreUsuario() 
                    + "', contrasena = '" + u.getContrasena() 
                    + "' WHERE idUsuario = " + u.getIdUsuario();
            db.execSQL(sql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
    }

    public static void eliminarUsuario(Context context, int idUsuario) {
        SQLiteDatabase db = conectar(context);
        try {
            String sql = "DELETE FROM usuarios WHERE idUsuario = " + idUsuario;
            db.execSQL(sql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
    }

    public static List<Usuario> obtenerUsuarios(Context context) {
        List<Usuario> lista = new ArrayList<>();
        SQLiteDatabase db = conectar(context);
        try {
            String sql = "SELECT * FROM usuarios";
            Cursor rs = db.rawQuery(sql, null);
            while (rs.moveToNext()) {
                Usuario u = new Usuario();
                u.setIdUsuario(rs.getInt(0));
                u.setNombreUsuario(rs.getString(1));
                u.setContrasena(rs.getString(2));
                lista.add(u);
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
        return lista;
    }

    public static Usuario login(Context context, String nombre, String pass) {
        SQLiteDatabase db = conectar(context);
        Usuario u = null;
        try {
            String sql = "SELECT * FROM usuarios WHERE nombreUsuario = '" + nombre + "' AND contrasena = '" + pass + "'";
            Cursor rs = db.rawQuery(sql, null);
            if (rs.moveToFirst()) {
                u = new Usuario();
                u.setIdUsuario(rs.getInt(0));
                u.setNombreUsuario(rs.getString(1));
                u.setContrasena(rs.getString(2));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
        return u;
    }

    public static boolean existeUsuario(Context context, String nombre) {
        SQLiteDatabase db = conectar(context);
        boolean existe = false;
        try {
            String sql = "SELECT * FROM usuarios WHERE nombreUsuario = '" + nombre + "'";
            Cursor rs = db.rawQuery(sql, null);
            existe = rs.getCount() > 0;
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
        return existe;
    }

    public static int nuevoIdUsuario(Context context) {
        SQLiteDatabase db = conectar(context);
        int id = 1;
        try {
            Cursor rs = db.rawQuery("SELECT MAX(idUsuario) FROM usuarios", null);
            if (rs.moveToFirst()) {
                id = rs.getInt(0) + 1;
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
        return id;
    }
}
