package com.marcel.a.n.roxha.deliciasdamamae.controller.helper;

import com.marcel.a.n.roxha.deliciasdamamae.model.ModeloMontanteMensalLoja;

public interface InterfaceModeloMontanteMensalDAO {


    public ModeloMontanteMensalLoja modeloMontanteEditando(ModeloMontanteMensalLoja modeloMontanteMensalLoja);
    public ModeloMontanteMensalLoja modeloMontanteIniciarMes(ModeloMontanteMensalLoja modeloMontanteMensalLoja, String dataReferenciaDiario);
    public ModeloMontanteMensalLoja modeloMontanteDeletando(ModeloMontanteMensalLoja modeloMontanteMensalLoja);


}
