package com.marcel.a.n.roxha.deliciasdamamae.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.marcel.a.n.roxha.deliciasdamamae.R;
import com.marcel.a.n.roxha.deliciasdamamae.activity.EditarReceitasCompletasCadastradas;
import com.marcel.a.n.roxha.deliciasdamamae.activity.FinalizarReceitaActivity;
import com.marcel.a.n.roxha.deliciasdamamae.activity.NovaReceitaActivity;
import com.marcel.a.n.roxha.deliciasdamamae.adapter.ReceitasProntasAdapter;
import com.marcel.a.n.roxha.deliciasdamamae.model.ReceitaModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReceitasProntasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReceitasProntasFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ReceitasProntasFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ReceitasProntasFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReceitasProntasFragment newInstance(String param1, String param2) {
        ReceitasProntasFragment fragment = new ReceitasProntasFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private RecyclerView recyclerView_Receitas_completas_cadastradas;


    private ReceitasProntasAdapter adapterReceitaCompleta;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_receitas_prontas, container, false);

        recyclerView_Receitas_completas_cadastradas = view.findViewById(R.id.recycler_receitas_prontas_fragment_id);


        return view;
    }

    public void carregarListaReceitasCompletasCadastradas(){

        Query query = FirebaseFirestore.getInstance().collection("RECEITA_CADASTRADA").orderBy("nomeReceita", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<ReceitaModel> options = new FirestoreRecyclerOptions.Builder<ReceitaModel>()
                .setQuery(query, ReceitaModel.class)
                .build();

         adapterReceitaCompleta = new ReceitasProntasAdapter(options);

        recyclerView_Receitas_completas_cadastradas.setHasFixedSize(true);
        recyclerView_Receitas_completas_cadastradas.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView_Receitas_completas_cadastradas.setAdapter(adapterReceitaCompleta);

        adapterReceitaCompleta.setOnItemClickListerner(new ReceitasProntasAdapter.OnItemClickLisener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {


                Intent intent = new Intent(getActivity(), EditarReceitasCompletasCadastradas.class);
                intent.putExtra("idReceita", documentSnapshot.getId());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        carregarListaReceitasCompletasCadastradas();

        adapterReceitaCompleta.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();

        adapterReceitaCompleta.stopListening();
    }
}