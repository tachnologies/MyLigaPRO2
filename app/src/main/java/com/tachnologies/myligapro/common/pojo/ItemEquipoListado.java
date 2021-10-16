package com.tachnologies.myligapro.common.pojo;

public class ItemEquipoListado {
    String uidEquipo;
    String nombreEquipo;
    String urlFotoEquipo;

    public ItemEquipoListado() {
    }

    public ItemEquipoListado(String uidEquipo, String nombreEquipo, String urlFotoEquipo) {
        this.uidEquipo = uidEquipo;
        this.nombreEquipo = nombreEquipo;
        this.urlFotoEquipo = urlFotoEquipo;
    }

    public String getUidEquipo() {
        return uidEquipo;
    }

    public void setUidEquipo(String uidEquipo) {
        this.uidEquipo = uidEquipo;
    }

    public String getNombreEquipo() {
        return nombreEquipo;
    }

    public void setNombreEquipo(String nombreEquipo) {
        this.nombreEquipo = nombreEquipo;
    }

    public String getUrlFotoEquipo() {
        return urlFotoEquipo;
    }

    public void setUrlFotoEquipo(String urlFotoEquipo) {
        this.urlFotoEquipo = urlFotoEquipo;
    }
}
