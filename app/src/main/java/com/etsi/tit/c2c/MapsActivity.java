package com.etsi.tit.c2c;



import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.etsi.tit.c2c.localizador.AugmentedReality;
import com.etsi.tit.c2c.localizador.Aula;
import com.etsi.tit.c2c.localizador.AulaUtils;
import com.etsi.tit.c2c.localizador.PedirClase;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //EJECUCION BOTON FLOAT
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (!AulaUtils.getAr()) {
            fab.setVisibility(View.GONE);
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent form = new Intent(MapsActivity.this, AugmentedReality.class);
                startActivity(form);
                finish();
            }
        });


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    //AL PRESIONAR BACK BUTTON IR A MENU PRINCIPAL ************************************************/
    public void onBackPressed() {
        if(AulaUtils.getAr()) {
            Intent form = new Intent(MapsActivity.this, AugmentedReality.class);
            startActivity(form);
            finish();

        }
        else {
            Intent form = new Intent(MapsActivity.this, PedirClase.class);
            startActivity(form);
            finish();

        }
    }


    //FUNCIÓN PARA CUANDO DAMOS HACIA ATRAS EN LA BARRA *******************************************/
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);

        // Create a LatLngBounds that includes the city of Adelaide in Australia.
        LatLngBounds ADELAIDE = new LatLngBounds(
                new LatLng( 43.261040, -2.950420 ), new LatLng( 43.262713, -2.948183  ));
        // Constrain the camera target to the Adelaide bounds.
        mMap.setLatLngBoundsForCameraTarget(ADELAIDE);


        //Obtenemos aula destino y origen para poner markers
        Aula origenM = AulaUtils.devuelveAula(AulaUtils.getAulaOrigen());
        Aula destinoM= AulaUtils.devuelveAula(AulaUtils.getAulaDestino());


        LatLng markerO = new LatLng(origenM.getLatitud(), origenM.getLongitud());
        mMap.addMarker(new MarkerOptions()
                .position(markerO)
                .title("Usted esta aquí")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.placeholder))
        );


        LatLng markerD = new LatLng(destinoM.getLatitud(), destinoM.getLongitud());
        mMap.addMarker(new MarkerOptions()
                .position(markerD)
                .title(destinoM.getAula())
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.flag))
        );


        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(markerO, 19));
        mMap.setMinZoomPreference(18);
        mMap.setMaxZoomPreference(21);
    }
}


