package com.marcel.a.n.roxha.deliciasdamamae.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.marcel.a.n.roxha.deliciasdamamae.R;
import com.marcel.a.n.roxha.deliciasdamamae.adapter.IngredienteAdapter;
import com.marcel.a.n.roxha.deliciasdamamae.adapter.IngredienteAdicionadoAdapter;
import com.marcel.a.n.roxha.deliciasdamamae.adapter.ItemEstoqueAdapter;
import com.marcel.a.n.roxha.deliciasdamamae.config.ConfiguracaoFirebase;
import com.marcel.a.n.roxha.deliciasdamamae.model.BolosModel;
import com.marcel.a.n.roxha.deliciasdamamae.model.CalcularValorIngredienteAdicionadoReceitaCompletaCadastrada;
import com.marcel.a.n.roxha.deliciasdamamae.model.ItemEstoqueModel;
import com.marcel.a.n.roxha.deliciasdamamae.model.ReceitaModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditarReceitasCompletasCadastradas extends AppCompatActivity {


    private String idReceitaCompletaCadastradaparaEditar = "";
    private String nomeReceitaCompletaCadastradaparaEditar;
    private String porceosReceita = "";
    private String porcentReceita = "";
    private List<Double> listaIngredientesAdicionadosReceitaCompletaCadastrada = new ArrayList<>();
    private String valorItemReceitaCadastradaCompleta;
    private String valorTotalIngredientesReceitaCadastradaCompleta;
    private String valorIngredienteAlertEditar;
    private Double valor;
    private String idIngredientesReceitaCadastradaCompletaCadastrada = "";
    private boolean statusCarregamentoValorIngredientes;
    private double valorRecuperado = 0;
    private double valorAdicionado = 0;
    private double resultado = 0;

    private Button botaoSalvarAlteracoesReceitaCadastrada ;

    private TextInputEditText inputPorcoesPorFornadaReceitaCompletaCadastradaEditar ;
    private TextView texto_nome_receita_completa_cadastrada_editar ;
    private TextView texto_valor_total_receita_completa_cadastrada_editar ;
    private TextView texto_valor_total_ingredientes_receita_completa_cadastrada_editar ;
    private TextInputEditText inputPorcentagemPorReceitaCompletaCadastradaEditar ;

    private RecyclerView listaIngredientesAdicionadosReceitaCompletaEditar;
    private RecyclerView listaIngredientesEstoqueEditar;

    private FirebaseFirestore db = ConfiguracaoFirebase.getFirestor();
    private CollectionReference referenceReceitaCompleta = db.collection("Receitas_completas");

    private IngredienteAdicionadoAdapter adicionadoAdapter;
    private IngredienteAdapter adapterItemEstoque;

//Swift


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setTheme(R.style.Theme_DeliciasDaMamae);
        setContentView(R.layout.activity_editar_receitas_completas_cadastradas);

        botaoSalvarAlteracoesReceitaCadastrada = findViewById(R.id.botao_salvar_alteracoes_receita_completa_cadastrada_id);
        inputPorcoesPorFornadaReceitaCompletaCadastradaEditar = findViewById(R.id.input_editar_porcoes_por_fornada_receita_completa_id);
        inputPorcentagemPorReceitaCompletaCadastradaEditar = findViewById(R.id.input_editar_porcentagem_acrescimo_receita_completa_id);
        texto_nome_receita_completa_cadastrada_editar = findViewById(R.id.nome_receita_completa_cadastrada_editar_id);
        texto_valor_total_receita_completa_cadastrada_editar = findViewById(R.id.valor_total_receita_cadastrada_completa_editar_id);
        texto_valor_total_ingredientes_receita_completa_cadastrada_editar = findViewById(R.id.valor_total_ingredientes_receitas_cadastrada_completa_editar_id);

        listaIngredientesAdicionadosReceitaCompletaEditar = findViewById(R.id.recyclerView_ingredientes_adicionados_receita_completa_editar_id);
        listaIngredientesEstoqueEditar = findViewById(R.id.recyclerView_ingredientes_estoque_id);

        texto_valor_total_ingredientes_receita_completa_cadastrada_editar.setVisibility(View.GONE);
        texto_valor_total_receita_completa_cadastrada_editar.setVisibility(View.GONE);

        botaoSalvarAlteracoesReceitaCadastrada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(), "Testando botão e inputs:  \n\n" +
                        "inputPorcoesPorFornadaReceitaCompletaCadastrada: " + inputPorcoesPorFornadaReceitaCompletaCadastradaEditar.getText().toString() +"\n\n" +
                        "inputPorcentagemPorReceitaCompletaCadastradaEditar: " + inputPorcentagemPorReceitaCompletaCadastradaEditar.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });
        idReceitaCompletaCadastradaparaEditar = getIntent().getStringExtra("idReceita");

        carregarInformacoesReceitaCompletaCadastrada(idReceitaCompletaCadastradaparaEditar);
        carregarIngredientesAdicionadosReceitaCompletaCadastradaEditar();




    }

    private void carregarInformacoesReceitaCompletaCadastrada(String idReceitaCompletaCadastradaparaEditar) {

        referenceReceitaCompleta.document(idReceitaCompletaCadastradaparaEditar).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                ReceitaModel receitaCompletaCadastradaCompletaCarregamentodeinformacoes = documentSnapshot.toObject(ReceitaModel.class);
                nomeReceitaCompletaCadastradaparaEditar = receitaCompletaCadastradaCompletaCarregamentodeinformacoes.getNomeReceita();

                inputPorcentagemPorReceitaCompletaCadastradaEditar.setText(receitaCompletaCadastradaCompletaCarregamentodeinformacoes.getPorcentagemServico());
                inputPorcoesPorFornadaReceitaCompletaCadastradaEditar.setText(receitaCompletaCadastradaCompletaCarregamentodeinformacoes.getQuantRendimentoReceita());
                texto_nome_receita_completa_cadastrada_editar.setText(nomeReceitaCompletaCadastradaparaEditar);


                Query queryReceitaCompleta = referenceReceitaCompleta.document(idReceitaCompletaCadastradaparaEditar).collection(nomeReceitaCompletaCadastradaparaEditar).orderBy("nameItem", Query.Direction.ASCENDING);

                FirestoreRecyclerOptions<ItemEstoqueModel> options = new FirestoreRecyclerOptions.Builder<ItemEstoqueModel>()
                        .setQuery(queryReceitaCompleta, ItemEstoqueModel.class)
                        .build();

                adicionadoAdapter = new IngredienteAdicionadoAdapter(options);

                listaIngredientesAdicionadosReceitaCompletaEditar.setHasFixedSize(true);
                listaIngredientesAdicionadosReceitaCompletaEditar.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                listaIngredientesAdicionadosReceitaCompletaEditar.setAdapter(adicionadoAdapter);
                listaIngredientesAdicionadosReceitaCompletaEditar.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));
                adicionadoAdapter.startListening();
                calcularValoresIngredientesAdicionados(idReceitaCompletaCadastradaparaEditar, nomeReceitaCompletaCadastradaparaEditar);

                adicionadoAdapter.setOnItemClickListerner(new ItemEstoqueAdapter.OnItemClickLisener() {
                    @Override
                    public void onItemClick(DocumentSnapshot documentSnapshot, int position) {

                        ItemEstoqueModel itemEstoqueModelClicado = documentSnapshot.toObject(ItemEstoqueModel.class);
                        Toast.makeText(getApplicationContext(), "item em estoque é o: " + itemEstoqueModelClicado.getNameItem(), Toast.LENGTH_SHORT).show();

                        if(itemEstoqueModelClicado != null){

                            String nomeReferencia = itemEstoqueModelClicado.getNameItem();

                            AlertDialog.Builder alert = new AlertDialog.Builder(EditarReceitasCompletasCadastradas.this);
                            alert.setTitle(itemEstoqueModelClicado.getNameItem());
                            View viewLayout = getLayoutInflater().inflate(R.layout.layout_alertdialog_editar_ingrediente_receita_completa_cadastrada_editar, null);
                            EditText editQuantUsadaReceita = viewLayout.findViewById(R.id.edit_quant_usada_receita_cadastrada_completa_alert_editar_id);
                            TextView valorTotaldoIngredienteUtilizadonaReceitaCadastradaCompletaAlertEditar = viewLayout.findViewById(R.id.texto_valor_total_do_ingrediente_utilizado_na_receita_cadastrada_completa_alert_editar_id);
                            valorIngredienteAlertEditar = itemEstoqueModelClicado.getValorItemPorReceita();
                            String quantUsadaIngredienteReceitaCadastradaCompletaEditarAlert = itemEstoqueModelClicado.getQuantUsadaReceita();
                            getValorIngredienteAlertEdit();


                            valorTotaldoIngredienteUtilizadonaReceitaCadastradaCompletaAlertEditar.setText(getValorIngredienteAlertEdit());
                            editQuantUsadaReceita.setText(quantUsadaIngredienteReceitaCadastradaCompletaEditarAlert);

                            Button botaoCalcularAlert = viewLayout.findViewById(R.id.botao_calcular_nova_quant_usada_receita_completa_cadasrada_alert_editar_id);

                            botaoCalcularAlert.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    if(editQuantUsadaReceita.getText().toString() != "" || editQuantUsadaReceita.getText().toString() != "0" ){

                                        String nomeReferenciaRecebido = nomeReferencia;
                                        String valorEditTextAlertDialogTextoValorTotalReceita = valorTotaldoIngredienteUtilizadonaReceitaCadastradaCompletaAlertEditar.getText().toString();

                                        valorIngredienteAlertEditar = "0";

                                        FirebaseFirestore firestoreFirestore = ConfiguracaoFirebase.getFirestor();
                                        CollectionReference collectionReferenceItemEstoque = firestoreFirestore.collection("Item_Estoque");
                                        collectionReferenceItemEstoque.whereEqualTo("nameItem",nomeReferenciaRecebido ).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                            @Override
                                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {


                                                String idRecuperado = "";
                                                List<DocumentSnapshot> snapshotList = queryDocumentSnapshots.getDocuments();
                                                for (DocumentSnapshot snapshot : snapshotList) {

                                                    idRecuperado =  snapshot.getId();

                                                }

                                                if(idRecuperado != ""){


                                                    FirebaseFirestore.getInstance().collection("Item_Estoque").document(idRecuperado).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                        @Override
                                                        public void onSuccess(DocumentSnapshot documentSnapshot) {

                                                            ItemEstoqueModel itemEstoqueModel = documentSnapshot.toObject(ItemEstoqueModel.class);

                                                            String valorItemEstoque = itemEstoqueModel.getValorItem();

                                                            double valorItemFracionado = 0;
                                                            valorItemFracionado =  Double.parseDouble(valorItemEstoque);
                                                            double valorQuantidadeDigitadoConvertido = 0;
                                                            valorQuantidadeDigitadoConvertido=  Double.parseDouble(valorTotaldoIngredienteUtilizadonaReceitaCadastradaCompletaAlertEditar.getText().toString());
                                                            double resultadodoCalculoEditQuantUsada = 0;
                                                                if(valorItemFracionado > 0 && valorQuantidadeDigitadoConvertido > 0){

                                                                    resultadodoCalculoEditQuantUsada = valorQuantidadeDigitadoConvertido * valorItemFracionado;
                                                                }

                                                            valorIngredienteAlertEditar = "0";
                                                                if(resultadodoCalculoEditQuantUsada > 0){
                                                                    valorIngredienteAlertEditar = String.valueOf(resultadodoCalculoEditQuantUsada);
                                                                    getValorIngredienteAlertEdit();
                                                                    valorTotaldoIngredienteUtilizadonaReceitaCadastradaCompletaAlertEditar.setText(getValorIngredienteAlertEdit());
                                                                }


                                                        }
                                                    });

                                                }


                                            }
                                        });
                                    }
                                    valorIngredienteAlertEditar = "0";
                                }
                            });




                            alert.setPositiveButton("SALVAR INGREDIENTE", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {


                                }
                            }).setNeutralButton("REMOVER DA RECEITA", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                        Toast.makeText(getApplicationContext(), "Foi clicado no remover", Toast.LENGTH_SHORT).show();

                                }
                            });
                            alert.setView(viewLayout);
                            alert.create();
                            alert.show();

                        }
                    }
                });
            }
        });

    }

    private String getValorIngredienteAlertEdit() {


        return valorIngredienteAlertEditar;
    }

    private void calcularValorIngredienteAlertEditar(String nomeReferencia, String valorDigitado) {


    }


    private void carregarIngredientesAdicionadosReceitaCompletaCadastradaEditar() {


/*
        referenceReceitaCompleta.document(idReceitaCompletaCadastradaparaEditar).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                ReceitaModel receitaCompletaCadastradaCompleta = documentSnapshot.toObject(ReceitaModel.class);







                calcularValoresIngredientesAdicionados(idReceitaCompletaCadastradaparaEditar, nomeReceitaCompletaCadastradaparaEditar);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });*/

    }
    private void calcularValoresIngredientesAdicionados(String idReceitaCadastradaCompleta, String nomeReceitaCompletaCadastrada){


        CollectionReference collectionReferencelistaIngredientesAdicionadosReceitaCompletaCalcularValor =  referenceReceitaCompleta.document(idReceitaCadastradaCompleta).collection(nomeReceitaCompletaCadastrada);

        collectionReferencelistaIngredientesAdicionadosReceitaCompletaCalcularValor.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                List<DocumentSnapshot> listIngredientes = queryDocumentSnapshots.getDocuments();
                for (DocumentSnapshot list: listIngredientes){

                    idIngredientesReceitaCadastradaCompletaCadastrada = list.getId();

                    collectionReferencelistaIngredientesAdicionadosReceitaCompletaCalcularValor.document(idIngredientesReceitaCadastradaCompletaCadastrada).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {

                            ItemEstoqueModel itemEstoqueModel = documentSnapshot.toObject(ItemEstoqueModel.class);

                            assert itemEstoqueModel != null;

                            valorItemReceitaCadastradaCompleta = itemEstoqueModel.getValorItemPorReceita();

                            valor = Double.parseDouble(valorItemReceitaCadastradaCompleta);
                            listaIngredientesAdicionadosReceitaCompletaCadastrada.add(valor);

                            guardarValoresIngredientes(valor);


                        }

                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
                }



            }
        });


    }

    private void carregarListaIngredientesEstoque(){

        Query query = FirebaseFirestore.getInstance().collection("Item_Estoque").orderBy("nameItem", Query.Direction.ASCENDING);



        FirestoreRecyclerOptions<ItemEstoqueModel> options = new FirestoreRecyclerOptions.Builder<ItemEstoqueModel>()
                .setQuery(query, ItemEstoqueModel.class)
                .build();

        adapterItemEstoque = new IngredienteAdapter(options);

        listaIngredientesEstoqueEditar.setHasFixedSize(true);
        listaIngredientesEstoqueEditar.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        listaIngredientesEstoqueEditar.setAdapter(adapterItemEstoque);
        listaIngredientesEstoqueEditar.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));

        adapterItemEstoque.setOnItemClickListerner(new IngredienteAdapter.OnItemClickLisener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {



                ItemEstoqueModel itemEstoqueModel = documentSnapshot.toObject(ItemEstoqueModel.class);

                assert itemEstoqueModel != null;

                String nomeItemAdd = itemEstoqueModel.getNameItem();
                String valorItemAdd = itemEstoqueModel.getValorItemPorReceita();
                String quantItemAdd = itemEstoqueModel.getQuantUsadaReceita();



                adicionarIngredienteReceitaCompleta(nomeReceitaCompletaCadastradaparaEditar, nomeItemAdd, valorItemAdd, quantItemAdd);





            }


        });



    }

    private double guardarValoresIngredientes(double valorIngrediente){

        resultado += valorIngrediente;
        System.out.println("Valor do resultado no guardar valores----------->: " + resultado);


        String resultadoConvertidoString = String.valueOf(resultado);
        System.out.println("Valor do resultadoConvertidoString no guardar valores----------->: " + resultadoConvertidoString);

        texto_valor_total_ingredientes_receita_completa_cadastrada_editar.setVisibility(View.VISIBLE);
        texto_valor_total_ingredientes_receita_completa_cadastrada_editar.setText(resultadoConvertidoString);

        return resultado;
    }

    private  void adicionarIngredienteReceitaCompleta(String nomeReceita, String nomeItemAdicionado, String valorItemAdicionado, String quantItemAdicionado){

     /*   FirebaseFirestore.getInstance().collection("Receitas_completas").whereEqualTo("nomeReceita", nomeReceita).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                List<DocumentSnapshot> snapshotList = queryDocumentSnapshots.getDocuments();


                List<String> listaReceita = new ArrayList<>();

                for (DocumentSnapshot snapshot : snapshotList){

                    idReceita = snapshot.getId();

                }

                CollectionReference reference = db.collection("Receitas_completas").document(idReceita).collection(nomeReceitaDigitado);

                Map<String, Object> ingredietenAdicionadoReceita = new HashMap<>();
                ingredietenAdicionadoReceita.put("nameItem", nomeItemAdicionado);
                ingredietenAdicionadoReceita.put("valorItemPorReceita", valorItemAdicionado);
                ingredietenAdicionadoReceita.put("quantUsadaReceita", quantItemAdicionado);

                reference.add(ingredietenAdicionadoReceita).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {

                        valorConvert = Double.parseDouble(valorItemAdicionado);
                        listValoresItensAdd.add(valorConvert);
                        contItem++ ;

                        setValorReceitaSalvo();
                        textValorIngredientes.setText("Valores adicionados R$: " + setValorReceitaSalvo());


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
        });*/

        Toast.makeText(getApplicationContext(), "Clicado no item: \n\n" +
                "nomeReceita: " + nomeReceita + " \n\n nomeItemAdicionado : " + nomeItemAdicionado + "\n\n valorItemAdicionado:  "+ valorItemAdicionado + "\n\n quantItemAdicionado: " + quantItemAdicionado, Toast.LENGTH_SHORT).show();

    }


    @Override
    protected void onStart() {
        carregarListaIngredientesEstoque();
        adapterItemEstoque.startListening();
        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();
       adicionadoAdapter.stopListening();
        adapterItemEstoque.stopListening();
    }
}