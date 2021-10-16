package com.tachnologies.myligapro.moduloAdm.equiposAdm.events;

import com.tachnologies.myligapro.common.pojo.Equipo;

public class EquiposAdmEvent {
    private Equipo equipo;
    private int typeEvent;
    private int resMsg;

    public EquiposAdmEvent(){}

    public Equipo getEquipo() {
        return equipo;
    }
    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
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
}
