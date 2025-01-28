package com.marcel.a.n.roxha.deliciasdamamae.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.marcel.a.n.roxha.deliciasdamamae.R;
import com.marcel.a.n.roxha.deliciasdamamae.adapter.AdapterListaDeItensAcabandoEstoque;
import com.marcel.a.n.roxha.deliciasdamamae.model.BolosModel;
import com.marcel.a.n.roxha.deliciasdamamae.model.ModeloItemEstoque;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListaDeItensAcabandoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListaDeItensAcabandoFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ListaDeItensAcabandoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListaDeItensAcabandoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListaDeItensAcabandoFragment newInstance(String param1, String param2) {
        ListaDeItensAcabandoFragment fragment = new ListaDeItensAcabandoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    private RecyclerView recyclerViewListaDeItensAcabando;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        
        View view = inflater.inflate(R.layout.fragment_lista_de_itens_acabando, container, false);

        recyclerViewListaDeItensAcabando = view.findViewById(R.id.recyclerViewItensAcabandoDoEstoque);
        carregaListaDeItensAcabando();
        return view;
    }

    private void carregaListaDeItensAcabando() {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = firestore.collection("ITEM_ESTOQUE");

        Query query = collectionReference.whereLessThan("quantidadeTotalItemEmEstoqueEmGramas", "50").orderBy("quantidadeTotalItemEmEstoqueEmGramas", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<ModeloItemEstoque> options = new FirestoreRecyclerOptions.Builder<ModeloItemEstoque>()
                .setQuery(query, ModeloItemEstoque.class)
                .build();

        AdapterListaDeItensAcabandoEstoque adapter = new AdapterListaDeItensAcabandoEstoque(options);
        recyclerViewListaDeItensAcabando.setHasFixedSize(true);
        recyclerViewListaDeItensAcabando.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewListaDeItensAcabando.setAdapter(adapter);
        recyclerViewListaDeItensAcabando.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayout.VERTICAL));
        adapter.startListening();
    }
}