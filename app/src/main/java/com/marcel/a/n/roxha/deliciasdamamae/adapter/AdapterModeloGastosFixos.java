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
import com.marcel.a.n.roxha.deliciasdamamae.model.ModeloGastosFixos;

public class AdapterModeloGastosFixos extends FirestoreRecyclerAdapter<ModeloGastosFixos, AdapterModeloGastosFixos.ModeloGastoFixoAdapterViewHolder> {
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public AdapterModeloGastosFixos(@NonNull FirestoreRecyclerOptions<ModeloGastosFixos> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ModeloGastoFixoAdapterViewHolder holder, int position, @NonNull ModeloGastosFixos model) {

        holder.nomeDoGasto.setText(model.getNomeDoGastoFixo());
        String valorRecuperado = String.valueOf(model.getValorGastoFixo());
        holder.valorDoGasto.setText(valorRecuperado);
        holder.dataPrevistaDePagamento.setText(model.getDataPrevistaDePagamento());
    }

    @NonNull
    @Override
    public ModeloGastoFixoAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_adapter_gasto_fixo, parent, false);

        return new ModeloGastoFixoAdapterViewHolder(view);
    }

    public class ModeloGastoFixoAdapterViewHolder extends RecyclerView.ViewHolder {

        TextView nomeDoGasto, valorDoGasto, dataPrevistaDePagamento;

        public ModeloGastoFixoAdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            nomeDoGasto = itemView.findViewById(R.id.nomeGastoAdapterId);
            valorDoGasto = itemView.findViewById(R.id.valorGastoFixoAdapterId);
            dataPrevistaDePagamento = itemView.findViewById(R.id.diaVencimentoGastoFixoAdapterId);
        }
    }
}
