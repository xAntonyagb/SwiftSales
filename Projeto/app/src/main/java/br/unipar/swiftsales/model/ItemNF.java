package br.unipar.swiftsales.model;

import kotlin.jvm.internal.PropertyReference0Impl;

public class ItemNF {

    private int nrItemNotaFiscal;
    private NotaFiscal notaFiscal;
    private Produto produto;
    private double vlUnitItem;
    private double vlDesconto;
    private double vlSubTotal;
    private int qtProduto;

    public ItemNF() {
    }

    public ItemNF(int nrItemNotaFiscal, NotaFiscal notaFiscal, Produto produto, double vlUnitItem, double vlDesconto, double vlSubTotal, int qtProduto) {
        this.nrItemNotaFiscal = nrItemNotaFiscal;
        this.notaFiscal = notaFiscal;
        this.produto = produto;
        this.vlUnitItem = vlUnitItem;
        this.vlDesconto = vlDesconto;
        this.vlSubTotal = vlSubTotal;
        this.qtProduto = qtProduto;
    }

    public NotaFiscal getNotaFiscal() {
        return notaFiscal;
    }

    public void setNotaFiscal(NotaFiscal notaFiscal) {
        this.notaFiscal = notaFiscal;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public double getVlUnitItem() {
        return vlUnitItem;
    }

    public void setVlUnitItem(double vlUnitItem) {
        this.vlUnitItem = vlUnitItem;
    }

    public double getVlDesconto() {
        return vlDesconto;
    }

    public void setVlDesconto(double vlDesconto) {
        this.vlDesconto = vlDesconto;
    }

    public double getVlSubTotal() {
        return vlSubTotal;
    }

    public void setVlSubTotal(double vlSubTotal) {
        this.vlSubTotal = vlSubTotal;
    }

    public int getQtProduto() {
        return qtProduto;
    }

    public void setQtProduto(int qtProduto) {
        this.qtProduto = qtProduto;
    }

    public int getNrItemNotaFiscal() {
        return nrItemNotaFiscal;
    }

    public void setNrItemNotaFiscal(int nrItemNotaFiscal) {
        this.nrItemNotaFiscal = nrItemNotaFiscal;
    }

    @Override
    public String toString() {
        return "ItemNF{" +
                "nrItemNotaFiscal=" + nrItemNotaFiscal +
                ", notaFiscal=" + notaFiscal +
                ", produto=" + produto +
                ", vlUnitItem=" + vlUnitItem +
                ", vlDesconto=" + vlDesconto +
                ", vlSubTotal=" + vlSubTotal +
                ", qtProduto=" + qtProduto +
                '}';
    }
}
