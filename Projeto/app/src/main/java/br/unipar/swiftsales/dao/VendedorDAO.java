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
import br.unipar.swiftsales.model.Vendedor;

public class VendedorDAO implements GenericDAO<Vendedor>{

    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase db;

    private String nomeTabela = "VENDEDOR";
    private String[] colunas = {"CD_VENDEDOR", "NM_VENDEDOR"};

    private Context context;
    private static VendedorDAO instancia;

    // Criando construtor para inicializar a conexão com o BD
    public VendedorDAO(Context context) {
        this.context = context;

        openHelper = new SQLiteDataHelper(this.context, "UNIPAR_DB", null, 1); // Abrindo conexão com o BD
        db = openHelper.getWritableDatabase(); //Disponibilizar escrita na database
    }

    // Singleton
    public static VendedorDAO getInstancia(Context context) {
        if (instancia == null) {
            return instancia = new VendedorDAO(context);
        } else {
            return instancia;
        }
    }

    // Criando método para inserir um vendedor
    public long insert(Vendedor obj) {
        try {
            ContentValues valores = new ContentValues();
            valores.put(colunas[0], obj.getCdVendedor());
            valores.put(colunas[1], obj.getNmVendedor());

            return db.insert(nomeTabela, null, valores);
        } catch (SQLException ex) {
            Log.e("ERRO", "VendedorDAO.insert(): " + ex.getMessage());
        }
        return 0;
    }

    // Criando método para atualizar um vendedor
    public long update(Vendedor obj) {
        try {
            ContentValues valores = new ContentValues();
            valores.put(colunas[0], obj.getCdVendedor());
            valores.put(colunas[1], obj.getNmVendedor());

            return db.update(nomeTabela, valores, colunas[0] + " = ?", new String[]{String.valueOf(obj.getCdVendedor())});
        } catch (SQLException ex) {
            Log.e("ERRO", "VendedorDAO.update(): " + ex.getMessage());
        }
        return 0;
    }

    // Criando método para excluir um vendedor
    public long delete(Vendedor obj) {
        try {
            return db.delete(nomeTabela, colunas[0] + " = ?", new String[]{String.valueOf(obj.getCdVendedor())});
        } catch (SQLException ex) {
            Log.e("ERRO", "VendedorDAO.delete(): " + ex.getMessage());
        }
        return 0;
    }


    // Criando método para retornar todos os vendedores
    @Override
    public ArrayList<Vendedor> getAll() {
        ArrayList<Vendedor> lista = new ArrayList<>();
        try {
            //Executa a consulta
            Cursor cursor = db.query(nomeTabela, colunas, null, null, null, null, colunas[0]);
            //Percorre o cursor
            if (cursor.moveToFirst()) {
                do {
                    Vendedor vendedor = new Vendedor();
                    vendedor.setCdVendedor(cursor.getInt(0));
                    vendedor.setNmVendedor(cursor.getString(1));
                    lista.add(vendedor);
                } while (cursor.moveToNext());
            }
            return lista;
        } catch (SQLException ex) {
            Log.e("ERRO","VendedorDAO.getAll():" +ex.getMessage());
        }
        return lista;
    }

    // Criando método para retornar um vendedor pelo código
    public Vendedor getById(int cdVendedor) {
        Vendedor vendedor = new Vendedor();
        try {
            Cursor cursor = db.query(nomeTabela, colunas, colunas[0] + " = ?", new String[]{String.valueOf(cdVendedor)}, null, null, null);
            if (cursor.moveToFirst()) {
                vendedor = new Vendedor();
                vendedor.setCdVendedor(cursor.getInt(0));
                vendedor.setNmVendedor(cursor.getString(1));
            }
            return vendedor;
        } catch (SQLException ex) {
            Log.e("ERRO", "VendedorDAO.getById():" + ex.getMessage());
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
            Log.e("ERRO","VendedorDAO.getProximoCodigo():" +ex.getMessage());
        }
        return 0;
    }
    public ArrayList<Vendedor> getByListNome(String dsProduto){
        ArrayList<Vendedor> lista = new ArrayList<>();
        dsProduto += "%";
        try {
            String[] identificador = {dsProduto.toUpperCase()};
            Cursor cursor = db.query(nomeTabela, colunas, colunas[1] + " LIKE UPPER(?)", identificador, null, null, colunas[1]);
            if (cursor.moveToFirst()) {
                do {
                    Vendedor vendedor = new Vendedor();
                    vendedor.setCdVendedor(cursor.getInt(0));
                    vendedor.setNmVendedor(cursor.getString(1));
                    lista.add(vendedor);
                } while (cursor.moveToNext());
            }
            return lista;
        } catch (SQLException ex) {
            Log.e("ERRO","VendedorDAO.getByDescricao():" +ex.getMessage());
        }
        return lista;
    }

}
