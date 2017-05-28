package projetopgm.com.br.projetopgm.localizacao;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class RotaTask extends AsyncTaskLoader<List<LatLng>>{
    private List<LatLng> rota;
    private LatLng orig;
    private LatLng dest;

    public RotaTask(Context context, LatLng orig, LatLng dest) {
        super(context);
        this.orig = orig;
        this.dest = dest;
    }

    @Override
    protected void onStartLoading() {
        if(rota == null)
            forceLoad();
        else
            deliverResult(rota);
    }

    @Override
    public List<LatLng> loadInBackground() {
        rota = RotaHelper.gerarRota(orig, dest);
        return rota;
    }
}
