package com.marcel.a.n.roxha.deliciasdamamae.model;

import java.io.Serializable;

public class ModeloControleRelatorioMensal implements Serializable {

    private String id;
    private String idMontanteMensal;
    private long quantidadeDeVendasNesseMes;
    private double valorDeVendasNesseMes;
    private double lucroAproximadoDeVendasNesseMes;
    private double despesasDeVendasNesseMes;
    private double gastoComComprasNoMercadoParaVendasNesseMes;

    public ModeloControleRelatorioMensal() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdMontanteMensal() {
        return idMontanteMensal;
    }

    public void setIdMontanteMensal(String idMontanteMensal) {
        this.idMontanteMensal = idMontanteMensal;
    }

    public long getQuantidadeDeVendasNesseMes() {
        return quantidadeDeVendasNesseMes;
    }

    public void setQuantidadeDeVendasNesseMes(long quantidadeDeVendasNesseMes) {
        this.quantidadeDeVendasNesseMes = quantidadeDeVendasNesseMes;
    }

    public double getValorDeVendasNesseMes() {
        return valorDeVendasNesseMes;
    }

    public void setValorDeVendasNesseMes(double valorDeVendasNesseMes) {
        this.valorDeVendasNesseMes = valorDeVendasNesseMes;
    }

    public double getLucroAproximadoDeVendasNesseMes() {
        return lucroAproximadoDeVendasNesseMes;
    }

    public void setLucroAproximadoDeVendasNesseMes(double lucroAproximadoDeVendasNesseMes) {
        this.lucroAproximadoDeVendasNesseMes = lucroAproximadoDeVendasNesseMes;
    }

    public double getDespesasDeVendasNesseMes() {
        return despesasDeVendasNesseMes;
    }

    public void setDespesasDeVendasNesseMes(double despesasDeVendasNesseMes) {
        this.despesasDeVendasNesseMes = despesasDeVendasNesseMes;
    }

    public double getGastoComComprasNoMercadoParaVendasNesseMes() {
        return gastoComComprasNoMercadoParaVendasNesseMes;
    }

    public void setGastoComComprasNoMercadoParaVendasNesseMes(double gastoComComprasNoMercadoParaVendasNesseMes) {
        this.gastoComComprasNoMercadoParaVendasNesseMes = gastoComComprasNoMercadoParaVendasNesseMes;
    }

    @Override
    public String toString() {
        return "ModeloControleRelatorioMensal{" +
                "id='" + id + '\'' +
                ", idMontanteMensal='" + idMontanteMensal + '\'' +
                ", quantidadeDeVendasNesseMes=" + quantidadeDeVendasNesseMes +
                ", valorDeVendasNesseMes=" + valorDeVendasNesseMes +
                ", lucroAproximadoDeVendasNesseMes=" + lucroAproximadoDeVendasNesseMes +
                ", despesasDeVendasNesseMes=" + despesasDeVendasNesseMes +
                ", gastoComComprasNoMercadoParaVendasNesseMes=" + gastoComComprasNoMercadoParaVendasNesseMes +
                '}';
    }
}
