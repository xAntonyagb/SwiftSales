package br.unipar.swiftsales.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import br.unipar.swiftsales.helper.SQLiteDataHelper;

public class CaixaDAO {

    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase bd;
    private Context context;
    private static CaixaDAO instancia;

    public CaixaDAO(Context context) {
        this.context = context;
        //Abrir uma conexão da BD
        openHelper = new SQLiteDataHelper(this.context, "UNIPAR_BD",
                null, 1);
        //Carrega a BD e da permissão para escrever na tabela
        bd = openHelper.getWritableDatabase();
    }

    public static CaixaDAO getInstancia(Context context) {
        if (instancia == null) {
            return instancia = new CaixaDAO(context);
        } else {
            return instancia;
        }
    }
}
