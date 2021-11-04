package com.tachnologies.myligapro.moduloAdm.agregaEditaLigas;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.tachnologies.myligapro.common.pojo.Liga;
import com.tachnologies.myligapro.common.utils.Constantes;
import com.tachnologies.myligapro.common.utils.Utilidades;
import com.tachnologies.myligapro.moduloAdm.agregaEditaLigas.events.AgregaEditaLigasEvent;
import com.tachnologies.myligapro.moduloAdm.agregaEditaLigas.model.AgregaEditaLigasInteractor;
import com.tachnologies.myligapro.moduloAdm.agregaEditaLigas.model.AgregaEditaLigasInteractorClass;
import com.tachnologies.myligapro.moduloAdm.agregaEditaLigas.view.AgregaEditaLigasView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class AgregaEditaLigasPresenterClass implements AgregaEditaLigasPresenter{
    private AgregaEditaLigasView mView;
    private AgregaEditaLigasInteractor mInteractor;

    public AgregaEditaLigasPresenterClass(AgregaEditaLigasView mView){
        this.mView = mView;
        this.mInteractor = new AgregaEditaLigasInteractorClass();
    }

    @Override
    public void onShow() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        mView = null;
    }

    @Override
    public void guardarLiga(Liga liga) {
        mInteractor.guardarLiga(liga);
    }

    @Override
    public void eliminarLiga(String uidLiga) {
        mInteractor.eliminarLiga(uidLiga);
    }

    @Override
    public void eliminarFotoLiga(String urlFotoLiga) {
        mInteractor.eliminarFotoLiga(urlFotoLiga);
    }

    @Override
    public void subirFotoLiga(String uidLiga, Uri uriFoto) {
        mInteractor.subirFotoLiga(uidLiga, uriFoto);
    }

    @Override
    public void checarPermisos(String permissionStr, int requestPermission, Context context, Activity activity) {
        if(!Utilidades.checarPermisosGaleria(permissionStr, requestPermission, context, activity)){
            return;
        }

        switch(requestPermission) {
            case Constantes.RP_STORAGE:
                mView.abrirGaleria();
                break;
        }
    }

    @Override
    public void resultadoActivity(int requestCode, int resultCode, Intent data) {
        System.out.println("----------------------------------resultadoActivity");
        if(resultCode == Activity.RESULT_OK){
            System.out.println("----------------------------------Activity.RESULT_OK");
            switch(requestCode){
                case Constantes.RC_FOTO_EQUIPO_PICKER:
                    System.out.println("----------------------------------RC_FOTO_EQUIPO_PICKER");
                    mView.setImagen(data);
                    break;
                default:
                    break;
            }
        }else{
            System.out.println("----------------------------------distinto a Activity.RESULT_OK");
            System.out.println("----------------------------------resultCode: " + resultCode);
        }
    }

    /** muy importante!!!! no olvidar el subscribe que si no vale chori*/
    @Subscribe
    @Override
    public void onEventListener(AgregaEditaLigasEvent evento) {
        switch(evento.getTypeEvent()) {
            case Constantes.GUARDADO_EXITOSO:
                mView.ligaGuardada();
                break;
            case Constantes.ALTA_IMAGEN_EXITOSA:
                String urlFotoJugador = evento.getUrlFotoLiga();
                mView.guardarLiga(urlFotoJugador);
                break;
            case Constantes.BORADO_EXITOSO:
                mView.ligaEliminada();
                break;
            case Constantes.ERROR_SUBIR_IMAGEN: case Constantes.ERROR_BORRADO:
                case Constantes.ERROR_SERVIDOR: default:
                mView.mostrarError(evento.getResMsg());
        }
    }

}
