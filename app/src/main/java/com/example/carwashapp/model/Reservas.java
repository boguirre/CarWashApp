package com.example.carwashapp.model;

public class Reservas {

    private int codcit;
    private String nombreservicio;
    private String fecha;
    private String hora;
    private String precio;
    private String estado;
    private String vehiculo;
    private int codcli;

    public Reservas() {
    }

    public Reservas(int codcit, String nombreservicio, String fecha, String hora, String precio, String estado, String vehiculo, int codcli) {
        this.codcit = codcit;
        this.nombreservicio = nombreservicio;
        this.fecha = fecha;
        this.hora = hora;
        this.precio = precio;
        this.estado = estado;
        this.vehiculo = vehiculo;
        this.codcli = codcli;
    }

    public int getCodcli() {
        return codcli;
    }

    public void setCodcli(int codcli) {
        this.codcli = codcli;
    }

    public int getCodcit() {
        return codcit;
    }

    public void setCodcit(int codcit) {
        this.codcit = codcit;
    }

    public String getNombreservicio() {
        return nombreservicio;
    }

    public void setNombreservicio(String nombreservicio) {
        this.nombreservicio = nombreservicio;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(String vehiculo) {
        this.vehiculo = vehiculo;
    }
}
