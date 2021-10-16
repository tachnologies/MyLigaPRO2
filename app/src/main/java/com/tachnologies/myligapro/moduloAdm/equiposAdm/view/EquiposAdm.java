package com.tachnologies.myligapro.moduloAdm.equiposAdm.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.tachnologies.myligapro.R;
import com.tachnologies.myligapro.common.animaciones.cargando.CargandoDialog;
import com.tachnologies.myligapro.common.model.dataSession.AdmSession;
import com.tachnologies.myligapro.common.pojo.Equipo;
import com.tachnologies.myligapro.common.pojo.ItemEquipoListado;
import com.tachnologies.myligapro.common.utils.Constantes;
import com.tachnologies.myligapro.moduloAdm.editarEquipo.view.EditarEquipoAdm;
import com.tachnologies.myligapro.moduloAdm.equiposAdm.EquiposAdmPresenter;
import com.tachnologies.myligapro.moduloAdm.equiposAdm.EquiposAdmPresenterClass;
import com.tachnologies.myligapro.moduloAdm.equiposAdm.adapters.ItemEquipoListadoAdapter;
import com.tachnologies.myligapro.moduloAdm.equiposAdm.adapters.OnItemClickListener;
import com.tachnologies.myligapro.moduloAdm.nuevoEquipo.view.NuevoEquipoActivity;
import com.tachnologies.myligapro.moduloAdminJugadores.view.AdministrarJugadoresActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EquiposAdm extends AppCompatActivity implements OnItemClickListener,
        EquiposAdmView {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.contentMain)
    ConstraintLayout contentMain;
    @BindView(R.id.tvMensaje)
    TextView tvMensaje;
    @BindView(R.id.btnNuevo)
    FloatingActionButton btnNuevo;
    private ItemEquipoListadoAdapter mAdapter;

    private EquiposAdmPresenter mPresenter;
    /**para lo del gif*/
    private CargandoDialog cargando;
    private String menuOrigen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipos_adm);
        ButterKnife.bind(this);

        configAdapter();
        configRecyclerView();

        Intent intent = getIntent();
        menuOrigen = intent.getStringExtra(Constantes.COMMON_MENU_ORIGEN);

        if(menuOrigen != null && menuOrigen.equals(Constantes.PATH_JUGADORES)){
            btnNuevo.setVisibility(View.GONE);
        }

        mPresenter = new EquiposAdmPresenterClass(this);
        mPresenter.onCreate();

        if(mAdapter.getItemCount() == 0){
            tvMensaje.setVisibility(View.VISIBLE);
            tvMensaje.setText(R.string.equipos_adm_error_no_equipos);
        }
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
        mAdapter.vaciarListado();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    private void configAdapter() {
        mAdapter = new ItemEquipoListadoAdapter(new ArrayList<ItemEquipoListado>(), this);
    }

    private void configRecyclerView() {
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(this,
                getResources().getInteger(R.integer.columnas_listado));
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mAdapter);
    }

    @OnClick(R.id.btnNuevo)
    public void onViewClicked() {
        Intent intent = new Intent(this, NuevoEquipoActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    /*** del view*/
    @Override
    public void bloquearPantalla() {
        cargando = new CargandoDialog(R.string.equipos_adm_msje_borrando_equipo);
        cargando.show(getSupportFragmentManager(), Constantes.CADENA_VACIA);
    }

    @Override
    public void desbloquearPantalla() {
        cargando.dismiss();
    }

    @Override
    public void equipoAgregado(Equipo equipo) {
        ItemEquipoListado itemEquipo = new ItemEquipoListado(equipo.getUid(), equipo.getNombre(),
                equipo.getUrlFoto());

        mAdapter.add(itemEquipo);

        tvMensaje.setVisibility(View.GONE);
    }

    @Override
    public void equipoActualizado(Equipo equipo) {
        ItemEquipoListado itemEquipo = new ItemEquipoListado(equipo.getUid(), equipo.getNombre(),
                equipo.getUrlFoto());

        mAdapter.update(itemEquipo);
    }

    @Override
    public void equipoBorrado(Equipo equipo) {
        ItemEquipoListado itemEquipo = new ItemEquipoListado(equipo.getUid(), equipo.getNombre(),
                equipo.getUrlFoto());

        mAdapter.remove(itemEquipo);
        desbloquearPantalla();
    }


    @Override
    public void onShowError(int resMsg) {
        Snackbar.make(contentMain, resMsg, Snackbar.LENGTH_LONG).show();
        desbloquearPantalla();
    }

    @Override
    public void onItemClick(ItemEquipoListado equipo) {
        Intent intent;

        AdmSession mSession = AdmSession.getInstance();
        mSession.setIdEquipoSel(equipo.getUidEquipo());
        mSession.setNombreEquipoSel(equipo.getNombreEquipo());
        mSession.setUrlLogoEquipoSel(equipo.getUrlFotoEquipo());

        switch(menuOrigen){
            case Constantes.PATH_JUGADORES:
                intent = new Intent(this, AdministrarJugadoresActivity.class);
                intent.putExtra(Constantes.TIPO_USUARIO, Constantes.ADMIN_LIGA);
                break;
            case Constantes.PATH_EQUIPOS: default:
                intent = new Intent(this, EditarEquipoAdm.class);
                break;
        }

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();

    }

    @Override
    public void onLongItemClick(ItemEquipoListado equipo) {
        Vibrator vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator != null){
            vibrator.vibrate(100);
        }

        new AlertDialog.Builder(this)
                .setTitle(R.string.equipos_adm_borrar_equipo)
                .setMessage(getBaseContext().getString(
                        R.string.equipos_adm_dialogo_borrar_equipo, equipo.getNombreEquipo()))
                .setPositiveButton(R.string.common_borrar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        bloquearPantalla();
                        mPresenter.eliminarEquipo(equipo.getUidEquipo());
                    }
                })
                .setNegativeButton(R.string.common_cancelar, null)
                .show();
    }
}