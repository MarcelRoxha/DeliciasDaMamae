package com.marcel.a.n.roxha.deliciasdamamae.model;

import java.io.Serializable;

public class ModeloProdutoVendido implements Serializable {

    private String idDoProdutoVendido;
    private String idDoProdutoCadastrado;
    private String valorQueOBoloFoiVendido;
    private String metodoDePagamento;
    private String plataformaVendida;
    private String registroDaVenda;

    public ModeloProdutoVendido() {

    }

    public String getIdDoProdutoVendido() {
        return idDoProdutoVendido;
    }

    public void setIdDoProdutoVendido(String idDoProdutoVendido) {
        this.idDoProdutoVendido = idDoProdutoVendido;
    }

    public String getIdDoProdutoCadastrado() {
        return idDoProdutoCadastrado;
    }

    public void setIdDoProdutoCadastrado(String idDoProdutoCadastrado) {
        this.idDoProdutoCadastrado = idDoProdutoCadastrado;
    }

    public String getValorQueOBoloFoiVendido() {
        return valorQueOBoloFoiVendido;
    }

    public void setValorQueOBoloFoiVendido(String valorQueOBoloFoiVendido) {
        this.valorQueOBoloFoiVendido = valorQueOBoloFoiVendido;
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

    public String getRegistroDaVenda() {
        return registroDaVenda;
    }

    public void setRegistroDaVenda(String registroDaVenda) {
        this.registroDaVenda = registroDaVenda;
    }

    @Override
    public String toString() {
        return "ModeloProdutoVendido{" +
                "idDoProdutoVendido='" + idDoProdutoVendido + '\'' +
                ", idDoProdutoCadastrado='" + idDoProdutoCadastrado + '\'' +
                ", valorQueOBoloFoiVendido='" + valorQueOBoloFoiVendido + '\'' +
                ", metodoDePagamento='" + metodoDePagamento + '\'' +
                ", plataformaVendida='" + plataformaVendida + '\'' +
                ", registroDaVenda='" + registroDaVenda + '\'' +
                '}';
    }
}
