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

public class ItemNFDAO implements GenericDAO<ItemNF> {

    //Variavel para abrir a conexão com BD
    private SQLiteOpenHelper openHelper;

    //Base de Dados
    private SQLiteDatabase db;

    //Nome da tabela
    private String nomeTabela = "ITEMNF";

    //Nome das colunas da tabela
    private String[]colunas = {"NR_ITEM_NOTAFISCAL", "NR_NOTAFISCAL", "CD_PRODUTO", "VL_UNITITEM", "VL_DESCONTO", "VL_SUBTOTAL", "QT_PRODUTO"};

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
    @Override
    public long insert(ItemNF obj) {
        try {
            ContentValues valores = new ContentValues();
            valores.put(colunas[0], obj.getNrItemNotaFiscal());
            valores.put(colunas[1], obj.getNotaFiscal().getNrNotaFiscal());
            valores.put(colunas[2], obj.getProduto().getCdProduto());
            valores.put(colunas[3], obj.getVlUnitItem());
            valores.put(colunas[4], obj.getVlDesconto());
            valores.put(colunas[5], obj.getVlSubTotal());
            valores.put(colunas[6], obj.getQtProduto());

            return db.insert(nomeTabela, null, valores);
        } catch (SQLException ex) {
            Log.e("ERRO","ItemNFDAO.insert():" +ex.getMessage());
        }
        return 0;
    }

    @Override
    public long update(ItemNF obj) {
        try {
            ContentValues valores = new ContentValues();
            valores.put(colunas[0], obj.getNrItemNotaFiscal());
            valores.put(colunas[1], obj.getNotaFiscal().getNrNotaFiscal());
            valores.put(colunas[2], obj.getProduto().getCdProduto());
            valores.put(colunas[3], obj.getVlUnitItem());
            valores.put(colunas[4], obj.getVlDesconto());
            valores.put(colunas[5], obj.getVlSubTotal());
            valores.put(colunas[6], obj.getQtProduto());

            String[] identificador = {String.valueOf(obj.getNrItemNotaFiscal())};

            return db.update(nomeTabela, valores, colunas[0] + " = ?", identificador);
        } catch (SQLException ex) {
            Log.e("ERRO","ItemNFDAO.update():" +ex.getMessage());
        }
        return 0;
    }
    @Override
    public long delete(ItemNF obj) {
        try {
            String[] identificador = {String.valueOf(obj.getNrItemNotaFiscal())};
            return db.delete(nomeTabela, colunas[0] + " = ?", identificador);
        } catch (SQLException ex) {
            Log.e("ERRO","ItemNFDAO.delete():" +ex.getMessage());
        }
        return 0;
    }
    @Override
    public ArrayList<ItemNF> getAll() {
        ArrayList<ItemNF> lista = new ArrayList<>();
        try {
            //Executa a consulta
            Cursor cursor = db.query(nomeTabela, colunas, null, null, null, null, colunas[0]);
            //Percorre o cursor
            if (cursor.moveToFirst()) {
                do {
                    ItemNF itemNF = new ItemNF();

                    itemNF.setNrItemNotaFiscal(cursor.getInt(0));

                    // Procurando e setando a Nota Fiscal pelo código no banco de dados
                    int codNotaFiscal = cursor.getInt(1);
                    NotaFiscal notaFiscal = NotaFiscalDAO.getInstancia(context).getById(codNotaFiscal);
                    itemNF.setNotaFiscal(notaFiscal);

                    // Procurando e setando o produto pelo código no banco de dados
                    int codProduto = cursor.getInt(2);
                    Produto produto = ProdutoDAO.getInstancia(context).getById(codProduto);
                    itemNF.setProduto(produto);

                    itemNF.setVlUnitItem(cursor.getDouble(3));
                    itemNF.setVlDesconto(cursor.getDouble(4));
                    itemNF.setVlSubTotal(cursor.getDouble(5));
                    itemNF.setQtProduto(cursor.getInt(6));

                    lista.add(itemNF);
                } while (cursor.moveToNext());
            }
            return lista;
        } catch (SQLException ex) {
            Log.e("ERRO","ItemNFDAO.getAll():" +ex.getMessage());
        }
        return lista;
    }
    @Override
    public ItemNF getById(int id) {
        ItemNF itemNF = new ItemNF();
        try {
            String[] identificador = {String.valueOf(id)};
            Cursor cursor = db.query(nomeTabela, colunas, colunas[0] + " = ?", identificador, null, null, null);
            if (cursor.moveToFirst()) {

                itemNF.setNrItemNotaFiscal(cursor.getInt(0));

                // Procurando e setando a Nota Fiscal pelo código no banco de dados
                int codNotaFiscal = cursor.getInt(1);
                NotaFiscal notaFiscal = NotaFiscalDAO.getInstancia(context).getById(codNotaFiscal);
                itemNF.setNotaFiscal(notaFiscal);

                // Procurando e setando o produto pelo código no banco de dados
                int codProduto = cursor.getInt(2);
                Produto produto = ProdutoDAO.getInstancia(context).getById(codProduto);
                itemNF.setProduto(produto);

                itemNF.setVlUnitItem(cursor.getDouble(3));
                itemNF.setVlDesconto(cursor.getDouble(4));
                itemNF.setVlSubTotal(cursor.getDouble(5));
                itemNF.setQtProduto(cursor.getInt(6));
            }
            return itemNF;
        } catch (SQLException ex) {
            Log.e("ERRO","ItemNFDAO.getById():" +ex.getMessage());
        }
        return null;
    }

    public int getProximoCodigo(){
        try {
            Cursor cursor = db.rawQuery("SELECT MAX(" + colunas[0] + ") FROM "+ nomeTabela, null);
            if (cursor.moveToFirst()) {
                return cursor.getInt(0) + 1;
            }
        }catch (SQLException ex){
            Log.e("ERRO","ItemNFDAO.getProximoCodigo():" +ex.getMessage());
        }
        return 0;
    }

    public ArrayList<ItemNF> getByListNome(String nrItemNotaFiscal){
        ArrayList<ItemNF> lista = new ArrayList<>();
        nrItemNotaFiscal += "%";
        try {
            String[] identificador = {nrItemNotaFiscal};
            Cursor cursor = db.query(nomeTabela, colunas, colunas[0] + " LIKE ?", identificador, null, null, colunas[1]);
            if (cursor.moveToFirst()) {
                do {
                    ItemNF itemNF = new ItemNF();
                    itemNF.setNrItemNotaFiscal(cursor.getInt(0));

                    // Procurando e setando a Nota Fiscal pelo código no banco de dados
                    int codNotaFiscal = cursor.getInt(1);
                    NotaFiscal notaFiscal = NotaFiscalDAO.getInstancia(context).getById(codNotaFiscal);
                    itemNF.setNotaFiscal(notaFiscal);

                    // Procurando e setando o produto pelo código no banco de dados
                    int codProduto = cursor.getInt(2);
                    Produto produto = ProdutoDAO.getInstancia(context).getById(codProduto);
                    itemNF.setProduto(produto);

                    itemNF.setVlUnitItem(cursor.getDouble(3));
                    itemNF.setVlDesconto(cursor.getDouble(4));
                    itemNF.setVlSubTotal(cursor.getDouble(5));
                    itemNF.setQtProduto(cursor.getInt(6));


                    lista.add(itemNF);
                } while (cursor.moveToNext());
            }
            return lista;
        } catch (SQLException ex) {
            Log.e("ERRO","ItemNFDAO.getByListNome():" +ex.getMessage());
        }
        return lista;
    }

}
