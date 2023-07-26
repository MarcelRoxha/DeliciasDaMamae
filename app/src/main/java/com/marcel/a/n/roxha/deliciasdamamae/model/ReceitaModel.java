package com.marcel.a.n.roxha.deliciasdamamae.model;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.marcel.a.n.roxha.deliciasdamamae.config.ConfiguracaoFirebase;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ReceitaModel implements Serializable {

    private String idReceita;
    private String nomeReceita;
    private String valorTotalReceita;
    private String valoresIngredientes;
    private String quantRendimentoReceita;
    private String porcentagemServico;
    private String quantBolototalLojaReceita;



    private FirebaseFirestore db = ConfiguracaoFirebase.getFirestor();



    public ReceitaModel() {


    }


    public String getIdReceita() {
        return idReceita;
    }

    public void setIdReceita(String idReceita) {
        this.idReceita = idReceita;
    }

    public String getNomeReceita() {
        return nomeReceita;
    }

    public void setNomeReceita(String nomeReceita) {
        this.nomeReceita = nomeReceita.trim();
    }

    public String getValorTotalReceita() {
        return valorTotalReceita;
    }

    public void setValorTotalReceita(String valorTotalReceita) {


        this.valorTotalReceita = valorTotalReceita;
    }

    public String getQuantRendimentoReceita() {
        return quantRendimentoReceita;
    }

    public void setQuantRendimentoReceita(String quantRendimentoReceita) {
        this.quantRendimentoReceita = quantRendimentoReceita;
    }

    public String getPorcentagemServico() {
        return porcentagemServico;
    }

    public void setPorcentagemServico(String porcentagemServico) {
        this.porcentagemServico = porcentagemServico;
    }

    public String getQuantBolototalLojaReceita() {
        return quantBolototalLojaReceita;
    }

    public void setQuantBolototalLojaReceita(String quantBolototalLojaReceita) {
        this.quantBolototalLojaReceita = quantBolototalLojaReceita;
    }

    public String getValoresIngredientes() {
        return valoresIngredientes;
    }

    public void setValoresIngredientes(String valoresIngredientes) {
        this.valoresIngredientes = valoresIngredientes;
    }

    public void salvarReceita(){


        CollectionReference receitaRef = ConfiguracaoFirebase.getFirestor().collection("Receitas_completas");


        Map<String, Object> receitaSalva = new HashMap<>();

        receitaSalva.put("nomeReceita", getNomeReceita());
        receitaSalva.put("quantRendimentoReceita", getQuantRendimentoReceita());
        receitaSalva.put("valorTotalReceita", getValorTotalReceita());

        receitaRef.add(receitaSalva).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });


    }


}