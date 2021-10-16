package com.tachnologies.myligapro.moduloAdm.jugadoresAdm;

import com.tachnologies.myligapro.common.event.EditarEquipoEvent;
import com.tachnologies.myligapro.common.pojo.Equipo;
import com.tachnologies.myligapro.common.pojo.Jugador;
import com.tachnologies.myligapro.common.utils.Constantes;
import com.tachnologies.myligapro.moduloAdm.jugadoresAdm.model.JugadoresAdmInteractor;
import com.tachnologies.myligapro.moduloAdm.jugadoresAdm.model.JugadoresAdmInteractorClass;
import com.tachnologies.myligapro.moduloAdm.jugadoresAdm.view.JugadoresAdmView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class JugadoresAdmPresenterClass implements JugadoresAdmPresenter{
    private JugadoresAdmView mView;
    private JugadoresAdmInteractor mInteractor;

    public JugadoresAdmPresenterClass(JugadoresAdmView mView) {
        this.mView = mView;
        this.mInteractor = new JugadoresAdmInteractorClass();
    }

    @Override
    public void consultarEquipo(String uidEquipo) {
        mInteractor.consultarEquipo(uidEquipo);
    }

    @Override
    public void onCreate() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        mView = null;
    }

    @Override
    public void eliminarJugador(String uidJugador) {
        if (mView != null){
            mInteractor.eliminarJugador(uidJugador);
        }
    }

    @Override
    public void eliminarFotoJugador(String urlFotoJugador) {
        if (mView != null){
            mInteractor.eliminarFotoJugador(urlFotoJugador);
        }
    }

    /** muy importante!!!! no olvidar el subscribe que si no vale chori*/
    @Subscribe
    @Override
    public void onEventListener(EditarEquipoEvent evento) {
        if (mView != null){
            switch(evento.getTypeEvent()) {
                case Constantes.EQUIPO_EXISTE:
                    Equipo equipo = evento.getEquipo();
                    mView.cargaJugadores(equipo);
                    break;
                case Constantes.BORADO_EXITOSO:
                    Jugador jugador = new Jugador();
                    jugador.setUid(evento.getDatoJugador());
                    mView.eliminarJugador(jugador);
                    break;
                default:
            }
        }
    }
}
