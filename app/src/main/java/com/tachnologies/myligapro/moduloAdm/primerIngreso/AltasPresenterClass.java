package com.tachnologies.myligapro.moduloAdm.primerIngreso;

import com.tachnologies.myligapro.common.pojo.Cuenta;
import com.tachnologies.myligapro.common.pojo.UsuarioAdmin;
import com.tachnologies.myligapro.common.utils.Constantes;
import com.tachnologies.myligapro.moduloAdm.primerIngreso.events.AltasEvent;
import com.tachnologies.myligapro.moduloAdm.primerIngreso.model.AltasInteractor;
import com.tachnologies.myligapro.moduloAdm.primerIngreso.model.AltasInteractorClass;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class AltasPresenterClass implements AltasPresenter{
    private AltasView mView;
    private AltasInteractor mInteractor;

    public AltasPresenterClass(AltasView mView) {
        this.mView = mView;
        this.mInteractor = new AltasInteractorClass();
    }

    /**----------------------------------AltasPresenter */
    @Override
    public void onCreate() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void onResume() {
        //creo que aqui se hace para que el consumo de datos sea menor, pero eso  tambien
        //creo que es exclusivo del firestore
        //mInteractor.onResume();
    }

    @Override
    public void onPause() {
        //mInteractor.onPause();
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        mView = null;
    }

    @Override
    public void altaCuenta(Cuenta cuenta) {
        mInteractor.altaCuenta(cuenta);
    }

    @Override
    public void altaUsuarioAdm(UsuarioAdmin admin) {
        mInteractor.altaUsuarioAdm(admin);
    }

    @Subscribe
    @Override
    public void onEventListener(AltasEvent event) {
        if (mView != null){

            switch (event.getTypeEvent()){
                case Constantes.ALTA_CUENTA:
                    String uid = event.getInfo();
                    mView.cuentaGuardada(uid);
                    break;
                case Constantes.ALTA_ADMIN:
                    mView.abrirAdmActivity();
                    break;
                case Constantes.ERROR_SERVIDOR:
                    mView.mostrarError(event.getResMsg());
                    break;
                default:
                    System.out.println("---------------------------- che evento madafaker: ");
            }
        }
    }
}
