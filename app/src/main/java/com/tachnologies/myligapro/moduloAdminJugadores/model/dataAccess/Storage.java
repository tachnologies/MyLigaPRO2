package com.tachnologies.myligapro.moduloAdminJugadores.model.dataAccess;

import com.tachnologies.myligapro.common.model.dataAccess.FirebaseStorageAPI;

public class Storage {
    private FirebaseStorageAPI mStorageAPI;

    public Storage() {
        mStorageAPI = FirebaseStorageAPI.getInstance();
    }

    public void eliminarFotoJugador(String urlFotoJugador){
        mStorageAPI.eliminarFotoJugador(urlFotoJugador);
    }
}
