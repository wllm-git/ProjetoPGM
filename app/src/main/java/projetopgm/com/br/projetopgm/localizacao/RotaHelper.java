package projetopgm.com.br.projetopgm.localizacao;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.PolyUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class RotaHelper {
    private static String API_KEY = "AIzaSyCoONX3Ws5_gdH5-yg4cz_5fxz1ZXvfz3I";
    public static List<LatLng> gerarRota(LatLng orig, LatLng dest){
        List<LatLng> posicoes = new ArrayList<>();

        try{
            String urlStr = String.format(Locale.US, "http://maps.google.com/maps/api/directions/json?"+
                        "origin=%f,%f&destination=%f,%f&"+
                        "mode=driving",
                    orig.latitude, orig.longitude,
                    dest.latitude, dest.longitude);

            URL url = new URL(urlStr);

            HttpURLConnection connection = (HttpURLConnection)url.openConnection();

            connection.setDoOutput(false);

            //AIzaSyCoONX3Ws5_gdH5-yg4cz_5fxz1ZXvfz3I
            Scanner scanner = new Scanner(connection.getInputStream());
            StringBuffer jsonBuffer = new StringBuffer();
            while (scanner.hasNext())
                jsonBuffer.append(scanner.next());

            JSONObject objeto = new JSONObject(jsonBuffer.toString());
            JSONObject rotas = objeto.getJSONArray("routes").getJSONObject(0);
            JSONObject parte = rotas.getJSONArray("legs").getJSONObject(0);
            JSONArray passos = parte.getJSONArray("steps");

            int qtd = passos.length();

            JSONObject passo;

            for(int i = 0; i < qtd; i++){
                passo = passos.getJSONObject(i);
                String pontos = passo.getJSONObject("polyline").getString("points");
                posicoes.addAll(PolyUtil.decode(pontos));
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return posicoes;
    }
}
