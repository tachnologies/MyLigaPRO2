package com.tachnologies.myligapro.moduloAdm.equiposAdm.model.dataAccess;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.tachnologies.myligapro.R;
import com.tachnologies.myligapro.common.callbacks.BasicErrorEventCallback;
import com.tachnologies.myligapro.common.model.dataAccess.FirebaseRealtimeDatabaseAPI;
import com.tachnologies.myligapro.common.model.dataSession.AdmSession;
import com.tachnologies.myligapro.common.pojo.Equipo;
import com.tachnologies.myligapro.common.pojo.RefDelegadoAdm;
import com.tachnologies.myligapro.common.utils.Constantes;
import com.tachnologies.myligapro.moduloAdm.equiposAdm.events.BuscarEquipoListener;
import com.tachnologies.myligapro.moduloAdm.equiposAdm.events.EquiposAdmEventListener;
import com.tachnologies.myligapro.moduloLogin.events.LoginEvent;

import java.util.List;

public class RealtimeDatabase {
    private FirebaseRealtimeDatabaseAPI mDatabaseAPI;
    private ChildEventListener mEquiposEventListener;

    public RealtimeDatabase() {
        mDatabaseAPI = FirebaseRealtimeDatabaseAPI.getInstance();
    }

    public void subscribirAEquipos(String uidCuenta, String uidCancha, String uidLiga,
                                   final EquiposAdmEventListener listener){

        if(mEquiposEventListener == null){
            mEquiposEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    listener.equipoAgregado(getEquipo(snapshot));
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    listener.equipoActualizado(getEquipo(snapshot));
                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                    listener.equipoEliminado(getEquipo(snapshot));
                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    switch (error.getCode()){
                        case DatabaseError.PERMISSION_DENIED:
                            listener.onError(R.string.error_servidor_permisos);
                            break;
                        default:
                            listener.onError(R.string.error_servidor_estandar);
                    }
                }
            };
        }

        mDatabaseAPI.getReferenciaAEquipos(uidCuenta, uidCancha, uidLiga)
                .addChildEventListener(mEquiposEventListener);
    }

    public void quitarSubscripcionAEquipos(String uidCuenta, String uidCancha, String uidLiga){
        if (mEquiposEventListener != null){
            mDatabaseAPI.getReferenciaAEquipos(uidCuenta, uidCancha, uidLiga)
                    .removeEventListener(mEquiposEventListener);
        }
    }

    private Equipo getEquipo(DataSnapshot dataSnapshot) {
        Equipo equipo = dataSnapshot.getValue(Equipo.class);
        if (equipo != null){
            equipo.setUid(dataSnapshot.getKey().toString());
        }
        return equipo;
    }

    public void buscarEquipo(String uidEquipo, BuscarEquipoListener listener){
        AdmSession mSession = AdmSession.getInstance();
        mDatabaseAPI.getReferenciaAEquiposPorId(mSession.getIdCuentaSel(), mSession.getIdCanchaSel(),
            mSession.getIdLigaSel(), uidEquipo).addListenerForSingleValueEvent(new ValueEventListener() {
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

    public void eliminaRefEquipoEnDelegados(String uidEquipo, List<RefDelegadoAdm> delegados,
                                            BasicErrorEventCallback callback){
        for(RefDelegadoAdm refDelegado:delegados){
            mDatabaseAPI.eliminaRefEquipoEnDelegado(refDelegado.getUid(), uidEquipo);
        }

        callback.onSuccess();

    }

    public void eliminarEquipo(String uidEquipo, final BasicErrorEventCallback callback){
        AdmSession mSession = AdmSession.getInstance();
        mDatabaseAPI.eliminarEquipo(uidEquipo, mSession.getIdCuentaSel(), mSession.getIdCanchaSel(),
                mSession.getIdLigaSel(),callback);
    }
}
