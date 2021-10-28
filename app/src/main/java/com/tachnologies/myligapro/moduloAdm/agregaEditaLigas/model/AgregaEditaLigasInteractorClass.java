package com.tachnologies.myligapro.moduloAdm.agregaEditaLigas.model;

import android.net.Uri;
import com.tachnologies.myligapro.common.callbacks.BasicErrorEventCallback;
import com.tachnologies.myligapro.common.model.StorageUploadImageCallback;
import com.tachnologies.myligapro.common.pojo.Liga;
import com.tachnologies.myligapro.common.utils.Constantes;
import com.tachnologies.myligapro.moduloAdm.agregaEditaLigas.events.AgregaEditaLigasEvent;
import com.tachnologies.myligapro.moduloAdm.agregaEditaLigas.model.dataAccess.RealtimeDatabase;
import com.tachnologies.myligapro.moduloAdm.agregaEditaLigas.model.dataAccess.Storage;

import org.greenrobot.eventbus.EventBus;

public class AgregaEditaLigasInteractorClass implements AgregaEditaLigasInteractor{
    private RealtimeDatabase mDatabase;
    private Storage mStorage;

    public AgregaEditaLigasInteractorClass(){
        mDatabase = new RealtimeDatabase();
        mStorage = new Storage();
    }

    /**--------------------------------- AgregaEditaLigasInteractor*/
    @Override
    public void guardarLiga(Liga liga) {
        mDatabase.guardarLiga(liga, new BasicErrorEventCallback() {
            @Override
            public void onSuccess() {
                post(Constantes.GUARDADO_EXITOSO);
            }

            @Override
            public void onError(int typeEvent, int resMsg) {
                post(Constantes.ERROR_SERVIDOR, resMsg);
            }
        });
    }

    @Override
    public void eliminarLiga(String uidLiga) {
        mDatabase.eliminarLiga(uidLiga, new BasicErrorEventCallback() {
            @Override
            public void onSuccess() {
                post(Constantes.BORADO_EXITOSO);
            }

            @Override
            public void onError(int typeEvent, int resMsg) {
                post(Constantes.ERROR_BORRADO, resMsg);
            }
        });
    }

    @Override
    public void eliminarFotoLiga(String urlFotoLiga) {
        mStorage.eliminarFotoLiga(urlFotoLiga);
    }

    @Override
    public void subirFotoLiga(String uidLiga, Uri uriFoto) {
        if(uriFoto != null && !uriFoto.toString().isEmpty()){
            mStorage.subirFotoLiga(uidLiga, uriFoto, new StorageUploadImageCallback() {
                @Override
                public void onSuccess(Uri uri) {
                    post(Constantes.ALTA_IMAGEN_EXITOSA, uri.toString());
                }

                @Override
                public void onError(int resMsg) {
                    post(Constantes.ERROR_SUBIR_IMAGEN, resMsg);
                }
            });
        }
    }

    private void post(int typeEvent){
        post(typeEvent, 0, "");
    }

    private void post(int typeEvent, int resMsg){
        post(typeEvent, resMsg, "");
    }

    private void post(int typeEvent, String urlFotoLiga){
        post(typeEvent, 0, urlFotoLiga);
    }

    private void post(int typeEvent, int resMsg, String urlFotoLiga){
        AgregaEditaLigasEvent evento = new AgregaEditaLigasEvent();
        evento.setTypeEvent(typeEvent);
        evento.setResMsg(resMsg);
        evento.setUrlFotoLiga(urlFotoLiga);
        EventBus.getDefault().post(evento);
    }

}
