package com.marcel.a.n.roxha.deliciasdamamae.helper;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.marcel.a.n.roxha.deliciasdamamae.config.ConfiguracaoFirebase;
import com.marcel.a.n.roxha.deliciasdamamae.model.BolosModel;
import com.marcel.a.n.roxha.deliciasdamamae.model.ModeloMontanteMensalLoja;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModeloMontanteMensalDAO implements InterfaceModeloMontanteMensalDAO{

    private static final String COLLECTIO_MONTANTE_MENSAL = "MONTANTES_GERAL";
    private Context context;
    private FirebaseFirestore firestore = ConfiguracaoFirebase.getFirestor();
    private CollectionReference referenceItemEstoque = firestore.collection(COLLECTIO_MONTANTE_MENSAL);
    private boolean resultadoUpdate;

    public ModeloMontanteMensalDAO(Context context) {
        this.context = context;
    }

    @Override
    public ModeloMontanteMensalLoja modeloMontanteCriando(String idUsuario, ModeloMontanteMensalLoja modeloMontanteMensalLoja) {

      /*   private String idMontante;
    private String ;
    private String ;
    private String ;
    private String ;
    private String ;
    private String ;
    private String ;
    */

        System.out.println("---------------------------------------- INICIANDO modeloMontanteCriandoDAO ----------------------------------------\"");
        System.out.println("---------------------------------------- idUsuario :" + idUsuario);



        if(idUsuario != null || !idUsuario.equals("")) {

            FirebaseFirestore firestore = ConfiguracaoFirebase.getFirestor();

            String mesReferenciaParaCompletarONomeDaCollection = modeloMontanteMensalLoja.getMesReferenciaDesseMontante();
            String nomeCompletoDaCollectionMontanteMensalSendoIniciado = "MONTANTE_MENSAL_" + mesReferenciaParaCompletarONomeDaCollection;
            CollectionReference referenceMontanteMensalSendoIniciado = firestore.collection(nomeCompletoDaCollectionMontanteMensalSendoIniciado);
            System.out.println("---------------------------------------- ENTROU NO IF DO IDUSUARIO ----------------------------------------\"");

            try {

                System.out.println("----------------------------------------modeloMontanteMensalLojaSendoIniciado DENTRO DO IF \n" + modeloMontanteMensalLoja.toString());


                Map<String, Object> montanteMensalSendoIniciadoParaArmazenar = new HashMap<>();
                montanteMensalSendoIniciadoParaArmazenar.put("idMontante", "N/D");
                montanteMensalSendoIniciadoParaArmazenar.put("mesReferenciaDesseMontante", modeloMontanteMensalLoja.getMesReferenciaDesseMontante());
                montanteMensalSendoIniciadoParaArmazenar.put("valorTotalVendasBoleriaMensal", modeloMontanteMensalLoja.getValorTotalVendasBoleriaMensal());
                montanteMensalSendoIniciadoParaArmazenar.put("valorTotalVendasIfoodMensal", modeloMontanteMensalLoja.getValorTotalVendasIfoodMensal());
                montanteMensalSendoIniciadoParaArmazenar.put("valorTotalVendasEmGeralMensal", modeloMontanteMensalLoja.getValorTotalVendasEmGeralMensal());
                montanteMensalSendoIniciadoParaArmazenar.put("quantoDinheiroEntrouEsseMes", modeloMontanteMensalLoja.getQuantoDinheiroEntrouEsseMes());
                montanteMensalSendoIniciadoParaArmazenar.put("quantoDinheiroSaiuEsseMes", modeloMontanteMensalLoja.getQuantoDinheiroSaiuEsseMes());
                montanteMensalSendoIniciadoParaArmazenar.put("valorQueOMontanteIniciouPositivo", modeloMontanteMensalLoja.getValorQueOMontanteIniciouPositivo());
                montanteMensalSendoIniciadoParaArmazenar.put("valorQueOMontanteIniciouNegativo", modeloMontanteMensalLoja.getValorQueOMontanteIniciouNegativo());

                referenceMontanteMensalSendoIniciado.add(montanteMensalSendoIniciadoParaArmazenar)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {

                                ModeloMontanteMensalLoja modeloMontanteMensalLojaParaAtualizarId = new ModeloMontanteMensalLoja();

                                String idRecuperado = documentReference.getId();
                                String idDoUsuario = idUsuario;
                                System.out.println("ID montante: " + idRecuperado);
                                System.out.println("ID usuario: " + idDoUsuario);

                                modeloMontanteMensalLojaParaAtualizarId.setIdMontante(idRecuperado);
                                modeloMontanteMensalLojaParaAtualizarId.setMesReferenciaDesseMontante(modeloMontanteMensalLoja.getMesReferenciaDesseMontante());
                                modeloMontanteMensalLojaParaAtualizarId.setValorQueOMontanteIniciouPositivo(modeloMontanteMensalLoja.getValorQueOMontanteIniciouPositivo());
                                modeloMontanteMensalLojaParaAtualizarId.setValorTotalVendasBoleriaMensal(modeloMontanteMensalLoja.getValorTotalVendasBoleriaMensal());
                                modeloMontanteMensalLojaParaAtualizarId.setValorTotalVendasIfoodMensal(modeloMontanteMensalLoja.getValorTotalVendasIfoodMensal());
                                modeloMontanteMensalLojaParaAtualizarId.setValorTotalVendasEmGeralMensal(modeloMontanteMensalLoja.getValorTotalVendasEmGeralMensal());
                                modeloMontanteMensalLojaParaAtualizarId.setQuantoDinheiroEntrouEsseMes(modeloMontanteMensalLoja.getQuantoDinheiroEntrouEsseMes());
                                modeloMontanteMensalLojaParaAtualizarId.setQuantoDinheiroSaiuEsseMes(modeloMontanteMensalLoja.getQuantoDinheiroSaiuEsseMes());
                                modeloMontanteMensalLojaParaAtualizarId.setValorQueOMontanteIniciouNegativo(modeloMontanteMensalLoja.getValorQueOMontanteIniciouNegativo());

                                atualizandoIdDoMontanteMensalCriadoIniciandoOMontante(idRecuperado, modeloMontanteMensalLojaParaAtualizarId);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });




            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }


        }else {
            Toast.makeText(context, "Algo deu errado, verifique as informações e a internet e tente novamente", Toast.LENGTH_SHORT).show();

        }


        return null;
    }

    @Override
    public ModeloMontanteMensalLoja modeloMontanteEditando(ModeloMontanteMensalLoja modeloMontanteMensalLoja) {
        return null;
    }

    @Override
    public ModeloMontanteMensalLoja modeloMontanteIniciarMes(ModeloMontanteMensalLoja modeloMontanteMensalLoja) {
        String mesReferenciaParaCompletarONomeDaCollection = modeloMontanteMensalLoja.getMesReferenciaDesseMontante();
        String nomeCompletoDaCollectionMontanteMensalSendoIniciado = "MONTANTE_MENSAL_" + mesReferenciaParaCompletarONomeDaCollection;
        CollectionReference referenceMontanteMensalSendoIniciado = firestore.collection(nomeCompletoDaCollectionMontanteMensalSendoIniciado);
        try {

            System.out.println("----------------------------------------modeloMontanteMensalLojaSendoIniciado DENTRO DO IF \n" + modeloMontanteMensalLoja.toString());


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

            referenceMontanteMensalSendoIniciado.add(montanteMensalSendoIniciadoParaArmazenar)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {

                            ModeloMontanteMensalLoja modeloMontanteMensalLojaParaAtualizarId = new ModeloMontanteMensalLoja();

                            String idRecuperado = documentReference.getId();


                            modeloMontanteMensalLojaParaAtualizarId.setIdMontante(idRecuperado);
                            modeloMontanteMensalLojaParaAtualizarId.setMesReferenciaDesseMontante(modeloMontanteMensalLoja.getMesReferenciaDesseMontante());
                            modeloMontanteMensalLojaParaAtualizarId.setValorQueOMontanteIniciouPositivo(modeloMontanteMensalLoja.getValorQueOMontanteIniciouPositivo());
                            modeloMontanteMensalLojaParaAtualizarId.setValorTotalVendasBoleriaMensal(modeloMontanteMensalLoja.getValorTotalVendasBoleriaMensal());
                            modeloMontanteMensalLojaParaAtualizarId.setValorTotalVendasIfoodMensal(modeloMontanteMensalLoja.getValorTotalVendasIfoodMensal());
                            modeloMontanteMensalLojaParaAtualizarId.setValorTotalVendasEmGeralMensal(modeloMontanteMensalLoja.getValorTotalVendasEmGeralMensal());
                            modeloMontanteMensalLojaParaAtualizarId.setQuantoDinheiroEntrouEsseMes(modeloMontanteMensalLoja.getQuantoDinheiroEntrouEsseMes());
                            modeloMontanteMensalLojaParaAtualizarId.setQuantoDinheiroSaiuEsseMes(modeloMontanteMensalLoja.getQuantoDinheiroSaiuEsseMes());
                            modeloMontanteMensalLojaParaAtualizarId.setValorQueOMontanteIniciouNegativo(modeloMontanteMensalLoja.getValorQueOMontanteIniciouNegativo());
                            modeloMontanteMensalLojaParaAtualizarId.setValorTotalDeVendasNoDinheiroDesseMes(modeloMontanteMensalLoja.getValorTotalDeVendasNoDinheiroDesseMes());
                            modeloMontanteMensalLojaParaAtualizarId.setValorTotalDeVendasNoCreditoDesseMes(modeloMontanteMensalLoja.getValorTotalDeVendasNoCreditoDesseMes());
                            modeloMontanteMensalLojaParaAtualizarId.setValorTotalDeVendasNoDebitoDesseMes(modeloMontanteMensalLoja.getValorTotalDeVendasNoDebitoDesseMes());

                            atualizandoIdDoMontanteMensalCriadoIniciandoOMontante(idRecuperado, modeloMontanteMensalLojaParaAtualizarId);
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


    private void atualizandoIdDoMontanteMensalCriadoIniciandoOMontante(String idCriado, ModeloMontanteMensalLoja modeloMontanteMensalLoja){

        if(idCriado != null) {



            FirebaseFirestore firestore = ConfiguracaoFirebase.getFirestor();

            String mesReferenciaParaCompletarONomeDaCollection = modeloMontanteMensalLoja.getMesReferenciaDesseMontante();
            String valorQueOMesFoiIniciado = modeloMontanteMensalLoja.getValorQueOMontanteIniciouPositivo();
            String nomeCompletoDaCollectionMontanteMensalSendoIniciado = "MONTANTE_MENSAL_" + mesReferenciaParaCompletarONomeDaCollection;
            CollectionReference referenceMontanteMensalSendoIniciado = firestore.collection(nomeCompletoDaCollectionMontanteMensalSendoIniciado);
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


                referenceMontanteMensalSendoIniciado.document(idCriado).update(montanteMensalSendoIniciadoParaArmazenar).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(context, "MONTANTE REFERENTE AO MÊS " + mesReferenciaParaCompletarONomeDaCollection +" CRIADO COM SUCESSO \n + " +
                                "VALOR INICIAL DO MÊS É DE R$ " + valorQueOMesFoiIniciado, Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "MONTANTE REFERENTE AO MÊS " + mesReferenciaParaCompletarONomeDaCollection +" NÃO FOI CRIADO FAVOR VERIFICAR SUA CONEXÃO", Toast.LENGTH_SHORT).show();
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
