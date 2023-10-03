package com.marcel.a.n.roxha.deliciasdamamae.controller.helper;

import com.marcel.a.n.roxha.deliciasdamamae.model.ModeloMontanteDiario;
import com.marcel.a.n.roxha.deliciasdamamae.model.ModeloMontanteMensalLoja;

public interface InterfaceModeloMontanteMensalDAO {


   /* public ModeloMontanteMensalLoja modeloMontanteIniciarMes(ModeloMontanteMensalLoja modeloMontanteMensalLoja, String dataReferenciaDiario);*/
    public ModeloMontanteMensalLoja modeloMontanteIniciarMesEDiario(ModeloMontanteMensalLoja modeloMontanteMensalLoja, String diaAtual);
    public ModeloMontanteMensalLoja modeloMontanteEditando(ModeloMontanteMensalLoja modeloMontanteMensalLoja);
    public ModeloMontanteMensalLoja modeloMontanteDeletando(ModeloMontanteMensalLoja modeloMontanteMensalLoja);


}
