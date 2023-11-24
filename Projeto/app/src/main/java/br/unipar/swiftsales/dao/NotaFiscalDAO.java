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

public class NotaFiscalDAO {

    //Variavel para abrir a conexão com BD
    private SQLiteOpenHelper openHelper;

    //Base de Dados
    private SQLiteDatabase db;

    //Nome da tabela
    private String nomeTabela = "NOTAFISCAL";

    //Nome das colunas da tabela
    private String[]colunas = {"NR_NOTAFISCAL", "VL_NOTAFISCAL","DT_EMISSAO", "NR_CHAVEACESSO", "CD_VENDEDOR", "CD_CLIENTE", "CD_FORMAPAGTO", "NR_CAIXA"};

    private Context context;

    private static NotaFiscalDAO instancia;

    public NotaFiscalDAO(Context context) {
        this.context = context;
        //Abrir uma conexão da BD
        openHelper = new SQLiteDataHelper(this.context, "UNIPAR_BD",
                null, 2);
        //Carrega a BD e da permissão para escrever na tabela
        db = openHelper.getWritableDatabase();
    }
    public static NotaFiscalDAO getInstancia(Context context){
        if(instancia == null){
            return instancia = new NotaFiscalDAO(context);
        }else{
            return instancia;
        }
    }

    public long insert(NotaFiscal obj) {
        try {
            ContentValues valores = new ContentValues();
            valores.put(colunas[0], obj.getNrNotaFiscal());
            valores.put(colunas[1], obj.getVlNotaFiscal());
            valores.put(colunas[2], obj.getDtEmissao());
            valores.put(colunas[3], obj.getNrChaveAcesso());
            valores.put(colunas[4], obj.getVendedor().getCdVendedor());
            valores.put(colunas[5], obj.getCliente().getCdCliente());
            valores.put(colunas[6], obj.getFormaPagamento().ordinal());
            valores.put(colunas[7], obj.getNrCaixa());


            return db.insert(nomeTabela, null, valores);
        } catch (SQLException ex) {
            Log.e("ERRO","NotaFiscalDAO.insert():" +ex.getMessage());
        }
        return 0;
    }

    public long insertTemp(NotaFiscal obj) {
        try {
            ContentValues valores = new ContentValues();
            valores.put(colunas[0], obj.getNrNotaFiscal());
            valores.put(colunas[2], obj.getDtEmissao());
            valores.put(colunas[7], obj.getNrCaixa());


            return db.insert(nomeTabela, null, valores);
        } catch (SQLException ex) {
            Log.e("ERRO","NotaFiscalDAO.insert():" +ex.getMessage());
        }
        return 0;
    }

    public long update(NotaFiscal obj) {
        try {
            ContentValues valores = new ContentValues();
            valores.put(colunas[0], obj.getNrNotaFiscal());
            valores.put(colunas[1], obj.getVlNotaFiscal());
            valores.put(colunas[2], obj.getDtEmissao());
            valores.put(colunas[3], obj.getNrChaveAcesso());
            valores.put(colunas[4], obj.getVendedor().getCdVendedor());
            if (obj.getCliente() != null) {
                if (obj.getCliente().getCdCliente() != 0) {
                    valores.put(colunas[5], obj.getCliente().getCdCliente());
                }
            }
            valores.put(colunas[6], obj.getFormaPagamento().ordinal());
            valores.put(colunas[7], obj.getNrCaixa());

            String[] identificador = {String.valueOf(obj.getNrNotaFiscal())};

            return db.update(nomeTabela, valores, colunas[0] + " = ?", identificador);
        } catch (SQLException ex) {
            Log.e("ERRO","NotaFiscalDAO.update():" +ex.getMessage());
        }
        return 0;
    }

    public long delete(int nrNotaFiscal) {
        try {
            String[] identificador = {String.valueOf(nrNotaFiscal)};
            return db.delete(nomeTabela, colunas[0] + " = ?", identificador);
        } catch (SQLException ex) {
            Log.e("ERRO","NotaFiscalDAO.delete():" +ex.getMessage());
        }
        return 0;
    }

    public ArrayList<ItemNF> getAllItensNota(int nrNotaFiscal) { //Retorna todos os itens dentro dessa nota fiscal
        ArrayList<ItemNF> lista = new ArrayList<>();
        try {
            //Executa a consulta no banco de dados procurando todas os itens da nota fiscal usando o código da nota fiscal
            String[] identificador = {String.valueOf(nrNotaFiscal)};
            String[] colunasQuerry = {"NR_NOTAFISCAL", "CD_PRODUTO", "VL_UNITITEM", "VL_DESCONTO", "VL_SUBTOTAL", "QT_PRODUTO"};

            Cursor cursor = db.query("ITEMNF", colunasQuerry, colunas[0] + " = ?", identificador, null, null, null);


            //Percorre o cursor
            if (cursor.moveToFirst()) {
                do {
                    ItemNF itemNF = new ItemNF();
                    itemNF.setNrNotaFiscal(cursor.getInt(0));

                    // Procurando e setando o produto pelo código no banco de dados
                    int codProduto = cursor.getInt(1);
                    Produto produto = ProdutoDAO.getInstancia(context).getById(codProduto);
                    itemNF.setProduto(produto);

                    itemNF.setVlUnitItem(cursor.getDouble(2));
                    itemNF.setVlDesconto(cursor.getDouble(3));
                    itemNF.setVlSubTotal(cursor.getDouble(4));
                    itemNF.setQtProduto(cursor.getInt(5));


                    lista.add(itemNF);
                } while (cursor.moveToNext());
            }
            return lista;
        } catch (SQLException ex) {
            Log.e("ERRO","NotaFiscalDAO.getAll():" +ex.getMessage());
        }
        return lista;
    }

    public NotaFiscal getById(int id) {
        NotaFiscal notaFiscal = new NotaFiscal();
        try {
            String[] identificador = {String.valueOf(id)};
            Cursor cursor = db.query(nomeTabela, colunas, colunas[0] + " = ?", identificador, null, null, null);
            if (cursor.moveToFirst()) {

                notaFiscal.setNrNotaFiscal(cursor.getInt(0));
                notaFiscal.setVlNotaFiscal(cursor.getDouble(1));
                notaFiscal.setDtEmissao(cursor.getString(2));
                notaFiscal.setNrChaveAcesso(cursor.getString(3));

                // Procurando e setando o vendedor pelo código no banco de dados
                int codVendedor = cursor.getInt(4);
                Vendedor vendedor = VendedorDAO.getInstancia(context).getById(codVendedor);
                notaFiscal.setVendedor(vendedor);

                // Procurando e setando o cliente pelo código no banco de dados
                int codCliente = cursor.getInt(5);
                Cliente cliente = ClienteDAO.getInstancia(context).getById(codCliente);
                notaFiscal.setCliente(cliente);

                // Atrelando o código do banco de dados com o enum de forma de pagamento
                notaFiscal.setFormaPagamento(FormaPagamentoEnum.values()[
                        Integer.parseInt(
                                cursor.getString(6))
                        ]);

                notaFiscal.setNrCaixa(cursor.getInt(7)
                );
            }
            return notaFiscal;
        } catch (SQLException ex) {
            Log.e("ERRO","NotaFiscalDAO.getById():" +ex.getMessage());
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
            Log.e("ERRO","NotaFiscalDAO.getProximoCodigo():" +ex.getMessage());
        }
        return 0;
    }

    public int getUltimoCodigo(){
        try {
            Cursor cursor = db.rawQuery("SELECT MAX(" + colunas[0] + ") FROM "+ nomeTabela, null);
            if (cursor.moveToFirst()) {
                return cursor.getInt(0);
            }
        }catch (SQLException ex){
            Log.e("ERRO","NotaFiscalDAO.getProximoCodigo():" +ex.getMessage());
        }
        return 0;
    }

    public ArrayList<NotaFiscal> getByListNome(String nrNotaFiscal){
        ArrayList<NotaFiscal> lista = new ArrayList<>();
        nrNotaFiscal += "%";
        try {
            String[] identificador = {nrNotaFiscal};
            Cursor cursor = db.query(nomeTabela, colunas, colunas[0] + " LIKE ?", identificador, null, null, colunas[2]);
            if (cursor.moveToFirst()) {
                do {
                    NotaFiscal notaFiscal = new NotaFiscal();
                    notaFiscal.setNrNotaFiscal(cursor.getInt(0));
                    notaFiscal.setVlNotaFiscal(cursor.getDouble(1));
                    notaFiscal.setDtEmissao(cursor.getString(2));
                    notaFiscal.setNrChaveAcesso(cursor.getString(3));

                    // Procurando e setando o vendedor pelo código no banco de dados
                    int codVendedor = cursor.getInt(4);
                    Vendedor vendedor = VendedorDAO.getInstancia(context).getById(codVendedor);
                    notaFiscal.setVendedor(vendedor);

                    // Procurando e setando o cliente pelo código no banco de dados
                    int codCliente = cursor.getInt(5);
                    Cliente cliente = ClienteDAO.getInstancia(context).getById(codCliente);
                    notaFiscal.setCliente(cliente);

                    // Atrelando o código do banco de dados com o enum de forma de pagamento
                    notaFiscal.setFormaPagamento(FormaPagamentoEnum.values()[
                            Integer.parseInt(
                                    cursor.getString(6))
                            ]);

                    notaFiscal.setNrCaixa(cursor.getInt(7));


                    lista.add(notaFiscal);
                } while (cursor.moveToNext());
            }
            return lista;
        } catch (SQLException ex) {
            Log.e("ERRO","NotaFiscalDAO.getByListNome():" +ex.getMessage());
        }
        return lista;
    }

    public double getValorTotalVenda(int nrNotaFiscal){
        double valorTotal = 0;
        try {
            String[] identificador = {String.valueOf(nrNotaFiscal)};
            Cursor cursor = db.rawQuery("SELECT SUM(VL_SUBTOTAL) FROM ITEMNF WHERE NR_NOTAFISCAL = ?", identificador);
            if (cursor.moveToFirst()) {
                valorTotal = cursor.getDouble(0);
            }
        }catch (SQLException ex){
            Log.e("ERRO","NotaFiscalDAO.getValorTotalVenda():" +ex.getMessage());
        }
        return valorTotal;
    }
}
