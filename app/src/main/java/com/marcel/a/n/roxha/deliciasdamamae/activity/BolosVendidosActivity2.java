package com.marcel.a.n.roxha.deliciasdamamae.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.marcel.a.n.roxha.deliciasdamamae.R;
import com.marcel.a.n.roxha.deliciasdamamae.adapter.BolosVendidosAdapter;
import com.marcel.a.n.roxha.deliciasdamamae.adapter.ModeloProdutoVendidoAdapter;
import com.marcel.a.n.roxha.deliciasdamamae.config.ConfiguracaoFirebase;
import com.marcel.a.n.roxha.deliciasdamamae.model.BoloVendidoModel;
import com.marcel.a.n.roxha.deliciasdamamae.model.ModeloProdutoVendido;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BolosVendidosActivity2 extends AppCompatActivity {


    //Componentes de tela..
    RecyclerView recyclerView_lista_bolos_vendidos;

    //Banco de dados
    FirebaseFirestore firebaseFirestore = ConfiguracaoFirebase.getFirestor();
    CollectionReference reference;
    private static final String COLLECTION_CAIXA_DIARIO = "CAIXAS_DIARIO_";


    //Classes

    BolosVendidosAdapter bolosVendidosAdapter;
    ModeloProdutoVendidoAdapter modeloProdutoVendidoAdapter;



    //Variáveis
    private Date hoje;
    private String idMontanteAtual;
    private SimpleDateFormat simpleDateFormatCollectionDataCaixaDiario = new SimpleDateFormat("dd/MM/yyyy");
    private SimpleDateFormat simpleDateFormatCollectionReferenciaAtual = new SimpleDateFormat("MM");
    private SimpleDateFormat simpleDateFormatCollectionReferenciaAnoAtual = new SimpleDateFormat("yyyy");

    private String nomeCollectionNomeCaixaDiario = "";
    private String mesAtual = "";
    private String anoAtual = "";
    private String dataCompleta = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setTheme(R.style.Theme_DeliciasDaMamae);
        setContentView(R.layout.activity_bolos_vendidos2);

        recyclerView_lista_bolos_vendidos = findViewById(R.id.recyclerview_lista_bolos_vendidos_id);
        hoje = new Date();
        idMontanteAtual = getIntent().getStringExtra("idMontanteDiario");
        dataCompleta = simpleDateFormatCollectionDataCaixaDiario.format(hoje.getTime());
        mesAtual = simpleDateFormatCollectionReferenciaAtual.format(hoje.getTime());
        anoAtual = simpleDateFormatCollectionReferenciaAnoAtual.format(hoje.getTime());
        String mesReferenciaDesseMontante = mesAtual + "_" + anoAtual;
        nomeCollectionNomeCaixaDiario = COLLECTION_CAIXA_DIARIO + mesReferenciaDesseMontante;

    }

    public void carregarlistabolosvendidos() {

        Query query = firebaseFirestore.collection(nomeCollectionNomeCaixaDiario).document(idMontanteAtual).collection("RELATORIOS").orderBy("nomeDoProdutoVendido", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<ModeloProdutoVendido> options = new FirestoreRecyclerOptions.Builder<ModeloProdutoVendido>()
                .setQuery(query, ModeloProdutoVendido.class)
                .build();
        modeloProdutoVendidoAdapter = new ModeloProdutoVendidoAdapter(options);

        recyclerView_lista_bolos_vendidos.setHasFixedSize(true);
        recyclerView_lista_bolos_vendidos.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView_lista_bolos_vendidos.setAdapter(modeloProdutoVendidoAdapter);
        recyclerView_lista_bolos_vendidos.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));


    }

    private void carregarAlertaSettingsBoloVendido(String id) {


        String idBolo = id;


        AlertDialog.Builder alert = new AlertDialog.Builder(BolosVendidosActivity2.this);
        alert.setTitle("OPÇÕES");
        alert.setMessage("Escolha uma das opções abaixo.");

        alert.setPositiveButton("EDITAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Intent intent = new Intent(BolosVendidosActivity2.this, EditarBoloVendidoBancoActivity.class);
                intent.putExtra("idBoloVendido", idBolo);
                intent.putExtra("idMontanteAtual", idMontanteAtual);
                startActivity(intent);


            }
        }).setNeutralButton("DELETAR DO BANCO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Intent intent = new Intent(BolosVendidosActivity2.this, DeletarBoloVendidoBancoActivity.class);
                intent.putExtra("idBoloVendido", idBolo);
                intent.putExtra("idMontanteAtual", idMontanteAtual);
                startActivity(intent);

            }
        }).setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        alert.create();
        alert.show();

    }

    @Override
    protected void onStart() {
        super.onStart();
        carregarlistabolosvendidos();
        modeloProdutoVendidoAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        modeloProdutoVendidoAdapter.stopListening();
    }


}