package projetopgm.com.br.projetopgm.abertura;


import android.support.v4.app.Fragment;
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
import projetopgm.com.br.projetopgm.bancodados.ServicoDAO;
import projetopgm.com.br.projetopgm.base.Cliente;
import projetopgm.com.br.projetopgm.base.Foto;
import projetopgm.com.br.projetopgm.base.Servico;
import projetopgm.com.br.projetopgm.login.LoginHelper;


public class AberturaServicoAcivity extends AppCompatActivity implements View.OnClickListener{

    static final int REQUEST_IMAGE_CAPTURE = 1;

    EditText description;
    Button btnTakePicture;
    Button btnSendService;

    AberturaFotoFragment fragmentFotos;
    AberturaInfoFrament fragmentInfo;

    Servico servico;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abertura_servico);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_launcher_round);


        fragmentFotos = (AberturaFotoFragment) getSupportFragmentManager().findFragmentById(R.id.framentAberturaFotos);
        fragmentInfo = (AberturaInfoFrament) getSupportFragmentManager().findFragmentById(R.id.fragmentDetalhesInfo);


        btnTakePicture = (Button) findViewById(R.id.btnTakePicture);
        btnTakePicture.setOnClickListener(this);
        btnSendService = (Button) findViewById(R.id.btnSendService);

        servico = new Servico();
        description = (EditText) findViewById(R.id.aberturaEdtInfo);


        if(savedInstanceState != null){

            if(servico.getFotos() != null){
                servico = (Servico) savedInstanceState.getSerializable("servico");

                for (int x = 0; x < servico.getFotos().size();x++){

                    byte [] file = servico.getFotos().get(x).getArquivo();
                    Bitmap bitmap = BitmapFactory.decodeByteArray(file,0,file.length);
                    fragmentFotos.addImage(bitmap);
                }
            }

            if(description != null)
                description.setText(savedInstanceState.getString("description"));

        }


    }

    @Override
    public void onClick(View v) {

        if((fragmentFotos.verifyTotalFotos()) == 6){
            Toast.makeText(this,"Número máximo de fotos atingido, apague uma foto para continuar",Toast.LENGTH_LONG).show();
            return;
        }

        takePictureIntent();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){

            Bundle extras = data.getExtras();
            Bitmap imageBitMap = (Bitmap) extras.get("data");

            fragmentFotos.addImage(imageBitMap);

            Foto foto = new Foto();

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            imageBitMap.compress(Bitmap.CompressFormat.PNG,100,stream);
            foto.setArquivo(stream.toByteArray());
            foto.setNome("Foto" + new Date().getTime());

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


    public void sendService(View view){

        ServicoDAO dao = new ServicoDAO(this);
        String descriptionToSave = getDescription();

        if(LoginHelper.usuarioLogado() != null)
            servico.setCliente(LoginHelper.usuarioLogado());

        if(descriptionToSave.trim() != "")
            servico.setDescricao(descriptionToSave);

        else{
            Toast.makeText(this, "Preencha a descrição do problema", Toast.LENGTH_SHORT).show();
            return;
        }

        if(servico.getFotos().isEmpty()) {
            Toast.makeText(this, "É necessário enviar no minimo uma foto do veículo", Toast.LENGTH_SHORT).show();
            return;
        }

        dao.salvar(servico);

        Toast.makeText(this, "Serviço enviado!", Toast.LENGTH_SHORT).show();

        disableComponents();

    }

    private void disableComponents(){
        description.setEnabled(false);
        btnTakePicture.setEnabled(false);
        btnSendService.setEnabled(false);

    }

    public String getDescription(){


        if(description != null)
            return description.getText().toString();

        return "";
    }

}



