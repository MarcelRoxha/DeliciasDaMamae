package com.marcel.a.n.roxha.deliciasdamamae.controller.helper;

import com.marcel.a.n.roxha.deliciasdamamae.model.ModeloItemEstoque;

public interface InterfaceItemEstoqueDAO {

    public boolean salvarItemEstoque(ModeloItemEstoque ModeloItemEstoque);
    public boolean salvarItemEstoqueRealtimeDataBase(ModeloItemEstoque ModeloItemEstoque);
    public boolean atualizarItemEstoque(String KeyItemEstoque, ModeloItemEstoque ModeloItemEstoque);
    public boolean deletarItemEstoque(String KeyItemEstoque, ModeloItemEstoque ModeloItemEstoque);
}
