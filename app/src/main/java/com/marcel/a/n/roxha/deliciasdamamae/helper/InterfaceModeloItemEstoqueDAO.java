package com.marcel.a.n.roxha.deliciasdamamae.helper;

import com.marcel.a.n.roxha.deliciasdamamae.model.ItemEstoqueModel;
import com.marcel.a.n.roxha.deliciasdamamae.model.ModeloItemEstoque;

public interface InterfaceModeloItemEstoqueDAO {

    public boolean salvarItemEstoque(ModeloItemEstoque modeloItemEstoque);
    public boolean salvarItemEstoqueRealtimeDataBase(ModeloItemEstoque modeloItemEstoque);
    public boolean atualizarItemEstoque(String KeyItemEstoque, ModeloItemEstoque modeloItemEstoque);
    public boolean deletarItemEstoque(String KeyItemEstoque, ModeloItemEstoque modeloItemEstoque);
}
