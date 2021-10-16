package com.tachnologies.myligapro.moduloDelegado.jugadoresDel.model;

public interface JugadoresDelInteractor {
    void consultarEquipo(String uidEquipo);
    void eliminarJugador(String uidJugador);
    void eliminarFotoJugador(String urlFotoJugador);
}
