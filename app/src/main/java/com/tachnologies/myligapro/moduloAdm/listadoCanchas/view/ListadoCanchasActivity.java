package com.tachnologies.myligapro.moduloAdm.listadoCanchas.view;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tachnologies.myligapro.R;
import com.tachnologies.myligapro.common.model.dataSession.AdmSession;
import com.tachnologies.myligapro.common.pojo.CuentaAdminInvitado;
import com.tachnologies.myligapro.common.pojo.ItemCanchaListado;
import com.tachnologies.myligapro.common.pojo.UsuarioAdmin;
import com.tachnologies.myligapro.moduloAdm.listadoCanchas.view.adapters.ItemCanchaListadoAdapter;
import com.tachnologies.myligapro.moduloAdm.listadoCanchas.view.adapters.OnItemClickListener;
import com.tachnologies.myligapro.moduloAdm.listadoLigas.ListadoLigasActivity;
import java.util.ArrayList;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListadoCanchasActivity extends AppCompatActivity implements OnItemClickListener {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.contentMain)
    ConstraintLayout contentMain;
    private AdmSession mSession;
    private ItemCanchaListadoAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_canchas);
        ButterKnife.bind(this);

        mSession = AdmSession.getInstance();
        UsuarioAdmin admin = mSession.getAdminLogeado();

        Map<String, ItemCanchaListado> misCanchas = admin.getMiCuenta().getCanchas();
        Map<String, CuentaAdminInvitado> adminInvitado = admin.getCuentasInvitado();

        configAdapter();
        configRecyclerView();

        if(misCanchas != null) {

            for (Map.Entry<String, ItemCanchaListado> entry : misCanchas.entrySet()) {
                String key = entry.getKey();
                ItemCanchaListado value = entry.getValue();
                value.setIdCancha(key);
                value.setIdCuenta(admin.getMiCuenta().getUidCuenta());
                mAdapter.add(value);
            }
        }

        if(adminInvitado != null) {
            for (Map.Entry<String, CuentaAdminInvitado> entry : adminInvitado.entrySet()) {
                String key = entry.getKey();
                CuentaAdminInvitado valor = entry.getValue();
                Map<String, ItemCanchaListado> canchasInvitado = valor.getCanchas();

                for (Map.Entry<String, ItemCanchaListado> mapa : canchasInvitado.entrySet()) {
                    String llave = entry.getKey();
                    ItemCanchaListado cancha = mapa.getValue();
                    cancha.setIdCuenta(key);
                    mAdapter.add(cancha);
                }
            }
        }
        if(mAdapter.getItemCount() > 0 ){
            if(mAdapter.getItemCount() == 1){
                onItemClick(mAdapter.getPrimero());
            }
        }else{
            System.out.println("No tienes canchas HOMS!! :V");
        }
    }

    private void configAdapter(){
        mAdapter = new ItemCanchaListadoAdapter(new ArrayList<ItemCanchaListado>(), this);
    }

    private void configRecyclerView() {
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(this,
                getResources().getInteger(R.integer.columnas_listado));
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onItemClick(ItemCanchaListado item) {
        mSession.setIdCanchaSel(item.getIdCancha());
        mSession.setIdCuentaSel(item.getIdCuenta());
        mSession.setNombreCanchaSel(item.getNombre());

        Intent intent = new Intent(this, ListadoLigasActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

}