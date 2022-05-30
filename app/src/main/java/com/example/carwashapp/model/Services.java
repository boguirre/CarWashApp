package com.example.carwashapp.model;

public class Services {

    private int idservice;
    private String name;
    private Double price;

    public Services() {
    }

    public Services(int idservice, String name, Double price) {
        this.idservice = idservice;
        this.name = name;
        this.price = price;
    }

    public int getIdservice() {
        return idservice;
    }

    public void setIdservice(int idservice) {
        this.idservice = idservice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
