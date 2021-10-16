package com.tachnologies.myligapro.moduloAdm.primerIngreso.altaCancha.ubicacion;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.maps.android.SphericalUtil;
import com.tachnologies.myligapro.R;
import com.tachnologies.myligapro.common.animaciones.cargando.CargandoDialog;
import com.tachnologies.myligapro.common.pojo.Cancha;
import com.tachnologies.myligapro.common.pojo.Ubicacion;
import com.tachnologies.myligapro.common.pojo.UsuarioAdmin;
import com.tachnologies.myligapro.common.utils.Constantes;
import com.tachnologies.myligapro.moduloAdm.primerIngreso.altaLiga.AltaLigaActivity;

import java.util.Arrays;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class UbicacionCanchaActivity extends AppCompatActivity implements OnMapReadyCallback {
    /**para el maps*/
    private GoogleMap mMap;
    private SupportMapFragment mMapFragment;

    /**para la ubicacion del usuario*/
    private LocationRequest mLocationRequest;
    private FusedLocationProviderClient mFusedLocation;

    /**Para el autocompletado*/
    private PlacesClient mPlaces;
    private AutocompleteSupportFragment mAutocomplete;

    /**para guardar el lugar seleccionado por el usuario*/
    private String mUbicacion;
    private LatLng mUbicacionLatLng;

    /**para capturar la ubicacion en el mapa cuando se mueve la camara*/
    private GoogleMap.OnCameraIdleListener mListenerCamara;

    /**no se pa que chingados*/
    private boolean primeraVez = true;

    /** Objetos que se seguiran pasando en sesion*/
    private UsuarioAdmin admin;
    private Cancha cancha;

    /** para lo del gif */
    CargandoDialog cargando;

    LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            for (Location location : locationResult.getLocations()) {
                if (getApplicationContext() != null) {
                    mUbicacionLatLng = new LatLng(location.getLatitude(), location.getLongitude());

                    //OBtener la ubicacion del usuario en tiempo real
                    mMap.moveCamera(CameraUpdateFactory.newCameraPosition(
                            new CameraPosition.Builder()
                                    .target(new LatLng(location.getLatitude(), location.getLongitude()))
                                    .zoom(Constantes.ZOOM_MAP)
                                    .build()
                    ));

                    //la neta ni perra idea para que es
                    if (primeraVez) {
                        primeraVez = false;
                        limitarBusqueda();
                    }

                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubicacion_cancha);
        ButterKnife.bind(this);

        cargando = new CargandoDialog();
        bloquearPantalla();

        mFusedLocation = LocationServices.getFusedLocationProviderClient(this);

        mMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mMapFragment.getMapAsync(this);

        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(),
                    getString(R.string.google_maps_key));
        }

        mPlaces = Places.createClient(this);

        instanciaAutocompletar();
        moverCamara();

        admin = (UsuarioAdmin) getIntent().getSerializableExtra(Constantes.USU_ADMIN);
        cancha = (Cancha) getIntent().getSerializableExtra(Constantes.CANCHA);
    }

    private void limitarBusqueda() {
        LatLng norte = SphericalUtil.computeOffset(mUbicacionLatLng, 10000, 0);
        LatLng sur = SphericalUtil.computeOffset(mUbicacionLatLng, 10000, 180);

        //mAutocomplete.setCountry("MEX");
        mAutocomplete.setLocationBias(RectangularBounds.newInstance(sur, norte));

    }

    private void moverCamara() {
        mListenerCamara = new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                try {
                    Geocoder geocoder = new Geocoder(UbicacionCanchaActivity.this);
                    mUbicacionLatLng = mMap.getCameraPosition().target;
                    List<Address> listaDirecciones = geocoder.getFromLocation(mUbicacionLatLng.latitude, mUbicacionLatLng.longitude, 1);
                    String direccion = listaDirecciones.get(0).getAddressLine(0);
                    mUbicacion = direccion;
                    mAutocomplete.setText(direccion);
                    desbloquearPantalla();
                } catch (Exception e) {
                    System.out.println("--------------------- Error: " + e.getMessage());
                }

            }
        };
    }

    private void instanciaAutocompletar() {
        mAutocomplete = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocompletaLugar);

        mAutocomplete.setPlaceFields(
                Arrays.asList(
                        Place.Field.ID,
                        Place.Field.LAT_LNG,
                        Place.Field.NAME
                )
        );

        mAutocomplete.setHint("Ubicación de mi cancha");

        mAutocomplete.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                mUbicacion = place.getName();
                mUbicacionLatLng = place.getLatLng();

                mMap.moveCamera(CameraUpdateFactory.newLatLng(mUbicacionLatLng));
                desbloquearPantalla();
            }

            @Override
            public void onError(@NonNull Status status) {

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Constantes.LOCATION_RC) {
            if (grantResults != null && grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                    if (gpsActivado()) {
                        mFusedLocation.requestLocationUpdates(mLocationRequest, mLocationCallback,
                                Looper.myLooper());
                    } else {
                        mostrarAlertNoGps();
                    }
                }
            } else {
                mostrarAlertNoGps();
            }
        } else {
            mostrarAlertNoGps();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case Constantes.ACTIVA_UBICACION:
                if (gpsActivado()) {
                    if (ActivityCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                            ActivityCompat.checkSelfPermission( this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    mFusedLocation.requestLocationUpdates(mLocationRequest, mLocationCallback,
                            Looper.myLooper());
                } else {
                    mostrarAlertNoGps();
                }
                break;
            default:
        }
    }

    private void mostrarAlertNoGps() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.ubicacion_cancha_msje_permisos))
                .setPositiveButton(getString(R.string.ubicacion_cancha_msje_boton_ok),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS),
                                        Constantes.ACTIVA_UBICACION);
                            }
                        })
                .create()
                .show();
    }

    private boolean gpsActivado() {
        boolean estaActivado = false;
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            estaActivado = true;
        }

        return estaActivado;
    }

    private void iniciaLocalizacion() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                if (gpsActivado()) {
                    mFusedLocation.requestLocationUpdates(mLocationRequest, mLocationCallback,
                            Looper.myLooper());
                } else {
                    mostrarAlertNoGps();
                }

            } else {
                verificaPermisosLocalizacion();
            }

        } else {

            if (gpsActivado()) {
                mFusedLocation.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
            } else {
                mostrarAlertNoGps();
            }

        }
    }

    private void verificaPermisosLocalizacion() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                muestraAlertPermisosLocalizacion();
            } else {
                ActivityCompat.requestPermissions(UbicacionCanchaActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Constantes.LOCATION_RC);
            }
        } else {
            muestraAlertPermisosLocalizacion();
        }
    }

    public void muestraAlertPermisosLocalizacion() {
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.ubicacion_cancha_titulo_permisos_ubicacion))
                .setMessage(getString(R.string.ubicacion_cancha_texto_permisos_ubicacion))
                .setPositiveButton(getString(R.string.common_ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ActivityCompat.requestPermissions(UbicacionCanchaActivity.this,
                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                Constantes.LOCATION_RC);

                    }
                })
                .create()
                .show();
    }

    @OnClick(R.id.fabAceptar)
    public void onViewClicked() {
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.ubicacion_cancha_titulo_confirmar_ubicacion))
                .setMessage(getString(R.string.ubicacion_cancha_texto_confirmar_ubicacion))
                .setPositiveButton(getString(R.string.common_ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        irAltaLigaActivity();
                    }
                })
                .setNegativeButton(getString(R.string.common_cancelar), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .create()
                .show();
    }

    public void irAltaLigaActivity(){
        bloquearPantalla();
        Intent intent = new Intent(this, AltaLigaActivity.class);

        Ubicacion ubicacion = new Ubicacion();
        ubicacion.setLatitud(String.valueOf(mUbicacionLatLng.latitude));
        ubicacion.setLongitud(String.valueOf(mUbicacionLatLng.longitude));
        cancha.setUbicacion(ubicacion);

        try{
            Geocoder geocoder = new Geocoder(UbicacionCanchaActivity.this);
            List<Address> listaDirecciones = geocoder.getFromLocation(mUbicacionLatLng.latitude, mUbicacionLatLng.longitude, 1);
            String direccion = listaDirecciones.get(0).getAddressLine(0);
            cancha.setDireccion(direccion);
        }catch(Exception e){
            System.out.println("---------------------------- pos trono alv sacar la direccion :V");
        }

        intent.putExtra(Constantes.USU_ADMIN, admin);
        intent.putExtra(Constantes.CANCHA, cancha);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        desbloquearPantalla();
        finish();

    }

    public void bloquearPantalla() {
        cargando.show(getSupportFragmentManager(), Constantes.CADENA_VACIA);
    }

    public void desbloquearPantalla() {
        cargando.dismiss();
    }

    /** -------------------------- OnMapReadyCallback */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setOnCameraIdleListener(mListenerCamara);

        mLocationRequest = new LocationRequest();
        //para tener alta presicion donde esta
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setSmallestDisplacement(Constantes.SMALL_DESPLAZAMIENTO);

        iniciaLocalizacion();
    }
    /**-------------------------- /OnMapReadyCallback*/
}