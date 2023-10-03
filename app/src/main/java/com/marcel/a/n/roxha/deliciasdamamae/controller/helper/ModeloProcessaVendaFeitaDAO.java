package com.marcel.a.n.roxha.deliciasdamamae.controller.helper;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.marcel.a.n.roxha.deliciasdamamae.config.ConfiguracaoFirebase;
import com.marcel.a.n.roxha.deliciasdamamae.model.BolosModel;
import com.marcel.a.n.roxha.deliciasdamamae.model.ModeloMontanteDiario;
import com.marcel.a.n.roxha.deliciasdamamae.model.ModeloMontanteMensalLoja;
import com.marcel.a.n.roxha.deliciasdamamae.model.ProdutoVendido;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModeloProcessaVendaFeitaDAO implements InterfaceModeloProcessaVendaFeitaDAO {
    private Context context;
    private static final String COLLECTION_MONTANTE_MENSAL = "MONTANTE_MENSAL_";
    private static final String COLLECTION_CAIXA_DIARIO = "CAIXA_DIARIO_";

    private String nomeCollectionNomeCaixaDiario = "";
    private String nomeCollectionNomeMontanteMensal = "";
    private Date dataHojeReferencia = new Date();
    private String hojeString = "";
    private String mesAtual = "";
    private String diaAtual = "";
    private String anoAtual = "";
    private String dataCompleta = "";
    private String dataCompletaDoProcessamentoDaVenda = "";
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
    private SimpleDateFormat simpleDateFormatVerificaDiaAtual = new SimpleDateFormat("dd");
    private SimpleDateFormat simpleDateFormatCollectionReferenciaAtual = new SimpleDateFormat("MM");
    private SimpleDateFormat simpleDateFormatCollectionReferenciaAnoAtual = new SimpleDateFormat("yyyy");
    private SimpleDateFormat simpleDateFormatCollectionDataCaixaDiario = new SimpleDateFormat("dd/MM/yyyy");
    private SimpleDateFormat simpleDateFormatHoraProcessamentoDaVenda = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    private boolean verificaVendaIfood = false;
    private boolean verificaVendaLoja = false;


    private boolean verificaMetodoDinheirOuPix = false;
    private boolean verificaMetodoDebito = false;
    private boolean verificaMetodoCredito = false;

    private String valorQuePrecisaraSerSomadoEmGeral;
    private String valorQuePrecisaraSerSomadoDependendoDeOndeSaiuAVenda;
    private String valorQuePrecisaSerSomadoDependendoDoTipoDeMetodoDePagamento;


    private String valorQuePrecisaraSerSomadoEmGeralDiario;
    private String valorQuePrecisaraSerSomadoDependendoDeOndeSaiuAVendaDiario;
    private String valorQuePrecisaSerSomadoDependendoDoTipoDeMetodoDePagamentoDiario;


    private FirebaseFirestore firestore = ConfiguracaoFirebase.getFirestor();
    private CollectionReference collectionReferenceMontanteMensal;

    public ModeloProcessaVendaFeitaDAO(Context context) {
        this.context = context;
        String mesReferenciaDesseMontante = mesAtual + "_" + anoAtual;
        dataHojeReferencia = new Date();
        hojeString = simpleDateFormat.format(dataHojeReferencia.getTime());
        mesAtual = simpleDateFormatCollectionReferenciaAtual.format(dataHojeReferencia.getTime());
        diaAtual = simpleDateFormatVerificaDiaAtual.format(dataHojeReferencia.getTime());
        anoAtual = simpleDateFormatCollectionReferenciaAnoAtual.format(dataHojeReferencia.getTime());
        dataCompletaDoProcessamentoDaVenda = simpleDateFormatHoraProcessamentoDaVenda.format(dataHojeReferencia.getTime());
        dataCompleta = simpleDateFormatCollectionDataCaixaDiario.format(dataHojeReferencia.getTime());
        nomeCollectionNomeMontanteMensal = COLLECTION_MONTANTE_MENSAL + mesReferenciaDesseMontante;
        nomeCollectionNomeCaixaDiario = COLLECTION_CAIXA_DIARIO + mesReferenciaDesseMontante;
    }

    @Override
    public boolean processaVendaFeita(BolosModel bolosModel, String verificaTipoDeVenda, String verificaTipoDeMetodoDePagamento) {
        String mesReferenciaDesseMontante = mesAtual + "_" + anoAtual;
        Map<String, Object> montanteMensalAtualizandoDevidoAVendaMensal = new HashMap<>();
        Map<String, Object> montanteDiarioAtualizandoDevidoAVendaDiario = new HashMap<>();


        try {
            firestore.collection(nomeCollectionNomeMontanteMensal).whereEqualTo("mesReferenciaDesseMontante", mesReferenciaDesseMontante).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                    List<DocumentSnapshot> snapshotList = queryDocumentSnapshots.getDocuments();

                    if (snapshotList.size() > 0) {
                        for (DocumentSnapshot listaCaixasDiariosCriados : snapshotList) {
                            String idRecuperado = listaCaixasDiariosCriados.getId();
                            FirebaseFirestore firebaseFirestore = ConfiguracaoFirebase.getFirestor();
                            firebaseFirestore.collection(nomeCollectionNomeMontanteMensal).document(idRecuperado).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {

                                    ModeloMontanteMensalLoja modeloMontanteMensalLojaRecuperadoJaCriado = documentSnapshot.toObject(ModeloMontanteMensalLoja.class);
                                    String valorQueOProdutoFoiVendido = bolosModel.getValorQueOBoloRealmenteFoiVendido();
                                    double valorQueOProdutoFoiVendidoConvert = Double.parseDouble(valorQueOProdutoFoiVendido);
                                    valorQuePrecisaraSerSomadoEmGeral = modeloMontanteMensalLojaRecuperadoJaCriado.getValorTotalVendasEmGeralMensal();
                                    //Verifica qual plataforma que saiu a venda
                                    if (verificaTipoDeVenda.equals("IFOOD")) {
                                        valorQuePrecisaraSerSomadoDependendoDeOndeSaiuAVenda = modeloMontanteMensalLojaRecuperadoJaCriado.getValorTotalVendasIfoodMensal().replace(",", ".");
                                        verificaVendaIfood = true;
                                        verificaVendaLoja = false;
                                        double valorQuePrecisaAtualizarDeVendasDependendoDaPlataformaConvert = Double.parseDouble(valorQuePrecisaraSerSomadoDependendoDeOndeSaiuAVenda);
                                        double resultadoDeVendasDependendoDaPlataform = valorQuePrecisaAtualizarDeVendasDependendoDaPlataformaConvert + valorQueOProdutoFoiVendidoConvert;
                                        String vendasDependendoDaPlataformaConvert = String.valueOf(resultadoDeVendasDependendoDaPlataform);
                                        montanteMensalAtualizandoDevidoAVendaMensal.put("valorTotalVendasIfoodMensal", vendasDependendoDaPlataformaConvert);
                                        Toast.makeText(context, "VENDEU PELO IFOOD", Toast.LENGTH_SHORT).show();
                                    } else if (verificaTipoDeVenda.equals("LOJA")) {
                                        verificaVendaIfood = false;
                                        verificaVendaLoja = true;
                                        valorQuePrecisaraSerSomadoDependendoDeOndeSaiuAVenda = modeloMontanteMensalLojaRecuperadoJaCriado.getValorTotalVendasBoleriaMensal().replace(",", ".");
                                        double valorQuePrecisaAtualizarDeVendasDependendoDaPlataformaConvert = Double.parseDouble(valorQuePrecisaraSerSomadoDependendoDeOndeSaiuAVenda);
                                        double resultadoDeVendasDependendoDaPlataform = valorQuePrecisaAtualizarDeVendasDependendoDaPlataformaConvert + valorQueOProdutoFoiVendidoConvert;
                                        String vendasDependendoDaPlataformaConvert = String.valueOf(resultadoDeVendasDependendoDaPlataform);
                                        montanteMensalAtualizandoDevidoAVendaMensal.put("valorTotalVendasBoleriaMensal", vendasDependendoDaPlataformaConvert);
                                        Toast.makeText(context, "VENDEU PELA LOJA", Toast.LENGTH_SHORT).show();


                                    } else if (verificaTipoDeVenda.equals("EDITOU")) {
                                        verificaVendaIfood = false;
                                        verificaVendaLoja = false;
                                        valorQuePrecisaraSerSomadoDependendoDeOndeSaiuAVenda = modeloMontanteMensalLojaRecuperadoJaCriado.getValorTotalVendasBoleriaMensal().replace(",", ".");
                                        double valorQuePrecisaAtualizarDeVendasDependendoDaPlataformaConvert = Double.parseDouble(valorQuePrecisaraSerSomadoDependendoDeOndeSaiuAVenda);
                                        double resultadoDeVendasDependendoDaPlataform = valorQuePrecisaAtualizarDeVendasDependendoDaPlataformaConvert + valorQueOProdutoFoiVendidoConvert;
                                        String vendasDependendoDaPlataformaConvert = String.valueOf(resultadoDeVendasDependendoDaPlataform);
                                        montanteMensalAtualizandoDevidoAVendaMensal.put("valorTotalVendasBoleriaMensal", vendasDependendoDaPlataformaConvert);
                                        Toast.makeText(context, "EDITOU O VALOR SERÁ MARCADO COMO VENDA NA LOJA", Toast.LENGTH_SHORT).show();
                                    }
                                    //Verifica o metodo de pagamento da venda
                                    if (verificaTipoDeMetodoDePagamento.equals("DINHEIROOUPIX")) {
                                        verificaMetodoDinheirOuPix = true;
                                        valorQuePrecisaSerSomadoDependendoDoTipoDeMetodoDePagamento = modeloMontanteMensalLojaRecuperadoJaCriado.getValorTotalDeVendasNoDinheiroDesseMes().replace(",", ".");

                                        double valorQuePrecisaAtualizarDeVendasDependendoDaPlataformaConvert = Double.parseDouble(valorQuePrecisaraSerSomadoDependendoDeOndeSaiuAVenda);
                                        double resultadoDeVendasDependendoDaPlataform = valorQuePrecisaAtualizarDeVendasDependendoDaPlataformaConvert + valorQueOProdutoFoiVendidoConvert;
                                        String vendasDependendoDaPlataformaConvert = String.valueOf(resultadoDeVendasDependendoDaPlataform);
                                        montanteMensalAtualizandoDevidoAVendaMensal.put("valorTotalDeVendasNoDinheiroDesseMes", vendasDependendoDaPlataformaConvert);
                                        Toast.makeText(context, "E PAGOU EM DINHEIRO OU PIX", Toast.LENGTH_SHORT).show();

                                    } else if (verificaTipoDeMetodoDePagamento.equals("CREDITO")) {
                                        verificaMetodoCredito = true;
                                        valorQuePrecisaSerSomadoDependendoDoTipoDeMetodoDePagamento = modeloMontanteMensalLojaRecuperadoJaCriado.getValorTotalDeVendasNoCreditoDesseMes().replace(",", ".");

                                        double valorQuePrecisaAtualizarDeVendasDependendoDaPlataformaConvert = Double.parseDouble(valorQuePrecisaraSerSomadoDependendoDeOndeSaiuAVenda);
                                        double resultadoDeVendasDependendoDaPlataform = valorQuePrecisaAtualizarDeVendasDependendoDaPlataformaConvert + valorQueOProdutoFoiVendidoConvert;
                                        String vendasDependendoDaPlataformaConvert = String.valueOf(resultadoDeVendasDependendoDaPlataform);
                                        montanteMensalAtualizandoDevidoAVendaMensal.put("valorTotalDeVendasNoCreditoDesseMes", vendasDependendoDaPlataformaConvert);
                                        Toast.makeText(context, "E PAGOU PELO CRÉDITO", Toast.LENGTH_SHORT).show();
                                    } else if (verificaTipoDeMetodoDePagamento.equals("DEBITO")) {

                                        valorQuePrecisaSerSomadoDependendoDoTipoDeMetodoDePagamento = modeloMontanteMensalLojaRecuperadoJaCriado.getValorTotalDeVendasNoDebitoDesseMes().replace(",", ".");

                                        double valorQuePrecisaAtualizarDeVendasDependendoDaPlataformaConvert = Double.parseDouble(valorQuePrecisaraSerSomadoDependendoDeOndeSaiuAVenda);
                                        double resultadoDeVendasDependendoDaPlataform = valorQuePrecisaAtualizarDeVendasDependendoDaPlataformaConvert + valorQueOProdutoFoiVendidoConvert;
                                        String vendasDependendoDaPlataformaConvert = String.valueOf(resultadoDeVendasDependendoDaPlataform);
                                        montanteMensalAtualizandoDevidoAVendaMensal.put("valorTotalDeVendasNoDebitoDesseMes", vendasDependendoDaPlataformaConvert);
                                        Toast.makeText(context, "E PAGOU PELO DÉBITO", Toast.LENGTH_SHORT).show();
                                        verificaMetodoDebito = true;
                                    }


                                    double valorQuePrecisaAtualizarDeVendasEmGeral = Double.parseDouble(valorQuePrecisaraSerSomadoEmGeral);
                                    double resultadoDeVendasDependendoDaPlataform = valorQuePrecisaAtualizarDeVendasEmGeral + valorQueOProdutoFoiVendidoConvert;
                                    String vendasDependendoDaPlataformaConvert = String.valueOf(resultadoDeVendasDependendoDaPlataform);
                                    montanteMensalAtualizandoDevidoAVendaMensal.put("valorTotalVendasEmGeralMensal", vendasDependendoDaPlataformaConvert);

                                    firebaseFirestore.collection(nomeCollectionNomeMontanteMensal).document(idRecuperado).update(montanteMensalAtualizandoDevidoAVendaMensal).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(context, "MONTANTE ATUALIZADO", Toast.LENGTH_SHORT).show();
                                            firestore.collection(nomeCollectionNomeCaixaDiario).whereEqualTo("dataReferenciaMontanteDiarioDesseDia", dataCompleta).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                                @Override
                                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                                                    List<DocumentSnapshot> listaDoCaixaDesseDia = queryDocumentSnapshots.getDocuments();

                                                    if (listaDoCaixaDesseDia.size() > 0) {
                                                        for (DocumentSnapshot list : listaDoCaixaDesseDia) {
                                                            String idRecuperadoDiario = list.getId();
                                                            firestore.collection(nomeCollectionNomeCaixaDiario).document(idRecuperadoDiario)
                                                                    .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                                        @Override
                                                                        public void onSuccess(DocumentSnapshot documentSnapshot) {

                                                                            ModeloMontanteDiario modeloMontanteDiarioRecuperado = documentSnapshot.toObject(ModeloMontanteDiario.class);
                                                                            ProdutoVendido produtoVendidoCaixa = new ProdutoVendido();
                                                                            produtoVendidoCaixa.setIdDoProdutoVendido(bolosModel.getIdBoloCadastrado());
                                                                            produtoVendidoCaixa.setIdReferenciaDoProduto(bolosModel.getIdReferenciaReceitaCadastrada());
                                                                            produtoVendidoCaixa.setValorDaVenda(bolosModel.getValorQueOBoloRealmenteFoiVendido());
                                                                            produtoVendidoCaixa.setPlataformaVendida(verificaTipoDeVenda);
                                                                            produtoVendidoCaixa.setMetodoDePagamento(verificaTipoDeMetodoDePagamento);
                                                                            produtoVendidoCaixa.setDataDaVenda(dataCompletaDoProcessamentoDaVenda);

                                                                            String valorQueOProdutoFoiVendido = bolosModel.getValorQueOBoloRealmenteFoiVendido();
                                                                            double valorQueOProdutoFoiVendidoConvert = Double.parseDouble(valorQueOProdutoFoiVendido);
                                                                            valorQuePrecisaraSerSomadoEmGeralDiario = modeloMontanteDiarioRecuperado.getValorTotalDeVendasEmGeralDesseDia();
                                                                            //Verifica qual plataforma que saiu a venda
                                                                            if (verificaTipoDeVenda.equals("IFOOD")) {
                                                                                valorQuePrecisaraSerSomadoDependendoDeOndeSaiuAVendaDiario = modeloMontanteDiarioRecuperado.getValorTotalDeVendasNoIfoodDesseDia().replace(",", ".");

                                                                                double valorQuePrecisaAtualizarDeVendasDependendoDaPlataformaConvertDario = Double.parseDouble(valorQuePrecisaraSerSomadoDependendoDeOndeSaiuAVenda);
                                                                                double resultadoDeVendasDependendoDaPlataformDario = valorQuePrecisaAtualizarDeVendasDependendoDaPlataformaConvertDario + valorQueOProdutoFoiVendidoConvert;
                                                                                String vendasDependendoDaPlataformaConvertDiario = String.valueOf(resultadoDeVendasDependendoDaPlataformDario);
                                                                                montanteDiarioAtualizandoDevidoAVendaDiario.put("valorTotalDeVendasNoIfoodDesseDia", vendasDependendoDaPlataformaConvertDiario);

                                                                            } else if (verificaTipoDeVenda.equals("LOJA")) {

                                                                                valorQuePrecisaraSerSomadoDependendoDeOndeSaiuAVendaDiario = modeloMontanteDiarioRecuperado.getValorTotalDeVendasNaLojaDesseDia().replace(",", ".");
                                                                                double valorQuePrecisaAtualizarDeVendasDependendoDaPlataformaConvertDario = Double.parseDouble(valorQuePrecisaraSerSomadoDependendoDeOndeSaiuAVenda);
                                                                                double resultadoDeVendasDependendoDaPlataformDario = valorQuePrecisaAtualizarDeVendasDependendoDaPlataformaConvertDario + valorQueOProdutoFoiVendidoConvert;
                                                                                String vendasDependendoDaPlataformaConvertDiario = String.valueOf(resultadoDeVendasDependendoDaPlataformDario);
                                                                                montanteDiarioAtualizandoDevidoAVendaDiario.put("valorTotalDeVendasNaLojaDesseDia", vendasDependendoDaPlataformaConvertDiario);



                                                                            } else if (verificaTipoDeVenda.equals("EDITOU")) {

                                                                                valorQuePrecisaraSerSomadoDependendoDeOndeSaiuAVendaDiario = modeloMontanteDiarioRecuperado.getValorTotalDeVendasNaLojaDesseDia().replace(",", ".");
                                                                                double valorQuePrecisaAtualizarDeVendasDependendoDaPlataformaConvertDario = Double.parseDouble(valorQuePrecisaraSerSomadoDependendoDeOndeSaiuAVendaDiario);
                                                                                double resultadoDeVendasDependendoDaPlataformDario = valorQuePrecisaAtualizarDeVendasDependendoDaPlataformaConvertDario + valorQueOProdutoFoiVendidoConvert;
                                                                                String vendasDependendoDaPlataformaConvertDiario = String.valueOf(resultadoDeVendasDependendoDaPlataformDario);
                                                                                montanteDiarioAtualizandoDevidoAVendaDiario.put("valorTotalDeVendasNaLojaDesseDia", vendasDependendoDaPlataformaConvertDiario);
                                                                            }
                                                                            //Verifica o metodo de pagamento da venda
                                                                            if (verificaTipoDeMetodoDePagamento.equals("DINHEIROOUPIX")) {

                                                                                valorQuePrecisaSerSomadoDependendoDoTipoDeMetodoDePagamentoDiario = modeloMontanteDiarioRecuperado.getValorTotalDeVendasNoDinheiroDesseDia().replace(",", ".");

                                                                                double valorQuePrecisaAtualizarDeVendasDependendoDaPlataformaConvertDiario = Double.parseDouble(valorQuePrecisaraSerSomadoDependendoDeOndeSaiuAVenda);
                                                                                double resultadoDeVendasDependendoDaPlataformvalorQuePrecisaraSerSomadoDependendoDeOndeSaiuAVendaDiario = valorQuePrecisaAtualizarDeVendasDependendoDaPlataformaConvertDiario + valorQueOProdutoFoiVendidoConvert;
                                                                                String vendasDependendoDaPlataformaConvertvalorQuePrecisaraSerSomadoDependendoDeOndeSaiuAVendaDiario = String.valueOf(resultadoDeVendasDependendoDaPlataformvalorQuePrecisaraSerSomadoDependendoDeOndeSaiuAVendaDiario);
                                                                                montanteDiarioAtualizandoDevidoAVendaDiario.put("valorTotalDeVendasNoDinheiroDesseDia", vendasDependendoDaPlataformaConvertvalorQuePrecisaraSerSomadoDependendoDeOndeSaiuAVendaDiario);


                                                                            } else if (verificaTipoDeMetodoDePagamento.equals("CREDITO")) {

                                                                                valorQuePrecisaSerSomadoDependendoDoTipoDeMetodoDePagamentoDiario = modeloMontanteDiarioRecuperado.getValorTotalDeVendasNoCreditoDesseDia().replace(",", ".");

                                                                                double valorQuePrecisaAtualizarDeVendasDependendoDaPlataformaConvertDiario = Double.parseDouble(valorQuePrecisaraSerSomadoDependendoDeOndeSaiuAVenda);
                                                                                double resultadoDeVendasDependendoDaPlataformDiario = valorQuePrecisaAtualizarDeVendasDependendoDaPlataformaConvertDiario + valorQueOProdutoFoiVendidoConvert;
                                                                                String vendasDependendoDaPlataformaConvertDiario = String.valueOf(resultadoDeVendasDependendoDaPlataformDiario);
                                                                                montanteDiarioAtualizandoDevidoAVendaDiario.put("valorTotalDeVendasNoCreditoDesseDia", vendasDependendoDaPlataformaConvertDiario);

                                                                            } else if (verificaTipoDeMetodoDePagamento.equals("DEBITO")) {

                                                                                valorQuePrecisaSerSomadoDependendoDoTipoDeMetodoDePagamentoDiario = modeloMontanteDiarioRecuperado.getValorTotalDeVendasNoDebitoDesseDia().replace(",", ".");

                                                                                double valorQuePrecisaAtualizarDeVendasDependendoDaPlataformaConvertDiario = Double.parseDouble(valorQuePrecisaraSerSomadoDependendoDeOndeSaiuAVenda);
                                                                                double resultadoDeVendasDependendoDaPlataformDiario = valorQuePrecisaAtualizarDeVendasDependendoDaPlataformaConvertDiario + valorQueOProdutoFoiVendidoConvert;
                                                                                String vendasDependendoDaPlataformaConvertDiario = String.valueOf(resultadoDeVendasDependendoDaPlataformDiario);
                                                                                montanteDiarioAtualizandoDevidoAVendaDiario.put("valorTotalDeVendasNoDebitoDesseDia", vendasDependendoDaPlataformaConvertDiario);

                                                                            }

                                                                            double valorQuePrecisaAtualizarDeVendasEmGeralDiario = Double.parseDouble(valorQuePrecisaraSerSomadoEmGeralDiario);
                                                                            double resultadoDeVendasDependendoDaPlataformDiario = valorQuePrecisaAtualizarDeVendasEmGeralDiario + valorQueOProdutoFoiVendidoConvert;
                                                                            String vendasDependendoDaPlataformaConvertDiario = String.valueOf(resultadoDeVendasDependendoDaPlataformDiario);
                                                                            montanteDiarioAtualizandoDevidoAVendaDiario.put("valorTotalDeVendasEmGeralDesseDia", vendasDependendoDaPlataformaConvertDiario);
                                                                            firestore.collection(nomeCollectionNomeCaixaDiario).document(idRecuperadoDiario).update(montanteDiarioAtualizandoDevidoAVendaDiario)
                                                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                        @Override
                                                                                        public void onSuccess(Void unused) {


                                                                                            Toast.makeText(context, "CAIXA DIARIO ATUALIZADO TAMBÉM", Toast.LENGTH_SHORT).show();
                                                                                        }
                                                                                    }).addOnFailureListener(new OnFailureListener() {
                                                                                        @Override
                                                                                        public void onFailure(@NonNull Exception e) {

                                                                                        }
                                                                                    });



                                                                        }
                                                                    }).addOnFailureListener(new OnFailureListener() {
                                                                        @Override
                                                                        public void onFailure(@NonNull Exception e) {
                                                                            Toast.makeText(context, "Verifique sua conexão e tente novamente!", Toast.LENGTH_SHORT).show();
                                                                            Log.i("Erro dentro do Processa venda 20 ", e.getMessage());
                                                                        }
                                                                    });

                                                        }
                                                    }

                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(context, "Verifique sua conexão e tente novamente!", Toast.LENGTH_SHORT).show();
                                                    Log.i("Erro dentro do Processa venda 21 ", e.getMessage());
                                                }
                                            });


                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(context, "Verifique sua conexão e tente novamente!", Toast.LENGTH_SHORT).show();
                                            Log.i("Erro dentro do Processa venda 22 ", e.getMessage());
                                        }
                                    });

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(context, "Algo deu errado, verifique sua conexão de internet e tente novamente", Toast.LENGTH_SHORT).show();
                                    Log.i("Erro dentro do Processa venda 23 ", e.getMessage());
                                }
                            });


                        }

                    }


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(context, "Algo deu errado, verifique sua conexão de internet e tente novamente", Toast.LENGTH_SHORT).show();
                    Log.i("Erro dentro do Processa venda 23 ", e.getMessage());
                }
            });

        } catch (Exception e) {

        }


        return false;
    }
}
