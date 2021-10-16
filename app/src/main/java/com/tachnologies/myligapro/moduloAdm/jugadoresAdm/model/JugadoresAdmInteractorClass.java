package com.tachnologies.myligapro.moduloAdm.jugadoresAdm.model;

import com.tachnologies.myligapro.common.callbacks.BasicErrorEventCallback;
import com.tachnologies.myligapro.common.event.EditarEquipoEvent;
import com.tachnologies.myligapro.common.model.dataSession.AdmSession;
import com.tachnologies.myligapro.common.pojo.Equipo;
import com.tachnologies.myligapro.common.utils.Constantes;
import com.tachnologies.myligapro.moduloAdm.equiposAdm.events.BuscarEquipoListener;
import com.tachnologies.myligapro.moduloAdm.jugadoresAdm.model.dataAccess.RealtimeDatabase;
import com.tachnologies.myligapro.moduloAdm.jugadoresAdm.model.dataAccess.Storage;

import org.greenrobot.eventbus.EventBus;

public class JugadoresAdmInteractorClass implements JugadoresAdmInteractor{
    private RealtimeDatabase mDatabase;
    private Storage mStorage;

    public JugadoresAdmInteractorClass(){
        mDatabase = new RealtimeDatabase();
        mStorage = new Storage();
    }

    @Override
    public void consultarEquipo(String uidEquipo) {
        AdmSession mSession = AdmSession.getInstance();
        mDatabase.buscarEquipo(uidEquipo, mSession.getIdCuentaSel(), mSession.getIdCanchaSel(),
            mSession.getIdLigaSel(), new BuscarEquipoListener() {
                @Override
                public void onError(int typeEvent, int resMsg) {
                        post(typeEvent, resMsg);
                    }

                @Override
                public void equipoExiste(int typeEvent, int resMsg, Equipo equipo) {
                    post(typeEvent, 0, equipo,"");
                }
        });
    }

    @Override
    public void eliminarJugador(String uidJugador) {
        mDatabase.eliminarJugador(uidJugador, new BasicErrorEventCallback() {
            @Override
            public void onSuccess() {
                post(Constantes.BORADO_EXITOSO, uidJugador);
            }

            @Override
            public void onError(int typeEvent, int resMsg) {
                post(typeEvent, resMsg, null,"");
            }
        });
    }

    @Override
    public void eliminarFotoJugador(String urlFotoJugador) {
        mStorage.eliminarFotoJugador(urlFotoJugador);
    }

    private void post(int typeEvent, String datoJugador){
        post(typeEvent, 0, null, datoJugador);
    }

    private void post(int typeEvent, int resMsg){
        post(typeEvent, resMsg, null, "");
    }

    private void post(int typeEvent, int resMsg, Equipo equipo, String datoJugador){
        EditarEquipoEvent evento = new EditarEquipoEvent();
        evento.setTypeEvent(typeEvent);
        evento.setResMsg(resMsg);
        evento.setEquipo(equipo);
        evento.setDatoJugador(datoJugador);
        EventBus.getDefault().post(evento);
    }
}
