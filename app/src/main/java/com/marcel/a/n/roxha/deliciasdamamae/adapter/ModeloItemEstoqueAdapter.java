package com.marcel.a.n.roxha.deliciasdamamae.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.marcel.a.n.roxha.deliciasdamamae.R;
import com.marcel.a.n.roxha.deliciasdamamae.model.ModeloItemEstoque;

public class ModeloItemEstoqueAdapter extends FirestoreRecyclerAdapter<ModeloItemEstoque, ModeloItemEstoqueAdapter.ModeloItemEstoqueViewHolder> {

    private OnItemClickLisener listener;
    private OnLongClickListener listenerLong;

    public ModeloItemEstoqueAdapter(@NonNull FirestoreRecyclerOptions<ModeloItemEstoque> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ModeloItemEstoqueViewHolder holder, int position, @NonNull ModeloItemEstoque model) {


        holder.nomeItem.setText(model.getNomeItemEstoque());
        holder.ultimoValotItem.setText(model.getValorIndividualItemEstoque());
        holder.quantEstoqueItem.setText(model.getQuantidadeTotalItemEstoque());
        holder.valorFracionado.setText(model.getValorFracionadoItemEstoque());
        holder.custoItemPorReceita.setText(model.getCustoPorReceitaItemEstoque());
        holder.quantUsadaReceita.setText(model.getQuantidadeUtilizadaNasReceitas());

    }

    public void deletarItemIndividual(int position){


        final Task<Void> delete = getSnapshots().getSnapshot(position).getReference().delete();
    }

    @NonNull
    @Override
    public ModeloItemEstoqueViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_estoque_adapter, parent, false);


        return new ModeloItemEstoqueViewHolder(v);
    }

    public class ModeloItemEstoqueViewHolder extends RecyclerView.ViewHolder {


        private TextView nomeItem;
        private TextView ultimoValotItem;
        private TextView quantEstoqueItem;
        private TextView valorFracionado;
        private TextView custoItemPorReceita;
        private TextView quantUsadaReceita;
        private TextView idItem;

        public ModeloItemEstoqueViewHolder(@NonNull View itemView) {
            super(itemView);


            nomeItem = itemView.findViewById(R.id.text_nome_item_estoque_adapter_id);
            ultimoValotItem = itemView.findViewById(R.id.text_valor_atual_item_estoque_adapter_id);
            quantEstoqueItem = itemView.findViewById(R.id.text_quant_total_estoque_adapter_id);
            valorFracionado = itemView.findViewById(R.id.text_valor_fracionado_adapter_id);
            custoItemPorReceita = itemView.findViewById(R.id.text_valor_atual_receita_adapter_id);
            quantUsadaReceita = itemView.findViewById(R.id.text_quant_usada_receita_adapter_id);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION && listener != null){
                        listener.onItemClick(getSnapshots().getSnapshot(position), position);


                    }

                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {

                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION && listenerLong != null){
                        listenerLong.onItemClick(getSnapshots().getSnapshot(position), position);

                        return true;
                    }

                    return false;
                }
            });



        }
    }

    public interface OnItemClickLisener{
        void onItemClick(DocumentSnapshot documentSnapshot, int position);

    }
    public void setOnItemClickListerner(ModeloItemEstoqueAdapter.OnItemClickLisener listerner){

        this.listener = listerner;

    }

    public interface OnLongClickListener{
        void onItemClick(DocumentSnapshot documentSnapshot, int position);

    }
    public void setOnItemLongClickListerner(ModeloItemEstoqueAdapter.OnLongClickListener listenerLong){

        this.listenerLong = listenerLong;

    }
}
