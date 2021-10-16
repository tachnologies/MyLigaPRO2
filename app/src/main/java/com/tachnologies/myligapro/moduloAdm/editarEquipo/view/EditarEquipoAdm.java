package com.tachnologies.myligapro.moduloAdm.editarEquipo.view;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.snackbar.Snackbar;
import com.tachnologies.myligapro.R;
import com.tachnologies.myligapro.common.animaciones.cargando.CargandoDialog;
import com.tachnologies.myligapro.common.model.dataSession.AdmSession;
import com.tachnologies.myligapro.common.pojo.Equipo;
import com.tachnologies.myligapro.common.pojo.RefDelegadoAdm;
import com.tachnologies.myligapro.common.pojo.RefEquipoDelegado;
import com.tachnologies.myligapro.common.utils.Constantes;
import com.tachnologies.myligapro.common.utils.Utilidades;
import com.tachnologies.myligapro.moduloAdm.editarEquipo.EditarEquipoAdmPresenter;
import com.tachnologies.myligapro.moduloAdm.editarEquipo.EditarEquipoAdmPresenterClass;
import com.tachnologies.myligapro.moduloAdm.equiposAdm.view.EquiposAdm;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditarEquipoAdm extends AppCompatActivity implements EditarEquipoAdmView {
    @BindView(R.id.tvTitulo) TextView tvTitulo;
    @BindView(R.id.imgFoto) AppCompatImageView imgFoto;
    @BindView(R.id.imgBorrarFoto) AppCompatImageView imgBorrarFoto;
    @BindView(R.id.imgDesdeGaleria) AppCompatImageView imgDesdeGaleria;
    @BindView(R.id.etNombre) EditText etNombre;
    @BindView(R.id.cbDelegado) CheckBox cbDelegado;
    @BindView(R.id.etNombreDelegado) EditText etNombreDelegado;
    @BindView(R.id.spLadas) Spinner spLadas;
    @BindView(R.id.etTelDelegado) EditText etTelDelegado;
    @BindView(R.id.btnCancelar) Button btnCancelar;
    @BindView(R.id.btnGuardar) Button btnGuardar;
    @BindView(R.id.btnEliminar) Button btnEliminar;
    @BindView(R.id.contentMain) LinearLayout contentMain;
    @BindView(R.id.llDelegado) LinearLayout llDelegado;

    /**para lo del gif*/
    private CargandoDialog cargando;
    private EditarEquipoAdmPresenter mPresenter;
    private Equipo equipoEditar;
    /** para la foto del equipo*/
    private Uri uriPhoto;

    /** para no volver a guardar la foto del equipo si no se modifico
     * 0 = sin editar
     * 1 = cambio foto
     * 2 = quito foto*/
    private int editoFoto;

    /** para ver si al delegado se le hizo un cambio, si se cambio de numero de telefono, o
     * elimino al delegado
     * 0 = sin cambios
     * 1 = edito nombre
     * 2 = cambio numero de telefono
     * 3 = elimino al delegado
     * 4 = agrego delegado
     * */
    private int editoDelegado;
    private String uidDelegadoAnterior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_equipo_adm);
        ButterKnife.bind(this);
        bloquearPantalla(R.string.common_cargando);
        editoFoto = 0;
        editoDelegado = 0;

        AdmSession mSession = AdmSession.getInstance();

        mPresenter = new EditarEquipoAdmPresenterClass(this);
        mPresenter.onShow();
        mPresenter.consultarEquipo(mSession.getIdEquipoSel());
    }

    @Override
    public void onDestroy() {
        mPresenter.onDestroy();
        super.onDestroy();
    }

    private void cargaFotoDeBD(String urlFoto) {
        uriPhoto = Uri.parse(urlFoto);

        RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop();

        Glide.with(this)
                .load(urlFoto)
                .apply(options)
                .into(imgFoto);
    }

    /**
     * ------------------------------------ metodos del view EditarEquipoAdmView
     */
    @Override
    public void desbloquearPantalla() {
        cargando.dismiss();
    }

    @Override
    public void bloquearPantalla(int resMsje) {
        cargando = new CargandoDialog(resMsje);
        cargando.show(getSupportFragmentManager(), Constantes.CADENA_VACIA);
    }

    @Override
    public void llenaInfoEquipo(Equipo equipo) {
        tvTitulo.setText("Editar Equipo");
        equipoEditar = equipo;
        etNombre.setText(equipo.getNombre());

        if(equipo.getUrlFoto() != null && !equipo.getUrlFoto().isEmpty()){
            cargaFotoDeBD(equipo.getUrlFoto());
        }

        if (equipo.getDelegados() != null && equipo.getDelegados().size() > 0) {
            cbDelegado.setChecked(true);
            String lada = equipo.getDelegados().get(0).getLada();
            String[] arrayLadas =  getResources().getStringArray(R.array.ladasValues);
            int index = -1;

            if(arrayLadas != null && arrayLadas.length > 1){
                for(int i=0; i<arrayLadas.length; i++){
                    if(lada.equals(arrayLadas[i])){
                        index = i;
                        break;
                    }
                }
                spLadas.setSelection(index);
            }

            lada = Constantes.ESCAPE + lada;

            String uidDelegado = equipo.getDelegados().get(0).getUid();
            String[] partes = uidDelegado.split(lada);
            String telefono = "";

            if (partes != null && partes.length > 1) {
                telefono = partes[1];
            }

            etNombreDelegado.setText(equipo.getDelegados().get(0).getNombre());
            etTelDelegado.setText(telefono);
        } else {
            llDelegado.setVisibility(View.GONE);
        }

        desbloquearPantalla();
    }

    private void limpiarFoto() {
        editoFoto = 2;
        uriPhoto = null;
        RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop();

        Glide.with(this)
                .load(R.drawable.escudo_equipo)
                .apply(options)
                .into(imgFoto);
    }

    @Override
    public void mostrarError(int resMsg) {
        Snackbar.make(contentMain, resMsg, Snackbar.LENGTH_LONG).show();
        desbloquearPantalla();
    }

    @Override
    public void abrirGaleria() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, Constantes.RC_FOTO_EQUIPO_PICKER);
    }

    @Override
    public void setImagen(Intent data) {
        editoFoto = 1;
        Bundle bundle = data.getExtras();
        uriPhoto = Uri.parse("");
        if (bundle == null || bundle.isEmpty()) {
            uriPhoto = Uri.parse(data.getDataString());
        }

        int sizeImagePreview = getResources().getDimensionPixelSize(R.dimen.nuevo_equipo_alto_img);
        Bitmap bitmap = Utilidades.reducirBitmap(this, contentMain, uriPhoto,
                sizeImagePreview, sizeImagePreview);

        if (bitmap != null) {
            imgFoto.setImageBitmap(bitmap);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPresenter.resultadoActivity(requestCode, resultCode, data);
    }

    @Override
    public void guardarEquipo(String urlFotoEquipo) {
        equipoEditar.setUrlFoto(urlFotoEquipo);
        String nombreEquipo = etNombre.getText().toString().trim();
        equipoEditar.setNombre(nombreEquipo);
        uidDelegadoAnterior = "";
        switch(editoDelegado){
            case 1:     //edito nombre
            case 2:     //cambio numero de telefono
            case 4:     //agrego delegado
                if(editoDelegado == 2){
                    uidDelegadoAnterior = equipoEditar.getDelegados().get(0).getUid();
                }

                String nombreDelegado = etNombreDelegado.getText().toString().trim();
                String celularDelegado = etTelDelegado.getText().toString().trim();
                String lada = getResources()
                        .getStringArray(R.array.ladasValues)[spLadas.getSelectedItemPosition()];
                List<RefDelegadoAdm> delegados = new ArrayList<RefDelegadoAdm>();
                delegados.add(new RefDelegadoAdm(nombreDelegado, "",
                        lada + celularDelegado, lada));
                equipoEditar.setDelegados(delegados);
                break;
            case 3:     //elimino al delegado
                uidDelegadoAnterior = equipoEditar.getDelegados().get(0).getUid();
                equipoEditar.setDelegados(new ArrayList<RefDelegadoAdm>());
                break;
            default: //sin cambios case 0
        }
        // los de control
        AdmSession mSession = AdmSession.getInstance();
        equipoEditar.setUsuarioModificacion(mSession.getAdminLogeado().getUid());
        equipoEditar.setFechaModificacion(Utilidades.fechaHoyFormateada());
        mPresenter.guardarEquipo(equipoEditar);
    }
    @Override
    public void equipoBorrado(){
        Toast.makeText(this, R.string.equipo_eliminado, Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, EquiposAdm.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    @Override
    public void equipoGuardado(){
        //validar si se modifico el delegado
        System.out.println("-------------------------- equipoGuardado");
        System.out.println("-------------------------- editoDelegado:" + editoDelegado);
        switch(editoDelegado){
            case 2:     //cambio numero de telefono
                //busca primer delegado y le borra la referencia al equipo
                //busca el segundo delegado y le agrega la referencia
                actBorrarRefDelegados(equipoEditar.getUid(),
                        equipoEditar.getDelegados().get(0).getUid(), uidDelegadoAnterior);
                break;
            case 3:     //busca al delegado y le borra la referencia al equipo
                borrarRefDelegado(equipoEditar.getUid(), uidDelegadoAnterior);
                break;
            case 4:     //agrego delegado
                //busca delegado y agrega referencia
                agregarRefDelegado(equipoEditar.getUid(), equipoEditar.getDelegados().get(0).getUid());
                break;
            default:
                if(cbDelegado.isChecked()){
                    actualizarDelegado(equipoEditar.getDelegados().get(0).getUid());
                }else{
                    finalGuardar();
                }
        }
    }

    public void actualizarDelegado(String uidDelegado) {
        mPresenter.actualizarDelegado(uidDelegado, equipoEditar.getNombre(),
                equipoEditar.getUrlFoto(), equipoEditar.getUid());
    }

    public void actBorrarRefDelegados(String uidEquipo, String uidDelegadoAgregar,
            String uidDelegadoBorrar) {
        AdmSession mSession = AdmSession.getInstance();

        RefEquipoDelegado referencia = new RefEquipoDelegado(mSession.getIdCanchaSel(),
                mSession.getNombreCanchaSel(), mSession.getUrlLogoCanchaSel(), uidEquipo,
                equipoEditar.getNombre(), equipoEditar.getUrlFoto(), mSession.getIdLigaSel(),
                mSession.getNombreLigaSel(), mSession.getUrlLogoLigaSel(), mSession.getIdCuentaSel()
        );
        String lada = equipoEditar.getDelegados().get(0).getLada();
        mPresenter.actBorrarRefDelegados(uidEquipo, uidDelegadoAgregar, referencia, lada, uidDelegadoBorrar);
    }

    public void borrarRefDelegado(String uidEquipo, String uidDelegado) {
        mPresenter.borrarRefDelegado(Constantes.DELEGADO_GUARDADO_EXITO, uidEquipo, uidDelegado);
    }

    public void agregarRefDelegado(String uidEquipo, String uidDelegadoAgregar) {
        AdmSession mSession = AdmSession.getInstance();

        RefEquipoDelegado referencia = new RefEquipoDelegado(mSession.getIdCanchaSel(),
                mSession.getNombreCanchaSel(), mSession.getUrlLogoCanchaSel(), uidEquipo,
                equipoEditar.getNombre(), equipoEditar.getUrlFoto(), mSession.getIdLigaSel(),
                mSession.getNombreLigaSel(), mSession.getUrlLogoLigaSel(), mSession.getIdCuentaSel()
        );
        String lada = equipoEditar.getDelegados().get(0).getLada();
        mPresenter.agregarRefDelegado(Constantes.DELEGADO_GUARDADO_EXITO,
                uidDelegadoAgregar, lada, referencia);
    }

    @Override
    public void finalGuardar() {
        desbloquearPantalla();
        Toast.makeText(this, R.string.nuevo_equipo_guardado, Toast.LENGTH_LONG).show();
    }

    public void subirFotoEquipo() {
        mPresenter.subirEscudoEquipo(uriPhoto, equipoEditar.getUid());
    }

    public boolean validar(){
        String nombreEquipo = etNombre.getText().toString().trim();
        if (nombreEquipo == null || nombreEquipo.isEmpty()) {
            etNombre.setError(getString(R.string.nuevo_equipo_error_sin_nombre));
            etNombre.requestFocus();
            return false;
        }
        String nombreDelegado = etNombreDelegado.getText().toString().trim();
        String celularDelegado = etTelDelegado.getText().toString().trim();

        if(cbDelegado.isChecked()){
            if (nombreDelegado == null || nombreDelegado.isEmpty()) {
                etNombreDelegado.setError(getString(R.string.nuevo_equipo_error_sin_nombre_delegado));
                etNombreDelegado.requestFocus();
                return false;
            }

            if (celularDelegado == null || celularDelegado.isEmpty()) {
                etTelDelegado.setError(getString(R.string.nuevo_equipo_error_sin_celular));
                etTelDelegado.requestFocus();
                return false;
            }

            if (celularDelegado.length() < 10) {
                etTelDelegado.setError(getString(R.string.nuevo_equipo_error_longitud_cel));
                etTelDelegado.requestFocus();
                return false;
            }

            String lada = getResources()
                    .getStringArray(R.array.ladasValues)[spLadas.getSelectedItemPosition()];

            //el equipo contaba con delegado
            if (equipoEditar.getDelegados() != null && equipoEditar.getDelegados().size() > 0) {
                RefDelegadoAdm delegadoEquipo = equipoEditar.getDelegados().get(0);

                if(delegadoEquipo.getNombre().equals(nombreDelegado) &&
                        delegadoEquipo.getLada().equals(lada) &&
                        delegadoEquipo.getUid().equals(lada+celularDelegado)){
                    editoDelegado = 0;
                }else{
                    if(!delegadoEquipo.getNombre().equals(nombreDelegado)){
                        editoDelegado = 1;
                    }
                    if(!delegadoEquipo.getLada().equals(lada) ||
                            !delegadoEquipo.getUid().equals(lada+celularDelegado)){
                        editoDelegado = 2;
                    }
                }
            }else{  //el equipo no contaba con delegado
                editoDelegado = 4;
            }
        }else{
            if(equipoEditar.getDelegados() == null || equipoEditar.getDelegados().isEmpty()){
                editoDelegado = 0;
            }else{
                editoDelegado = 3;
            }
        }

        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            switch (requestCode) {
                case Constantes.RP_STORAGE:
                    abrirGaleria();
                    break;
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void permisosGaleria() {
        mPresenter.checarPermisos(Manifest.permission.READ_EXTERNAL_STORAGE, Constantes.RP_STORAGE,
                getBaseContext(), this);
    }

    @OnClick({R.id.imgBorrarFoto, R.id.imgDesdeGaleria, R.id.btnCancelar, R.id.btnGuardar,
            R.id.btnEliminar})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgBorrarFoto:
                limpiarFoto();
                break;
            case R.id.imgDesdeGaleria:
                permisosGaleria();
                break;
            case R.id.btnCancelar:
                Intent intent = new Intent(this, EquiposAdm.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                break;
            case R.id.btnGuardar:
                if (validar()) {
                    bloquearPantalla(R.string.common_cargando);

                    /** para no volver a guardar la foto del equipo si no se modifico
                     * 0 = sin editar
                     * 1 = cambio foto
                     * 2 = quito foto*/
                    System.out.println("-------------------- editoFoto:" + editoFoto);
                    switch(editoFoto){
                        case 0:         //sin cambios
                            System.out.println("-------------------- Sin cambios");
                            guardarEquipo(equipoEditar.getUrlFoto());
                            break;
                        case 1:         //cambio foto
                            System.out.println("-------------------- cambio de foto");
                            subirFotoEquipo();
                            break;
                        case 2:         //elimino foto
                            System.out.println("-------------------- se eliminara la foto");
                            if(equipoEditar.getUrlFoto() != null && !equipoEditar.getUrlFoto().isEmpty()){
                                eliminarFotoEquipo();
                            }
                            guardarEquipo("");
                            break;
                        default:
                    }
                }
                break;
            case R.id.btnEliminar:
                eliminarEquipo(equipoEditar);
                break;
        }
    }

    private void eliminarFotoEquipo(){
        mPresenter.eliminarFotoEquipo(equipoEditar.getUid());
    }

    private void eliminarEquipo(Equipo equipo){
        Vibrator vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator != null){
            vibrator.vibrate(100);
        }

        new AlertDialog.Builder(this)
            .setTitle(R.string.equipos_adm_borrar_equipo)
            .setMessage(getBaseContext().getString(
                R.string.equipos_adm_dialogo_borrar_equipo, equipo.getNombre()))
            .setPositiveButton(R.string.common_borrar, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    bloquearPantalla(R.string.equipos_adm_msje_borrando_equipo);
                    if(equipo.getDelegados() != null && !equipo.getDelegados().isEmpty()){
                        for(RefDelegadoAdm delegado:equipo.getDelegados()){
                            mPresenter.borrarRefDelegado(Constantes.BORADO_EXITOSO, equipo.getUid(),
                                    delegado.getUid());
                        }
                    }
                    mPresenter.eliminarEquipo(equipo.getUid());
                }
            })
            .setNegativeButton(R.string.common_cancelar, null)
            .show();

    }

    @OnClick(R.id.cbDelegado)
    public void onViewClicked() {
        float alpha = 0f;
        int mediumAnimationDuration = getResources().getInteger(
                android.R.integer.config_mediumAnimTime);

        if (cbDelegado.isChecked()) {
            llDelegado.setVisibility(View.VISIBLE);
            alpha = 1f;
        } else {
            llDelegado.setVisibility(View.GONE);
        }

        llDelegado.animate()
                .alpha(alpha)
                .setDuration(mediumAnimationDuration)
                .setListener(null);
    }
}