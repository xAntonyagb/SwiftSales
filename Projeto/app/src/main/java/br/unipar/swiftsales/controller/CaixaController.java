package br.unipar.swiftsales.controller;

import android.content.Context;
import android.util.Log;
import java.util.ArrayList;
import br.unipar.swiftsales.dao.CaixaDAO;
import br.unipar.swiftsales.enums.StatusCaixaEnum;
import br.unipar.swiftsales.model.Caixa;
import br.unipar.swiftsales.utils.DataAtual;
import br.unipar.swiftsales.view.CaixaActivity;

public class CaixaController {
    private Context context;

    public CaixaController(Context context) {
        this.context = context;
    }

    public ArrayList<Caixa> getAllCaixas() {
        try {
            return CaixaDAO.getInstancia(context).getAll();
        } catch (Exception ex) {
            Log.e("ERRO", "CaixaController.getAllCaixas():" + ex.getMessage());
        }
        return null;
    }

    public Caixa getCaixaById(int nrCaixa) {
        try {
            return CaixaDAO.getInstancia(context).getById(nrCaixa);
        } catch (Exception ex) {
            Log.e("ERRO", "CaixaController.getCaixaById():" + ex.getMessage());
        }
        return null;
    }





}

