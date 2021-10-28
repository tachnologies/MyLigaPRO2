package com.tachnologies.myligapro.moduloAdm.agregaEditaLigas;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.tachnologies.myligapro.common.pojo.Liga;
import com.tachnologies.myligapro.moduloAdm.agregaEditaLigas.events.AgregaEditaLigasEvent;

public interface AgregaEditaLigasPresenter {
    void onShow();
    void onDestroy();
    void guardarLiga(Liga liga);
    void eliminarLiga(String uidLiga);
    void eliminarFotoLiga(String urlFotoLiga);
    void subirFotoLiga(String uidLiga, Uri uriFoto);
    void checarPermisos(String permissionStr, int requestPermission, Context context, Activity activity);
    void resultadoActivity(int requestCode,int resultCode, Intent data);
    void onEventListener(AgregaEditaLigasEvent evento);
}
