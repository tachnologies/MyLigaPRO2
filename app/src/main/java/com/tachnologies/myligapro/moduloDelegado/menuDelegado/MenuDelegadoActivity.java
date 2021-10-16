package com.tachnologies.myligapro.moduloDelegado.menuDelegado;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tachnologies.myligapro.R;
import com.tachnologies.myligapro.common.model.dataSession.DelSession;
import com.tachnologies.myligapro.common.pojo.ItemMenuAdm;
import com.tachnologies.myligapro.common.pojo.UsuarioDelegado;
import com.tachnologies.myligapro.common.utils.Constantes;
import com.tachnologies.myligapro.moduloAdm.mainAdm.adapters.ItemMenuAdapter;
import com.tachnologies.myligapro.moduloAdm.mainAdm.adapters.OnItemClickListener;
import com.tachnologies.myligapro.moduloAdminJugadores.view.AdministrarJugadoresActivity;
import com.tachnologies.myligapro.moduloDelegado.editarEquipoDel.view.EditarEquipoDelActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MenuDelegadoActivity extends AppCompatActivity implements OnItemClickListener {

    @BindView(R.id.tvTitulo)
    TextView tvTitulo;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.contentMain)
    ConstraintLayout contentMain;

    private DelSession mSession;
    private ItemMenuAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_delegado);
        ButterKnife.bind(this);

        mSession = DelSession.getInstance();
        UsuarioDelegado delegado = mSession.getDelLogeado();

        if(delegado != null){
            configAdapter();
            configRecyclerView();
            llenarAdapter();

            String titulo = getString(R.string.menu_delegado_titulo, mSession.getNombreLigaSel());
            tvTitulo.setText(titulo);
        }else{
            System.out.println("------------------------- DELEGADO NULL");
        }
    }

    private void configAdapter() {
        mAdapter = new ItemMenuAdapter(new ArrayList<ItemMenuAdm>(), this, getBaseContext());
    }

    private void llenarAdapter(){
        /** El menu sera
         *  Tabla           Decretos
         *  Resultados      Goleo
         *  Sancionados     Equipo Estadisticas
         *  Mi Equipo       Mis Jugadores
         *  FanStats
         * */

        ItemMenuAdm item = new ItemMenuAdm(getString(R.string.item_menu_tabla_gral), R.drawable.tabla,1);
        mAdapter.add(item);

        item = new ItemMenuAdm(getString(R.string.item_menu_decretos), R.drawable.calendario,2);
        mAdapter.add(item);

        item = new ItemMenuAdm(getString(R.string.item_menu_resultados), R.drawable.gol,3);
        mAdapter.add(item);

        item = new ItemMenuAdm(getString(R.string.item_menu_goleo), R.drawable.trofeo_zapato,4);
        mAdapter.add(item);

        item = new ItemMenuAdm(getString(R.string.item_menu_sancionados), R.drawable.tarjeta_roja,5);
        mAdapter.add(item);

        item = new ItemMenuAdm(getString(R.string.item_menu_equipos_estadisticas), R.drawable.clipboard,6);
        mAdapter.add(item);

        item = new ItemMenuAdm(getString(R.string.item_menu_mi_equipo), R.drawable.escudo_equipo,7);
        mAdapter.add(item);

        item = new ItemMenuAdm(getString(R.string.item_menu_mis_jugadores), R.drawable.shoes,8);
        mAdapter.add(item);

        item = new ItemMenuAdm(getString(R.string.item_menu_fanstats), R.drawable.escudo_equipo2,9);
        mAdapter.add(item);
    }

    private void configRecyclerView() {
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(this,
                getResources().getInteger(R.integer.columnas_menu));

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onItemClick(ItemMenuAdm item) {
        System.out.println("------------------ item.getIdMenu():" + item.getIdMenu());
        Intent intent = null;

        /** El menu sera
         *  Tabla           Decretos
         *  Resultados      Goleo
         *  Sancionados     Equipo Estadisticas
         *  Mi Equipo       Mis Jugadores
         *  FanStats
         * */

        switch (item.getIdMenu()){
            case 1:
                Toast.makeText(this, "Tabla proximamente", Toast.LENGTH_LONG).show();
                break;
            case 2:
                Toast.makeText(this, "Decretos proximamente", Toast.LENGTH_LONG).show();
                break;
            case 3:
                Toast.makeText(this, "resultados proximamente", Toast.LENGTH_LONG).show();
                break;
            case 4:
                Toast.makeText(this, "goleo proximamente", Toast.LENGTH_LONG).show();
                break;
            case 5:
                Toast.makeText(this, "sancionados proximamente", Toast.LENGTH_LONG).show();
                break;
            case 6:
                Toast.makeText(this, "Equipos estadisticas", Toast.LENGTH_LONG).show();
                break;
            case 7:
                intent = new Intent(this, EditarEquipoDelActivity.class);
                //Toast.makeText(this, "mi equipo proximamente", Toast.LENGTH_LONG).show();
                break;
            case 8:
                //Toast.makeText(this, "mis jugadores proximamente", Toast.LENGTH_LONG).show();
                intent = new Intent(this, AdministrarJugadoresActivity.class);
                intent.putExtra(Constantes.TIPO_USUARIO, Constantes.DELEGADO);
                break;
            case 9:
                Toast.makeText(this, "FanStats proximamente", Toast.LENGTH_LONG).show();
                break;
            default:
        }

        if(intent != null){
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
    }
}