package com.tachnologies.myligapro.moduloAdminJugadores.agregarEditar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.tachnologies.myligapro.common.pojo.Jugador;
import com.tachnologies.myligapro.moduloAdminJugadores.agregarEditar.events.AgregarEditarJugadorEvent;

public interface AgregarEditarJugadorPresenter {
    void onShow();
    void onDestroy();
    void subirFotoJugador(String uidCuenta, String uidCancha, String uidLiga, String equipoId,
            String jugadorId, Uri uriFoto);

    void guardarJugador(Jugador jugador, String uidCuenta, String uidCancha, String uidLiga,
            String uidEquipo);

    void eliminarJugador(String uidCuenta, String uidCancha, String uidLiga, String uidEquipo,
            String uidJugador);

    void eliminarFotoJugador(String urlFotoJugador);
    void checarPermisos(String permissionStr, int requestPermission, Context context, Activity activity);
    void resultadoActivity(int requestCode,int resultCode, Intent data);
    void onEventListener(AgregarEditarJugadorEvent evento);
}
