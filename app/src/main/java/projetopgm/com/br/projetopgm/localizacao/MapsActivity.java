package projetopgm.com.br.projetopgm.localizacao;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;

import projetopgm.com.br.projetopgm.R;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks{

    private GoogleMap mMap;
    private LocalizacaoHelper localizacaoHelper;
    private LoaderManager loaderManager;
    private LatLng oficina;
    private LatLng local;

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
        mMap.addMarker(new MarkerOptions().position(local));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(local, 14.5f));
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
    public void onConnected(@Nullable Bundle bundle) {
        local = localizacaoHelper.obterLocalizacao();
        if(local != null)
            loaderManager.initLoader(12, null, rotaCallbacks);
    }

    @Override
    public void onConnectionSuspended(int i) { }
}
