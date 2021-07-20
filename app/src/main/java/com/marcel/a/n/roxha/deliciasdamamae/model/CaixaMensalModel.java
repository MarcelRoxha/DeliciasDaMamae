package com.marcel.a.n.roxha.deliciasdamamae.model;

import java.io.Serializable;

public class CaixaMensalModel implements Serializable {

    private String identificadorCaixaMensal;
    private int mesReferencia;
    private int quantTotalBolosAdicionadosMensal;
    private double valorTotalBolosVendidosMensal;
    private double valorTotalCustosBolosVendidosMensal;
    private double totalGastoMensal;

    public CaixaMensalModel() {

    }

    public String getIdentificadorCaixaMensal() {
        return identificadorCaixaMensal;
    }

    public void setIdentificadorCaixaMensal(String id) {
        this.identificadorCaixaMensal = id;
    }

    public int getMesReferencia() {
        return mesReferencia;
    }

    public void setMesReferencia(int mesReferencia) {
        this.mesReferencia = mesReferencia;
    }

    public int getQuantTotalBolosAdicionadosMensal() {
        return quantTotalBolosAdicionadosMensal;
    }

    public void setQuantTotalBolosAdicionadosMensal(int quantTotalBolosAdicionadosMensal) {
        this.quantTotalBolosAdicionadosMensal = quantTotalBolosAdicionadosMensal;
    }

    public double getValorTotalBolosVendidosMensal() {
        return valorTotalBolosVendidosMensal;
    }

    public void setValorTotalBolosVendidosMensal(double valorTotalBolosVendidosMensal) {
        this.valorTotalBolosVendidosMensal = valorTotalBolosVendidosMensal;
    }

    public double getValorTotalCustosBolosVendidosMensal() {
        return valorTotalCustosBolosVendidosMensal;
    }

    public void setValorTotalCustosBolosVendidosMensal(double valorTotalCustosBolosVendidosMensal) {
        this.valorTotalCustosBolosVendidosMensal = valorTotalCustosBolosVendidosMensal;
    }

    public double getTotalGastoMensal() {
        return totalGastoMensal;
    }

    public void setTotalGastoMensal(double totalGastoMensal) {
        this.totalGastoMensal = totalGastoMensal;
    }
}
