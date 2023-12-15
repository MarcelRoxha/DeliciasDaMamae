package com.marcel.a.n.roxha.deliciasdamamae.fragments;

import android.annotation.SuppressLint;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.common.util.concurrent.ExecutionError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.snapshot.Index;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.marcel.a.n.roxha.deliciasdamamae.R;
import com.marcel.a.n.roxha.deliciasdamamae.adapter.BolosAdicionadosVitrineParaExibirQuandoVenderAdapter;
import com.marcel.a.n.roxha.deliciasdamamae.config.ConfiguracaoFirebase;
import com.marcel.a.n.roxha.deliciasdamamae.model.ModeloMontanteDiario;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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


    //Componentes de tela
    public AlertDialog progressDialogCarregandoAsInformacoesDoCaixaDiario;
    private RecyclerView recyclerView_relatorios_mensal;
    private RecyclerView recyclerView_relatorios_Diario;

    private LayoutInflater inflaterLayout;

    private BarChart barChart;
    private BarChart barChartHoje;
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


    //Diario
    private TextView textoInformativoTotalDeVendasLojaHoje, valorTotalDeVendasLojaHoje;
    private TextView textoInformativoTotalDeVendasIfoodHoje, valorTotalDeVendasIfoodHoje;
    private TextView textoInformativoTotalDeVendasNoDinheiroHoje, valorTotalDeVendasNoDinheiroHoje;
    private TextView textoInformativoTotalDeVendasNoDebitoHoje, valorTotalDeVendasNoDebitoHoje;
    private TextView textoInformativoTotalDeVendasNoCreditoHoje, valorTotalDeVendasNoCreditoHoje;
    private TextView textoInformativoQuantidadeTotalDeVendasHoje, quantidadeDeVendasHoje;
    private TextView textoInformaQueNaoTemDadosDiarioParaSeremExibidos;


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

    public FinanceiroFragment() {
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
        verificaSeJaTemDadosParaSeremExibidos(this.progressDialogCarregandoAsInformacoesDoCaixaDiario, nomeCompletoColletionMontanteDiario);

        //Graficos
        barChart = viewFinanceiroFragment.findViewById(R.id.grafico_mensal_id);
        barChartHoje = viewFinanceiroFragment.findViewById(R.id.grafico_hoje_id);
        this.carregaGraficoMensal();
        this.carregaGraficoDeHoje();


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
        textoInformaQueNaoTemDadosDiarioParaSeremExibidos = viewFinanceiroFragment.findViewById(R.id.textoInformaNaoTemDadosParaExibir);
        quantidadeDeVendasHoje = viewFinanceiroFragment.findViewById(R.id.texto_quantidade_total_de_vendas_de_hoje_financeiro);

        this.carregaCargaParaDesaparecerComponentesDaTela();
        // Inflate the layout for this fragment
        return viewFinanceiroFragment;
    }

    private void verificaSeJaTemDadosParaSeremExibidos(AlertDialog progressDialogCarregandoAsInformacoesDoCaixaDiario, String nomeCompletoColletionMontanteDiario) {

        firebaseFirestore.collection(nomeCompletoColletionMontanteDiario).whereEqualTo("dataReferenciaMontanteDiarioDesseDia", diaAtual).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                List<DocumentSnapshot> snapshotList = queryDocumentSnapshots.getDocuments();

                if (snapshotList.size() > 0) {
                    //Significa que existe o documento com a data de hoje então já tem um caixa diario referente a esse dia
                    for (DocumentSnapshot listaCaixasDiariosCriados : snapshotList) {

                        firebaseFirestore.collection(nomeCompletoColletionMontanteDiario).document(listaCaixasDiariosCriados.getId()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {

                                ModeloMontanteDiario modeloMontanteDiarioRecuperadoJaCriado = documentSnapshot.toObject(ModeloMontanteDiario.class);
//                                String idDocumento = modeloMontanteDiarioRecuperadoJaCriado.getIdReferenciaMontanteDiarioDesseDia();
                                idRecuperadoCaixaDiario = modeloMontanteDiarioRecuperadoJaCriado.getIdReferenciaMontanteDiarioDesseDia();
                                idRecuperadoMontanteMensal = modeloMontanteDiarioRecuperadoJaCriado.getIdReferenciaMontanteMensal();
                                String valorQueOCaixaIniciouODia = modeloMontanteDiarioRecuperadoJaCriado.getValorQueOCaixaIniciouODia();
                                String totalDeVendasNoCaixaHoje = modeloMontanteDiarioRecuperadoJaCriado.getValorTotalDeVendasNaLojaDesseDia();
                                String totalDeVendasEmDinheiro = modeloMontanteDiarioRecuperadoJaCriado.getValorTotalDeVendasNoDinheiroDesseDia();
                                String totalDeSaidaCaixaHoje = modeloMontanteDiarioRecuperadoJaCriado.getValorTotalDeTrocoDesseDia();
                                String totalDeVendasNoCreditoHoje = modeloMontanteDiarioRecuperadoJaCriado.getValorTotalDeVendasNoCreditoDesseDia();
                                String totalDeVendasNoDebitoNoCaixHoje = modeloMontanteDiarioRecuperadoJaCriado.getValorTotalDeVendasNoDebitoDesseDia();
                                String valorTotalDeVendasNoIfoodDesseDia = modeloMontanteDiarioRecuperadoJaCriado.getValorTotalDeVendasNoIfoodDesseDia();
                                String valorTotalDeVendasEmGeralDesseDia = modeloMontanteDiarioRecuperadoJaCriado.getValorTotalDeVendasEmGeralDesseDia();
                                int quantidadeTotalDeVendas = modeloMontanteDiarioRecuperadoJaCriado.getQuantidadeDeVendasFeitasDesseDia();


                                ModeloMontanteDiario modeloMontanteDiarioExibe = new ModeloMontanteDiario();

                                modeloMontanteDiarioExibe.setValorQueOCaixaIniciouODia(valorQueOCaixaIniciouODia);
                                modeloMontanteDiarioExibe.setValorQueOCaixaIniciouODia(totalDeVendasNoCaixaHoje);
                                modeloMontanteDiarioExibe.setValorQueOCaixaIniciouODia(totalDeVendasEmDinheiro);
                                modeloMontanteDiarioExibe.setValorQueOCaixaIniciouODia(totalDeSaidaCaixaHoje);
                                modeloMontanteDiarioExibe.setValorQueOCaixaIniciouODia(totalDeVendasNoCreditoHoje);
                                modeloMontanteDiarioExibe.setValorQueOCaixaIniciouODia(totalDeVendasNoDebitoNoCaixHoje);
                                modeloMontanteDiarioExibe.setValorQueOCaixaIniciouODia(valorTotalDeVendasNoIfoodDesseDia);
                                modeloMontanteDiarioExibe.setValorQueOCaixaIniciouODia(valorTotalDeVendasEmGeralDesseDia);
                                modeloMontanteDiarioExibe.setQuantidadeDeVendasFeitasDesseDia(quantidadeTotalDeVendas);
                                progressDialogCarregandoAsInformacoesDoCaixaDiario.show();
                                carregaCargaInformacoesDiariaDoBancoParaATela(progressDialogCarregandoAsInformacoesDoCaixaDiario, modeloMontanteDiarioExibe);


                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });
                    }

                } else {
                    carregaCargaParaAparecerComponentesDaTela();
                    progressDialogCarregandoAsInformacoesDoCaixaDiario.dismiss();


                }


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

       // carregaCargaParaAparecerComponentesDaTela();
    }

    private void carregaGraficoMensal(){
        List<String> xValues = Arrays.asList("Vendas na Loja", "Vendas Ifood", "Gastos");
        barChart.getAxisRight().setDrawLabels(false);

        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0, 45f));
        entries.add(new BarEntry(1, 80f));
        entries.add(new BarEntry(2, 60f));
        entries.add(new BarEntry(3, 35));
        entries.add(new BarEntry(4, 21));

        YAxis yAxis = barChart.getAxisLeft();
        yAxis.setAxisMaximum(0f);
        yAxis.setAxisMaximum(100f);
        yAxis.setAxisLineWidth(2f);
        yAxis.setAxisLineColor(Color.BLACK);
        yAxis.setLabelCount(10);


        barDataSet = new BarDataSet(entries, "Valores ao longo do mês");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        barData = new BarData(barDataSet);
        barChart.setData(barData);

        barChart.getDescription().setEnabled(false);

        barChart.invalidate();

        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xValues));
        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        barChart.getXAxis().setGranularity(1f);
        barChart.getXAxis().setGranularityEnabled(true);
    }

    private void carregaGraficoDeHoje(){
        List<String> xValues = Arrays.asList("Vendas na Loja", "Vendas Ifood", "Gastos");
        barChartHoje.getAxisRight().setDrawLabels(false);

        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0, 45f));
        entries.add(new BarEntry(1, 80f));
        entries.add(new BarEntry(2, 60f));
        entries.add(new BarEntry(3, 35));
        entries.add(new BarEntry(4, 21));

        YAxis yAxis = barChartHoje.getAxisLeft();
        yAxis.setAxisMaximum(0f);
        yAxis.setAxisMaximum(100f);
        yAxis.setAxisLineWidth(2f);
        yAxis.setAxisLineColor(Color.BLACK);
        yAxis.setLabelCount(10);


        barDataSetHoje = new BarDataSet(entries, "Valores ao londo do dia");
        barDataSetHoje.setColors(ColorTemplate.MATERIAL_COLORS);

        barDataHoje = new BarData(barDataSetHoje);
        barChartHoje.setData(barDataHoje);

        barChartHoje.getDescription().setEnabled(false);

        barChartHoje.invalidate();

        barChartHoje.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xValues));
        barChartHoje.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        barChartHoje.getXAxis().setGranularity(4f);
        barChartHoje.getXAxis().setGranularityEnabled(true);
        this.progressDialogCarregandoAsInformacoesDoCaixaDiario.dismiss();
    }

    private void carregaCargaParaDesaparecerComponentesDaTela(){
        //Componentes de tela

        //Mensal
        textoInformativoUltimaAtualizacaoDoPainel.setVisibility(View.GONE);
        textoInformativoTotalDeVendasLojaMensal.setVisibility(View.GONE);
        valorTotalDeVendasLojaMensal.setVisibility(View.GONE);
        textoInformativoTotalDeVendasIfoodMensal.setVisibility(View.GONE);
        valorTotalDeVendasIfoodMensal.setVisibility(View.GONE);
        textoInformativoTotalDeVendasNoDinheiroMensal.setVisibility(View.GONE);
        valorTotalDeVendasNoDinheiroMensal.setVisibility(View.GONE);
        textoInformativoTotalDeVendasNoDebitoMensal.setVisibility(View.GONE);
        valorTotalDeVendasNoDebitoMensal.setVisibility(View.GONE);
        textoInformativoTotalDeVendasNoCreditoMensal.setVisibility(View.GONE);
        valorTotalDeVendasNoCreditoMensal.setVisibility(View.GONE);
        textoInformativoQuantidadeTotalDeVendasMensal.setVisibility(View.GONE);
        quantidadeDeVendasMensal.setVisibility(View.GONE);
        textoInformativoUltimaAtualizacaoDoPainel.setVisibility(View.GONE);
        textoInformativoUltimaAtualizacaoDoPainel.setVisibility(View.GONE);
        textoInformativoUltimaAtualizacaoDoPainel.setVisibility(View.GONE);
        textoInformativoUltimaAtualizacaoDoPainel.setVisibility(View.GONE);
        textoInformativoUltimaAtualizacaoDoPainel.setVisibility(View.GONE);
        textoInformativoUltimaAtualizacaoDoPainel.setVisibility(View.GONE);



        //Diario

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
        quantidadeDeVendasHoje.setVisibility(View.GONE);

    }

    private void carregaCargaParaAparecerComponentesDaTela(){
        //Componentes de tela

        //Mensal
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
        textoInformativoUltimaAtualizacaoDoPainel.setVisibility(View.VISIBLE);
        textoInformativoUltimaAtualizacaoDoPainel.setVisibility(View.VISIBLE);
        textoInformativoUltimaAtualizacaoDoPainel.setVisibility(View.VISIBLE);
        textoInformativoUltimaAtualizacaoDoPainel.setVisibility(View.VISIBLE);
        textoInformativoUltimaAtualizacaoDoPainel.setVisibility(View.VISIBLE);



        //Diario

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

    }


    private void carregaCargaInformacoesDiariaDoBancoParaATela(AlertDialog progressDialogCarregandoAsInformacoesDoCaixaDiario, ModeloMontanteDiario modeloMontanteDiario){
        carregaCargaParaAparecerComponentesDaTela();
        String totalDeVendasNoCaixaHoje = modeloMontanteDiario.getValorTotalDeVendasNaLojaDesseDia();
        String totalDeVendasEmDinheiro = modeloMontanteDiario.getValorTotalDeVendasNoDinheiroDesseDia();
        String totalDeSaidaCaixaHoje = modeloMontanteDiario.getValorTotalDeTrocoDesseDia();
        String totalDeVendasNoCreditoHoje = modeloMontanteDiario.getValorTotalDeVendasNoCreditoDesseDia();
        String totalDeVendasNoDebitoNoCaixHoje = modeloMontanteDiario.getValorTotalDeVendasNoDebitoDesseDia();
        String valorTotalDeVendasNoIfoodDesseDia = modeloMontanteDiario.getValorTotalDeVendasNoIfoodDesseDia();
        int quantidadeTotalDeVendasHoje = modeloMontanteDiario.getQuantidadeDeVendasFeitasDesseDia();
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
        quantidadeDeVendasHoje.setText(quantidadeTotalDeVendasHoje);

    }

    private void verificaSeTemDadosParaSeremExibidosNaTela(AlertDialog progressDialogCarregandoAsInformacoesDoCaixaDiario) {

        try {
            firebaseFirestore.collection(nomeCompletoColletionMontanteMensal).whereEqualTo("mesReferenciaDesseMontante", mesReferenciaDesseMontante).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                    List<DocumentSnapshot> listaDeMontanteParaVerificar = queryDocumentSnapshots.getDocuments();

                    if (listaDeMontanteParaVerificar.size() > 0) {

                        firebaseFirestore.collection(nomeCompletoColletionMontanteDiario).whereEqualTo("dataReferenciaMontanteDiarioDesseDia", dataReferenciaCaixaDiario).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                                List<DocumentSnapshot> listaMontantesDiariosDesseMes = queryDocumentSnapshots.getDocuments();

                                if (listaMontantesDiariosDesseMes.size() > 0) {
                                    progressDialogCarregandoAsInformacoesDoCaixaDiario.dismiss();
                                    //alertaOpaVendi();
                                } else {
                                  //  alertaNecessarioCriarOMontanteAoEfetuarUmaVenda("DIARIO");
                                    progressDialogCarregandoAsInformacoesDoCaixaDiario.dismiss();
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getContext(), "Verifique a conexão e tente novamente mais tarde!", Toast.LENGTH_SHORT).show();
                                Log.i("Erro no verifica se tem montante criado", e.getMessage());
                            }
                        });

                    } else {


                      //  alertaNecessarioCriarOMontanteAoEfetuarUmaVenda("MENSAL");
                    }

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    Toast.makeText(getContext(), "Verifique a conexão e tente novamente mais tarde!", Toast.LENGTH_SHORT).show();
                    Log.i("Erro no verifica se tem montante criado", e.getMessage());

                }
            });


        } catch (ExceptionInInitializerError e) {
            System.out.println("ExceptionInInitializerError: " + e.getMessage());

        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());

        } catch (ExecutionError ex) {
            System.out.println("ExecutionError: " + ex.getMessage());

        }


    }
}