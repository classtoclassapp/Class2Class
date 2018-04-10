package com.etsi.tit.c2c.tablon;

/**
 * Created by josel on 14/03/2018.
 */

public class Anuncio {

    //Atributos
    public String nombre;
    public String email;
    public String telefono;
    public String anuncio;

    //Constructores
    public Anuncio(){
        super();
    }

    public Anuncio(String autor, String email, String telefono, String texto) {
        super();
        this.nombre = autor;
        this.email = email;
        this.telefono = telefono;
        this.anuncio = texto;
    }
}
