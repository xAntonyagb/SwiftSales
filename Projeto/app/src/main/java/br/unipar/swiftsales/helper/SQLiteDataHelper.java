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
        sqLiteDatabase.execSQL("CREATE TABLE NOTAFISCAL (NR_NOTAFISCAL INTEGER(10) PRIMARY KEY, VL_NOTAFISCAL NUMERIC(10,2), DT_EMISSAO DATE, NR_CHAVEACESSO VARCHAR(44) UNIQUE, CD_VENDEDOR INTEGER(5), CD_CLIENTE INTEGER(5), CD_FORMAPAGTO VARCHAR(2), NR_CAIXA INTEGER(5))");
        sqLiteDatabase.execSQL("CREATE TABLE ITEMNF (NR_NOTAFISCAL INTEGER(10), CD_PRODUTO INTEGER(5), VL_UNITITEM NUMERIC(10,2), VL_DESCONTO NUMERIC(10,2), VL_SUBTOTAL NUMERIC(10,2), QT_PRODUTO INTEGER(5), PRIMARY KEY (NR_NOTAFISCAL, CD_PRODUTO))");
        sqLiteDatabase.execSQL("CREATE TABLE CAIXA (NR_CAIXA INTEGER(5) PRIMARY KEY, VL_INICIAL NUMERIC(10,2), VL_FINAL NUMERIC(10,2), DT_CAIXA DATE, ST_CAIXA VARCHAR(1), CD_VENDEDOR INTEGER(5))");
        sqLiteDatabase.execSQL("CREATE TABLE FORMAPAGTO (CD_FORMAPAGTO VARCHAR(2) PRIMARY KEY, DS_FORMAPAGTO VARCHAR(100))");

        //Adicionando FKs
//        sqLiteDatabase.execSQL("ALTER TABLE NOTAFISCAL ADD CONSTRAINT FK_NOTAFISCAL_CD_VENDEDOR FOREIGN KEY (CD_VENDEDOR) REFERENCES VENDEDOR (CD_VENDEDOR) ON DELETE CASCADE");
//        sqLiteDatabase.execSQL("ALTER TABLE NOTAFISCAL ADD CONSTRAINT FK_NOTAFISCAL_CD_CLIENTE FOREIGN KEY (CD_CLIENTE) REFERENCES CLIENTE (CD_CLIENTE) ON DELETE CASCADE");
//        sqLiteDatabase.execSQL("ALTER TABLE NOTAFISCAL ADD CONSTRAINT FK_NOTAFISCAL_CD_FORMAPAGTO FOREIGN KEY (CD_FORMAPAGTO) REFERENCES FORMAPAGTO (CD_FORMAPAGTO) ON DELETE CASCADE");
//        sqLiteDatabase.execSQL("ALTER TABLE ITEMNF ADD CONSTRAINT FK_ITEMNF_CD_PRODUTO FOREIGN KEY (CD_PRODUTO) REFERENCES PRODUTO (CD_PRODUTO) ON DELETE CASCADE");
//        sqLiteDatabase.execSQL("ALTER TABLE ITEMNF ADD CONSTRAINT FK_ITEMNF_NR_NOTAFISCAL FOREIGN KEY (NR_NOTAFISCAL) REFERENCES NOTAFISCAL (NR_NOTAFISCAL) ON DELETE CASCADE");
//        sqLiteDatabase.execSQL("ALTER TABLE CAIXA ADD CONSTRAINT FK_CAIXA_CD_VENDEDOR FOREIGN KEY (CD_VENDEDOR) REFERENCES VENDEDOR (CD_VENDEDOR) ON DELETE CASCADE");

    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
