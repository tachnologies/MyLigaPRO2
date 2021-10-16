package com.tachnologies.myligapro.moduloAdm.nuevoEquipo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.tachnologies.myligapro.common.pojo.Equipo;
import com.tachnologies.myligapro.common.pojo.UsuarioDelegado;
import com.tachnologies.myligapro.common.utils.Constantes;
import com.tachnologies.myligapro.common.utils.Utilidades;
import com.tachnologies.myligapro.moduloAdm.nuevoEquipo.events.NuevoEquipoEvent;
import com.tachnologies.myligapro.moduloAdm.nuevoEquipo.model.NuevoEquipoInteractor;
import com.tachnologies.myligapro.moduloAdm.nuevoEquipo.model.NuevoEquipoInteractorClass;
import com.tachnologies.myligapro.moduloAdm.nuevoEquipo.view.NuevoEquipoView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class NuevoEquipoPresenterClass implements NuevoEquipoPresenter{
    private NuevoEquipoView mView;
    private NuevoEquipoInteractor mInteractor;

    public NuevoEquipoPresenterClass(NuevoEquipoView mView){
        this.mView = mView;
        this.mInteractor = new NuevoEquipoInteractorClass();
    }

    /**-----------------------NuevoEquipoPresenter */
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
    public void subirEscudoEquipo(Uri uriFoto, String equipoId){
        if (mView != null){
            mInteractor.subirEscudoEquipo(uriFoto, equipoId);
        }
    }

    @Override
    public void guardarEquipo(Equipo equipo) {
        if (mView != null){
            mInteractor.guardarEquipo(equipo);
        }
    }

    @Override
    public void actualizarDelegado(UsuarioDelegado delegado){
        if (mView != null){
            mInteractor.actualizarDelegado(delegado);
        }
    }
    @Override
    public void guardarDelegado(UsuarioDelegado delegado) {
        if (mView != null){
            mInteractor.guardarDelegado(delegado);
        }
    }

    @Override
    public void checarPermisos(String permissionStr, int requestPermission, Context context,
                               Activity activity) {

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
    public void resultadoActivity(int requestCode,int resultCode, Intent data){
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
    public void onEventListener(NuevoEquipoEvent evento) {
        if (mView != null){
            //mView.desbloquearPantalla();

            switch(evento.getTypeEvent()) {
                case Constantes.ALTA_EXITOSA:
                    mView.equipoAgregado();
                    break;
                case Constantes.ALTA_ESCUDO_EQUIPO_EXITOSA:
                    String uidEquipo = evento.getIdEquipo();
                    String urlEscudoEquipo = evento.getUrlEscudoEquipo();
                    Equipo equipo = new Equipo(uidEquipo, urlEscudoEquipo);
                    mView.guardarEquipo(equipo);
                    break;
                case Constantes.ALTA_DELEGADO_EXITOSA: case Constantes.ACTUALIZACION_EXITOSA:
                    System.out.println("------------------------- evento.getTypeEvent(): " + evento.getTypeEvent());
                    mView.finalGuardar();
                    break;
                case Constantes.DELEGADO_EXISTENTE:
                    UsuarioDelegado delegado = evento.getDelegado();
                    mView.actualizarDelegado(delegado);
                    break;
                case Constantes.ERROR_SUBIR_IMAGEN: case Constantes.ERROR_SERVIDOR: default:
                    mView.mostrarError(evento.getResMsg());

            }
        }
    }

}
