package br.unipar.swiftsales.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import br.unipar.swiftsales.helper.SQLiteDataHelper;
import br.unipar.swiftsales.model.Produto;

public class ProdutoDAO implements GenericDAO<Produto>{
    //Variavel para abrir a conexão com BD
    private SQLiteOpenHelper openHelper;

    //Base de Dados
    private SQLiteDatabase bd;

    //Nome da tabela
    private String nomeTabela = "PRODUTO";

    //Nome das colunas da tabela
    private String[]colunas = {"CD_PRODUTO", "DS_PRODUTO","VL_PRODUTO", "QT_ESTOQUE" };

    private Context context;

    private static ProdutoDAO instancia;

    public ProdutoDAO(Context context) {
        this.context = context;
        //Abrir uma conexão da BD
        openHelper = new SQLiteDataHelper(this.context, "UNIPAR_BD",
                null, 2);
        //Carrega a BD e da permissão para escrever na tabela
        bd = openHelper.getWritableDatabase();
    }
    public static ProdutoDAO getInstancia(Context context){
        if(instancia == null){
            return instancia = new ProdutoDAO(context);
        }else{
            return instancia;
        }
    }
    @Override
    public long insert(Produto obj) {
        try {
            ContentValues valores = new ContentValues();
            valores.put(colunas[0], obj.getCdProduto());
            valores.put(colunas[1], obj.getDsProduto());
            valores.put(colunas[2], obj.getVlProduto());
            valores.put(colunas[3], obj.getQtProduto());

            return bd.insert(nomeTabela, null, valores);
        } catch (SQLException ex) {
            Log.e("ERRO","ProdutoDAO.insert():" +ex.getMessage());
        }
        return 0;
    }

    @Override
    public long update(Produto obj) {
        try {
            ContentValues valores = new ContentValues();
            valores.put(colunas[0], obj.getCdProduto());
            valores.put(colunas[1], obj.getDsProduto());
            valores.put(colunas[2], obj.getVlProduto());
            valores.put(colunas[3], obj.getQtProduto());
            String[] identificador = {String.valueOf(obj.getCdProduto())};

            return bd.update(nomeTabela, valores, colunas[0] + " = ?", identificador);
        } catch (SQLException ex) {
            Log.e("ERRO","ProdutoDAO.update():" +ex.getMessage());
        }
        return 0;
    }
    @Override
    public long delete(Produto obj) {
        try {
            String[] identificador = {String.valueOf(obj.getCdProduto())};
            return bd.delete(nomeTabela, colunas[0] + " = ?", identificador);
        } catch (SQLException ex) {
            Log.e("ERRO","ProdutoDAO.delete():" +ex.getMessage());
        }
        return 0;
    }
    @Override
    public ArrayList<Produto> getAll() {
        ArrayList<Produto> lista = new ArrayList<>();
        try {
            //Executa a consulta
            Cursor cursor = bd.query(nomeTabela, colunas, null, null, null, null, colunas[0]);
            //Percorre o cursor
            if (cursor.moveToFirst()) {
                do {
                    Produto produto = new Produto();
                    produto.setCdProduto(cursor.getInt(0));
                    produto.setDsProduto(cursor.getString(1));
                    produto.setVlProduto(cursor.getDouble(2));
                    produto.setQtProduto(cursor.getInt(3));
                    lista.add(produto);
                } while (cursor.moveToNext());
            }
            return lista;
        } catch (SQLException ex) {
            Log.e("ERRO","ProdutoDAO.getAll():" +ex.getMessage());
        }
        return lista;
    }
    @Override
    public Produto getById(int cdProduto) {
        Produto produto = new Produto();
        try {
            String[] identificador = {String.valueOf(cdProduto)};
            Cursor cursor = bd.query(nomeTabela, colunas, colunas[0] + " = ?", identificador, null, null, null);
            if (cursor.moveToFirst()) {
                produto.setCdProduto(cursor.getInt(0));
                produto.setDsProduto(cursor.getString(1));
                produto.setVlProduto(cursor.getDouble(2));
                produto.setQtProduto(cursor.getInt(3));
            }
            return produto;
        } catch (SQLException ex) {
            Log.e("ERRO","ProdutoDAO.getById():" +ex.getMessage());
        }
        return produto;
    }

    public int getProximoCodigo(){
        try {
            Cursor cursor = bd.rawQuery("SELECT MAX(CD_PRODUTO) FROM PRODUTO", null);
            if (cursor.moveToFirst()) {
                return cursor.getInt(0) + 1;
            }
        }catch (SQLException ex){
            Log.e("ERRO","ProdutoDAO.getProximoCodigo():" +ex.getMessage());
        }
        return 0;
    }
    public ArrayList<Produto> getByListNome(String dsProduto){
        ArrayList<Produto> lista = new ArrayList<>();
        dsProduto += "%";
        try {
            String[] identificador = {dsProduto.toUpperCase()};
            Cursor cursor = bd.rawQuery("SELECT * FROM PRODUTO WHERE DS_PRODUTO LIKE UPPER(?)", identificador);
            if (cursor.moveToFirst()) {
                do {
                    Produto produto = new Produto();
                    produto.setCdProduto(cursor.getInt(0));
                    produto.setDsProduto(cursor.getString(1));
                    produto.setVlProduto(cursor.getDouble(2));
                    produto.setQtProduto(cursor.getInt(3));
                    lista.add(produto);
                } while (cursor.moveToNext());
            }
            return lista;
        } catch (SQLException ex) {
            Log.e("ERRO","ProdutoDAO.getByDescricao():" +ex.getMessage());
        }
        return lista;
    }
}
