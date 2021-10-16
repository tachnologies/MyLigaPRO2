package com.tachnologies.myligapro.moduloAdm.primerIngreso.model.dataAccess;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.tachnologies.myligapro.R;
import com.tachnologies.myligapro.common.callbacks.BasicErrorEventCallback;
import com.tachnologies.myligapro.common.callbacks.CallbackBasicoErrorConUid;
import com.tachnologies.myligapro.common.model.dataAccess.FirebaseRealtimeDatabaseAPI;
import com.tachnologies.myligapro.common.model.dataSession.AdmSession;
import com.tachnologies.myligapro.common.pojo.Cuenta;
import com.tachnologies.myligapro.common.pojo.UsuarioAdmin;
import com.tachnologies.myligapro.common.utils.Constantes;
import java.util.HashMap;
import java.util.Map;

public class RealtimeDatabase {
    private FirebaseRealtimeDatabaseAPI mDatabaseAPI;
    private AdmSession mSession;

    public RealtimeDatabase() {
        mDatabaseAPI = FirebaseRealtimeDatabaseAPI.getInstance();
        mSession = AdmSession.getInstance();
    }

    public void altaCuenta(Cuenta cuenta, final CallbackBasicoErrorConUid callback){
        mDatabaseAPI.altaCuenta(cuenta, callback);
        /*mDatabaseAPI.getReferenciaCuentas().push().setValue(cuenta, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError == null){
                    callback.onSuccess(databaseReference.getKey());
                } else {
                    switch (databaseError.getCode()){
                        case DatabaseError.PERMISSION_DENIED:
                            break;
                        default:
                            callback.onError(Constantes.ERROR_SERVIDOR,
                                    R.string.error_servidor_estandar);
                    }
                }
            }
        });*/
    }

    public void altaAdminLiga(UsuarioAdmin usuario, final BasicErrorEventCallback callback){
        System.out.println("--------------------------------------- altaUsuarioAdm mDatabase");
        mDatabaseAPI.altaAdminLiga(usuario, callback);
    }

    public void setAdminLogueado(UsuarioAdmin adminLogeado){
        mSession.setAdminLogeado(adminLogeado);
    }

    /*public UsuarioAdmin getAdminLogueado(){
        return mSession.getAdminLogeado();
    }*/

}
