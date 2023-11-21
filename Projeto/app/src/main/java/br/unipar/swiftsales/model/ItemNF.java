package br.unipar.swiftsales.model;

public class ItemNF {
    private NotaFiscal notaFiscal;
    private Produto produto;
    private double vlTotalItem;
    private double vlDesconto;
    private double vlSubTotal;
    private int qtProduto;

    public ItemNF() {
    }

    public ItemNF(NotaFiscal notaFiscal, Produto produto, double vlTotalItem, double vlDesconto, double vlSubTotal, int qtProduto) {
        this.notaFiscal = notaFiscal;
        this.produto = produto;
        this.vlTotalItem = vlTotalItem;
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

    public double getVlTotalItem() {
        return vlTotalItem;
    }

    public void setVlTotalItem(double vlTotalItem) {
        this.vlTotalItem = vlTotalItem;
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

    @Override
    public String toString() {
        return "ItemNF{" +
                "notaFiscal=" + notaFiscal +
                ", produto=" + produto +
                ", vlTotalItem=" + vlTotalItem +
                ", vlDesconto=" + vlDesconto +
                ", vlSubTotal=" + vlSubTotal +
                ", qtProduto=" + qtProduto +
                '}';
    }
}
