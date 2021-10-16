package com.tachnologies.myligapro.moduloAdm.equiposAdm.events;

import com.tachnologies.myligapro.common.pojo.Equipo;

public interface EquiposAdmEventListener {

    void equipoAgregado(Equipo equipo);
    void equipoActualizado(Equipo equipo);
    void equipoEliminado(Equipo equipo);

    void onError(int resMsg);

}
