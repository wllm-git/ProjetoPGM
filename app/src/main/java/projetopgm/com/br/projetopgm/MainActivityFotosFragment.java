package projetopgm.com.br.projetopgm;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import projetopgm.com.br.projetopgm.base.Servico;
import projetopgm.com.br.projetopgm.compartilhados.FuncoesGlobais;


public class MainActivityFotosFragment extends Fragment {

    private Servico servico;

    public MainActivityFotosFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(savedInstanceState != null)
            servico = (Servico) savedInstanceState.getSerializable("servico");
        return inflater.inflate(R.layout.fragment_main_fotos, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        if(servico != null)
            atualizarFotos();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if(servico != null)
            outState.putSerializable("servico", servico);
    }

    public void adicionarFotos(Servico servico){
        if(servico != null)
            this.servico = servico;
        else
            this.servico = null;
    }

    private void atualizarFotos(){

        switch (servico.getFotos().size()){
            case 6:
                atualizarImageView(R.id.imagem6);
            case 5:
                atualizarImageView(R.id.imagem5);
            case 4:
                atualizarImageView(R.id.imagem4);
            case 3:
                atualizarImageView(R.id.imagem3);
            case 2:
                atualizarImageView(R.id.imagem2);
            case 1:
                atualizarImageView(R.id.imagem1);
        }
    }

    private void atualizarImageView(int id){
        ImageView imageView = (ImageView) getActivity().findViewById(id);
        int x = imageView.getDrawable().getMinimumWidth();
        int y = imageView.getDrawable().getMinimumHeight();
        String path = servico.getFotos().get(0).getArquivo();
        imageView.setImageBitmap(FuncoesGlobais.decodeFile(path, x, y));
    }
}
