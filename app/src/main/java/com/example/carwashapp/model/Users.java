package com.example.carwashapp.model;

public class Users {

    private int codcli;
    private  String nombrecli, apellidocli, telefono, numdoc,  email,  password ;

    public Users() {
    }

    public Users(int codcli, String nombrecli, String apellidocli, String telefono, String numdoc, String email, String password) {
        this.codcli = codcli;
        this.nombrecli = nombrecli;
        this.apellidocli = apellidocli;
        this.telefono = telefono;
        this.numdoc = numdoc;
        this.email = email;
        this.password = password;
    }

    public int getCodcli() {
        return codcli;
    }

    public void setCodcli(int codcli) {
        this.codcli = codcli;
    }

    public String getNombrecli() {
        return nombrecli;
    }

    public void setNombrecli(String nombrecli) {
        this.nombrecli = nombrecli;
    }

    public String getApellidocli() {
        return apellidocli;
    }

    public void setApellidocli(String apellidocli) {
        this.apellidocli = apellidocli;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getNumdoc() {
        return numdoc;
    }

    public void setNumdoc(String numdoc) {
        this.numdoc = numdoc;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
