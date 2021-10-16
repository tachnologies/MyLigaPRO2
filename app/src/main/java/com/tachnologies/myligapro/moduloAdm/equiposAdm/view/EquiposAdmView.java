package com.tachnologies.myligapro.moduloAdm.equiposAdm.view;

import com.tachnologies.myligapro.common.pojo.Equipo;

public interface EquiposAdmView {
    void bloquearPantalla();
    void desbloquearPantalla();

    void equipoAgregado(Equipo equipo);
    void equipoActualizado(Equipo equipo);
    void equipoBorrado(Equipo equipo);
    void onShowError(int resMsg);
}
