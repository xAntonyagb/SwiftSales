package br.unipar.swiftsales.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import br.unipar.swiftsales.helper.SQLiteDataHelper;
import br.unipar.swiftsales.model.Caixa;
import br.unipar.swiftsales.model.RelatorioCaixa;
import br.unipar.swiftsales.utils.DataAtual;

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
            String sql = "SELECT CAIXA.NR_CAIXA, COUNT(NOTAFISCAL.NR_NOTAFISCAL) AS QT_TOTVENDA, SUM(NOTAFISCAL.VL_NOTAFISCAL)  AS VL_SALDO, SUBSTR(CAST(NOTAFISCAL.DT_EMISSAO AS CHAR(10)), 7, 4) || '-' || SUBSTR(CAST(NOTAFISCAL.DT_EMISSAO AS CHAR(10)), 4, 2) || '-' || SUBSTR(CAST(NOTAFISCAL.DT_EMISSAO AS CHAR(10)), 1, 2) AS DT_EMISSAO  FROM CAIXA, NOTAFISCAL WHERE CAIXA.NR_CAIXA = NOTAFISCAL.NR_CAIXA  GROUP BY CAIXA.NR_CAIXA, SUBSTR(CAST(NOTAFISCAL.DT_EMISSAO AS CHAR(10)), 7, 4) || '-' || SUBSTR(CAST(NOTAFISCAL.DT_EMISSAO AS CHAR(10)), 4, 2) || '-' || SUBSTR(CAST(NOTAFISCAL.DT_EMISSAO AS CHAR(10)), 1, 2)";
            System.out.println(sql);
            Cursor cursor = bd.rawQuery(sql, null);
            if (cursor.moveToFirst()) {
                do {
                    RelatorioCaixa relatorioCaixa = new RelatorioCaixa();
                    Caixa caixa = new Caixa();
                    int numeroCaixa = cursor.getInt(0);
                    caixa = CaixaDAO.getInstancia(context).getById(numeroCaixa);
                    relatorioCaixa.setCaixa(caixa);
                    relatorioCaixa.setQtVendas(cursor.getInt(1));
                    relatorioCaixa.setVlSaldo(cursor.getDouble(2) + caixa.getVlInicial());
                    relatorioCaixa.setData(cursor.getString(3));
                    listaRelatorioCaixa.add(relatorioCaixa);
                } while (cursor.moveToNext());
            }
        } catch (SQLException e) {
            Log.e("Erro ao buscar dados do relatório. Erro:", e.getMessage());
        }
        return listaRelatorioCaixa;
    }




}
