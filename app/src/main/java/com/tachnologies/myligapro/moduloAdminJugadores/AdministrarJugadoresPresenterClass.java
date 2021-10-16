package com.tachnologies.myligapro.moduloAdminJugadores;

import com.tachnologies.myligapro.common.event.EditarEquipoEvent;
import com.tachnologies.myligapro.common.pojo.Equipo;
import com.tachnologies.myligapro.common.pojo.Jugador;
import com.tachnologies.myligapro.common.utils.Constantes;
import com.tachnologies.myligapro.moduloAdminJugadores.model.AdministrarJugadoresInteractor;
import com.tachnologies.myligapro.moduloAdminJugadores.model.AdministrarJugadoresInteractorClass;
import com.tachnologies.myligapro.moduloAdminJugadores.view.AdministrarJugadoresView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class AdministrarJugadoresPresenterClass implements AdministrarJugadoresPresenter{
    private AdministrarJugadoresView mView;
    private AdministrarJugadoresInteractor mInteractor;

    public AdministrarJugadoresPresenterClass(AdministrarJugadoresView mView) {
        this.mView = mView;
        this.mInteractor = new AdministrarJugadoresInteractorClass();
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
    public void consultarEquipo(String uidEquipo, String uidCuenta, String uidCancha, String uidLiga) {
        mInteractor.consultarEquipo(uidEquipo, uidCuenta, uidCancha, uidLiga);
    }

    @Override
    public void eliminarJugador(String uidCuenta, String uidCancha, String uidLiga, String uidEquipo,
                                String uidJugador) {
        mInteractor.eliminarJugador(uidCuenta, uidCancha, uidLiga, uidEquipo, uidJugador);
    }

    @Override
    public void eliminarFotoJugador(String urlFotoJugador) {
        mInteractor.eliminarFotoJugador(urlFotoJugador);
    }

    /** muy importante!!!! no olvidar el subscribe que si no vale chori*/
    @Subscribe
    @Override
    public void onEventListener(EditarEquipoEvent evento) {
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
