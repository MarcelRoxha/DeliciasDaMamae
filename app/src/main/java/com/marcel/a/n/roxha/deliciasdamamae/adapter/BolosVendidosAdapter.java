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
import com.marcel.a.n.roxha.deliciasdamamae.model.BoloVendidoModel;
import com.marcel.a.n.roxha.deliciasdamamae.model.ModeloProdutoVendido;

public class BolosVendidosAdapter extends FirestoreRecyclerAdapter<ModeloProdutoVendido, BolosVendidosAdapter.BolosVendidoViewHolder> {

    private OnItemClickLisener listener;


    public BolosVendidosAdapter(@NonNull FirestoreRecyclerOptions<ModeloProdutoVendido> options) {
        super(options);
        System.out.println("dentro do BolosVendidosAdapter");

    }

    @Override
    protected void onBindViewHolder(@NonNull BolosVendidoViewHolder holder, int position, @NonNull ModeloProdutoVendido model) {
        System.out.println("dentro do onBindViewHolder");
        holder.textoNome.setText(model.getNomeDoProdutoVendido());
        holder.textoValorVenda.setText(String.valueOf(model.getValorQueOBoloFoiVendido()));
      // holder.textoCusto.setText(String.valueOf(model.getCustoQueOProdutoTeveAoSerConfeccionado()));


    }

    @NonNull
    @Override
    public BolosVendidoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        System.out.println("dentro-- do BolosVendidoViewHolder");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_bolos_vendidos_adapter, parent, false);

        return new BolosVendidoViewHolder(view);
    }

    public class BolosVendidoViewHolder extends RecyclerView.ViewHolder {

        TextView textoNome;
        TextView textoValorVenda;
        TextView textoCusto;


        public BolosVendidoViewHolder(@NonNull View itemView) {
            super(itemView);

            textoNome = itemView.findViewById(R.id.nome_bolo_vendido_id);
            textoValorVenda = itemView.findViewById(R.id.valor_venda_bolo_vendido_id);
            textoCusto = itemView.findViewById(R.id.valor_custo_bolo_vendido_id);
            System.out.println("dentro----- do BolosVendidoViewHolder");
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
