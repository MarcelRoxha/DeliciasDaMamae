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
import com.marcel.a.n.roxha.deliciasdamamae.model.ModeloGastosAvulsos;

public class AdapterModeloGastoAvulsos extends FirestoreRecyclerAdapter<ModeloGastosAvulsos, AdapterModeloGastoAvulsos.ModeloGastosAvulsosViewHolder> {
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public AdapterModeloGastoAvulsos(@NonNull FirestoreRecyclerOptions<ModeloGastosAvulsos> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ModeloGastosAvulsosViewHolder holder, int position, @NonNull ModeloGastosAvulsos model) {

        holder.nomeDoGasto.setText(model.getNomeDoGastoAvulsos());
        String valorRecuperado = String.valueOf(model.getValorGastoAvulsos());
        holder.valorDoGasto.setText(valorRecuperado);
        holder.dataPrevistaDePagamento.setText(model.getDataDeRegistroGasto());

    }

    @NonNull
    @Override
    public ModeloGastosAvulsosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_adapter_gastos_avulsos, parent, false);


        return new ModeloGastosAvulsosViewHolder(view);
    }


    public class ModeloGastosAvulsosViewHolder extends RecyclerView.ViewHolder {

        TextView nomeDoGasto, valorDoGasto, dataPrevistaDePagamento;
        public ModeloGastosAvulsosViewHolder(@NonNull View itemView) {
            super(itemView);

            nomeDoGasto = itemView.findViewById(R.id.textoNomeGastoAvulsoId);
            valorDoGasto = itemView.findViewById(R.id.valorRegistradoDoGastoId);
            dataPrevistaDePagamento = itemView.findViewById(R.id.dataRegistroDoGastoId);
        }
    }
}
