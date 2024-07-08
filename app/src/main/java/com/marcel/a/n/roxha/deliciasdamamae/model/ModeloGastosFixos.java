package com.marcel.a.n.roxha.deliciasdamamae.model;

import java.io.Serializable;

public class ModeloGastosFixos implements Serializable {

    private String nomeDoGastoFixo;
    private String dataPrevistaDePagamento;
    private String dataPagamento;
    private double valorGastoFixo;
    private boolean verificaSeOPagamentoRepeteNoMes;

    public ModeloGastosFixos() {

    }

    public String getNomeDoGastoFixo() {
        return nomeDoGastoFixo;
    }

    public void setNomeDoGastoFixo(String nomeDoGastoFixo) {
        this.nomeDoGastoFixo = nomeDoGastoFixo;
    }

    public String getDataPrevistaDePagamento() {
        return dataPrevistaDePagamento;
    }

    public void setDataPrevistaDePagamento(String dataPrevistaDePagamento) {
        this.dataPrevistaDePagamento = dataPrevistaDePagamento;
    }

    public String getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(String dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public double getValorGastoFixo() {
        return valorGastoFixo;
    }

    public void setValorGastoFixo(double valorGastoFixo) {
        this.valorGastoFixo = valorGastoFixo;
    }

    public boolean isVerificaSeOPagamentoRepeteNoMes() {
        return verificaSeOPagamentoRepeteNoMes;
    }

    public void setVerificaSeOPagamentoRepeteNoMes(boolean verificaSeOPagamentoRepeteNoMes) {
        this.verificaSeOPagamentoRepeteNoMes = verificaSeOPagamentoRepeteNoMes;
    }

    @Override
    public String toString() {
        return "ModeloGastosFixos{" +
                "nomeDoGastoFixo='" + nomeDoGastoFixo + '\'' +
                ", dataPrevistaDePagamento='" + dataPrevistaDePagamento + '\'' +
                ", dataPagamento='" + dataPagamento + '\'' +
                ", valorGastoFixo=" + valorGastoFixo +
                ", verificaSeOPagamentoRepeteNoMes=" + verificaSeOPagamentoRepeteNoMes +
                '}';
    }
}
