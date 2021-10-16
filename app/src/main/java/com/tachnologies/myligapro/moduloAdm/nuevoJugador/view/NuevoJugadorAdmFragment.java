package com.tachnologies.myligapro.moduloAdm.nuevoJugador.view;

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
import com.tachnologies.myligapro.common.pojo.Equipo;
import com.tachnologies.myligapro.common.pojo.Jugador;
import com.tachnologies.myligapro.common.utils.Constantes;
import com.tachnologies.myligapro.common.utils.Utilidades;
import com.tachnologies.myligapro.moduloAdm.jugadoresAdm.view.JugadoresAdmView;
import com.tachnologies.myligapro.moduloAdm.nuevoJugador.NuevoJugadorAdmPresenter;
import com.tachnologies.myligapro.moduloAdm.nuevoJugador.NuevoJugadorAdmPresenterClass;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class NuevoJugadorAdmFragment extends DialogFragment implements DialogInterface.OnShowListener,
        NuevoJugadorAdmView {

    Unbinder unbinder;
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
    @BindView(R.id.tvTitulo)
    TextView tvTitulo;

    private JugadoresAdmView mActivity;
    private NuevoJugadorAdmPresenter mPresenter;

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

    public NuevoJugadorAdmFragment(JugadoresAdmView mActivity, boolean esEditar) {
        this.mActivity = mActivity;
        this.esEditar = esEditar;
        equipo = this.mActivity.getEquipo();
        mPresenter = new NuevoJugadorAdmPresenterClass(this);
    }

    public NuevoJugadorAdmFragment(JugadoresAdmView mActivity, boolean esEditar, Jugador jugador) {
        this.jugador = jugador;
        this.mActivity = mActivity;
        this.esEditar = esEditar;
        equipo = this.mActivity.getEquipo();
        mPresenter = new NuevoJugadorAdmPresenterClass(this);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_nuevo_jugador_adm, null);
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

    /**------------------------------- DialogInterface.OnShowListener*/
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
            case R.id.btnEliminar:
                eliminarJugador();
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
            default:
        }
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
            System.out.println("---------------------------------- tiene foto");
            cargaFotoDeBD(this.jugador.getUrlFoto());
            this.teniaFoto = true;
        } else {
            System.out.println("---------------------------------- limpiarFoto");
            limpiarFoto();
        }
    }

    private void subirFotoJugador() {
        mPresenter.subirFotoJugador(uriFoto, equipo.getUid(), jugadorId);
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

    /**
     * ---------------------------- de la interfaz NuevoJugadorAdmView
     */
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

    private void eliminarJugador(){
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

                        mPresenter.eliminarJugador(jugador.getUid());
                    }
                })
                .setNegativeButton(R.string.common_cancelar, null)
                .show();
    }

    private void eliminarFotoJugador(){
        mPresenter.eliminarFotoJugador(jugador.getUrlFoto());
    }

    @Override
    public void jugadorEliminado(Jugador jugador){
        mActivity.eliminarJugador(jugador);
        desbloquearPantalla();
        dismiss();
    }

    @Override
    public void guardarJugador(Jugador nuevoJugador) {
        AdmSession mSession = AdmSession.getInstance();

        if(!esEditar){
            jugador = nuevoJugador;
            jugador.setUid(jugadorId);
            jugador.setUsuarioAlta(mSession.getAdminLogeado().getUid());
            jugador.setFechaAlta(Utilidades.fechaHoyFormateada());
            jugador.setEstatus(Constantes.ESTATUS_ALTA);
        }else{
            jugador.setUsuarioModificacion(mSession.getAdminLogeado().getUid());
            jugador.setFechaModificacion(Utilidades.fechaHoyFormateada());
        }

        if(uriFoto != null){
            if(nuevoJugador.getUrlFoto() != null && !nuevoJugador.getUrlFoto().isEmpty()){
                jugador.setUrlFoto(nuevoJugador.getUrlFoto());
            }else{
                jugador.setUrlFoto("");
                teniaFoto = false;
            }
        }else{
            jugador.setUrlFoto("");
            teniaFoto = false;
        }

        jugador.setNombre(etNombre.getText().toString());
        jugador.setApellido(etApellido.getText().toString());
        jugador.setApodo(etApodo.getText().toString());
        mPresenter.guardarJugador(jugador);
    }

    @Override
    public void jugadorGuardado(Jugador jugador) {
        desbloquearPantalla();
        if(esEditar){
            mActivity.actualizarJugador(jugador);
        }else{
            mActivity.agregarJugador(jugador);
            inicializarComponentes();
        }
        Toast.makeText(getContext(), R.string.nuevo_jugador_guardado, Toast.LENGTH_LONG).show();
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