package com.marcel.a.n.roxha.deliciasdamamae.controller.helper;

import com.marcel.a.n.roxha.deliciasdamamae.model.ModeloMaquininhaDePassarCartao;

public interface InterfaceModeloMaquininhaDeCartaoDAO {

    public boolean cadastrarMaquininha(ModeloMaquininhaDePassarCartao modeloMaquininhaDePassarCartao);
    public boolean atualizarMaquininha(ModeloMaquininhaDePassarCartao modeloMaquininhaDePassarCartao);
    public boolean deletarMaquininha(ModeloMaquininhaDePassarCartao modeloMaquininhaDePassarCartao);

}
