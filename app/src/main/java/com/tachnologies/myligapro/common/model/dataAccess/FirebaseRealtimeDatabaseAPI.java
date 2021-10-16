package com.tachnologies.myligapro.common.model.dataAccess;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tachnologies.myligapro.R;
import com.tachnologies.myligapro.common.callbacks.BasicDelegadoCallback;
import com.tachnologies.myligapro.common.callbacks.BasicErrorEventCallback;
import com.tachnologies.myligapro.common.callbacks.CallbackBasicoErrorConUid;
import com.tachnologies.myligapro.common.model.EventErrorTypeListener;
import com.tachnologies.myligapro.common.pojo.Cuenta;
import com.tachnologies.myligapro.common.pojo.Equipo;
import com.tachnologies.myligapro.common.pojo.Jugador;
import com.tachnologies.myligapro.common.pojo.RefEquipoDelegado;
import com.tachnologies.myligapro.common.pojo.UsuarioAdmin;
import com.tachnologies.myligapro.common.pojo.UsuarioDelegado;
import com.tachnologies.myligapro.common.utils.Constantes;
import com.tachnologies.myligapro.moduloLogin.events.LoginEvent;

import java.util.List;
import java.util.Map;

public class FirebaseRealtimeDatabaseAPI {

    private DatabaseReference mDatabaseReference;

    private static class SingletonHolder{
        private static final FirebaseRealtimeDatabaseAPI INSTANCE = new FirebaseRealtimeDatabaseAPI();
    }

    public static FirebaseRealtimeDatabaseAPI getInstance(){
        return SingletonHolder.INSTANCE;
    }

    private FirebaseRealtimeDatabaseAPI(){
        this.mDatabaseReference = FirebaseDatabase
                .getInstance()
                .getReference();
    }

    /**
     * References
     * */
    public DatabaseReference getReferenciaRaiz(){
        return mDatabaseReference.getRoot();
    }

    public DatabaseReference getReferenciaCuentas(){
        return mDatabaseReference.getRoot().child(Constantes.CUENTAS);
    }

    public DatabaseReference getReferenciaCuentaPorUid(String uidCuenta){
        return mDatabaseReference.getRoot().child(Constantes.CUENTAS).child(uidCuenta);
    }

    public DatabaseReference getReferenciaUsuarioAdm(){
        return mDatabaseReference.getRoot().child(Constantes.PATH_USUARIO_ADM);
    }

    public DatabaseReference getReferenciaUsuarioAdmPorUid(String uid){
        return mDatabaseReference.getRoot().child(Constantes.PATH_USUARIO_ADM).child(uid);
    }

    public DatabaseReference getReferenciaUsuarioDel(){
        return mDatabaseReference.getRoot().child(Constantes.PATH_USUARIO_DEL);
    }

    public DatabaseReference getReferenciaUsuarioDelPorUid(String uid){
        return mDatabaseReference.getRoot().child(Constantes.PATH_USUARIO_DEL).child(uid);
    }

    public DatabaseReference getReferenciaAEquipos(String uidCuenta, String uidCancha, String uidLiga){
        return mDatabaseReference.getRoot().child(Constantes.CUENTAS).child(uidCuenta)
                .child(Constantes.PATH_CANCHAS).child(uidCancha)
                .child(Constantes.PATH_LIGAS).child(uidLiga)
                .child(Constantes.PATH_EQUIPOS);
    }

    public DatabaseReference getReferenciaAEquiposPorId(String uidCuenta, String uidCancha,
            String uidLiga, String uidEquipo){
        return mDatabaseReference.getRoot()
                .child(Constantes.CUENTAS).child(uidCuenta)
                .child(Constantes.PATH_CANCHAS).child(uidCancha)
                .child(Constantes.PATH_LIGAS).child(uidLiga)
                .child(Constantes.PATH_EQUIPOS).child(uidEquipo);
    }

    public DatabaseReference getReferenciaAJugadorPorId(String uidCuenta, String uidCancha,
            String uidLiga, String uidEquipo, String uidJugador){

        return mDatabaseReference.getRoot()
                .child(Constantes.CUENTAS).child(uidCuenta)
                .child(Constantes.PATH_CANCHAS).child(uidCancha)
                .child(Constantes.PATH_LIGAS).child(uidLiga)
                .child(Constantes.PATH_EQUIPOS).child(uidEquipo)
                .child(Constantes.PATH_JUGADORES).child(uidJugador);
    }

    /**------------------------------------------ metodos comunes que me pueden ayudar*/
    public void verificaExisteUsuario(String uid, String tipoUsuario, EventErrorTypeListener listener){
        switch(tipoUsuario){
            case Constantes.ADMIN_LIGA:
                getReferenciaUsuarioAdmPorUid(uid)
                    .addListenerForSingleValueEvent( new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            UsuarioAdmin usuAdmin = snapshot.getValue(UsuarioAdmin.class);
                            if(usuAdmin == null){
                                listener.onError(LoginEvent.USUARIO_NO_EXISTE,
                                        R.string.common_usuario_nuevo);
                            }else{
                                usuAdmin.setUid(snapshot.getKey());
                                listener.usuarioAdminExistente(LoginEvent.USUARIO_EXISTE,
                                    R.string.common_usuario_existe, usuAdmin);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            listener.onError(LoginEvent.ERROR_SERVER, R.string.login_mensaje_error);
                        }
                });
                break;
            case Constantes.DELEGADO:
                getReferenciaUsuarioDelPorUid(uid).addListenerForSingleValueEvent(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            UsuarioDelegado usuDelegado = snapshot.getValue(UsuarioDelegado.class);

                            if(usuDelegado == null){
                                listener.onError(LoginEvent.USUARIO_NO_EXISTE, R.string.common_usuario_nuevo);
                            }else{
                                usuDelegado.setUid(snapshot.getKey());
                                listener.usuarioDelegadoExistente(LoginEvent.USUARIO_EXISTE,
                                    R.string.common_usuario_existe, usuDelegado);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            listener.onError(LoginEvent.ERROR_SERVER, R.string.login_mensaje_error);
                        }
                    });
                break;
            default:
                System.out.println("------------------------------ default ");
        }
    }

    public void buscarDelegado(String uidDelegado, BasicDelegadoCallback callback){
        getReferenciaUsuarioDelPorUid(uidDelegado).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UsuarioDelegado usuDelegado = snapshot.getValue(UsuarioDelegado.class);

                if(usuDelegado == null){
                    callback.onError(Constantes.DELEGADO_NO_EXISTE, R.string.common_delegado_no_existe);
                }else{
                    usuDelegado.setUid(snapshot.getKey());
                    callback.delegadoExistente(Constantes.DELEGADO_EXISTENTE, usuDelegado);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onError(LoginEvent.ERROR_SERVER, R.string.login_mensaje_error);
            }
        });
    }

    public void altaCuenta(Cuenta cuenta, final CallbackBasicoErrorConUid callback){
        guardarConRetorno(getReferenciaCuentas(), cuenta, callback);
    }

    public void altaDelegado(UsuarioDelegado delegado, final BasicErrorEventCallback callback){
        Map<String, Object> valores = delegado.getObjectoParaInsertar();
        DatabaseReference referenciaDelegado = getReferenciaUsuarioDelPorUid(delegado.getUid());

        guardar(referenciaDelegado, valores, callback);
    }

    public void altaAdminLiga(UsuarioAdmin admin, final BasicErrorEventCallback callback){
        Map<String, Object> valores = admin.getObjectoParaInsertar();
        DatabaseReference referenciaAdmin = getReferenciaUsuarioAdmPorUid(admin.getUid());

        guardar(referenciaAdmin, valores, callback);

    }

    public void guardarEquipo(Equipo equipo, String idCuenta, String idCancha, String idLiga,
                              final BasicErrorEventCallback callback){

        Map<String, Object> valores = equipo.getObjectoParaInsertar();
        DatabaseReference referenciaEquipo = getReferenciaAEquiposPorId(idCuenta, idCancha, idLiga,
                equipo.getUid());

        guardar(referenciaEquipo, valores, callback);

    }

    public void guardarJugador(Jugador jugador, String idCuenta, String idCancha, String idLiga,
        String idEquipo, final BasicErrorEventCallback callback){

        Map<String, Object> valores = jugador.getObjectoParaInsertar();
        DatabaseReference referenciaEquipo = getReferenciaAJugadorPorId(idCuenta, idCancha, idLiga,
                idEquipo, jugador.getUid());

        guardar(referenciaEquipo, valores, callback);

    }



    /** Metodo hecho para obtener el mensaje a mostrar cuando hay un error al guardar, esto deberia
     * ser global para todos los guardados*/
    public int getMsjeErrorGuardar(int errorCode){
        switch (errorCode){
            case DatabaseError.NETWORK_ERROR:
                return R.string.common_error_internet;
            case DatabaseError.PERMISSION_DENIED:
                return R.string.common_error_no_permisos;
            case DatabaseError.DISCONNECTED:
                return R.string.common_error_desconectado;
            case DatabaseError.OPERATION_FAILED:
                return R.string.common_error_operacion;
            default:
                return R.string.error_servidor_estandar;
        }
    }

    public void guardar(DatabaseReference referencia, Map<String, Object> valores,
                        final BasicErrorEventCallback callback){

        referencia.updateChildren(valores)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        callback.onSuccess();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callback.onError(Constantes.ERROR_SERVIDOR, R.string.error_servidor_estandar);
            }
        });
    }

    public void guardarSinCallback(DatabaseReference referencia, Map<String, Object> valores){
        referencia.updateChildren(valores);
    }

    public void eliminarEquipo(String uidEquipo, String uidCuenta, String uidCancha, String uidLiga,
                               final BasicErrorEventCallback callback){
        DatabaseReference referencia = getReferenciaAEquiposPorId(uidCuenta, uidCancha, uidLiga,
                uidEquipo);
        eliminar(referencia, callback);
    }

    public void eliminarJugador(String uidCuenta, String uidCancha, String uidLiga, String uidEquipo,
            String uidJugador,final BasicErrorEventCallback callback){
        DatabaseReference referencia = getReferenciaAJugadorPorId(uidCuenta, uidCancha, uidLiga,
                uidEquipo, uidJugador);
        eliminar(referencia, callback);
    }

    private void eliminar(DatabaseReference referencia, final BasicErrorEventCallback callback){
        referencia.removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                if (error == null){
                    callback.onSuccess();
                } else {
                    int resMsje = getMsjeErrorGuardar(error.getCode());
                    callback.onError(Constantes.ERROR_SERVIDOR, resMsje);
                }
            }
        });
    }

    public void eliminaRefEquipoEnDelegado(String uidDelegado, String uidEquipo){
        verificaExisteUsuario(uidDelegado, Constantes.DELEGADO, new EventErrorTypeListener() {
            @Override
            public void onError(int typeEvent, int resMsg) {
                System.out.println("----------------------------- error al consultar el usuario");
            }

            @Override
            public void usuarioAdminExistente(int typeEvent, int resMsg, UsuarioAdmin admin) {}

            @Override
            public void usuarioDelegadoExistente(int typeEvent, int resMsg, UsuarioDelegado delegado) {
                List<RefEquipoDelegado> equipos = delegado.getEquipos();
                int index = -1;

                for(int i = 0; i<equipos.size(); i++){
                    if(uidEquipo.equals(equipos.get(i).getUidEquipo())){
                        index = i;
                        break;
                    }
                }

                if(index != -1){
                    equipos.remove(index);
                }

                delegado.setEquipos(equipos);
                Map<String, Object> valores = delegado.getObjectoParaInsertar();
                DatabaseReference referenciaDelegado = getReferenciaUsuarioDelPorUid(delegado.getUid());

                guardarSinCallback(referenciaDelegado, valores);
            }
        });
    }

    public void guardarConRetorno(DatabaseReference referencia, Object objeto,
                                  final CallbackBasicoErrorConUid callback){

        referencia.push().setValue(objeto, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError == null){
                    callback.onSuccess(databaseReference.getKey());
                } else {
                    int resMsje = getMsjeErrorGuardar(databaseError.getCode());
                    callback.onError(Constantes.ERROR_SERVIDOR, resMsje);
                }
            }
        });

    }

}
