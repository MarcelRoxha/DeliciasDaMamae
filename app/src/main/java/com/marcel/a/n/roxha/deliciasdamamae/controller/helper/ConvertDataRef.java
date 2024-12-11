package com.marcel.a.n.roxha.deliciasdamamae.controller.helper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ConvertDataRef {


    private String dataRef;
    private String dataCompletaRef;
    private String diaAtual;
    private Date dataDeHoje = new Date();
    SimpleDateFormat mesFormart = new SimpleDateFormat("MM");
    SimpleDateFormat anosFormart = new SimpleDateFormat("yyyy");
    SimpleDateFormat diaFormart = new SimpleDateFormat("dd");
    SimpleDateFormat dataCompleta = new SimpleDateFormat("dd/MM/yyyy");
    public ConvertDataRef() {


    }

    public String getRetornaDataRefAtual(){

        String mes = mesFormart.format(dataDeHoje);
        String ano = anosFormart.format(dataDeHoje);
        dataRef = mes + "_" + ano;

        return dataRef;
    }

    public String getRetornaDataCompletaRefAtual(){



        String data = dataCompleta.format(dataDeHoje);
        dataCompletaRef = data;

        return dataCompletaRef;
    }

    public String getRetornaDiaDeHoje(){
        this.diaAtual = diaFormart.format(dataDeHoje);
        return this.diaAtual;
    }
}
