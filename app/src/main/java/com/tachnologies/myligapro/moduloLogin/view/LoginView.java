package com.tachnologies.myligapro.moduloLogin.view;

import android.content.Intent;

import com.tachnologies.myligapro.common.pojo.UsuarioAdmin;
import com.tachnologies.myligapro.common.pojo.UsuarioDelegado;

public interface LoginView {

    void showProgress();
    void hideProgress();

    //void openMainActivity(String tipoUsuario);       //openMainActivity        lanzar la activityMain

    void openAdminActivity(UsuarioAdmin admin);
    void openDelegadoActivity(UsuarioDelegado delegado, boolean esNuevo);
    void openAltaAdminActivity(UsuarioAdmin admin);
    //void openMainActivity(UsuarioDelegado usuarioDelegado);

    //openUILogin             lanza firebaseUI
    void openUILogin();

    //showLoginSuccessfully   es la respuesta del onActivityResult del firebaseUI
    void showLoginSuccessfully();
    //showMessageStarting     para mostrar Msje cuando se haya logeado correctamente y se este cargando la info
    void showMessageStarting();
    void showError(int resMsg);


}
