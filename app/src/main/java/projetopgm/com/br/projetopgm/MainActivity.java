package projetopgm.com.br.projetopgm;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
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
import projetopgm.com.br.projetopgm.listagem.ListagemActivity;
import projetopgm.com.br.projetopgm.localizacao.MapsActivity;
import projetopgm.com.br.projetopgm.login.LoginHelper;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener{
    private static final int RC_SIGN_IN = 1;
    private GoogleApiClient mGoogleApiClient;

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


        //TESTANDO 1,2,3.
        /*ServicoDAO servicoDAO = new ServicoDAO(this);
        Servico servico = new Servico();
        servico.setCliente(LoginHelper.usuarioLogado());
        servico.setDataAbertura(new Date());
        servico.setNumero("G78");
        servico.setDescricao("TESTE WEB SERVICE COM NOTIFICAÇÃO DO SERVIDOR.");
        servico.setTipo(Servico.Tipo.ORCAMENTO);
        servico.setStatus(Servico.Status.ABERTO);
        servicoDAO.salvar(servico);

        servico.setCliente(LoginHelper.usuarioLogado());
        servico.setDataAbertura(new Date());
        servico.setNumero("JJ78");
        servico.setDescricao("FECHADA24");
        servico.setTipo(Servico.Tipo.ORCAMENTO);
        servico.setStatus(Servico.Status.CANCELADO);
        servico.setId(null);
        servicoDAO.salvar(servico);


        ServicoWebTask webTask = new ServicoWebTask();
        webTask.execute(servico);*/
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

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onClick(View v) {
        Intent it = new Intent(this, AberturaServicoAcivity.class);
        startActivity(it);
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
        } else {
            Toast.makeText(this, "Teste " + result.isSuccess(), Toast.LENGTH_SHORT).show();
        }
    }

/* TODO google People API para consultar a idade do cliente para não permitir menores de idade.
    private static HttpTransport HTTP_TRANSPORT = AndroidHttp.newCompatibleTransport();
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String APPLICATION_NAME = "Projeto PGM";
    private class Teste extends AsyncTask<GoogleSignInAccount, Void, Person> {

        @Override
        protected Person doInBackground(GoogleSignInAccount... params) {

            GoogleSignInAccount googleSignInAccount = params[0];

            Collection<String> collection = new ArrayList();
            collection.add(Scopes.PROFILE);
            collection.add(Scopes.PLUS_ME);

            GoogleAccountCredential credential =
                    GoogleAccountCredential.usingOAuth2(MainActivity.this, Arrays.asList(s));
            credential.setSelectedAccount(googleSignInAccount.getAccount());
                    //new Account(googleSignInAccount.getEmail() , "com.google"));
            People service = new People.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                    //.setApplicationName(APPLICATION_NAME)
                    .build();

            try{


                Person meProfile = service.people().get("people/me")
                        //.setRequestMaskIncludeField("person.addresses")
                        .setRequestMaskIncludeField("person.birthdays")
                        //.setRequestMaskIncludeField("person.genders")
                        .execute();
                // e.g. Gender

                return meProfile;
            }catch (IOException ex){
                ex.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Person aPerson) {
            if(aPerson != null)
                Toast.makeText(MainActivity.this, aPerson.getAddresses().get(0).getStreetAddress(), Toast.LENGTH_LONG).show();
            else
                Toast.makeText(MainActivity.this, "Teste erro", Toast.LENGTH_SHORT).show();
        }
    }
*/
}
