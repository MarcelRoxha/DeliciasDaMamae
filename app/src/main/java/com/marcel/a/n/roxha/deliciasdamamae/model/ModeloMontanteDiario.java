package com.marcel.a.n.roxha.deliciasdamamae.model;

import java.io.Serializable;

public class ModeloMontanteDiario implements Serializable {

    private String idReferenciaMontanteDiarioDesseDia;
    private String dataReferenciaMontanteDiarioDesseDia;
    private String valorQueOCaixaIniciouODia;
    private String valorTotalDeVendasNaLojaDesseDia;
    private String valorTotalDeVendasNoIfoodDesseDia;
    private String valorTotalDeVendasEmGeralDesseDia;
    private String valorTotalDeVendasNoDinheiroDesseDia;
    private String valorTotalDeVendasNoCreditoDesseDia;
    private String valorTotalDeVendasNoDebitoDesseDia;
    private String valorTotalDeTrocoDesseDia;

    public ModeloMontanteDiario() {
    }

    public ModeloMontanteDiario(String idReferenciaMontanteDiarioDesseDia, String dataReferenciaMontanteDiarioDesseDia, String valorQueOCaixaIniciouODia, String valorTotalDeVendasNaLojaDesseDia, String valorTotalDeVendasNoIfoodDesseDia, String valorTotalDeVendasEmGeralDesseDia, String valorTotalDeVendasNoDinheiroDesseDia, String valorTotalDeVendasNoCreditoDesseDia, String valorTotalDeVendasNoDebitoDesseDia, String valorTotalDeTrocoDesseDia) {
        this.idReferenciaMontanteDiarioDesseDia = idReferenciaMontanteDiarioDesseDia;
        this.dataReferenciaMontanteDiarioDesseDia = dataReferenciaMontanteDiarioDesseDia;
        this.valorQueOCaixaIniciouODia = valorQueOCaixaIniciouODia;
        this.valorTotalDeVendasNaLojaDesseDia = valorTotalDeVendasNaLojaDesseDia;
        this.valorTotalDeVendasNoIfoodDesseDia = valorTotalDeVendasNoIfoodDesseDia;
        this.valorTotalDeVendasEmGeralDesseDia = valorTotalDeVendasEmGeralDesseDia;
        this.valorTotalDeVendasNoDinheiroDesseDia = valorTotalDeVendasNoDinheiroDesseDia;
        this.valorTotalDeVendasNoCreditoDesseDia = valorTotalDeVendasNoCreditoDesseDia;
        this.valorTotalDeVendasNoDebitoDesseDia = valorTotalDeVendasNoDebitoDesseDia;
        this.valorTotalDeTrocoDesseDia = valorTotalDeTrocoDesseDia;
    }

    public String getIdReferenciaMontanteDiarioDesseDia() {
        return idReferenciaMontanteDiarioDesseDia;
    }

    public void setIdReferenciaMontanteDiarioDesseDia(String idReferenciaMontanteDiarioDesseDia) {
        this.idReferenciaMontanteDiarioDesseDia = idReferenciaMontanteDiarioDesseDia;
    }

    public String getDataReferenciaMontanteDiarioDesseDia() {
        return dataReferenciaMontanteDiarioDesseDia;
    }

    public void setDataReferenciaMontanteDiarioDesseDia(String dataReferenciaMontanteDiarioDesseDia) {
        this.dataReferenciaMontanteDiarioDesseDia = dataReferenciaMontanteDiarioDesseDia;
    }

    public String getValorQueOCaixaIniciouODia() {
        return valorQueOCaixaIniciouODia;
    }

    public void setValorQueOCaixaIniciouODia(String valorQueOCaixaIniciouODia) {
        this.valorQueOCaixaIniciouODia = valorQueOCaixaIniciouODia;
    }

    public String getValorTotalDeVendasNaLojaDesseDia() {
        return valorTotalDeVendasNaLojaDesseDia;
    }

    public void setValorTotalDeVendasNaLojaDesseDia(String valorTotalDeVendasNaLojaDesseDia) {
        this.valorTotalDeVendasNaLojaDesseDia = valorTotalDeVendasNaLojaDesseDia;
    }

    public String getValorTotalDeVendasNoIfoodDesseDia() {
        return valorTotalDeVendasNoIfoodDesseDia;
    }

    public void setValorTotalDeVendasNoIfoodDesseDia(String valorTotalDeVendasNoIfoodDesseDia) {
        this.valorTotalDeVendasNoIfoodDesseDia = valorTotalDeVendasNoIfoodDesseDia;
    }

    public String getValorTotalDeVendasEmGeralDesseDia() {
        return valorTotalDeVendasEmGeralDesseDia;
    }

    public void setValorTotalDeVendasEmGeralDesseDia(String valorTotalDeVendasEmGeralDesseDia) {
        this.valorTotalDeVendasEmGeralDesseDia = valorTotalDeVendasEmGeralDesseDia;
    }

    public String getValorTotalDeVendasNoDinheiroDesseDia() {
        return valorTotalDeVendasNoDinheiroDesseDia;
    }

    public void setValorTotalDeVendasNoDinheiroDesseDia(String valorTotalDeVendasNoDinheiroDesseDia) {
        this.valorTotalDeVendasNoDinheiroDesseDia = valorTotalDeVendasNoDinheiroDesseDia;
    }

    public String getValorTotalDeVendasNoCreditoDesseDia() {
        return valorTotalDeVendasNoCreditoDesseDia;
    }

    public void setValorTotalDeVendasNoCreditoDesseDia(String valorTotalDeVendasNoCreditoDesseDia) {
        this.valorTotalDeVendasNoCreditoDesseDia = valorTotalDeVendasNoCreditoDesseDia;
    }

    public String getValorTotalDeVendasNoDebitoDesseDia() {
        return valorTotalDeVendasNoDebitoDesseDia;
    }

    public void setValorTotalDeVendasNoDebitoDesseDia(String valorTotalDeVendasNoDebitoDesseDia) {
        this.valorTotalDeVendasNoDebitoDesseDia = valorTotalDeVendasNoDebitoDesseDia;
    }

    public String getValorTotalDeTrocoDesseDia() {
        return valorTotalDeTrocoDesseDia;
    }

    public void setValorTotalDeTrocoDesseDia(String valorTotalDeTrocoDesseDia) {
        this.valorTotalDeTrocoDesseDia = valorTotalDeTrocoDesseDia;
    }

    @Override
    public String toString() {
        return "ModeloMontanteDiario{" +
                "idReferenciaMontanteDiarioDesseDia='" + idReferenciaMontanteDiarioDesseDia + '\'' +
                ", dataReferenciaMontanteDiarioDesseDia='" + dataReferenciaMontanteDiarioDesseDia + '\'' +
                ", valorQueOCaixaIniciouODia='" + valorQueOCaixaIniciouODia + '\'' +
                ", valorTotalDeVendasNaLojaDesseDia='" + valorTotalDeVendasNaLojaDesseDia + '\'' +
                ", valorTotalDeVendasNoIfoodDesseDia='" + valorTotalDeVendasNoIfoodDesseDia + '\'' +
                ", valorTotalDeVendasEmGeralDesseDia='" + valorTotalDeVendasEmGeralDesseDia + '\'' +
                ", valorTotalDeVendasNoDinheiroDesseDia='" + valorTotalDeVendasNoDinheiroDesseDia + '\'' +
                ", valorTotalDeVendasNoCreditoDesseDia='" + valorTotalDeVendasNoCreditoDesseDia + '\'' +
                ", valorTotalDeVendasNoDebitoDesseDia='" + valorTotalDeVendasNoDebitoDesseDia + '\'' +
                ", valorTotalDeTrocoDesseDia='" + valorTotalDeTrocoDesseDia + '\'' +
                '}';
    }
}
