package projetopgm.com.br.projetopgm.listagem;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.zip.Inflater;

import projetopgm.com.br.projetopgm.R;
import projetopgm.com.br.projetopgm.base.Servico;

public class ListagemAdapter extends BaseAdapter {

    private ArrayList<Servico> servicos;
    private Context context;

    public ListagemAdapter(Context context, ArrayList<Servico> servicos) {
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

        return position;
    }

        @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = LayoutInflater.from(context).inflate(R.layout.fragment_listagem_item, null);

        Servico s = servicos.get(position);
        //ImageView img = (ImageView) convertView.findViewById(R.id.imgIcone);
        TextView dsc = (TextView) convertView.findViewById(R.id.tvwDesc);
        TextView dt = (TextView) convertView.findViewById(R.id.tvwData);
        TextView st = (TextView) convertView.findViewById(R.id.tvwStatusListagem);

        dsc.setText(s.getDescricao());
        dt.setText(s.getDataAbertura().toString());
        st.setText(s.getStatus().toString());

        return convertView;
    }

    public ArrayList<Servico> getServicos(){

        return this.servicos;
    }
}
