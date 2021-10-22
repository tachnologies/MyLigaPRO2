package com.tachnologies.myligapro.moduloAdm.mainAdm;

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
import com.tachnologies.myligapro.common.model.dataSession.AdmSession;
import com.tachnologies.myligapro.common.pojo.ItemMenuAdm;
import com.tachnologies.myligapro.common.pojo.UsuarioAdmin;
import com.tachnologies.myligapro.common.utils.Constantes;
import com.tachnologies.myligapro.moduloAdm.equiposAdm.view.EquiposAdm;
import com.tachnologies.myligapro.moduloAdm.mainAdm.adapters.ItemMenuAdapter;
import com.tachnologies.myligapro.moduloAdm.mainAdm.adapters.OnItemClickListener;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;

public class AdminActivity extends AppCompatActivity implements OnItemClickListener {

    @BindView(R.id.tvTitulo)
    TextView tvTitulo;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.contentMain)
    ConstraintLayout contentMain;
    private AdmSession mSession;
    private ItemMenuAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        ButterKnife.bind(this);

        mSession = AdmSession.getInstance();
        UsuarioAdmin admin = mSession.getAdminLogeado();

        if (admin != null) {
            configAdapter();
            configRecyclerView();
            llenarAdapter();
            tvTitulo.setText(getString(R.string.common_liga) + ": " +mSession.getNombreLigaSel());
        } else {
            System.out.println("------------------------- ADMIN NULL");
        }
    }


    private void configAdapter() {
        mAdapter = new ItemMenuAdapter(new ArrayList<ItemMenuAdm>(), this, getBaseContext());
    }

    private void llenarAdapter(){
        /** El menu sera
         * Decretos             calendario             Resultados           gol
         * Tabla General        tabla                  Tabla Sancionados    tarjeta_roja
         * Tabla Goleo          trofeo_zapato          Administradores      tabla_2
         * Delegados            delegado               Equipos              escudo_equipo
         * Editar Datos Cancha  campo_2                Editar Datos Liga trofeo_2
         *
         * ----- de momento los ultimos 2 no apareceran
         * Reglamento           Noticias
         * */

        ItemMenuAdm item = new ItemMenuAdm(getString(R.string.item_menu_decretos), R.drawable.calendario,1);
        mAdapter.add(item);
        item = new ItemMenuAdm(getString(R.string.item_menu_resultados), R.drawable.gol,2);
        mAdapter.add(item);
        item = new ItemMenuAdm(getString(R.string.item_menu_tabla_gral), R.drawable.tabla,3);
        mAdapter.add(item);
        item = new ItemMenuAdm(getString(R.string.item_menu_sancionados), R.drawable.tarjeta_roja,4);
        mAdapter.add(item);
        item = new ItemMenuAdm(getString(R.string.item_menu_goleo), R.drawable.trofeo_zapato,5);
        mAdapter.add(item);
        item = new ItemMenuAdm(getString(R.string.item_menu_admins), R.drawable.tabla_2,6);
        mAdapter.add(item);
        item = new ItemMenuAdm(getString(R.string.item_menu_jugadores), R.drawable.shoes,7);
        mAdapter.add(item);
        item = new ItemMenuAdm(getString(R.string.item_menu_equipos), R.drawable.escudo_equipo,8);
        mAdapter.add(item);
        item = new ItemMenuAdm(getString(R.string.item_menu_delegados), R.drawable.delegado,9);
        mAdapter.add(item);
        item = new ItemMenuAdm(getString(R.string.item_menu_editar_cancha), R.drawable.campo_2,10);
        mAdapter.add(item);
        item = new ItemMenuAdm(getString(R.string.item_menu_editar_liga), R.drawable.trofeo,11);
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
        Intent intent = null;
        /** El menu sera
         * Decretos             calendario             Resultados           gol
         * Tabla General        tabla                  Tabla Sancionados    tarjeta_roja
         * Tabla Goleo          trofeo_zapato          Administradores      tabla_2
         * Delegados            delegado               Equipos              escudo_equipo
         * Editar Datos Cancha  campo_2                Editar Datos Liga trofeo_2*/
        switch (item.getIdMenu()){
            case 1:
                Toast.makeText(this, "Decretos proximamente", Toast.LENGTH_LONG).show();
                break;
            case 2:
                Toast.makeText(this, "Resultados proximamente", Toast.LENGTH_LONG).show();
                break;
            case 3:
                Toast.makeText(this, "Tabla General proximamente", Toast.LENGTH_LONG).show();
                break;
            case 4:
                Toast.makeText(this, "Sancionados proximamente", Toast.LENGTH_LONG).show();
                break;
            case 5:
                Toast.makeText(this, "Goleo proximamente", Toast.LENGTH_LONG).show();
                break;
            case 6:
                Toast.makeText(this, "Administradores proximamente", Toast.LENGTH_LONG).show();
                break;
            case 7:
                intent = new Intent(this, EquiposAdm.class);
                intent.putExtra(Constantes.COMMON_MENU_ORIGEN, Constantes.PATH_JUGADORES);
                break;
            case 8:
                intent = new Intent(this, EquiposAdm.class);
                intent.putExtra(Constantes.COMMON_MENU_ORIGEN, Constantes.PATH_EQUIPOS);
                break;
            case 9:
                Toast.makeText(this, "Delegados proximamente", Toast.LENGTH_LONG).show();
                break;
            case 10:
                Toast.makeText(this, "Editar Datos Cancha proximamente", Toast.LENGTH_LONG).show();
                break;
            case 11:
                Toast.makeText(this, "Editar Datos Liga proximamente", Toast.LENGTH_LONG).show();
                break;
            default:
                Toast.makeText(this, "Ahhhh nu maaaa ese menu: " + item.getIdMenu() +" no staba", Toast.LENGTH_LONG).show();
        }

        if(intent != null){
            //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            //finish();
        }

    }
}