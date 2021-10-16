package com.tachnologies.myligapro.moduloLogin.model.dataAccess;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.tachnologies.myligapro.R;
import com.tachnologies.myligapro.common.model.EventErrorTypeListener;
import com.tachnologies.myligapro.common.model.dataAccess.FirebaseRealtimeDatabaseAPI;
import com.tachnologies.myligapro.common.pojo.UsuarioAdmin;
import com.tachnologies.myligapro.common.pojo.UsuarioDelegado;
import com.tachnologies.myligapro.common.utils.Constantes;
import com.tachnologies.myligapro.moduloLogin.events.LoginEvent;

public class RealtimeDatabase {
    private FirebaseRealtimeDatabaseAPI mDatabaseAPI;

    public RealtimeDatabase() {
        mDatabaseAPI = FirebaseRealtimeDatabaseAPI.getInstance();
    }

    public void verificaExisteUsuario(String uid, String tipoUsuario, EventErrorTypeListener listener){
        System.out.println("------------------------------ checkUserExist ");
        mDatabaseAPI.verificaExisteUsuario(uid, tipoUsuario, listener);
        /*switch(tipoUsuario){
            case Constantes.ADMIN_LIGA:
                System.out.println("------------------------------ Constantes.ADMIN_LIGA: " + Constantes.ADMIN_LIGA);

                mDatabaseAPI.getReferenciaUsusarioAdmPorUid(uid)
                        .addListenerForSingleValueEvent( new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        System.out.println("------------------------------ onDataChange ");
                        UsuarioAdmin usuAdmin = snapshot.getValue(UsuarioAdmin.class);

                        if(usuAdmin == null){
                            listener.onError(LoginEvent.USUARIO_NO_EXISTE, R.string.common_usuario_nuevo);
                        }else{
                            usuAdmin.setUid(snapshot.getKey());
                            listener.usuarioAdminExistente(LoginEvent.USUARIO_EXISTE,
                                    R.string.common_usuario_existe, usuAdmin);
                            //listener.onError(LoginEvent.USUARIO_EXISTE, R.string.common_usuario_existe);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        System.out.println("------------------------------ onCancelled ");
                        listener.onError(LoginEvent.ERROR_SERVER, R.string.login_mensaje_error);
                    }
                });
                break;
            case Constantes.DELEGADO:
                mDatabaseAPI.getReferenciaUsuarioDelPorUid(uid).addListenerForSingleValueEvent(
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
                            //listener.onError(LoginEvent.USUARIO_EXISTE, R.string.common_usuario_existe);
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
        }*/
        System.out.println("------------------------------ termina checkuserExist");
    }

    /*public void setAdminLogueado(UsuarioAdmin adminLogeado){
        mDatabaseAPI.setAdminLogeado(adminLogeado);
    }

    public void setDelegadoLogueado(UsuarioDelegado delegadoLogeado){
        mDatabaseAPI.setDelegadoLogeado(delegadoLogeado);
    }*/
    //public FirebaseRealtimeDatabaseAPI getmDatabaseAPI() {
        //return mDatabaseAPI;
   // }

}
