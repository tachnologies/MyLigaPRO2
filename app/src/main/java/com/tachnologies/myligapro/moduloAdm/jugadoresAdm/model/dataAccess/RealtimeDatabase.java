package com.tachnologies.myligapro.moduloAdm.jugadoresAdm.model.dataAccess;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.tachnologies.myligapro.R;
import com.tachnologies.myligapro.common.callbacks.BasicDelegadoCallback;
import com.tachnologies.myligapro.common.callbacks.BasicErrorEventCallback;
import com.tachnologies.myligapro.common.model.dataAccess.FirebaseRealtimeDatabaseAPI;
import com.tachnologies.myligapro.common.model.dataSession.AdmSession;
import com.tachnologies.myligapro.common.pojo.Equipo;
import com.tachnologies.myligapro.common.pojo.UsuarioDelegado;
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

    public void eliminarEquipo(String uidEquipo, BasicErrorEventCallback callback){
        AdmSession mSession = AdmSession.getInstance();
        mDatabaseAPI.eliminarEquipo(uidEquipo, mSession.getIdCuentaSel(), mSession.getIdCanchaSel(),
                mSession.getIdLigaSel(),callback);
    }

    public void guardarEquipo(Equipo equipo, BasicErrorEventCallback callback){
        AdmSession mSession = AdmSession.getInstance();
        mDatabaseAPI.guardarEquipo(equipo, mSession.getIdCuentaSel(), mSession.getIdCanchaSel(),
                mSession.getIdLigaSel(), callback);

    }

    public void eliminarJugador(String uidJugador, BasicErrorEventCallback callback){
        AdmSession mSession = AdmSession.getInstance();
        mDatabaseAPI.eliminarJugador(mSession.getIdCuentaSel(), mSession.getIdCanchaSel(),
                mSession.getIdLigaSel(), mSession.getIdEquipoSel(), uidJugador, callback);
    }

    public void guardarDelegado(UsuarioDelegado delegado, BasicErrorEventCallback callback){
        mDatabaseAPI.altaDelegado(delegado, callback);
    }
}
