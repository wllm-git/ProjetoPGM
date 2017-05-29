package projetopgm.com.br.projetopgm.abertura;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import projetopgm.com.br.projetopgm.R;
import projetopgm.com.br.projetopgm.base.Servico;

public class AberturaInfoFrament extends Fragment {

    EditText descricao;
    Button btnEnviar;
    Button btnFoto;


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,  Bundle savedInstanceState) {


        View layout = inflater.inflate(R.layout.fragment_abertura_detalhes, container, false);

        descricao = (EditText) layout.findViewById(R.id.aberturaEdtInfo);
        btnEnviar = (Button) layout.findViewById(R.id.btnSendService);
        btnFoto = (Button) layout.findViewById(R.id.btnTakePicture);


        return layout;
    }
}
