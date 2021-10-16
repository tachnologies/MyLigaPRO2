package com.tachnologies.myligapro.moduloAdm.nuevoJugador.model.dataAccess;

import android.net.Uri;

import com.tachnologies.myligapro.common.model.StorageUploadImageCallback;
import com.tachnologies.myligapro.common.model.dataAccess.FirebaseStorageAPI;
import com.tachnologies.myligapro.common.model.dataSession.AdmSession;

public class Storage {
    private FirebaseStorageAPI mStorageAPI;

    public Storage() {
        mStorageAPI = FirebaseStorageAPI.getInstance();
    }

    public void subirFotoJugador(Uri uriFoto, String equipoId, String jugadorId,
            StorageUploadImageCallback callback){

        AdmSession mSession = AdmSession.getInstance();
        mStorageAPI.subirFotoJugador( mSession.getIdCuentaSel(), mSession.getIdCanchaSel(),
                mSession.getIdLigaSel(), equipoId, jugadorId, uriFoto, callback );
    }

    public void eliminarFotoJugador(String jugadorId){
        mStorageAPI.eliminarFotoJugador(jugadorId);
    }

}
