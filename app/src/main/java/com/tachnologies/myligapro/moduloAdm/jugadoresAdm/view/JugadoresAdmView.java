package com.tachnologies.myligapro.moduloAdm.jugadoresAdm.view;

import com.tachnologies.myligapro.common.pojo.Equipo;
import com.tachnologies.myligapro.common.pojo.Jugador;

public interface JugadoresAdmView {
    void bloquearPantalla(int resMsg);
    void desbloquearPantalla();
    void mostrarError(int resMsg);
    void cargaJugadores(Equipo equipo);
    void agregarJugador(Jugador jugador);
    void actualizarJugador(Jugador jugador);
    void eliminarJugador(Jugador jugador);
    Equipo getEquipo();
}
