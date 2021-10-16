package com.tachnologies.myligapro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.tachnologies.myligapro.common.utils.Constantes;
import com.tachnologies.myligapro.moduloJugador.JugadorActivity;
import com.tachnologies.myligapro.moduloLogin.view.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.btnJugador)
    Button btnJugador;
    @BindView(R.id.btnDelegado)
    Button btnDelegado;
    @BindView(R.id.btnAdm)
    Button btnAdm;
    @BindView(R.id.ivAppImage)
    ImageView ivAppImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.btnJugador, R.id.btnDelegado, R.id.btnAdm})
    public void onViewClicked(View view) {
        Intent intent = null;

        switch (view.getId()) {
            case R.id.btnJugador:
                intent = new Intent(this, JugadorActivity.class);
                break;
            case R.id.btnDelegado:
                //se van a redirigir al activity de login
                intent = new Intent(this, LoginActivity.class);
                intent.putExtra(Constantes.TIPO_USUARIO, Constantes.DELEGADO);
                break;
            case R.id.btnAdm:
                //se van a redirigir al activity de login
                intent = new Intent(this, LoginActivity.class);
                intent.putExtra(Constantes.TIPO_USUARIO, Constantes.ADMIN_LIGA);
                break;
            default:
        }

        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        //finish();
    }
}