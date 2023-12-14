package com.marcel.a.n.roxha.deliciasdamamae.model;

public class ModeloControleRelatorioDiario {

    private String id;
    private String idReferenciaCaixa;
    private int quantidadeDeBolosVendidos;
    private double lucroAproximado;
    private double custoAproximado;
    private double totalVendido;

    public ModeloControleRelatorioDiario() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdReferenciaCaixa() {
        return idReferenciaCaixa;
    }

    public void setIdReferenciaCaixa(String idReferenciaCaixa) {
        this.idReferenciaCaixa = idReferenciaCaixa;
    }

    public int getQuantidadeDeBolosVendidos() {
        return quantidadeDeBolosVendidos;
    }

    public void setQuantidadeDeBolosVendidos(int quantidadeDeBolosVendidos) {
        this.quantidadeDeBolosVendidos = quantidadeDeBolosVendidos;
    }

    public double getLucroAproximado() {
        return lucroAproximado;
    }

    public void setLucroAproximado(double lucroAproximado) {
        this.lucroAproximado = lucroAproximado;
    }

    public double getCustoAproximado() {
        return custoAproximado;
    }

    public void setCustoAproximado(double custoAproximado) {
        this.custoAproximado = custoAproximado;
    }

    public double getTotalVendido() {
        return totalVendido;
    }

    public void setTotalVendido(double totalVendido) {
        this.totalVendido = totalVendido;
    }

    @Override
    public String toString() {
        return "ModeloControleRelatorioDiario{" +
                "id='" + id + '\'' +
                ", idReferenciaCaixa='" + idReferenciaCaixa + '\'' +
                ", quantidadeDeBolosVendidos=" + quantidadeDeBolosVendidos +
                ", lucroAproximado=" + lucroAproximado +
                ", custoAproximado=" + custoAproximado +
                ", totalVendido=" + totalVendido +
                '}';
    }
}
