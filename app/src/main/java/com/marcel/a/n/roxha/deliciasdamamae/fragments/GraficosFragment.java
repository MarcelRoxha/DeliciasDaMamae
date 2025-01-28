package com.marcel.a.n.roxha.deliciasdamamae.fragments;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.marcel.a.n.roxha.deliciasdamamae.R;
import com.marcel.a.n.roxha.deliciasdamamae.adapter.BolosAdicionadosVitrineParaExibirQuandoVenderAdapter;
import com.marcel.a.n.roxha.deliciasdamamae.config.ConfiguracaoFirebase;
import com.marcel.a.n.roxha.deliciasdamamae.model.ModeloFormataData;
import com.marcel.a.n.roxha.deliciasdamamae.model.ModeloGastosAvulsos;
import com.marcel.a.n.roxha.deliciasdamamae.model.ModeloGastosFixos;
import com.marcel.a.n.roxha.deliciasdamamae.model.ModeloItemEstoque;
import com.marcel.a.n.roxha.deliciasdamamae.model.ModeloMontanteDiario;
import com.marcel.a.n.roxha.deliciasdamamae.model.ModeloMontanteMensalLoja;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GraficosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GraficosFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    //Componentes de tela
    public AlertDialog progressDialogCarregandoAsInformacoesDoCaixaDiario;
    private RecyclerView recyclerView_relatorios_mensal;
    private RecyclerView recyclerView_relatorios_Diario;

    private LayoutInflater inflaterLayout;

    private BarChart barChart;
    private BarChart barChartHoje;
    private BarChart barChartGastos;
    private BarData barData;
    private BarData barDataHoje;
    private BarDataSet barDataSet;
    private BarDataSet barDataSetHoje;

    private TextView textoInformativoUltimaAtualizacaoDoPainel;
    //Mensal
    private TextView textoInformativoTotalDeVendasLojaMensal, valorTotalDeVendasLojaMensal;
    private TextView textoInformativoTotalDeVendasIfoodMensal, valorTotalDeVendasIfoodMensal;
    private TextView textoInformativoTotalDeVendasNoDinheiroMensal, valorTotalDeVendasNoDinheiroMensal;
    private TextView textoInformativoTotalDeVendasNoDebitoMensal, valorTotalDeVendasNoDebitoMensal;
    private TextView textoInformativoTotalDeVendasNoCreditoMensal, valorTotalDeVendasNoCreditoMensal;
    private TextView textoInformativoQuantidadeTotalDeVendasMensal, quantidadeDeVendasMensal;
    private TextView textoInformaQueNaoTemDadosMensaisParaSeremExibidos;
    private TextView textoGraficoDesseMes;

    //Diario
    private TextView textoInformativoTotalDeVendasLojaHoje, valorTotalDeVendasLojaHoje;
    private TextView textoInformativoTotalDeVendasIfoodHoje, valorTotalDeVendasIfoodHoje;
    private TextView textoInformativoTotalDeVendasNoDinheiroHoje, valorTotalDeVendasNoDinheiroHoje;
    private TextView textoInformativoTotalDeVendasNoDebitoHoje, valorTotalDeVendasNoDebitoHoje;
    private TextView textoInformativoTotalDeVendasNoCreditoHoje, valorTotalDeVendasNoCreditoHoje;
    private TextView textoInformativoQuantidadeTotalDeVendasHoje, quantidadeDeVendasHoje;
    private TextView textoInformaQueNaoTemDadosDiarioParaSeremExibidos;
    private TextView textoGraficoDeHoje;


    //Variaveis globais
    private String keyUserRecuperado = "";
    private String mesAtual = "";
    private String diaAtual = "";
    private String diaAnterior = "";
    private String dataReferenciaCaixaDiario = "";
    private String anoAtual = "";
    private Date dataHoje = new Date();
    private String idRecuperadoMontanteCasoExista = "";
    private String idRecuperadoMontanteMensal = "";
    private String idRecuperadoCaixaDiario = "";
    private String idRecuperadoMontanteColect = "";
    private int numMesReferencia;
    private int verificaCaixaMensalCriado = 0;
    private final String COLLECTION_CAIXA_DIARIO = "CAIXAS_DIARIO";

    private final String COLLECTION_MONTANTE_DESSE_MES = "MONTANTE_MENSAL";

    private final String COLLECTION_BOLOS_EXPOSTOS_VITRINE = "BOLOS_EXPOSTOS_VITRINE";

    private String nomeCompletoColletionMontanteMensal = "";
    private String nomeCompletoColletionMontanteDiario = "";
    private String mesReferenciaDesseMontante = "";
    private String nomeDoMes = "";

    private Date hoje = new Date();
    private Date dataCollectionReferenciaMontanteMensal = new Date();
    private String hojeString = "";
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
    private SimpleDateFormat simpleDateFormatCollectionReferenciaAtual = new SimpleDateFormat("MM");
    private SimpleDateFormat simpleDateFormatCollectionReferenciaAnoAtual = new SimpleDateFormat("yyyy");
    private SimpleDateFormat simpleDateFormatCollectionReferenciaAtualModeloCaixaDiario = new SimpleDateFormat("dd/MM/yyyy");

    //BANCO FIREBASE
    private FirebaseAuth auth = ConfiguracaoFirebase.getAuth();
    private FirebaseFirestore firebaseFirestore = ConfiguracaoFirebase.getFirestor();
    private CollectionReference referenceCaixaDiario = firebaseFirestore.collection(COLLECTION_CAIXA_DIARIO);
    private CollectionReference referenceMontanteDesseMes = firebaseFirestore.collection(COLLECTION_MONTANTE_DESSE_MES);
    private BolosAdicionadosVitrineParaExibirQuandoVenderAdapter expostoAdapter;

    private CollectionReference refBolosExpostosVitrine = firebaseFirestore.collection(COLLECTION_BOLOS_EXPOSTOS_VITRINE);

    private List<ModeloGastosFixos> listaGastosFixos = new ArrayList<>();
    private List<ModeloGastosAvulsos> listaGastosAvulsos = new ArrayList<>();
    private List<ModeloItemEstoque> listaItensEmEstoque = new ArrayList<>();

    public GraficosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FinanceiroFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GraficosFragment newInstance(String param1, String param2) {
        GraficosFragment fragment = new GraficosFragment();
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



        View viewFinanceiroFragment = inflater.inflate(R.layout.fragment_financeiro, container, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        inflaterLayout = getActivity().getLayoutInflater();
        builder.setView(inflaterLayout.inflate(R.layout.progress_dialog_carregando_informacoes_caixa_diario, null));
        builder.setCancelable(true);

        this.progressDialogCarregandoAsInformacoesDoCaixaDiario = builder.create();
        this.progressDialogCarregandoAsInformacoesDoCaixaDiario.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        this.progressDialogCarregandoAsInformacoesDoCaixaDiario.show();

        //Montar as datas referencia atual
        diaAtual = simpleDateFormatCollectionReferenciaAtualModeloCaixaDiario.format(dataHoje);
        anoAtual = simpleDateFormatCollectionReferenciaAnoAtual.format(dataHoje);
        mesAtual = simpleDateFormatCollectionReferenciaAtual.format(dataHoje);
        dataReferenciaCaixaDiario = simpleDateFormatCollectionReferenciaAtualModeloCaixaDiario.format(dataHoje);
        mesReferenciaDesseMontante = mesAtual + "_" + anoAtual;
        nomeCompletoColletionMontanteMensal = COLLECTION_MONTANTE_DESSE_MES + "_" + mesReferenciaDesseMontante;
        nomeCompletoColletionMontanteDiario = COLLECTION_CAIXA_DIARIO + "_" + mesReferenciaDesseMontante;
        this.progressDialogCarregandoAsInformacoesDoCaixaDiario.show();
        verificaSeJaTemDadosParaSeremExibidos(this.progressDialogCarregandoAsInformacoesDoCaixaDiario);

        //Graficos
        barChart = viewFinanceiroFragment.findViewById(R.id.grafico_mensal_id);
        barChartHoje = viewFinanceiroFragment.findViewById(R.id.grafico_hoje_id);
        barChartGastos = viewFinanceiroFragment.findViewById(R.id.grafico_gastos_id);


        //Componentes de tela
        textoInformativoUltimaAtualizacaoDoPainel = viewFinanceiroFragment.findViewById(R.id.texto_ultima_atualizacao_financeiro);
        //Mensal
        textoInformativoTotalDeVendasLojaMensal = viewFinanceiroFragment.findViewById(R.id.textoTotalDeVendasLojaFragmentFinanceiro);
        valorTotalDeVendasLojaMensal = viewFinanceiroFragment.findViewById(R.id.texto_total_vendas_loja_esse_mes_financeiro);
        textoInformativoTotalDeVendasIfoodMensal = viewFinanceiroFragment.findViewById(R.id.textoTotalDeIfoodFragmentFinanceiro);
        valorTotalDeVendasIfoodMensal = viewFinanceiroFragment.findViewById(R.id.texto_total_vendas_ifood_esse_mes_financeiro);
        textoInformativoTotalDeVendasNoDinheiroMensal = viewFinanceiroFragment.findViewById(R.id.textoTotalDeVendasNoDinheiroFragmentFinanceiro);
        valorTotalDeVendasNoDinheiroMensal = viewFinanceiroFragment.findViewById(R.id.texto_total_vendas_no_dinheiro_esse_mes_financeiro);
        textoInformativoTotalDeVendasNoDebitoMensal = viewFinanceiroFragment.findViewById(R.id.textoTotalDeVendasNoDebitoFragmentFinanceiro);
        valorTotalDeVendasNoDebitoMensal = viewFinanceiroFragment.findViewById(R.id.texto_total_vendas_no_debito_esse_mes_financeiro);
        textoInformativoTotalDeVendasNoCreditoMensal = viewFinanceiroFragment.findViewById(R.id.textoTotalDeVendasNoCreditoFragmentFinanceiro);
        valorTotalDeVendasNoCreditoMensal = viewFinanceiroFragment.findViewById(R.id.texto_total_vendas_no_credito_esse_mes_financeiro);
        textoInformativoQuantidadeTotalDeVendasMensal = viewFinanceiroFragment.findViewById(R.id.textoQuantidadeTotalDeVendasFragmentFinanceiro);
        quantidadeDeVendasMensal = viewFinanceiroFragment.findViewById(R.id.texto_quantidade_total_de_vendas_esse_mes_financeiro);
        textoInformaQueNaoTemDadosMensaisParaSeremExibidos = viewFinanceiroFragment.findViewById(R.id.textoInformaNaoTemDadosParaExibir);
        textoGraficoDesseMes = viewFinanceiroFragment.findViewById(R.id.textoGraficoMensalFragmentFinanceiro);

        //Diario
        textoInformativoTotalDeVendasLojaHoje = viewFinanceiroFragment.findViewById(R.id.textoTotalDeVendasLojaDeHojeFragmentFinanceiro);
        valorTotalDeVendasLojaHoje = viewFinanceiroFragment.findViewById(R.id.texto_total_vendas_loja_de_hoje_financeiro);
        textoInformativoTotalDeVendasIfoodHoje = viewFinanceiroFragment.findViewById(R.id.textoTotalDeIfoodDeHojeFragmentFinanceiro);
        valorTotalDeVendasIfoodHoje = viewFinanceiroFragment.findViewById(R.id.texto_total_vendas_ifood_de_hoje_financeiro);
        textoInformativoTotalDeVendasNoDinheiroHoje = viewFinanceiroFragment.findViewById(R.id.textoTotalDeVendasNoDinheiroHojeFragmentFinanceiro);
        valorTotalDeVendasNoDinheiroHoje = viewFinanceiroFragment.findViewById(R.id.texto_total_vendas_no_dinheiro_de_hoje_financeiro);
        textoInformativoTotalDeVendasNoDebitoHoje = viewFinanceiroFragment.findViewById(R.id.textoTotalDeVendasNoDebitoHojeFragmentFinanceiro);
        valorTotalDeVendasNoDebitoHoje = viewFinanceiroFragment.findViewById(R.id.texto_total_vendas_no_debito_de_hoje_financeiro);
        textoInformativoTotalDeVendasNoCreditoHoje = viewFinanceiroFragment.findViewById(R.id.textoTotalDeVendasNoCreditoHojeFragmentFinanceiro);
        valorTotalDeVendasNoCreditoHoje = viewFinanceiroFragment.findViewById(R.id.texto_total_vendas_no_credito_de_hoje_financeiro);
        textoInformativoQuantidadeTotalDeVendasHoje = viewFinanceiroFragment.findViewById(R.id.textoQuantidadeTotalDeVendasHojeFragmentFinanceiro);
        quantidadeDeVendasHoje = viewFinanceiroFragment.findViewById(R.id.texto_quantidade_total_de_vendas_de_hoje_financeiro);
        textoInformaQueNaoTemDadosDiarioParaSeremExibidos = viewFinanceiroFragment.findViewById(R.id.textoInformaNaoTemDadosParaExibirDiario);
        textoGraficoDeHoje = viewFinanceiroFragment.findViewById(R.id.textoGraficoHojeFragmentFinanceiro);

        hoje = new Date();
        hojeString = simpleDateFormat.format(hoje.getTime());
        textoInformativoUltimaAtualizacaoDoPainel.setText(hojeString);
        // Inflate the layout for this fragment
        recuperaDadosDeGastosAvulsosEFixos();
        this.progressDialogCarregandoAsInformacoesDoCaixaDiario.dismiss();
        return viewFinanceiroFragment;
    }

    private void verificaSeJaTemDadosParaSeremExibidos(AlertDialog progressDialogCarregandoAsInformacoesDoCaixaDiario) {
        progressDialogCarregandoAsInformacoesDoCaixaDiario.show();
        firebaseFirestore.collection(nomeCompletoColletionMontanteMensal).whereEqualTo("mesReferenciaDesseMontante", mesReferenciaDesseMontante).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots){
                List<DocumentSnapshot> listaDeMontanteMensal = queryDocumentSnapshots.getDocuments();

                if(listaDeMontanteMensal.size() > 0){

                    for(DocumentSnapshot list: listaDeMontanteMensal){

                        String idRecuperadoDoMontanteMensal = list.getId();
                        carregaDadosDoMontanteMensal(idRecuperadoDoMontanteMensal);
                    }
                }else{

                    carregaCargaParaDesaparecerComponentesDaTelaQuandoNaoTemMontanteMensalCriado();
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

       // carregaCargaParaAparecerComponentesDaTela();
    }
    private void carregaDadosDoMontanteMensal(String idRecuperadoDoMontanteMensal) {
        firebaseFirestore.collection(nomeCompletoColletionMontanteMensal).document(idRecuperadoDoMontanteMensal).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                System.out.println("dentro documentSnapshot");
                ModeloMontanteMensalLoja modeloMontanteMensalLojaRecuperaDados = documentSnapshot.toObject(ModeloMontanteMensalLoja.class);
                String totalDeVendasBoleriaRecuperada = modeloMontanteMensalLojaRecuperaDados.getValorTotalVendasBoleriaMensal().replace(",", ".");
                String totalDeVendasNoIfoodRecuperado = modeloMontanteMensalLojaRecuperaDados.getValorTotalVendasIfoodMensal().replace(",", ",");
                String totalDeVendasNoDinheiroRecuperado = modeloMontanteMensalLojaRecuperaDados.getValorTotalDeVendasNoDinheiroDesseMes().replace(",", ",");
                String totalDeVendasNoDebitoRecuperado = modeloMontanteMensalLojaRecuperaDados.getValorTotalDeVendasNoDebitoDesseMes().replace(",", ",");
                String totalDeVendasNoCreditoRecuperado = modeloMontanteMensalLojaRecuperaDados.getValorTotalDeVendasNoCreditoDesseMes().replace(",", ",");

                int quantidadeTotalDeVendas = modeloMontanteMensalLojaRecuperaDados.getQuantidadeDeVendasDesseMes();
                ModeloMontanteMensalLoja modeloMontanteMensalLojaExibeDadosTela = new ModeloMontanteMensalLoja();
                modeloMontanteMensalLojaExibeDadosTela.setValorTotalVendasBoleriaMensal(totalDeVendasBoleriaRecuperada);
                modeloMontanteMensalLojaExibeDadosTela.setValorTotalVendasIfoodMensal(totalDeVendasNoIfoodRecuperado);
                modeloMontanteMensalLojaExibeDadosTela.setValorTotalDeVendasNoDinheiroDesseMes(totalDeVendasNoDinheiroRecuperado);
                modeloMontanteMensalLojaExibeDadosTela.setValorTotalDeVendasNoDebitoDesseMes(totalDeVendasNoDebitoRecuperado);
                modeloMontanteMensalLojaExibeDadosTela.setValorTotalDeVendasNoCreditoDesseMes(totalDeVendasNoCreditoRecuperado);
                modeloMontanteMensalLojaExibeDadosTela.setQuantidadeDeVendasDesseMes(quantidadeTotalDeVendas);

                cargaDeDadosPraExibirReferenteAoMes(modeloMontanteMensalLojaExibeDadosTela);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println("Errorrr++++ " + e.getMessage());
                Toast.makeText(getContext(), "Verifique sua internet e tente novamente", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void cargaDeDadosPraExibirReferenteAoMes(ModeloMontanteMensalLoja modeloMontanteMensalLojaExibeDadosTela) {
        progressDialogCarregandoAsInformacoesDoCaixaDiario.show();
        textoInformativoUltimaAtualizacaoDoPainel.setVisibility(View.VISIBLE);
        textoInformativoTotalDeVendasLojaMensal.setVisibility(View.VISIBLE);
        valorTotalDeVendasLojaMensal.setVisibility(View.VISIBLE);
        textoInformativoTotalDeVendasIfoodMensal.setVisibility(View.VISIBLE);
        valorTotalDeVendasIfoodMensal.setVisibility(View.VISIBLE);
        textoInformativoTotalDeVendasNoDinheiroMensal.setVisibility(View.VISIBLE);
        valorTotalDeVendasNoDinheiroMensal.setVisibility(View.VISIBLE);
        textoInformativoTotalDeVendasNoDebitoMensal.setVisibility(View.VISIBLE);
        valorTotalDeVendasNoDebitoMensal.setVisibility(View.VISIBLE);
        textoInformativoTotalDeVendasNoCreditoMensal.setVisibility(View.VISIBLE);
        valorTotalDeVendasNoCreditoMensal.setVisibility(View.VISIBLE);
        textoInformativoQuantidadeTotalDeVendasMensal.setVisibility(View.VISIBLE);
        quantidadeDeVendasMensal.setVisibility(View.VISIBLE);
        textoInformativoUltimaAtualizacaoDoPainel.setVisibility(View.VISIBLE);
        textoInformaQueNaoTemDadosMensaisParaSeremExibidos.setVisibility(View.GONE);
        barChart.setVisibility(View.VISIBLE);
        carregaGraficoMensal(modeloMontanteMensalLojaExibeDadosTela);
        valorTotalDeVendasLojaMensal.setText(modeloMontanteMensalLojaExibeDadosTela.getValorTotalVendasBoleriaMensal());
        valorTotalDeVendasIfoodMensal.setText(modeloMontanteMensalLojaExibeDadosTela.getValorTotalVendasIfoodMensal());
        valorTotalDeVendasNoDinheiroMensal.setText(modeloMontanteMensalLojaExibeDadosTela.getValorTotalDeVendasNoDinheiroDesseMes());
        valorTotalDeVendasNoDebitoMensal.setText(modeloMontanteMensalLojaExibeDadosTela.getValorTotalDeVendasNoDebitoDesseMes());
        valorTotalDeVendasNoCreditoMensal.setText(modeloMontanteMensalLojaExibeDadosTela.getValorTotalDeVendasNoCreditoDesseMes());
        String quantidadeConvert = String.valueOf(modeloMontanteMensalLojaExibeDadosTela.getQuantidadeDeVendasDesseMes());
        quantidadeDeVendasMensal.setText(quantidadeConvert);

        verificaSeTemMontanteDiarioParaDadosSeremExibidos(progressDialogCarregandoAsInformacoesDoCaixaDiario);


    }

    private void recuperaDadosDeGastosAvulsosEFixos(){
        this.progressDialogCarregandoAsInformacoesDoCaixaDiario.show();
        listaGastosFixos = new ArrayList<>();
        // Inicializa o Firebase Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();

// Referencia a coleção de documentos
        CollectionReference collectionRef = db.collection("GASTOS_FIXOS");

// Cria um listener para receber as atualizações em tempo real
        collectionRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@NonNull QuerySnapshot snapshot, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.d("TAG", "Erro ao ouvir atualizações: ", e);
                    return;
                }

                List<DocumentSnapshot> documents = snapshot.getDocuments();
                for (DocumentSnapshot document : documents) {
                    ModeloGastosFixos modeloGastosFixosRecuperado = new ModeloGastosFixos();
                    Log.d("TAG", document.getId() + " => " + document.toObject(ModeloGastosFixos.class));
                    modeloGastosFixosRecuperado = document.toObject(ModeloGastosFixos.class);
                    listaGastosFixos.add(modeloGastosFixosRecuperado);
                    // Atualiza a interface do usuário com os dados do documento
//                    atualizarUI(document);
                }
                double somaGastosFixos = 0;
                for (ModeloGastosFixos list: listaGastosFixos){
                    somaGastosFixos += list.getValorGastoFixo();
                }

                carregaGastosAvulsosDesseMes(somaGastosFixos);

            }
        });


    }

    private void carregaGastosAvulsosDesseMes(double somaGastosFixos) {
        listaGastosAvulsos = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        ModeloFormataData dataRef = new ModeloFormataData();
        String referenciaAtual = dataRef.getRetornaDataRefAtual();

// Referencia a coleção de documentos
        CollectionReference collectionRef = db.collection("GASTOS_AVULSOS");

// Cria um listener para receber as atualizações em tempo real
        collectionRef.whereEqualTo("dataPagamentoAvulsos", referenciaAtual).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@NonNull QuerySnapshot snapshot, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.d("TAG", "Erro ao ouvir atualizações: ", e);
                    return;
                }

                List<DocumentSnapshot> documents = snapshot.getDocuments();
                for (DocumentSnapshot document : documents) {
                    ModeloGastosAvulsos modeloGastosFixosRecuperado = new ModeloGastosAvulsos();
                    Log.d("TAG", document.getId() + " => " + document.toObject(ModeloGastosAvulsos.class));
                    modeloGastosFixosRecuperado = document.toObject(ModeloGastosAvulsos.class);
                    listaGastosAvulsos.add(modeloGastosFixosRecuperado);
                    // Atualiza a interface do usuário com os dados do documento
//                    atualizarUI(document);
                }
                double somaValores = 0;
                for (ModeloGastosAvulsos list: listaGastosAvulsos){
                    somaValores += list.getValorGastoAvulsos();
                }

                double somaTotalDeGastos = somaValores + somaGastosFixos;
//                carregaGastosAvulsosDesseMes(somaGastosFixos);

                carregaValorDoEstoqueAtualmente(somaValores, somaGastosFixos,somaTotalDeGastos);

            }
        });

    }

    private void carregaValorDoEstoqueAtualmente(double somaValores, double somaGastosFixos, double somaTotalDeGastos) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collectionRef = db.collection("ITEM_ESTOQUE");
        listaItensEmEstoque = new ArrayList<>();
        collectionRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.d("TAG", "Erro ao ouvir atualizações: ", error);
                    return;
                }
                List<DocumentSnapshot> documents = value.getDocuments();
                for (DocumentSnapshot document : documents) {
                    ModeloItemEstoque modeloGastosFixosRecuperado = new ModeloItemEstoque();
                    Log.d("TAG", document.getId() + " => " + document.toObject(ModeloItemEstoque.class));
                    modeloGastosFixosRecuperado = document.toObject(ModeloItemEstoque.class);
                    listaItensEmEstoque.add(modeloGastosFixosRecuperado);
                    // Atualiza a interface do usuário com os dados do documento
//                    atualizarUI(document);
                }

                double soma = 0;
                for (ModeloItemEstoque list: listaItensEmEstoque){
                    String quantidade = list.getQuantidadeTotalItemEstoque();
                    int quantidadeConvertida = Integer.parseInt(quantidade);
                    String valorIndividual = list.getValorIndividualItemEstoque().replace(",", ".");
                    double valorIndividualConvertido = Double.parseDouble(valorIndividual);
                    double valorTotalDoItem = valorIndividualConvertido * quantidadeConvertida;
                    soma += valorTotalDoItem;
                }

                double somaTotal = soma + somaValores + somaGastosFixos;

                System.out.println("finalizou total avulso" + somaValores);
                System.out.println("finalizou total fixo" + somaGastosFixos);
                System.out.println("finalizou total itens estoque" + soma);
                System.out.println("finalizou total total" + somaTotal);

                carragarValoresParaOsGraficos(somaValores, somaGastosFixos, soma, somaTotal);
            }
        });
    }

    private void carragarValoresParaOsGraficos(double somaValores, double somaGastosFixos, double soma, double somaTotal) {

        String valorGastosAvulsos = String.valueOf(somaValores);
        String valorGastosFixos = String.valueOf(somaGastosFixos);
        String valorGastosComItensEmEstoque = String.valueOf(soma);
        String valorTotalDosGastos = String.valueOf(somaTotal);

        Float floatTotalGastosAvulsos = Float.parseFloat(valorGastosAvulsos.replace(",", "."));
        Float floatTotalGastosFixos = Float.parseFloat(valorGastosFixos.replace(",", "."));
        Float floatTotalGastosItensEstoque = Float.parseFloat(valorGastosComItensEmEstoque.replace(",", "."));
        Float floatTotalDeGastosEmGeral = Float.parseFloat(valorTotalDosGastos.replace(",", "."));

        Float determinaTetoGrafico = floatTotalDeGastosEmGeral + 10;


        List<String> xValuesDiario = Arrays.asList("G.Avulso", "G. Fixo", "Total Estoque", "Total Geral");
        barChartGastos.getAxisRight().setDrawLabels(false);

        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0, floatTotalGastosAvulsos));
        entries.add(new BarEntry(1, floatTotalGastosFixos));
        entries.add(new BarEntry(2, floatTotalGastosItensEstoque));
        entries.add(new BarEntry(3, floatTotalDeGastosEmGeral));

        YAxis yAxis = barChartGastos.getAxisLeft();
        yAxis.setAxisMaximum(0f);
        yAxis.setAxisMaximum(determinaTetoGrafico);
        yAxis.setAxisLineWidth(2f);
        yAxis.setAxisLineColor(Color.BLACK);
        yAxis.setLabelCount(10);


        barDataSetHoje = new BarDataSet(entries, "Valores ao londo do dia");
        barDataSetHoje.setColors(ColorTemplate.COLORFUL_COLORS);

        barDataHoje = new BarData(barDataSetHoje);
        barChartGastos.setData(barDataHoje);

        barChartGastos.getDescription().setEnabled(false);

        barChartGastos.invalidate();

        barChartGastos.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xValuesDiario));
        barChartGastos.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        barChartGastos.getXAxis().setGranularity(1f);
        barChartGastos.getXAxis().setGranularityEnabled(true);
        this.progressDialogCarregandoAsInformacoesDoCaixaDiario.dismiss();
    }

    private void verificaSeTemMontanteDiarioParaDadosSeremExibidos(AlertDialog progressDialogCarregandoAsInformacoesDoCaixaDiario) {
        progressDialogCarregandoAsInformacoesDoCaixaDiario.show();

        System.out.println("verificaSeTemMontanteDiarioParaDadosSeremExibidos");
        System.out.println("nomeCompletoColletionMontanteDiario " + nomeCompletoColletionMontanteDiario);
        System.out.println("diaAtual " + diaAtual);
        firebaseFirestore.collection(nomeCompletoColletionMontanteDiario).whereEqualTo("dataReferenciaMontanteDiarioDesseDia", diaAtual).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> listaCaixasDiarios = queryDocumentSnapshots.getDocuments();
                System.out.println("verificaSeTemMontanteDiarioParaDadosSeremExibidos listaCaixasDiarios");
                if(listaCaixasDiarios.size()>0){
                    for(DocumentSnapshot list: listaCaixasDiarios){
                        String idRecuperado = list.getId();
                        System.out.println("idRecuperado " + idRecuperado);
                        corregaDadosParaSeremExibidosNaTelaMontateDiario(idRecuperado);
                    }
                }else{
                    carregaCargaDeNaoTemInformacoesDiario(progressDialogCarregandoAsInformacoesDoCaixaDiario);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    private void carregaGraficoMensal(ModeloMontanteMensalLoja modeloMontanteMensalLojaRecuperado){

        Float floatTotalLoja = Float.parseFloat(modeloMontanteMensalLojaRecuperado.getValorTotalVendasBoleriaMensal().replace(",", "."));
        Float floatTotalIfood = Float.parseFloat(modeloMontanteMensalLojaRecuperado.getValorTotalVendasIfoodMensal().replace(",", "."));
        Float floatTotalDinheiro = Float.parseFloat(modeloMontanteMensalLojaRecuperado.getValorTotalDeVendasNoDinheiroDesseMes().replace(",", "."));
        Float floatTotalDebito = Float.parseFloat(modeloMontanteMensalLojaRecuperado.getValorTotalDeVendasNoDebitoDesseMes().replace(",", "."));
        Float floatTotalCredito= Float.parseFloat(modeloMontanteMensalLojaRecuperado.getValorTotalDeVendasNoCreditoDesseMes().replace(",", "."));

        Float determinaTetoGrafico = floatTotalLoja + floatTotalIfood;

        List<String> xValues = Arrays.asList("Boleria", "Ifood", "Dinheiro", "Débito", "Crédito");
        barChart.getAxisRight().setDrawLabels(false);

        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0, floatTotalLoja));
        entries.add(new BarEntry(1, floatTotalIfood));
        entries.add(new BarEntry(2, floatTotalDinheiro));
        entries.add(new BarEntry(3, floatTotalDebito));
        entries.add(new BarEntry(4, floatTotalCredito));

        YAxis yAxis = barChart.getAxisLeft();
        yAxis.setAxisMaximum(0f);
        yAxis.setAxisMaximum(determinaTetoGrafico);
        yAxis.setAxisLineWidth(2f);
        yAxis.setAxisLineColor(Color.BLACK);
        yAxis.setLabelCount(10);


        barDataSet = new BarDataSet(entries, "Vendas ao longo do mês");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Cliquei aqui", Toast.LENGTH_SHORT).show();
            }
        });

        barData = new BarData(barDataSet);
        barChart.setData(barData);

        barChart.getDescription().setEnabled(false);

        barChart.invalidate();

        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xValues));
        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        barChart.getXAxis().setGranularity(1f);
        barChart.getXAxis().setGranularityEnabled(true);
    }

    private void carregaGraficoDeHoje(ModeloMontanteDiario modeloMontanteDiario){

        Float floatTotalLoja = Float.parseFloat(modeloMontanteDiario.getValorTotalDeVendasNaLojaDesseDia().replace(",", "."));
        Float floatTotalIfood = Float.parseFloat(modeloMontanteDiario.getValorTotalDeVendasNoIfoodDesseDia().replace(",", "."));
        Float floatTotalDinheiro = Float.parseFloat(modeloMontanteDiario.getValorTotalDeVendasNoDinheiroDesseDia().replace(",", "."));
        Float floatTotalDebito = Float.parseFloat(modeloMontanteDiario.getValorTotalDeVendasNoDebitoDesseDia().replace(",", "."));
        Float floatTotalCredito= Float.parseFloat(modeloMontanteDiario.getValorTotalDeVendasNoCreditoDesseDia().replace(",", "."));

        Float determinaTetoGrafico = floatTotalLoja + floatTotalIfood + 10;


        List<String> xValuesDiario = Arrays.asList("Boleria", "Ifood", "Dinheiro", "Débito", "Crédito");
        barChartHoje.getAxisRight().setDrawLabels(false);

        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0, floatTotalLoja));
        entries.add(new BarEntry(1, floatTotalIfood));
        entries.add(new BarEntry(2, floatTotalDinheiro));
        entries.add(new BarEntry(3, floatTotalDebito));
        entries.add(new BarEntry(4, floatTotalCredito));

        YAxis yAxis = barChartHoje.getAxisLeft();
        yAxis.setAxisMaximum(0f);
        yAxis.setAxisMaximum(determinaTetoGrafico);
        yAxis.setAxisLineWidth(2f);
        yAxis.setAxisLineColor(Color.BLACK);
        yAxis.setLabelCount(10);


        barDataSetHoje = new BarDataSet(entries, "Valores ao londo do dia");
        barDataSetHoje.setColors(ColorTemplate.MATERIAL_COLORS);

        barDataHoje = new BarData(barDataSetHoje);
        barChartHoje.setData(barDataHoje);

        barChartHoje.getDescription().setEnabled(false);

        barChartHoje.invalidate();

        barChartHoje.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xValuesDiario));
        barChartHoje.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        barChartHoje.getXAxis().setGranularity(1f);
        barChartHoje.getXAxis().setGranularityEnabled(true);
        this.progressDialogCarregandoAsInformacoesDoCaixaDiario.dismiss();
    }

    private void carregaCargaParaDesaparecerComponentesDaTelaQuandoNaoTemMontanteMensalCriado(){
        //Componentes de tela

        //Mensal
        textoInformativoUltimaAtualizacaoDoPainel.setVisibility(View.INVISIBLE);
        textoInformativoTotalDeVendasLojaMensal.setVisibility(View.INVISIBLE);
        valorTotalDeVendasLojaMensal.setVisibility(View.INVISIBLE);
        textoInformativoTotalDeVendasIfoodMensal.setVisibility(View.INVISIBLE);
        valorTotalDeVendasIfoodMensal.setVisibility(View.INVISIBLE);
        textoInformativoTotalDeVendasNoDinheiroMensal.setVisibility(View.INVISIBLE);
        valorTotalDeVendasNoDinheiroMensal.setVisibility(View.INVISIBLE);
        textoInformativoTotalDeVendasNoDebitoMensal.setVisibility(View.INVISIBLE);
        valorTotalDeVendasNoDebitoMensal.setVisibility(View.INVISIBLE);
        textoInformativoTotalDeVendasNoCreditoMensal.setVisibility(View.INVISIBLE);
        valorTotalDeVendasNoCreditoMensal.setVisibility(View.INVISIBLE);
        textoInformativoQuantidadeTotalDeVendasMensal.setVisibility(View.INVISIBLE);
        quantidadeDeVendasMensal.setVisibility(View.INVISIBLE);
        textoInformativoUltimaAtualizacaoDoPainel.setVisibility(View.INVISIBLE);
        textoGraficoDesseMes.setVisibility(View.INVISIBLE);

        barChart.setVisibility(View.GONE);
        //Diario

        textoInformativoTotalDeVendasLojaHoje.setVisibility(View.INVISIBLE);
        valorTotalDeVendasLojaHoje.setVisibility(View.INVISIBLE);
        textoInformativoTotalDeVendasIfoodHoje.setVisibility(View.INVISIBLE);
        valorTotalDeVendasIfoodHoje.setVisibility(View.INVISIBLE);
        textoInformativoTotalDeVendasNoDinheiroHoje.setVisibility(View.INVISIBLE);
        valorTotalDeVendasNoDinheiroHoje.setVisibility(View.INVISIBLE);
        textoInformativoTotalDeVendasNoDebitoHoje.setVisibility(View.INVISIBLE);
        valorTotalDeVendasNoDebitoHoje.setVisibility(View.INVISIBLE);
        textoInformativoTotalDeVendasNoCreditoHoje.setVisibility(View.INVISIBLE);
        valorTotalDeVendasNoCreditoHoje.setVisibility(View.INVISIBLE);
        textoInformativoQuantidadeTotalDeVendasHoje.setVisibility(View.INVISIBLE);
        quantidadeDeVendasHoje.setVisibility(View.INVISIBLE);
        textoGraficoDeHoje.setVisibility(View.INVISIBLE);
        barChartHoje.setVisibility(View.INVISIBLE);

        textoInformaQueNaoTemDadosDiarioParaSeremExibidos.setVisibility(View.VISIBLE);
        textoInformaQueNaoTemDadosMensaisParaSeremExibidos.setVisibility(View.VISIBLE);

    }

    private void carregaCargaInformacoesDiariaDoBancoParaATela(AlertDialog progressDialogCarregandoAsInformacoesDoCaixaDiario, ModeloMontanteDiario modeloMontanteDiario){

        System.out.println("modeloMontanteDiario " + modeloMontanteDiario.toString());
        textoInformativoTotalDeVendasLojaHoje.setVisibility(View.VISIBLE);
        valorTotalDeVendasLojaHoje.setVisibility(View.VISIBLE);
        textoInformativoTotalDeVendasIfoodHoje.setVisibility(View.VISIBLE);
        valorTotalDeVendasIfoodHoje.setVisibility(View.VISIBLE);
        textoInformativoTotalDeVendasNoDinheiroHoje.setVisibility(View.VISIBLE);
        valorTotalDeVendasNoDinheiroHoje.setVisibility(View.VISIBLE);
        textoInformativoTotalDeVendasNoDebitoHoje.setVisibility(View.VISIBLE);
        valorTotalDeVendasNoDebitoHoje.setVisibility(View.VISIBLE);
        textoInformativoTotalDeVendasNoCreditoHoje.setVisibility(View.VISIBLE);
        valorTotalDeVendasNoCreditoHoje.setVisibility(View.VISIBLE);
        textoInformativoQuantidadeTotalDeVendasHoje.setVisibility(View.VISIBLE);
        quantidadeDeVendasHoje.setVisibility(View.VISIBLE);
        barChartHoje.setVisibility(View.VISIBLE);
        textoInformaQueNaoTemDadosDiarioParaSeremExibidos.setVisibility(View.GONE);

        String totalDeVendasNoCaixaHoje = modeloMontanteDiario.getValorTotalDeVendasNaLojaDesseDia();
        String totalDeVendasEmDinheiro = modeloMontanteDiario.getValorTotalDeVendasNoDinheiroDesseDia();
        String totalDeSaidaCaixaHoje = modeloMontanteDiario.getValorTotalDeTrocoDesseDia();
        String totalDeVendasNoCreditoHoje = modeloMontanteDiario.getValorTotalDeVendasNoCreditoDesseDia();
        String totalDeVendasNoDebitoNoCaixHoje = modeloMontanteDiario.getValorTotalDeVendasNoDebitoDesseDia();
        String valorTotalDeVendasNoIfoodDesseDia = modeloMontanteDiario.getValorTotalDeVendasNoIfoodDesseDia();
        int quantidadeTotalDeVendasHoje = modeloMontanteDiario.getQuantidadeDeVendasFeitasDesseDia();
        String quantidadeConvert = String.valueOf(quantidadeTotalDeVendasHoje);
        //Diario

       // textoInformativoTotalDeVendasLojaHoje.setText(valorQueOCaixaIniciouODia);
        valorTotalDeVendasLojaHoje.setText(totalDeVendasNoCaixaHoje);
       // textoInformativoTotalDeVendasIfoodHoje.setText(totalDeVendasEmDinheiro);
        valorTotalDeVendasIfoodHoje.setText(valorTotalDeVendasNoIfoodDesseDia);
        //textoInformativoTotalDeVendasNoDinheiroHoje.setText(totalDeVendasNoCreditoHoje);
        valorTotalDeVendasNoDinheiroHoje.setText(totalDeVendasEmDinheiro);
       // textoInformativoTotalDeVendasNoDebitoHoje.setText(valorTotalDeVendasNoIfoodDesseDia);
        valorTotalDeVendasNoDebitoHoje.setText(totalDeVendasNoDebitoNoCaixHoje);
       // textoInformativoTotalDeVendasNoCreditoHoje.setText(View.VISIBLE);
        valorTotalDeVendasNoCreditoHoje.setText(totalDeVendasNoCreditoHoje);
       // textoInformativoQuantidadeTotalDeVendasHoje.setText(View.VISIBLE);
        quantidadeDeVendasHoje.setText(quantidadeConvert);
        carregaGraficoDeHoje(modeloMontanteDiario);
        progressDialogCarregandoAsInformacoesDoCaixaDiario.dismiss();
    }


    private void carregaCargaDeNaoTemInformacoesDiario(AlertDialog progressDialogCarregandoAsInformacoesDoCaixaDiario) {


        textoGraficoDeHoje.setVisibility(View.GONE);
        barChartHoje.setVisibility(View.GONE);
        textoInformativoTotalDeVendasLojaHoje.setVisibility(View.GONE);
        valorTotalDeVendasLojaHoje.setVisibility(View.GONE);
        textoInformativoTotalDeVendasIfoodHoje.setVisibility(View.GONE);
        valorTotalDeVendasIfoodHoje.setVisibility(View.GONE);
        textoInformativoTotalDeVendasNoDinheiroHoje.setVisibility(View.GONE);
        valorTotalDeVendasNoDinheiroHoje.setVisibility(View.GONE);
        textoInformativoTotalDeVendasNoDebitoHoje.setVisibility(View.GONE);
        valorTotalDeVendasNoDebitoHoje.setVisibility(View.GONE);
        textoInformativoTotalDeVendasNoCreditoHoje.setVisibility(View.GONE);
        valorTotalDeVendasNoCreditoHoje.setVisibility(View.GONE);
        textoInformativoQuantidadeTotalDeVendasHoje.setVisibility(View.GONE);
        quantidadeDeVendasHoje.setVisibility(View.INVISIBLE);
        textoInformaQueNaoTemDadosDiarioParaSeremExibidos.setVisibility(View.VISIBLE);
        progressDialogCarregandoAsInformacoesDoCaixaDiario.dismiss();
    }

    private void corregaDadosParaSeremExibidosNaTelaMontateDiario(String idRecuperadoDiario) {
        System.out.println("idRecuperadoDiario " + idRecuperadoDiario);
        firebaseFirestore.collection(nomeCompletoColletionMontanteDiario).document(idRecuperadoDiario).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                ModeloMontanteDiario modeloMontanteDiarioRecuperado = documentSnapshot.toObject(ModeloMontanteDiario.class);

                System.out.println("modeloMontanteDiarioRecuperado " + modeloMontanteDiarioRecuperado.toString());
                ModeloMontanteDiario modeloMontanteDiarioExibeDadosNaTela = modeloMontanteDiarioRecuperado;

                System.out.println("modeloMontanteDiarioExibeDadosNaTela " + modeloMontanteDiarioExibeDadosNaTela.toString());
                progressDialogCarregandoAsInformacoesDoCaixaDiario.show();
                carregaCargaInformacoesDiariaDoBancoParaATela(progressDialogCarregandoAsInformacoesDoCaixaDiario, modeloMontanteDiarioExibeDadosNaTela);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Verifique a conexão e tente novamente mais tarde!", Toast.LENGTH_SHORT).show();
                Log.i("Erro no verifica se tem montante criado", e.getMessage());
            }
        });
    }


}