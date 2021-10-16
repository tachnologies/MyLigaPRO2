package com.tachnologies.myligapro.moduloLogin;

import android.app.Activity;
import android.content.Intent;

import com.tachnologies.myligapro.R;
import com.tachnologies.myligapro.common.model.dataSession.AdmSession;
import com.tachnologies.myligapro.common.pojo.UsuarioAdmin;
import com.tachnologies.myligapro.common.pojo.UsuarioDelegado;
import com.tachnologies.myligapro.common.utils.Constantes;
import com.tachnologies.myligapro.moduloLogin.events.LoginEvent;
import com.tachnologies.myligapro.moduloLogin.model.LoginInteractor;
import com.tachnologies.myligapro.moduloLogin.model.LoginInteractorClass;
import com.tachnologies.myligapro.moduloLogin.view.LoginView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class LoginPresenterClass implements LoginPresenter{
    private String tipoUsuario;
    private LoginView mView;
    private LoginInteractor mInteractor;

    public LoginPresenterClass(LoginView mView, String tipoUsuario) {
        this.mView = mView;
        this.mInteractor = new LoginInteractorClass();
        this.tipoUsuario = tipoUsuario;
    }

    private boolean setProgress() {
        if(mView != null){
            mView.showProgress();
            return true;
        }
        return false;
    }

    @Override
    public void onCreate() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void onResume() {
        if (setProgress()) {
            mInteractor.onResume();
        }
    }

    @Override
    public void onPause() {
        if(setProgress()){
            mInteractor.onPause();
        }
    }

    @Override
    public void onDestroy() {
        mView = null;
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void result(int requestCode, int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_OK){
            switch(requestCode){
                case Constantes.RC_SIGN_IN:
                    if(data != null){
                        mView.showLoginSuccessfully();
                    }
                    break;
                default:
            }
        }else{
            mView.showError(R.string.login_mensaje_error);
        }
    }

    @Override
    public void getStatusAuth() {
        if(setProgress()){
            mInteractor.getStatusAuth(this.tipoUsuario);
        }
    }

    @Subscribe
    @Override
    public void onEventListener(LoginEvent event) {
        if(mView != null){
            mView.hideProgress();

            switch(event.getTypeEvent()){
                case LoginEvent.AUTH_NUEVO_EXITOSO:
                    if(setProgress()){
                        mView.showMessageStarting();

                        switch (tipoUsuario){
                            case Constantes.ADMIN_LIGA:
                                UsuarioAdmin admin = event.getAdmin();
                                mView.openAltaAdminActivity(admin);
                                break;
                            case Constantes.DELEGADO:
                                UsuarioDelegado delegado = event.getDelegado();
                                mView.openDelegadoActivity(delegado, true);
                                break;
                            default:
                                System.out.println("--------------- Valio verdura niuno homs!!! :V");
                        }
                    }
                    break;
                case LoginEvent.AUTH_EXITOSO:
                    if(setProgress()){
                        mView.showMessageStarting();

                        switch (tipoUsuario){
                            case Constantes.ADMIN_LIGA:
                                UsuarioAdmin admin = event.getAdmin();
                                mView.openAdminActivity(admin);
                                break;
                            case Constantes.DELEGADO:
                                UsuarioDelegado delegado = event.getDelegado();
                                mView.openDelegadoActivity(delegado, false);
                                break;
                            default:
                                System.out.println("--------------- Valio verdura niuno homs!!! :V");
                                mView.showError(R.string.login_error_respuesta);
                        }
                    }
                    break;
                case LoginEvent.ERROR_AUTH:
                    mView.openUILogin();
                    break;
                case LoginEvent.ERROR_SERVER:
                    mView.showError(event.getResMsg());
                    break;
                default:
                    mView.showError(R.string.login_error_respuesta);
            }
        }
    }
}
