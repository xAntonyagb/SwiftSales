package br.unipar.swiftsales.controller;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

import br.unipar.swiftsales.dao.RelatorioCaixaDAO;
import br.unipar.swiftsales.model.RelatorioCaixa;

public class RelatorioCaixaController {
    public Context context;

    public RelatorioCaixaController(Context context){
        this.context = context;
    }
    public ArrayList<RelatorioCaixa> getRelatorioCaixa(String dataInicial, String dataFinal) {
        try {
            return RelatorioCaixaDAO.getInstancia(context).getRelatorioCaixa(dataInicial, dataFinal);
        } catch (Exception ex) {
            Log.e("ERRO", ex.getMessage());
        }
        return null;
    }
}
