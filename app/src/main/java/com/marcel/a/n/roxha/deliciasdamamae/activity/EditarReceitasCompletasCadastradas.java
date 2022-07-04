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

import com.bumptech.glide.Glide;
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
import com.marcel.a.n.roxha.deliciasdamamae.model.BolosModel;
import com.marcel.a.n.roxha.deliciasdamamae.model.CalcularValorIngredienteAdicionadoReceitaCompletaCadastrada;
import com.marcel.a.n.roxha.deliciasdamamae.model.ItemEstoqueModel;
import com.marcel.a.n.roxha.deliciasdamamae.model.ReceitaModel;

public class EditarReceitasCompletasCadastradas extends AppCompatActivity {


    private String idReceitaCompletaCadastradaparaEditar = "";
    private String nomeReceitaCompletaCadastradaparaEditar;
    private String porceosReceita = "";
    private String porcentReceita = "";
    private boolean statusCarregamentoValorIngredientes;
    private double valorRecuperado = 0;

    private Button botaoSalvarAlteracoesReceitaCadastrada ;

    private TextInputEditText inputPorcoesPorFornadaReceitaCompletaCadastradaEditar ;
    private TextView texto_nome_receita_completa_cadastrada_editar ;
    private TextInputEditText inputPorcentagemPorReceitaCompletaCadastradaEditar ;

    private RecyclerView listaIngredientesAdicionadosReceitaCompletaEditar;
    private RecyclerView listaIngredientesEstoqueEditar;

    private FirebaseFirestore db = ConfiguracaoFirebase.getFirestor();
    private CollectionReference referenceReceitaCompleta = db.collection("Receitas_completas");

    private IngredienteAdicionadoAdapter adicionadoAdapter;





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

        listaIngredientesAdicionadosReceitaCompletaEditar = findViewById(R.id.recyclerView_ingredientes_adicionados_receita_completa_editar_id);

        botaoSalvarAlteracoesReceitaCadastrada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(), "Testando botão e inputs:  \n\n" +
                        "inputPorcoesPorFornadaReceitaCompletaCadastrada: " + inputPorcoesPorFornadaReceitaCompletaCadastradaEditar.getText().toString() +"\n\n" +
                        "inputPorcentagemPorReceitaCompletaCadastradaEditar: " + inputPorcentagemPorReceitaCompletaCadastradaEditar.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });
        idReceitaCompletaCadastradaparaEditar = getIntent().getStringExtra("idReceita");
        carregarIngredientesAdicionadosReceitaCompletaCadastradaEditar();

    }

    private void carregarIngredientesAdicionadosReceitaCompletaCadastradaEditar() {
        CalcularValorIngredienteAdicionadoReceitaCompletaCadastrada calcularValorIngredienteAdicionadoReceitaCompletaCadastrada = new CalcularValorIngredienteAdicionadoReceitaCompletaCadastrada();

        referenceReceitaCompleta.document(idReceitaCompletaCadastradaparaEditar).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                ReceitaModel receitaCompletaCadastradaCompleta = documentSnapshot.toObject(ReceitaModel.class);
                nomeReceitaCompletaCadastradaparaEditar = receitaCompletaCadastradaCompleta.getNomeReceita();

                inputPorcentagemPorReceitaCompletaCadastradaEditar.setText(receitaCompletaCadastradaCompleta.getPorcentagemServico());
                inputPorcoesPorFornadaReceitaCompletaCadastradaEditar.setText(receitaCompletaCadastradaCompleta.getQuantRendimentoReceita());
                texto_nome_receita_completa_cadastrada_editar.setText(nomeReceitaCompletaCadastradaparaEditar);

                //carregarIngredientesAdicionadosReceitaCompletaCadastradaEditar();

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

              calcularValorIngredienteAdicionadoReceitaCompletaCadastrada.calcularValoresIngredientesAdicionados(idReceitaCompletaCadastradaparaEditar, nomeReceitaCompletaCadastradaparaEditar);

                Toast.makeText(getApplicationContext(), "Teste valor total: " + calcularValorIngredienteAdicionadoReceitaCompletaCadastrada.getValorTotalCalculadoIngredientesReceitaCadastradaCompleta(), Toast.LENGTH_SHORT).show();






            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();
       adicionadoAdapter.stopListening();
    }
}