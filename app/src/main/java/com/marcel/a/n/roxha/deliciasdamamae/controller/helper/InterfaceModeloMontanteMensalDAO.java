package com.marcel.a.n.roxha.deliciasdamamae.controller.helper;

import com.marcel.a.n.roxha.deliciasdamamae.model.ModeloMontanteMensalLoja;

public interface InterfaceModeloMontanteMensalDAO {

    public ModeloMontanteMensalLoja modeloMontanteCriando(String idUsuario, ModeloMontanteMensalLoja modeloMontanteMensalLoja);
    public ModeloMontanteMensalLoja modeloMontanteEditando(ModeloMontanteMensalLoja modeloMontanteMensalLoja);
    public ModeloMontanteMensalLoja modeloMontanteIniciarMes(ModeloMontanteMensalLoja modeloMontanteMensalLoja);
    public ModeloMontanteMensalLoja modeloMontanteDeletando(ModeloMontanteMensalLoja modeloMontanteMensalLoja);


}
