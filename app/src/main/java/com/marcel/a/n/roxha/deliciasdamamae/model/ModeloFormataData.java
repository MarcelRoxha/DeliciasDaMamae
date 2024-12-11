package com.marcel.a.n.roxha.deliciasdamamae.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ModeloFormataData {


    private String dataRef;
    private String dataCompletaRef;

    public ModeloFormataData() {

    }

    public String getRetornaDataRefAtual(){

        Date dataDeHoje = new Date();
        SimpleDateFormat mesFormart = new SimpleDateFormat("MM");
        SimpleDateFormat anosFormart = new SimpleDateFormat("yyyy");

        String mes = mesFormart.format(dataDeHoje);
        String ano = anosFormart.format(dataDeHoje);
        dataRef = mes + "_" + ano;

        return dataRef;
    }

    public String getRetornaDataCompletaRefAtual(){

        Date dataDeHoje = new Date();
        SimpleDateFormat mesFormart = new SimpleDateFormat("dd/MM/yyyy");

        String data = mesFormart.format(dataDeHoje);
        dataCompletaRef = data;

        return dataCompletaRef;
    }

}
