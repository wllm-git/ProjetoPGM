package projetopgm.com.br.projetopgm.listagem;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import projetopgm.com.br.projetopgm.R;


public class ListagemFragment extends Fragment {


    public ListagemFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_listagem, container, false);
    }

}
