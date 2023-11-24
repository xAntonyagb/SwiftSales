package br.unipar.swiftsales.model;

public class Cliente {
    private int cdCliente;
    private String nmCliente;
    private String nrTelefone;
    private String dsEmail;
    private String nrCpf;

    public Cliente() {
    }

    public Cliente(int cdCliente, String nmCliente, String nrTelefone, String dsEmail, String nrCpf) {
        this.cdCliente = cdCliente;
        this.nmCliente = nmCliente;
        this.nrTelefone = nrTelefone;
        this.dsEmail = dsEmail;
        this.nrCpf = nrCpf;
    }

    public int getCdCliente() {
        return cdCliente;
    }

    public void setCdCliente(int cdCliente) {
        this.cdCliente = cdCliente;
    }

    public String getNmCliente() {
        return nmCliente;
    }

    public void setNmCliente(String nmCliente) {
        this.nmCliente = nmCliente;
    }

    public String getNrTelefone() {
        return nrTelefone;
    }

    public void setNrTelefone(String nrTelefone) {
        this.nrTelefone = nrTelefone;
    }

    public String getDsEmail() {
        return dsEmail;
    }

    public void setDsEmail(String dsEmail) {
        this.dsEmail = dsEmail;
    }

    public String getNrCpf() {
        return nrCpf;
    }

    public void setNrCpf(String nrCpf) {
        this.nrCpf = nrCpf;
    }

    @Override
    public String toString() {
        return nmCliente;
    }
}
