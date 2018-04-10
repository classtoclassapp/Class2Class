package com.etsi.tit.c2c.localizador;

/**
 * Created by josel on 19/03/2018.
 */

public class Aula {

    //Variables
    private String aula;
    private double latitud;
    private double longitud;

    //Constructor
    public Aula(String aul, double lat, double longi){
        this.aula=aul;
        this.latitud=lat;
        this.longitud=longi;
    }

    //Getters, Setters

    public String getAula() {
        return aula;
    }

    public double getLatitud() {
        return latitud;
    }

    public double getLongitud() {
        return longitud;
    }
}
