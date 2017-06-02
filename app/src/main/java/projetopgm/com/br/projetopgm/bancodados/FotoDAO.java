package projetopgm.com.br.projetopgm.bancodados;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import projetopgm.com.br.projetopgm.base.Foto;
import projetopgm.com.br.projetopgm.base.Servico;

public class FotoDAO extends BancoDados{

    public FotoDAO(Context context) {
        super(context);
    }

    public static void createTable(SQLiteDatabase db){
        String sql = "CREATE TABLE "+ Tabela.NOME_TABELA + " ( " +
                Tabela.CAMPO_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Tabela.CAMPO_NOME + " TEXT, " +
                Tabela.CAMPO_ARQUIVO + " TEXT NOT NULL, " +
                Tabela.CAMPO_SERVICO_ID + " INTEGER NOT NULL, " +
                "UNIQUE("+ Tabela.CAMPO_ARQUIVO + "));";
        db.execSQL(sql);
    }

    public void salvar(Foto foto){
        if(foto.getId() == null || foto.getId() <= 0)
            inserir(foto);
        else
            atualizar(foto);
    }

    private long inserir(Foto foto) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(Tabela.CAMPO_NOME, foto.getNome());
        valores.put(Tabela.CAMPO_ARQUIVO, foto.getArquivo());
        valores.put(Tabela.CAMPO_SERVICO_ID, foto.getServicoId());

        long id = db.insert(Tabela.NOME_TABELA, null, valores);
        if(id != -1)
            foto.setId(id);

        db.close();

        return id;
    }

    private int atualizar(Foto foto) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(Tabela.CAMPO_NOME, foto.getNome());
        valores.put(Tabela.CAMPO_ARQUIVO, foto.getArquivo());

        int linhas = db.update(Tabela.NOME_TABELA,
                valores,
                Tabela.CAMPO_ID + " = ?",
                new String[]{String.valueOf(foto.getId())});

        db.close();

        return linhas;
    }

    public ArrayList<Foto> buscarPorServico(Servico servico){
        SQLiteDatabase db = getReadableDatabase();

        ArrayList<Foto> fotos = new ArrayList<>();

        String sql = "select * from " + Tabela.NOME_TABELA + " where "+ Tabela.CAMPO_SERVICO_ID + " = ? order by " + Tabela.CAMPO_ID;

        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(servico.getId())});

        while (cursor.moveToNext()){
            Foto c = new Foto();

            c.setId(cursor.getLong(cursor.getColumnIndex(Tabela.CAMPO_ID)));
            c.setNome(cursor.getString(cursor.getColumnIndex(Tabela.CAMPO_NOME)));
            c.setArquivo(cursor.getString(cursor.getColumnIndex(Tabela.CAMPO_ARQUIVO)));
            c.setServicoId(servico.getId());

            fotos.add(c);
        }

        cursor.close();
        db.close();

        return fotos;
    }

    public class Tabela{
        public static final String NOME_TABELA = "Foto";
        public static final String CAMPO_ID = "_id";
        public static final String CAMPO_NOME = "nome";
        public static final String CAMPO_ARQUIVO = "arquivo";
        public static final String CAMPO_SERVICO_ID = "servico_id";
    }
}
