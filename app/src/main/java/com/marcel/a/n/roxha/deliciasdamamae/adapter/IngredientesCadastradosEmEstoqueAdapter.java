package com.marcel.a.n.roxha.deliciasdamamae.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.marcel.a.n.roxha.deliciasdamamae.R;
import com.marcel.a.n.roxha.deliciasdamamae.model.ModeloItemEstoque;

public class IngredientesCadastradosEmEstoqueAdapter extends FirestoreRecyclerAdapter<ModeloItemEstoque, IngredientesCadastradosEmEstoqueAdapter.ModeloItemEstoqueViewHolder> {

    private OnItemClickLisener lisener;



    public IngredientesCadastradosEmEstoqueAdapter(@NonNull FirestoreRecyclerOptions<ModeloItemEstoque> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ModeloItemEstoqueViewHolder holder, int position, @NonNull ModeloItemEstoque model) {

        holder.nomeIngredienteCadastrado.setText(model.getNomeItemEstoque());
        holder.custoPorReceitaIngredienteCadastrado.setText(model.getCustoPorReceitaItemEstoque());
        holder.quantidadeDoIngredienteUtilizadoNaReceita.setText(model.getQuantidadeUtilizadaNasReceitas());
        holder.unidadeMedidaDoIngredienteUtilizadoNaReceita.setText(model.getUnidadeMedidaUtilizadoNasReceitas());
    }

    @NonNull
    @Override
    public ModeloItemEstoqueViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_ingredientes_adapter, parent, false);

        return new ModeloItemEstoqueViewHolder(v);
    }

    public class ModeloItemEstoqueViewHolder extends RecyclerView.ViewHolder {

        private TextView nomeIngredienteCadastrado;
        private TextView custoPorReceitaIngredienteCadastrado;
        private TextView quantidadeDoIngredienteUtilizadoNaReceita;
        private TextView unidadeMedidaDoIngredienteUtilizadoNaReceita;

        public ModeloItemEstoqueViewHolder(@NonNull View itemView) {
            super(itemView);

            nomeIngredienteCadastrado = itemView.findViewById(R.id.text_nome_ingrediente_adicionado_adapter_id);
            custoPorReceitaIngredienteCadastrado = itemView.findViewById(R.id.text_valor_add_item_receita_id);
            quantidadeDoIngredienteUtilizadoNaReceita = itemView.findViewById(R.id.text_quant_add_item_receita_id);
            unidadeMedidaDoIngredienteUtilizadoNaReceita = itemView.findViewById(R.id.texto_unidade_medida_ingrediente_cadastrado_em_estoque_adapter_id);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION && lisener != null){
                        lisener.onItemClick(getSnapshots().getSnapshot(position), position);


                    }

                }
            });

        }
    }
    public interface OnItemClickLisener{
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }
    public void setOnItemClickListerner(IngredientesCadastradosEmEstoqueAdapter.OnItemClickLisener lisener){
        this.lisener = lisener;
    }
}

