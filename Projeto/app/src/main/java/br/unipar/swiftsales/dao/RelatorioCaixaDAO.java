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
import br.unipar.swiftsales.model.RelatorioCaixa;

public class RelatorioCaixaDAO {
    //Variavel para abrir a conexão com BD
    private SQLiteOpenHelper openHelper;

    //Base de Dados
    private SQLiteDatabase bd;

    //Nome da tabela
    private String nomeTabela = "CAIXA, NOTAFISCAL";

    //Nome das colunas da tabela
    private String[] colunas = {"CD_CAIXA", "QT_TOTVENDA", "VL_TOTVENDA", "DT_CAIXA"};

    private Context context;

    private static RelatorioCaixaDAO instancia;

    public RelatorioCaixaDAO(Context context) {
        this.context = context;
        //Abrir uma conexão da BD
        openHelper = new SQLiteDataHelper(this.context, "UNIPAR_BD",
                null, 2);
        //Carrega a BD e da permissão para escrever na tabela
        bd = openHelper.getWritableDatabase();
    }

    public static RelatorioCaixaDAO getInstancia(Context context) {
        if (instancia == null) {
            return instancia = new RelatorioCaixaDAO(context);
        } else {
            return instancia;
        }
    }
    public ArrayList<RelatorioCaixa> getRelatorioCaixa(String dataInicial, String dataFinal) {
        ArrayList<RelatorioCaixa> listaRelatorioCaixa = new ArrayList<>();
        try {
            Cursor cursor = bd.rawQuery("SELECT CAIXA.NR_CAIXA, COUNT(NOTAFISCAL.NR_NOTAFISCAL) AS QT_TOTVENDA, SUM(NOTAFISCAL.VL_NOTAFISCAL)  AS VL_SALDO, NOTAFISCAL.DT_EMISSAO FROM CAIXA, NOTAFISCAL WHERE CAIXA.NR_CAIXA = NOTAFISCAL.NR_CAIXA AND DATE(NOTAFISCAL.DT_EMISSAO) BETWEEN '" + dataInicial + "' AND '" + dataFinal + "' GROUP BY CAIXA.NR_CAIXA, DATE(NOTAFISCAL.DT_EMISSAO)", null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    RelatorioCaixa relatorioCaixa = new RelatorioCaixa();
                    Caixa caixa = new Caixa();
                    int numeroCaixa = cursor.getInt(0);
                    caixa = CaixaDAO.getInstancia(context).getById(numeroCaixa);
                    relatorioCaixa.setCaixa(caixa);
                    relatorioCaixa.setQtVendas(cursor.getInt(1));
                    relatorioCaixa.setVlSaldo(cursor.getDouble(2) - caixa.getVlInicial());
                    listaRelatorioCaixa.add(relatorioCaixa);
                } while (cursor.moveToNext());
            }
        } catch (SQLException e) {
            Log.e("Erro ao buscar dados do relatório. Erro:", e.getMessage());
        }
        return listaRelatorioCaixa;
    }




}
