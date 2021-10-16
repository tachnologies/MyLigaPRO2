package com.tachnologies.myligapro.common.pojo;

import java.util.List;

public class Partido {
    private String equipo1;     //nombreEquipo
    private String uidEquipo1;
    private int golesEquipo1;
    private String urlEquipo1;
    private String equipo2;
    private String uidEquipo2;
    private int golesEquipo2;
    private String urlEquipo2;
    private String ganadorPuntoExtra;   //es el uid del ganador
    private String fecha;
    private String hora;
    private List<GolesPartido> goles;

    //aqui deberia agregarse lo de los fanPoints

    //al inicializar
    public Partido() {
    }

}
