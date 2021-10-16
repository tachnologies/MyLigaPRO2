package com.tachnologies.myligapro.moduloAdm.editarEquipo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.tachnologies.myligapro.common.event.EditarEquipoEvent;
import com.tachnologies.myligapro.common.pojo.Equipo;
import com.tachnologies.myligapro.common.pojo.RefEquipoDelegado;

public interface EditarEquipoAdmPresenter {
    void consultarEquipo(String uidEquipo);
    void onShow();
    void onDestroy();
    void subirEscudoEquipo(Uri uriFoto, String uidEquipo);
    void guardarEquipo(Equipo equipo);
    void eliminarFotoEquipo(String uidEquipo);
    void eliminarEquipo(String uidEquipo);
    void checarPermisos(String permissionStr, int requestPermission, Context context,
                        Activity activity);
    void resultadoActivity(int requestCode,int resultCode, Intent data);
    void onEventListener(EditarEquipoEvent evento);

    void actBorrarRefDelegados(String uidEquipo, String uidDelegadoAgregar, RefEquipoDelegado referencia,
                               String lada, String uidDelegadoBorrar);
    void borrarRefDelegado(int respuestaExito, String uidEquipo, String uidDelegado);
    void agregarRefDelegado(int respuestaExito,  String uidDelegadoAgregar, String lada,
                            RefEquipoDelegado referencia);

    void actualizarDelegado(String uidDelegado, String nombreEquipo, String urlLogoEquipo,
                            String uidEquipo);

}
