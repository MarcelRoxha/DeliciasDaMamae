package com.marcel.a.n.roxha.deliciasdamamae.model;

import java.io.Serializable;

public class ModeloItemEstoque  implements Serializable {


    private String idItemEstoque;
    private String nomeItemEstoque;
    private String quantidadeTotalItemEstoque;
    private String quantidadePorVolumeItemEstoque;
    private String valorIndividualItemEstoque;
    private String unidadeMedidaPacoteItemEstoque;
    private String unidadeMedidaUtilizadoNasReceitas;
    private String valorFracionadoItemEstoque;
    private String custoPorReceitaItemEstoque;
    private String quantidadeUtilizadaNasReceitas;
    private String resultadoRetornado;
    private String resultadoRetornadoValorItemPorReceita;
    private double resultado = 0;
    private double resultadoCustoDoItemNaReceita = 0;
    private double resultadoDivisaoQuantVolumeVezesValorUnitarioItemEstoque = 0;
    private double resultadoDivisaoQuantVolumeVezesValorUnitarioItemEstoqueNoCustoDoItemNaReceita = 0;

    public ModeloItemEstoque() {
    }

    public ModeloItemEstoque(String idItemEstoque, String nomeItemEstoque, String quantidadeTotalItemEstoque, String quantidadePorPacoteItemEstoque,  String valorIndividualItemEstoque, String unidadeMedidaPacoteItemEstoque, String unidadeMedidaUtilizadoNasReceitas, String valorFracionadoItemEstoque, String custoPorReceitaItemEstoque, String quantidadeUtilizadaNasReceitas, String resultadoRetornado, String resultadoRetornadoValorItemPorReceita) {
        this.idItemEstoque = idItemEstoque;
        this.nomeItemEstoque = nomeItemEstoque;
        this.quantidadeTotalItemEstoque = quantidadeTotalItemEstoque;
        this.quantidadePorVolumeItemEstoque = quantidadePorPacoteItemEstoque;
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
    public String getQuantidadePorVolumeItemEstoque() {
        return quantidadePorVolumeItemEstoque;
    }

    public void setQuantidadePorVolumeItemEstoque(String quantidadePorPacoteItemEstoque) {
        this.quantidadePorVolumeItemEstoque = quantidadePorPacoteItemEstoque;
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
        String quantidadePorPacoteLimpo = this.quantidadePorVolumeItemEstoque.replaceAll(",", ".");
        double valorItemLimpoConvertido = Double.parseDouble(valorItemLimpo);
        double quantidadePorPacoteLimpoConvertido = Double.parseDouble(quantidadePorPacoteLimpo);
        double resultado = 0;

        switch (this.unidadeMedidaPacoteItemEstoque){
            case "GRAMA(S)":

                 resultado = valorItemLimpoConvertido / quantidadePorPacoteLimpoConvertido;

                 this.valorFracionadoItemEstoque = String.valueOf(resultado);
                System.out.println("GRAMAS FOI SELECIONADO, RESULTADO DA CONTA: " + resultado);
                System.out.println("ITEM SALVO: " + this.toString());
                break;
            case "ML(S)":

                 resultado = valorItemLimpoConvertido / quantidadePorPacoteLimpoConvertido;
                this.valorFracionadoItemEstoque = String.valueOf(resultado);
                System.out.println("ML FOI SELECIONADO, RESULTADO DA CONTA: " + resultado);
                System.out.println("ITEM SALVO: " + this.toString());
                break;
            case "UNIDADE(S)":

                 resultado = valorItemLimpoConvertido / 1;
                this.valorFracionadoItemEstoque = String.valueOf(resultado);
                System.out.println("UNIDADE FOI SELECIONADO, RESULTADO DA CONTA: " + resultado);
                System.out.println("ITEM SALVO: " + this.toString());
                break;
            case "LITRO(S)":
                resultado = valorItemLimpoConvertido / 1000;
                this.valorFracionadoItemEstoque = String.valueOf(resultado);
                System.out.println("LITRO FOI SELECIONADO, RESULTADO DA CONTA: " + resultado);
                System.out.println("ITEM SALVO: " + this.toString());
                break;
            case "KILO(S)":
                resultado =  valorItemLimpoConvertido / 1000;
                this.valorFracionadoItemEstoque = String.valueOf(resultado);
                System.out.println("KILO FOI SELECIONADO, RESULTADO DA CONTA: "  + resultado);
                System.out.println("ITEM SALVO: " + this.toString());
                break;
            default:
                break;

        }




/*

        double valorConvertido = Double.parseDouble(valorItemLimpo);
        double quantidadePorPacoteConvertido = Double.parseDouble(quantidadePorPacoteLimpo);
        //double unidadeMedidaConvertido = Double.parseDouble(unidadeMedidaItemEstoque);

        double totalPorPacote = quantidadePorPacoteConvertido * unidadeMedidaConvertido;
        double resultado = Double.valueOf(valorConvertido / totalPorPacote);


        this.resultadoRetornado = String.valueOf(resultado);

        this.valorFracionadoItemEstoque = this.resultadoRetornado;*/

    return " Passou pelo calcularValorFracionadoModeloItemEstoque";
    }

    public String calcularValorItemPorReceita(){


        String valorItemLimpo = this.valorIndividualItemEstoque.replaceAll(",", ".");
        String quantidadeUtilizadaPorReceitaLimpo = this.quantidadeUtilizadaNasReceitas.replaceAll(",", ".");
        double valorItemLimpoConvertido = Double.parseDouble(valorItemLimpo);
        double quantidadeUtilizadaNaReceitaLimpoConvertido = Double.parseDouble(quantidadeUtilizadaPorReceitaLimpo);
        double quantidadePorPacoteLimpoConvertido = Double.parseDouble(this.quantidadePorVolumeItemEstoque);


        switch (this.unidadeMedidaUtilizadoNasReceitas){

            case "KILO(S)":
            case "LITRO(S)":
                System.out.println("Passou pelo kilos e litros");
                resultadoCustoDoItemNaReceita = (quantidadeUtilizadaNaReceitaLimpoConvertido * valorItemLimpoConvertido);
                this.custoPorReceitaItemEstoque = String.valueOf(resultadoCustoDoItemNaReceita);
                break;

            case "GRAMA(S)":
            case "ML(S)":
            case "UNIDADE(S)":
                resultadoCustoDoItemNaReceita = (quantidadeUtilizadaNaReceitaLimpoConvertido * valorItemLimpoConvertido) / 1000;
                this.custoPorReceitaItemEstoque = String.valueOf(resultadoCustoDoItemNaReceita);
                break;

            case "XICARA(S)":

                if(this.nomeItemEstoque.contains("FARINHA")){
                    resultadoCustoDoItemNaReceita = ((120 * quantidadeUtilizadaNaReceitaLimpoConvertido) * valorItemLimpoConvertido) / 1000;
                    this.custoPorReceitaItemEstoque = String.valueOf(resultadoCustoDoItemNaReceita);
                    System.out.println("Entrou no farinha de xicara");
                }else if (this.nomeItemEstoque.contains("CHOCOLATE EM PO")){
                    resultadoCustoDoItemNaReceita = ((90 * quantidadeUtilizadaNaReceitaLimpoConvertido) * valorItemLimpoConvertido) / 1000;
                    this.custoPorReceitaItemEstoque = String.valueOf(resultadoCustoDoItemNaReceita);
                    System.out.println("Tem chocolate no nome");
                }else if (this.nomeItemEstoque.contains("CHOCOLATE EM PÓ")){
                    resultadoCustoDoItemNaReceita = ((90 * quantidadeUtilizadaNaReceitaLimpoConvertido) * valorItemLimpoConvertido) / 1000;
                    this.custoPorReceitaItemEstoque = String.valueOf(resultadoCustoDoItemNaReceita);
                }else if (this.nomeItemEstoque.contains("AÇUCAR REFINADO")){
                    resultadoCustoDoItemNaReceita = ((180 * quantidadeUtilizadaNaReceitaLimpoConvertido) * valorItemLimpoConvertido) / 1000;
                    this.custoPorReceitaItemEstoque = String.valueOf(resultadoCustoDoItemNaReceita);
                }else if (this.nomeItemEstoque.contains("AÇUCAR CRISTAL")){
                    resultadoCustoDoItemNaReceita = ((200 * quantidadeUtilizadaNaReceitaLimpoConvertido) * valorItemLimpoConvertido) / 1000;
                    this.custoPorReceitaItemEstoque = String.valueOf(resultadoCustoDoItemNaReceita);
                }else if (this.nomeItemEstoque.contains("AÇUCAR MASCAVO")){
                    resultadoCustoDoItemNaReceita = ((200 * quantidadeUtilizadaNaReceitaLimpoConvertido) * valorItemLimpoConvertido) / 1000;
                    this.custoPorReceitaItemEstoque = String.valueOf(resultadoCustoDoItemNaReceita);
                }else if (this.nomeItemEstoque.contains("AÇÚCAR REFINADO")){
                    resultadoCustoDoItemNaReceita = ((180 * quantidadeUtilizadaNaReceitaLimpoConvertido) * valorItemLimpoConvertido) / 1000;
                    this.custoPorReceitaItemEstoque = String.valueOf(resultadoCustoDoItemNaReceita);
                }else if (this.nomeItemEstoque.contains("AÇÚCAR CRISTAL")){
                    resultadoCustoDoItemNaReceita = ((200 * quantidadeUtilizadaNaReceitaLimpoConvertido) * valorItemLimpoConvertido) / 1000;
                    this.custoPorReceitaItemEstoque = String.valueOf(resultadoCustoDoItemNaReceita);
                }else if (this.nomeItemEstoque.contains("AÇÚCAR MASCAVO")){
                    resultadoCustoDoItemNaReceita = ((200 * quantidadeUtilizadaNaReceitaLimpoConvertido)* valorItemLimpoConvertido) / 1000;
                    this.custoPorReceitaItemEstoque = String.valueOf(resultadoCustoDoItemNaReceita);
                }

                break;

            default:
                break;

        }
        this.custoPorReceitaItemEstoque = String.valueOf(this.resultadoCustoDoItemNaReceita);
      return   this.resultadoRetornadoValorItemPorReceita;
    }
    @Override
    public String toString() {
        return "ModeloItemEstoque{" +
                "idItemEstoque='" + idItemEstoque + '\'' + "\n" +
                ", nomeItemEstoque='" + nomeItemEstoque + '\'' + "\n" +
                ", quantidadeTotalItemEstoque='" + quantidadeTotalItemEstoque + '\'' + "\n" +
                ", valorIndividualItemEstoque='" + valorIndividualItemEstoque + '\'' + "\n" +
                ", unidadeMedidaPacoteItemEstoque='" + unidadeMedidaPacoteItemEstoque + '\'' + "\n" +
                ", unidadeMedidaUtilizadoNasReceitas='" + unidadeMedidaUtilizadoNasReceitas + '\'' + "\n" +
                ", valorFracionadoItemEstoque='" + valorFracionadoItemEstoque + '\'' + "\n" +
                ", custoPorReceitaItemEstoque='" + custoPorReceitaItemEstoque + '\'' + "\n" +
                ", quantidadeUtilizadaNasReceitas='" + quantidadeUtilizadaNasReceitas + '\'' + "\n" +
                ", quantidadePorVolumeItemEstoque='" + quantidadePorVolumeItemEstoque + '\'' + "\n" +
                '}';
    }
}
