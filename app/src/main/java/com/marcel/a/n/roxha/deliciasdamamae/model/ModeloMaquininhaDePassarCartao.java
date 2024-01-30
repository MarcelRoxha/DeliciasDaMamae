package com.marcel.a.n.roxha.deliciasdamamae.model;

import java.io.Serializable;

public class ModeloMaquininhaDePassarCartao implements Serializable {
    
    private String id;
    private String nomeDaMaquininha;
    private double porcentagemDeDescontoCredito;
    private double porcentagemDeDescontoDebito;
    private String dataPrevistaParaPagamentoDosValoresPassados;

    public ModeloMaquininhaDePassarCartao() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNomeDaMaquininha() {
        return nomeDaMaquininha;
    }

    public void setNomeDaMaquininha(String nomeDaMaquininha) {
        this.nomeDaMaquininha = nomeDaMaquininha;
    }

    public double getPorcentagemDeDescontoCredito() {
        return porcentagemDeDescontoCredito;
    }

    public void setPorcentagemDeDescontoCredito(double porcentagemDeDescontoCredito) {
        this.porcentagemDeDescontoCredito = porcentagemDeDescontoCredito;
    }

    public double getPorcentagemDeDescontoDebito() {
        return porcentagemDeDescontoDebito;
    }

    public void setPorcentagemDeDescontoDebito(double porcentagemDeDescontoDebito) {
        this.porcentagemDeDescontoDebito = porcentagemDeDescontoDebito;
    }

    public String getDataPrevistaParaPagamentoDosValoresPassados() {
        return dataPrevistaParaPagamentoDosValoresPassados;
    }

    public void setDataPrevistaParaPagamentoDosValoresPassados(String dataPrevistaParaPagamentoDosValoresPassados) {
        this.dataPrevistaParaPagamentoDosValoresPassados = dataPrevistaParaPagamentoDosValoresPassados;
    }

    @Override
    public String toString() {
        return "ModeloMaquininhaDePassarCartao{" +
                "id='" + id + '\'' +
                ", nomeDaMaquininha='" + nomeDaMaquininha + '\'' +
                ", porcentagemDeDescontoCredito=" + porcentagemDeDescontoCredito +
                ", porcentagemDeDescontoDebito=" + porcentagemDeDescontoDebito +
                ", dataPrevistaParaPagamentoDosValoresPassados='" + dataPrevistaParaPagamentoDosValoresPassados + '\'' +
                '}';
    }
}
