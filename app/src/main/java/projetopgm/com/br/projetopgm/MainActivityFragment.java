package projetopgm.com.br.projetopgm;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import projetopgm.com.br.projetopgm.base.Servico;
import projetopgm.com.br.projetopgm.listagem.ListagemAdapter;

public class MainActivityFragment extends Fragment {

    private Servico servico;
    private ListagemAdapter adapter;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        servico = (Servico) getActivity().getIntent().getSerializableExtra("servico");

        View fragmentListagem = inflater.inflate(R.layout.fragment_main, container, false);

        ListView listView = (ListView) fragmentListagem.findViewById(R.id.lvwListaServicos);
        adapter = new ListagemAdapter(getContext());
        listView.setAdapter(adapter);

        return fragmentListagem;
    }

    @Override
    public void onStart() {
        super.onStart();

        if(servico != null)
            atualizarInfo();
    }

    private void atualizarInfo(){
        adapter.getServicos().clear();
        adapter.getServicos().add(servico);
        adapter.notifyDataSetChanged();
    }
}
