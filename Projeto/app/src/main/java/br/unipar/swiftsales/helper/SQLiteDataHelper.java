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
        //Criando tabelas
        sqLiteDatabase.execSQL("CREATE TABLE CLIENTE (CD_CLIENTE INTEGER(5) PRIMARY KEY,NM_CLIENTE VARCHAR(100), NR_CPF VARCHAR(11), NR_TELEFONE VARCHAR(20), DS_EMAIL VARCHAR(100))");
        sqLiteDatabase.execSQL("CREATE TABLE PRODUTO (CD_PRODUTO INTEGER(5) PRIMARY KEY,DS_PRODUTO VARCHAR(100), VL_PRODUTO NUMERIC(10,2), QT_ESTOQUE INTEGER(10))");
        sqLiteDatabase.execSQL("CREATE TABLE VENDEDOR (CD_VENDEDOR INTEGER(5) PRIMARY KEY,NM_VENDEDOR VARCHAR(100))");
        sqLiteDatabase.execSQL("CREATE TABLE FORMAPAGTO (CD_FORMAPAGTO VARCHAR(2) PRIMARY KEY, DS_FORMAPAGTO VARCHAR(100))");
        sqLiteDatabase.execSQL("CREATE TABLE CAIXA (NR_CAIXA INTEGER(5) PRIMARY KEY, VL_INICIAL NUMERIC(10,2), VL_FINAL NUMERIC(10,2), DT_CAIXA DATE, ST_CAIXA INTEGER(1))");

        sqLiteDatabase.execSQL("INSERT INTO CAIXA VALUES (1, 0, 0, '23-11-2023', 0)");
        sqLiteDatabase.execSQL("INSERT INTO VENDEDOR VALUES (1, 'ANTONY')");
        sqLiteDatabase.execSQL("INSERT INTO VENDEDOR VALUES (2, 'GABRIEL')");
        sqLiteDatabase.execSQL("INSERT INTO VENDEDOR VALUES (3, 'WALLYSON')");

        sqLiteDatabase.execSQL("CREATE TABLE NOTAFISCAL (NR_NOTAFISCAL INTEGER(10) PRIMARY KEY, VL_NOTAFISCAL NUMERIC(10,2), DT_EMISSAO DATE, NR_CHAVEACESSO VARCHAR(44) UNIQUE, CD_VENDEDOR INTEGER(5), CD_CLIENTE INTEGER(5), CD_FORMAPAGTO VARCHAR(2), NR_CAIXA INTEGER(5), foreign key (CD_VENDEDOR) references VENDEDOR(CD_VENDEDOR), foreign key (CD_CLIENTE) references CLIENTE(CD_CLIENTE), foreign key (CD_FORMAPAGTO) references FORMAPAGTO(CD_FORMAPAGTO), foreign key (NR_CAIXA) references CAIXA(NR_CAIXA))");
        sqLiteDatabase.execSQL("CREATE TABLE ITEMNF (NR_NOTAFISCAL INTEGER(10), CD_PRODUTO INTEGER(5), VL_UNITITEM NUMERIC(10,2), VL_DESCONTO NUMERIC(10,2), VL_SUBTOTAL NUMERIC(10,2), QT_PRODUTO INTEGER(5), PRIMARY KEY (NR_NOTAFISCAL, CD_PRODUTO), foreign key (NR_NOTAFISCAL) references NOTAFISCAL(NR_NOTAFISCAL), foreign key (CD_PRODUTO) references PRODUTO(CD_PRODUTO))");
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // Drop older table if existed
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS CLIENTE");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS PRODUTO");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS VENDEDOR");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS NOTAFISCAL");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS ITEMNF");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS CAIXA");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS FORMAPAGTO");

        // Create tables again
        onCreate(sqLiteDatabase);
    }
}
