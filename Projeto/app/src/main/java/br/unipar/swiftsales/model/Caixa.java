package br.unipar.swiftsales.model;

import java.sql.Date;
import java.text.SimpleDateFormat;

import br.unipar.swiftsales.enums.StatusCaixaEnum;

public class Caixa {
    private int nrCaixa;
    private double vlInicial;
    private double vlFinal;
    private String dtCaixa;
    private StatusCaixaEnum stCaixa;
    private Vendedor vendedor;

    public Caixa(int nrCaixa, double vlInicial, double vlFinal, String dtCaixa, StatusCaixaEnum stCaixa, Vendedor vendedor) {
        this.nrCaixa = nrCaixa;
        this.vlInicial = vlInicial;
        this.vlFinal = vlFinal;
        this.dtCaixa = dtCaixa;
        this.stCaixa = stCaixa;
        this.vendedor = vendedor;
    }

    public Caixa() {
    }

    public int getNrCaixa() {
        return nrCaixa;
    }

    public void setNrCaixa(int nrCaixa) {
        this.nrCaixa = nrCaixa;
    }

    public double getVlInicial() {
        return vlInicial;
    }

    public void setVlInicial(double vlInicial) {
        this.vlInicial = vlInicial;
    }

    public double getVlFinal() {
        return vlFinal;
    }

    public void setVlFinal(double vlFinal) {
        this.vlFinal = vlFinal;
    }

    public String getDtCaixa() {
        return dtCaixa;
    }

    public void setDtCaixa(String dtCaixa) {
        this.dtCaixa = dtCaixa;
    }

    public StatusCaixaEnum getStCaixa() {
        return stCaixa;
    }

    public void setStCaixa(StatusCaixaEnum stCaixa) {
        this.stCaixa = stCaixa;
    }

    public Vendedor getVendedor() {
        return vendedor;
    }

    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
    }

    @Override
    public String toString() {
        return "Caixa{" +
                "nrCaixa=" + nrCaixa +
                ", vlInicial=" + vlInicial +
                ", vlFinal=" + vlFinal +
                ", dtCaixa='" + dtCaixa + '\'' +
                ", stCaixa=" + stCaixa.descricao +
                ", vendedor=" + vendedor +
                '}';
    }
}
