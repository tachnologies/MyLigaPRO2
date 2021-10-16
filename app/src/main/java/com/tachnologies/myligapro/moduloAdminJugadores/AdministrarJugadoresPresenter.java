package com.tachnologies.myligapro.moduloAdminJugadores;

import com.tachnologies.myligapro.common.event.EditarEquipoEvent;

public interface AdministrarJugadoresPresenter {
    void onCreate();
    void onDestroy();
    void consultarEquipo(String uidEquipo, String uidCuenta, String uidCancha, String uidLiga);
    void eliminarJugador(String uidCuenta, String uidCancha, String uidLiga, String uidEquipo,
                         String uidJugador);
    void eliminarFotoJugador(String urlFotoJugador);
    void onEventListener(EditarEquipoEvent evento);
}
