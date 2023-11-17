package br.unipar.swiftsales.model;

public class Produto {
    private int cdProduto;
    private String dsProduto;
    private double vlProduto;
    private int qtProduto;

    public Produto() {
    }

    public Produto(int cdProduto, String dsProduto, double vlProduto, int qtProduto) {
        this.cdProduto = cdProduto;
        this.dsProduto = dsProduto;
        this.vlProduto = vlProduto;
        this.qtProduto = qtProduto;
    }

    public int getCdProduto() {
        return cdProduto;
    }

    public void setCdProduto(int cdProduto) {
        this.cdProduto = cdProduto;
    }

    public String getDsProduto() {
        return dsProduto;
    }

    public void setDsProduto(String dsProduto) {
        this.dsProduto = dsProduto;
    }

    public double getVlProduto() {
        return vlProduto;
    }

    public void setVlProduto(double vlProduto) {
        this.vlProduto = vlProduto;
    }

    public int getQtProduto() {
        return qtProduto;
    }

    public void setQtProduto(int qtProduto) {
        this.qtProduto = qtProduto;
    }
}
