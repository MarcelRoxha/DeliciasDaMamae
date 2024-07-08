package com.marcel.a.n.roxha.deliciasdamamae.model;

import java.io.Serializable;

public class ModeloGastosAvulsos implements Serializable {

    private String nomeDoGastoAvulsos;
    private String dataDeRegistroGasto;
    private String dataPagamentoAvulsos;
    private double valorGastoAvulsos;

    public ModeloGastosAvulsos() {
    }

    public String getNomeDoGastoAvulsos() {
        return nomeDoGastoAvulsos;
    }

    public void setNomeDoGastoAvulsos(String nomeDoGastoAvulsos) {
        this.nomeDoGastoAvulsos = nomeDoGastoAvulsos;
    }

    public String getDataDeRegistroGasto() {
        return dataDeRegistroGasto;
    }

    public void setDataDeRegistroGasto(String dataDeRegistroGasto) {
        this.dataDeRegistroGasto = dataDeRegistroGasto;
    }

    public String getDataPagamentoAvulsos() {
        return dataPagamentoAvulsos;
    }

    public void setDataPagamentoAvulsos(String dataPagamentoAvulsos) {
        this.dataPagamentoAvulsos = dataPagamentoAvulsos;
    }

    public double getValorGastoAvulsos() {
        return valorGastoAvulsos;
    }

    public void setValorGastoAvulsos(double valorGastoAvulsos) {
        this.valorGastoAvulsos = valorGastoAvulsos;
    }

    @Override
    public String toString() {
        return "ModeloGastosAvulsos{" +
                "nomeDoGastoAvulsos='" + nomeDoGastoAvulsos + '\'' +
                ", dataDeRegistroGasto='" + dataDeRegistroGasto + '\'' +
                ", dataPagamentoAvulsos='" + dataPagamentoAvulsos + '\'' +
                ", valorGastoAvulsos=" + valorGastoAvulsos +
                '}';
    }
}
