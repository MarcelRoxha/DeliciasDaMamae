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
import com.marcel.a.n.roxha.deliciasdamamae.model.LoadingDialog;
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
    private String idRecuperadoMontanteCasoExista = "";
    private String idRecuperadoMontanteDiarioCasoExista = "";
    private String idRecuperadoCaixaDiarioCasoExista = "";
    private int numMesReferencia;
    private int verificaCaixaMensalCriado = 0;
    private final String COLLECTION_CAIXA_DIARIO = "CAIXA_DIARIO";
    private final String COLLECTION_MONTANTE_DESSE_MES = "MONTANTES_GERAL";

    private Date hoje = new Date();
    private Date dataCollectionReferenciaMontanteMensal = new Date();
    private String hojeString = "";
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
    private SimpleDateFormat simpleDateFormatCollectionReferenciaAtual = new SimpleDateFormat("MM/yy");




    //BANCO FIREBASE
    private FirebaseAuth auth = ConfiguracaoFirebase.getAuth();
    private FirebaseFirestore firebaseFirestore = ConfiguracaoFirebase.getFirestor();
    private CollectionReference referenceCaixaDiario = firebaseFirestore.collection(COLLECTION_CAIXA_DIARIO);
    private CollectionReference referenceMontanteDesseMes = firebaseFirestore.collection(COLLECTION_MONTANTE_DESSE_MES);



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

        carregarComponentesDeTelaParaNaoVisualizar();

        verificarMontanteCaixaCriado(keyUserRecuperado, progressDialogCarregandoAsInformacoesDoCaixaDiario);

        botaoParaCriarMontante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                verificaSeExisteMontanteCriadoAoTentarCriarUm(keyUserRecuperado);

            }
        });


        return viewCaixaFragment;


    }

    private void carregandoAsInformacoesDoCaixaDiario(String idUsuarioReferencia, AlertDialog.Builder alertaCarregandoAsInformacoesDoCaixaDiario) {

        //Verificando se existe montante criado




    }

    private  String recuperaUUIUsuario(){
      return   keyUserRecuperado =  auth.getCurrentUser().toString();
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


    public void verificaSeExisteMontanteCriadoAoTentarCriarUm(String idDoUsuario){


        dataCollectionReferenciaMontanteMensal = new Date();
        String colletionReferenciaVerificaMontante = simpleDateFormatCollectionReferenciaAtual.format(dataCollectionReferenciaMontanteMensal.getTime());

        String nomeCompletoColletion = COLLECTION_MONTANTE_DESSE_MES + "_" + colletionReferenciaVerificaMontante;

        firebaseFirestore.collection(idDoUsuario).whereEqualTo("mesReferenciaMontanteMensal",colletionReferenciaVerificaMontante)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                List<DocumentSnapshot> snapshotList = queryDocumentSnapshots.getDocuments();

                if(snapshotList.size() > 0) {
                    for (DocumentSnapshot snapshot : snapshotList) {
                        idRecuperadoMontanteDiarioCasoExista = snapshot.getId();
                    }
                    Toast.makeText(getActivity(), "ATENÇÃO JÁ EXISTE UM MONTANTE MENSAL CRIADO, VERIFIQUE A ABA FINANCEIRO PARA MAIS DETALHES", Toast.LENGTH_SHORT).show();

                }else{


                    AlertDialog.Builder alertaParaCriarUmMontante = new AlertDialog.Builder(getActivity());
                    View viewLayout = getLayoutInflater().inflate(R.layout.alerta_criar_um_montante_mensal, null);

                    EditText quantoIniciaraPositivoMontanteMensal = viewLayout.findViewById(R.id.input_com_quanto_o_montante_iniciara_positivo_id);
                    EditText quantoIniciaraNegativoMontanteMensal = viewLayout.findViewById(R.id.input_com_quanto_o_montante_iniciara_negativo_id);
                    TextView textoInformativoCriandoMontanteMensal = viewLayout.findViewById(R.id.texto_informativo_criando_montante_mensal_do_mes_de_id);

                    //CRIARNDO UM MONTANTE MENSAL DO MÊS DE:


                    alertaParaCriarUmMontante.setCancelable(false);

                    alertaParaCriarUmMontante.setView(quantoIniciaraPositivoMontanteMensal);
                    alertaParaCriarUmMontante.setView(quantoIniciaraNegativoMontanteMensal);
                    alertaParaCriarUmMontante.setView(textoInformativoCriandoMontanteMensal);
                    textoInformativoCriandoMontanteMensal.setText("CRIANDO UM MONTANTE MENSAL DO MÊS DE: " + colletionReferenciaVerificaMontante);
                    alertaParaCriarUmMontante.setView(viewLayout);

                    alertaParaCriarUmMontante.setPositiveButton("CRIAR MONTANTE", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).setNegativeButton("CANCELAR CRIAÇÃO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    alertaParaCriarUmMontante.show();
                    alertaParaCriarUmMontante.create();


                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });




    }

    private void verificarMontanteCaixaCriado(String idDoUsuario, AlertDialog progressDialogAlertaCarregandoInformacoesCaixaDiario) {
       // progressDialogCarregandoAsInformacoesDoCaixaDiario.setMessage("CARREGANDO AS INFORMAÇÕES.");

        dataCollectionReferenciaMontanteMensal = new Date();
        String colletionReferenciaVerificaMontante = simpleDateFormatCollectionReferenciaAtual.format(dataCollectionReferenciaMontanteMensal.getTime());

        String nomeCompletoColletion = COLLECTION_MONTANTE_DESSE_MES + "_" + colletionReferenciaVerificaMontante;

        firebaseFirestore.collection(idDoUsuario).whereEqualTo("mesReferenciaMontanteMensal",colletionReferenciaVerificaMontante)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                List<DocumentSnapshot> snapshotList = queryDocumentSnapshots.getDocuments();

                if(snapshotList.size() > 0){

                    for (DocumentSnapshot snapshot : snapshotList) {
                        idRecuperadoMontanteDiarioCasoExista = snapshot.getId();
                    }
                    firebaseFirestore.collection(idDoUsuario).document(nomeCompletoColletion).collection(idRecuperadoMontanteDiarioCasoExista)
                            .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                            List<DocumentSnapshot> snapshotListMontanteDiario = queryDocumentSnapshots.getDocuments();

                            if(snapshotListMontanteDiario.size() > 0){

                                //TEM MONTANTE DIARIO CRIADO
                                for (DocumentSnapshot snapshot : snapshotListMontanteDiario) {
                                    idRecuperadoMontanteDiarioCasoExista = snapshot.getId();
                                }
                                firebaseFirestore.collection(idDoUsuario).document(nomeCompletoColletion).collection(idRecuperadoMontanteDiarioCasoExista)
                                        .document(idRecuperadoMontanteDiarioCasoExista)
                                        .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {


                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                    }
                                });





                            }else{

                                //NÃO TEM MONTANTE DIARIO CRIADO



                            }


                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });


                }else{

                //    progressDialogCarregandoAsInformacoesDoCaixaDiario.setMessage("CARREGANDO AS INFORMAÇÕES.");


                    progressDialogAlertaCarregandoInformacoesCaixaDiario.dismiss();
                 //   progressDialogAlertaCarregandoInformacoesCaixaDiario.dismiss();
                    carregarComponentesDeTelaParaVizualizarQuandoNaoTemMontanteDiarioCriado();
                }


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });



    }

    private void carregarInformacoesMontanteExiste(String idRecuperadoMontante) {

        referenceMontanteDesseMes.document(idRecuperadoMontante).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });


    }

    @Override
    public void onStart() {
        super.onStart();


    }
}