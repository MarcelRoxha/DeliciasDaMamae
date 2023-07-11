package com.marcel.a.n.roxha.deliciasdamamae.model;

import java.io.Serializable;

public class ModeloBolosAdicionadosVitrineQuandoVender implements Serializable {

    private String nomeDoBoloVendido;
    private String precoQueFoiVendido;
    private String precoCadastradoVendaNaLoja;
    private String precoCadastradoVendaIfood;
    private boolean vendeuNoIfood;
    private boolean vendeuNaLoja;


    public ModeloBolosAdicionadosVitrineQuandoVender() {
    }

    public ModeloBolosAdicionadosVitrineQuandoVender(String nomeDoBoloVendido, String precoQueFoiVendido, String precoCadastradoVendaNaLoja, String precoCadastradoVendaIfood, boolean vendeuNoIfood, boolean vendeuNaLoja) {
        this.nomeDoBoloVendido = nomeDoBoloVendido;
        this.precoQueFoiVendido = precoQueFoiVendido;
        this.precoCadastradoVendaNaLoja = precoCadastradoVendaNaLoja;
        this.precoCadastradoVendaIfood = precoCadastradoVendaIfood;
        this.vendeuNoIfood = vendeuNoIfood;
        this.vendeuNaLoja = vendeuNaLoja;
    }

    public String getNomeDoBoloVendido() {
        return nomeDoBoloVendido;
    }

    public void setNomeDoBoloVendido(String nomeDoBoloVendido) {
        this.nomeDoBoloVendido = nomeDoBoloVendido;
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

    @Override
    public String toString() {
        return "ModeloBolosAdicionadosVitrineQuandoVender{" +
                "nomeDoBoloVendido='" + nomeDoBoloVendido + '\'' +
                ", precoQueFoiVendido='" + precoQueFoiVendido + '\'' +
                ", precoCadastradoVendaNaLoja='" + precoCadastradoVendaNaLoja + '\'' +
                ", precoCadastradoVendaIfood='" + precoCadastradoVendaIfood + '\'' +
                ", vendeuNoIfood=" + vendeuNoIfood +
                ", vendeuNaLoja=" + vendeuNaLoja +
                '}';
    }
}
