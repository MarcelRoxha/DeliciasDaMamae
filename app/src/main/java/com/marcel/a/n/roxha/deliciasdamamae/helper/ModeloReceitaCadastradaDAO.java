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
import com.google.android.gms.tasks.Task;
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
import com.marcel.a.n.roxha.deliciasdamamae.model.ModeloItemEstoque;
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
    private final String COLLECTION_ITEM_ESTOQUE = "ITEM_ESTOQUE";
    private CollectionReference referenceReceitaCadastrada ;
    private CollectionReference referenceItemEstoque ;
    public String idReceita;
    public TextView textValorIngredientes;
    private Context context;

    public ModeloReceitaCadastradaDAO(Context context){
        this.context = context;
        referenceReceitaCadastrada = firestoreReceitaCadastrada.collection(COLLECTION_RECEITA_CADASTRADA);
        referenceItemEstoque = firestoreReceitaCadastrada.collection(COLLECTION_ITEM_ESTOQUE);

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
    public boolean adicionarIngredienteDaReceitaCadastrando(String nomeReceitaCadastrando, ModeloIngredienteAdicionadoReceita modeloIngredienteAdicionadoReceita) {

        try{

            referenceReceitaCadastrada.whereEqualTo("nomeReceita", nomeReceitaCadastrando.trim())
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        List<DocumentSnapshot> snapshotList = queryDocumentSnapshots.getDocuments();

                        for (DocumentSnapshot snapshot : snapshotList){
                            idReceita = snapshot.getId();
                            somarCustoIngredienteAdicionadoNaReceita(idReceita, modeloIngredienteAdicionadoReceita.getCustoIngredientePorReceita());
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


    public void adicionarIngredienteNaReceitaJaCadastrada(String idReceita, ModeloIngredienteAdicionadoReceita modeloIngredienteAdicionadoReceita){


        referenceReceitaCadastrada.document(idReceita).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                ReceitaModel receitaModelCadastradaRecuperada = documentSnapshot.toObject(ReceitaModel.class);
                String idReceitaRecuperada = receitaModelCadastradaRecuperada.getIdReceita();
                String nomeReceitaRecuperada = receitaModelCadastradaRecuperada.getNomeReceita();
                String porcentagemReceitaRecuperada = receitaModelCadastradaRecuperada.getPorcentagemServico();
                String valorTotalIngredientesReceitaRecuperada = receitaModelCadastradaRecuperada.getValoresIngredientes().replace(",", ".");
                String valorTotalReceitaRecuperada = receitaModelCadastradaRecuperada.getValorTotalReceita().replace(",", ".");
                String quantoRendeCadaFornadaReceitaRecuperada = receitaModelCadastradaRecuperada.getQuantRendimentoReceita();

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

                        double valorTotalIngredientesConvertido = Double.parseDouble(valorTotalIngredientesReceitaRecuperada);
                        double porcentagemRecuperadaDaReceitaConvertido = Double.parseDouble(porcentagemReceitaRecuperada);

                        double valorDoIngredienteAdicionado = Double.parseDouble(modeloIngredienteAdicionadoReceita.getCustoIngredientePorReceita().replace(",", "."));

                        double novoValorDosIngredientesConvertido = valorTotalIngredientesConvertido + valorDoIngredienteAdicionado;


                        double valorDaPorcentagemDaReceitaConvertido = (novoValorDosIngredientesConvertido * porcentagemRecuperadaDaReceitaConvertido) / 100;
                        double novoValorTotalDaReceitaConvertido = novoValorDosIngredientesConvertido + valorDaPorcentagemDaReceitaConvertido;

                        String novoValorDoTotalDeIngredientesAdicionados = String.valueOf(novoValorDosIngredientesConvertido);
                        String novoValorTotalDaReceitaCadastrada = String.valueOf(novoValorTotalDaReceitaConvertido);


                        ReceitaModel receitaModelAtualizadaComOsNovosValores = new ReceitaModel();
                        receitaModelAtualizadaComOsNovosValores.setIdReceita(idReceitaRecuperada);
                        receitaModelAtualizadaComOsNovosValores.setNomeReceita(nomeReceitaRecuperada);
                        receitaModelAtualizadaComOsNovosValores.setQuantRendimentoReceita(quantoRendeCadaFornadaReceitaRecuperada);
                        receitaModelAtualizadaComOsNovosValores.setPorcentagemServico(porcentagemReceitaRecuperada);
                        receitaModelAtualizadaComOsNovosValores.setValorTotalReceita(novoValorTotalDaReceitaCadastrada);
                        receitaModelAtualizadaComOsNovosValores.setValoresIngredientes(novoValorDoTotalDeIngredientesAdicionados);

                        Map<String, Object> receitaComNovosValores = new HashMap<>();
                        receitaComNovosValores.put("idReceita",receitaModelAtualizadaComOsNovosValores.getIdReceita());
                        receitaComNovosValores.put("nomeReceita",receitaModelAtualizadaComOsNovosValores.getNomeReceita());
                        receitaComNovosValores.put("valorTotalReceita",receitaModelAtualizadaComOsNovosValores.getValorTotalReceita());
                        receitaComNovosValores.put("valoresIngredientes",receitaModelAtualizadaComOsNovosValores.getValoresIngredientes());
                        receitaComNovosValores.put("quantRendimentoReceita",receitaModelAtualizadaComOsNovosValores.getQuantRendimentoReceita());
                        receitaComNovosValores.put("porcentagemServico",receitaModelAtualizadaComOsNovosValores.getPorcentagemServico());




                        referenceReceitaCadastrada.document(idReceitaRecuperada).update(receitaComNovosValores).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(context, "Receita teve os valores atualizados", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });



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


    }




    @Override
    public boolean editarIngredienteAdicionadoNaReceita(String idReceitaEditando, String valorParaSubtrair, String idIngredienteEditando, ModeloIngredienteAdicionadoReceita modeloIngredienteAdicionadoReceita) {

        try{

            referenceItemEstoque.document(idIngredienteEditando).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {

                    ModeloItemEstoque itemEstoqueCadastradoRecuperadoParaPegarValorAntigoDoIngredienteNaReceita = documentSnapshot.toObject(ModeloItemEstoque.class);

                    double valorDoItemEstoqueCadastradoParaSubtrairConvertido = Double.parseDouble(itemEstoqueCadastradoRecuperadoParaPegarValorAntigoDoIngredienteNaReceita.getCustoPorReceitaItemEstoque().replace(",", "."));

                    referenceReceitaCadastrada.document(idReceitaEditando).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {

                            ReceitaModel receitaModelRecuperadaParaSubtrairValorDoIngrediente = documentSnapshot.toObject(ReceitaModel.class);
                            String idReceitaCadastradaRecuperada = receitaModelRecuperadaParaSubtrairValorDoIngrediente.getIdReceita();
                            String nomeReceitaCadastradaRecuperada = receitaModelRecuperadaParaSubtrairValorDoIngrediente.getNomeReceita();
                            String porcentagemDeServicoReceitaCadastrada = receitaModelRecuperadaParaSubtrairValorDoIngrediente.getPorcentagemServico();
                            String quantidadeQueRendePorFornadaReceitaCadastrada = receitaModelRecuperadaParaSubtrairValorDoIngrediente.getQuantRendimentoReceita();



                            double porcentagemDaReceitaQueEstaEditandoConvertido = Double.parseDouble(receitaModelRecuperadaParaSubtrairValorDoIngrediente.getPorcentagemServico().replace(",", "."));



                            double valorTotalDosIngredientesNaReceitaQueEstaEditando = Double.parseDouble(receitaModelRecuperadaParaSubtrairValorDoIngrediente.getValoresIngredientes().replace(",", "."));
                            double valorDoIngredienteEditadoConvertido = Double.parseDouble(modeloIngredienteAdicionadoReceita.getCustoIngredientePorReceita().replace(",", "."));
                            double resultadoDaSubtracaoDosValoresDoIngredienteERNoTotalDeIngredientesNaReceita = valorTotalDosIngredientesNaReceitaQueEstaEditando - valorDoItemEstoqueCadastradoParaSubtrairConvertido;

                            double novoValorDoTotalDeIngredientesReceita = resultadoDaSubtracaoDosValoresDoIngredienteERNoTotalDeIngredientesNaReceita + valorDoIngredienteEditadoConvertido;

                            if(novoValorDoTotalDeIngredientesReceita > 0){
                                double resultadoValorTotalDaReceitaCadastradaRecuperadaConvertido = (novoValorDoTotalDeIngredientesReceita * porcentagemDaReceitaQueEstaEditandoConvertido) / 100;

                                String novoValorTotalDaReceitaQueEstouEditando = String.valueOf(resultadoValorTotalDaReceitaCadastradaRecuperadaConvertido);
                                String novoValorDoTotalDosIngredientesAdicionadosNaReceita = String.valueOf(novoValorDoTotalDeIngredientesReceita);

                                ReceitaModel receitaModelComOsValoresAlterados = new ReceitaModel();
                                receitaModelComOsValoresAlterados.setIdReceita(idReceitaCadastradaRecuperada);
                                receitaModelComOsValoresAlterados.setNomeReceita(nomeReceitaCadastradaRecuperada);
                                receitaModelComOsValoresAlterados.setPorcentagemServico(porcentagemDeServicoReceitaCadastrada);
                                receitaModelComOsValoresAlterados.setQuantRendimentoReceita(quantidadeQueRendePorFornadaReceitaCadastrada);
                                receitaModelComOsValoresAlterados.setValorTotalReceita(novoValorTotalDaReceitaQueEstouEditando);
                                receitaModelComOsValoresAlterados.setValoresIngredientes(novoValorDoTotalDosIngredientesAdicionadosNaReceita);

                                Map<String, Object> receitaInicializadaAtualizandoId = new HashMap<>();
                                receitaInicializadaAtualizandoId.put("idReceita",receitaModelComOsValoresAlterados.getIdReceita());
                                receitaInicializadaAtualizandoId.put("nomeReceita",receitaModelComOsValoresAlterados.getNomeReceita());
                                receitaInicializadaAtualizandoId.put("valorTotalReceita",receitaModelComOsValoresAlterados.getValorTotalReceita());
                                receitaInicializadaAtualizandoId.put("valoresIngredientes",receitaModelComOsValoresAlterados.getValoresIngredientes());
                                receitaInicializadaAtualizandoId.put("quantRendimentoReceita",receitaModelComOsValoresAlterados.getQuantRendimentoReceita());
                                receitaInicializadaAtualizandoId.put("porcentagemServico",receitaModelComOsValoresAlterados.getPorcentagemServico());

                                referenceReceitaCadastrada.document(receitaModelComOsValoresAlterados.getIdReceita()).update(receitaInicializadaAtualizandoId).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {

                                        Toast.makeText(context, "Valor da Receita " + receitaModelComOsValoresAlterados.getNomeReceita().toUpperCase() + "atualizado com sucesso", Toast.LENGTH_SHORT).show();

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                    }
                                });

                            }




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


/*




            FirebaseFirestore firestoreItemEstoqueCadastrado = ConfiguracaoFirebase.getFirestor();
            Task<DocumentSnapshot> documentReferenceItemEstoqueCadastrado = firestoreItemEstoqueCadastrado.collection("ITEM_ESTOQUE")
                    .document(modeloIngredienteAdicionadoReceita.getIdReferenciaItemEmEstoque())
                    .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {




                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });


*/



                    DocumentReference referenciaIngredientesEditados = referenceReceitaCadastrada.document(idReceitaEditando)
                            .collection(COLLECTION_INGREDIENTES_ADICIONADOS).document(idIngredienteEditando);

                    Map<String, Object> ingredienteEditadoNaReceita = new HashMap<>();
                    ingredienteEditadoNaReceita.put("idReferenciaItemEmEstoque", modeloIngredienteAdicionadoReceita.getIdReferenciaItemEmEstoque());
                    ingredienteEditadoNaReceita.put("nomeIngredienteAdicionadoReceita", modeloIngredienteAdicionadoReceita.getNomeIngredienteAdicionadoReceita());
                    ingredienteEditadoNaReceita.put("custoIngredientePorReceita", modeloIngredienteAdicionadoReceita.getCustoIngredientePorReceita());
                    ingredienteEditadoNaReceita.put("quantidadeUtilizadaReceita", modeloIngredienteAdicionadoReceita.getQuantidadeUtilizadaReceita());
                    ingredienteEditadoNaReceita.put("unidadeMedidaUsadaReceita", modeloIngredienteAdicionadoReceita.getUnidadeMedidaUsadaReceita());

                    referenciaIngredientesEditados.update(ingredienteEditadoNaReceita).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });


        }catch (Exception e){
            System.out.println("Error ----------------------------------> :" + e.getMessage());
        }




        return false;
    }

    public void editarValorDoIngredienteEditadoNaReceitaJaCadastrada(String idReceita, String valorIngredienteSubtrair, String valorIngredienteSomar){

        String valorIngredienteNovo = valorIngredienteSomar.replace(",", ".");
        String valorIngredienteAntigo = valorIngredienteSubtrair.replace(",", ".");

        System.out.println("valorIngredienteNovo:---------------------------------------------------------------" + valorIngredienteNovo);
        System.out.println("valorIngredienteAntigo:---------------------------------------------------------------" + valorIngredienteAntigo);


        referenceReceitaCadastrada.document(idReceita).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                ReceitaModel receitaModelEditarValorDaReceitaEdititandoValorDoIngrediente = documentSnapshot.toObject(ReceitaModel.class);

                String custoTotalIngredienteRecuperado = receitaModelEditarValorDaReceitaEdititandoValorDoIngrediente.getValoresIngredientes();
                String valorTotalReceitaRecuperado = receitaModelEditarValorDaReceitaEdititandoValorDoIngrediente.getValorTotalReceita().replace(",", ".");
                System.out.println("Entrou no addOnSucessListener---------------------------------------------------------------");



                double custoTotalIngredienteRecuperadoConvertido = Double.parseDouble(custoTotalIngredienteRecuperado);
                double valorTotalReceitaRecuperadoConvertido = Double.parseDouble(valorTotalReceitaRecuperado);
                double valorIngredienteEditadoConvertido = Double.parseDouble(valorIngredienteNovo);
                double valorIngredienteAntigoConvertido = Double.parseDouble(valorIngredienteAntigo);

                double resultadoSubtracaoCustoTotalIngredientes = custoTotalIngredienteRecuperadoConvertido - valorIngredienteAntigoConvertido;
                double resultadoSubtracao2CustoTotalReceita = valorTotalReceitaRecuperadoConvertido - valorIngredienteAntigoConvertido;

                double resultadoSomaCustoTotalIngredientes = resultadoSubtracaoCustoTotalIngredientes + valorIngredienteEditadoConvertido;
                double resultadoSomaCustoTotalReceita = resultadoSubtracao2CustoTotalReceita + valorIngredienteEditadoConvertido;




                if (resultadoSomaCustoTotalIngredientes > 0 && resultadoSomaCustoTotalReceita > 0){

                    String idReceitaCadastrada = receitaModelEditarValorDaReceitaEdititandoValorDoIngrediente.getIdReceita();
                    String nomeReceitaCadastrada = receitaModelEditarValorDaReceitaEdititandoValorDoIngrediente.getNomeReceita();
                    String porcentagemReceitaCadastrada = receitaModelEditarValorDaReceitaEdititandoValorDoIngrediente.getPorcentagemServico();
                    String quantidadeRendimentoPorFornada = receitaModelEditarValorDaReceitaEdititandoValorDoIngrediente.getQuantRendimentoReceita();
                    String novoValorTotalIngredientesNaReceitaCadastrada = String.valueOf(resultadoSomaCustoTotalIngredientes);
                    String novoValorTotalReceitaCadastrada = String.valueOf(resultadoSomaCustoTotalReceita);


                    ReceitaModel receitaComNovosValoresDeIngredientesETotal = new ReceitaModel();

                    receitaComNovosValoresDeIngredientesETotal.setIdReceita(idReceitaCadastrada);
                    receitaComNovosValoresDeIngredientesETotal.setNomeReceita(nomeReceitaCadastrada);
                    receitaComNovosValoresDeIngredientesETotal.setPorcentagemServico(porcentagemReceitaCadastrada);
                    receitaComNovosValoresDeIngredientesETotal.setQuantRendimentoReceita(quantidadeRendimentoPorFornada);
                    receitaComNovosValoresDeIngredientesETotal.setValoresIngredientes(novoValorTotalIngredientesNaReceitaCadastrada);
                    receitaComNovosValoresDeIngredientesETotal.setValorTotalReceita(novoValorTotalReceitaCadastrada);

                    try{
                        Map<String, Object> receitaCadastradaParaEditar = new HashMap<>();
                        receitaCadastradaParaEditar.put("idReceita",receitaComNovosValoresDeIngredientesETotal.getIdReceita());
                        receitaCadastradaParaEditar.put("nomeReceita",receitaComNovosValoresDeIngredientesETotal.getNomeReceita());
                        receitaCadastradaParaEditar.put("valorTotalReceita",receitaComNovosValoresDeIngredientesETotal.getValorTotalReceita());
                        receitaCadastradaParaEditar.put("valoresIngredientes",receitaComNovosValoresDeIngredientesETotal.getValoresIngredientes());
                        receitaCadastradaParaEditar.put("quantRendimentoReceita",receitaComNovosValoresDeIngredientesETotal.getQuantRendimentoReceita());
                        receitaCadastradaParaEditar.put("porcentagemServico",receitaComNovosValoresDeIngredientesETotal.getPorcentagemServico());

                        referenceReceitaCadastrada.document(receitaComNovosValoresDeIngredientesETotal.getIdReceita()).update(receitaCadastradaParaEditar).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(context, "Atualizou com sucesso a nova receita...", Toast.LENGTH_SHORT).show();

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
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
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


    private void somarCustoIngredienteAdicionadoNaReceita(String idReceita, String custoIngredientePorReceita) {
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
                    Toast.makeText(context, "Valor da receita atualizado com sucesso", Toast.LENGTH_SHORT).show();

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
