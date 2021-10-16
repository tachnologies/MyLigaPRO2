package com.tachnologies.myligapro.common.pojo;

import java.io.Serializable;

public class Ubicacion implements Serializable {
    private String latitud;
    private String longitud;

    public Ubicacion() {
    }

    public String getLatitud() {
        return latitud;
    }
    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }
    public String getLongitud() {
        return longitud;
    }
    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }
}
