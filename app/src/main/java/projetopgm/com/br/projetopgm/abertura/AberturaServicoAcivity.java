package projetopgm.com.br.projetopgm.abertura;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import projetopgm.com.br.projetopgm.R;

public class AberturaServicoAcivity extends AppCompatActivity implements View.OnClickListener{

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int COLUMNS_NUMBER = 3;

    LinearLayout firstRow;
    LinearLayout secondRow;
    int imagemid;
    int firstRowColumnNumber = 0;
    int secondRowColumnNumber = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abertura_servico);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_launcher_round);

        Button btnTakePicture = (Button) findViewById(R.id.btnTakePicture);
        btnTakePicture.setOnClickListener(this);
        firstRow = (LinearLayout) findViewById(R.id.linha1);
        secondRow = (LinearLayout) findViewById(R.id.linha2);

    }

    @Override
    public void onClick(View v) {

        if((firstRowColumnNumber + secondRowColumnNumber) == 6){
            Toast.makeText(this,"Número máximo de fotos atingido, apague uma foto para continuar",Toast.LENGTH_LONG).show();
            return;
        }

        imagemid++;
        takePictureIntent();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){


            Bundle extras = data.getExtras();
            Bitmap imageBitMap = (Bitmap) extras.get("data");

            addImage(imageBitMap);

        }

    }

    private void takePictureIntent(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takePictureIntent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private void addImage(Bitmap imagem){


        if(firstRowColumnNumber < COLUMNS_NUMBER){
            rowIterator(firstRow,imagem);
            firstRowColumnNumber++;
        }

        else if(secondRowColumnNumber < COLUMNS_NUMBER){
            rowIterator(secondRow,imagem);
            secondRowColumnNumber++;
            }

    }

    //Verify in which row the image will be added.
    private void rowIterator(LinearLayout row, Bitmap image) {
        for (int position = 0; position < row.getChildCount(); position++) {

            ImageView placeHolder = (ImageView) row.getChildAt(position);
            Bitmap placeHolderBitmap = ((BitmapDrawable)placeHolder.getDrawable()).getBitmap();
            Drawable myDrawable = getImagePlaceHolder();
            Bitmap imagePlaceHolder = ((BitmapDrawable)myDrawable).getBitmap();


            if ( placeHolderBitmap.sameAs(imagePlaceHolder)) {
                placeHolder.setId(imagemid);
                placeHolder.setImageBitmap(Bitmap.createScaledBitmap(image,160,110,true));
                return;
            }
        }
    }
    
    private Drawable getImagePlaceHolder(){
        return getResources().getDrawable(R.drawable.celta_mini);
    }
}

