package com.tachnologies.myligapro.moduloAdm.agregaEditaLigas.model;

import android.net.Uri;
import com.tachnologies.myligapro.common.pojo.Liga;

public interface AgregaEditaLigasInteractor {
    void guardarLiga(Liga liga);
    void eliminarLiga(String uidLiga);
    void eliminarFotoLiga(String urlFotoLiga);
    void subirFotoLiga(String uidLiga, Uri uriFoto);
}
