package com.tachnologies.myligapro.moduloAdm.nuevoJugador.eventos;

import com.tachnologies.myligapro.common.pojo.Jugador;

public class NuevoJugadorEvent {
    private int typeEvent;
    private int resMsg;
    private Jugador jugador;
    //private String urlFotoJugador;

    public NuevoJugadorEvent() {
    }

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
    public Jugador getJugador() {
        return jugador;
    }
    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }
}
