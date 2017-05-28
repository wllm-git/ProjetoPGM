package projetopgm.com.br.projetopgm.fcm;


import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import projetopgm.com.br.projetopgm.base.Servico;

public class JsonTipoDeserializer implements JsonDeserializer<Servico.Tipo> {
    public Servico.Tipo deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String s = json.getAsJsonPrimitive().getAsString();
        int i = Integer.parseInt(s);
        Servico.Tipo tipo;

        switch (i){
            case 0:
                tipo = Servico.Tipo.ORCAMENTO;
                break;
            case 1:
                tipo = Servico.Tipo.OS;
                break;
            default:
                tipo = Servico.Tipo.ORCAMENTO;
        }

        return tipo;
    }
}
