package br.unipar.swiftsales.controller;

import android.content.Context;

import java.util.ArrayList;

import br.unipar.swiftsales.dao.ClienteDAO;
import br.unipar.swiftsales.dao.ProdutoDAO;
import br.unipar.swiftsales.model.Produto;
import br.unipar.swiftsales.view.ProdutoActivity;

public class ProdutoController {
    public Context context;

    public static ProdutoController instancia;
    public static ProdutoController getInstancia(Context context){
        if (instancia == null) {
            return instancia = new ProdutoController(context);
        } else {
            return instancia;
        }
    }
    public ProdutoController(Context context){
        this.context = context;
        instancia = this;
    }

    public String retornaProximoCodigo(){
        return String.valueOf(ProdutoDAO.getInstancia(context).getProximoCodigo());
    }

    public String salvarProduto(String cdProduto, String dsProduto, String vlProduto, String qtProduto){
        try{
            //Validar os campos
            if(cdProduto.equals("") ||cdProduto.isEmpty()) {
                return "Código não informado.";
            }
            if(dsProduto.equals("") ||dsProduto.isEmpty()){
                return "Descrição não informado.";
            }
            if(vlProduto.equals("") ||vlProduto.isEmpty()){
                return "Valor não informado.";
            }
            if(qtProduto.equals("") ||qtProduto.isEmpty()){
                return "Quantidade não informada.";
            }
            Produto produto = ProdutoDAO.getInstancia(context).getById(Integer.parseInt(cdProduto));

            if(produto.getCdProduto() != 0) {
                return "Código ("+cdProduto+") já cadastrado.";
            }else {
                produto = new Produto();
                produto.setCdProduto(Integer.parseInt(cdProduto));
                produto.setDsProduto(dsProduto);
                produto.setVlProduto(Double.parseDouble(vlProduto));
                produto.setQtProduto(Integer.parseInt(qtProduto));
                ProdutoDAO.getInstancia(context).insert(produto);
            }
        }catch(Exception ex){
            return "Erro ao gravar Produto.";
        }
        return null;
    }
    public ArrayList<Produto> retornaListaProdutos(){
        return ProdutoDAO.getInstancia(context).getAll();
    }
    public String alterarProduto(String cdProduto, String dsProduto, String vlProduto, String qtProduto){
        try{
            //Validar os campos
            if(cdProduto.equals("") ||cdProduto.isEmpty()) {
                return "Código não informado.";
            }
            if(dsProduto.equals("") ||dsProduto.isEmpty()){
                return "Descrição não informado.";
            }
            if(vlProduto.equals("") ||vlProduto.isEmpty()){
                return "Valor não informado.";
            }
            if(qtProduto.equals("") ||qtProduto.isEmpty()){
                return "Quantidade não informada.";
            }
            Produto produto = ProdutoDAO.getInstancia(context).getById(Integer.parseInt(cdProduto));

            if(produto == null) {
                return "Código ("+cdProduto+") não cadastrado.";
            }else {
                produto = new Produto();
                produto.setCdProduto(Integer.parseInt(cdProduto));
                produto.setDsProduto(dsProduto);
                produto.setVlProduto(Double.parseDouble(vlProduto));
                produto.setQtProduto(Integer.parseInt(qtProduto));
                ProdutoDAO.getInstancia(context).update(produto);
            }
        }catch(Exception ex){
            return "Erro ao alterar Produto.";
        }
        return null;
    }
    public String excluirProduto(String cdProduto){
        try{
            //Validar os campos
            if(cdProduto.equals("") ||cdProduto.isEmpty()) {
                return "Código não informado.";
            }
            Produto produto = ProdutoDAO.getInstancia(context).getById(Integer.parseInt(cdProduto));

            if(produto == null) {
                return "Código ("+cdProduto+") não cadastrado.";
            }else {
                ProdutoDAO.getInstancia(context).delete(produto);
            }
        }catch(Exception ex){
            return "Erro ao excluir Produto.";
        }
        return null;
    }
    public Produto retornaProduto(String cdProduto){
        return ProdutoDAO.getInstancia(context).getById(Integer.parseInt(cdProduto));
    }
    public ArrayList<Produto> getByListNome(String dsProduto){
        return ProdutoDAO.getInstancia(context).getByListNome(dsProduto);
    }
}
