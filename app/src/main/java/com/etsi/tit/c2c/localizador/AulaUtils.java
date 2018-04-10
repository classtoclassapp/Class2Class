package com.etsi.tit.c2c.localizador;

import java.util.ArrayList;

/**
 * Created by josel on 19/03/2018.
 */

public class AulaUtils {

    //Variables
    static String localizadorOrigen = null;
    static String aulaDestino = null;
    static String direccion = null;
    static boolean ar = true;

    //Getters Setters
    public static String getAulaOrigen() {
        return localizadorOrigen;
    }

    public static void setAulaOrigen(String aulaOrigen) {
        AulaUtils.localizadorOrigen = aulaOrigen;
    }

    public static String getAulaDestino() {
        return aulaDestino;
    }

    public static void setAulaDestino(String aulaDestino) {
        AulaUtils.aulaDestino = aulaDestino;
    }

    public static void setDireccion(String dir) {
        AulaUtils.aulaDestino=dir;
    }

    public static String getDireccion() {
        return direccion;
    }

    public static boolean getAr() {
        return ar;
    }

    public static void setAr(boolean ar) {
        AulaUtils.ar = ar;
    }
/**************************************************************************************************/

    //Funciones
    public static boolean compruebaAula(String aula) {

        //Array Aulas (Meter los aulas)
        ArrayList<Aula> listaAulas = new ArrayList<Aula>();
        listaAulas.add(new Aula("P1G6A",43.262379,-2.948441));
        listaAulas.add(new Aula("P2G6A",43.262379,-2.948441));
        listaAulas.add(new Aula("P1B1A",43.262013,-2.949798));
        listaAulas.add(new Aula("P1A9A",43.262604,-2.948357));
        listaAulas.add(new Aula("CAFETERIA", 43.262514, -2.948939));

        //Comprobar
        boolean encontrado = false;
        int i =0;
        do {
            if(aula.equalsIgnoreCase(listaAulas.get(i).getAula())) {
                encontrado=true;
            }
            else {
                i++;
            }
        }
        while(i<listaAulas.size()&&encontrado==false);
        return encontrado;
    }

    public static boolean compruebaLocalizador(String origen) {

        //Array Aulas (Meter los localizadores)
        ArrayList<Aula> listaLocalizadores = new ArrayList<Aula>();
        listaLocalizadores.add(new Aula("localizador11",43.262589,-2.948364));
        listaLocalizadores.add(new Aula("localizador12",43.262062,-2.948694));
        listaLocalizadores.add(new Aula("localizador13",43.262392,-2.949205));
        listaLocalizadores.add(new Aula("localizador14",43.262716,-2.948776));

        //Comprobar
        boolean encontrado = false;
        int i =0;
        do {
            if(origen.equalsIgnoreCase(listaLocalizadores.get(i).getAula())) {
                encontrado=true;
            }
            else {
                i++;
            }
        }
        while(i<listaLocalizadores.size()&&encontrado==false);
        return encontrado;
    }

    private static boolean comprobarContiguos(Aula localizadorO, Aula localizadorD) {
        boolean comprobacion = false;
        char codOrigen = localizadorO.getAula().charAt(12);
        char codDestino = localizadorD.getAula().charAt(12);
        int numOrigen = Character.getNumericValue(codOrigen);
        int numDestino = Character.getNumericValue(codDestino);

        if (numDestino == numOrigen +1 || numDestino  == numOrigen -1){ //Caso normal
            comprobacion = true;
        }
        else if ((numOrigen==4 && numDestino==1) || numDestino==4 && numOrigen==1) {
            comprobacion = true;
        }
        return comprobacion;
    }

    public static void devuelveDirecciónAR () {
        //Localizadores
        ArrayList<Aula> listaLocalizadores = new ArrayList<Aula>();
        listaLocalizadores.add(new Aula("localizador11",43.262589,-2.948364));
        listaLocalizadores.add(new Aula("localizador12",43.262062,-2.948694));
        listaLocalizadores.add(new Aula("localizador13",43.262392,-2.949205));
        listaLocalizadores.add(new Aula("localizador14",43.262716,-2.948776));

        //Aulas
        ArrayList<Aula> listaAulas = new ArrayList<Aula>();
        listaAulas.add(new Aula("P1G6A",43.262379,-2.948441));
        listaAulas.add(new Aula("P2G6A",43.262379,-2.948441));
        listaAulas.add(new Aula("P1B1A",43.262013,-2.949798));
        listaAulas.add(new Aula("P1A9A",43.262604,-2.948357));
        listaAulas.add(new Aula("CAFETERIA", 43.262514, -2.948939));

        //Devolvemos localizador origen
        int i = 0, encontrado = 0;
        Aula origenL = null;
        while (i<listaLocalizadores.size() && encontrado !=1) {
            if(listaLocalizadores.get(i).getAula().equalsIgnoreCase(localizadorOrigen)) {
                origenL = listaLocalizadores.get(i);
                encontrado++;
            }
            i++;
        }

        //Obtener aula destino
        i=0; encontrado=0;
        Aula destinoA = null, destinoL = null;
        while(i<listaAulas.size() && encontrado !=1) {
            if(listaAulas.get(i).getAula().equalsIgnoreCase(aulaDestino)) {
                destinoA = listaAulas.get(i);
                encontrado++;
            }
            i++;
        }

        //Comprobamos la diferencia de pisos
        if (aulaDestino.equalsIgnoreCase("CAFETERIA")) {
            direccion = "Mapas";
            ar = false;
        }
        else {
            if(aulaDestino.toCharArray()[1]==localizadorOrigen.toCharArray()[11]) { //Mismo
                double latD = destinoA.getLatitud();
                double lonD = destinoA.getLongitud();

                ArrayList<Double> distancias = new ArrayList<Double>();
                for(int index = 0; index < listaLocalizadores.size(); index++) {
                    double latO = listaLocalizadores.get(index).getLatitud();
                    double lonO = listaLocalizadores.get(index).getLongitud();

                    double difLat = Math.abs(latD - latO);
                    double difLon = Math.abs(lonD - lonO);

                    double distancia = Math.sqrt(Math.pow(difLat,2)+Math.pow(difLon,2));
                    distancias.add(distancia);
                }

                i=0;
                double distFinal = 0;
                int indiceFinal = 0;
                while(i<distancias.size()) {
                    if (i==0) {
                        distFinal=distancias.get(i);
                    }
                    else{
                        if(distFinal > distancias.get(i)) {
                            distFinal = distancias.get(i);
                            indiceFinal = i;
                        }
                    }
                    i++;
                }
                destinoL = listaLocalizadores.get(indiceFinal);
                boolean comp = comprobarContiguos(origenL, destinoL);

                if (destinoL.getAula().charAt(12) == origenL.getAula().charAt(12)) {
                    direccion = "Mapas";
                    ar = false;
                }
                else {
                    if (comp) {
                        if(destinoL.getAula().charAt(12) > origenL.getAula().charAt(12) ||
                                (destinoL.getAula().charAt(12) == '1' && origenL.getAula().charAt(12) =='4')
                                ) {
                            direccion = "Right";
                        }
                        else if (destinoL.getAula().charAt(12) < origenL.getAula().charAt(12) ||
                                (destinoL.getAula().charAt(12) == '4' && origenL.getAula().charAt(12) =='1')
                                ) {
                            direccion = "Left";
                        }
                    }
                    else {
                        if(origenL.getAula().charAt(12) == '4') {
                            direccion = "Right";
                        }
                        else if (origenL.getAula().charAt(12) == '1') {
                            direccion = "Left";
                        }
                        else if (origenL.getAula().charAt(12) == '2') {
                            direccion = "Left";
                        }
                        else if (origenL.getAula().charAt(12) == '3') {
                            direccion = "Right";
                        }
                    }
                }
            }
            else if (aulaDestino.toCharArray()[1] > localizadorOrigen.toCharArray()[11]){ //Subir
                direccion = "Up";
            }
            else if (aulaDestino.toCharArray()[1] < localizadorOrigen.toCharArray()[11]){ //Bajar
                direccion = "Down";
            }
        }
    }

    public static Aula devuelveAula(String aula) {
        Aula buscado = null;

        ArrayList<Aula> listaLocalizadores = new ArrayList<Aula>();
        listaLocalizadores.add(new Aula("localizador11",43.262589,-2.948364));
        listaLocalizadores.add(new Aula("localizador12",43.262062,-2.948694));
        listaLocalizadores.add(new Aula("localizador13",43.262392,-2.949205));
        listaLocalizadores.add(new Aula("localizador14",43.262716,-2.948776));

        //Aulas
        ArrayList<Aula> listaAulas = new ArrayList<Aula>();
        listaAulas.add(new Aula("P1G6A",43.262379,-2.948441));
        listaAulas.add(new Aula("P2G6A",43.262379,-2.948441));
        listaAulas.add(new Aula("P1B1A",43.262013,-2.949798));
        listaAulas.add(new Aula("P1A9A",43.262604,-2.948357));
        listaAulas.add(new Aula("CAFETERIA", 43.262514, -2.948939));

        int encontrado = 0;
        int i = 0;
        while(i < listaLocalizadores.size() && encontrado !=1) {
            if(listaLocalizadores.get(i).getAula().equalsIgnoreCase(aula))
            {
                buscado = listaLocalizadores.get(i);
                encontrado = 1;
            }
            i++;
        }
        encontrado = 0;
        i = 0;
        while(i < listaAulas.size() && encontrado !=1) {
            if(listaAulas.get(i).getAula().equalsIgnoreCase(aula))
            {
                buscado = listaAulas.get(i);
                encontrado = 1;
            }
            i++;
        }
        return buscado;
    }


}
