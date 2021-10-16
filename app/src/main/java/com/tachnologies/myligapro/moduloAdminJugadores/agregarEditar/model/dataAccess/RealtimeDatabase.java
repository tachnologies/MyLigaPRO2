package com.tachnologies.myligapro.moduloAdminJugadores.agregarEditar.model.dataAccess;

import com.tachnologies.myligapro.common.callbacks.BasicErrorEventCallback;
import com.tachnologies.myligapro.common.model.dataAccess.FirebaseRealtimeDatabaseAPI;
import com.tachnologies.myligapro.common.pojo.Jugador;


public class RealtimeDatabase {
    private FirebaseRealtimeDatabaseAPI mDatabaseAPI;

    public RealtimeDatabase(){
        mDatabaseAPI = FirebaseRealtimeDatabaseAPI.getInstance();
    }

    public void guardarJugador(Jugador jugador, String uidCuenta, String uidCancha, String uidLiga,
            String uidEquipo, BasicErrorEventCallback callback){
        mDatabaseAPI.guardarJugador(jugador, uidCuenta, uidCancha, uidLiga, uidEquipo, callback);
    }

    public void eliminarJugador(String uidCuenta, String uidCancha, String uidLiga, String uidEquipo,
            String uidJugador, BasicErrorEventCallback callback){
        mDatabaseAPI.eliminarJugador(uidCuenta, uidCancha, uidLiga, uidEquipo, uidJugador, callback);
    }
}
