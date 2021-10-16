package com.tachnologies.myligapro.moduloAdm.nuevoEquipo.events;

import com.tachnologies.myligapro.common.pojo.UsuarioDelegado;

public class NuevoEquipoEvent {
    private int typeEvent;
    private int resMsg;
    private String urlEscudoEquipo;
    private String idEquipo;
    private UsuarioDelegado delegado;

    public NuevoEquipoEvent() {
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
    public String getUrlEscudoEquipo() {
        return urlEscudoEquipo;
    }
    public void setUrlEscudoEquipo(String urlEscudoEquipo) {
        this.urlEscudoEquipo = urlEscudoEquipo;
    }
    public String getIdEquipo() {
        return idEquipo;
    }
    public void setIdEquipo(String idEquipo) {
        this.idEquipo = idEquipo;
    }
    public UsuarioDelegado getDelegado() {
        return delegado;
    }
    public void setDelegado(UsuarioDelegado delegado) {
        this.delegado = delegado;
    }
}
