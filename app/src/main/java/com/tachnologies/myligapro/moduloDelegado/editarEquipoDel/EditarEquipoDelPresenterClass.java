package com.tachnologies.myligapro.moduloDelegado.editarEquipoDel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.tachnologies.myligapro.common.callbacks.BasicDelegadoCallback;
import com.tachnologies.myligapro.common.event.EditarEquipoEvent;
import com.tachnologies.myligapro.common.model.dataSession.DelSession;
import com.tachnologies.myligapro.common.pojo.Equipo;
import com.tachnologies.myligapro.common.pojo.RefEquipoDelegado;
import com.tachnologies.myligapro.common.pojo.UsuarioDelegado;
import com.tachnologies.myligapro.common.utils.Constantes;
import com.tachnologies.myligapro.moduloDelegado.editarEquipoDel.model.EditarEquipoDelInteractor;
import com.tachnologies.myligapro.moduloDelegado.editarEquipoDel.model.EditarEquipoDelInteractorClass;
import com.tachnologies.myligapro.moduloDelegado.editarEquipoDel.view.EditarEquipoDelView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

public class EditarEquipoDelPresenterClass implements EditarEquipoDelPresenter{
    private EditarEquipoDelView mView;
    private EditarEquipoDelInteractor mInteractor;

    public EditarEquipoDelPresenterClass(EditarEquipoDelView mView) {
        this.mView = mView;
        this.mInteractor = new EditarEquipoDelInteractorClass();
    }

    public void buscarDelegado(String uidDelegado, BasicDelegadoCallback callback){
        mInteractor.buscarDelegado(uidDelegado, callback);
    }

    private int getIndexEquipo(String uidEquipo, List<RefEquipoDelegado> equipos){
        int index = -1;
        DelSession mSession = DelSession.getInstance();

        for(int i = 0; i< equipos.size(); i++){
            RefEquipoDelegado referencia = equipos.get(i);
            if(referencia.getUidCuenta().equals(mSession.getIdCuentaSel()) &&
                    referencia.getUidCancha().equals(mSession.getIdCanchaSel()) &&
                    referencia.getUidLiga().equals(mSession.getIdLigaSel()) &&
                    referencia.getUidEquipo().equals(uidEquipo)) {
                index = i;
            }
        }

        return index;
    }

    @Override
    public void actualizarDelegado(String nombreEquipo, String urlLogoEquipo,
                                   String uidEquipo){
        DelSession mSession = DelSession.getInstance();
        buscarDelegado(mSession.getDelLogeado().getUid(), new BasicDelegadoCallback() {
            @Override
            public void onError(int typeEvent, int resMsg) {
                mView.mostrarError(resMsg);
            }

            @Override
            public void delegadoExistente(int typeEvent, UsuarioDelegado delegado) {

                if(delegado.getEquipos() != null && !delegado.getEquipos().isEmpty()){
                    int index =  getIndexEquipo(uidEquipo, delegado.getEquipos());
                    if(index != -1){
                        delegado.getEquipos().get(index).setNombreEquipo(nombreEquipo);
                        delegado.getEquipos().get(index).setUrlLogoEquipo(urlLogoEquipo);
                        mSession.setNombreEquipoSel(nombreEquipo);
                        mSession.setUrlLogoEquipoSel(urlLogoEquipo);

                        mInteractor.guardarDelegado(delegado);
                    }
                }else{
                    mView.finalGuardar();
                }

            }
        });
    }
    /**-------------------------------------- metodos de la interfaz EditarEquipoDelPresenter*/
    @Override
    public void consultarEquipo(String uidEquipo) {
        mInteractor.consultarEquipo(uidEquipo);
    }

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
    public void subirEscudoEquipo(Uri uriFoto, String uidEquipo) {
        if (mView != null){
            mInteractor.subirEscudoEquipo(uriFoto, uidEquipo);
        }
    }

    @Override
    public void guardarEquipo(Equipo equipo) {
        if (mView != null){
            mInteractor.guardarEquipo(equipo);
        }
    }

    @Override
    public void eliminarFotoEquipo(String uidEquipo) {
        if (mView != null){
            mInteractor.eliminarFotoEquipo(uidEquipo);
        }
    }

    @Override
    public void checarPermisos(String permissionStr, int requestPermission, Context context, Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, permissionStr) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, new String[]{permissionStr}, requestPermission);
                return;
            }
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
    public void onEventListener(EditarEquipoEvent evento) {
        if (mView != null){
            switch(evento.getTypeEvent()) {
                case Constantes.EQUIPO_EXISTE:
                    Equipo equipo = evento.getEquipo();
                    mView.llenaInfoEquipo(equipo);
                    break;
                case Constantes.ALTA_ESCUDO_EQUIPO_EXITOSA:
                    String urlEscudoEquipo = evento.getEquipo().getUrlFoto();
                    mView.guardarEquipo(urlEscudoEquipo);
                    break;
                case Constantes.EQUIPO_GUARDADO:
                    mView.equipoGuardado();
                    break;
                case Constantes.DELEGADO_GUARDADO_EXITO:
                    mView.finalGuardar();
                    break;
                case Constantes.GUARDADO_SIN_RESPUESTA: break;
                case Constantes.ERROR_SUBIR_IMAGEN: case Constantes.ERROR_SERVIDOR: default:
                    mView.mostrarError(evento.getResMsg());
            }
        }
    }
}
