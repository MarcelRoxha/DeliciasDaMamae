package com.marcel.a.n.roxha.deliciasdamamae.helper;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.contentcapture.ContentCaptureCondition;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.marcel.a.n.roxha.deliciasdamamae.R;
import com.marcel.a.n.roxha.deliciasdamamae.activity.FinalizarReceitaActivity;
import com.marcel.a.n.roxha.deliciasdamamae.activity.ProducaoActivity;
import com.marcel.a.n.roxha.deliciasdamamae.config.ConfiguracaoFirebase;
import com.marcel.a.n.roxha.deliciasdamamae.fragments.ReceitaNovaFragment;
import com.marcel.a.n.roxha.deliciasdamamae.model.ModeloIngredienteAdicionadoReceita;
import com.marcel.a.n.roxha.deliciasdamamae.model.ReceitaModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModeloReceitaCadastradaDAO implements InterfaceModeloReceitaCadastradaDAO {

    private FirebaseFirestore firestoreReceitaCadastrada = ConfiguracaoFirebase.getFirestor();
    private final String COLLECTION_RECEITA_CADASTRADA = "RECEITA_CADASTRADA";
    private final String COLLECTION_INGREDIENTES_ADICIONADOS = "INGREDIENTES_ADICIONADOS";
    private CollectionReference referenceReceitaCadastrada = firestoreReceitaCadastrada.collection(COLLECTION_RECEITA_CADASTRADA);
    public String idReceita;
    public TextView textValorIngredientes;
    private Context context;

    public ModeloReceitaCadastradaDAO(Context context){
        this.context = context;

    }


    @Override
    public boolean iniciarCadastroReceitaCadastrando(ReceitaModel receitaModel) {

        try{
            Map<String, Object> receitaInicializada = new HashMap<>();
            receitaInicializada.put("nomeReceita",receitaModel.getNomeReceita());
            receitaInicializada.put("valorTotalReceita","INICIANDO");
            receitaInicializada.put("valoresIngredientes","INICIANDO");
            receitaInicializada.put("quantRendimentoReceita","INICIANDO");
            receitaInicializada.put("porcentagemServico","INICIANDO");

            this.referenceReceitaCadastrada.add(receitaInicializada).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    atualizarIdReceita(documentReference.getId(), receitaModel);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(context, "Algo deu errado! Verifique a sua conexão  \n de internet e tente novamente", Toast.LENGTH_SHORT).show();
                }
            });


        }catch (Exception e){
            System.out.println("Exception e ReceitaCadastradaDAO" + e.getMessage());

        }

        return false;
    }

    @Override
    public boolean finalizarCadastroDaReceitaCadastrando(ReceitaModel receitaModel) {

        try{
            Map<String, Object> receitaFinalizandoCadastro = new HashMap<>();
            receitaFinalizandoCadastro.put("idReceita",receitaModel.getIdReceita());
            receitaFinalizandoCadastro.put("nomeReceita",receitaModel.getNomeReceita());
            receitaFinalizandoCadastro.put("valorTotalReceita",receitaModel.getValorTotalReceita());
            receitaFinalizandoCadastro.put("valoresIngredientes",receitaModel.getValoresIngredientes());
            receitaFinalizandoCadastro.put("quantRendimentoReceita",receitaModel.getQuantRendimentoReceita());
            receitaFinalizandoCadastro.put("porcentagemServico",receitaModel.getPorcentagemServico());

            this.referenceReceitaCadastrada.document(receitaModel.getIdReceita()).update(receitaFinalizandoCadastro).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(context, "Receita finalizada com sucesso...", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, ProducaoActivity.class);
                    context.startActivity(intent);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });


        }catch (Exception e){
            System.out.println("Exception e ReceitaCadastradaDAO" + e.getMessage());

        }



        return false;
    }

    @Override
    public boolean adicionarIngredienteDaReceitaCadastrando(View view, String nomeReceitaCadastrando, ModeloIngredienteAdicionadoReceita modeloIngredienteAdicionadoReceita) {

        try{

            referenceReceitaCadastrada.whereEqualTo("nomeReceita", nomeReceitaCadastrando.trim())
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        List<DocumentSnapshot> snapshotList = queryDocumentSnapshots.getDocuments();

                        for (DocumentSnapshot snapshot : snapshotList){
                            idReceita = snapshot.getId();
                            somarCustoIngredienteAdicionadoNaReceita(view, idReceita, modeloIngredienteAdicionadoReceita.getCustoIngredientePorReceita());
                        }
                        CollectionReference referenciaIngredientesAdicionados = referenceReceitaCadastrada.document(idReceita)
                                .collection(COLLECTION_INGREDIENTES_ADICIONADOS);

                        Map<String, Object> ingredienteAdicionadoNaReceita = new HashMap<>();
                        ingredienteAdicionadoNaReceita.put("idReferenciaItemEmEstoque", modeloIngredienteAdicionadoReceita.getIdReferenciaItemEmEstoque());
                        ingredienteAdicionadoNaReceita.put("nomeIngredienteAdicionadoReceita", modeloIngredienteAdicionadoReceita.getNomeIngredienteAdicionadoReceita());
                        ingredienteAdicionadoNaReceita.put("custoIngredientePorReceita", modeloIngredienteAdicionadoReceita.getCustoIngredientePorReceita());
                        ingredienteAdicionadoNaReceita.put("quantidadeUtilizadaReceita", modeloIngredienteAdicionadoReceita.getQuantidadeUtilizadaReceita());
                        ingredienteAdicionadoNaReceita.put("unidadeMedidaUsadaReceita", modeloIngredienteAdicionadoReceita.getUnidadeMedidaUsadaReceita());

                        referenciaIngredientesAdicionados.add(ingredienteAdicionadoNaReceita).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {

                                Toast.makeText(context, "Ingrediente " + modeloIngredienteAdicionadoReceita.getNomeIngredienteAdicionadoReceita().toUpperCase() + " adicionado com sucesso!", Toast.LENGTH_SHORT).show();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

        }catch (Exception e){

        }

        return false;
    }

    @Override
    public boolean removerIngredienteDaReceitaCadastrando(String idReceita, String idIngrediente, String custoIngredienteNaReceita) {

        double valorRecebidoConvertido = Double.parseDouble(custoIngredienteNaReceita);

        try{

            referenceReceitaCadastrada.document(idReceita).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {

                    ReceitaModel receitaRecuperadaParaAtualizarOValorDosIngredientesAdicionados = documentSnapshot.toObject(ReceitaModel.class);
                    String custoTotalIngredienteRecuperado = receitaRecuperadaParaAtualizarOValorDosIngredientesAdicionados.getValoresIngredientes();
                    double custoTotalIngredienteRecuperadoDaReceitaConvertido = Double.parseDouble(custoTotalIngredienteRecuperado);


                    double subtrairValorTotalDosIngredientesDaReceitaComValorDoIngredienteRemovido = custoTotalIngredienteRecuperadoDaReceitaConvertido - valorRecebidoConvertido;

                    String valorSubtraidoDaReceitaEmTexto = String.valueOf(subtrairValorTotalDosIngredientesDaReceitaComValorDoIngredienteRemovido);

                    ReceitaModel receitaTempParaAtualizarValores = new ReceitaModel();
                    receitaTempParaAtualizarValores.setIdReceita(receitaRecuperadaParaAtualizarOValorDosIngredientesAdicionados.getIdReceita());
                    receitaTempParaAtualizarValores.setNomeReceita(receitaRecuperadaParaAtualizarOValorDosIngredientesAdicionados.getNomeReceita());
                    receitaTempParaAtualizarValores.setValorTotalReceita(receitaRecuperadaParaAtualizarOValorDosIngredientesAdicionados.getValorTotalReceita());
                    receitaTempParaAtualizarValores.setValoresIngredientes(valorSubtraidoDaReceitaEmTexto);
                    receitaTempParaAtualizarValores.setQuantRendimentoReceita(receitaRecuperadaParaAtualizarOValorDosIngredientesAdicionados.getQuantRendimentoReceita());
                    receitaTempParaAtualizarValores.setPorcentagemServico(receitaRecuperadaParaAtualizarOValorDosIngredientesAdicionados.getPorcentagemServico());

                    subtrairValorDoIngredienteNoTotalDeIngredientesNaReceita(receitaRecuperadaParaAtualizarOValorDosIngredientesAdicionados.getIdReceita(),receitaTempParaAtualizarValores);

                    referenceReceitaCadastrada.document(receitaRecuperadaParaAtualizarOValorDosIngredientesAdicionados.getIdReceita()).collection("INGREDIENTES_ADICIONADOS").document(idIngrediente).delete();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(context, "Algo deu errado! Verifique a internet e tente novamente", Toast.LENGTH_SHORT).show();

                }
            });



        }catch (Exception e){

        }


        return false;
    }


    public String getRetorneIdReceitaCadastrando(){
        return this.idReceita;
    }


    private void somarCustoIngredienteAdicionadoNaReceita(View view, String idReceita, String custoIngredientePorReceita) {
        System.out.println("Passou pelo somar custo ingrediente");
        double custoIngredienteConvert = Double.parseDouble(custoIngredientePorReceita.replace(",", "."));
        System.out.println("custoIngredienteConvert: " + custoIngredienteConvert);
        referenceReceitaCadastrada.document(idReceita).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                ReceitaModel receitaRecuperadaParaAtualizarOValorDosIngredientesAdicionados = documentSnapshot.toObject(ReceitaModel.class);
                String custoTotalIngredienteRecuperado = receitaRecuperadaParaAtualizarOValorDosIngredientesAdicionados.getValoresIngredientes();

                if(custoTotalIngredienteRecuperado.equals("INICIANDO")){
                    custoTotalIngredienteRecuperado = "0";
                    System.out.println("custoTotalIngredienteRecuperado = \"0\";: " + custoTotalIngredienteRecuperado);
                }


                double custoDosIngredientesReceitaRecuperada = Double.parseDouble(custoTotalIngredienteRecuperado);
                double somarCustoIngredienteComValorTotalDosIngredientesDaReceitaCadastrada = custoDosIngredientesReceitaRecuperada + custoIngredienteConvert;
                String novoValorParaValorTotalDeIngredientesNaReceita = String.valueOf(somarCustoIngredienteComValorTotalDosIngredientesDaReceitaCadastrada);

                ReceitaModel receitaTempParaAtualizarValores = new ReceitaModel();
                receitaTempParaAtualizarValores.setIdReceita(receitaRecuperadaParaAtualizarOValorDosIngredientesAdicionados.getIdReceita());
                receitaTempParaAtualizarValores.setNomeReceita(receitaRecuperadaParaAtualizarOValorDosIngredientesAdicionados.getNomeReceita());
                receitaTempParaAtualizarValores.setValorTotalReceita(receitaRecuperadaParaAtualizarOValorDosIngredientesAdicionados.getValorTotalReceita());
                receitaTempParaAtualizarValores.setValoresIngredientes(novoValorParaValorTotalDeIngredientesNaReceita);
                receitaTempParaAtualizarValores.setQuantRendimentoReceita(receitaRecuperadaParaAtualizarOValorDosIngredientesAdicionados.getQuantRendimentoReceita());
                receitaTempParaAtualizarValores.setPorcentagemServico(receitaRecuperadaParaAtualizarOValorDosIngredientesAdicionados.getPorcentagemServico());

                somarValorDoIngredienteNoTotalDeIngredientesNaReceita(receitaRecuperadaParaAtualizarOValorDosIngredientesAdicionados.getIdReceita(), receitaTempParaAtualizarValores);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });


    }

    private void subtrairCustoIngredienteAdicionadoNaReceita(String idReceita, String custoIngredientePorReceita){
        double custoIngredienteConvert = Double.parseDouble(custoIngredientePorReceita.replace(",", "."));
        referenceReceitaCadastrada.document(idReceita).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                ReceitaModel receitaRecuperadaParaAtualizarOValorDosIngredientesAdicionados = documentSnapshot.toObject(ReceitaModel.class);
                String custoTotalIngredienteRecuperado = receitaRecuperadaParaAtualizarOValorDosIngredientesAdicionados.getValoresIngredientes();

                if(custoTotalIngredienteRecuperado.equals("INICIANDO")){
                    custoTotalIngredienteRecuperado = "0";
                    System.out.println("custoTotalIngredienteRecuperado = \"0\";: " + custoTotalIngredienteRecuperado);
                }


                double custoDosIngredientesReceitaRecuperada = Double.parseDouble(custoTotalIngredienteRecuperado);
                double somarCustoIngredienteComValorTotalDosIngredientesDaReceitaCadastrada = custoDosIngredientesReceitaRecuperada - custoIngredienteConvert;
                String novoValorParaValorTotalDeIngredientesNaReceita = String.valueOf(somarCustoIngredienteComValorTotalDosIngredientesDaReceitaCadastrada);

                ReceitaModel receitaTempParaAtualizarValores = new ReceitaModel();
                receitaTempParaAtualizarValores.setIdReceita(receitaRecuperadaParaAtualizarOValorDosIngredientesAdicionados.getIdReceita());
                receitaTempParaAtualizarValores.setNomeReceita(receitaRecuperadaParaAtualizarOValorDosIngredientesAdicionados.getNomeReceita());
                receitaTempParaAtualizarValores.setValorTotalReceita(receitaRecuperadaParaAtualizarOValorDosIngredientesAdicionados.getValorTotalReceita());
                receitaTempParaAtualizarValores.setValoresIngredientes(novoValorParaValorTotalDeIngredientesNaReceita);
                receitaTempParaAtualizarValores.setQuantRendimentoReceita(receitaRecuperadaParaAtualizarOValorDosIngredientesAdicionados.getQuantRendimentoReceita());
                receitaTempParaAtualizarValores.setPorcentagemServico(receitaRecuperadaParaAtualizarOValorDosIngredientesAdicionados.getPorcentagemServico());

                subtrairValorDoIngredienteNoTotalDeIngredientesNaReceita(receitaRecuperadaParaAtualizarOValorDosIngredientesAdicionados.getIdReceita(), receitaTempParaAtualizarValores);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    @Override
    public boolean editarReceitaCadastrada(ReceitaModel receitaModel) {
        return false;
    }

    @Override
    public boolean deletarReceitaCadastrada(ReceitaModel receitaModel) {
        return false;
    }

    private void atualizarIdReceita(String id, ReceitaModel receitaModel){


        try{
            Map<String, Object> receitaInicializadaAtualizandoId = new HashMap<>();
            receitaInicializadaAtualizandoId.put("idReceita",id);
            receitaInicializadaAtualizandoId.put("nomeReceita",receitaModel.getNomeReceita());
            receitaInicializadaAtualizandoId.put("valorTotalReceita","INICIANDO");
            receitaInicializadaAtualizandoId.put("valoresIngredientes","INICIANDO");
            receitaInicializadaAtualizandoId.put("quantRendimentoReceita","INICIANDO");
            receitaInicializadaAtualizandoId.put("porcentagemServico","INICIANDO");

            this.referenceReceitaCadastrada.document(id).update(receitaInicializadaAtualizandoId).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(context, "Processo de cadastro de receita iniciado...", Toast.LENGTH_SHORT).show();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });


        }catch (Exception e){
            System.out.println("Exception e ReceitaCadastradaDAO" + e.getMessage());

        }





    }

    private void somarValorDoIngredienteNoTotalDeIngredientesNaReceita(String id, ReceitaModel receitaModel){
        try{
            Map<String, Object> receitaInicializadaAtualizandoId = new HashMap<>();
            receitaInicializadaAtualizandoId.put("idReceita",id);
            receitaInicializadaAtualizandoId.put("nomeReceita",receitaModel.getNomeReceita());
            receitaInicializadaAtualizandoId.put("valorTotalReceita",receitaModel.getValorTotalReceita());
            receitaInicializadaAtualizandoId.put("valoresIngredientes",receitaModel.getValoresIngredientes());
            receitaInicializadaAtualizandoId.put("quantRendimentoReceita",receitaModel.getQuantRendimentoReceita());
            receitaInicializadaAtualizandoId.put("porcentagemServico",receitaModel.getPorcentagemServico());

            this.referenceReceitaCadastrada.document(id).update(receitaInicializadaAtualizandoId).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });


        }catch (Exception e){
            System.out.println("Exception e ReceitaCadastradaDAO" + e.getMessage());

        }
    }

    private void subtrairValorDoIngredienteNoTotalDeIngredientesNaReceita(String id, ReceitaModel receitaModel){
        try{
            Map<String, Object> receitaInicializadaAtualizandoId = new HashMap<>();
            receitaInicializadaAtualizandoId.put("idReceita",id);
            receitaInicializadaAtualizandoId.put("nomeReceita",receitaModel.getNomeReceita());
            receitaInicializadaAtualizandoId.put("valorTotalReceita",receitaModel.getValorTotalReceita());
            receitaInicializadaAtualizandoId.put("valoresIngredientes",receitaModel.getValoresIngredientes());
            receitaInicializadaAtualizandoId.put("quantRendimentoReceita",receitaModel.getQuantRendimentoReceita());
            receitaInicializadaAtualizandoId.put("porcentagemServico",receitaModel.getPorcentagemServico());

            this.referenceReceitaCadastrada.document(id).update(receitaInicializadaAtualizandoId).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(context, "Valor do ingrediente removido da receita", Toast.LENGTH_SHORT).show();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });


        }catch (Exception e){
            System.out.println("Exception e ReceitaCadastradaDAO" + e.getMessage());

        }
    }



}
