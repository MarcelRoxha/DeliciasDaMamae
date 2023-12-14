package com.marcel.a.n.roxha.deliciasdamamae.controller.helper;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.marcel.a.n.roxha.deliciasdamamae.activity.LojaActivityV2;
import com.marcel.a.n.roxha.deliciasdamamae.config.ConfiguracaoFirebase;
import com.marcel.a.n.roxha.deliciasdamamae.model.BolosModel;
import com.marcel.a.n.roxha.deliciasdamamae.model.ModeloControleRelatorioDiario;
import com.marcel.a.n.roxha.deliciasdamamae.model.ModeloControleRelatorioMensal;
import com.marcel.a.n.roxha.deliciasdamamae.model.ModeloMontanteDiario;
import com.marcel.a.n.roxha.deliciasdamamae.model.ModeloMontanteMensalLoja;
import com.marcel.a.n.roxha.deliciasdamamae.model.ModeloProdutoVendido;
import com.marcel.a.n.roxha.deliciasdamamae.model.ProdutoVendido;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModeloProcessaVendaFeitaDAO implements InterfaceModeloProcessaVendaFeitaDAO {
    private Context context;
    private static final String COLLECTION_MONTANTE_MENSAL = "MONTANTE_MENSAL_";
    private static final String COLLECTION_CAIXA_DIARIO = "CAIXAS_DIARIO_";

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

    private String valorQuePrecisaraSerSomadoEmGeralMontanteMensal = "";
    private String valorQuePrecisaraSerSomadoEmGeralMontanteDiario = "";
    private String valorQuePrecisaraSerSomadoDependendoDeOndeSaiuAVendaMensal = "";
    private String valorQuePrecisaraSerSomadoDependendoDeOndeSaiuAVendaMontanteDiario = "";
    private String valorQuePrecisaSerSomadoDependendoDoTipoDeMetodoDePagamentoMensal = "";
    private String valorQuePrecisaSerSomadoDependendoDoTipoDeMetodoDePagamentoDiario = "";


//    private String valorQuePrecisaraSerSomadoEmGeralDiario;
//    private String valorQuePrecisaraSerSomadoDependendoDeOndeSaiuAVendaDiario;
//    private String valorQuePrecisaSerSomadoDependendoDoTipoDeMetodoDePagamentoDiario;

    private String valorQueOProdutoFoiVendido;
    private FirebaseFirestore firestore = ConfiguracaoFirebase.getFirestor();
    private CollectionReference collectionReferenceMontanteMensal;

    private ModeloMontanteMensalLoja modeloMontanteMensalLoja;
    private  ModeloMontanteDiario modeloMontanteDiario;

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
    public boolean processaNoMontanteMensalVendaFeita(@NonNull ModeloProdutoVendido modeloProdutoVendido, String idMontanteMensal, String idMontanteDiario) {
        String mesReferenciaDesseMontante = mesAtual + "_" + anoAtual;
        valorQueOProdutoFoiVendido = modeloProdutoVendido.getValorQueOBoloFoiVendido();
        Map<String, Object> montanteMensalAtualizandoDevidoAVendaMensal = new HashMap<>();
        nomeCollectionNomeMontanteMensal = COLLECTION_MONTANTE_MENSAL + mesReferenciaDesseMontante;
        nomeCollectionNomeCaixaDiario = COLLECTION_CAIXA_DIARIO + mesReferenciaDesseMontante;
        ModeloMontanteMensalLoja montanteMensalLojaAtualizar = new ModeloMontanteMensalLoja();

        try {


           firestore.collection(nomeCollectionNomeMontanteMensal).document(idMontanteMensal)
                    .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            modeloMontanteMensalLoja = documentSnapshot.toObject(ModeloMontanteMensalLoja.class);
                            valorQuePrecisaraSerSomadoEmGeralMontanteMensal = modeloMontanteMensalLoja.getValorTotalVendasEmGeralMensal().replace(",", ".");
                            int quantidadeDeVendasEsseMes = modeloMontanteMensalLoja.getQuantidadeDeVendasDesseMes();
                            int somaQuantidade = quantidadeDeVendasEsseMes + 1;
                            montanteMensalAtualizandoDevidoAVendaMensal.put("quantidadeDeVendasDesseMes", somaQuantidade);

                            double valorQuePrecisaSerSomadoEmGeralMontanteMensal = Double.parseDouble(valorQuePrecisaraSerSomadoEmGeralMontanteMensal);
                            double valorQueOProdutoFoiVendidoConvert = Double.parseDouble(valorQueOProdutoFoiVendido);
                            double resultadoEmGeralMontanteMensal = valorQuePrecisaSerSomadoEmGeralMontanteMensal + valorQueOProdutoFoiVendidoConvert;
                            String resultadoConvertido = String.format("%.2f", resultadoEmGeralMontanteMensal);
                            montanteMensalAtualizandoDevidoAVendaMensal.put("valorTotalVendasEmGeralMensal", resultadoConvertido);
                            montanteMensalLojaAtualizar.setValorTotalVendasEmGeralMensal(resultadoConvertido);


                            if (modeloProdutoVendido.getPlataformaVendida().equals("IFOOD")) {
                                valorQuePrecisaraSerSomadoDependendoDeOndeSaiuAVendaMensal = modeloMontanteMensalLoja.getValorTotalVendasIfoodMensal().replace(",", ".");
                                double valorQuePrecisaAtualizarDeVendasDependendoDaPlataformaConvert = Double.parseDouble(valorQuePrecisaraSerSomadoDependendoDeOndeSaiuAVendaMensal);
                                double resultadoDeVendasDependendoDaPlataform = valorQuePrecisaAtualizarDeVendasDependendoDaPlataformaConvert + valorQueOProdutoFoiVendidoConvert;
                                String vendasDependendoDaPlataformaConvert = String.format("%.2f", resultadoDeVendasDependendoDaPlataform);
                                montanteMensalAtualizandoDevidoAVendaMensal.put("valorTotalVendasIfoodMensal", vendasDependendoDaPlataformaConvert);
                                montanteMensalLojaAtualizar.setValorTotalVendasIfoodMensal(vendasDependendoDaPlataformaConvert);
                                Toast.makeText(context, "VENDEU PELO IFOOD", Toast.LENGTH_SHORT).show();
                            } else if (modeloProdutoVendido.getPlataformaVendida().equals("LOJA")) {
                                valorQuePrecisaraSerSomadoDependendoDeOndeSaiuAVendaMensal = modeloMontanteMensalLoja.getValorTotalVendasBoleriaMensal().replace(",", ".");
                                double valorQuePrecisaAtualizarDeVendasDependendoDaPlataformaConvert = Double.parseDouble(valorQuePrecisaraSerSomadoDependendoDeOndeSaiuAVendaMensal);
                                double resultadoDeVendasDependendoDaPlataform = valorQuePrecisaAtualizarDeVendasDependendoDaPlataformaConvert + valorQueOProdutoFoiVendidoConvert;
                                String vendasDependendoDaPlataformaConvert = String.format("%.2f", resultadoDeVendasDependendoDaPlataform);
                                montanteMensalAtualizandoDevidoAVendaMensal.put("valorTotalVendasBoleriaMensal", vendasDependendoDaPlataformaConvert);
                                montanteMensalLojaAtualizar.setValorTotalVendasBoleriaMensal(vendasDependendoDaPlataformaConvert);
                                Toast.makeText(context, "VENDEU PELA LOJA", Toast.LENGTH_SHORT).show();
                            } else if (modeloProdutoVendido.getPlataformaVendida().equals("EDITOU")) {
                                valorQuePrecisaraSerSomadoDependendoDeOndeSaiuAVendaMensal = modeloMontanteMensalLoja.getValorTotalVendasBoleriaMensal().replace(",", ".");
                                double valorQuePrecisaAtualizarDeVendasDependendoDaPlataformaConvert = Double.parseDouble(valorQuePrecisaraSerSomadoDependendoDeOndeSaiuAVendaMensal);
                                double resultadoDeVendasDependendoDaPlataform = valorQuePrecisaAtualizarDeVendasDependendoDaPlataformaConvert + valorQueOProdutoFoiVendidoConvert;
                                String vendasDependendoDaPlataformaConvert = String.format("%.2f", resultadoDeVendasDependendoDaPlataform);
                                montanteMensalAtualizandoDevidoAVendaMensal.put("valorTotalVendasBoleriaMensal", vendasDependendoDaPlataformaConvert);
                                montanteMensalLojaAtualizar.setValorTotalVendasBoleriaMensal(vendasDependendoDaPlataformaConvert);
                                Toast.makeText(context, "EDITOU O VALOR SERÁ MARCADO COMO VENDA NA LOJA", Toast.LENGTH_SHORT).show();
                            }
                            if (modeloProdutoVendido.getMetodoDePagamento().equals("DINHEIROOUPIX")) {
                                valorQuePrecisaraSerSomadoDependendoDeOndeSaiuAVendaMensal = modeloMontanteMensalLoja.getValorTotalDeVendasNoDinheiroDesseMes().replace(",", ".");
                                double valorQuePrecisaAtualizarDeVendasDependendoDaPlataformaConvert = Double.parseDouble(valorQuePrecisaraSerSomadoDependendoDeOndeSaiuAVendaMensal);
                                double resultadoDeVendasDependendoDaPlataform = valorQuePrecisaAtualizarDeVendasDependendoDaPlataformaConvert + valorQueOProdutoFoiVendidoConvert;
                                String vendasDependendoDaPlataformaConvert = String.format("%.2f", resultadoDeVendasDependendoDaPlataform);
                                montanteMensalAtualizandoDevidoAVendaMensal.put("valorTotalDeVendasNoDinheiroDesseMes", vendasDependendoDaPlataformaConvert);
                                montanteMensalLojaAtualizar.setValorTotalDeVendasNoDinheiroDesseMes(vendasDependendoDaPlataformaConvert);
                                Toast.makeText(context, "E PAGOU EM DINHEIRO OU PIX", Toast.LENGTH_SHORT).show();

                            } else if (modeloProdutoVendido.getMetodoDePagamento().equals("CREDITO")) {
                                valorQuePrecisaraSerSomadoDependendoDeOndeSaiuAVendaMensal = modeloMontanteMensalLoja.getValorTotalDeVendasNoCreditoDesseMes().replace(",", ".");
                                double valorQuePrecisaAtualizarDeVendasDependendoDaPlataformaConvert = Double.parseDouble(valorQuePrecisaraSerSomadoDependendoDeOndeSaiuAVendaMensal);
                                double resultadoDeVendasDependendoDaPlataform = valorQuePrecisaAtualizarDeVendasDependendoDaPlataformaConvert + valorQueOProdutoFoiVendidoConvert;
                                String vendasDependendoDaPlataformaConvert = String.format("%.2f", resultadoDeVendasDependendoDaPlataform);
                                montanteMensalAtualizandoDevidoAVendaMensal.put("valorTotalDeVendasNoCreditoDesseMes", vendasDependendoDaPlataformaConvert);
                                montanteMensalLojaAtualizar.setValorTotalDeVendasNoCreditoDesseMes(vendasDependendoDaPlataformaConvert);
                                Toast.makeText(context, "E PAGOU PELO CRÉDITO", Toast.LENGTH_SHORT).show();
                            } else if (modeloProdutoVendido.getMetodoDePagamento().equals("DEBITO")) {
                                valorQuePrecisaraSerSomadoDependendoDeOndeSaiuAVendaMensal = modeloMontanteMensalLoja.getValorTotalDeVendasNoDebitoDesseMes().replace(",", ".");
                                double valorQuePrecisaAtualizarDeVendasDependendoDaPlataformaConvert = Double.parseDouble(valorQuePrecisaraSerSomadoDependendoDeOndeSaiuAVendaMensal);
                                double resultadoDeVendasDependendoDaPlataform = valorQuePrecisaAtualizarDeVendasDependendoDaPlataformaConvert + valorQueOProdutoFoiVendidoConvert;
                                String vendasDependendoDaPlataformaConvert = String.format("%.2f", resultadoDeVendasDependendoDaPlataform);
                                montanteMensalAtualizandoDevidoAVendaMensal.put("valorTotalDeVendasNoDebitoDesseMes", vendasDependendoDaPlataformaConvert);
                                Toast.makeText(context, "E PAGOU PELO DÉBITO", Toast.LENGTH_SHORT).show();
                            }

                            firestore.collection(nomeCollectionNomeMontanteMensal).document(idMontanteMensal).update(montanteMensalAtualizandoDevidoAVendaMensal)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(context, "MONTANTE MENSAL ATUALIZADO", Toast.LENGTH_SHORT).show();
                                            adicionarVendaNoRelatorioMensal(modeloProdutoVendido, idMontanteMensal, idMontanteDiario);

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(context, "Algo deu errado verifique sua conexão e tente novamente", Toast.LENGTH_SHORT).show();
                                            System.out.println("Error################# " + e.getMessage());

                                        }
                                    });


                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                        System.out.println("Errooooo " + e.getMessage());
                        }
                    });


        } catch (Exception e) {

        }


        return false;
    }
    public boolean processaNoMontanteDiarioVendaFeita(@NonNull ModeloProdutoVendido modeloProdutoVendido, String idMontanteDiario) {
        valorQueOProdutoFoiVendido = modeloProdutoVendido.getValorQueOBoloFoiVendido();
        Map<String, Object> montanteDiarioAtualizandoDevidoAVendaDiario = new HashMap<>();

        try {

            firestore.collection(nomeCollectionNomeCaixaDiario).document(idMontanteDiario)
                    .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {


                            modeloMontanteDiario = documentSnapshot.toObject(ModeloMontanteDiario.class);
                            double valorQueOProdutoFoiVendidoConvert = Double.parseDouble(valorQueOProdutoFoiVendido);
                            int quantiadeRecuperada = modeloMontanteDiario.getQuantidadeDeVendasFeitasDesseDia();
                            int soma = quantiadeRecuperada + 1;
                            montanteDiarioAtualizandoDevidoAVendaDiario.put("quantidadeDeVendasFeitasDesseDia", soma);
                           // System.out.println("valorQuePrecisaraSerSomadoEmGeralMontanteDiario" + modeloMontanteDiario.getValorTotalDeVendasEmGeralDesseDia().replace(",", "."));
                            valorQuePrecisaraSerSomadoEmGeralMontanteDiario = modeloMontanteDiario.getValorTotalDeVendasEmGeralDesseDia().replace(",", ".");

                            double valorQuePrecisaSerSomadoEmGeralMontanteMensal = Double.parseDouble(valorQuePrecisaraSerSomadoEmGeralMontanteDiario);
                            double resultadoEmGeralMontanteMensal = valorQuePrecisaSerSomadoEmGeralMontanteMensal + valorQueOProdutoFoiVendidoConvert;
                            String resultadoConvertido = String.format("%.2f", resultadoEmGeralMontanteMensal);
                            montanteDiarioAtualizandoDevidoAVendaDiario.put("valorTotalDeVendasEmGeralDesseDia", resultadoConvertido);
                            //Verifica qual plataforma que saiu a venda
                            if (modeloProdutoVendido.getPlataformaVendida().equals("IFOOD")) {
                                valorQuePrecisaraSerSomadoDependendoDeOndeSaiuAVendaMontanteDiario = modeloMontanteDiario.getValorTotalDeVendasNoIfoodDesseDia().replace(",", ".");
                                double valorQuePrecisaAtualizarDeVendasDependendoDaPlataformaConvertDario = Double.parseDouble(valorQuePrecisaraSerSomadoDependendoDeOndeSaiuAVendaMontanteDiario);
                                double resultadoDeVendasDependendoDaPlataformDario = valorQuePrecisaAtualizarDeVendasDependendoDaPlataformaConvertDario + valorQueOProdutoFoiVendidoConvert;
                                String vendasDependendoDaPlataformaConvertDiario = String.format("%.2f", resultadoDeVendasDependendoDaPlataformDario);
                                montanteDiarioAtualizandoDevidoAVendaDiario.put("valorTotalDeVendasNoIfoodDesseDia", vendasDependendoDaPlataformaConvertDiario);

                            } else if (modeloProdutoVendido.getPlataformaVendida().equals("LOJA")) {
                                valorQuePrecisaraSerSomadoDependendoDeOndeSaiuAVendaMontanteDiario = modeloMontanteDiario.getValorTotalDeVendasNaLojaDesseDia().replace(",", ".");
                                double valorQuePrecisaAtualizarDeVendasDependendoDaPlataformaConvertDario = Double.parseDouble(valorQuePrecisaraSerSomadoDependendoDeOndeSaiuAVendaMontanteDiario);
                                double resultadoDeVendasDependendoDaPlataformDario = valorQuePrecisaAtualizarDeVendasDependendoDaPlataformaConvertDario + valorQueOProdutoFoiVendidoConvert;
                                String vendasDependendoDaPlataformaConvertDiario = String.format("%.2f", resultadoDeVendasDependendoDaPlataformDario);
                                montanteDiarioAtualizandoDevidoAVendaDiario.put("valorTotalDeVendasNaLojaDesseDia", vendasDependendoDaPlataformaConvertDiario);


                            } else if (modeloProdutoVendido.getPlataformaVendida().equals("EDITOU")) {

                                valorQuePrecisaraSerSomadoDependendoDeOndeSaiuAVendaMontanteDiario = modeloMontanteDiario.getValorTotalDeVendasNaLojaDesseDia().replace(",", ".");
                                double valorQuePrecisaAtualizarDeVendasDependendoDaPlataformaConvertDario = Double.parseDouble(valorQuePrecisaraSerSomadoDependendoDeOndeSaiuAVendaMontanteDiario);
                                double resultadoDeVendasDependendoDaPlataformDario = valorQuePrecisaAtualizarDeVendasDependendoDaPlataformaConvertDario + valorQueOProdutoFoiVendidoConvert;
                                String vendasDependendoDaPlataformaConvertDiario = String.format("%.2f", resultadoDeVendasDependendoDaPlataformDario);
                                montanteDiarioAtualizandoDevidoAVendaDiario.put("valorTotalDeVendasNaLojaDesseDia", vendasDependendoDaPlataformaConvertDiario);
                            }
                            //Verifica o metodo de pagamento da venda
                            if (modeloProdutoVendido.getMetodoDePagamento().equals("DINHEIROOUPIX")) {

                                valorQuePrecisaSerSomadoDependendoDoTipoDeMetodoDePagamentoDiario = modeloMontanteDiario.getValorTotalDeVendasNoDinheiroDesseDia().replace(",", ".");

                                double valorQuePrecisaAtualizarDeVendasDependendoDaPlataformaConvertDiario = Double.parseDouble(valorQuePrecisaraSerSomadoDependendoDeOndeSaiuAVendaMontanteDiario);
                                double resultadoDeVendasDependendoDaPlataformvalorQuePrecisaraSerSomadoDependendoDeOndeSaiuAVendaDiario = valorQuePrecisaAtualizarDeVendasDependendoDaPlataformaConvertDiario + valorQueOProdutoFoiVendidoConvert;
                                String vendasDependendoDaFormaDePagamentoConvertDiario = String.format("%.2f", resultadoDeVendasDependendoDaPlataformvalorQuePrecisaraSerSomadoDependendoDeOndeSaiuAVendaDiario);
                                montanteDiarioAtualizandoDevidoAVendaDiario.put("valorTotalDeVendasNoDinheiroDesseDia", vendasDependendoDaFormaDePagamentoConvertDiario);


                            } else if (modeloProdutoVendido.getMetodoDePagamento().equals("CREDITO")) {

                                valorQuePrecisaSerSomadoDependendoDoTipoDeMetodoDePagamentoDiario = modeloMontanteDiario.getValorTotalDeVendasNoCreditoDesseDia().replace(",", ".");

                                double valorQuePrecisaAtualizarDeVendasDependendoDaPlataformaConvertDiario = Double.parseDouble(valorQuePrecisaSerSomadoDependendoDoTipoDeMetodoDePagamentoDiario);
                                double resultadoDeVendasDependendoDaPlataformDiario = valorQuePrecisaAtualizarDeVendasDependendoDaPlataformaConvertDiario + valorQueOProdutoFoiVendidoConvert;
                                String vendasDependendoDaFormaDePagamentoConvertDiario = String.format("%.2f", resultadoDeVendasDependendoDaPlataformDiario);
                                montanteDiarioAtualizandoDevidoAVendaDiario.put("valorTotalDeVendasNoCreditoDesseDia", vendasDependendoDaFormaDePagamentoConvertDiario);

                            } else if (modeloProdutoVendido.getMetodoDePagamento().equals("DEBITO")) {

                                valorQuePrecisaSerSomadoDependendoDoTipoDeMetodoDePagamentoDiario = modeloMontanteDiario.getValorTotalDeVendasNoDebitoDesseDia().replace(",", ".");

                                double valorQuePrecisaAtualizarDeVendasDependendoDaPlataformaConvertDiario = Double.parseDouble(valorQuePrecisaraSerSomadoDependendoDeOndeSaiuAVendaMontanteDiario);
                                double resultadoDeVendasDependendoDaPlataformDiario = valorQuePrecisaAtualizarDeVendasDependendoDaPlataformaConvertDiario + valorQueOProdutoFoiVendidoConvert;
                                String vendasDependendoDaFormaDePagamentoConvertDiario = String.format("%.2f", resultadoDeVendasDependendoDaPlataformDiario);
                                montanteDiarioAtualizandoDevidoAVendaDiario.put("valorTotalDeVendasNoDebitoDesseDia", vendasDependendoDaFormaDePagamentoConvertDiario);

                            }



                            firestore.collection(nomeCollectionNomeCaixaDiario).document(idMontanteDiario).update(montanteDiarioAtualizandoDevidoAVendaDiario)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(context, "CAIXA DIARIO ATUALIZADO TAMBÉM", Toast.LENGTH_SHORT).show();
                                            adicionarVendaNoRelatorio(modeloProdutoVendido, idMontanteDiario);
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

                        }
                    });





        } catch (Exception e) {

        }


        return false;
    }

    private boolean adicionarVendaNoRelatorio(ModeloProdutoVendido modeloProdutoVendido, String idMontanteDiarioRelatorios){

        Map<String, Object> produtoVendidoAdicionandoParaRelatorios = new HashMap<>();

        produtoVendidoAdicionandoParaRelatorios.put("idDoProdutoVendido", modeloProdutoVendido.getIdDoProdutoVendido());
        produtoVendidoAdicionandoParaRelatorios.put("idDoProdutoCadastrado", modeloProdutoVendido.getIdDoProdutoCadastrado());
        produtoVendidoAdicionandoParaRelatorios.put("valorQueOBoloFoiVendido", modeloProdutoVendido.getValorQueOBoloFoiVendido());
        produtoVendidoAdicionandoParaRelatorios.put("nomeDoProdutoVendido", modeloProdutoVendido.getNomeDoProdutoVendido());
        produtoVendidoAdicionandoParaRelatorios.put("metodoDePagamento", modeloProdutoVendido.getMetodoDePagamento());
        produtoVendidoAdicionandoParaRelatorios.put("plataformaVendida", modeloProdutoVendido.getPlataformaVendida());
        produtoVendidoAdicionandoParaRelatorios.put("registroDaVenda", modeloProdutoVendido.getRegistroDaVenda());

        firestore.collection(nomeCollectionNomeCaixaDiario).document(idMontanteDiarioRelatorios).collection("RELATORIOS").add(produtoVendidoAdicionandoParaRelatorios).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(context, "VENDA ADICIONADA NO RELATÓRIO DIÁRIO", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, LojaActivityV2.class);
                context.startActivity(intent);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Algo deu errado verifique sua internet e tente novamente mais tarde", Toast.LENGTH_SHORT).show();
                System.out.println("Erro*&888888888" + e.getMessage());
            }
        });


        return false;
    }


    private boolean adicionarVendaNoRelatorioMensal(ModeloProdutoVendido modeloProdutoVendido, String idMontanteMensal, String idMontanteDiario){

        firestore.collection(nomeCollectionNomeMontanteMensal).document(idMontanteMensal).collection("RELATORIOS").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                List<DocumentSnapshot> listRelatorio = queryDocumentSnapshots.getDocuments();

                if(listRelatorio.size() > 0 ){
                    for (DocumentSnapshot list: listRelatorio){
                        String idRecuperado = list.getId();
                        firestore.collection(nomeCollectionNomeMontanteMensal).document(idMontanteMensal).collection("RELATORIOS").document(idRecuperado)
                                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                        ModeloControleRelatorioMensal modeloControleRelatorioMensal = documentSnapshot.toObject(ModeloControleRelatorioMensal.class);

                                        long quantidadeDeVendasNesseMesRecuperado = modeloControleRelatorioMensal.getQuantidadeDeVendasNesseMes();
                                        double valorDeVendasNesseMesRecuperado =modeloControleRelatorioMensal.getValorDeVendasNesseMes();
                                        double lucroAproximadoDeVendasNesseMesRecuperado =modeloControleRelatorioMensal.getLucroAproximadoDeVendasNesseMes();
                                        double despesasDeVendasNesseMesRecuperado =modeloControleRelatorioMensal.getDespesasDeVendasNesseMes();

                                        double valorConvertidoDaVenda = Double.parseDouble(modeloProdutoVendido.getValorQueOBoloFoiVendido().replace(",", "."));
                                        double custoTemp = modeloProdutoVendido.getCustoProduto();
                                        double calculaLucroDessaVenda=valorConvertidoDaVenda - custoTemp;



                                        long somaQuantidade = quantidadeDeVendasNesseMesRecuperado + 1;
                                        double somaValorDeVendas =valorDeVendasNesseMesRecuperado + valorConvertidoDaVenda;
                                        double somaLucroVenda=lucroAproximadoDeVendasNesseMesRecuperado + calculaLucroDessaVenda;
                                        double somaDespesasDessaVenda=despesasDeVendasNesseMesRecuperado + custoTemp;

                                        ModeloControleRelatorioMensal modeloControleRelatorioMensalAtualizadoParaOBanco = new ModeloControleRelatorioMensal();

                                        modeloControleRelatorioMensalAtualizadoParaOBanco.setQuantidadeDeVendasNesseMes(somaQuantidade);
                                        modeloControleRelatorioMensalAtualizadoParaOBanco.setValorDeVendasNesseMes(somaValorDeVendas);
                                        modeloControleRelatorioMensalAtualizadoParaOBanco.setLucroAproximadoDeVendasNesseMes(somaLucroVenda);
                                        modeloControleRelatorioMensalAtualizadoParaOBanco.setDespesasDeVendasNesseMes(somaDespesasDessaVenda);

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                    }
                                });


                    }
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });




        Map<String, Object> produtoVendidoAdicionandoParaRelatorios = new HashMap<>();

        produtoVendidoAdicionandoParaRelatorios.put("idDoProdutoVendido", modeloProdutoVendido.getIdDoProdutoVendido());
        produtoVendidoAdicionandoParaRelatorios.put("idDoProdutoCadastrado", modeloProdutoVendido.getIdDoProdutoCadastrado());
        produtoVendidoAdicionandoParaRelatorios.put("valorQueOBoloFoiVendido", modeloProdutoVendido.getValorQueOBoloFoiVendido());
        produtoVendidoAdicionandoParaRelatorios.put("nomeDoProdutoVendido", modeloProdutoVendido.getNomeDoProdutoVendido());
        produtoVendidoAdicionandoParaRelatorios.put("metodoDePagamento", modeloProdutoVendido.getMetodoDePagamento());
        produtoVendidoAdicionandoParaRelatorios.put("plataformaVendida", modeloProdutoVendido.getPlataformaVendida());
        produtoVendidoAdicionandoParaRelatorios.put("registroDaVenda", modeloProdutoVendido.getRegistroDaVenda());

        firestore.collection(nomeCollectionNomeMontanteMensal).document(idMontanteMensal).collection("RELATORIOS").add(produtoVendidoAdicionandoParaRelatorios).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(context, "RELATÓRIO MENSAL ATUALIZADO", Toast.LENGTH_SHORT).show();
                processaNoMontanteDiarioVendaFeita(modeloProdutoVendido, idMontanteDiario);


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Algo deu errado verifique sua internet e tente novamente mais tarde", Toast.LENGTH_SHORT).show();
                System.out.println("Erro*&888888888" + e.getMessage());
            }
        });


        return false;
    }
}
