package com.example.crudv2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class UsuariosDAO {
    SQLiteDatabase bd;
    ArrayList<Usuario> lista = new ArrayList<Usuario>();
    Usuario u;
    Context ct;
    String nombreDB = "Bunny_ShoreDB";
    String tablaU = "create table if not exists usuarios(id integer primary key autoincrement, nombre text, contraseña text, email text, telefono text)";

    public UsuariosDAO(Context c) {
        this.ct = c;
        bd = c.openOrCreateDatabase(nombreDB, Context.MODE_PRIVATE, null);
        bd.execSQL(tablaU);
    }

    public boolean insertar(Usuario u) {
        ContentValues contenedor = new ContentValues();
        contenedor.put("nombre", u.getNombre());
        contenedor.put("contraseña", u.getContraseña());
        contenedor.put("email", u.getEmail());
        contenedor.put("telefono", u.getTelefono());
        return (bd.insert("usuarios", null, contenedor)) >0;
    }

    public boolean eliminar(int id) {
        return (bd.delete("usuarios", "id="+id, null))>0;
    }

    public boolean editar(Usuario u) {
        ContentValues contenedor = new ContentValues();
        contenedor.put("nombre", u.getNombre());
        contenedor.put("contraseña", u.getContraseña());
        contenedor.put("email", u.getEmail());
        contenedor.put("telefono", u.getTelefono());
        return (bd.update("usuarios", contenedor, "id="+u.getId(), null))> 0;
    }

    public ArrayList<Usuario> verTodos() {
        lista.clear();
        Cursor cursor = bd.rawQuery("select * from usuarios", null);
        if (cursor != null && cursor.getCount()>0) {
            cursor.moveToFirst();
            do {
                lista.add(new Usuario(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4)));

            } while (cursor.moveToNext());
        }
        return lista;
    }

    public Usuario verUno(int posicion) {
        Cursor cursor = bd.rawQuery("select * from usuarios", null);
        cursor.moveToPosition(posicion);
        u = new Usuario(cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4));
        return u;
    }
}
