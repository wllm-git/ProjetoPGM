package projetopgm.com.br.projetopgm.localizacao;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import projetopgm.com.br.projetopgm.R;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LocalizacaoHelper localizacaoHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng oficina = new LatLng(-8.1280737,-34.9154712);
        mMap.addMarker(new MarkerOptions().position(oficina).title("Oficina"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(oficina, 14.5f));

        localizacaoHelper = new LocalizacaoHelper(this, mMap);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(localizacaoHelper != null)
            localizacaoHelper.onStart();
    }

    @Override
    protected void onStop() {
        if(localizacaoHelper != null)
            localizacaoHelper.onStop();
        super.onStop();
    }
}
