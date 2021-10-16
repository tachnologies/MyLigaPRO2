package com.tachnologies.myligapro.moduloAdminJugadores.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.tachnologies.myligapro.common.model.dataSession.DelSession;
import com.tachnologies.myligapro.common.pojo.Equipo;
import com.tachnologies.myligapro.common.pojo.Jugador;
import com.tachnologies.myligapro.common.utils.Constantes;
import com.tachnologies.myligapro.moduloAdm.jugadoresAdm.adapters.OnItemClickListener;
import com.tachnologies.myligapro.moduloAdminJugadores.AdministrarJugadoresPresenter;
import com.tachnologies.myligapro.moduloAdminJugadores.AdministrarJugadoresPresenterClass;
import com.tachnologies.myligapro.moduloAdminJugadores.adapters.AdminJugadorAdapter;
import com.tachnologies.myligapro.moduloAdminJugadores.agregarEditar.view.AgregarEditarJugadorFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AdministrarJugadoresActivity extends AppCompatActivity
        implements AdministrarJugadoresView, OnItemClickListener {
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
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private String uidCuenta;
    private String uidCancha;
    private String uidLiga;
    private String uidEquipo;
    private String tipoUsuario;
    /**para lo del gif*/
    private CargandoDialog cargando;
    private Equipo equipoEditar;
    private AdminJugadorAdapter mAdapter;
    private AdministrarJugadoresPresenter mPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrar_jugadores);
        ButterKnife.bind(this);
        bloquearPantalla();

        configAdapter();
        configRecyclerView();

        uidCuenta = "";
        uidCancha = "";
        uidLiga = "";
        uidEquipo = "";

        Intent intent = getIntent();
        tipoUsuario = intent.getStringExtra(Constantes.TIPO_USUARIO);
        switch (tipoUsuario) {
            case Constantes.ADMIN_LIGA:
                AdmSession admSession = AdmSession.getInstance();
                uidCuenta = admSession.getIdCuentaSel();
                uidCancha = admSession.getIdCanchaSel();
                uidLiga   = admSession.getIdLigaSel();
                uidEquipo = admSession.getIdEquipoSel();
                break;
            case Constantes.DELEGADO:
                DelSession delSession = DelSession.getInstance();
                uidCuenta = delSession.getIdCuentaSel();
                uidCancha = delSession.getIdCanchaSel();
                uidLiga   = delSession.getIdLigaSel();
                uidEquipo = delSession.getIdEquipoSel();
                break;
            default:
        }

        mPresenter = new AdministrarJugadoresPresenterClass(this);
        mPresenter.onCreate();
        mPresenter.consultarEquipo(uidEquipo, uidCuenta, uidCancha, uidLiga);

    }

    @Override
    protected void onDestroy() {
        mPresenter.onDestroy();
        super.onDestroy();
    }

    private void configAdapter() {
        mAdapter = new AdminJugadorAdapter(new ArrayList<Jugador>(), this);
    }

    private void configRecyclerView() {
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(this,
                getResources().getInteger(R.integer.columnas_listado));
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mAdapter);
    }


    @OnClick(R.id.btnNuevo)
    public void onViewClicked() {
        new AgregarEditarJugadorFragment(this, false, tipoUsuario)
                .show(getSupportFragmentManager(), getString(R.string.nuevo_jugador_titulo));
    }

    public void bloquearPantalla() {
        cargando = new CargandoDialog(R.string.common_cargando);
        cargando.show(getSupportFragmentManager(), Constantes.CADENA_VACIA);
    }

    public void desbloquearPantalla() {
        cargando.dismiss();
    }

    /**
     * --------------------------------------------AdministrarJugadoresView
     */
    @Override
    public void mostrarError(int resMsg) {
        Toast.makeText(this, resMsg, Toast.LENGTH_LONG).show();
        desbloquearPantalla();
    }

    @Override
    public void cargaJugadores(Equipo equipo) {
        equipoEditar = equipo;
        if (equipoEditar != null) {
            this.tvnombreEquipo.setText(equipoEditar.getNombre());

            if (equipoEditar.getUrlFoto() != null && !equipoEditar.getUrlFoto().isEmpty()) {
                RequestOptions options = new RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop();

                Glide.with(this.getBaseContext())
                    .load(equipoEditar.getUrlFoto())
                    .apply(options)
                    .into(imgFoto);
            }

            Map<String, Jugador> jugadores = equipoEditar.getJugadores();

            if (jugadores != null && !jugadores.isEmpty()) {
                tvMensaje.setVisibility(View.GONE);

                for (Map.Entry<String, Jugador> entry : jugadores.entrySet()) {
                    Jugador jugador = entry.getValue();
                    jugador.setUid(entry.getKey());
                    mAdapter.add(jugador);
                }

            } else {
                tvMensaje.setVisibility(View.VISIBLE);
            }
        } else {
            System.out.println("------------------------ NO HAY equipo jaja k meko");
        }
        desbloquearPantalla();
    }

    @Override
    public void agregarJugador(Jugador jugador) {
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
    public void actualizarJugador(Jugador jugador) {
        if(jugador != null){
            mAdapter.update(jugador);
            equipoEditar.getJugadores().put(jugador.getUid(), jugador);
        }else{
            System.out.println("--------------------------------- jugador ES NULL PUERKAS");
        }
    }

    @Override
    public void eliminarJugador(Jugador jugador) {
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

    /**-----------------------------ItemClick */
    @Override
    public void onItemClick(Jugador jugador) {
        new AgregarEditarJugadorFragment(this, true, jugador, tipoUsuario)
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
                bloquearPantalla();

                if(jugador.getUrlFoto() != null && !jugador.getUrlFoto().isEmpty()){
                    mPresenter.eliminarFotoJugador(jugador.getUrlFoto());
                }

                mPresenter.eliminarJugador(uidCuenta, uidCancha, uidLiga, uidEquipo, jugador.getUid());
            }
            }).setNegativeButton(R.string.common_cancelar, null)
            .show();
    }

    public Equipo getEquipo() {
        return equipoEditar;
    }

}