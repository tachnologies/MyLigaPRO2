package com.tachnologies.myligapro.moduloAdm.nuevoEquipo.view;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.textfield.TextInputLayout;
import com.tachnologies.myligapro.R;
import com.tachnologies.myligapro.common.animaciones.cargando.CargandoDialog;
import com.tachnologies.myligapro.common.model.dataSession.AdmSession;
import com.tachnologies.myligapro.common.pojo.Equipo;
import com.tachnologies.myligapro.common.pojo.RefDelegadoAdm;
import com.tachnologies.myligapro.common.pojo.RefEquipoDelegado;
import com.tachnologies.myligapro.common.pojo.UsuarioDelegado;
import com.tachnologies.myligapro.common.utils.Constantes;
import com.tachnologies.myligapro.common.utils.Utilidades;
import com.tachnologies.myligapro.moduloAdm.equiposAdm.view.EquiposAdm;
import com.tachnologies.myligapro.moduloAdm.nuevoEquipo.NuevoEquipoPresenter;
import com.tachnologies.myligapro.moduloAdm.nuevoEquipo.NuevoEquipoPresenterClass;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NuevoEquipoActivity extends AppCompatActivity implements NuevoEquipoView {

    @BindView(R.id.imgFoto)
    AppCompatImageView imgFoto;
    @BindView(R.id.imgBorrarFoto)
    AppCompatImageView imgBorrarFoto;
    @BindView(R.id.imgDesdeGaleria)
    AppCompatImageView imgDesdeGaleria;
    @BindView(R.id.etNombre)
    EditText etNombre;
    @BindView(R.id.btnCancelar)
    Button btnCancelar;
    @BindView(R.id.btnGuardar)
    Button btnGuardar;
    @BindView(R.id.contentMain)
    LinearLayout contentMain;
    @BindView(R.id.tilNombre)
    TextInputLayout tilNombre;
    @BindView(R.id.etNombreDelegado)
    EditText etNombreDelegado;
    @BindView(R.id.etTelDelegado)
    EditText etTelDelegado;
    @BindView(R.id.spLadas)
    Spinner spLadas;
    @BindView(R.id.cbDelegado)
    CheckBox cbDelegado;
    @BindView(R.id.llDelegado)
    LinearLayout llDelegado;
    /**
     * para obtener la lada del pais
     */
    private Set<String> mLadasSet;
    private SharedPreferences mSharedPreferences;

    private NuevoEquipoPresenter mPresenter;
    /**
     * para lo del gif
     */
    private CargandoDialog cargando;
    /**
     * para la foto del equipo
     */
    private Uri uriPhoto;
    /**
     * para el delegado
     */
    private String nombreDelegado;
    private String celularDelegado;
    private Equipo equipoGuardar;

    private AdmSession mSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_equipo);
        ButterKnife.bind(this);
        mSession = AdmSession.getInstance();
        mPresenter = new NuevoEquipoPresenterClass(this);
        mPresenter.onShow();
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        limpiarFoto();
        nombreDelegado = "";
        celularDelegado = "";
        etNombre.setText(Constantes.CADENA_VACIA);
        etNombreDelegado.setText(Constantes.CADENA_VACIA);
        etTelDelegado.setText(Constantes.CADENA_VACIA);
        configSharedPreferences();
    }

    private void configSharedPreferences() {
        mSharedPreferences = getPreferences(Context.MODE_PRIVATE);
        mLadasSet = mSharedPreferences.getStringSet(Constantes.SP_LADAS, new HashSet<String>());
        //showTopics();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    private boolean validar() {
        String nombreEquipo = etNombre.getText().toString().trim();
        if (nombreEquipo == null || nombreEquipo.isEmpty()) {
            etNombre.setError(getString(R.string.nuevo_equipo_error_sin_nombre));
            etNombre.requestFocus();
            return false;
        }
        nombreDelegado = etNombreDelegado.getText().toString().trim();
        celularDelegado = etTelDelegado.getText().toString().trim();
        if ((nombreDelegado != null && !nombreDelegado.isEmpty()) ||
                (celularDelegado != null && !celularDelegado.isEmpty())) {

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
        }
        return true;
    }

    private void limpiarFoto() {
        uriPhoto = null;
        RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop();

        Glide.with(this)
                .load(R.drawable.escudo_equipo)
                .apply(options)
                .into(imgFoto);
    }

    @OnClick({R.id.imgBorrarFoto, R.id.imgDesdeGaleria, R.id.btnCancelar, R.id.btnGuardar})
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
                    bloquearPantalla();
                    if (uriPhoto == null) {
                        String uidEquipo = new SimpleDateFormat("ddMMyyyyHHmmssSSS",
                                Locale.ROOT).format(new Date());
                        Equipo equipo = new Equipo(uidEquipo);
                        guardarEquipo(equipo);
                    } else {
                        subirFotoEquipo();
                    }
                }
                break;
            default:
        }
    }

    @Override
    public void guardarEquipo(Equipo equipo) {
        String nombreEquipo = etNombre.getText().toString().trim();
        equipo.setNombre(nombreEquipo);
        equipo.setUsuarioAlta(mSession.getAdminLogeado().getUid());
        equipo.setFechaAlta(Utilidades.fechaHoyFormateada());
        equipo.setEstatus(Constantes.ESTATUS_ALTA);

        if (nombreDelegado != null && !nombreDelegado.isEmpty()) {
            String lada = getResources()
                    .getStringArray(R.array.ladasValues)[spLadas.getSelectedItemPosition()];
            List<RefDelegadoAdm> delegados = new ArrayList<RefDelegadoAdm>();
            delegados.add(new RefDelegadoAdm(nombreDelegado, "", lada + celularDelegado,
                    lada));
            equipo.setDelegados(delegados);
        }

        equipoGuardar = equipo;
        mPresenter.guardarEquipo(equipoGuardar);
    }

    public void subirFotoEquipo() {
        final String EquipoId = new SimpleDateFormat("ddMMyyyyHHmmssSSS", Locale.ROOT)
                .format(new Date());

        mPresenter.subirEscudoEquipo(uriPhoto, EquipoId);
    }


    private void guardarDelegado() {
        String lada = getResources()
                .getStringArray(R.array.ladasValues)[spLadas.getSelectedItemPosition()];

        String uidDelegado = lada + celularDelegado;
        UsuarioDelegado delegado = new UsuarioDelegado(uidDelegado, lada, Constantes.CADENA_VACIA,
                mSession.getAdminLogeado().getUid(), Utilidades.fechaHoyFormateada(),
                Constantes.ESTATUS_ALTA);

        List<RefEquipoDelegado> referenciasEquipos = new ArrayList<RefEquipoDelegado>();

        referenciasEquipos.add(new RefEquipoDelegado(mSession.getIdCanchaSel(),
                mSession.getNombreCanchaSel(), mSession.getUrlLogoCanchaSel(),
                equipoGuardar.getUid(), equipoGuardar.getNombre(), equipoGuardar.getUrlFoto(),
                mSession.getIdLigaSel(), mSession.getNombreLigaSel(), mSession.getUrlLogoLigaSel(),
                mSession.getIdCuentaSel()
        ));

        delegado.setEquipos(referenciasEquipos);

        mPresenter.guardarDelegado(delegado);
    }

    private void permisosGaleria() {
        System.out.println("-------------------------- permisosGaleria");
        //Activity activity = this.getActivity();
        mPresenter.checarPermisos(Manifest.permission.READ_EXTERNAL_STORAGE, Constantes.RP_STORAGE,
                getBaseContext(), this);
    }

    /**
     * -------------------------------------- metodos NuevoEquipoView
     */

    @Override
    public void setImagen(Intent data) {
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
    public void abrirGaleria() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, Constantes.RC_FOTO_EQUIPO_PICKER);
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

    @Override
    public void desbloquearPantalla() {
        cargando.dismiss();
    }

    @Override
    public void bloquearPantalla() {
        cargando = new CargandoDialog();
        cargando.show(getSupportFragmentManager(), Constantes.CADENA_VACIA);
    }

    @Override
    public void equipoAgregado() {
        System.out.println("------------------------------- equipoAgregado");
        if (celularDelegado != null && !celularDelegado.isEmpty()) {
            guardarDelegado();
        } else {
            finalGuardar();
        }
    }

    @Override
    public void actualizarDelegado(UsuarioDelegado delegado) {

        List<RefEquipoDelegado> referenciasEquipos = delegado.getEquipos() == null ?
                new ArrayList<RefEquipoDelegado>() : delegado.getEquipos();
        //AdmSession mSession = AdmSession.getInstance();

        referenciasEquipos.add(new RefEquipoDelegado(mSession.getIdCanchaSel(),
                mSession.getNombreCanchaSel(), mSession.getUrlLogoCanchaSel(),
                equipoGuardar.getUid(), equipoGuardar.getNombre(), equipoGuardar.getUrlFoto(),
                mSession.getIdLigaSel(), mSession.getNombreLigaSel(), mSession.getUrlLogoLigaSel(),
                mSession.getIdCuentaSel()
        ));
        delegado.setEquipos(referenciasEquipos);
        mPresenter.actualizarDelegado(delegado);
    }

    @Override
    public void finalGuardar() {
        desbloquearPantalla();
        Toast.makeText(this, R.string.nuevo_equipo_guardado, Toast.LENGTH_LONG).show();
        inicializarComponentes();
    }

    @Override
    public void mostrarError(int resMsg) {
        desbloquearPantalla();
        Toast.makeText(this, resMsg, Toast.LENGTH_LONG).show();
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
            etNombreDelegado.setText(Constantes.CADENA_VACIA);
            etTelDelegado.setText(Constantes.CADENA_VACIA);
            llDelegado.setVisibility(View.GONE);
        }

        llDelegado.animate()
                .alpha(alpha)
                .setDuration(mediumAnimationDuration)
                .setListener(null);

    }
}