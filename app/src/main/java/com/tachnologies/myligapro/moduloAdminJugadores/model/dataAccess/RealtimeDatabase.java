package com.tachnologies.myligapro.moduloAdminJugadores.model.dataAccess;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.tachnologies.myligapro.R;
import com.tachnologies.myligapro.common.callbacks.BasicErrorEventCallback;
import com.tachnologies.myligapro.common.model.dataAccess.FirebaseRealtimeDatabaseAPI;
import com.tachnologies.myligapro.common.pojo.Equipo;
import com.tachnologies.myligapro.common.pojo.Jugador;
import com.tachnologies.myligapro.common.utils.Constantes;
import com.tachnologies.myligapro.moduloAdm.equiposAdm.events.BuscarEquipoListener;
import com.tachnologies.myligapro.moduloLogin.events.LoginEvent;

public class RealtimeDatabase {
    private FirebaseRealtimeDatabaseAPI mDatabaseAPI;

    public RealtimeDatabase(){
        mDatabaseAPI = FirebaseRealtimeDatabaseAPI.getInstance();
    }

    public void buscarEquipo(String uidEquipo, String uidCuenta, String uidCancha, String uidLiga,
                             BuscarEquipoListener listener){

        mDatabaseAPI.getReferenciaAEquiposPorId(uidCuenta, uidCancha, uidLiga,
                uidEquipo).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Equipo equipo = snapshot.getValue(Equipo.class);

                if(equipo == null){
                    listener.onError(Constantes.EQUIPO_NO_EXISTE,
                            R.string.equipos_adm_equipo_no_existe);
                }else{
                    equipo.setUid(snapshot.getKey());
                    listener.equipoExiste(Constantes.EQUIPO_EXISTE,
                            R.string.equipos_adm_equipo_existe, equipo);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                listener.onError(LoginEvent.ERROR_SERVER, R.string.common_error_consulta_datos);
            }
        });
    }

    public void eliminarJugador(String uidCuenta, String uidCancha, String uidLiga, String uidEquipo,
                                String uidJugador, BasicErrorEventCallback callback){
        mDatabaseAPI.eliminarJugador(uidCuenta, uidCancha, uidLiga, uidEquipo, uidJugador, callback);
    }

}
