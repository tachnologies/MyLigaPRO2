package com.tachnologies.myligapro.moduloLogin.events;

import com.tachnologies.myligapro.common.pojo.UsuarioAdmin;
import com.tachnologies.myligapro.common.pojo.UsuarioDelegado;

public class LoginEvent {
    public static final int AUTH_EXITOSO      = 0;
    public static final int AUTH_NUEVO_EXITOSO= 1;
    public static final int ERROR_SERVER      = 100;
    public static final int ERROR_AUTH        = 101;
    public static final int USUARIO_NO_EXISTE = 102;
    public static final int USUARIO_EXISTE    = 103;

    private UsuarioAdmin admin;
    private UsuarioDelegado delegado;
    private int typeEvent;
    private int resMsg;

    public LoginEvent() {
    }

    public UsuarioAdmin getAdmin() {
        return admin;
    }
    public void setAdmin(UsuarioAdmin admin) {
        this.admin = admin;
    }
    public UsuarioDelegado getDelegado() {
        return delegado;
    }
    public void setDelegado(UsuarioDelegado delegado) {
        this.delegado = delegado;
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
