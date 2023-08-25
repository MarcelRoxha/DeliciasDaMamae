package com.marcel.a.n.roxha.deliciasdamamae.controller.helper;

import com.marcel.a.n.roxha.deliciasdamamae.model.BolosModel;
import com.marcel.a.n.roxha.deliciasdamamae.model.ModeloItemEstoque;

public interface InterfaceModeloProcessaVendaFeitaDAO {

    public boolean processaVendaFeita(BolosModel bolosModel, String verificaTipoDeVenda, String verificaTipoDeMetodoDePagamento);
    /*public boolean salvarItemEstoqueRealtimeDataBase(ModeloItemEstoque modeloItemEstoque);
    public boolean atualizarItemEstoque(String KeyItemEstoque, ModeloItemEstoque modeloItemEstoque);
    public boolean deletarItemEstoque(String KeyItemEstoque, ModeloItemEstoque modeloItemEstoque);*/
}
