package br.unipar.swiftsales.enums;

public enum StatusCaixaEnum {
    ABERTO("Aberto"),
    FECHADO("Fechado");

    public final String descricao;

    private StatusCaixaEnum(String descricao) {
        this.descricao = descricao;
    }
}
