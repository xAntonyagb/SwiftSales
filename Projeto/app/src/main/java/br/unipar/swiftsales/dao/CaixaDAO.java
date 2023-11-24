package br.unipar.swiftsales.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import br.unipar.swiftsales.enums.StatusCaixaEnum;
import br.unipar.swiftsales.helper.SQLiteDataHelper;
import br.unipar.swiftsales.model.Caixa;

public class CaixaDAO {

    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase bd;
    private Context context;
    private static CaixaDAO instancia;


    private String nomeTabela = "CAIXA";

    private String[] colunas = {"NR_CAIXA", "VL_INICIAL", "VL_FINAL", "DT_CAIXA", "DATA_FECHAMENTO"};

    public CaixaDAO(Context context) {
        this.context = context;
        //Abrir uma conexão da BD
        openHelper = new SQLiteDataHelper(this.context, "UNIPAR_BD",
                null, 2);
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
    public long update(Caixa caixa) {
        ContentValues valores = new ContentValues();
        valores.put("VL_INICIAL", caixa.getVlInicial());
        valores.put("VL_FINAL", caixa.getVlFinal());
        valores.put("DT_CAIXA", caixa.getDtCaixa());
        valores.put("ST_CAIXA", caixa.getStCaixa().descricao.replace("A","ABERTO").replace("F","FECHADO"));

        String[] identificador = {String.valueOf(caixa.getNrCaixa())};

        try {
            return bd.update("CAIXA", valores, "NR_CAIXA = ?", identificador);
        } catch (SQLException ex) {
            Log.e("ERRO", "CaixaDAO.update():" + ex.getMessage());
            return -1;
        }
    }
    public long insert(Caixa obj) {

            ContentValues valores = new ContentValues();
            valores.put(colunas[0], obj.getNrCaixa());
            valores.put(colunas[1], obj.getVlInicial());
            valores.put(colunas[2], obj.getVlFinal());
            valores.put(colunas[3], obj.getDtCaixa());
            

            return bd.insert(nomeTabela, null, valores);

    }
    public long delete(Caixa caixa) {
        String[] identificador = {String.valueOf(caixa.getNrCaixa())};

        try {
            return bd.delete("CAIXA", "NR_CAIXA = ?", identificador);
        } catch (SQLException ex) {
            Log.e("ERRO", "CaixaDAO.delete():" + ex.getMessage());
            return -1;
        }
    }
    public ArrayList<Caixa> getAll() {
        ArrayList<Caixa> lista = new ArrayList<>();
        try {
            Cursor cursor = bd.query("CAIXA", null, null, null, null, null, null);
            if (cursor.moveToFirst()) {
                do {
                    Caixa caixa = new Caixa();
                    caixa.setNrCaixa(cursor.getInt(cursor.getColumnIndex("NR_CAIXA")));
                    caixa.setVlInicial(cursor.getDouble(cursor.getColumnIndex("VL_INICIAL")));
                    caixa.setVlFinal(cursor.getDouble(cursor.getColumnIndex("VL_FINAL")));
                    caixa.setDtCaixa(cursor.getString(cursor.getColumnIndex("DT_CAIXA")));
                    caixa.setStCaixa(StatusCaixaEnum.valueOf(cursor.getString(cursor.getColumnIndex("ST_CAIXA"))));
                    lista.add(caixa);
                } while (cursor.moveToNext());
            }
        } catch (SQLException ex) {
            Log.e("ERRO", "CaixaDAO.getAll():" + ex.getMessage());
        }
        return lista;
    }
    public Caixa getById(int nrCaixa) {
        Caixa caixa = null;
        try {
            String[] identificador = {String.valueOf(nrCaixa)};
            Cursor cursor = bd.query("CAIXA", null, "NR_CAIXA = ?", identificador, null, null, null);
            if (cursor.moveToFirst()) {
                caixa = new Caixa();
                caixa.setNrCaixa(cursor.getInt(cursor.getColumnIndex("NR_CAIXA")));
                caixa.setVlInicial(cursor.getDouble(cursor.getColumnIndex("VL_INICIAL")));
                caixa.setVlFinal(cursor.getDouble(cursor.getColumnIndex("VL_FINAL")));
                caixa.setDtCaixa(cursor.getString(cursor.getColumnIndex("DT_CAIXA")));
                caixa.setStCaixa(StatusCaixaEnum.valueOf(cursor.getString(cursor.getColumnIndex("ST_CAIXA"))));
            }
        } catch (SQLException ex) {
            Log.e("ERRO", "CaixaDAO.getById():" + ex.getMessage());
        }
        return caixa;
    }
}
