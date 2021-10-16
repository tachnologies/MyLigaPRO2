package com.tachnologies.myligapro.moduloAdm.jugadoresAdm.model;

public interface JugadoresAdmInteractor {
    void consultarEquipo(String uidEquipo);
    void eliminarJugador(String uidJugador);
    void eliminarFotoJugador(String urlFotoJugador);
}
