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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
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
    private String dataReferenciaCaixaDiario = "";
    private String anoAtual = "";
    private Date dataHoje = new Date();
    private String idRecuperadoMontanteCasoExista = "";
    private String idRecuperadoMontanteDiarioCasoExista = "";
    private String idRecuperadoCaixaDiarioCasoExista = "";
    private String idRecuperadoMontanteColect = "";
    private int numMesReferencia;
    private int verificaCaixaMensalCriado = 0;
    private final String COLLECTION_CAIXA_DIARIO = "CAIXAS_DIARIO";

    private final String COLLECTION_MONTANTE_DESSE_MES = "MONTANTE_MENSAL";

    private final String COLLECTION_BOLOS_EXPOSTOS_VITRINE = "BOLOS_EXPOSTOS_VITRINE";

    private String nomeCompletoColletionMontanteMensal="";
    private String nomeCompletoColletionMontanteDiario="";
    private String mesReferenciaDesseMontante="";
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View viewCaixaFragment = inflater.inflate(R.layout.fragment_caixa, container, false);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        inflaterLayout = getActivity().getLayoutInflater();
        builder.setView(inflaterLayout.inflate(R.layout.progress_dialog_carregando_informacoes_caixa_diario, null));
        builder.setCancelable(true);

        this.progressDialogCarregandoAsInformacoesDoCaixaDiario = builder.create();
        this.progressDialogCarregandoAsInformacoesDoCaixaDiario.getWindow().setBackgroundDrawableResource(
                android.R.color.transparent
        );
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
                getActivity().finish();
            }
        });

        progressDialogCarregandoAsInformacoesDoCaixaDiario.dismiss();
        return viewCaixaFragment;


    }

    private void alertaOpaVendi() {
        AlertDialog.Builder alertaMostrarProdutosDaVetrinePoisFoiVendidoAlgo = new AlertDialog.Builder(getContext());
        alertaMostrarProdutosDaVetrinePoisFoiVendidoAlgo.setTitle("OPA VENDI");

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
                Intent intentAdicionarProdutoComoVendidoNoSistema = new Intent(getContext(), AdicionarProdutoComoVendidoNoSistema.class);
                intentAdicionarProdutoComoVendidoNoSistema.putExtra("itemKey", idRecuperadoDoBoloExpostoVitrine);
                startActivity(intentAdicionarProdutoComoVendidoNoSistema);

            }
        });

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

        firebaseFirestore.collection(nomeCompletoColletionMontanteMensal).whereEqualTo("mesReferenciaDesseMontante", mesReferenciaDesseMontante)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        List<DocumentSnapshot> listaDeMontanteParaVerificar = queryDocumentSnapshots.getDocuments();

                        if (listaDeMontanteParaVerificar.size() > 0) {

                            firebaseFirestore.collection(nomeCompletoColletionMontanteDiario).whereEqualTo("dataReferenciaMontanteDiarioDesseDia", dataReferenciaCaixaDiario)
                                    .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                        @Override
                                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                                            List<DocumentSnapshot> listaMontantesDiariosDesseMes = queryDocumentSnapshots.getDocuments();

                                            if (listaMontantesDiariosDesseMes.size() > 0) {
                                                progressDialogCarregandoAsInformacoesDoCaixaDiario.dismiss();
                                                alertaOpaVendi();
                                            }else{
                                                alertaNecessarioCriarOMontanteAoEfetuarUmaVenda("DIARIO");
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


    }

    private void iniciarCaixaDiario(AlertDialog progressDialogCarregandoAsInformacoesDoCaixaDiario) {

        firebaseFirestore.collection(nomeCompletoColletionMontanteMensal).whereEqualTo("mesReferenciaDesseMontante", mesReferenciaDesseMontante)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        List<DocumentSnapshot> listaDeMontanteParaVerificar = queryDocumentSnapshots.getDocuments();

                        if (listaDeMontanteParaVerificar.size() > 0) {
                        // Se cair nessa condição então eu já tenho um montante mensal criado então só preciso recuperar o último valor que o caixa terminou positivo

                           CollectionReference collectionReferenceMontantesDiarios =  firebaseFirestore.collection(nomeCompletoColletionMontanteDiario);
                            Query query = collectionReferenceMontantesDiarios.orderBy("dataReferenciaMontanteDiarioDesseDia", Query.Direction.DESCENDING).limit(1);

                            query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                    if (task.isSuccessful()) {
                                        if (!task.getResult().isEmpty()) {
                                            progressDialogCarregandoAsInformacoesDoCaixaDiario.dismiss();
                                            DocumentSnapshot document = task.getResult().getDocuments().get(0);
                                            String valorQueOCaixaFinalizou = document.getString("valorQueOCaixaFinalizou");
                                            String informativoDoValorDoDiaAnterior = "DEVIDO AOS RESULTADOS DE ONTEM O VALOR QUE DEVERIA SER INICIADO O CAIXA É R$: " + valorQueOCaixaFinalizou +
                                                    " PARA INICIAR COM ESSE VALOR CLIQUE EM CONFIRMAR";


                                            AlertDialog.Builder alertaCriarMontanteDiario = new AlertDialog.Builder(getActivity());
                                            LayoutInflater inflaterLayout = getActivity().getLayoutInflater();
                                            alertaCriarMontanteDiario.setView(inflaterLayout.inflate(R.layout.alerta_iniciando_o_dia_quando_tem_caixa_diario_do_dia_anterior, null));
                                            View view = getLayoutInflater().inflate(R.layout.alerta_iniciando_o_dia_quando_tem_caixa_diario_do_dia_anterior, null);
                                            EditText valorDigitadoPeloUsuario = view.findViewById(R.id.input_caixa_iniciou_o_dia_com_calculo_do_dia_anterior);
                                            TextView textoInformativoSobreOValorJaCalculado = view.findViewById(R.id.texto_informativo_iniciando_o_dia_quando_tem_dia_anterior);

                                            textoInformativoSobreOValorJaCalculado.setText(informativoDoValorDoDiaAnterior);
                                            alertaCriarMontanteDiario.setView(view);

                                            alertaCriarMontanteDiario.setPositiveButton("CONFIRMAR", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {

                                                    String valorDigitado = valorDigitadoPeloUsuario.getText().toString().replace(",", ".");

                                                    if(valorDigitado.isEmpty() || valorDigitado.equals("")){
                                                        String valorDigitadoParaIniciarOMontateDesseMes = valorQueOCaixaFinalizou.replace(",", ".");
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

                                                    }else if(valorDigitado.equals("0") || valorDigitado != null){
                                                        String valorDigitadoParaIniciarOMontateDesseMes = valorDigitado;
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
                                                    }else{
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
                                            alertaCriarMontanteDiario.create();
                                            alertaCriarMontanteDiario.show();

                                        } else {

                                            firebaseFirestore.collection(nomeCompletoColletionMontanteMensal).whereEqualTo("mesReferenciaDesseMontante",mesReferenciaDesseMontante)
                                                    .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                                        @Override
                                                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                                                            List<DocumentSnapshot> listaVerificaSePrecisaCriarMontanteMensal = queryDocumentSnapshots.getDocuments();

                                                            if(listaVerificaSePrecisaCriarMontanteMensal.size() > 0){
                                                                //Precisa criar apenas o montante diario
                                                                String informativoDoValorQueOCaixaDiarioIraIniciar = "DIGITE ABAIXO O VALOR QUE O CAIXA DE HOJE IRÁ INICIAR";
                                                                String informativoExDeValoresValidos = "DIGITE UM VALOR VALIDO E ACIMA DE 0 EX: 100,00 OU 100.00";
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

                                                                       if(valorDigitado.equals("0") || valorDigitado != null || !valorDigitado.isEmpty()){
                                                                            String valorDigitadoParaIniciarOMontateDesseMes = valorDigitado;
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
                                                                        }else{
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
                                                                alertaCriarMontanteDiario.create();
                                                                alertaCriarMontanteDiario.show();

                                                            }else{
                                                                String informativoDoValorQueOCaixaDiarioIraIniciar = "DIGITE ABAIXO O VALOR QUE O CAIXA DE HOJE IRÁ INICIAR ESSE VALOR SERÁ CONTABILIZADO COMO O VALOR INICIAL DO MÊS TAMBÉM";
                                                                String informativoExDeValoresValidos = "DIGITE UM VALOR VALIDO E ACIMA DE 0 EX: 100,00 OU 100.00";
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

                                                                        if(valorDigitado.equals("0") || valorDigitado != null || !valorDigitado.isEmpty()){
                                                                            String valorDigitadoParaIniciarOMontateDesseMes = valorDigitado;
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
                                                                        }else{
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
                                                    }).addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            Toast.makeText(getContext(), "Verifique a conexão e tente novamente mais tarde!", Toast.LENGTH_SHORT).show();
                                                            Log.i("Erro no inicia caixa diario criado", e.getMessage());
                                                        }
                                                    });

                                            // A coleção está vazia
                                        }
                                    } else {
                                        // Trate os erros aqui
                                    }

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getContext(), "Verifique a conexão e tente novamente mais tarde!", Toast.LENGTH_SHORT).show();
                                    Log.i("Erro no inicia caixa diario criado", e.getMessage());
                                }
                            });


                        } else {
                            Toast.makeText(getContext(), "ATENÇÃO JÁ FOI IDENTIFICADO UM MONTANTE CRIADO HOJE, NÃO É POSSÍVEL CRIAR DOIS MONTANTE NO MESMO DIA!", Toast.LENGTH_SHORT).show();
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

    private void verificaSeJaTemCaixaDiarioCriado(AlertDialog progressDialogCarregandoAsInformacoesDoCaixaDiario, String nomeCollectionCaixaDiario) {

        firebaseFirestore.collection(nomeCollectionCaixaDiario).whereEqualTo("dataReferenciaMontanteDiarioDesseDia", diaAtual)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        List<DocumentSnapshot> snapshotList = queryDocumentSnapshots.getDocuments();

                        if (snapshotList.size() > 0) {
                            //Significa que existe o documento com a data de hoje então já tem um caixa diario referente a esse dia
                            for (DocumentSnapshot listaCaixasDiariosCriados : snapshotList) {
                                firebaseFirestore.collection(nomeCollectionCaixaDiario).document(listaCaixasDiariosCriados.getId())
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

