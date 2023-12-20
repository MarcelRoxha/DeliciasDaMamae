package com.marcel.a.n.roxha.deliciasdamamae.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.marcel.a.n.roxha.deliciasdamamae.config.ConfiguracaoFirebase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CalcularValorIngredienteAdicionadoReceitaCompletaCadastrada implements Serializable {

    private CollectionReference collectionReferenceReceitaCadastradaCompleta = ConfiguracaoFirebase.getFirestor().collection("Receitas_completas");
    private String idIngredientesReceitaCadastradaCompletaCadastrada;
    private String valorTotalReceitaCadastradaCompletaConvertString;
    private List<Double> listaIngredientesAdicionadosReceitaCompletaCadastrada = new ArrayList<>();
    private String valorItemReceitaCadastradaCompleta;
    private String valorTotalIngredientesReceitaCadastradaCompleta;
    private Double valor;
    private boolean statusBancoRecuperar = false;

    private double valorAdicionado = 0;
    private double resultado = 0;

    public CalcularValorIngredienteAdicionadoReceitaCompletaCadastrada() {
    }




    public String calcularValoresIngredientesAdicionados(String idReceitaCadastradaCompleta, String nomeReceitaCompletaCadastrada){

        final String[][] valorTotalIngredientes = {{""}};
       CollectionReference collectionReferencelistaIngredientesAdicionadosReceitaCompletaCalcularValor =  collectionReferenceReceitaCadastradaCompleta.document(idReceitaCadastradaCompleta).collection(nomeReceitaCompletaCadastrada);

    collectionReferencelistaIngredientesAdicionadosReceitaCompletaCalcularValor.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
        @Override
        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

            List<DocumentSnapshot> listIngredientes = queryDocumentSnapshots.getDocuments();
            for (DocumentSnapshot list: listIngredientes){

                idIngredientesReceitaCadastradaCompletaCadastrada = list.getId();

                collectionReferencelistaIngredientesAdicionadosReceitaCompletaCalcularValor.document(idIngredientesReceitaCadastradaCompletaCadastrada).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        ModeloItemEstoque ModeloItemEstoque = documentSnapshot.toObject(ModeloItemEstoque.class);

                        assert ModeloItemEstoque != null;

                        valorItemReceitaCadastradaCompleta = ModeloItemEstoque.getCustoPorReceitaItemEstoque();

                        valor = Double.parseDouble(valorItemReceitaCadastradaCompleta);
                        listaIngredientesAdicionadosReceitaCompletaCadastrada.add(valor);
                        statusBancoRecuperar = true;
                        guardarValoresIngredientes(valor);

                    }

                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
            }
            System.out.println("Antes do for contagem");
                double contagem = 0;
            for (double list: listaIngredientesAdicionadosReceitaCompletaCadastrada){
                contagem =+ list;
            }

            System.out.println("depois contagem tem o valor de: " + contagem);

        }
    });

        System.out.println("Antes do return valor da variável" + this.valorTotalReceitaCadastradaCompletaConvertString);

    return this.valorTotalReceitaCadastradaCompletaConvertString;
    }

    public double guardarValoresIngredientes(double valorIngrediente){

        resultado += valorIngrediente;
        System.out.println("Valor do resultado no guardar valores: " + resultado);
        this.valorTotalReceitaCadastradaCompletaConvertString = String.format("%.2f", resultado);
        System.out.println("Valor do resultado no guardar valores em String: " + resultado);
       return resultado;
    }

    public String getValorTotalCalculadoIngredientesReceitaCadastradaCompleta(){
       valorTotalIngredientesReceitaCadastradaCompleta =  String.valueOf(resultado);
        return valorTotalIngredientesReceitaCadastradaCompleta;
    }



}
