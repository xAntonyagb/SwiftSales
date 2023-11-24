package br.unipar.swiftsales.controller;

import android.content.Context;

import java.util.ArrayList;

import br.unipar.swiftsales.dao.ClienteDAO;
import br.unipar.swiftsales.dao.VendedorDAO;
import br.unipar.swiftsales.model.Cliente;
import br.unipar.swiftsales.model.Vendedor;

public class VendedorController {
    private Context context;


    public VendedorController(Context context){
        this.context = context;
    }
    public String retornaProximoCodigo(){
        return String.valueOf(VendedorDAO.getInstancia(context).getProximoCodigo());
    }

    public String salvarVendedor(String cdVendedor, String nmVendedor){

        try{
            //Validar os campos
            if(cdVendedor.equals("") ||cdVendedor.isEmpty()) {
                return "Código não informado.";
            }
            if(nmVendedor.equals("") ||nmVendedor.isEmpty()){
                return "Nome não informado.";
            }

            Vendedor vendedor = VendedorDAO.getInstancia(context).getById(Integer.parseInt(cdVendedor));

            if(vendedor != null) {
                return "Código ("+cdVendedor+") já cadastrado.";
            }else {
                vendedor = new Vendedor();
                vendedor.setCdVendedor(Integer.parseInt(cdVendedor));
                vendedor.setNmVendedor(nmVendedor);
                VendedorDAO.getInstancia(context).insert(vendedor);
            }
        }catch(Exception ex){
            return "Erro ao gravar Vendedor.";
        }
        return null;
    }
    public ArrayList<Vendedor> retornaListaVendedores(){
        return VendedorDAO.getInstancia(context).getAll();
    }
    public String alterarVendedor(String cdVendedor, String nmVendedor){
        try{
            //Validar os campos
            if(cdVendedor.equals("") ||cdVendedor.isEmpty()) {
                return "Código não informado.";
            }
            if(nmVendedor.equals("") ||nmVendedor.isEmpty()){
                return "Nome não informado.";
            }


            Vendedor vendedor = VendedorDAO.getInstancia(context).getById(Integer.parseInt(cdVendedor));

            if(vendedor == null) {
                return "O Cliente ("+cdVendedor+") não existe.";
            }else {
                vendedor = new Vendedor();
                vendedor.setCdVendedor(Integer.parseInt(cdVendedor));
                vendedor.setNmVendedor(nmVendedor);
                VendedorDAO.getInstancia(context).update(vendedor);
            }
        }catch(Exception ex){
            return "Erro ao editar Vendedor.";
        }
        return null;
    }
    public String excluirVendedor(String cdVendedor){
        try{
            //Validar os campos
            if(cdVendedor.equals("") ||cdVendedor.isEmpty()) {
                return "Código não informado.";
            }

            Vendedor vendedor = VendedorDAO.getInstancia(context).getById(Integer.parseInt(cdVendedor));

            if(vendedor == null) {
                return "O vendedor ("+cdVendedor+") não existe.";
            }else {
                vendedor = new Vendedor();
                vendedor.setCdVendedor(Integer.parseInt(cdVendedor));
                VendedorDAO.getInstancia(context).delete(vendedor);
            }
        }catch(Exception ex){
            return "Erro ao excluir Vendedor.";
        }
        return null;
    }
    public Vendedor retornaVendedor(String cdVendedor){
        return VendedorDAO.getInstancia(context).getById(Integer.parseInt(cdVendedor));
    }
    public ArrayList<Vendedor> getByListNome(String nmVendedor){
        return VendedorDAO.getInstancia(context).getByListNome(nmVendedor);
    }
}
