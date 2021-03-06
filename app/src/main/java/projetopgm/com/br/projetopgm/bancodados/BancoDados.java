package projetopgm.com.br.projetopgm.bancodados;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BancoDados extends SQLiteOpenHelper{

    private static final String NOME_BANCO = "ProjetoPGM";
    private static final int VERSAO_BANCO = 4;

    public BancoDados(Context context) {
        super(context, NOME_BANCO, null, VERSAO_BANCO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        ServicoDAO.createTable(db);
        ClienteDAO.createTable(db);
        FotoDAO.createTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ServicoDAO.Tabela.NOME_TABELA);
        db.execSQL("DROP TABLE IF EXISTS " + ClienteDAO.Tabela.NOME_TABELA);
        db.execSQL("DROP TABLE IF EXISTS " + FotoDAO.Tabela.NOME_TABELA);

        onCreate(db);
    }
}
