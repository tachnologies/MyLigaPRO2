package com.tachnologies.myligapro.common.model;

import com.tachnologies.myligapro.common.pojo.UsuarioAdmin;
import com.tachnologies.myligapro.common.pojo.UsuarioDelegado;

public interface EventErrorTypeListener {
    void onError(int typeEvent, int resMsg);
    void usuarioAdminExistente(int typeEvent, int resMsg, UsuarioAdmin admin);
    void usuarioDelegadoExistente(int typeEvent, int resMsg, UsuarioDelegado delegado);
}
