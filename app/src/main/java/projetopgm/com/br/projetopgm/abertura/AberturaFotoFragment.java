package projetopgm.com.br.projetopgm.abertura;


import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import projetopgm.com.br.projetopgm.R;
import projetopgm.com.br.projetopgm.compartilhados.FuncoesGlobais;

public class AberturaFotoFragment extends Fragment {

    static final int COLUMNS_NUMBER = 3;

    LinearLayout firstRow;
    LinearLayout secondRow;

    int imagemid;
    int firstRowColumnNumber = 0;
    int secondRowColumnNumber = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View layout = inflater.inflate(R.layout.fragment_abertura_fotos, container, false);

        firstRow = (LinearLayout) layout.findViewById(R.id.row1);
        secondRow = (LinearLayout) layout.findViewById(R.id.row2);

        LinearLayout imagesContainer = (LinearLayout) layout.findViewById(R.id.imagesContainer);
        if (!FuncoesGlobais.isPortrait(getActivity())) {
            imagesContainer.setOrientation(LinearLayout.HORIZONTAL);
            firstRow.setOrientation(LinearLayout.VERTICAL);
            secondRow.setOrientation(LinearLayout.VERTICAL);
        }

        return layout;
    }

    public void addImage(Bitmap image){


        if(firstRowColumnNumber < COLUMNS_NUMBER){
            rowIterator(firstRow,image);
            firstRowColumnNumber++;
        }

        else if(secondRowColumnNumber < COLUMNS_NUMBER){
            rowIterator(secondRow,image);
            secondRowColumnNumber++;
        }

    }

    //Verify in which row the image will be added.
    private void rowIterator(LinearLayout row, Bitmap image) {

        for (int position = 0; position < row.getChildCount(); position++) {

            ImageView placeHolder = (ImageView) row.getChildAt(position);

            if ( isPlaceHolderImage(placeHolder)) {
                placeHolder.setId(imagemid);
                //place_holder.setImageBitmap(Bitmap.createScaledBitmap(image,160,110,true));
                placeHolder.setImageBitmap(image);
                return;
            }
        }
    }

    private Drawable getImagePlaceHolder(){
        return getResources().getDrawable(R.drawable.place_holder);
    }

    private boolean isPlaceHolderImage(ImageView placeHolder){

        Bitmap placeHolderBitmap = ((BitmapDrawable)placeHolder.getDrawable()).getBitmap();
        Drawable myDrawable = getImagePlaceHolder();
        Bitmap imagePlaceHolder = ((BitmapDrawable)myDrawable).getBitmap();

        if ( placeHolderBitmap.sameAs(imagePlaceHolder) )
            return true;
        else
            return false;
    }

    public int verifyTotalFotos(){
        return firstRowColumnNumber + secondRowColumnNumber;
    }

}
