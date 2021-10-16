package com.tachnologies.myligapro.moduloAdm.primerIngreso.events;

public class AltasEvent {
    private int typeEvent;
    private int resMsg;
    private String info;

    public AltasEvent() {
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

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
