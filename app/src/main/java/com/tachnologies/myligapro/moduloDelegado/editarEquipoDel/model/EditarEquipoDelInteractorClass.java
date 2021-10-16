package com.tachnologies.myligapro.moduloDelegado.editarEquipoDel.model;

import android.net.Uri;

import com.tachnologies.myligapro.common.callbacks.BasicDelegadoCallback;
import com.tachnologies.myligapro.common.callbacks.BasicErrorEventCallback;
import com.tachnologies.myligapro.common.event.EditarEquipoEvent;
import com.tachnologies.myligapro.common.model.StorageUploadImageCallback;
import com.tachnologies.myligapro.common.model.dataSession.DelSession;
import com.tachnologies.myligapro.common.pojo.Equipo;
import com.tachnologies.myligapro.common.pojo.UsuarioDelegado;
import com.tachnologies.myligapro.common.utils.Constantes;
import com.tachnologies.myligapro.moduloAdm.equiposAdm.events.BuscarEquipoListener;
import com.tachnologies.myligapro.moduloDelegado.editarEquipoDel.model.dataAccess.RealtimeDatabase;
import com.tachnologies.myligapro.moduloDelegado.editarEquipoDel.model.dataAccess.Storage;

import org.greenrobot.eventbus.EventBus;

public class EditarEquipoDelInteractorClass implements EditarEquipoDelInteractor{
    private RealtimeDatabase mDatabase;
    private Storage mStorage;

    public EditarEquipoDelInteractorClass(){
        mDatabase = new RealtimeDatabase();
        mStorage = new Storage();
    }

    /** ---------------------------------- metodos de la interfaz EditarEquipoDelInteractor*/
    @Override
    public void consultarEquipo(String uidEquipo) {
        DelSession mSession = DelSession.getInstance();

        mDatabase.buscarEquipo(uidEquipo, mSession.getIdCuentaSel(), mSession.getIdCanchaSel(),
            mSession.getIdLigaSel(), new BuscarEquipoListener() {
                @Override
                public void onError(int typeEvent, int resMsg) {
                    post(typeEvent, resMsg);
                }

                @Override
                public void equipoExiste(int typeEvent, int resMsg, Equipo equipo) {
                    post(typeEvent, 0, equipo);
                }
        });
    }

    @Override
    public void subirEscudoEquipo(Uri uriFoto, String uidEquipo) {
        if(uriFoto != null && !uriFoto.toString().isEmpty()){

            mStorage.subirEscudoEquipo(uriFoto, uidEquipo, new StorageUploadImageCallback() {
                @Override
                public void onSuccess(Uri uri) {
                    Equipo equipo = new Equipo();
                    equipo.setUrlFoto(uri.toString());
                    post(Constantes.ALTA_ESCUDO_EQUIPO_EXITOSA, 0 , equipo);
                }

                @Override
                public void onError(int resMsg) {
                    post(Constantes.ERROR_SUBIR_IMAGEN, resMsg);
                }
            });
        }
    }

    @Override
    public void eliminarFotoEquipo(String uidEquipo) {
        mStorage.eliminarFotoEquipo(uidEquipo);
    }

    @Override
    public void guardarEquipo(Equipo equipo) {
        mDatabase.guardarEquipo(equipo, new BasicErrorEventCallback() {
            @Override
            public void onSuccess() {
                post(Constantes.EQUIPO_GUARDADO);
            }

            @Override
            public void onError(int typeEvent, int resMsg) {
                post(typeEvent, resMsg);
            }
        });
    }

    @Override
    public void buscarDelegado(String uidDelegado, BasicDelegadoCallback callback){
        mDatabase.buscarDelegado(uidDelegado, callback);
    }

    @Override
    public void guardarDelegado(UsuarioDelegado delegado){
        mDatabase.guardarDelegado(delegado, new BasicErrorEventCallback() {
            @Override
            public void onSuccess() {
                post(Constantes.DELEGADO_GUARDADO_EXITO);
            }

            @Override
            public void onError(int typeEvent, int resMsg) {
                post(typeEvent, resMsg);
            }
        });
    }
    /** ------------------------- posts*/
    private void post(int typeEvent){
        post(typeEvent, 0, null);
    }

    private void post(int typeEvent, int resMsg){
        post(typeEvent, resMsg, null);
    }

    private void post(int typeEvent, int resMsg, Equipo equipo){
        EditarEquipoEvent evento = new EditarEquipoEvent();
        evento.setTypeEvent(typeEvent);
        evento.setResMsg(resMsg);
        evento.setEquipo(equipo);
        EventBus.getDefault().post(evento);
    }
}
