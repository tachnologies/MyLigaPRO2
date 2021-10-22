package com.tachnologies.myligapro.moduloAdm;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.tachnologies.myligapro.R;
import com.tachnologies.myligapro.common.animaciones.cargando.CargandoDialog;
import com.tachnologies.myligapro.common.model.dataSession.AdmSession;
import com.tachnologies.myligapro.common.utils.Constantes;
import com.tachnologies.myligapro.common.utils.Validaciones;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AgregaEditaLigas extends AppCompatActivity {

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

    /**
     * para lo del gif
     */
    private CargandoDialog cargando;
    private boolean esNuevo;
    private AdmSession mSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agrega_edita_ligas);
        ButterKnife.bind(this);

        esNuevo = (boolean) getIntent().getSerializableExtra(Constantes.ES_NUEVO);

        if(esNuevo){
            tvTitulo.setText(R.string.alta_liga_titulo);
        }else{
            tvTitulo.setText(R.string.editar_liga_titulo);
        }

        mSession = AdmSession.getInstance();
        cargando = new CargandoDialog();

        tvDescTipoTorneo.setText(Constantes.CADENA_VACIA);

        spTipoTorneo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("---------------------------------- onItemSelected");
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
                System.out.println("---------------------------------- onItemSelected");
                tvDescTipoTorneo.setText(Constantes.CADENA_VACIA);
                tvDescTipoTorneo.setVisibility(View.GONE);
            }
        });

    }

    @OnClick({R.id.imgFoto, R.id.imgBorrarFoto, R.id.imgDesdeGaleria, R.id.cbLimJugadores,
            R.id.cbRepechaje, R.id.cbEmpatePuntoExtra, R.id.btnGuardar, R.id.btnCancelar,
            R.id.btnEliminar})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgFoto:
                break;
            case R.id.imgBorrarFoto:
                break;
            case R.id.imgDesdeGaleria:
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
                break;
            default:
        }
    }

}