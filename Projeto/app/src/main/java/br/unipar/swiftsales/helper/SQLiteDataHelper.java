package br.unipar.swiftsales.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLiteDataHelper extends SQLiteOpenHelper{
    public SQLiteDataHelper(@Nullable Context context,
                            @Nullable String name,
                            @Nullable SQLiteDatabase.CursorFactory factory,
                            int version) {
        super(context, name, factory, version);
    }
    /**
     * Método responsável pela criação das tabelas
     * Ele executa os scripts no momento de instalação do aplicativo
     * @param sqLiteDatabase
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE CLIENTE (CD_CLIENTE INTEGER(5) PRIMARY KEY,NM_CLIENTE VARCHAR(100), NR_CPF VARCHAR(11), NR_TELEFONE VARCHAR(20), DS_EMAIL VARCHAR(100))");
        sqLiteDatabase.execSQL("CREATE TABLE PRODUTO (CD_PRODUTO INTEGER(5) PRIMARY KEY,DS_PRODUTO VARCHAR(100), VL_PRODUTO NUMERIC(10,2), QT_ESTOQUE INTEGER(10))");
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
