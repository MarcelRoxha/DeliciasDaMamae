package com.marcel.a.n.roxha.deliciasdamamae.controller.helper;

import com.marcel.a.n.roxha.deliciasdamamae.model.BolosModel;
import com.marcel.a.n.roxha.deliciasdamamae.model.ModeloBolosAdicionadosVitrineQuandoVender;

public interface InterfaceModeloBoloAdicionadoExpostoVitrineParaOpaVendi {

    public boolean salvarBoloAdicionadoNaVitrineParaOpaVendi(BolosModel bolosModel);
    public boolean atualizarBoloAdicionadoNaVitrineParaOpaVendi(ModeloBolosAdicionadosVitrineQuandoVender modeloBolosAdicionadosVitrineQuandoVender);
    public boolean deletarBoloAdicionadoNaVitrineParaOpaVendi(ModeloBolosAdicionadosVitrineQuandoVender modeloBolosAdicionadosVitrineQuandoVender);
}
