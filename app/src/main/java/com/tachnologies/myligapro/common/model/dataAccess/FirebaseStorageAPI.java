package com.tachnologies.myligapro.common.model.dataAccess;

import android.net.Uri;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.tachnologies.myligapro.R;
import com.tachnologies.myligapro.common.callbacks.BasicErrorEventCallback;
import com.tachnologies.myligapro.common.model.StorageUploadImageCallback;
import com.tachnologies.myligapro.common.utils.Constantes;

public class FirebaseStorageAPI {
    private FirebaseStorage mFirebaseStorage;

    private static class Singleton{
        private static final FirebaseStorageAPI INSTANCE = new FirebaseStorageAPI();
    }

    public static FirebaseStorageAPI getInstance(){
        return Singleton.INSTANCE;
    }

    private FirebaseStorageAPI(){
        this.mFirebaseStorage = FirebaseStorage.getInstance();
    }


    public FirebaseStorage getmFirebaseStorage() {
        return mFirebaseStorage;
    }


    public StorageReference getReferenciaCanchaPorUid(String uidCuenta, String uidCancha){
        return mFirebaseStorage.getReference()
                .child(Constantes.PATH_CUENTAS)
                .child(uidCuenta)
                .child(Constantes.PATH_CANCHAS)
                .child(uidCancha);
    }

    public StorageReference getReferenciaLigaPorUid(String uidCuenta, String uidCancha, String uidLiga){
        return  mFirebaseStorage.getReference()
                .child(Constantes.PATH_CUENTAS)
                .child(uidCuenta)
                .child(Constantes.PATH_CANCHAS)
                .child(uidCancha)
                .child(Constantes.PATH_LIGAS)
                .child(uidLiga);
    }

    public StorageReference getReferenciaEquipoPorUid(String uidCuenta, String uidCancha, String uidLiga,
                                                      String uidEquipo){
        return  mFirebaseStorage.getReference()
                .child(Constantes.PATH_CUENTAS)
                .child(uidCuenta)
                .child(Constantes.PATH_CANCHAS)
                .child(uidCancha)
                .child(Constantes.PATH_LIGAS)
                .child(uidLiga)
                .child(Constantes.PATH_EQUIPOS)
                .child(uidEquipo)
                .child(Constantes.PATH_ESCUDO);
    }

    public StorageReference getReferenciaJugadorPorUid(String uidCuenta, String uidCancha, String uidLiga,
                                                      String uidEquipo, String uidJugador){
        return  mFirebaseStorage.getReference()
                .child(Constantes.PATH_CUENTAS)
                .child(uidCuenta)
                .child(Constantes.PATH_CANCHAS)
                .child(uidCancha)
                .child(Constantes.PATH_LIGAS)
                .child(uidLiga)
                .child(Constantes.PATH_EQUIPOS)
                .child(uidEquipo)
                .child(Constantes.PATH_JUGADORES)
                .child(uidJugador);
    }

    public void eliminarFotoLiga(String urlFotoLiga){
        StorageReference referencia = mFirebaseStorage.getReferenceFromUrl(urlFotoLiga);
        if(referencia != null){
            borrarSinCallback(referencia);
        }
    }

    public void borrarFotoEquipo(String uidCuenta, String uidCancha, String uidLiga,
                                    String uidEquipo){
        StorageReference referencia = getReferenciaEquipoPorUid(uidCuenta, uidCancha, uidLiga,
                uidEquipo);

        if(referencia != null){
            borrarSinCallback(referencia);
        }
    }

    public void eliminarFotoJugador(String urlFotoJugador){

        StorageReference referencia = mFirebaseStorage.getReferenceFromUrl(urlFotoJugador);
        if(referencia != null){
            borrarSinCallback(referencia);
            /*referencia.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                }
            });*/
        }
    }

    public void subirFotoLiga(String uidCuenta, String uidCancha, String uidLiga, Uri uriFoto,
                              StorageUploadImageCallback callback){

        final StorageReference referencia = getReferenciaLigaPorUid(uidCuenta, uidCancha, uidLiga);
        guardarImagenConCallback(referencia, uriFoto, callback);
    }

    public void subirFotoJugador(String uidCuenta, String uidCancha, String uidLiga, String uidEquipo,
            String uidJugador, Uri uriFoto, StorageUploadImageCallback callback){

        final StorageReference referencia =
                getReferenciaJugadorPorUid(uidCuenta, uidCancha, uidLiga, uidEquipo, uidJugador);

        guardarImagenConCallback(referencia, uriFoto, callback);
    }

    public void subirEscudoEquipo(String uidCuenta, String uidCancha, String uidLiga,
            String uidEquipo, Uri uriFoto, StorageUploadImageCallback callback){

        final StorageReference referencia =
                getReferenciaEquipoPorUid(uidCuenta, uidCancha, uidLiga, uidEquipo);

        guardarImagenConCallback(referencia, uriFoto, callback);
    }

    public void guardarImagenConCallback(StorageReference referencia, Uri uriFoto,
            StorageUploadImageCallback callback){
        try {
            referencia.putFile(uriFoto)
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
    }

    public void borrarCarpetaEquipo(String uidCuenta, String uidCancha, String uidLiga,
                                    String uidEquipo){
        StorageReference referencia = getReferenciaEquipoPorUid(uidCuenta, uidCancha, uidLiga,
                uidEquipo);

        if(referencia != null){
            borrarSinCallback(referencia);
        }
    }

    public void borrarConCallback(StorageReference referencia, final BasicErrorEventCallback callback){
        referencia.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                callback.onSuccess();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                callback.onError(Constantes.ERROR_SERVIDOR, R.string.common_error_borrado);
            }
        });
    }

    public void borrarSinCallback(StorageReference referencia){
        referencia.delete();
    }
}
