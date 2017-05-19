package projetopgm.com.br.projetopgm;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import projetopgm.com.br.projetopgm.abertura.AberturaServicoAcivity;
import projetopgm.com.br.projetopgm.listagem.ListagemActivity;
import projetopgm.com.br.projetopgm.localizacao.MapsActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_launcher_round);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabNovo);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(MainActivity.this, AberturaServicoAcivity.class);
                startActivity(it);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_listagem) {
            Intent it = new Intent(this, ListagemActivity.class);
            startActivity(it);
            return true;
        }else if(id == R.id.action_localizacao){
            Intent it = new Intent(this, MapsActivity.class);
            startActivity(it);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
