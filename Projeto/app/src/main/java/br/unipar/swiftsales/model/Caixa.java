package br.unipar.swiftsales.model;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import br.unipar.swiftsales.enums.StatusCaixaEnum;

public class Caixa {
    private int nrCaixa;
    private ArrayList<NotaFiscal> listaNotas;
    private double vlInicial;
    private double vlFinal;
    private String dtCaixa;
    private StatusCaixaEnum stCaixa;

    public Caixa(int nrCaixa, ArrayList<NotaFiscal> listaNotas, double vlInicial, double vlFinal, String dtCaixa, StatusCaixaEnum stCaixa) {
        this.nrCaixa = nrCaixa;
        this.listaNotas = listaNotas;
        this.vlInicial = vlInicial;
        this.vlFinal = vlFinal;
        this.dtCaixa = dtCaixa;
        this.stCaixa = stCaixa;
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

    public ArrayList<NotaFiscal> getListaNotas() {
        return listaNotas;
    }

    public void setListaNotas(ArrayList<NotaFiscal> listaNotas) {
        this.listaNotas = listaNotas;
    }

    @Override
    public String toString() {
        return "Caixa{" +
                "nrCaixa=" + nrCaixa +
                ", listaNotas=" + listaNotas +
                ", vlInicial=" + vlInicial +
                ", vlFinal=" + vlFinal +
                ", dtCaixa='" + dtCaixa + '\'' +
                ", stCaixa=" + stCaixa.descricao +
                '}';
    }
}
