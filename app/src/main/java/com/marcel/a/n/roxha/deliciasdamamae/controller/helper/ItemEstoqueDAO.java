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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.marcel.a.n.roxha.deliciasdamamae.activity.AddItemEstoqueActivity;
import com.marcel.a.n.roxha.deliciasdamamae.activity.EstoqueActivity;
import com.marcel.a.n.roxha.deliciasdamamae.config.ConfiguracaoFirebase;
import com.marcel.a.n.roxha.deliciasdamamae.model.ModeloItemEstoque;
import com.marcel.a.n.roxha.deliciasdamamae.model.ModeloItemEstoque;

import java.util.HashMap;
import java.util.Map;


public class ItemEstoqueDAO implements InterfaceItemEstoqueDAO {

    //Instanciar Banco de dados;

    //-------->Firestore
    FirebaseFirestore firebaseFirestore = ConfiguracaoFirebase.getFirestor();
    CollectionReference referenceItemEstoque = firebaseFirestore.collection("ITEM_ESTOQUE");
    //-------->Realtime
    DatabaseReference firebaseDatabase = ConfiguracaoFirebase.getReference().child("ITEM_ESTOQUE");

    //Atributo Contexto
    Context context;

    //Atributo Para estrutura de decisão
    boolean resultadoAdd;
    boolean resultadoUpdate;
    boolean resultadoDelet;

    //Identificador do item para atualização

    public String idRecuperado;
    private ModeloItemEstoque farinhaTrigo;

    public ItemEstoqueDAO(Context context) {
        this.context = context;
    }

    @Override
    public boolean salvarItemEstoque(ModeloItemEstoque ModeloItemEstoque) {

        System.out.println("Objeto recuperado:  \n " + ModeloItemEstoque);

        try {
//            Map<String, Object> item = new HashMap<>();
//
//            item.put("nameItem", ModeloItemEstoque.getNameItem());
//            item.put("versionEstoque", ModeloItemEstoque.getVersionEstoque());
//            item.put("valorItem", ModeloItemEstoque.getValorItem());
//            item.put("quantItem", ModeloItemEstoque.getQuantItem());
//            item.put("unidMedida", ModeloItemEstoque.getUnidMedida());
//            item.put("valorFracionado", ModeloItemEstoque.calcularValorFracionado());
//            item.put("valorItemPorReceita", ModeloItemEstoque.valorItemPorReceita());
//            item.put("quantPacote", ModeloItemEstoque.getQuantPacote());
//            item.put("quantUsadaReceita", ModeloItemEstoque.getQuantUsadaReceita());
//
//            referenceItemEstoque.add(item).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                @Override
//                public void onSuccess(DocumentReference documentReference) {
//
//                    Log.i("Firestore", documentReference.getId());
//                    Toast.makeText(context, "Sucesso ao salvar  item estoque", Toast.LENGTH_SHORT).show();
//                    resultadoAdd = true;
//
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                    Toast.makeText(context, "Erro ao salvar  item estoque", Toast.LENGTH_SHORT).show();
//                    resultadoAdd = false;
//                }
//            });


        } catch (Exception e) {
            Log.i("Error Exception: ", e.getMessage());
            return false;
        }

        return resultadoAdd;


    }

    @Override
    public boolean salvarItemEstoqueRealtimeDataBase(ModeloItemEstoque ModeloItemEstoque) {


        return false;
    }

    @Override
    public boolean atualizarItemEstoque(String id, ModeloItemEstoque ModeloItemEstoque) {

        this.idRecuperado = id;

        if (idRecuperado != null) {

            try {
//
//                Map<String, Object> itemAtualiza = new HashMap<>();
//
//                itemAtualiza.put("nameItem", ModeloItemEstoque.getNameItem());
//                itemAtualiza.put("versionEstoque", ModeloItemEstoque.getVersionEstoque());
//                itemAtualiza.put("valorItem", ModeloItemEstoque.getValorItem());
//                itemAtualiza.put("quantItem", ModeloItemEstoque.getQuantItem());
//                itemAtualiza.put("unidMedida", ModeloItemEstoque.getUnidMedida());
//                itemAtualiza.put("valorFracionado", ModeloItemEstoque.calcularValorFracionado());
//                itemAtualiza.put("valorItemPorReceita", ModeloItemEstoque.valorItemPorReceita());
//                itemAtualiza.put("quantPacote", ModeloItemEstoque.getQuantPacote());
//                itemAtualiza.put("quantUsadaReceita", ModeloItemEstoque.getQuantUsadaReceita());
//
//
//                referenceItemEstoque.document(idRecuperado).update(itemAtualiza).addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//
//                        Toast.makeText(context, "Suceso ao atualizar item", Toast.LENGTH_SHORT).show();
//                        resultadoUpdate = true;
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(context, "Erro ao atualizar item", Toast.LENGTH_SHORT).show();
//                        resultadoUpdate = false;
//                    }
//                });


            } catch (Exception e) {

                Log.i("Error: ", e.getMessage());
                resultadoUpdate = false;
            }


        }


        return resultadoUpdate;
    }

    @Override
    public boolean deletarItemEstoque(String id, ModeloItemEstoque ModeloItemEstoque) {


        return false;
    }

    //Methodo para inicializar uma lista completa de itens estoque com valores padrão
    public void listaDefault() {

        //Instanciando a farinha de trigo com valores padrão------------Inicio


        ModeloItemEstoque farinhaTrigo = new ModeloItemEstoque();
        ModeloItemEstoqueDAO modeloItemEstoqueDAOFarinhaTrigo = new ModeloItemEstoqueDAO(context);

//            ModeloItemEstoque farinhaTrigo = new ModeloItemEstoque();
        farinhaTrigo.setNomeItemEstoque("Farinha de trigo");
        farinhaTrigo.setQuantidadeTotalItemEstoque("1");
        farinhaTrigo.setQuantidadePorVolumeItemEstoque("1");
        farinhaTrigo.setQuantidadeUtilizadaNasReceitas("375");
        farinhaTrigo.setUnidadeMedidaPacoteItemEstoque("KILO(S)");
        farinhaTrigo.setUnidadeMedidaUtilizadoNasReceitas("GRAMA(S)");
        farinhaTrigo.setValorIndividualItemEstoque("9.98");

        farinhaTrigo.calcularValorFracionadoModeloItemEstoque();
        farinhaTrigo.calcularValorItemPorReceita();
        farinhaTrigo.calcularQuantidadeTotalItemEmEstoqueEmGramas();
        farinhaTrigo.calcularQuantidadeTotalItemEmEstoquePorVolumeSalvar();
        farinhaTrigo.calcularCustoTotalDoItemEmEstoque();

        modeloItemEstoqueDAOFarinhaTrigo.salvarItemEstoque(farinhaTrigo);


        ModeloItemEstoque fuba = new ModeloItemEstoque();
        ModeloItemEstoqueDAO modeloItemEstoqueDAOFuba = new ModeloItemEstoqueDAO(context);


        //Instanciando o Fuba com valores padrão-----inicio
//        ModeloItemEstoque fuba = new ModeloItemEstoque();
        fuba.setNomeItemEstoque("Fubá");
        fuba.setQuantidadeTotalItemEstoque("3");
        fuba.setQuantidadePorVolumeItemEstoque("1");
        fuba.setQuantidadeUtilizadaNasReceitas("200");
        fuba.setUnidadeMedidaPacoteItemEstoque("KILO(S)");
        fuba.setUnidadeMedidaUtilizadoNasReceitas("GRAMA(S)");
        fuba.setValorIndividualItemEstoque("2.50");

        fuba.calcularValorFracionadoModeloItemEstoque();
        fuba.calcularValorItemPorReceita();
        fuba.calcularQuantidadeTotalItemEmEstoqueEmGramas();
        fuba.calcularQuantidadeTotalItemEmEstoquePorVolumeSalvar();
        fuba.calcularCustoTotalDoItemEmEstoque();
        modeloItemEstoqueDAOFuba.salvarItemEstoque(fuba);


        //Instanciando o Ovo com valores padrão---inicio
        ModeloItemEstoque ovo = new ModeloItemEstoque();
        ModeloItemEstoqueDAO modeloItemEstoqueDAOOvo = new ModeloItemEstoqueDAO(context);
        ovo.setNomeItemEstoque("Ovo");
        ovo.setValorIndividualItemEstoque("12.00");
        ovo.setQuantidadeTotalItemEstoque("1");
        ovo.setUnidadeMedidaPacoteItemEstoque("UNIDADE(S)");
        ovo.setUnidadeMedidaUtilizadoNasReceitas("UNIDADE(S)");
        ovo.setQuantidadePorVolumeItemEstoque("12");
        ovo.setQuantidadeUtilizadaNasReceitas("5");

        ovo.calcularValorFracionadoModeloItemEstoque();
        ovo.calcularValorItemPorReceita();
        ovo.calcularQuantidadeTotalItemEmEstoqueEmGramas();
        ovo.calcularQuantidadeTotalItemEmEstoquePorVolumeSalvar();
        ovo.calcularCustoTotalDoItemEmEstoque();
        modeloItemEstoqueDAOOvo.salvarItemEstoque(ovo);
        //Instanciando o Ovo com valores padrão---fim



        //Instanciando Fermento com valores padrão---inicio
        ModeloItemEstoque fermento = new ModeloItemEstoque();
        ModeloItemEstoqueDAO modeloItemEstoqueDAOFermento = new ModeloItemEstoqueDAO(context);
        fermento.setNomeItemEstoque("Fermento");
        fermento.setValorIndividualItemEstoque("1.79");
        fermento.setQuantidadeTotalItemEstoque("1");
        fermento.setUnidadeMedidaPacoteItemEstoque("KILO(S)");
        fermento.setUnidadeMedidaUtilizadoNasReceitas("GRAMA(S)");
        fermento.setQuantidadePorVolumeItemEstoque("1");
        fermento.setQuantidadeUtilizadaNasReceitas("30");

        fermento.calcularValorFracionadoModeloItemEstoque();
        fermento.calcularValorItemPorReceita();
        fermento.calcularQuantidadeTotalItemEmEstoqueEmGramas();
        fermento.calcularQuantidadeTotalItemEmEstoquePorVolumeSalvar();
        fermento.calcularCustoTotalDoItemEmEstoque();
        modeloItemEstoqueDAOFermento.salvarItemEstoque(fermento);

        //Instanciando a Essencia com valores padrão---inicio
        ModeloItemEstoque essencia = new ModeloItemEstoque();
        ModeloItemEstoqueDAO modeloItemEstoqueDAOEssencia = new ModeloItemEstoqueDAO(context);
        essencia.setNomeItemEstoque("Essência");
        essencia.setValorIndividualItemEstoque("3.00");
        essencia.setQuantidadeTotalItemEstoque("1");
        essencia.setUnidadeMedidaPacoteItemEstoque("KILO(S)");
        essencia.setUnidadeMedidaUtilizadoNasReceitas("GRAMA(S)");
        essencia.setQuantidadePorVolumeItemEstoque("1");
        essencia.setQuantidadeUtilizadaNasReceitas("15");

        essencia.calcularValorFracionadoModeloItemEstoque();
        essencia.calcularValorItemPorReceita();
        essencia.calcularQuantidadeTotalItemEmEstoqueEmGramas();
        essencia.calcularQuantidadeTotalItemEmEstoquePorVolumeSalvar();
        essencia.calcularCustoTotalDoItemEmEstoque();
        modeloItemEstoqueDAOEssencia.salvarItemEstoque(essencia);

        //Instanciando Açucar com valores padrão---inicio
        ModeloItemEstoque acucar = new ModeloItemEstoque();
        ModeloItemEstoqueDAO modeloItemEstoqueDAOAcucar = new ModeloItemEstoqueDAO(context);
        acucar.setNomeItemEstoque("Açucar");
        acucar.setValorIndividualItemEstoque("1.50");
        acucar.setQuantidadeTotalItemEstoque("1");
        acucar.setUnidadeMedidaPacoteItemEstoque("KILO(S)");
        acucar.setUnidadeMedidaUtilizadoNasReceitas("GRAMA(S)");
        acucar.setQuantidadePorVolumeItemEstoque("1");
        acucar.setQuantidadeUtilizadaNasReceitas("400");

        acucar.calcularValorFracionadoModeloItemEstoque();
        acucar.calcularValorItemPorReceita();
        acucar.calcularQuantidadeTotalItemEmEstoqueEmGramas();
        acucar.calcularQuantidadeTotalItemEmEstoquePorVolumeSalvar();
        acucar.calcularCustoTotalDoItemEmEstoque();
        modeloItemEstoqueDAOAcucar.salvarItemEstoque(acucar);

        //Instanciando Leite com valores padrão---inicio
        ModeloItemEstoque leite = new ModeloItemEstoque();
        ModeloItemEstoqueDAO modeloItemEstoqueDAOLeite = new ModeloItemEstoqueDAO(context);
        leite.setNomeItemEstoque("Leite");
        leite.setValorIndividualItemEstoque("2.14");
        leite.setQuantidadeTotalItemEstoque("1");
        leite.setUnidadeMedidaPacoteItemEstoque("LITRO(S)");
        leite.setUnidadeMedidaUtilizadoNasReceitas("ML(S)");
        leite.setQuantidadePorVolumeItemEstoque("1");
        leite.setQuantidadeUtilizadaNasReceitas("250");

        leite.calcularValorFracionadoModeloItemEstoque();
        leite.calcularValorItemPorReceita();
        leite.calcularQuantidadeTotalItemEmEstoqueEmGramas();
        leite.calcularQuantidadeTotalItemEmEstoquePorVolumeSalvar();
        leite.calcularCustoTotalDoItemEmEstoque();
        modeloItemEstoqueDAOLeite.salvarItemEstoque(leite);


        //Instanciando Oleo com valores padrão---inicio
        ModeloItemEstoque oleo = new ModeloItemEstoque();
        ModeloItemEstoqueDAO modeloItemEstoqueDAOOleo = new ModeloItemEstoqueDAO(context);
        oleo.setNomeItemEstoque("Óleo");
        oleo.setValorIndividualItemEstoque("2.89");
        oleo.setQuantidadeTotalItemEstoque("1");
        oleo.setUnidadeMedidaPacoteItemEstoque("ML(S)");
        oleo.setUnidadeMedidaUtilizadoNasReceitas("ML(S)");
        oleo.setQuantidadePorVolumeItemEstoque("750");
        oleo.setQuantidadeUtilizadaNasReceitas("250");

        oleo.calcularValorFracionadoModeloItemEstoque();
        oleo.calcularValorItemPorReceita();
        oleo.calcularQuantidadeTotalItemEmEstoqueEmGramas();
        oleo.calcularQuantidadeTotalItemEmEstoquePorVolumeSalvar();
        oleo.calcularCustoTotalDoItemEmEstoque();
        modeloItemEstoqueDAOOleo.salvarItemEstoque(oleo);


        //Instanciando antimofo com valores padrão---inicio
        ModeloItemEstoque antimofo = new ModeloItemEstoque();
        ModeloItemEstoqueDAO modeloItemEstoqueDAOOAntimofo = new ModeloItemEstoqueDAO(context);
        antimofo.setNomeItemEstoque("Anti Mofo");
        antimofo.setValorIndividualItemEstoque("2.00");
        antimofo.setQuantidadeTotalItemEstoque("1");
        antimofo.setUnidadeMedidaPacoteItemEstoque("KILO(S)");
        antimofo.setUnidadeMedidaUtilizadoNasReceitas("GRAMA(S)");
        antimofo.setQuantidadePorVolumeItemEstoque("1");
        antimofo.setQuantidadeUtilizadaNasReceitas("15");

        antimofo.calcularValorFracionadoModeloItemEstoque();
        antimofo.calcularValorItemPorReceita();
        antimofo.calcularQuantidadeTotalItemEmEstoqueEmGramas();
        antimofo.calcularQuantidadeTotalItemEmEstoquePorVolumeSalvar();
        antimofo.calcularCustoTotalDoItemEmEstoque();
        modeloItemEstoqueDAOOAntimofo.salvarItemEstoque(antimofo);

        //Instanciando queijoRalado com valores padrão---inicio
        ModeloItemEstoque queijoRalado = new ModeloItemEstoque();
        ModeloItemEstoqueDAO modeloItemEstoqueDAOQueijoRalado = new ModeloItemEstoqueDAO(context);
        queijoRalado.setNomeItemEstoque("Queijo Ralado");
        queijoRalado.setValorIndividualItemEstoque("2.00");
        queijoRalado.setQuantidadeTotalItemEstoque("1");
        queijoRalado.setUnidadeMedidaPacoteItemEstoque("KILO(S)");
        queijoRalado.setUnidadeMedidaUtilizadoNasReceitas("GRAMA(S)");
        queijoRalado.setQuantidadePorVolumeItemEstoque("1");
        queijoRalado.setQuantidadeUtilizadaNasReceitas("50");

        queijoRalado.calcularValorFracionadoModeloItemEstoque();
        queijoRalado.calcularValorItemPorReceita();
        queijoRalado.calcularQuantidadeTotalItemEmEstoqueEmGramas();
        queijoRalado.calcularQuantidadeTotalItemEmEstoquePorVolumeSalvar();
        queijoRalado.calcularCustoTotalDoItemEmEstoque();
        modeloItemEstoqueDAOQueijoRalado.salvarItemEstoque(queijoRalado);


        //Instanciando sazonMassa com valores padrão---inicio
        ModeloItemEstoque sazonMassa = new ModeloItemEstoque();
        ModeloItemEstoqueDAO modeloItemEstoqueDAOSazonMassa = new ModeloItemEstoqueDAO(context);
        sazonMassa.setNomeItemEstoque("Sazon Massa");
        sazonMassa.setValorIndividualItemEstoque("2.00");
        sazonMassa.setQuantidadeTotalItemEstoque("1");
        sazonMassa.setUnidadeMedidaPacoteItemEstoque("KILO(S)");
        sazonMassa.setUnidadeMedidaUtilizadoNasReceitas("GRAMA(S)");
        sazonMassa.setQuantidadePorVolumeItemEstoque("1");
        sazonMassa.setQuantidadeUtilizadaNasReceitas("5");

        sazonMassa.calcularValorFracionadoModeloItemEstoque();
        sazonMassa.calcularValorItemPorReceita();
        sazonMassa.calcularQuantidadeTotalItemEmEstoqueEmGramas();
        sazonMassa.calcularQuantidadeTotalItemEmEstoquePorVolumeSalvar();
        sazonMassa.calcularCustoTotalDoItemEmEstoque();
        modeloItemEstoqueDAOSazonMassa.salvarItemEstoque(sazonMassa);


        //Instanciando acucarMascavo com valores padrão---inicio
        ModeloItemEstoque acucarMascavo = new ModeloItemEstoque();
        ModeloItemEstoqueDAO modeloItemEstoqueDAOAcucarMascavo = new ModeloItemEstoqueDAO(context);
        acucarMascavo.setNomeItemEstoque("Açucar Mascavo");
        acucarMascavo.setValorIndividualItemEstoque("9.00");
        acucarMascavo.setQuantidadeTotalItemEstoque("1");
        acucarMascavo.setUnidadeMedidaPacoteItemEstoque("KILO(S)");
        acucarMascavo.setUnidadeMedidaUtilizadoNasReceitas("GRAMA(S)");
        acucarMascavo.setQuantidadePorVolumeItemEstoque("1");
        acucarMascavo.setQuantidadeUtilizadaNasReceitas("75");

        acucarMascavo.calcularValorFracionadoModeloItemEstoque();
        acucarMascavo.calcularValorItemPorReceita();
        acucarMascavo.calcularQuantidadeTotalItemEmEstoqueEmGramas();
        acucarMascavo.calcularQuantidadeTotalItemEmEstoquePorVolumeSalvar();
        acucarMascavo.calcularCustoTotalDoItemEmEstoque();
        modeloItemEstoqueDAOOAntimofo.salvarItemEstoque(acucarMascavo);

        //Instanciando manteiga com valores padrão---inicio
        ModeloItemEstoque manteiga = new ModeloItemEstoque();
        ModeloItemEstoqueDAO modeloItemEstoqueDAOManteiga = new ModeloItemEstoqueDAO(context);
        manteiga.setNomeItemEstoque("Manteiga");
        manteiga.setValorIndividualItemEstoque("2.30");
        manteiga.setQuantidadeTotalItemEstoque("1");
        manteiga.setUnidadeMedidaPacoteItemEstoque("KILO(S)");
        manteiga.setUnidadeMedidaUtilizadoNasReceitas("GRAMA(S)");
        manteiga.setQuantidadePorVolumeItemEstoque("1");
        manteiga.setQuantidadeUtilizadaNasReceitas("30");

        manteiga.calcularValorFracionadoModeloItemEstoque();
        manteiga.calcularValorItemPorReceita();
        manteiga.calcularQuantidadeTotalItemEmEstoqueEmGramas();
        manteiga.calcularQuantidadeTotalItemEmEstoquePorVolumeSalvar();
        manteiga.calcularCustoTotalDoItemEmEstoque();
        modeloItemEstoqueDAOManteiga.salvarItemEstoque(manteiga);

        //Instanciando bicarbonatoSodio com valores padrão---inicio
        ModeloItemEstoque bicarbonatoSodio = new ModeloItemEstoque();
        ModeloItemEstoqueDAO modeloItemEstoqueDAOBicarbonatoSodio = new ModeloItemEstoqueDAO(context);
        bicarbonatoSodio.setNomeItemEstoque("Bicarbonado de Sódio");
        bicarbonatoSodio.setValorIndividualItemEstoque("1.00");
        bicarbonatoSodio.setQuantidadeTotalItemEstoque("1");
        bicarbonatoSodio.setUnidadeMedidaPacoteItemEstoque("KILO(S)");
        bicarbonatoSodio.setUnidadeMedidaUtilizadoNasReceitas("GRAMA(S)");
        bicarbonatoSodio.setQuantidadePorVolumeItemEstoque("1");
        bicarbonatoSodio.setQuantidadeUtilizadaNasReceitas("10");

        bicarbonatoSodio.calcularValorFracionadoModeloItemEstoque();
        bicarbonatoSodio.calcularValorItemPorReceita();
        bicarbonatoSodio.calcularQuantidadeTotalItemEmEstoqueEmGramas();
        bicarbonatoSodio.calcularQuantidadeTotalItemEmEstoquePorVolumeSalvar();
        bicarbonatoSodio.calcularCustoTotalDoItemEmEstoque();
        modeloItemEstoqueDAOBicarbonatoSodio.salvarItemEstoque(bicarbonatoSodio);


        //Instanciando canelaPo com valores padrão---inicio
        ModeloItemEstoque canelaPo = new ModeloItemEstoque();
        ModeloItemEstoqueDAO modeloItemEstoqueDAOCanelaEmPo= new ModeloItemEstoqueDAO(context);
        canelaPo.setNomeItemEstoque("Canela em Pó");
        canelaPo.setValorIndividualItemEstoque("4.00");
        canelaPo.setQuantidadeTotalItemEstoque("1");
        canelaPo.setUnidadeMedidaPacoteItemEstoque("KILO(S)");
        canelaPo.setUnidadeMedidaUtilizadoNasReceitas("GRAMA(S)");
        canelaPo.setQuantidadePorVolumeItemEstoque("1");
        canelaPo.setQuantidadeUtilizadaNasReceitas("10");


        canelaPo.calcularValorFracionadoModeloItemEstoque();
        canelaPo.calcularValorItemPorReceita();
        canelaPo.calcularQuantidadeTotalItemEmEstoqueEmGramas();
        canelaPo.calcularQuantidadeTotalItemEmEstoquePorVolumeSalvar();
        canelaPo.calcularCustoTotalDoItemEmEstoque();
        modeloItemEstoqueDAOCanelaEmPo.salvarItemEstoque(canelaPo);


        //Instanciando cravoPo com valores padrão---inicio
        ModeloItemEstoque cravoPo = new ModeloItemEstoque();
        ModeloItemEstoqueDAO modeloItemEstoqueDAOCravoEmPo= new ModeloItemEstoqueDAO(context);
        cravoPo.setNomeItemEstoque("Cravo em Pó");
        cravoPo.setValorIndividualItemEstoque("4.00");
        cravoPo.setQuantidadeTotalItemEstoque("1");
        cravoPo.setUnidadeMedidaPacoteItemEstoque("KILO(S)");
        cravoPo.setUnidadeMedidaUtilizadoNasReceitas("GRAMA(S)");
        cravoPo.setQuantidadePorVolumeItemEstoque("1");
        cravoPo.setQuantidadeUtilizadaNasReceitas("2.5");

        cravoPo.calcularValorFracionadoModeloItemEstoque();
        cravoPo.calcularValorItemPorReceita();
        cravoPo.calcularQuantidadeTotalItemEmEstoqueEmGramas();
        cravoPo.calcularQuantidadeTotalItemEmEstoquePorVolumeSalvar();
        canelaPo.calcularCustoTotalDoItemEmEstoque();
        modeloItemEstoqueDAOCravoEmPo.salvarItemEstoque(canelaPo);

        //Instanciando nozMoscada com valores padrão---inicio
        ModeloItemEstoque nozMoscada = new ModeloItemEstoque();
        ModeloItemEstoqueDAO modeloItemEstoqueDAONozMoscada= new ModeloItemEstoqueDAO(context);
        nozMoscada.setNomeItemEstoque("Noz-Moscada");
        nozMoscada.setValorIndividualItemEstoque("3.00");
        nozMoscada.setQuantidadeTotalItemEstoque("1");
        nozMoscada.setUnidadeMedidaPacoteItemEstoque("KILO(S)");
        nozMoscada.setUnidadeMedidaUtilizadoNasReceitas("GRAMA(S)");
        nozMoscada.setQuantidadePorVolumeItemEstoque("1");
        nozMoscada.setQuantidadeUtilizadaNasReceitas("2.5");

        nozMoscada.calcularValorFracionadoModeloItemEstoque();
        nozMoscada.calcularValorItemPorReceita();
        nozMoscada.calcularQuantidadeTotalItemEmEstoqueEmGramas();
        nozMoscada.calcularQuantidadeTotalItemEmEstoquePorVolumeSalvar();
        nozMoscada.calcularCustoTotalDoItemEmEstoque();
        modeloItemEstoqueDAONozMoscada.salvarItemEstoque(nozMoscada);

        //Instanciando melFavinho com valores padrão---inicio
        ModeloItemEstoque melFavinho = new ModeloItemEstoque();
        ModeloItemEstoqueDAO modeloItemEstoqueDAOMelFavinho= new ModeloItemEstoqueDAO(context);
        melFavinho.setNomeItemEstoque("Mel Favinho");
        melFavinho.setValorIndividualItemEstoque("6.39");
        melFavinho.setQuantidadeTotalItemEstoque("1");
        melFavinho.setUnidadeMedidaPacoteItemEstoque("KILO(S)");
        melFavinho.setUnidadeMedidaUtilizadoNasReceitas("GRAMA(S)");
        melFavinho.setQuantidadePorVolumeItemEstoque("1");
        melFavinho.setQuantidadeUtilizadaNasReceitas("150");

        melFavinho.calcularValorFracionadoModeloItemEstoque();
        melFavinho.calcularValorItemPorReceita();
        melFavinho.calcularQuantidadeTotalItemEmEstoqueEmGramas();
        melFavinho.calcularQuantidadeTotalItemEmEstoquePorVolumeSalvar();
        melFavinho.calcularCustoTotalDoItemEmEstoque();
        modeloItemEstoqueDAOMelFavinho.salvarItemEstoque(melFavinho);


        //Instanciando leiteCondensado com valores padrão---inicio
        ModeloItemEstoque leiteCondensado = new ModeloItemEstoque();
        ModeloItemEstoqueDAO modeloItemEstoqueDAOLeiteCondensado= new ModeloItemEstoqueDAO(context);
        leiteCondensado.setNomeItemEstoque("Leite condensado");
        leiteCondensado.setValorIndividualItemEstoque("2.99");
        leiteCondensado.setQuantidadeTotalItemEstoque("1");
        leiteCondensado.setUnidadeMedidaPacoteItemEstoque("KILO(S)");
        leiteCondensado.setUnidadeMedidaUtilizadoNasReceitas("GRAMA(S)");
        leiteCondensado.setQuantidadePorVolumeItemEstoque("1");
        leiteCondensado.setQuantidadeUtilizadaNasReceitas("395");

        leiteCondensado.calcularValorFracionadoModeloItemEstoque();
        leiteCondensado.calcularValorItemPorReceita();
        leiteCondensado.calcularQuantidadeTotalItemEmEstoqueEmGramas();
        leiteCondensado.calcularQuantidadeTotalItemEmEstoquePorVolumeSalvar();
        leiteCondensado.calcularCustoTotalDoItemEmEstoque();
        modeloItemEstoqueDAOLeiteCondensado.salvarItemEstoque(leiteCondensado);


        //Instanciando cremedeLeite com valores padrão---inicio
        ModeloItemEstoque cremedeLeite = new ModeloItemEstoque();
        ModeloItemEstoqueDAO modeloItemEstoqueDAOCremeDeLeite= new ModeloItemEstoqueDAO(context);
        cremedeLeite.setNomeItemEstoque("Creme de Leite");
        cremedeLeite.setValorIndividualItemEstoque("1.69");
        cremedeLeite.setQuantidadeTotalItemEstoque("1");
        cremedeLeite.setUnidadeMedidaPacoteItemEstoque("KILO(S)");
        cremedeLeite.setUnidadeMedidaUtilizadoNasReceitas("GRAMA(S)");
        cremedeLeite.setQuantidadePorVolumeItemEstoque("1");
        cremedeLeite.setQuantidadeUtilizadaNasReceitas("200");

        cremedeLeite.calcularValorFracionadoModeloItemEstoque();
        cremedeLeite.calcularValorItemPorReceita();
        cremedeLeite.calcularQuantidadeTotalItemEmEstoqueEmGramas();
        cremedeLeite.calcularQuantidadeTotalItemEmEstoquePorVolumeSalvar();
        cremedeLeite.calcularCustoTotalDoItemEmEstoque();
        modeloItemEstoqueDAOCremeDeLeite.salvarItemEstoque(cremedeLeite);



        //Instanciando leitePo com valores padrão---inicio
        ModeloItemEstoque leitePo = new ModeloItemEstoque();
        ModeloItemEstoqueDAO modeloItemEstoqueDAOLeiteEmPo= new ModeloItemEstoqueDAO(context);

        leitePo.setNomeItemEstoque("Leite em Pó");
        leitePo.setValorIndividualItemEstoque("8.15");
        leitePo.setQuantidadeTotalItemEstoque("1");
        leitePo.setUnidadeMedidaPacoteItemEstoque("KILO(S)");
        leitePo.setUnidadeMedidaUtilizadoNasReceitas("GRAMA(S)");
        leitePo.setQuantidadePorVolumeItemEstoque("1");
        leitePo.setQuantidadeUtilizadaNasReceitas("200");

        leitePo.calcularValorFracionadoModeloItemEstoque();
        leitePo.calcularValorItemPorReceita();
        leitePo.calcularQuantidadeTotalItemEmEstoqueEmGramas();
        leitePo.calcularQuantidadeTotalItemEmEstoquePorVolumeSalvar();
        leitePo.calcularCustoTotalDoItemEmEstoque();
        modeloItemEstoqueDAOLeiteEmPo.salvarItemEstoque(leitePo);


        //Instanciando leiteCoco com valores padrão---inicio
        ModeloItemEstoque leiteCoco = new ModeloItemEstoque();
        ModeloItemEstoqueDAO modeloItemEstoqueDAOLeiteDeCoco= new ModeloItemEstoqueDAO(context);
        leiteCoco.setNomeItemEstoque("Leite de coco ");
        leiteCoco.setValorIndividualItemEstoque("2.00");
        leiteCoco.setQuantidadeTotalItemEstoque("1");
        leiteCoco.setUnidadeMedidaPacoteItemEstoque("KILO(S)");
        leiteCoco.setUnidadeMedidaUtilizadoNasReceitas("GRAMA(S)");
        leiteCoco.setQuantidadePorVolumeItemEstoque("1");
        leiteCoco.setQuantidadeUtilizadaNasReceitas("200");

        leiteCoco.calcularValorFracionadoModeloItemEstoque();
        leiteCoco.calcularValorItemPorReceita();
        leiteCoco.calcularQuantidadeTotalItemEmEstoqueEmGramas();
        leiteCoco.calcularQuantidadeTotalItemEmEstoquePorVolumeSalvar();
        leiteCoco.calcularCustoTotalDoItemEmEstoque();
        modeloItemEstoqueDAOLeiteDeCoco.salvarItemEstoque(leiteCoco);


        //Instanciando cocoRalado com valores padrão---inicio
        ModeloItemEstoque cocoRalado = new ModeloItemEstoque();
        ModeloItemEstoqueDAO modeloItemEstoqueDAOCocoRalado= new ModeloItemEstoqueDAO(context);
        cocoRalado.setNomeItemEstoque("Coco ralado");
        cocoRalado.setValorIndividualItemEstoque("1.95");
        cocoRalado.setQuantidadeTotalItemEstoque("1");
        cocoRalado.setUnidadeMedidaPacoteItemEstoque("KILO(S)");
        cocoRalado.setUnidadeMedidaUtilizadoNasReceitas("GRAMA(S)");
        cocoRalado.setQuantidadePorVolumeItemEstoque("1");
        cocoRalado.setQuantidadeUtilizadaNasReceitas("100");

        cocoRalado.calcularValorFracionadoModeloItemEstoque();
        cocoRalado.calcularValorItemPorReceita();
        cocoRalado.calcularQuantidadeTotalItemEmEstoqueEmGramas();
        cocoRalado.calcularQuantidadeTotalItemEmEstoquePorVolumeSalvar();
        cocoRalado.calcularCustoTotalDoItemEmEstoque();
        modeloItemEstoqueDAOCocoRalado.salvarItemEstoque(cocoRalado);

//Instanciando maizena com valores padrão---inicio
        ModeloItemEstoque maizena = new ModeloItemEstoque();
        ModeloItemEstoqueDAO modeloItemEstoqueDAOMaizena= new ModeloItemEstoqueDAO(context);
        maizena.setNomeItemEstoque("Maizena");
        maizena.setValorIndividualItemEstoque("4.15");
        maizena.setQuantidadeTotalItemEstoque("1");
        maizena.setUnidadeMedidaPacoteItemEstoque("KILO(S)");
        maizena.setUnidadeMedidaUtilizadoNasReceitas("GRAMA(S)");
        maizena.setQuantidadePorVolumeItemEstoque("1");
        maizena.setQuantidadeUtilizadaNasReceitas("30");

        maizena.calcularValorFracionadoModeloItemEstoque();
        maizena.calcularValorItemPorReceita();
        maizena.calcularQuantidadeTotalItemEmEstoqueEmGramas();
        maizena.calcularQuantidadeTotalItemEmEstoquePorVolumeSalvar();
        maizena.calcularCustoTotalDoItemEmEstoque();
        modeloItemEstoqueDAOMaizena.salvarItemEstoque(maizena);



        //Instanciando chocolate50porcento com valores padrão---inicio
        ModeloItemEstoque chocolate50porcento = new ModeloItemEstoque();
        ModeloItemEstoqueDAO modeloItemEstoqueDAOChocolate50PorCento= new ModeloItemEstoqueDAO(context);
        chocolate50porcento.setNomeItemEstoque("Chocolate 50%");
        chocolate50porcento.setValorIndividualItemEstoque("19.60");
        chocolate50porcento.setQuantidadeTotalItemEstoque("1");
        chocolate50porcento.setUnidadeMedidaPacoteItemEstoque("KILO(S)");
        chocolate50porcento.setUnidadeMedidaUtilizadoNasReceitas("GRAMA(S)");
        chocolate50porcento.setQuantidadePorVolumeItemEstoque("1");
        chocolate50porcento.setQuantidadeUtilizadaNasReceitas("45");

        chocolate50porcento.calcularValorFracionadoModeloItemEstoque();
        chocolate50porcento.calcularValorItemPorReceita();
        chocolate50porcento.calcularQuantidadeTotalItemEmEstoqueEmGramas();
        chocolate50porcento.calcularQuantidadeTotalItemEmEstoquePorVolumeSalvar();
        chocolate50porcento.calcularCustoTotalDoItemEmEstoque();
        modeloItemEstoqueDAOChocolate50PorCento.salvarItemEstoque(chocolate50porcento);



        //Instanciando leiteRecheios com valores padrão---inicio
        ModeloItemEstoque leiteRecheios = new ModeloItemEstoque();
        ModeloItemEstoqueDAO modeloItemEstoqueDAOLeiteRecheios= new ModeloItemEstoqueDAO(context);
        leiteRecheios.setNomeItemEstoque("Leite para recheios");
        leiteRecheios.setValorIndividualItemEstoque("2.14");
        leiteRecheios.setQuantidadeTotalItemEstoque("1");
        leiteRecheios.setUnidadeMedidaPacoteItemEstoque("KILO(S)");
        leiteRecheios.setUnidadeMedidaUtilizadoNasReceitas("GRAMA(S)");
        leiteRecheios.setQuantidadePorVolumeItemEstoque("1");
        leiteRecheios.setQuantidadeUtilizadaNasReceitas("480");

        leiteRecheios.calcularValorFracionadoModeloItemEstoque();
        leiteRecheios.calcularValorItemPorReceita();
        leiteRecheios.calcularQuantidadeTotalItemEmEstoqueEmGramas();
        leiteRecheios.calcularQuantidadeTotalItemEmEstoquePorVolumeSalvar();
        leiteRecheios.calcularCustoTotalDoItemEmEstoque();
        modeloItemEstoqueDAOLeiteRecheios.salvarItemEstoque(leiteRecheios);


        //Instanciando nozMoscada com valores padrão---inicio
        ModeloItemEstoque preparoSobremesaMorango = new ModeloItemEstoque();
        ModeloItemEstoqueDAO modeloItemEstoqueDAOPreparoSobremesaMorango= new ModeloItemEstoqueDAO(context);
        preparoSobremesaMorango.setNomeItemEstoque("Preparo Sobremesa Morango");
        preparoSobremesaMorango.setValorIndividualItemEstoque("5.00");
        preparoSobremesaMorango.setQuantidadeTotalItemEstoque("1");
        preparoSobremesaMorango.setUnidadeMedidaPacoteItemEstoque("KILO(S)");
        preparoSobremesaMorango.setUnidadeMedidaUtilizadoNasReceitas("GRAMA(S)");
        preparoSobremesaMorango.setQuantidadePorVolumeItemEstoque("1");
        preparoSobremesaMorango.setQuantidadeUtilizadaNasReceitas("45");

        preparoSobremesaMorango.calcularValorFracionadoModeloItemEstoque();
        preparoSobremesaMorango.calcularValorItemPorReceita();
        preparoSobremesaMorango.calcularQuantidadeTotalItemEmEstoqueEmGramas();
        preparoSobremesaMorango.calcularQuantidadeTotalItemEmEstoquePorVolumeSalvar();
        preparoSobremesaMorango.calcularCustoTotalDoItemEmEstoque();
        modeloItemEstoqueDAOPreparoSobremesaMorango.salvarItemEstoque(preparoSobremesaMorango);


        //Instanciando preparoSobremesaChocolateBranco com valores padrão---inicio

        ModeloItemEstoque preparoSobremesaChocolateBranco = new ModeloItemEstoque();
        ModeloItemEstoqueDAO modeloItemEstoqueDAOPreparoSobremesaChocolateBranco= new ModeloItemEstoqueDAO(context);
        preparoSobremesaChocolateBranco.setNomeItemEstoque("Preparo Sobremesa Chocolate Branco");
        preparoSobremesaChocolateBranco.setValorIndividualItemEstoque("3.29");
        preparoSobremesaChocolateBranco.setQuantidadeTotalItemEstoque("1");
        preparoSobremesaChocolateBranco.setUnidadeMedidaPacoteItemEstoque("KILO(S)");
        preparoSobremesaChocolateBranco.setUnidadeMedidaUtilizadoNasReceitas("GRAMA(S)");
        preparoSobremesaChocolateBranco.setQuantidadePorVolumeItemEstoque("1");
        preparoSobremesaChocolateBranco.setQuantidadeUtilizadaNasReceitas("45");

        preparoSobremesaChocolateBranco.calcularValorFracionadoModeloItemEstoque();
        preparoSobremesaChocolateBranco.calcularValorItemPorReceita();
        preparoSobremesaChocolateBranco.calcularQuantidadeTotalItemEmEstoqueEmGramas();
        preparoSobremesaChocolateBranco.calcularQuantidadeTotalItemEmEstoquePorVolumeSalvar();
        preparoSobremesaChocolateBranco.calcularCustoTotalDoItemEmEstoque();
        modeloItemEstoqueDAOPreparoSobremesaChocolateBranco.salvarItemEstoque(preparoSobremesaChocolateBranco);



        //Instanciando milho com valores padrão---inicio
        ModeloItemEstoque milho = new ModeloItemEstoque();
        ModeloItemEstoqueDAO modeloItemEstoqueDAOMilho= new ModeloItemEstoqueDAO(context);
        milho.setNomeItemEstoque("Milho");
        milho.setValorIndividualItemEstoque("1.79");
        milho.setQuantidadeTotalItemEstoque("1");
        milho.setUnidadeMedidaPacoteItemEstoque("KILO(S)");
        milho.setUnidadeMedidaUtilizadoNasReceitas("GRAMA(S)");
        milho.setQuantidadePorVolumeItemEstoque("1");
        milho.setQuantidadeUtilizadaNasReceitas("170");

        milho.calcularValorFracionadoModeloItemEstoque();
        milho.calcularValorItemPorReceita();
        milho.calcularQuantidadeTotalItemEmEstoqueEmGramas();
        milho.calcularQuantidadeTotalItemEmEstoquePorVolumeSalvar();
        milho.calcularCustoTotalDoItemEmEstoque();
        modeloItemEstoqueDAOMilho.salvarItemEstoque(milho);


        //Instanciando goiabada com valores padrão---inicio
        ModeloItemEstoque goiabada = new ModeloItemEstoque();
        ModeloItemEstoqueDAO modeloItemEstoqueDAOGoiaba= new ModeloItemEstoqueDAO(context);
        goiabada.setNomeItemEstoque("Goiabada");
        goiabada.setValorIndividualItemEstoque("1.15");
        goiabada.setQuantidadeTotalItemEstoque("1");
        goiabada.setUnidadeMedidaPacoteItemEstoque("KILO(S)");
        goiabada.setUnidadeMedidaUtilizadoNasReceitas("GRAMA(S)");
        goiabada.setQuantidadePorVolumeItemEstoque("1");
        goiabada.setQuantidadeUtilizadaNasReceitas("30");

        goiabada.calcularValorFracionadoModeloItemEstoque();
        goiabada.calcularValorItemPorReceita();
        goiabada.calcularQuantidadeTotalItemEmEstoqueEmGramas();
        goiabada.calcularQuantidadeTotalItemEmEstoquePorVolumeSalvar();
        goiabada.calcularCustoTotalDoItemEmEstoque();
        modeloItemEstoqueDAOGoiaba.salvarItemEstoque(goiabada);



        //Instanciando ervaDoce com valores padrão---inicio
        ModeloItemEstoque ervaDoce = new ModeloItemEstoque();
        ModeloItemEstoqueDAO modeloItemEstoqueDAOErvaDoce= new ModeloItemEstoqueDAO(context);
        ervaDoce.setNomeItemEstoque("Erva doce");
        ervaDoce.setValorIndividualItemEstoque("1.00");
        ervaDoce.setQuantidadeTotalItemEstoque("1");
        ervaDoce.setUnidadeMedidaPacoteItemEstoque("KILO(S)");
        ervaDoce.setUnidadeMedidaUtilizadoNasReceitas("GRAMA(S)");
        ervaDoce.setQuantidadePorVolumeItemEstoque("1");
        ervaDoce.setQuantidadeUtilizadaNasReceitas("10");

        ervaDoce.calcularValorFracionadoModeloItemEstoque();
        ervaDoce.calcularValorItemPorReceita();
        ervaDoce.calcularQuantidadeTotalItemEmEstoqueEmGramas();
        ervaDoce.calcularQuantidadeTotalItemEmEstoquePorVolumeSalvar();
        ervaDoce.calcularCustoTotalDoItemEmEstoque();
        modeloItemEstoqueDAOErvaDoce.salvarItemEstoque(ervaDoce);

        //Instanciando nutella com valores padrão---inicio
        ModeloItemEstoque nutella = new ModeloItemEstoque();
        ModeloItemEstoqueDAO modeloItemEstoqueDAONutella= new ModeloItemEstoqueDAO(context);

        nutella.setNomeItemEstoque("Nutella");
        nutella.setValorIndividualItemEstoque("26.50");
        nutella.setQuantidadeTotalItemEstoque("1");
        nutella.setUnidadeMedidaPacoteItemEstoque("KILO(S)");
        nutella.setUnidadeMedidaUtilizadoNasReceitas("GRAMA(S)");
        nutella.setQuantidadePorVolumeItemEstoque("1");
        nutella.setQuantidadeUtilizadaNasReceitas("200");

        nutella.calcularValorFracionadoModeloItemEstoque();
        nutella.calcularValorItemPorReceita();
        nutella.calcularQuantidadeTotalItemEmEstoqueEmGramas();
        nutella.calcularQuantidadeTotalItemEmEstoquePorVolumeSalvar();
        nutella.calcularCustoTotalDoItemEmEstoque();
        modeloItemEstoqueDAONutella.salvarItemEstoque(nutella);

//Instanciando frangoDesfiado com valores padrão---inicio
        ModeloItemEstoque frangoDesfiado = new ModeloItemEstoque();
        ModeloItemEstoqueDAO modeloItemEstoqueDAOFrangoDesfiado= new ModeloItemEstoqueDAO(context);

        frangoDesfiado.setNomeItemEstoque("Frango desfiado");
        frangoDesfiado.setValorIndividualItemEstoque("9.00");
        frangoDesfiado.setQuantidadeTotalItemEstoque("1");
        frangoDesfiado.setUnidadeMedidaPacoteItemEstoque("KILO(S)");
        frangoDesfiado.setUnidadeMedidaUtilizadoNasReceitas("GRAMA(S)");
        frangoDesfiado.setQuantidadePorVolumeItemEstoque("1");
        frangoDesfiado.setQuantidadeUtilizadaNasReceitas("1000");

        frangoDesfiado.calcularValorFracionadoModeloItemEstoque();
        frangoDesfiado.calcularValorItemPorReceita();
        frangoDesfiado.calcularQuantidadeTotalItemEmEstoqueEmGramas();
        frangoDesfiado.calcularQuantidadeTotalItemEmEstoquePorVolumeSalvar();
        frangoDesfiado.calcularCustoTotalDoItemEmEstoque();
        modeloItemEstoqueDAOFrangoDesfiado.salvarItemEstoque(frangoDesfiado);


        //Instanciando cebola com valores padrão---inicio
        ModeloItemEstoque cebola = new ModeloItemEstoque();
        ModeloItemEstoqueDAO modeloItemEstoqueDAOCebola= new ModeloItemEstoqueDAO(context);
        cebola.setNomeItemEstoque("Cebola");
        cebola.setValorIndividualItemEstoque("0.50");
        cebola.setQuantidadeTotalItemEstoque("1");
        cebola.setUnidadeMedidaPacoteItemEstoque("UNIDADE(S)");
        cebola.setUnidadeMedidaUtilizadoNasReceitas("UNIDADE(S)");
        cebola.setQuantidadePorVolumeItemEstoque("1");
        cebola.setQuantidadeUtilizadaNasReceitas("1");

        cebola.calcularValorFracionadoModeloItemEstoque();
        cebola.calcularValorItemPorReceita();
        cebola.calcularQuantidadeTotalItemEmEstoqueEmGramas();
        cebola.calcularQuantidadeTotalItemEmEstoquePorVolumeSalvar();
        cebola.calcularCustoTotalDoItemEmEstoque();
        modeloItemEstoqueDAOCebola.salvarItemEstoque(cebola);


        //Instanciando seleta com valores padrão---inicio
        ModeloItemEstoque seleta = new ModeloItemEstoque();
        ModeloItemEstoqueDAO modeloItemEstoqueDAOSeleta= new ModeloItemEstoqueDAO(context);
        seleta.setNomeItemEstoque("Seleta");
        seleta.setValorIndividualItemEstoque("2.50");
        seleta.setQuantidadeTotalItemEstoque("1");
        seleta.setUnidadeMedidaPacoteItemEstoque("UNIDADE(S)");
        seleta.setUnidadeMedidaUtilizadoNasReceitas("UNIDADE(S)");
        seleta.setQuantidadePorVolumeItemEstoque("1");
        seleta.setQuantidadeUtilizadaNasReceitas("1");

        seleta.calcularValorFracionadoModeloItemEstoque();
        seleta.calcularValorItemPorReceita();
        seleta.calcularQuantidadeTotalItemEmEstoqueEmGramas();
        seleta.calcularQuantidadeTotalItemEmEstoquePorVolumeSalvar();
        seleta.calcularCustoTotalDoItemEmEstoque();
        modeloItemEstoqueDAOSeleta.salvarItemEstoque(seleta);


        //Instanciando massaTomate com valores padrão---inicio
        ModeloItemEstoque massaTomate = new ModeloItemEstoque();
        ModeloItemEstoqueDAO modeloItemEstoqueDAOMassaTomate= new ModeloItemEstoqueDAO(context);
        massaTomate.setNomeItemEstoque("Massa de Tomate");
        massaTomate.setValorIndividualItemEstoque("1.05");
        massaTomate.setQuantidadeTotalItemEstoque("1");
        massaTomate.setUnidadeMedidaPacoteItemEstoque("UNIDADE(S)");
        massaTomate.setUnidadeMedidaUtilizadoNasReceitas("UNIDADE(S)");
        massaTomate.setQuantidadePorVolumeItemEstoque("1");
        massaTomate.setQuantidadeUtilizadaNasReceitas("1");

        massaTomate.calcularValorFracionadoModeloItemEstoque();
        massaTomate.calcularValorItemPorReceita();
        massaTomate.calcularQuantidadeTotalItemEmEstoqueEmGramas();
        massaTomate.calcularQuantidadeTotalItemEmEstoquePorVolumeSalvar();
        massaTomate.calcularCustoTotalDoItemEmEstoque();
        modeloItemEstoqueDAOMassaTomate.salvarItemEstoque(massaTomate);


        //Instanciando azeitonas com valores padrão---inicio
        ModeloItemEstoque azeitonas = new ModeloItemEstoque();
        ModeloItemEstoqueDAO modeloItemEstoqueDAOAzeitonas= new ModeloItemEstoqueDAO(context);
        azeitonas.setNomeItemEstoque("Azeitonas");
        azeitonas.setValorIndividualItemEstoque("1.50");
        azeitonas.setQuantidadeTotalItemEstoque("1");
        azeitonas.setUnidadeMedidaPacoteItemEstoque("GRAMA(S)");
        azeitonas.setUnidadeMedidaUtilizadoNasReceitas("GRAMA(S)");
        azeitonas.setQuantidadePorVolumeItemEstoque("50");
        azeitonas.setQuantidadeUtilizadaNasReceitas("50");

        azeitonas.calcularValorFracionadoModeloItemEstoque();
        azeitonas.calcularValorItemPorReceita();
        azeitonas.calcularQuantidadeTotalItemEmEstoqueEmGramas();
        azeitonas.calcularQuantidadeTotalItemEmEstoquePorVolumeSalvar();
        azeitonas.calcularCustoTotalDoItemEmEstoque();
        modeloItemEstoqueDAOAzeitonas.salvarItemEstoque(azeitonas);

        Intent intent = new Intent(context, EstoqueActivity.class);
        context.startActivity(intent);

    }
}
