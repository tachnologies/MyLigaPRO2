package com.tachnologies.myligapro.moduloAdm.jugadoresAdm.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tachnologies.myligapro.R;
import com.tachnologies.myligapro.common.animaciones.cargando.CargandoDialog;
import com.tachnologies.myligapro.common.model.dataSession.AdmSession;
import com.tachnologies.myligapro.common.pojo.Equipo;
import com.tachnologies.myligapro.common.pojo.Jugador;
import com.tachnologies.myligapro.common.utils.Constantes;
import com.tachnologies.myligapro.moduloAdm.jugadoresAdm.JugadoresAdmPresenter;
import com.tachnologies.myligapro.moduloAdm.jugadoresAdm.JugadoresAdmPresenterClass;
import com.tachnologies.myligapro.moduloAdm.jugadoresAdm.adapters.JugadorAdmAdapter;
import com.tachnologies.myligapro.moduloAdm.jugadoresAdm.adapters.OnItemClickListener;
import com.tachnologies.myligapro.moduloAdm.nuevoJugador.view.NuevoJugadorAdmFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class JugadoresAdmActivity extends AppCompatActivity implements JugadoresAdmView, OnItemClickListener {
    @BindView(R.id.imgFoto)
    AppCompatImageView imgFoto;
    @BindView(R.id.tvnombreEquipo)
    TextView tvnombreEquipo;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.contentMain)
    ConstraintLayout contentMain;
    @BindView(R.id.tvMensaje)
    TextView tvMensaje;
    @BindView(R.id.tvTitulo)
    TextView tvTitulo;
    @BindView(R.id.btnNuevo)
    FloatingActionButton btnNuevo;
    /**para lo del gif*/
    private CargandoDialog cargando;
    private JugadoresAdmPresenter mPresenter;
    private Equipo equipoEditar;
    private JugadorAdmAdapter mAdapter;
    private AdmSession mSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jugadores_adm);
        ButterKnife.bind(this);

        bloquearPantalla(R.string.common_cargando);
        this.mSession = AdmSession.getInstance();

        configAdapter();
        configRecyclerView();

        this.tvnombreEquipo.setText(mSession.getNombreEquipoSel());
        if (mSession.getUrlLogoEquipoSel() != null && !mSession.getUrlLogoEquipoSel().isEmpty()) {

            RequestOptions options = new RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop();

            Glide.with(this.getBaseContext())
                    .load(mSession.getUrlLogoEquipoSel())
                    .apply(options)
                    .into(imgFoto);
        }

        mPresenter = new JugadoresAdmPresenterClass(this);
        mPresenter.onCreate();
        mPresenter.consultarEquipo(mSession.getIdEquipoSel());
    }

    private void configAdapter() {
        mAdapter = new JugadorAdmAdapter(new ArrayList<Jugador>(), this);
    }

    private void configRecyclerView() {
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(this,
                getResources().getInteger(R.integer.columnas_listado));
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void agregarJugador(Jugador jugador){
        if(jugador != null){
            tvMensaje.setVisibility(View.GONE);
            mAdapter.add(jugador);

            if (equipoEditar.getJugadores() == null || equipoEditar.getJugadores().isEmpty()) {
                equipoEditar.setJugadores(new HashMap<String, Jugador>());
            }
            equipoEditar.getJugadores().put(jugador.getUid(), jugador);
        }else{
            System.out.println("--------------------------------- jugador ES NULL PUERKAS");
        }

    }

    @Override
    public void actualizarJugador(Jugador jugador){
        if(jugador != null){
            mAdapter.update(jugador);
            equipoEditar.getJugadores().put(jugador.getUid(), jugador);
        }else{
            System.out.println("--------------------------------- jugador ES NULL PUERKAS");
        }

    }

    @Override
    public void eliminarJugador(Jugador jugador){
        System.out.println("--------------------------------- eliminarJugador");
        if(jugador != null){
            System.out.println("--------------------------------- jugador != null");
            mAdapter.remove(jugador);
            equipoEditar.getJugadores().remove(jugador.getUid());
            if(mAdapter.getItemCount() <= 0){
                tvMensaje.setVisibility(View.VISIBLE);
            }

            if (cargando != null){
                cargando.dismiss();
            }
            Toast.makeText(this, R.string.jugador_eliminado, Toast.LENGTH_LONG).show();
        }else{
            System.out.println("--------------------------------- jugador ES NULL PUERKAS");
        }

    }

    @Override
    public void cargaJugadores(Equipo equipo) {
        equipoEditar = equipo;
        System.out.println("------------------------ cargaJugadores");
        if (equipoEditar != null) {
            System.out.println("------------------------ equipo: " + equipoEditar.getNombre());
            Map<String, Jugador> jugadores = equipoEditar.getJugadores();

            if (jugadores != null && !jugadores.isEmpty()) {
                tvMensaje.setVisibility(View.GONE);

                for (Map.Entry<String, Jugador> entry : jugadores.entrySet()) {
                    Jugador jugador = entry.getValue();
                    jugador.setUid(entry.getKey());
                    mAdapter.add(jugador);
                }

                System.out.println("------------------------ hay " + jugadores.size() + " jugadores");
            } else {
                tvMensaje.setVisibility(View.VISIBLE);
                System.out.println("------------------------ NO HAY jugadores :V LEL");
            }
        } else {
            System.out.println("------------------------ NO HAY equipo jaja k meko");
        }
        desbloquearPantalla();
    }

    @Override
    public void bloquearPantalla(int resMsg) {
        cargando = new CargandoDialog(resMsg);
        cargando.show(getSupportFragmentManager(), Constantes.CADENA_VACIA);
    }

    @Override
    public void desbloquearPantalla() {
        cargando.dismiss();
    }

    @Override
    public void mostrarError(int resMsg) {
        Toast.makeText(this, resMsg, Toast.LENGTH_LONG).show();
        desbloquearPantalla();
    }

    @Override
    public void onItemClick(Jugador jugador) {
        new NuevoJugadorAdmFragment(this, true, jugador)
                .show(getSupportFragmentManager(), getString(R.string.nuevo_jugador_titulo));
    }

    @Override
    public void onLongItemClick(Jugador jugador) {
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator != null){
            vibrator.vibrate(100);
        }

        new AlertDialog.Builder(this)
                .setTitle(R.string.borrar_jugador_titulo)
                .setMessage(getString(R.string.dialogo_borrar_jugador,
                        jugador.getNombre() + " " + jugador.getApellido()))
                .setPositiveButton(R.string.common_borrar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        bloquearPantalla(R.string.common_cargando);

                        if(jugador.getUrlFoto() != null && !jugador.getUrlFoto().isEmpty()){
                            mPresenter.eliminarFotoJugador(jugador.getUrlFoto());
                        }

                        mPresenter.eliminarJugador(jugador.getUid());
                    }
                })
                .setNegativeButton(R.string.common_cancelar, null)
                .show();
    }

    @OnClick(R.id.btnNuevo)
    public void onViewClicked() {
        new NuevoJugadorAdmFragment(this, false).show(getSupportFragmentManager(), getString(R.string.nuevo_jugador_titulo));
    }

    @Override
    public Equipo getEquipo() {
        return equipoEditar;
    }
}