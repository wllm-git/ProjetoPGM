package projetopgm.com.br.projetopgm.listagem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;

import projetopgm.com.br.projetopgm.R;
import projetopgm.com.br.projetopgm.bancodados.ServicoDAO;
import projetopgm.com.br.projetopgm.base.Servico;
import projetopgm.com.br.projetopgm.detalhes.DetalhesActivity;

public class ListagemActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListagemAdapter listagemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listagem);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_launcher_round);

        ListagemFragment listagemFragment = (ListagemFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentListagem);

        ListView listView = (ListView) listagemFragment.getView().findViewById(R.id.lvwListaServicos);
        listView.setOnItemClickListener(this);
        listagemAdapter = (ListagemAdapter) listView.getAdapter();

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Servico servico = listagemAdapter.getServicos().get(position);
        Intent it = new Intent(this, DetalhesActivity.class);
        it.putExtra("servico", servico);
        startActivity(it);

    }

}
