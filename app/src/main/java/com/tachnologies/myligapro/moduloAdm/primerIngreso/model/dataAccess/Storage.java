package com.tachnologies.myligapro.moduloAdm.primerIngreso.model.dataAccess;

import android.net.Uri;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.tachnologies.myligapro.R;
import com.tachnologies.myligapro.common.model.StorageUploadImageCallback;
import com.tachnologies.myligapro.common.model.dataAccess.FirebaseStorageAPI;
import com.tachnologies.myligapro.common.utils.Constantes;

import java.util.Map;

public class Storage {
    private FirebaseStorageAPI mStorageAPI;

    public Storage() {
        mStorageAPI = FirebaseStorageAPI.getInstance();
    }

    /** Tratare de hacer la subida de imagenes solo 1 metodo*/
    public void subirImagenGeneral(Uri imageUri, Map<String, String> uids, String tipoEntidad,
                                  StorageUploadImageCallback callback){
        if(imageUri.getLastPathSegment() != null){
            StorageReference refFoto = obtenerReferencia(uids, tipoEntidad);

            try {
                refFoto.putFile(imageUri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                taskSnapshot.getStorage().getDownloadUrl()
                                        .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {
                                                if (uri != null){
                                                    callback.onSuccess(uri);
                                                }else {
                                                    callback.onError(R.string.common_error_subir_imagen);
                                                }
                                            }
                                        });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        e.printStackTrace();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }

        }else{
            System.out.println("--------------------- nno hay LastPathSegment() :V");
        }
    }

    public void borrarImagen(String urlVieja, String uriDescarga){
        if(urlVieja != null && !urlVieja.isEmpty()){
            StorageReference storageReference = mStorageAPI.getmFirebaseStorage()
                .getReferenceFromUrl(uriDescarga);

            StorageReference oldStorageReference = null;

            try {
                oldStorageReference = mStorageAPI.getmFirebaseStorage().getReferenceFromUrl(urlVieja);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if(oldStorageReference != null && !oldStorageReference.getPath()
                .equals(storageReference.getPath())){
                oldStorageReference.delete()
                    .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //por si falla aqui podriamos volver a reintentar el borrado

                    }
                    })
                ;
            }

        }
    }

    private StorageReference obtenerReferencia(Map<String, String> uids, String tipoEntidad){
        StorageReference refFoto = null;
        String uidCuenta  = uids.get(Constantes.UID_CUENTA);
        String uidCancha  = uids.get(Constantes.UID_CANCHA);
        String uidLiga    = uids.get(Constantes.UID_LIGA);
        String uidEquipo  = uids.get(Constantes.UID_EQUIPO);
        String uidJugador = uids.get(Constantes.UID_JUGADOR);

        switch(tipoEntidad){
            case Constantes.CANCHA:
                refFoto = mStorageAPI.getReferenciaCanchaPorUid(uidCuenta, uidCancha)
                        .child(Constantes.PATH_FOTOS);
                break;
            case Constantes.LIGA:
                System.out.println("--------------------- uidLiga:" + uidLiga);
                refFoto = mStorageAPI.getReferenciaLigaPorUid(uidCuenta, uidCancha, uidLiga)
                        .child(Constantes.PATH_FOTOS);
                break;
            case Constantes.EQUIPO:
                refFoto = mStorageAPI.getReferenciaEquipoPorUid(uidCuenta, uidCancha, uidLiga,
                        uidEquipo
                ).child(Constantes.PATH_FOTOS);
                break;
            case Constantes.JUGADOR:
                refFoto = mStorageAPI.getReferenciaJugadorPorUid(uidCuenta, uidCancha, uidLiga,
                        uidEquipo,uidJugador
                ).child(Constantes.PATH_FOTOS);
                break;
            case Constantes.DELEGADO: case Constantes.ADMIN_LIGA:
                break;
            default:
        }
        return refFoto;
    }
    
}
