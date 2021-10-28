package com.tachnologies.myligapro.moduloAdminJugadores.agregarEditar.view;

import android.content.Intent;
import com.tachnologies.myligapro.common.pojo.Jugador;

public interface AgregarEditarJugadorView {
    void bloquearPantalla();
    void desbloquearPantalla();
    void abrirGaleria();
    void setImagen(Intent data);
    void guardarJugador(Jugador jugador);
    void jugadorGuardado(Jugador jugador);
    void jugadorEliminado(Jugador jugador);
    void mostrarError(int resMsg);
}
