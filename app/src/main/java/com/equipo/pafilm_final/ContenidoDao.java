package com.equipo.pafilm_final;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

public class ContenidoDao {

    private static SQLiteDatabase conectar(Context context) {
        DatabaseHelper helper = new DatabaseHelper(context);
        return helper.getWritableDatabase();
    }

    public static void crearContenido(Context context, Contenido c) {
        SQLiteDatabase db = conectar(context);
        try {
            String sql = "INSERT INTO contenidos (idContenido, titulo, anio, nota, tipo, spoiler) VALUES ("
                    + c.getIdContenido() + ", '" 
                    + c.getTitulo() + "', " 
                    + c.getAnio() + ", " 
                    + c.getNota() + ", '" 
                    + c.getTipo() + "', " 
                    + (c.isSpoiler() ? 1 : 0) + ")";
            db.execSQL(sql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
    }

    public static Contenido leerContenido(Context context, int idContenido) {
        SQLiteDatabase db = conectar(context);
        Contenido c = null;
        try {
            String sql = "SELECT * FROM contenidos WHERE idContenido = " + idContenido;
            Cursor rs = db.rawQuery(sql, null);
            if (rs.moveToFirst()) {
                c = new Contenido();
                c.setIdContenido(rs.getInt(0));
                c.setTitulo(rs.getString(1));
                c.setAnio(rs.getInt(2));
                c.setNota(rs.getDouble(3));
                c.setTipo(rs.getString(4));
                c.setSpoiler(rs.getInt(5) == 1);
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
        return c;
    }

    public static List<Contenido> obtenerContenidos(Context context) {
        List<Contenido> lista = new ArrayList<>();
        SQLiteDatabase db = conectar(context);
        try {
            String sql = "SELECT * FROM contenidos";
            Cursor rs = db.rawQuery(sql, null);
            while (rs.moveToNext()) {
                Contenido c = new Contenido();
                c.setIdContenido(rs.getInt(0));
                c.setTitulo(rs.getString(1));
                c.setAnio(rs.getInt(2));
                c.setNota(rs.getDouble(3));
                c.setTipo(rs.getString(4));
                c.setSpoiler(rs.getInt(5) == 1);
                lista.add(c);
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
        return lista;
    }

    public static List<Contenido> obtenerPorTipo(Context context, String tipo) {
        List<Contenido> lista = new ArrayList<>();
        SQLiteDatabase db = conectar(context);
        try {
            String sql = "SELECT * FROM contenidos WHERE tipo = '" + tipo + "'";
            Cursor rs = db.rawQuery(sql, null);
            while (rs.moveToNext()) {
                Contenido c = new Contenido();
                c.setIdContenido(rs.getInt(0));
                c.setTitulo(rs.getString(1));
                c.setAnio(rs.getInt(2));
                c.setNota(rs.getDouble(3));
                c.setTipo(rs.getString(4));
                c.setSpoiler(rs.getInt(5) == 1);
                lista.add(c);
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
        return lista;
    }

    public static List<Contenido> buscarPorTitulo(Context context, String texto) {
        List<Contenido> lista = new ArrayList<>();
        SQLiteDatabase db = conectar(context);
        try {
            String sql = "SELECT * FROM contenidos WHERE titulo LIKE '%" + texto + "%'";
            Cursor rs = db.rawQuery(sql, null);
            while (rs.moveToNext()) {
                Contenido c = new Contenido();
                c.setIdContenido(rs.getInt(0));
                c.setTitulo(rs.getString(1));
                c.setAnio(rs.getInt(2));
                c.setNota(rs.getDouble(3));
                c.setTipo(rs.getString(4));
                c.setSpoiler(rs.getInt(5) == 1);
                lista.add(c);
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
        return lista;
    }

    public static void actualizarNotaMedia(Context context, int idContenido) {
        List<Resena> resenas = ResenaDao.obtenerPorContenido(context, idContenido);
        if (resenas.isEmpty()) return;

        double suma = 0;
        for (Resena r : resenas) {
            suma += r.getNota();
        }
        double media = suma / resenas.size();

        // actualizo la nota directamente con SQL
        SQLiteDatabase db = conectar(context);
        try {
            String sql = "UPDATE contenidos SET nota = " + media + " WHERE idContenido = " + idContenido;
            db.execSQL(sql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
    }

    public static int nuevoIdContenido(Context context) {
        SQLiteDatabase db = conectar(context);
        int id = 1;
        try {
            Cursor rs = db.rawQuery("SELECT MAX(idContenido) FROM contenidos", null);
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
