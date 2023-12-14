package com.marcel.a.n.roxha.deliciasdamamae.controller.helper;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.marcel.a.n.roxha.deliciasdamamae.activity.LojaActivityV2;
import com.marcel.a.n.roxha.deliciasdamamae.config.ConfiguracaoFirebase;
import com.marcel.a.n.roxha.deliciasdamamae.model.ModeloControleRelatorioDiario;
import com.marcel.a.n.roxha.deliciasdamamae.model.ModeloMontanteDiario;
import com.marcel.a.n.roxha.deliciasdamamae.model.ModeloMontanteMensalLoja;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ModeloMontanteDiarioDAO implements InterfaceModeloMontanteDiarioDAO{
    private static final String COLLECTION_CAIXA_DIARIO = "CAIXAS_DIARIO";
    private Date dataHoje = new Date();
    private String mesAtual = "";
    private String anoAtual = "";
    private String  nomeCompletoColletion = "";
    private SimpleDateFormat simpleDateFormatCollectionReferenciaMesAtual = new SimpleDateFormat("MM");
    private SimpleDateFormat simpleDateFormatCollectionReferenciaAnoAtual = new SimpleDateFormat("yyyy");


    private Context context;
    private FirebaseFirestore firestore = ConfiguracaoFirebase.getFirestor();
    private CollectionReference referenceMontanteDiarioHoje;

    public ModeloMontanteDiarioDAO(Context context){
        this.context = context;
        this.mesAtual = simpleDateFormatCollectionReferenciaMesAtual.format(dataHoje);
        this.anoAtual = simpleDateFormatCollectionReferenciaAnoAtual.format(dataHoje);
        this.nomeCompletoColletion = COLLECTION_CAIXA_DIARIO + "_" + mesAtual + "_" + anoAtual;
        this.referenceMontanteDiarioHoje = firestore.collection(nomeCompletoColletion);
    }

    @Override
    public ModeloMontanteMensalLoja modeloMontanteDiarioIniciandoODia(ModeloMontanteDiario modeloMontanteDiario) {
        try {
            Map<String, Object> montanteMensalSendoIniciadoParaArmazenar = new HashMap<>();
            montanteMensalSendoIniciadoParaArmazenar.put("idReferenciaMontanteDiarioDesseDia", modeloMontanteDiario.getIdReferenciaMontanteDiarioDesseDia());
            montanteMensalSendoIniciadoParaArmazenar.put("idReferenciaMontanteMensal", modeloMontanteDiario.getIdReferenciaMontanteMensal());
            montanteMensalSendoIniciadoParaArmazenar.put("dataReferenciaMontanteDiarioDesseDia", modeloMontanteDiario.getDataReferenciaMontanteDiarioDesseDia());
            montanteMensalSendoIniciadoParaArmazenar.put("valorQueOCaixaIniciouODia", modeloMontanteDiario.getValorQueOCaixaIniciouODia());
            montanteMensalSendoIniciadoParaArmazenar.put("valorQueOCaixaFinalizou", modeloMontanteDiario.getValorTotalDeVendasNaLojaDesseDia());
            montanteMensalSendoIniciadoParaArmazenar.put("valorTotalDeVendasNaLojaDesseDia", modeloMontanteDiario.getValorTotalDeVendasNaLojaDesseDia());
            montanteMensalSendoIniciadoParaArmazenar.put("valorTotalDeVendasNoIfoodDesseDia", modeloMontanteDiario.getValorTotalDeVendasNoIfoodDesseDia());
            montanteMensalSendoIniciadoParaArmazenar.put("valorTotalDeVendasEmGeralDesseDia", modeloMontanteDiario.getValorTotalDeVendasEmGeralDesseDia());
            montanteMensalSendoIniciadoParaArmazenar.put("valorTotalDeVendasNoDinheiroDesseDia", modeloMontanteDiario.getValorTotalDeVendasNoDinheiroDesseDia());
            montanteMensalSendoIniciadoParaArmazenar.put("valorTotalDeVendasNoCreditoDesseDia", modeloMontanteDiario.getValorTotalDeVendasNoCreditoDesseDia());
            montanteMensalSendoIniciadoParaArmazenar.put("valorTotalDeVendasNoDebitoDesseDia", modeloMontanteDiario.getValorTotalDeVendasNoDebitoDesseDia());
            montanteMensalSendoIniciadoParaArmazenar.put("valorTotalDeTrocoDesseDia", modeloMontanteDiario.getValorTotalDeTrocoDesseDia());
            montanteMensalSendoIniciadoParaArmazenar.put("quantidadeDeVendasFeitasDesseDia", modeloMontanteDiario.getQuantidadeDeVendasFeitasDesseDia());
            this.referenceMontanteDiarioHoje.add(montanteMensalSendoIniciadoParaArmazenar).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    String idCriadoReferenteAoMontanteCriadoDeHoje = documentReference.getId();
                    String mensagemRetornoToast = "SUCESSO MONTANTES DIÁRIO INICIADO REFERENTE A DATA"  + modeloMontanteDiario.getDataReferenciaMontanteDiarioDesseDia()+
                            "INICIOU O DIA COM R$: " + modeloMontanteDiario.getValorQueOCaixaIniciouODia();
                    atualizaIdMontanteDiarioSendoIniciado(idCriadoReferenteAoMontanteCriadoDeHoje, mensagemRetornoToast);


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });


        }catch (Exception e){

        }


        return null;
    }

    @Override
    public ModeloMontanteDiario modeloMontanteDiarioSendoIniciadoJuntoComMes(ModeloMontanteDiario modeloMontanteDiario) {
        System.out.println("modeloMontanteDiarioSendoIniciadoJuntoComMes  ###################################");
        try {
            Map<String, Object> montanteMensalJaIniciadoAtualizandoIdParaArmazenar = new HashMap<>();
            montanteMensalJaIniciadoAtualizandoIdParaArmazenar.put("idReferenciaMontanteDiarioDesseDia", modeloMontanteDiario.getIdReferenciaMontanteDiarioDesseDia());
            montanteMensalJaIniciadoAtualizandoIdParaArmazenar.put("idReferenciaMontanteMensal", modeloMontanteDiario.getIdReferenciaMontanteMensal());
            montanteMensalJaIniciadoAtualizandoIdParaArmazenar.put("dataReferenciaMontanteDiarioDesseDia", modeloMontanteDiario.getDataReferenciaMontanteDiarioDesseDia());
            montanteMensalJaIniciadoAtualizandoIdParaArmazenar.put("valorQueOCaixaIniciouODia", modeloMontanteDiario.getValorQueOCaixaIniciouODia());
            montanteMensalJaIniciadoAtualizandoIdParaArmazenar.put("valorTotalDeVendasNaLojaDesseDia", modeloMontanteDiario.getValorTotalDeVendasNaLojaDesseDia());
            montanteMensalJaIniciadoAtualizandoIdParaArmazenar.put("valorTotalDeVendasNoIfoodDesseDia", modeloMontanteDiario.getValorTotalDeVendasNoIfoodDesseDia());
            montanteMensalJaIniciadoAtualizandoIdParaArmazenar.put("valorTotalDeVendasEmGeralDesseDia", modeloMontanteDiario.getValorTotalDeVendasEmGeralDesseDia());
            montanteMensalJaIniciadoAtualizandoIdParaArmazenar.put("valorTotalDeVendasNoDinheiroDesseDia", modeloMontanteDiario.getValorTotalDeVendasNoDinheiroDesseDia());
            montanteMensalJaIniciadoAtualizandoIdParaArmazenar.put("valorTotalDeVendasNoCreditoDesseDia", modeloMontanteDiario.getValorTotalDeVendasNoCreditoDesseDia());
            montanteMensalJaIniciadoAtualizandoIdParaArmazenar.put("valorTotalDeVendasNoDebitoDesseDia", modeloMontanteDiario.getValorTotalDeVendasNoDebitoDesseDia());
            montanteMensalJaIniciadoAtualizandoIdParaArmazenar.put("valorTotalDeTrocoDesseDia", modeloMontanteDiario.getValorTotalDeTrocoDesseDia());
            this.referenceMontanteDiarioHoje.add(montanteMensalJaIniciadoAtualizandoIdParaArmazenar).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    String idCriadoReferenteAoMontanteCriadoDeHoje = documentReference.getId();
                    String mensagemRetornoToast = "SUCESSO MONTANTE DIÁRIO E MENSAL INICIADO REFERENTE A DATA"  + modeloMontanteDiario.getDataReferenciaMontanteDiarioDesseDia()+
                            "INICIOU COM R$: " + modeloMontanteDiario.getValorQueOCaixaIniciouODia();
                    atualizaIdMontanteDiarioSendoIniciado(idCriadoReferenteAoMontanteCriadoDeHoje, mensagemRetornoToast);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(context, "Verifique a conexão e tente novamente mais tarde", Toast.LENGTH_SHORT).show();
                    System.out.println("Erro dentro do methodo modeloMontanteDiarioSendoIniciadoJuntoComMes " + e.getMessage());
                }
            });

        }catch (Exception e){
            Toast.makeText(context, "Algo deu errado verifique sua internet e tente novamente", Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    private void atualizaIdMontanteDiarioSendoIniciado(String idCriado, String mensagemRetornoToast){
        try {
            Map<String, Object> montanteMensalJaIniciadoAtualizandoIdParaArmazenar = new HashMap<>();
            montanteMensalJaIniciadoAtualizandoIdParaArmazenar.put("idReferenciaMontanteDiarioDesseDia", idCriado);
            this.referenceMontanteDiarioHoje.document(idCriado).update(montanteMensalJaIniciadoAtualizandoIdParaArmazenar).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {


                    Toast.makeText(context, mensagemRetornoToast, Toast.LENGTH_LONG).show();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(context, "Algo deu errado verifique sua internet e tente novamente", Toast.LENGTH_SHORT).show();
                    System.out.println("Exception e " + e.getMessage() );

                }
            });

        }catch (Exception e){
            Toast.makeText(context, "Algo deu errado verifique sua internet e tente novamente", Toast.LENGTH_SHORT).show();
        }
    }



}
