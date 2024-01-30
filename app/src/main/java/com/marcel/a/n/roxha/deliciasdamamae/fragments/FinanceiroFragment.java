package com.marcel.a.n.roxha.deliciasdamamae.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.marcel.a.n.roxha.deliciasdamamae.R;
import com.marcel.a.n.roxha.deliciasdamamae.activity.CadastrarMaquininhaDeCartaoActivity;
import com.marcel.a.n.roxha.deliciasdamamae.config.ConfiguracaoFirebase;
import com.marcel.a.n.roxha.deliciasdamamae.model.ModeloMaquininhaDePassarCartao;

import java.text.SimpleDateFormat;
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
    private RecyclerView recyclerViewGastosFixos, recyclerViewGastosAvulsos;
    private TextView textViewInformaQueAindaNaoCadastrouPorcentagemDaMaquininha ,textViewInformandoQueNaoTemGastosFixosCadastrados,
            textViewInformandoQueNaoTemGastosAvulsos, textViewPorcentagemCartaoCredito, textViewPorcentagemCartaoDebito, textViewNomeMaquininhaQuandoHouver;

    private FirebaseFirestore firestore = ConfiguracaoFirebase.getFirestor();
    private CollectionReference collectionReference;

    private SimpleDateFormat simpleDateFormatDataCompleta = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
    private SimpleDateFormat simpleDateFormatCollectionReferenciaAtual = new SimpleDateFormat("MM");
    private SimpleDateFormat simpleDateFormatCollectionReferenciaAnoAtual = new SimpleDateFormat("yyyy");
    private SimpleDateFormat simpleDateFormatCollectionReferenciaAtualModeloCaixaDiario = new SimpleDateFormat("dd/MM/yyyy");

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

        carregarDadosDaMaquininha();
        botaoCadastrarPorcentagensMaquininha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verificaSeTemMaquininhaCadastrada();
            }
        });

        // Inflate the layout for this fragment
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



//    private String retornaData(){
//        return
//    }


    @Override
    public void onStart() {
        super.onStart();
        carregarDadosDaMaquininha();
    }
}