package projetopgm.com.br.projetopgm.detalhes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import projetopgm.com.br.projetopgm.R;
import projetopgm.com.br.projetopgm.base.Servico;


public class DetalhesFragment extends Fragment {
    public DetalhesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragmentDetalhes = inflater.inflate(R.layout.fragment_detalhes, container, false);
        Intent it = getActivity().getIntent();
        Servico servico = (Servico) it.getSerializableExtra("servico");
        if (servico != null){
            TextView textDescDetalhes = (TextView) fragmentDetalhes.findViewById(R.id.tvwDescDetalhes);
            textDescDetalhes.setText(servico.getDescricao());
            TextView textDataAberta = (TextView) fragmentDetalhes.findViewById(R.id.tvwDataAberta);
            textDataAberta.setText(servico.getDataAbertura().toString());


        }


        return fragmentDetalhes;
    }


}
