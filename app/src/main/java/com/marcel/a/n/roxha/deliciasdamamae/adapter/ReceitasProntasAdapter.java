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
import com.marcel.a.n.roxha.deliciasdamamae.model.ReceitaModel;

public class ReceitasProntasAdapter extends FirestoreRecyclerAdapter<ReceitaModel, ReceitasProntasAdapter.ReceitasProntasViewHolder> {

    public ReceitasProntasAdapter(@NonNull FirestoreRecyclerOptions<ReceitaModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ReceitasProntasViewHolder holder, int position, @NonNull ReceitaModel model) {

        holder.nomeReceita.setText(model.getNomeReceita());
        holder.valorTotalReceita.setText(model.getValorTotalReceita());
        holder.rendimento.setText(model.getQuantRendimentoReceita());

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

        public ReceitasProntasViewHolder(@NonNull View itemView) {
            super(itemView);

            nomeReceita = itemView.findViewById(R.id.text_nome_receita_pronta_adapter_id);
            valorTotalReceita = itemView.findViewById(R.id.text_valor_total_receita_pronta_adapter_id);
            rendimento = itemView.findViewById(R.id.text_rendimento_por_fornada_receita_pronta_adapter_id);
        }
    }
}
