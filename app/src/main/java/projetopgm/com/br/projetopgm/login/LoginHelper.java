package projetopgm.com.br.projetopgm.login;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.FirebaseApp;

import projetopgm.com.br.projetopgm.bancodados.ClienteDAO;
import projetopgm.com.br.projetopgm.base.Cliente;

public class LoginHelper {
    private static final String PREFERENCES = "SharedPreferences";
    private static Cliente cliente;
    private static SharedPreferences preferences;

    public static void init(Context context) {
        preferences = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        if(preferences != null){
            long id = preferences.getLong("clienteId", -1);
            String nome = preferences.getString("clienteNome", null);
            String email = preferences.getString("clienteEmail", null);
            if(id != -1){
                cliente = new Cliente();
                cliente.setId(id);
                cliente.setNome(nome);
                cliente.setEmail(email);
                FirebaseApp.initializeApp(context);
            }
        }
    }

    public static boolean isLogado() {
        return cliente != null;
    }

    public static Cliente usuarioLogado(){
        return cliente;
    }

    public static void salvarUsuario(Context context, GoogleSignInAccount acct) {
        ClienteDAO clienteDAO = new ClienteDAO(context);
        cliente = clienteDAO.buscarPorEmail(acct.getEmail());
        if(cliente == null){
            cliente = new Cliente();
            cliente.setNome(acct.getDisplayName());
            cliente.setEmail(acct.getEmail());
            clienteDAO.salvar(cliente);
        }
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong("clienteId", cliente.getId());
        editor.putString("clienteNome", cliente.getNome());
        editor.putString("clienteEmail", cliente.getEmail());
        editor.commit();
        FirebaseApp.initializeApp(context);
    }
}
