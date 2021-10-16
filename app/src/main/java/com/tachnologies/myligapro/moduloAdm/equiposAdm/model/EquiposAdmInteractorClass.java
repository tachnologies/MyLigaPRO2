package com.tachnologies.myligapro.moduloAdm.equiposAdm.model;

import com.tachnologies.myligapro.common.callbacks.BasicErrorEventCallback;
import com.tachnologies.myligapro.common.model.dataSession.AdmSession;
import com.tachnologies.myligapro.common.pojo.Equipo;
import com.tachnologies.myligapro.common.utils.Constantes;
import com.tachnologies.myligapro.moduloAdm.equiposAdm.events.BuscarEquipoListener;
import com.tachnologies.myligapro.moduloAdm.equiposAdm.events.EquiposAdmEvent;
import com.tachnologies.myligapro.moduloAdm.equiposAdm.events.EquiposAdmEventListener;
import com.tachnologies.myligapro.moduloAdm.equiposAdm.model.dataAccess.RealtimeDatabase;
import com.tachnologies.myligapro.moduloAdm.equiposAdm.model.dataAccess.Storage;

import org.greenrobot.eventbus.EventBus;

public class EquiposAdmInteractorClass implements EquiposAdmInteractor{
    private RealtimeDatabase mDatabase;
    private AdmSession mSession;
    private Storage mStorage;
    public EquiposAdmInteractorClass(){
        mDatabase = new RealtimeDatabase();
        mSession = AdmSession.getInstance();
        mStorage = new Storage();
    }

    @Override
    public void subscribirAEquipos() {
        //(final EquiposAdmEventListener listener)
        System.out.println("------------------- Interactor subscribir a equipos");
        mDatabase.subscribirAEquipos(mSession.getIdCuentaSel(), mSession.getIdCanchaSel(),
                mSession.getIdLigaSel(), new EquiposAdmEventListener() {
            @Override
            public void equipoAgregado(Equipo equipo) {
                post(equipo, Constantes.ALTA_EXITOSA);
            }

            @Override
            public void equipoActualizado(Equipo equipo) {
                post(equipo, Constantes.ACTUALIZACION_EXITOSA);
            }

            @Override
            public void equipoEliminado(Equipo equipo) {
                post(equipo, Constantes.BORADO_EXITOSO);
            }

            @Override
            public void onError(int resMsg) {
                post(Constantes.ERROR_SERVIDOR, resMsg);
            }
        });
    }

    @Override
    public void quitarSubscripcionAEquipos() {
        mDatabase.quitarSubscripcionAEquipos(mSession.getIdCuentaSel(), mSession.getIdCanchaSel(),
                mSession.getIdLigaSel());
    }

    @Override
    public void eliminarEquipo(Equipo equipo) {

        mStorage.borrarCarpetaEquipo(equipo.getUid());

        mDatabase.eliminarEquipo(equipo.getUid(), new BasicErrorEventCallback() {
            @Override
            public void onSuccess() {
                post(equipo, Constantes.BORADO_EXITOSO);
            }

            @Override
            public void onError(int typeEvent, int resMsg) {
                post(typeEvent, resMsg);
            }
        });
    }

    @Override
    public void buscarEquipo(String uidEquipo){
        mDatabase.buscarEquipo(uidEquipo, new BuscarEquipoListener() {
            @Override
            public void onError(int typeEvent, int resMsg) {
                post(typeEvent, resMsg);
            }

            @Override
            public void equipoExiste(int typeEvent, int resMsg, Equipo equipo) {
                //List<RefDelegadoAdm> delegados = equipo.getDelegados();
                System.out.println("--------------------------A borrar referencia a delegado");
                if(equipo.getDelegados() != null && equipo.getDelegados().size() > 0){
                    mDatabase.eliminaRefEquipoEnDelegados(equipo.getUid(), equipo.getDelegados(),
                            new BasicErrorEventCallback() {
                        @Override
                        public void onSuccess() {
                        eliminarEquipo(equipo);
                    }

                        @Override
                        public void onError(int typeEvent, int resMsg) {
                        post(typeEvent, resMsg);
                    }
                    });
                }else{
                    eliminarEquipo(equipo);
                }
            }
        });
    }

    private void post(int typeEvent, int resMsg){
        post(null, typeEvent, resMsg);
    }

    private void post(Equipo equipo, int typeEvent){
        post(equipo, typeEvent, 0);
    }

    private void post(Equipo equipo, int typeEvent, int resMsg){
        EquiposAdmEvent evento = new EquiposAdmEvent();
        evento.setEquipo(equipo);
        evento.setTypeEvent(typeEvent);
        evento.setResMsg(resMsg);

        EventBus.getDefault().post(evento);
    }

}
