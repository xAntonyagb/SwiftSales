package br.unipar.swiftsales.utils;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class DataAtual {
    public static String getDataAtual() {
        //Utilizando a classe SimpleDateFormat para formatar a data
        SimpleDateFormat formatador = new SimpleDateFormat("yyyy-MM-dd 'ás' HH:mm:ss");
        //Criando um objeto Date com a data atual
        Date date = new Date(System.currentTimeMillis());

        //Retornando a data formatada
        return formatador.format(date);
    }
}
