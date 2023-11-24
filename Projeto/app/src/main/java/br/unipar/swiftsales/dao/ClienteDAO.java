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
import br.unipar.swiftsales.model.Cliente;

public class ClienteDAO implements GenericDAO<Cliente> {
    //Variavel para abrir a conexão com BD
    private SQLiteOpenHelper openHelper;

    //Base de Dados
    private SQLiteDatabase bd;

    //Nome da tabela
    private String nomeTabela = "CLIENTE";

    //Nome das colunas da tabela
    private String[] colunas = {"CD_CLIENTE", "NM_CLIENTE", "NR_CPF", "DS_EMAIL", "NR_TELEFONE"};

    private Context context;

    private static ClienteDAO instancia;

    public ClienteDAO(Context context) {
        this.context = context;
        //Abrir uma conexão da BD
        openHelper = new SQLiteDataHelper(this.context, "UNIPAR_BD",
                null, 2);
        //Carrega a BD e da permissão para escrever na tabela
        bd = openHelper.getWritableDatabase();
    }

    public static ClienteDAO getInstancia(Context context) {
        if (instancia == null) {
            return instancia = new ClienteDAO(context);
        } else {
            return instancia;
        }
    }

    @Override
    public long insert(Cliente obj) {
        try {
            ContentValues valores = new ContentValues();
            valores.put(colunas[0], obj.getCdCliente());
            valores.put(colunas[1], obj.getNmCliente());
            valores.put(colunas[2], obj.getNrCpf());
            valores.put(colunas[3], obj.getDsEmail());
            valores.put(colunas[4], obj.getNrTelefone());

            return bd.insert(nomeTabela, null, valores);
        } catch (SQLException ex) {
            Log.e("ERRO", "ClienteDao.insert():" + ex.getMessage());
        }
        return 0;
    }

    @Override
    public long update(Cliente obj) {
        try {
            ContentValues valores = new ContentValues();
            valores.put(colunas[1], obj.getNmCliente());
            valores.put(colunas[2], obj.getNrCpf());
            valores.put(colunas[3], obj.getDsEmail());
            valores.put(colunas[4], obj.getNrTelefone());

            String[] identificador = {String.valueOf(obj.getCdCliente())};

            return bd.update(nomeTabela, valores, colunas[0] + " = ?", identificador);
        } catch (SQLException ex) {
            Log.e("ERRO", "ClienteDao.update():" + ex.getMessage());
        }
        return 0;
    }

    @Override
    public long delete(Cliente obj) {
        try {
            String[] identificador = {String.valueOf(obj.getCdCliente())};
            return bd.delete(nomeTabela, colunas[0] + " = ?", identificador);
        } catch (SQLException ex) {
            Log.e("ERRO", "ClienteDao.delete():" + ex.getMessage());
        }
        return 0;
    }

    @Override
    public ArrayList<Cliente> getAll() {
        ArrayList<Cliente> lista = new ArrayList<>();
        try {
            Cursor cursor = bd.query(nomeTabela, colunas, null, null, null, null, colunas[0]);
            if (cursor.moveToFirst()) {
                do {
                    Cliente cliente = new Cliente();
                    cliente.setCdCliente(cursor.getInt(0));
                    cliente.setNmCliente(cursor.getString(1));
                    cliente.setNrCpf(cursor.getString(2));
                    cliente.setDsEmail(cursor.getString(3));
                    cliente.setNrTelefone(cursor.getString(4));

                    lista.add(cliente);
                } while (cursor.moveToNext());
            }
            return lista;
        } catch (SQLException ex) {
            Log.e("ERRO", "ClienteDao.getAll():" + ex.getMessage());
        }
        return lista;
    }

    @Override
    public Cliente getById(int id) {
        try {
            String[] identificador = {String.valueOf(id)};
            Cursor cursor = bd.query(nomeTabela, colunas, colunas[0] + " = ?", identificador, null, null, null);
            if (cursor.moveToFirst()) {
                Cliente cliente = new Cliente();
                cliente.setCdCliente(cursor.getInt(0));
                cliente.setNmCliente(cursor.getString(1));
                cliente.setNrCpf(cursor.getString(2));
                cliente.setDsEmail(cursor.getString(3));
                cliente.setNrTelefone(cursor.getString(4));

                return cliente;
            }
        } catch (SQLException ex) {
            Log.e("ERRO", "ClienteDao.getById():" + ex.getMessage());
        }
        return null;
    }

    public int getProximoCodigo() {
        try {
            Cursor cursor = bd.rawQuery("SELECT MAX(CD_CLIENTE) FROM CLIENTE", null);
            if (cursor.moveToFirst()) {
                return cursor.getInt(0) + 1;
            }
        } catch (SQLException ex) {
            Log.e("ERRO", "ClienteDao.getProximoCodigo():" + ex.getMessage());
        }
        return 0;
    }

    public ArrayList<Cliente> getByListNome(String nmCliente) {
        ArrayList<Cliente> lista = new ArrayList<>();
        nmCliente += "%";
        String[] identificador = {nmCliente.toUpperCase()};
        try {
            Cursor cursor = bd.query(nomeTabela, colunas, colunas[1] + " LIKE UPPER(?)", identificador, null, null, colunas[1]);
            if (cursor.moveToFirst()) {
                do {
                    Cliente cliente = new Cliente();
                    cliente.setCdCliente(cursor.getInt(0));
                    cliente.setNmCliente(cursor.getString(1));
                    cliente.setNrCpf(cursor.getString(2));
                    cliente.setDsEmail(cursor.getString(3));
                    cliente.setNrTelefone(cursor.getString(4));

                    lista.add(cliente);
                } while (cursor.moveToNext());
            }
            return lista;
        } catch (SQLException ex) {
            Log.e("ERRO", "ClienteDao.getByListNome():" + ex.getMessage());
        }
        return lista;
    }
}
