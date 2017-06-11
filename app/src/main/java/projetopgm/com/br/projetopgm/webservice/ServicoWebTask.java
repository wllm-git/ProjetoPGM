package projetopgm.com.br.projetopgm.webservice;

import android.os.AsyncTask;

import projetopgm.com.br.projetopgm.base.Servico;


public class ServicoWebTask extends AsyncTask<Servico, Void, String> {

    @Override
    protected String doInBackground(Servico... params) {
        try{
            String cod = ServicoWebHelper.sendServicoToServer(params[0]);
            if(params[0].getStatus() == Servico.Status.ABERTO)
                RetroClient.uploadImages(params[0].getFotos());
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
