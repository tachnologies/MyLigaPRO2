package com.tachnologies.myligapro.moduloAdm.primerIngreso.altaAdmin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.textfield.TextInputEditText;
import com.tachnologies.myligapro.R;
import com.tachnologies.myligapro.common.animaciones.cargando.CargandoDialog;
import com.tachnologies.myligapro.common.pojo.UsuarioAdmin;
import com.tachnologies.myligapro.common.utils.Constantes;
import com.tachnologies.myligapro.common.utils.Utilidades;
import com.tachnologies.myligapro.moduloAdm.primerIngreso.altaCancha.AltaCanchaActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AltaAdminActivity extends AppCompatActivity{

    /**@BindView(R.id.ivFoto)
    ImageView ivFoto;*/
    @BindView(R.id.btnSiguiente)
    Button btnSiguiente;
    @BindView(R.id.etNombre)
    TextInputEditText etNombre;
    @BindView(R.id.contentMain)
    ConstraintLayout contentMain;
    private UsuarioAdmin admin;

    /** para lo del gif */
    CargandoDialog cargando;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alta_admin);
        ButterKnife.bind(this);

        admin = (UsuarioAdmin) getIntent().getSerializableExtra(Constantes.USU_ADMIN);
        System.out.println("------------------ AltaAdminActivity uid: " + admin.getUid());

        cargando = new CargandoDialog();
    }

    @OnClick(R.id.btnSiguiente)
    public void siguiente() {
        bloquearPantalla();
        if (esValido()) {
            Intent intent = new Intent(this, AltaCanchaActivity.class);
            admin.setNombre(etNombre.getText().toString());
            admin.setEstatus("A");
            admin.setFechaAlta(Utilidades.fechaHoyFormateada());
            admin.setUsuarioAlta(admin.getUid());
            intent.putExtra(Constantes.USU_ADMIN, admin);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            desbloquearPantalla();
            finish();
        }else{
            desbloquearPantalla();
        }

    }

    private boolean esValido() {
        if (etNombre.getText() == null || etNombre.getText().toString().trim().isEmpty()) {
            etNombre.setError(getString(R.string.common_error_ingrese_nombre));
            return false;
        } else {
            return true;
        }
    }

    public void bloquearPantalla() {
        cargando.show(getSupportFragmentManager(), Constantes.CADENA_VACIA);
        etNombre.setEnabled(false);
    }

    public void desbloquearPantalla() {
        cargando.dismiss();
        etNombre.setEnabled(true);
    }

}