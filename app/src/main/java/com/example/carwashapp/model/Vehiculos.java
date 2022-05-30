package com.example.carwashapp.model;

public class Vehiculos {

    private int codveh;
    private String marca;
    private String modelo;
    private int codcli;

    public Vehiculos() {
    }

    public Vehiculos(int codveh, String marca, String modelo, int codcli) {
        this.codveh = codveh;
        this.marca = marca;
        this.modelo = modelo;
        this.codcli = codcli;
    }

    public int getCodveh() {
        return codveh;
    }

    public void setCodveh(int codveh) {
        this.codveh = codveh;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String toString() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getCodcli() {
        return codcli;
    }

    public void setCodcli(int codcli) {
        this.codcli = codcli;
    }
}
