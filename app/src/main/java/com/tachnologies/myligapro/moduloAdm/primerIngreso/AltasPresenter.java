package com.tachnologies.myligapro.moduloAdm.primerIngreso;

import com.tachnologies.myligapro.common.pojo.Cuenta;
import com.tachnologies.myligapro.common.pojo.UsuarioAdmin;
import com.tachnologies.myligapro.moduloAdm.primerIngreso.events.AltasEvent;

public interface AltasPresenter {
    void onCreate();
    void onResume();
    void onPause();
    void onDestroy();
    void altaCuenta(Cuenta cuenta);
    void altaUsuarioAdm(UsuarioAdmin admin);
    void onEventListener(AltasEvent event);
}
