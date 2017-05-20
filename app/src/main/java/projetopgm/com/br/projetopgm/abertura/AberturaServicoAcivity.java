package projetopgm.com.br.projetopgm.abertura;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import projetopgm.com.br.projetopgm.R;
import projetopgm.com.br.projetopgm.base.Servico;

public class AberturaServicoAcivity extends AppCompatActivity {

    public static final String EXTRA_SERVICO_ABERTURA = "abertura";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abertura_servico);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_launcher_round);


        Intent intent = getIntent();
        Servico servico = (Servico) intent.getSerializableExtra(EXTRA_SERVICO_ABERTURA);

        AberturaFrament frament = AberturaFrament.novaInstancia(servico);
        FragmentManager fm = getSupportFragmentManager();

        FragmentTransaction ft = fm.beginTransaction();

        ft.replace(R.id.aberturaDetalhes, frament,AberturaFrament.TAG_DETALHE);
        ft.commit();
    }
}
