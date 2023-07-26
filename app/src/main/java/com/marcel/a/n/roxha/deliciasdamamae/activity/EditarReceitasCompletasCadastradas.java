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
import com.marcel.a.n.roxha.deliciasdamamae.adapter.IngredienteAdicionadoAdapter;
import com.marcel.a.n.roxha.deliciasdamamae.adapter.ItemEstoqueAdapter;
import com.marcel.a.n.roxha.deliciasdamamae.adapter.ModeloItemEstoqueAdapter;
import com.marcel.a.n.roxha.deliciasdamamae.config.ConfiguracaoFirebase;
import com.marcel.a.n.roxha.deliciasdamamae.model.ItemEstoqueModel;
import com.marcel.a.n.roxha.deliciasdamamae.model.ModeloIngredienteAdicionadoReceita;
import com.marcel.a.n.roxha.deliciasdamamae.model.ModeloItemEstoque;
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
    private String textoNovoCalculoValorIngredienteNaRececita;
    private String textoAntigoValorDoIngredienteNaReceita;
    private String unidadeMedidaReferenciaDoIngredienteQueEstaEditandoAdicionadoNaReceita;

    private final String COLLECTION_INGREDIENTES_ADICIONADOS = "INGREDIENTES_ADICIONADOS";



    public String idReferenciaDoIngredienteClicado = "";
    public String idReferenciaDoItemCadastradoEmEstoque = "";
    public String nomeReferenciaDoIngredienteClicado = "";
    public String quantidadeUtilizadaReceitaReferenciaDoIngredienteClicado = "";




    private Double valor;
    private String idIngredientesReceitaCadastradaCompletaCadastrada = "";
    private boolean statusCarregamentoValorIngredientes;
    private double valorRecuperado = 0;
    private double valorAdicionado = 0;
    private double resultado = 0;

    private Button botaoSalvarAlteracoesReceitaCadastrada ;

    private TextInputEditText inputPorcoesPorFornadaReceitaCompletaCadastradaEditar ;
    private TextInputEditText editarNomeReceitaInput ;
    private TextView texto_nome_receita_completa_cadastrada_editar ;
    private TextView texto_valor_total_receita_completa_cadastrada_editar ;
    private TextView texto_valor_total_ingredientes_receita_completa_cadastrada_editar ;
    private TextInputEditText inputPorcentagemPorReceitaCompletaCadastradaEditar ;

    private RecyclerView listaIngredientesAdicionadosReceitaCompletaEditar;
    private RecyclerView listaIngredientesEstoqueEditar;

    private FirebaseFirestore db = ConfiguracaoFirebase.getFirestor();
    private CollectionReference referenceReceitaCompleta = db.collection("RECEITA_CADASTRADA");

    private IngredienteAdicionadoAdapter adicionadoAdapter;
    private ModeloItemEstoqueAdapter adapterItemEstoque;
    private ReceitaModel receitaModelCadastradaParaAtualizar;

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
        idReceitaCompletaCadastradaparaEditar = getIntent().getStringExtra("idReceita");

        receitaModelCadastradaParaAtualizar = new ReceitaModel();


        botaoSalvarAlteracoesReceitaCadastrada = findViewById(R.id.botao_salvar_alteracoes_receita_completa_cadastrada_id);
        inputPorcoesPorFornadaReceitaCompletaCadastradaEditar = findViewById(R.id.input_editar_porcoes_por_fornada_receita_completa_id);
        inputPorcentagemPorReceitaCompletaCadastradaEditar = findViewById(R.id.input_editar_porcentagem_acrescimo_receita_completa_id);
        texto_nome_receita_completa_cadastrada_editar = findViewById(R.id.nome_receita_completa_cadastrada_editar_id);
        texto_valor_total_receita_completa_cadastrada_editar = findViewById(R.id.valor_total_receita_cadastrada_completa_editar_id);
        texto_valor_total_ingredientes_receita_completa_cadastrada_editar = findViewById(R.id.valor_total_ingredientes_receitas_cadastrada_completa_editar_id);

        listaIngredientesAdicionadosReceitaCompletaEditar = findViewById(R.id.recyclerView_ingredientes_adicionados_receita_completa_editar_id);
        listaIngredientesEstoqueEditar = findViewById(R.id.recyclerView_ingredientes_estoque_id);
        editarNomeReceitaInput = findViewById(R.id.editar_nome_receita_cadastrada_id);

        texto_valor_total_ingredientes_receita_completa_cadastrada_editar.setVisibility(View.GONE);
        texto_valor_total_receita_completa_cadastrada_editar.setVisibility(View.GONE);

        botaoSalvarAlteracoesReceitaCadastrada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (inputPorcoesPorFornadaReceitaCompletaCadastradaEditar.getText().toString() != "" &&
                        !inputPorcoesPorFornadaReceitaCompletaCadastradaEditar.getText().toString().equals("0")
                        && inputPorcentagemPorReceitaCompletaCadastradaEditar.getText().toString() != ""
                        && !inputPorcentagemPorReceitaCompletaCadastradaEditar.getText().toString().equals("0")
                        && editarNomeReceitaInput.getText().toString() != "" && !editarNomeReceitaInput.getText().toString().equals("0")) {


                    String nomeDoEditNomeReceita = editarNomeReceitaInput.getText().toString().trim();
                    String porcentagemDoEditTexto = inputPorcentagemPorReceitaCompletaCadastradaEditar.getText().toString();
                    String quantidadeQueRendePorFornada = inputPorcoesPorFornadaReceitaCompletaCadastradaEditar.getText().toString();


                    FirebaseFirestore firestoreReceitaCadastrada = ConfiguracaoFirebase.getFirestor();
                    CollectionReference referenceReceitaCadastrada = firestoreReceitaCadastrada.collection("RECEITA_CADASTRADA");

                    referenceReceitaCadastrada.document(idReceitaCompletaCadastradaparaEditar).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {

                            ReceitaModel receitaModelCadastradaRecuperada = documentSnapshot.toObject(ReceitaModel.class);

                            String idReceitaCadastrada = receitaModelCadastradaRecuperada.getIdReceita();

                            String valorTotalIngredientesReceita = receitaModelCadastradaRecuperada.getValoresIngredientes().replace(",", ".");


                            double porcentagemDaReceitaCadastradaConvertido = Double.parseDouble(porcentagemDoEditTexto);
                            double valorTotalIngredientesConvertido = Double.parseDouble(valorTotalIngredientesReceita);

                            double resultadoPorcentagemNovo = ((valorTotalIngredientesConvertido * porcentagemDaReceitaCadastradaConvertido) / 100) + valorTotalIngredientesConvertido;


                            String novoValorDosIngredientesAdicionados = String.valueOf(valorTotalIngredientesConvertido);
                            String novoValorTotalDaReceitaCadastrada = String.valueOf(resultadoPorcentagemNovo);


                            ReceitaModel receitaModelComNovosValores = new ReceitaModel();


                            receitaModelComNovosValores.setIdReceita(idReceitaCadastrada);
                            receitaModelComNovosValores.setNomeReceita(nomeDoEditNomeReceita);
                            receitaModelComNovosValores.setPorcentagemServico(porcentagemDoEditTexto);
                            receitaModelComNovosValores.setQuantRendimentoReceita(quantidadeQueRendePorFornada);
                            receitaModelComNovosValores.setValoresIngredientes(novoValorDosIngredientesAdicionados);
                            receitaModelComNovosValores.setValorTotalReceita(novoValorTotalDaReceitaCadastrada);

                            try {
                                Map<String, Object> receitaInicializadaAtualizandoId = new HashMap<>();
                                receitaInicializadaAtualizandoId.put("idReceita", receitaModelComNovosValores.getIdReceita());
                                receitaInicializadaAtualizandoId.put("nomeReceita", receitaModelComNovosValores.getNomeReceita());
                                receitaInicializadaAtualizandoId.put("valorTotalReceita", receitaModelComNovosValores.getValorTotalReceita());
                                receitaInicializadaAtualizandoId.put("valoresIngredientes", receitaModelComNovosValores.getValoresIngredientes());
                                receitaInicializadaAtualizandoId.put("quantRendimentoReceita", receitaModelComNovosValores.getQuantRendimentoReceita());
                                receitaInicializadaAtualizandoId.put("porcentagemServico", receitaModelComNovosValores.getPorcentagemServico());

                                referenceReceitaCadastrada.document(idReceitaCompletaCadastradaparaEditar).update(receitaInicializadaAtualizandoId).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {

                                        Toast.makeText(getApplicationContext(), "Alterações salva com sucesso", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), ProducaoActivity.class);
                                        startActivity(intent);


                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                    }
                                });


                            } catch (Exception e) {
                                System.out.println("Exception e ReceitaCadastradaDAO" + e.getMessage());
                                Toast.makeText(getApplicationContext(), "Favor verifique sua internet e tente novamente", Toast.LENGTH_SHORT).show();

                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });

                } else {
                    Toast.makeText(getApplicationContext(), "Atenção, verifique as informações de rendimento e porcengatem, elas devem ser maior que zero \n" +
                            "e os campos não podem estar vazios. Favor tente novamente", Toast.LENGTH_SHORT).show();
                }
            }
        });


        carregarInformacoesReceitaCompletaCadastrada(idReceitaCompletaCadastradaparaEditar);
        carregarIngredientesAdicionadosReceitaCompletaCadastradaEditar();




    }

    private void carregarInformacoesReceitaCompletaCadastrada(String idReceitaCompletaCadastradaparaEditar) {

        referenceReceitaCompleta.document(idReceitaCompletaCadastradaparaEditar).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                ReceitaModel receitaCompletaCadastradaCompletaCarregamentodeinformacoes = documentSnapshot.toObject(ReceitaModel.class);
                String nomeReceita = receitaCompletaCadastradaCompletaCarregamentodeinformacoes.getNomeReceita();
                String porcentagemServico = receitaCompletaCadastradaCompletaCarregamentodeinformacoes.getPorcentagemServico();
                String quantidadeQueRendeCadaFornada = receitaCompletaCadastradaCompletaCarregamentodeinformacoes.getQuantRendimentoReceita();
                String valorTotalDaReceita = receitaCompletaCadastradaCompletaCarregamentodeinformacoes.getValorTotalReceita();
                String valorTotalIngredientesReceita = receitaCompletaCadastradaCompletaCarregamentodeinformacoes.getValoresIngredientes();

                if(nomeReceita != "" && porcentagemServico != "" &&
                quantidadeQueRendeCadaFornada != "" && valorTotalDaReceita != "" && valorTotalIngredientesReceita !=""){
                    nomeReceitaCompletaCadastradaparaEditar = receitaCompletaCadastradaCompletaCarregamentodeinformacoes.getNomeReceita();
                    texto_valor_total_receita_completa_cadastrada_editar.setVisibility(View.VISIBLE);
                    texto_valor_total_ingredientes_receita_completa_cadastrada_editar.setVisibility(View.VISIBLE);
                    inputPorcentagemPorReceitaCompletaCadastradaEditar.setText(porcentagemServico);
                    inputPorcoesPorFornadaReceitaCompletaCadastradaEditar.setText(quantidadeQueRendeCadaFornada);
                    texto_nome_receita_completa_cadastrada_editar.setText("EDITANDO RECEITA");
                    texto_valor_total_ingredientes_receita_completa_cadastrada_editar.setText(valorTotalIngredientesReceita);

                    texto_valor_total_receita_completa_cadastrada_editar.setText(valorTotalDaReceita);
                    editarNomeReceitaInput.setText(nomeReceitaCompletaCadastradaparaEditar);

                }

                Query queryReceitaCompleta = referenceReceitaCompleta.document(idReceitaCompletaCadastradaparaEditar).collection("INGREDIENTES_ADICIONADOS").orderBy("nomeIngredienteAdicionadoReceita", Query.Direction.ASCENDING);

                FirestoreRecyclerOptions<ModeloIngredienteAdicionadoReceita> options = new FirestoreRecyclerOptions.Builder<ModeloIngredienteAdicionadoReceita>()
                        .setQuery(queryReceitaCompleta, ModeloIngredienteAdicionadoReceita.class)
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

                        ModeloIngredienteAdicionadoReceita modeloIngredienteAdicionadoReceita = documentSnapshot.toObject(ModeloIngredienteAdicionadoReceita.class);
                        textoAntigoValorDoIngredienteNaReceita = modeloIngredienteAdicionadoReceita.getCustoIngredientePorReceita();



                        if(modeloIngredienteAdicionadoReceita != null){


                             idReferenciaDoIngredienteClicado = documentSnapshot.getId();
                             idReferenciaDoItemCadastradoEmEstoque = modeloIngredienteAdicionadoReceita.getIdReferenciaItemEmEstoque();
                             nomeReferenciaDoIngredienteClicado = modeloIngredienteAdicionadoReceita.getNomeIngredienteAdicionadoReceita();
                             quantidadeUtilizadaReceitaReferenciaDoIngredienteClicado = modeloIngredienteAdicionadoReceita.getQuantidadeUtilizadaReceita();
                             textoNovoCalculoValorIngredienteNaRececita = modeloIngredienteAdicionadoReceita.getCustoIngredientePorReceita().replace(",", ".");
                             unidadeMedidaReferenciaDoIngredienteQueEstaEditandoAdicionadoNaReceita = modeloIngredienteAdicionadoReceita.getUnidadeMedidaUsadaReceita();


                            String idReferenciaItemEstoque = modeloIngredienteAdicionadoReceita.getIdReferenciaItemEmEstoque();


                            AlertDialog.Builder alert = new AlertDialog.Builder(EditarReceitasCompletasCadastradas.this);
                            alert.setTitle(modeloIngredienteAdicionadoReceita.getNomeIngredienteAdicionadoReceita());
                            View viewLayout = getLayoutInflater().inflate(R.layout.layout_alertdialog_editar_ingrediente_receita_completa_cadastrada_editar, null);
                            EditText editQuantUsadaReceita = viewLayout.findViewById(R.id.edit_quant_usada_receita_cadastrada_completa_alert_editar_id);
                            TextView valorTotaldoIngredienteUtilizadonaReceitaCadastradaCompletaAlertEditar = viewLayout.findViewById(R.id.texto_valor_total_do_ingrediente_utilizado_na_receita_cadastrada_completa_alert_editar_id);

                            String quantUsadaIngredienteReceitaCadastradaCompletaEditarAlert = modeloIngredienteAdicionadoReceita.getQuantidadeUtilizadaReceita();


                            valorTotaldoIngredienteUtilizadonaReceitaCadastradaCompletaAlertEditar.setText(textoNovoCalculoValorIngredienteNaRececita);
                            editQuantUsadaReceita.setText(quantidadeUtilizadaReceitaReferenciaDoIngredienteClicado);

                            Button botaoCalcularAlert = viewLayout.findViewById(R.id.botao_calcular_nova_quant_usada_receita_completa_cadasrada_alert_editar_id);

                            botaoCalcularAlert.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    if(editQuantUsadaReceita.getText().toString() != "" || editQuantUsadaReceita.getText().toString() != "0" ){

                                        String nomeReferenciaRecebido = nomeReferenciaDoIngredienteClicado;
                                        String valorEditTextAlertDialogTextoValorTotalReceita = editQuantUsadaReceita.getText().toString();

                                        valorIngredienteAlertEditar = "0";

                                        FirebaseFirestore firestoreFirestore = ConfiguracaoFirebase.getFirestor();
                                        CollectionReference collectionReferenceItemEstoque = firestoreFirestore.collection("ITEM_ESTOQUE");
                                        collectionReferenceItemEstoque.whereEqualTo("nomeItemEstoque",nomeReferenciaRecebido )
                                                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                            @Override
                                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {


                                                String idRecuperado = "";
                                                List<DocumentSnapshot> snapshotList = queryDocumentSnapshots.getDocuments();
                                                for (DocumentSnapshot snapshot : snapshotList) {
                                                    idRecuperado =  snapshot.getId();
                                                }

                                                if(idRecuperado != ""){


                                                    FirebaseFirestore.getInstance().collection("ITEM_ESTOQUE").document(idRecuperado)
                                                            .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                        @Override
                                                        public void onSuccess(DocumentSnapshot documentSnapshot) {

                                                            ModeloItemEstoque modeloItemEstoque = documentSnapshot.toObject(ModeloItemEstoque.class);

                                                            String valorItemEstoque = modeloItemEstoque.getValorFracionadoItemEstoque();
                                                            double valorItemFracionado = Double.parseDouble(valorItemEstoque);
                                                            double valorQuantidadeDigitadoConvertido =  Double.parseDouble(valorEditTextAlertDialogTextoValorTotalReceita);
                                                            double resultadodoCalculoEditQuantUsada = valorItemFracionado * valorQuantidadeDigitadoConvertido;
                                                            textoNovoCalculoValorIngredienteNaRececita = String.valueOf(resultadodoCalculoEditQuantUsada);

                                                          valorTotaldoIngredienteUtilizadonaReceitaCadastradaCompletaAlertEditar.setText(textoNovoCalculoValorIngredienteNaRececita);

                                                        }
                                                    });
                                                }
                                            }
                                        });
                                    }
                                }
                            });

                            alert.setPositiveButton("SALVAR INGREDIENTE", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {


                                    ModeloIngredienteAdicionadoReceita ingredienteAdicionadoReceitaAtualizar = new ModeloIngredienteAdicionadoReceita();
                                    ingredienteAdicionadoReceitaAtualizar.setNomeIngredienteAdicionadoReceita(nomeReferenciaDoIngredienteClicado);
                                    ingredienteAdicionadoReceitaAtualizar.setCustoIngredientePorReceita(textoNovoCalculoValorIngredienteNaRececita);
                                    ingredienteAdicionadoReceitaAtualizar.setUnidadeMedidaUsadaReceita(unidadeMedidaReferenciaDoIngredienteQueEstaEditandoAdicionadoNaReceita);
                                    ingredienteAdicionadoReceitaAtualizar.setIdReferenciaItemEmEstoque(idReferenciaItemEstoque);
                                    ingredienteAdicionadoReceitaAtualizar.setQuantidadeUtilizadaReceita(editQuantUsadaReceita.getText().toString());


                                    Map<String, Object> ingredienteAdicionadoNaReceita = new HashMap<>();
                                    ingredienteAdicionadoNaReceita.put("idReferenciaItemEmEstoque", ingredienteAdicionadoReceitaAtualizar.getIdReferenciaItemEmEstoque());
                                    ingredienteAdicionadoNaReceita.put("nomeIngredienteAdicionadoReceita", ingredienteAdicionadoReceitaAtualizar.getNomeIngredienteAdicionadoReceita());
                                    ingredienteAdicionadoNaReceita.put("custoIngredientePorReceita", ingredienteAdicionadoReceitaAtualizar.getCustoIngredientePorReceita());
                                    ingredienteAdicionadoNaReceita.put("quantidadeUtilizadaReceita", ingredienteAdicionadoReceitaAtualizar.getQuantidadeUtilizadaReceita());
                                    ingredienteAdicionadoNaReceita.put("unidadeMedidaUsadaReceita", ingredienteAdicionadoReceitaAtualizar.getUnidadeMedidaUsadaReceita());




                                    referenceReceitaCompleta.document(idReceitaCompletaCadastradaparaEditar).collection("INGREDIENTES_ADICIONADOS")
                                            .document(idReferenciaDoIngredienteClicado).update(ingredienteAdicionadoNaReceita).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {

                                                    Toast.makeText(getApplicationContext(), "Ingrediente " + ingredienteAdicionadoReceitaAtualizar.getNomeIngredienteAdicionadoReceita().toUpperCase() + " atualizado com sucesso", Toast.LENGTH_SHORT).show();

                                                    FirebaseFirestore firestoreReceitaCadastrada = ConfiguracaoFirebase.getFirestor();
                                                    CollectionReference referenceReceitaCadastrada = firestoreReceitaCadastrada.collection("RECEITA_CADASTRADA");

                                                    referenceReceitaCadastrada.document(idReceitaCompletaCadastradaparaEditar).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                        @Override
                                                        public void onSuccess(DocumentSnapshot documentSnapshot) {

                                                            ReceitaModel receitaModelCadastradaRecuperada = documentSnapshot.toObject(ReceitaModel.class);

                                                            String idReceitaCadastrada = receitaModelCadastradaRecuperada.getIdReceita();
                                                            String nomeReceita = receitaModelCadastradaRecuperada.getNomeReceita();
                                                            String porcentagemServico = receitaModelCadastradaRecuperada.getPorcentagemServico();
                                                            String quantidadeQueRendeCadaFornada = receitaModelCadastradaRecuperada.getQuantRendimentoReceita();
                                                            String valorTotalDaReceita = receitaModelCadastradaRecuperada.getValorTotalReceita();
                                                            String valorTotalIngredientesReceita = receitaModelCadastradaRecuperada.getValoresIngredientes().replace(",", ".");


                                                            double porcentagemDaReceitaCadastradaConvertido = Double.parseDouble(porcentagemServico);
                                                            double valorTotalIngredientesConvertido = Double.parseDouble(valorTotalIngredientesReceita);
                                                            double valorAtualizadoDoIngrediente = Double.parseDouble(textoNovoCalculoValorIngredienteNaRececita);
                                                            double valorAntigoDoIngredienteConvertido = Double.parseDouble(textoNovoCalculoValorIngredienteNaRececita);

                                                            double subtracaDosValores = valorTotalIngredientesConvertido - valorAntigoDoIngredienteConvertido;
                                                            double somaDosValores = subtracaDosValores + valorAtualizadoDoIngrediente;
                                                            double resultadoPorcentagemNovo = (somaDosValores * porcentagemDaReceitaCadastradaConvertido) / 100;


                                                            double novoValorTotalDaReceita = resultadoPorcentagemNovo + somaDosValores;


                                                            String novoValorDosIngredientesAdicionados = String.valueOf(somaDosValores);
                                                            String novoValorTotalDaReceitaCadastrada = String.valueOf(novoValorTotalDaReceita);




                                                            ReceitaModel receitaModelComNovosValores = new ReceitaModel();


                                                            receitaModelComNovosValores.setIdReceita(idReceitaCadastrada);
                                                            receitaModelComNovosValores.setNomeReceita(nomeReceita);
                                                            receitaModelComNovosValores.setPorcentagemServico(porcentagemServico);
                                                            receitaModelComNovosValores.setQuantRendimentoReceita(quantidadeQueRendeCadaFornada);
                                                            receitaModelComNovosValores.setValoresIngredientes(novoValorDosIngredientesAdicionados);
                                                            receitaModelComNovosValores.setValorTotalReceita(novoValorTotalDaReceitaCadastrada);

                                                            try{
                                                                Map<String, Object> receitaInicializadaAtualizandoId = new HashMap<>();
                                                                receitaInicializadaAtualizandoId.put("idReceita",receitaModelComNovosValores.getIdReceita());
                                                                receitaInicializadaAtualizandoId.put("nomeReceita",receitaModelComNovosValores.getNomeReceita());
                                                                receitaInicializadaAtualizandoId.put("valorTotalReceita",receitaModelComNovosValores.getValorTotalReceita());
                                                                receitaInicializadaAtualizandoId.put("valoresIngredientes",receitaModelComNovosValores.getValoresIngredientes());
                                                                receitaInicializadaAtualizandoId.put("quantRendimentoReceita",receitaModelComNovosValores.getQuantRendimentoReceita());
                                                                receitaInicializadaAtualizandoId.put("porcentagemServico",receitaModelComNovosValores.getPorcentagemServico());

                                                                referenceReceitaCadastrada.document(idReceitaCompletaCadastradaparaEditar).update(receitaInicializadaAtualizandoId).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                    @Override
                                                                    public void onSuccess(Void unused) {

                                                                        texto_valor_total_ingredientes_receita_completa_cadastrada_editar.setText(novoValorDosIngredientesAdicionados);
                                                                        texto_valor_total_receita_completa_cadastrada_editar.setText(novoValorTotalDaReceitaCadastrada);
                                                                        Toast.makeText(getApplicationContext(), "Receita " + receitaModelComNovosValores.getNomeReceita().toUpperCase() + " teve o valor alterado", Toast.LENGTH_SHORT).show();

                                                                    }
                                                                }).addOnFailureListener(new OnFailureListener() {
                                                                    @Override
                                                                    public void onFailure(@NonNull Exception e) {

                                                                    }
                                                                });

                                                            }catch (Exception e){
                                                                System.out.println("Exception e ReceitaCadastradaDAO" + e.getMessage());
                                                                Toast.makeText(getApplicationContext(), "Favor verifique sua internet e tente novamente", Toast.LENGTH_SHORT).show();

                                                            }
                                                        }
                                                    }).addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {

                                                        }
                                                    });

                                                    Toast.makeText(getApplicationContext(), "Favor verifique sua internet e tente novamente", Toast.LENGTH_SHORT).show();

                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(getApplicationContext(), "Favor verifique sua internet e tente novamente", Toast.LENGTH_SHORT).show();

                                                }
                                            });

                                }
                            }).setNeutralButton("REMOVER DA RECEITA", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    ModeloIngredienteAdicionadoReceita ingredienteAdicionadoReceitaAtualizar = new ModeloIngredienteAdicionadoReceita();
                                    ingredienteAdicionadoReceitaAtualizar.setNomeIngredienteAdicionadoReceita(nomeReferenciaDoIngredienteClicado);
                                    ingredienteAdicionadoReceitaAtualizar.setCustoIngredientePorReceita(textoNovoCalculoValorIngredienteNaRececita);
                                    ingredienteAdicionadoReceitaAtualizar.setUnidadeMedidaUsadaReceita(unidadeMedidaReferenciaDoIngredienteQueEstaEditandoAdicionadoNaReceita);
                                    ingredienteAdicionadoReceitaAtualizar.setIdReferenciaItemEmEstoque(idReferenciaItemEstoque);
                                    ingredienteAdicionadoReceitaAtualizar.setQuantidadeUtilizadaReceita(editQuantUsadaReceita.getText().toString());


                                    referenceReceitaCompleta.document(idReceitaCompletaCadastradaparaEditar).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                        @Override
                                        public void onSuccess(DocumentSnapshot documentSnapshot) {

                                            ReceitaModel receitaModelCadastradaRecuperada = documentSnapshot.toObject(ReceitaModel.class);

                                            String idReceitaCadastrada = receitaModelCadastradaRecuperada.getIdReceita();
                                            String nomeReceita = receitaModelCadastradaRecuperada.getNomeReceita();
                                            String porcentagemServico = receitaModelCadastradaRecuperada.getPorcentagemServico();
                                            String quantidadeQueRendeCadaFornada = receitaModelCadastradaRecuperada.getQuantRendimentoReceita();
                                            String valorTotalDaReceita = receitaModelCadastradaRecuperada.getValorTotalReceita();
                                            String valorTotalIngredientesReceita = receitaModelCadastradaRecuperada.getValoresIngredientes().replace(",", ".");


                                            double porcentagemDaReceitaCadastradaConvertido = Double.parseDouble(porcentagemServico);
                                            double valorTotalIngredientesConvertido = Double.parseDouble(valorTotalIngredientesReceita);
                                            double valorAtualizadoDoIngrediente = Double.parseDouble(textoNovoCalculoValorIngredienteNaRececita);
                                            double valorAntigoDoIngredienteConvertido = Double.parseDouble(textoAntigoValorDoIngredienteNaReceita);

                                            double subtracaDosValores = valorTotalIngredientesConvertido - valorAntigoDoIngredienteConvertido;
                                            double resultadoPorcentagemNovo = (subtracaDosValores * porcentagemDaReceitaCadastradaConvertido) / 100;


                                            double novoValorTotalDaReceita = resultadoPorcentagemNovo + subtracaDosValores;


                                            String novoValorDosIngredientesAdicionados = String.valueOf(subtracaDosValores);
                                            String novoValorTotalDaReceitaCadastrada = String.valueOf(novoValorTotalDaReceita);




                                            ReceitaModel receitaModelComNovosValores = new ReceitaModel();


                                            receitaModelComNovosValores.setIdReceita(idReceitaCadastrada);
                                            receitaModelComNovosValores.setNomeReceita(nomeReceita);
                                            receitaModelComNovosValores.setPorcentagemServico(porcentagemServico);
                                            receitaModelComNovosValores.setQuantRendimentoReceita(quantidadeQueRendeCadaFornada);
                                            receitaModelComNovosValores.setValoresIngredientes(novoValorDosIngredientesAdicionados);
                                            receitaModelComNovosValores.setValorTotalReceita(novoValorTotalDaReceitaCadastrada);

                                            try{
                                                Map<String, Object> receitaInicializadaAtualizandoId = new HashMap<>();
                                                receitaInicializadaAtualizandoId.put("idReceita",receitaModelComNovosValores.getIdReceita());
                                                receitaInicializadaAtualizandoId.put("nomeReceita",receitaModelComNovosValores.getNomeReceita());
                                                receitaInicializadaAtualizandoId.put("valorTotalReceita",receitaModelComNovosValores.getValorTotalReceita());
                                                receitaInicializadaAtualizandoId.put("valoresIngredientes",receitaModelComNovosValores.getValoresIngredientes());
                                                receitaInicializadaAtualizandoId.put("quantRendimentoReceita",receitaModelComNovosValores.getQuantRendimentoReceita());
                                                receitaInicializadaAtualizandoId.put("porcentagemServico",receitaModelComNovosValores.getPorcentagemServico());

                                                referenceReceitaCompleta.document(idReceitaCompletaCadastradaparaEditar).update(receitaInicializadaAtualizandoId).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {

                                                        referenceReceitaCompleta.document(idReceitaCompletaCadastradaparaEditar).collection("INGREDIENTES_ADICIONADOS")
                                                                .document(idReferenciaDoIngredienteClicado).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void unused) {

                                                                texto_valor_total_ingredientes_receita_completa_cadastrada_editar.setText(novoValorDosIngredientesAdicionados);
                                                                texto_valor_total_receita_completa_cadastrada_editar.setText(novoValorTotalDaReceitaCadastrada);
                                                                Toast.makeText(getApplicationContext(), "Receita " + receitaModelComNovosValores.getNomeReceita().toUpperCase() + " teve o valor alterado", Toast.LENGTH_SHORT).show();


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
                                                System.out.println("Exception e ReceitaCadastradaDAO" + e.getMessage());
                                                Toast.makeText(getApplicationContext(), "Favor verifique sua internet e tente novamente", Toast.LENGTH_SHORT).show();

                                            }
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {

                                        }
                                    });



                                }
                            }).setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(getApplicationContext(), "Alterações descartadas", Toast.LENGTH_SHORT).show();
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







    private void carregarIngredientesAdicionadosReceitaCompletaCadastradaEditar() {



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

        Query query = FirebaseFirestore.getInstance().collection("ITEM_ESTOQUE").orderBy("nomeItemEstoque", Query.Direction.ASCENDING);



        FirestoreRecyclerOptions<ModeloItemEstoque> options = new FirestoreRecyclerOptions.Builder<ModeloItemEstoque>()
                .setQuery(query, ModeloItemEstoque.class)
                .build();

        adapterItemEstoque = new ModeloItemEstoqueAdapter(options);

        listaIngredientesEstoqueEditar.setHasFixedSize(true);
        listaIngredientesEstoqueEditar.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        listaIngredientesEstoqueEditar.setAdapter(adapterItemEstoque);
        listaIngredientesEstoqueEditar.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));

        adapterItemEstoque.setOnItemClickListerner(new ModeloItemEstoqueAdapter.OnItemClickLisener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {



                ModeloItemEstoque itemEstoqueModel = documentSnapshot.toObject(ModeloItemEstoque.class);
                assert itemEstoqueModel != null;

                String nomeItemAdd = itemEstoqueModel.getNomeItemEstoque();
                String valorItemAdd = itemEstoqueModel.getCustoPorReceitaItemEstoque();
                String quantItemAdd = itemEstoqueModel.getQuantidadeUtilizadaNasReceitas();
                String unidadeMedidaUtilizadaReceita = itemEstoqueModel.getUnidadeMedidaUtilizadoNasReceitas();


                ModeloIngredienteAdicionadoReceita modeloIngredienteAdicionadoReceita = new ModeloIngredienteAdicionadoReceita();
                modeloIngredienteAdicionadoReceita.setIdReferenciaItemEmEstoque(documentSnapshot.getId());
                modeloIngredienteAdicionadoReceita.setNomeIngredienteAdicionadoReceita(nomeItemAdd);
                modeloIngredienteAdicionadoReceita.setQuantidadeUtilizadaReceita(quantItemAdd);
                modeloIngredienteAdicionadoReceita.setCustoIngredientePorReceita(valorItemAdd);
                modeloIngredienteAdicionadoReceita.setUnidadeMedidaUsadaReceita(unidadeMedidaUtilizadaReceita);





                referenceReceitaCompleta.document(idReceitaCompletaCadastradaparaEditar).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        ReceitaModel receitaModelCadastradaRecuperada = documentSnapshot.toObject(ReceitaModel.class);
                        String idReceitaRecuperada = receitaModelCadastradaRecuperada.getIdReceita();
                        String nomeReceitaRecuperada = receitaModelCadastradaRecuperada.getNomeReceita();
                        String porcentagemReceitaRecuperada = receitaModelCadastradaRecuperada.getPorcentagemServico();
                        String valorTotalIngredientesReceitaRecuperada = receitaModelCadastradaRecuperada.getValoresIngredientes().replace(",", ".");
                        String valorTotalReceitaRecuperada = receitaModelCadastradaRecuperada.getValorTotalReceita().replace(",", ".");
                        String quantoRendeCadaFornadaReceitaRecuperada = receitaModelCadastradaRecuperada.getQuantRendimentoReceita();

                        CollectionReference referenciaIngredientesAdicionados = referenceReceitaCompleta.document(idReceitaRecuperada)
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




                                referenceReceitaCompleta.document(idReceitaRecuperada).update(receitaComNovosValores).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {

                                        texto_valor_total_ingredientes_receita_completa_cadastrada_editar.setText(receitaModelAtualizadaComOsNovosValores.getValoresIngredientes());
                                        texto_valor_total_receita_completa_cadastrada_editar.setText(receitaModelAtualizadaComOsNovosValores.getValorTotalReceita());



                                        Toast.makeText(getApplicationContext(), "Receita teve os valores atualizados", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                    }
                                });



                                Toast.makeText(getApplicationContext(), "Ingrediente " + modeloIngredienteAdicionadoReceita.getNomeIngredienteAdicionadoReceita().toUpperCase() + " adicionado com sucesso!", Toast.LENGTH_SHORT).show();

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

                ModeloReceitaCadastradaDAO receitaCadastradaDAO = new ModeloReceitaCadastradaDAO(getApplicationContext());
                receitaCadastradaDAO.adicionarIngredienteNaReceitaJaCadastrada(idReceitaCompletaCadastradaparaEditar, modeloIngredienteAdicionadoReceita);
*//*

              //  carregarIngredientesAdicionadosReceitaCompletaCadastradaEditar();

                for(int i = 0; i <=1000; i++){
                    System.out.println("Passou pelo for");
                    recarregarInformacoesDaReceitaCadastrada();
                }*/


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


    private void recarregarInformacoesDaReceitaCadastrada(){

        System.out.println("Passou pelo recarregar informações----------------------------------------");
        referenceReceitaCompleta.document(idReceitaCompletaCadastradaparaEditar).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        ReceitaModel receitaCompletaCadastradaCompletaCarregamentodeinformacoes = documentSnapshot.toObject(ReceitaModel.class);
                        String nomeReceita = receitaCompletaCadastradaCompletaCarregamentodeinformacoes.getNomeReceita();
                        String porcentagemServico = receitaCompletaCadastradaCompletaCarregamentodeinformacoes.getPorcentagemServico();
                        String quantidadeQueRendeCadaFornada = receitaCompletaCadastradaCompletaCarregamentodeinformacoes.getQuantRendimentoReceita();
                        String valorTotalDaReceita = receitaCompletaCadastradaCompletaCarregamentodeinformacoes.getValorTotalReceita();
                        String valorTotalIngredientesReceita = receitaCompletaCadastradaCompletaCarregamentodeinformacoes.getValoresIngredientes();

                        texto_valor_total_ingredientes_receita_completa_cadastrada_editar.setText(valorTotalIngredientesReceita);
                        texto_valor_total_receita_completa_cadastrada_editar.setText(valorTotalDaReceita);
                        System.out.println("Valor total receita recuperado: " + valorTotalDaReceita);
                        System.out.println("Valor total ingredientes receita recuperado: " + valorTotalIngredientesReceita);

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });



    }



    private  void adicionarIngredienteReceitaCompleta(String nomeReceita, String nomeItemAdicionado, String valorItemAdicionado, String quantItemAdicionado){


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