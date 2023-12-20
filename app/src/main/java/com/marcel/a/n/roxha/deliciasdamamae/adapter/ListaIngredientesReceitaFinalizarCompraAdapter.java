package com.marcel.a.n.roxha.deliciasdamamae.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.marcel.a.n.roxha.deliciasdamamae.R;
import com.marcel.a.n.roxha.deliciasdamamae.model.ModeloItemEstoque;
import com.marcel.a.n.roxha.deliciasdamamae.model.ReceitaModel;

public class ListaIngredientesReceitaFinalizarCompraAdapter extends FirestoreRecyclerAdapter<ModeloItemEstoque, ListaIngredientesReceitaFinalizarCompraAdapter.ListaIngredientesReceitafinalizarViewHolder> {


    public ListaIngredientesReceitaFinalizarCompraAdapter(@NonNull FirestoreRecyclerOptions<ModeloItemEstoque> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ListaIngredientesReceitafinalizarViewHolder holder, int position, @NonNull ModeloItemEstoque model) {

        holder.nomeIngrediente.setText(model.getNomeItemEstoque());

    }

    @NonNull
    @Override
    public ListaIngredientesReceitafinalizarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layou_ingredientes_receita_finalizar_venda_adapter, parent, false);

        return new ListaIngredientesReceitafinalizarViewHolder(view);
    }

    public class ListaIngredientesReceitafinalizarViewHolder extends RecyclerView.ViewHolder {

        TextView nomeIngrediente;
        public ListaIngredientesReceitafinalizarViewHolder(@NonNull View itemView) {
            super(itemView);

            nomeIngrediente = itemView.findViewById(R.id.nome_ingrediente_receita_finalizar_venda_bolo_id);
        }
    }
}
