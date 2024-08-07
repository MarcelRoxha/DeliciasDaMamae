package com.marcel.a.n.roxha.deliciasdamamae.controller.helper;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.marcel.a.n.roxha.deliciasdamamae.config.ConfiguracaoFirebase;
import com.marcel.a.n.roxha.deliciasdamamae.model.BolosModel;
import com.marcel.a.n.roxha.deliciasdamamae.model.ModeloBolosAdicionadosVitrineQuandoVender;

import java.util.HashMap;
import java.util.Map;

public class ModeloBoloAdicionadoExpostoVitrineParaOpaVendiDAO implements InterfaceModeloBoloAdicionadoExpostoVitrineParaOpaVendi{

    private FirebaseFirestore firestore = ConfiguracaoFirebase.getFirestor();
    private static final String COLLECTION_BOLOS_ADICIONADOS_EXPOSTOS_VITRINE_PARA_OPA_VENDI = "BOLOS_EXPOSTOS_VITRINE";
    private CollectionReference collectionReferenceBoloAdicionadoNaVitrineParaOpaVendi = firestore.collection(COLLECTION_BOLOS_ADICIONADOS_EXPOSTOS_VITRINE_PARA_OPA_VENDI);


    public ModeloBoloAdicionadoExpostoVitrineParaOpaVendiDAO(Context context) {
        this.context = context;
    }

    private Context context;


    @Override
    public boolean salvarBoloAdicionadoNaVitrineParaOpaVendi(BolosModel bolosModel) {

        try{

            if(bolosModel != null){
                Map<String, Object> boloCadastrando = new HashMap<>();
                boloCadastrando.put("idBoloCadastrado",bolosModel.getIdBoloCadastrado());
                boloCadastrando.put("nomeBoloCadastrado", bolosModel.getNomeBoloCadastrado());
                boloCadastrando.put("idReferenciaReceitaCadastrada", bolosModel.getIdReferenciaReceitaCadastrada());
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

                collectionReferenceBoloAdicionadoNaVitrineParaOpaVendi.add(boloCadastrando).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {

                        Toast.makeText(context, "Bolo adicionado a vitrine com sucesso", Toast.LENGTH_SHORT).show();

//
//                        ModeloBolosAdicionadosVitrineQuandoVender bolosAdicionadosVitrineQuandoVender = new ModeloBolosAdicionadosVitrineQuandoVender();
//                        bolosAdicionadosVitrineQuandoVender.setIdModeloBoloAdicionadosVitrineQuandoVender(documentReference.getId());
//                        bolosAdicionadosVitrineQuandoVender.setIdReferenciaBoloCadastradoParaVenda(bolosModel.getIdReferenciaBoloCadastradoParaVenda());
//                        bolosAdicionadosVitrineQuandoVender.setNomeBoloCadastrado(bolosModel.getNomeBoloCadastrado());
//                        bolosAdicionadosVitrineQuandoVender.setPrecoQueFoiVendido(bolosModel.getPrecoQueFoiVendido());
//                        bolosAdicionadosVitrineQuandoVender.setPrecoCadastradoVendaNaLoja(bolosModel.getPrecoCadastradoVendaNaLoja());
//                        bolosAdicionadosVitrineQuandoVender.setPrecoCadastradoVendaIfood(bolosModel.getPrecoCadastradoVendaIfood());
//                        bolosAdicionadosVitrineQuandoVender.setValorSugeridoParaVendaNaBoleria(bolosModel.getValorSugeridoParaVendaNaBoleria());
//                        bolosAdicionadosVitrineQuandoVender.setValorSugeridoParaVendaIfood(bolosModel.getValorSugeridoParaVendaIfood());
//                        bolosAdicionadosVitrineQuandoVender.setEnderecoFoto(bolosModel.getEnderecoFoto());
//                        bolosAdicionadosVitrineQuandoVender.setVendeuNaLoja(bolosModel.isVendeuNaLoja());
//                        bolosAdicionadosVitrineQuandoVender.setVendeuNoIfood(bolosModel.isVendeuNoIfood());
//
//
//                        atualizarIdDoProprioBoloAdicionado(bolosAdicionadosVitrineQuandoVender.getIdModeloBoloAdicionadosVitrineQuandoVender(), bolosAdicionadosVitrineQuandoVender);
//

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
    public boolean atualizarBoloAdicionadoNaVitrineParaOpaVendi(ModeloBolosAdicionadosVitrineQuandoVender bolosModel) {
        return false;
    }

    @Override
    public boolean deletarBoloAdicionadoNaVitrineParaOpaVendi(ModeloBolosAdicionadosVitrineQuandoVender bolosModel) {
        return false;
    }


    private void atualizarIdDoProprioBoloAdicionado(String id, ModeloBolosAdicionadosVitrineQuandoVender boloParaAtualizarOId){

        try{

            Map<String, Object> boloComIdAtualizado = new HashMap<>();
            boloComIdAtualizado.put("idBoloCadastrado","N/D");
            boloComIdAtualizado.put("nomeBoloCadastrado", boloParaAtualizarOId.getNomeBoloCadastrado());
            boloComIdAtualizado.put("idReferenciaBoloCadastradoParaVenda", boloParaAtualizarOId.getIdReferenciaBoloCadastradoParaVenda());
            boloComIdAtualizado.put("precoQueFoiVendido", boloParaAtualizarOId.getPrecoQueFoiVendido());
            boloComIdAtualizado.put("precoCadastradoVendaNaLoja", boloParaAtualizarOId.getPrecoCadastradoVendaNaLoja());
            boloComIdAtualizado.put("precoCadastradoVendaIfood", boloParaAtualizarOId.getPrecoCadastradoVendaIfood());
            boloComIdAtualizado.put("valorSugeridoParaVendaNaBoleria", boloParaAtualizarOId.getValorSugeridoParaVendaIfood());
            boloComIdAtualizado.put("valorSugeridoParaVendaIfood", boloParaAtualizarOId.getValorSugeridoParaVendaNaBoleria());
            boloComIdAtualizado.put("enderecoFoto", boloParaAtualizarOId.getEnderecoFoto());
            boloComIdAtualizado.put("vendeuNoIfood", boloParaAtualizarOId.isVendeuNoIfood());
            boloComIdAtualizado.put("vendeuNaLoja", boloParaAtualizarOId.isVendeuNaLoja());



            collectionReferenceBoloAdicionadoNaVitrineParaOpaVendi.document(id).update(boloComIdAtualizado).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(context, boloParaAtualizarOId.getNomeBoloCadastrado()+ " adicionado com sucesso a vitrine", Toast.LENGTH_SHORT).show();


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

    }
}
