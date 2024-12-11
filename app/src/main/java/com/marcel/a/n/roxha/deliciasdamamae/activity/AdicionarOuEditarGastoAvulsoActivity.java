package com.marcel.a.n.roxha.deliciasdamamae.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.marcel.a.n.roxha.deliciasdamamae.R;
import com.marcel.a.n.roxha.deliciasdamamae.config.ConfiguracaoFirebase;
import com.marcel.a.n.roxha.deliciasdamamae.controller.helper.ModeloGastosAvulsosDAO;
import com.marcel.a.n.roxha.deliciasdamamae.controller.helper.ModeloMontanteMensalDAO;
import com.marcel.a.n.roxha.deliciasdamamae.model.ModeloGastosAvulsos;
import com.marcel.a.n.roxha.deliciasdamamae.model.ModeloMontanteMensalLoja;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class AdicionarOuEditarGastoAvulsoActivity extends AppCompatActivity {
    private Button botaoVoltar, botaoCadastrar;
    private EditText nomeGastoAvulso, valorDesseGastoAvulso, diaDeCobrancaDesseGastoAvulso;

    private ModeloGastosAvulsos modeloGastosAvulsosCadastrar, modeloGastosAvulsosAtualizar;

    private ModeloGastosAvulsosDAO modeloGastosAvulsosDAO;


    private String mensagemDeCamposInvalidos = "";

    private String diaAtual, mesAtual, anoAtual, dataCompara, montaData;
    private Date dataHoje = new Date();
    private SimpleDateFormat formataDataParaRegistro = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
    private SimpleDateFormat formataDataAtual = new SimpleDateFormat("dd/MM/yy");

    private FirebaseFirestore firestore = ConfiguracaoFirebase.getFirestor();
    private CollectionReference referenceMontante;
    private final String COLLECTION_MONTANTE_DESSE_MES = "MONTANTE_MENSAL";

    private String nomeCompletoColletionMontanteMensal = "";

    private SimpleDateFormat mesFormatoData = new SimpleDateFormat("MM");
    private SimpleDateFormat anoFormatoData = new SimpleDateFormat("yyyy");

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setTheme(R.style.Theme_DeliciasDaMamae);
        setContentView(R.layout.activity_adicionar_gasto_avulso);

        botaoVoltar = findViewById(R.id.botaoCancelaOCadastroGastoFixoId);
        botaoCadastrar = findViewById(R.id.botaoCadastrarGastoFixoId);

        nomeGastoAvulso = findViewById(R.id.inputNomeGastoAvulsoId);
        valorDesseGastoAvulso = findViewById(R.id.inputValorGastoAvulsoId);
        diaDeCobrancaDesseGastoAvulso = findViewById(R.id.inputDiaCobradoDoGastoAvulsoId);

        mesAtual = mesFormatoData.format(dataHoje);
        anoAtual = anoFormatoData.format(dataHoje);
        dataCompara = formataDataAtual.format(dataHoje);

        montaData = mesAtual+"_"+anoAtual;
        nomeCompletoColletionMontanteMensal =COLLECTION_MONTANTE_DESSE_MES +"_"+ montaData;

       // verificaMontanteMensalCriado(nomeCompletoColletionMontanteMensal);

       diaAtual = formataDataParaRegistro.format(dataHoje);

        diaDeCobrancaDesseGastoAvulso.setText(diaAtual);

        botaoCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nomeDigitado = nomeGastoAvulso.getText().toString();
                String valorDigitado = valorDesseGastoAvulso.getText().toString();
                if(!nomeDigitado.isEmpty() && nomeDigitado.length() > 2){
                    if(!valorDigitado.isEmpty() && !valorDigitado.equals("0") & !valorDigitado.equals("00")){
                        modeloGastosAvulsosDAO = new ModeloGastosAvulsosDAO(AdicionarOuEditarGastoAvulsoActivity.this) ;
                        double valorDigitadoConvertido = Double.parseDouble(valorDigitado);

                        modeloGastosAvulsosCadastrar = new ModeloGastosAvulsos();
                        modeloGastosAvulsosCadastrar.setNomeDoGastoAvulsos(nomeDigitado);
                        modeloGastosAvulsosCadastrar.setValorGastoAvulsos(valorDigitadoConvertido);
                        modeloGastosAvulsosCadastrar.setDataDeRegistroGasto(diaAtual);
                        modeloGastosAvulsosCadastrar.setDataPagamentoAvulsos(montaData);
                        System.out.println("modeloGastosFixosCadastra " + modeloGastosAvulsosCadastrar.toString());
                        System.out.println("nomeCompletoColletionMontanteMensal " + nomeCompletoColletionMontanteMensal);

                        verificaMontanteMensalCriado(nomeCompletoColletionMontanteMensal, modeloGastosAvulsosCadastrar);



                    }else {
                        mensagemDeCamposInvalidos = "Valor Digitado é invalido, não pode ser 0 nem 00 precisa ser um valor que seja passível de cálculo";
                    }

                }else{
                    mensagemDeCamposInvalidos = "Nome digitado inválido, não pode ser vazio e nem sigla";
                }

//                System.out.println("nome " + nomeDigitado + "\nvalor " + valorDigitado + "\ndia " + diaDigitado);


            }
        });

        botaoVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void verificaMontanteMensalCriado(String nomeCompletoColletionMontanteMensal, ModeloGastosAvulsos modeloGastosAvulsos){

        referenceMontante = firestore.collection(nomeCompletoColletionMontanteMensal);
        System.out.println("nomeCompletoColletionMontanteMensal " + nomeCompletoColletionMontanteMensal);
        System.out.println("montaData " + montaData);
        referenceMontante.whereEqualTo("mesReferenciaDesseMontante", montaData).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> listaMontantes = queryDocumentSnapshots.getDocuments();

                        if(listaMontantes.size() > 0){
                            System.out.println("maior que zero ");
                            for(DocumentSnapshot list: listaMontantes){
                                String idRecuperadoMontante = list.getId();

                                referenceMontante.document(idRecuperadoMontante).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                        ModeloMontanteMensalLoja modeloMontanteMensalLoja = documentSnapshot.toObject(ModeloMontanteMensalLoja.class);
                                        String valorTotalGastoAvulso = "";
                                        String quantidadeDinheiro = modeloMontanteMensalLoja.getQuantoDinheiroSaiuEsseMes().replace(",", ".");

                                        if(modeloMontanteMensalLoja.getValorTotalGastoAvulsoDesseMes() != null){

                                            valorTotalGastoAvulso = modeloMontanteMensalLoja.getValorTotalGastoAvulsoDesseMes().replace(",", ".");

                                        }else{
                                            valorTotalGastoAvulso = "0";
                                        }

                                        double quantidadeDinheiroConvert = Double.parseDouble(quantidadeDinheiro);
                                        double valorTotalGastoAvulsoConvert = Double.parseDouble(valorTotalGastoAvulso);

                                        double somaQuantidadeDinheiroSaiu = quantidadeDinheiroConvert + modeloGastosAvulsos.getValorGastoAvulsos();
                                        double somaValorTotalGastoAvulso = valorTotalGastoAvulsoConvert + modeloGastosAvulsos.getValorGastoAvulsos();

                                        adicionaGastoAvulsoNoMontanteMensal(somaQuantidadeDinheiroSaiu,somaValorTotalGastoAvulso, idRecuperadoMontante, modeloGastosAvulsos);



                                    }
                                });
                            }

                            //montante recuperado
                        }else{
                          carregarAlertaCriarMontanteMensal();


                        }
                    }
                });

    }

    private void adicionaGastoAvulsoNoMontanteMensal(double somaQuantidadeDinheiroSaiu,
                                                     double somaValorTotalGastoAvulso,
                                                     String idRecuperadoMontante,
                                                     ModeloGastosAvulsos modeloGastosAvulsos) {
        ModeloMontanteMensalDAO modeloMontanteMensalDAO = new ModeloMontanteMensalDAO(AdicionarOuEditarGastoAvulsoActivity.this);

        System.out.println("adicionaGastoAvulsoNoMontanteMensal ModeloGastosAvulsos " + modeloGastosAvulsos);
        modeloMontanteMensalDAO.adicionarGastoAvulsoMontanteMensal(somaQuantidadeDinheiroSaiu, somaValorTotalGastoAvulso, idRecuperadoMontante, modeloGastosAvulsos);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStackImmediate();
        finish();

    }

    private void carregarAlertaCriarMontanteMensal() {

        AlertDialog.Builder alertaENecessarioCriarOMontante = new AlertDialog.Builder(AdicionarOuEditarGastoAvulsoActivity.this);
        alertaENecessarioCriarOMontante.setTitle("NECESSÁRIO INICIAR O MÊS/DIA");
        alertaENecessarioCriarOMontante.setMessage("Atenção, é necessário criar o montante mensal e/ou diário para adicionar um gasto avulso volte para tela do Caixa e inicie o mÊs");

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