package com.tachnologies.myligapro.moduloAdm.nuevoJugador;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.tachnologies.myligapro.common.pojo.Jugador;
import com.tachnologies.myligapro.moduloAdm.nuevoJugador.eventos.NuevoJugadorEvent;

public interface NuevoJugadorAdmPresenter {
    void onShow();
    void onDestroy();
    void subirFotoJugador(Uri uriFoto, String equipoId, String jugadorId);
    void guardarJugador(Jugador jugador);
    void eliminarJugador(String uidJugador);
    void eliminarFotoJugador(String urlFotoJugador);
    void checarPermisos(String permissionStr, int requestPermission, Context context,
                        Activity activity);
    void resultadoActivity(int requestCode,int resultCode, Intent data);
    void onEventListener(NuevoJugadorEvent evento);
}
