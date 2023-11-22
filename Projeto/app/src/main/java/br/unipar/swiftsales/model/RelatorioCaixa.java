package br.unipar.swiftsales.model;

public class RelatorioCaixa {
    private Caixa caixa;
    private double vlSaldo;
    private int qtVendas;
    private String data;

    public RelatorioCaixa(Caixa caixa, double vlSaldo, int qtVendas, String data) {
        this.caixa = caixa;
        this.vlSaldo = vlSaldo;
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

    public double getVlSaldo() {
        return vlSaldo;
    }

    public void setVlSaldo(double vlSaldo) {
        this.vlSaldo = vlSaldo;
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
                ", vlSaldo=" + vlSaldo +
                ", qtVendas=" + qtVendas +
                ", data='" + data + '\'' +
                '}';
    }
}
