package com.marcel.a.n.roxha.deliciasdamamae.controller.helper;

import android.view.View;

import com.marcel.a.n.roxha.deliciasdamamae.model.ModeloIngredienteAdicionadoReceita;
import com.marcel.a.n.roxha.deliciasdamamae.model.ReceitaModel;

public interface InterfaceModeloReceitaCadastradaDAO {

    public boolean iniciarCadastroReceitaCadastrando(ReceitaModel receitaModel);
    public boolean finalizarCadastroDaReceitaCadastrando(ReceitaModel receitaModel);
    public boolean adicionarIngredienteDaReceitaCadastrando(String nomeReceitaCadastrando, ModeloIngredienteAdicionadoReceita modeloIngredienteAdicionadoReceita);
    public boolean editarIngredienteAdicionadoNaReceita(String idReceitaEditando,String valorAntigoDoIngredienteAdicionadoNaReceitaAntes,String idIngredienteEditando, ModeloIngredienteAdicionadoReceita modeloIngredienteAdicionadoReceita);

    public boolean removerIngredienteDaReceitaCadastrando (String idReceita, String idIngrediente, String custoIngredienteNaReceita);
    public boolean editarReceitaCadastrada(ReceitaModel receitaModel);
    public boolean deletarReceitaCadastrada(ReceitaModel receitaModel);


}
