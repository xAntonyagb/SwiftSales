package br.unipar.swiftsales.model;

public class Vendedor {
    private int cdVendedor;
    private String nmVendedor;

    public Vendedor() {
    }

    public Vendedor(int cdVendedor, String nmVendedor) {
        this.cdVendedor = cdVendedor;
        this.nmVendedor = nmVendedor;
    }

    public int getCdVendedor() {
        return cdVendedor;
    }

    public void setCdVendedor(int cdVendedor) {
        this.cdVendedor = cdVendedor;
    }

    public String getNmVendedor() {
        return nmVendedor;
    }

    public void setNmVendedor(String nmVendedor) {
        this.nmVendedor = nmVendedor;
    }

    @Override
    public String toString() {
        return  nmVendedor;
    }
}
