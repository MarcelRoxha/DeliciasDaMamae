package com.marcel.a.n.roxha.deliciasdamamae.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.marcel.a.n.roxha.deliciasdamamae.R;
import com.marcel.a.n.roxha.deliciasdamamae.adapter.ReceitasProntasAdapter;
import com.marcel.a.n.roxha.deliciasdamamae.model.ReceitaModel;

public class ReceitasProntasActivity extends AppCompatActivity {

    private RecyclerView recycler_receitas_prontas;
    private ReceitasProntasAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setTheme(R.style.Theme_DeliciasDaMamae);
        setContentView(R.layout.activity_receitas_prontas);

        recycler_receitas_prontas = findViewById(R.id.recycler_receitas_prontas_id);
    }

    public void carregarReceitassProntas(){

        Query query = FirebaseFirestore.getInstance().collection("Receitas").orderBy("nomeReceita", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<ReceitaModel> options = new FirestoreRecyclerOptions.Builder<ReceitaModel>()
                .setQuery(query, ReceitaModel.class)
                .build();

        adapter = new ReceitasProntasAdapter(options);

        recycler_receitas_prontas.setHasFixedSize(true);
        recycler_receitas_prontas.setLayoutManager(new LinearLayoutManager(this));
        recycler_receitas_prontas.setAdapter(adapter);
       recycler_receitas_prontas.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));


    }

    @Override
    protected void onStart() {
        super.onStart();
        carregarReceitassProntas();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}