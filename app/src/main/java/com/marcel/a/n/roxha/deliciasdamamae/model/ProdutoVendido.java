package com.marcel.a.n.roxha.deliciasdamamae.model;

import java.io.Serializable;

public class ProdutoVendido implements Serializable {

    private String idDoProdutoVendido;
    private String idReferenciaDoProduto;
    private String metodoDePagamento;
    private String plataformaVendida;
    private String dataDaVenda;

    public ProdutoVendido(String idDoProdutoVendido) {
        this.idDoProdutoVendido = idDoProdutoVendido;
    }

    public ProdutoVendido(String idDoProdutoVendido, String idReferenciaDoProduto, String metodoDePagamento, String plataformaVendida, String dataDaVenda) {
        this.idDoProdutoVendido = idDoProdutoVendido;
        this.idReferenciaDoProduto = idReferenciaDoProduto;
        this.metodoDePagamento = metodoDePagamento;
        this.plataformaVendida = plataformaVendida;
        this.dataDaVenda = dataDaVenda;

    }

    public String getIdDoProdutoVendido() {
        return idDoProdutoVendido;
    }

    public void setIdDoProdutoVendido(String idDoProdutoVendido) {
        this.idDoProdutoVendido = idDoProdutoVendido;
    }

    public String getIdReferenciaDoProduto() {
        return idReferenciaDoProduto;
    }

    public void setIdReferenciaDoProduto(String idReferenciaDoProduto) {
        this.idReferenciaDoProduto = idReferenciaDoProduto;
    }

    public String getMetodoDePagamento() {
        return metodoDePagamento;
    }

    public void setMetodoDePagamento(String metodoDePagamento) {
        this.metodoDePagamento = metodoDePagamento;
    }

    public String getPlataformaVendida() {
        return plataformaVendida;
    }

    public void setPlataformaVendida(String plataformaVendida) {
        this.plataformaVendida = plataformaVendida;
    }

    public String getDataDaVenda() {
        return dataDaVenda;
    }

    public void setDataDaVenda(String dataDaVenda) {
        this.dataDaVenda = dataDaVenda;
    }

    @Override
    public String toString() {
        return "ProdutoVendido{" +
                "idDoProdutoVendido='" + idDoProdutoVendido + '\'' +
                ", idReferenciaDoProduto='" + idReferenciaDoProduto + '\'' +
                ", metodoDePagamento='" + metodoDePagamento + '\'' +
                ", plataformaVendida='" + plataformaVendida + '\'' +
                ", dataDaVenda='" + dataDaVenda + '\'' +
                '}';
    }
}
