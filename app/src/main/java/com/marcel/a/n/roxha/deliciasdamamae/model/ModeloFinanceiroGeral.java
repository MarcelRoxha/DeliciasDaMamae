package com.marcel.a.n.roxha.deliciasdamamae.model;

import java.io.Serializable;

public class ModeloFinanceiroGeral implements Serializable {

    private double porcentagemDescontoMaquininhaCartaoCredito;
    private double porcentagemDescontoMaquininhaCartaoDebito;
    private double valorTotalDeDescontoAvulsoIfood;

    private double valorTotalDeComprasComIngredientesDesseMes;

    private double valorTotalDeCustoComIngredientesAteOMomentoDeVendas;
    private double valorTotalGastosAvulsosLoja;
    private double valorTotalGastosFixos;

    public ModeloFinanceiroGeral() {
    }

    public double getPorcentagemDescontoMaquininhaCartaoCredito() {
        return porcentagemDescontoMaquininhaCartaoCredito;
    }

    public void setPorcentagemDescontoMaquininhaCartaoCredito(double porcentagemDescontoMaquininhaCartaoCredito) {
        this.porcentagemDescontoMaquininhaCartaoCredito = porcentagemDescontoMaquininhaCartaoCredito;
    }

    public double getPorcentagemDescontoMaquininhaCartaoDebito() {
        return porcentagemDescontoMaquininhaCartaoDebito;
    }

    public void setPorcentagemDescontoMaquininhaCartaoDebito(double porcentagemDescontoMaquininhaCartaoDebito) {
        this.porcentagemDescontoMaquininhaCartaoDebito = porcentagemDescontoMaquininhaCartaoDebito;
    }

    public double getValorTotalDeDescontoAvulsoIfood() {
        return valorTotalDeDescontoAvulsoIfood;
    }

    public void setValorTotalDeDescontoAvulsoIfood(double valorTotalDeDescontoAvulsoIfood) {
        this.valorTotalDeDescontoAvulsoIfood = valorTotalDeDescontoAvulsoIfood;
    }

    public double getValorTotalDeComprasComIngredientesDesseMes() {
        return valorTotalDeComprasComIngredientesDesseMes;
    }

    public void setValorTotalDeComprasComIngredientesDesseMes(double valorTotalDeComprasComIngredientesDesseMes) {
        this.valorTotalDeComprasComIngredientesDesseMes = valorTotalDeComprasComIngredientesDesseMes;
    }

    public double getValorTotalDeCustoComIngredientesAteOMomentoDeVendas() {
        return valorTotalDeCustoComIngredientesAteOMomentoDeVendas;
    }

    public void setValorTotalDeCustoComIngredientesAteOMomentoDeVendas(double valorTotalDeCustoComIngredientesAteOMomentoDeVendas) {
        this.valorTotalDeCustoComIngredientesAteOMomentoDeVendas = valorTotalDeCustoComIngredientesAteOMomentoDeVendas;
    }

    public double getValorTotalGastosAvulsosLoja() {
        return valorTotalGastosAvulsosLoja;
    }

    public void setValorTotalGastosAvulsosLoja(double valorTotalGastosAvulsosLoja) {
        this.valorTotalGastosAvulsosLoja = valorTotalGastosAvulsosLoja;
    }

    public double getValorTotalGastosFixos() {
        return valorTotalGastosFixos;
    }

    public void setValorTotalGastosFixos(double valorTotalGastosFixos) {
        this.valorTotalGastosFixos = valorTotalGastosFixos;
    }

    @Override
    public String toString() {
        return "ModeloFinanceiroGeral{" +
                "porcentagemDescontoMaquininhaCartaoCredito=" + porcentagemDescontoMaquininhaCartaoCredito +
                ", porcentagemDescontoMaquininhaCartaoDebito=" + porcentagemDescontoMaquininhaCartaoDebito +
                ", valorTotalDeDescontoAvulsoIfood=" + valorTotalDeDescontoAvulsoIfood +
                ", valorTotalDeComprasComIngredientesDesseMes=" + valorTotalDeComprasComIngredientesDesseMes +
                ", valorTotalDeCustoComIngredientesAteOMomentoDeVendas=" + valorTotalDeCustoComIngredientesAteOMomentoDeVendas +
                ", valorTotalGastosAvulsosLoja=" + valorTotalGastosAvulsosLoja +
                ", valorTotalGastosFixos=" + valorTotalGastosFixos +
                '}';
    }
}
