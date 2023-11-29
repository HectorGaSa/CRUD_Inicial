package com.example.crudv2;

import android.app.Dialog;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.core.view.WindowCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.crudv2.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    UsuariosDAO dao;
    Usuario u;
    Adaptador adapter;
    ArrayList<Usuario> lista;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_crud);
        dao = new UsuariosDAO(MainActivity.this);
        lista = dao.verTodos();
        adapter = new Adaptador(this, lista, dao);
        ListView list = (ListView) findViewById(R.id.lista);
        Button agregar = (Button)findViewById(R.id.agregar);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Dialog para ver vista previa de registro vista.xml
            }
        });
        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Dialogo de agregar dialog.xml
                final Dialog dialogo = new Dialog(MainActivity.this);
                dialogo.setTitle("Nuevo registro");
                dialogo.setCancelable(true);
                dialogo.setContentView(R.layout.dialog);
                dialogo.show();
                final EditText nombre = (EditText) dialogo.findViewById(R.id.nombre);
                final EditText contraseña = (EditText) dialogo.findViewById(R.id.contraseña);
                final EditText email = (EditText) dialogo.findViewById(R.id.email);
                final EditText telefono = (EditText) dialogo.findViewById(R.id.telefono);
                Button guardar = (Button) dialogo.findViewById(R.id.d_agregar);
                guardar.setText("Agregar");
                Button cancelar = (Button) dialogo.findViewById(R.id.d_cancelar);
                guardar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            u = new Usuario(nombre.getText().toString(), contraseña.getText().toString(), email.getText().toString(), telefono.getText().toString());
                            dao.insertar(u);
                            lista = dao.verTodos();
                            adapter.notifyDataSetChanged();
                            dialogo.dismiss();
                        } catch (Exception e) {
                            Toast.makeText(getApplication(), "ERROR", Toast.LENGTH_SHORT).show();
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
    }
}
