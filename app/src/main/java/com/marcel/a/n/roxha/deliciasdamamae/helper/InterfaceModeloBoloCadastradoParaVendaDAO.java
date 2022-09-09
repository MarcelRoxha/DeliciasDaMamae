package com.marcel.a.n.roxha.deliciasdamamae.helper;

import com.marcel.a.n.roxha.deliciasdamamae.model.BolosModel;

public interface InterfaceModeloBoloCadastradoParaVendaDAO {

    public boolean cadastrarBoloParaVenda(BolosModel bolosModel);
    public boolean atualizarIdDoBoloQuandoCadastrado(String id, BolosModel bolosModel);
    public boolean editarBoloCadastrado(BolosModel bolosModel);
    public boolean deletarBoloCadastrado(BolosModel bolosModel);
}
