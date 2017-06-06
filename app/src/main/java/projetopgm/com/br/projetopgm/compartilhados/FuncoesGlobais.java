package projetopgm.com.br.projetopgm.compartilhados;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;

import projetopgm.com.br.projetopgm.R;
import projetopgm.com.br.projetopgm.base.Servico;

public class FuncoesGlobais {
    public static boolean isPortrait(Context context){
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int altura = displayMetrics.heightPixels;
        int largura = displayMetrics.widthPixels;

        return altura > largura;
    }

    public static Bitmap decodeFile(String pathFile, int REQUIRED_WIDTH, int REQUIRED_HIGHT){
        try {
            //Decode image size
            BitmapFactory.Options opt = new BitmapFactory.Options();
            opt.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(pathFile, opt);

            //Find the correct scale value. It should be the power of 2.
            int scale=1;
            while(opt.outWidth/scale/2>=REQUIRED_WIDTH && opt.outHeight/scale/2>=REQUIRED_HIGHT)
                scale*=2;

            //Decode with inSampleSize
            BitmapFactory.Options opt2 = new BitmapFactory.Options();
            opt2.inSampleSize=scale;
            return BitmapFactory.decodeFile(pathFile, opt2);
        } catch (Exception e) {}
        return null;
    }

    public static String formatarStatus(Context context, Servico.Status status){
        String statusStr;

        switch (status){
            case ABERTO:
                statusStr = context.getString(R.string.status_aberto);
                break;
            case ANDAMENTO:
                statusStr = context.getString(R.string.status_adamento);
                break;
            case FECHADO:
                statusStr = context.getString(R.string.status_fechado);
                break;
            case CANCELADO:
                statusStr = context.getString(R.string.status_cancelado);
                break;
            default:
                statusStr = context.getString(R.string.status_aberto);
        }
        return statusStr;
    }

    public static String formatarTipo(Context context, Servico.Tipo tipo){
        String tipoStr;
        switch (tipo){
            case ORCAMENTO:
                tipoStr = context.getString(R.string.tipo_orcamento);;
                break;
            case OS:
                tipoStr = context.getString(R.string.tipo_os);;
                break;
            default:
                tipoStr = context.getString(R.string.tipo_orcamento);;
        }

        return tipoStr;
    }
}
