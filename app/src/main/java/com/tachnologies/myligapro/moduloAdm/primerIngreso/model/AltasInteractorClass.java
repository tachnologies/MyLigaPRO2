package com.tachnologies.myligapro.moduloAdm.primerIngreso.model;

import com.tachnologies.myligapro.common.callbacks.BasicErrorEventCallback;
import com.tachnologies.myligapro.common.callbacks.CallbackBasicoErrorConUid;
import com.tachnologies.myligapro.common.pojo.Cuenta;
import com.tachnologies.myligapro.common.pojo.UsuarioAdmin;
import com.tachnologies.myligapro.common.utils.Constantes;
import com.tachnologies.myligapro.moduloAdm.primerIngreso.events.AltasEvent;
import com.tachnologies.myligapro.moduloAdm.primerIngreso.model.dataAccess.RealtimeDatabase;
import org.greenrobot.eventbus.EventBus;

public class AltasInteractorClass implements AltasInteractor{
    private RealtimeDatabase mDatabase;

    public AltasInteractorClass(){
        mDatabase = new RealtimeDatabase();
    }

    /**-------------------------------------AltasInteractor */
    public void altaCuenta(Cuenta cuenta) {
        mDatabase.altaCuenta(cuenta, new CallbackBasicoErrorConUid() {
            @Override
            public void onSuccess(String uid) {
                post(Constantes.ALTA_CUENTA, 0, uid);
            }

            @Override
            public void onError(int typeEvent, int resMsg) {
                post(typeEvent, resMsg, "");
            }
        });

    }

    @Override
    public void altaUsuarioAdm(UsuarioAdmin admin) {

        mDatabase.altaAdminLiga(admin, new BasicErrorEventCallback() {
            @Override
            public void onSuccess() {
                mDatabase.setAdminLogueado(admin);
                post(Constantes.ALTA_ADMIN, "");
            }

            @Override
            public void onError(int typeEvent, int resMsg) {
                post(typeEvent, resMsg, "");
            }
        });
    }

    private void post(int typeEvent, String info) {
        post(typeEvent, 0, info);
    }

    private void post(int typeEvent, int resMsg, String info) {
        AltasEvent event = new AltasEvent();
        event.setTypeEvent(typeEvent);
        event.setResMsg(resMsg);
        event.setInfo(info);
        EventBus.getDefault().post(event);
    }

}
