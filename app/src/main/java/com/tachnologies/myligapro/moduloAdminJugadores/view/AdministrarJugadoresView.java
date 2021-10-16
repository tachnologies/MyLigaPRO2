package com.tachnologies.myligapro.moduloAdminJugadores.view;

import com.tachnologies.myligapro.common.pojo.Equipo;
import com.tachnologies.myligapro.common.pojo.Jugador;

public interface AdministrarJugadoresView {
    void mostrarError(int resMsg);
    void cargaJugadores(Equipo equipo);
    void agregarJugador(Jugador jugador);
    void actualizarJugador(Jugador jugador);
    void eliminarJugador(Jugador jugador);
}
