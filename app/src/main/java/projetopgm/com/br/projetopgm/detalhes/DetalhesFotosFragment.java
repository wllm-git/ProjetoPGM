package projetopgm.com.br.projetopgm.detalhes;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import projetopgm.com.br.projetopgm.R;
import projetopgm.com.br.projetopgm.base.Servico;
import projetopgm.com.br.projetopgm.compartilhados.FuncoesGlobais;

public class DetalhesFotosFragment extends Fragment {

    private Servico servico;

    public DetalhesFotosFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentFotos = (View) inflater.inflate(R.layout.fragment_detalhes_fotos, container, false);

        LinearLayout linearLayout = (LinearLayout) fragmentFotos.findViewById(R.id.row1);
        if (!FuncoesGlobais.isPortrait(getActivity())) {
            linearLayout.setOrientation(LinearLayout.VERTICAL);
        }

        Intent it = getActivity().getIntent();
        servico = (Servico) it.getSerializableExtra("servico");
        if (servico != null)
            atualizarFotos(fragmentFotos);

        return fragmentFotos;
    }

    private void atualizarFotos(View viewGroup){

        switch (servico.getFotos().size()){
            case 6:
                atualizarImageView(R.id.imagem6, viewGroup,5);
            case 5:
                atualizarImageView(R.id.imagem5, viewGroup,4);
            case 4:
                atualizarImageView(R.id.imagem4, viewGroup,3);
            case 3:
                atualizarImageView(R.id.imagem3, viewGroup,2);
            case 2:
                atualizarImageView(R.id.imagem2, viewGroup,1);
            case 1:
                atualizarImageView(R.id.imagem1, viewGroup,0);
        }
    }

    private void atualizarImageView(int id, View viewGroup, int i){
        ImageView imageView = (ImageView) viewGroup.findViewById(id);
        String path = servico.getFotos().get(i).getArquivo();
        imageView.setImageBitmap(FuncoesGlobais.decodeFile(path, 100, 100));
    }
}
