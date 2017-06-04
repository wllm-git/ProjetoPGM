package projetopgm.com.br.projetopgm.compartilhados;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;

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
}
