package projetopgm.com.br.projetopgm.webservice;

import android.os.AsyncTask;

import projetopgm.com.br.projetopgm.base.Servico;

public class ServicoWebTask extends AsyncTask<Servico, Void, String> {

    @Override
    protected String doInBackground(Servico... params) {
        try{
            String cod = ServicoWebHelper.sendRegistrationToServer("321sds5a6d4w8e7w9q74d5sa54d56a1");
            return cod;
        }catch (Exception ex){
            return ex.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String s) {
        if(s == null){

        }
    }
}
