package projetopgm.com.br.projetopgm.detalhes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import projetopgm.com.br.projetopgm.R;
import projetopgm.com.br.projetopgm.base.Servico;
import projetopgm.com.br.projetopgm.compartilhados.FuncoesGlobais;


public class DetalhesInfoFragment extends Fragment {
    private SimpleDateFormat sdf;
    public DetalhesInfoFragment() {
        this.sdf = new SimpleDateFormat("dd/MM/yyyy");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragmentDetalhes = inflater.inflate(R.layout.fragment_detalhes_info, container, false);
        Intent it = getActivity().getIntent();
        Servico servico = (Servico) it.getSerializableExtra("servico");
        if (servico != null){
            TextView textDescDetalhes = (TextView) fragmentDetalhes.findViewById(R.id.tvwDescDetalhes);
            textDescDetalhes.setText(servico.getDescricao());

            TextView textDataAberta = (TextView) fragmentDetalhes.findViewById(R.id.tvwDataAberta);
            textDataAberta.setText(sdf.format(servico.getDataAbertura()));

            TextView textDataFechada = (TextView) fragmentDetalhes.findViewById(R.id.tvwDataFechada);
            textDataFechada.setText(sdf.format(servico.getDataFechamento()));

            TextView textValor = (TextView) fragmentDetalhes.findViewById(R.id.tvwValor);
            textValor.setText(String.valueOf(servico.getPrecoFinal()));

            TextView textStatus = (TextView) fragmentDetalhes.findViewById(R.id.tvwStatus);
            textStatus.setText(FuncoesGlobais.formatarStatus(getActivity(), servico.getStatus()));

            TextView precoAvaliado = (TextView) fragmentDetalhes.findViewById(R.id.tvwPrecoAvaliado);
            precoAvaliado.setText(String.valueOf(servico.getPrecoAvaliado()));

            TextView numero = (TextView) fragmentDetalhes.findViewById(R.id.tvwNumero);
            numero.setText(String.valueOf(servico.getNumero()));

        }


        return fragmentDetalhes;
    }


}
