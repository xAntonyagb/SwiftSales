package br.unipar.swiftsales.model;

import java.sql.Date;
import java.text.SimpleDateFormat;

import br.unipar.swiftsales.enums.FormaPagamentoEnum;

public class NotaFiscal {
    private int nrNotaFiscal;
    private double vlNotaFiscal;
    private String dtEmissao;
    private String nrChaveAcesso;
    private Vendedor vendedor;
    private Cliente cliente;
    private FormaPagamentoEnum formaPagamento;

    public NotaFiscal(int nrNotaFiscal, double vlNotaFiscal, String dtEmissao, String nrChaveAcesso, Vendedor vendedor, Cliente cliente, FormaPagamentoEnum formaPagamento) {
        this.nrNotaFiscal = nrNotaFiscal;
        this.vlNotaFiscal = vlNotaFiscal;
        this.dtEmissao = dtEmissao;
        this.nrChaveAcesso = nrChaveAcesso;
        this.vendedor = vendedor;
        this.cliente = cliente;
        this.formaPagamento = formaPagamento;
    }

    public NotaFiscal() {
    }

    public int getNrNotaFiscal() {
        return nrNotaFiscal;
    }

    public void setNrNotaFiscal(int nrNotaFiscal) {
        this.nrNotaFiscal = nrNotaFiscal;
    }

    public double getVlNotaFiscal() {
        return vlNotaFiscal;
    }

    public void setVlNotaFiscal(double vlNotaFiscal) {
        this.vlNotaFiscal = vlNotaFiscal;
    }

    public String getDtEmissao() {
        return dtEmissao;
    }

    public void setDtEmissao(String dtEmissao) {
        this.dtEmissao = dtEmissao;
    }

    public String getNrChaveAcesso() {
        return nrChaveAcesso;
    }

    public void setNrChaveAcesso(String nrChaveAcesso) {
        this.nrChaveAcesso = nrChaveAcesso;
    }

    public Vendedor getVendedor() {
        return vendedor;
    }

    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public FormaPagamentoEnum getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(FormaPagamentoEnum formaPagamento) {
        this.formaPagamento = formaPagamento;
    }


    @Override
    public String toString() {
        return "NotaFiscal{" +
                "nrNotaFiscal=" + nrNotaFiscal +
                ", vlNotaFiscal=" + vlNotaFiscal +
                ", dtEmissao='" + dtEmissao + '\'' +
                ", nrChaveAcesso='" + nrChaveAcesso + '\'' +
                ", vendedor=" + vendedor.getNmVendedor() +
                ", cliente=" + cliente.getNmCliente() +
                ", formaPagamento=" + formaPagamento.descricao +
                '}';
    }
}
