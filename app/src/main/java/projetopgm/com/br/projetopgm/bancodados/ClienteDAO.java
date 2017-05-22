package projetopgm.com.br.projetopgm.bancodados;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import projetopgm.com.br.projetopgm.base.Cliente;

public class ClienteDAO extends BancoDados{

    public ClienteDAO(Context context) {
        super(context);
    }

    public static void createTable(SQLiteDatabase db){
        String sql = "CREATE TABLE "+ Tabela.NOME_TABELA + " ( " +
                Tabela.CAMPO_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Tabela.CAMPO_NOME + " TEXT NOT NULL, " +
                Tabela.CAMPO_EMAIL + " TEXT NOT NULL, " +
                Tabela.CAMPO_APLICATIVO_TOKEN + " TEXT, " +
                "UNIQUE("+ Tabela.CAMPO_EMAIL + "));";
        db.execSQL(sql);
    }

    public void salvar(Cliente cliente){
        if(cliente.getId() == null || cliente.getId() <= 0)
            inserir(cliente);
        else
            atualizar(cliente);
    }

    private long inserir(Cliente cliente) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(Tabela.CAMPO_NOME, cliente.getNome());
        valores.put(Tabela.CAMPO_EMAIL, cliente.getEmail());
        valores.put(Tabela.CAMPO_APLICATIVO_TOKEN, cliente.getAplicativoToken());

        long id = db.insert(Tabela.NOME_TABELA, null, valores);
        if(id != -1)
            cliente.setId(id);

        db.close();

        return id;
    }

    private int atualizar(Cliente cliente) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(Tabela.CAMPO_NOME, cliente.getNome());
        valores.put(Tabela.CAMPO_EMAIL, cliente.getEmail());
        valores.put(Tabela.CAMPO_APLICATIVO_TOKEN, cliente.getAplicativoToken());

        int linhas = db.update(Tabela.NOME_TABELA,
                valores,
                Tabela.CAMPO_ID + " = ?",
                new String[]{String.valueOf(cliente.getId())});

        db.close();

        return linhas;
    }

    public ArrayList<Cliente> buscarTodos(){
        SQLiteDatabase db = getReadableDatabase();

        ArrayList<Cliente> clientes = new ArrayList<>();

        String sql = "select * from " + Tabela.NOME_TABELA + " order by " + Tabela.CAMPO_ID;

        Cursor cursor = db.rawQuery(sql, null);

        while (cursor.moveToNext()){
            Cliente c = new Cliente();

            c.setId(cursor.getLong(cursor.getColumnIndex(Tabela.CAMPO_ID)));
            c.setNome(cursor.getString(cursor.getColumnIndex(Tabela.CAMPO_NOME)));
            c.setEmail(cursor.getString(cursor.getColumnIndex(Tabela.CAMPO_EMAIL)));
            c.setAplicativoToken(cursor.getString(cursor.getColumnIndex(Tabela.CAMPO_APLICATIVO_TOKEN)));

            clientes.add(c);
        }

        cursor.close();
        db.close();

        return clientes;
    }

    public Cliente buscarPorEmail(String email){
        SQLiteDatabase db = getReadableDatabase();

        String sql = "select * from " + Tabela.NOME_TABELA + " where "+ Tabela.CAMPO_EMAIL + " = ? order by " + Tabela.CAMPO_ID;

        Cursor cursor = db.rawQuery(sql, new String[]{email});

        Cliente cliente = null;
        if (cursor.moveToNext()){
            cliente = new Cliente();

            cliente.setId(cursor.getLong(cursor.getColumnIndex(Tabela.CAMPO_ID)));
            cliente.setNome(cursor.getString(cursor.getColumnIndex(Tabela.CAMPO_NOME)));
            cliente.setEmail(cursor.getString(cursor.getColumnIndex(Tabela.CAMPO_EMAIL)));
            cliente.setAplicativoToken(cursor.getString(cursor.getColumnIndex(Tabela.CAMPO_APLICATIVO_TOKEN)));
        }

        cursor.close();
        db.close();

        return cliente;
    }

    public class Tabela{
        public static final String NOME_TABELA = "Cliente";
        public static final String CAMPO_ID = "_id";
        public static final String CAMPO_NOME = "nome";
        public static final String CAMPO_EMAIL = "email";
        public static final String CAMPO_APLICATIVO_TOKEN = "aplicativoToken";
    }
}
