package com.marcel.a.n.roxha.deliciasdamamae.controller.helper;

import com.marcel.a.n.roxha.deliciasdamamae.model.BolosModel;
import com.marcel.a.n.roxha.deliciasdamamae.model.ModeloItemEstoque;
import com.marcel.a.n.roxha.deliciasdamamae.model.ModeloProdutoVendido;

public interface InterfaceModeloProcessaVendaFeitaDAO {

    public boolean processaNoMontanteMensalVendaFeita(ModeloProdutoVendido modeloProdutoVendido, String idMontante, String idCaixa );

    public boolean removeBoloVitrineVendido(String idProduto);
    /*public boolean salvarItemEstoqueRealtimeDataBase(ModeloItemEstoque modeloItemEstoque);
    public boolean atualizarItemEstoque(String KeyItemEstoque, ModeloItemEstoque modeloItemEstoque);
    public boolean deletarItemEstoque(String KeyItemEstoque, ModeloItemEstoque modeloItemEstoque);*/
}
