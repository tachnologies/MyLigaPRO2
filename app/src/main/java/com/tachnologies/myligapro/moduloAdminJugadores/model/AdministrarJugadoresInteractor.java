package com.tachnologies.myligapro.moduloAdminJugadores.model;

public interface AdministrarJugadoresInteractor {
    void consultarEquipo(String uidEquipo, String uidCuenta, String uidCancha, String uidLiga);
    void eliminarJugador(String uidCuenta, String uidCancha, String uidLiga, String uidEquipo,
                         String uidJugador);
    void eliminarFotoJugador(String urlFotoJugador);
}
