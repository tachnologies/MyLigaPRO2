package com.tachnologies.myligapro.moduloAdm.primerIngreso.model;

import com.tachnologies.myligapro.common.pojo.Cuenta;
import com.tachnologies.myligapro.common.pojo.UsuarioAdmin;

public interface AltasInteractor {
    void altaCuenta(Cuenta cuenta);
    void altaUsuarioAdm(UsuarioAdmin admin);
}
