package com.tachnologies.myligapro.moduloAdminJugadores.agregarEditar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.tachnologies.myligapro.common.pojo.Jugador;
import com.tachnologies.myligapro.common.utils.Constantes;
import com.tachnologies.myligapro.common.utils.Utilidades;
import com.tachnologies.myligapro.moduloAdminJugadores.agregarEditar.events.AgregarEditarJugadorEvent;
import com.tachnologies.myligapro.moduloAdminJugadores.agregarEditar.model.AgregarEditarJugadorInteractor;
import com.tachnologies.myligapro.moduloAdminJugadores.agregarEditar.model.AgregarEditarJugadorInteractorClass;
import com.tachnologies.myligapro.moduloAdminJugadores.agregarEditar.view.AgregarEditarJugadorView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class AgregarEditarJugadorPresenterClass implements AgregarEditarJugadorPresenter{
    private AgregarEditarJugadorView mView;
    private AgregarEditarJugadorInteractor mInteractor;

    public AgregarEditarJugadorPresenterClass(AgregarEditarJugadorView mView){
        this.mView = mView;
        this.mInteractor = new AgregarEditarJugadorInteractorClass();
    }

    /** ------------------------------ interfaz AgregarEditarJugadorPresenter*/
    @Override
    public void onShow() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        mView = null;
    }

    @Override
    public void subirFotoJugador(String uidCuenta, String uidCancha, String uidLiga, String equipoId,
            String jugadorId, Uri uriFoto) {
        mInteractor.subirFotoJugador(uidCuenta, uidCancha, uidLiga, equipoId, jugadorId, uriFoto);
    }

    @Override
    public void guardarJugador(Jugador jugador, String uidCuenta, String uidCancha, String uidLiga,
            String uidEquipo) {
        mInteractor.guardarJugador(jugador, uidCuenta, uidCancha, uidLiga, uidEquipo);
    }

    @Override
    public void eliminarJugador(String uidCuenta, String uidCancha, String uidLiga, String uidEquipo, String uidJugador) {
        mInteractor.eliminarJugador(uidCuenta, uidCancha, uidLiga, uidEquipo, uidJugador);
    }

    @Override
    public void eliminarFotoJugador(String urlFotoJugador) {
        mInteractor.eliminarFotoJugador(urlFotoJugador);
    }

    @Override
    public void checarPermisos(String permissionStr, int requestPermission, Context context, Activity activity) {
        if(!Utilidades.checarPermisosGaleria(permissionStr, requestPermission, context, activity)){
            return;
        }

        switch (requestPermission) {
            case Constantes.RP_STORAGE:
                mView.abrirGaleria();
                break;
        }
    }

    @Override
    public void resultadoActivity(int requestCode, int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_OK){
            switch(requestCode){
                case Constantes.RC_FOTO_EQUIPO_PICKER:
                    mView.setImagen(data);
                    break;
                default:
                    break;
            }
        }
    }

    /** muy importante!!!! no olvidar el subscribe que si no vale chori*/
    @Subscribe
    @Override
    public void onEventListener(AgregarEditarJugadorEvent evento) {
        switch(evento.getTypeEvent()) {
            case Constantes.GUARDADO_EXITOSO:
                mView.jugadorGuardado(evento.getJugador());
                break;
            case Constantes.ALTA_IMAGEN_EXITOSA:
                String urlFotoJugador = evento.getJugador().getUrlFoto();
                Jugador jugador = new Jugador();
                jugador.setUrlFoto(urlFotoJugador);
                mView.guardarJugador(jugador);
                break;
            case Constantes.BORADO_EXITOSO:
                mView.jugadorEliminado(evento.getJugador());
                break;
            case Constantes.ERROR_SUBIR_IMAGEN: case Constantes.ERROR_SERVIDOR: default:
                mView.mostrarError(evento.getResMsg());
        }
    }
}
