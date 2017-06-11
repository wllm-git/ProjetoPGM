package projetopgm.com.br.projetopgm.fcm;

import android.content.Context;
import android.content.SharedPreferences;
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

    private static final String PREFERENCES_FCM = "SharedPreferences";
    private static final String SERVER_URL = "www.projetopgmads.somee.com";// "localhost:3461"

    @Override
    public void onTokenRefresh() {
        try{
            String token = FirebaseInstanceId.getInstance().getToken();

            if(token != null && token.length() > 5){
                SharedPreferences.Editor editor = getApplicationContext()
                        .getSharedPreferences(PREFERENCES_FCM, Context.MODE_PRIVATE)
                        .edit();
                editor.putString("tokenFCM", token);
                editor.apply();
            }
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

        if(responseCode != HttpURLConnection.HTTP_OK)
            throw new RuntimeException("Erro ao salvar no servidor");
    }

    public void registrarInBackground(final SharedPreferences sharedPreferences, final boolean firstLogin){

        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                boolean enviado = sharedPreferences.getBoolean("enviado", firstLogin);
                if(enviado && !firstLogin)
                    return "";

                while (true){
                    try {
                        Thread.sleep(2000);

                        String token = sharedPreferences.getString("tokenFCM", null);
                        if(token != null && token.length() > 5 && LoginHelper.isLogado()){
                            sendRegistrationToServer(token);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putBoolean("enviado", true);
                            editor.apply();
                            break;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return "";
            }
        }.execute();
    }
}
