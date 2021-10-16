package com.tachnologies.myligapro.common.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.view.View;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.tachnologies.myligapro.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/** Esta clase servira para realizar trabajos que puedan ser usados en el proyecto*/
public class Utilidades {

    /* Mostrar mensajes con Snackbar*/
    public static void mostrarSnackbar(View contentMain, int resMsg, int duration) {
        Snackbar.make(contentMain, resMsg, duration).show();
    }

    /* static void showSnackbar(View contentMain, int resMsg) {
        showSnackbar(contentMain, resMsg, Snackbar.LENGTH_LONG);
    }
    */

    /** regresar la fecha del dia en formato DD-MM-YYYY*/
    public static String fechaHoyFormateada(){
        return new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ROOT).format(new Date());
    }

    public static String obtenerStringTimestamp(){
        Timestamp timestamp = new Timestamp(new Date().getTime());
        return String.valueOf(timestamp.getTime());
    }

    /** reducir imagenes*/
    public static Bitmap reducirBitmap(Context context, View container, Uri uri, int maxAncho,
                                      int maxAlto){
        try{
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri),
                    null, options);
            options.inSampleSize = (int)Math.max(
                    Math.ceil(options.outWidth / maxAncho),
                    Math.ceil(options.outHeight / maxAlto));
            options.inJustDecodeBounds = false;
            return BitmapFactory.decodeStream(context.getContentResolver()
                    .openInputStream(uri), null, options);
        }catch (FileNotFoundException e){
            e.printStackTrace();
            Snackbar.make(container, R.string.common_archivo_no_encontrado,
                    BaseTransientBottomBar.LENGTH_LONG).show();
            //UtilsCommon.showSnackbar(container, R.string.profile_error_notfound);
            return null;
        }
    }

    /** borrar archivos temporales*/
    public static void borrarArchivosTemporales(File file) {
        if (file.isDirectory()){
            File[] files = file.listFiles();
            if (files != null){
                for (File f : files){
                    if (f.isDirectory()) {
                        borrarArchivosTemporales(f);
                    } else {
                        f.delete();
                    }
                }
            }
        }
    }

    public static boolean checarPermisosGaleria(String permissionStr, int requestPermission, Context context,
            Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, permissionStr) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, new String[]{permissionStr}, requestPermission);
                return false;
            }
        }
        return true;
    }

}
