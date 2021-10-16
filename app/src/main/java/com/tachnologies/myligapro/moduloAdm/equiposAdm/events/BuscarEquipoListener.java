package com.tachnologies.myligapro.moduloAdm.equiposAdm.events;
import com.tachnologies.myligapro.common.pojo.Equipo;

public interface BuscarEquipoListener {
    void onError(int typeEvent, int resMsg);
    void equipoExiste(int typeEvent, int resMsg, Equipo equipo);
}
