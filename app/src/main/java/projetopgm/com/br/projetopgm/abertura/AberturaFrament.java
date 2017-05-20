package projetopgm.com.br.projetopgm.abertura;



import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import projetopgm.com.br.projetopgm.R;
import projetopgm.com.br.projetopgm.base.Servico;

public class AberturaFrament extends Fragment {

    public static final String TAG_DETALHE = "tagDetalhe";
    private static final String EXTRA_SERVICO_ABERTURA = "servicoAbertura";

    EditText descricao;
    Button btnEnviar;
    Button btnFoto;

    Servico servico;

    public static AberturaFrament novaInstancia(Servico servico){
        Bundle paramentros = new Bundle();
        paramentros.putSerializable(EXTRA_SERVICO_ABERTURA,servico);

        AberturaFrament frament = new AberturaFrament();
        frament.setArguments(paramentros);
        return frament;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        servico = (Servico) getArguments().getSerializable(EXTRA_SERVICO_ABERTURA);
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,  Bundle savedInstanceState) {


        View layout = inflater.inflate(R.layout.fragment_abertura_detalhes, container, false);

        descricao = (EditText) layout.findViewById(R.id.edtAberturaDescricao);
        btnEnviar = (Button) layout.findViewById(R.id.btnEnviar);
        btnFoto = (Button) layout.findViewById(R.id.btnFoto);


        return layout;
    }
}
