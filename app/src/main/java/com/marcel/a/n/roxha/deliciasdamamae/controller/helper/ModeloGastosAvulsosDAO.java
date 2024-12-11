package com.marcel.a.n.roxha.deliciasdamamae.controller.helper;

import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.marcel.a.n.roxha.deliciasdamamae.config.ConfiguracaoFirebase;
import com.marcel.a.n.roxha.deliciasdamamae.model.ModeloFormataData;
import com.marcel.a.n.roxha.deliciasdamamae.model.ModeloGastosAvulsos;
import com.marcel.a.n.roxha.deliciasdamamae.model.ModeloGastosFixos;

import java.util.ArrayList;
import java.util.List;


public class ModeloGastosAvulsosDAO implements InterfaceModeloGastosAvulsosDAO{

    private Context context;
    private static final String COLLECTION_GASTOS_AVULSOS = "GASTOS_AVULSOS";
    private FirebaseFirestore firestore = ConfiguracaoFirebase.getFirestor();
    private CollectionReference collectionReference = firestore.collection(COLLECTION_GASTOS_AVULSOS);

    public ModeloGastosAvulsosDAO(Context context) {
        this.context = context;
    }

    @Override
    public boolean cadastrar(ModeloGastosAvulsos modeloGastosAvulsos) {
        return false;
    }

    public boolean recuperarValorTotalGastoAvulso(TextView textViewExibeValor){
        collectionReference = firestore.collection(COLLECTION_GASTOS_AVULSOS);
        ModeloFormataData formataData = new ModeloFormataData();
        String dataRef = formataData.getRetornaDataRefAtual();

        collectionReference.whereEqualTo("dataPagamentoAvulsos",dataRef).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                List<DocumentSnapshot> listaRecuperada = queryDocumentSnapshots.getDocuments();
                List<Double> listaValorController = new ArrayList<>();
                if (listaRecuperada.size() > 0) {
                    for (DocumentSnapshot list : listaRecuperada) {
                        ModeloGastosAvulsos modeloGastosFixosRecuperado = list.toObject(ModeloGastosAvulsos.class);
                        double valorRecuperado = modeloGastosFixosRecuperado.getValorGastoAvulsos();
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
                            textViewExibeValor.setText(valorConvert);
                        }else{
                            textViewExibeValor.setText("0");
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
                System.out.println("Error Modelos Gastos Avulso DAO linha 85 --Xh-- " + e.getMessage());
            }
        });

        return false;

    }
}
