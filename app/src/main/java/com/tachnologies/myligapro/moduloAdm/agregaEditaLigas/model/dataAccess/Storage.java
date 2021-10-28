package com.tachnologies.myligapro.moduloAdm.agregaEditaLigas.model.dataAccess;

import android.net.Uri;

import com.tachnologies.myligapro.common.model.StorageUploadImageCallback;
import com.tachnologies.myligapro.common.model.dataAccess.FirebaseStorageAPI;
import com.tachnologies.myligapro.common.model.dataSession.AdmSession;

public class Storage {
    private FirebaseStorageAPI mStorageAPI;

    public Storage() {
        mStorageAPI = FirebaseStorageAPI.getInstance();
    }

    public void eliminarFotoLiga(String urlFotoLiga){
        mStorageAPI.eliminarFotoLiga(urlFotoLiga);
    }

    public void subirFotoLiga(String uidLiga, Uri uriFoto, StorageUploadImageCallback callback){
        AdmSession mSession = AdmSession.getInstance();

        mStorageAPI.subirFotoLiga(mSession.getIdCuentaSel(), mSession.getIdCanchaSel(), uidLiga,
                uriFoto, callback);
    }

}
