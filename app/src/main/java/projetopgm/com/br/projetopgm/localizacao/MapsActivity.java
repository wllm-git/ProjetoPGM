package projetopgm.com.br.projetopgm.localizacao;

import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;

import projetopgm.com.br.projetopgm.R;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        LocationListener {

    private GoogleMap mMap;
    private LocalizacaoHelper localizacaoHelper;
    private LoaderManager loaderManager;
    private LatLng oficina;
    private LatLng local;
    private Marker marker;
    LoaderManager.LoaderCallbacks<List<LatLng>> rotaCallbacks = new LoaderManager.LoaderCallbacks<List<LatLng>>() {
        @Override
        public Loader<List<LatLng>> onCreateLoader(int id, Bundle args) {
            return new RotaTask(MapsActivity.this, oficina, local);
        }

        @Override
        public void onLoadFinished(Loader<List<LatLng>> loader, List<LatLng> rota) {
            if(rota != null && rota.size() > 0){
                PolylineOptions polylineOptions = new PolylineOptions()
                        .addAll(rota)
                        .width(5)
                        .color(Color.BLUE)
                        .visible(true);

                mMap.addPolyline(polylineOptions);
            }
            atualizarMap();
        }

        @Override
        public void onLoaderReset(Loader<List<LatLng>> loader) { }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        localizacaoHelper = new LocalizacaoHelper(this, this);
        loaderManager = getSupportLoaderManager();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        oficina = new LatLng(-8.1280737,-34.9154712);
        mMap.addMarker(new MarkerOptions().position(oficina).title("Oficina"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(oficina, 14.5f));
    }

    public void atualizarMap(){
        if(marker != null)
            marker.setPosition(local);
        else
            marker = mMap.addMarker(new MarkerOptions().position(local));
        mMap.animateCamera(CameraUpdateFactory.newLatLng(local));
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

    @Override
    public void onLocationChanged(Location location) {
        if(location != null) {
            local = new LatLng(location.getLatitude(), location.getLongitude());
            loaderManager.initLoader(12, null, rotaCallbacks);
        }
    }
}
