package com.marcel.a.n.roxha.deliciasdamamae.controller.helper;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.marcel.a.n.roxha.deliciasdamamae.activity.EstoqueActivity;
import com.marcel.a.n.roxha.deliciasdamamae.config.ConfiguracaoFirebase;
import com.marcel.a.n.roxha.deliciasdamamae.model.ModeloItemEstoque;

import java.util.HashMap;
import java.util.Map;

public class ModeloItemEstoqueDAO implements InterfaceModeloItemEstoqueDAO{

    private static final String COLLECTIO_ITEM_ESTOQUE = "ITEM_ESTOQUE";
    private static final String COLLECTIO_ITEM_ESTOQUE_ACABANDO = "ITEM_ESTOQUE_ACABANDO";
    private Context context;
    private FirebaseFirestore firestore = ConfiguracaoFirebase.getFirestor();
    private CollectionReference referenceItemEstoque = firestore.collection(COLLECTIO_ITEM_ESTOQUE);
    private CollectionReference referenceItemEstoqueAcabando = firestore.collection(COLLECTIO_ITEM_ESTOQUE_ACABANDO);
    private boolean resultadoUpdate;

    public ModeloItemEstoqueDAO(Context context) {
        this.context = context;
    }

    @Override
    public boolean salvarItemEstoque(ModeloItemEstoque modeloItemEstoque) {

        try {

            Map<String, Object> item = new HashMap<>();

            item.put("nomeItemEstoque",modeloItemEstoque.getNomeItemEstoque().trim());
            item.put("quantidadeTotalItemEstoque", modeloItemEstoque.getQuantidadeTotalItemEstoque());
            item.put("quantidadePorVolumeItemEstoque", modeloItemEstoque.getQuantidadePorVolumeItemEstoque());
            item.put("quantidadeUtilizadaNasReceitas", modeloItemEstoque.getQuantidadeUtilizadaNasReceitas());


            item.put("unidadeMedidaPacoteItemEstoque", modeloItemEstoque.getUnidadeMedidaPacoteItemEstoque());
            item.put("unidadeMedidaUtilizadoNasReceitas", modeloItemEstoque.getUnidadeMedidaUtilizadoNasReceitas());

            item.put("valorIndividualItemEstoque", modeloItemEstoque.getValorIndividualItemEstoque());
            item.put("valorFracionadoItemEstoque", modeloItemEstoque.getValorFracionadoItemEstoque());
            item.put("custoPorReceitaItemEstoque", modeloItemEstoque.getCustoPorReceitaItemEstoque());
            item.put("quantidadeTotalItemEmEstoquePorVolume", modeloItemEstoque.getQuantidadeTotalItemEmEstoquePorVolume());
            item.put("quantidadeTotalItemEmEstoqueEmGramas", modeloItemEstoque.getQuantidadeTotalItemEmEstoqueEmGramas());
            item.put("custoTotalDoItemEmEstoque", modeloItemEstoque.getCustoTotalDoItemEmEstoque());
            item.put("versionEstoque", "Estoque_DeliciasDaMamae");
            referenceItemEstoque.add(item).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {

                    Toast.makeText(context, "Sucesso ao salvar  item estoque", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, EstoqueActivity.class);
                    context.startActivity(intent);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(context, "Erro ao salvar  item estoque", Toast.LENGTH_SHORT).show();
                }
            });





        }catch (Exception e){

        }



        return false;
    }

    @Override
    public boolean salvarItemEstoqueRealtimeDataBase(ModeloItemEstoque modeloItemEstoque) {
        return false;
    }

    @Override
    public boolean atualizarItemEstoque(String KeyItemEstoque, ModeloItemEstoque modeloItemEstoque) {

        if(KeyItemEstoque != null){

            try{

                Map<String, Object> item = new HashMap<>();

                item.put("nomeItemEstoque",modeloItemEstoque.getNomeItemEstoque().trim());
                item.put("quantidadeTotalItemEstoque", modeloItemEstoque.getQuantidadeTotalItemEstoque());
                item.put("quantidadePorVolumeItemEstoque", modeloItemEstoque.getQuantidadePorVolumeItemEstoque());
                item.put("quantidadeUtilizadaNasReceitas", modeloItemEstoque.getQuantidadeUtilizadaNasReceitas());


                item.put("unidadeMedidaPacoteItemEstoque", modeloItemEstoque.getUnidadeMedidaPacoteItemEstoque());
                item.put("unidadeMedidaUtilizadoNasReceitas", modeloItemEstoque.getUnidadeMedidaUtilizadoNasReceitas());

                item.put("valorIndividualItemEstoque", modeloItemEstoque.getValorIndividualItemEstoque());
                item.put("valorFracionadoItemEstoque", modeloItemEstoque.getValorFracionadoItemEstoque());
                item.put("custoPorReceitaItemEstoque", modeloItemEstoque.getCustoPorReceitaItemEstoque());
                item.put("quantidadeTotalItemEmEstoquePorVolume", modeloItemEstoque.getQuantidadeTotalItemEmEstoquePorVolume());
                item.put("quantidadeTotalItemEmEstoqueEmGramas", modeloItemEstoque.getQuantidadeTotalItemEmEstoqueEmGramas());
                item.put("custoTotalDoItemEmEstoque", modeloItemEstoque.getCustoTotalDoItemEmEstoque());
                item.put("versionEstoque", "Estoque_DeliciasDaMamae");

                referenceItemEstoque.document(KeyItemEstoque).update(item).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                        Toast.makeText(context, "Sucesso ao atualizar item estoque", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context, EstoqueActivity.class);
                        context.startActivity(intent);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Erro ao atualizar  item estoque", Toast.LENGTH_SHORT).show();
                    }
                });

            }catch (Exception e){

                Log.i("Error: " , e.getMessage());
                resultadoUpdate = false;
            }



        }


        return resultadoUpdate;

    }

    @Override
    public boolean deletarItemEstoque(String KeyItemEstoque, ModeloItemEstoque modeloItemEstoque) {
        return false;
    }

    public boolean atualizarItemAoSerAdicionadoNaVitrine (String KeyItemEstoque, ModeloItemEstoque modeloItemEstoque){
        if(KeyItemEstoque != null){

            try{

                Map<String, Object> item = new HashMap<>();
                item.put("quantidadeTotalItemEmEstoqueEmGramas", modeloItemEstoque.getQuantidadeTotalItemEmEstoqueEmGramas());

                referenceItemEstoque.document(KeyItemEstoque).update(item).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                        Toast.makeText(context, modeloItemEstoque.getNomeItemEstoque().toUpperCase() + "----------------ATUALIZADO", Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Erro ao atualizar  item estoque", Toast.LENGTH_SHORT).show();
                    }
                });

            }catch (Exception e){

                Log.i("Error: " , e.getMessage());
                resultadoUpdate = false;
            }



        }


        return resultadoUpdate;

    }


    public boolean adicionarItemEstoqueAListaDeItensAcabandoEmEstoque(String idReferenciaItemEstoque, ModeloItemEstoque modeloItemEstoque){

        if(idReferenciaItemEstoque != null){

            try{

                Map<String, Object> item = new HashMap<>();

                item.put("idReferenciaItemEmEstoque",idReferenciaItemEstoque);
                item.put("nomeItemEstoque",modeloItemEstoque.getNomeItemEstoque().trim());
                item.put("quantidadeTotalItemEstoque", modeloItemEstoque.getQuantidadeTotalItemEstoque());
                item.put("quantidadePorVolumeItemEstoque", modeloItemEstoque.getQuantidadePorVolumeItemEstoque());
                item.put("quantidadeUtilizadaNasReceitas", modeloItemEstoque.getQuantidadeUtilizadaNasReceitas());
                item.put("unidadeMedidaPacoteItemEstoque", modeloItemEstoque.getUnidadeMedidaPacoteItemEstoque());
                item.put("unidadeMedidaUtilizadoNasReceitas", modeloItemEstoque.getUnidadeMedidaUtilizadoNasReceitas());
                item.put("valorIndividualItemEstoque", modeloItemEstoque.getValorIndividualItemEstoque());
                item.put("valorFracionadoItemEstoque", modeloItemEstoque.getValorFracionadoItemEstoque());
                item.put("custoPorReceitaItemEstoque", modeloItemEstoque.getCustoPorReceitaItemEstoque());
                item.put("quantidadeTotalItemEmEstoquePorVolume", modeloItemEstoque.getQuantidadeTotalItemEmEstoquePorVolume());
                item.put("quantidadeTotalItemEmEstoqueEmGramas", modeloItemEstoque.getQuantidadeTotalItemEmEstoqueEmGramas());
                item.put("custoTotalDoItemEmEstoque", modeloItemEstoque.getCustoTotalDoItemEmEstoque());
                item.put("versionEstoque", "Estoque_DeliciasDaMamae");

                referenceItemEstoqueAcabando.add(item).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(context, modeloItemEstoque.getNomeItemEstoque() +" FOI ADICIONADO A LISTA DE ITENS QUE ESTÃO ACABANDO", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "VERIFIQUE A INTERNET E TENTE NOVAMENTE", Toast.LENGTH_SHORT).show();
                    }
                });
            }catch (Exception e){

                Log.i("Error: " , e.getMessage());
                resultadoUpdate = false;
            }



        }


        return resultadoUpdate;
    }

}
