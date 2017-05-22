package projetopgm.com.br.projetopgm.listagem;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import projetopgm.com.br.projetopgm.R;
import projetopgm.com.br.projetopgm.bancodados.ServicoDAO;
import projetopgm.com.br.projetopgm.login.LoginHelper;


public class ListagemFragment extends Fragment {

    private ServicoDAO servicoDAO;

    public ListagemFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragmentListagem = inflater.inflate(R.layout.fragment_listagem, container, false);
        servicoDAO = new ServicoDAO(getActivity());
        ListView listView = (ListView) fragmentListagem.findViewById(R.id.lvwListaServicos);
        ListagemAdapter adapter = new ListagemAdapter(getActivity(), servicoDAO.buscarFechadas(LoginHelper.usuarioLogado()));
        listView.setAdapter(adapter);



        return fragmentListagem;
    }




}
