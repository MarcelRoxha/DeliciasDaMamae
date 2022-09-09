package com.marcel.a.n.roxha.deliciasdamamae.helper;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.marcel.a.n.roxha.deliciasdamamae.activity.BolosActivity;
import com.marcel.a.n.roxha.deliciasdamamae.config.ConfiguracaoFirebase;
import com.marcel.a.n.roxha.deliciasdamamae.model.BolosModel;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ModeloBoloCadastradoParaVendaDAO implements InterfaceModeloBoloCadastradoParaVendaDAO{

    private FirebaseFirestore firestore = ConfiguracaoFirebase.getFirestor();
    private CollectionReference collectionBoloCadastradoParaVenda = firestore.collection("BOLOS_CADASTRADOS_PARA_ADICIONAR_PARA_VENDA");
    private Context context;

    public ModeloBoloCadastradoParaVendaDAO(Context context) {
        this.context = context;

    }

    @Override
    public boolean cadastrarBoloParaVenda(BolosModel bolosModel) {

        try{

            if(bolosModel != null){

                Map<String, Object> boloCadastrando = new HashMap<>();
                boloCadastrando.put("idBoloCadastrado","N/D");
                boloCadastrando.put("nomeBoloCadastrado", bolosModel.getNomeBoloCadastrado());
                boloCadastrando.put("custoTotalDaReceitaDoBolo", bolosModel.getCustoTotalDaReceitaDoBolo());
                boloCadastrando.put("valorCadastradoParaVendasNaBoleria", bolosModel.getValorCadastradoParaVendasNaBoleria());
                boloCadastrando.put("valorCadastradoParaVendasNoIfood", bolosModel.getValorCadastradoParaVendasNoIfood());
                boloCadastrando.put("porcentagemAdicionadoPorContaDoIfood", bolosModel.getPorcentagemAdicionadoPorContaDoIfood());
                boloCadastrando.put("porcentagemAdicionadoPorContaDoLucro", bolosModel.getPorcentagemAdicionadoPorContaDoLucro());
                boloCadastrando.put("valorSugeridoParaVendasNoIfoodComAcrescimoDaPorcentagem", bolosModel.getValorSugeridoParaVendasNoIfoodComAcrescimoDaPorcentagem());
                boloCadastrando.put("valorSugeridoParaVendasNaBoleriaComAcrescimoDoLucro", bolosModel.getValorSugeridoParaVendasNaBoleriaComAcrescimoDoLucro());
                boloCadastrando.put("enderecoFoto", bolosModel.getEnderecoFoto());
                boloCadastrando.put("valorQueOBoloRealmenteFoiVendido", bolosModel.getValorQueOBoloRealmenteFoiVendido());
                boloCadastrando.put("verificaCameraGaleria", bolosModel.getVerificaCameraGaleria());

                collectionBoloCadastradoParaVenda.add(boloCadastrando).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {

                        BolosModel bolosModelAtualizarId = new BolosModel();

                        bolosModelAtualizarId.setIdBoloCadastrado(documentReference.getId());
                        bolosModelAtualizarId.setNomeBoloCadastrado(bolosModel.getNomeBoloCadastrado());
                        bolosModelAtualizarId.setCustoTotalDaReceitaDoBolo(bolosModel.getCustoTotalDaReceitaDoBolo());
                        bolosModelAtualizarId.setValorCadastradoParaVendasNaBoleria(bolosModel.getValorCadastradoParaVendasNaBoleria());
                        bolosModelAtualizarId.setValorCadastradoParaVendasNoIfood(bolosModel.getValorCadastradoParaVendasNoIfood());
                        bolosModelAtualizarId.setPorcentagemAdicionadoPorContaDoIfood(bolosModel.getPorcentagemAdicionadoPorContaDoIfood());
                        bolosModelAtualizarId.setPorcentagemAdicionadoPorContaDoLucro(bolosModel.getPorcentagemAdicionadoPorContaDoLucro());
                        bolosModelAtualizarId.setValorSugeridoParaVendasNoIfoodComAcrescimoDaPorcentagem(bolosModel.getValorSugeridoParaVendasNoIfoodComAcrescimoDaPorcentagem());
                        bolosModelAtualizarId.setValorSugeridoParaVendasNaBoleriaComAcrescimoDoLucro(bolosModel.getValorSugeridoParaVendasNaBoleriaComAcrescimoDoLucro());
                        bolosModelAtualizarId.setEnderecoFoto(bolosModel.getEnderecoFoto());
                        bolosModelAtualizarId.setValorQueOBoloRealmenteFoiVendido(bolosModel.getValorQueOBoloRealmenteFoiVendido());
                        bolosModelAtualizarId.setVerificaCameraGaleria(bolosModel.getVerificaCameraGaleria());

                        atualizarIdDoBoloQuandoCadastrado(bolosModelAtualizarId.getIdBoloCadastrado(), bolosModelAtualizarId);


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });




            }else{
                Toast.makeText(context, "Algo deu errado, verifique as informações e a internet e tente novamente", Toast.LENGTH_SHORT).show();
            }


        }catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }



        return false;
    }

    @Override
    public boolean atualizarIdDoBoloQuandoCadastrado(String id, BolosModel boloParaAtualizarOId) {

        try{

            Map<String, Object> boloComIdAtualizado = new HashMap<>();
            boloComIdAtualizado.put("idBoloCadastrado",boloParaAtualizarOId.getIdBoloCadastrado());
            boloComIdAtualizado.put("nomeBoloCadastrado", boloParaAtualizarOId.getNomeBoloCadastrado());
            boloComIdAtualizado.put("custoTotalDaReceitaDoBolo", boloParaAtualizarOId.getCustoTotalDaReceitaDoBolo());
            boloComIdAtualizado.put("valorCadastradoParaVendasNaBoleria", boloParaAtualizarOId.getValorCadastradoParaVendasNaBoleria());
            boloComIdAtualizado.put("valorCadastradoParaVendasNoIfood", boloParaAtualizarOId.getValorCadastradoParaVendasNoIfood());
            boloComIdAtualizado.put("porcentagemAdicionadoPorContaDoIfood", boloParaAtualizarOId.getPorcentagemAdicionadoPorContaDoIfood());
            boloComIdAtualizado.put("porcentagemAdicionadoPorContaDoLucro", boloParaAtualizarOId.getPorcentagemAdicionadoPorContaDoLucro());
            boloComIdAtualizado.put("valorSugeridoParaVendasNoIfoodComAcrescimoDaPorcentagem", boloParaAtualizarOId.getValorSugeridoParaVendasNoIfoodComAcrescimoDaPorcentagem());
            boloComIdAtualizado.put("valorSugeridoParaVendasNaBoleriaComAcrescimoDoLucro", boloParaAtualizarOId.getValorSugeridoParaVendasNaBoleriaComAcrescimoDoLucro());
            boloComIdAtualizado.put("enderecoFoto", boloParaAtualizarOId.getEnderecoFoto());
            boloComIdAtualizado.put("valorQueOBoloRealmenteFoiVendido", boloParaAtualizarOId.getValorQueOBoloRealmenteFoiVendido());
            boloComIdAtualizado.put("verificaCameraGaleria", boloParaAtualizarOId.getVerificaCameraGaleria());



            collectionBoloCadastradoParaVenda.document(id).update(boloComIdAtualizado).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(context, "Sucesso ao cadastrar o bolo para adicionar na vitrine de vendas", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, BolosActivity.class);
                    context.startActivity(intent);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception er) {

                    Toast.makeText(context, "Algo deu errado, verifique a conexão de internet e tente novamente", Toast.LENGTH_SHORT).show();
                    System.out.println("Error: " + er.getMessage());
                }
            });
        }catch (Exception e){
            System.out.println("Error: " +e.getMessage());
        }
        return false;
    }

    @Override
    public boolean editarBoloCadastrado(BolosModel bolosModel) {
        return false;
    }

    @Override
    public boolean deletarBoloCadastrado(BolosModel bolosModel) {
        return false;
    }
}
