package com.tachnologies.myligapro.moduloAdminJugadores.agregarEditar.model;

import android.net.Uri;

import com.tachnologies.myligapro.common.pojo.Jugador;

public interface AgregarEditarJugadorInteractor {
    void guardarJugador(Jugador jugador, String uidCuenta, String uidCancha, String uidLiga,
            String uidEquipo);

    void eliminarJugador(String uidCuenta, String uidCancha, String uidLiga, String uidEquipo,
            String uidJugador);

    void eliminarFotoJugador(String urlFotoJugador);

    void subirFotoJugador(String uidCuenta, String uidCancha, String uidLiga, String equipoId,
            String jugadorId, Uri uriFoto);
}
