package br.unipar.swiftsales.model;

public class RelatorioCaixa {
    private Caixa caixa;
    private double vlTotalVendas;
    private int qtVendas;
    private String data;

    public RelatorioCaixa(Caixa caixa, double vlTotalVendas, int qtVendas, String data) {
        this.caixa = caixa;
        this.vlTotalVendas = vlTotalVendas;
        this.qtVendas = qtVendas;
        this.data = data;
    }

    public RelatorioCaixa() {
    }

    public Caixa getCaixa() {
        return caixa;
    }

    public void setCaixa(Caixa caixa) {
        this.caixa = caixa;
    }

    public double getVlTotalVendas() {
        return vlTotalVendas;
    }

    public void setVlTotalVendas(double vlTotalVendas) {
        this.vlTotalVendas = vlTotalVendas;
    }

    public int getQtVendas() {
        return qtVendas;
    }

    public void setQtVendas(int qtVendas) {
        this.qtVendas = qtVendas;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "RelatorioCaixa{" +
                "caixa=" + caixa +
                ", vlTotalVendas=" + vlTotalVendas +
                ", qtVendas=" + qtVendas +
                ", data='" + data + '\'' +
                '}';
    }
}
