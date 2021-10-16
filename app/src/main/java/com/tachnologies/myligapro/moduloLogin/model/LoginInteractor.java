package com.tachnologies.myligapro.moduloLogin.model;

import com.tachnologies.myligapro.moduloLogin.model.dataAccess.Authentication;
import com.tachnologies.myligapro.moduloLogin.model.dataAccess.RealtimeDatabase;

public interface LoginInteractor {

    void onResume();
    void onPause();

    void getStatusAuth(String tipoUsuario);

    RealtimeDatabase getmDatabase();
    Authentication getmAuthentication();

}
