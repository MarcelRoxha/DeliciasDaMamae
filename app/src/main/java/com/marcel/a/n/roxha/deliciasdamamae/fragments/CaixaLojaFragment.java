package com.marcel.a.n.roxha.deliciasdamamae.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.common.util.concurrent.ExecutionError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.marcel.a.n.roxha.deliciasdamamae.R;
import com.marcel.a.n.roxha.deliciasdamamae.activity.AdicionarBoloVitrineActivity2;
import com.marcel.a.n.roxha.deliciasdamamae.activity.AdicionarProdutoComoVendidoNoSistema;
import com.marcel.a.n.roxha.deliciasdamamae.activity.BolosVendidosActivity2;
import com.marcel.a.n.roxha.deliciasdamamae.adapter.AdapterModeloBolosAdicionadosVitrineQuandoVender;
import com.marcel.a.n.roxha.deliciasdamamae.adapter.BolosAdicionadosVitrineParaExibirQuandoVenderAdapter;
import com.marcel.a.n.roxha.deliciasdamamae.config.ConfiguracaoFirebase;
import com.marcel.a.n.roxha.deliciasdamamae.controller.helper.ModeloMontanteDiarioDAO;
import com.marcel.a.n.roxha.deliciasdamamae.controller.helper.ModeloMontanteMensalDAO;
import com.marcel.a.n.roxha.deliciasdamamae.model.BolosModel;
import com.marcel.a.n.roxha.deliciasdamamae.model.ModeloBolosAdicionadosVitrineQuandoVender;
import com.marcel.a.n.roxha.deliciasdamamae.model.ModeloControleRelatorioMensal;
import com.marcel.a.n.roxha.deliciasdamamae.model.ModeloMontanteDiario;
import com.marcel.a.n.roxha.deliciasdamamae.model.ModeloMontanteMensalLoja;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CaixaLojaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */


public class CaixaLojaFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    //ALERT CARREGANDO AS INFORMAÇÕES DO CAIXA DIARIO DO USUARIO

    //private ProgressDialog progressDialogCarregandoAsInformacoesDoCaixaDiario;
    public AlertDialog progressDialogCarregandoAsInformacoesDoCaixaDiario;
    private RecyclerView recyclerView_itens_vitrine;


    //CARREGAR AS INFORMAÇÕES DO USUARIO COMO TOKEN.


    //COMPONENTES DE TELA

    private String valorQueOCaixaIniciouODia = "0";
    private String totalDeVendasNoCaixaHoje = "0";
    private String totalDeVendasNoIfood = "0";
    private String totalDeVendasEmGeral = "0";
    private String totalDeVendasEmDinheiro = "0";
    private String totalDeSaidaCaixaHoje = "0";
    private String totalDeVendasNoCreditoHoje = "0";
    private String totalDeVendasNoDebitoNoCaixHoje = "0";
    private String informativoDessaAba = "NA ABA CAIXA, VOCÊ GERENCIA AS ATIVIDADES DIARIA DA LOJINHA: \n*-INICIE O DIA PARA INICIAR O PROCESSO DE CONTABILIZAÇÃO DIÁRIA E VISUALIZAÇÕES FUTURAS";


    //Texto
    private TextView textoDataDaUltimaAtualizacaoDoCaixaApresentandoParaOUsuario;
    private TextView textoOCaixaIniciouODiaComOValor;
    private TextView textoTotalNoCaixaHoje;
    private TextView textoTotalDeEntradaEmDinheiroNoCaixaHoje;
    private TextView textoTotalDeSaidaNoCaixaHoje;
    private TextView textoTotalDeVendasNoCreditoHoje;
    private TextView textoTotalDeVendasNoDebitoNoCaixaHoje;
    private TextView textoInformativoSobreEssaAbaCaixa;
    private TextView textoTotalDeVendasNoIfoofHoje;
    private TextView textoTotalDeVendasEmGeral;


    //TextoInfo
    private TextView infoTextoDataDaUltimaAtualizacaoDoCaixaApresentandoParaOUsuario;
    private TextView infoTextoOCaixaIniciouODiaComOValor;
    private TextView infoTextoTotalNoCaixaHoje;
    private TextView infoTextoTotalDeEntradaEmDinheiroNoCaixaHoje;
    private TextView infoTextoTotalDeSaidaNoCaixaHoje;
    private TextView infoTextoTotalDeVendasNoCreditoHoje;
    private TextView infoTextoTotalDeVendasNoDebitoNoCaixaHoje;
    private TextView infoTextoTotalDeVendasNoIfoofHoje;
    private TextView infoTextoTotalDeVendasEmGeral;


    //Botões
    private Button botaoParaIniciarOdia;
    private Button botaoParaIniciarOMes;
    private Button botaoOpaVendi;
    private Button botaoVitrineLoja;
    private Button botaoVendasFeitas;
    private Button botaoFinalizarDia;

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

    private AdapterModeloBolosAdicionadosVitrineQuandoVender adapter;

    private CollectionReference refBolosExpostosVitrine = firebaseFirestore.collection(COLLECTION_BOLOS_EXPOSTOS_VITRINE);

    LayoutInflater inflaterLayout;
    //CLASSES


    public CaixaLojaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Caixa.
     */
    // TODO: Rename and change types and number of parameters
    public static CaixaLojaFragment newInstance(String param1, String param2) {
        CaixaLojaFragment fragment = new CaixaLojaFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View viewCaixaFragment = inflater.inflate(R.layout.fragment_caixa, container, false);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        inflaterLayout = getActivity().getLayoutInflater();
        builder.setView(inflaterLayout.inflate(R.layout.progress_dialog_carregando_informacoes_caixa_diario, null));
        builder.setCancelable(true);

        this.progressDialogCarregandoAsInformacoesDoCaixaDiario = builder.create();
        this.progressDialogCarregandoAsInformacoesDoCaixaDiario.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        this.progressDialogCarregandoAsInformacoesDoCaixaDiario.show();

        keyUserRecuperado = auth.getCurrentUser().toString();

        // Cria um objeto Calendar com a data/hora atual

        diaAtual = simpleDateFormatCollectionReferenciaAtualModeloCaixaDiario.format(dataHoje);
        anoAtual = simpleDateFormatCollectionReferenciaAnoAtual.format(dataHoje);

        //Carregando os componentes de tela em visualização em GONE

        textoDataDaUltimaAtualizacaoDoCaixaApresentandoParaOUsuario = viewCaixaFragment.findViewById(R.id.texto_ultima_atualizacao_caixa_loja);
        textoOCaixaIniciouODiaComOValor = viewCaixaFragment.findViewById(R.id.texto_total_que_o_caixa_iniciou_o_dia_id);
        textoTotalNoCaixaHoje = viewCaixaFragment.findViewById(R.id.texto_total_no_caixa_id);
        textoTotalDeEntradaEmDinheiroNoCaixaHoje = viewCaixaFragment.findViewById(R.id.texto_entrada_diaria_no_caixa_id);
        textoTotalDeSaidaNoCaixaHoje = viewCaixaFragment.findViewById(R.id.texto_saida_diaria_no_caixa_id);
        textoTotalDeVendasNoCreditoHoje = viewCaixaFragment.findViewById(R.id.texto_entrada_no_credito_hoje_no_caixa_id);
        textoTotalDeVendasNoDebitoNoCaixaHoje = viewCaixaFragment.findViewById(R.id.texto_entrada_no_debito_hoje_no_caixa_id);
        textoInformativoSobreEssaAbaCaixa = viewCaixaFragment.findViewById(R.id.texto_informativo_sobre_aba_caixa_id);
        textoTotalDeVendasNoIfoofHoje = viewCaixaFragment.findViewById(R.id.texto_venda_no_ifood_hoje_id);
        textoTotalDeVendasEmGeral = viewCaixaFragment.findViewById(R.id.texto_total_de_vendas_em_geral_hoje_id);


        infoTextoDataDaUltimaAtualizacaoDoCaixaApresentandoParaOUsuario = viewCaixaFragment.findViewById(R.id.textoInfoDataUltimaAtualizacaoFragmentCaixa);
        infoTextoOCaixaIniciouODiaComOValor = viewCaixaFragment.findViewById(R.id.textViewcaixa95);
        infoTextoTotalNoCaixaHoje = viewCaixaFragment.findViewById(R.id.textViewcaixa90);
        infoTextoTotalDeEntradaEmDinheiroNoCaixaHoje = viewCaixaFragment.findViewById(R.id.textViewcaixa91);
        infoTextoTotalDeSaidaNoCaixaHoje = viewCaixaFragment.findViewById(R.id.textViewcaixa92);
        infoTextoTotalDeVendasNoCreditoHoje = viewCaixaFragment.findViewById(R.id.textViewcaixa97);
        infoTextoTotalDeVendasNoDebitoNoCaixaHoje = viewCaixaFragment.findViewById(R.id.textViewcaixa98);
        infoTextoTotalDeVendasNoIfoofHoje = viewCaixaFragment.findViewById(R.id.textViewcaixa99);
        infoTextoTotalDeVendasEmGeral = viewCaixaFragment.findViewById(R.id.textViewcaixa100);


        botaoParaIniciarOdia = viewCaixaFragment.findViewById(R.id.botao_iniciar_o_dia_boleria_id);
        botaoParaIniciarOMes = viewCaixaFragment.findViewById(R.id.botao_iniciar_o_mes_boleria_id);

        botaoOpaVendi = viewCaixaFragment.findViewById(R.id.botao_vendi_alguma_coisa_id);
        botaoVitrineLoja = viewCaixaFragment.findViewById(R.id.botao_vitrine_loja_id);
        botaoVendasFeitas = viewCaixaFragment.findViewById(R.id.botao_ver_vendas_id);
        botaoFinalizarDia = viewCaixaFragment.findViewById(R.id.botao_finalizar_dia_id);
        carregarComponentesDeTelaParaNaoVisualizar();


        hoje = new Date();
        hojeString = simpleDateFormat.format(hoje.getTime());
        textoDataDaUltimaAtualizacaoDoCaixaApresentandoParaOUsuario.setText(hojeString);
        numMesReferencia = 1 + hoje.getMonth();


        //Montar as datas referencia atual
        diaAtual = simpleDateFormatCollectionReferenciaAtualModeloCaixaDiario.format(dataHoje);
        anoAtual = simpleDateFormatCollectionReferenciaAnoAtual.format(dataHoje);
        mesAtual = simpleDateFormatCollectionReferenciaAtual.format(dataHoje);
        dataReferenciaCaixaDiario = simpleDateFormatCollectionReferenciaAtualModeloCaixaDiario.format(dataHoje);
        mesReferenciaDesseMontante = mesAtual + "_" + anoAtual;
        nomeCompletoColletionMontanteMensal = COLLECTION_MONTANTE_DESSE_MES + "_" + mesReferenciaDesseMontante;
        nomeCompletoColletionMontanteDiario = COLLECTION_CAIXA_DIARIO + "_" + mesReferenciaDesseMontante;
        this.progressDialogCarregandoAsInformacoesDoCaixaDiario.show();
        verificaSeJaTemCaixaDiarioCriado(this.progressDialogCarregandoAsInformacoesDoCaixaDiario, nomeCompletoColletionMontanteDiario);


        botaoParaIniciarOdia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialogCarregandoAsInformacoesDoCaixaDiario.show();
                progressDialogCarregandoAsInformacoesDoCaixaDiario.setCancelable(false);
                iniciarCaixaDiario(progressDialogCarregandoAsInformacoesDoCaixaDiario);

            }
        });

        botaoParaIniciarOMes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               /* progressDialogCarregandoAsInformacoesDoCaixaDiario.show();
                progressDialogCarregandoAsInformacoesDoCaixaDiario.setCancelable(false);*/
                iniciarMontanteMensal(progressDialogCarregandoAsInformacoesDoCaixaDiario);

            }

        });

        botaoOpaVendi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialogCarregandoAsInformacoesDoCaixaDiario.show();
                progressDialogCarregandoAsInformacoesDoCaixaDiario.setCancelable(false);
                verificaSePrecisaCriarAlgumMontanteAoVender(progressDialogCarregandoAsInformacoesDoCaixaDiario);
            }
        });

        botaoVitrineLoja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AdicionarBoloVitrineActivity2.class);
                startActivity(intent);
            }
        });

        botaoVendasFeitas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialogCarregandoAsInformacoesDoCaixaDiario.show();
                progressDialogCarregandoAsInformacoesDoCaixaDiario.setCancelable(false);
                verificaSeJaTemCaixaDiarioCriadoParaVisualizarRelatorioDiario(progressDialogCarregandoAsInformacoesDoCaixaDiario, nomeCompletoColletionMontanteDiario);

            }
        });


        botaoFinalizarDia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialogCarregandoAsInformacoesDoCaixaDiario.show();
                progressDialogCarregandoAsInformacoesDoCaixaDiario.setCancelable(false);
                finalizarDia(idRecuperadoCaixaDiario);
            }
        });


        progressDialogCarregandoAsInformacoesDoCaixaDiario.dismiss();
        return viewCaixaFragment;


    }

    private void verificaSeJaTemCaixaDiarioCriadoParaVisualizarRelatorioDiario(AlertDialog progressDialogCarregandoAsInformacoesDoCaixaDiario, String nomeCollectionCaixaDiario) {

        firebaseFirestore.collection(nomeCollectionCaixaDiario).whereEqualTo("dataReferenciaMontanteDiarioDesseDia", diaAtual).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                List<DocumentSnapshot> snapshotList = queryDocumentSnapshots.getDocuments();

                if (snapshotList.size() > 0) {
                    //Significa que existe o documento com a data de hoje então já tem um caixa diario referente a esse dia
                    for (DocumentSnapshot listaCaixasDiariosCriados : snapshotList) {

                        firebaseFirestore.collection(nomeCollectionCaixaDiario).document(listaCaixasDiariosCriados.getId()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {

                                ModeloMontanteDiario modeloMontanteDiarioRecuperadoJaCriado = documentSnapshot.toObject(ModeloMontanteDiario.class);
                                idRecuperadoCaixaDiario = modeloMontanteDiarioRecuperadoJaCriado.getIdReferenciaMontanteDiarioDesseDia();
                                progressDialogCarregandoAsInformacoesDoCaixaDiario.dismiss();
                                Intent intent = new Intent(getContext(), BolosVendidosActivity2.class);
                                intent.putExtra("idMontanteDiario", idRecuperadoCaixaDiario);
                                startActivity(intent);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });
                    }

                } else {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                    alertDialog.setTitle("AÇÃO NECESSÁRIA");
                    alertDialog.setMessage("Atenção, para acessar essa parte é necessário ter adicionado ao menos uma venda no controle");
                    alertDialog.setNeutralButton("ENTENDI", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(getContext(), "Ação cancelada", Toast.LENGTH_SHORT).show();
                        }
                    });
                    alertDialog.show();
                    alertDialog.create();
                    progressDialogCarregandoAsInformacoesDoCaixaDiario.dismiss();


                }


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

        carregarComponentesDeTelaParaVizualizar();

    }

    private void alertaOpaVendi() {
        AlertDialog.Builder alertaMostrarProdutosDaVetrinePoisFoiVendidoAlgo = new AlertDialog.Builder(getContext());
        alertaMostrarProdutosDaVetrinePoisFoiVendidoAlgo.setTitle("OPA VENDI");

        alertaMostrarProdutosDaVetrinePoisFoiVendidoAlgo.setView(inflaterLayout.inflate(R.layout.alerta_opa_vendi_alguma_coisa, null));
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.alerta_opa_vendi_alguma_coisa, null);
        alertaMostrarProdutosDaVetrinePoisFoiVendidoAlgo.setView(dialogView);


        recyclerView_itens_vitrine = dialogView.findViewById(R.id.recyclerView_opa_vendi_alguma_coisa_id);
        Query query = refBolosExpostosVitrine.orderBy("nomeBoloCadastrado", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<ModeloBolosAdicionadosVitrineQuandoVender> options = new FirestoreRecyclerOptions.Builder<ModeloBolosAdicionadosVitrineQuandoVender>().setQuery(query, ModeloBolosAdicionadosVitrineQuandoVender.class).build();

//        expostoAdapter = new BolosAdicionadosVitrineParaExibirQuandoVenderAdapter(options, getContext());
        adapter = new AdapterModeloBolosAdicionadosVitrineQuandoVender(options, getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        recyclerView_itens_vitrine.setHasFixedSize(true);
        recyclerView_itens_vitrine.setLayoutManager(layoutManager);
        recyclerView_itens_vitrine.setAdapter(adapter);

        adapter.setOnItemClickListerner(new AdapterModeloBolosAdicionadosVitrineQuandoVender.OnItemClickLisener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {

                BolosModel boloRecuperadoParaProcessamentoDaVenda = documentSnapshot.toObject(BolosModel.class);
                String idDoProdutoCadastrado = boloRecuperadoParaProcessamentoDaVenda.getIdReferenciaReceitaCadastrada();
                String idRecuperadoDoBoloExpostoVitrine = boloRecuperadoParaProcessamentoDaVenda.getIdBoloCadastrado();
                String nomeRecuperadoExpostoVitrine = boloRecuperadoParaProcessamentoDaVenda.getNomeBoloCadastrado();
                String custoDoProdutoRecuperadoExpostoVitrine = boloRecuperadoParaProcessamentoDaVenda.getCustoTotalDaReceitaDoBolo();
                Intent intentAdicionarProdutoComoVendidoNoSistema = new Intent(getContext(), AdicionarProdutoComoVendidoNoSistema.class);
                intentAdicionarProdutoComoVendidoNoSistema.putExtra("itemKey", idRecuperadoDoBoloExpostoVitrine);
                intentAdicionarProdutoComoVendidoNoSistema.putExtra("itemKeyMontanteMensal", idRecuperadoMontanteMensal);
                intentAdicionarProdutoComoVendidoNoSistema.putExtra("nomeRecuperadoExpostoVitrine", nomeRecuperadoExpostoVitrine);
                intentAdicionarProdutoComoVendidoNoSistema.putExtra("itemKeyMontanteDiario", idRecuperadoCaixaDiario);
                intentAdicionarProdutoComoVendidoNoSistema.putExtra("itemKeyIdProdutoCadastrado", idDoProdutoCadastrado);
                intentAdicionarProdutoComoVendidoNoSistema.putExtra("custoDoProdutoRecuperadoExpostoVitrine", custoDoProdutoRecuperadoExpostoVitrine);

                startActivity(intentAdicionarProdutoComoVendidoNoSistema);
            }
        });
//        expostoAdapter.startListening();
//        expostoAdapter.setOnItemClickListerner(new BolosAdicionadosVitrineParaExibirQuandoVenderAdapter.OnItemClickLisener() {
//            @Override
//            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
//
//                BolosModel boloRecuperadoParaProcessamentoDaVenda = documentSnapshot.toObject(BolosModel.class);
//                String idDoProdutoCadastrado = boloRecuperadoParaProcessamentoDaVenda.getIdReferenciaReceitaCadastrada();
//                String idRecuperadoDoBoloExpostoVitrine = boloRecuperadoParaProcessamentoDaVenda.getIdBoloCadastrado();
//                String nomeRecuperadoExpostoVitrine = boloRecuperadoParaProcessamentoDaVenda.getNomeBoloCadastrado();
//                String custoDoProdutoRecuperadoExpostoVitrine = boloRecuperadoParaProcessamentoDaVenda.getCustoTotalDaReceitaDoBolo();
//                Intent intentAdicionarProdutoComoVendidoNoSistema = new Intent(getContext(), AdicionarProdutoComoVendidoNoSistema.class);
//                intentAdicionarProdutoComoVendidoNoSistema.putExtra("itemKey", idRecuperadoDoBoloExpostoVitrine);
//                intentAdicionarProdutoComoVendidoNoSistema.putExtra("itemKeyMontanteMensal", idRecuperadoMontanteMensal);
//                intentAdicionarProdutoComoVendidoNoSistema.putExtra("nomeRecuperadoExpostoVitrine", nomeRecuperadoExpostoVitrine);
//                intentAdicionarProdutoComoVendidoNoSistema.putExtra("itemKeyMontanteDiario", idRecuperadoCaixaDiario);
//                intentAdicionarProdutoComoVendidoNoSistema.putExtra("itemKeyIdProdutoCadastrado", idDoProdutoCadastrado);
//                intentAdicionarProdutoComoVendidoNoSistema.putExtra("custoDoProdutoRecuperadoExpostoVitrine", custoDoProdutoRecuperadoExpostoVitrine);
//
//                startActivity(intentAdicionarProdutoComoVendidoNoSistema);
//
//            }
//        });
        adapter.startListening();
        alertaMostrarProdutosDaVetrinePoisFoiVendidoAlgo.create();
        alertaMostrarProdutosDaVetrinePoisFoiVendidoAlgo.show();

    }

    private void alertaNecessarioCriarOMontanteAoEfetuarUmaVenda(String tipoMensagem) {


        if (tipoMensagem.equals("MENSAL")) {
            AlertDialog.Builder alertaENecessarioCriarOMontante = new AlertDialog.Builder(getActivity());
            alertaENecessarioCriarOMontante.setTitle("NECESSÁRIO INICIAR O MÊS/DIA");
            alertaENecessarioCriarOMontante.setMessage("Atenção, para processar essa venda é necessário iniciar o mês e o dia, para isso clique em iniciar dia e o valor digitado será o valor inicia dos montante mensal e também do caixa atual");

            alertaENecessarioCriarOMontante.setCancelable(false);
            alertaENecessarioCriarOMontante.setNeutralButton("ENTENDI", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            alertaENecessarioCriarOMontante.create();
            alertaENecessarioCriarOMontante.show();

        } else if (tipoMensagem.equals("DIARIO")) {
            AlertDialog.Builder alertaENecessarioCriarOMontante = new AlertDialog.Builder(getActivity());
            alertaENecessarioCriarOMontante.setTitle("NECESSÁRIO INICIAR O DIA");
            alertaENecessarioCriarOMontante.setMessage("Atenção, para processar essa venda é necessário iniciar o dia, para isso, clique em iniciar dia");

            alertaENecessarioCriarOMontante.setCancelable(false);
            alertaENecessarioCriarOMontante.setNeutralButton("ENTENDI", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            alertaENecessarioCriarOMontante.create();
            alertaENecessarioCriarOMontante.show();
        }


    }


    private void verificaSePrecisaCriarAlgumMontanteAoVender(AlertDialog progressDialogCarregandoAsInformacoesDoCaixaDiario) {

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
                                    alertaOpaVendi();
                                } else {
                                    alertaNecessarioCriarOMontanteAoEfetuarUmaVenda("DIARIO");
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
                        alertaNecessarioCriarOMontanteAoEfetuarUmaVenda("MENSAL");
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

    private void iniciarCaixaDiario(AlertDialog progressDialogCarregandoAsInformacoesDoCaixaDiario) {
        //Verificar se há montante mensal criado primeiro, se não houver criado é necessário criar um separado do caixa diário
        firebaseFirestore.collection(nomeCompletoColletionMontanteMensal).whereEqualTo("mesReferenciaDesseMontante", mesReferenciaDesseMontante).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                List<DocumentSnapshot> listaDeMontanteParaVerificar = queryDocumentSnapshots.getDocuments();

                if (listaDeMontanteParaVerificar.size() > 0) {

                    // Se cair nessa condição então eu já tenho um montante mensal criado então preciso recuperar seu id
                    for (DocumentSnapshot listaRecuperada : listaDeMontanteParaVerificar) {
                        String idRecuperado = listaRecuperada.getId();
                        firebaseFirestore.collection(nomeCompletoColletionMontanteDiario).whereEqualTo("dataReferenciaMontanteDiarioDesseDia", dataReferenciaCaixaDiario)
                                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                    @Override
                                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                                        List<DocumentSnapshot> listaRecuperada = queryDocumentSnapshots.getDocuments();

                                        if (listaRecuperada.size() > 0) {
                                            progressDialogCarregandoAsInformacoesDoCaixaDiario.dismiss();
                                            String titulo = "AÇÃO NÃO PERMITIDA";
                                            String corpodaMensagem = "Não é possível criar um montante diário do mesmo dia";
                                            criarAlertaPrecisaCriarMontanteMensalOuDiario(titulo, corpodaMensagem);
                                        } else {
                                            progressDialogCarregandoAsInformacoesDoCaixaDiario.dismiss();
                                            CollectionReference collectionReferenceMontantesDiarios = firebaseFirestore.collection(nomeCompletoColletionMontanteDiario);
                                            Query query = collectionReferenceMontantesDiarios.orderBy("dataReferenciaMontanteDiarioDesseDia", Query.Direction.DESCENDING).limit(1);

                                            query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                                    if (task.isSuccessful()) {
                                                        if (!task.getResult().isEmpty()) {
                                                            progressDialogCarregandoAsInformacoesDoCaixaDiario.dismiss();
                                                            DocumentSnapshot document = task.getResult().getDocuments().get(0);
                                                            String valorQueOCaixaFinalizou = document.get("valorQueOCaixaFinalizou").toString();

                                                            criandoAlertaDoMontanteQuandoPrecisaCriarApenasDeMontanteDiario("DIARIO-COM-DIA-ANTERIOR", valorQueOCaixaFinalizou, idRecuperado);

                                                        } else {
                                                            criandoAlertaDoMontanteQuandoPrecisaCriarApenasDeMontanteDiario("DIARIO", null, idRecuperado);
                                                        }


                                                    } else {
                                                        Toast.makeText(getContext(), "Verifique a conexão e tente novamente mais tarde!", Toast.LENGTH_SHORT).show();
                                                        Log.i("taskError: ", task.getException().getMessage().toString());
                                                    }

                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(getContext(), "Verifique a conexão e tente novamente mais tarde!", Toast.LENGTH_SHORT).show();
                                                    Log.i("Erro no inicia caixa diario criado", e.getMessage());
                                                }
                                            });
                                        }
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                    }
                                });
                    }
                } else {
                    progressDialogCarregandoAsInformacoesDoCaixaDiario.dismiss();
                    criandoAlertaDoMontanteQuandoPrecisaCriarApenasDeMontanteDiario("MENSAL", null, null);

                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialogCarregandoAsInformacoesDoCaixaDiario.dismiss();
                Toast.makeText(getContext(), "Verifique a conexão e tente novamente mais tarde!", Toast.LENGTH_SHORT).show();
                Log.i("Erro no verifica se tem montante criado", e.getMessage());

            }
        });

    }

    private void iniciarMontanteMensal(AlertDialog progressDialogCarregandoAsInformacoesDoCaixaDiario) {
        //Verificar se há montante mensal criado primeiro, se não houver criado é necessário criar um separado do caixa diário
        firebaseFirestore.collection(nomeCompletoColletionMontanteMensal).whereEqualTo("mesReferenciaDesseMontante", mesReferenciaDesseMontante).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                List<DocumentSnapshot> listaDeMontanteParaVerificar = queryDocumentSnapshots.getDocuments();

                if (listaDeMontanteParaVerificar.size() > 0) {
                    // Se cair nessa condição então eu já tenho um montante mensal criado então Não é necessário criar outro
                    String mensagemQueApareceNoTituloDoAlerta = "AÇÃO NÃO PERMITIDA!";
                    String mensagemDeExemplo = "Já foi criado um montante referente a esse mês então não é possível ter dois montantes do mesmo mês";
                    criarAlertaPrecisaCriarMontanteMensalOuDiario(mensagemQueApareceNoTituloDoAlerta, mensagemDeExemplo);

                } else {
                    criandoAlertaDoMontanteQuandoPrecisaCriarApenasDeMontanteDiario("MENSAL", null, null);
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(getContext(), "Verifique a conexão e tente novamente mais tarde!", Toast.LENGTH_SHORT).show();
                Log.i("Erro no verifica se tem montante criado", e.getMessage());

            }
        });

    }

    private void insertCaixaDiarioCreated(String dataAtual, String valorDigitadoParaIniciarOMontateDiaio, String idMontanteMensal) {
        progressDialogCarregandoAsInformacoesDoCaixaDiario.show();
        ModeloMontanteDiario modeloMontanteDiarioIniciado = new ModeloMontanteDiario();
        ModeloMontanteDiarioDAO modeloMontanteDiarioDAOSendoIniciadoDesseMes = new ModeloMontanteDiarioDAO(getContext());
        modeloMontanteDiarioIniciado.setIdReferenciaMontanteDiarioDesseDia("N/D");
        modeloMontanteDiarioIniciado.setIdReferenciaMontanteMensal(idMontanteMensal);
        modeloMontanteDiarioIniciado.setDataReferenciaMontanteDiarioDesseDia(dataAtual);
        modeloMontanteDiarioIniciado.setValorQueOCaixaIniciouODia(valorDigitadoParaIniciarOMontateDiaio);
        modeloMontanteDiarioIniciado.setValorQueOCaixaFinalizou("0");
        modeloMontanteDiarioIniciado.setValorTotalDeVendasNaLojaDesseDia("0");
        modeloMontanteDiarioIniciado.setValorTotalDeVendasNoIfoodDesseDia("0");
        modeloMontanteDiarioIniciado.setValorTotalDeVendasEmGeralDesseDia("0");
        modeloMontanteDiarioIniciado.setValorTotalDeVendasNoDinheiroDesseDia("0");
        modeloMontanteDiarioIniciado.setValorTotalDeVendasNoCreditoDesseDia("0");
        modeloMontanteDiarioIniciado.setValorTotalDeVendasNoDebitoDesseDia("0");
        modeloMontanteDiarioIniciado.setValorTotalDeTrocoDesseDia("0");
        modeloMontanteDiarioIniciado.setQuantidadeDeVendasFeitasDesseDia(0);
        modeloMontanteDiarioDAOSendoIniciadoDesseMes.modeloMontanteDiarioIniciandoODia(modeloMontanteDiarioIniciado);
        verificaSeJaTemCaixaDiarioCriado(progressDialogCarregandoAsInformacoesDoCaixaDiario, nomeCompletoColletionMontanteDiario);
    }

    private void insertCaixaMensalCreated(String valorDigitadoParaIniciarOMontateDiaio, String mesReferenciaMontanteMensal) {

        ModeloMontanteMensalDAO modeloMontanteDiarioDAOSendoIniciadoDesseMes = new ModeloMontanteMensalDAO(getContext());
        ModeloMontanteMensalLoja modeloMontanteMensalLojaIniciandoMes = new ModeloMontanteMensalLoja();

        modeloMontanteMensalLojaIniciandoMes.setIdMontante("N/D");
        modeloMontanteMensalLojaIniciandoMes.setMesReferenciaDesseMontante(mesReferenciaMontanteMensal);
        modeloMontanteMensalLojaIniciandoMes.setValorQueOMontanteIniciouPositivo(valorDigitadoParaIniciarOMontateDiaio);
        modeloMontanteMensalLojaIniciandoMes.setValorTotalVendasBoleriaMensal("0");
        modeloMontanteMensalLojaIniciandoMes.setValorTotalVendasIfoodMensal("0");
        modeloMontanteMensalLojaIniciandoMes.setValorTotalVendasEmGeralMensal("0");
        modeloMontanteMensalLojaIniciandoMes.setQuantoDinheiroEntrouEsseMes("0");
        modeloMontanteMensalLojaIniciandoMes.setQuantoDinheiroSaiuEsseMes("0");
        modeloMontanteMensalLojaIniciandoMes.setValorQueOMontanteIniciouNegativo("0");
        modeloMontanteMensalLojaIniciandoMes.setValorTotalDeVendasNoDinheiroDesseMes("0");
        modeloMontanteMensalLojaIniciandoMes.setValorTotalDeVendasNoCreditoDesseMes("0");
        modeloMontanteMensalLojaIniciandoMes.setValorTotalDeVendasNoDebitoDesseMes("0");
        modeloMontanteMensalLojaIniciandoMes.setQuantidadeDeVendasDesseMes(0);
        modeloMontanteDiarioDAOSendoIniciadoDesseMes.modeloMontanteIniciarMesEDiario(modeloMontanteMensalLojaIniciandoMes, diaAtual);
    }

    private void criandoAlertaDoMontanteQuandoPrecisaCriarApenasDeMontanteDiario(String tipoDeAlerta, String valorDoDiaAnterior, String idReferenciaMontanteMensal) {

        System.out.println("idReferenciaMontanteMensal " + idReferenciaMontanteMensal);
        String tipoDeAlertaRecebido = tipoDeAlerta;
        if (tipoDeAlertaRecebido.equals("DIARIO")) {
            String informativoDoValorQueOCaixaDiarioIraIniciar = "DIGITE ABAIXO O VALOR QUE O CAIXA DE HOJE IRÁ INICIAR";
            String informativoExDeValoresValidos = "DIGITE UM VALOR VALIDO E ACIMA DE 0 (Zero) EX: 100,00 OU 100.00";
            AlertDialog.Builder alertaCriarMontanteDiario = new AlertDialog.Builder(getActivity());
            LayoutInflater inflaterLayout = getActivity().getLayoutInflater();
            alertaCriarMontanteDiario.setView(inflaterLayout.inflate(R.layout.alerta_iniciando_o_dia_quando_tem_caixa_diario_do_dia_anterior, null));
            View view = getLayoutInflater().inflate(R.layout.alerta_iniciando_o_dia_quando_tem_caixa_diario_do_dia_anterior, null);
            EditText valorDigitadoPeloUsuario = view.findViewById(R.id.input_caixa_iniciou_o_dia_com_calculo_do_dia_anterior);
            TextView textoInformativoInserindoValorNoCaixaDiario = view.findViewById(R.id.texto_informativo_iniciando_o_dia_quando_tem_dia_anterior);
            TextView textoInformativoExDeValoresValidos = view.findViewById(R.id.textoInformativoDigiteOValorEditadoId);

            textoInformativoInserindoValorNoCaixaDiario.setText(informativoDoValorQueOCaixaDiarioIraIniciar);
            textoInformativoExDeValoresValidos.setText(informativoExDeValoresValidos);
            alertaCriarMontanteDiario.setView(view);

            alertaCriarMontanteDiario.setPositiveButton("CONFIRMAR", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    String valorDigitado = valorDigitadoPeloUsuario.getText().toString().replace(",", ".");

                    if (!valorDigitado.equals("0") || valorDigitado != null || !valorDigitado.isEmpty()) {
                        System.out.println("dentro do if ----------");
                        System.out.println("valorDigitado ----------" + valorDigitado);
                        String valorDigitadoParaIniciarOMontateDesseMes = valorDigitado;
                        System.out.println("valorDigitadoParaIniciarOMontateDesseMes ----------" + valorDigitadoParaIniciarOMontateDesseMes);
                        insertCaixaDiarioCreated(diaAtual, valorDigitadoParaIniciarOMontateDesseMes, idReferenciaMontanteMensal);
                    } else {
                        System.out.println("dentro do else ----------");
                        Toast.makeText(getContext(), "ATENÇÃO O VALOR DIGITADO PRECISA SER MAIOR QUE 0 E UM VALOR VÁLIDO EX: 100,00 OU 100.00", Toast.LENGTH_SHORT).show();
                    }
                }
            }).setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(getContext(), "PROCESSO CANCELADOs", Toast.LENGTH_SHORT).show();
                }
            });
            alertaCriarMontanteDiario.create();
            alertaCriarMontanteDiario.show();

        } else if (tipoDeAlertaRecebido.equals("DIARIO-COM-DIA-ANTERIOR")) {
            String valorQueOCaixaFinalizou = valorDoDiaAnterior;
            String informativoDoValorDoDiaAnterior = "DEVIDO AOS RESULTADOS DE ONTEM O VALOR QUE DEVERIA SER INICIADO O CAIXA É R$: " + valorQueOCaixaFinalizou + " PARA INICIAR COM ESSE VALOR CLIQUE EM CONFIRMAR OU ENTÃO DIGITE O VALOR QUE O CAIXA ESTÁ INICIANDO O DIA";


            AlertDialog.Builder alertaCriarMontanteDiarioComDiaAnterior = new AlertDialog.Builder(getActivity());
            LayoutInflater inflaterLayout = getActivity().getLayoutInflater();
            alertaCriarMontanteDiarioComDiaAnterior.setView(inflaterLayout.inflate(R.layout.alerta_iniciando_o_dia_quando_tem_caixa_diario_do_dia_anterior, null));
            View view = getLayoutInflater().inflate(R.layout.alerta_iniciando_o_dia_quando_tem_caixa_diario_do_dia_anterior, null);
            EditText valorDigitadoPeloUsuario = view.findViewById(R.id.input_caixa_iniciou_o_dia_com_calculo_do_dia_anterior);
            TextView textoInformativoSobreOValorJaCalculado = view.findViewById(R.id.texto_informativo_iniciando_o_dia_quando_tem_dia_anterior);
            valorDigitadoPeloUsuario.setText(valorQueOCaixaFinalizou);
            textoInformativoSobreOValorJaCalculado.setText(informativoDoValorDoDiaAnterior);
            alertaCriarMontanteDiarioComDiaAnterior.setView(view);

            alertaCriarMontanteDiarioComDiaAnterior.setPositiveButton("CONFIRMAR", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    String valorDigitado = valorDigitadoPeloUsuario.getText().toString().replace(",", ".");

                    if (!valorDigitado.isEmpty() || !valorDigitado.equals("")) {
                        String valorDigitadoParaIniciarOMontateDesseMes = valorDigitado;
                        insertCaixaDiarioCreated(diaAtual, valorDigitadoParaIniciarOMontateDesseMes, idReferenciaMontanteMensal);

                    } else if (!valorDigitado.equals("0") || valorDigitado != null) {

                        String valorDigitadoParaIniciarOMontateDesseMes = valorDigitado;
                        insertCaixaDiarioCreated(diaAtual, valorDigitadoParaIniciarOMontateDesseMes, idReferenciaMontanteMensal);
                    } else {
                        Toast.makeText(getContext(), "ATENÇÃO O VALOR DIGITADO PRECISA SER MAIOR QUE 0 (Zero) E UM VALOR VÁLIDO EX: 100,00 OU 100.00", Toast.LENGTH_SHORT).show();
                    }
                }
            }).setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //Toast.makeText(getContext(), "VALOR DIGITADO FOI:  " +valorDigitadoPeloUsuario.getText().toString() , Toast.LENGTH_SHORT).show();
                    Toast.makeText(getContext(), "PROCESSO CANCELADOs", Toast.LENGTH_SHORT).show();
                }
            });
            alertaCriarMontanteDiarioComDiaAnterior.create();
            alertaCriarMontanteDiarioComDiaAnterior.show();

        } else {
            String informativoDoValorQueOCaixaDiarioIraIniciar = "DIGITE ABAIXO O VALOR QUE O MONTANTE MENSAL IRÁ INICIAR E TAMBÉM O DIA DE HOJE";
            String informativoExDeValoresValidos = "DIGITE UM VALOR VALIDO E ACIMA DE 0 (Zero) EX: 100,00 OU 100.00";
            AlertDialog.Builder alertaCriarMontanteDiarioEMensal = new AlertDialog.Builder(getActivity());
            LayoutInflater inflaterLayout = getActivity().getLayoutInflater();
            alertaCriarMontanteDiarioEMensal.setView(inflaterLayout.inflate(R.layout.alerta_iniciando_o_dia_quando_tem_caixa_diario_do_dia_anterior, null));
            View view = getLayoutInflater().inflate(R.layout.alerta_iniciando_o_dia_quando_tem_caixa_diario_do_dia_anterior, null);
            EditText valorDigitadoPeloUsuario = view.findViewById(R.id.input_caixa_iniciou_o_dia_com_calculo_do_dia_anterior);
            TextView textoInformativoInserirValorMontanteDiarioEMensal = view.findViewById(R.id.texto_informativo_iniciando_o_dia_quando_tem_dia_anterior);
            TextView textoInformativoExDeValoresValidos = view.findViewById(R.id.textoInformativoDigiteOValorEditadoId);

            textoInformativoInserirValorMontanteDiarioEMensal.setText(informativoDoValorQueOCaixaDiarioIraIniciar);
            textoInformativoExDeValoresValidos.setText(informativoExDeValoresValidos);
            alertaCriarMontanteDiarioEMensal.setView(view);

            alertaCriarMontanteDiarioEMensal.setPositiveButton("CONFIRMAR", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    String valorDigitado = valorDigitadoPeloUsuario.getText().toString().replace(",", ".");

                    if (!valorDigitado.equals("0") || valorDigitado != null || !valorDigitado.isEmpty()) {
                        insertCaixaMensalCreated(valorDigitado, mesReferenciaDesseMontante);


                    } else {
                        Toast.makeText(getContext(), "ATENÇÃO O VALOR DIGITADO PRECISA SER MAIOR QUE 0 E UM VALOR VÁLIDO EX: 100,00 OU 100.00", Toast.LENGTH_SHORT).show();
                    }


                }
            }).setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //Toast.makeText(getContext(), "VALOR DIGITADO FOI:  " +valorDigitadoPeloUsuario.getText().toString() , Toast.LENGTH_SHORT).show();
                    Toast.makeText(getContext(), "PROCESSO CANCELADOs", Toast.LENGTH_SHORT).show();
                }
            });
            alertaCriarMontanteDiarioEMensal.create();
            alertaCriarMontanteDiarioEMensal.show();


        }


    }

    private void criarAlertaPrecisaCriarMontanteMensalOuDiario(String mensagemQueApareceNoTituloDoAlerta, String mensagemDeExemplo) {
        String informativoDoValorQueOCaixaDiarioIraIniciar = mensagemQueApareceNoTituloDoAlerta;
        String informativoExDeValoresValidos = mensagemDeExemplo;
        AlertDialog.Builder alertaPrecisaCriarAlgumMontante = new AlertDialog.Builder(getActivity());
        alertaPrecisaCriarAlgumMontante.setTitle(informativoDoValorQueOCaixaDiarioIraIniciar);
        alertaPrecisaCriarAlgumMontante.setMessage(informativoExDeValoresValidos);
        alertaPrecisaCriarAlgumMontante.setNeutralButton("ENTENDI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        alertaPrecisaCriarAlgumMontante.create();
        alertaPrecisaCriarAlgumMontante.show();


    }

    private void verificaSeJaTemCaixaDiarioCriado(AlertDialog progressDialogCarregandoAsInformacoesDoCaixaDiario, String nomeCollectionCaixaDiario) {

        firebaseFirestore.collection(nomeCollectionCaixaDiario).whereEqualTo("dataReferenciaMontanteDiarioDesseDia", diaAtual).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                List<DocumentSnapshot> snapshotList = queryDocumentSnapshots.getDocuments();

                if (snapshotList.size() > 0) {
                    //Significa que existe o documento com a data de hoje então já tem um caixa diario referente a esse dia
                    for (DocumentSnapshot listaCaixasDiariosCriados : snapshotList) {

                        firebaseFirestore.collection(nomeCollectionCaixaDiario).document(listaCaixasDiariosCriados.getId()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
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

                                botaoFinalizarDia.setVisibility(View.VISIBLE);
                                botaoVendasFeitas.setVisibility(View.VISIBLE);
                                botaoOpaVendi.setVisibility(View.VISIBLE);
                                botaoVitrineLoja.setVisibility(View.VISIBLE);
                                botaoParaIniciarOdia.setVisibility(View.VISIBLE);
                                textoInformativoSobreEssaAbaCaixa.setVisibility(View.VISIBLE);
                                textoTotalDeVendasNoDebitoNoCaixaHoje.setVisibility(View.VISIBLE);
                                textoTotalDeVendasNoCreditoHoje.setVisibility(View.VISIBLE);
                                textoTotalDeSaidaNoCaixaHoje.setVisibility(View.VISIBLE);
                                textoTotalDeEntradaEmDinheiroNoCaixaHoje.setVisibility(View.VISIBLE);
                                textoTotalNoCaixaHoje.setVisibility(View.VISIBLE);
                                textoOCaixaIniciouODiaComOValor.setVisibility(View.VISIBLE);
                                textoDataDaUltimaAtualizacaoDoCaixaApresentandoParaOUsuario.setVisibility(View.VISIBLE);

                                textoTotalDeVendasEmGeral.setVisibility(View.VISIBLE);
                                textoTotalDeVendasNoIfoofHoje.setVisibility(View.VISIBLE);


                                infoTextoDataDaUltimaAtualizacaoDoCaixaApresentandoParaOUsuario.setVisibility(View.VISIBLE);
                                infoTextoOCaixaIniciouODiaComOValor.setVisibility(View.VISIBLE);
                                infoTextoTotalNoCaixaHoje.setVisibility(View.VISIBLE);
                                infoTextoTotalDeEntradaEmDinheiroNoCaixaHoje.setVisibility(View.VISIBLE);
                                infoTextoTotalDeSaidaNoCaixaHoje.setVisibility(View.VISIBLE);
                                infoTextoTotalDeVendasNoCreditoHoje.setVisibility(View.VISIBLE);
                                infoTextoTotalDeVendasNoDebitoNoCaixaHoje.setVisibility(View.VISIBLE);

                                infoTextoTotalDeVendasEmGeral.setVisibility(View.VISIBLE);
                                infoTextoTotalDeVendasNoIfoofHoje.setVisibility(View.VISIBLE);


                                textoInformativoSobreEssaAbaCaixa.setText(informativoDessaAba);
                                textoOCaixaIniciouODiaComOValor.setText(valorQueOCaixaIniciouODia);
                                textoTotalDeVendasNoDebitoNoCaixaHoje.setText(totalDeVendasNoDebitoNoCaixHoje);
                                textoTotalDeEntradaEmDinheiroNoCaixaHoje.setText(totalDeVendasEmDinheiro);
                                textoTotalDeVendasNoCreditoHoje.setText(totalDeVendasNoCreditoHoje);
                                textoTotalDeSaidaNoCaixaHoje.setText(totalDeSaidaCaixaHoje);
                                textoTotalNoCaixaHoje.setText(totalDeVendasNoCaixaHoje);
                                textoTotalDeVendasEmGeral.setText(valorTotalDeVendasEmGeralDesseDia);
                                textoTotalDeVendasNoIfoofHoje.setText(valorTotalDeVendasNoIfoodDesseDia);
                                progressDialogCarregandoAsInformacoesDoCaixaDiario.dismiss();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });
                    }

                } else {
                    carregarComponentesDeTelaParaVizualizar();
                    progressDialogCarregandoAsInformacoesDoCaixaDiario.dismiss();


                }


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

        carregarComponentesDeTelaParaVizualizar();

    }

    public void carregarComponentesDeTelaParaVizualizar() {

        botaoFinalizarDia.setVisibility(View.VISIBLE);
        botaoVendasFeitas.setVisibility(View.VISIBLE);
        botaoOpaVendi.setVisibility(View.VISIBLE);
        botaoVitrineLoja.setVisibility(View.VISIBLE);
        botaoParaIniciarOdia.setVisibility(View.VISIBLE);
        botaoParaIniciarOMes.setVisibility(View.VISIBLE);
        textoInformativoSobreEssaAbaCaixa.setVisibility(View.VISIBLE);
        textoTotalDeVendasNoDebitoNoCaixaHoje.setVisibility(View.VISIBLE);
        textoTotalDeVendasNoCreditoHoje.setVisibility(View.VISIBLE);
        textoTotalDeSaidaNoCaixaHoje.setVisibility(View.VISIBLE);
        textoTotalDeEntradaEmDinheiroNoCaixaHoje.setVisibility(View.VISIBLE);
        textoTotalNoCaixaHoje.setVisibility(View.VISIBLE);
        textoOCaixaIniciouODiaComOValor.setVisibility(View.VISIBLE);
        textoDataDaUltimaAtualizacaoDoCaixaApresentandoParaOUsuario.setVisibility(View.VISIBLE);

        textoTotalDeVendasEmGeral.setVisibility(View.VISIBLE);
        textoTotalDeVendasNoIfoofHoje.setVisibility(View.VISIBLE);


        infoTextoDataDaUltimaAtualizacaoDoCaixaApresentandoParaOUsuario.setVisibility(View.VISIBLE);
        infoTextoOCaixaIniciouODiaComOValor.setVisibility(View.VISIBLE);
        infoTextoTotalNoCaixaHoje.setVisibility(View.VISIBLE);
        infoTextoTotalDeEntradaEmDinheiroNoCaixaHoje.setVisibility(View.VISIBLE);
        infoTextoTotalDeSaidaNoCaixaHoje.setVisibility(View.VISIBLE);
        infoTextoTotalDeVendasNoCreditoHoje.setVisibility(View.VISIBLE);
        infoTextoTotalDeVendasNoDebitoNoCaixaHoje.setVisibility(View.VISIBLE);

        infoTextoTotalDeVendasEmGeral.setVisibility(View.VISIBLE);
        infoTextoTotalDeVendasNoIfoofHoje.setVisibility(View.VISIBLE);


        textoInformativoSobreEssaAbaCaixa.setText(informativoDessaAba);
        textoOCaixaIniciouODiaComOValor.setText(valorQueOCaixaIniciouODia);
        textoTotalDeVendasNoDebitoNoCaixaHoje.setText(totalDeVendasNoDebitoNoCaixHoje);
        textoTotalDeEntradaEmDinheiroNoCaixaHoje.setText(totalDeVendasEmDinheiro);
        textoTotalDeVendasNoCreditoHoje.setText(totalDeVendasNoCreditoHoje);
        textoTotalDeSaidaNoCaixaHoje.setText(totalDeSaidaCaixaHoje);
        textoTotalNoCaixaHoje.setText(totalDeVendasNoCaixaHoje);
        textoTotalDeVendasEmGeral.setText(totalDeVendasEmGeral);
        textoTotalDeVendasNoIfoofHoje.setText(totalDeVendasNoIfood);


        this.progressDialogCarregandoAsInformacoesDoCaixaDiario.dismiss();

    }


    public void carregarComponentesDeTelaParaNaoVisualizar() {


        botaoFinalizarDia.setVisibility(View.GONE);
        botaoVendasFeitas.setVisibility(View.GONE);
        botaoOpaVendi.setVisibility(View.GONE);
        botaoVitrineLoja.setVisibility(View.GONE);
        botaoParaIniciarOdia.setVisibility(View.GONE);
        botaoParaIniciarOMes.setVisibility(View.GONE);
        textoInformativoSobreEssaAbaCaixa.setVisibility(View.GONE);
        textoTotalDeVendasNoDebitoNoCaixaHoje.setVisibility(View.GONE);
        textoTotalDeVendasNoCreditoHoje.setVisibility(View.GONE);
        textoTotalDeSaidaNoCaixaHoje.setVisibility(View.GONE);
        textoTotalDeEntradaEmDinheiroNoCaixaHoje.setVisibility(View.GONE);
        textoTotalNoCaixaHoje.setVisibility(View.GONE);
        textoOCaixaIniciouODiaComOValor.setVisibility(View.GONE);
        textoDataDaUltimaAtualizacaoDoCaixaApresentandoParaOUsuario.setVisibility(View.GONE);
        textoTotalDeVendasEmGeral.setVisibility(View.GONE);
        textoTotalDeVendasNoIfoofHoje.setVisibility(View.GONE);


        infoTextoDataDaUltimaAtualizacaoDoCaixaApresentandoParaOUsuario.setVisibility(View.GONE);
        infoTextoOCaixaIniciouODiaComOValor.setVisibility(View.GONE);
        infoTextoTotalNoCaixaHoje.setVisibility(View.GONE);
        infoTextoTotalDeEntradaEmDinheiroNoCaixaHoje.setVisibility(View.GONE);
        infoTextoTotalDeSaidaNoCaixaHoje.setVisibility(View.GONE);
        infoTextoTotalDeVendasNoCreditoHoje.setVisibility(View.GONE);
        infoTextoTotalDeVendasNoDebitoNoCaixaHoje.setVisibility(View.GONE);
        infoTextoTotalDeVendasEmGeral.setVisibility(View.GONE);
        infoTextoTotalDeVendasNoIfoofHoje.setVisibility(View.GONE);

    }

    private void finalizarDia(String idRecuperado) {

        System.out.println("finalizarDia   idRecuperado " + idRecuperado);
        firebaseFirestore.collection(nomeCompletoColletionMontanteDiario).document(idRecuperado).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                ModeloMontanteDiario modeloMontanteDiarioRecuperadoParaFinalizar = documentSnapshot.toObject(ModeloMontanteDiario.class);
                System.out.println("modeloMontanteDiarioRecuperadoParaFinalizar " + modeloMontanteDiarioRecuperadoParaFinalizar.toString());
                String retiradoDoCaixaHoje = modeloMontanteDiarioRecuperadoParaFinalizar.getValorTotalDeTrocoDesseDia().replace(",", ".");
                String totalDeVendasDoCaixaHoje = modeloMontanteDiarioRecuperadoParaFinalizar.getValorTotalDeVendasEmGeralDesseDia().replace(",", ".");
                System.out.println("retiradoDoCaixaHoje " + retiradoDoCaixaHoje);
                System.out.println("totalDeVendasDoCaixaHoje " + totalDeVendasDoCaixaHoje);
                double retiradoDoCaixaHojeConvert = Double.parseDouble(retiradoDoCaixaHoje);
                double totalDeVendasDoCaixaHojeConvert = Double.parseDouble(totalDeVendasDoCaixaHoje);

                double resultadoAoFinalizarODiaHoje = totalDeVendasDoCaixaHojeConvert - retiradoDoCaixaHojeConvert;

                String resultadoConvert = String.valueOf(resultadoAoFinalizarODiaHoje);

                registraFinalizaçãoDoDia(resultadoConvert, idRecuperado);


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }

    private void registraFinalizaçãoDoDia(String resultadoDaConta, String idRecuperado) {

        Map<String, Object> finalizaDiaCaixaDiario = new HashMap<>();
        finalizaDiaCaixaDiario.put("valorQueOCaixaFinalizou", resultadoDaConta);


        firebaseFirestore.collection(nomeCompletoColletionMontanteDiario).document(idRecuperado).update(finalizaDiaCaixaDiario).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

                Toast.makeText(getContext(), "Finalização efetuada com sucesso", Toast.LENGTH_SHORT).show();
                progressDialogCarregandoAsInformacoesDoCaixaDiario.dismiss();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println("Eroooooo121545 " + e.getMessage());
                Toast.makeText(getContext(), "Verifique sua conexão e tente novamente!", Toast.LENGTH_SHORT).show();

            }
        });

    }

}

