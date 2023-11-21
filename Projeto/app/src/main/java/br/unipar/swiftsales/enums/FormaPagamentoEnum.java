package br.unipar.swiftsales.enums;

public enum FormaPagamentoEnum {
    CREDITO("Crédito"),
    DEBITO("Débito"),
    PIX("Pix");

    public final String descricao;

    private FormaPagamentoEnum(String descricao) {
        this.descricao = descricao;
    }
}
