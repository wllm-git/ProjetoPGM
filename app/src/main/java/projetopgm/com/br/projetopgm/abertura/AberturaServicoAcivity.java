package projetopgm.com.br.projetopgm.abertura;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.provider.MediaStore;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import projetopgm.com.br.projetopgm.R;
import projetopgm.com.br.projetopgm.base.Servico;

public class AberturaServicoAcivity extends AppCompatActivity implements View.OnClickListener{

    static final int REQUEST_IMAGE_CAPTURE = 1;
    LinearLayout linha1;
    LinearLayout linha2;
    int imagemid;
    int linha1Size = 0;
    int linha2Size = 0;
    int totalFotos = 0;

    public static final String EXTRA_SERVICO_ABERTURA = "abertura";

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
        linha1 = (LinearLayout) findViewById(R.id.linha1);
        linha2 = (LinearLayout) findViewById(R.id.linha2);

    }
    private void takePictureIntent(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takePictureIntent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }


    @Override
    public void onClick(View v) {

        if(totalFotos > 6){
            Toast.makeText(this,"Número máximo de fotos atingido, exclua uma foto.",Toast.LENGTH_LONG).show();
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

            adicionarImagem(imageBitMap);

        }

    }

    private void adicionarImagem(Bitmap imagem){


        if(linha1Size < 3){
            percorrerLinha(linha1,imagem);
            linha1Size++;
        }

        else if(linha2Size < 3){
            percorrerLinha(linha2,imagem);
            linha2Size++;
            }

    }

    private void percorrerLinha(LinearLayout linha, Bitmap imagem) {
        for (int x = 0; x < linha.getChildCount(); x++) {

            ImageView temp = (ImageView) linha.getChildAt(x);
            Bitmap bmap = ((BitmapDrawable)temp.getDrawable()).getBitmap();
            Drawable myDrawable = getResources().getDrawable(R.drawable.celta_mini);
            Bitmap mylogo = ((BitmapDrawable)myDrawable).getBitmap();


            if ( bmap.sameAs(mylogo)) {
                temp.setId(imagemid);
                temp.setImageBitmap(imagem);
                return;
            }
        }
    }
}

