package com.marcel.a.n.roxha.deliciasdamamae.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;
import com.marcel.a.n.roxha.deliciasdamamae.R;
import com.marcel.a.n.roxha.deliciasdamamae.adapter.ItemEstoqueAdapter;
import com.marcel.a.n.roxha.deliciasdamamae.adapter.ModeloItemEstoqueAdapter;
import com.marcel.a.n.roxha.deliciasdamamae.config.ConfiguracaoFirebase;
import com.marcel.a.n.roxha.deliciasdamamae.controller.helper.ItemEstoqueDAO;
import com.marcel.a.n.roxha.deliciasdamamae.model.ItemEstoqueModel;
import com.marcel.a.n.roxha.deliciasdamamae.model.ModeloItemEstoque;

import java.util.List;

public class EstoqueActivity extends AppCompatActivity {

    /*Componentes de tela*/
    private Button botao_adiciona_item_estoque;
    private RecyclerView recyclerView;
    private ItemEstoqueAdapter adapter;
    private TextView valor_total_de_itens_cadastrados_em_estoque;

    private ModeloItemEstoqueAdapter modeloItemEstoqueAdapter;


    private FirebaseFirestore firestore = ConfiguracaoFirebase.getFirestor();
    private CollectionReference itemEstoqueRef = firestore.collection("ITEM_ESTOQUE");
    private String chaveSeg = "DeliciasDaMamae";

    private ItemEstoqueModel itemSelecionado;
    private int contador = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setTheme(R.style.Theme_DeliciasDaMamae);

        setContentView(R.layout.activity_estoque);



        botao_adiciona_item_estoque = findViewById(R.id.bt_add_item_estoque_id);



        botao_adiciona_item_estoque.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(EstoqueActivity.this, AddItemEstoqueActivity.class);
                startActivity(intent);

            }
        });


        recyclerView = findViewById(R.id.recycler_lista_itens_receita_estoque_id);



    }

    public void carregarListaItensEstoque(){

        carregarQuantidadeTotalDeItensCadastradosEmEstoque();

        Query query = itemEstoqueRef.orderBy("nomeItemEstoque", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<ModeloItemEstoque> options = new FirestoreRecyclerOptions.Builder<ModeloItemEstoque>()
                .setQuery(query, ModeloItemEstoque.class)
                .build();

      //  adapter = new ItemEstoqueAdapter(options);


        modeloItemEstoqueAdapter = new ModeloItemEstoqueAdapter(options);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(modeloItemEstoqueAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));



        modeloItemEstoqueAdapter.setOnItemClickListerner(new ModeloItemEstoqueAdapter.OnItemClickLisener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {

                Intent intent = new Intent( EstoqueActivity.this, AddItemEstoqueActivity.class);
                intent.putExtra("itemKey", documentSnapshot.getId());
                startActivity(intent);


            }
        });

        modeloItemEstoqueAdapter.setOnItemLongClickListerner(new ModeloItemEstoqueAdapter.OnLongClickListener() {
    @Override
    public void onItemClick(DocumentSnapshot documentSnapshot, int position) {

        AlertDialog.Builder alert = new AlertDialog.Builder(EstoqueActivity.this);
        alert.setTitle("ATENÇÃO");
        alert.setMessage("Deseja realmente excluir esse item do estoque?");
        alert.setIcon(R.drawable.ic_alertdialogatencao_24);
        alert.setCancelable(false);

        alert.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                modeloItemEstoqueAdapter.deletarItemIndividual(position);
                carregarQuantidadeTotalDeItensCadastradosEmEstoque();
                Toast.makeText(EstoqueActivity.this, "Item excluido permanentemente", Toast.LENGTH_SHORT).show();


            }
        }).setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                recyclerView.refreshDrawableState();
                Toast.makeText(EstoqueActivity.this, "Ação cancelada", Toast.LENGTH_SHORT).show();

            }
        });
        alert.create();
        alert.show();



    }
});


    /*    if(contador == 0){
            valor_total_de_itens_cadastrados_em_estoque.setText("Algo deu errado \n tente novamente");
        }else{

        }*/




    }


    public void carregarQuantidadeTotalDeItensCadastradosEmEstoque(){
        valor_total_de_itens_cadastrados_em_estoque = findViewById(R.id.texto_total_itens_cadastrados_em_estoque_id);

        FirebaseFirestore.getInstance().collection("ITEM_ESTOQUE")
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                contador = 0;
                List<DocumentSnapshot> listaDeItensCadastradosNoBanco = queryDocumentSnapshots.getDocuments();

                for(DocumentSnapshot listaRecebida: listaDeItensCadastradosNoBanco){
                    contador++;
                }
                String valorConvert = String.valueOf(contador);

                valor_total_de_itens_cadastrados_em_estoque.setText(valorConvert);


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
        carregarListaItensEstoque();
        carregarQuantidadeTotalDeItensCadastradosEmEstoque();
        modeloItemEstoqueAdapter.startListening();

    }

    @Override
    protected void onStop() {
        super.onStop();
        modeloItemEstoqueAdapter.stopListening();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

       getMenuInflater().inflate(R.menu.menu_restaurar_lista_padrao_itens_estoque, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.botao_restaurar_itensPadrao_estoque:

                carregarAlertachaveReiniciarListaItensEstoque();

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void carregarAlertachaveReiniciarListaItensEstoque(){

        AlertDialog.Builder dialog = new AlertDialog.Builder(EstoqueActivity.this);

        dialog.setTitle("ATENÇÃO");
        dialog.setMessage("Você irá apagar todos os dados dos Itens em estoque, insira a chave de segurança para executar o reset");
        dialog.setIcon(R.drawable.ic_alertdialogatencao_24);
        dialog.setCancelable(false);
        EditText editText = new EditText(this);
        dialog.setView(editText);

        dialog.setPositiveButton("LISTA PADRRÃO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                String chaveSeguranca = editText.getText().toString();

                if(!chaveSeguranca.isEmpty() && chaveSeguranca.contains(chaveSeg)){
                    ItemEstoqueDAO itemEstoqueDAO = new ItemEstoqueDAO(EstoqueActivity.this);
                    itemEstoqueDAO.listaDefault();
                    Toast.makeText(EstoqueActivity.this, "Lista iniciada com valores padrão", Toast.LENGTH_SHORT).show();
                    carregarQuantidadeTotalDeItensCadastradosEmEstoque();
                }else {
                    Toast.makeText(EstoqueActivity.this, "Chave de segurança incorreta", Toast.LENGTH_SHORT).show();
                }

            }
        }).setNegativeButton("LISTA VAZIA", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                String chaveSeguranca = editText.getText().toString();

                if (!chaveSeguranca.isEmpty() && chaveSeguranca.contains(chaveSeg)) {

                    FirebaseFirestore.getInstance().collection("ITEM_ESTOQUE")
                            .whereEqualTo("versionEstoque", "Estoque_DeliciasDaMamae")
                            .get()
                            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {


                                    WriteBatch batch = FirebaseFirestore.getInstance().batch();

                                    List<DocumentSnapshot> snapshotList = queryDocumentSnapshots.getDocuments();
                                    for (DocumentSnapshot snapshot : snapshotList) {

                                        batch.delete(snapshot.getReference());


                                    }
                                    batch.commit()
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Toast.makeText(EstoqueActivity.this, "Lista iniciada vazia", Toast.LENGTH_SHORT).show();
                                                    carregarQuantidadeTotalDeItensCadastradosEmEstoque();
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(EstoqueActivity.this, "Erro", Toast.LENGTH_SHORT).show();

                                        }
                                    });

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast.makeText(EstoqueActivity.this, "Erro", Toast.LENGTH_SHORT).show();

                        }
                    });

                }else {
                    Toast.makeText(EstoqueActivity.this, "Chave de segurança incorreta", Toast.LENGTH_SHORT).show();


                }
            }
        });

        dialog.create();
        dialog.show();


    }
}