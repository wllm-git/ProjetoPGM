package projetopgm.com.br.projetopgm.acompanhamento;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import projetopgm.com.br.projetopgm.R;
import projetopgm.com.br.projetopgm.bancodados.ServicoDAO;
import projetopgm.com.br.projetopgm.base.Servico;
import projetopgm.com.br.projetopgm.webservice.ServicoWebTask;

public class AcompanhamentoServicoActivity extends AppCompatActivity
        implements View.OnClickListener{
    private Servico servico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acompanhamento_servico);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_launcher_round);

        FloatingActionButton fabAceitar = (FloatingActionButton) findViewById(R.id.fabAceitar);
        fabAceitar.setOnClickListener(this);

        FloatingActionButton fabCancelar = (FloatingActionButton) findViewById(R.id.fabCancelar);
        fabCancelar.setOnClickListener(this);

        Intent it = getIntent();
        servico = (Servico) it.getSerializableExtra("servico");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fabAceitar:
                if(servico != null && servico.getTipo()== Servico.Tipo.ORCAMENTO
                        && servico.getStatus()== Servico.Status.ANDAMENTO){
                    ServicoDAO servicoDAO = new ServicoDAO(this);
                    servico.setTipo(Servico.Tipo.OS);
                    servicoDAO.salvar(servico);
                    new ServicoWebTask().execute(servico);
                    finish();
                }
                break;
            case R.id.fabCancelar:
                if(servico != null && servico.getTipo()== Servico.Tipo.ORCAMENTO
                        && servico.getStatus()== Servico.Status.ANDAMENTO){
                    ServicoDAO servicoDAO = new ServicoDAO(this);
                    servico.setStatus(Servico.Status.CANCELADO);
                    servicoDAO.salvar(servico);
                    new ServicoWebTask().execute(servico);
                    finish();
                }
                break;
        }

    }
}
