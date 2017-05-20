package projetopgm.com.br.projetopgm.abertura;

import android.content.Intent;
import android.graphics.Bitmap;
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
    LinearLayout linear;
    int imagemid;

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
        linear = (LinearLayout) findViewById(R.id.linear);


        if(linear.getChildCount() == 0){
            ImageView img = new ImageView(this);

            img.setId(0);
            img.setImageResource(R.drawable.celta_mini);

            img.setPadding(5,5,5,5);

            linear.addView(img);
        }



    }
    private void takePictureIntent(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takePictureIntent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }


    @Override
    public void onClick(View v) {
        if(linear.getChildAt(0).getId() == 0){
            linear.removeView(linear.getChildAt(0));
        }

        if(linear.getChildCount() > 3){
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

            ImageView imagem = new ImageView(this);


            imagem.setId(imagemid);

            imagem.setPadding(10,10,10,10);
            imagem.setImageBitmap(Bitmap.createScaledBitmap(imageBitMap,220,200,true));

            linear.addView(imagem);


        }

    }
}

