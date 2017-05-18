package projetopgm.com.br.projetopgm.localizacao;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class LocalizacaoHelper implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener{

    private Context context;
    private GoogleMap mMap;
    private GoogleApiClient googleApiClient;
    private LatLng local;

    public LocalizacaoHelper(Context context, GoogleMap mMap) {
        this.context = context;
        this.mMap = mMap;
        googleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        onStart();
    }

    public void onStart(){
        googleApiClient.connect();
    }

    public void onStop(){
        if(googleApiClient != null && googleApiClient.isConnected())
            googleApiClient.disconnect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        obterLocalizacao();
    }

    @Override
    public void onConnectionSuspended(int i) {
        googleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(context, connectionResult.getErrorCode() + " - " + connectionResult.getErrorMessage(), Toast.LENGTH_LONG);
    }

    private void obterLocalizacao(){
        try{
            Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
            if(location != null){
                local = new LatLng(location.getLatitude(), location.getLongitude());
                atualizarMap();
            }

        }catch (SecurityException ex){
            Toast.makeText(context, ex.getMessage(), Toast.LENGTH_LONG);
        }
    }

    private void atualizarMap(){
        mMap.addMarker(new MarkerOptions().position(local));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(local, 14.5f));
    }
}
