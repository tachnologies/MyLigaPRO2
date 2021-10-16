package com.tachnologies.myligapro.moduloAdm.nuevoJugador.model;

import android.net.Uri;

import com.tachnologies.myligapro.common.callbacks.BasicErrorEventCallback;
import com.tachnologies.myligapro.common.model.StorageUploadImageCallback;
import com.tachnologies.myligapro.common.pojo.Jugador;
import com.tachnologies.myligapro.common.utils.Constantes;
import com.tachnologies.myligapro.moduloAdm.nuevoJugador.eventos.NuevoJugadorEvent;
import com.tachnologies.myligapro.moduloAdm.nuevoJugador.model.dataAccess.RealtimeDatabase;
import com.tachnologies.myligapro.moduloAdm.nuevoJugador.model.dataAccess.Storage;

import org.greenrobot.eventbus.EventBus;

public class NuevoJugadorAdmInteractorClass implements NuevoJugadorAdmInteractor{
    private RealtimeDatabase mDatabase;
    private Storage mStorage;

    public NuevoJugadorAdmInteractorClass(){
        mDatabase = new RealtimeDatabase();
        mStorage = new Storage();
    }


    /** ------------------------------- metodos del NuevoJugadorAdmInteractor*/
    @Override
    public void guardarJugador(Jugador jugador) {
        mDatabase.guardarJugador(jugador, new BasicErrorEventCallback() {
            @Override
            public void onSuccess() {
                post(Constantes.GUARDADO_EXITOSO, jugador);
            }

            @Override
            public void onError(int typeEvent, int resMsg) {
                post(typeEvent, resMsg, null);
            }
        });
    }

    @Override
    public void eliminarFotoJugador(String urlFotoJugador){
        mStorage.eliminarFotoJugador(urlFotoJugador);
    }

    @Override
    public void eliminarJugador(String uidJugador){
        mDatabase.eliminarJugador(uidJugador, new BasicErrorEventCallback() {
            @Override
            public void onSuccess() {
                Jugador jugador = new Jugador();
                jugador.setUid(uidJugador);
                post(Constantes.BORADO_EXITOSO, jugador);
            }

            @Override
            public void onError(int typeEvent, int resMsg) {
                post(typeEvent, resMsg, null);
            }
        });
    }

    @Override
    public void subirFotoJugador(Uri uriFoto, String equipoId, String jugadorId) {
        System.out.println("--------------------------- subirFotoEquipo Interactor");
        if(uriFoto != null && !uriFoto.toString().isEmpty()){
            mStorage.subirFotoJugador(uriFoto, equipoId, jugadorId, new StorageUploadImageCallback() {
                @Override
                public void onSuccess(Uri uri) {
                    post(Constantes.ALTA_IMAGEN_EXITOSA, uri.toString());
                }

                @Override
                public void onError(int resMsg) {
                    post(Constantes.ERROR_SUBIR_IMAGEN, resMsg,null);
                }
            });
        }
    }

    private void post(int typeEvent, String urlEscudoEquipo){
        Jugador jugador = new Jugador();
        jugador.setUrlFoto(urlEscudoEquipo);
        post(typeEvent, 0, jugador);
    }

    private void post(int typeEvent, Jugador jugador){
        post(typeEvent, 0, jugador);
    }

    private void post(int typeEvent, int resMsg, Jugador jugador){
        NuevoJugadorEvent evento = new NuevoJugadorEvent();
        evento.setTypeEvent(typeEvent);
        evento.setResMsg(resMsg);
        evento.setJugador(jugador);
        EventBus.getDefault().post(evento);
    }

}
