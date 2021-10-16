package com.tachnologies.myligapro.moduloLogin.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.tachnologies.myligapro.R;
import com.tachnologies.myligapro.common.animaciones.cargando.CargandoDialog;
import com.tachnologies.myligapro.common.pojo.UsuarioAdmin;
import com.tachnologies.myligapro.common.pojo.UsuarioDelegado;
import com.tachnologies.myligapro.common.utils.Constantes;
import com.tachnologies.myligapro.moduloAdm.listadoCanchas.view.ListadoCanchasActivity;
import com.tachnologies.myligapro.moduloAdm.primerIngreso.altaAdmin.AltaAdminActivity;
import com.tachnologies.myligapro.moduloDelegado.mainDelegado.view.MainDelegadoActivity;
import com.tachnologies.myligapro.moduloLogin.LoginPresenter;
import com.tachnologies.myligapro.moduloLogin.LoginPresenterClass;

import java.util.Arrays;

import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements LoginView {

    /**@BindView(R.id.tvMessage)
    TextView tvMessage;*/
    /**@BindView(R.id.progressBar)
    ProgressBar progressBar;*/
    private LoginPresenter mPresenter;

    /** para lo del gif */
    CargandoDialog cargando;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String tipoUsuario = intent.getStringExtra(Constantes.TIPO_USUARIO);

        mPresenter = new LoginPresenterClass(this, tipoUsuario);
        mPresenter.onCreate();      //para subscribirse a eventbus
        mPresenter.getStatusAuth(); //para ver si el usuario esta autenticado

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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPresenter.result(requestCode, resultCode, data);
    }

    /**
    * LoginView
    * metodos implementados por la interfaz
    * para tener un codigo mas ordenado por si hay mas de una interfaz
    */
    @Override
    public void showProgress() {
        cargando = new CargandoDialog(R.string.login_cargando);
        cargando.show(getSupportFragmentManager(), Constantes.CADENA_VACIA);
    }

    @Override
    public void hideProgress() {
        cargando.dismiss();
    }

    @Override
    public void openAdminActivity(UsuarioAdmin admin) {
        Intent intent = new Intent(this, ListadoCanchasActivity.class);
        comienzaActivity(intent);
    }

    @Override
    public void openDelegadoActivity(UsuarioDelegado delegado, boolean esNuevo) {
        Intent intent = new Intent(this, MainDelegadoActivity.class);
        intent.putExtra(Constantes.ES_NUEVO, esNuevo);
        comienzaActivity(intent);
    }

    @Override
    public void openAltaAdminActivity(UsuarioAdmin admin) {
        Intent intent = new Intent(this, AltaAdminActivity.class);

        intent.putExtra(Constantes.USU_ADMIN, admin);
        comienzaActivity(intent);
    }

    private void comienzaActivity(Intent intent){
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        hideProgress();
        finish();
    }

    @Override
    public void openUILogin() {
        //AuthUI.IdpConfig googleIdp = new AuthUI.IdpConfig.GoogleBuilder().build();
        AuthUI.IdpConfig phoneIdp = new AuthUI.IdpConfig.PhoneBuilder().build();

        startActivityForResult(AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setIsSmartLockEnabled(false)
                .setTosAndPrivacyPolicyUrls("www.politica_privacidad_tach.com",
                        "www.privacidad_tach.com")
                .setAvailableProviders(Arrays.asList(
                        phoneIdp
                        //,googleIdp
                ))
                .setTheme(R.style.AppTheme)
                .setLogo(R.drawable.imagengenerica)
                .build(), Constantes.RC_SIGN_IN);
    }

    @Override
    public void showLoginSuccessfully() {

        Toast.makeText(this, getString(R.string.mensaje_login_exitoso),
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void showMessageStarting() {
        //.setText(R.string.common_cargando);
    }

    @Override
    public void showError(int resMsg) {
        Toast.makeText(this, getString(resMsg), Toast.LENGTH_LONG).show();
    }

}