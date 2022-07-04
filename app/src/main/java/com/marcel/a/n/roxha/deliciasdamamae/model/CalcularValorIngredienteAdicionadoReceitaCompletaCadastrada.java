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
    private List<Double> listaIngredientesAdicionadosReceitaCompletaCadastrada = new ArrayList<>();
    private String valorItemReceitaCadastradaCompleta;
    private String valorTotalIngredientesReceitaCadastradaCompleta;
    private Double valor;
    private boolean statusBancoRecuperar = false;

    private double valorAdicionado = 0;
    private double resultado = 0;

    public CalcularValorIngredienteAdicionadoReceitaCompletaCadastrada() {
    }




    public void calcularValoresIngredientesAdicionados(String idReceitaCadastradaCompleta, String nomeReceitaCompletaCadastrada){

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

                        ItemEstoqueModel itemEstoqueModel = documentSnapshot.toObject(ItemEstoqueModel.class);

                        assert itemEstoqueModel != null;

                        valorItemReceitaCadastradaCompleta = itemEstoqueModel.getValorItemPorReceita();

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



        }
    });
    }

    public double guardarValoresIngredientes(double valorIngrediente){

        resultado += valorIngrediente;
        System.out.println("Valor do resultado no guardar valores: " + resultado);
       return resultado;
    }

    public String getValorTotalCalculadoIngredientesReceitaCadastradaCompleta(){
       valorTotalIngredientesReceitaCadastradaCompleta =  String.valueOf(resultado);
        return valorTotalIngredientesReceitaCadastradaCompleta;
    }



}
