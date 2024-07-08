package com.marcel.a.n.roxha.deliciasdamamae.controller.helper;

import com.marcel.a.n.roxha.deliciasdamamae.model.ModeloGastosFixos;

public interface InterfaceModeloGastosFixosDAO {

    public boolean cadastrarGastoFixo(ModeloGastosFixos modeloGastosFixos);
    public boolean atualizarGastoFixo(ModeloGastosFixos modeloGastosFixos, String idRecuperado);
}
