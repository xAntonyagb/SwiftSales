package br.unipar.swiftsales.controller;

import android.content.Context;

import java.util.ArrayList;

import br.unipar.swiftsales.dao.ClienteDAO;
import br.unipar.swiftsales.model.Cliente;

public class ClienteController {
    private Context context;


    public ClienteController(Context context){
        this.context = context;
    }
    public String retornaProximoCodigo(){
        return String.valueOf(ClienteDAO.getInstancia(context).getProximoCodigo());
    }
    public String salvarCliente(String cdCliente, String nmCliente, String nrTelefone, String dsEmail, String nrCpf){
        try{
        //Validar os campos
        if(cdCliente.equals("") ||cdCliente.isEmpty()) {
            return "Código não informado.";
        }
        if(nmCliente.equals("") ||nmCliente.isEmpty()){
            return "Nome não informado.";
        }
        if(nrTelefone.equals("") ||nrTelefone.isEmpty()){
            return "Telefone não informado.";
        }
        if(dsEmail.equals("") ||dsEmail.isEmpty()){
            return "E-mail não informado.";
        }
        if(nrCpf.equals("") ||nrCpf.isEmpty()){
            return "CPF não informado.";
        }
        Cliente cliente = ClienteDAO.getInstancia(context).getById(Integer.parseInt(cdCliente));

        if(cliente != null) {
            return "Código ("+cdCliente+") já cadastrado.";
        }else {
            cliente = new Cliente();
            cliente.setCdCliente(Integer.parseInt(cdCliente));
            cliente.setNmCliente(nmCliente);
            cliente.setNrTelefone(nrTelefone);
            cliente.setDsEmail(dsEmail);
            cliente.setNrCpf(nrCpf);
            ClienteDAO.getInstancia(context).insert(cliente);
        }
        }catch(Exception ex){
            return "Erro ao gravar Cliente.";
        }
        return null;
    }
    public ArrayList<Cliente> retornaListaClientes(){
        return ClienteDAO.getInstancia(context).getAll();
    }
    public String alterarCliente(String cdCliente, String nmCliente, String nrTelefone, String dsEmail, String nrCpf){
        try{
        //Validar os campos
        if(cdCliente.equals("") ||cdCliente.isEmpty()) {
            return "Código não informado.";
        }
        if(nmCliente.equals("") ||nmCliente.isEmpty()){
            return "Nome não informado.";
        }
        if(nrTelefone.equals("") ||nrTelefone.isEmpty()){
            return "Telefone não informado.";
        }
        if(dsEmail.equals("") ||dsEmail.isEmpty()){
            return "E-mail não informado.";
        }
        if(nrCpf.equals("") ||nrCpf.isEmpty()){
            return "CPF não informado.";
        }
        Cliente cliente = ClienteDAO.getInstancia(context).getById(Integer.parseInt(cdCliente));

        if(cliente == null) {
            return "O Cliente ("+cdCliente+") não existe.";
        }else {
            cliente = new Cliente();
            cliente.setCdCliente(Integer.parseInt(cdCliente));
            cliente.setNmCliente(nmCliente);
            cliente.setNrTelefone(nrTelefone);
            cliente.setDsEmail(dsEmail);
            cliente.setNrCpf(nrCpf);
            ClienteDAO.getInstancia(context).update(cliente);
        }
        }catch(Exception ex){
            return "Erro ao editar Cliente.";
        }
        return null;
    }
    public String excluirCliente(String cdCliente){
        try{
        //Validar os campos
        if(cdCliente.equals("") ||cdCliente.isEmpty()) {
            return "Código não informado.";
        }
        Cliente cliente = ClienteDAO.getInstancia(context).getById(Integer.parseInt(cdCliente));

        if(cliente == null) {
            return "O Cliente ("+cdCliente+") não existe.";
        }else {
            cliente = new Cliente();
            cliente.setCdCliente(Integer.parseInt(cdCliente));
            ClienteDAO.getInstancia(context).delete(cliente);
        }
        }catch(Exception ex){
            return "Erro ao excluir Cliente.";
        }
        return null;
    }
    public Cliente retornaCliente(String cdCliente){
        return ClienteDAO.getInstancia(context).getById(Integer.parseInt(cdCliente));
    }
    public ArrayList<Cliente> getByListNome(String nmCliente){
        return ClienteDAO.getInstancia(context).getByListNome(nmCliente);
    }
}
