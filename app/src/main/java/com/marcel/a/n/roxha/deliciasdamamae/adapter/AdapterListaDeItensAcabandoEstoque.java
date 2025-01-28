package com.marcel.a.n.roxha.deliciasdamamae.adapter;

import android.content.Context;
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

import org.w3c.dom.Text;

public class AdapterListaDeItensAcabandoEstoque extends FirestoreRecyclerAdapter<ModeloItemEstoque, AdapterListaDeItensAcabandoEstoque.ItemEstoqueViewHolder> {
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */

    private Context context;
    private OnItemClickLisener listener;
    public AdapterListaDeItensAcabandoEstoque(@NonNull FirestoreRecyclerOptions<ModeloItemEstoque> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ItemEstoqueViewHolder holder, int position, @NonNull ModeloItemEstoque model) {
        holder.nomeDoItemEmEstoque.setText(model.getNomeItemEstoque());
        String totalEmGramasConverido = String.valueOf(model.getQuantidadeTotalItemEmEstoqueEmGramas());
        holder.quantidadeEmEstoque.setText(totalEmGramasConverido);
    }

    @NonNull
    @Override
    public ItemEstoqueViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_lista_de_itens_acabando_estoque, parent, false);

        return new ItemEstoqueViewHolder(view);
    }

    public class ItemEstoqueViewHolder extends RecyclerView.ViewHolder {

        private TextView nomeDoItemEmEstoque, quantidadeEmEstoque;
        public ItemEstoqueViewHolder(@NonNull View itemView) {
            super(itemView);
            nomeDoItemEmEstoque = itemView.findViewById(R.id.nomeItemEstoqueAcabando);
            quantidadeEmEstoque = itemView.findViewById(R.id.textoQuantidadeTotamEmGramasEmEstoqueId);
        }
    }
    public interface OnItemClickLisener{
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }
    public void setOnItemClickListerner(OnItemClickLisener listerner){
        this.listener = listerner;
    }
}

