package projetopgm.com.br.projetopgm;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import projetopgm.com.br.projetopgm.listagem.ListagemAdapter;

public class MainActivityFragment extends Fragment {

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentListagem = inflater.inflate(R.layout.fragment_main, container, false);

        ListView listView = (ListView) fragmentListagem.findViewById(R.id.lvwListaServicos);
        ListagemAdapter adapter = new ListagemAdapter(getContext());
        listView.setAdapter(adapter);

        return fragmentListagem;
    }
}
