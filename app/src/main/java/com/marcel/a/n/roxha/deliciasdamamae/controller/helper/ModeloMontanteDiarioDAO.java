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
    private SimpleDateFormat simpleDateFormatCollectionReferenciaAtual = new SimpleDateFormat("MM");
    private SimpleDateFormat simpleDateFormatCollectionReferenciaAnoAtual = new SimpleDateFormat("yyyy");


    private Context context;
    private FirebaseFirestore firestore = ConfiguracaoFirebase.getFirestor();
    private CollectionReference referenceMontanteDiarioHoje;

    public ModeloMontanteDiarioDAO(Context context){
        this.context = context;
        this.mesAtual = simpleDateFormatCollectionReferenciaAtual.format(dataHoje);
        this.anoAtual = simpleDateFormatCollectionReferenciaAnoAtual.format(dataHoje);
        this.nomeCompletoColletion = COLLECTION_CAIXA_DIARIO + "_" + mesAtual + "_" + anoAtual;
        this.referenceMontanteDiarioHoje = firestore.collection(nomeCompletoColletion);
    }


    @Override
    public ModeloMontanteMensalLoja modeloMontanteDiarioIniciandoODia(ModeloMontanteDiario modeloMontanteDiario) {


        try {
            Map<String, Object> montanteMensalSendoIniciadoParaArmazenar = new HashMap<>();
            montanteMensalSendoIniciadoParaArmazenar.put("idReferenciaMontanteDiarioDesseDia", modeloMontanteDiario.getIdReferenciaMontanteDiarioDesseDia());
            montanteMensalSendoIniciadoParaArmazenar.put("dataReferenciaMontanteDiarioDesseDia", modeloMontanteDiario.getDataReferenciaMontanteDiarioDesseDia());
            montanteMensalSendoIniciadoParaArmazenar.put("valorQueOCaixaIniciouODia", modeloMontanteDiario.getValorQueOCaixaIniciouODia());
            montanteMensalSendoIniciadoParaArmazenar.put("valorTotalDeVendasNaLojaDesseDia", modeloMontanteDiario.getValorTotalDeVendasNaLojaDesseDia());
            montanteMensalSendoIniciadoParaArmazenar.put("valorTotalDeVendasNoIfoodDesseDia", modeloMontanteDiario.getValorTotalDeVendasNoIfoodDesseDia());
            montanteMensalSendoIniciadoParaArmazenar.put("valorTotalDeVendasEmGeralDesseDia", modeloMontanteDiario.getValorTotalDeVendasEmGeralDesseDia());
            montanteMensalSendoIniciadoParaArmazenar.put("valorTotalDeVendasNoDinheiroDesseDia", modeloMontanteDiario.getValorTotalDeVendasNoDinheiroDesseDia());
            montanteMensalSendoIniciadoParaArmazenar.put("valorTotalDeVendasNoCreditoDesseDia", modeloMontanteDiario.getValorTotalDeVendasNoCreditoDesseDia());
            montanteMensalSendoIniciadoParaArmazenar.put("valorTotalDeVendasNoDebitoDesseDia", modeloMontanteDiario.getValorTotalDeVendasNoDebitoDesseDia());
            montanteMensalSendoIniciadoParaArmazenar.put("valorTotalDeTrocoDesseDia", modeloMontanteDiario.getValorTotalDeTrocoDesseDia());
            this.referenceMontanteDiarioHoje.add(montanteMensalSendoIniciadoParaArmazenar).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {

                    String idCriadoReferenteAoMontanteCriadoDeHoje = documentReference.getId();
                    atualizaIdMontanteDiarioSendoIniciado(idCriadoReferenteAoMontanteCriadoDeHoje, modeloMontanteDiario);


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
    public ModeloMontanteMensalLoja modeloMontanteDiarioEditando(ModeloMontanteDiario modeloMontanteDiario) {
        return null;
    }

    @Override
    public ModeloMontanteMensalLoja modeloMontanteDiarioDeletando(ModeloMontanteDiario modeloMontanteDiario) {
        return null;
    }

    public void atualizaIdMontanteDiarioSendoIniciado(String idCriado, ModeloMontanteDiario modeloMontanteDiario){

        try {
            Map<String, Object> montanteMensalJaIniciadoAtualizandoIdParaArmazenar = new HashMap<>();
            montanteMensalJaIniciadoAtualizandoIdParaArmazenar.put("idReferenciaMontanteDiarioDesseDia", idCriado);
            montanteMensalJaIniciadoAtualizandoIdParaArmazenar.put("dataReferenciaMontanteDiarioDesseDia", modeloMontanteDiario.getDataReferenciaMontanteDiarioDesseDia());
            montanteMensalJaIniciadoAtualizandoIdParaArmazenar.put("valorQueOCaixaIniciouODia", modeloMontanteDiario.getValorQueOCaixaIniciouODia());
            montanteMensalJaIniciadoAtualizandoIdParaArmazenar.put("valorTotalDeVendasNaLojaDesseDia", modeloMontanteDiario.getValorTotalDeVendasNaLojaDesseDia());
            montanteMensalJaIniciadoAtualizandoIdParaArmazenar.put("valorTotalDeVendasNoIfoodDesseDia", modeloMontanteDiario.getValorTotalDeVendasNoIfoodDesseDia());
            montanteMensalJaIniciadoAtualizandoIdParaArmazenar.put("valorTotalDeVendasEmGeralDesseDia", modeloMontanteDiario.getValorTotalDeVendasEmGeralDesseDia());
            montanteMensalJaIniciadoAtualizandoIdParaArmazenar.put("valorTotalDeVendasNoDinheiroDesseDia", modeloMontanteDiario.getValorTotalDeVendasNoDinheiroDesseDia());
            montanteMensalJaIniciadoAtualizandoIdParaArmazenar.put("valorTotalDeVendasNoCreditoDesseDia", modeloMontanteDiario.getValorTotalDeVendasNoCreditoDesseDia());
            montanteMensalJaIniciadoAtualizandoIdParaArmazenar.put("valorTotalDeVendasNoDebitoDesseDia", modeloMontanteDiario.getValorTotalDeVendasNoDebitoDesseDia());
            montanteMensalJaIniciadoAtualizandoIdParaArmazenar.put("valorTotalDeTrocoDesseDia", modeloMontanteDiario.getValorTotalDeTrocoDesseDia());
            this.referenceMontanteDiarioHoje.document(idCriado).update(montanteMensalJaIniciadoAtualizandoIdParaArmazenar).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(context, "SUCESSO MONTANTE DIÁRIO INICIADO REFERENTE A DATA " + modeloMontanteDiario.getDataReferenciaMontanteDiarioDesseDia() + "\n " +
                            "INICIOU O DIA COM R$: " + modeloMontanteDiario.getValorQueOCaixaIniciouODia(), Toast.LENGTH_SHORT).show();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(context, "Algo deu errado verifique sua internet e tente novamente", Toast.LENGTH_SHORT).show();

                }
            });

        }catch (Exception e){
            Toast.makeText(context, "Algo deu errado verifique sua internet e tente novamente", Toast.LENGTH_SHORT).show();
        }
    }



}
