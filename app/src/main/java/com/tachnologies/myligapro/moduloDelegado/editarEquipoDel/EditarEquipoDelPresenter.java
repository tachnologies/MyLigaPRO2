package com.tachnologies.myligapro.moduloDelegado.editarEquipoDel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.tachnologies.myligapro.common.event.EditarEquipoEvent;
import com.tachnologies.myligapro.common.pojo.Equipo;

public interface EditarEquipoDelPresenter {
    void consultarEquipo(String uidEquipo);
    void onShow();
    void onDestroy();
    void subirEscudoEquipo(Uri uriFoto, String uidEquipo);
    void guardarEquipo(Equipo equipo);
    void eliminarFotoEquipo(String uidEquipo);
    void checarPermisos(String permissionStr, int requestPermission, Context context, Activity activity);
    void resultadoActivity(int requestCode,int resultCode, Intent data);
    void onEventListener(EditarEquipoEvent evento);
    void actualizarDelegado(String nombreEquipo, String urlLogoEquipo, String uidEquipo);

}
