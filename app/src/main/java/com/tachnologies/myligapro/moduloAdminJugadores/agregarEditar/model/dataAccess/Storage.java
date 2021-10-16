package com.tachnologies.myligapro.moduloAdminJugadores.agregarEditar.model.dataAccess;

import android.net.Uri;

import com.tachnologies.myligapro.common.model.StorageUploadImageCallback;
import com.tachnologies.myligapro.common.model.dataAccess.FirebaseStorageAPI;

public class Storage {
    private FirebaseStorageAPI mStorageAPI;

    public Storage() {
        mStorageAPI = FirebaseStorageAPI.getInstance();
    }

    public void subirFotoJugador(String uidCuenta, String uidCancha, String uidLiga, String equipoId,
                String jugadorId, Uri uriFoto, StorageUploadImageCallback callback){

        mStorageAPI.subirFotoJugador( uidCuenta, uidCancha, uidLiga, equipoId, jugadorId, uriFoto,
                callback );
    }

    public void eliminarFotoJugador(String jugadorId){
        mStorageAPI.eliminarFotoJugador(jugadorId);
    }

}
