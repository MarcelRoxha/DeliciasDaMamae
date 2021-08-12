package com.marcel.a.n.roxha.deliciasdamamae.helper;

import com.marcel.a.n.roxha.deliciasdamamae.model.ItemEstoqueModel;

public interface InterfaceItemEstoqueDAO {

    public boolean salvarItemEstoque(ItemEstoqueModel itemEstoqueModel);
    public boolean salvarItemEstoqueRealtimeDataBase(ItemEstoqueModel itemEstoqueModel);
    public boolean atualizarItemEstoque(ItemEstoqueModel itemEstoqueModel);
    public boolean deletarItemEstoque(ItemEstoqueModel itemEstoqueModel);
}
