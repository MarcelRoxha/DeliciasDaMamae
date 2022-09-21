package com.marcel.a.n.roxha.deliciasdamamae.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.marcel.a.n.roxha.deliciasdamamae.R;
import com.marcel.a.n.roxha.deliciasdamamae.model.BolosModel;

public class BolosConfecionadosAdapter extends FirestoreRecyclerAdapter<BolosModel, BolosConfecionadosAdapter.BolosConfecionadoViewHolder> {

    private Context context;
    private ItemEstoqueAdapter.OnItemClickLisener listener;
    private ItemEstoqueAdapter.OnLongClickListener listenerLong;

    public BolosConfecionadosAdapter(@NonNull FirestoreRecyclerOptions<BolosModel> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull BolosConfecionadoViewHolder holder, int position, @NonNull BolosModel model) {

        holder.nomeCadastrado.setText(model.getNomeBoloCadastrado());
        holder.custoTotalDaReceitaCadastrada.setText(model.getCustoTotalDaReceitaDoBolo());
        holder.valorCadastradoDeVendaNaBoleria.setText(model.getValorCadastradoParaVendasNaBoleria());
        holder.valorCadastradoDeVendaNoIfood.setText(model.getValorCadastradoParaVendasNoIfood());
        holder.porcentagemCadastradaDeLucro.setText(model.getPorcentagemAdicionadoPorContaDoLucro());
        holder.porcentagemCadastradaDeAcrescimoPorContaDoIfood.setText(model.getPorcentagemAdicionadoPorContaDoIfood());
        holder.valorSugeridoParaVendaNaBoleria.setText(model.getValorSugeridoParaVendasNaBoleriaComAcrescimoDoLucro());
        holder.valorSugeridoParaVendaNoIfood.setText(model.getValorCadastradoParaVendasNoIfood());
        Glide.with(context).load(model.getEnderecoFoto()).into(holder.fotoCadastrada);
    }

    @NonNull
    @Override
    public BolosConfecionadoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_bolos_confecionados_adapter, parent, false);

        return new BolosConfecionadoViewHolder(view);
    }

    public class BolosConfecionadoViewHolder extends RecyclerView.ViewHolder {

        ImageView fotoCadastrada;
        TextView nomeCadastrado;
        TextView custoTotalDaReceitaCadastrada;
        TextView valorCadastradoDeVendaNaBoleria;
        TextView valorCadastradoDeVendaNoIfood;
        TextView porcentagemCadastradaDeLucro;
        TextView porcentagemCadastradaDeAcrescimoPorContaDoIfood;
        TextView valorSugeridoParaVendaNaBoleria;
        TextView valorSugeridoParaVendaNoIfood;


        public BolosConfecionadoViewHolder(@NonNull View itemView) {
            super(itemView);

            fotoCadastrada = itemView.findViewById(R.id.fotoCadastradaAdapter);
            nomeCadastrado = itemView.findViewById(R.id.textoNomeBoloAdapter);
            custoTotalDaReceitaCadastrada = itemView.findViewById(R.id.textoCustoBoloAdapter);
            valorCadastradoDeVendaNaBoleria = itemView.findViewById(R.id.textoValorVendaBoloAdapter);
            valorCadastradoDeVendaNoIfood = itemView.findViewById(R.id.textoValorCadastradoParaVendaNoIfoodAdapter);
            porcentagemCadastradaDeLucro = itemView.findViewById(R.id.textoPorcentagemLucroCadastradaAdapter);
            porcentagemCadastradaDeAcrescimoPorContaDoIfood = itemView.findViewById(R.id.textoPorcentagemDoIfoodCadastradaAdapter);
            valorSugeridoParaVendaNaBoleria = itemView.findViewById(R.id.textoValorSugeridoParaVendasNaBoleria);
            valorSugeridoParaVendaNoIfood = itemView.findViewById(R.id.textoValorSugeridoParaVendasNoIfood);

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
    public void setOnItemClickListerner(ItemEstoqueAdapter.OnItemClickLisener listerner){

        this.listener = listerner;

    }

    public interface OnLongClickListener{
        void onItemClick(DocumentSnapshot documentSnapshot, int position);

    }
    public void setOnItemLongClickListerner(ItemEstoqueAdapter.OnLongClickListener listenerLong){

        this.listenerLong = listenerLong;

    }
}
