package com.tachnologies.myligapro.moduloAdm.agregaEditaLigas.view;

import android.content.Intent;

public interface AgregaEditaLigasView {
    void desbloquearPantalla();
    void bloquearPantalla();

    void ligaGuardada();
    void guardarLiga(String urlFotoJugador);
    void ligaEliminada();
    void mostrarError(int resMsg);
    void setImagen(Intent data);
    void abrirGaleria();
}
