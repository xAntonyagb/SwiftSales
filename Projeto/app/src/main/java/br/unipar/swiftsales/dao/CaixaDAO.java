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
import br.unipar.swiftsales.model.RelatorioCaixa;

public class CaixaDAO {

    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase bd;
    private Context context;
    private static CaixaDAO instancia;


    private String nomeTabela = "CAIXA";

    private String[] colunas = {"NR_CAIXA", "VL_INICIAL", "VL_FINAL", "DT_CAIXA", "ST_CAIXA"};

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
        valores.put("ST_CAIXA", caixa.getStCaixa().descricao);

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
            valores.put(colunas[4], obj.getStCaixa().descricao);
            

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
            Cursor cursor = bd.query(nomeTabela, colunas, null, null, null, null, colunas[0]);
            if (cursor.moveToFirst()) {
                do {
                    Caixa caixa = new Caixa();
                    caixa.setNrCaixa(cursor.getInt(0));
                    caixa.setVlInicial(cursor.getDouble(1));
                    caixa.setVlFinal(cursor.getDouble(2));
                    caixa.setDtCaixa(cursor.getString(3));
                    caixa.setStCaixa(StatusCaixaEnum.valueOf(cursor.getString(4)));
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
                caixa.setNrCaixa(cursor.getInt(0));
                caixa.setVlInicial(cursor.getDouble(1));
                caixa.setVlFinal(cursor.getDouble(2));
                caixa.setDtCaixa(cursor.getString(3));
                caixa.setStCaixa(StatusCaixaEnum.valueOf(cursor.getString(4)));
            }
        } catch (SQLException ex) {
            Log.e("ERRO", "CaixaDAO.getById():" + ex.getMessage());
        }
        return caixa;
    }
    public boolean isCaixaAberto() {
        boolean caixaAberto = false;

        Cursor cursor = bd.rawQuery("SELECT ST_CAIXA FROM CAIXA WHERE NR_CAIXA = 1", null);
        if (cursor.moveToFirst()) {
            String statusCaixa = cursor.getString(0);
            caixaAberto = statusCaixa.equals("A");
        }

        cursor.close();
        return caixaAberto;
    }
    public double getValorInicial() {
        double valorInicial = -1;

        Cursor cursor = bd.rawQuery("SELECT VL_INICIAL FROM CAIXA LIMIT 1", null);
        if (cursor.moveToFirst()) {
            valorInicial = cursor.getDouble(0);
        }

        cursor.close();
        return valorInicial;
    }

    public void salvarValorInicial(double valorInicial) {
        ContentValues valores = new ContentValues();
        valores.put("VL_INICIAL", valorInicial);

        bd.update("CAIXA", valores, null, null);
    }
    public void atualizarStatusCaixa(Caixa caixa) {
        ContentValues values = new ContentValues();
        values.put("ST_CAIXA", caixa.getStCaixa().descricao.replace("A", "ABERTO").replace("F", "FECHADO"));

        String whereClause = "NR_CAIXA = ?";
        String[] whereArgs = {String.valueOf(caixa.getNrCaixa())};

        try {
            bd.update("CAIXA", values, whereClause, whereArgs);
        } catch (SQLException ex) {
            Log.e("ERRO", "CaixaDAO.atualizarStatusCaixa():" + ex.getMessage());
        }
    }


    public int getProximoCodigo(){
        try {
            Cursor cursor = bd.rawQuery("SELECT MAX(" + colunas[0] + ") FROM "+ nomeTabela, null);
            if (cursor.moveToFirst()) {
                return cursor.getInt(0) + 1;
            }
        }catch (SQLException ex){
            Log.e("ERRO","CaixaDAO.getProximoCodigo():" +ex.getMessage());
        }
        return 0;
    }

    public int getUltimoCodigo(){
        try {
            Cursor cursor = bd.rawQuery("SELECT MAX(" + colunas[0] + ") FROM "+ nomeTabela, null);
            if (cursor.moveToFirst()) {
                return cursor.getInt(0);
            }
        }catch (SQLException ex){
            Log.e("ERRO","CaixaDAO.getProximoCodigo():" +ex.getMessage());
        }
        return 0;
    }


    public double retornaSaldoFinal(int codCaixa) {
        double saldoFinal = 0;
        try {
            Cursor cursor = bd.rawQuery("SELECT CAIXA.NR_CAIXA, CAIXA.VL_INICIAL, SUM(NOTAFISCAL.VL_NOTAFISCAL) AS VL_ENTRADA FROM CAIXA, NOTAFISCAL WHERE CAIXA.NR_CAIXA = NOTAFISCAL.NR_CAIXA AND DATE(CAIXA.DT_CAIXA) = DATE(NOTAFISCAL.DT_EMISSAO) AND CAIXA.NR_CAIXA = "+ codCaixa +" GROUP BY CAIXA.NR_CAIXA, DATE(NOTAFISCAL.DT_EMISSAO)", null);

            if (cursor.moveToFirst()) {
                    saldoFinal = cursor.getDouble(2) + cursor.getDouble(1);
            }
        } catch (SQLException e) {
            Log.e("Erro ao buscar dados do relatório. Erro:", e.getMessage());
        }
        return saldoFinal;



    }

}
