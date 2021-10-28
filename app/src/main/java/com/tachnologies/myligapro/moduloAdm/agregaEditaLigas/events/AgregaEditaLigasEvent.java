package com.tachnologies.myligapro.moduloAdm.agregaEditaLigas.events;

public class AgregaEditaLigasEvent {
    private int typeEvent;
    private int resMsg;
    private String urlFotoLiga;

    public AgregaEditaLigasEvent(){}

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
    public String getUrlFotoLiga() {
        return urlFotoLiga;
    }
    public void setUrlFotoLiga(String urlFotoLiga) {
        this.urlFotoLiga = urlFotoLiga;
    }
}