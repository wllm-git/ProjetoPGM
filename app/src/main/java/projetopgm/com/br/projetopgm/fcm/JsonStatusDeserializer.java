package projetopgm.com.br.projetopgm.fcm;


import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import projetopgm.com.br.projetopgm.base.Servico;

public class JsonStatusDeserializer implements JsonDeserializer<Servico.Status> {
    public Servico.Status deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String s = json.getAsJsonPrimitive().getAsString();
        int i = Integer.parseInt(s);
        Servico.Status status;

        switch (i){
            case 0:
                status = Servico.Status.ABERTO;
                break;
            case 1:
                status = Servico.Status.ANDAMENTO;
                break;
            case 2:
                status = Servico.Status.FECHADO;
                break;
            case 3:
                status = Servico.Status.CANCELADO;
            default:
                status = Servico.Status.ABERTO;
        }

        return status;
    }
}
