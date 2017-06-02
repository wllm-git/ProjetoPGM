package projetopgm.com.br.projetopgm.webservice;

import android.support.annotation.NonNull;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import projetopgm.com.br.projetopgm.base.Cliente;
import projetopgm.com.br.projetopgm.base.Servico;
import projetopgm.com.br.projetopgm.login.LoginHelper;

public class ServicoWebHelper {
    private static final String SERVER_URL = "localhost:3461";//"www.projetopgmads.somee.com"

    @NonNull
    public static String sendServicoToServer(Servico servico) throws IOException {
        URL url = new URL("http://" + SERVER_URL + "/api/ServicoApi");
        HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
        conexao.setRequestMethod("POST");
        conexao.setDoOutput(true);
        conexao.setRequestProperty("Content-Type", "application/json");
        conexao.setRequestProperty("Accept", "application/json");

        Cliente cliente = LoginHelper.usuarioLogado();
        servico.setCliente(cliente);
        Gson gson = new Gson();

        String json = gson.toJson(servico);

        OutputStream os = conexao.getOutputStream();
        os.write(json.getBytes());

        os.flush();
        os.close();
        conexao.connect();
        int responseCode = conexao.getResponseCode();

        if (responseCode != HttpURLConnection.HTTP_OK)
            throw new RuntimeException("Erro ao salvar no servidor");

        return String.valueOf(responseCode);
    }
}