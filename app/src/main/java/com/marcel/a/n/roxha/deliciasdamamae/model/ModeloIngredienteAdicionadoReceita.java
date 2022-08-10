package com.marcel.a.n.roxha.deliciasdamamae.model;

public class ModeloIngredienteAdicionadoReceita {

    private String idReferenciaItemEmEstoque;
    private String nomeIngredienteAdicionadoReceita;
    private String quantidadeUtilizadaReceita;
    private String custoIngredientePorReceita;
    private String unidadeMedidaUsadaReceita;

    public ModeloIngredienteAdicionadoReceita() {
    }

    public ModeloIngredienteAdicionadoReceita(String idReferenciaItemEmEstoque, String nomeIngredienteAdicionadoReceita, String quantidadeUtilizadaReceita, String custoIngredientePorReceita) {
        this.idReferenciaItemEmEstoque = idReferenciaItemEmEstoque;
        this.nomeIngredienteAdicionadoReceita = nomeIngredienteAdicionadoReceita;
        this.quantidadeUtilizadaReceita = quantidadeUtilizadaReceita;
        this.custoIngredientePorReceita = custoIngredientePorReceita;
    }

    public String getIdReferenciaItemEmEstoque() {
        return idReferenciaItemEmEstoque;
    }

    public void setIdReferenciaItemEmEstoque(String idReferenciaItemEmEstoque) {
        this.idReferenciaItemEmEstoque = idReferenciaItemEmEstoque;
    }

    public String getNomeIngredienteAdicionadoReceita() {
        return nomeIngredienteAdicionadoReceita;
    }

    public void setNomeIngredienteAdicionadoReceita(String nomeIngredienteAdicionadoReceita) {
        this.nomeIngredienteAdicionadoReceita = nomeIngredienteAdicionadoReceita;
    }

    public String getQuantidadeUtilizadaReceita() {
        return quantidadeUtilizadaReceita;
    }

    public void setQuantidadeUtilizadaReceita(String quantidadeUtilizadaReceita) {
        this.quantidadeUtilizadaReceita = quantidadeUtilizadaReceita;
    }

    public String getCustoIngredientePorReceita() {
        return custoIngredientePorReceita;
    }

    public void setCustoIngredientePorReceita(String custoIngredientePorReceita) {
        this.custoIngredientePorReceita = custoIngredientePorReceita;
    }

    public String getUnidadeMedidaUsadaReceita() {
        return unidadeMedidaUsadaReceita;
    }

    public void setUnidadeMedidaUsadaReceita(String unidadeMedidaUsadaReceita) {
        this.unidadeMedidaUsadaReceita = unidadeMedidaUsadaReceita;
    }

    @Override
    public String toString() {
        return "ModeloIngredienteAdicionadoReceita{" +
                "idReferenciaItemEmEstoque='" + idReferenciaItemEmEstoque + '\'' +
                ", nomeIngredienteAdicionadoReceita='" + nomeIngredienteAdicionadoReceita + '\'' +
                ", quantidadeUtilizadaReceita='" + quantidadeUtilizadaReceita + '\'' +
                ", custoIngredientePorReceita='" + custoIngredientePorReceita + '\'' +
                '}';
    }
}
