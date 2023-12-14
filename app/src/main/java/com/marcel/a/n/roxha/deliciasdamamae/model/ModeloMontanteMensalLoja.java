package com.marcel.a.n.roxha.deliciasdamamae.model;

import java.io.Serializable;

public class ModeloMontanteMensalLoja implements Serializable {

    private String idMontante;
    private String mesReferenciaDesseMontante;
    private String valorTotalVendasBoleriaMensal;
    private String valorTotalVendasIfoodMensal;
    private String valorTotalVendasEmGeralMensal;
    private String quantoDinheiroEntrouEsseMes;
    private String quantoDinheiroSaiuEsseMes;
    private String valorQueOMontanteIniciouPositivo;
    private String valorQueOMontanteIniciouNegativo;
    private String valorTotalDeVendasNoDinheiroDesseMes;
    private String valorTotalDeVendasNoCreditoDesseMes;
    private String valorTotalDeVendasNoDebitoDesseMes;

    private int quantidadeDeVendasDesseMes;

    public ModeloMontanteMensalLoja() {
    }

    public ModeloMontanteMensalLoja(String idMontante, String mesReferenciaDesseMontante, String valorTotalVendasBoleriaMensal, String valorTotalVendasIfoodMensal, String valorTotalVendasEmGeralMensal, String quantoDinheiroEntrouEsseMes, String quantoDinheiroSaiuEsseMes, String valorQueOMontanteIniciouPositivo, String valorQueOMontanteIniciouNegativo, String valorTotalDeVendasNoDinheiroDesseMes, String valorTotalDeVendasNoCreditoDesseMes, String valorTotalDeVendasNoDebitoDesseMes) {
        this.idMontante = idMontante;
        this.mesReferenciaDesseMontante = mesReferenciaDesseMontante;
        this.valorTotalVendasBoleriaMensal = valorTotalVendasBoleriaMensal;
        this.valorTotalVendasIfoodMensal = valorTotalVendasIfoodMensal;
        this.valorTotalVendasEmGeralMensal = valorTotalVendasEmGeralMensal;
        this.quantoDinheiroEntrouEsseMes = quantoDinheiroEntrouEsseMes;
        this.quantoDinheiroSaiuEsseMes = quantoDinheiroSaiuEsseMes;
        this.valorQueOMontanteIniciouPositivo = valorQueOMontanteIniciouPositivo;
        this.valorQueOMontanteIniciouNegativo = valorQueOMontanteIniciouNegativo;
        this.valorTotalDeVendasNoDinheiroDesseMes = valorTotalDeVendasNoDinheiroDesseMes;
        this.valorTotalDeVendasNoCreditoDesseMes = valorTotalDeVendasNoCreditoDesseMes;
        this.valorTotalDeVendasNoDebitoDesseMes = valorTotalDeVendasNoDebitoDesseMes;
    }

    public String getIdMontante() {
        return idMontante;
    }

    public void setIdMontante(String idMontante) {
        this.idMontante = idMontante;
    }

    public String getValorTotalVendasBoleriaMensal() {
        return valorTotalVendasBoleriaMensal;
    }

    public void setValorTotalVendasBoleriaMensal(String valorTotalVendasBoleriaMensal) {
        this.valorTotalVendasBoleriaMensal = valorTotalVendasBoleriaMensal;
    }

    public String getValorTotalVendasIfoodMensal() {
        return valorTotalVendasIfoodMensal;
    }

    public void setValorTotalVendasIfoodMensal(String valorTotalVendasIfoodMensal) {
        this.valorTotalVendasIfoodMensal = valorTotalVendasIfoodMensal;
    }

    public String getValorTotalVendasEmGeralMensal() {
        return valorTotalVendasEmGeralMensal;
    }

    public void setValorTotalVendasEmGeralMensal(String valorTotalVendasEmGeralMensal) {
        this.valorTotalVendasEmGeralMensal = valorTotalVendasEmGeralMensal;
    }

    public String getQuantoDinheiroEntrouEsseMes() {
        return quantoDinheiroEntrouEsseMes;
    }

    public void setQuantoDinheiroEntrouEsseMes(String quantoDinheiroEntrouEsseMes) {
        this.quantoDinheiroEntrouEsseMes = quantoDinheiroEntrouEsseMes;
    }

    public String getQuantoDinheiroSaiuEsseMes() {
        return quantoDinheiroSaiuEsseMes;
    }

    public void setQuantoDinheiroSaiuEsseMes(String quantoDinheiroSaiuEsseMes) {
        this.quantoDinheiroSaiuEsseMes = quantoDinheiroSaiuEsseMes;
    }

    public String getMesReferenciaDesseMontante() {
        return mesReferenciaDesseMontante;
    }

    public void setMesReferenciaDesseMontante(String dataReferenciaDesseMontante) {
        this.mesReferenciaDesseMontante = dataReferenciaDesseMontante;
    }

    public String getValorQueOMontanteIniciouPositivo() {
        return valorQueOMontanteIniciouPositivo;
    }

    public void setValorQueOMontanteIniciouPositivo(String valorQueOMontanteIniciouPositivo) {
        this.valorQueOMontanteIniciouPositivo = valorQueOMontanteIniciouPositivo;
    }

    public String getValorQueOMontanteIniciouNegativo() {
        return valorQueOMontanteIniciouNegativo;
    }

    public void setValorQueOMontanteIniciouNegativo(String valorQueOMontanteIniciouNegativo) {
        this.valorQueOMontanteIniciouNegativo = valorQueOMontanteIniciouNegativo;
    }

    public String getValorTotalDeVendasNoDinheiroDesseMes() {
        return valorTotalDeVendasNoDinheiroDesseMes;
    }

    public void setValorTotalDeVendasNoDinheiroDesseMes(String valorTotalDeVendasNoDinheiroDesseMes) {
        this.valorTotalDeVendasNoDinheiroDesseMes = valorTotalDeVendasNoDinheiroDesseMes;
    }

    public String getValorTotalDeVendasNoCreditoDesseMes() {
        return valorTotalDeVendasNoCreditoDesseMes;
    }

    public void setValorTotalDeVendasNoCreditoDesseMes(String valorTotalDeVendasNoCreditoDesseMes) {
        this.valorTotalDeVendasNoCreditoDesseMes = valorTotalDeVendasNoCreditoDesseMes;
    }

    public String getValorTotalDeVendasNoDebitoDesseMes() {
        return valorTotalDeVendasNoDebitoDesseMes;
    }

    public void setValorTotalDeVendasNoDebitoDesseMes(String valorTotalDeVendasNoDebitoDesseMes) {
        this.valorTotalDeVendasNoDebitoDesseMes = valorTotalDeVendasNoDebitoDesseMes;
    }

    public int getQuantidadeDeVendasDesseMes() {
        return quantidadeDeVendasDesseMes;
    }

    public void setQuantidadeDeVendasDesseMes(int quantidadeDeVendasDesseMes) {
        this.quantidadeDeVendasDesseMes = quantidadeDeVendasDesseMes;
    }

    @Override
    public String toString() {
        return "ModeloMontanteMensalLoja{" +
                "idMontante='" + idMontante + '\'' +
                ", mesReferenciaDesseMontante='" + mesReferenciaDesseMontante + '\'' +
                ", valorTotalVendasBoleriaMensal='" + valorTotalVendasBoleriaMensal + '\'' +
                ", valorTotalVendasIfoodMensal='" + valorTotalVendasIfoodMensal + '\'' +
                ", valorTotalVendasEmGeralMensal='" + valorTotalVendasEmGeralMensal + '\'' +
                ", quantoDinheiroEntrouEsseMes='" + quantoDinheiroEntrouEsseMes + '\'' +
                ", quantoDinheiroSaiuEsseMes='" + quantoDinheiroSaiuEsseMes + '\'' +
                ", valorQueOMontanteIniciouPositivo='" + valorQueOMontanteIniciouPositivo + '\'' +
                ", valorQueOMontanteIniciouNegativo='" + valorQueOMontanteIniciouNegativo + '\'' +
                ", valorTotalDeVendasNoDinheiroDesseMes='" + valorTotalDeVendasNoDinheiroDesseMes + '\'' +
                ", valorTotalDeVendasNoCreditoDesseMes='" + valorTotalDeVendasNoCreditoDesseMes + '\'' +
                ", valorTotalDeVendasNoDebitoDesseMes='" + valorTotalDeVendasNoDebitoDesseMes + '\'' +
                ", quantidadeDeVendasDesseMes=" + quantidadeDeVendasDesseMes +
                '}';
    }
}
