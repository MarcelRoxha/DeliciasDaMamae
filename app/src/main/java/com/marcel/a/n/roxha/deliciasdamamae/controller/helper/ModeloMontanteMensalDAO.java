package com.marcel.a.n.roxha.deliciasdamamae.controller.helper;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.marcel.a.n.roxha.deliciasdamamae.config.ConfiguracaoFirebase;
import com.marcel.a.n.roxha.deliciasdamamae.model.ModeloControleRelatorioMensal;
import com.marcel.a.n.roxha.deliciasdamamae.model.ModeloGastosAvulsos;
import com.marcel.a.n.roxha.deliciasdamamae.model.ModeloMontanteDiario;
import com.marcel.a.n.roxha.deliciasdamamae.model.ModeloMontanteMensalLoja;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ModeloMontanteMensalDAO implements InterfaceModeloMontanteMensalDAO{

    private static final String COLLECTIO_MONTANTE_MENSAL = "MONTANTE_MENSAL";
    private static final String COLLECTIO_GASTO_AVULSO = "GASTO_AVULSO";
    private Context context;
    private FirebaseFirestore firestore = ConfiguracaoFirebase.getFirestor();
    private CollectionReference referenciaMontanteMensal ;
    private Date dataHoje = new Date();
    private String mesAtual = "";
    private String anoAtual = "";
    private String  nomeCompletoColletion = "";

    private SimpleDateFormat simpleDateFormatCollectionReferenciaMesAtual = new SimpleDateFormat("MM");
    private SimpleDateFormat simpleDateFormatCollectionReferenciaAnoAtual = new SimpleDateFormat("yyyy");
    ModeloMontanteMensalLoja modeloRecuperaValorDigitado;
    public ModeloMontanteMensalDAO(Context context) {
        this.context = context;
        this.referenciaMontanteMensal = firestore.collection(COLLECTIO_MONTANTE_MENSAL);
        this.mesAtual = simpleDateFormatCollectionReferenciaMesAtual.format(dataHoje);
        this.anoAtual = simpleDateFormatCollectionReferenciaAnoAtual.format(dataHoje);
        this.nomeCompletoColletion = COLLECTIO_MONTANTE_MENSAL + "_" + mesAtual + "_" + anoAtual;
        this.referenciaMontanteMensal = firestore.collection(nomeCompletoColletion);
        this.modeloRecuperaValorDigitado = new ModeloMontanteMensalLoja();
    }


    @Override
    public ModeloMontanteMensalLoja modeloMontanteEditando(ModeloMontanteMensalLoja modeloMontanteMensalLoja) {
        return null;
    }

    @Override
    public ModeloMontanteMensalLoja modeloMontanteIniciarMesEDiario(ModeloMontanteMensalLoja modeloMontanteMensalLoja, String diaAtual) {


        modeloRecuperaValorDigitado = modeloMontanteMensalLoja;
        try {
            Map<String, Object> montanteMensalSendoIniciadoParaArmazenar = new HashMap<>();
            montanteMensalSendoIniciadoParaArmazenar.put("idMontante", modeloMontanteMensalLoja.getIdMontante());
            montanteMensalSendoIniciadoParaArmazenar.put("mesReferenciaDesseMontante", modeloMontanteMensalLoja.getMesReferenciaDesseMontante());
            montanteMensalSendoIniciadoParaArmazenar.put("valorTotalVendasBoleriaMensal", modeloMontanteMensalLoja.getValorTotalVendasBoleriaMensal());
            montanteMensalSendoIniciadoParaArmazenar.put("valorTotalVendasIfoodMensal", modeloMontanteMensalLoja.getValorTotalVendasIfoodMensal());
            montanteMensalSendoIniciadoParaArmazenar.put("valorTotalVendasEmGeralMensal", modeloMontanteMensalLoja.getValorTotalVendasEmGeralMensal());
            montanteMensalSendoIniciadoParaArmazenar.put("quantoDinheiroEntrouEsseMes", modeloMontanteMensalLoja.getQuantoDinheiroEntrouEsseMes());
            montanteMensalSendoIniciadoParaArmazenar.put("quantoDinheiroSaiuEsseMes", modeloMontanteMensalLoja.getQuantoDinheiroSaiuEsseMes());
            montanteMensalSendoIniciadoParaArmazenar.put("valorQueOMontanteIniciouPositivo", modeloMontanteMensalLoja.getValorQueOMontanteIniciouPositivo());
            montanteMensalSendoIniciadoParaArmazenar.put("valorQueOMontanteIniciouNegativo", modeloMontanteMensalLoja.getValorQueOMontanteIniciouNegativo());
            montanteMensalSendoIniciadoParaArmazenar.put("valorTotalDeVendasNoDinheiroDesseMes", modeloMontanteMensalLoja.getValorTotalDeVendasNoDinheiroDesseMes());
            montanteMensalSendoIniciadoParaArmazenar.put("valorTotalDeVendasNoCreditoDesseMes", modeloMontanteMensalLoja.getValorTotalDeVendasNoCreditoDesseMes());
            montanteMensalSendoIniciadoParaArmazenar.put("valorTotalDeVendasNoDebitoDesseMes", modeloMontanteMensalLoja.getValorTotalDeVendasNoDebitoDesseMes());
            montanteMensalSendoIniciadoParaArmazenar.put("quantidadeDeVendasDesseMes", modeloMontanteMensalLoja.getQuantidadeDeVendasDesseMes());


            this.referenciaMontanteMensal.add(montanteMensalSendoIniciadoParaArmazenar)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {

                            ModeloMontanteMensalLoja modeloMontanteMensalLojaParaAtualizarId = new ModeloMontanteMensalLoja();

                            String idRecuperado = documentReference.getId();
                            String valorRecuperadoParaAdicionarNoMontanteDiario = modeloRecuperaValorDigitado.getValorQueOMontanteIniciouPositivo();
                            atualizandoIdDoMontanteMensalCriadoIniciandoOMontante(idRecuperado, valorRecuperadoParaAdicionarNoMontanteDiario, diaAtual);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });




        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }




        return null;
    }

    @Override
    public ModeloMontanteMensalLoja modeloMontanteDeletando(ModeloMontanteMensalLoja modeloMontanteMensalLoja) {
        return null;
    }


    private void atualizandoIdDoMontanteMensalCriadoIniciandoOMontante(String idCriado, String valorQueOMontanteMensalIniciou, String diaAtual){
        if(idCriado != null) {
            try {
                Map<String, Object> montanteMensalSendoIniciadoParaArmazenar = new HashMap<>();
                montanteMensalSendoIniciadoParaArmazenar.put("idMontante", idCriado);
                this.referenciaMontanteMensal.document(idCriado).update(montanteMensalSendoIniciadoParaArmazenar).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                        ModeloMontanteDiario modeloMontanteDiarioIniciado = new ModeloMontanteDiario();
                        ModeloMontanteDiarioDAO modeloMontanteDiarioDAOSendoIniciadoDesseMes = new ModeloMontanteDiarioDAO(context);
                        modeloMontanteDiarioIniciado.setIdReferenciaMontanteDiarioDesseDia("N/D");
                        modeloMontanteDiarioIniciado.setDataReferenciaMontanteDiarioDesseDia(diaAtual);
                        modeloMontanteDiarioIniciado.setIdReferenciaMontanteMensal(idCriado);
                        modeloMontanteDiarioIniciado.setValorQueOCaixaIniciouODia(valorQueOMontanteMensalIniciou);
                        modeloMontanteDiarioIniciado.setValorTotalDeVendasNaLojaDesseDia("0");
                        modeloMontanteDiarioIniciado.setValorTotalDeVendasNoIfoodDesseDia("0");
                        modeloMontanteDiarioIniciado.setValorTotalDeVendasEmGeralDesseDia("0");
                        modeloMontanteDiarioIniciado.setValorTotalDeVendasNoDinheiroDesseDia("0");
                        modeloMontanteDiarioIniciado.setValorTotalDeVendasNoCreditoDesseDia("0");
                        modeloMontanteDiarioIniciado.setValorTotalDeVendasNoDebitoDesseDia("0");
                        modeloMontanteDiarioIniciado.setValorTotalDeTrocoDesseDia("0");
                        modeloMontanteDiarioDAOSendoIniciadoDesseMes.modeloMontanteDiarioSendoIniciadoJuntoComMes(modeloMontanteDiarioIniciado);

                        ModeloControleRelatorioMensalDAO modeloControleRelatorioMensalIniciandoDAO = new ModeloControleRelatorioMensalDAO(context);
                        modeloControleRelatorioMensalIniciandoDAO.adicionarRelatorio(idCriado);

                        System.out.println("Void unused");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "MONTANTE NÃO FOI CRIADO FAVOR VERIFICAR SUA CONEXÃO", Toast.LENGTH_SHORT).show();
                        System.out.println("Exception e " + e.getMessage());
                    }
                });


            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }


        }else {
            Toast.makeText(context, "Algo deu errado, verifique as informações e a internet e tente novamente", Toast.LENGTH_SHORT).show();

        }

    }

    public void adicionarGastoAvulsoMontanteMensal(double valorQueSaiuAtualiza, double valorGastoAvulsoAtualiza, String idMontanteAtualiza, ModeloGastosAvulsos modeloGastosAvulsos){
        if(idMontanteAtualiza != null) {

            String valorQueSaiuAtulizaConvert = String.valueOf(valorQueSaiuAtualiza);
            String valorGastoAvulsoAtualizaConvert = String.valueOf(valorGastoAvulsoAtualiza);

            System.out.println("idMontanteAtualiza " + idMontanteAtualiza);
            try {
                Map<String, Object> montanteMensalSendoIniciadoParaArmazenar = new HashMap<>();
                montanteMensalSendoIniciadoParaArmazenar.put("quantoDinheiroSaiuEsseMes", valorQueSaiuAtulizaConvert);
                montanteMensalSendoIniciadoParaArmazenar.put("valorTotalGastoAvulsoDesseMes", valorGastoAvulsoAtualizaConvert);


                CollectionReference collectionReference = firestore.collection("GASTOS_AVULSOS");
                this.referenciaMontanteMensal.document(idMontanteAtualiza).update(montanteMensalSendoIniciadoParaArmazenar).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                        System.out.println("atualizou");
                        HashMap<String, Object> gastoAvulsoAdiciona = new HashMap<>();
                        gastoAvulsoAdiciona.put("nomeDoGastoAvulsos", modeloGastosAvulsos.getNomeDoGastoAvulsos());
                        gastoAvulsoAdiciona.put("dataDeRegistroGasto", modeloGastosAvulsos.getDataDeRegistroGasto());
                        gastoAvulsoAdiciona.put("dataPagamentoAvulsos", modeloGastosAvulsos.getDataPagamentoAvulsos());
                        gastoAvulsoAdiciona.put("valorGastoAvulsos", modeloGastosAvulsos.getValorGastoAvulsos());


                        firestore.collection("GASTOS_AVULSOS").add(gastoAvulsoAdiciona)
                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference) {
                                                Toast.makeText(context, "Gasto Avulso adicionado com sucesso", Toast.LENGTH_SHORT).show();
                                            }
                                        });


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Atenção, gasto avulso não foi adicionado, favor verifique sua internet e tente novamente", Toast.LENGTH_SHORT).show();

                    }
                });


            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }


        }else {
            Toast.makeText(context, "Algo deu errado, verifique as informações e a internet e tente novamente", Toast.LENGTH_SHORT).show();

        }


    }

}
