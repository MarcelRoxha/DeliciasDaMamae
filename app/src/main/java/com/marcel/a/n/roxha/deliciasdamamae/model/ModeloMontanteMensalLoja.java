package com.marcel.a.n.roxha.deliciasdamamae.model;

import java.io.Serializable;

public class ModeloMontanteMensalLoja implements Serializable {

    private String idMontante;
    private String dataReferenciaDesseMontante;
    private String valorTotalVendasBoleriaMensal;
    private String valorTotalVendasIfoodMensal;
    private String valorTotalVendasEmGeralMensal;
    private String quantoDinheiroEntrouEsseMes;
    private String quantoDinheiroSaiuEsseMes;

    public ModeloMontanteMensalLoja() {
    }

    public ModeloMontanteMensalLoja(String idMontante, String valorTotalVendasBoleriaMensal, String valorTotalVendasIfoodMensal, String valorTotalVendasEmGeralMensal, String quantoDinheiroEntrouEsseMes, String quantoDinheiroSaiuEsseMes) {
        this.idMontante = idMontante;
        this.valorTotalVendasBoleriaMensal = valorTotalVendasBoleriaMensal;
        this.valorTotalVendasIfoodMensal = valorTotalVendasIfoodMensal;
        this.valorTotalVendasEmGeralMensal = valorTotalVendasEmGeralMensal;
        this.quantoDinheiroEntrouEsseMes = quantoDinheiroEntrouEsseMes;
        this.quantoDinheiroSaiuEsseMes = quantoDinheiroSaiuEsseMes;
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

    @Override
    public String toString() {
        return "ModeloMontanteMensalLoja{" +
                "idMontante='" + idMontante + '\'' +
                ", valorTotalVendasBoleriaMensal='" + valorTotalVendasBoleriaMensal + '\'' +
                ", valorTotalVendasIfoodMensal='" + valorTotalVendasIfoodMensal + '\'' +
                ", valorTotalVendasEmGeralMensal='" + valorTotalVendasEmGeralMensal + '\'' +
                ", quantoDinheiroEntrouEsseMes='" + quantoDinheiroEntrouEsseMes + '\'' +
                ", quantoDinheiroSaiuEsseMes='" + quantoDinheiroSaiuEsseMes + '\'' +
                '}';
    }
}
