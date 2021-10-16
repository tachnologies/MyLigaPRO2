package com.tachnologies.myligapro.moduloDelegado.mainDelegado.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tachnologies.myligapro.R;
import com.tachnologies.myligapro.common.animaciones.cargando.CargandoDialog;
import com.tachnologies.myligapro.common.model.dataSession.DelSession;
import com.tachnologies.myligapro.common.pojo.RefEquipoDelegado;
import com.tachnologies.myligapro.common.pojo.UsuarioDelegado;
import com.tachnologies.myligapro.common.utils.Constantes;
import com.tachnologies.myligapro.moduloDelegado.editarEquipoDel.view.EditarEquipoDelActivity;
import com.tachnologies.myligapro.moduloDelegado.mainDelegado.adapters.ItemRefEquipoDelAdapter;
import com.tachnologies.myligapro.moduloDelegado.mainDelegado.adapters.OnItemClickListener;
import com.tachnologies.myligapro.moduloDelegado.menuDelegado.MenuDelegadoActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainDelegadoActivity extends AppCompatActivity implements OnItemClickListener,
        MainDelegadoView{

    @BindView(R.id.tvTitulo)
    TextView tvTitulo;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.contentMain)
    ConstraintLayout contentMain;
    @BindView(R.id.tvMensaje)
    TextView tvMensaje;
    private ItemRefEquipoDelAdapter mAdapter;
    private DelSession mSession;
    /**para lo del gif*/
    private CargandoDialog cargando;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delegado);
        ButterKnife.bind(this);

        configAdapter();
        configRecyclerView();

        mSession = DelSession.getInstance();
        UsuarioDelegado delegado = mSession.getDelLogeado();

        List<RefEquipoDelegado> misEquipos =
                (delegado.getEquipos() != null && !delegado.getEquipos().isEmpty())?
                        delegado.getEquipos():null;

        if(misEquipos != null){
            bloquearPantalla();
            for(RefEquipoDelegado equipo:misEquipos){
                mAdapter.add(equipo);
            }
            desbloquearPantalla();
        }else{
            tvMensaje.setVisibility(View.VISIBLE);
            tvMensaje.setText(R.string.main_delegado_sin_equipos);
        }
    }

    private void configAdapter() {
        mAdapter = new ItemRefEquipoDelAdapter(new ArrayList<RefEquipoDelegado>(), this);
    }

    private void configRecyclerView() {
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(this,
                getResources().getInteger(R.integer.columnas_listado));
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mAdapter);
    }

    /** -------------------------------------- Metodos del view*/
    @Override
    public void bloquearPantalla() {
        cargando = new CargandoDialog();
        cargando.show(getSupportFragmentManager(), Constantes.CADENA_VACIA);
    }

    @Override
    public void bloquearPantalla(int resMsge) {
        cargando = new CargandoDialog(resMsge);
        cargando.show(getSupportFragmentManager(), Constantes.CADENA_VACIA);
    }
    @Override
    public void desbloquearPantalla() {
        cargando.dismiss();
    }

    @Override
    public void equipoAgregado(RefEquipoDelegado equipo) {

    }

    @Override
    public void equipoActualizado(RefEquipoDelegado equipo) {

    }

    @Override
    public void equipoBorrado(RefEquipoDelegado equipo) {

    }

    @Override
    public void onShowError(int resMsg) {

    }
    /** del clickListener*/
    @Override
    public void onItemClick(RefEquipoDelegado equipo) {
        mSession.setIdCuentaSel(equipo.getUidCuenta());
        mSession.setIdCanchaSel(equipo.getUidCancha());
        mSession.setIdLigaSel(equipo.getUidLiga());
        mSession.setIdEquipoSel(equipo.getUidEquipo());

        mSession.setNombreCanchaSel(equipo.getNombreCancha());
        mSession.setNombreLigaSel(equipo.getNombreLiga());
        mSession.setNombreEquipoSel(equipo.getNombreEquipo());

        mSession.setUrlLogoCanchaSel(equipo.getUrlLogoCancha());
        mSession.setUrlLogoLigaSel(equipo.getUrlLogoLiga());
        mSession.setUrlLogoEquipoSel(equipo.getUrlLogoEquipo());

        Intent intent = new Intent(this, MenuDelegadoActivity.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}