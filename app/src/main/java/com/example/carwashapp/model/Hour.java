package com.example.carwashapp.model;

public class Hour {
    private int idhour;
    private String horaini;
    private String horafin;
    private String estado;

    public Hour() {
    }

    public Hour(int idhour, String horaini, String horafin, String estado) {
        this.idhour = idhour;
        this.horaini = horaini;
        this.horafin = horafin;
        this.estado = estado;
    }

    public int getIdhour() {
        return idhour;
    }

    public void setIdhour(int idhour) {
        this.idhour = idhour;
    }

    public String toString() {
        return horaini;
    }

    public void setHoraini(String horaini) {
        this.horaini = horaini;
    }

    public String getHorafin() {
        return horafin;
    }

    public void setHorafin(String horafin) {
        this.horafin = horafin;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
