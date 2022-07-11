package com.marcel.a.n.roxha.deliciasdamamae.model;

import java.io.Serializable;

public class ModeloItemEstoque  implements Serializable {


    private String idItemEstoque;
    private String nomeItemEstoque;
    private String quantidadeTotalItemEstoque;
    private String quantidadePorPacoteItemEstoque;
    private String valorIndividualItemEstoque;
    private String unidadeMedidaPacoteItemEstoque;
    private String unidadeMedidaUtilizadoNasReceitas;
    private String valorFracionadoItemEstoque;
    private String custoPorReceitaItemEstoque;
    private String quantidadeUtilizadaNasReceitas;
    private String resultadoRetornado;
    private String resultadoRetornadoValorItemPorReceita;

    public ModeloItemEstoque() {
    }

    public ModeloItemEstoque(String idItemEstoque, String nomeItemEstoque, String quantidadeTotalItemEstoque, String quantidadePorPacoteItemEstoque,  String valorIndividualItemEstoque, String unidadeMedidaPacoteItemEstoque, String unidadeMedidaUtilizadoNasReceitas, String valorFracionadoItemEstoque, String custoPorReceitaItemEstoque, String quantidadeUtilizadaNasReceitas, String resultadoRetornado, String resultadoRetornadoValorItemPorReceita) {
        this.idItemEstoque = idItemEstoque;
        this.nomeItemEstoque = nomeItemEstoque;
        this.quantidadeTotalItemEstoque = quantidadeTotalItemEstoque;
        this.quantidadePorPacoteItemEstoque = quantidadePorPacoteItemEstoque;
        this.valorIndividualItemEstoque = valorIndividualItemEstoque;
        this.unidadeMedidaPacoteItemEstoque = unidadeMedidaPacoteItemEstoque;
        this.unidadeMedidaUtilizadoNasReceitas = unidadeMedidaUtilizadoNasReceitas;
        this.valorFracionadoItemEstoque = valorFracionadoItemEstoque;
        this.custoPorReceitaItemEstoque = custoPorReceitaItemEstoque;
        this.quantidadeUtilizadaNasReceitas = quantidadeUtilizadaNasReceitas;
        this.resultadoRetornado = resultadoRetornado;
        this.resultadoRetornadoValorItemPorReceita = resultadoRetornadoValorItemPorReceita;
    }

    public String getIdItemEstoque() {
        return idItemEstoque;
    }

    public void setIdItemEstoque(String idItemEstoque) {
        this.idItemEstoque = idItemEstoque;
    }

    public String getNomeItemEstoque() {
        return nomeItemEstoque;
    }

    public void setNomeItemEstoque(String nomeItemEstoque) {
        this.nomeItemEstoque = nomeItemEstoque.toUpperCase();
    }

    public String getQuantidadeTotalItemEstoque() {
        return quantidadeTotalItemEstoque;
    }

    public void setQuantidadeTotalItemEstoque(String quantidadeTotalItemEstoque) {
        this.quantidadeTotalItemEstoque = quantidadeTotalItemEstoque;
    }

    public String getValorIndividualItemEstoque() {
        return valorIndividualItemEstoque;
    }
    public String getQuantidadePorPacoteItemEstoque() {
        return quantidadePorPacoteItemEstoque;
    }

    public void setQuantidadePorPacoteItemEstoque(String quantidadePorPacoteItemEstoque) {
        this.quantidadePorPacoteItemEstoque = quantidadePorPacoteItemEstoque;
    }

    public void setValorIndividualItemEstoque(String valorIndividualItemEstoque) {
        this.valorIndividualItemEstoque = valorIndividualItemEstoque;
    }

    public String getUnidadeMedidaPacoteItemEstoque() {
        return unidadeMedidaPacoteItemEstoque;
    }

    public void setUnidadeMedidaPacoteItemEstoque(String unidadeMedidaPacoteItemEstoque) {
        this.unidadeMedidaPacoteItemEstoque = unidadeMedidaPacoteItemEstoque;
    }

    public String getUnidadeMedidaUtilizadoNasReceitas() {
        return unidadeMedidaUtilizadoNasReceitas;
    }

    public void setUnidadeMedidaUtilizadoNasReceitas(String unidadeMedidaUtilizadoNasReceitas) {
        this.unidadeMedidaUtilizadoNasReceitas = unidadeMedidaUtilizadoNasReceitas;
    }

    public String getValorFracionadoItemEstoque() {
        return valorFracionadoItemEstoque;
    }

    public void setValorFracionadoItemEstoque(String valorFracionadoItemEstoque) {
        this.valorFracionadoItemEstoque = valorFracionadoItemEstoque;
    }

    public String getCustoPorReceitaItemEstoque() {
        return custoPorReceitaItemEstoque;
    }

    public void setCustoPorReceitaItemEstoque(String custoPorReceitaItemEstoque) {
        this.custoPorReceitaItemEstoque = custoPorReceitaItemEstoque;
    }

    public String getQuantidadeUtilizadaNasReceitas() {
        return quantidadeUtilizadaNasReceitas;
    }

    public void setQuantidadeUtilizadaNasReceitas(String quantidadeUtilizadaNasReceitas) {
        this.quantidadeUtilizadaNasReceitas = quantidadeUtilizadaNasReceitas;
    }


    public String calcularValorFracionadoModeloItemEstoque(){


        String valorItemLimpo = this.valorIndividualItemEstoque.replaceAll(",", ".");
        String quantidadePorPacoteLimpo = this.quantidadePorPacoteItemEstoque.replaceAll(",", ".");

        String unidadeMedidaItemEstoque = this.unidadeMedidaPacoteItemEstoque;

        double valorConvertido = Double.parseDouble(valorItemLimpo);
        double quantidadePorPacoteConvertido = Double.parseDouble(quantidadePorPacoteLimpo);
        double unidadeMedidaConvertido = Double.parseDouble(unidadeMedidaItemEstoque);

        double totalPorPacote = quantidadePorPacoteConvertido * unidadeMedidaConvertido;
        double resultado = Double.valueOf(valorConvertido / totalPorPacote);


        this.resultadoRetornado = String.valueOf(resultado);

        this.valorFracionadoItemEstoque = this.resultadoRetornado;

    return this.resultadoRetornado;
    }

    public String calcularValorItemPorReceita(){

        String quantidadeUtilizadaPorReceitaLimpo = this.quantidadeUtilizadaNasReceitas.replaceAll(",", ".");
        String unidadeMedidaUtilizadaPorReceitaLimpo = this.unidadeMedidaUtilizadoNasReceitas;

        double quantidadeUtilizadaPorReceitaConvertido = Double.parseDouble(quantidadeUtilizadaPorReceitaLimpo);
        double unidadeMedidaUtilizadaPorReceitaConvertido = Double.parseDouble(unidadeMedidaUtilizadaPorReceitaLimpo);
        double valorFracionadoConvertido = Double.parseDouble(this.valorFracionadoItemEstoque);

        double totalPorReceita = quantidadeUtilizadaPorReceitaConvertido * unidadeMedidaUtilizadaPorReceitaConvertido;
        double resultado = totalPorReceita * valorFracionadoConvertido;

        this.resultadoRetornadoValorItemPorReceita = String.valueOf(resultado);

        this.custoPorReceitaItemEstoque = this.resultadoRetornadoValorItemPorReceita;
      return   this.resultadoRetornadoValorItemPorReceita;
    }
    @Override
    public String toString() {
        return "ModeloItemEstoque{" +
                "idItemEstoque='" + idItemEstoque + '\'' +
                ", nomeItemEstoque='" + nomeItemEstoque + '\'' +
                ", quantidadeTotalItemEstoque='" + quantidadeTotalItemEstoque + '\'' +
                ", valorIndividualItemEstoque='" + valorIndividualItemEstoque + '\'' +
                ", unidadeMedidaPacoteItemEstoque='" + unidadeMedidaPacoteItemEstoque + '\'' +
                ", unidadeMedidaUtilizadoNasReceitas='" + unidadeMedidaUtilizadoNasReceitas + '\'' +
                ", valorFracionadoItemEstoque='" + valorFracionadoItemEstoque + '\'' +
                ", custoPorReceitaItemEstoque='" + custoPorReceitaItemEstoque + '\'' +
                ", quantidadeUtilizadaNasReceitas='" + quantidadeUtilizadaNasReceitas + '\'' +
                '}';
    }
}
