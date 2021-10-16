package com.tachnologies.myligapro.moduloAdminJugadores.agregarEditar.view;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.tachnologies.myligapro.R;
import com.tachnologies.myligapro.common.animaciones.cargando.CargandoDialog;
import com.tachnologies.myligapro.common.model.dataSession.AdmSession;
import com.tachnologies.myligapro.common.model.dataSession.DelSession;
import com.tachnologies.myligapro.common.pojo.Equipo;
import com.tachnologies.myligapro.common.pojo.Jugador;
import com.tachnologies.myligapro.common.utils.Constantes;
import com.tachnologies.myligapro.common.utils.Utilidades;
import com.tachnologies.myligapro.moduloAdminJugadores.agregarEditar.AgregarEditarJugadorPresenter;
import com.tachnologies.myligapro.moduloAdminJugadores.agregarEditar.AgregarEditarJugadorPresenterClass;
import com.tachnologies.myligapro.moduloAdminJugadores.view.AdministrarJugadoresActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class AgregarEditarJugadorFragment extends DialogFragment
        implements DialogInterface.OnShowListener, AgregarEditarJugadorView {
    @BindView(R.id.tvTitulo)
    TextView tvTitulo;
    @BindView(R.id.imgFoto)
    AppCompatImageView imgFoto;
    @BindView(R.id.imgBorrarFoto)
    AppCompatImageView imgBorrarFoto;
    @BindView(R.id.imgDesdeGaleria)
    AppCompatImageView imgDesdeGaleria;
    @BindView(R.id.etNombre)
    EditText etNombre;
    @BindView(R.id.etApellido)
    EditText etApellido;
    @BindView(R.id.etApodo)
    EditText etApodo;
    @BindView(R.id.btnCancelar)
    Button btnCancelar;
    @BindView(R.id.btnGuardar)
    Button btnGuardar;
    @BindView(R.id.btnEliminar)
    Button btnEliminar;
    @BindView(R.id.contentMain)
    LinearLayout contentMain;
    private Unbinder unbinder;

    private AdministrarJugadoresActivity mActivity;
    private AgregarEditarJugadorPresenter mPresenter;
    /**para la foto del equipo*/
    private Uri uriFoto;
    /**para lo del gif*/
    private CargandoDialog cargando;
    private Equipo equipo;
    private String jugadorId;
    private Jugador jugador;
    /**para diferenciar si se edita o agrega*/
    private boolean esEditar;
    /**para identificar si al editar el jugador tenia una foto*/
    private boolean teniaFoto;
    private String uidCuenta;
    private String uidCancha;
    private String uidLiga;
    private String uidEquipo;
    private String uidUsuarioLogeado;

    public AgregarEditarJugadorFragment(AdministrarJugadoresActivity mActivity, boolean esEditar,
            String tipoUsuario) {
        cargaVariables(mActivity, esEditar, tipoUsuario);
    }

    public AgregarEditarJugadorFragment(AdministrarJugadoresActivity mActivity, boolean esEditar,
            Jugador jugador, String tipoUsuario) {
        this.jugador = jugador;
        cargaVariables(mActivity, esEditar, tipoUsuario);
    }

    private void cargaVariables(AdministrarJugadoresActivity mActivity, boolean esEditar,
            String tipoUsuario){
        this.mActivity = mActivity;
        this.esEditar = esEditar;
        equipo = this.mActivity.getEquipo();
        mPresenter = new AgregarEditarJugadorPresenterClass(this);

        switch (tipoUsuario) {
            case Constantes.ADMIN_LIGA:
                AdmSession admSession = AdmSession.getInstance();
                uidCuenta = admSession.getIdCuentaSel();
                uidCancha = admSession.getIdCanchaSel();
                uidLiga   = admSession.getIdLigaSel();
                uidEquipo = admSession.getIdEquipoSel();
                uidUsuarioLogeado = admSession.getAdminLogeado().getUid();
                break;
            case Constantes.DELEGADO:
                DelSession delSession = DelSession.getInstance();
                uidCuenta = delSession.getIdCuentaSel();
                uidCancha = delSession.getIdCanchaSel();
                uidLiga   = delSession.getIdLigaSel();
                uidEquipo = delSession.getIdEquipoSel();
                uidUsuarioLogeado = delSession.getDelLogeado().getUid();
                break;
            default:
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_agregar_editar_jugador, null);
        unbinder = ButterKnife.bind(this, view);
        builder.setView(view);

        inicializarComponentes();
        AlertDialog dialog = builder.create();
        dialog.setOnShowListener(this);
        return dialog;
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        mPresenter.onDestroy();
        super.onDestroyView();
    }

    private void inicializarComponentes() {
        if (esEditar) {
            this.tvTitulo.setText(R.string.editar_jugador_titulo);
            this.jugadorId = jugador.getUid();
            this.etNombre.setText(jugador.getNombre());
            this.etApellido.setText(jugador.getApellido());
            this.etApodo.setText(jugador.getApodo());
            this.btnEliminar.setVisibility(View.VISIBLE);
        } else {
            this.jugador = new Jugador();
            this.jugadorId = "";
            this.etNombre.setText(Constantes.CADENA_VACIA);
            this.etApellido.setText(Constantes.CADENA_VACIA);
            this.etApodo.setText(Constantes.CADENA_VACIA);
        }

        if (this.jugador.getUrlFoto() != null && !this.jugador.getUrlFoto().isEmpty()) {
            cargaFotoDeBD(this.jugador.getUrlFoto());
            this.teniaFoto = true;
        } else {
            limpiarFoto();
            this.teniaFoto = false;
        }
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

    private void subirFotoJugador() {
        mPresenter.subirFotoJugador(uidCuenta, uidCancha, uidLiga, equipo.getUid(), jugadorId,
                uriFoto);

    }

    private void permisosGaleria() {
        mPresenter.checarPermisos(Manifest.permission.READ_EXTERNAL_STORAGE, Constantes.RP_STORAGE,
                getActivity().getBaseContext(), getActivity());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPresenter.resultadoActivity(requestCode, resultCode, data);
    }

    private void cargaFotoDeBD(String urlFoto) {
        uriFoto = Uri.parse(urlFoto);

        RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop();

        Glide.with(this)
                .load(urlFoto)
                .apply(options)
                .into(imgFoto);
    }

    public boolean validar() {
        String nombreJugador = etNombre.getText().toString().trim();
        if (nombreJugador == null || nombreJugador.isEmpty()) {
            etNombre.setError(getString(R.string.nuevo_jugador_error_sin_nombre));
            etNombre.requestFocus();
            return false;
        }
        String apellidoJugador = etApellido.getText().toString().trim();
        if (apellidoJugador == null || apellidoJugador.isEmpty()) {
            etApellido.setError(getString(R.string.nuevo_jugador_error_sin_apellidos));
            etApellido.requestFocus();
            return false;
        }
        return true;
    }

    private void eliminarFotoJugador(){
        mPresenter.eliminarFotoJugador(jugador.getUrlFoto());
        jugador.setUrlFoto("");
    }

    private void alertEliminarJugador(){
        Vibrator vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator != null){
            vibrator.vibrate(100);
        }

        new AlertDialog.Builder(getContext())
                .setTitle(R.string.borrar_jugador_titulo)
                .setMessage(getContext().getString(
                        R.string.dialogo_borrar_jugador, jugador.getNombre() + " " + jugador.getApellido()))
                .setPositiveButton(R.string.common_borrar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        bloquearPantalla();

                        if(teniaFoto){
                            eliminarFotoJugador();
                        }

                        mPresenter.eliminarJugador(uidCuenta, uidCancha, uidLiga, uidEquipo,
                                jugador.getUid());
                    }
                })
                .setNegativeButton(R.string.common_cancelar, null)
                .show();
    }

    /** ------------------------------ metodos de la interfaz*/
    @Override
    public void onShow(DialogInterface dialog) {
        mPresenter.onShow();
    }

    @OnClick({R.id.imgBorrarFoto, R.id.imgDesdeGaleria, R.id.btnCancelar, R.id.btnGuardar, R.id.btnEliminar})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgBorrarFoto:
                limpiarFoto();
                break;
            case R.id.imgDesdeGaleria:
                permisosGaleria();
                break;
            case R.id.btnCancelar:
                dismiss();
                break;
            case R.id.btnGuardar:
                if (validar()) {
                    bloquearPantalla();

                    if (uriFoto == null) {
                        if(esEditar){
                            if(teniaFoto){
                                eliminarFotoJugador();
                            }
                            guardarJugador(jugador);
                        }else{
                            jugadorId = new SimpleDateFormat("ddMMyyyyHHmmssSSS", Locale.ROOT)
                                    .format(new Date());
                            Jugador nuevoJugador = new Jugador();
                            guardarJugador(nuevoJugador);
                        }

                    } else {
                        if(!esEditar){
                            jugadorId = new SimpleDateFormat("ddMMyyyyHHmmssSSS", Locale.ROOT)
                                    .format(new Date());
                        }
                        subirFotoJugador();
                    }
                }
                break;
            case R.id.btnEliminar:
                alertEliminarJugador();
                break;
        }
    }

    @Override
    public void bloquearPantalla() {
        cargando = new CargandoDialog();
        cargando.show(getActivity().getSupportFragmentManager(), Constantes.CADENA_VACIA);
    }

    @Override
    public void desbloquearPantalla() {
        cargando.dismiss();
    }

    @Override
    public void abrirGaleria() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, Constantes.RC_FOTO_EQUIPO_PICKER);
    }

    @Override
    public void setImagen(Intent data) {
        Bundle bundle = data.getExtras();
        uriFoto = Uri.parse("");
        if (bundle == null || bundle.isEmpty()) {
            uriFoto = Uri.parse(data.getDataString());
        }

        int sizeImagePreview = getResources().getDimensionPixelSize(R.dimen.nuevo_equipo_alto_img);
        Bitmap bitmap = Utilidades.reducirBitmap(getActivity(), contentMain, uriFoto,
                sizeImagePreview, sizeImagePreview);

        if (bitmap != null) {
            imgFoto.setImageBitmap(bitmap);
        }
    }

    @Override
    public void guardarJugador(Jugador nuevoJugador) {
        if(!esEditar){
            jugador = nuevoJugador;
            jugador.setUid(jugadorId);
            jugador.setUsuarioAlta(uidUsuarioLogeado);
            jugador.setFechaAlta(Utilidades.fechaHoyFormateada());
            jugador.setEstatus(Constantes.ESTATUS_ALTA);
        }else{
            jugador.setUsuarioModificacion(uidUsuarioLogeado);
            jugador.setFechaModificacion(Utilidades.fechaHoyFormateada());
        }


        if(nuevoJugador.getUrlFoto() != null && !nuevoJugador.getUrlFoto().isEmpty()){
            jugador.setUrlFoto(nuevoJugador.getUrlFoto());
        }else{
            jugador.setUrlFoto("");
            teniaFoto = false;
        }

        jugador.setNombre(etNombre.getText().toString());
        jugador.setApellido(etApellido.getText().toString());
        jugador.setApodo(etApodo.getText().toString());
        mPresenter.guardarJugador(jugador, uidCuenta, uidCancha, uidLiga, uidEquipo);
    }

    @Override
    public void jugadorGuardado(Jugador jugador) {
        if(esEditar){
            mActivity.actualizarJugador(jugador);
        }else{
            mActivity.agregarJugador(jugador);
            inicializarComponentes();
        }
        desbloquearPantalla();
        Toast.makeText(getContext(), R.string.nuevo_jugador_guardado, Toast.LENGTH_LONG).show();
    }

    @Override
    public void jugadorEliminado(Jugador jugador) {
        mActivity.eliminarJugador(jugador);
        desbloquearPantalla();
        dismiss();
    }

    @Override
    public void mostrarError(int resMsg) {
        desbloquearPantalla();
        Toast.makeText(getContext(), resMsg, Toast.LENGTH_LONG).show();
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