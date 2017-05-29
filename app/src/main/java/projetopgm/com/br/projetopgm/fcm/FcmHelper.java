package projetopgm.com.br.projetopgm.fcm;

import android.os.AsyncTask;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import projetopgm.com.br.projetopgm.base.Cliente;
import projetopgm.com.br.projetopgm.login.LoginHelper;

public class FcmHelper extends FirebaseInstanceIdService {

    private static final String SERVER_URL = "www.projetopgmads.somee.com";
    @Override
    public void onTokenRefresh() {
        try{
            String token = FirebaseInstanceId.getInstance().getToken();
            registrarInBackground(token);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void sendRegistrationToServer(String token) throws IOException {
        URL url = new URL("http://"+ SERVER_URL + "/api/ClienteApi");
        HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
        conexao.setRequestMethod("POST");
        conexao.setDoOutput(true);
        conexao.setRequestProperty("Content-Type", "application/json");
        conexao.setRequestProperty("Accept", "application/json");

        Cliente cliente = LoginHelper.usuarioLogado();
        cliente.setAplicativoToken(token);
        Gson gson = new Gson();

        String json = gson.toJson(cliente);

        OutputStream os = conexao.getOutputStream();
        os.write(json.getBytes());

        os.flush();
        os.close();
        conexao.connect();
        int responseCode = conexao.getResponseCode();

        if(responseCode != HttpURLConnection.HTTP_OK);
            throw new RuntimeException("Erro ao salvar no servidor");
    }

    public void registrarInBackground(final String token){
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                try {
                    sendRegistrationToServer(token);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return "";
            }
        }.execute();
    }
}
