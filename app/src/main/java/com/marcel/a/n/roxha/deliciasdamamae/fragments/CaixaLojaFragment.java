package com.marcel.a.n.roxha.deliciasdamamae.fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.OptIn;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.marcel.a.n.roxha.deliciasdamamae.R;
import com.marcel.a.n.roxha.deliciasdamamae.activity.LojaActivityV2;
import com.marcel.a.n.roxha.deliciasdamamae.config.ConfiguracaoFirebase;
import com.marcel.a.n.roxha.deliciasdamamae.helper.ModeloMontanteDiarioDAO;
import com.marcel.a.n.roxha.deliciasdamamae.helper.ModeloMontanteMensalDAO;
import com.marcel.a.n.roxha.deliciasdamamae.model.LoadingDialog;
import com.marcel.a.n.roxha.deliciasdamamae.model.ModeloMontanteDiario;
import com.marcel.a.n.roxha.deliciasdamamae.model.ModeloMontanteMensalLoja;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
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


    //CARREGAR AS INFORMAÇÕES DO USUARIO COMO TOKEN.



    //COMPONENTES DE TELA

    //Texto
    private TextView textoDataDaUltimaAtualizacaoDoCaixaApresentandoParaOUsuario;
    private TextView textoOCaixaIniciouODiaComOValor;
    private TextView textoTotalNoCaixaHoje;
    private TextView textoTotalDeEntradaEmDinheiroNoCaixaHoje;
    private TextView textoTotalDeSaidaNoCaixaHoje;
    private TextView textoTotalDeVendasNoCreditoHoje;
    private TextView textoTotalDeVendasNoDebitoNoCaixaHoje;
    private TextView textoInformativoSobreEssaAbaCaixa;


    //TextoInfo
    private TextView infoTextoDataDaUltimaAtualizacaoDoCaixaApresentandoParaOUsuario;
    private TextView infoTextoOCaixaIniciouODiaComOValor;
    private TextView infoTextoTotalNoCaixaHoje;
    private TextView infoTextoTotalDeEntradaEmDinheiroNoCaixaHoje;
    private TextView infoTextoTotalDeSaidaNoCaixaHoje;
    private TextView infoTextoTotalDeVendasNoCreditoHoje;
    private TextView infoTextoTotalDeVendasNoDebitoNoCaixaHoje;
    private TextView infoTextoInformativoSobreEssaAbaCaixa;


    //Botões
    private Button botaoParaIniciarOdia;
    private Button botaoParaCriarMontante;
    private Button botaoOpaVendi;
    private Button botaoVendasFeitas;
    private Button botaoFinalizarDia;

    //Variaveis globais
    private String keyUserRecuperado = "";
    private String mesAtual = "";
    private String diaAtual = "";
    private String anoAtual = "";
    private String idRecuperadoMontanteCasoExista = "";
    private String idRecuperadoMontanteDiarioCasoExista = "";
    private String idRecuperadoCaixaDiarioCasoExista = "";
    private String idRecuperadoMontanteColect = "";
    private int numMesReferencia;
    private int verificaCaixaMensalCriado = 0;
    private final String COLLECTION_CAIXA_DIARIO= "CAIXAS_DIARIO";
    private final String COLLECTION_CAIXA_DIARIO_COMPLETO = "CAIXA_DIARIO_ANO";
    private final String COLLECTION_MONTANTE_DESSE_MES = "MONTANTE_MENSAL";
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

       keyUserRecuperado =  auth.getCurrentUser().toString();
        Date dataHoje = new Date();

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

        infoTextoDataDaUltimaAtualizacaoDoCaixaApresentandoParaOUsuario = viewCaixaFragment.findViewById(R.id.textoInfoDataUltimaAtualizacaoFragmentCaixa);
        infoTextoOCaixaIniciouODiaComOValor = viewCaixaFragment.findViewById(R.id.textViewcaixa95);
        infoTextoTotalNoCaixaHoje = viewCaixaFragment.findViewById(R.id.textViewcaixa90);
        infoTextoTotalDeEntradaEmDinheiroNoCaixaHoje = viewCaixaFragment.findViewById(R.id.textViewcaixa91);
        infoTextoTotalDeSaidaNoCaixaHoje = viewCaixaFragment.findViewById(R.id.textViewcaixa92);
        infoTextoTotalDeVendasNoCreditoHoje = viewCaixaFragment.findViewById(R.id.textViewcaixa97);
        infoTextoTotalDeVendasNoDebitoNoCaixaHoje = viewCaixaFragment.findViewById(R.id.textViewcaixa98);

        botaoParaIniciarOdia = viewCaixaFragment.findViewById(R.id.botao_iniciar_o_dia_boleria_id);
        botaoParaCriarMontante = viewCaixaFragment.findViewById(R.id.botao_criar_montante_mensal_id);
        botaoOpaVendi = viewCaixaFragment.findViewById(R.id.botao_vendi_alguma_coisa_id);
        botaoVendasFeitas = viewCaixaFragment.findViewById(R.id.botao_ver_vendas_id);
        botaoFinalizarDia = viewCaixaFragment.findViewById(R.id.botao_finalizar_dia_id);

        hoje = new Date();
        hojeString = simpleDateFormat.format(hoje.getTime());
        textoDataDaUltimaAtualizacaoDoCaixaApresentandoParaOUsuario.setText(hojeString);
        numMesReferencia = 1 + hoje.getMonth();

        diaAtual = simpleDateFormatCollectionReferenciaAtualModeloCaixaDiario.format(hoje.getTime());
        System.out.println("diaAtual = " + diaAtual);


        carregarComponentesDeTelaParaNaoVisualizar();

        verificaCaixaDiarioCriado(progressDialogCarregandoAsInformacoesDoCaixaDiario);

//        verificarMontanteCaixaCriado(keyUserRecuperado, progressDialogCarregandoAsInformacoesDoCaixaDiario);
        botaoParaCriarMontante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialogCarregandoAsInformacoesDoCaixaDiario.show();
                criarMontanteInicialMes(progressDialogCarregandoAsInformacoesDoCaixaDiario);

            }
        });
        botaoParaIniciarOdia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialogCarregandoAsInformacoesDoCaixaDiario.show();
                iniciarCaixaDiario(progressDialogCarregandoAsInformacoesDoCaixaDiario);

            }
        });



        return viewCaixaFragment;


    }

    private void criarMontanteInicialMes(AlertDialog progressDialogCarregandoAsInformacoesDoCaixaDiario){

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

                if(snapshotList.size() > 0){
                    System.out.println("Entrou no lista maior que zero");
                    for(DocumentSnapshot listaCaixasDiariosCriados: snapshotList){
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
                }else{

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





    private void iniciarCaixaDiario(AlertDialog progressDialogCarregandoAsInformacoesDoCaixaDiario){


        Date dataHoje = new Date();
        System.out.println("dataHoje = " + dataHoje.toString());
        diaAtual = simpleDateFormatCollectionReferenciaAtualModeloCaixaDiario.format(dataHoje);

        System.out.println("DIA ATUAL = " + diaAtual);

        String nomeCompletoColletion = COLLECTION_CAIXA_DIARIO;
        System.out.println("nomeCompletoColletion" + nomeCompletoColletion);
        firebaseFirestore.collection(nomeCompletoColletion).whereEqualTo("dataReferenciaMontanteDiarioDesseDia", diaAtual)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> snapshotList = queryDocumentSnapshots.getDocuments();

                if(snapshotList.size() > 0){
                    progressDialogCarregandoAsInformacoesDoCaixaDiario.dismiss();
                    for(DocumentSnapshot listaCaixasDiariosCriados: snapshotList){
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
                                        Toast.makeText(getContext(), "Obrigado", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                alertaMontanteDiarioJaCriado.show();
                                alertaMontanteDiarioJaCriado.create();



//                                carregandoAsInformacoesDoCaixaDiario(progressDialogCarregandoAsInformacoesDoCaixaDiario,valorQueOCaixaIniciouODia, totalDeVendasNoCaixaHoje, totalDeVendasEmDinheiro,
//                                        totalDeSaidaCaixaHoje, totalDeVendasNoCreditoHoje, totalDeVendasNoDebitoNoCaixHoje, valorTotalDeVendasNoIfoodDesseDia, valorTotalDeVendasEmGeralDesseDia);


                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });
                    }

                }else {


                    progressDialogCarregandoAsInformacoesDoCaixaDiario.dismiss();
                    AlertDialog.Builder alertaCriarMontanteMensal = new AlertDialog.Builder(getActivity());
                    LayoutInflater inflaterLayout = getActivity().getLayoutInflater();
                    alertaCriarMontanteMensal.setView(inflaterLayout.inflate(R.layout.alerta_iniciando_o_dia, null));
                    View view = getLayoutInflater().inflate(R.layout.alerta_iniciando_o_dia, null);

                    EditText valorDigitadoPeloUsuario = view.findViewById(R.id.input_caixa_iniciou_com);
                    alertaCriarMontanteMensal.setView(view);

                    alertaCriarMontanteMensal.setPositiveButton("CONFIRMAR", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {


                            String valorDigitadoParaIniciarOMontateDesseMes = valorDigitadoPeloUsuario.getText().toString().replace(",", ".");
                            ModeloMontanteDiario modeloMontanteDiarioIniciado = new ModeloMontanteDiario();
                            ModeloMontanteDiarioDAO modeloMontanteMensalDAOSendoIniciadoDesseMes = new ModeloMontanteDiarioDAO(getContext());
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


                            modeloMontanteMensalDAOSendoIniciadoDesseMes.modeloMontanteDiarioIniciandoODia(modeloMontanteDiarioIniciado);

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

        carregarComponentesDeTelaParaVizualizar();

    }




    private void verificaCaixaDiarioCriado(AlertDialog progressDialogCarregandoAsInformacoesDoCaixaDiario) {
        Date dataHoje = new Date();
        System.out.println("dataHoje = " + dataHoje.toString());
        diaAtual = simpleDateFormatCollectionReferenciaAtualModeloCaixaDiario.format(dataHoje);
        anoAtual = simpleDateFormatCollectionReferenciaAnoAtual.format(dataHoje);
        System.out.println("ANO ATUAL = " + anoAtual);
        System.out.println("DIA ATUAL = " + diaAtual);
        int mesReferenciaVerifica = dataHoje.getMonth() + 1;
//        String mesReferenciaBanco = String.valueOf(mesReferenciaVerifica);
        String nomeCompletoColletion = COLLECTION_CAIXA_DIARIO + "_" + anoAtual;
        System.out.println("nomeCompletoColletion" + nomeCompletoColletion);
        firebaseFirestore.collection(nomeCompletoColletion).whereEqualTo("dataReferenciaMontanteDiarioDesseDia", diaAtual)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                System.out.println("antes do snapshotList");
                List<DocumentSnapshot> snapshotList = queryDocumentSnapshots.getDocuments();

                if(snapshotList.size() > 0){
                    System.out.println("Dentro do if maior que zero");
                    for(DocumentSnapshot listaCaixasDiariosCriados: snapshotList){
                        firebaseFirestore.collection(nomeCompletoColletion).document(listaCaixasDiariosCriados.getId())
                                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                System.out.println("Dentro achou documento com a data atual");
                                ModeloMontanteDiario modeloMontanteDiarioRecuperadoJaCriado = documentSnapshot.toObject(ModeloMontanteDiario.class);
                                String valorQueOCaixaIniciouODia = modeloMontanteDiarioRecuperadoJaCriado.getValorQueOCaixaIniciouODia();
                                String totalDeVendasNoCaixaHoje = modeloMontanteDiarioRecuperadoJaCriado.getValorTotalDeVendasNaLojaDesseDia();
                                String totalDeVendasEmDinheiro = modeloMontanteDiarioRecuperadoJaCriado.getValorTotalDeVendasNoDinheiroDesseDia();
                                String totalDeSaidaCaixaHoje = modeloMontanteDiarioRecuperadoJaCriado.getValorTotalDeTrocoDesseDia();
                                String totalDeVendasNoCreditoHoje = modeloMontanteDiarioRecuperadoJaCriado.getValorTotalDeVendasNoCreditoDesseDia();
                                String totalDeVendasNoDebitoNoCaixHoje = modeloMontanteDiarioRecuperadoJaCriado.getValorTotalDeVendasNoDebitoDesseDia();
                                String valorTotalDeVendasNoIfoodDesseDia = modeloMontanteDiarioRecuperadoJaCriado.getValorTotalDeVendasNoIfoodDesseDia();
                                String valorTotalDeVendasEmGeralDesseDia = modeloMontanteDiarioRecuperadoJaCriado.getValorTotalDeVendasEmGeralDesseDia();
//                                carregandoAsInformacoesDoCaixaDiario(progressDialogCarregandoAsInformacoesDoCaixaDiario,valorQueOCaixaIniciouODia, totalDeVendasNoCaixaHoje, totalDeVendasEmDinheiro,
//                                        totalDeSaidaCaixaHoje, totalDeVendasNoCreditoHoje, totalDeVendasNoDebitoNoCaixHoje, valorTotalDeVendasNoIfoodDesseDia, valorTotalDeVendasEmGeralDesseDia);


                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });
                    }

                }else {
                    System.out.println("PASSOU PELO ELSE SEM MONTANTE DIÁRIO CRIADO");
                    Date verificarSeJaAbriu = new Date();
                    SimpleDateFormat formatarVerificaSeJaAbriu = new SimpleDateFormat("HH");
                    String horaAtual = formatarVerificaSeJaAbriu.format(verificarSeJaAbriu);
                    int horaAtualConvertida = Integer.valueOf(horaAtual);





                    String caixaInicouCom = "0";
                    String totalNoCaixaHoje = "0";
                    String totalDeVendasEmDinheiro = "0";
                    String totalDeSaidaCaixaHoje = "0";
                    String totalDeVendasNoCreditoHoje = "0";
                    String totalDeVendasNoDebitoNoCaixHoje = "0";
                    String informativoDessaAba = "NA ABA CAIXA, VOCÊ GERENCIA AS ATIVIDADES DIARIA DA LOJINHA: \n*-INICIE O DIA PARA INICIAR O PROCESSO DE CONTABILIZAÇÃO DIÁRIA";


                    carregandoAsInformacoesDoCaixaDiario(progressDialogCarregandoAsInformacoesDoCaixaDiario,caixaInicouCom, totalNoCaixaHoje,
                            totalDeVendasEmDinheiro, totalDeSaidaCaixaHoje, totalDeVendasNoCreditoHoje, totalDeVendasNoDebitoNoCaixHoje, informativoDessaAba, "0", informativoDessaAba);

//                    System.out.println("ENTROU NO NÃO TEM MONTANTE CRIADO COM ESSE NOME" + nomeCompletoColletion);
//                    botaoFinalizarDia.setVisibility(View.VISIBLE);
//                    botaoVendasFeitas.setVisibility(View.VISIBLE);
//                    botaoOpaVendi.setVisibility(View.VISIBLE);
//                    botaoParaCriarMontante.setVisibility(View.VISIBLE);
//                    botaoParaIniciarOdia.setVisibility(View.VISIBLE);
//                    textoInformativoSobreEssaAbaCaixa.setVisibility(View.VISIBLE);
//
//
//                    textoTotalDeVendasNoDebitoNoCaixaHoje.setVisibility(View.VISIBLE);
//                    textoTotalDeVendasNoCreditoHoje.setVisibility(View.VISIBLE);
//                    textoTotalDeSaidaNoCaixaHoje.setVisibility(View.VISIBLE);
//                    textoTotalDeEntradaEmDinheiroNoCaixaHoje.setVisibility(View.VISIBLE);
//                    textoTotalNoCaixaHoje.setVisibility(View.VISIBLE);
//                    textoOCaixaIniciouODiaComOValor.setVisibility(View.VISIBLE);
//                    textoDataDaUltimaAtualizacaoDoCaixaApresentandoParaOUsuario.setVisibility(View.VISIBLE);
//
//
//                    infoTextoDataDaUltimaAtualizacaoDoCaixaApresentandoParaOUsuario.setVisibility(View.VISIBLE);
//                    infoTextoOCaixaIniciouODiaComOValor.setVisibility(View.VISIBLE);
//                    infoTextoTotalNoCaixaHoje.setVisibility(View.VISIBLE);
//                    infoTextoTotalDeEntradaEmDinheiroNoCaixaHoje.setVisibility(View.VISIBLE);
//                    infoTextoTotalDeSaidaNoCaixaHoje.setVisibility(View.VISIBLE);
//                    infoTextoTotalDeVendasNoCreditoHoje.setVisibility(View.VISIBLE);
//                    infoTextoTotalDeVendasNoDebitoNoCaixaHoje.setVisibility(View.VISIBLE);
//
//
//                    textoInformativoSobreEssaAbaCaixa.setText("NA ABA CAIXA, VOCÊ GERENCIA AS ATIVIDADES DIARIA DA LOJINHA: \n*-INICIE O DIA PARA INICIAR O PROCESSO DE CONTABILIZAÇÃO DIÁRIA");
//                    textoTotalDeVendasNoDebitoNoCaixaHoje.setText("0");
//                    textoTotalDeEntradaEmDinheiroNoCaixaHoje.setText("0");
//                    textoTotalDeVendasNoCreditoHoje.setText("0");
//                    textoTotalDeSaidaNoCaixaHoje.setText("0");
//                    textoTotalNoCaixaHoje.setText("0");
//                    textoOCaixaIniciouODiaComOValor.setText("0");
//                    progressDialogCarregandoAsInformacoesDoCaixaDiario.dismiss();



                }



            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

        carregarComponentesDeTelaParaVizualizar();

    }


    private void carregandoAsInformacoesDoCaixaDiario(AlertDialog alertaCarregandoAsInformacoesDoCaixaDiario, String caixaInicouCom,
                                                      String totalNoCaixaHoje, String totalDeVendasEmDinheiro, String totalDeSaidaCaixaHoje,
                                                      String totalDeVendasNoCreditoHoje, String totalDeVendasNoDebitoNoCaixHoje, String valorTotalDeVendasNoIfoodDesseDia, String valorTotalDeVendasEmGeralDesseDia, String textoInformativoDessaAba) {

                    botaoFinalizarDia.setVisibility(View.VISIBLE);
                    botaoVendasFeitas.setVisibility(View.VISIBLE);
                    botaoOpaVendi.setVisibility(View.VISIBLE);
                    botaoParaCriarMontante.setVisibility(View.VISIBLE);
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


//                    textoInformativoSobreEssaAbaCaixa.setText("NA ABA CAIXA, VOCÊ GERENCIA AS ATIVIDADES DIARIA DA LOJINHA: \n*-INICIE O DIA PARA INICIAR O PROCESSO DE CONTABILIZAÇÃO DIÁRIA");
//                    textoTotalDeVendasNoDebitoNoCaixaHoje.setText("0");
//                    textoTotalDeEntradaEmDinheiroNoCaixaHoje.setText("0");
//                    textoTotalDeVendasNoCreditoHoje.setText("0");
//                    textoTotalDeSaidaNoCaixaHoje.setText("0");
//                    textoTotalNoCaixaHoje.setText("0");
//                    textoOCaixaIniciouODiaComOValor.setText("0");
//                    progressDialogCarregandoAsInformacoesDoCaixaDiario.dismiss();

//
//        textoInformativoSobreEssaAbaCaixa.setText("NA ABA CAIXA, VOCÊ GERENCIA AS ATIVIDADES DIARIA DA LOJINHA: \n*-INICIE O DIA PARA INICIAR O PROCESSO DE CONTABILIZAÇÃO DIÁRIA");
//        textoTotalDeVendasNoDebitoNoCaixaHoje.setText("0");
//        textoTotalDeEntradaEmDinheiroNoCaixaHoje.setText("0");
//        textoTotalDeVendasNoCreditoHoje.setText("0");
//        textoTotalDeSaidaNoCaixaHoje.setText("0");
//        textoTotalNoCaixaHoje.setText("0");
//        textoOCaixaIniciouODiaComOValor.setText("0");



        try{
            textoOCaixaIniciouODiaComOValor.setText(caixaInicouCom);
            textoInformativoSobreEssaAbaCaixa.setText(textoInformativoDessaAba);
            textoTotalDeVendasNoDebitoNoCaixaHoje.setText(totalDeVendasNoDebitoNoCaixHoje);
            textoTotalDeEntradaEmDinheiroNoCaixaHoje.setText(totalDeVendasEmDinheiro);
            textoTotalDeVendasNoCreditoHoje.setText(totalDeVendasNoCreditoHoje);
            textoTotalDeSaidaNoCaixaHoje.setText(totalDeSaidaCaixaHoje);
            textoTotalNoCaixaHoje.setText(totalNoCaixaHoje);
            alertaCarregandoAsInformacoesDoCaixaDiario.dismiss();

        }catch (Exception e){
            Toast.makeText(getActivity(), "Erro inesperado, verifique sua conexão e tente novamente", Toast.LENGTH_SHORT).show();
        }







    }



    public void carregarComponentesDeTelaParaVizualizar(){

        botaoFinalizarDia.setVisibility(View.VISIBLE);
        botaoVendasFeitas.setVisibility(View.VISIBLE);
        botaoOpaVendi.setVisibility(View.VISIBLE);
        botaoParaCriarMontante.setVisibility(View.VISIBLE);
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

    }


    public void carregarComponentesDeTelaParaVizualizarQuandoNaoTemMontanteDiarioCriado(){

        botaoFinalizarDia.setVisibility(View.VISIBLE);
        botaoVendasFeitas.setVisibility(View.VISIBLE);
        botaoOpaVendi.setVisibility(View.VISIBLE);
        botaoParaCriarMontante.setVisibility(View.VISIBLE);
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


        textoInformativoSobreEssaAbaCaixa.setText("NA ABA CAIXA, VOCÊ GERENCIA AS ATIVIDADES DIARIA DA LOJINHA: \n  *-CRIE UM MONTANTE PARA INICIAR O PROCESSO DE CONTABILIZACÃO MENSAL \n *-INICIE O DIA PARA INICIAR O PROCESSO DE CONTABILIZAÇÃO DIÁRIA");
        textoTotalDeVendasNoDebitoNoCaixaHoje.setText("0");
        textoTotalDeEntradaEmDinheiroNoCaixaHoje.setText("0");
        textoTotalDeVendasNoCreditoHoje.setText("0");
        textoTotalDeSaidaNoCaixaHoje.setText("0");
        textoTotalNoCaixaHoje.setText("0");
        textoOCaixaIniciouODiaComOValor.setText("0");

    }


    public void carregarComponentesDeTelaParaNaoVisualizar(){


        botaoFinalizarDia.setVisibility(View.GONE);
        botaoVendasFeitas.setVisibility(View.GONE);
        botaoOpaVendi.setVisibility(View.GONE);
        botaoParaCriarMontante.setVisibility(View.GONE);
        botaoParaIniciarOdia.setVisibility(View.GONE);
        textoInformativoSobreEssaAbaCaixa.setVisibility(View.GONE);
        textoTotalDeVendasNoDebitoNoCaixaHoje.setVisibility(View.GONE);
        textoTotalDeVendasNoCreditoHoje.setVisibility(View.GONE);
        textoTotalDeSaidaNoCaixaHoje.setVisibility(View.GONE);
        textoTotalDeEntradaEmDinheiroNoCaixaHoje.setVisibility(View.GONE);
        textoTotalNoCaixaHoje.setVisibility(View.GONE);
        textoOCaixaIniciouODiaComOValor.setVisibility(View.GONE);
        textoDataDaUltimaAtualizacaoDoCaixaApresentandoParaOUsuario.setVisibility(View.GONE);


        infoTextoDataDaUltimaAtualizacaoDoCaixaApresentandoParaOUsuario.setVisibility(View.GONE);
        infoTextoOCaixaIniciouODiaComOValor.setVisibility(View.GONE);
        infoTextoTotalNoCaixaHoje.setVisibility(View.GONE);
        infoTextoTotalDeEntradaEmDinheiroNoCaixaHoje.setVisibility(View.GONE);
        infoTextoTotalDeSaidaNoCaixaHoje.setVisibility(View.GONE);
        infoTextoTotalDeVendasNoCreditoHoje.setVisibility(View.GONE);
        infoTextoTotalDeVendasNoDebitoNoCaixaHoje.setVisibility(View.GONE);

    }





    @Override
    public void onStart() {
        super.onStart();


    }
}