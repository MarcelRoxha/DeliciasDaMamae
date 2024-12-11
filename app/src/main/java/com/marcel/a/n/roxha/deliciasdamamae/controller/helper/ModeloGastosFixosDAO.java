package com.marcel.a.n.roxha.deliciasdamamae.controller.helper;

import android.content.Context;
import android.widget.TextView;
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
import com.marcel.a.n.roxha.deliciasdamamae.model.ModeloGastosFixos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModeloGastosFixosDAO implements InterfaceModeloGastosFixosDAO {

    private static final String COLLECTION_GASTOS_FIXOS = "GASTOS_FIXOS";
    private FirebaseFirestore firestore = ConfiguracaoFirebase.getFirestor();
    private CollectionReference collectionReference = firestore.collection(COLLECTION_GASTOS_FIXOS);

    private Context context;


    public ModeloGastosFixosDAO(Context context) {
        this.context = context;
    }

    @Override
    public boolean cadastrarGastoFixo(ModeloGastosFixos modeloGastosFixos) {

        if (modeloGastosFixos != null) {
            try {
                Map<String, Object> gastoSendoCadastrado = new HashMap<>();
                gastoSendoCadastrado.put("nomeDoGastoFixo", modeloGastosFixos.getNomeDoGastoFixo());
                gastoSendoCadastrado.put("valorGastoFixo", modeloGastosFixos.getValorGastoFixo());
                gastoSendoCadastrado.put("dataPagamento", modeloGastosFixos.getDataPagamento());
                gastoSendoCadastrado.put("dataPrevistaDePagamento", modeloGastosFixos.getDataPrevistaDePagamento());

                collectionReference.add(gastoSendoCadastrado).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(context, "Gasto " + modeloGastosFixos.getNomeDoGastoFixo() + " cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Algo deu errado verifique sua internet e tente novamente mais tarde", Toast.LENGTH_SHORT).show();
                    }
                });


            } catch (Exception e) {

            }
        }


        return false;
    }

    @Override
    public boolean atualizarGastoFixo(ModeloGastosFixos modeloGastosFixos, String idRecuperado) {
        return false;
    }

    public boolean recuperaTotalGastosFixos(TextView textViewValorExibe) {
        collectionReference = firestore.collection(COLLECTION_GASTOS_FIXOS);

        collectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                List<DocumentSnapshot> listaRecuperada = queryDocumentSnapshots.getDocuments();
                List<Double> listaValorController = new ArrayList<>();
                if (listaRecuperada.size() > 0) {
                    for (DocumentSnapshot list : listaRecuperada) {
                        ModeloGastosFixos modeloGastosFixosRecuperado = list.toObject(ModeloGastosFixos.class);
                        double valorRecuperado = modeloGastosFixosRecuperado.getValorGastoFixo();
                        listaValorController.add(valorRecuperado);
                    }

                    if (listaValorController.size() > 0) {
                        System.out.println("Recuperou algo na lista");
                        double valorIni = 0;
                        for (int i = 0; i < listaValorController.size(); ++i) {
                            valorIni = valorIni + listaValorController.get(i);
                        }

                        String valorConvert = String.valueOf(valorIni);
                        System.out.println("valorIni recuperado " + valorConvert);
                        if (valorConvert != null || !valorConvert.isEmpty()) {
                            textViewValorExibe.setText(valorConvert);
                        }else{
                            textViewValorExibe.setText("0");
                        }
                    }

                } else {
                    //Não encontrou Gasto fixo cadastrado
                }


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(context, "Algo deu errado, verifique a rede e tente novamente mais tarde", Toast.LENGTH_SHORT).show();
                System.out.println("Error Modelos Gastos Fixo DAO linha 86 --Xh-- " + e.getMessage());
            }
        });

        return false;
    }
}