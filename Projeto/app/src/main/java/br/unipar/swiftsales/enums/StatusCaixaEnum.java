package br.unipar.swiftsales.enums;

public enum StatusCaixaEnum {
    ABERTO("A"),
    FECHADO("F");

    public final String descricao;

    private StatusCaixaEnum(String descricao) {
        this.descricao = descricao;
    }
}
