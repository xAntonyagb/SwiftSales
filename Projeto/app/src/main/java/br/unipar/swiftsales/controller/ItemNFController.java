package br.unipar.swiftsales.controller;

import android.content.Context;

import java.util.ArrayList;

import br.unipar.swiftsales.dao.ClienteDAO;
import br.unipar.swiftsales.dao.ItemNFDAO;
import br.unipar.swiftsales.dao.ProdutoDAO;
import br.unipar.swiftsales.model.ItemNF;
import br.unipar.swiftsales.model.Produto;

public class ItemNFController {
    public Context context;

    public static ItemNFController instancia;
    public static ItemNFController getInstancia(Context context){
        if (instancia == null) {
            return instancia = new ItemNFController(context);
        } else {
            return instancia;
        }
    }
    public ItemNFController(Context context){
        this.context = context;
        instancia = this;
    }

    public String salvarItemNF(ItemNF obj){
        try{
            //Validar os campos
            if(obj.getNrNotaFiscal() == 0){
                return "Nota Fiscal não informado.";
            }

            if(obj.getVlUnitItem() == 0){
                return "Valor inválido.";
            }

            if(obj.getVlSubTotal() == 0){
                return "Valor inválido.";
            }

            if(obj.getQtProduto() == 0){
                return "Quantidade inválida.";
            }

            if(obj.getProduto() == null){
                return "Item da NF não informado.";
            }


            ItemNF itemNF = ItemNFDAO.getInstancia(context).getById(obj.getNrNotaFiscal(), obj.getProduto().getCdProduto());

            if(itemNF != null) {
                return "Código (Nota Fiscal: "+obj.getNrNotaFiscal()+" Produto: "+ obj.getProduto().getCdProduto() +") já cadastrado.";
            }else {
                ItemNFDAO.getInstancia(context).insert(obj);
            }
        }catch(Exception ex){
            return "Erro ao gravar Item da NF.";
        }
        return null;
    }
    public ArrayList<Produto> retornaListaProdutos(){
        return ProdutoDAO.getInstancia(context).getAll();
    }
    public String alterarItemNF(ItemNF obj){
        try{
            //Validar os campos
            if(obj.getNrNotaFiscal() == 0){
                return "Nota Fiscal não informado.";
            }

            if(obj.getVlUnitItem() == 0){
                return "Valor inválido.";
            }

            if(obj.getVlSubTotal() == 0){
                return "Valor inválido.";
            }

            if(obj.getQtProduto() == 0){
                return "Quantidade inválida.";
            }

            if(obj.getProduto() == null){
                return "Item da NF não informado.";
            }

            ItemNF itemNF = ItemNFDAO.getInstancia(context).getById(obj.getNrNotaFiscal(), obj.getProduto().getCdProduto());

            if(itemNF == null) {
                return "Código (Nota Fiscal: "+ obj.getNrNotaFiscal() +" Produto: "+ obj.getProduto().getCdProduto() +") não cadastrado.";
            }else {
                ItemNFDAO.getInstancia(context).update(obj);
            }
        }catch(Exception ex){
            return "Erro ao alterar Item NF";
        }
        return null;
    }
    public String excluirItemNF(int nrNotaFiscal, int cdProduto){
        try{
            //Validar os campos
            if(cdProduto == 0) {
                return "Código não informado.";
            }
            if(nrNotaFiscal == 0) {
                return "Número de Nota Fiscal não informado.";
            }

            ItemNF itemNF = ItemNFDAO.getInstancia(context).getById(nrNotaFiscal, cdProduto);

            if(itemNF == null) {
                return "Código (Nota Fiscal: "+ nrNotaFiscal +" Produto: "+ cdProduto +") não cadastrado.";
            }else {
                ItemNFDAO.getInstancia(context).delete(nrNotaFiscal, cdProduto);
            }
        }catch(Exception ex){
            return "Erro ao excluir Produto.";
        }
        return null;
    }

    public ItemNF getById(int nrNotaFiscal, int cdProduto){
        return ItemNFDAO.getInstancia(context).getById(nrNotaFiscal, cdProduto);
    }

}
