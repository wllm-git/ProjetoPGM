package projetopgm.com.br.projetopgm.detalhes;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import projetopgm.com.br.projetopgm.R;
import projetopgm.com.br.projetopgm.base.Servico;
import projetopgm.com.br.projetopgm.compartilhados.FuncoesGlobais;

public class DetalhesFotosFragment extends Fragment {


    public DetalhesFotosFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentFotos = (View) inflater.inflate(R.layout.fragment_detalhes_fotos, container, false);

        LinearLayout linearLayout = (LinearLayout) fragmentFotos.findViewById(R.id.row1);
        if (!FuncoesGlobais.isPortrait(getActivity())) {
            linearLayout.setOrientation(LinearLayout.VERTICAL);
        }

        Intent it = getActivity().getIntent();
        Servico servico = (Servico) it.getSerializableExtra("servico");
        if (servico != null){
          // TODO LEMBRAR DE FAZER A LISTA DE FOTOS DENTRO DO FRAGMENT DETALHES FOTOS


        }


        return fragmentFotos;

    }

}
