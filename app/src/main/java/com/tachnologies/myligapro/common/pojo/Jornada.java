package com.tachnologies.myligapro.common.pojo;

import java.util.List;

public class Jornada {
    private String uid; //sera del tipo: Jornada1 o solo el # de jornada
    private List<Partido> partidos;

    public Jornada(String uid, List<Partido> partidos) {
        this.uid = uid;
        this.partidos = partidos;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public List<Partido> getPartidos() {
        return partidos;
    }

    public void setPartidos(List<Partido> partidos) {
        this.partidos = partidos;
    }

}
