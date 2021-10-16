package com.tachnologies.myligapro.moduloAdm.nuevoEquipo.model;

import android.net.Uri;

import com.tachnologies.myligapro.common.callbacks.BasicErrorEventCallback;
import com.tachnologies.myligapro.common.model.EventErrorTypeListener;
import com.tachnologies.myligapro.common.model.StorageUploadImageCallback;
import com.tachnologies.myligapro.common.pojo.Equipo;
import com.tachnologies.myligapro.common.pojo.UsuarioAdmin;
import com.tachnologies.myligapro.common.pojo.UsuarioDelegado;
import com.tachnologies.myligapro.common.utils.Constantes;
import com.tachnologies.myligapro.moduloAdm.nuevoEquipo.events.NuevoEquipoEvent;
import com.tachnologies.myligapro.moduloAdm.nuevoEquipo.model.dataAccess.RealtimeDatabase;
import com.tachnologies.myligapro.moduloAdm.nuevoEquipo.model.dataAccess.Storage;
import com.tachnologies.myligapro.moduloLogin.events.LoginEvent;

import org.greenrobot.eventbus.EventBus;

public class NuevoEquipoInteractorClass implements NuevoEquipoInteractor{
    private RealtimeDatabase mDatabase;
    private Storage mStorage;

    public NuevoEquipoInteractorClass(){
        mDatabase = new RealtimeDatabase();
        mStorage = new Storage();
    }

    @Override
    public void subirEscudoEquipo(Uri uriFoto, String equipoId){
        if(uriFoto != null && !uriFoto.toString().isEmpty()){

            mStorage.subirEscudoEquipo(uriFoto, equipoId, new StorageUploadImageCallback() {
                @Override
                public void onSuccess(Uri uri) {
                    post(Constantes.ALTA_ESCUDO_EQUIPO_EXITOSA, equipoId, uri.toString());
                }

                @Override
                public void onError(int resMsg) {
                    post(Constantes.ERROR_SUBIR_IMAGEN, resMsg);
                }
            });
        }
    }

    @Override
    public void guardarEquipo(Equipo equipo) {

        mDatabase.guardarEquipo(equipo, new BasicErrorEventCallback() {
            @Override
            public void onSuccess() {
                post(Constantes.ALTA_EXITOSA);
            }

            @Override
            public void onError(int typeEvent, int resMsg) {
                post(typeEvent, resMsg);
            }
        });
    }

    @Override
    public void guardarDelegado(UsuarioDelegado delegado) {
        mDatabase.verificaExisteUsuario(delegado.getUid(), new EventErrorTypeListener() {
            @Override
            public void onError(int typeEvent, int resMsg) {
                if(LoginEvent.USUARIO_NO_EXISTE == typeEvent){
                    mDatabase.altaDelegado(delegado, new BasicErrorEventCallback() {
                        @Override
                        public void onSuccess() {
                            post(Constantes.ALTA_DELEGADO_EXITOSA);
                        }

                        @Override
                        public void onError(int typeEvent, int resMsg) {
                            post(typeEvent, resMsg);
                        }
                    });
                }
            }

            @Override
            public void usuarioAdminExistente(int typeEvent, int resMsg, UsuarioAdmin admin) {}

            @Override
            public void usuarioDelegadoExistente(int typeEvent, int resMsg, UsuarioDelegado delegado) {
                post(Constantes.DELEGADO_EXISTENTE, delegado);
            }
        });
    }

    @Override
    public void actualizarDelegado(UsuarioDelegado delegado){
        mDatabase.actualizarDelegado(delegado, new BasicErrorEventCallback() {
            @Override
            public void onSuccess() {
                post(Constantes.ACTUALIZACION_EXITOSA);
            }

            @Override
            public void onError(int typeEvent, int resMsg) {
            }
        });
    }

    private void post(int typeEvent, UsuarioDelegado delegado){
        NuevoEquipoEvent evento = new NuevoEquipoEvent();
        evento.setTypeEvent(typeEvent);
        evento.setDelegado(delegado);
        EventBus.getDefault().post(evento);
    }

    private void post(int typeEvent, String idEquipo, String urlEscudoEquipo){
        NuevoEquipoEvent evento = new NuevoEquipoEvent();
        evento.setTypeEvent(typeEvent);
        evento.setIdEquipo(idEquipo);
        evento.setUrlEscudoEquipo(urlEscudoEquipo);
        EventBus.getDefault().post(evento);
    }

    private void post(int typeEvent){
        post(typeEvent, 0);
    }

    private void post(int typeEvent, int resMsg){
        NuevoEquipoEvent evento = new NuevoEquipoEvent();
        evento.setTypeEvent(typeEvent);
        evento.setResMsg(resMsg);
        EventBus.getDefault().post(evento);
    }
}
