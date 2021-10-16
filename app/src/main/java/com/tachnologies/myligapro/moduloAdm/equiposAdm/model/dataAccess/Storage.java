package com.tachnologies.myligapro.moduloAdm.equiposAdm.model.dataAccess;

import com.tachnologies.myligapro.common.model.dataAccess.FirebaseStorageAPI;
import com.tachnologies.myligapro.common.model.dataSession.AdmSession;

public class Storage {
    private FirebaseStorageAPI mStorageAPI;

    public Storage() {
        mStorageAPI = FirebaseStorageAPI.getInstance();
    }

    public void borrarCarpetaEquipo(String uidEquipo){
        AdmSession admSession = AdmSession.getInstance();
        mStorageAPI.borrarCarpetaEquipo( admSession.getIdCuentaSel(), admSession.getIdCanchaSel(),
                admSession.getIdLigaSel(), uidEquipo);
    }

}
