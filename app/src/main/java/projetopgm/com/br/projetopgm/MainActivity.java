package projetopgm.com.br.projetopgm;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;

import projetopgm.com.br.projetopgm.abertura.AberturaServicoAcivity;
import projetopgm.com.br.projetopgm.acompanhamento.AcompanhamentoServicoActivity;
import projetopgm.com.br.projetopgm.bancodados.ServicoDAO;
import projetopgm.com.br.projetopgm.base.Servico;
import projetopgm.com.br.projetopgm.listagem.ListagemActivity;
import projetopgm.com.br.projetopgm.listagem.ListagemAdapter;
import projetopgm.com.br.projetopgm.localizacao.MapsActivity;
import projetopgm.com.br.projetopgm.login.LoginHelper;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener{
    private static final int RC_SIGN_IN = 1;
    private GoogleApiClient mGoogleApiClient;
    private ListagemAdapter listagemAdapter;
    private ServicoPagerAdapter servicoPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_launcher_round);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabNovo);
        fab.setOnClickListener(this);

        servicoPagerAdapter = new ServicoPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(servicoPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestScopes(new Scope(Scopes.PLUS_ME))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        mGoogleApiClient.connect();
        LoginHelper.init(this);
        signIn();
    }

    @Override
    protected void onStart() {
        if(LoginHelper.isLogado())
            loadServicoAberto();

        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(!LoginHelper.isLogado()){
            signIn();
            return false;
        }

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

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onClick(View v) {
        if(!LoginHelper.isLogado()){
            signIn();
            return;
        }

        ServicoDAO servicoDAO = new ServicoDAO(this);
        Servico servico = servicoDAO.buscarAberta(LoginHelper.usuarioLogado());

        if(servico == null){
            Intent it = new Intent(this, AberturaServicoAcivity.class);
            startActivity(it);
        }
        else{
            Intent it = new Intent(this, AcompanhamentoServicoActivity.class);
            it.putExtra("servico", servico);
            startActivity(it);
        }
    }

    private void signIn() {
        if(!LoginHelper.isLogado()) {
            Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
            startActivityForResult(signInIntent, RC_SIGN_IN);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            GoogleSignInAccount acct = result.getSignInAccount();
            LoginHelper.salvarUsuario(this, acct);
            Toast.makeText(this, acct.getDisplayName() +" "+ acct.getEmail(), Toast.LENGTH_LONG).show();
            loadServicoAberto();
        } else {
            Toast.makeText(this, "Teste " + result.isSuccess(), Toast.LENGTH_SHORT).show();
        }
    }

    private void loadServicoAberto(){
        ServicoDAO servicoDAO = new ServicoDAO(this);
        Servico servico = servicoDAO.buscarAberta(LoginHelper.usuarioLogado());

        servicoPagerAdapter.adicionarServico(servico);
    }
}
