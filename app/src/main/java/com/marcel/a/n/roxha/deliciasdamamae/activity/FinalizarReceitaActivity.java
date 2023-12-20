package com.marcel.a.n.roxha.deliciasdamamae.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.marcel.a.n.roxha.deliciasdamamae.R;
import com.marcel.a.n.roxha.deliciasdamamae.adapter.IngredienteAdicionadoAdapter;
import com.marcel.a.n.roxha.deliciasdamamae.config.ConfiguracaoFirebase;
import com.marcel.a.n.roxha.deliciasdamamae.controller.helper.ModeloReceitaCadastradaDAO;
import com.marcel.a.n.roxha.deliciasdamamae.model.ModeloIngredienteAdicionadoReceita;
import com.marcel.a.n.roxha.deliciasdamamae.model.ReceitaModel;

import java.util.ArrayList;
import java.util.List;

public class FinalizarReceitaActivity extends AppCompatActivity {

    private TextInputEditText edit_porcentagem_acrescentada;
    private TextInputEditText edit_porcoes_receita;

    private RecyclerView recyclerViewLista_ingrediente_adicionados;
    private TextView textNomeReceita, valoresIngredientes, valorTotalReceita;
    private String valorItemReceita;

    List<Double> listaIngredientes = new ArrayList<>();

    private FirebaseFirestore db = ConfiguracaoFirebase.getFirestor();
    private final String NOME_COLLECTION_REFERENCIA_RECEITAS_CADASTRADAS = "RECEITA_CADASTRADA";
    private CollectionReference referenceReceitaCompleta = db.collection(NOME_COLLECTION_REFERENCIA_RECEITAS_CADASTRADAS);
    private CollectionReference referenceMassa = db.collection("Massas");
    private CollectionReference referenceCobertura = db.collection("Coberturas");

    private IngredienteAdicionadoAdapter adicionadoAdapter;

    private String idReceita;
    private String nameKeyReceita;
    private String idIngredientes;


    private String porceosReceita;
    private String porcentReceita;
    private double valorAdicionado = 0;
    private double resultado = 0;
    private double valor;
    private int count = 0;
    private String contTipoProducao;
    private String valorIngredientes;
    private double valorIngredientesConvert;
    private double valorIngredientesTotal;
    private double porcentConvert = 0;
    private Button botao_calcular_receita, botao_finalizar_receita;
    private String textoTotalReceita;

    private String valorTotalIngredientesRecuperadoDaReceitaFinalizandoString;
    private String valorTotalDaReceitaFinalizandoString;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setTheme(R.style.Theme_DeliciasDaMamae);
        setContentView(R.layout.activity_finalizar_receita);

        nameKeyReceita = getIntent().getStringExtra("nameReceita");
        idReceita = getIntent().getStringExtra("idReceita");

        edit_porcentagem_acrescentada = findViewById(R.id.edit_porcentagem_acrescentada_id);
        edit_porcoes_receita = findViewById(R.id.edit_quant_porcoes_receita_id);

        recyclerViewLista_ingrediente_adicionados = findViewById(R.id.recycler_lista_ingredientes_adicionados_receita_id);

        textNomeReceita = findViewById(R.id.text_nome_receita_finalizada_id);

        textNomeReceita.setText(nameKeyReceita);

        valoresIngredientes = findViewById(R.id.textView_Valores_Ingredientes_id);
        valorTotalReceita = findViewById(R.id.textView_valor_total_receita_id);

        valoresIngredientes.setVisibility(View.GONE);
        valorTotalReceita.setVisibility(View.GONE);

        botao_calcular_receita = findViewById(R.id.bt_calcular_receita_id);
        botao_finalizar_receita = findViewById(R.id.bt_finalizar_receita_id);

       // carregarListaItensAdicionados();
        recuperarvaloresDaReceitaCadastrando(idReceita);
        botao_finalizar_receita.setVisibility(View.GONE);
        botao_calcular_receita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                porcentReceita = edit_porcentagem_acrescentada.getText().toString();
                porceosReceita = edit_porcoes_receita.getText().toString();

                if(!porcentReceita.isEmpty() && !porceosReceita.isEmpty()){

                    if(count < 1 ) {
                        setValor();
                        double resultado = 0;
                        valorIngredientesConvert = Double.parseDouble(valorTotalIngredientesRecuperadoDaReceitaFinalizandoString);
                        porcentConvert = Double.parseDouble(porcentReceita);

                        resultado = (valorIngredientesConvert * porcentConvert / 100 ) + valorIngredientesConvert ;

                        textoTotalReceita = String.format("%.2f", resultado);

                        valoresIngredientes.setVisibility(View.VISIBLE);
                        String textoTotalIngredientes = String.format("%.2f", valorIngredientesConvert);
                        valoresIngredientes.setText("Valor total dos ingredientes: " + textoTotalIngredientes);
                        valorTotalReceita.setVisibility(View.VISIBLE);
                        valorTotalReceita.setText("Valor total da Receita " + nameKeyReceita.toLowerCase() + ": " + textoTotalReceita);

                        count++;
                        botao_calcular_receita.setVisibility(View.GONE);
                        botao_finalizar_receita.setVisibility(View.VISIBLE);
                    }

                }else {
                    Toast.makeText(FinalizarReceitaActivity.this, "Favor insira as informações, como Porções e Porcentagem da receita", Toast.LENGTH_SHORT).show();
                    count = 0;

                    valoresIngredientes.setVisibility(View.GONE);
                    valorTotalReceita.setVisibility(View.GONE);

                    botao_finalizar_receita.setVisibility(View.GONE);
                    botao_calcular_receita.setVisibility(View.VISIBLE);

                }


            }
        });

        botao_finalizar_receita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                porcentReceita = edit_porcentagem_acrescentada.getText().toString();
                porceosReceita = edit_porcoes_receita.getText().toString();

                if(!porcentReceita.isEmpty() && !porceosReceita.isEmpty()){

                    ReceitaModel receitaModelFinalizadaAtualizarNoSistema = new ReceitaModel();
                    ModeloReceitaCadastradaDAO modeloReceitaCadastradaDAOFinalizandoReceita = new ModeloReceitaCadastradaDAO(FinalizarReceitaActivity.this);
                    receitaModelFinalizadaAtualizarNoSistema.setIdReceita(idReceita);
                    receitaModelFinalizadaAtualizarNoSistema.setNomeReceita(nameKeyReceita);
                    receitaModelFinalizadaAtualizarNoSistema.setQuantRendimentoReceita(porceosReceita);
                    receitaModelFinalizadaAtualizarNoSistema.setPorcentagemServico(porcentReceita);
                    receitaModelFinalizadaAtualizarNoSistema.setValorTotalReceita(textoTotalReceita);
                    receitaModelFinalizadaAtualizarNoSistema.setValoresIngredientes(valorTotalIngredientesRecuperadoDaReceitaFinalizandoString);

                    modeloReceitaCadastradaDAOFinalizandoReceita.finalizarCadastroDaReceitaCadastrando(receitaModelFinalizadaAtualizarNoSistema);


                }else{
                    Toast.makeText(FinalizarReceitaActivity.this, "Favor insira as informações, como Porções e Porcentagem da receita", Toast.LENGTH_SHORT).show();
                    count = 0;

                    valoresIngredientes.setVisibility(View.GONE);
                    valorTotalReceita.setVisibility(View.GONE);

                    botao_finalizar_receita.setVisibility(View.GONE);
                    botao_calcular_receita.setVisibility(View.VISIBLE);

                }

            }
        });

    }

    private void recuperarvaloresDaReceitaCadastrando(String idReceita) {


        referenceReceitaCompleta.document(idReceita).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                ReceitaModel receitaModelTempRecuperada = new ReceitaModel();

                ReceitaModel receitaModelFinalizandoCadastro = documentSnapshot.toObject(ReceitaModel.class);

                valorTotalIngredientesRecuperadoDaReceitaFinalizandoString = receitaModelFinalizandoCadastro.getValoresIngredientes();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });


    }

    public void carregarListaIngredientesAdicionados(){


        Query queryReceitaCompleta = referenceReceitaCompleta.document(idReceita)
                .collection("INGREDIENTES_ADICIONADOS")
                .orderBy("nomeIngredienteAdicionadoReceita", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<ModeloIngredienteAdicionadoReceita> options = new FirestoreRecyclerOptions.Builder<ModeloIngredienteAdicionadoReceita>()
                .setQuery(queryReceitaCompleta, ModeloIngredienteAdicionadoReceita.class)
                .build();


        adicionadoAdapter = new IngredienteAdicionadoAdapter(options);


        recyclerViewLista_ingrediente_adicionados.setHasFixedSize(true);
        recyclerViewLista_ingrediente_adicionados.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerViewLista_ingrediente_adicionados.setAdapter(adicionadoAdapter);
        recyclerViewLista_ingrediente_adicionados.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
    }

  /*  public void carregarListaItensAdicionados(){


        int tipoProducao = Integer.parseInt(contTipoProducao);

        if (tipoProducao == 1){

            CollectionReference ref = referenceReceitaCompleta.document(idReceita).collection(nameKeyReceita);

            ref.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                    List<DocumentSnapshot> listIngredientes = queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot list: listIngredientes){

                        idIngredientes = list.getId();

                        salvarValoresIngredientes(idIngredientes);
                    }


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        }else if (tipoProducao == 2){

            CollectionReference ref = referenceMassa.document(idReceita).collection(nameKeyReceita);

            ref.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                    List<DocumentSnapshot> listIngredientes = queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot list: listIngredientes){

                        idIngredientes = list.getId();

                        salvarValoresIngredientes(idIngredientes);
                    }


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });

        }else if (tipoProducao == 3){

            CollectionReference ref = referenceCobertura.document(idReceita).collection(nameKeyReceita);

            ref.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                    List<DocumentSnapshot> listIngredientes = queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot list: listIngredientes){

                        idIngredientes = list.getId();

                        salvarValoresIngredientes(idIngredientes);
                    }


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        }



    }



    public void atualizarReceita(){


        int tipoProducao = Integer.parseInt(contTipoProducao);
        //atualizar caso seja, massa, cobertura ou receita completa...


        if(tipoProducao == 1){


            DocumentReference reference = referenceReceitaCompleta.document(idReceita);

            Map<String, Object> receitaAtualizada = new HashMap<>();

            receitaAtualizada.put("idReceita", idReceita);
            receitaAtualizada.put("nomeReceita", nameKeyReceita);
            receitaAtualizada.put("quantRendimentoReceita",porceosReceita );
            receitaAtualizada.put("valorTotalReceita", textoTotalReceita);

            reference.update(receitaAtualizada).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {

                    Toast.makeText(FinalizarReceitaActivity.this, "Receita atual tem os seguintes dados: \n " +
                            "nome " + nameKeyReceita + "\n"  +
                            "valor total: " + textoTotalReceita + "\n"  +
                            " Quantidade na loja: " + porceosReceita + " Bolos..", Toast.LENGTH_LONG).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });

        }else if (tipoProducao == 2){

            DocumentReference reference = referenceMassa.document(idReceita);

            Map<String, Object> receitaAtualizada = new HashMap<>();

            receitaAtualizada.put("idReceita", idReceita);
            receitaAtualizada.put("nomeReceita", nameKeyReceita);
            receitaAtualizada.put("quantRendimentoReceita",porceosReceita );
            receitaAtualizada.put("valorTotalReceita", textoTotalReceita);

            reference.update(receitaAtualizada).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {

                    Toast.makeText(FinalizarReceitaActivity.this, "Receita atual tem os seguintes dados: \n " +
                            "nome " + nameKeyReceita + "\n"  +
                            "valor total: " + textoTotalReceita + "\n"  +
                            " Quantidade na loja: " + porceosReceita + " Bolos..", Toast.LENGTH_LONG).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });

        }else if (tipoProducao == 3 ){

            DocumentReference reference = referenceCobertura.document(idReceita);

            Map<String, Object> receitaAtualizada = new HashMap<>();

            receitaAtualizada.put("idReceita", idReceita);
            receitaAtualizada.put("nomeReceita", nameKeyReceita);
            receitaAtualizada.put("quantRendimentoReceita",porceosReceita );
            receitaAtualizada.put("valorTotalReceita", textoTotalReceita);

            reference.update(receitaAtualizada).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {

                    Toast.makeText(FinalizarReceitaActivity.this, "Receita atual tem os seguintes dados: \n " +
                            "nome " + nameKeyReceita + "\n"  +
                            "valor total: " + textoTotalReceita + "\n"  +
                            " Quantidade na loja: " + porceosReceita + " Bolos..", Toast.LENGTH_LONG).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        }






    }

    public void salvarValoresIngredientes(String identificadorIngrediente){


        int tipoProducao = Integer.parseInt(contTipoProducao);

        if (tipoProducao == 1){

            FirebaseFirestore.getInstance().collection("Receitas_completas").document(idReceita).collection(nameKeyReceita).document(identificadorIngrediente).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {

                    ModeloItemEstoque ModeloItemEstoque = documentSnapshot.toObject(ModeloItemEstoque.class);

                    assert ModeloItemEstoque != null;

                    valorItemReceita = ModeloItemEstoque.getValorItemPorReceita();

                    valor = Double.parseDouble(valorItemReceita);

                    listaIngredientes.add(valor);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });



        }else if (tipoProducao == 2){

            FirebaseFirestore.getInstance().collection("Massas").document(idReceita).collection(nameKeyReceita).document(identificadorIngrediente).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {

                    ModeloItemEstoque ModeloItemEstoque = documentSnapshot.toObject(ModeloItemEstoque.class);

                    assert ModeloItemEstoque != null;

                    valorItemReceita = ModeloItemEstoque.getValorItemPorReceita();

                    valor = Double.parseDouble(valorItemReceita);

                    listaIngredientes.add(valor);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });




        }else if (tipoProducao == 3) {


            FirebaseFirestore.getInstance().collection("Coberturas").document(idReceita).collection(nameKeyReceita).document(identificadorIngrediente).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {

                    ModeloItemEstoque ModeloItemEstoque = documentSnapshot.toObject(ModeloItemEstoque.class);

                    assert ModeloItemEstoque != null;

                    valorItemReceita = ModeloItemEstoque.getValorItemPorReceita();

                    valor = Double.parseDouble(valorItemReceita);

                    listaIngredientes.add(valor);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });


        }




    }
*/
public String setValor(){

for (Double list : listaIngredientes){
    resultado +=  valorAdicionado + list;
}
String valor = String.valueOf(resultado);

this.valorIngredientes = valor;
  return valor;
}

    @Override
    protected void onStart() {
        super.onStart();
        carregarListaIngredientesAdicionados();
        adicionadoAdapter.startListening();


    }

    @Override
    protected void onStop() {
        super.onStop();
        adicionadoAdapter.stopListening();
    }
}