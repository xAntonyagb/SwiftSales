package br.unipar.swiftsales.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import br.unipar.swiftsales.helper.SQLiteDataHelper;
import br.unipar.swiftsales.model.Caixa;

public class RelatorioDAO  {
    //Variavel para abrir a conexão com BD
    private SQLiteOpenHelper openHelper;

    //Base de Dados
    private SQLiteDatabase bd;

    //Nome da tabela
    private String nomeTabela = "CAIXA";

    //Nome das colunas da tabela
    private String[] colunas = {"CD_CAIXA", "CD_VENDEDOR", "ST_CAIXA", "VL_INICIAL", "VL_FINAL", "DT_CAIXA"};

    private Context context;

    private static RelatorioDAO instancia;

    public RelatorioDAO(Context context) {
        this.context = context;
        //Abrir uma conexão da BD
        openHelper = new SQLiteDataHelper(this.context, "UNIPAR_BD",
                null, 1);
        //Carrega a BD e da permissão para escrever na tabela
        bd = openHelper.getWritableDatabase();
    }

    public static RelatorioDAO getInstancia(Context context) {
        if (instancia == null) {
            return instancia = new RelatorioDAO(context);
        } else {
            return instancia;
        }
    }
    public ArrayList<Caixa> getByData(String dataInicial, String dataFinal, String status) {
        ArrayList<Caixa> listaCaixa = new ArrayList<>();
        try {
            if (dataInicial == null || dataInicial.isEmpty()) {
                dataInicial = "01/01/1900";
            }
            if (dataFinal == null || dataFinal.isEmpty()) {
                dataFinal = "31/12/9999";
            }
            if (status == null || status.isEmpty()) {
                status = "A";
            }
            String sql = "SELECT * FROM CAIXA WHERE DT_CAIXA BETWEEN DATE(?) AND DATE(?) AND ST_CAIXA = ?";
            String[] argumentos = {dataInicial, dataFinal, status};
            Cursor cursor = bd.rawQuery(sql, argumentos);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    Caixa caixa = new Caixa();
                    caixa.setNrCaixa(cursor.getInt(0));
                    caixa.setVlInicial(cursor.getDouble(1));
                    caixa.setVlFinal(cursor.getDouble(2));
                    caixa.setDtCaixa(cursor.getString(3));
                    caixa.setStCaixa(cursor.getString(4));
                    caixa.setVendedor(VendedorDAO.getInstancia(context).getById(cursor.getInt(5)));
                    listaCaixa.add(caixa);
                } while (cursor.moveToNext());
            }
        } catch (SQLException ex) {
            Log.e("ERRO", "RelatorioDAO.getByData(): " + ex.getMessage());
        }
        return listaCaixa;
    }



}
