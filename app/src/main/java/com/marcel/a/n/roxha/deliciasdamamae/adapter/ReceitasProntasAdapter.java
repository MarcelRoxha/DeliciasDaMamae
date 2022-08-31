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
import com.marcel.a.n.roxha.deliciasdamamae.model.ReceitaModel;

public class ReceitasProntasAdapter extends FirestoreRecyclerAdapter<ReceitaModel, ReceitasProntasAdapter.ReceitasProntasViewHolder> {

    private ReceitasProntasAdapter.OnItemClickLisener listener;

    public ReceitasProntasAdapter(@NonNull FirestoreRecyclerOptions<ReceitaModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ReceitasProntasViewHolder holder, int position, @NonNull ReceitaModel model) {

        holder.nomeReceita.setText(model.getNomeReceita());
        holder.valorTotalReceita.setText(model.getValorTotalReceita());
        holder.rendimento.setText(model.getQuantRendimentoReceita());
        holder.porcentagem.setText(model.getPorcentagemServico());
        holder.valorTotalIngredientes.setText(model.getValoresIngredientes());

    }

    @NonNull
    @Override
    public ReceitasProntasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_receitas_prontas_adapter, parent, false);


        return new ReceitasProntasViewHolder(view);
    }

    public class ReceitasProntasViewHolder extends RecyclerView.ViewHolder {

        TextView nomeReceita;
        TextView valorTotalReceita;
        TextView rendimento;
        TextView porcentagem;
        TextView valorTotalIngredientes;

        public ReceitasProntasViewHolder(@NonNull View itemView) {
            super(itemView);

            nomeReceita = itemView.findViewById(R.id.text_nome_receita_pronta_adapter_id);
            valorTotalReceita = itemView.findViewById(R.id.text_valor_total_receita_pronta_adapter_id);
            rendimento = itemView.findViewById(R.id.text_rendimento_por_fornada_receita_pronta_adapter_id);
            porcentagem = itemView.findViewById(R.id.texto_porcentagem_receita_cadastrada_adapter_id);
            valorTotalIngredientes = itemView.findViewById(R.id.texto_valor_total_ingredientes_adicionados_na_receita_adapter_id);

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
    public void setOnItemClickListerner(ReceitasProntasAdapter.OnItemClickLisener listerner){

        this.listener = listerner;

    }
}
