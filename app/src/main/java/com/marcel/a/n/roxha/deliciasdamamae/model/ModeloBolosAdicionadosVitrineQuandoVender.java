package com.marcel.a.n.roxha.deliciasdamamae.model;

import java.io.Serializable;

public class ModeloBolosAdicionadosVitrineQuandoVender implements Serializable {

    private String idModeloBoloAdicionadosVitrineQuandoVender;
    private String idReferenciaBoloCadastradoParaVenda ;

    private String nomeBoloCadastrado;
    private String precoQueFoiVendido;
    private String precoCadastradoVendaNaLoja;
    private String precoCadastradoVendaIfood;

    private String valorSugeridoParaVendaNaBoleria;

    private String valorSugeridoParaVendaIfood;

    private String custoTotalDaReceita;

    private String enderecoFoto;
    private boolean vendeuNoIfood = false;
    private boolean vendeuNaLoja = false;




    public ModeloBolosAdicionadosVitrineQuandoVender() {
    }

    public ModeloBolosAdicionadosVitrineQuandoVender(String idReferenciaBoloCadastradoParaVenda, String nomeDoBoloVendido, String precoQueFoiVendido, String precoCadastradoVendaNaLoja, String precoCadastradoVendaIfood, String valorSugeridoParaVendaNaBoleria, String valorSugeridoParaVendaIfood, String custoTotalDaReceita,  boolean vendeuNoIfood, boolean vendeuNaLoja, String enderecoFoto, String idModeloBoloAdicionadosVitrineQuandoVender) {
        this.idReferenciaBoloCadastradoParaVenda = idReferenciaBoloCadastradoParaVenda;
        this.nomeBoloCadastrado = nomeDoBoloVendido;
        this.precoQueFoiVendido = precoQueFoiVendido;
        this.precoCadastradoVendaNaLoja = precoCadastradoVendaNaLoja;
        this.precoCadastradoVendaIfood = precoCadastradoVendaIfood;
        this.valorSugeridoParaVendaNaBoleria = valorSugeridoParaVendaNaBoleria;
        this.valorSugeridoParaVendaIfood = valorSugeridoParaVendaIfood;
        this.vendeuNoIfood = vendeuNoIfood;
        this.vendeuNaLoja = vendeuNaLoja;
        this.enderecoFoto = enderecoFoto;
        this.idModeloBoloAdicionadosVitrineQuandoVender = idModeloBoloAdicionadosVitrineQuandoVender;
        this.custoTotalDaReceita = custoTotalDaReceita;
    }

    public String getIdModeloBoloAdicionadosVitrineQuandoVender() {
        return idModeloBoloAdicionadosVitrineQuandoVender;
    }

    public void setIdModeloBoloAdicionadosVitrineQuandoVender(String idModeloBoloAdicionadosVitrineQuandoVender) {
        this.idModeloBoloAdicionadosVitrineQuandoVender = idModeloBoloAdicionadosVitrineQuandoVender;
    }

    public String getIdReferenciaBoloCadastradoParaVenda() {
        return idReferenciaBoloCadastradoParaVenda;
    }

    public void setIdReferenciaBoloCadastradoParaVenda(String idReferenciaBoloCadastradoParaVenda) {
        this.idReferenciaBoloCadastradoParaVenda = idReferenciaBoloCadastradoParaVenda;
    }

    public String getNomeBoloCadastrado() {
        return nomeBoloCadastrado;
    }

    public void setNomeBoloCadastrado(String nomeDoBoloVendido) {
        this.nomeBoloCadastrado = nomeDoBoloVendido;
    }

    public String getPrecoQueFoiVendido() {
        return precoQueFoiVendido;
    }

    public void setPrecoQueFoiVendido(String precoQueFoiVendido) {
        this.precoQueFoiVendido = precoQueFoiVendido;
    }

    public String getPrecoCadastradoVendaNaLoja() {
        return precoCadastradoVendaNaLoja;
    }

    public void setPrecoCadastradoVendaNaLoja(String precoCadastradoVendaNaLoja) {
        this.precoCadastradoVendaNaLoja = precoCadastradoVendaNaLoja;
    }

    public String getPrecoCadastradoVendaIfood() {
        return precoCadastradoVendaIfood;
    }

    public void setPrecoCadastradoVendaIfood(String precoCadastradoVendaIfood) {
        this.precoCadastradoVendaIfood = precoCadastradoVendaIfood;
    }

    public String getEnderecoFoto() {
        return enderecoFoto;
    }

    public void setEnderecoFoto(String enderecoFoto) {
        this.enderecoFoto = enderecoFoto;
    }

    public boolean isVendeuNoIfood() {
        return vendeuNoIfood;
    }

    public void setVendeuNoIfood(boolean vendeuNoIfood) {
        this.vendeuNoIfood = vendeuNoIfood;
    }

    public boolean isVendeuNaLoja() {
        return vendeuNaLoja;
    }

    public void setVendeuNaLoja(boolean vendeuNaLoja) {
        this.vendeuNaLoja = vendeuNaLoja;
    }

    public String getValorSugeridoParaVendaNaBoleria() {
        return valorSugeridoParaVendaNaBoleria;
    }

    public void setValorSugeridoParaVendaNaBoleria(String valorSugeridoParaVendaNaBoleria) {
        this.valorSugeridoParaVendaNaBoleria = valorSugeridoParaVendaNaBoleria;
    }

    public String getValorSugeridoParaVendaIfood() {
        return valorSugeridoParaVendaIfood;
    }

    public void setValorSugeridoParaVendaIfood(String valorSugeridoParaVendaIfood) {
        this.valorSugeridoParaVendaIfood = valorSugeridoParaVendaIfood;
    }

    public String getCustoTotalDaReceita() {
        return custoTotalDaReceita;
    }

    public void setCustoTotalDaReceita(String custoTotalDaReceita) {
        this.custoTotalDaReceita = custoTotalDaReceita;
    }

    @Override
    public String toString() {
        return "ModeloBolosAdicionadosVitrineQuandoVender{" +
                "idModeloBoloAdicionadosVitrineQuandoVender='" + idModeloBoloAdicionadosVitrineQuandoVender + '\'' +
                ", idReferenciaBoloCadastradoParaVenda='" + idReferenciaBoloCadastradoParaVenda + '\'' +
                ", nomeDoBoloVendido='" + nomeBoloCadastrado + '\'' +
                ", precoQueFoiVendido='" + precoQueFoiVendido + '\'' +
                ", precoCadastradoVendaNaLoja='" + precoCadastradoVendaNaLoja + '\'' +
                ", precoCadastradoVendaIfood='" + precoCadastradoVendaIfood + '\'' +
                ", valorSugeridoParaVendaNaBoleria='" + valorSugeridoParaVendaNaBoleria + '\'' +
                ", valorSugeridoParaVendaIfood='" + valorSugeridoParaVendaIfood + '\'' +
                ", custoTotalDaReceita='" + custoTotalDaReceita + '\'' +
                ", enderecoFoto='" + enderecoFoto + '\'' +
                ", vendeuNoIfood=" + vendeuNoIfood +
                ", vendeuNaLoja=" + vendeuNaLoja +
                '}';
    }

    public class ModeloBolosAdicionadosVitrineQuandoVenderViewHolder {
    }
}
