package br.unipar.swiftsales.enums;

public enum StatusCaixaEnum {
    ABERTO("A"),
    FECHADO("F");

    public final String descricao;

    private StatusCaixaEnum(String descricao) {
        this.descricao = descricao;
    }

    public static StatusCaixaEnum getEnum(String descricao) {
        for (StatusCaixaEnum status : StatusCaixaEnum.values()) {
            if (status.descricao.equals(descricao)) {
                return status;
            }
        }
        return null;
    }
}
