package com.tachnologies.myligapro.common.pojo;

public class GolesPartido {

    private String uidEquipo;
    private String uidJugador;
    private String nombreJugador;
    private String nombreEquipo;
    private int cantidad;

    public GolesPartido(String uidEquipo, String uidJugador,String nombreJugador, String nombreEquipo,
                        int cantidad) {
        this.uidEquipo = uidEquipo;
        this.uidJugador = uidJugador;
        this.nombreJugador = nombreJugador;
        this.nombreEquipo = nombreEquipo;
        this.cantidad = cantidad;
    }

    public String getUidEquipo() {
        return uidEquipo;
    }
    public void setUidEquipo(String uidEquipo) {
        this.uidEquipo = uidEquipo;
    }
    public String getUidJugador() {
        return uidJugador;
    }
    public void setUidJugador(String uidJugador) {
        this.uidJugador = uidJugador;
    }
    public String getNombreJugador() {
        return nombreJugador;
    }
    public void setNombreJugador(String nombreJugador) {
        this.nombreJugador = nombreJugador;
    }
    public String getNombreEquipo() {
        return nombreEquipo;
    }
    public void setNombreEquipo(String nombreEquipo) {
        this.nombreEquipo = nombreEquipo;
    }
    public int getCantidad() {
        return cantidad;
    }
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
