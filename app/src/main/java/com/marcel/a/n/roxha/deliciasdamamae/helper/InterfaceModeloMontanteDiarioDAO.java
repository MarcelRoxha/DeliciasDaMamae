package com.marcel.a.n.roxha.deliciasdamamae.helper;

import com.marcel.a.n.roxha.deliciasdamamae.model.ModeloMontanteDiario;
import com.marcel.a.n.roxha.deliciasdamamae.model.ModeloMontanteMensalLoja;

public interface InterfaceModeloMontanteDiarioDAO {

    public ModeloMontanteMensalLoja modeloMontanteDiarioIniciandoODia(ModeloMontanteDiario modeloMontanteDiario);
    public ModeloMontanteMensalLoja modeloMontanteDiarioEditando(ModeloMontanteDiario modeloMontanteDiario);
    public ModeloMontanteMensalLoja modeloMontanteDiarioDeletando(ModeloMontanteDiario modeloMontanteDiario);

}
