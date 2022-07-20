package com.marcel.a.n.roxha.deliciasdamamae.model;

import java.io.Serializable;
import java.util.List;

public class ModeloEstoque implements Serializable {

    private String totalDeItensEmEstoque;
    private ModeloItemEstoque modeloItemEstoque;
    private List<ModeloItemEstoque> listaItensAtuaisEmEstoque;
    private List<ModeloItemEstoque> listaItensUltimaCompra;

}
