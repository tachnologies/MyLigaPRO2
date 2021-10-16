package com.tachnologies.myligapro.moduloAdm.equiposAdm;

import com.tachnologies.myligapro.common.utils.Constantes;
import com.tachnologies.myligapro.moduloAdm.equiposAdm.events.EquiposAdmEvent;
import com.tachnologies.myligapro.moduloAdm.equiposAdm.model.EquiposAdmInteractor;
import com.tachnologies.myligapro.moduloAdm.equiposAdm.model.EquiposAdmInteractorClass;
import com.tachnologies.myligapro.moduloAdm.equiposAdm.view.EquiposAdmView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class EquiposAdmPresenterClass implements EquiposAdmPresenter{
    private EquiposAdmInteractor mInteractor;
    private EquiposAdmView mView;

    public EquiposAdmPresenterClass(EquiposAdmView mView){
        this.mView = mView;
        this.mInteractor = new EquiposAdmInteractorClass();
    }


    /** --------------------------- Metodos del presenter*/
    @Override
    public void onCreate() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        mInteractor.quitarSubscripcionAEquipos();
    }

    @Override
    public void onResume() {
        mInteractor.subscribirAEquipos();
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        mView = null;
    }

    @Override
    public void eliminarEquipo(String uidEquipo) {
        //aqui bloquear pantalla cuando vaya a borrar
        //mInteractor.eliminarEquipo(uidEquipo);
        mInteractor.buscarEquipo(uidEquipo);
    }

    @Subscribe
    @Override
    public void onEventListener(EquiposAdmEvent event) {
        System.out.println("------------------- onEventListener");
        if (mView != null){
            //aqui deberia de habilitar la pantalla
            System.out.println("------------------- event.getTypeEvent(): " + event.getTypeEvent());
            switch (event.getTypeEvent()){
                case Constantes.ALTA_EXITOSA:
                    mView.equipoAgregado(event.getEquipo());
                    break;
                case Constantes.BORADO_EXITOSO:
                    System.out.println("------------------- BORADO_EXITOSO");
                    mView.equipoBorrado(event.getEquipo());
                    break;
                case Constantes.ACTUALIZACION_EXITOSA:
                    mView.equipoActualizado(event.getEquipo());
                    break;
                case Constantes.ERROR_SERVIDOR:
                    mView.onShowError(event.getResMsg());
                    break;
                default:
            }
        }
    }
}
