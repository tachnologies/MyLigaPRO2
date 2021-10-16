package com.tachnologies.myligapro.moduloAdm.editarEquipo;

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
import com.tachnologies.myligapro.common.model.dataSession.AdmSession;
import com.tachnologies.myligapro.common.pojo.Equipo;
import com.tachnologies.myligapro.common.pojo.RefEquipoDelegado;
import com.tachnologies.myligapro.common.pojo.UsuarioDelegado;
import com.tachnologies.myligapro.common.utils.Constantes;
import com.tachnologies.myligapro.common.utils.Utilidades;
import com.tachnologies.myligapro.moduloAdm.editarEquipo.model.EditarEquipoAdmInteractor;
import com.tachnologies.myligapro.moduloAdm.editarEquipo.model.EditarEquipoAdmInteractorClass;
import com.tachnologies.myligapro.moduloAdm.editarEquipo.view.EditarEquipoAdmView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class EditarEquipoAdmPresenterClass implements EditarEquipoAdmPresenter{
    private EditarEquipoAdmView mView;
    private EditarEquipoAdmInteractor mInteractor;

    public EditarEquipoAdmPresenterClass(EditarEquipoAdmView mView){
        this.mView = mView;
        this.mInteractor = new EditarEquipoAdmInteractorClass();
    }

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
    public void eliminarFotoEquipo(String uidEquipo){
        if (mView != null){
            mInteractor.eliminarFotoEquipo(uidEquipo);
        }
    }

    @Override
    public void eliminarEquipo(String uidEquipo){
        if (mView != null){
            mInteractor.eliminarEquipo(uidEquipo);
        }
    }

    @Override
    public void guardarEquipo(Equipo equipo) {

        if (mView != null){
            mInteractor.guardarEquipo(equipo);
        }
    }

    @Override
    public void actBorrarRefDelegados(String uidEquipo, String uidDelegadoAgregar,
            RefEquipoDelegado referencia, String lada, String uidDelegadoBorrar) {

        borrarRefDelegado(Constantes.GUARDADO_SIN_RESPUESTA, uidEquipo, uidDelegadoBorrar);
        agregarRefDelegado(Constantes.DELEGADO_GUARDADO_EXITO , uidDelegadoAgregar, lada,  referencia);

    }

    @Override
    public void borrarRefDelegado(int respuestaExito, String uidEquipo, String uidDelegado) {
        buscarDelegado(uidDelegado, new BasicDelegadoCallback() {
            @Override
            public void onError(int typeEvent, int resMsg) {
                mView.mostrarError(resMsg);
            }

            @Override
            public void delegadoExistente(int typeEvent, UsuarioDelegado delegado) {

                if(delegado.getEquipos() != null && !delegado.getEquipos().isEmpty()){

                    int index =  getIndexEquipo(uidEquipo, delegado.getEquipos());
                    if(index != -1){
                        delegado.getEquipos().remove(index);
                    }
                }
                AdmSession mSession = AdmSession.getInstance();
                delegado.setUsuarioModificacion(mSession.getAdminLogeado().getUid());
                delegado.setFechaModificacion(Utilidades.fechaHoyFormateada());
                mInteractor.guardarDelegado(respuestaExito, delegado);
            }
        });
    }

    private int getIndexEquipo(String uidEquipo, List<RefEquipoDelegado> equipos){
        int index = -1;
        AdmSession mSession = AdmSession.getInstance();

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
    public void actualizarDelegado(String uidDelegado, String nombreEquipo, String urlLogoEquipo,
                                   String uidEquipo){
        buscarDelegado(uidDelegado, new BasicDelegadoCallback() {
            @Override
            public void onError(int typeEvent, int resMsg) {

            }

            @Override
            public void delegadoExistente(int typeEvent, UsuarioDelegado delegado) {
                if(delegado.getEquipos() != null && !delegado.getEquipos().isEmpty()){
                    int index =  getIndexEquipo(uidEquipo, delegado.getEquipos());
                    if(index != -1){
                        delegado.getEquipos().get(index).setNombreEquipo(nombreEquipo);
                        delegado.getEquipos().get(index).setUrlLogoEquipo(urlLogoEquipo);

                        mInteractor.guardarDelegado(Constantes.DELEGADO_GUARDADO_EXITO, delegado);
                    }else{
                        mView.finalGuardar();
                    }

                }else{
                    mView.finalGuardar();
                }

            }
        });
    }

    @Override
    public void agregarRefDelegado(int respuestaExito, String uidDelegadoAgregar, String lada,
            RefEquipoDelegado referencia) {
        buscarDelegado(uidDelegadoAgregar, new BasicDelegadoCallback() {
            @Override
            public void onError(int typeEvent, int resMsg) {

                switch(typeEvent){
                    case Constantes.DELEGADO_NO_EXISTE:
                        AdmSession mSession = AdmSession.getInstance();
                        UsuarioDelegado delegado = new UsuarioDelegado(uidDelegadoAgregar, lada,
                                Constantes.CADENA_VACIA, mSession.getAdminLogeado().getUid(),
                                Utilidades.fechaHoyFormateada(), Constantes.ESTATUS_ALTA);

                        List<RefEquipoDelegado> equipos = new ArrayList<RefEquipoDelegado>();
                        equipos.add(referencia);

                        delegado.setEquipos(equipos);
                        mInteractor.guardarDelegado(respuestaExito, delegado);
                        break;
                    default:
                        mView.mostrarError(resMsg);
                }
            }

            @Override
            public void delegadoExistente(int typeEvent, UsuarioDelegado delegado) {
                List<RefEquipoDelegado> equipos;
                if(delegado.getEquipos() != null && !delegado.getEquipos().isEmpty()){
                    equipos = delegado.getEquipos();
                }else{
                    equipos = new ArrayList<RefEquipoDelegado>();
                }

                equipos.add(referencia);
                delegado.setEquipos(equipos);
                AdmSession mSession = AdmSession.getInstance();
                delegado.setUsuarioModificacion(mSession.getAdminLogeado().getUid());
                delegado.setFechaModificacion(Utilidades.fechaHoyFormateada());
                mInteractor.guardarDelegado(respuestaExito, delegado);
            }
        });
    }

    public void buscarDelegado(String uidDelegado, BasicDelegadoCallback callback){
        mInteractor.buscarDelegado(uidDelegado, callback);
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
                case Constantes.BORADO_EXITOSO:
                    mView.equipoBorrado();
                    break;
                case Constantes.EQUIPO_EXISTE:
                    Equipo equipo = evento.getEquipo();
                    mView.llenaInfoEquipo(equipo);
                    break;
                case Constantes.ALTA_ESCUDO_EQUIPO_EXITOSA:
                    String urlEscudoEquipo = evento.getEquipo().getUrlFoto();
                    mView.guardarEquipo(urlEscudoEquipo);
                    break;
                case Constantes.DELEGADO_GUARDADO_EXITO:
                    mView.finalGuardar();
                    break;
                case Constantes.EQUIPO_GUARDADO:
                    mView.equipoGuardado();
                    break;
                case Constantes.GUARDADO_SIN_RESPUESTA: break;
                case Constantes.ERROR_SUBIR_IMAGEN: case Constantes.ERROR_SERVIDOR: default:
                    mView.mostrarError(evento.getResMsg());
            }
        }
    }

}
