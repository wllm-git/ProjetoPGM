package projetopgm.com.br.projetopgm.abertura;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;
import java.util.Date;

import projetopgm.com.br.projetopgm.R;
import projetopgm.com.br.projetopgm.bancodados.ServicoDAO;
import projetopgm.com.br.projetopgm.base.Foto;
import projetopgm.com.br.projetopgm.base.Servico;
import projetopgm.com.br.projetopgm.login.LoginHelper;
import projetopgm.com.br.projetopgm.webservice.ServicoWebTask;


public class AberturaServicoAcivity extends AppCompatActivity implements View.OnClickListener{

    static final int REQUEST_IMAGE_CAPTURE = 1;


//    Button btnTakePicture;
//    Button btnSendService;

    AberturaFotoFragment fragmentFotos;
    AberturaInfoFrament fragmentInfo;

    LinearLayout layout;

    Servico servico;
    Uri outputFileUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abertura_servico);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_launcher_round);


        fragmentFotos = (AberturaFotoFragment) getSupportFragmentManager().findFragmentById(R.id.framentAberturaFotos);
        fragmentInfo = (AberturaInfoFrament) getSupportFragmentManager().findFragmentById(R.id.framentAberturaInfo);

        fragmentInfo.addOnClickListener(this);




//        btnTakePicture = (Button) findViewById(R.id.btnTakePicture);
//        btnTakePicture.setOnClickListener(this);
//        btnSendService = (Button) findViewById(R.id.btnSendService);

        servico = new Servico();


        if(savedInstanceState != null){

            if(servico.getFotos() != null){
                servico = (Servico) savedInstanceState.getSerializable("servico");

                for (int x = 0; x < servico.getFotos().size();x++){

                    //byte [] file = servico.getFotos().get(x).getArquivo();
                    //Bitmap bitmap = BitmapFactory.decodeByteArray(file,0,file.length);
                    String file = servico.getFotos().get(x).getArquivo();
                    Bitmap bitmap = BitmapFactory.decodeFile(file);

                    fragmentFotos.addImage(bitmap);
                }
            }

           if(fragmentInfo.descricao != null)
               fragmentInfo.descricao.setText(savedInstanceState.getString("description"));

        }


    }



    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btnTakePicture :
                if((fragmentFotos.verifyTotalFotos()) == 6){
                    Toast.makeText(this,"Número máximo de fotos atingido, apague uma foto para continuar",Toast.LENGTH_LONG).show();
                    return;
                }
                takePictureIntent();
                break;

            case R.id.btnSendService:
                sendService();
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){

            //outputFileUri = data.getData();
            String path = outputFileUri.getPath();

            Bitmap imageBitMap = BitmapFactory.decodeFile(path);
            fragmentFotos.addImage(imageBitMap);

            Foto foto = new Foto();
            foto.setNome(new File(path).getName());
            foto.setArquivo(path);

            /*
            Bundle extras = data.getExtras();
            Bitmap imageBitMap = (Bitmap) extras.get("data");

            fragmentFotos.addImage(imageBitMap);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            imageBitMap.compress(Bitmap.CompressFormat.PNG,100,stream);

            Foto foto = new Foto();
            foto.setArquivo(stream.toByteArray());
            foto.setNome("Foto" + new Date().getTime());
            */

            servico.getFotos().add(foto);
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

            if(servico.getFotos() != null)
                outState.putSerializable("servico",servico);

            if(fragmentInfo.descricao.getText().toString() != null)
                outState.putString("descricao",fragmentInfo.descricao.getText().toString());
    }

    private void takePictureIntent(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        String nameImg = System.currentTimeMillis() + ".jpg";
        File file = new File(Environment.getExternalStorageDirectory(), nameImg);
        outputFileUri = Uri.fromFile(file);
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
        if(takePictureIntent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }


    public void sendService(){

        ServicoDAO dao = new ServicoDAO(this);
        String descriptionToSave = getDescription();

        if(LoginHelper.usuarioLogado() != null)
            servico.setCliente(LoginHelper.usuarioLogado());

        if(!descriptionToSave.trim().equals("") )
            servico.setDescricao(descriptionToSave);
        else{
            Toast.makeText(this, "Preencha a descrição do problema", Toast.LENGTH_SHORT).show();
            return;
        }

//        if(servico.getFotos().isEmpty()) {
//            Toast.makeText(this, "É necessário enviar no minimo uma foto do veículo", Toast.LENGTH_SHORT).show();
//            return;
//        }

        servico.setStatus(Servico.Status.ABERTO);
        servico.setTipo(Servico.Tipo.ORCAMENTO);
        servico.setDataAbertura(new Date());
        servico.setNumero(String.valueOf(new Date().getTime()));

        dao.salvar(servico);

        Toast.makeText(this, "Serviço salvo!", Toast.LENGTH_SHORT).show();

        ServicoWebTask webTask = new ServicoWebTask();
        webTask.execute(servico);

        disableComponents();

    }

    private void disableComponents(){
        fragmentInfo.descricao.setEnabled(false);
        fragmentInfo.btnFoto.setEnabled(false);
        fragmentInfo.btnEnviar.setEnabled(false);


    }

    public String getDescription(){

        if(fragmentInfo.descricao.getText().toString() != null)
            return fragmentInfo.descricao.getText().toString();

        return "";
    }



}



