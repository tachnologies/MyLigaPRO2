package com.tachnologies.myligapro.moduloAdminJugadores.agregarEditar.model;

import android.net.Uri;
import com.tachnologies.myligapro.common.callbacks.BasicErrorEventCallback;
import com.tachnologies.myligapro.common.model.StorageUploadImageCallback;
import com.tachnologies.myligapro.common.pojo.Jugador;
import com.tachnologies.myligapro.common.utils.Constantes;
import com.tachnologies.myligapro.moduloAdminJugadores.agregarEditar.events.AgregarEditarJugadorEvent;
import com.tachnologies.myligapro.moduloAdminJugadores.agregarEditar.model.dataAccess.RealtimeDatabase;
import com.tachnologies.myligapro.moduloAdminJugadores.agregarEditar.model.dataAccess.Storage;

import org.greenrobot.eventbus.EventBus;

public class AgregarEditarJugadorInteractorClass implements AgregarEditarJugadorInteractor{
    private RealtimeDatabase mDatabase;
    private Storage mStorage;

    public AgregarEditarJugadorInteractorClass(){
        mDatabase = new RealtimeDatabase();
        mStorage = new Storage();
    }

    /**--------------------------------- AgregarEditarJugadorInteractor*/
    @Override
    public void guardarJugador(Jugador jugador, String uidCuenta, String uidCancha, String uidLiga, String uidEquipo) {
        mDatabase.guardarJugador(jugador, uidCuenta, uidCancha, uidLiga, uidEquipo, new BasicErrorEventCallback() {
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
    public void eliminarJugador(String uidCuenta, String uidCancha, String uidLiga, String uidEquipo,
            String uidJugador) {
        mDatabase.eliminarJugador(uidCuenta, uidCancha, uidLiga, uidEquipo, uidJugador,
            new BasicErrorEventCallback() {
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
    public void eliminarFotoJugador(String urlFotoJugador) {
        mStorage.eliminarFotoJugador(urlFotoJugador);
    }

    @Override
    public void subirFotoJugador(String uidCuenta, String uidCancha, String uidLiga, String equipoId,
                String jugadorId, Uri uriFoto) {
        if(uriFoto != null && !uriFoto.toString().isEmpty()){
            mStorage.subirFotoJugador(uidCuenta, uidCancha, uidLiga, equipoId, jugadorId, uriFoto,
                new StorageUploadImageCallback() {
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

    private void post(int typeEvent, String urlFotoJugador){
        Jugador jugador = new Jugador();
        jugador.setUrlFoto(urlFotoJugador);
        post(typeEvent, 0, jugador);
    }

    private void post(int typeEvent, Jugador jugador){
        post(typeEvent, 0, jugador);
    }

    private void post(int typeEvent, int resMsg, Jugador jugador){
        AgregarEditarJugadorEvent evento = new AgregarEditarJugadorEvent();
        evento.setTypeEvent(typeEvent);
        evento.setResMsg(resMsg);
        evento.setJugador(jugador);
        EventBus.getDefault().post(evento);
    }
}
