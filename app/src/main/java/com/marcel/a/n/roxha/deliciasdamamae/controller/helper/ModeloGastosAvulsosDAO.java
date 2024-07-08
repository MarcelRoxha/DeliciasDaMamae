package com.marcel.a.n.roxha.deliciasdamamae.controller.helper;

import android.content.Context;

import com.marcel.a.n.roxha.deliciasdamamae.model.ModeloGastosAvulsos;



public class ModeloGastosAvulsosDAO implements InterfaceModeloGastosAvulsosDAO{

    private Context context;

    public ModeloGastosAvulsosDAO(Context context) {
        this.context = context;
    }

    @Override
    public boolean cadastrar(ModeloGastosAvulsos modeloGastosAvulsos) {
        return false;
    }
}
