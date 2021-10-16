package com.tachnologies.myligapro.moduloAdm.primerIngreso.altaLiga;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.tachnologies.myligapro.R;
import com.tachnologies.myligapro.common.animaciones.cargando.CargandoDialog;
import com.tachnologies.myligapro.common.model.dataSession.AdmSession;
import com.tachnologies.myligapro.common.pojo.Cancha;
import com.tachnologies.myligapro.common.pojo.Cuenta;
import com.tachnologies.myligapro.common.pojo.ItemCanchaListado;
import com.tachnologies.myligapro.common.pojo.ItemLigaListado;
import com.tachnologies.myligapro.common.pojo.Liga;
import com.tachnologies.myligapro.common.pojo.MiCuentaAdmin;
import com.tachnologies.myligapro.common.pojo.UsuarioAdmin;
import com.tachnologies.myligapro.common.utils.Constantes;
import com.tachnologies.myligapro.common.utils.Utilidades;
import com.tachnologies.myligapro.common.utils.Validaciones;
import com.tachnologies.myligapro.moduloAdm.mainAdm.AdminActivity;
import com.tachnologies.myligapro.moduloAdm.primerIngreso.AltasPresenter;
import com.tachnologies.myligapro.moduloAdm.primerIngreso.AltasPresenterClass;
import com.tachnologies.myligapro.moduloAdm.primerIngreso.AltasView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AltaLigaActivity extends AppCompatActivity implements AltasView {

    @BindView(R.id.etNombre)
    TextInputEditText etNombre;
    @BindView(R.id.etJornadas)
    TextInputEditText etJornadas;
    @BindView(R.id.etEquiposCalifican)
    TextInputEditText etEquiposCalifican;
    @BindView(R.id.cbRepechaje)
    CheckBox cbRepechaje;
    @BindView(R.id.etRepechaje)
    TextInputEditText etRepechaje;
    @BindView(R.id.cbEmpatePuntoExtra)
    CheckBox cbEmpatePuntoExtra;
    @BindView(R.id.tlRepechaje)
    TextInputLayout tlRepechaje;
    @BindView(R.id.btnGuardar)
    Button btnGuardar;

    private UsuarioAdmin admin;
    private Cancha cancha;
    private Cuenta cuenta;
    private Liga liga;
    /** para lo del gif */
    CargandoDialog cargando;

    private AltasPresenter mPresenter;

    private AdmSession mSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alta_liga);
        ButterKnife.bind(this);
        mPresenter = new AltasPresenterClass(this);
        mPresenter.onCreate();
        mSession = AdmSession.getInstance();
        admin = (UsuarioAdmin) getIntent().getSerializableExtra(Constantes.USU_ADMIN);
        cancha = (Cancha) getIntent().getSerializableExtra(Constantes.CANCHA);
        cargando = new CargandoDialog();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPresenter.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    @OnClick({R.id.cbRepechaje, R.id.cbEmpatePuntoExtra})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.cbRepechaje:
                float alpha = 0f;
                if(cbRepechaje.isChecked()){
                    tlRepechaje.setVisibility(View.VISIBLE);
                    alpha = 1f;
                }else{
                    tlRepechaje.setVisibility(View.GONE);
                    etRepechaje.setText("");
                }
                int mediumAnimationDuration = getResources().getInteger(
                        android.R.integer.config_mediumAnimTime);

                tlRepechaje.animate()
                        .alpha(alpha)
                        .setDuration(mediumAnimationDuration)
                        .setListener(null);
                break;

            default:
        }
    }

    @OnClick(R.id.btnGuardar)
    public void guardar() {
        bloquearPantalla();
        if(esValido()){

            String stringTimestamp = Utilidades.obtenerStringTimestamp();
            cuenta = new Cuenta(admin.getUid(), "S");
            String uidLiga = "L-" + stringTimestamp;
            liga = new Liga(uidLiga, etNombre.getText().toString(), admin.getUid());
            /** Ver si se puede iniciar sin una liga dada de alta o poner la opcion torneo*/
            liga.setNumJornadas(Integer.parseInt(etJornadas.getText().toString()));
            liga.setEquiposCalifican(Integer.parseInt(etEquiposCalifican.getText().toString()));

            if(cbEmpatePuntoExtra.isChecked()){
                liga.setEmpateJuegaPuntoExtra(true);
            }else{
                liga.setEmpateJuegaPuntoExtra(false);
            }

            if(cbRepechaje.isChecked()){
                liga.setHayRepechaje(true);
                liga.setEquiposRepechaje(Integer.parseInt(etRepechaje.getText().toString()));
            }else{
                liga.setHayRepechaje(false);
            }

            liga.setEstatus("A");
            liga.setUsuarioAlta(admin.getUid());
            liga.setFechaAlta(Utilidades.fechaHoyFormateada());

            Map<String, Liga> ligas = new HashMap<String, Liga>();
            ligas.put(liga.getUid(), liga);
            cancha.setLigas(ligas);

            cancha.setUid("C-" + stringTimestamp);
            Map<String, Cancha> canchas = new HashMap<String, Cancha>();
            canchas.put(cancha.getUid(), cancha);

            cuenta.setCanchas(canchas);
            mPresenter.altaCuenta(cuenta);

        }else{
            desbloquearPantalla();
        }

    }

    private boolean esValido(){
        boolean esValido = true;
        if (etNombre.getText() == null || etNombre.getText().toString().trim().isEmpty()){
            etNombre.setError(getString(R.string.common_error_ingrese_nombre));
            esValido = false;
        }

        if (etJornadas.getText() == null || etJornadas.getText().toString().trim().isEmpty()){
            etJornadas.setError(getString(R.string.alta_liga_error_num_jornadas));
            esValido = false;
        }

        if (etEquiposCalifican.getText() == null || etEquiposCalifican.getText().toString().trim().isEmpty()){
            etEquiposCalifican.setError(getString(R.string.alta_liga_error_num_equipos_liguilla));
            esValido = false;
        }else{
            if(!Validaciones.esNumerico(etEquiposCalifican.getText().toString())){
                etEquiposCalifican.setError(getString(R.string.alta_liga_error_no_numero));
                esValido = false;
            }
        }

        if(cbRepechaje.isChecked()){
            if (etRepechaje.getText() == null || etRepechaje.getText().toString().trim().isEmpty()){
                etRepechaje.setError(getString(R.string.alta_liga_error_num_equipos_repechaje));
                esValido = false;
            }else{
                if(!Validaciones.esNumerico(etRepechaje.getText().toString())){
                    etEquiposCalifican.setError(getString(R.string.alta_liga_error_no_numero));
                    esValido = false;
                }
            }
        }
        return esValido;
    }

    /** ---------------------------------- AltasView*/
    @Override
    public void bloquearPantalla() {
        desactivarComponentes();
        cargando.show(getSupportFragmentManager(), Constantes.CADENA_VACIA);
    }

    @Override
    public void desbloquearPantalla() {
        activarComponentes();
        cargando.dismiss();
    }
    @Override
    public void activarComponentes() {
        etNombre.setEnabled(true);
        etJornadas.setEnabled(true);
        etEquiposCalifican.setEnabled(true);
        cbRepechaje.setEnabled(true);
        etRepechaje.setEnabled(true);
        cbEmpatePuntoExtra.setEnabled(true);
        btnGuardar.setEnabled(true);
    }

    @Override
    public void desactivarComponentes() {
        etNombre.setEnabled(false);
        etJornadas.setEnabled(false);
        etEquiposCalifican.setEnabled(false);
        cbRepechaje.setEnabled(false);
        etRepechaje.setEnabled(false);
        cbEmpatePuntoExtra.setEnabled(false);
        btnGuardar.setEnabled(false);
    }

    @Override
    public void cuentaGuardada(String uidCuenta) {
        cuenta.setUid(uidCuenta);
        mSession.setIdCuentaSel(uidCuenta);

        Map<String, ItemLigaListado> itemsLiga = new HashMap<String, ItemLigaListado>();
        ItemLigaListado itemLiga = new ItemLigaListado(liga.getUid(), liga.getNombre(), liga.getUrlFotoLogo());
        itemsLiga.put(liga.getUid(), itemLiga);

        Map<String, ItemCanchaListado> itemsCancha = new HashMap<String, ItemCanchaListado>();
        ItemCanchaListado itemCancha =
                new ItemCanchaListado(cancha.getUid(), cancha.getNombre(), cancha.getUrlFotoLogo(),
                        cancha.getDireccion(), itemsLiga, uidCuenta);

        itemsCancha.put(cancha.getUid(), itemCancha);

        MiCuentaAdmin miCuenta = new MiCuentaAdmin(uidCuenta, itemsCancha);
        admin.setMiCuenta(miCuenta);
        mPresenter.altaUsuarioAdm(admin);

    }

    @Override
    public void abrirAdmActivity() {
        activarComponentes();
        Toast.makeText(this, "Guardado con exito", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, AdminActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    @Override
    public void mostrarError(int resMsg) {
        Toast.makeText(this, resMsg, Toast.LENGTH_LONG).show();
    }
}