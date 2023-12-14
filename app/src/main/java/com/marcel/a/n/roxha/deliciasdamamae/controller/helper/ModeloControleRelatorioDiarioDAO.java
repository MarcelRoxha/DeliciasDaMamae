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
import com.marcel.a.n.roxha.deliciasdamamae.model.ModeloControleRelatorioDiario;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ModeloControleRelatorioDiarioDAO implements InterfaceModeloControleRelatorioDiarioDAO{

    private static final String COLLECTIO_MONTANTE_DIARIO = "CAIXAS_DIARIO";
    private static final String COLLECTIO_RELATORIOS = "RELATORIOS";
    private Context context;
    private FirebaseFirestore firestore = ConfiguracaoFirebase.getFirestor();
    private CollectionReference referenciaRelatorioMenDiario ;
    private Date dataHoje = new Date();
    private String mesAtual = "";
    private String anoAtual = "";
    private String dataAtual = "";
    private String  nomeCompletoColletion = "";

    private SimpleDateFormat simpleDateFormatCollectionReferenciaMesAtual = new SimpleDateFormat("MM");
    private SimpleDateFormat simpleDateFormatCollectionReferenciaDataAtual = new SimpleDateFormat("dd/MM/yyyy");
    private SimpleDateFormat simpleDateFormatCollectionReferenciaAnoAtual = new SimpleDateFormat("yyyy");

    public ModeloControleRelatorioDiarioDAO(Context context) {
        this.context = context;
        this.mesAtual = simpleDateFormatCollectionReferenciaMesAtual.format(dataHoje);
        this.anoAtual = simpleDateFormatCollectionReferenciaAnoAtual.format(dataHoje);
        this.dataAtual = simpleDateFormatCollectionReferenciaDataAtual.format(dataHoje);
        this.nomeCompletoColletion = COLLECTIO_MONTANTE_DIARIO + "_" + mesAtual + "_" + anoAtual;
        this.referenciaRelatorioMenDiario = firestore.collection(nomeCompletoColletion);
    }

    @Override
    public boolean inicializarRelatorioDiario(String idMontanteDiario) {

        if(idMontanteDiario != null){

            ModeloControleRelatorioDiario modeloControleRelatorioDiarioInicial = new ModeloControleRelatorioDiario();

            modeloControleRelatorioDiarioInicial.setQuantidadeDeBolosVendidos(0);
            modeloControleRelatorioDiarioInicial.setLucroAproximado(0);
            modeloControleRelatorioDiarioInicial.setCustoAproximado(0);
            modeloControleRelatorioDiarioInicial.setTotalVendido(0);

            try{

                Map<String, Object> relatorioDiarioIniciando = new HashMap<>();
                relatorioDiarioIniciando.put("id", "N/D");
                relatorioDiarioIniciando.put("idReferenciaCaixa", idMontanteDiario);
                relatorioDiarioIniciando.put("quantidadeDeBolosVendidos", modeloControleRelatorioDiarioInicial.getQuantidadeDeBolosVendidos());
                relatorioDiarioIniciando.put("lucroAproximado", modeloControleRelatorioDiarioInicial.getLucroAproximado());
                relatorioDiarioIniciando.put("custoAproximado", modeloControleRelatorioDiarioInicial.getCustoAproximado());
                relatorioDiarioIniciando.put("totalVendido", modeloControleRelatorioDiarioInicial.getTotalVendido());
                System.out.println("idMontanteDiario============= " + idMontanteDiario);


                referenciaRelatorioMenDiario.document(idMontanteDiario).collection(COLLECTIO_RELATORIOS).add(relatorioDiarioIniciando).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {

                        String idRecuperado = documentReference.getId();
                        System.out.println("idRecuperado============= " + idRecuperado);
                        atualizarIdRelatorioDiarioIniciado(idRecuperado, idMontanteDiario);

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

            }catch (Exception e){
                System.out.println("Error 345 " + e.getMessage());
                Toast.makeText(context, "Algo deu errado verifique sua internet e tente novamente mais tarde", Toast.LENGTH_SHORT).show();
            }
        }

        return false;
    }

    public boolean atualizaRelatorioComVenda(String idMontanteDiario, String idRelatorio, ModeloControleRelatorioDiario modeloControleRelatorioDiario){


        Map<String, Object> relatorioDiarioAtualizadoComVenda = new HashMap<>();
        relatorioDiarioAtualizadoComVenda.put("quantidadeDeBolosVendidos", modeloControleRelatorioDiario.getQuantidadeDeBolosVendidos());
        relatorioDiarioAtualizadoComVenda.put("lucroAproximado", modeloControleRelatorioDiario.getLucroAproximado());
        relatorioDiarioAtualizadoComVenda.put("custoAproximado", modeloControleRelatorioDiario.getCustoAproximado());
        relatorioDiarioAtualizadoComVenda.put("totalVendido", modeloControleRelatorioDiario.getTotalVendido());

        referenciaRelatorioMenDiario.document(idMontanteDiario).collection(COLLECTIO_RELATORIOS).document(idRelatorio).update(relatorioDiarioAtualizadoComVenda).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(context, "Venda adicionada no relatório com sucesso!", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println("Error 34598 " + e.getMessage());
                Toast.makeText(context, "Algo deu errado verifique sua internet e tente novamente mais tarde", Toast.LENGTH_SHORT).show();
            }
        });
        return false;

    }
    private boolean atualizarIdRelatorioDiarioIniciado(String idCriado, String idMontanteDiario){
        Map<String, Object> relatorioAtualizaID = new HashMap<>();
        relatorioAtualizaID.put("id", idCriado);

        referenciaRelatorioMenDiario.document(idMontanteDiario).collection(COLLECTIO_RELATORIOS).document(idCriado).update(relatorioAtualizaID).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(context, "Relatório do dia criado com sucesso", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println("Error 34598 " + e.getMessage());
                Toast.makeText(context, "Algo deu errado verifique sua internet e tente novamente mais tarde", Toast.LENGTH_SHORT).show();
            }
        });
        return false;
    }
}
