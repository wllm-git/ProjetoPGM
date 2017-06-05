package projetopgm.com.br.projetopgm.acompanhamento;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import projetopgm.com.br.projetopgm.R;


public class AcompanhamentoDetalhesFragment extends  Fragment {

    private TextView numeroServico;
    private TextView precoServico;
    private TextView statusServico;
    private TextView dataAberturaServico;
    private EditText descricaoProblema;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.fragment_acompanhamento_detalhes, container, false);

        numeroServico = (TextView) layout.findViewById(R.id.numeroServico);
        precoServico = (TextView) layout.findViewById(R.id.precoServico);
        statusServico = (TextView) layout.findViewById(R.id.statusServico);
        dataAberturaServico = (TextView) layout.findViewById(R.id.dataAberturaServico);
        descricaoProblema = (EditText) layout.findViewById(R.id.problemDescription);
        descricaoProblema.setEnabled(false);

        return layout;


    }


    public void setTxtNumeroServico(String value){
        numeroServico.setText(value);
    }

    public void setTxtPrecoServico(String value){
        precoServico.setText(value);
    }

    public void setTxtStatusServico(String value){
        statusServico.setText(value);
    }

    public void setTxtDataAberturaServico(String value){
        dataAberturaServico.setText(value);
    }

    public void setDescricaoProblema(String value){
        descricaoProblema.setText(value);
    }
}
