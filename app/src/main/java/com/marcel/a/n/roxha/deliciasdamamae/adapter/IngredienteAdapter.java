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

public class IngredienteAdapter extends FirestoreRecyclerAdapter<ModeloItemEstoque, IngredienteAdapter.IngredienteViewHolder> {

    private OnItemClickLisener listener;


    public IngredienteAdapter(@NonNull FirestoreRecyclerOptions<ModeloItemEstoque> options) {
        super(options);
    }



    @Override
    protected void onBindViewHolder(@NonNull IngredienteViewHolder holder, int position, @NonNull ModeloItemEstoque model) {

        holder.nomeIngrediente.setText(model.getNomeItemEstoque());
        holder.quantUsadaReceitaIngrediente.setText(model.getQuantidadeUtilizadaNasReceitas());
        holder.custoIngredienteReceita.setText(model.getValorIndividualItemEstoque());
    }

    @NonNull
    @Override
    public IngredienteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_ingredientes_adapter, parent, false);

        return new IngredienteViewHolder(v);
    }



    public class IngredienteViewHolder extends RecyclerView.ViewHolder {

        private TextView nomeIngrediente;
        private TextView quantUsadaReceitaIngrediente;
        private TextView custoIngredienteReceita;

        public IngredienteViewHolder(@NonNull View itemView) {
            super(itemView);

            nomeIngrediente = itemView.findViewById(R.id.text_nome_ingrediente_adicionado_adapter_id);
            quantUsadaReceitaIngrediente = itemView.findViewById(R.id.text_quant_add_item_receita_id);
            custoIngredienteReceita = itemView.findViewById(R.id.text_valor_add_item_receita_id);



            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int position = getAdapterPosition();

                    if(position != RecyclerView.NO_POSITION && listener != null){
                    listener.onItemClick(getSnapshots().getSnapshot(position), position);

                    }

                }
            });

        }


    }


    public interface OnItemClickLisener{
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }
    public void setOnItemClickListerner(OnItemClickLisener listerner){
        this.listener =  listerner;


    }

}
