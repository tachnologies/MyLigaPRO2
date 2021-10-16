package com.tachnologies.myligapro.moduloLogin;

import android.content.Intent;

import com.tachnologies.myligapro.moduloLogin.events.LoginEvent;

public interface LoginPresenter {
    void onCreate();
    void onResume();
    void onPause();
    void onDestroy();

    //para saber que hacer cuando responda el onActivityResult
    void result(int requestCode, int resultCode, Intent data);  //result

    //nos ayudara a consultar el estado de la autenticacion para saber si lanzamos firebaseUI o la main activity
    void getStatusAuth();

    void onEventListener(LoginEvent event);

}
