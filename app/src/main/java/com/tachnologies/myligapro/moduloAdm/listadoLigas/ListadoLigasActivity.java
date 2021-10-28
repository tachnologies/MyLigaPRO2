package com.tachnologies.myligapro.moduloAdm.listadoLigas;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tachnologies.myligapro.R;
import com.tachnologies.myligapro.common.model.dataSession.AdmSession;
import com.tachnologies.myligapro.common.pojo.CuentaAdminInvitado;
import com.tachnologies.myligapro.common.pojo.ItemCanchaListado;
import com.tachnologies.myligapro.common.pojo.ItemLigaListado;
import com.tachnologies.myligapro.common.pojo.UsuarioAdmin;
import com.tachnologies.myligapro.common.utils.Constantes;
import com.tachnologies.myligapro.moduloAdm.agregaEditaLigas.view.AgregaEditaLigas;
import com.tachnologies.myligapro.moduloAdm.listadoLigas.adapters.ItemLigaListadoAdapter;
import com.tachnologies.myligapro.moduloAdm.listadoLigas.adapters.OnItemClickListener;
import com.tachnologies.myligapro.moduloAdm.mainAdm.AdminActivity;

import java.util.ArrayList;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ListadoLigasActivity extends AppCompatActivity implements OnItemClickListener {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.contentMain)
    ConstraintLayout contentMain;
    @BindView(R.id.btnNuevo)
    FloatingActionButton btnNuevo;

    private String idCancha;
    private AdmSession mSession;
    private Map<String, ItemLigaListado> ligas;
    private ItemLigaListadoAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_ligas);
        ButterKnife.bind(this);
        configAdapter();
        configRecyclerView();

        mSession = AdmSession.getInstance();
        UsuarioAdmin admin = mSession.getAdminLogeado();
        idCancha = mSession.getIdCanchaSel();
        Map<String, ItemCanchaListado> misCanchas = admin.getMiCuenta().getCanchas();
        Map<String, CuentaAdminInvitado> adminInvitado = admin.getCuentasInvitado();

        boolean encontroCancha = false;

        if (idCancha != null) {
            if (misCanchas != null) {
                if (misCanchas.containsKey(idCancha)) {
                    encontroCancha = true;

                    ligas = misCanchas.get(idCancha).getLigas();
                    llenaAdapter();
                }
            }
            if (!encontroCancha) {
                if (adminInvitado != null) {
                    for (Map.Entry<String, CuentaAdminInvitado> entry : adminInvitado.entrySet()) {
                        CuentaAdminInvitado cuentaInvitado = entry.getValue();
                        Map<String, ItemCanchaListado> canchasInvitado = cuentaInvitado.getCanchas();

                        if (canchasInvitado.containsKey(idCancha)) {
                            encontroCancha = true;

                            ligas = misCanchas.get(idCancha).getLigas();
                            llenaAdapter();
                            break;
                        }
                    }
                }
            }

            if (encontroCancha) {
                for (Map.Entry<String, ItemLigaListado> liga : ligas.entrySet()) {
                    mAdapter.add(liga.getValue());
                }

                /**
                 * ------------------------------------     NO QUITARRRRRRR!!!!!!
                 * posteriormente vere si es necesario que si se tiene 1 sola cancha se de clic solo
                 * esto x que es posible agregar mas ligas
                 * if (mAdapter.getItemCount() > 0) {
                    if (mAdapter.getItemCount() == 1) {
                        onItemClick(mAdapter.getPrimero());
                    }
                } else {
                    System.out.println("-------- sin pinshis LIGAS :V");
                }*/
            } else {
                System.out.println("------------- No se encontro cancha puerks");
            }

        }
    }

    private void configAdapter() {
        mAdapter = new ItemLigaListadoAdapter(new ArrayList<ItemLigaListado>(), this);
    }

    private void configRecyclerView() {
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(this,
                getResources().getInteger(R.integer.columnas_listado));
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mAdapter);
    }

    public void llenaAdapter() {
        if (ligas != null) {
            for (Map.Entry<String, ItemLigaListado> liga : ligas.entrySet()) {
                ItemLigaListado itemLiga = liga.getValue();
                itemLiga.setUid(liga.getKey());
                mAdapter.add(itemLiga);
            }
        } else {
            System.out.println("--------------------- ligas viene NUUUULZZZZ madafakers!!! >:v");
        }
    }

    @Override
    public void onItemClick(ItemLigaListado item) {
        Intent intent = new Intent(this, AdminActivity.class);
        mSession.setIdLigaSel(item.getUid());
        mSession.setNombreLigaSel(item.getNombre());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.btnNuevo)
    public void onViewClicked() {
        Intent intent = new Intent(this, AgregaEditaLigas.class);
        intent.putExtra(Constantes.ES_NUEVO, true);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        //finish();
    }
}