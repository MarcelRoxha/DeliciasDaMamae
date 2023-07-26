package com.marcel.a.n.roxha.deliciasdamamae.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.marcel.a.n.roxha.deliciasdamamae.R;
import com.marcel.a.n.roxha.deliciasdamamae.model.BolosModel;
import com.marcel.a.n.roxha.deliciasdamamae.model.ReceitaModel;

public class BolosAdicionadosVitrineParaExibirQuandoVenderAdapter extends FirestoreRecyclerAdapter<BolosModel, BolosAdicionadosVitrineParaExibirQuandoVenderAdapter.BolosOpaVendiViewHolder> {

    private Context context;
    private OnItemClickLisener listener;


    public BolosAdicionadosVitrineParaExibirQuandoVenderAdapter(@NonNull FirestoreRecyclerOptions<BolosModel> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull BolosOpaVendiViewHolder holder, int position, @NonNull BolosModel model) {

        holder.nomeProduto.setText(model.getNomeBoloCadastrado());
        holder.valorVendaLojaProduto.setText(model.getValorCadastradoParaVendasNaBoleria());
        holder.velorVendaIfoodProduto.setText(model.getValorCadastradoParaVendasNoIfood());

        Glide.with(context).load(model.getEnderecoFoto()).into(holder.imagemDoProduto);

    }

    @NonNull
    @Override
    public BolosOpaVendiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_alerta_opa_vendi, parent, false);


        return new BolosOpaVendiViewHolder(view);
    }

    public class BolosOpaVendiViewHolder extends RecyclerView.ViewHolder{

        ImageView imagemDoProduto;
        TextView nomeProduto;
        TextView valorVendaLojaProduto;
        TextView velorVendaIfoodProduto;


        public BolosOpaVendiViewHolder(@NonNull View itemView) {
            super(itemView);

            imagemDoProduto = itemView.findViewById(R.id.imagemDoOpaVendi);
            nomeProduto = itemView.findViewById(R.id.nome_do_produto_vendido_opa_vendi);
            valorVendaLojaProduto = itemView.findViewById(R.id.valor_venda_loja);
            velorVendaIfoodProduto = itemView.findViewById(R.id.valor_venda_ifood);

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
        this.listener = listerner;
    }
}
