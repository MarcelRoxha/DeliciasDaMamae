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
import com.marcel.a.n.roxha.deliciasdamamae.model.ModeloProdutoVendido;

public class ModeloProdutoVendidoAdapter extends FirestoreRecyclerAdapter<ModeloProdutoVendido, ModeloProdutoVendidoAdapter.ModeloProdutoVendidoViewHolder> {
    private OnItemClickLisener listener;
    public ModeloProdutoVendidoAdapter(@NonNull FirestoreRecyclerOptions<ModeloProdutoVendido> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ModeloProdutoVendidoAdapter.ModeloProdutoVendidoViewHolder holder, int position, @NonNull ModeloProdutoVendido model) {
        holder.textoNome.setText(model.getNomeDoProdutoVendido());
        holder.textoValorVenda.setText(String.valueOf(model.getValorQueOBoloFoiVendido()));
    }

    @NonNull
    @Override
    public ModeloProdutoVendidoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_bolos_vendidos_adapter, parent, false);
        return new ModeloProdutoVendidoViewHolder(view);
    }

    public class ModeloProdutoVendidoViewHolder extends RecyclerView.ViewHolder {

        TextView textoNome;
        TextView textoValorVenda;
        public ModeloProdutoVendidoViewHolder(@NonNull View itemView) {
            super(itemView);
            textoNome = itemView.findViewById(R.id.nome_bolo_vendido_id);
            textoValorVenda = itemView.findViewById(R.id.valor_venda_bolo_vendido_id);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION && listener != null) {
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
