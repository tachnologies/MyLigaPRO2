package com.tachnologies.myligapro.moduloLogin.model;

import com.google.firebase.auth.FirebaseUser;
import com.tachnologies.myligapro.common.model.EventErrorTypeListener;
import com.tachnologies.myligapro.common.model.dataSession.AdmSession;
import com.tachnologies.myligapro.common.model.dataSession.DelSession;
import com.tachnologies.myligapro.common.pojo.UsuarioAdmin;
import com.tachnologies.myligapro.common.pojo.UsuarioDelegado;
import com.tachnologies.myligapro.common.utils.Constantes;
import com.tachnologies.myligapro.moduloLogin.events.LoginEvent;
import com.tachnologies.myligapro.moduloLogin.model.dataAccess.Authentication;
import com.tachnologies.myligapro.moduloLogin.model.dataAccess.RealtimeDatabase;
import com.tachnologies.myligapro.moduloLogin.model.dataAccess.StatusAuthCallback;
import org.greenrobot.eventbus.EventBus;

public class LoginInteractorClass implements LoginInteractor{
    private Authentication mAuthentication;
    private RealtimeDatabase mDatabase;
    private AdmSession mSession;
    private DelSession mDelSession;

    public LoginInteractorClass() {
        mAuthentication = new Authentication();
        mDatabase = new RealtimeDatabase();
        mSession = AdmSession.getInstance();
        mDelSession = DelSession.getInstance();
    }

    @Override
    public void onResume() {
        mAuthentication.onResume();
    }

    @Override
    public void onPause() {
        mAuthentication.onPause();
    }

    @Override
    public void getStatusAuth(String tipoUsuario) {
        mAuthentication.getStatusAuth(new StatusAuthCallback() {
            @Override
            public void onGetUser(FirebaseUser user) {

                mDatabase.verificaExisteUsuario(user.getPhoneNumber(), tipoUsuario, new EventErrorTypeListener() {
                    @Override
                    public void onError(int typeEvent, int resMsg) {
                        mSession.setAdminLogeado(null);
                        mDelSession.setDelLogeado(null);

                        switch(typeEvent){
                            case LoginEvent.USUARIO_NO_EXISTE:
                                switch(tipoUsuario){
                                    case Constantes.ADMIN_LIGA:
                                        UsuarioAdmin admin = new UsuarioAdmin();
                                        admin.setUid(user.getPhoneNumber());
                                        post(LoginEvent.AUTH_NUEVO_EXITOSO, admin);
                                        break;
                                    case Constantes.DELEGADO:
                                        UsuarioDelegado delegado = new UsuarioDelegado();
                                        delegado.setUid(user.getPhoneNumber());
                                        post(LoginEvent.AUTH_NUEVO_EXITOSO, delegado);
                                    break;
                                    default:
                                        post(typeEvent);
                                }

                                break;
                            default:
                                post(typeEvent);
                        }
                    }

                    @Override
                    public void usuarioAdminExistente(int typeEvent, int resMsg, UsuarioAdmin admin) {
                        mSession.setAdminLogeado(admin);
                        post(LoginEvent.AUTH_EXITOSO, admin);
                    }

                    @Override
                    public void usuarioDelegadoExistente(int typeEvent, int resMsg, UsuarioDelegado delegado) {
                        mDelSession.setDelLogeado(delegado);
                        post(LoginEvent.AUTH_EXITOSO, delegado);
                    }
                });
            }

            @Override
            public void onLaunchUILogin() {
                post(LoginEvent.ERROR_AUTH);
            }
        });
    }

    private void post(int typeEvent) {
        post(typeEvent, null, null);
    }

    private void post(int typeEvent, UsuarioAdmin admin) {
        post(typeEvent, admin, null);
    }

    private void post(int typeEvent, UsuarioDelegado delegado) {
        post(typeEvent, null, delegado);
    }

    private void post(int typeEvent, UsuarioAdmin admin, UsuarioDelegado delegado) {
        LoginEvent event = new LoginEvent();
        event.setTypeEvent(typeEvent);
        event.setAdmin(admin);
        event.setDelegado(delegado);
        EventBus.getDefault().post(event);
    }

    public RealtimeDatabase getmDatabase() {
        return mDatabase;
    }

    public Authentication getmAuthentication() {
        return mAuthentication;
    }
}
