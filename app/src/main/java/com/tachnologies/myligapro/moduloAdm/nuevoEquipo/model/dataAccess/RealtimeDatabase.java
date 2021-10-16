package com.tachnologies.myligapro.moduloAdm.nuevoEquipo.model.dataAccess;

import com.tachnologies.myligapro.common.callbacks.BasicErrorEventCallback;
import com.tachnologies.myligapro.common.model.EventErrorTypeListener;
import com.tachnologies.myligapro.common.model.dataAccess.FirebaseRealtimeDatabaseAPI;
import com.tachnologies.myligapro.common.model.dataSession.AdmSession;
import com.tachnologies.myligapro.common.pojo.Equipo;
import com.tachnologies.myligapro.common.pojo.UsuarioDelegado;
import com.tachnologies.myligapro.common.utils.Constantes;


public class RealtimeDatabase {
    private FirebaseRealtimeDatabaseAPI mDatabaseAPI;

    public RealtimeDatabase(){
        mDatabaseAPI = FirebaseRealtimeDatabaseAPI.getInstance();
    }

    public void verificaExisteUsuario(String uid, EventErrorTypeListener listener){
        mDatabaseAPI.verificaExisteUsuario(uid, Constantes.DELEGADO, listener);
    }

    public void altaDelegado(UsuarioDelegado delegado, final BasicErrorEventCallback callback){
        mDatabaseAPI.altaDelegado(delegado, callback);
    }

    public void actualizarDelegado(UsuarioDelegado delegado, final BasicErrorEventCallback callback){
        mDatabaseAPI.altaDelegado(delegado, callback);
    }

    public void guardarEquipo(Equipo equipo, BasicErrorEventCallback callback){
        AdmSession mSession = AdmSession.getInstance();
        mDatabaseAPI.guardarEquipo(equipo, mSession.getIdCuentaSel(), mSession.getIdCanchaSel(),
                mSession.getIdLigaSel(), callback);

    }

}
