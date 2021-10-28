package com.tachnologies.myligapro.moduloAdm.agregaEditaLigas.view;

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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.tachnologies.myligapro.R;
import com.tachnologies.myligapro.common.animaciones.cargando.CargandoDialog;
import com.tachnologies.myligapro.common.model.dataSession.AdmSession;
import com.tachnologies.myligapro.common.pojo.Liga;
import com.tachnologies.myligapro.common.utils.Constantes;
import com.tachnologies.myligapro.common.utils.Utilidades;
import com.tachnologies.myligapro.common.utils.Validaciones;
import com.tachnologies.myligapro.moduloAdm.agregaEditaLigas.AgregaEditaLigasPresenter;
import com.tachnologies.myligapro.moduloAdm.agregaEditaLigas.AgregaEditaLigasPresenterClass;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AgregaEditaLigas extends AppCompatActivity implements AgregaEditaLigasView{

    @BindView(R.id.tvTitulo)
    TextView tvTitulo;
    @BindView(R.id.imgFoto)
    AppCompatImageView imgFoto;
    @BindView(R.id.imgBorrarFoto)
    AppCompatImageView imgBorrarFoto;
    @BindView(R.id.imgDesdeGaleria)
    AppCompatImageView imgDesdeGaleria;
    @BindView(R.id.etNombre)
    TextInputEditText etNombre;
    @BindView(R.id.spGenero)
    Spinner spGenero;
    @BindView(R.id.spTipoTorneo)
    Spinner spTipoTorneo;
    @BindView(R.id.tvDescTipoTorneo)
    TextView tvDescTipoTorneo;
    @BindView(R.id.cbLimJugadores)
    CheckBox cbLimJugadores;
    @BindView(R.id.etLimJugadores)
    TextInputEditText etLimJugadores;
    @BindView(R.id.cbLunes)
    CheckBox cbLunes;
    @BindView(R.id.cbMartes)
    CheckBox cbMartes;
    @BindView(R.id.cbMiercoles)
    CheckBox cbMiercoles;
    @BindView(R.id.cbJueves)
    CheckBox cbJueves;
    @BindView(R.id.cbViernes)
    CheckBox cbViernes;
    @BindView(R.id.cbSabado)
    CheckBox cbSabado;
    @BindView(R.id.cbDomingo)
    CheckBox cbDomingo;
    @BindView(R.id.cbRepechaje)
    CheckBox cbRepechaje;
    @BindView(R.id.etRepechaje)
    TextInputEditText etRepechaje;
    @BindView(R.id.etEquiposCalifican)
    TextInputEditText etEquiposCalifican;
    @BindView(R.id.cbEmpatePuntoExtra)
    CheckBox cbEmpatePuntoExtra;
    @BindView(R.id.btnGuardar)
    Button btnGuardar;
    @BindView(R.id.contentMain)
    LinearLayout contentMain;
    @BindView(R.id.tlLimJugadores)
    TextInputLayout tlLimJugadores;
    @BindView(R.id.llRepechaje)
    LinearLayout llRepechaje;
    @BindView(R.id.llEquiposCalifican)
    LinearLayout llEquiposCalifican;
    @BindView(R.id.btnCancelar)
    Button btnCancelar;
    @BindView(R.id.btnEliminar)
    Button btnEliminar;
    @BindView(R.id.tlRepechaje)
    TextInputLayout tlRepechaje;

    /** para lo del gif */
    private CargandoDialog cargando;
    private boolean esNuevo;
    //private AdmSession mSession;
    /**para la foto del equipo*/
    private Uri uriFoto;
    /**para diferenciar si se edita o agrega*/
    private boolean esEditar;
    /**para identificar si al editar el jugador tenia una foto*/
    private boolean teniaFoto;
    private AgregaEditaLigasPresenter mPresenter;
    private Liga ligaEditar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agrega_edita_ligas);
        ButterKnife.bind(this);

        mPresenter = new AgregaEditaLigasPresenterClass(this);
        esNuevo = (boolean) getIntent().getSerializableExtra(Constantes.ES_NUEVO);

        if(esNuevo){
            tvTitulo.setText(R.string.alta_liga_titulo);
        }else{
            tvTitulo.setText(R.string.editar_liga_titulo);
        }

        //mSession = AdmSession.getInstance();
        cargando = new CargandoDialog();

        tvDescTipoTorneo.setText(Constantes.CADENA_VACIA);

        spTipoTorneo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String tipoTorneo = getResources()
                        .getStringArray(R.array.tipo_torneoValues)[spTipoTorneo.getSelectedItemPosition()];

                if (!Validaciones.estaVacio(tipoTorneo)) {
                    tvDescTipoTorneo.setVisibility(View.VISIBLE);

                    String descripcionTorneo = Constantes.CADENA_VACIA;

                    switch (tipoTorneo) {
                        case Constantes.TIPO_LIGA_TORNEO:               //T
                            descripcionTorneo = getString(R.string.alta_liga_torneo_liga_descripcion);
                            llRepechaje.setVisibility(View.GONE);
                            llEquiposCalifican.setVisibility(View.GONE);
                            cbEmpatePuntoExtra.setVisibility(View.VISIBLE);
                            break;
                        case Constantes.TIPO_LIGA_LIGAYELIMINATORIA:    //L
                            descripcionTorneo =
                                    getString(R.string.alta_liga_torneo_liga_eliminatoria_descripcion);
                            llRepechaje.setVisibility(View.VISIBLE);
                            llEquiposCalifican.setVisibility(View.VISIBLE);
                            cbEmpatePuntoExtra.setVisibility(View.VISIBLE);
                            break;
                        case Constantes.TIPO_LIGA_ELIMINATORIA:         //E
                            descripcionTorneo = getString(R.string.alta_liga_eliminatoria_descripcion);
                            llRepechaje.setVisibility(View.GONE);
                            llEquiposCalifican.setVisibility(View.GONE);
                            cbEmpatePuntoExtra.setVisibility(View.GONE);
                            break;
                        default:
                    }
                    tvDescTipoTorneo.setText(descripcionTorneo);
                    tvDescTipoTorneo.setVisibility(View.VISIBLE);
                } else {
                    tvDescTipoTorneo.setText(Constantes.CADENA_VACIA);
                    tvDescTipoTorneo.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                tvDescTipoTorneo.setText(Constantes.CADENA_VACIA);
                tvDescTipoTorneo.setVisibility(View.GONE);
            }
        });

    }

    @OnClick({R.id.imgBorrarFoto, R.id.imgDesdeGaleria, R.id.cbLimJugadores,
            R.id.cbRepechaje, R.id.cbEmpatePuntoExtra, R.id.btnGuardar, R.id.btnCancelar,
            R.id.btnEliminar})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgBorrarFoto:
                limpiarFoto();
                break;
            case R.id.imgDesdeGaleria:
                permisosGaleria();
                break;
            case R.id.cbLimJugadores:
                if (cbLimJugadores.isChecked()) {
                    tlLimJugadores.setVisibility(View.VISIBLE);
                } else {
                    tlLimJugadores.setVisibility(View.GONE);
                    etLimJugadores.setText(Constantes.CADENA_VACIA);
                }
                break;
            case R.id.cbRepechaje:
                if (cbRepechaje.isChecked()) {
                    tlRepechaje.setVisibility(View.VISIBLE);
                } else {
                    tlRepechaje.setVisibility(View.GONE);
                    etRepechaje.setText(Constantes.CADENA_VACIA);
                }
                break;
            case R.id.cbEmpatePuntoExtra:
                break;
            case R.id.btnGuardar:
                break;
            case R.id.btnCancelar:
                break;
            case R.id.btnEliminar:
                alertEliminarLiga();
                break;
            default:
        }
    }

    private void alertEliminarLiga(){
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator != null){
            vibrator.vibrate(100);
        }

        new AlertDialog.Builder(this)
                .setTitle(R.string.agrega_elimina_liga_titulo)
                .setMessage(getString( R.string.agrega_elimina_liga_dialogo,
                        ligaEditar.getNombre()))
                .setPositiveButton(R.string.common_borrar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        bloquearPantalla();

                        if(teniaFoto){
                            eliminarFotoLiga();
                        }

                        mPresenter.eliminarLiga(ligaEditar.getUid());
                    }
                })
                .setNegativeButton(R.string.common_cancelar, null)
                .show();
    }

    private void eliminarFotoLiga(){

    }

    private void limpiarFoto() {
        uriFoto = null;
        RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop();

        Glide.with(this)
                .load(R.drawable.jugador)
                .apply(options)
                .into(imgFoto);
    }

    private void permisosGaleria() {
        mPresenter.checarPermisos(Manifest.permission.READ_EXTERNAL_STORAGE, Constantes.RP_STORAGE,
                getBaseContext(), this);
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
    public void ligaGuardada() {

    }

    @Override
    public void guardarLiga(String urlFotoJugador) {

    }

    @Override
    public void ligaEliminada() {

    }

    @Override
    public void mostrarError(int resMsg) {
        desbloquearPantalla();
        Toast.makeText(this, resMsg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setImagen(Intent data) {
        Bundle bundle = data.getExtras();
        uriFoto = Uri.parse("");
        if (bundle == null || bundle.isEmpty()) {
            uriFoto = Uri.parse(data.getDataString());
        }

        int sizeImagePreview = getResources().getDimensionPixelSize(R.dimen.nuevo_equipo_alto_img);
        Bitmap bitmap = Utilidades.reducirBitmap(this, contentMain, uriFoto,
                sizeImagePreview, sizeImagePreview);

        if (bitmap != null) {
            imgFoto.setImageBitmap(bitmap);
        }
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
}