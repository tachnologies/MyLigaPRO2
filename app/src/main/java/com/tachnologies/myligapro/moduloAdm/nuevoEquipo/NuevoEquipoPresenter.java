package com.tachnologies.myligapro.moduloAdm.nuevoEquipo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.tachnologies.myligapro.common.pojo.Equipo;
import com.tachnologies.myligapro.common.pojo.UsuarioDelegado;
import com.tachnologies.myligapro.moduloAdm.nuevoEquipo.events.NuevoEquipoEvent;

public interface NuevoEquipoPresenter {

    void onShow();
    void onDestroy();
    void subirEscudoEquipo(Uri uriFoto, String equipoId);
    void guardarEquipo(Equipo equipo);
    void guardarDelegado(UsuarioDelegado delegado);
    void checarPermisos(String permissionStr, int requestPermission, Context context,
                        Activity activity);
    void resultadoActivity(int requestCode,int resultCode, Intent data);
    void onEventListener(NuevoEquipoEvent evento);
    void actualizarDelegado(UsuarioDelegado delegado);


}
