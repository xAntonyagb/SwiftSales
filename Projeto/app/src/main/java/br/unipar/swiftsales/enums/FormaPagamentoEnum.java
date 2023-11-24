package br.unipar.swiftsales.enums;

public enum FormaPagamentoEnum {
    CREDITO("Crédito"),
    DEBITO("Débito"),
    PIX("Pix");

    public final String descricao;

    private FormaPagamentoEnum(String descricao) {
        this.descricao = descricao;
    }

    public static FormaPagamentoEnum descricaoToEnum(String descricao) {
        switch (descricao) {
            case "Crédito":
                return CREDITO;
            case "Débito":
                return DEBITO;
            case "Pix":
                return PIX;
            default:
                return null;
        }
    }

    public static String[] descFormasPagamento = {
        "Crédito",
        "Débito",
        "Pix"
    };
}
