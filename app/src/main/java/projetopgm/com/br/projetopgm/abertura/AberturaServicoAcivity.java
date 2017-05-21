package projetopgm.com.br.projetopgm.abertura;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.Date;

import projetopgm.com.br.projetopgm.R;
import projetopgm.com.br.projetopgm.base.Foto;
import projetopgm.com.br.projetopgm.base.Servico;

public class AberturaServicoAcivity extends AppCompatActivity implements View.OnClickListener{

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int COLUMNS_NUMBER = 3;

    LinearLayout firstRow;
    LinearLayout secondRow;
    EditText description;
    int imagemid;
    int firstRowColumnNumber = 0;
    int secondRowColumnNumber = 0;
    Servico servico;


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

        servico = new Servico();
        firstRow = (LinearLayout) findViewById(R.id.row1);
        secondRow = (LinearLayout) findViewById(R.id.row2);
        description = (EditText) findViewById(R.id.edtAberturaDescricao);

        if(savedInstanceState != null){

            if(servico.getFotos() != null){
                servico = (Servico) savedInstanceState.getSerializable("servico");

                for (int x = 0; x < servico.getFotos().size();x++){

                    byte [] file = servico.getFotos().get(x).getArquivo();
                    Bitmap bitmap = BitmapFactory.decodeByteArray(file,0,file.length);
                    addImage(bitmap);
                }
            }

            if(description != null)
                description.setText(savedInstanceState.getString("description"));

        }


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

            Foto foto = new Foto();

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            imageBitMap.compress(Bitmap.CompressFormat.PNG,100,stream);
            foto.setArquivo(stream.toByteArray());
            foto.setName("Foto0" + imagemid);

            servico.getFotos().add(foto);

        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

            if(servico.getFotos() != null)
                outState.putSerializable("servico",servico);

            if(description != null)
                outState.putString("descricao",description.getText().toString());
    }

    private void takePictureIntent(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takePictureIntent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private void addImage(Bitmap image){


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
                placeHolder.setImageBitmap(Bitmap.createScaledBitmap(image,160,110,true));
                return;
            }
        }
    }
    
    private Drawable getImagePlaceHolder(){
        return getResources().getDrawable(R.drawable.celta_mini);
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
}

