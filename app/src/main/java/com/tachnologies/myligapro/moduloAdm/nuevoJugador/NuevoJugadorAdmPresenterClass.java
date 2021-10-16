package com.tachnologies.myligapro.moduloAdm.nuevoJugador;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.tachnologies.myligapro.common.pojo.Jugador;
import com.tachnologies.myligapro.common.utils.Constantes;
import com.tachnologies.myligapro.common.utils.Utilidades;
import com.tachnologies.myligapro.moduloAdm.nuevoJugador.eventos.NuevoJugadorEvent;
import com.tachnologies.myligapro.moduloAdm.nuevoJugador.model.NuevoJugadorAdmInteractor;
import com.tachnologies.myligapro.moduloAdm.nuevoJugador.model.NuevoJugadorAdmInteractorClass;
import com.tachnologies.myligapro.moduloAdm.nuevoJugador.view.NuevoJugadorAdmView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class NuevoJugadorAdmPresenterClass implements NuevoJugadorAdmPresenter{
    private NuevoJugadorAdmView mView;
    private NuevoJugadorAdmInteractor mInteractor;

    public NuevoJugadorAdmPresenterClass(NuevoJugadorAdmView mView){
        this.mView = mView;
        this.mInteractor = new NuevoJugadorAdmInteractorClass();
    }
    /** ------------------------------ interfaz NuevoJugadorAdmPresenter*/
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
    public void subirFotoJugador(Uri uriFoto, String equipoId, String jugadorId) {
        if (mView != null){
            mInteractor.subirFotoJugador(uriFoto, equipoId, jugadorId);
        }
    }

    @Override
    public void guardarJugador(Jugador jugador) {
        if (mView != null){
            mInteractor.guardarJugador(jugador);
        }
    }

    @Override
    public void eliminarFotoJugador(String urlFotoJugador){
        if (mView != null){
            mInteractor.eliminarFotoJugador(urlFotoJugador);
        }
    }

    @Override
    public void eliminarJugador(String uidJugador){
        if (mView != null){
            mInteractor.eliminarJugador(uidJugador);
        }
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
    public void onEventListener(NuevoJugadorEvent evento) {
        if (mView != null){
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
}
