package com.tachnologies.myligapro.moduloAdm.agregaEditaLigas.model.dataAccess;

import com.tachnologies.myligapro.common.callbacks.BasicErrorEventCallback;
import com.tachnologies.myligapro.common.model.EventErrorTypeListener;
import com.tachnologies.myligapro.common.model.dataAccess.FirebaseRealtimeDatabaseAPI;
import com.tachnologies.myligapro.common.model.dataSession.AdmSession;
import com.tachnologies.myligapro.common.pojo.Equipo;
import com.tachnologies.myligapro.common.pojo.Liga;
import com.tachnologies.myligapro.common.pojo.UsuarioDelegado;
import com.tachnologies.myligapro.common.utils.Constantes;


public class RealtimeDatabase {
    private FirebaseRealtimeDatabaseAPI mDatabaseAPI;

    public RealtimeDatabase(){
        mDatabaseAPI = FirebaseRealtimeDatabaseAPI.getInstance();
    }



    public void actualizarDelegado(UsuarioDelegado delegado, final BasicErrorEventCallback callback){
        mDatabaseAPI.altaDelegado(delegado, callback);
    }

    public void guardarLiga(Liga liga, BasicErrorEventCallback callback){
        AdmSession mSession = AdmSession.getInstance();
        mDatabaseAPI.guardarLiga(liga, mSession.getIdCuentaSel(), mSession.getIdCanchaSel(), callback);
    }

    public void eliminarLiga(String uidLiga, final BasicErrorEventCallback callback){
        AdmSession mSession = AdmSession.getInstance();
        mDatabaseAPI.eliminarLiga(mSession.getIdCuentaSel(), mSession.getIdCanchaSel(), uidLiga, callback);
    }

}
