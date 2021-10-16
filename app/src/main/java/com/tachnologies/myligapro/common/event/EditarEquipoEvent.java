package com.tachnologies.myligapro.common.event;

import com.tachnologies.myligapro.common.pojo.Equipo;

public class EditarEquipoEvent {
    private int typeEvent;
    private int resMsg;
    private Equipo equipo;
    private String datoJugador;

    public EditarEquipoEvent(){}

    public int getTypeEvent() {
        return typeEvent;
    }
    public void setTypeEvent(int typeEvent) {
        this.typeEvent = typeEvent;
    }
    public int getResMsg() {
        return resMsg;
    }
    public void setResMsg(int resMsg) {
        this.resMsg = resMsg;
    }
    public Equipo getEquipo() {
        return equipo;
    }
    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }
    public String getDatoJugador() {
        return datoJugador;
    }
    public void setDatoJugador(String datoJugador) {
        this.datoJugador = datoJugador;
    }
}
