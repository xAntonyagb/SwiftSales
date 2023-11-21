package br.unipar.swiftsales.model;

import java.sql.Date;
import java.text.SimpleDateFormat;

import br.unipar.swiftsales.enums.FormaPagamentoEnum;

public class NotaFiscal {
    private int nr_nota_fiscal;
    private int vl_nota_fiscal;
    private String dt_nota_fiscal;
    private String nr_chave_acesso;
    private Vendedor vendedor;
    private Cliente cliente;
    private FormaPagamentoEnum formaPagamento;

    public NotaFiscal(int nr_nota_fiscal, int vl_nota_fiscal, String dt_nota_fiscal, String nr_chave_acesso, Vendedor vendedor, Cliente cliente, FormaPagamentoEnum formaPagamento) {
        this.nr_nota_fiscal = nr_nota_fiscal;
        this.vl_nota_fiscal = vl_nota_fiscal;
        this.dt_nota_fiscal = dt_nota_fiscal;
        this.nr_chave_acesso = nr_chave_acesso;
        this.vendedor = vendedor;
        this.cliente = cliente;
        this.formaPagamento = formaPagamento;
    }

    public NotaFiscal() {
    }

    public int getNr_nota_fiscal() {
        return nr_nota_fiscal;
    }

    public void setNr_nota_fiscal(int nr_nota_fiscal) {
        this.nr_nota_fiscal = nr_nota_fiscal;
    }

    public int getVl_nota_fiscal() {
        return vl_nota_fiscal;
    }

    public void setVl_nota_fiscal(int vl_nota_fiscal) {
        this.vl_nota_fiscal = vl_nota_fiscal;
    }

    public String getDt_nota_fiscal() {
        return dt_nota_fiscal;
    }

    public void setDt_nota_fiscal(String dt_nota_fiscal) {
        this.dt_nota_fiscal = dt_nota_fiscal;
    }

    public String getNr_chave_acesso() {
        return nr_chave_acesso;
    }

    public void setNr_chave_acesso(String nr_chave_acesso) {
        this.nr_chave_acesso = nr_chave_acesso;
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
                "nr_nota_fiscal=" + nr_nota_fiscal +
                ", vl_nota_fiscal=" + vl_nota_fiscal +
                ", dt_nota_fiscal=" + dt_nota_fiscal +
                ", nr_chave_acesso='" + nr_chave_acesso + '\'' +
                ", vendedor=" + vendedor +
                ", cliente=" + cliente +
                ", formaPagamento=" + formaPagamento.descricao +
                '}';
    }
}
