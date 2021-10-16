package com.tachnologies.myligapro.moduloAdm.equiposAdm;

import com.tachnologies.myligapro.common.pojo.Equipo;
import com.tachnologies.myligapro.moduloAdm.equiposAdm.events.EquiposAdmEvent;

public interface EquiposAdmPresenter {
    void onCreate();
    void onPause();
    void onResume();
    void onDestroy();

    void eliminarEquipo(String uidEquipo);
    void onEventListener(EquiposAdmEvent event);

}
