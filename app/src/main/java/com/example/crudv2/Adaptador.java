package com.example.crudv2;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Adaptador extends BaseAdapter {
    ArrayList<Usuario> lista;
    UsuariosDAO dao;
    Usuario u;
    Activity a;
    int id= 0;

    public Adaptador(Activity a, ArrayList<Usuario> lista, UsuariosDAO dao) {
        this.lista = lista;
        this.a = a;
        this.dao = dao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Usuario getItem(int i) {
        u = lista.get(i);
        return u;
    }

    @Override
    public long getItemId(int i) {
        u = lista.get(i);
        return u.getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        View v = view;
        if (v == null) {
            LayoutInflater li = (LayoutInflater) a.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v= li.inflate(R.layout.item, null);
        }
        u = lista.get(i);
        TextView nombre = (TextView) v.findViewById(R.id.t_nombre);
        TextView contraseña = (TextView) v.findViewById(R.id.t_contraseña);
        TextView email = (TextView) v.findViewById(R.id.t_email);
        TextView telefono = (TextView) v.findViewById(R.id.t_telefono);
        Button editar = (Button)  v.findViewById(R.id.editar);
        Button eliminar = (Button)  v.findViewById(R.id.eliminar);
        nombre.setText(u.getNombre());
        contraseña.setText(u.getContraseña());
        email.setText(u.getEmail());
        telefono.setText(u.getTelefono());
        editar.setTag(i);
        eliminar.setTag(i);
        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Dialog de editar dialog.xml
                int pos = Integer.parseInt(v.getTag().toString());
                final Dialog dialogo = new Dialog(a);
                dialogo.setTitle("Editar registro");
                dialogo.setCancelable(true);
                dialogo.setContentView(R.layout.dialog);
                dialogo.show();
                final EditText nombre = (EditText) dialogo.findViewById(R.id.nombre);
                final EditText contraseña = (EditText) dialogo.findViewById(R.id.contraseña);
                final EditText email = (EditText) dialogo.findViewById(R.id.email);
                final EditText telefono = (EditText) dialogo.findViewById(R.id.telefono);
                Button guardar = (Button) dialogo.findViewById(R.id.d_agregar);
                guardar.setText("Guardar");
                Button cancelar = (Button) dialogo.findViewById(R.id.d_cancelar);
                u = lista.get(pos);
                setId(u.getId());
                nombre.setText(u.getNombre());
                contraseña.setText(u.getContraseña());
                email.setText(u.getEmail());
                telefono.setText(u.getTelefono());

                guardar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            u = new Usuario(getId(), nombre.getText().toString(), contraseña.getText().toString(), email.getText().toString(), telefono.getText().toString());
                            dao.editar(u);
                            lista = dao.verTodos();
                            notifyDataSetChanged();
                            dialogo.dismiss();
                        } catch (Exception e) {
                            Toast.makeText(a, "ERROR", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                cancelar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogo.dismiss();
                    }
                });

            }
        });
        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Dialogo para confirmar SI/NO
                int pos = Integer.parseInt(v.getTag().toString());
                u = lista.get(pos);
                setId(u.getId());
                AlertDialog.Builder del = new AlertDialog.Builder(a);
                del.setMessage("Estas seguro de eliminar el usuario?");
                del.setCancelable(false);
                del.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dao.eliminar(getId());
                        lista = dao.verTodos();
                        notifyDataSetChanged();
                    }
                });
                del.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                del.show();
            }
        });

        return v;
    }
}
