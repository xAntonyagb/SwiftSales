package br.unipar.swiftsales.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import br.unipar.swiftsales.enums.FormaPagamentoEnum;
import br.unipar.swiftsales.helper.SQLiteDataHelper;
import br.unipar.swiftsales.model.Cliente;
import br.unipar.swiftsales.model.ItemNF;
import br.unipar.swiftsales.model.NotaFiscal;
import br.unipar.swiftsales.model.Produto;
import br.unipar.swiftsales.model.Vendedor;

public class ItemNFDAO {

    //Variavel para abrir a conexão com BD
    private SQLiteOpenHelper openHelper;

    //Base de Dados
    private SQLiteDatabase db;

    //Nome da tabela
    private String nomeTabela = "ITEMNF";

    //Nome das colunas da tabela

    private String[] colunas = {"NR_NOTAFISCAL", "CD_PRODUTO", "VL_UNITITEM", "VL_DESCONTO", "VL_SUBTOTAL", "QT_PRODUTO"};

    private Context context;

    private static ItemNFDAO instancia;

    public ItemNFDAO(Context context) {
        this.context = context;
        //Abrir uma conexão da BD
        openHelper = new SQLiteDataHelper(this.context, "UNIPAR_BD",
                null, 1);
        //Carrega a BD e da permissão para escrever na tabela
        db = openHelper.getWritableDatabase();
    }
    public static ItemNFDAO getInstancia(Context context){
        if(instancia == null){
            return instancia = new ItemNFDAO(context);
        }else{
            return instancia;
        }
    }

    public long insert(ItemNF obj) {
        try {
            ContentValues valores = new ContentValues();
            valores.put(colunas[0], obj.getNrNotaFiscal());
            valores.put(colunas[1], obj.getProduto().getCdProduto());
            valores.put(colunas[2], obj.getVlUnitItem());
            valores.put(colunas[3], obj.getVlDesconto());
            valores.put(colunas[4], obj.getVlSubTotal());
            valores.put(colunas[5], obj.getQtProduto());

            return db.insert(nomeTabela, null, valores);
        } catch (SQLException ex) {
            Log.e("ERRO","ItemNFDAO.insert():" +ex.getMessage());
        }
        return 0;
    }

    public long update(ItemNF obj) {
        try {
            ContentValues valores = new ContentValues();
            valores.put(colunas[0], obj.getNrNotaFiscal());
            valores.put(colunas[1], obj.getProduto().getCdProduto());
            valores.put(colunas[2], obj.getVlUnitItem());
            valores.put(colunas[3], obj.getVlDesconto());
            valores.put(colunas[4], obj.getVlSubTotal());
            valores.put(colunas[5], obj.getQtProduto());

            String[] identificador = {String.valueOf(obj.getNrNotaFiscal()), String.valueOf(obj.getProduto().getCdProduto())};

            return db.update(nomeTabela, valores, colunas[0] + " = ? " + colunas[1] + " = ? ", identificador);
        } catch (SQLException ex) {
            Log.e("ERRO","ItemNFDAO.update():" +ex.getMessage());
        }
        return 0;
    }

    public long delete(ItemNF obj) {
        try {
            String[] identificador = {String.valueOf(obj.getNrNotaFiscal()), String.valueOf(obj.getProduto().getCdProduto())};
            return db.delete(nomeTabela, colunas[0] + " = ? " + colunas[1] + " = ? ", identificador);
        } catch (SQLException ex) {
            Log.e("ERRO","ItemNFDAO.delete():" +ex.getMessage());
        }
        return 0;
    }


    public ItemNF getById(ItemNF obj) {
        ItemNF itemNF = new ItemNF();
        try {
            String[] identificador = {String.valueOf(obj.getNrNotaFiscal()), String.valueOf(obj.getProduto().getCdProduto())};
            Cursor cursor = db.query(nomeTabela, colunas, colunas[0] + " = ? " + colunas[1] + " = ? ", identificador, null, null, null);
            if (cursor.moveToFirst()) {

                itemNF.setNrNotaFiscal(cursor.getInt(0));

                // Procurando e setando o produto pelo código no banco de dados
                int codProduto = cursor.getInt(1);
                Produto produto = ProdutoDAO.getInstancia(context).getById(codProduto);
                itemNF.setProduto(produto);

                itemNF.setVlUnitItem(cursor.getDouble(2));
                itemNF.setVlDesconto(cursor.getDouble(3));
                itemNF.setVlSubTotal(cursor.getDouble(4));
                itemNF.setQtProduto(cursor.getInt(5));
            }
            return itemNF;
        } catch (SQLException ex) {
            Log.e("ERRO","ItemNFDAO.getById():" +ex.getMessage());
        }
        return null;
    }

}
