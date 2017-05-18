package projetopgm.com.br.projetopgm.detalhes;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import projetopgm.com.br.projetopgm.R;
import projetopgm.com.br.projetopgm.base.Servico;

public class DetalhesAdapter extends BaseAdapter{

    private ArrayList<Servico> servicos;
    private Context context;

    public DetalhesAdapter(Context context, ArrayList<Servico> servicos) {
        this.servicos = servicos;
        this.context = context;
    }

    @Override
    public int getCount() {
        return servicos.size();
    }

    @Override
    public Object getItem(int position) {
        return servicos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = LayoutInflater.from(context).inflate(R.layout.fragment_detalhes, null);

        Servico s = servicos.get(position);
        //ImageView img = (ImageView) convertView.findViewById(R.id.imgIcone);
        TextView dsc = (TextView) convertView.findViewById(R.id.tvwDescDetalhes);
        TextView dtaberta = (TextView) convertView.findViewById(R.id.tvwDataAberta);
        TextView dtfechada = (TextView) convertView.findViewById(R.id.tvwDataFechada);
        TextView valor = (TextView) convertView.findViewById(R.id.tvwValor);

        dsc.setText(s.getDescricao());
        dtaberta.setText(s.getDataAbertura().toString());
        dtfechada.setText(s.getDataFechamento().toString());
        valor.setText(s.getPrecoFinal().toString());

        return convertView;
    }


}
