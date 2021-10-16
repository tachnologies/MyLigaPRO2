package com.tachnologies.myligapro.moduloDelegado.mainDelegado.view;

import com.tachnologies.myligapro.common.pojo.RefEquipoDelegado;

public interface MainDelegadoView {
    void bloquearPantalla();
    void bloquearPantalla(int resMsge);
    void desbloquearPantalla();

    void equipoAgregado(RefEquipoDelegado equipo);
    void equipoActualizado(RefEquipoDelegado equipo);
    void equipoBorrado(RefEquipoDelegado equipo);
    void onShowError(int resMsg);
}
