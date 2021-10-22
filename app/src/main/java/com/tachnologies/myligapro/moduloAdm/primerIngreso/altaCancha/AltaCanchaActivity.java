package com.tachnologies.myligapro.moduloAdm.primerIngreso.altaCancha;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.tachnologies.myligapro.R;
import com.tachnologies.myligapro.common.animaciones.cargando.CargandoDialog;
import com.tachnologies.myligapro.common.model.dataSession.AdmSession;
import com.tachnologies.myligapro.common.pojo.Cancha;
import com.tachnologies.myligapro.common.pojo.UsuarioAdmin;
import com.tachnologies.myligapro.common.utils.Constantes;
import com.tachnologies.myligapro.moduloAdm.primerIngreso.altaCancha.ubicacion.UbicacionCanchaActivity;
import com.tachnologies.myligapro.moduloAdm.primerIngreso.altaLiga.AltaLigaActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AltaCanchaActivity extends AppCompatActivity {

    @BindView(R.id.etNombre)
    TextInputEditText etNombre;
    @BindView(R.id.btnSiguiente)
    Button btnSiguiente;
    @BindView(R.id.cbUbicacion)
    CheckBox cbUbicacion;
    @BindView(R.id.cbFotos)
    CheckBox cbFotos;
    @BindView(R.id.llFotos)
    LinearLayout llFotos;

    private UsuarioAdmin admin;

    /** para lo del gif */
    CargandoDialog cargando;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alta_cancha);
        ButterKnife.bind(this);

        admin = (UsuarioAdmin) getIntent().getSerializableExtra(Constantes.USU_ADMIN);
        cargando = new CargandoDialog();

    }

    @OnClick(R.id.btnSiguiente)
    public void siguiente() {
        bloquearPantalla();

        if (esValido()) {
            Intent intent;
            if(cbUbicacion.isChecked()){
                intent = new Intent(this, UbicacionCanchaActivity.class);
            }else{
                intent = new Intent(this, AltaLigaActivity.class);
            }

            List<String> admins = new ArrayList<String>();
            admins.add(admin.getUid());
            Cancha cancha = new Cancha(etNombre.getText().toString().trim(), admins, admin.getUid());

            AdmSession mSession = AdmSession.getInstance();
            mSession.setAdminLogeado(admin);

            intent.putExtra(Constantes.ESTATUS, 0);
            //intent.putExtra(Constantes.USU_ADMIN, admin);
            intent.putExtra(Constantes.CANCHA, cancha);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
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

    /** click */
    @OnClick({R.id.cbUbicacion, R.id.cbFotos})
    public void onViewClicked(View view) {
        float alpha = 0f;
        int mediumAnimationDuration = getResources().getInteger(
                android.R.integer.config_mediumAnimTime);

        switch (view.getId()) {
            case R.id.cbUbicacion:
                break;
            case R.id.cbFotos:

                if (cbUbicacion.isChecked()) {
                    llFotos.setVisibility(View.VISIBLE);
                    alpha = 1f;
                } else {
                    llFotos.setVisibility(View.GONE);
                }

                llFotos.animate()
                        .alpha(alpha)
                        .setDuration(mediumAnimationDuration)
                        .setListener(null);

                break;
            default:
        }
    }

    public void bloquearPantalla() {
        cargando.show(getSupportFragmentManager(), Constantes.CADENA_VACIA);
        etNombre.setEnabled(false);
        btnSiguiente.setEnabled(false);
        cbUbicacion.setEnabled(false);
        cbFotos.setEnabled(false);
    }

    public void desbloquearPantalla() {
        cargando.dismiss();
        etNombre.setEnabled(true);
        btnSiguiente.setEnabled(true);
        cbUbicacion.setEnabled(true);
        cbFotos.setEnabled(true);
    }

}