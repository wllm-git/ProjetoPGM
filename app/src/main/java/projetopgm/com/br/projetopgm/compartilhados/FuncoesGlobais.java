package projetopgm.com.br.projetopgm.compartilhados;


import android.content.Context;
import android.util.DisplayMetrics;

public class FuncoesGlobais {
    public static boolean isPortrait(Context context){
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int altura = displayMetrics.heightPixels;
        int largura = displayMetrics.widthPixels;

        return altura > largura;
    }

}
