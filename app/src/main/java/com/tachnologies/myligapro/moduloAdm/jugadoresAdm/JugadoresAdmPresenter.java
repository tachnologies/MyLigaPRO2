package com.tachnologies.myligapro.moduloAdm.jugadoresAdm;

import com.tachnologies.myligapro.common.event.EditarEquipoEvent;

public interface JugadoresAdmPresenter {
    void consultarEquipo(String uidEquipo);
    void onCreate();
    void onDestroy();
    void eliminarJugador(String uidJugador);
    void eliminarFotoJugador(String urlFotoJugador);

    void onEventListener(EditarEquipoEvent evento);
}
