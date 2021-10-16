package com.tachnologies.myligapro.moduloDelegado.editarEquipoDel.view;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.snackbar.Snackbar;
import com.tachnologies.myligapro.R;
import com.tachnologies.myligapro.common.animaciones.cargando.CargandoDialog;
import com.tachnologies.myligapro.common.model.dataSession.DelSession;
import com.tachnologies.myligapro.common.pojo.Equipo;
import com.tachnologies.myligapro.common.utils.Constantes;
import com.tachnologies.myligapro.common.utils.Utilidades;
import com.tachnologies.myligapro.moduloDelegado.editarEquipoDel.EditarEquipoDelPresenter;
import com.tachnologies.myligapro.moduloDelegado.editarEquipoDel.EditarEquipoDelPresenterClass;
import com.tachnologies.myligapro.moduloDelegado.menuDelegado.MenuDelegadoActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditarEquipoDelActivity extends AppCompatActivity implements EditarEquipoDelView {

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
    @BindView(R.id.btnCancelar)
    Button btnCancelar;
    @BindView(R.id.btnGuardar)
    Button btnGuardar;
    @BindView(R.id.contentMain)
    LinearLayout contentMain;

    /** para lo del gif */
    private CargandoDialog cargando;
    private EditarEquipoDelPresenter mPresenter;
    private Equipo equipoEditar;

    /** para la foto del equipo */
    private Uri uriPhoto;

    /**
     * para no volver a guardar la foto del equipo si no se modifico
     * 0 = sin editar
     * 1 = cambio foto
     * 2 = quito foto
     */
    private int editoFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_equipo_del);
        ButterKnife.bind(this);

        bloquearPantalla(R.string.common_cargando);
        editoFoto = 0;
        DelSession mSession = DelSession.getInstance();
        mPresenter = new EditarEquipoDelPresenterClass(this);
        mPresenter.onShow();
        mPresenter.consultarEquipo(mSession.getIdEquipoSel());
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
                Intent intent = new Intent(this, MenuDelegadoActivity.class);
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
                    switch(editoFoto){
                        case 0:         //sin cambios
                            guardarEquipo(equipoEditar.getUrlFoto());
                            break;
                        case 1:         //cambio foto
                            subirFotoEquipo();
                            break;
                        case 2:         //elimino foto
                            if(equipoEditar.getUrlFoto() != null && !equipoEditar.getUrlFoto().isEmpty()){
                                eliminarFotoEquipo();
                            }
                            guardarEquipo("");
                            break;
                        default:
                    }
                }
                break;
        }
    }

    public boolean validar(){
        String nombreEquipo = etNombre.getText().toString().trim();
        if (nombreEquipo == null || nombreEquipo.isEmpty()) {
            etNombre.setError(getString(R.string.nuevo_equipo_error_sin_nombre));
            etNombre.requestFocus();
            return false;
        }
        return true;
    }

    private void eliminarFotoEquipo(){
        mPresenter.eliminarFotoEquipo(equipoEditar.getUid());
    }

    private void permisosGaleria() {
        mPresenter.checarPermisos(Manifest.permission.READ_EXTERNAL_STORAGE, Constantes.RP_STORAGE,
                getBaseContext(), this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPresenter.resultadoActivity(requestCode, resultCode, data);
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

    public void subirFotoEquipo() {
        mPresenter.subirEscudoEquipo(uriPhoto, equipoEditar.getUid());
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
     * ----------------------------------------metodos de la clase view
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

        desbloquearPantalla();
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
    public void guardarEquipo(String urlFotoEquipo) {
        equipoEditar.setUrlFoto(urlFotoEquipo);
        String nombreEquipo = etNombre.getText().toString().trim();
        equipoEditar.setNombre(nombreEquipo);

        DelSession mSession = DelSession.getInstance();
        equipoEditar.setUsuarioModificacion(mSession.getDelLogeado().getUid());
        equipoEditar.setFechaModificacion(Utilidades.fechaHoyFormateada());
        mPresenter.guardarEquipo(equipoEditar);
    }

    @Override
    public void equipoGuardado() {
        mPresenter.actualizarDelegado(equipoEditar.getNombre(), equipoEditar.getUrlFoto(),
                equipoEditar.getUid());
    }

    @Override
    public void finalGuardar() {
        desbloquearPantalla();
        Toast.makeText(this, R.string.nuevo_equipo_guardado, Toast.LENGTH_LONG).show();
    }
}