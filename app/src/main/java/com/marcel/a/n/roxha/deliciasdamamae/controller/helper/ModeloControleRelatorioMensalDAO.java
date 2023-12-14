package com.marcel.a.n.roxha.deliciasdamamae.controller.helper;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.common.util.concurrent.ExecutionError;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.marcel.a.n.roxha.deliciasdamamae.config.ConfiguracaoFirebase;
import com.marcel.a.n.roxha.deliciasdamamae.model.ModeloControleRelatorioMensal;
import com.marcel.a.n.roxha.deliciasdamamae.model.ModeloMontanteMensalLoja;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ModeloControleRelatorioMensalDAO implements InterfaceModeloControleRelatorioMensalDAO{

    private static final String COLLECTIO_MONTANTE_MENSAL = "MONTANTE_MENSAL";
    private static final String COLLECTIO_RELATORIOS = "RELATORIOS";
    private Context context;
    private FirebaseFirestore firestore = ConfiguracaoFirebase.getFirestor();
    private CollectionReference referenciaRelatorioMensal ;
    private Date dataHoje = new Date();
    private String mesAtual = "";
    private String anoAtual = "";
    private String  nomeCompletoColletion = "";

    private SimpleDateFormat simpleDateFormatCollectionReferenciaMesAtual = new SimpleDateFormat("MM");
    private SimpleDateFormat simpleDateFormatCollectionReferenciaDataAtual = new SimpleDateFormat("dd/MM/yyyy");
    private SimpleDateFormat simpleDateFormatCollectionReferenciaAnoAtual = new SimpleDateFormat("yyyy");

    public ModeloControleRelatorioMensalDAO(Context context) {
        this.context = context;
        this.mesAtual = simpleDateFormatCollectionReferenciaMesAtual.format(dataHoje);
        this.anoAtual = simpleDateFormatCollectionReferenciaAnoAtual.format(dataHoje);
        this.nomeCompletoColletion = COLLECTIO_MONTANTE_MENSAL + "_" + mesAtual + "_" + anoAtual;
        this.referenciaRelatorioMensal = firestore.collection(nomeCompletoColletion);
    }

    @Override
    public boolean adicionarRelatorio(String idMensal) {

        if(idMensal != null){
            ModeloControleRelatorioMensal modeloControleRelatorioMensalIniciando = new ModeloControleRelatorioMensal();
            modeloControleRelatorioMensalIniciando.setIdMontanteMensal(idMensal);
            modeloControleRelatorioMensalIniciando.setQuantidadeDeVendasNesseMes(0);
            modeloControleRelatorioMensalIniciando.setValorDeVendasNesseMes(0);
            modeloControleRelatorioMensalIniciando.setLucroAproximadoDeVendasNesseMes(0);
            modeloControleRelatorioMensalIniciando.setGastoComComprasNoMercadoParaVendasNesseMes(0);


            try{
                Map<String, Object> relatorioInicial = new HashMap<>();
                relatorioInicial.put("id", "N/D");
                relatorioInicial.put("idMontanteMensal", modeloControleRelatorioMensalIniciando.getIdMontanteMensal());
                relatorioInicial.put("quantidadeDeVendasNesseMes", modeloControleRelatorioMensalIniciando.getValorDeVendasNesseMes());
                relatorioInicial.put("lucroAproximadoDeVendasNesseMes", modeloControleRelatorioMensalIniciando.getLucroAproximadoDeVendasNesseMes());
                relatorioInicial.put("despesasDeVendasNesseMes", modeloControleRelatorioMensalIniciando.getDespesasDeVendasNesseMes());
                relatorioInicial.put("gastoComComprasNoMercadoParaVendasNesseMes", modeloControleRelatorioMensalIniciando.getGastoComComprasNoMercadoParaVendasNesseMes());

                referenciaRelatorioMensal.document(idMensal).collection(COLLECTIO_RELATORIOS).add(relatorioInicial).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {

                        String idRecuperadoCriado = documentReference.getId();

                        atualizaIdRelatorio(idRecuperadoCriado, idMensal);

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("Error 31 " + e.getMessage());
                        Toast.makeText(context, "Algo deu errado verifique sua internet e tente novamente mais tarde", Toast.LENGTH_SHORT).show();
                    }
                });


            }catch (ExecutionError e){
                System.out.println("Error 28 " + e.getMessage());
            }catch (Exception erro){
                System.out.println("Error 29 " + erro.getMessage());
            }

        }


        return false;
    }

    @Override
    public boolean atualizaRelatorio(ModeloControleRelatorioMensal modeloControleRelatorioMensal, String idMensal) {

        Map<String, Object> relatorio = new HashMap<>();
        relatorio.put("quantidadeDeVendasNesseMes", modeloControleRelatorioMensal.getQuantidadeDeVendasNesseMes());
        relatorio.put("valorDeVendasNesseMes", modeloControleRelatorioMensal.getValorDeVendasNesseMes());
        relatorio.put("lucroAproximadoDeVendasNesseMes", modeloControleRelatorioMensal.getLucroAproximadoDeVendasNesseMes());
        relatorio.put("despesasDeVendasNesseMes", modeloControleRelatorioMensal.getDespesasDeVendasNesseMes());
        relatorio.put("gastoComComprasNoMercadoParaVendasNesseMes", modeloControleRelatorioMensal.getGastoComComprasNoMercadoParaVendasNesseMes());
        return false;
    }

    public boolean atualizaIdRelatorio(String idRelatorioInicial, String idMontanteMensal) {

        Map<String, Object> relatorioAtualizaId = new HashMap<>();
        relatorioAtualizaId.put("id", idRelatorioInicial);

        referenciaRelatorioMensal.document(idMontanteMensal).collection(COLLECTIO_RELATORIOS).document(idRelatorioInicial).update(relatorioAtualizaId).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(context, "Relatorio criado com sucesso!", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println("Error 31 " + e.getMessage());
                Toast.makeText(context, "Algo deu errado verifique sua internet e tente novamente mais tarde", Toast.LENGTH_SHORT).show();
            }
        });
        return false;
    }
}
