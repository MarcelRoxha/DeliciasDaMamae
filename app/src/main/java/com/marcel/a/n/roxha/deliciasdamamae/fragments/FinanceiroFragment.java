package com.marcel.a.n.roxha.deliciasdamamae.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.marcel.a.n.roxha.deliciasdamamae.R;
import com.marcel.a.n.roxha.deliciasdamamae.activity.AdicionarOuEditarGastoAvulsoActivity;
import com.marcel.a.n.roxha.deliciasdamamae.activity.CadastrarMaquininhaDeCartaoActivity;
import com.marcel.a.n.roxha.deliciasdamamae.activity.CadastrarOuAtualizarGastoFixoActivity;
import com.marcel.a.n.roxha.deliciasdamamae.adapter.AdapterModeloGastoAvulsos;
import com.marcel.a.n.roxha.deliciasdamamae.adapter.AdapterModeloGastosFixos;
import com.marcel.a.n.roxha.deliciasdamamae.adapter.ModeloItemEstoqueAdapter;
import com.marcel.a.n.roxha.deliciasdamamae.config.ConfiguracaoFirebase;
import com.marcel.a.n.roxha.deliciasdamamae.model.ModeloGastosAvulsos;
import com.marcel.a.n.roxha.deliciasdamamae.model.ModeloGastosFixos;
import com.marcel.a.n.roxha.deliciasdamamae.model.ModeloItemEstoque;
import com.marcel.a.n.roxha.deliciasdamamae.model.ModeloMaquininhaDePassarCartao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FinanceiroFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FinanceiroFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Button botaoCadastrarPorcentagensMaquininha, botaoCadastrarGastoFixos, botaoAdicionarGastoAvulso;
    private RecyclerView recyclerViewGastosFixos;
    private RecyclerView recyclerViewGastosAvulsos;
    private TextView textViewInformaQueAindaNaoCadastrouPorcentagemDaMaquininha ,textViewInformandoQueNaoTemGastosFixosCadastrados,
            textViewInformandoQueNaoTemGastosAvulsos, textViewPorcentagemCartaoCredito, textViewPorcentagemCartaoDebito, textViewNomeMaquininhaQuandoHouver;

    private FirebaseFirestore firestore = ConfiguracaoFirebase.getFirestor();
    private CollectionReference collectionReference;
    private CollectionReference collectionReferenceGastosAvulsos = firestore.collection("GASTOS_AVULSOS");
    private String mesAtual = "";
    private String diaAtual = "";
    private String dataReferenciaGastosAvulsos = "";
    private String anoAtual = "";

    private Date dataHoje = new Date();
    private Date dataCollectionReferenciaMontanteMensal = new Date();

    private SimpleDateFormat simpleDateFormatDataCompleta = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
    private SimpleDateFormat simpleDateFormatCollectionReferenciaAtual = new SimpleDateFormat("MM");
    private SimpleDateFormat simpleDateFormatCollectionReferenciaAnoAtual = new SimpleDateFormat("yyyy");
    private SimpleDateFormat formatoDataVerificaGastosAvulsos = new SimpleDateFormat("dd/MM/yyyy");

    private AdapterModeloGastosFixos adapterModeloGastosFixos;
    private AdapterModeloGastoAvulsos adapterModeloGastoAvulsos;

    private String mesReferenciaDesseMontante;
    private String nomeCompletoColletionMontanteMensal;
    private final String COLLECTION_MONTANTE_DESSE_MES = "MONTANTE_MENSAL";
    public FinanceiroFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Financeiro_oldFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FinanceiroFragment newInstance(String param1, String param2) {
        FinanceiroFragment fragment = new FinanceiroFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View viewFinanceiroFragment = inflater.inflate(R.layout.fragment_financeiro_old, container, false);

        botaoCadastrarPorcentagensMaquininha = viewFinanceiroFragment.findViewById(R.id.botaoCadastrarPorcentagemCreditoEDebitoMaquininhaId);
        botaoCadastrarGastoFixos = viewFinanceiroFragment.findViewById(R.id.botaoCadastrarGastoFixoId);
        botaoAdicionarGastoAvulso = viewFinanceiroFragment.findViewById(R.id.botaoAdicionarGastoAvulsoId);

        textViewPorcentagemCartaoCredito = viewFinanceiroFragment.findViewById(R.id.porcentagemCadastradaParaComprasNaMaquininhaNoCreditoId);
        textViewPorcentagemCartaoDebito = viewFinanceiroFragment.findViewById(R.id.porcentagemCadastradaParaComprasNaMaquininhaNoDebitoId);
        textViewInformaQueAindaNaoCadastrouPorcentagemDaMaquininha = viewFinanceiroFragment.findViewById(R.id.textoVoceAindaNaoCadastrouAsPorcentagensId);
        textViewNomeMaquininhaQuandoHouver = viewFinanceiroFragment.findViewById(R.id.textView99);
        textViewInformandoQueNaoTemGastosAvulsos = viewFinanceiroFragment.findViewById(R.id.textoVoceAindaNaoAdicionouNenhumGastoAvulsoId);
        //Montar as datas referencia atual
        diaAtual = formatoDataVerificaGastosAvulsos.format(dataHoje);
        anoAtual = simpleDateFormatCollectionReferenciaAnoAtual.format(dataHoje);
        mesAtual = simpleDateFormatCollectionReferenciaAtual.format(dataHoje);
        dataReferenciaGastosAvulsos = formatoDataVerificaGastosAvulsos.format(dataHoje);
        mesReferenciaDesseMontante = mesAtual + "_" + anoAtual;
        nomeCompletoColletionMontanteMensal = COLLECTION_MONTANTE_DESSE_MES + "_" + mesReferenciaDesseMontante;

        textViewInformandoQueNaoTemGastosFixosCadastrados = viewFinanceiroFragment.findViewById(R.id.textoVoceAindaNaoPossuiGastosFixosId);
        recyclerViewGastosFixos = viewFinanceiroFragment.findViewById(R.id.recyclerViewGastosFixosId);
        recyclerViewGastosAvulsos = viewFinanceiroFragment.findViewById(R.id.listaGastoAvulsosId);

        botaoCadastrarGastoFixos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), CadastrarOuAtualizarGastoFixoActivity.class);
                startActivity(intent);
            }
        });

        carregarDadosDaMaquininha();
        botaoCadastrarPorcentagensMaquininha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verificaSeTemMaquininhaCadastrada();
            }
        });


        botaoAdicionarGastoAvulso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(), AdicionarOuEditarGastoAvulsoActivity.class);
                startActivity(intent);
            }
        });
//
        carregarDadosGastosFixos();
        verificaSeTemGastosFixosCadastrados();

        adapterModeloGastosFixos.startListening();
        verificaSeTemGastoAvulsoNesseMes();
        return viewFinanceiroFragment;
    }

    private void carregarDadosDaMaquininha() {

        firestore.collection("MAQUININHA_CARTAO").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                List<DocumentSnapshot> listaMaquininhas = queryDocumentSnapshots.getDocuments();

                if(listaMaquininhas.size() > 0){
                    for (DocumentSnapshot list: listaMaquininhas){
                        String idRecuperado = list.getId();

                        firestore.collection("MAQUININHA_CARTAO").document(idRecuperado)
                                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                                        ModeloMaquininhaDePassarCartao maquininhaDePassarCartaoCadastrada = documentSnapshot.toObject(ModeloMaquininhaDePassarCartao.class);
                                        System.out.println("maquininhaDePassarCartaoCadastrada " + maquininhaDePassarCartaoCadastrada.toString());
                                        String nomeMaquininha = String.valueOf(maquininhaDePassarCartaoCadastrada.getNomeDaMaquininha());
                                        String porcentegemConvertidaCredito = String.valueOf(maquininhaDePassarCartaoCadastrada.getPorcentagemDeDescontoCredito());
                                        String porcentegemConvertidaDebito = String.valueOf(maquininhaDePassarCartaoCadastrada.getPorcentagemDeDescontoDebito());
                                        String dataRecuperadaParaRepasse = maquininhaDePassarCartaoCadastrada.getDataPrevistaParaPagamentoDosValoresPassados();
                                        textViewPorcentagemCartaoCredito.setText(porcentegemConvertidaCredito + "%");
                                        textViewPorcentagemCartaoDebito.setText(porcentegemConvertidaDebito + "%");
                                        textViewInformaQueAindaNaoCadastrouPorcentagemDaMaquininha.setTextSize(16f);
                                        textViewInformaQueAindaNaoCadastrouPorcentagemDaMaquininha.setText("Data para repasse todo dia " + dataRecuperadaParaRepasse + " de cada mês");

                                        textViewNomeMaquininhaQuandoHouver.setTextSize(18f);
                                        textViewNomeMaquininhaQuandoHouver.setText("% De desconto maquininha " + nomeMaquininha);
                                    }
                                });
                    }
                }else{
                    textViewPorcentagemCartaoCredito.setText("0%");
                    textViewPorcentagemCartaoDebito.setText("0%");
                }

            }
        });
    }


    private void verificaSeTemMaquininhaCadastrada() {

        firestore.collection("MAQUININHA_CARTAO").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                List<DocumentSnapshot> listaMaquininhas = queryDocumentSnapshots.getDocuments();

                if(listaMaquininhas.size() > 0){
                    for (DocumentSnapshot list: listaMaquininhas){
                        String idRecuperado = list.getId();

                        firestore.collection("MAQUININHA_CARTAO").document(idRecuperado)
                                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                        ModeloMaquininhaDePassarCartao maquininhaDePassarCartaoCadastrada = documentSnapshot.toObject(ModeloMaquininhaDePassarCartao.class);
                                        System.out.println("maquininhaDePassarCartaoCadastrada " + maquininhaDePassarCartaoCadastrada.toString());
                                        String nomeRecuperadoDaMaquininha = maquininhaDePassarCartaoCadastrada.getNomeDaMaquininha();
                                        String porcentegemConvertidaCredito = String.valueOf(maquininhaDePassarCartaoCadastrada.getPorcentagemDeDescontoCredito());
                                        String porcentegemConvertidaDebito = String.valueOf(maquininhaDePassarCartaoCadastrada.getPorcentagemDeDescontoDebito());

                                        textViewPorcentagemCartaoCredito.setText(porcentegemConvertidaCredito + "%");
                                        textViewPorcentagemCartaoDebito.setText(porcentegemConvertidaDebito + "%");
                                        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                                        alert.setTitle("MAQUININHA JÁ CADASTRADA");
                                        alert.setMessage("Atenção, a aplicação foi feita para identificar apenas uma maquininha de cartão, caso precise cadastrar uma a mais contate o suporte, caso contrário atualize a maquininha existe " + nomeRecuperadoDaMaquininha);
                                        alert.setCancelable(false);
                                        alert.setPositiveButton("ATUALIZAR", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {

                                                Intent intent = new Intent(getContext(), CadastrarMaquininhaDeCartaoActivity.class);
                                                System.out.println("Id recuperado " + idRecuperado);
                                                intent.putExtra("idMaquininha", idRecuperado);
                                                startActivity(intent);

                                            }
                                        }).setNeutralButton("CANCELAR", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {

                                            }
                                        });
                                        alert.show();
                                        alert.create();

                                    }
                                });
                    }
                }else{
                    Intent intent = new Intent(getContext(), CadastrarMaquininhaDeCartaoActivity.class);
                    startActivity(intent);
                }

            }
        });
    }


    private void carregarDadosGastosFixos(){

        collectionReference = firestore.collection("GASTOS_FIXOS");

        Query query = collectionReference.orderBy("nomeDoGastoFixo", Query.Direction.ASCENDING);
        System.out.println("Passou pelo carrega dados Fixos");
        FirestoreRecyclerOptions<ModeloGastosFixos> options = new FirestoreRecyclerOptions.Builder<ModeloGastosFixos>()
                .setQuery(query, ModeloGastosFixos.class)
                .build();




        adapterModeloGastosFixos = new AdapterModeloGastosFixos(options);
        recyclerViewGastosFixos.setHasFixedSize(true);
        recyclerViewGastosFixos.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewGastosFixos.setAdapter(adapterModeloGastosFixos);
        adapterModeloGastosFixos.startListening();

    }


    public void verificaSeTemGastosFixosCadastrados(){

        firestore.collection("GASTOS_FIXOS").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                List<DocumentSnapshot> listaDeGastosFixo = queryDocumentSnapshots.getDocuments();

                if(listaDeGastosFixo.size() > 0){
                    textViewInformandoQueNaoTemGastosFixosCadastrados.setVisibility(View.GONE);
                }else {
                    textViewInformandoQueNaoTemGastosFixosCadastrados.setVisibility(View.VISIBLE);
                }


            }
        });

    }


    public void verificaSeTemGastoAvulsoNesseMes(){

        System.out.println("mes referencia " + mesReferenciaDesseMontante);
        firestore.collection("GASTOS_AVULSOS").whereEqualTo("dataPagamentoAvulsos", mesReferenciaDesseMontante).get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                           List<DocumentSnapshot> listaGastoAvulsosDesseMes = queryDocumentSnapshots.getDocuments();
                           if(listaGastoAvulsosDesseMes.size() > 0){
                               System.out.println("Achou a queryy");
                               carregarDadosDosGastosAvulsosDesseMes();
                           }else{
                               System.out.println("Não achou a queryy");
                           }
                            }
                        });



    }

    private void carregarDadosDosGastosAvulsosDesseMes() {
//        CollectionReference collectionReference = firestore.collection("GASTOS_AVULSOS");

        Query query = collectionReferenceGastosAvulsos.whereEqualTo("dataPagamentoAvulsos", mesReferenciaDesseMontante).orderBy("nomeDoGastoAvulsos", Query.Direction.ASCENDING);
        System.out.println("Query++++++++++++++++++++++ " + query.get());



//        Query query = collectionReferenceGastosAvulsos.orderBy("nomeDoGastoFixo", Query.Direction.ASCENDING);
        System.out.println("Passou pelo carregarDadosDosGastosAvulsosDesseMes");


        FirestoreRecyclerOptions<ModeloGastosAvulsos> options = new FirestoreRecyclerOptions.Builder<ModeloGastosAvulsos>()
                .setQuery(query, ModeloGastosAvulsos.class)
                .build();



        adapterModeloGastoAvulsos = new AdapterModeloGastoAvulsos(options);
        recyclerViewGastosAvulsos.setHasFixedSize(true);
        recyclerViewGastosAvulsos.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewGastosAvulsos.setAdapter(adapterModeloGastoAvulsos);
        adapterModeloGastoAvulsos.startListening();

    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();
//        adapterModeloGastoAvulsos.stopListening();
    }
}