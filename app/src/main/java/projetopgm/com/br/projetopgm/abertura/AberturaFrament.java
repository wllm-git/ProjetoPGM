package projetopgm.com.br.projetopgm.abertura;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import projetopgm.com.br.projetopgm.R;

/**
 * Created by Roldao Wilker on 19/05/2017.
 */

public class AberturaFrament extends Fragment{


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_abertura_detalhes, container, false);
    }
}
