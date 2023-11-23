package br.unipar.swiftsales.controller;

import android.content.Context;

import java.util.ArrayList;

import br.unipar.swiftsales.dao.NotaFiscalDAO;
import br.unipar.swiftsales.model.ItemNF;
import br.unipar.swiftsales.model.NotaFiscal;

public class NotaFiscalController {
    public Context context;

    public static NotaFiscalController instancia;
    public static NotaFiscalController getInstancia(Context context){
        if (instancia == null) {
            return instancia = new NotaFiscalController(context);
        } else {
            return instancia;
        }
    }
    public NotaFiscalController(Context context){
        this.context = context;
        instancia = this;
    }

    public NotaFiscal retornaNotaFiscal(int nrNotaFiscal){
        return NotaFiscalDAO.getInstancia(context).getById(nrNotaFiscal);
    }

    public String retornaProximoCodigo(){
        return String.valueOf(NotaFiscalDAO.getInstancia(context).getProximoCodigo());
    }

    public String retornaUltimoCodigo(){
        return String.valueOf(NotaFiscalDAO.getInstancia(context).getUltimoCodigo());
    }

    public String salvarNotaFiscal(NotaFiscal obj){
        try{
            //Validar os campos
            if(obj.getNrNotaFiscal() == 0){
                return "Nota Fiscal não informado.";
            }
            if(obj.getNrCaixa() == 0){
                return "Número do Caixa não informado.";
            }
            if(obj.getVlNotaFiscal() == 0){
                return "Valor inválido.";
            }
            if(obj.getDtEmissao() == null || obj.getDtEmissao().equals("")){
                return "Data de Emissão não informada.";
            }
            if(obj.getNrChaveAcesso() == null || obj.getNrChaveAcesso().equals("")){
                return "Chave de Acesso não informada.";
            }
            if(obj.getVendedor() == null){
                return "Vendedor não informado.";
            }
            if(obj.getCliente() == null){
                return "Cliente não informado.";
            }
            if(obj.getFormaPagamento() == null){
                return "Forma de Pagamento não informada.";
            }

            NotaFiscal notaFiscal = NotaFiscalDAO.getInstancia(context).getById(obj.getNrNotaFiscal());

            if(notaFiscal != null) {
                return "Código ("+obj.getNrNotaFiscal()+") já cadastrado.";
            }else {
                NotaFiscalDAO.getInstancia(context).insert(obj);
            }
        }catch(Exception ex){
            return "Erro ao gravar Nota Fiscal.";
        }
        return null;
    }
    public ArrayList<ItemNF> retornaItensNotaFiscal(int nrNotaFiscal){
        return NotaFiscalDAO.getInstancia(context).getAllItensNota(nrNotaFiscal);
    }
    public String alterarNotaFiscal(NotaFiscal obj){
        try{
            //Validar os campos
            if(obj.getNrNotaFiscal() == 0){
                return "Nota Fiscal não informado.";
            }
            if(obj.getNrCaixa() == 0){
                return "Número do Caixa não informado.";
            }
            if(obj.getVlNotaFiscal() == 0){
                return "Valor inválido.";
            }
            if(obj.getDtEmissao() == null || obj.getDtEmissao().equals("")){
                return "Data de Emissão não informada.";
            }
            if(obj.getNrChaveAcesso() == null || obj.getNrChaveAcesso().equals("")){
                return "Chave de Acesso não informada.";
            }
            if(obj.getVendedor() == null){
                return "Vendedor não informado.";
            }
            if(obj.getCliente() == null){
                return "Cliente não informado.";
            }
            if(obj.getFormaPagamento() == null){
                return "Forma de Pagamento não informada.";
            }

            NotaFiscal notaFiscal = NotaFiscalDAO.getInstancia(context).getById(obj.getNrNotaFiscal());

            if(notaFiscal == null) {
                return "Código ("+obj.getNrNotaFiscal()+") não cadastrado.";
            }else {
                NotaFiscalDAO.getInstancia(context).update(obj);
            }
        }catch(Exception ex){
            return "Erro ao alterar Nota Fiscal";
        }
        return null;
    }
    public String excluirNotaFiscal(int nrNotaFiscal){
        try{
            //Validar os campos
            if(nrNotaFiscal == 0) {
                return "Código não informado.";
            }

            NotaFiscal notaFiscal = NotaFiscalDAO.getInstancia(context).getById(nrNotaFiscal);

            if(notaFiscal == null) {
                return "Código ("+ nrNotaFiscal +") não cadastrado.";
            }else {
                NotaFiscalDAO.getInstancia(context).delete(nrNotaFiscal);
            }
        }catch(Exception ex){
            return "Erro ao excluir Nota Fiscal.";
        }
        return null;
    }



}
