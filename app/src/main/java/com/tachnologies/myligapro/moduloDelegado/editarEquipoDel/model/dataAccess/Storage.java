package com.tachnologies.myligapro.moduloDelegado.editarEquipoDel.model.dataAccess;

import android.net.Uri;

import com.tachnologies.myligapro.common.model.StorageUploadImageCallback;
import com.tachnologies.myligapro.common.model.dataAccess.FirebaseStorageAPI;
import com.tachnologies.myligapro.common.model.dataSession.DelSession;

public class Storage {
    private FirebaseStorageAPI mStorageAPI;

    public Storage() {
        mStorageAPI = FirebaseStorageAPI.getInstance();
    }

    public void subirEscudoEquipo(Uri uriFoto, String equipoId,
                                  StorageUploadImageCallback callback){
        DelSession mSession = DelSession.getInstance();

        mStorageAPI.subirEscudoEquipo( mSession.getIdCuentaSel(), mSession.getIdCanchaSel(),
                mSession.getIdLigaSel(), equipoId, uriFoto, callback );

    }

    public void eliminarFotoEquipo(String uidEquipo){
        DelSession mSession = DelSession.getInstance();

        mStorageAPI.borrarFotoEquipo(mSession.getIdCuentaSel(), mSession.getIdCanchaSel(),
                mSession.getIdLigaSel(), uidEquipo);
    }

}
