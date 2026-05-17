package com.Modelo.pafilm_final;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

public class ResenaDao {

    private static SQLiteDatabase conectar(Context context) {
        DatabaseHelper helper = new DatabaseHelper(context);
        return helper.getWritableDatabase();
    }

    public static void crearResena(Context context, Resena r) {
        SQLiteDatabase db = conectar(context);
        try {
            String sql = "INSERT INTO resenas (idResena, titulo, comentario, nota, idUsuario, idContenido, spoiler) VALUES ("
                    + r.getIdResena() + ", '" 
                    + r.getTitulo() + "', '" 
                    + r.getComentario() + "', " 
                    + r.getNota() + ", " 
                    + r.getIdUsuario() + ", " 
                    + r.getIdContenido() + ", "
                    + (r.isSpoiler() ? 1 : 0) + ")";
            db.execSQL(sql);
            
            // Actualizar nota media automáticamente
            ContenidoDao.actualizarNotaMedia(context, r.getIdContenido());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
    }

    public static List<Resena> obtenerResenas(Context context) {
        List<Resena> lista = new ArrayList<>();
        SQLiteDatabase db = conectar(context);
        try {
            String sql = "SELECT * FROM resenas";
            Cursor rs = db.rawQuery(sql, null);
            while (rs.moveToNext()) {
                Resena r = new Resena();
                r.setIdResena(rs.getInt(0));
                r.setTitulo(rs.getString(1));
                r.setComentario(rs.getString(2));
                r.setNota(rs.getFloat(3));
                r.setIdUsuario(rs.getInt(4));
                r.setIdContenido(rs.getInt(5));
                r.setSpoiler(rs.getInt(6) == 1);
                lista.add(r);
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
        return lista;
    }

    public static List<Resena> obtenerPorUsuario(Context context, int idUsuario) {
        List<Resena> lista = new ArrayList<>();
        SQLiteDatabase db = conectar(context);
        try {
            String sql = "SELECT * FROM resenas WHERE idUsuario = " + idUsuario;
            Cursor rs = db.rawQuery(sql, null);
            while (rs.moveToNext()) {
                Resena r = new Resena();
                r.setIdResena(rs.getInt(0));
                r.setTitulo(rs.getString(1));
                r.setComentario(rs.getString(2));
                r.setNota(rs.getFloat(3));
                r.setIdUsuario(rs.getInt(4));
                r.setIdContenido(rs.getInt(5));
                r.setSpoiler(rs.getInt(6) == 1);
                lista.add(r);
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
        return lista;
    }

    public static List<Resena> obtenerPorContenido(Context context, int idContenido) {
        List<Resena> lista = new ArrayList<>();
        SQLiteDatabase db = conectar(context);
        try {
            String sql = "SELECT * FROM resenas WHERE idContenido = " + idContenido;
            Cursor rs = db.rawQuery(sql, null);
            while (rs.moveToNext()) {
                Resena r = new Resena();
                r.setIdResena(rs.getInt(0));
                r.setTitulo(rs.getString(1));
                r.setComentario(rs.getString(2));
                r.setNota(rs.getFloat(3));
                r.setIdUsuario(rs.getInt(4));
                r.setIdContenido(rs.getInt(5));
                r.setSpoiler(rs.getInt(6) == 1);
                lista.add(r);
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
        return lista;
    }

    public static int nuevoIdResena(Context context) {
        SQLiteDatabase db = conectar(context);
        int id = 1;
        try {
            Cursor rs = db.rawQuery("SELECT MAX(idResena) FROM resenas", null);
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
