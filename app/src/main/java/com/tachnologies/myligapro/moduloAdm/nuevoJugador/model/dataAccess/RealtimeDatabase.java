package com.tachnologies.myligapro.moduloAdm.nuevoJugador.model.dataAccess;

import com.tachnologies.myligapro.common.callbacks.BasicErrorEventCallback;
import com.tachnologies.myligapro.common.model.dataAccess.FirebaseRealtimeDatabaseAPI;
import com.tachnologies.myligapro.common.model.dataSession.AdmSession;
import com.tachnologies.myligapro.common.pojo.Jugador;


public class RealtimeDatabase {
    private FirebaseRealtimeDatabaseAPI mDatabaseAPI;

    public RealtimeDatabase(){
        mDatabaseAPI = FirebaseRealtimeDatabaseAPI.getInstance();
    }

    public void guardarJugador(Jugador jugador, BasicErrorEventCallback callback){
        AdmSession mSession = AdmSession.getInstance();
        mDatabaseAPI.guardarJugador(jugador, mSession.getIdCuentaSel(), mSession.getIdCanchaSel(),
                mSession.getIdLigaSel(), mSession.getIdEquipoSel(), callback);
    }

    public void eliminarJugador(String uidJugador, BasicErrorEventCallback callback){
        AdmSession mSession = AdmSession.getInstance();
        mDatabaseAPI.eliminarJugador(mSession.getIdCuentaSel(), mSession.getIdCanchaSel(),
                mSession.getIdLigaSel(), mSession.getIdEquipoSel(), uidJugador, callback);
    }
}
