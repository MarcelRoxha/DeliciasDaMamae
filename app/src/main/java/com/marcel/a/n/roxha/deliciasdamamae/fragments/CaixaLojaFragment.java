package com.marcel.a.n.roxha.deliciasdamamae.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.marcel.a.n.roxha.deliciasdamamae.R;
import com.marcel.a.n.roxha.deliciasdamamae.activity.AddItemEstoqueActivity;
import com.marcel.a.n.roxha.deliciasdamamae.activity.AdicionarBoloVitrineActivity2;
import com.marcel.a.n.roxha.deliciasdamamae.activity.AdicionarProdutoComoVendidoNoSistema;
import com.marcel.a.n.roxha.deliciasdamamae.activity.EstoqueActivity;
import com.marcel.a.n.roxha.deliciasdamamae.adapter.BolosAdicionadosExpostosVitrineAdapter;
import com.marcel.a.n.roxha.deliciasdamamae.adapter.BolosAdicionadosVitrineParaExibirQuandoVenderAdapter;
import com.marcel.a.n.roxha.deliciasdamamae.config.ConfiguracaoFirebase;
import com.marcel.a.n.roxha.deliciasdamamae.controller.helper.ModeloMontanteDiarioDAO;
import com.marcel.a.n.roxha.deliciasdamamae.controller.helper.ModeloMontanteMensalDAO;
import com.marcel.a.n.roxha.deliciasdamamae.model.BolosAdicionadosVitrine;
import com.marcel.a.n.roxha.deliciasdamamae.model.BolosModel;
import com.marcel.a.n.roxha.deliciasdamamae.model.ModeloMontanteDiario;
import com.marcel.a.n.roxha.deliciasdamamae.model.ModeloMontanteMensalLoja;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
    private Button botaoOpaVendi;
    private Button botaoVitrineLoja;
    private Button botaoVendasFeitas;
    private Button botaoFinalizarDia;

    //Variaveis globais
    private String keyUserRecuperado = "";
    private String mesAtual = "";
    private String diaAtual = "";
    private String diaAnterior = "";
    private String anoAtual = "";
    private String idRecuperadoMontanteCasoExista = "";
    private String idRecuperadoMontanteDiarioCasoExista = "";
    private String idRecuperadoCaixaDiarioCasoExista = "";
    private String idRecuperadoMontanteColect = "";
    private int numMesReferencia;
    private int verificaCaixaMensalCriado = 0;
    private final String COLLECTION_CAIXA_DIARIO = "CAIXAS_DIARIO";

    private final String COLLECTION_MONTANTE_DESSE_MES = "MONTANTE_MENSAL";

    private final String COLLECTION_BOLOS_EXPOSTOS_VITRINE = "BOLOS_EXPOSTOS_VITRINE";
    private String nomeDoMes = "";

    private Date hoje = new Date();
    private Date dataCollectionReferenciaMontanteMensal = new Date();
    private String hojeString = "";
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
    private SimpleDateFormat simpleDateFormatVerificaDiaAnterior = new SimpleDateFormat("dd/MM/yy");
    private SimpleDateFormat simpleDateFormatVerificaDiaAnteriorConcatenaRestante = new SimpleDateFormat("MM/yy");
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View viewCaixaFragment = inflater.inflate(R.layout.fragment_caixa, container, false);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflaterLayout = getActivity().getLayoutInflater();
        builder.setView(inflaterLayout.inflate(R.layout.progress_dialog_carregando_informacoes_caixa_diario, null));
        builder.setCancelable(true);

        this.progressDialogCarregandoAsInformacoesDoCaixaDiario = builder.create();
        this.progressDialogCarregandoAsInformacoesDoCaixaDiario.getWindow().setBackgroundDrawableResource(
                android.R.color.transparent
        );
        this.progressDialogCarregandoAsInformacoesDoCaixaDiario.show();

        keyUserRecuperado = auth.getCurrentUser().toString();
        Date dataHoje = new Date();
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

        botaoOpaVendi = viewCaixaFragment.findViewById(R.id.botao_vendi_alguma_coisa_id);
        botaoVitrineLoja = viewCaixaFragment.findViewById(R.id.botao_vitrine_loja_id);
        botaoVendasFeitas = viewCaixaFragment.findViewById(R.id.botao_ver_vendas_id);
        botaoFinalizarDia = viewCaixaFragment.findViewById(R.id.botao_finalizar_dia_id);
        carregarComponentesDeTelaParaNaoVisualizar();


        hoje = new Date();
        hojeString = simpleDateFormat.format(hoje.getTime());
        textoDataDaUltimaAtualizacaoDoCaixaApresentandoParaOUsuario.setText(hojeString);
        numMesReferencia = 1 + hoje.getMonth();

        diaAtual = simpleDateFormatCollectionReferenciaAtualModeloCaixaDiario.format(hoje.getTime());

        this.progressDialogCarregandoAsInformacoesDoCaixaDiario.show();

        verificaSeJaTemCaixaDiarioCriado(this.progressDialogCarregandoAsInformacoesDoCaixaDiario);



        botaoParaIniciarOdia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialogCarregandoAsInformacoesDoCaixaDiario.show();
                iniciarCaixaDiario(progressDialogCarregandoAsInformacoesDoCaixaDiario);

            }
        });

        botaoOpaVendi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alertaMostrarProdutosDaVetrinePoisFoiVendidoAlgo = new AlertDialog.Builder(getContext());
                alertaMostrarProdutosDaVetrinePoisFoiVendidoAlgo.setTitle("TESTANDO");

                alertaMostrarProdutosDaVetrinePoisFoiVendidoAlgo.setView(inflaterLayout.inflate(R.layout.alerta_opa_vendi_alguma_coisa, null));
                View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.alerta_opa_vendi_alguma_coisa, null);
                alertaMostrarProdutosDaVetrinePoisFoiVendidoAlgo.setView(dialogView);


                recyclerView_itens_vitrine = dialogView.findViewById(R.id.recyclerView_opa_vendi_alguma_coisa_id);
                Query query = refBolosExpostosVitrine.orderBy("nomeBoloCadastrado", Query.Direction.ASCENDING);

                FirestoreRecyclerOptions<BolosModel> options = new FirestoreRecyclerOptions.Builder<BolosModel>()
                        .setQuery(query, BolosModel.class)
                        .build();

                expostoAdapter = new BolosAdicionadosVitrineParaExibirQuandoVenderAdapter(options, getContext());

                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

                recyclerView_itens_vitrine.setHasFixedSize(true);
                recyclerView_itens_vitrine.setLayoutManager(layoutManager);
                recyclerView_itens_vitrine.setAdapter(expostoAdapter);
                expostoAdapter.startListening();
                expostoAdapter.setOnItemClickListerner(new BolosAdicionadosVitrineParaExibirQuandoVenderAdapter.OnItemClickLisener() {
                    @Override
                    public void onItemClick(DocumentSnapshot documentSnapshot, int position) {

                        String idRecuperadoDoBoloExpostoVitrine = documentSnapshot.getId();
                        Intent intentAdicionarProdutoComoVendidoNoSistema = new Intent( getContext(), AdicionarProdutoComoVendidoNoSistema.class);
                        intentAdicionarProdutoComoVendidoNoSistema.putExtra("itemKey", idRecuperadoDoBoloExpostoVitrine);
                        startActivity(intentAdicionarProdutoComoVendidoNoSistema);


//                        Toast.makeText(getContext(), "Id recuperado: " + idRecuperadoDoBoloExpostoVitrine, Toast.LENGTH_SHORT).show();

                    }
                });

                alertaMostrarProdutosDaVetrinePoisFoiVendidoAlgo.create();
                alertaMostrarProdutosDaVetrinePoisFoiVendidoAlgo.show();

            }
        });

        botaoVitrineLoja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AdicionarBoloVitrineActivity2.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        progressDialogCarregandoAsInformacoesDoCaixaDiario.dismiss();
        return viewCaixaFragment;


    }

    private void criarMontanteInicialMes(AlertDialog progressDialogCarregandoAsInformacoesDoCaixaDiario) {

        Date dataHoje = new Date();
        System.out.println("dataHoje = " + dataHoje.toString());
        diaAtual = simpleDateFormatCollectionReferenciaAtualModeloCaixaDiario.format(dataHoje);
        anoAtual = simpleDateFormatCollectionReferenciaAnoAtual.format(dataHoje);
        mesAtual = simpleDateFormatCollectionReferenciaAtual.format(dataHoje);
        System.out.println("ANO ATUAL = " + anoAtual);
        System.out.println("DIA ATUAL = " + diaAtual);
        System.out.println("MÊS ATUAL = " + mesAtual);
        String referenciaDesseMesNoMontanteMensal = mesAtual + "_" + anoAtual;

        String nomeCompletoColletion = COLLECTION_MONTANTE_DESSE_MES + "_" + referenciaDesseMesNoMontanteMensal;
        System.out.println("nomeCompletoColletion --" + nomeCompletoColletion);
        firebaseFirestore.collection(nomeCompletoColletion).whereEqualTo("mesReferenciaDesseMontante", referenciaDesseMesNoMontanteMensal)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                progressDialogCarregandoAsInformacoesDoCaixaDiario.dismiss();
                System.out.println("antes do snapshotList");
                List<DocumentSnapshot> snapshotList = queryDocumentSnapshots.getDocuments();

                if (snapshotList.size() > 0) {
                    System.out.println("Entrou no lista maior que zero");
                    for (DocumentSnapshot listaCaixasDiariosCriados : snapshotList) {
                        firebaseFirestore.collection(nomeCompletoColletion).document(listaCaixasDiariosCriados.getId())
                                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {

                                System.out.println("Dentro achou documento com a data atual");
                                ModeloMontanteMensalLoja modeloMontanteMensalRecuperadoJaCriado = documentSnapshot.toObject(ModeloMontanteMensalLoja.class);
                                String mesReferenciaDesseMontante = modeloMontanteMensalRecuperadoJaCriado.getMesReferenciaDesseMontante();
                                String valorTotalVendasBoleriaMensal = modeloMontanteMensalRecuperadoJaCriado.getValorTotalVendasBoleriaMensal();
                                String valorTotalVendasIfoodMensal = modeloMontanteMensalRecuperadoJaCriado.getValorTotalVendasIfoodMensal();
                                String valorTotalVendasEmGeralMensal = modeloMontanteMensalRecuperadoJaCriado.getValorTotalVendasEmGeralMensal();
                                String quantoDinheiroEntrouEsseMes = modeloMontanteMensalRecuperadoJaCriado.getQuantoDinheiroEntrouEsseMes();
                                String quantoDinheiroSaiuEsseMes = modeloMontanteMensalRecuperadoJaCriado.getQuantoDinheiroSaiuEsseMes();
                                String valorQueOMontanteIniciouPositivo = modeloMontanteMensalRecuperadoJaCriado.getValorQueOMontanteIniciouPositivo();
                                String valorQueOMontanteIniciouNegativo = modeloMontanteMensalRecuperadoJaCriado.getValorQueOMontanteIniciouNegativo();
                                String valorTotalDeVendasNoDinheiroDesseMes = modeloMontanteMensalRecuperadoJaCriado.getValorTotalDeVendasNoDinheiroDesseMes();
                                String valorTotalDeVendasNoCreditoDesseMes = modeloMontanteMensalRecuperadoJaCriado.getValorTotalDeVendasNoCreditoDesseMes();
                                String valorTotalDeVendasNoDebitoDesseMes = modeloMontanteMensalRecuperadoJaCriado.getValorTotalDeVendasNoDebitoDesseMes();
                                AlertDialog.Builder alertaMontanteDiarioJaCriado = new AlertDialog.Builder(getActivity());

                                alertaMontanteDiarioJaCriado.setTitle("MONTANTE DIÁRIO JÁ CRIADO");
                                alertaMontanteDiarioJaCriado.setMessage("Atenção já existe um montante diário referente á hoje com os seguintes dados: \n" +
                                        "VALOR INICIADO DO MÊS: " + valorQueOMontanteIniciouPositivo + "\n" +
                                        "TOTAL DE VENDAS CAIXA: " + valorTotalVendasBoleriaMensal + "\n" +
                                        "TOTAL VENDAS CAIXA EM DINHEIRO: " + valorTotalDeVendasNoDinheiroDesseMes + "\n" +
                                        "TOTAL DE SAIDAS: " + quantoDinheiroSaiuEsseMes + "\n" +
                                        "TOTAL DE VENDAS NO CRÉDITO: " + valorTotalDeVendasNoCreditoDesseMes + "\n" +
                                        "TOTAL DE VENDAS NO DÉBITO: " + valorTotalDeVendasNoDebitoDesseMes + "\n" +
                                        "TOTAL VENDAS IFOOD: " + valorTotalVendasIfoodMensal + "\n" +
                                        "QUANTO DE DINHEIRO ENTROU ESSE MÊS: " + quantoDinheiroEntrouEsseMes + "\n" +
                                        "TOTAL DE VENDAS EM GERAL: " + valorTotalVendasEmGeralMensal);
                                alertaMontanteDiarioJaCriado.setCancelable(false);
                                alertaMontanteDiarioJaCriado.setNeutralButton("OK ENTENDI!", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(getContext(), "Obrigado", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                alertaMontanteDiarioJaCriado.show();
                                alertaMontanteDiarioJaCriado.create();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });
                    }
                } else {

                    AlertDialog.Builder alertaCriarMontanteMensal = new AlertDialog.Builder(getActivity());
                    LayoutInflater inflaterLayout = getActivity().getLayoutInflater();
                    alertaCriarMontanteMensal.setView(inflaterLayout.inflate(R.layout.alerta_criar_um_montante_mensal, null));
                    View view = getLayoutInflater().inflate(R.layout.alerta_criar_um_montante_mensal, null);

                    EditText valorDigitadoPeloUsuario = view.findViewById(R.id.input_com_quanto_ira_iniciar_o_caixa_esse_mes);
                    alertaCriarMontanteMensal.setView(view);

                    alertaCriarMontanteMensal.setPositiveButton("CONFIRMAR", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {


                            String valorDigitadoParaIniciarOMontateDesseMes = valorDigitadoPeloUsuario.getText().toString().replace(",", ".");
                            ModeloMontanteMensalLoja modeloMontanteMensalLojaSendoIniciadoDesseMes = new ModeloMontanteMensalLoja();
                            ModeloMontanteMensalDAO modeloMontanteMensalDAOSendoIniciadoDesseMes = new ModeloMontanteMensalDAO(getContext());
                            modeloMontanteMensalLojaSendoIniciadoDesseMes.setIdMontante("N/D");
                            modeloMontanteMensalLojaSendoIniciadoDesseMes.setMesReferenciaDesseMontante(referenciaDesseMesNoMontanteMensal);
                            modeloMontanteMensalLojaSendoIniciadoDesseMes.setValorQueOMontanteIniciouPositivo(valorDigitadoParaIniciarOMontateDesseMes);
                            modeloMontanteMensalLojaSendoIniciadoDesseMes.setValorTotalVendasBoleriaMensal("0");
                            modeloMontanteMensalLojaSendoIniciadoDesseMes.setValorTotalVendasIfoodMensal("0");
                            modeloMontanteMensalLojaSendoIniciadoDesseMes.setValorTotalVendasEmGeralMensal("0");
                            modeloMontanteMensalLojaSendoIniciadoDesseMes.setQuantoDinheiroEntrouEsseMes("0");
                            modeloMontanteMensalLojaSendoIniciadoDesseMes.setQuantoDinheiroSaiuEsseMes("0");
                            modeloMontanteMensalLojaSendoIniciadoDesseMes.setValorQueOMontanteIniciouNegativo("0");
                            modeloMontanteMensalLojaSendoIniciadoDesseMes.setValorTotalDeVendasNoDinheiroDesseMes("0");
                            modeloMontanteMensalLojaSendoIniciadoDesseMes.setValorTotalDeVendasNoCreditoDesseMes("0");
                            modeloMontanteMensalLojaSendoIniciadoDesseMes.setValorTotalDeVendasNoDebitoDesseMes("0");


                            modeloMontanteMensalDAOSendoIniciadoDesseMes.modeloMontanteIniciarMes(modeloMontanteMensalLojaSendoIniciadoDesseMes);

                        }
                    }).setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //Toast.makeText(getContext(), "VALOR DIGITADO FOI:  " +valorDigitadoPeloUsuario.getText().toString() , Toast.LENGTH_SHORT).show();
                            Toast.makeText(getContext(), "Clicou no CANCELAR", Toast.LENGTH_SHORT).show();
                        }
                    });
                    alertaCriarMontanteMensal.create();
                    alertaCriarMontanteMensal.show();

                    Toast.makeText(getContext(), "É possível fazer um montante mensal", Toast.LENGTH_SHORT).show();
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }


    private void iniciarCaixaDiario(AlertDialog progressDialogCarregandoAsInformacoesDoCaixaDiario) {


        // Obtém a data atual
        LocalDate hoje = LocalDate.now();
        Calendar calendar = Calendar.getInstance();

        // Subtrai um dia do dia atual
        calendar.add(Calendar.DAY_OF_MONTH, -1);

        // Obtém o dia, mês e ano do dia anterior
        int diaAnteriorInt = calendar.get(Calendar.DAY_OF_MONTH);
        int mesAnterior = calendar.get(Calendar.MONTH) + 1; // Adiciona 1 porque o mês começa em 0
        int anoAnterior = calendar.get(Calendar.YEAR);

        // Obtém o primeiro dia do mês
        LocalDate primeiroDiaDoMes = hoje.withDayOfMonth(1);

        // Verifica se o primeiro dia do mês é um dia útil (de segunda a sexta-feira)
        if (primeiroDiaDoMes.getDayOfWeek() == DayOfWeek.SATURDAY) {
            primeiroDiaDoMes = primeiroDiaDoMes.plusDays(2);
        } else if (primeiroDiaDoMes.getDayOfWeek() == DayOfWeek.SUNDAY) {
            primeiroDiaDoMes = primeiroDiaDoMes.plusDays(1);
        }

        if (hoje.isEqual(primeiroDiaDoMes)) {
            //Se entrar nesse if significa que é o primeiro dia útil do mês de segunda a sexta
            Date dataHoje = new Date();
            diaAtual = simpleDateFormatCollectionReferenciaAtualModeloCaixaDiario.format(dataHoje);
            String nomeCompletoColletion = COLLECTION_CAIXA_DIARIO;

            diaAtual = simpleDateFormatCollectionReferenciaAtualModeloCaixaDiario.format(dataHoje);
            anoAtual = simpleDateFormatCollectionReferenciaAnoAtual.format(dataHoje);
            mesAtual = simpleDateFormatCollectionReferenciaAtual.format(dataHoje);

            String referenciaDesseMesNoMontanteMensal = mesAtual + "_" + anoAtual;


            firebaseFirestore.collection(nomeCompletoColletion).whereEqualTo("dataReferenciaMontanteDiarioDesseDia", diaAtual)
                    .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    List<DocumentSnapshot> snapshotList = queryDocumentSnapshots.getDocuments();

                    if (snapshotList.size() > 0) {
                        progressDialogCarregandoAsInformacoesDoCaixaDiario.dismiss();
                        for (DocumentSnapshot listaCaixasDiariosCriados : snapshotList) {
                            firebaseFirestore.collection(nomeCompletoColletion).document(listaCaixasDiariosCriados.getId())
                                    .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {

                                    ModeloMontanteDiario modeloMontanteDiarioRecuperadoJaCriado = documentSnapshot.toObject(ModeloMontanteDiario.class);
                                    String valorQueOCaixaIniciouODia = modeloMontanteDiarioRecuperadoJaCriado.getValorQueOCaixaIniciouODia();
                                    String totalDeVendasNoCaixaHoje = modeloMontanteDiarioRecuperadoJaCriado.getValorTotalDeVendasNaLojaDesseDia();
                                    String totalDeVendasEmDinheiro = modeloMontanteDiarioRecuperadoJaCriado.getValorTotalDeVendasNoDinheiroDesseDia();
                                    String totalDeSaidaCaixaHoje = modeloMontanteDiarioRecuperadoJaCriado.getValorTotalDeTrocoDesseDia();
                                    String totalDeVendasNoCreditoHoje = modeloMontanteDiarioRecuperadoJaCriado.getValorTotalDeVendasNoCreditoDesseDia();
                                    String totalDeVendasNoDebitoNoCaixHoje = modeloMontanteDiarioRecuperadoJaCriado.getValorTotalDeVendasNoDebitoDesseDia();
                                    String valorTotalDeVendasNoIfoodDesseDia = modeloMontanteDiarioRecuperadoJaCriado.getValorTotalDeVendasNoIfoodDesseDia();
                                    String valorTotalDeVendasEmGeralDesseDia = modeloMontanteDiarioRecuperadoJaCriado.getValorTotalDeVendasEmGeralDesseDia();
                                    AlertDialog.Builder alertaMontanteDiarioJaCriado = new AlertDialog.Builder(getActivity());

                                    alertaMontanteDiarioJaCriado.setTitle("MONTANTE DIÁRIO JÁ CRIADO");
                                    alertaMontanteDiarioJaCriado.setMessage("Atenção já existe um montante diário referente á hoje com os seguintes dados: \n" +
                                            "VALOR QUE O CAIXA INICIOU O DIA: " + valorQueOCaixaIniciouODia + "\n" +
                                            "TOTAL DE VENDAS NO CAIXA HOJE: " + totalDeVendasNoCaixaHoje + "\n" +
                                            "TOTAL DE VENDAS EM DINHEIRO: " + totalDeVendasEmDinheiro + "\n" +
                                            "TOTAL DE SAIDAS CAIXA HOJE: " + totalDeSaidaCaixaHoje + "\n" +
                                            "TOTAL DE VENDAS NO CRÉDITO HOJE: " + totalDeVendasNoCreditoHoje + "\n" +
                                            "TOTAL DE VENDAS NO DÉBITO NO CAIXA HOJE: " + totalDeVendasNoDebitoNoCaixHoje + "\n" +
                                            "VALOR TOTAL DE VENDAS NO IFOOD DESSE DIA: " + valorTotalDeVendasNoIfoodDesseDia + "\n" +
                                            "VALOR TOTAL DE VENDAS EM GERAL DESSE DIA: " + valorTotalDeVendasEmGeralDesseDia);
                                    alertaMontanteDiarioJaCriado.setCancelable(false);
                                    alertaMontanteDiarioJaCriado.setNeutralButton("OK ENTENDI!", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Toast.makeText(getContext(), "Boas Vendas!", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    alertaMontanteDiarioJaCriado.show();
                                    alertaMontanteDiarioJaCriado.create();


                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                }
                            });
                        }

                    } else {


                        progressDialogCarregandoAsInformacoesDoCaixaDiario.dismiss();
                        AlertDialog.Builder alertaCriarMontanteMensal = new AlertDialog.Builder(getActivity());
                        LayoutInflater inflaterLayout = getActivity().getLayoutInflater();
                        alertaCriarMontanteMensal.setView(inflaterLayout.inflate(R.layout.alerta_iniciando_o_primeiro_dia_util_do_mes, null));
                        View view = getLayoutInflater().inflate(R.layout.alerta_iniciando_o_primeiro_dia_util_do_mes, null);

                        EditText valorDigitadoPeloUsuario = view.findViewById(R.id.input_caixa_iniciou_primeiro_dia_util_do_mes_com);
                        alertaCriarMontanteMensal.setView(view);

                        alertaCriarMontanteMensal.setPositiveButton("CONFIRMAR", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                String valorDigitadoParaIniciarOMontateDesseMes = valorDigitadoPeloUsuario.getText().toString().replace(",", ".");
                                ModeloMontanteDiario modeloMontanteDiarioIniciado = new ModeloMontanteDiario();
                                ModeloMontanteDiarioDAO modeloMontanteDiarioDAOSendoIniciadoDesseMes = new ModeloMontanteDiarioDAO(getContext());
                                modeloMontanteDiarioIniciado.setIdReferenciaMontanteDiarioDesseDia("N/D");
                                modeloMontanteDiarioIniciado.setDataReferenciaMontanteDiarioDesseDia(diaAtual);
                                modeloMontanteDiarioIniciado.setValorQueOCaixaIniciouODia(valorDigitadoParaIniciarOMontateDesseMes);
                                modeloMontanteDiarioIniciado.setValorTotalDeVendasNaLojaDesseDia("0");
                                modeloMontanteDiarioIniciado.setValorTotalDeVendasNoIfoodDesseDia("0");
                                modeloMontanteDiarioIniciado.setValorTotalDeVendasEmGeralDesseDia("0");
                                modeloMontanteDiarioIniciado.setValorTotalDeVendasNoDinheiroDesseDia("0");
                                modeloMontanteDiarioIniciado.setValorTotalDeVendasNoCreditoDesseDia("0");
                                modeloMontanteDiarioIniciado.setValorTotalDeVendasNoDebitoDesseDia("0");
                                modeloMontanteDiarioIniciado.setValorTotalDeTrocoDesseDia("0");


                                ModeloMontanteMensalLoja modeloMontanteMensalLojaSendoIniciadoDesseMes = new ModeloMontanteMensalLoja();
                                ModeloMontanteMensalDAO modeloMontanteMensalDAOSendoIniciadoDesseMes = new ModeloMontanteMensalDAO(getContext());
                                modeloMontanteMensalLojaSendoIniciadoDesseMes.setIdMontante("N/D");
                                modeloMontanteMensalLojaSendoIniciadoDesseMes.setMesReferenciaDesseMontante(referenciaDesseMesNoMontanteMensal);
                                modeloMontanteMensalLojaSendoIniciadoDesseMes.setValorQueOMontanteIniciouPositivo(valorDigitadoParaIniciarOMontateDesseMes);
                                modeloMontanteMensalLojaSendoIniciadoDesseMes.setValorTotalVendasBoleriaMensal("0");
                                modeloMontanteMensalLojaSendoIniciadoDesseMes.setValorTotalVendasIfoodMensal("0");
                                modeloMontanteMensalLojaSendoIniciadoDesseMes.setValorTotalVendasEmGeralMensal("0");
                                modeloMontanteMensalLojaSendoIniciadoDesseMes.setQuantoDinheiroEntrouEsseMes("0");
                                modeloMontanteMensalLojaSendoIniciadoDesseMes.setQuantoDinheiroSaiuEsseMes("0");
                                modeloMontanteMensalLojaSendoIniciadoDesseMes.setValorQueOMontanteIniciouNegativo("0");
                                modeloMontanteMensalLojaSendoIniciadoDesseMes.setValorTotalDeVendasNoDinheiroDesseMes("0");
                                modeloMontanteMensalLojaSendoIniciadoDesseMes.setValorTotalDeVendasNoCreditoDesseMes("0");
                                modeloMontanteMensalLojaSendoIniciadoDesseMes.setValorTotalDeVendasNoDebitoDesseMes("0");


                                modeloMontanteMensalDAOSendoIniciadoDesseMes.modeloMontanteIniciarMes(modeloMontanteMensalLojaSendoIniciadoDesseMes);


                                modeloMontanteDiarioDAOSendoIniciadoDesseMes.modeloMontanteDiarioIniciandoODia(modeloMontanteDiarioIniciado);

                            }
                        }).setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Toast.makeText(getContext(), "VALOR DIGITADO FOI:  " +valorDigitadoPeloUsuario.getText().toString() , Toast.LENGTH_SHORT).show();
                                Toast.makeText(getContext(), "Clicou no CANCELAR", Toast.LENGTH_SHORT).show();
                            }
                        });
                        alertaCriarMontanteMensal.create();
                        alertaCriarMontanteMensal.show();

                    }


                }
            });

        } else {
            //Não é o primeiro dia útil do mês então verifico se existe algo criado do dia anterior
            Date dataHoje = new Date();
            diaAtual = simpleDateFormatCollectionReferenciaAtualModeloCaixaDiario.format(dataHoje);
            String nomeCompletoColletion = COLLECTION_CAIXA_DIARIO;

            diaAtual = simpleDateFormatCollectionReferenciaAtualModeloCaixaDiario.format(dataHoje);
            anoAtual = simpleDateFormatCollectionReferenciaAnoAtual.format(dataHoje);
            mesAtual = simpleDateFormatCollectionReferenciaAtual.format(dataHoje);

            String referenciaDesseMesNoMontanteMensal = mesAtual + "_" + anoAtual;

            progressDialogCarregandoAsInformacoesDoCaixaDiario.setCancelable(false);
            progressDialogCarregandoAsInformacoesDoCaixaDiario.show();


            // Imprime o dia anterior
            System.out.println("O dia anterior foi: " + diaAnteriorInt + "/" + mesAnterior + "/" + anoAnterior);

            String dataReferenciaAnteriorConvertida = (diaAnteriorInt + "/" + mesAnterior + "/" + anoAnterior);

            firebaseFirestore.collection(nomeCompletoColletion).whereEqualTo("dataReferenciaMontanteDiarioDesseDia", dataReferenciaAnteriorConvertida)
                    .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                    List<DocumentSnapshot> listaRecuperadaDeCaixasDiarioFerenteAoMesAnterior = queryDocumentSnapshots.getDocuments();

                    if (listaRecuperadaDeCaixasDiarioFerenteAoMesAnterior.size() > 0) {
                        for (DocumentSnapshot listaCaixasDiariosCriados : listaRecuperadaDeCaixasDiarioFerenteAoMesAnterior) {

                            ModeloMontanteDiario modeloMontanteDiarioRecuperadoJaCriado = listaCaixasDiariosCriados.toObject(ModeloMontanteDiario.class);
                            String valorQueOCaixaIniciouODia = modeloMontanteDiarioRecuperadoJaCriado.getValorQueOCaixaIniciouODia().replace(",", ".");
                            String totalDeVendasNoCaixaHoje = modeloMontanteDiarioRecuperadoJaCriado.getValorTotalDeVendasNaLojaDesseDia();
                            String totalDeVendasEmDinheiro = modeloMontanteDiarioRecuperadoJaCriado.getValorTotalDeVendasNoDinheiroDesseDia();
                            String totalDeSaidaCaixaHoje = modeloMontanteDiarioRecuperadoJaCriado.getValorTotalDeTrocoDesseDia().replace(",", ".");
                            String totalDeVendasNoCreditoHoje = modeloMontanteDiarioRecuperadoJaCriado.getValorTotalDeVendasNoCreditoDesseDia();
                            String totalDeVendasNoDebitoNoCaixHoje = modeloMontanteDiarioRecuperadoJaCriado.getValorTotalDeVendasNoDebitoDesseDia();
                            String valorTotalDeVendasNoIfoodDesseDia = modeloMontanteDiarioRecuperadoJaCriado.getValorTotalDeVendasNoIfoodDesseDia();
                            String valorTotalDeVendasEmGeralDesseDia = modeloMontanteDiarioRecuperadoJaCriado.getValorTotalDeVendasEmGeralDesseDia();


                            double valorQueSaiuDoCaixaConvertido = Double.valueOf(totalDeSaidaCaixaHoje);
                            double valorQueOCaixaIniciouODiaConvertido = Double.valueOf(valorQueOCaixaIniciouODia);

                            double resultadoDaSubtracaoParaInformarOQueSaiuEQuantoIniciou = valorQueOCaixaIniciouODiaConvertido - valorQueSaiuDoCaixaConvertido;
                            String valorConvertido = String.valueOf(resultadoDaSubtracaoParaInformarOQueSaiuEQuantoIniciou);

                            //Foi feito um calculo e ontem o dia inicou com {variavel} e terminou o dia com {variavel2} se deseja manter o calculo para iniciar o dia com
                            //{valorConvertido} não precisa adicionar valor abaixo se deseja acrescentar mais ao valor inicial basta adicionar um valor se deseja remover
                            // algum valor do calculo basta adicionar um valor negativo Ex: desejo remover 5 reais do valor inicial, digito: -5

                            String informativoDoValorDoDiaAnterior = "Foi feito um calculo referente ao dia anterior e o valor sujerido para iniciar o dia é de R$: " + valorConvertido
                                    + " devido a entrada que foi de R$: " + valorQueOCaixaIniciouODia + " e de troco foi dado R$: " + totalDeSaidaCaixaHoje + " do dia de ontem";
                            progressDialogCarregandoAsInformacoesDoCaixaDiario.dismiss();

                            AlertDialog.Builder alertaCriarMontanteMensal = new AlertDialog.Builder(getActivity());
                            LayoutInflater inflaterLayout = getActivity().getLayoutInflater();
                            alertaCriarMontanteMensal.setView(inflaterLayout.inflate(R.layout.alerta_iniciando_o_dia_quando_tem_caixa_diario_do_dia_anterior, null));
                            View view = getLayoutInflater().inflate(R.layout.alerta_iniciando_o_dia_quando_tem_caixa_diario_do_dia_anterior, null);

                            EditText valorDigitadoPeloUsuario = view.findViewById(R.id.input_caixa_iniciou_o_dia_com_calculo_do_dia_anterior);
                            valorDigitadoPeloUsuario.setText(valorConvertido);
                            TextView textoInformativoSobreOValorJaCalculado = view.findViewById(R.id.texto_informativo_iniciando_o_dia_quando_tem_dia_anterior);

                            textoInformativoSobreOValorJaCalculado.setText(informativoDoValorDoDiaAnterior);
                            alertaCriarMontanteMensal.setView(view);

                            alertaCriarMontanteMensal.setPositiveButton("CONFIRMAR", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {


                                    String valorDigitadoParaIniciarOMontateDesseMes = valorDigitadoPeloUsuario.getText().toString().replace(",", ".");
                                    ModeloMontanteDiario modeloMontanteDiarioIniciado = new ModeloMontanteDiario();
                                    ModeloMontanteDiarioDAO modeloMontanteDiarioDAOSendoIniciadoDesseMes = new ModeloMontanteDiarioDAO(getContext());
                                    modeloMontanteDiarioIniciado.setIdReferenciaMontanteDiarioDesseDia("N/D");
                                    modeloMontanteDiarioIniciado.setDataReferenciaMontanteDiarioDesseDia(diaAtual);
                                    modeloMontanteDiarioIniciado.setValorQueOCaixaIniciouODia(valorDigitadoParaIniciarOMontateDesseMes);
                                    modeloMontanteDiarioIniciado.setValorTotalDeVendasNaLojaDesseDia("0");
                                    modeloMontanteDiarioIniciado.setValorTotalDeVendasNoIfoodDesseDia("0");
                                    modeloMontanteDiarioIniciado.setValorTotalDeVendasEmGeralDesseDia("0");
                                    modeloMontanteDiarioIniciado.setValorTotalDeVendasNoDinheiroDesseDia("0");
                                    modeloMontanteDiarioIniciado.setValorTotalDeVendasNoCreditoDesseDia("0");
                                    modeloMontanteDiarioIniciado.setValorTotalDeVendasNoDebitoDesseDia("0");
                                    modeloMontanteDiarioIniciado.setValorTotalDeTrocoDesseDia("0");


                                    modeloMontanteDiarioDAOSendoIniciadoDesseMes.modeloMontanteDiarioIniciandoODia(modeloMontanteDiarioIniciado);

                                }
                            }).setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //Toast.makeText(getContext(), "VALOR DIGITADO FOI:  " +valorDigitadoPeloUsuario.getText().toString() , Toast.LENGTH_SHORT).show();
                                    Toast.makeText(getContext(), "Clicou no CANCELAR", Toast.LENGTH_SHORT).show();
                                }
                            });
                            alertaCriarMontanteMensal.create();
                            alertaCriarMontanteMensal.show();


                        }


                    } else {


                        Date dataHoje = new Date();

                        anoAtual = simpleDateFormatCollectionReferenciaAnoAtual.format(dataHoje);
                        mesAtual = simpleDateFormatCollectionReferenciaAtual.format(dataHoje);

                        String referenciaDesseMesNoMontanteMensal = mesAtual + "_" + anoAtual;

                        String nomeCompletoColletion = COLLECTION_MONTANTE_DESSE_MES + "_" + referenciaDesseMesNoMontanteMensal;
                        String nomeCompletoCollectionMontanteDiario = COLLECTION_CAIXA_DIARIO + "_" + referenciaDesseMesNoMontanteMensal;

                        System.out.println("nomeCompletoColletion --" + nomeCompletoColletion);
                        firebaseFirestore.collection(nomeCompletoColletion).whereEqualTo("mesReferenciaDesseMontante", referenciaDesseMesNoMontanteMensal)
                                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                progressDialogCarregandoAsInformacoesDoCaixaDiario.dismiss();
                                System.out.println("antes do snapshotList");
                                List<DocumentSnapshot> snapshotList = queryDocumentSnapshots.getDocuments();

                                if (snapshotList.size() > 0) {
                                    AlertDialog.Builder alertaCriarMontanteMensal = new AlertDialog.Builder(getActivity());
                                    LayoutInflater inflaterLayout = getActivity().getLayoutInflater();
                                    alertaCriarMontanteMensal.setView(inflaterLayout.inflate(R.layout.alerta_criar_um_montante_mensal, null));
                                    View view = getLayoutInflater().inflate(R.layout.alerta_criar_um_montante_mensal, null);

                                    EditText valorDigitadoPeloUsuario = view.findViewById(R.id.input_com_quanto_ira_iniciar_o_caixa_esse_mes);
                                    alertaCriarMontanteMensal.setView(view);

                                    alertaCriarMontanteMensal.setPositiveButton("CONFIRMAR", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {


                                            String valorDigitadoParaIniciarOMontateDesseMes = valorDigitadoPeloUsuario.getText().toString().replace(",", ".");
                                            ModeloMontanteDiario modeloMontanteDiarioIniciado = new ModeloMontanteDiario();
                                            ModeloMontanteDiarioDAO modeloMontanteDiarioDAOSendoIniciadoDesseMes = new ModeloMontanteDiarioDAO(getContext());
                                            modeloMontanteDiarioIniciado.setIdReferenciaMontanteDiarioDesseDia("N/D");
                                            modeloMontanteDiarioIniciado.setDataReferenciaMontanteDiarioDesseDia(diaAtual);
                                            modeloMontanteDiarioIniciado.setValorQueOCaixaIniciouODia(valorDigitadoParaIniciarOMontateDesseMes);
                                            modeloMontanteDiarioIniciado.setValorTotalDeVendasNaLojaDesseDia("0");
                                            modeloMontanteDiarioIniciado.setValorTotalDeVendasNoIfoodDesseDia("0");
                                            modeloMontanteDiarioIniciado.setValorTotalDeVendasEmGeralDesseDia("0");
                                            modeloMontanteDiarioIniciado.setValorTotalDeVendasNoDinheiroDesseDia("0");
                                            modeloMontanteDiarioIniciado.setValorTotalDeVendasNoCreditoDesseDia("0");
                                            modeloMontanteDiarioIniciado.setValorTotalDeVendasNoDebitoDesseDia("0");
                                            modeloMontanteDiarioIniciado.setValorTotalDeTrocoDesseDia("0");

                                            modeloMontanteDiarioDAOSendoIniciadoDesseMes.modeloMontanteDiarioIniciandoODia(modeloMontanteDiarioIniciado);

                                        }
                                    }).setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            //Toast.makeText(getContext(), "VALOR DIGITADO FOI:  " +valorDigitadoPeloUsuario.getText().toString() , Toast.LENGTH_SHORT).show();
                                            Toast.makeText(getContext(), "Clicou no CANCELAR", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    alertaCriarMontanteMensal.create();
                                    alertaCriarMontanteMensal.show();


                                } else {

                                    AlertDialog.Builder alertaCriarMontanteMensal = new AlertDialog.Builder(getActivity());
                                    LayoutInflater inflaterLayout = getActivity().getLayoutInflater();
                                    alertaCriarMontanteMensal.setView(inflaterLayout.inflate(R.layout.alerta_criar_um_montante_diario_e_montante_mensal, null));
                                    View view = getLayoutInflater().inflate(R.layout.alerta_criar_um_montante_diario_e_montante_mensal, null);

                                    EditText valorDigitadoPeloUsuario = view.findViewById(R.id.input_com_quanto_ira_iniciar_o_caixa_diario_e_desse_mes);
                                    alertaCriarMontanteMensal.setView(view);

                                    alertaCriarMontanteMensal.setPositiveButton("CONFIRMAR", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {


                                            String valorDigitadoParaIniciarOMontateDesseMes = valorDigitadoPeloUsuario.getText().toString().replace(",", ".");
                                            ModeloMontanteDiario modeloMontanteDiarioIniciado = new ModeloMontanteDiario();
                                            ModeloMontanteDiarioDAO modeloMontanteDiarioDAOSendoIniciadoDesseMes = new ModeloMontanteDiarioDAO(getContext());
                                            modeloMontanteDiarioIniciado.setIdReferenciaMontanteDiarioDesseDia("N/D");
                                            modeloMontanteDiarioIniciado.setDataReferenciaMontanteDiarioDesseDia(diaAtual);
                                            modeloMontanteDiarioIniciado.setValorQueOCaixaIniciouODia(valorDigitadoParaIniciarOMontateDesseMes);
                                            modeloMontanteDiarioIniciado.setValorTotalDeVendasNaLojaDesseDia("0");
                                            modeloMontanteDiarioIniciado.setValorTotalDeVendasNoIfoodDesseDia("0");
                                            modeloMontanteDiarioIniciado.setValorTotalDeVendasEmGeralDesseDia("0");
                                            modeloMontanteDiarioIniciado.setValorTotalDeVendasNoDinheiroDesseDia("0");
                                            modeloMontanteDiarioIniciado.setValorTotalDeVendasNoCreditoDesseDia("0");
                                            modeloMontanteDiarioIniciado.setValorTotalDeVendasNoDebitoDesseDia("0");
                                            modeloMontanteDiarioIniciado.setValorTotalDeTrocoDesseDia("0");


                                            ModeloMontanteMensalLoja modeloMontanteMensalLojaSendoIniciadoDesseMes = new ModeloMontanteMensalLoja();
                                            ModeloMontanteMensalDAO modeloMontanteMensalDAOSendoIniciadoDesseMes = new ModeloMontanteMensalDAO(getContext());
                                            modeloMontanteMensalLojaSendoIniciadoDesseMes.setIdMontante("N/D");
                                            modeloMontanteMensalLojaSendoIniciadoDesseMes.setMesReferenciaDesseMontante(referenciaDesseMesNoMontanteMensal);
                                            modeloMontanteMensalLojaSendoIniciadoDesseMes.setValorQueOMontanteIniciouPositivo(valorDigitadoParaIniciarOMontateDesseMes);
                                            modeloMontanteMensalLojaSendoIniciadoDesseMes.setValorTotalVendasBoleriaMensal("0");
                                            modeloMontanteMensalLojaSendoIniciadoDesseMes.setValorTotalVendasIfoodMensal("0");
                                            modeloMontanteMensalLojaSendoIniciadoDesseMes.setValorTotalVendasEmGeralMensal("0");
                                            modeloMontanteMensalLojaSendoIniciadoDesseMes.setQuantoDinheiroEntrouEsseMes("0");
                                            modeloMontanteMensalLojaSendoIniciadoDesseMes.setQuantoDinheiroSaiuEsseMes("0");
                                            modeloMontanteMensalLojaSendoIniciadoDesseMes.setValorQueOMontanteIniciouNegativo("0");
                                            modeloMontanteMensalLojaSendoIniciadoDesseMes.setValorTotalDeVendasNoDinheiroDesseMes("0");
                                            modeloMontanteMensalLojaSendoIniciadoDesseMes.setValorTotalDeVendasNoCreditoDesseMes("0");
                                            modeloMontanteMensalLojaSendoIniciadoDesseMes.setValorTotalDeVendasNoDebitoDesseMes("0");


                                            modeloMontanteMensalDAOSendoIniciadoDesseMes.modeloMontanteIniciarMes(modeloMontanteMensalLojaSendoIniciadoDesseMes);


                                            modeloMontanteDiarioDAOSendoIniciadoDesseMes.modeloMontanteDiarioIniciandoODia(modeloMontanteDiarioIniciado);

                                            carregandoAsInformacoesDoCaixaDiario(progressDialogCarregandoAsInformacoesDoCaixaDiario, diaAtual, nomeCompletoCollectionMontanteDiario);




                                        }
                                    }).setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            //Toast.makeText(getContext(), "VALOR DIGITADO FOI:  " +valorDigitadoPeloUsuario.getText().toString() , Toast.LENGTH_SHORT).show();
                                            Toast.makeText(getContext(), "Clicou no CANCELAR", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    alertaCriarMontanteMensal.create();
                                    alertaCriarMontanteMensal.show();

                                    Toast.makeText(getContext(), "É possível fazer um montante mensal", Toast.LENGTH_SHORT).show();
                                }

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        }).

                                addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                        Toast.makeText(getContext(), "FAVOR VERIFIQUE SUA CONEXÃO E TENTE NOVAMENTE", Toast.LENGTH_SHORT).show();
                                    }
                                });


                    }


                }
            });
            System.out.println("A data atual não é o primeiro dia útil do mês.");
        }
    }

    private void verificaSeJaTemCaixaDiarioCriado(AlertDialog progressDialogCarregandoAsInformacoesDoCaixaDiario) {

        Date dataHoje = new Date();
        diaAtual = simpleDateFormatCollectionReferenciaAtualModeloCaixaDiario.format(dataHoje);
        anoAtual = simpleDateFormatCollectionReferenciaAnoAtual.format(dataHoje);

        String nomeCompletoColletion = COLLECTION_CAIXA_DIARIO;

        firebaseFirestore.collection(nomeCompletoColletion).whereEqualTo("dataReferenciaMontanteDiarioDesseDia", diaAtual)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                List<DocumentSnapshot> snapshotList = queryDocumentSnapshots.getDocuments();

                if (snapshotList.size() > 0) {
                    //Significa que existe o documento com a data de hoje então já tem um caixa diario referente a esse dia
                    for (DocumentSnapshot listaCaixasDiariosCriados : snapshotList) {
                        firebaseFirestore.collection(nomeCompletoColletion).document(listaCaixasDiariosCriados.getId())
                                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {

                                ModeloMontanteDiario modeloMontanteDiarioRecuperadoJaCriado = documentSnapshot.toObject(ModeloMontanteDiario.class);
                                String idDocumento = modeloMontanteDiarioRecuperadoJaCriado.getIdReferenciaMontanteDiarioDesseDia();
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
                                textoTotalDeVendasEmGeral.setText(valorTotalDeVendasNoIfoodDesseDia);
                                textoTotalDeVendasNoIfoofHoje.setText(valorTotalDeVendasEmGeralDesseDia);
                                progressDialogCarregandoAsInformacoesDoCaixaDiario.dismiss();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });
                    }

                } else {

                    //Não existe documento então não tem montante diario criado, só precisa carregar os componentes da tela
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


    private void carregandoAsInformacoesDoCaixaDiario(AlertDialog alertaCarregandoAsInformacoesDoCaixaDiario, String dataReferenciaCaixaDiarioCriado, String nomeDaCollection) {


        firebaseFirestore.collection(nomeDaCollection).whereEqualTo("dataReferenciaMontanteDiarioDesseDia", dataReferenciaCaixaDiarioCriado)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                List<DocumentSnapshot> listaDosCaixasDiariosRecuperadosParaMostrar = queryDocumentSnapshots.getDocuments();

                if (listaDosCaixasDiariosRecuperadosParaMostrar.size() > 0) {
                    for (DocumentSnapshot lista : listaDosCaixasDiariosRecuperadosParaMostrar) {
                        firebaseFirestore.collection(nomeDaCollection).document(lista.getId()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {

                                ModeloMontanteDiario modeloMontanteDiarioRecuperado = documentSnapshot.toObject(ModeloMontanteDiario.class);

                                String valorQueOCaixaIniciouODia = modeloMontanteDiarioRecuperado.getValorQueOCaixaIniciouODia().replace(".", ",");
                                String totalDeVendasNoCaixaHoje = modeloMontanteDiarioRecuperado.getValorTotalDeVendasNaLojaDesseDia();
                                String totalDeVendasEmDinheiro = modeloMontanteDiarioRecuperado.getValorTotalDeVendasNoDinheiroDesseDia();
                                String totalDeSaidaCaixaHoje = modeloMontanteDiarioRecuperado.getValorTotalDeTrocoDesseDia().replace(".", ",");
                                String totalDeVendasNoCreditoHoje = modeloMontanteDiarioRecuperado.getValorTotalDeVendasNoCreditoDesseDia();
                                String totalDeVendasNoDebitoNoCaixHoje = modeloMontanteDiarioRecuperado.getValorTotalDeVendasNoDebitoDesseDia();
                                String valorTotalDeVendasNoIfoodDesseDia = modeloMontanteDiarioRecuperado.getValorTotalDeVendasNoIfoodDesseDia();
                                String valorTotalDeVendasEmGeralDesseDia = modeloMontanteDiarioRecuperado.getValorTotalDeVendasEmGeralDesseDia();
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


                                infoTextoDataDaUltimaAtualizacaoDoCaixaApresentandoParaOUsuario.setVisibility(View.VISIBLE);
                                infoTextoOCaixaIniciouODiaComOValor.setVisibility(View.VISIBLE);
                                infoTextoTotalNoCaixaHoje.setVisibility(View.VISIBLE);
                                infoTextoTotalDeEntradaEmDinheiroNoCaixaHoje.setVisibility(View.VISIBLE);
                                infoTextoTotalDeSaidaNoCaixaHoje.setVisibility(View.VISIBLE);
                                infoTextoTotalDeVendasNoCreditoHoje.setVisibility(View.VISIBLE);
                                infoTextoTotalDeVendasNoDebitoNoCaixaHoje.setVisibility(View.VISIBLE);


                                textoOCaixaIniciouODiaComOValor.setText(valorQueOCaixaIniciouODia);
                                textoTotalDeVendasNoDebitoNoCaixaHoje.setText(totalDeVendasNoDebitoNoCaixHoje);
                                textoTotalDeEntradaEmDinheiroNoCaixaHoje.setText(totalDeVendasEmDinheiro);
                                textoTotalDeVendasNoCreditoHoje.setText(totalDeVendasNoCreditoHoje);
                                textoTotalDeSaidaNoCaixaHoje.setText(totalDeSaidaCaixaHoje);
                                textoTotalNoCaixaHoje.setText(totalDeVendasNoCaixaHoje);
                                alertaCarregandoAsInformacoesDoCaixaDiario.dismiss();


                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });
                    }
                }


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });


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


        infoTextoDataDaUltimaAtualizacaoDoCaixaApresentandoParaOUsuario.setVisibility(View.VISIBLE);
        infoTextoOCaixaIniciouODiaComOValor.setVisibility(View.VISIBLE);
        infoTextoTotalNoCaixaHoje.setVisibility(View.VISIBLE);
        infoTextoTotalDeEntradaEmDinheiroNoCaixaHoje.setVisibility(View.VISIBLE);
        infoTextoTotalDeSaidaNoCaixaHoje.setVisibility(View.VISIBLE);
        infoTextoTotalDeVendasNoCreditoHoje.setVisibility(View.VISIBLE);
        infoTextoTotalDeVendasNoDebitoNoCaixaHoje.setVisibility(View.VISIBLE);


        try {


        } catch (Exception e) {
            Toast.makeText(getActivity(), "Erro inesperado, verifique sua conexão e tente novamente", Toast.LENGTH_SHORT).show();
        }


    }


    public void carregarComponentesDeTelaParaVizualizar() {

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

}

