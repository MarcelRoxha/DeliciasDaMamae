package com.marcel.a.n.roxha.deliciasdamamae.controller.helper;

import com.marcel.a.n.roxha.deliciasdamamae.model.ModeloControleRelatorioMensal;

public interface InterfaceModeloControleRelatorioMensalDAO {

    public boolean adicionarRelatorio(String idMensal);
    public boolean atualizaRelatorio(ModeloControleRelatorioMensal modeloControleRelatorioMensal, String idMensal);
}
