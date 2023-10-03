package com.marcel.a.n.roxha.deliciasdamamae.controller.helper;

import com.marcel.a.n.roxha.deliciasdamamae.model.ModeloMontanteDiario;
import com.marcel.a.n.roxha.deliciasdamamae.model.ModeloMontanteMensalLoja;

public interface InterfaceModeloMontanteDiarioDAO {

    public ModeloMontanteMensalLoja modeloMontanteDiarioIniciandoODia(ModeloMontanteDiario modeloMontanteDiario);

    public ModeloMontanteDiario modeloMontanteDiarioSendoIniciadoJuntoComMes(ModeloMontanteDiario modeloMontanteDiario);

}
