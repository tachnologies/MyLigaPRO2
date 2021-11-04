package com.tachnologies.myligapro.moduloAdm.agregaEditaLigas.view;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Instrumentation;
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

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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
import com.tachnologies.myligapro.moduloAdm.listadoLigas.ListadoLigasActivity;
import com.tachnologies.myligapro.moduloAdm.mainAdm.AdminActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AgregaEditaLigas extends AppCompatActivity implements AgregaEditaLigasView {

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
    @BindView(R.id.tvDias)
    TextView tvDias;

    /**para lo del gif*/
    private CargandoDialog cargando;
    /**para la foto del equipo*/
    private Uri uriFoto;
    /**para diferenciar si se edita o agrega*/
    private boolean esEditar;
    /**para identificar si al editar el jugador tenia una foto*/
    private boolean teniaFoto;
    private AgregaEditaLigasPresenter mPresenter;
    private Liga ligaEditar;

    /**Para la foto de la galeria */
    ActivityResultLauncher<String> mGetContent;

    ActivityResultLauncher<Intent> mStartForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
        new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent intent = result.getData();
                    setImagen(intent);
                }
            }
        });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agrega_edita_ligas);
        ButterKnife.bind(this);

        mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri uriImagen) {
                if(uriImagen != null){
                    setImagen(uriImagen);
                }
            }
        });

        cargando = new CargandoDialog();
        mPresenter = new AgregaEditaLigasPresenterClass(this);
        esEditar = (boolean) getIntent().getSerializableExtra(Constantes.ES_EDITAR);
        ligaEditar = new Liga();

        if (esEditar) {
            tvTitulo.setText(R.string.editar_liga_titulo);
            AdmSession mSession = AdmSession.getInstance();
            String idLiga = mSession.getIdLigaSel();
            bloquearPantalla();
            //consultar equipo

        } else {
            tvTitulo.setText(R.string.alta_liga_titulo);
        }

        //mSession = AdmSession.getInstance();
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

    private void setImagen(Uri urlImagen) {
        System.out.println("--------------------- set Imagen");
        System.out.println("--------------------- deberia poner la foto");
        //Bundle bundle = data.getExtras();
        uriFoto = urlImagen;
        /*if (bundle == null || bundle.isEmpty()) {
            uriFoto = Uri.parse(data.getDataString());
        }*/

        int sizeImagePreview = getResources().getDimensionPixelSize(R.dimen.nuevo_equipo_alto_img);
        Bitmap bitmap = Utilidades.reducirBitmap(this, contentMain, uriFoto,
                sizeImagePreview, sizeImagePreview);

        if (bitmap != null) {
            imgFoto.setImageBitmap(bitmap);
        }
    }

    @OnClick({R.id.imgBorrarFoto, R.id.imgDesdeGaleria, R.id.cbLimJugadores,
            R.id.cbRepechaje, R.id.btnGuardar, R.id.btnCancelar,
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
            case R.id.btnGuardar:
                if (validar()) {
                    bloquearPantalla();
                    if (uriFoto == null) {
                        if (esEditar) {
                            if (teniaFoto) {
                                eliminarFotoLiga();
                            }
                            guardarLiga();
                        } else {
                            String stringTimestamp = Utilidades.obtenerStringTimestamp();
                            String uidLiga = "L-" + stringTimestamp;
                            ligaEditar.setUid(uidLiga);
                            guardarLiga();
                        }
                    } else {
                        if(!esEditar){
                            String uidLiga = new SimpleDateFormat("ddMMyyyyHHmmssSSS", Locale.ROOT)
                                    .format(new Date());

                            ligaEditar.setUid(uidLiga);
                            guardarLiga();
                        }
                        subirFotoLiga();
                    }
                }
                break;
            case R.id.btnCancelar:
                Intent intent = new Intent(this, ListadoLigasActivity.class);
                //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                break;
            case R.id.btnEliminar:
                alertEliminarLiga();
                break;
            default:
        }
    }

    private boolean validar() {
        boolean esValido = true;

        String tipoTorneo = getResources()
                .getStringArray(R.array.tipo_torneoValues)[spTipoTorneo.getSelectedItemPosition()];

        if (Constantes.TIPO_LIGA_LIGAYELIMINATORIA.equals(tipoTorneo)) {
            if (cbRepechaje.isChecked()) {
                String etNumRepechaje = etRepechaje.getText().toString();
                if (Validaciones.esNumerico(etNumRepechaje)) {
                    int numEquiposRepechaje = Integer.parseInt(etNumRepechaje);
                    if (numEquiposRepechaje <= 0) {
                        etRepechaje.setError(getString(R.string.agrega_elim_liga_error_numero_mayor_cero));
                        etRepechaje.requestFocus();
                        esValido = false;
                    }
                } else {
                    etRepechaje.setError(getString(R.string.agrega_elim_liga_error_no_numerico));
                    etRepechaje.requestFocus();
                    esValido = false;
                }
            }

            String etNumEqCalifican = etEquiposCalifican.getText().toString();
            if (Validaciones.esNumerico(etNumEqCalifican)) {
                int numEqCalifican = Integer.parseInt(etNumEqCalifican);
                if (numEqCalifican <= 0) {
                    etEquiposCalifican.setError(getString(R.string.agrega_elim_liga_error_numero_mayor_cero));
                    etEquiposCalifican.requestFocus();
                    esValido = false;
                }
            } else {
                etEquiposCalifican.setError(getString(R.string.agrega_elim_liga_error_no_numerico));
                etEquiposCalifican.requestFocus();
                esValido = false;
            }
        }

        if (!cbLunes.isChecked() && !cbMartes.isChecked() && !cbMiercoles.isChecked() &&
                !cbJueves.isChecked() && !cbViernes.isChecked() && !cbSabado.isChecked() &&
                !cbDomingo.isChecked()) {
            tvDias.setError(getString(R.string.agrega_elim_liga_error_no_dias));
            tvDias.requestFocus();
            esValido = false;
        }

        if (cbLimJugadores.isChecked()) {
            String etNumJugadores = etLimJugadores.getText().toString();
            if (Validaciones.esNumerico(etNumJugadores)) {
                int numJugadores = Integer.parseInt(etNumJugadores);
                if (numJugadores <= 0) {
                    etLimJugadores.setError(getString(R.string.agrega_elim_liga_error_numero_mayor_cero));
                    etLimJugadores.requestFocus();
                    esValido = false;
                }
            } else {
                etLimJugadores.setError(getString(R.string.agrega_elim_liga_error_no_numerico));
                etLimJugadores.requestFocus();
                esValido = false;
            }
        }

        if (Validaciones.estaVacio(tipoTorneo)) {
            Toast.makeText(this, R.string.agrega_elim_liga_error_no_tipo_liga, Toast.LENGTH_LONG).show();
            spTipoTorneo.requestFocus();
            esValido = false;
        }

        String genero = getResources()
                .getStringArray(R.array.generos)[spGenero.getSelectedItemPosition()];
        if (Validaciones.estaVacio(genero)) {
            Toast.makeText(this, R.string.agrega_elim_liga_error_no_genero, Toast.LENGTH_LONG).show();
            spGenero.requestFocus();
            esValido = false;
        }

        if (Validaciones.estaVacio(etNombre.getText().toString())) {
            etNombre.setError(getString(R.string.agrega_elim_liga_error_sin_nombre));
            etNombre.requestFocus();
            esValido = false;
        }

        return esValido;
    }

    private void alertEliminarLiga() {
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator != null) {
            vibrator.vibrate(100);
        }

        new AlertDialog.Builder(this)
                .setTitle(R.string.agrega_elimina_liga_titulo)
                .setMessage(getString(R.string.agrega_elimina_liga_dialogo,
                        ligaEditar.getNombre()))
                .setPositiveButton(R.string.common_borrar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        bloquearPantalla();

                        if (teniaFoto) {
                            eliminarFotoLiga();
                        }

                        mPresenter.eliminarLiga(ligaEditar.getUid());
                    }
                })
                .setNegativeButton(R.string.common_cancelar, null)
                .show();
    }

    private void eliminarFotoLiga() {
        mPresenter.eliminarFotoLiga(ligaEditar.getUrlFoto());
        ligaEditar.setUrlFoto("");
    }

    private void subirFotoLiga() {
        mPresenter.subirFotoLiga(ligaEditar.getUid(), uriFoto);
    }

    private void limpiarFoto() {
        uriFoto = null;
        RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop();

        Glide.with(this)
                .load(R.drawable.trofeo_2)
                .apply(options)
                .into(imgFoto);
    }

    /**@Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        System.out.println("----------------------------- onActivityResult");
        super.onActivityResult(requestCode, resultCode, data);
        mPresenter.resultadoActivity(requestCode, resultCode, data);
    }*/

    private void permisosGaleria() {
        System.out.println("----------------------------------permisosGaleria");
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
        //aqui redireccionar al menuAdm y setear el id de la liga en admSession
        desbloquearPantalla();
        AdmSession mSession = AdmSession.getInstance();
        mSession.setIdLigaSel(ligaEditar.getUid());

        Intent intent = new Intent(this, AdminActivity.class);
        mSession.setIdLigaSel(ligaEditar.getUid());
        mSession.setNombreLigaSel(ligaEditar.getNombre());
        mSession.setUrlLogoLigaSel(ligaEditar.getUrlFoto());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        //finish();
    }

    @Override
    public void guardarLiga(String urlFotoLiga) {
        ligaEditar.setUrlFoto(urlFotoLiga);
        guardarLiga();
        //este se usa para cuando regresa de guardar la imagen de la liga
        //despues va a llamar al metodo guardarLiga para hacer eso psssss guardar la liga wey :v
    }

    public void guardarLiga() {
        String tipoTorneo = getResources()
                .getStringArray(R.array.tipo_torneoValues)[spTipoTorneo.getSelectedItemPosition()];

        ligaEditar.setTipoTorneo(tipoTorneo);

        if (Constantes.TIPO_LIGA_LIGAYELIMINATORIA.equals(tipoTorneo)) {
            if (cbRepechaje.isChecked()) {
                int numEquiposRepechaje = Integer.parseInt(etRepechaje.getText().toString());
                ligaEditar.setHayRepechaje(true);
                ligaEditar.setEquiposRepechaje(numEquiposRepechaje);
            }else{
                ligaEditar.setHayRepechaje(false);
            }

            int numEqCalifican = Integer.parseInt(etEquiposCalifican.getText().toString());
            ligaEditar.setEquiposCalifican(numEqCalifican);
        }

        List<String> dias = new ArrayList<String>();
        if(cbLunes.isChecked()){
            dias.add(Constantes.DIA_LUNES);
        }
        if(cbMartes.isChecked()){
            dias.add(Constantes.DIA_MARTES);
        }
        if(cbMiercoles.isChecked()){
            dias.add(Constantes.DIA_MIERCOLES);
        }
        if(cbJueves.isChecked()){
            dias.add(Constantes.DIA_JUEVES);
        }
        if(cbViernes.isChecked()){
            dias.add(Constantes.DIA_VIERNES);
        }
        if(cbSabado.isChecked()){
            dias.add(Constantes.DIA_SABADO);
        }
        if(cbDomingo.isChecked()){
            dias.add(Constantes.DIA_DOMINGO);
        }

        ligaEditar.setDias(dias);
        if (cbLimJugadores.isChecked()) {
            int numJugadores = Integer.parseInt(etLimJugadores.getText().toString());
            ligaEditar.setTieneLimiteRegistros(true);
            ligaEditar.setCantidadRegistros(numJugadores);
        }else{
            ligaEditar.setTieneLimiteRegistros(false);
        }

        String genero = getResources()
                .getStringArray(R.array.generos)[spGenero.getSelectedItemPosition()];

        ligaEditar.setGenero(genero);

        ligaEditar.setNombre(etNombre.getText().toString());

        if(ligaEditar.getUrlFoto() != null && !Validaciones.estaVacio(ligaEditar.getUrlFoto())){
            teniaFoto = true;
        }else{
            teniaFoto = false;
        }

        mPresenter.guardarLiga(ligaEditar);
    }

    @Override
    public void ligaEliminada() {
        //creo este llega despues de eliminada la liga y se tendra que salir hasta el listado de ligas,
        //OJOOO aqui hay que dejar limpio AdmSession unicamente contendra al admin logeado
        //y con banderas que eliminan la pila de actividades
        desbloquearPantalla();
        AdmSession mSession = AdmSession.getInstance();
        mSession.setIdLigaSel("");
        mSession.setNombreLigaSel("");
        mSession.setUrlLogoLigaSel("");
        mSession.setIdEquipoSel("");
        mSession.setNombreEquipoSel("");
        mSession.setUrlLogoEquipoSel("");
        //aqui se hace la redireccion
        Intent intent = new Intent(this, ListadoLigasActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    @Override
    public void mostrarError(int resMsg) {
        desbloquearPantalla();
        Toast.makeText(this, resMsg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setImagen(Intent data) {
        System.out.println("--------------------- set Imagen");
        System.out.println("--------------------- deberia poner la foto");
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
        System.out.println("----------------------------------abrirGaleria");
        //Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        //startActivityForResult(intent, Constantes.RC_FOTO_EQUIPO_PICKER);

        //mGetContent.launch("image/*");
        mStartForResult.launch(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI));
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