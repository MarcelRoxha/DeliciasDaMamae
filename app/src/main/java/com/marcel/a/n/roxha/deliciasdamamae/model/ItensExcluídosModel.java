package com.marcel.a.n.roxha.deliciasdamamae.model;

import java.io.Serializable;

public class ItensExcluídosModel implements Serializable {


    //Variaveis CAIXA_MENSAL:
    private String idCaixaMensal;
    private int mesReferencia;
    private int quantTotalBoloAdicionadoMensal;
    private double totalGastoMensal;
    private double valorTotalBolosVendidosMensal;
    private double valorTotalCustoBoloVendidoMensal;

    //Variaveis Item_Estoque:
    private String nomeItem;
    private String quantItem;
    private String quantPacote;
    private String quantUsadoReceita;
    private String unidMedida;
    private String unidReceita;
    private String valorFracionado;
    private String valorItem;
    private String valorItemPorReceita;
    private String versionEstoque;

    //Variaveis Receitas_complestas:
    private String idReceita;
    private String nomeReceita;
    private String quantRendimentoReceita;
    private String valorTotalReceita;

    //Variaveis Bolos_Cadastrados_venda:
    private String custoBolo;
    private String enderecoFoto;
    private String nomeBolo;
    private String quantBoloVenda;
    private String valorVenda;
    private String verificaCameraGaleria;

    //Variaveis BOLOS_VENDIDOS;
    private double custoBoloVendido;
    private String idReferenciaBoloVendido;
    private String nomeBoloVendido;
    private double valorVendaBoloVendido;

    //Variaveis BOLOS_EXPOSTOS_VITRINE:
    private String identificadorBoloExpostoVitrine;
    private String nomeBoloExpostoVitrine;
    private String enderecoFotoSalvaExpostoVitrine;
    private double valorVendaBoloExpostoVitrine;
    private double custoBoloAdicionadoExpostoVitrine;






}
