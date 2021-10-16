package com.tachnologies.myligapro.moduloDelegado.jugadoresDel.view;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tachnologies.myligapro.R;
import com.tachnologies.myligapro.common.animaciones.cargando.CargandoDialog;
import com.tachnologies.myligapro.common.model.dataSession.DelSession;
import com.tachnologies.myligapro.common.pojo.Equipo;
import com.tachnologies.myligapro.moduloAdm.jugadoresAdm.adapters.JugadorAdmAdapter;
import com.tachnologies.myligapro.moduloDelegado.jugadoresDel.JugadoresDelPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class JugadoresDelActivity extends AppCompatActivity {

    @BindView(R.id.imgFoto)
    AppCompatImageView imgFoto;
    @BindView(R.id.tvnombreEquipo)
    TextView tvnombreEquipo;
    @BindView(R.id.tvTitulo)
    TextView tvTitulo;
    @BindView(R.id.tvMensaje)
    TextView tvMensaje;
    @BindView(R.id.contentMain)
    ConstraintLayout contentMain;
    @BindView(R.id.btnNuevo)
    FloatingActionButton btnNuevo;

    /**para lo del gif*/
    private CargandoDialog cargando;
    private JugadoresDelPresenter mPresenter;
    private Equipo equipoEditar;
    private JugadorAdmAdapter mAdapter;
    private DelSession mSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jugadores_del);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnNuevo)
    public void onViewClicked() {
    }
}