package com.tachnologies.myligapro.moduloAdm.primerIngreso.model.dataAccess;

import com.tachnologies.myligapro.common.callbacks.BasicErrorEventCallback;
import com.tachnologies.myligapro.common.callbacks.CallbackBasicoErrorConUid;
import com.tachnologies.myligapro.common.model.dataAccess.FirebaseRealtimeDatabaseAPI;
import com.tachnologies.myligapro.common.model.dataSession.AdmSession;
import com.tachnologies.myligapro.common.pojo.Cuenta;
import com.tachnologies.myligapro.common.pojo.UsuarioAdmin;

public class RealtimeDatabase {
    private FirebaseRealtimeDatabaseAPI mDatabaseAPI;
    private AdmSession mSession;

    public RealtimeDatabase() {
        mDatabaseAPI = FirebaseRealtimeDatabaseAPI.getInstance();
        mSession = AdmSession.getInstance();
    }

    public void altaCuenta(Cuenta cuenta, final CallbackBasicoErrorConUid callback){
        mDatabaseAPI.altaCuenta(cuenta, callback);
    }

    public void altaAdminLiga(UsuarioAdmin usuario, final BasicErrorEventCallback callback){
        System.out.println("--------------------------------------- altaUsuarioAdm mDatabase");
        mDatabaseAPI.altaAdminLiga(usuario, callback);
    }

    public void setAdminLogueado(UsuarioAdmin adminLogeado){
        mSession.setAdminLogeado(adminLogeado);
    }
}
