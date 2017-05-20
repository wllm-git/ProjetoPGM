package projetopgm.com.br.projetopgm.bancodados;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import projetopgm.com.br.projetopgm.base.Servico;

public class ServicoDAO extends BancoDados{

    public ServicoDAO(Context context) {
        super(context);
    }

    public void salvar(Servico servico){
        if(servico.getId() == null || servico.getId() <= 0)
            inserir(servico);
        else
            atualizar(servico);
    }

    private long inserir(Servico servico){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(Tabela.CAMPO_DATAABERTURA, servico.getDataAbertura().getTime());
        valores.put(Tabela.CAMPO_NUMERO, servico.getNumero());
        valores.put(Tabela.CAMPO_DESCRICAO, servico.getDescricao());
        valores.put(Tabela.CAMPO_TIPO, servico.getTipo().toString());
        valores.put(Tabela.CAMPO_STATUS, servico.getStatus().toString());

        long id = db.insert(Tabela.NOME_TABELA, null, valores);
        if(id != -1)
            servico.setId(id);

        db.close();

        return id;
    }

    private long atualizar(Servico servico){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(Tabela.CAMPO_NUMERO, servico.getNumero());
        valores.put(Tabela.CAMPO_DESCRICAO, servico.getDescricao());
        valores.put(Tabela.CAMPO_PRECOAVALIADO, servico.getPrecoAvaliado());
        valores.put(Tabela.CAMPO_PRECOFINAL, servico.getPrecoFinal());
        valores.put(Tabela.CAMPO_ACRESCIMO, servico.getAcrescimo());
        valores.put(Tabela.CAMPO_DESCONTO, servico.getDesconto());
        valores.put(Tabela.CAMPO_TIPO, servico.getTipo().toString());
        valores.put(Tabela.CAMPO_STATUS, servico.getStatus().toString());

        int linhas = db.update(Tabela.NOME_TABELA,
                valores,
                Tabela.CAMPO_ID + " = ?",
                new String[]{String.valueOf(servico.getId())});

        db.close();

        return linhas;
    }

    public ArrayList<Servico> buscarTodos(){
        SQLiteDatabase db = getReadableDatabase();

        ArrayList<Servico> servicos = new ArrayList<>();

        String sql = "select * from " + Tabela.NOME_TABELA + " order by " + Tabela.CAMPO_ID;

        Cursor cursor = db.rawQuery(sql, null);

        while (cursor.moveToNext()){
            Servico s = new Servico();

            s.setId(cursor.getLong(cursor.getColumnIndex(Tabela.CAMPO_ID)));
            s.setDataAbertura(cursor.getLong(cursor.getColumnIndex(Tabela.CAMPO_DATAABERTURA)));
            s.setDataAvaliacao(cursor.getLong(cursor.getColumnIndex(Tabela.CAMPO_DATAAVALIACAO)));
            s.setDataFechamento(cursor.getLong(cursor.getColumnIndex(Tabela.CAMPO_DATAFECHAMENTO)));
            s.setNumero(cursor.getString(cursor.getColumnIndex(Tabela.CAMPO_NUMERO)));
            s.setDescricao(cursor.getString(cursor.getColumnIndex(Tabela.CAMPO_DESCRICAO)));
            s.setPrecoAvaliado(cursor.getDouble(cursor.getColumnIndex(Tabela.CAMPO_PRECOAVALIADO)));
            s.setPrecoFinal(cursor.getDouble(cursor.getColumnIndex(Tabela.CAMPO_PRECOFINAL)));
            s.setAcrescimo(cursor.getDouble(cursor.getColumnIndex(Tabela.CAMPO_ACRESCIMO)));
            s.setDesconto(cursor.getDouble(cursor.getColumnIndex(Tabela.CAMPO_DESCONTO)));
            s.setTipo(cursor.getString(cursor.getColumnIndex(Tabela.CAMPO_TIPO)));
            s.setStatus(cursor.getString(cursor.getColumnIndex(Tabela.CAMPO_STATUS)));

            servicos.add(s);
        }

        cursor.close();
        db.close();

        return servicos;
    }

    public ArrayList<Servico> buscarFechadas(){
        SQLiteDatabase db = getReadableDatabase();

        ArrayList<Servico> servicos = new ArrayList<>();

        String sql = "select * from " + Tabela.NOME_TABELA + " where "+ Tabela.CAMPO_STATUS + " = ? order by " + Tabela.CAMPO_ID;

        Cursor cursor = db.rawQuery(sql, new String[]{Servico.Status.FECHADO.toString()});

        while (cursor.moveToNext()){
            Servico s = new Servico();

            s.setId(cursor.getLong(cursor.getColumnIndex(Tabela.CAMPO_ID)));
            s.setDataAbertura(cursor.getLong(cursor.getColumnIndex(Tabela.CAMPO_DATAABERTURA)));
            s.setDataAvaliacao(cursor.getLong(cursor.getColumnIndex(Tabela.CAMPO_DATAAVALIACAO)));
            s.setDataFechamento(cursor.getLong(cursor.getColumnIndex(Tabela.CAMPO_DATAFECHAMENTO)));
            s.setNumero(cursor.getString(cursor.getColumnIndex(Tabela.CAMPO_NUMERO)));
            s.setDescricao(cursor.getString(cursor.getColumnIndex(Tabela.CAMPO_DESCRICAO)));
            s.setPrecoAvaliado(cursor.getDouble(cursor.getColumnIndex(Tabela.CAMPO_PRECOAVALIADO)));
            s.setPrecoFinal(cursor.getDouble(cursor.getColumnIndex(Tabela.CAMPO_PRECOFINAL)));
            s.setAcrescimo(cursor.getDouble(cursor.getColumnIndex(Tabela.CAMPO_ACRESCIMO)));
            s.setDesconto(cursor.getDouble(cursor.getColumnIndex(Tabela.CAMPO_DESCONTO)));
            s.setTipo(cursor.getString(cursor.getColumnIndex(Tabela.CAMPO_TIPO)));
            s.setStatus(cursor.getString(cursor.getColumnIndex(Tabela.CAMPO_STATUS)));

            servicos.add(s);
        }

        cursor.close();
        db.close();

        return servicos;
    }
    public class Tabela{
        public static final String NOME_TABELA = "Servico";
        public static final String CAMPO_ID = "_id";
        public static final String CAMPO_DATAABERTURA = "dataAbertura";
        public static final String CAMPO_DATAAVALIACAO = "dataAvaliacao";
        public static final String CAMPO_DATAFECHAMENTO = "dataFechamento";
        public static final String CAMPO_NUMERO = "numero";
        public static final String CAMPO_DESCRICAO = "descricao";
        public static final String CAMPO_PRECOAVALIADO = "precoAvaliado";
        public static final String CAMPO_PRECOFINAL = "precoFinal";
        public static final String CAMPO_ACRESCIMO = "acrescimo";
        public static final String CAMPO_DESCONTO = "desconto";
        public static final String CAMPO_TIPO = "tipo";
        public static final String CAMPO_STATUS = "status";
    }

    public static void createTable(SQLiteDatabase db){
        String sql = "CREATE TABLE "+ Tabela.NOME_TABELA + " ( " +
                Tabela.CAMPO_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Tabela.CAMPO_DATAABERTURA + " INTEGER NOT NULL, " +
                Tabela.CAMPO_DATAAVALIACAO + " INTEGER, " +
                Tabela.CAMPO_DATAFECHAMENTO + " INTEGER, " +
                Tabela.CAMPO_NUMERO + " TEXT NOT NULL, " +
                Tabela.CAMPO_DESCRICAO + " TEXT NOT NULL, " +
                Tabela.CAMPO_PRECOAVALIADO + " REAL, " +
                Tabela.CAMPO_PRECOFINAL + " REAL, " +
                Tabela.CAMPO_ACRESCIMO + " REAL, " +
                Tabela.CAMPO_DESCONTO + " REAL, " +
                Tabela.CAMPO_TIPO + " TEXT NOT NULL, " +
                Tabela.CAMPO_STATUS + " TEXT NOT NULL, " +
                "UNIQUE("+ Tabela.CAMPO_NUMERO + "));";
        db.execSQL(sql);
    }
}
