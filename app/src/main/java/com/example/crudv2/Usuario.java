package com.example.crudv2;

public class Usuario {
    int id;
    String nombre;
    String contraseña;
    String email;
    String telefono;

    public Usuario() {
    }

    public Usuario(String nombre, String contraseña, String email, String telefono) {
        this.nombre = nombre;
        this.contraseña = contraseña;
        this.email = email;
        this.telefono = telefono;
    }

    public Usuario(int id, String nombre, String contraseña, String email, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.contraseña = contraseña;
        this.email = email;
        this.telefono = telefono;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
