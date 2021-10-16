package com.tachnologies.myligapro.moduloAdm.nuevoJugador.model;

import android.net.Uri;
import com.tachnologies.myligapro.common.pojo.Jugador;

public interface NuevoJugadorAdmInteractor {
    void guardarJugador(Jugador jugador);
    void eliminarJugador(String uidJugador);
    void eliminarFotoJugador(String urlFotoJugador);
    void subirFotoJugador(Uri uriFoto, String equipoId, String jugadorId);
}
