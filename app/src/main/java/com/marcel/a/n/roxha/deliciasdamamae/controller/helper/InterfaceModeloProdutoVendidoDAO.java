package com.marcel.a.n.roxha.deliciasdamamae.controller.helper;

import com.marcel.a.n.roxha.deliciasdamamae.model.BolosModel;
import com.marcel.a.n.roxha.deliciasdamamae.model.ProdutoVendido;

public interface InterfaceModeloProdutoVendidoDAO {

    public boolean guardaProdutoVendido(String idMontanteDiario, ProdutoVendido produtoVendido);
}
