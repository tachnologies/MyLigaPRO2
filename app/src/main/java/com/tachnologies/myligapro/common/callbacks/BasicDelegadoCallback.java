package com.tachnologies.myligapro.common.callbacks;

import com.tachnologies.myligapro.common.pojo.UsuarioDelegado;

public interface BasicDelegadoCallback {
    void onError(int typeEvent, int resMsg);
    void delegadoExistente(int typeEvent, UsuarioDelegado delegado);
}
